package com.pbtd.playuser.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.playuser.domain.VodCollectInfo;
import com.pbtd.playuser.exception.JsonMessageException;
import com.pbtd.playuser.mapper.VodCollectInfoPhoneMapper;
import com.pbtd.playuser.page.CollectHistoryQuery;
import com.pbtd.playuser.service.VodCollectInfoPhoneService;

@Service
public class VodCollectInfoPhoneServiceImpl implements VodCollectInfoPhoneService {
	@Autowired
	private VodCollectInfoPhoneMapper vodCollectInfoMapper;

	@Transactional
	public void insert(VodCollectInfo collectInfo) {
		vodCollectInfoMapper.delete(collectInfo.getUserId(), collectInfo.getSeriesCode());
		collectInfo.setCreateTime(new Date());
		vodCollectInfoMapper.insert(collectInfo);
	}

	@Transactional
	public void delete(String userId, String seriesCode) {
		if (seriesCode != null && seriesCode.length() > 0) {
			String separator = ",";
			if (seriesCode.indexOf(separator) != -1) {
				List<Integer> list = new ArrayList<Integer>();
				String[] split = seriesCode.split(separator);
				if (split.length > 0) {
					for (int i = 0; i < split.length; i++) {
						list.add(Integer.valueOf(split[i]));
					}
				} else {
					throw new JsonMessageException("seriesCode参数不正确！");
				}
				// 批量删除
				vodCollectInfoMapper.deleteBatch(userId, list);
				return;
			}
			// 删除单条记录
			vodCollectInfoMapper.delete(userId, Integer.valueOf(seriesCode));
			return;
		}
		// 删除用户所有记录
		vodCollectInfoMapper.delete(userId, null);
		return;
	}

	@Override
	public List<VodCollectInfo> queryAll(CollectHistoryQuery chq) {
		return vodCollectInfoMapper.queryAll(chq);
	}

	@Override
	public boolean queryIsCollect(String userId, Integer seriesCode) {
		return vodCollectInfoMapper.queryIsCollect(userId, seriesCode) < 1L ? false : true;
	}

	@Override
	public void update(VodCollectInfo collectInfo) {
		vodCollectInfoMapper.update(collectInfo);
	}
}
