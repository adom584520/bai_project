package com.pbtd.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.domain.CpChannel;
import com.pbtd.manager.query.CpChannelQueryObject;

public interface CpChannelMapper {
	/**
	 * 查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(CpChannelQueryObject qo);

	/**
	 * 查询总记录
	 * @param qo
	 * @return
	 */
	List<CpChannel> queryList(CpChannelQueryObject qo);
	
	CpChannel queryById(Integer id);

	void unpinless(@Param("ids")List<Integer> ids, @Param("cc")CpChannel cc);

	void bindingChannel(CpChannel cc);

	CpChannel queryByChnCode(@Param("chnCode")String chnCode,@Param("cpCode")String cpCode);

	CpChannel queryByCpChnCode(@Param("cpChnCode")String cpChnCode,@Param("cpCode") String cpCode);

	void insert(CpChannel cc);

	void update(CpChannel cc);
}
