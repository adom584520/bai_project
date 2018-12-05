package com.pbtd.manager.service;

import java.util.List;

import com.pbtd.manager.domain.PushUser;

public interface AppService {
	public void getAddTv(PushUser push);// 手机和TV绑定参数
	//public void getAddPhone(PushUser push);
	/*public int getDelPhone(String pUserId);
	public int getDelTv(String tvUserId);*/
	public int getTokenPhone(String pUserId,String pToken,String pSystem);
	public int getTokenTv(String tvUserId,String tvToken);
	public PushUser getselect(String pUserId,String tvUserId);//查询绑定参数
	public PushUser getSelectDel(String pUserId,String tvUserId);//解除绑定关系
	public PushUser getDeleteBy(String tvUserId);// 解除TV所有绑定
	public List<PushUser> getselectPhone(String pUserId);
	public List<PushUser> getselectTv(String tvUserId);
}
