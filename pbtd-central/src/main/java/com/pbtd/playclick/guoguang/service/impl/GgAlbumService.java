package com.pbtd.playclick.guoguang.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.guoguang.domain.GgAlbums;
import com.pbtd.playclick.guoguang.domain.GgAlbumsinfo;
import com.pbtd.playclick.guoguang.domain.GgAlbumsinfovideo;
import com.pbtd.playclick.guoguang.domain.GgChannel;
import com.pbtd.playclick.guoguang.domain.GgLabel;
import com.pbtd.playclick.guoguang.mapper.GgAlbumMapper;
import com.pbtd.playclick.guoguang.service.IGgAlbumService;

@Service
public class GgAlbumService implements IGgAlbumService {

	
    @Autowired
    private GgAlbumMapper ggalbumMapper;
	@Override
	public int insertAlbums(GgAlbums album) {
		return ggalbumMapper.insertAlbums(album);
	}
	@Override
	public int countAlbums(GgAlbums queryParams) {
		return ggalbumMapper.countAlbums(queryParams);
	}
	@Override
	public int updateAlbums(GgAlbums album) {
		return ggalbumMapper.updateAlbums(album);
	}
	@Override
	public int insertAlbuminfo(GgAlbumsinfo album) {
		return ggalbumMapper.insertAlbuminfo(album);
	}
	@Override
	public int countAlbuminfo(GgAlbumsinfo queryParams) {
		return ggalbumMapper.countAlbuminfo(queryParams);
	}
	@Override
	public int updateAlbuminfo(GgAlbumsinfo album) {
		return ggalbumMapper.updateAlbuminfo(album);
	}
	@Override
	public int insertAlbuminfovideo(GgAlbumsinfovideo album) {
		return ggalbumMapper.insertAlbuminfovideo(album);
	}
	@Override
	public int countAlbuminfovideo(GgAlbumsinfovideo queryParams) {
		return ggalbumMapper.countAlbuminfovideo(queryParams);
	}
	@Override
	public int updateAlbuminfovideo(GgAlbumsinfovideo album) {
		return ggalbumMapper.updateAlbuminfovideo(album);
	}
	@Override
	public Map<String, Object> getupdate() {
		return ggalbumMapper.getupdate();
	}
	@Override
	public int insertGgChannel(GgChannel channel) {
		return ggalbumMapper.insertGgChannel(channel);
	}
	@Override
	public Map<String,Object> getGgChannel(GgChannel channel) {
		return ggalbumMapper.getGgChannel(channel);
	}
	@Override
	public int updatetime(String time) {
		return ggalbumMapper.updatetime(time);
	}
	@Override
	public int insertLabel(GgLabel label) {
		return ggalbumMapper.insertLabel(label);
	}
	@Override
	public  Map<String,Object>  getLabel(GgLabel label) {
		return ggalbumMapper.getLabel(label);
	}
	@Override
	public void updateDramacode() {
		// 更新dramacode
		ggalbumMapper.updateDramacode();
		
	}
	@Override
	public void updateSequence() {
		//国广剧集排序增量sequence
		ggalbumMapper.updateSequence();
	}

}
