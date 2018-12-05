package com.pbtd.manager.vod.phone.slideshow.service;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.vod.phone.slideshow.domain.Slideshow;
import com.pbtd.manager.vod.phone.slideshow.query.SlideshowQueryObject;

public interface SlideshowService {

	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(SlideshowQueryObject qo);

	/**
	 * 添加slideshow
	 * @param slideshow
	 */
	void insert(Slideshow slideshow);

	/**
	 * 删除slideshow
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 修改slideshow
	 * @param id
	 */
	void update(Slideshow slideshow);

	/**
	 * 上下线操作
	 * @param ids
	 * @param status
	 */
	void uplineOrDownLine(Integer ids, Integer status);

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
	void updateImageUrl(Integer id,String url);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int deleteBatch(List<Integer> ids);
	/**
	 * 下发数据保存
	 */
	 int insertjson(Slideshow slideshow);
	//查询排序的最大值和最小值
     Map<String, Object> findmaxVSminsequence(Map<String, Object> map);
	  //查询更改数据的历史排序
	  List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams);
	 //查询排序
	  List<Map<String, Object>> findsequence(Map<String, Object> map);
}
