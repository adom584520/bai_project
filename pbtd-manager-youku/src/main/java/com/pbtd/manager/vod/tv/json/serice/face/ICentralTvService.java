package com.pbtd.manager.vod.tv.json.serice.face;

import com.pbtd.manager.vod.tv.json.domain.VodjsonTvAlbuminfo;

public interface ICentralTvService {
	public void getInsertUpdate(VodjsonTvAlbuminfo vodTvAlbuminfo)throws Exception;
	public void Addtvchannel(Integer id,String channelName,Integer channelCode)throws Exception;
	public void Addtvlabel(Integer id,String name,Integer channelCode,Integer level)throws Exception;
	public int UpdateSeriesCode();
}
