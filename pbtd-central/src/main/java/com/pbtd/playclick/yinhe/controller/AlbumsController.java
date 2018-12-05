package com.pbtd.playclick.yinhe.controller;

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
import com.pbtd.playclick.integrate.service.face.IStrategyService;
import com.pbtd.playclick.page.PageResult;
import com.pbtd.playclick.page.QueryObject;
import com.pbtd.playclick.yinhe.domain.Albums;
import com.pbtd.playclick.yinhe.domain.AlbumsWithBLOBs;
import com.pbtd.playclick.yinhe.domain.Gitvvideo;
import com.pbtd.playclick.yinhe.service.IAlbumsService;

 

@Controller("yinhe.AlbumsController")
@RequestMapping("/yinhe/albums")
public class AlbumsController {
	
	@Autowired
	private IAlbumsService albumsService;
	
	  @Autowired
	    private IStrategyService strategyService;
	  
	 @RequestMapping(value = {"/index"})
	    public String index() {
	    	return "/yinhe/Yhalbums/index";
	    }
	 
	  @ResponseBody
	    @RequestMapping("/page")
	    public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
	    	List<Albums> data = new ArrayList<Albums>();
	    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
	        int total = this.albumsService.count(queryParams);
			if (total == 0) {
				return new PageResult(total, Collections.EMPTY_LIST);
			}
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(limit);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
				data =this.albumsService.find(queryParams);
				return new PageResult(total, data);
	    }
	  
	    /**
	     * GET 编辑
	     *
	     * @param id 标识
	     * @return 视图路径
	     */
	    @RequestMapping("/show/{id}")
	    public String edit(@PathVariable("id") String id,ModelMap model,HttpServletRequest request) {
	    	model.addAttribute("id",id);
	    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
	    		queryParams.put("parentId", id);
	    		List<Gitvvideo> gitlist=albumsService.findGitvvideo(queryParams);
	    		model.addAttribute("gitlist",gitlist);
	        return "/yinhe/Yhalbums/show";
	    }
	    
	    @ResponseBody
	    @RequestMapping("/load/{id}")
	    public AlbumsWithBLOBs load(@PathVariable("id") String id) {
	    	AlbumsWithBLOBs albus = null;
	        if (!id.equals("") && id !=null ) {
	        	albus = this.albumsService.load(id);
	        } 
	        else {
	        	albus  =null;
	        }
	        return albus;
	    }
	    @RequestMapping("/showvideo/{playOrder}/{tvId}/{parentId}")
	    public String video(@PathVariable("playOrder") String playOrder,
	    		@PathVariable("tvId") String tvId,
	    		@PathVariable("parentId") String parentId,
	    		ModelMap model,HttpServletRequest request) {
	    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    		queryParams.put("parentId", parentId);
    		queryParams.put("tvId", tvId);
    		queryParams.put("playOrder", playOrder);
    		List<Gitvvideo> gitvideomap=albumsService.findGitvvideo(queryParams);
    		model.addAttribute("map",gitvideomap.get(0));
	        return "/yinhe/Yhalbums/showvideo";
	    }
	    
	    
}
