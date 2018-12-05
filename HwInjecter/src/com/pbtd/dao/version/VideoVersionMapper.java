package com.pbtd.dao.version;

import com.pbtd.entity.VideoVersion;

public interface VideoVersionMapper {

	public VideoVersion findNameByCode(VideoVersion version);
	
}
