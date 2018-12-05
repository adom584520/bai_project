package com.pbtd.manager.inject.phone.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.inject.page.controller.InjectPhoneOutPutController;
import com.pbtd.manager.inject.phone.service.face.IInjectPhoneZxService;
import com.pbtd.manager.util.RequestUtil;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;

@Controller
@RequestMapping("/inject/injectPhoneFail")
public class InjectPhoneFailController {
	
	private static final Logger log=Logger.getLogger(InjectPhoneFailController.class);
	
	@Autowired
	private IInjectPhoneZxService injectPhoneZxService; 
	@Autowired
	private InjectPhoneOutPutController injectPhoneOutPutController;
	
	@RequestMapping(value={"/index",""})
	public String index(){
		return "/inject/phone/fail/index";
	}
	
	//专辑分页
		@RequestMapping("/page")
		@ResponseBody
		public PageResult getAlbumGrid(HttpServletRequest request,@RequestParam(value="page") int page,@RequestParam(value="rows") int rows){
			PageResult pr=new PageResult();
			Map<String,Object> queryParams=RequestUtil.asMap(request, false);
			queryParams.put("zxInjectState",-1); //失败
			queryParams.put("hwInjectState",-1); //失败
			int total=this.injectPhoneZxService.countalbumVideo(queryParams);
			if(total==0){
				pr.setTotal(0L);
				pr.setRows(Collections.EMPTY_LIST);
			}else{
				QueryObject qo=new QueryObject();
				qo.setPage(page);
				qo.setRows(rows);
				queryParams.put("start", qo.getStart());
				queryParams.put("pageSize", qo.getPageSize());
				List list=this.injectPhoneZxService.pagealbumVideo(queryParams);
				pr.setTotal(Long.valueOf(total));
				pr.setRows(list);
			}
			return pr;
		}
		
		//优先级页面
		@RequestMapping("/showVideoPriority")
		public String showVideoPriority(HttpServletRequest request){
			String id=request.getParameter("id");
			String type=request.getParameter("type");
			request.setAttribute("id", id); //转交id
			request.setAttribute("type", type);  //区分zx,hw
			return "/inject/phone/fail/showVideoPriority";
		}
		
		@RequestMapping("/updateSingleVideoPriority")
		@ResponseBody
		public int updateSingleVideoPriority(HttpServletRequest request){
			String id=request.getParameter("id");
			String type=request.getParameter("type");
			String priority=request.getParameter("priority");
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", id);
			try{
				if(type!=null && !"".equals(type)){
					if("zx".equals(type)){
						map.put("zxPriority", priority);						 
						this.injectPhoneZxService.updateZxAlbumVideoPriority(map); 
						
					}else if("hw".equals(type)){
						map.put("hwPriority", priority);
						this.injectPhoneZxService.updateHwAlbumVideoPriority(map);
					}
				}
				return 1;
			}catch(Exception e){
				return -1;
			}
		}
		
		//发送剧集
		@RequestMapping("/SendOutAlbumVideo")
		@ResponseBody
		public int SendOutAlbumVideo(HttpServletRequest request){
			String id=request.getParameter("id");
			String vodId=request.getParameter("vodId");
			String type=request.getParameter("type");
			Map<String,Object> map=new HashMap<String,Object>();
			Map<String,Object> mapState=new HashMap<String,Object>();
			map.put("id", id);
			map.put("vodId", vodId);
			String ids=vodId;
			
			mapState.put("id",id);
			//发送zx,hw新数据
			try{
				if(type!=null && !"".equals(type)){
					if("hw".equals(type)){
						mapState.put("hwInjectState",0); //重置为0
						this.injectPhoneZxService.updateAlbumVideoHwInjectState(mapState);
						
						this.injectPhoneOutPutController.phoneInjectId(ids,null);
						mapState.put("hwInjectState",3); //已发送
						this.injectPhoneZxService.updateAlbumVideoHwInjectState(mapState); //已发送
						
					}else if("zx".equals(type)){
						mapState.put("hwInjectState",0); //重置为0
						this.injectPhoneZxService.updateAlbumVideoHwInjectState(mapState);
						
						this.injectPhoneOutPutController.phoneZxInjectId(ids, null);
						mapState.put("zxInjectState",3); //已发送
						this.injectPhoneZxService.updateAlbumVideoZxInjectState(mapState); //已发送
					}
				}
				return 1;
			}catch(Exception e){
				log.error("发送指定剧集到注入平台异常",e);
				return -1;
			}
		}
		
		
}
