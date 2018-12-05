
package com.pbtd.manager.user.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pbtd.manager.system.domain.UserKeep;
import com.pbtd.manager.user.domain.UserKeepMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.util.DateUtil;
import com.pbtd.manager.base.common.util.excelTools.ExcelUtils;
import com.pbtd.manager.base.common.util.excelTools.JsGridReportBase;
import com.pbtd.manager.base.common.util.excelTools.TableData;
import com.pbtd.manager.user.service.SysCountService;
import com.pbtd.manager.user.util.DateFormaUtil;
import com.pbtd.manager.util.RequestUtil;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;


@Controller 
@RequestMapping("/user")
public class UserCountController {

	@Autowired
	private SysCountService sysCountService;

	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/usercount/index", ""})
	public String index(Model model) {
		return "/user/usercount/index";
	}
	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/seriescount/index", ""})
	public String index2(Model model) {
		model.addAttribute("channellist", sysCountService.findallchannel());
		return "/user/seriescount/index";
	}
	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/channelcount/index", ""})
	public String index3(Model model) {
		model.addAttribute("channellist", sysCountService.findallchannel());
		return "/user/channelcount/index";
	}
	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/mobilecount/index", ""})
	public String index4(Model model) {
		return "/user/mobilecount/index";
	}
	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/userkeepcount/index", ""})
	public String index5(Model model) {
		return "/user/userkeepcount/index";
	}


