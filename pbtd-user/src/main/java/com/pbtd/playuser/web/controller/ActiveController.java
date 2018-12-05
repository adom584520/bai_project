package com.pbtd.playuser.web.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pbtd.playuser.service.IActivitiesInfoService;
import com.pbtd.playuser.util.AccountValidatorUtil;
import com.pbtd.playuser.util.JsonMessage;

@RestController
@RequestMapping("/phone/user")
public class ActiveController {
	private static final Logger logger = LoggerFactory.getLogger(ActiveController.class);
	@Autowired
	private IActivitiesInfoService iActivitiesInfoService;

	/***
	 * 1，获取所有活动接口
	 */
	@RequestMapping("/queryAllActivitise")
	@ResponseBody
	public JsonMessage queryAllActivity(HttpServletRequest request) throws IOException {
		String userId = request.getParameter("userId");
		if (userId == null) {
			logger.info("返回值：-1,返回信息：请输入用户ID！");
			return new JsonMessage(-1, "请输入用户ID！");
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		logger.info("入参userId:" + userId);
		params.put("userid", userId);
		try {
			return iActivitiesInfoService.queryActivity(params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("返回值：0,返回信息：系统正在维护中,请稍后！");
			return new JsonMessage(0, "系统正在维护中,请稍后！");
		}
	}

	/***
	 * 2，获取指定活动详细描述
	 */
	@RequestMapping("/queryActivity")
	@ResponseBody
	public JsonMessage queryActivity(HttpServletRequest request) throws IOException {
		String activecode = request.getParameter("activeCode");
		String userId = request.getParameter("userId");
		if (activecode == null) {
			logger.info("返回值：-2,返回信息：请输入活动名称！");
			return new JsonMessage(-2, "请输入活动名称！");
		}
		if (userId == null) {
			logger.info("返回值：-3,返回信息：请输入用户ID！");
			return new JsonMessage(-3, "请输入用户ID！");
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		logger.info("入参userId:" + userId + " |activeCode:" + activecode);
		params.put("activeCode", activecode);
		params.put("userId", userId);
		try {
			switch (activecode) {
			case "ZPNUMONE":
				return iActivitiesInfoService.queryActivityForZP(params);
			case "QIANDAOHD":
				return iActivitiesInfoService.signInActivities(params);
			case "SHENGJIHD":
				return iActivitiesInfoService.upgradeActivities(params);
			case "FIRSTLOGIN":
				return iActivitiesInfoService.queryActivityForFL(params);
			default:
				return iActivitiesInfoService.queryActivityForOther(params);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("返回值：0,返回信息：系统正在维护中,请稍后！");
			return new JsonMessage(0, "系统正在维护中,请稍后！");
		}
	}

	/***
	 * 3，生成奖品或参与活动
	 * 
	 */
	@RequestMapping("/joinForActivity")
	@ResponseBody
	public JsonMessage getprizeforactivity(HttpServletRequest request) throws IOException {
		String userId = request.getParameter("userId");
		if (userId == null) {
			logger.info("返回值：-1,返回信息：请输入用户ID！");
			return new JsonMessage(-1, "请输入用户ID！");
		}
		String activecode = request.getParameter("activeCode");
		if (activecode == null) {
			logger.info("返回值：-2,返回信息：请输入活动名称！");
			return new JsonMessage(-2, "请输入活动名称！");
		}
		String mobiel = request.getParameter("userMobile");
		HashMap<String, Object> params = new HashMap<String, Object>();
		logger.info("入参userId:" + userId + " |activeCode:" + activecode +"| mobiel:" +mobiel);
		params.put("userId", userId);
		params.put("userid", userId);
		params.put("activeCode", activecode);
		params.put("userMobile", mobiel);
		try {
			switch (activecode) {
			case "ZPNUMONE":
				return iActivitiesInfoService.isnotJoinActivities(params);
			case "QIANDAOHD":
				return iActivitiesInfoService.signInActivitiesJoin(params);
			case "SHENGJIHD":
				return iActivitiesInfoService.upgradeActivities(params);
			case "FIRSTLOGIN":
				if(mobiel == null){
					return new JsonMessage(-1,"请输入推荐人手机号");
				}else{
					if(!AccountValidatorUtil.isMobile(mobiel)){
						return new JsonMessage(-3, "请输入正确长度的手机号！");
					}
				}
				return iActivitiesInfoService.ActivityForFLJoin(params);
			default:
				return iActivitiesInfoService.isnotJoinActivities(params);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("返回值：0,返回信息：系统正在维护中,请稍后！");
			return new JsonMessage(0, "系统正在维护中,请稍后！");
		}
	}

	/***
	 * 4，输入收货地址
	 */
	@RequestMapping("/saveAddress")
	@ResponseBody
	public JsonMessage saveAddress(HttpServletRequest request) throws IOException {
		JsonMessage result = new JsonMessage();
		String userId = request.getParameter("userId");
		if (userId == null) {
			result.setCode(-1);
			result.setMessage("请输入用户ID！");
			return result;
		}
		String activeName = request.getParameter("activeName");
		if (activeName == null) {
			result.setCode(-2);
			result.setMessage("请输入参与活动ID!");
			return result;
		}
		String userAddress = request.getParameter("userAddress");
		if (userAddress == null || userAddress.trim().length() == 0) {
			result.setCode(-3);
			result.setMessage("请输入收货地址");
			return result;
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		logger.info("入参userId:" + userId + " |activeName:" + activeName + " |userAddress:" + userAddress);
		params.put("userid", userId);
		params.put("activeName", activeName);
		params.put("userAddress", userAddress);
		try {
			result = iActivitiesInfoService.saveAddress(params);
		} catch (Exception e) {
			result.setCode(0);
			result.setMessage("系统正在维护中,请稍后！");
			e.printStackTrace();
			return result;
		}
		return result;
	}

}
