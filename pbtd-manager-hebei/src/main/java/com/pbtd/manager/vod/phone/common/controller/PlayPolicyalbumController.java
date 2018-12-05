package com.pbtd.manager.vod.phone.common.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.ParseExcel;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.phone.common.service.face.IPlayPolicyalbumService;

@Controller("PlayPolicyalbumController")
@RequestMapping("/vod/phone/album/playpolicy")
public class PlayPolicyalbumController {

	@Autowired
	private IPlayPolicyalbumService playservice;
	@Autowired
	private IDictionaryService dictionaryService;

	@RequestMapping(value = {"/index",""})
	public String index(Model model) {
		Map<String, Object> params=new HashMap<>();
		params.put("levels", 1);
		params.put("navigationtype", 1);
		model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));
		return "/vod/phone/album/playpolicy/index";
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
		int total = this.playservice.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.playservice.page(queryParams);
		return new PageResult(total, data);
	}


	@RequestMapping(value = "/importalbum")
	public String importalbum() {
		return "/vod/phone/album/playpolicy/importalbum";
	}
	@ResponseBody
	@RequestMapping(value="importExcel",method = RequestMethod.POST)
	public String importExcel(MultipartHttpServletRequest request, HttpServletResponse response) {
		MultipartFile file = request.getFile("excel");
		String flag = "1";
		Map<String,Object> m=new HashMap<String, Object>();
		try {
			List<Map<String,Object>> list = ParseExcel.parseExcelplaypolicy(file.getInputStream());
			for (Map<String,Object> map : list) {
				String id = map.get("mediaId") == null ? "" : map.get("mediaId").toString();
				if (id != "") {
					m.put("showid", map.get("showid"));
					m.put("videoid", map.get("videoid"));
					m.put("type", map.get("type"));
					int c=playservice.count(m);
					if(c>0){
						playservice.update(map);
					}else{
						playservice.insert(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = "0";
		}
		return flag;
	}
	
	
	/**
	 * POST 更改剧集是否显示
	 * @param ids 标识列表
	 */
	@LogAnnotation(operationInfo = "点播-播控-更改剧集是否显示操作")
	@ResponseBody
	@RequestMapping(value = "/video/updateisshow", method = RequestMethod.POST)
	public int videoupdateisshow( HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.playservice.updateisshow(queryParams);
	}

}
