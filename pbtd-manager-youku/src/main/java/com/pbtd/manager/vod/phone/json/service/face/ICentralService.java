package com.pbtd.manager.vod.phone.json.service.face;

import com.pbtd.manager.vod.phone.json.domain.VodjsonPhoneAlbuminfo;

public interface ICentralService {
	public void getInsertUpdate(VodjsonPhoneAlbuminfo vodPhoneAlbuminfo)throws Exception;
	public void Addphonechannel(Integer id,String channelName,Integer channelCode)throws Exception;
	public void Addphonelabel(Integer id,String name,Integer channelCode,Integer level)throws Exception;
	public int UpdateSeriesCode();
}
