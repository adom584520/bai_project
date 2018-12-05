package com.pbtd.manager.vod.phone.slideshow.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.vod.phone.slideshow.domain.Slideshow;
import com.pbtd.manager.vod.phone.slideshow.query.SlideshowQueryObject;

public interface SlideshowMapper {
	/**
	 * 高级查询加分页-查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(SlideshowQueryObject qo);

	/**
	 * 高级查询加分页-查询数据集合
	 * @param qo
	 * @return
	 */
	List<Slideshow> queryList(SlideshowQueryObject qo);

	/**
	 * 增加slideshow
	 * @param slideshow
	 * @return
	 */
	Integer insert(Slideshow slideshow);

	/**
	 * 修改slideshow
	 * @param slideshow
	 * @return
	 */
	Integer update(Slideshow slideshow);

	/**
	 * 删除slideshow
	 * @param id
	 * @return
	 */
	Integer delete(Integer id);

	/**
	 * @param ids
	 * @param status
	 */
	Integer uplineOrDownLine(@Param("id")Integer list, @Param("status")Integer status);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteBatch(List<Integer> ids);

	/**
	 * 根据ID查询单个Slideshow
	 * @param id
	 * @return
	 */
	Slideshow queryById(Integer id);

	/**
	 * 根据ID修改图片URL
	 * @param id
	 * @param url
	 */
	Integer updateImageUrl(@Param("id")Integer id,@Param("imageUrl")String imageUrl);

	/**
	 * 查询已上线的总条数
	 * @param status
	 * @return
	 */
	long queryUpLineCount(Integer status);
	/**
	 * 下发数据保存
	 */
	 int insertjson(Slideshow slideshow);

	// 查询排序的最大值和最小值
	Map<String, Object> findmaxVSminsequence(Map<String, Object> map);

	// 查询更改数据的历史排序
	List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams);

	// 查询排序
	List<Map<String, Object>> findsequence(Map<String, Object> map);
}
