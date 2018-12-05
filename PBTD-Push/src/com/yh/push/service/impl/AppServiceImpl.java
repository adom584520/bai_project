package com.yh.push.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yh.push.bean.PushUser;
import com.yh.push.mapper.PushUserMapper;
import com.yh.push.service.AppService;

@Service
public class AppServiceImpl implements AppService {

	@Autowired
	private PushUserMapper pushUserMapper;

	@Override
	public void getAddTv(PushUser push) {
		pushUserMapper.insert(push);

	}

	@Override
	public void getAddPhone(PushUser push) {

		pushUserMapper.updatePrimary(push);

	}

	@Override
	public PushUser getselect(String src_token, String des_token) {
		List<PushUser> Primary = pushUserMapper.selectByPrimaryKey(src_token, des_token);
		if (Primary.size() > 0) {
			PushUser pushUser = Primary.get(0);
			return pushUser;
		}
		return null;
	}

	@Override
	public int getDelPhone(String src_userID) {
		int key = pushUserMapper.deletePhone(src_userID);
		return key;
	}

	@Override
	public int getDelTv(String des_userID) {
		int key = pushUserMapper.deleteTv(des_userID);
		return key;
	}

	@Override
	public int getTokenPhone(String src_userID, String src_token, String src_type) {
		int key = pushUserMapper.UpdatePhoneToken(src_userID, src_token, src_type);
		return key;
	}

	@Override
	public int getTokenTv(String src_userID, String src_token) {
		int key = pushUserMapper.UpdateTvToken(src_userID, src_token);
		return key;
	}

	@Override
	public List<PushUser> getselectPhone(String src_userID) {
		List<PushUser> Primary = pushUserMapper.selectPhoneToken(src_userID);
		return Primary;
	}

	@Override
	public List<PushUser> getselectTv(String src_userID) {
		List<PushUser> Primary = pushUserMapper.selectTvToken(src_userID);
		return Primary;
	}

	@Override
	public PushUser getselectDel(String des_token) {
		List<PushUser> PushUser = pushUserMapper.selectDel(des_token);
		if (PushUser.size() > 0) {
			PushUser pushUser = PushUser.get(0);
			pushUserMapper.deleteToken(des_token);
			return pushUser;
		}
		return null;
	}

}
