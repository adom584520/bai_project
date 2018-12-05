package com.pbtd.manager.service;

import java.util.List;

import com.pbtd.manager.domain.CpAlbuminfo;
import com.pbtd.manager.query.CpAlbuminfoQueryObject;
import com.pbtd.manager.util.PageResult;

public interface CpAlbuminfoService {

	PageResult queryList(CpAlbuminfoQueryObject qo);

	CpAlbuminfo queryById(Long id);

	CpAlbuminfo queryBySeriesCode(Long seriesCode,String cpCode);

	/**
	 * 根据ID绑定专辑
	 * @param id
	 * @param seriesCode
	 * @param seriesName
	 */
	void bindingAlbum(Long id,Long seriesCode,String seriesName,String cpCode);

	void unpinless(List<Long> ids);

	void updateStatus(String cpCode,Long cpSeriesCode,Integer status);

	void cpUploadAlbum(List<CpAlbuminfo> list,String cpCode);

	void confirm(List<Long> ids);

	CpAlbuminfo queryByCpSeriesCode(Long cpSeriesCode,String cpCode);
}
