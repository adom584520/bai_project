package com.pbtd.playuser.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.playuser.component.ConstantBeanConfig;
import com.pbtd.playuser.domain.PlayHistoryInfo;
import com.pbtd.playuser.exception.JsonMessageException;
import com.pbtd.playuser.mapper.PlayHistoryInfoPhoneMapper;
import com.pbtd.playuser.page.CollectHistoryQuery;
import com.pbtd.playuser.service.PlayHistoryInfoPhoneService;
import com.pbtd.playuser.util.UserVodConstant;

@Service
public class PlayHistoryInfoPhoneServiceImpl implements PlayHistoryInfoPhoneService {
	@Autowired
	private PlayHistoryInfoPhoneMapper playHistoryInfoMapper;
	@Autowired
	private ConstantBeanConfig constantBeanConfig;

	@Transactional
	public int insert(Map<String, Object> queryParams) {
		// 将改用户相同专辑的播放记录改为无效状态
		/*playHistoryInfoMapper.updateStatus(ph.getUserId(), ph.getSeriesCode(),
				UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD,
				UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_DELETE);
		CollectHistoryQuery qo = new CollectHistoryQuery();
		qo.setRow(constantBeanConfig.total);
		qo.setUserId(ph.getUserId());
		qo.setStatus(UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD);
		List<PlayHistoryInfo> list = playHistoryInfoMapper.queryAll(qo);
		//当数据超过配置的条数，将数据库最后一条数据创建时间之前的数据删除
		if (!(list != null && list.size() <= constantBeanConfig.total)) {
			PlayHistoryInfo endPh = list.get(list.size() - 1);
			playHistoryInfoMapper.updateStatusByGtId(endPh.getCreateTime(), ph.getUserId(),
					UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD,
					UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_DELETE);
		}*/
		queryParams.put("createtime", new Date());
		queryParams.put("status", UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD);
		queryParams.put("id",null);
		int flag = 0;
		try {
			int a =  playHistoryInfoMapper.insert(queryParams);
			
			flag =  Integer.valueOf(queryParams.get("id").toString());
		} catch (Exception e) {
			System.out.println(e);
			flag = -1;
		}
		return flag;
	}

	@Transactional
	public void updateStatus(String userId, String seriesCode) {
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
				playHistoryInfoMapper.updateStatusBatch(userId, list,
						UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD,
						UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_DELETE);
				return;
			}
			// 删除单条记录
			playHistoryInfoMapper.updateStatus(userId, Integer.valueOf(seriesCode),
					UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD,
					UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_DELETE);
			return;
		}
		// 删除用户所有记录
		playHistoryInfoMapper.updateStatus(userId, null, UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD,
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
	public PlayHistoryInfo queryOne(String userId, Integer seriesCode) {
		List<PlayHistoryInfo> list = playHistoryInfoMapper.queryOne(userId, seriesCode,
				UserVodConstant.USER_VOD_PLAY_HISTORY_STATUS_STANDARD);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Override
	@Transactional
	public void update(Map<String, Object> queryParams) {
		playHistoryInfoMapper.update( queryParams);
	}
}
