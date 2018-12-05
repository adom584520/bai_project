package com.pbtd.manager.vodOnlinelibrary.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.vodOnlinelibrary.service.face.ICommonOnlineService;

/**
 * 调用分平台接口 获取数据
 * @author zr
 *
 */
@Controller("pbtdController.CommonOnlineController")
@RequestMapping("/commonreset")
public class CommonOnlineController {
	@Autowired
	private ICommonOnlineService  commonservice;
	/**
	 * 演员
	 * @return
	 */
	@RequestMapping("/resetactors")
	@ResponseBody
	public int resetrecommandpic(HttpServletRequest request) {
			 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			 try {
				 commonservice.resetactors(queryParams);
			} catch (Exception e) {
				e.printStackTrace();
				 return 0;
			}
			 return 1;		
	}
	/**
	 * 角标
	 * @return
	 */
	@RequestMapping("/resetcorner")
	@ResponseBody
	public int resetcorner(HttpServletRequest request) {
			 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			 try {
				 commonservice.resetcorner(queryParams);
			} catch (Exception e) {
				e.printStackTrace();
				 return 0;
			}
			 return 1;		
	}
	/**
	 * 付费包
	 * @return
	 */
	@RequestMapping("/restcollectfeesbag")
	@ResponseBody
	public int restcollectfeesbag(HttpServletRequest request) {
			 Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			 try {
				 commonservice.restcollectfeesbag(queryParams);
			} catch (Exception e) {
				e.printStackTrace();
				 return 0;
			}
			 return 1;		
	}
	/**
	 * 模版运营位同步
	 * @return
	 */
	@RequestMapping("/restmasterplateSon")
	@ResponseBody
	public int restmasterplateSon(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			commonservice.restmasterplateSon(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;		
	}
	/**
	 * 系统参数同步
	 * @return
	 */
	@RequestMapping("/restSysParam")
	@ResponseBody
	public int restSysParam(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		try {
			commonservice.restSysParam(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;		
	}

}
