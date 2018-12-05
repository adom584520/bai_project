package com.pbtd.manager.vod.phone.slideshow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.vod.phone.slideshow.domain.Slideshow;
import com.pbtd.manager.vod.phone.slideshow.domain.StartSlideshow;
import com.pbtd.manager.vod.phone.slideshow.query.StartSlideshowQueryObject;

public interface StartSlideshowMapper {
	/**
	 * 高级查询加分页-查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(StartSlideshowQueryObject qo);

	/**
	 * 高级查询加分页-查询数据集合
	 * @param qo
	 * @return
	 */
	List<StartSlideshow> queryList(StartSlideshowQueryObject qo);

	/**
	 * 增加startShow
	 * @param startShow
	 * @return
	 */
	Integer insert(StartSlideshow startShow);

	/**
	 * 修改startShow
	 * @param startShow
	 * @return
	 */
	Integer update(StartSlideshow startShow);

	/**
	 * 删除startShow
	 * @param id
	 * @return
	 */
	Integer delete(Integer id);

	/**
	 * 上下线
	 * @param id
	 * @param status
	 */
	Integer uplineOrDownLine(@Param("id")Integer id, @Param("status")Integer status);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	Integer deleteBatch(List<Integer> ids);

	/**
	 * 根据ID查询单个startShow
	 * @param id
	 * @return
	 */
	StartSlideshow queryById(Integer id);

	/**
	 * 根据ID修改图片URL
	 * @param id
	 * @param url
	 */
	Integer updateImageUrl(@Param("id")Integer id,@Param("imageUrl")String imageUrl);

	/**
	 * 查询是否有上线信息
	 * @param status
	 * @return
	 */
	StartSlideshow queryByStatus(Integer status);
	/**
	 * 下发数据保存
	 */
	 int insertjson(StartSlideshow startShow);
}
