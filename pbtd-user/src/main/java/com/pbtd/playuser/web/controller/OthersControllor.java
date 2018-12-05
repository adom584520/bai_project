package com.pbtd.playuser.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OthersControllor {

	Logger logger = Logger.getLogger(OthersControllor.class);

	@Autowired
	private HttpServletRequest request;

	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/userAgreement", ""})
	public String indexaa() {
		return "/userAgreement";
	}
}

