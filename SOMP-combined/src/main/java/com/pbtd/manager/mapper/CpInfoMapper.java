package com.pbtd.manager.mapper;

import java.util.List;

import com.pbtd.manager.domain.CpInfo;
import com.pbtd.manager.query.CpInfoQueryObject;

public interface CpInfoMapper {
	/**
	 * 查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(CpInfoQueryObject qo);

	/**
	 * 查询总记录
	 * @param qo
	 * @return
	 */
	List<CpInfo> queryList(CpInfoQueryObject qo);

	List<CpInfo> queryAll();

	CpInfo queryByCode(String code);

	void insert(CpInfo cpInfo);

	void update(CpInfo cpInfo);

	void delete(Integer id);
}
