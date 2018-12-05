package com.pbtd.manager.launcher.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.launcher.domain.OperationPosition;
import com.pbtd.manager.launcher.page.OperationPositionQueryObject;

public interface OperationPositionMapper {

	/**
	 * 高级查询加分页-查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(OperationPositionQueryObject qo);

	/**
	 * 高级查询加分页-查询数据集合
	 * @param qo
	 * @return
	 */
	List<OperationPosition> queryList(OperationPositionQueryObject qo);

	/**
	 * 增加运营位
	 * @param operationPosition
	 * @return
	 */
	Integer insert(OperationPosition operationPosition);

	/**
	 * 修改运营位
	 * @param operationPosition
	 * @return
	 */
	Integer update(OperationPosition operationPosition);

	/**
	 * 删除运营位
	 * @param id
	 * @return
	 */
	Integer delete(Long id);

	/**
	 * 上下线，需要保存修改时间
	 * @param op
	 * @return
	 */
	Integer uplineOrDownLine(OperationPosition op);

	/**
	 * 根据导航id查询该导航中指定位置的已上线运营位
	 * @param id
	 * @param sortpos
	 * @param status
	 * @return
	 */
	OperationPosition queryByNavIdSortpos(@Param("navId")Long navId,@Param("sortpos")Integer sortpos,@Param("status")Integer status);

	/**
	 * 通过id查询运营位
	 * @param id
	 * @return
	 */
	OperationPosition queryById(Long id);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteBatch(List<Long> ids);

	/**
	 * 根据运营位模板ID修改运营位上下线
	 * @param tempId
	 * @param status
	 */
	void updateStatusByTempId(@Param("tempId")Long tempId, @Param("status")Integer status,@Param("modifyTime")Date modifyTime);

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
	Integer batchInsert(List<OperationPosition> opList);
}
