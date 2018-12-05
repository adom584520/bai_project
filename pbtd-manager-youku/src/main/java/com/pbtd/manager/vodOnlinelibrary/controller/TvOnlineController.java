package com.pbtd.manager.vodOnlinelibrary.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.web.RequestUtil;
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


}