	/**
	 * @return 总记录
	 */
	@ResponseBody
	@RequestMapping("/usercount/page")
	public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit,HttpServletRequest request) {
		List<Map<String,Object>> list=null;
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
		try {
			if(!"".equals(queryParams.get("starttime")) && queryParams.get("starttime")!=null){
				String str=queryParams.get("starttime").toString();
				queryParams.put("starttime",sfEnd.format(sfStart.parse(str)));
			}
			if(!"".equals(queryParams.get("endtime")) && queryParams.get("endtime")!=null){
				String str=queryParams.get("endtime").toString();
				queryParams.put("endtime",sfEnd.format(sfStart.parse(str)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date now = new Date();
		String createtime = (String) (queryParams.get("starttime")==null?DateFormaUtil.thismouthfirstString(now).trim():queryParams.get("starttime"));
		String endtime = (String) (queryParams.get("endtime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("endtime"));

		queryParams.put("starttime", createtime+" 00:00:00");
		queryParams.put("endtime", endtime+" 23:59:59");
		int total=sysCountService.count(queryParams);
		if (total==0) {
			list=Collections.emptyList();
		} 
		else {
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(limit);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
			list =sysCountService.find(queryParams);
		}
		return new PageResult(total+1, list);

	}

	/**
	 * @return 专辑总记录 
	 */
	@ResponseBody
	@RequestMapping("/seriescount/page")
	public PageResult seriespage(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit,HttpServletRequest request) {
		List<Map<String,Object>> list=null;
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
		try {
			if(!"".equals(queryParams.get("starttime")) && queryParams.get("starttime")!=null){
				String str=queryParams.get("starttime").toString();
				queryParams.put("starttime",sfEnd.format(sfStart.parse(str)));
			}
			if(!"".equals(queryParams.get("endtime")) && queryParams.get("endtime")!=null){
				String str=queryParams.get("endtime").toString();
				queryParams.put("endtime",sfEnd.format(sfStart.parse(str)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date now = new Date();
		String createtime = (String) (queryParams.get("starttime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("starttime"));
		String endtime = (String) (queryParams.get("endtime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("endtime"));

		queryParams.put("starttime", createtime+" 00:00:00");
		queryParams.put("endtime", endtime+" 23:59:59");
		String topnum = (String) queryParams.get("topstatus");


		int total=sysCountService.seriespagecount(queryParams);
		if (total==0) {
			list=Collections.emptyList();
		}else {
			if(topnum != null){
				int topstatus = 0;
				try {
					topstatus= Integer.valueOf(topnum);
				} catch (Exception e) {
					System.out.println("传入参数有误");
				}
				queryParams.put("topstatus", topstatus);
				total= topstatus;
			}else{
				queryParams.put("limitnum", "limitnum");
			}
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(limit);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
			list =sysCountService.seriespagefind(queryParams);
		}

		return new PageResult(total, list);

	}


	/**
	 * @return 频道总记录 
	 */
	@ResponseBody
	@RequestMapping("/channelcount/page")
	public PageResult channelpage(HttpServletRequest request) {
		List<Map<String,Object>> list=null;
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
		try {
			if(!"".equals(queryParams.get("starttime")) && queryParams.get("starttime")!=null){
				String str=queryParams.get("starttime").toString();
				queryParams.put("starttime",sfEnd.format(sfStart.parse(str)));
			}
			if(!"".equals(queryParams.get("endtime")) && queryParams.get("endtime")!=null){
				String str=queryParams.get("endtime").toString();
				queryParams.put("endtime",sfEnd.format(sfStart.parse(str)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date now = new Date();
		String createtime = (String) (queryParams.get("starttime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("starttime"));
		String endtime = (String) (queryParams.get("endtime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("endtime"));

		queryParams.put("starttime", createtime+" 00:00:00");
		queryParams.put("endtime", endtime+" 23:59:59");

		int total=sysCountService.channelpagecount(queryParams);
		if (total==0) {
			list=Collections.emptyList();
		} 
		else {
			list =sysCountService.channelpagefind(queryParams);
		}
		return new PageResult(total, list);
	}


	/**
	 * @return 手机号记录 ~~ 慢慢查 user数据库 
	 */
	@ResponseBody
	@RequestMapping("/mobilecount/page")
	public PageResult mobilepage(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit,HttpServletRequest request) {
		List<Map<String,Object>> list=null;
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
		try {
			if(!"".equals(queryParams.get("starttime")) && queryParams.get("starttime")!=null){
				String str=queryParams.get("starttime").toString();
				queryParams.put("starttime",sfEnd.format(sfStart.parse(str)));
			}
			if(!"".equals(queryParams.get("endtime")) && queryParams.get("endtime")!=null){
				String str=queryParams.get("endtime").toString();
				queryParams.put("endtime",sfEnd.format(sfStart.parse(str)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date now = new Date();
		String createtime = (String) (queryParams.get("starttime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("starttime"));
		String endtime = (String) (queryParams.get("endtime")==null?DateFormaUtil.todayString(now).trim():queryParams.get("endtime"));

		queryParams.put("starttime", createtime);
		queryParams.put("endtime", endtime);
		String mobile = (String) queryParams.get("mobile");
		if(mobile == null || mobile.isEmpty()){
			list=Collections.emptyList();
			return new PageResult(0, list);
		}

		int total=sysCountService.mobilepagecount(queryParams);
		if (total==0) {
			list=Collections.emptyList();
		}else {
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(limit);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
			list =sysCountService.mobilepagefind(queryParams);
		}

		return new PageResult(total, list);
	}


	/**
	 * 查看剧集点播数据
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/mobileseriescount/page/{seriesCode}")
	public String mobileseriescount(@PathVariable("seriesCode") String seriesCode,HttpServletRequest request,ModelMap model) {
		model.addAttribute("id",seriesCode);
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
		try {
			if(!"".equals(queryParams.get("starttime")) && queryParams.get("starttime")!=null){
				String str=queryParams.get("starttime").toString();
				queryParams.put("starttime",sfEnd.format(sfStart.parse(str)));
			}
			if(!"".equals(queryParams.get("endtime")) && queryParams.get("endtime")!=null){
				String str=queryParams.get("endtime").toString();
				queryParams.put("endtime",sfEnd.format(sfStart.parse(str)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date now = new Date();
		String createtime = (String) (queryParams.get("starttime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("starttime"));
		String endtime = (String) (queryParams.get("endtime")==null?DateFormaUtil.todayString(now).trim():queryParams.get("endtime"));

		//	queryParams.put("starttime", createtime);
		//	queryParams.put("endtime", endtime);
		String mobile = (String) queryParams.get("mobile");
		//	queryParams.put("seriesCode", seriesCode);
		//	List list = sysCountService.mobileseriespagefind(queryParams);
		model.addAttribute("seriesCode",seriesCode);
		model.addAttribute("mobile",mobile);
		model.addAttribute("createtime",createtime);
		model.addAttribute("endtime",endtime);
		///	model.addAttribute("serieslist",list);
		//	model.addAttribute("allcount",list.size());
		return "/user/mobilecount/seriespageindex";
	}
	/**
	 * 查看剧集点播数据
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mobilepagecount/page")
	public PageResult mobilepagecount(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
		try {
			if(!"".equals(queryParams.get("starttime")) && queryParams.get("starttime")!=null){
				String str=queryParams.get("starttime").toString();
				queryParams.put("starttime",sfEnd.format(sfStart.parse(str)));
			}
			if(!"".equals(queryParams.get("endtime")) && queryParams.get("endtime")!=null){
				String str=queryParams.get("endtime").toString();
				queryParams.put("endtime",sfEnd.format(sfStart.parse(str)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date now = new Date();
		String createtime = (String) (queryParams.get("starttime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("starttime"));
		String endtime = (String) (queryParams.get("endtime")==null?DateFormaUtil.todayString(now).trim():queryParams.get("endtime"));

		queryParams.put("starttime", createtime);
		queryParams.put("endtime", endtime);
		int total= sysCountService.mobileseriespagecount(queryParams);
		List<Map<String,Object>> list=null;

		if (total==0) {
			list=Collections.emptyList();
		}else {
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(limit);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
			list = sysCountService.mobileseriespagefind(queryParams);
		}
		return new PageResult(total, list);
	}




	/**
	 * @return 用户留存 ~~ 慢慢查 user数据库 
	 */
	@ResponseBody
	@RequestMapping("/userkeepcount/page")
	public PageResult userkeeppage(HttpServletRequest request) {
		List<Map<String,Object>> list=null;
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
		try {
			if(!"".equals(queryParams.get("starttime")) && queryParams.get("starttime")!=null){
				String str=queryParams.get("starttime").toString();
				queryParams.put("starttime",sfEnd.format(sfStart.parse(str)));
			}
			if(!"".equals(queryParams.get("endtime")) && queryParams.get("endtime")!=null){
				String str=queryParams.get("endtime").toString();
				queryParams.put("endtime",sfEnd.format(sfStart.parse(str)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date now = new Date();
		String createtime = (String) (queryParams.get("starttime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("starttime"));
		String endtime = (String) (queryParams.get("endtime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("endtime"));

		queryParams.put("starttime", createtime+" 00:00:00");
		queryParams.put("startendtime", createtime+" 23:59:59");
		queryParams.put("endtime", endtime+" 23:59:59");
		queryParams.put("baseStartTime", createtime);
		queryParams.put("baseEndTime", endtime);
		try {
			int count  = DateFormaUtil.daysBetween(createtime,endtime);
			queryParams.put("count", count+1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		list =sysCountService.userkeeppagefind(queryParams);
		return new PageResult(1, list);
	}
















	/**
	 *导出
	 * @param 用户实例
	 */
	@ResponseBody
	@RequestMapping(value = "/usercount/export")
	public void exportuser( HttpServletRequest request,HttpServletResponse response) {
		List<Map<String,String>>  data = new ArrayList<>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		data =this.sysCountService.page(queryParams);
		String[] fields = new String[15];
		String[] titles = new String[15];
		fields[0] = "createtime";
		titles[0] = "日期";
		fields[1] = "usercount";
		titles[1] = "会员数";
		fields[2] = "addusercount";
		titles[2] = "新增会员数";
		fields[3] = "playusercount";
		titles[3] = "点播过的用户数";
		fields[4] = "addplayusercount";
		titles[4] = "点播用户数较前日差";
		fields[5] = "playcount";
		titles[5] = "点播次数";
		fields[6] = "addplaycount";
		titles[6] = "点播次数较前日差";
		fields[7] = "activecount";
		titles[7] = "页面点击次数";
		fields[8] = "addactivecount";
		titles[8] = "页面点击次数次数较前日差";
		fields[9] = "activeusercount";
		titles[9] = "点击用户数";
		fields[10] = "addactiveusercount";
		titles[10] = "点击用户数次数较前日差";
		fields[11] = "playtime";
		titles[11] = "点播总时长（min）";
		fields[12] = "activedegree";
		titles[12] = "活跃度（%）";
		fields[13] = "playactivedegree";
		titles[13] = "点播活跃度（%）";
		fields[14] = "playtimetoone";
		titles[14] = "人均播放时长（min）";
		String subject = "用户统计--"+DateUtil.format(new Date(),DateUtil.dateFormat5);
		TableData td = ExcelUtils.createTableData(data, ExcelUtils.createTableHeader(titles),fields);
		try {
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportToExcel(subject, " ", td);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 *导出
	 * @param 专辑实例
	 */
	@ResponseBody
	@RequestMapping(value = "/seriescount/export")
	public void exportseries( HttpServletRequest request,HttpServletResponse response) {
		List<Map<String,Object>>  data = new ArrayList<>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
		try {
			if(!"".equals(queryParams.get("starttime")) && queryParams.get("starttime")!=null){
				String str=queryParams.get("starttime").toString();
				queryParams.put("starttime",sfEnd.format(sfStart.parse(str)));
			}
			if(!"".equals(queryParams.get("endtime")) && queryParams.get("endtime")!=null){
				String str=queryParams.get("endtime").toString();
				queryParams.put("endtime",sfEnd.format(sfStart.parse(str)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Date now = new Date();
		String createtime = (String) (queryParams.get("starttime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("starttime"));
		String endtime = (String) (queryParams.get("endtime")==null?DateFormaUtil.yestodayString(now).trim():queryParams.get("endtime"));

		queryParams.put("starttime", createtime+" 00:00:00");
		queryParams.put("endtime", endtime+" 23:59:59");


		String topnum = (String) queryParams.get("topstatus");
		if(topnum != null){
			int topstatus = 0;
			try {
				topstatus= Integer.valueOf(topnum);
			} catch (Exception e) {
				System.out.println("传入参数有误");
			}
			queryParams.put("topstatus", topstatus);
		}
		data =this.sysCountService.seriespagefind(queryParams);
		String[] fields = new String[8];
		String[] titles = new String[8];
		fields[0] = "createtime";
		titles[0] = "日期";
		fields[1] = "seriesCode";
		titles[1] = "专辑Code";
		fields[2] = "seriesName";
		titles[2] = "专辑名称";
		fields[3] = "playtime";
		titles[3] = "播放总时长";
		fields[4] = "playcount";
		titles[4] = "播放总次数";
		fields[5] = "playusercount";
		titles[5] = "播放人数";
		fields[6] = "channel";
		titles[6] = "频道Code";
		fields[7] = "channleName";
		titles[7] = "频道名称";
		String subject = "排行榜统计--"+DateUtil.format(new Date(),DateUtil.dateFormat5);
		TableData td = ExcelUtils.createTableData(data, ExcelUtils.createTableHeader(titles),fields);
		try {
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportToExcel(subject, " ", td);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}




	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@ResponseBody
	@RequestMapping(value = {"/aaaaa"})
	public String bbb(HttpServletRequest request) {
		String createtime  =  request.getParameter("downloadtime");
		String string = DateFormaUtil.tomorrowString(createtime) +" 01:31:00";
		System.out.println("aaaaa开始aaaaa开始手动导入专辑数据："+string);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = null;
		try {
			now = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sysCountService.usercount(now);
		System.out.println("aaaaa结束aaaaa" +now);
		return "1";
	}

	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@ResponseBody
	@RequestMapping(value = {"/bbbbb"})
	public void eee(HttpServletRequest request) {
		String createtime  =  request.getParameter("downloadtime");
		String string = DateFormaUtil.tomorrowString(createtime)+" 01:41:00";
		System.out.println("~~~~~~~bbbbb~~~~~~~~开始手动导入专辑数据："+string);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = null;
		try {
			now = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sysCountService.seriescount(now);
		System.out.println(string+"~~~~~~~~~bbbbb~~~~~~结束"+now);
	}

	/**
	 * GET 查询
	 *
	 */
	@ResponseBody
	@RequestMapping(value = {"/getUserKeep"})
	public PageResult getUserKeep(HttpServletRequest request) {

		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("startTime", startTime);
		queryParams.put("endTime", endTime);
		List<UserKeep> userKeepList = sysCountService.getUserKeep(queryParams);

		return new PageResult(userKeepList.size(),userKeepList);
	}

	@ResponseBody
	@RequestMapping(value = {"/insertUserKeep"})
	public PageResult insertUserKeep(HttpServletRequest request) {

		String endTime = request.getParameter("endTime");
//		String endTime = request.getParameter("endTime");

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("endTime", endTime);
		sysCountService.insertUserKeep(queryParams);

		return new PageResult();
	}

	/**
	 * GET 查询
	 *
	 */
	@ResponseBody
	@RequestMapping(value = {"/getUserKeepOfMonth"})
	public PageResult getUserKeepOfMonth(HttpServletRequest request) {

		String startTime = request.getParameter("startTime");
//		String endTime = request.getParameter("endTime");

		List<String> timeList = new ArrayList<>();
		timeList.add("2018-10-01");
		timeList.add("2018-10-02");
		timeList.add("2018-10-03");
		timeList.add("2018-10-04");
		timeList.add("2018-10-05");
		timeList.add("2018-10-06");
		timeList.add("2018-10-07");


		List<UserKeepMonth> userKeepMonths = new ArrayList<>();
		for(int i = 0; i < timeList.size(); i ++){
			Map<String, Object> queryParams = new HashMap<>();
			queryParams.put("startTime", timeList.get(i));

			List<UserKeep> userKeepList = sysCountService.getUserKeep(queryParams);
			UserKeepMonth userKeepMonth = new UserKeepMonth();
			int j = 0;
			for(UserKeep userKeep : userKeepList){
				j ++;
				float keepPercent = userKeep.keepPercent;
				userKeepMonth.keepUser = userKeep.keepUser;
				userKeepMonth.startTime = userKeep.startTime;
				userKeepMonth.endTime = userKeep.endTime;
				userKeepMonth.startUser = userKeep.startUser;
				setKeepPercent(userKeepMonth, keepPercent, j);

			}
			userKeepMonths.add(userKeepMonth);

		}




		return new PageResult(userKeepMonths.size(),userKeepMonths);
	}

	private void setKeepPercent(UserKeepMonth userKeepMonth, float keepPercent, int i) {
		switch (i){
			case 1:
				userKeepMonth.keepPercent1 = keepPercent;
				break;
			case 2:
				userKeepMonth.keepPercent2 = keepPercent;
				break;
			case 3:
				userKeepMonth.keepPercent3 = keepPercent;
				break;
			case 4:
				userKeepMonth.keepPercent4 = keepPercent;
				break;
			case 5:
				userKeepMonth.keepPercent5 = keepPercent;
				break;
			case 6:
				userKeepMonth.keepPercent6 = keepPercent;
				break;
			case 7:
				userKeepMonth.keepPercent7 = keepPercent;
				break;
			case 8:
				userKeepMonth.keepPercent8 = keepPercent;
				break;
			case 9:
				userKeepMonth.keepPercent9 = keepPercent;
				break;
			case 10:
				userKeepMonth.keepPercent10 = keepPercent;
				break;
			case 11:
				userKeepMonth.keepPercent11 = keepPercent;
				break;
			case 12:
				userKeepMonth.keepPercent12 = keepPercent;
				break;
			case 13:
				userKeepMonth.keepPercent13 = keepPercent;
				break;
			case 14:
				userKeepMonth.keepPercent14 = keepPercent;
				break;
			case 15:
				userKeepMonth.keepPercent15 = keepPercent;
				break;
			case 16:
				userKeepMonth.keepPercent16 = keepPercent;
				break;
			case 17:
				userKeepMonth.keepPercent17 = keepPercent;
				break;
			case 18:
				userKeepMonth.keepPercent18 = keepPercent;
				break;
			case 19:
				userKeepMonth.keepPercent19 = keepPercent;
				break;
			case 20:
				userKeepMonth.keepPercent20 = keepPercent;
				break;
			case 21:
				userKeepMonth.keepPercent21 = keepPercent;
				break;
			case 22:
				userKeepMonth.keepPercent22 = keepPercent;
				break;
			case 23:
				userKeepMonth.keepPercent23 = keepPercent;
				break;
			case 24:
				userKeepMonth.keepPercent24 = keepPercent;
				break;
			case 25:
				userKeepMonth.keepPercent25 = keepPercent;
				break;
			case 26:
				userKeepMonth.keepPercent26 = keepPercent;
				break;
			case 27:
				userKeepMonth.keepPercent27 = keepPercent;
				break;
			case 28:
				userKeepMonth.keepPercent28 = keepPercent;
				break;
			case 29:
				userKeepMonth.keepPercent29 = keepPercent;
				break;
			case 30:
				userKeepMonth.keepPercent30 = keepPercent;
				break;
			case 31:
				userKeepMonth.keepPercent31 = keepPercent;
				break;
			default:
			    break;
		}
	}

}
