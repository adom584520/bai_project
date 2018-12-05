package com.pbtd.playlive.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playlive.config.LiveInterfaceUtil;
import com.pbtd.playlive.domain.LiveChannel;
import com.pbtd.playlive.page.PageResult;
import com.pbtd.playlive.service.ILiveChannelService;
import com.pbtd.playlive.service.ILiveGroupService;


/**
 * 用户操作
 * 
 * @author JOJO
 *
 */
@RequestMapping("/phone/live")
@Controller
public class LivePhoneInterfaceController {
	@Autowired
	private ILiveChannelService liveChannelService;
	@Autowired
	private ILiveGroupService liveGroupService;

	private static final Logger logger = LoggerFactory.getLogger(LivePhoneInterfaceController.class); 
	
	private  static String PRO_ID =LiveInterfaceUtil.PROJ_ID;
	
	/** 
	 * 1、分组获取所有分组信息   /央视/卫视/特色/体育/我的收藏
	 * /live/phone/interface/group/load?proj_id=cmcc
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getGroupList")
	@ResponseBody
	public PageResult<?> LiveGroupInfo(HttpServletRequest request) {
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id == null || proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-3);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		logger.info("访问getGroupList获取分组接口;入参proj_id:"+proj_id);
		try {
			pageResult = liveGroupService.querylistallLiveGroup();
			pageResult.setMessage("响应成功");
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		logger.info("访问getGroupList返回值"+pageResult.getCode());
		return pageResult;
	}

	/**
	 * 2、分组获取当前分组下的频道信息    /央视下的 cctv-1，cctv-2。。。
	 * /live/phone/interface/channel/load?proj_id=cmcc&groupid=1
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getChannelByGroupId")
	@ResponseBody
	public PageResult<?> LiveChannelInfo(HttpServletRequest request) {
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		int groupid ;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id == null || proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-2);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		try {
			groupid = Integer.valueOf(request.getParameter("groupId"));
		} catch (Exception e1) {
			pageResult.setCode(-3);
			pageResult.setMessage("分组ID有误");
			return pageResult;
		}
		
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问getChannelByGroupId接口;入参proj_id:"+proj_id+"groupid:"+groupid);
		params.put("proj_id", proj_id);
		params.put("groupid", groupid);
		try {
			pageResult = liveChannelService.querylistallLiveChannel(params);
			pageResult.setMessage("响应成功");
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		logger.info("访问getChannelByGroupId返回值"+pageResult.getCode());
		return pageResult;
		
	}
	
	/**
	 * 3、分组获取当前分组下的频道的当前播放界面和下一个节目    /央视下的 cctv-1，cctv-2 的 真在播放和下一个播放
	 * /live/phone/interface/playepg/load?proj_id=cmcc&groupid=1
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getPlayepgByGroupId")
	@ResponseBody
	public PageResult<?> LiveplayEpgInfo(HttpServletRequest request) {
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		int groupid ;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-2);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		try {
			groupid = Integer.valueOf(request.getParameter("groupId"));
		} catch (Exception e1) {
			pageResult.setCode(-3);
			pageResult.setMessage("分组ID有误");
			return pageResult;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问 getPlayepgByGroupId 接口；入参proj_id:"+proj_id+"groupid:"+groupid);
		params.put("proj_id", proj_id);
		params.put("groupid", groupid);
		try {
			pageResult = liveChannelService.queryLiveChannelplay(params);
			pageResult.setMessage("响应成功");
			if(pageResult.getData() == null ||pageResult.getData().equals("")){
				pageResult.setCode(-3);
				pageResult.setMessage("获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		logger.info("访问getPlayepgByGroupId返回值"+pageResult.getCode());
		return pageResult;
	}
	
	/**
	 * 4、获取指定频道的 7 天节目单		--> cctv-1 7天的节目单   
	 * /live/phone/interface/chnCodeepg/load?proj_id=cmcc&chnCode=cctv-1
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getSevenDayEpgByChnCode")
	@ResponseBody
	public PageResult<?> LivechnCodeepgInfo(HttpServletRequest request) {
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		String chnCode =null;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-2);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		LiveChannel liveChannel  = new LiveChannel();
		try {
			chnCode = request.getParameter("chnCode");
			if(chnCode.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("频道CODE不能为空");
				return pageResult;
			}
			liveChannel = liveChannelService.selectLiveChannel(chnCode);
			if( liveChannel == null){
				pageResult.setCode(-2);
				pageResult.setMessage("频道CODE不正确");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("频道CODE有误");
			return pageResult;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问 getSevenDayEpgByChnCode接口 ;入参proj_id:"+proj_id+"chnCode:"+chnCode);
		params.put("proj_id", proj_id);
		params.put("liveChannel", liveChannel);
		try {
			pageResult = liveChannelService.queryLiveChannelplayepg(params);
			pageResult.setMessage("响应成功");
			if(pageResult.getData() == null ||pageResult.getData().equals("")){
				pageResult.setCode(-3);
				pageResult.setMessage("获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		logger.info("访问getSevenDayEpgByChnCode返回值"+pageResult.getCode());
		return pageResult;
	}
	/**
	 * 5、获取当前频道当前时间的EPG信息    /cctv-1 正在播放的节目
	 * 192.168.0.54/live/phone/interface/chnCodeNowepg/load?proj_id=cmcc&chnCode=cctv-1
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getChnCodeNowEpg")
	@ResponseBody
	public PageResult<?> LivechnCodeNowepgInfo(HttpServletRequest request) {
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		String chnCode =null;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-2);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		LiveChannel liveChannel  = new LiveChannel();
		try {
			chnCode = request.getParameter("chnCode");
			if(chnCode.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("频道CODE不能为空");
				return pageResult;
			}
			liveChannel = liveChannelService.selectLiveChannel(chnCode);
			if( liveChannel == null){
				pageResult.setCode(-2);
				pageResult.setMessage("频道CODE不正确");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("频道CODE有误");
			return pageResult;
		}
		
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问接口 getChnCodeNowEpg；入参proj_id:"+proj_id+"chnCode:"+chnCode);
		params.put("proj_id", proj_id);
		params.put("liveChannel", liveChannel);
		try {
			pageResult = liveChannelService.queryLiveChannelplayNowepg(params);
			pageResult.setMessage("响应成功");
			if(pageResult.getData() == null ||pageResult.getData().equals("")){
				pageResult.setCode(-3);
				pageResult.setMessage("获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		logger.info("访问getChnCodeNowEpg返回值"+pageResult.getCode());
		return pageResult;

	}
	/**
	 * 6、获取所有频道信息    / cctv-1，cctv-2。。。
	 * /live/phone/interface/allchannel/load?proj_id=cmcc
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getAllChannelNowEpg")
	@ResponseBody
	public PageResult<?> LiveAllChannelInfo(HttpServletRequest request) {
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id == null || proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-2);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问接口 getAllChannelNowEpg；入参proj_id:"+proj_id);
		params.put("proj_id", proj_id);
		try {
			pageResult = liveChannelService.querylistallLiveChannel(params);
			pageResult.setMessage("响应成功");
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		logger.info("访问getAllChannelNowEpg返回值"+pageResult.getCode());
		return pageResult;
	}

	
//	/**
//	 * 7、获取七天日期    /明天，今天，昨天，前天。。。
//	 * 192.168.0.54/live/phone/interface/chnCodeNowepg/load?proj_id=cmcc&chnCode=cctv-1
//	 * @param qo
//	 * @return
//	 */
//	@RequestMapping("/dateinfo/load")
//	@ResponseBody
//	public PageResult<?> LiveDateInfo(HttpServletRequest request) {
//		PageResult<?> pageResult = new PageResult<>();
//		String proj_id =null;
//		String chnCode =null;
//		try {
//			proj_id = request.getParameter("proj_id");
//			if(proj_id.isEmpty()){
//				pageResult.setCode(-1);
//				pageResult.setMessage("项目ID不能为空");
//				return pageResult;
//			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
//				pageResult.setCode(-2);
//				pageResult.setMessage("项目ID有误");
//				return pageResult;
//			}
//		} catch (Exception e1) {
//			pageResult.setCode(-2);
//			pageResult.setMessage("项目ID有误");
//			return pageResult;
//		}
//		LiveChannel liveChannel  = new LiveChannel();
//		try {
//			chnCode = request.getParameter("chnCode");
//			if(chnCode.isEmpty()){
//				pageResult.setCode(-1);
//				pageResult.setMessage("频道CODE不能为空");
//				return pageResult;
//			}
//			liveChannel = liveChannelService.selectLiveChannel(chnCode);
//			if( liveChannel == null){
//				pageResult.setCode(-2);
//				pageResult.setMessage("频道CODE不正确");
//				return pageResult;
//			}
//		} catch (Exception e1) {
//			pageResult.setCode(-2);
//			pageResult.setMessage("频道CODE有误");
//			return pageResult;
//		}
//		
//		Map<String,Object> params = new HashMap<String, Object>();
//		logger.info("入参proj_id:"+proj_id);
//		logger.info("入参chnCode:"+chnCode);
//		params.put("proj_id", proj_id);
//		params.put("liveChannel", liveChannel);
//		try {
//			pageResult = liveChannelService.queryLiveChannelplayNowepg(params);
//			pageResult.setMessage("响应成功");
//			if(pageResult.getData() == null ||pageResult.getData().equals("")){
//				pageResult.setCode(-3);
//				pageResult.setMessage("获取失败");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			pageResult.setCode(0);
//			pageResult.setMessage("服务器异常");
//		}
//		return pageResult;
//	}

}