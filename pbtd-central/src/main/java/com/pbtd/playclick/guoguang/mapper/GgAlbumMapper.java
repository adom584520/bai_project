package com.pbtd.playclick.guoguang.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.guoguang.domain.GgAlbums;
import com.pbtd.playclick.guoguang.domain.GgAlbumsinfo;
import com.pbtd.playclick.guoguang.domain.GgAlbumsinfovideo;
import com.pbtd.playclick.guoguang.domain.GgChannel;
import com.pbtd.playclick.guoguang.domain.GgLabel;

@Mapper
public interface GgAlbumMapper {
	// 产品 增改查
	int insertAlbums(GgAlbums album);

	int countAlbums(GgAlbums queryParams);

	int updateAlbums(GgAlbums album);

	// 专辑 增改查
	int insertAlbuminfo(GgAlbumsinfo album);

	int countAlbuminfo(GgAlbumsinfo queryParams);

	int updateAlbuminfo(GgAlbumsinfo album);

	// 专辑明细 增改查
	int insertAlbuminfovideo(GgAlbumsinfovideo album);

	int countAlbuminfovideo(GgAlbumsinfovideo queryParams);

	int updateAlbuminfovideo(GgAlbumsinfovideo album);

	// 查询更新时间
	Map<String,Object> getupdate();

	int updatetime(String time);

	// 频道
	int insertGgChannel(GgChannel channel);

	Map<String,Object> getGgChannel(GgChannel channel);

	// 标签
	int insertLabel(GgLabel label);

	Map<String,Object>  getLabel(GgLabel label);

	// 更新Dramacode
	void updateDramacode();

	// 国广剧集排序增量sequence
	void updateSequence();
}
