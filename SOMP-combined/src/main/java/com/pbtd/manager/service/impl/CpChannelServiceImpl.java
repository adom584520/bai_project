package com.pbtd.manager.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.domain.CpChannel;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.mapper.CpChannelMapper;
import com.pbtd.manager.query.CpChannelQueryObject;
import com.pbtd.manager.service.CpChannelService;
import com.pbtd.manager.util.AlbuminfoConstant;
import com.pbtd.manager.util.PageResult;

@Service
public class CpChannelServiceImpl implements CpChannelService {
	@Autowired
	private CpChannelMapper cpChannelMapper;

	@Override
	public PageResult queryList(CpChannelQueryObject qo) {
		Long count = cpChannelMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<CpChannel> data = cpChannelMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	public void bindingChannel(CpChannel cc) {
		CpChannel cc2 = cpChannelMapper.queryById(cc.getId());
		CpChannel newCc = cpChannelMapper.queryByChnCode(cc.getChnCode(), cc2.getCpCode());
		if (newCc != null) {
			if (!newCc.getId().equals(cc.getId())) {
				throw new JsonMessageException("该频道已有绑定的数据！");
			}
		}
		cc.setJoinStatus(AlbuminfoConstant.CP_ALBUMINFO_JOIN_STATUS_CONFIRMED);
		cpChannelMapper.bindingChannel(cc);
	}

	@Override
	public CpChannel queryByChnCode(String chnCode, String cpCode) {
		return cpChannelMapper.queryByChnCode(chnCode, cpCode);
	}

	@Override
	public CpChannel queryByCpChnCode(String cpChnCode, String cpCode) {
		return cpChannelMapper.queryByCpChnCode(cpChnCode, cpCode);
	}

	@Override
	public void insert(CpChannel cc) {
		cpChannelMapper.insert(cc);
	}

	@Override
	public void update(CpChannel cc) {
		cpChannelMapper.update(cc);
	}

	@Override
	public void unpinless(List<Integer> ids) {
		CpChannel cc = new CpChannel();
		cc.setChnCode("");
		cc.setChnName("");
		cc.setJoinStatus(AlbuminfoConstant.CP_CHANNEL_JOIN_STATUS_NOT);
		cpChannelMapper.unpinless(ids, cc);

	}

	@Override
	@Transactional
	public void cpUploadChannel(List<CpChannel> list, String cpCode) {
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				CpChannel cc = list.get(i);
				cc.setCpCode(cpCode);
				CpChannel cc2 = cpChannelMapper.queryByCpChnCode(cc.getCpChnCode(), cpCode);
				if (cc2 == null) {
					cc.setUpdateTime(new Date());
					cc.setCreateTime(new Date());
					cc.setJoinStatus(AlbuminfoConstant.CP_CHANNEL_JOIN_STATUS_NOT);
					cpChannelMapper.insert(cc);
				} else {
					cc2.setCpChnName(cc.getCpChnName());
					cc2.setStatus(cc.getStatus());
					cc2.setUpdateTime(new Date());
					cpChannelMapper.update(cc2);
				}
			}
		}
	}
}
