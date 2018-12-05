package com.pbtd.manager.vod.page.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbtd.manager.base.common.web.RequestUtil;

@Controller
@RequestMapping("/uploadPic")
public class UpLoadPicController {

	/**
	 * @return
	 */
	@RequestMapping(value = "/updateimg")
	public String uploadPicPage(Model model, HttpServletRequest request) {
		String picname = request.getParameter("picname");
		String id = request.getParameter("id");
		String imgtype = request.getParameter("imgtype") == null ? "" : request.getParameter("imgtype").toString();
		model.addAttribute("id", id);
		model.addAttribute("picname", picname);
		model.addAttribute("imgtype", imgtype);
		return "/vod/uploadPic/index";
	}
	/**
	 * @return
	 */
	@RequestMapping(value="/showimg")
	public String showimg(Model  model,HttpServletRequest request) {
		Map<String,Object> params = RequestUtil.asMap(request);
		String img=request.getParameter("img");
		model.addAttribute("img", img);
		return "/vod/uploadPic/showimg";
	}
}