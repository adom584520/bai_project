package com.pbtd.playclick.youku.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.pbtd.playclick.youku.domain.YouKualbum;
import com.pbtd.playclick.youku.domain.YouKualbumvideo;
import com.pbtd.playclick.youku.service.impl.YouKualbumService;
/**
 * 优酷媒资管理
 * @author zr
 *
 */
@Controller("youku.YoukuContrlller")
@RequestMapping("/youku/albums")
public class YoukuContrlller {
	public static Logger log = Logger.getLogger(YoukuContrlller.class);
	@Autowired
	private YouKualbumService  youkualbumserbice;

	@RequestMapping(value = {"/index"})
	public String index() {
		return "/youku/albumsinfo/index";
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
		List<YouKualbum> data = new ArrayList<YouKualbum>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		int total = this.youkualbumserbice.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.youkualbumserbice.page(queryParams);
		return new PageResult(total, data);
	}

	@RequestMapping("/show/{id}")
	public String edit(@PathVariable("id") String id,ModelMap model,HttpServletRequest request) {
		model.addAttribute("id",id);
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("show_id", id);
		List<YouKualbumvideo> gitlist=youkualbumserbice.findvideo(queryParams);
		model.addAttribute("gitlist",gitlist);
		return "/youku/albumsinfo/show";
	}

	@ResponseBody
	@RequestMapping("/load/{id}")
	public YouKualbum load(@PathVariable("id") String id,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("show_id", id);
		YouKualbum albus = null;
		if (!"".equals(id) && id !=null ) {
			albus = this.youkualbumserbice.load(queryParams);
		} 
		else {
			albus  =null;
		}
		return albus;
	}

	@RequestMapping("/showvideo/{id}")
	public String video(@PathVariable("id") String id,
			ModelMap model,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("video_id", id);
		YouKualbumvideo gitvideomap=youkualbumserbice.loadvideo(queryParams);
		model.addAttribute("map",gitvideomap);
		return "/youku/albumsinfo/showvideo";
	}

	@ResponseBody
	@RequestMapping("/status_paid")
	public Map<String, Object>  status_paid(ModelMap model,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		Map<String, Object> mapnext=new HashMap<>();
		String[] ids=queryParams.get("id").toString().split(",");
		String type=queryParams.get("type").toString();
		if(type.equals("paid")){
			queryParams.put("paid_next", 1);
		}else{
			queryParams.put("status_next", 1);
		}
		StringBuffer  msg=new StringBuffer();
		 for (String s : ids) {
			queryParams.put("show_id", s);
			List<Map<String, Object>> videolist=youkualbumserbice.findvideoalbum(queryParams);
			if(videolist.size()>0){
				String  name="";
				String  sequence="[";
			//	String  title="";
				for (Map<String, Object> map : videolist) {
					name=map.get("name").toString();
					sequence+=map.get("sequence").toString()+",";
					// title=map.get("title").toString();
				}
				sequence=sequence.substring(0, sequence.length()-1);
				sequence+="]";
				msg.append("{节目名称："+name+" &nbsp; &nbsp;"+sequence+"}</br>");
			}
		} 
		 mapnext.put("values", msg);
		 model.put("msg", msg);
		return mapnext;
	}
	
	@ResponseBody
	@RequestMapping("/updatestatus_paid")
	public int updatestatus_paid(ModelMap model,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String[] ids=queryParams.get("id").toString().split(",");
		String type=queryParams.get("type").toString();
		int flag=0;
		if(type.equals("paid")){
			queryParams.put("paid_next", 1);
		}else{
			queryParams.put("status_next", 1);
		}
		 for (String s : ids) {
			queryParams.put("show_id", s);
			youkualbumserbice.updatealbumnext(queryParams);
			youkualbumserbice.updatealbumvideonext(queryParams);
			}
		return flag;
	}
	
	 /**
     *导出
     * @param vodChannel VodChannel实例
     */
    @ResponseBody
    @RequestMapping(value = "/exportalbum")
    public void exportalbum( HttpServletRequest request,HttpServletResponse response) {
		List<YouKualbum> data = new ArrayList<YouKualbum>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        data =this.youkualbumserbice.page(queryParams);
        String[] fields = new String[9];
		String[] titles = new String[9];
		fields[0] = "show_id";
		titles[0] = "id";
		fields[1] = "status_next";
		titles[1] = "有效状态";
		fields[2] = "paid_next";
		titles[3] = "收费状态";
	String subject = "优酷媒资--"+DateUtil.format(new Date(),DateUtil.dateFormat5);
		TableData td = ExcelUtils.createTableData(data, ExcelUtils.createTableHeader(titles),fields);
		try {
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportToExcel(subject, " ", td);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
    @RequestMapping("/editvideo/{id}")
	public String showvideo(@PathVariable("id") String id,ModelMap model,HttpServletRequest request) {
		model.addAttribute("show_id",id);
		return "/youku/albumsinfo/editvideo";
	}
    @ResponseBody
	@RequestMapping("/showvideopage")
	public PageResult showvideopage(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
		List<YouKualbumvideo> data = new ArrayList<YouKualbumvideo>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		int total = this.youkualbumserbice.countvideo(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.youkualbumserbice.pagevideo(queryParams);
		return new PageResult(total, data);
	}
}
