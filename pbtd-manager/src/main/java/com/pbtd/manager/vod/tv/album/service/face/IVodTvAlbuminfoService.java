package com.pbtd.manager.vod.tv.album.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.vod.tv.album.domain.VodTvAlbuminfo;
import com.pbtd.manager.vod.tv.album.domain.VodTvAlbuminfoReturn;

public interface IVodTvAlbuminfoService {

	/**
     * 模糊统计符合查询条件的记录总数
     */
    int count(Map<String, Object> queryParams);

	/**
     * 模糊获取符合查询条件的分页记录
     * @return 记录列表
     */
    List<VodTvAlbuminfo> page(Map<String, Object> queryParams);

	/**
     * 精确获取符合查询条件的记录
     */
    List<VodTvAlbuminfo> find(Map<String, Object> queryParams);

	/**
     * 根据标识获取记录
     */
    VodTvAlbuminfo load(int id);

	/**
     * 插入记录
     */
    int insert(VodTvAlbuminfo vodTvAlbuminfo);

	/**
     * 修改记录
     */
    int update(VodTvAlbuminfo vodTvAlbuminfo);

	/**
     * 批量上线下线
     */
    int updatestatus(Map<String,Object> queryParams);

	/**
     * 删除多条
     */
    int deletes(Map<String, Object> ids);

	int updatesequce(Map<String, Object> queryParams);

	int addsequce(Map<String, Object> queryParams);

	List<Map<String,Object>> loadVideo(int id);

	public List<Map<String, Object>> findAlbumsinfovideo(Map<String, Object> queryParams);

	public List<Map<String,Object>> pageAlbumsinfovideo(Map<String, Object> queryParams);

	int countAlbumsinfovideo(Map<String, Object> queryParams);

	int insertvideo(Map<String, Object> queryParams);

	int deletesvideo(Map<String, Object> queryParams);

	/**
     * 编辑剧集
     * 
     */
    int updatevideo(Map<String,Object> queryParams);

	/**
     * 节目关联推荐剧集
     * 
     */
    List<Map<String,Object>> pagealbum(Map<String,Object> queryParams);

	List<Map<String,Object>> recommandalbum(Map<String,Object> queryParams);

	int updatealbumsequence(Map<String,Object>queryParams);

	int countalbum(Map<String,Object> queryParams);

	int addalbum(Map<String,Object> queryParams);

	int deletealbum(Map<String,Object> queryParams);

	/**
     * 专辑绑定角标
     */
    int updatecorner(String albumid,String cornerid);

	/**
     * 专辑绑定付费包
     */
    int updatecollectfeesbag(String albumid,String collectid);

	/**
	 * 通过频道ID获取专辑
	 */
    List<Map<String,Object>> findIdByChannel(Map<String,Object> queryParams);

	/**
     * 查询专辑code和专辑名称
     * @param seriesCode
     * @return
     */
    VodTvAlbuminfoReturn querySeriesCodeAndName(String seriesCode);
    //查询绑定信息排序
    List<Map<String, Object>> findalbumsequence(Map<String, Object> map);
  //查询绑定信息的最大和最小排序
    Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map);
    //查询更改数据的历史排序
    List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams);
}
