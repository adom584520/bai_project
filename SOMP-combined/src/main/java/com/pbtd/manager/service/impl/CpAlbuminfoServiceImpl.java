package com.pbtd.manager.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.domain.Albuminfo;
import com.pbtd.manager.domain.CpAlbuminfo;
import com.pbtd.manager.domain.CpDrama;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.mapper.CpAlbuminfoJoinMapper;
import com.pbtd.manager.mapper.CpAlbuminfoMapper;
import com.pbtd.manager.query.CpAlbuminfoQueryObject;
import com.pbtd.manager.service.AlbuminfoService;
import com.pbtd.manager.service.CpAlbuminfoService;
import com.pbtd.manager.service.CpDramaService;
import com.pbtd.manager.util.AlbuminfoConstant;
import com.pbtd.manager.util.PageResult;

@Service
public class CpAlbuminfoServiceImpl implements CpAlbuminfoService {
	@Autowired
	private CpAlbuminfoMapper cpAlbuminfoMapper;
	@Autowired
	private CpAlbuminfoJoinMapper cpAlbuminfoJoinMapper;
	@Autowired
	private CpDramaService cpDramaService;
	@Autowired
	private AlbuminfoService albuminfoService;

	@Override
	public PageResult queryList(CpAlbuminfoQueryObject qo) {
		Long count = cpAlbuminfoMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<CpAlbuminfo> data = cpAlbuminfoMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	public CpAlbuminfo queryById(Long id) {
		return cpAlbuminfoMapper.queryById(id);
	}

	@Override
	public CpAlbuminfo queryBySeriesCode(Long seriesCode, String cpCode) {
		return cpAlbuminfoMapper.queryBySeriesCode(seriesCode, cpCode);
	}

	@Override
	@Transactional
	public void bindingAlbum(Long id, Long seriesCode, String seriesName, String cpCode) {
		CpAlbuminfo ca = cpAlbuminfoMapper.queryBySeriesCode(seriesCode, cpCode);
		if (ca != null) {
			if (!id.equals(ca.getId())) {
				throw new JsonMessageException("该专辑已绑定，如果想关联该专辑，请先将该专辑解除绑定！");
			}
		}
		cpAlbuminfoMapper.bindingAlbum(id, seriesCode, seriesName,
				AlbuminfoConstant.CP_ALBUMINFO_JOIN_STATUS_CONFIRMED);
	}

	@Override
	@Transactional
	public void unpinless(List<Long> ids) {
		CpAlbuminfo ca = new CpAlbuminfo();
		ca.setSeriesCode(null);
		ca.setSeriesName("");
		ca.setJoinStatus(AlbuminfoConstant.CP_ALBUMINFO_JOIN_STATUS_NOT);
		//先清空专辑绑定
		cpAlbuminfoMapper.unpinless(ids, ca);
		List<CpAlbuminfo> cas = cpAlbuminfoMapper.queryByIds(ids);
		if (cas.size() < 1) {
			return;
		}
		
		List<Long> cdIdList = new ArrayList<Long>();
		for (int i = 0; i < cas.size(); i++) {
			CpAlbuminfo cpAlbuminfo = cas.get(i);
			List<CpDrama> cdList = cpDramaService.queryByAlbumId(cpAlbuminfo.getCpSeriesCode(),
					cpAlbuminfo.getCpCode());
			if (cdList.size() < 1) {
				continue;
			}
			for (int j = 0; j < cdList.size(); j++) {
				cdIdList.add(cdList.get(j).getId());
			}
		}
		// 清除专辑下的所有剧集的绑定
		cpDramaService.unpinlessBatch(cdIdList);
		
		// 删除专辑绑定 关联表中剧集绑定的数据
		cpAlbuminfoJoinMapper.deleteByDramaIdBatch(cdIdList);
	}

	@Override
	public void updateStatus(String cpCode, Long cpSeriesCode, Integer status) {
		cpAlbuminfoMapper.updateStatus(cpCode, cpSeriesCode, status);
	}

	/**
	 * 新增  生成绑定关系
	 * 更新
	 */
	@Override
	public void cpUploadAlbum(List<CpAlbuminfo> list, String cpCode) {
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				CpAlbuminfo ca = list.get(i);
				//某个CP，某个专辑有没有
				CpAlbuminfo ca2 = cpAlbuminfoMapper.queryByCpSeriesCode(ca.getCpSeriesCode(), cpCode);
				ca.setJoinStatus(AlbuminfoConstant.CP_ALBUMINFO_JOIN_STATUS_NOT);
				ca.setSeriesName("");
				if (ca2 == null) {
					// 根据专辑名称范围查找我方专辑数据
					List<Albuminfo> list2 = albuminfoService.queryBySeriesName(ca.getCpSeriesName());
					if (list2 != null && list2.size() > 0) {
						// 将查询出来的数据遍历
						for (int j = 0; j < list2.size(); j++) {
							// 判断我方该专辑有无绑定数据（条件：专辑code，cpCode），有则不进行匹配， 无则继续下一步
							Albuminfo albuminfo = list2.get(j);
							CpAlbuminfo cpAlbum = cpAlbuminfoMapper.queryBySeriesCodeCpCode(albuminfo.getSeriesCode(),
									cpCode);
							if (cpAlbum == null) {
								// 判断播放时长是否相同
								if (albuminfo.getDuration().equals(ca.getDuration())) {
									// 判断地区是否相同
									if (albuminfo.getOriginalCountry().equals(ca.getOriginalCountry())) {
										// 设置状态为未确认绑定，绑定关系在确认绑定关系的时候添加      
										//J:未确认状态，需要运营确认->确认状态
										ca.setJoinStatus(AlbuminfoConstant.CP_ALBUMINFO_JOIN_STATUS_UNCONFIRMED);
										//J:设置的我方冗余字段
										ca.setSeriesCode(albuminfo.getSeriesCode());
										ca.setSeriesName(albuminfo.getSeriesName());
										break;
									}
								}
							}
						}
					}
					ca.setCpCode(cpCode);
					ca.setUpdateTime(new Date());
					ca.setCreateTime(new Date());
					cpAlbuminfoMapper.insert(ca);
				} else {
					ca2.setActorName(ca.getActorName());
					ca2.setCpSeriesName(ca.getCpSeriesName());
					ca2.setCurrentNum(ca.getCurrentNum());
					ca2.setDescription(ca.getDescription());
					ca2.setDuration(ca.getDuration());
					ca2.setOrgairDate(ca.getOrgairDate());
					ca2.setOriginalCountry(ca.getOriginalCountry());
					ca2.setOriginalName(ca.getOriginalName());
					ca2.setReleaseYear(ca.getReleaseYear());
					ca2.setStatus(ca.getStatus());
					ca2.setTag(ca.getTag());
					ca2.setUpdateTime(new Date());
					ca2.setViewPoint(ca.getViewPoint());
					ca2.setVolumnCount(ca.getVolumnCount());
					ca2.setWriterName(ca.getWriterName());
					cpAlbuminfoMapper.update(ca2);
				}
			}
		}
	}

	@Override
	public void confirm(List<Long> ids) {
		if (ids != null && ids.size() > 0) {
			cpAlbuminfoMapper.confirm(ids, AlbuminfoConstant.CP_ALBUMINFO_JOIN_STATUS_CONFIRMED);
			return;
		}
		throw new JsonMessageException("未选中需要确认绑定的数据！");
	}

	@Override
	public CpAlbuminfo queryByCpSeriesCode(Long cpSeriesCode, String cpCode) {
		return cpAlbuminfoMapper.queryByCpSeriesCode(cpSeriesCode, cpCode);
	}
}
