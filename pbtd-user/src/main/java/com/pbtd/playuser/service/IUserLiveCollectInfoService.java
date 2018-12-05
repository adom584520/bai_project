package com.pbtd.playuser.service;

import java.util.HashMap;

import com.pbtd.playuser.util.JsonMessage;

public interface IUserLiveCollectInfoService {
	
	public JsonMessage livecollect(HashMap<String,Object> params);
	public JsonMessage livecollectdelete(HashMap<String,Object> params);
	public JsonMessage isnotlivecollect(HashMap<String,Object> params);
	public JsonMessage liveCollectList(HashMap<String,Object> params);
	public JsonMessage liveCollectListEpg(HashMap<String,Object> params);

}
