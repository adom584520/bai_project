package com.pbtd.playclick.youku.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.util.DateUtil;
import com.pbtd.playclick.base.common.util.excelTools.ExcelUtils;
import com.pbtd.playclick.base.common.util.excelTools.JsGridReportBase;
import com.pbtd.playclick.base.common.util.excelTools.TableData;
import com.pbtd.playclick.base.common.web.RequestUtil;
import com.pbtd.playclick.page.PageResult;
import com.pbtd.playclick.page.QueryObject;
import com.pbtd.playclick.youku.service.impl.YouKualbumService;

@Controller("youku.PlayPolicyMsgsController")
@RequestMapping("/youku/playpolicy")
public class PlayPolicyMsgsController {
	
	@Autowired
	private YouKualbumService  youkualbumserbice;
	
	@RequestMapping(value = {"/index"})
	public String index() {
		return "/youku/playpolicy/index";
	}
	/**
	 * 分页查询
	 * @param page
	 * @param limit
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/page")
	public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
		try {
			if(!"".equals(queryParams.get("start_create_time")) && queryParams.get("start_create_time")!=null){
				String str=queryParams.get("start_create_time").toString();
				queryParams.put("start_create_time",sfEnd.format(sfStart.parse(str)));
			}
			if(!"".equals(queryParams.get("end_create_time")) && queryParams.get("end_create_time")!=null){
				String str=queryParams.get("end_create_time").toString();
				queryParams.put("end_create_time",sfEnd.format(sfStart.parse(str)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int total = this.youkualbumserbice.countPlayPolicyMsgs(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.youkualbumserbice.pagePlayPolicyMsgs(queryParams);
		return new PageResult(total, data);
	}
	 /**
     *导出
     * @param vodChannel VodChannel实例
     */
    @ResponseBody
    @RequestMapping(value = "/exportalbum")
    public void exportalbum( HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		SimpleDateFormat sfEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sfStart = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",java.util.Locale.ENGLISH) ;
		try {
			if(!"".equals(queryParams.get("start_create_time")) && queryParams.get("start_create_time")!=null){
				String str=queryParams.get("start_create_time").toString();
				queryParams.put("start_create_time",sfEnd.format(sfStart.parse(str)));
			}
			if(!"".equals(queryParams.get("end_create_time")) && queryParams.get("end_create_time")!=null){
				String str=queryParams.get("end_create_time").toString();
				queryParams.put("end_create_time",sfEnd.format(sfStart.parse(str)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> data =this.youkualbumserbice.pagePlayPolicyMsgs(queryParams);
        String[] fields = new String[5];
		String[] titles = new String[5];
		fields[0] = "mediaId";
		titles[0] = "id";
		fields[1] = "play";
		titles[1] = "播控状态";
		fields[2] = "type";
		titles[2] = "类别";
		fields[3] = "showid";
		titles[3] = "专辑id";
		fields[4] = "videoid";
		titles[4] = "剧集id";
	String subject = "优酷播控媒资--"+DateUtil.format(new Date(),DateUtil.dateFormat5);
		TableData td = ExcelUtils.createTableData(data, ExcelUtils.createTableHeader(titles),fields);
		try {
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportToExcel(subject, " ", td);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    
    //删除 
    @ResponseBody
	@RequestMapping("/deletes")
	public int updatestatus_paid(ModelMap model,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String[] ids=queryParams.get("id").toString().split(",");
		int flag=0;
		 for (String s : ids) {
			queryParams.put("id", s);
			queryParams.put("ye", 1);
			youkualbumserbice.deletePlayPolicyMsgs3(queryParams);
			}
		return flag;
	}
	
}
