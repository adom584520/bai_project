package com.pbtd.manager.launcher.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.launcher.domain.LauncherUI;
import com.pbtd.manager.launcher.page.LauncherUIQueryObject;

public interface LauncherUIMapper {
	/**
	 * 高级查询加分页-查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(LauncherUIQueryObject qo);

	/**
	 * 高级查询加分页-查询数据集合
	 * @param qo
	 * @return
	 */
	List<LauncherUI> queryList(LauncherUIQueryObject qo);

	/**
	 * 根据id查询LauncherUI
	 * @param id
	 * @return
	 */
	LauncherUI queryById(Long id);

	/**
	 * 增加mac
	 * @param launcherGroupDetail
	 * @return
	 */
	Integer insert(LauncherUI LauncherUI);

	/**
	 * 修改mac
	 * @param launcherGroupDetail
	 * @return
	 */
	Integer update(LauncherUI LauncherUI);

	/**
	 * 删除launcherGroupDetail
	 * @param id
	 * @return
	 */
	Integer delete(Long id);

	/**
	 * 通过上线状态码查询是否已有上线数据
	 * @param status
	 * @return
	 */
	LauncherUI queryGroupDetailByStatus(@Param("groupId")Long groupId,@Param("status")Integer status);

	/**
	 * 根据id上下线
	 * @param launcherUI
	 * @return
	 */
	Integer uplineOrDownline(LauncherUI launcherUI);

	/**
	 * 根据id查询GroupDetail
	 * @param id
	 * @return
	 */
	LauncherUI queryLauncherUI(Long id);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteBatch(List<Long> ids);

	/**
	 * 根据分组ID修改数据是否有效
	 * @param groupId
	 * @param dataType
	 */
	void updateDataStatus(@Param("list")List<Long> list,@Param("dataStatus")Integer dataStatus);
}
