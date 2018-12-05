package com.pbtd.playclick.integrate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.integrate.domain.VodAlbuminfo;
@Mapper
public interface VodAlbuminfoMapper {
	  /**
     * 模糊统计符合查询条件的记录总数
     *
     * @param queryParams 查询参数
     * @return 记录总数
     */
    int count(Map<String, Object> queryParams);

    /**
     * 模糊获取符合查询条件的分页记录
     *
     * @param start 记录起始位置
     * @param limit 记录条数
     * @param queryParams 查询参数
     * @return 记录列表
     */
    List<VodAlbuminfo> page(int start, int limit, Map<String, Object> queryParams);

    /**
     * 精确获取符合查询条件的记录
     *
     * @param queryParams 查询参数
     * @return 记录列表
     */
    List<VodAlbuminfo> find(Map<String, Object> queryParams);

    /**
     * 精确生成将要插入的记录的序号
     *
     * @param queryParams 查询参数
     * @return 序号
     */
    int generatePosition(Map<String, Object> queryParams);

    /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    VodAlbuminfo load(Map<String, Object> queryParams);

    /**
     * 插入记录
     *
     * @param VodAlbuminfo VodAlbuminfo实例
     * @return 被插入的记录标识
     */
    int insert(VodAlbuminfo vodAlbuminfo);

    /**
     * 修改记录
     *
     * @param VodAlbuminfo VodAlbuminfo实例
     * @return 被修改的记录数
     */
    int update(VodAlbuminfo vodAlbuminfo);

	/**
     * 删除多条
     * 
     * @param ids 标识列表
     * @return 被删除的记录数
     */
    int deletes(Map<String, Object> queryParams);
    List<Map<String,Object>> findAlbumsinfovideo(Map<String, Object> queryParams) ;
    int countAlbumsinfovideo(Map<String, Object> queryParams);
    //修改下发状态
    int updateissue(Map<String, Object> queryParams);
    int insertvideo(Map<String, Object> queryParams);
    int deletesvideo(Map<String, Object> queryParams);

	int loadvodalbum(Map<String, Object> map);
}
