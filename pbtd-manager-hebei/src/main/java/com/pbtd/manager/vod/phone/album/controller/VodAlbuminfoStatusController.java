package com.pbtd.manager.vod.phone.album.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
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
import com.pbtd.manager.vod.phone.album.service.face.IVodAlbuminfoService;
import com.pbtd.manager.vod.phone.album.service.face.IVodAlbuminfoStatusService;


/**
 * 专辑信息变更
 * @author zr
 */
@Controller 
@RequestMapping("/vod/phone/vodalbuminfostatus")
public class VodAlbuminfoStatusController {

    @Autowired
    private IVodAlbuminfoService vodAlbuminfoService;
    
    @Autowired
    private IDictionaryService dictionaryService;
    
    @Autowired
    private IVodAlbuminfoStatusService vodAlbuminfoStatus;
    /**
     * GET 查询
     *
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index(Model model) {
    	Map<String, Object> params=new HashMap<>();
    	params.put("levels", 1);
    	params.put("navigationtype", 1);
    	model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));
    	params.put("levels", 1);
    	params.put("navigationtype", 2);
    	model.addAttribute("channellist2", dictionaryService.findDictionaryMap(params, "choosechannel"));
    	model.addAttribute("sourceTypelist", dictionaryService.findDictionaryMap(params,"choosesourcetype"));
    	return "/vod/phone/album/vodalbuminfostatus/index";
    }

     
 
    /**
     * 模糊获取符合查询条件的分页记录
     * 
     * @param page 页码
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     */
    @ResponseBody
    @RequestMapping("/page")
    public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
    	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodAlbuminfoService.countstatus(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodAlbuminfoService.findstatus(queryParams);
			return new PageResult(total, data);
		
    }

    
    @RequestMapping(value = "/importalbum")
    public String importalbum() {
    	return "/vod/phone/album/vodalbuminfostatus/importalbum";
    }
   

    @ResponseBody
    @RequestMapping(value="importExcel",method = RequestMethod.POST)
    public String importExcel(MultipartHttpServletRequest request, HttpServletResponse response) {
      MultipartFile file = request.getFile("excel");
      String flag = "1";
      try {
    	    List<Map<String,Object>> list = ParseExcel.parseExcelalbumstatusupdate(file.getInputStream());
    	   if(list.size()>0){
    		   vodAlbuminfoService.deletealbumstatusupdate(null);
    	   }
    	    for (Map<String,Object> map : list) {
    	      String id = map.get("showid") == null ? "" : map.get("showid").toString();
    	      if (id != "") {
    	    	  vodAlbuminfoService.insertalbumstatusupdate(map);
    	      }
    	    }
      } catch (Exception e) {
        e.printStackTrace();
        flag = "0";
      }
      return flag;
    }
    
    
    /**
	 * 更改收费状态
	 * @param id标识
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-更改收费状态")
	@RequestMapping("/updateAlbumpaid")
	@ResponseBody
	public int updateAlbumpaid(HttpServletRequest request)throws ServletException{
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		String id=request.getParameter("albumid");
       	String[] ids=id.split(",");
		queryParams.put("update_user",request.getSession().getAttribute("username") );
		for (String i : ids) {
			queryParams.put("seriesCode", i);
			this.vodAlbuminfoStatus.updatepaid(queryParams);
		}
		return 1;		
	}
	
	/**
	 * 更改专辑状态
	 * @param id标识
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-更改专辑状态")
	@RequestMapping("/updateAlbumstatus")
	@ResponseBody
	public int updateAlbumstatus( HttpServletRequest request)throws ServletException{
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		String id=request.getParameter("albumid");
       	String[] ids=id.split(",");
		queryParams.put("update_user",request.getSession().getAttribute("username") );
		for (String i : ids) {
			queryParams.put("seriesCode", i);
			this.vodAlbuminfoStatus.updatestatus(queryParams);
		}
		return 1;		
	}
	
}


