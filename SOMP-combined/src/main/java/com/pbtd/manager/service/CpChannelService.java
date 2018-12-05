package com.pbtd.manager.service;

import java.util.List;

import com.pbtd.manager.domain.CpChannel;
import com.pbtd.manager.query.CpChannelQueryObject;
import com.pbtd.manager.util.PageResult;

public interface CpChannelService {
	PageResult queryList(CpChannelQueryObject qo);

	/**
	 * 绑定频道
	 * @param cc
	 */
	void bindingChannel(CpChannel cc);

	/**
	 * 批量解除绑定
	 * @param ids
	 */
	void unpinless(List<Integer> ids);

	CpChannel queryByChnCode(String chnCode,String cpCode);

	CpChannel queryByCpChnCode(String cpChnCode,String cpCode);

	void insert(CpChannel cc);

	void update(CpChannel cc);

	void cpUploadChannel(List<CpChannel> list,String cpCode);
}
