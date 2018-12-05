package com.pbtd.manager.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.domain.PushUser;
import com.pbtd.manager.mapper.PushUserMapper;
import com.pbtd.manager.service.AppService;

@Service
public class AppServiceImpl implements AppService {
	public static Logger log = Logger.getLogger(AppServiceImpl.class);

	@Autowired
	private PushUserMapper pushUserMapper;

	/**
	 * 手机和TV绑定参数
	 */
	@Override
	public void getAddTv(PushUser push) {
		
		List<PushUser> pushUser = pushUserMapper.select(push.getPUserId(), push.getTvUserId());
		if (pushUser.size() == 0) {
			try {
				pushUserMapper.insert(push);
			} catch (Exception e) {
				log.error("重复添加绑定参数:" + e);
			}
		} else {
			pushUserMapper.updateByPrimaryKey(push);
		}

	}

	/*
	 * @Override public void getAddPhone(PushUser push) {
	 * 
	 * pushUserMapper.updatePrimary(push);
	 * 
	 * }
	 */

	/**
	 * 查询绑定参数
	 */
	@Override
	public PushUser getselect(String pUserId, String tvUserId) {
		List<PushUser> Primary = pushUserMapper.selectByPrimaryKey(pUserId, tvUserId);
		if (Primary.size() > 0) {
			PushUser pushUser = Primary.get(0);
			return pushUser;
		}
		return null;
	}

/*	@Override
	public int getDelPhone(String pUserId) {
		int key = pushUserMapper.deletePhone(pUserId);
		return key;
	}

	@Override
	public int getDelTv(String tvUserId) {
		int key = pushUserMapper.deleteTv(tvUserId);
		return key;
	}*/

	@Override
	public int getTokenPhone(String pUserId, String pToken, String pSystem) {
		int key = pushUserMapper.UpdatePhoneToken(pUserId, pToken, pSystem);
		return key;
	}

	@Override
	public int getTokenTv(String tvUserId, String tvToken) {
		int key = pushUserMapper.UpdateTvToken(tvUserId, tvToken);
		return key;
	}

	@Override
	public List<PushUser> getselectPhone(String pUserId) {
		List<PushUser> Primary = pushUserMapper.selectPhoneToken(pUserId);
		return Primary;
	}

	@Override
	public List<PushUser> getselectTv(String tvUserId) {
		List<PushUser> Primary = pushUserMapper.selectTvToken(tvUserId);
		return Primary;
	}

	/**
	 * 解除TV所有绑定
	 */
	@Override
	public PushUser getDeleteBy(String tvUserId) {
		List<PushUser> PushUserDel = pushUserMapper.selectDel(tvUserId);
		if (PushUserDel.size() > 0) {
			PushUser pushUserDel = PushUserDel.get(0);
			pushUserMapper.deleteToken(tvUserId);
			return pushUserDel;
		}
		return null;
	}

	/**
	 * 解除绑定关系
	 */
	@Override
	public PushUser getSelectDel(String pUserId, String tvUserId) {
		List<PushUser> pushUser = pushUserMapper.select(pUserId, tvUserId);
		if (pushUser.size() > 0) {
			pushUserMapper.deleteByPrimaryKey(pUserId, tvUserId);
			return pushUser.get(0);
		}
		return null;
	}

}
