package com.yh.push.service;

import java.util.List;

import com.yh.push.bean.PushUser;

public interface AppService {
	public void getAddTv(PushUser push);
	public void getAddPhone(PushUser push);
	public int getDelPhone(String src_userID);
	public int getDelTv(String des_userID);
	public int getTokenPhone(String src_userID,String src_token,String src_type);
	public int getTokenTv(String src_userID,String src_token);
	public PushUser getselect(String src_token,String des_token);
	public PushUser getselectDel(String des_token);
	public List<PushUser> getselectPhone(String src_userID);
	public List<PushUser> getselectTv(String src_userID);
}
