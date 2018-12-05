package com.pbtd.manager.service;

import java.util.List;

import com.pbtd.manager.domain.CpDrama;

public interface CpDramaService {
	List<CpDrama> queryByAlbumId(Long cpSeriesCode,String cpCode);

	List<Long> queryIdsByAlbumId(List<Long> albumIds);

	void bindingDrama(Long cpDramaId,Long dramaId);

	/**
	 * 根据ID批量解除绑定剧集
	 * @param ids
	 */
	void unpinlessBatch(List<Long> ids);
	
	void cpUploadDrama(List<CpDrama> list,String cpCode);
	
	void confirm(List<Long> list);
}
