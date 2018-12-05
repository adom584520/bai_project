package com.pbtd.manager.vod.phone.slideshow.service;

import java.util.List;

import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.vod.phone.slideshow.domain.StartSlideshow;
import com.pbtd.manager.vod.phone.slideshow.query.StartSlideshowQueryObject;

public interface StartSlideshowService {

	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(StartSlideshowQueryObject qo);

	/**
	 * 添加StartSlideshow
	 * @param startShow
	 */
	void insert(StartSlideshow startShow);

	/**
	 * 删除startShow
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 修改startShow
	 * @param id
	 */
	void update(StartSlideshow startShow);

	/**
	 * 上下线操作
	 * @param id
	 * @param status
	 */
	void uplineOrDownLine(Integer id, Integer status);

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
	void updateImageUrl(Integer id,String url);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int deleteBatch(List<Integer> ids);
	/**
	 * 高级查询加分页-查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(StartSlideshowQueryObject qo);
	//下发获取信息接口
		List<StartSlideshow> queryListforinterface(StartSlideshowQueryObject qo);

}
