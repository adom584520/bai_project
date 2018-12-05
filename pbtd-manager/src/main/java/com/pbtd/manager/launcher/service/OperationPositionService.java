package com.pbtd.manager.launcher.service;

import java.util.List;

import com.pbtd.manager.launcher.domain.OperationPosition;
import com.pbtd.manager.launcher.page.OperationPositionQueryObject;
import com.pbtd.manager.util.PageResult;

public interface OperationPositionService {
	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(OperationPositionQueryObject qo);

	/**
	 * 添加运营位
	 * @param operationPosition
	 */
	void insert(OperationPosition operationPosition);

	/**
	 * 删除运营位
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改运营位
	 * @param id
	 */
	void update(OperationPosition operationPosition);

	/**
	 * 上下线操作，需要判断该导航的相同位置是否有已上线的运营位
	 * @param op
	 */
	void uplineOrDownLine(OperationPosition op);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(List<Long> ids);

	/**
	 * 根据运营位模板的ID修改运营位上下线状态
	 * @param tempId
	 * @param status
	 */
	void updateStatusByTempId(Long tempId,Integer status);

	/**
	 * 根据ID查询单条记录
	 * @param id
	 * @return
	 */
	OperationPosition queryById(Long id);

	/**
	 * 拷贝数据
	 * @param operationPosition
	 */
	void copy(OperationPosition operationPosition);
	
	/**
	 * 根据tempId查询数据
	 * @param tempId
	 * @return
	 */
	List<OperationPosition> queryByTempId(Long tempId);
	
	/**
	 * 批量添加
	 * @param opList
	 * @return
	 */
	void batchInsert(List<OperationPosition> opList);
}
