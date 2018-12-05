package com.pbtd.manager.service;

import com.alibaba.fastjson.JSONObject;

public interface UniformInterfaceService {
	String validataAlbum(JSONObject jsonObject);

	String validataChannel(JSONObject jsonObject);

	String initVodPlayHistorys(JSONObject jsonObject);

	String initLiveFavorites(JSONObject jsonObject);

	String initVodFavorites(JSONObject jsonObject);

	String uploadVodPlayHistorys(JSONObject jsonObject);

	String getVodPlayHistorys(JSONObject jsonObject);

	String uploadLiveFavorites(JSONObject jsonObject);

	String uploadVodFavorites(JSONObject jsonObject);

	String getLiveFavorites(JSONObject jsonObject);

	String getVodFavorites(JSONObject jsonObject);

	String deleteVodFavorites(JSONObject jsonObject);

	String deleteVodPlayHistorys(JSONObject jsonObject);

	String deleteLiveFavorites(JSONObject jsonObject);
}
