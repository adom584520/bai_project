package com.pbtd.manager.launcher.mapper;

import java.util.List;

import com.pbtd.manager.launcher.domain.Mac;
import com.pbtd.manager.launcher.page.MacQueryObject;

public interface MacMapper {

	/**
	 * 高级查询加分页-查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(MacQueryObject qo);

	/**
	 * 高级查询加分页-查询数据集合
	 * @param qo
	 * @return
	 */
	List<Mac> queryList(MacQueryObject qo);

	/**
	 * 增加mac
	 * @param mac
	 * @return
	 */
	Integer insert(Mac mac);

	/**
	 * 修改mac
	 * @param mac
	 * @return
	 */
	Integer update(Mac mac);

	/**
	 * 删除mac
	 * @param id
	 * @return
	 */
	Integer delete(Long id);

	/**
	 * 通过macName查询mac
	 * @param macName
	 * @return
	 */
	Mac getMacByMacName(String macName);

	/**
	 * 根据macName模糊查询出mac的分组id
	 * @param macName
	 * @return
	 */
	List<Long> queryListGroupIdByMacName(String macName);

	/**
	 * 根据mac名称批量查询数据库已有的mac名称
	 * @param macNameList
	 * @return
	 */
	List<String> batchQueryByMacName(List<String> macNameList);

	/**
	 * 批量添加
	 * @param macList
	 * @return
	 */
	Integer batchInsert(List<Mac> macList);

	/**
	 * 查询分组下所有的mac
	 * @param groupId
	 * @return
	 */
	List<Mac> batchExportByGroupId(Long groupId);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteBatch(List<Long> ids);
}
