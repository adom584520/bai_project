package com.pbtd.manager.launcher.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.launcher.domain.OpTemplate;
import com.pbtd.manager.launcher.page.OpTemplateQueryObject;

public interface OpTemplateMapper {

	/**
	 * 高级查询加分页-查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(OpTemplateQueryObject qo);

	/**
	 * 高级查询加分页-查询数据集合
	 * @param qo
	 * @return
	 */
	List<OpTemplate> queryList(OpTemplateQueryObject qo);

	/**
	 * 增加运营位模板
	 * @param temp
	 * @return
	 */
	Integer insert(OpTemplate temp);

	/**
	 * 修改运营位模板
	 * @param operationPosition
	 * @return
	 */
	Integer update(OpTemplate temp);

	/**
	 * 删除运营位模板
	 * @param id
	 * @return
	 */
	Integer delete(Long id);

	/**
	 * 上下线，需要保存修改时间
	 * @param op
	 * @return
	 */
	Integer uplineOrDownLine(@Param("id")Long id,@Param("status")Integer status,@Param("modifyTime")Date modifyTime);

	/**
	 * 通过id查询运营位模板
	 * @param id
	 * @return
	 */
	OpTemplate queryById(Long id);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteBatch(List<Long> ids);

	/**
	 * 根据导航ID查询该导航是否有已上线运营位模板
	 * @param navId
	 * @param status
	 * @return
	 */
	OpTemplate queryByNavIdUpline(@Param("navId")Long navId,@Param("status")Integer status);

	/**
	 * 根据导航ID查询模板
	 * @param navId
	 * @return
	 */
	List<OpTemplate> queryListByNavId(Long navId);
}
