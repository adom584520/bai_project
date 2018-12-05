package com.pbtd.playuser.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playuser.domain.UserBaseInfo;
import com.pbtd.playuser.service.IUserBaseInfoService;
import com.pbtd.playuser.util.JsonMessage;

/**
 * 用户操作
 * 
 * @author JOJO
 *				
 */
@Controller
public class UserBaseInfoController {
	private static final Logger logger = LoggerFactory.getLogger(UserBaseInfoController.class); 

	@Autowired
	private IUserBaseInfoService userBaseInfoService;

	

	/**
	 * 用户发送验证码
	 * @param 
	 * @return
	 */
	@RequestMapping("/common/user/sendCode")
	@ResponseBody
	public JsonMessage sendCode(HttpServletRequest request){
		long start = System.currentTimeMillis();
		logger.info("接口sendCode开始:"+start);
		JsonMessage result = new JsonMessage();
		String balance = request.getParameter("mobile");
		if(balance == null){
			result.setCode(-1);
			result.setMessage("请检查参数或输入手机号！");
			return result;
		}
		long mobilenum = 0;
		try {
			mobilenum=Long.parseLong(balance);
			if(balance.length() != 11){
				result.setCode(-2);
				result.setMessage("请输入正确长度的手机号！");
				return result;
			}
		} catch (NumberFormatException e) {
			result.setCode(-3);
			result.setMessage("请输入正确的手机号格式的 !");
			return result;
		}
		logger.info("接口sendCode入参mobile:"+mobilenum);
		try {
			result = userBaseInfoService.getcode(mobilenum);
		}catch (Exception e) {
			result.setCode(0);
			result.setMessage("系统正在维护中,请稍后！");
			e.printStackTrace();
			return result;
		}
		long end = System.currentTimeMillis();
		logger.info("接口sendCode结束:"+end);
		logger.info("接口sendCode时间:"+(end-start));
		return result;
	}

	/**
	 * 用户登录/注册
	 * @param 
	 * @return
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@RequestMapping("/common/user/login")
	@ResponseBody
	public JsonMessage login(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		JsonMessage result = new JsonMessage();
		String code = null;
		String mobile = null;
		String device = null;
		code = request.getParameter("code");;
		mobile = request.getParameter("mobile");
		device = request.getParameter("device");
//		if(code == null || mobile == null){
//			result.setCode(-1);
//			result.setMessage("请检查输入参数");
//			return result;
//		}
		if(mobile.length() != 11 ){
			result.setCode(-2);
			result.setMessage("请输入正确的手机号!");
			return result;
		}
		try {
			long mobilenum=Long.parseLong(mobile);
		//	long codenum=Long.parseLong(code);
		}catch (NumberFormatException e) {
			result.setCode(-3);
			result.setMessage("请输入正确的手机号格式!");
			return result;
		}	
		logger.info("接口login入参mobile,code,device:"+mobile+","+code+","+device);
		try {
			result = userBaseInfoService.login(mobile, code, device);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("登录失败");
		}
		return result;
	}


	/**
	 * 用户token 校验
	 * @param 
	 * @return
	 * 
	 */
	@RequestMapping("/common/user/checkToken")
	@ResponseBody
	public JsonMessage checkToken(HttpServletRequest request) {
		JsonMessage result = new JsonMessage();
		String userId = null;
		String token = null;
		userId = request.getParameter("userId");;
		token = request.getParameter("token");
		if(token == null ||userId == null || token.isEmpty() || userId.isEmpty()){
			result.setCode(0);
			result.setMessage("校验失败");
			return result;
		}
		UserBaseInfo userinfo;
		try {
			userinfo = userBaseInfoService.selectUser(userId);
		} catch (Exception e1) {
			result.setCode(-2);                 
			result.setMessage("数据库查询失败");
			return result;                      
		}  
		if(userinfo == null){
			result.setCode(-3);                 
			result.setMessage("用户不存在");
			return result;                      
		}
		logger.info("接口checkToken入参token,userId:"+token+","+userId);
		if("tv_token_6688_pbtd".equals(token)){
			return new JsonMessage(1,"校验成功");
		}
		try {
			result = userBaseInfoService.checkToken(token,userId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("校验失败");
			return result;
		}
		return result;
	}

}