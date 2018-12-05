package com.pbtd.manager.vodOnlinelibrary.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.system.service.IRealmNameService;
import com.pbtd.manager.vod.phone.mould.service.face.IVodPhoneModuleInterface;
import com.pbtd.manager.vodOnlinelibrary.service.face.IPhoneOnlineService;
import com.pbtd.manager.vodOnlinelibrary.service.face.ITvOnlineService;


/**
 * 调用分平台接口 获取数据
 * @author zr
 *
 */
@Controller("pbtdController.PhoneOnlineController")
@RequestMapping("/phonereset")
public class PhoneOnlineController {
	@Autowired
	private IPhoneOnlineService phoneService;
	@Autowired
	private ITvOnlineService tvService;
	@Autowired
	public   IRealmNameService realmnameService;
	@Autowired
	private IVodPhoneModuleInterface   vodPhoneModuleInterface;
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
			phoneService.resetalbum(queryParams);
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
			phoneService.resetchannel(queryParams);
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
			phoneService.resetlabel(queryParams);
			//tvService.resetlabel(queryParams);
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
			phoneService.resetspecial(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 热搜同步
	 * 
	 * @return
	 */
	@RequestMapping("/resethotsearch")
	@ResponseBody
	public int resethotsearch(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			phoneService.resethotsearch(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	/**
	 * 专区推荐图片
	 * 
	 * @return
	 */
	@RequestMapping("/resetrecommandpic")
	@ResponseBody
	public int resetrecommandpic(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			phoneService.resetrecommandpic(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;		
	}
	/**
	 * 底部导航
	 * 
	 * @return
	 */
	@RequestMapping("/resetbottomnavigation")
	@ResponseBody
	public int resetbottomnavigation(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			phoneService.resetbottomnavigation(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;		
	}

	/**
	 * 文字描述
	 * 
	 * @return
	 */
	@RequestMapping("/resettextrecommendation")
	@ResponseBody
	public int resettextrecommendation(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			phoneService.resettextrecommendation(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;		
	}

	/**
	 *logo 
	 * @return
	 */
	@RequestMapping("/resetlogo")
	@ResponseBody
	public int resetlogo(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			phoneService.resetlogo(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;		
	}
	/**
	 *热播推荐
	 * @return
	 */
	@RequestMapping("/resethotseries")
	@ResponseBody
	public int resethotseries(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			phoneService.resethotseries(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;		
	}

	/**
	 *开机轮播图
	 * @return
	 */
	@RequestMapping("/resetsatrtslideshow")
	@ResponseBody
	public int resetsatrtslideshow(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			phoneService.resetstartslideshow(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;		
	}

	/**
	 *专区轮播图
	 * @return
	 */
	@RequestMapping("/resetslideshow")
	@ResponseBody
	public int resetslideshow(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			phoneService.resetslideshow(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;		
	}

	/**
	 *播放地址同步
	 * @return
	 */
	@RequestMapping("/resetmovieurl")
	@ResponseBody
	public int resetmovieurl(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			phoneService.resetmovieurl(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;		
	}

	/**
	 *标签分类
	 */
	@RequestMapping("/phonelabeltype")
	@ResponseBody
	public int phonelabeltype(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			phoneService.resetlabeltype(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;		
	}


	/**
	 * phone频道模块同步
	 * @return /phonereset/resetmodule
	 */
	@RequestMapping("/resetmodule/{channel}")
	@ResponseBody
	public Map<String, Object>  resetmodule(HttpServletRequest request,@PathVariable("channel") String channel,Model model){
		//Map<String, Object> queryParams =new HashMap<>();
		Map<String, Object> jsonMap=new HashMap<String,Object>();	
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("channel", channel.substring(channel.indexOf("_")+1,channel.length()));
		queryParams.put("flag", 0);
		queryParams.put("channelCode", channel);

		try {
			phoneService.resetmodule(queryParams);
			this.vodPhoneModuleInterface.updateChannelModuleFlag(queryParams);
			jsonMap.put("code", 1);
			jsonMap.put("message", "同步成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("code", 0);
			jsonMap.put("message", "同步失败");
		}
		return jsonMap;
	}


	/**
	 * phone全部频道模块同步
	 * 
	 * @return 
	 */
	@RequestMapping("/resetAllmodule")
	@ResponseBody
	public Map<String, Object> resetAllmodule(HttpServletRequest request,Model model){
		//Map<String, Object> params =new HashMap<String,Object>();
		Map<String, Object> jsonMap=new HashMap<String,Object>();	
		Map<String, Object> params = RequestUtil.asMap(request, false);
		try {
			phoneService.resetAllmodule(params);
			params.put("flag", 0);
			vodPhoneModuleInterface.updateChannelModuleFlag(params);
			jsonMap.put("code", 1);
			jsonMap.put("message", "同步成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("code", 0);
			jsonMap.put("message", "同步失败");
		}
		System.out.println("同步全部phone频道模版");
		return jsonMap;
	}


	/**
	 * phone module单模块同步
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/resetSingleModule")
	public int resetSingleModule(HttpServletRequest request) throws Exception{
		String moduleId=request.getParameter("curid");
		//Map<String,Object> param=new HashMap<String,Object>();
		Map<String, Object> param = RequestUtil.asMap(request, false);
		param.put("moduleId",moduleId );
		try {
			phoneService.resetSingleMoudle(param);
		} catch (Exception e) {
			throw new Exception("phone模块单模块同步时：",e);
		}
		return 1;
	}

	

	/**
	 * phone 收费专辑同步
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/phonepidalbuminfo")
	public int phonepidalbuminfo(HttpServletRequest request) throws Exception{
		Map<String, Object> param = RequestUtil.asMap(request, false);
		try {
			phoneService.resetphonepaidalbum(param);
		} catch (Exception e) {
			throw new Exception("phone收费专辑同步时：",e);
		}
		return 1;
	}
	
	
	/**
	 * phone 自动剧集同步
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/phonealbumvideo_interface")
	public int phonealbumvideo_interface(Map<String, Object> map) throws Exception{
		 Map<String,Object> maprealm=realmnameService.findtitle(null);
		  String img=maprealm==null?"":maprealm.get("imgtitle").toString();
	        map.put("imgtitle",img );
		try {
			phoneService.resetphonealbumvideo_interface(map);
		} catch (Exception e) {
			throw new Exception("自动剧集同步：",e);
		}
		return 1;
	}
	/**
	 * phone 自动增量专辑同步
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/phonealbum_interface")
	public int phonealbum_interface(Map<String, Object> map) throws Exception{
		 Map<String,Object> maprealm=realmnameService.findtitle(null);
		 String img=maprealm==null?"":maprealm.get("imgtitle").toString();
	        map.put("imgtitle",img );
		try {
			phoneService.resetphonealbum_interface(map);
		} catch (Exception e) {
			throw new Exception("自动增量专辑同步：",e);
		}
		return 1;
	}
}
