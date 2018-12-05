package com.pbtd.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.domain.LoginInfo;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.mapper.LoginInfoMapper;
import com.pbtd.manager.service.LoginInfoService;
import com.pbtd.manager.util.LoginInfoContext;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {
	@Autowired
	private LoginInfoMapper loginInfoMapper;
	@Autowired
	private LoginInfoContext loginInfoContext;


	public LoginInfo login(String username, String password) {
		if (username != null && "".equals(username)) {
			throw new JsonMessageException("用户名不能为空！");
		}
		if (password != null && "".equals(password)) {
			throw new JsonMessageException("密码不能为空！");
		}
		LoginInfo loginInfo = loginInfoMapper.login(username, password);
		if (loginInfo == null) {
			throw new JsonMessageException("用户名或密码错误！");
		}
		LoginInfoContext.setCurrent(loginInfo);
		loginInfoContext.setsession();
		return loginInfo;
	}
}
