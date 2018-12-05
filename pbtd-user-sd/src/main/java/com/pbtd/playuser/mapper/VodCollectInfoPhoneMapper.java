package com.pbtd.playuser.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.playuser.domain.VodCollectInfo;
import com.pbtd.playuser.page.CollectHistoryQuery;

public interface VodCollectInfoPhoneMapper {
	/**
	 * 添加收藏
	 * @param collectInfo
	 * @return
	 */
	int insert(VodCollectInfo collectInfo);

	/**
	 * 删除收藏
	 * @param userId
	 * @param seriesCode
	 * @return
	 */
	int delete(@Param("userId")String userId,@Param("seriesCode")Integer seriesCode);

	/**
	 * 查询收藏记录
	 * @param userId
	 * @return
	 */
	List<VodCollectInfo> queryAll(CollectHistoryQuery chq);

	/**
	 * 根据userId和seriesCode查询是否有数据
	 * @param userId
	 * @param seriesCode
	 * @return
	 */
	long queryIsCollect(@Param("userId")String userId, @Param("seriesCode")Integer seriesCode);

	/**
	 * 修改VodCollectInfo
	 * @param ph
	 */
	void update(VodCollectInfo collectInfo);

	/**
	 * 批量删除
	 * @param userId
	 * @param list
	 */
	void deleteBatch(@Param("userId")String userId,@Param("list")List<Integer> list);
}
