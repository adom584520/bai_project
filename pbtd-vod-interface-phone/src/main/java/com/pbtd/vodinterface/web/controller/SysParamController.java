package com.pbtd.vodinterface.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.vodinterface.util.RequestUtil;
import com.pbtd.vodinterface.web.service.face.ISysParamService;


/**系统参数接口
 * @author yf
 */
@Controller 
@RequestMapping("/phone/vod/sys")
public class SysParamController {
	@Autowired
	private ISysParamService sysParamService;
	private static final Logger logger = (Logger) LoggerFactory.getLogger(AlbumController.class); 

		/**
		* showdoc
		* @catalog 测试文档/用户相关
		* @title 1.获取频道下的所有模块数据
		* @description 获取频道下的所有模块数据
		* @method get
		* @url https://www.showdoc.cc/home/user/login
		* @param username 必选 string 用户名  
		* @param password 必选 string 密码  
		* @param name 可选 string 用户昵称  
		* @return {"error_code":0,"data":{"uid":"1","username":"12154545","name":"吴系挂","groupid":2,"reg_time":"1436864169","last_login_time":"0"}}
		* @return_param groupid int 用户组id
		* @return_param name string 用户昵称
		* @remark 这里是备注信息
		* @number 98
		*/
	@RequestMapping(value = "/getSysParam")
	@ResponseBody
	public Map<String, Object> getChannelModuleList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,Object> json=new HashMap<String,Object>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		response.setContentType("text/html;charset=utf-8");
		String platform = queryParams.get("platform").toString();
		queryParams.put("platform", platform);
		if(!"1".equals(platform)  &&  !"0".equals(platform)){
			json.put("code", 1);
			json.put("message", "请检查参数");
			json.put("data", null);
			return json;
		}
		try {
			Map<String, Object> channellist = sysParamService.getSysParam(queryParams);	//获取数据
			if(channellist != null){
				json.put("code", 1);
				json.put("message", "成功");
				json.put("data", channellist);
			}else{
				json.put("code", 1);
				json.put("message", "无数据");
				json.put("data", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问出错: 二级栏目换一批专辑列表 tgetchannel2Albumpage?"+queryParams);
			json.put("code", -1);
			json.put("message", "访问出错,请稍后访问！");
			json.put("data", null);
		}
		return json;
	}

}
