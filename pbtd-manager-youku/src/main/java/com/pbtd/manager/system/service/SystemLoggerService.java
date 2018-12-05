package com.pbtd.manager.system.service;

import java.util.List;

import com.pbtd.manager.system.domain.SystemLogger;
import com.pbtd.manager.system.page.SystemLoggerQueryObject;
import com.pbtd.manager.util.PageResult;

public interface SystemLoggerService {
	/**
	 * 分页加查询
	 * @param qo
	 * @return
	 */
	PageResult queryList(SystemLoggerQueryObject qo);

	void insert(SystemLogger logger);

	void delete(Long id);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(List<Long> ids);

	/**
	 * 根据条件删除
	 * @param qo
	 */
	void deleteCondition(SystemLoggerQueryObject qo);

	/**
	 * 定时删除任务
	 */
	void deleteTiming();
}
