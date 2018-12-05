package com.pbtd.manager.vodOnlinelibrary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.vod.tv.mould.service.face.IvodTvModuleInterface;
import com.pbtd.manager.vodOnlinelibrary.service.face.ITvOnlineService;


/**
 * 调用分平台接口 获取数据
 * @author zr
 *
 */
@Controller("pbtdController.TvOnlineController")
@RequestMapping("/tvreset")
public class TvOnlineController {
	@Autowired
	private ITvOnlineService tvService;
	
	@Autowired
	private IvodTvModuleInterface   vodTvModuleInterface;
	
	
	/**
	 * 专辑同步
	 * 
	 * @return
	 */
	@RequestMapping("/resetalbum")
	@ResponseBody
	public int resetalbum(HttpServletRequest request) {
			 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			 try {
				tvService.resetalbum(queryParams);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 return 1;
	}

	/**
	 * 频道同步
	 * 
	 * @return
	 */
	@RequestMapping("/resetchannel")
	@ResponseBody
	public int resetchannel(HttpServletRequest request) {
			 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			 try {
				tvService.resetchannel(queryParams);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 return 1;
	}

	/**
	 * 标签同步
	 * 
	 * @return
	 */
	@RequestMapping("/resetlabel")
	@ResponseBody
	public int resetlabel(HttpServletRequest request) {
			 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			 try {
				tvService.resetlabel(queryParams);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 return 1;
	}

	/**
	 * 专题同步
	 * 
	 * @return
	 */
	@RequestMapping("/resetspecial")
	@ResponseBody
	public int resetspecial(HttpServletRequest request) {
			 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			 try {
				tvService.resetspecial(queryParams);
			} catch (Exception e) {
				e.printStackTrace();
			}
			 return 1;
	}
	/**
	 * tv频道模块同步
	 * 
	 * @return /tvreset/resetmodule
	 */
	@RequestMapping("/resetmodule/{channel}")
	@ResponseBody
	public Map<String, Object>  resetmodule(HttpServletRequest request,@PathVariable("channel") String channel,Model model){
		Map<String, Object> queryParams =new HashMap<>();
		Map<String, Object> jsonMap=new HashMap<String,Object>();	
		queryParams.put("channel", channel);
		queryParams.put("flag", 0);
		queryParams.put("channelCode", channel);
		
		try {
			tvService.resetmodule(queryParams);
			this.vodTvModuleInterface.updateChannelModuleFlag(queryParams);
			jsonMap.put("code", 1);
			jsonMap.put("message", "同步成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("code", 0);
			jsonMap.put("message", "同步失败");
		}
		
	
		//vodTvModuleInterface.updateflagforchannel(queryParams);
		
		/*Map<String, Object> params=new HashMap<>();
		List<Map<String, Object>> items=vodTvModuleInterface.find(params);
		JSONArray array = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> info=new HashMap<>();
		for (int i = 0; i < items.size(); i++) {
			info = items.get(i);
			jsonObject = new JSONObject();
			jsonObject.put("id", Long.parseLong(info.get("id").toString()));
			jsonObject.put("pId",  Long.parseLong(info.get("pid").toString()));
			jsonObject.put("name", info.get("name"));
			String open=info.get("open").toString();
			jsonObject.put("open", open.equals("1")?true:false);
			jsonObject.put("type", "tv");
			array.add(jsonObject);
		}
		model.addAttribute("modlulist", array.toString());
		return "/vod/tv/mould/index";*/
		return jsonMap;
	}
	
	
	/**
	 * tv全部频道模块同步
	 * 
	 * @return 
	 */
	@RequestMapping("/resetAllmodule")
	@ResponseBody
	public Map<String, Object> resetAllmodule(HttpServletRequest request,Model model){
		Map<String, Object> params =new HashMap<String,Object>();
		Map<String, Object> jsonMap=new HashMap<String,Object>();		
		try {
			tvService.resetAllmodule(params);
			params.put("flag", 0);
			vodTvModuleInterface.updateChannelModuleFlag(params);
			jsonMap.put("code", 1);
			jsonMap.put("message", "同步成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("code", 0);
			jsonMap.put("message", "同步失败");
		}
		System.out.println("同步全部tv频道模版");
		
	/*	Map<String, Object> params=new HashMap<>();
		List<Map<String, Object>> items=vodTvModuleInterface.find(params);
		JSONArray array = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> info=new HashMap<>();
		for (int i = 0; i < items.size(); i++) {
			info = items.get(i);
			jsonObject = new JSONObject();
			jsonObject.put("id", Long.parseLong(info.get("id").toString()));
			jsonObject.put("pId",  Long.parseLong(info.get("pid").toString()));
			jsonObject.put("name", info.get("name"));
			String open=info.get("open").toString();
			jsonObject.put("open", open.equals("1")?true:false);
			jsonObject.put("type", "tv");
			array.add(jsonObject);
		}
		model.addAttribute("modlulist", array.toString());
		return "/vod/tv/mould/index";*/
		return jsonMap;
	}

	
	/**
	 * tv module单模块同步
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/resetSingleModule")
	public int resetSingleModule(HttpServletRequest request) throws Exception{
		String moduleId=request.getParameter("curid");
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("moduleId",moduleId );
		try {
			tvService.resetSingleMoudle(param);
		} catch (Exception e) {
			throw new Exception("tv模块单模块同步时：",e);
		}
		return 1;
	}
	

}
