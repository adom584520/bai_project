package com.pbtd.playuser.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.playuser.component.ConstantBeanConfig;
import com.pbtd.playuser.domain.PlayHistoryInfo;
import com.pbtd.playuser.exception.JsonMessageException;
import com.pbtd.playuser.mapper.PlayHistoryInfoTVMapper;
import com.pbtd.playuser.page.CollectHistoryQuery;
import com.pbtd.playuser.service.PlayHistoryInfoTVService;
import com.pbtd.playuser.util.UserVodConstant;

@Service
public class PlayHistoryInfoTVServiceImpl implements PlayHistoryInfoTVService {
	@Autowired
	private PlayHistoryInfoTVMapper playHistoryInfoMapper;
	@Autowired
	private ConstantBeanConfig constantBeanConfig;

	@Transactional
	public void insert(PlayHistoryInfo ph) {
		// 将改用户相同专辑的播放记录改为无效状态
		playHistoryInfoMapper.updateStatus(ph.getMac(), ph.getSeriesCode(),
				UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD,
				UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_DELETE);
		CollectHistoryQuery qo = new CollectHistoryQuery();
		qo.setRow(constantBeanConfig.total);
		qo.setMac(ph.getMac());
		qo.setStatus(UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD);
		List<PlayHistoryInfo> list = playHistoryInfoMapper.queryAll(qo);
		if (!(list != null && list.size() <= constantBeanConfig.total)) {
			PlayHistoryInfo endPh = list.get(list.size() - 1);
			playHistoryInfoMapper.updateStatusByGtId(endPh.getCreateTime(), ph.getMac(),
					UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD,
					UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_DELETE);
		}
		ph.setCreateTime(new Date());
		ph.setStatus(UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD);
		playHistoryInfoMapper.insert(ph);
	}

	@Transactional
	public void updateStatus(String mac, String seriesCode) {
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
				playHistoryInfoMapper.updateStatusBatch(mac, list,
						UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD,
						UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_DELETE);
				return;
			}
			// 删除单条记录
			playHistoryInfoMapper.updateStatus(mac, Integer.valueOf(seriesCode),
					UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD,
					UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_DELETE);
			return;
		}
		// 删除用户所有记录
		playHistoryInfoMapper.updateStatus(mac, null, UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD,
				UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_DELETE);
		return;
	}

	@Override
	public List<PlayHistoryInfo> queryAll(CollectHistoryQuery chq) {
		chq.setRow(constantBeanConfig.total);
		chq.setStatus(UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD);
		return playHistoryInfoMapper.queryAll(chq);
	}

	@Override
	public PlayHistoryInfo queryOne(String mac, Integer seriesCode) {
		List<PlayHistoryInfo> list = playHistoryInfoMapper.queryOne(mac, seriesCode,
				UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public void update(PlayHistoryInfo ph) {
		playHistoryInfoMapper.update(ph);

	}
}
