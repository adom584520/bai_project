package com.pbtd.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.domain.CpAlbuminfo;
import com.pbtd.manager.domain.CpAlbuminfoJoin;
import com.pbtd.manager.domain.CpDrama;
import com.pbtd.manager.domain.Drama;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.mapper.CpAlbuminfoJoinMapper;
import com.pbtd.manager.mapper.CpDramaMapper;
import com.pbtd.manager.mapper.DramaMapper;
import com.pbtd.manager.service.CpAlbuminfoService;
import com.pbtd.manager.service.CpDramaService;
import com.pbtd.manager.util.AlbuminfoConstant;

@Service
public class CpDramaServiceImpl implements CpDramaService {
	@Autowired
	private CpDramaMapper cpDramaMapper;
	@Autowired
	private CpAlbuminfoJoinMapper cpAlbuminfoJoinMapper;
	@Autowired
	private DramaMapper dramaMapper;
	@Autowired
	private CpAlbuminfoService cpAlbuminfoService;

	@Override
	@Transactional
	public void bindingDrama(Long cpDramaId, Long dramaId) {
		CpDrama cd = cpDramaMapper.queryById(cpDramaId);
		if (cd == null) {
			throw new JsonMessageException("数据有误，请刷新页面后重试！");
		}
		Drama drama = dramaMapper.queryById(dramaId);
		if (drama == null) {
			throw new JsonMessageException("数据有误，请刷新页面后重试！");
		}
		CpAlbuminfoJoin caj = cpAlbuminfoJoinMapper.validataDramaNoRepeat(drama.getId(), cd.getCpCode());
		if (caj != null) {
			if (!caj.getCpDramaId().equals(cd.getId())) {
				throw new JsonMessageException("该剧集已经与其他剧集绑定，请先将改剧集解除绑定好再进行绑定！");
			}
			return;
		}
		CpAlbuminfoJoin newCaj = cpAlbuminfoJoinMapper.queryByCpDrama(cpDramaId);
		if (newCaj != null) {
			newCaj.setSeriesCode(drama.getSeriesCode());
			newCaj.setDramaId(drama.getId());
			newCaj.setDrama(drama.getDrama());
			cpAlbuminfoJoinMapper.update(newCaj);
		} else {
			newCaj = new CpAlbuminfoJoin();
			newCaj.setCpCode(cd.getCpCode());
			newCaj.setCpSeriesCode(cd.getCpSeriesCode());
			newCaj.setCpDramaId(cd.getId());
			newCaj.setCpDrama(cd.getCpDrama());
			newCaj.setSeriesCode(drama.getSeriesCode());
			newCaj.setDramaId(drama.getId());
			newCaj.setDrama(drama.getDrama());
			cpAlbuminfoJoinMapper.insert(newCaj);
		}
		cd.setJoinStatus(AlbuminfoConstant.CP_DRAMA_JOIN_STATUS_CONFIRMED);
		cd.setDrama(drama.getDrama());
		cd.setDramaname(drama.getDramaname());
		cpDramaMapper.updateJoinStatus(cd);
	}

	@Override
	public void unpinlessBatch(List<Long> ids) {
		CpDrama cd = new CpDrama();
		cd.setDrama(null);
		cd.setDramaname("");
		cd.setJoinStatus(AlbuminfoConstant.CP_DRAMA_JOIN_STATUS_NOT);
		cpDramaMapper.unpinlessBatch(ids, cd);
	}

	@Override
	public List<Long> queryIdsByAlbumId(List<Long> albumIds) {
		return cpDramaMapper.queryIdsByAlbumId(albumIds);
	}

	@Override
	public List<CpDrama> queryByAlbumId(Long cpSeriesCode, String cpCode) {
		return cpDramaMapper.queryByAlbumId(cpSeriesCode, cpCode);
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public void cpUploadDrama(List<CpDrama> list, String cpCode) {
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				CpDrama cd = list.get(i);
				cd.setCpCode(cpCode);
				//cp_drama有么有
				CpDrama cd2 = cpDramaMapper.queryByCpDrama(cd);
				if (cd2 == null) {
					// 根据剧集的cp专辑code查询该cp方的专辑是否有确认绑定的专辑    J:查专辑是否传了
					CpAlbuminfo ca = cpAlbuminfoService.queryByCpSeriesCode(cd.getCpSeriesCode(), cpCode);
					// 如果有绑定则查询出专辑下的所有剧集
					if (ca != null) {
						if(AlbuminfoConstant.CP_ALBUMINFO_JOIN_STATUS_CONFIRMED.equals(ca.getJoinStatus())){
							Long seriesCode = ca.getSeriesCode();
							List<Drama> dramas = dramaMapper.queryBySeriesCode(seriesCode);
							// 遍历剧集，根据剧集的名称判断是否相同
							if (dramas != null && dramas.size() > 0) {
								for (int j = 0; j < dramas.size(); j++) {
									Drama drama = dramas.get(j);
									// 如果剧集名称相同，则再判断播放时长是否相同
									if (drama.getDramaname().equals(cd.getCpDramaname())) {
										// 如果播放时长相同，则绑定该剧集
										if (drama.getDuration().equals(cd.getDuration())) {
											cd.setDrama(drama.getDrama());
											cd.setDramaname(drama.getDramaname());
											break;
										}
									}
								}
							}
						}
						//J:cp方和我方，专辑未关联，只保存，不匹配
						cd.setUpdateTime(new Date());
						cd.setCreateTime(new Date());
						cd.setJoinStatus(AlbuminfoConstant.CP_ALBUMINFO_JOIN_STATUS_NOT); //J:剧集没自动匹配
						cd.setDramaname("");
						cpDramaMapper.insert(cd);
					}
					//J:cp方没有发专辑，不处理
				} else {
					cd2.setCpDramaname(cd.getCpDramaname());
					cd2.setDescription(cd.getDescription());
					cd2.setDuration(cd.getDuration());
					cd2.setType(cd.getType());
					cd2.setUpdateTime(new Date());
					cpDramaMapper.update(cd2);
				}
			}
		}
	}

	@Override
	@Transactional
	public void confirm(List<Long> list) {
		if (list != null && list.size() > 0) {
			cpDramaMapper.confirm(list, AlbuminfoConstant.CP_DRAMA_JOIN_STATUS_CONFIRMED);
			List<CpDrama> cds = cpDramaMapper.queryBatchById(list);
			for (int i = 0; i < cds.size(); i++) {
				CpDrama cd = cds.get(i);
				CpAlbuminfoJoin caj = new CpAlbuminfoJoin();
				caj.setCpCode(cd.getCpCode());
				caj.setCpDrama(cd.getCpDrama());
				caj.setCpDramaId(cd.getId());
				caj.setCpSeriesCode(cd.getCpSeriesCode());
				caj.setDrama(cd.getDrama());
				Drama drama = dramaMapper.queryByDramaName(cd.getDrama(), cd.getDramaname(),cd.getDuration());
				if(drama==null){
					throw new JsonMessageException("己方数据库不存在该剧集！");
				}
				caj.setDramaId(drama.getId());
				caj.setSeriesCode(drama.getSeriesCode());
				cpAlbuminfoJoinMapper.insert(caj);
			}
		}
	}

}
