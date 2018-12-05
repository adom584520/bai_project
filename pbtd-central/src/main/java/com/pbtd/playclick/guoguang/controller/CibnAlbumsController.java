package com.pbtd.playclick.guoguang.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playclick.base.common.web.RequestUtil;
import com.pbtd.playclick.guoguang.domain.GgAlbums;
import com.pbtd.playclick.guoguang.domain.GgAlbumsinfo;
import com.pbtd.playclick.guoguang.domain.GgAlbumsinfovideo;
import com.pbtd.playclick.guoguang.service.ICibnAlbumservice;
import com.pbtd.playclick.page.PageResult;
import com.pbtd.playclick.page.QueryObject;
import com.pbtd.playclick.yinhe.domain.Albums;
import com.pbtd.playclick.yinhe.domain.AlbumsWithBLOBs;
import com.pbtd.playclick.yinhe.domain.Gitvvideo;
import com.pbtd.playclick.yinhe.service.IAlbumsService;

 

@Controller("cibn.CibnAlbumsController")
@RequestMapping("/cibn/albums")
public class CibnAlbumsController {
	
	@Autowired
	private ICibnAlbumservice cibnAlbumservice;
	
	 @RequestMapping(value = {"/index"})
	    public String index() {
	    	return "/cibn/albumsinfo/index";
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
	    	List<GgAlbumsinfo> data = new ArrayList<GgAlbumsinfo>();
	    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
	        int total = this.cibnAlbumservice.count(queryParams);
			if (total == 0) {
				return new PageResult(total, Collections.EMPTY_LIST);
			}
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(limit);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
				data =this.cibnAlbumservice.find(queryParams);
				return new PageResult(total, data);
	    }
	    
	    @RequestMapping("/show/{id}")
	    public String edit(@PathVariable("id") String id,ModelMap model,HttpServletRequest request) {
	    	model.addAttribute("id",id);
	    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    		queryParams.put("code", id);
    		List<GgAlbumsinfovideo> gitlist=cibnAlbumservice.findAlbumsinfovideo(queryParams);
    		model.addAttribute("gitlist",gitlist);
	        return "/cibn/albumsinfo/show";
	    }
	    
	    @ResponseBody
	    @RequestMapping("/load/{id}")
	    public GgAlbumsinfo load(@PathVariable("id") String id,HttpServletRequest request) {
	    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
	    	queryParams.put("code", id);
	    	GgAlbumsinfo albus = null;
	        if (!"".equals(id) && id !=null ) {
	        	albus = this.cibnAlbumservice.load(queryParams);
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
    		queryParams.put("id", id);
    		List<GgAlbumsinfovideo> gitvideomap=cibnAlbumservice.findAlbumsinfovideo(queryParams);
    		model.addAttribute("map",gitvideomap.get(0));
	        return "/cibn/albumsinfo/showvideo";
	    }
}
