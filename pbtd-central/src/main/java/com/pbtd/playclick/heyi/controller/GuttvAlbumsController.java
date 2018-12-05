package com.pbtd.playclick.heyi.controller;

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
import com.pbtd.playclick.heyi.domain.GuttvAlbuminfo;
import com.pbtd.playclick.heyi.service.IGuttvAlbumservice;
import com.pbtd.playclick.page.PageResult;
import com.pbtd.playclick.page.QueryObject;

 

@Controller("guttv.GuttvAlbumsController")
@RequestMapping("/guttv/albums")
public class GuttvAlbumsController {
	
	@Autowired
	private IGuttvAlbumservice guttvAlbumservice;
	
	 @RequestMapping(value = {"/index"})
	    public String index() {
	    	return "/guttv/albumsinfo/index";
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
	    	List<GuttvAlbuminfo> data = new ArrayList<GuttvAlbuminfo>();
	    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
	        int total = this.guttvAlbumservice.count(queryParams);
			if (total == 0) {
				return new PageResult(total, Collections.EMPTY_LIST);
			}
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(limit);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
				data =this.guttvAlbumservice.page(queryParams);
				return new PageResult(total, data);
	    }
	    
	    @RequestMapping("/show/{id}")
	    public String edit(@PathVariable("id") String id,ModelMap model,HttpServletRequest request) {
	    	model.addAttribute("id",id);
	    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    		queryParams.put("code", id);
    		List<Map<String, Object>> gitlist=guttvAlbumservice.findAlbumsinfovideo(queryParams);
    		model.addAttribute("gitlist",gitlist);
	        return "/guttv/albumsinfo/show";
	    }
	    
	    @ResponseBody
	    @RequestMapping("/load/{id}")
	    public GuttvAlbuminfo load(@PathVariable("id") String id,HttpServletRequest request) {
	    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
	    	queryParams.put("seriesCode", id);
	    	GuttvAlbuminfo albus = null;
	        if (!"".equals(id) && id !=null ) {
	        	albus = this.guttvAlbumservice.load(queryParams);
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
    		List<Map<String, Object>> gitvideomap=guttvAlbumservice.findAlbumsinfovideo(queryParams);
    		model.addAttribute("map",gitvideomap.get(0));
	        return "/guttv/albumsinfo/showvideo";
	    }
}
