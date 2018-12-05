package com.pbtd.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.domain.CpAlbuminfo;
import com.pbtd.manager.query.CpAlbuminfoQueryObject;

public interface CpAlbuminfoMapper {
	/**
	 * 查询总条数
	 * @param qo
	 * @return
	 */
	Long queryCount(CpAlbuminfoQueryObject qo);

	/**
	 * 查询总记录
	 * @param qo
	 * @return
	 */
	List<CpAlbuminfo> queryList(CpAlbuminfoQueryObject qo);

	CpAlbuminfo queryById(Long id);

	CpAlbuminfo queryBySeriesCode(@Param("seriesCode")Long seriesCode, @Param("cpCode")String cpCode);

	CpAlbuminfo queryByCpSeriesCode(@Param("cpSeriesCode")Long cpSeriesCode, @Param("cpCode")String cpCode);

	void insert(CpAlbuminfo ca);

	void update(CpAlbuminfo ca);

	/**
	 * 绑定专辑或解除绑定
	 * @param id
	 * @param seriesCode
	 * @param seriesName
	 */
	void bindingAlbum(@Param("id")Long id, @Param("seriesCode")Long seriesCode, @Param("seriesName")String seriesName,@Param("joinStatus")Integer joinStatus);

	void unpinless(@Param("ids")List<Long> ids,@Param("ca")CpAlbuminfo ca);

	List<CpAlbuminfo> queryByIds(List<Long> ids);

	void updateStatus(@Param("cpCode")String cpCode, @Param("cpSeriesCode")Long cpSeriesCode, @Param("status")Integer status);

	CpAlbuminfo queryBySeriesCodeCpCode(@Param("seriesCode")Long seriesCode,@Param("cpCode")String cpCode);

	void confirm(@Param("list")List<Long> ids,@Param("joinStatus")Integer joinStatus);
}
