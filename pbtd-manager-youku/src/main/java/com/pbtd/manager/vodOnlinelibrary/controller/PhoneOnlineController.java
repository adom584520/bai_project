package com.pbtd.manager.vodOnlinelibrary.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.web.RequestUtil;
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
			//	tvService.resetalbum(queryParams);
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
}
