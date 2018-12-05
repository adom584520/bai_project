package com.pbtd.playuser.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.playuser.domain.PlayHistoryInfo;
import com.pbtd.playuser.page.CollectHistoryQuery;

public interface PlayHistoryInfoTVMapper {
	/**
	 * 添加播放记录
	 * @param ph
	 * @return
	 */
	int insert(PlayHistoryInfo ph);

	/**
	 * 批量修改播放记录状态
	 * @param userId
	 * @param list
	 * @param qeuryStatus 查询状态
	 * @param status 修改状态
	 * @return
	 */
	int updateStatusBatch(@Param("mac")String mac,@Param("list")List<Integer> list,@Param("queryStatus")Integer queryStatus,@Param("status")Integer status);

	/**
	 * 查询播放记录
	 * @return
	 */
	List<PlayHistoryInfo> queryAll(CollectHistoryQuery chq);

	/**
	 * 修改播放记录状态
	 * @param userId
	 * @param list
	 * @param qeuryStatus 查询状态
	 * @param status 修改状态
	 * @return
	 */
	int updateStatus(@Param("mac")String mac,@Param("seriesCode")Integer seriesCode,@Param("queryStatus")Integer queryStatus,@Param("status")Integer status);
	
	/**
	 * 根据用户播放所有的记录修改播放记录状态
	 * @param userId
	 * @param qeuryStatus 查询状态
	 * @param status 修改状态
	 * @param id 最后一条数据的ID
	 * @return
	 */
	int updateStatusByGtId(@Param("createTime")Date createTime,@Param("mac")String mac,@Param("queryStatus")Integer queryStatus,@Param("status")Integer status);
	/**
	 * 根据userId和seriesCode查询单挑记录
	 * @param userId
	 * @param seriesCode
	 * @return
	 */
	List<PlayHistoryInfo> queryOne(@Param("mac")String mac,@Param("seriesCode")Integer seriesCode,@Param("status")Integer status);

	/**
	 * 修改PlayHistoryInfo
	 * @param ph
	 */
	void update(PlayHistoryInfo ph);
}
