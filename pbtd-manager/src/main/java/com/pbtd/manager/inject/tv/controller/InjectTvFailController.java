package com.pbtd.manager.inject.tv.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.inject.tv.service.face.IInjectTvZxService;
import com.pbtd.manager.util.RequestUtil;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;

@Controller
@RequestMapping("/inject/injectTvFail")
public class InjectTvFailController {
	
	@Autowired
	private IInjectTvZxService injectTvZxService;     @RequestMapping(value={"/index",""})
	public String index(){
		return "/inject/tv/fail/index";
	}
	
	//专辑分页
		@RequestMapping("/page")
		@ResponseBody
		public PageResult getAlbumGrid(HttpServletRequest request,@RequestParam(value="page") int page,@RequestParam(value="rows") int rows){
			PageResult pr=new PageResult();
			Map<String,Object> queryParams=RequestUtil.asMap(request, false);
			queryParams.put("zxInjectState",-1); //失败
			queryParams.put("hwInjectState",-1); //失败
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
}
