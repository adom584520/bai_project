package com.pbtd.manager.system.mapper;

import java.util.List;

import com.pbtd.manager.system.domain.SystemLogger;
import com.pbtd.manager.system.page.SystemLoggerQueryObject;

/**
 * 系统日志Mapper
 * 
 * @author JOJO
 *
 */
public interface SystemLoggerMapper {
	/**
	 * 查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(SystemLoggerQueryObject qo);

	/**
	 * 分页加查询
	 * @param qo
	 * @return
	 */
	List<SystemLogger> queryList(SystemLoggerQueryObject qo);

	Integer insert(SystemLogger logger);

	Integer delete(Long id);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteBatch(List<Long> ids);

	/**
	 * 根据条件删除日志
	 * @param qo
	 */
	Integer deleteCondition(SystemLoggerQueryObject qo);
}
