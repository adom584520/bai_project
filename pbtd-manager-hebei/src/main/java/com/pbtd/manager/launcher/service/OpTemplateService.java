package com.pbtd.manager.launcher.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.launcher.domain.OpTemplate;
import com.pbtd.manager.launcher.page.OpTemplateQueryObject;
import com.pbtd.manager.util.PageResult;

public interface OpTemplateService {
	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(OpTemplateQueryObject qo);

	/**
	 * 添加运营位模板
	 * @param temp
	 */
	void insert(OpTemplate temp);

	/**
	 * 删除运营位模板
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改运营位模板
	 * @param temp
	 */
	void update(OpTemplate temp);

	/**
	 * 上下线操作，需要判断该导航的相同位置是否有已上线的运营位
	 * @param op
	 */
	void uplineOrDownLine(Long id,Integer status);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(List<Long> ids);

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

	/**
	 * 根据ID查询单条数据
	 * @param id
	 * @return
	 */
	OpTemplate queryById(Long id);

	/**
	 * 拷贝数据
	 * @param temp
	 */
	void copy(OpTemplate temp);

	/**
	 * 根据导航ID修改数据是否有效
	 * @param groupId
	 * @param dataType
	 */
	void updateDataStatus(List<Long> list,Integer dataStatus);

	/**
	 * 根据分组ID修改数据是否有效
	 * @param groupId
	 * @param dataType
	 */
	void updateDataStatusByGroupId(List<Long> list,Integer dataStatus);
}
