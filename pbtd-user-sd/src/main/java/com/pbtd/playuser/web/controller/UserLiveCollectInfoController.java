package com.pbtd.playuser.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pbtd.playuser.domain.UserLiveCollectInfo;
import com.pbtd.playuser.service.IUserLiveCollectInfoService;
import com.pbtd.playuser.util.JsonMessage;

/**
 * 用户操作
 * 
 * @author JOJO
 *				
 */
@Controller
public class UserLiveCollectInfoController {
	private static final Logger logger = LoggerFactory.getLogger(UserLiveCollectInfoController.class); 

	@Autowired
	private IUserLiveCollectInfoService userLiveCollectInfoService;

	/**
	 * 1 直播节目收藏接口
	 * @param 
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/phone/user/liveCollect")
	@ResponseBody
	public JsonMessage livecollect(HttpServletRequest request) throws IOException{
		// 读取请求内容
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer("");
		String temp;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		// 将资料解码
		String reqBody = sb.toString();
		reqBody = URLDecoder.decode(reqBody, "utf-8");
		reqBody = URLDecoder.decode(reqBody, "utf-8");
		System.out.println("123:"+reqBody.toString());
		UserLiveCollectInfo collectinfo = JSON.parseObject(reqBody, UserLiveCollectInfo.class);
		JsonMessage result = new JsonMessage();
		String userId = collectinfo.getUserid();
		if(userId == null){
			result.setCode(-1);
			result.setMessage("请登录！");
			return result;
		}
		String chnCode = collectinfo.getChncode();
		if(chnCode == null){
			result.setCode(-1);
			result.setMessage("请输入收藏频道code");
			return result;
		}
		String chnName =collectinfo.getChnname();
		if(chnName == null){
			result.setCode(-1);
			result.setMessage("请输入收藏频道名称");
			return result;
		}
		String packageCover = collectinfo.getPackagecover();
		if(packageCover == null){
			result.setCode(-1);
			result.setMessage("请输入频道图标");
			return result;
		}
		String playUrl = collectinfo.getPlayurl();
		String videoId = collectinfo.getVideoid().toString();
		String groupId = collectinfo.getGroupid().toString();
		HashMap<String,Object> params = new HashMap<String, Object>();
		logger.info("入参userId:"+userId);
		logger.info("入参chnCode:"+chnCode);
		logger.info("入参chnName:"+chnName);
		logger.info("入参packageCover:"+packageCover);
		logger.info("入参playUrl:"+playUrl);
		logger.info("入参videoId:"+videoId);
		logger.info("入参groupId:"+groupId);
		params.put("userId", userId);
		params.put("chnCode", chnCode);
		params.put("chnName", chnName);
		params.put("packageCover",packageCover);
		params.put("playUrl",playUrl);
		params.put("videoId",videoId);
		params.put("groupId",groupId);
		try {
			result = userLiveCollectInfoService.livecollect(params);
		}catch (Exception e) {
			result.setCode(0);
			result.setMessage("系统正在维护中,请稍后！");
			e.printStackTrace();
			return result;
		}
		return result;
	}

	/**
	 * 2 直播节目删除 一个或多个 接口
	 * @param 
	 * @return
	 */
	@RequestMapping("/phone/user/liveCollectDelete")
	@ResponseBody
	public JsonMessage livecollectdelete(HttpServletRequest request){
		JsonMessage result = new JsonMessage();
		String userId = request.getParameter("userId");
		if(userId == null){
			result.setCode(-1);
			result.setMessage("请输入用户ID！");
			return result;
		}
		String chnCode = request.getParameter("chnCode");
		HashMap<String,Object> params = new HashMap<String, Object>();
		logger.info("入参userId:"+userId);
		logger.info("入参chnCode:"+chnCode);
		params.put("userId", userId);
		try {
			if(chnCode == null){
				result = userLiveCollectInfoService.livecollectdelete(params);
			}else{
				String arr[] = chnCode.split(",");
				for (String string : arr) {
					params.put("chnCode", string);
					result = userLiveCollectInfoService.livecollectdelete(params);
				}
				result.setMessage("删除"+arr.length+"条收藏记录成功");
			}
		}catch (Exception e) {
			result.setCode(0);
			result.setMessage("系统正在维护中,请稍后！");
			e.printStackTrace();
			return result;
		}
		return result;
	}

	/**
	 * 3 直播频道节目是否收藏接口
	 * @param 
	 * @return
	 */
	@RequestMapping("/phone/user/liveCollectCheck")
	@ResponseBody
	public JsonMessage livecollectcheck(HttpServletRequest request){
		JsonMessage result = new JsonMessage();
		String userId = request.getParameter("userId");
		if(userId == null){
			result.setCode(0);
			result.setMessage("该节目未收藏");
			return result;
		}
		String chnCode = request.getParameter("chnCode");
		if(chnCode == null){
			result.setCode(-1);
			result.setMessage("请输入收藏频道code");
			return result;
		}
		HashMap<String,Object> params = new HashMap<String, Object>();
		logger.info("入参userId:"+userId);
		logger.info("入参chnCode:"+chnCode);
		params.put("userId", userId);
		params.put("chnCode", chnCode);
		try {
			result = userLiveCollectInfoService.isnotlivecollect(params);
		}catch (Exception e) {
			result.setCode(0);
			result.setMessage("系统正在维护中,请稍后！");
			e.printStackTrace();
			return result;
		}
		return result;
	}
	/**
	 * 4  直播频道收藏列表接口
	 * @param 
	 * @return
	 */
	@RequestMapping("/phone/user/liveCollectList")
	@ResponseBody
	public JsonMessage liveCollectList(HttpServletRequest request){
		JsonMessage result = new JsonMessage();
		String userId = request.getParameter("userId");
		if(userId == null){
			result.setCode(0);
			result.setMessage("请输入用户ID");
			return result;
		}
		HashMap<String,Object> params = new HashMap<String, Object>();
		logger.info("入参userId:"+userId);
		params.put("userId", userId);
		try {
			result = userLiveCollectInfoService.liveCollectList(params);
		}catch (Exception e) {
			result.setCode(0);
			result.setMessage("系统正在维护中,请稍后！");
			e.printStackTrace();
			return result;
		}
		return result;
	}/**
	 * 5  直播频道收藏列表当前播放和下一个节目接口
	 * @param 
	 * @return
	 */
	@RequestMapping("/phone/user/liveCollectListEpg")
	@ResponseBody
	public JsonMessage liveCollectListEpg(HttpServletRequest request){
		JsonMessage result = new JsonMessage();
		String userId = request.getParameter("userId");
		if(userId == null){
			result.setCode(0);
			result.setMessage("请输入用户ID");
			return result;
		}
		HashMap<String,Object> params = new HashMap<String, Object>();
		logger.info("入参userId:"+userId);
		params.put("userId", userId);
		try {
			result = userLiveCollectInfoService.liveCollectListEpg(params);
		}catch (Exception e) {
			result.setCode(0);
			result.setMessage("系统正在维护中,请稍后！");
			e.printStackTrace();
			return result;
		}
		return result;
	}

}