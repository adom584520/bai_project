package com.pbtd.manager.inject.tv.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.inject.page.controller.InjectTvOutPutController;
import com.pbtd.manager.inject.tv.service.face.IInjectTvZxService;
import com.pbtd.manager.util.RequestUtil;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;

@Controller
@RequestMapping("/inject/injectTvZx")
public class InjectTvZxController {
	
	private static final Logger log=Logger.getLogger(InjectTvZxController.class);
	
	@Autowired
	private IInjectTvZxService injectTvZxService; 
	@Autowired
	private InjectTvOutPutController injectTvOutPutController;
	
	@RequestMapping(value={"/index",""})
	public String index(){
		return "/inject/tv/zx/index";
	}
	
	//专辑分页
	@RequestMapping("/page")
	@ResponseBody
	public PageResult getAlbumGrid(HttpServletRequest request,@RequestParam(value="page") int page,@RequestParam(value="rows") int rows){
		PageResult pr=new PageResult();
		Map<String,Object> queryParams=RequestUtil.asMap(request, false);
		int total=this.injectTvZxService.countalbum(queryParams);
		if(total==0){
			pr.setTotal(0L);
			pr.setRows(Collections.EMPTY_LIST);
		}else{
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(rows);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
			List list=this.injectTvZxService.pagealbum(queryParams);
			pr.setTotal(Long.valueOf(total));
			pr.setRows(list);
		}
		return pr;
	}
	
	//优先级页面
	@RequestMapping("/showPriority")
	public String showPriority(HttpServletRequest request){
		String id=request.getParameter("id");
		request.setAttribute("id", id); //转交id
		return "/inject/tv/zx/showPriority";
	}
	
	//更新专辑优先级
	@RequestMapping("/updateAlbumPriority")
	@ResponseBody
	public int updateAlbumPriority(HttpServletRequest request){
		String id=request.getParameter("id");
		String zxPriority=request.getParameter("zxPriority");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("zxPriority", zxPriority);
		try{
			this.injectTvZxService.updateAlbumPriority(map);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	
	
	//更新专辑注入状态
		@RequestMapping("/updateAlbumInjectState")
		@ResponseBody
		public int updateAlbumInjectState(HttpServletRequest request){
			String id=request.getParameter("id");
			String zxInjectState=request.getParameter("zxInjectState");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", id);
			map.put("zxInjectState", zxInjectState);
			try{
				/**
				 * Todo 调用公共接口send to 注入平台 
				 * 查询具体剧集，每发送一条，标记一个状态
				 * 最终下面标记全部状态
				 */
				this.injectTvZxService.updateAlbumPriority(map);
				return 1;
			}catch(Exception e){
				e.printStackTrace();
				return -1;
			}
		}
	
	
	
	
	
	
	//跳转到剧集页面
	@RequestMapping("/videoIndex")
	public String videoIndex(HttpServletRequest request){
		String id=request.getParameter("seriesCode");
		request.setAttribute("seriesCode", id); //转交id
		return "/inject/tv/zx/videoIndex";
	}
	
	//专辑分页
		@RequestMapping("/videopage/{seriesCode}")
		@ResponseBody
		public PageResult getAlbumVideoGrid(@PathVariable("seriesCode") int seriesCode,HttpServletRequest request,@RequestParam(value="page") int page,@RequestParam(value="rows") int rows){
			System.out.println(seriesCode);
			PageResult pr=new PageResult();
			Map<String,Object> queryParams=RequestUtil.asMap(request, false);
			queryParams.put("seriesCode",seriesCode);
			int total=this.injectTvZxService.countalbumVideo(queryParams);
			if(total==0){
				pr.setTotal(0L);
				pr.setRows(Collections.EMPTY_LIST);
			}else{
				QueryObject qo=new QueryObject();
				qo.setPage(page);
				qo.setRows(rows);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				List list=this.injectTvZxService.pagealbumVideo(queryParams);
				pr.setTotal(Long.valueOf(total));
				pr.setRows(list);
			}
			return pr;
		}
	
		//优先级页面
		@RequestMapping("/showVideoPriority")
		public String showVideoPriority(HttpServletRequest request){
			String id=request.getParameter("id");
			request.setAttribute("id", id); //转交id
			return "/inject/tv/zx/showVideoPriority";
		}
		
		//更新专辑优先级
		@RequestMapping("/updateVideoPriority")
		@ResponseBody
		public int updateAlbumVideoPriority(HttpServletRequest request){
			String id=request.getParameter("id");
			String priority=request.getParameter("priority");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", id);
			map.put("zxPriority", priority);
			map.put("hwPriority", priority);
			try{
				this.injectTvZxService.updateAlbumVideoPriority(map);
				return 1;
			}catch(Exception e){
				e.printStackTrace();
				return -1;
			}
		}
		
		
		//发送剧集
		@RequestMapping("/SendOutAlbumVideo")
		@ResponseBody
		public int SendOutAlbumVideo(HttpServletRequest request){
			String id=request.getParameter("id");
			String vodId=request.getParameter("vodId");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", id);
			map.put("vodId", vodId);
			String ids=vodId;
			//发送zx,hw新数据
			try{
				this.injectTvOutPutController.tvInjectId(ids,null); 
				this.injectTvOutPutController.tvZxInjectId(ids, null);
				return 1;
			}catch(Exception e){
				log.error("发送指定剧集到注入平台异常",e);
				return -1;
			}
		}

}
