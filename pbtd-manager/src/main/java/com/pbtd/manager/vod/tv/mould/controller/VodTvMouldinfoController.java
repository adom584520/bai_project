package com.pbtd.manager.vod.tv.mould.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.vod.common.mould.domain.vodMasterplate;
import com.pbtd.manager.vod.common.mould.service.face.IvodMasterplateInterface;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.tv.common.domain.VodTvchannel;
import com.pbtd.manager.vod.tv.common.service.face.IVodTvChannelService;
import com.pbtd.manager.vod.tv.mould.domain.VodTvModule;
import com.pbtd.manager.vod.tv.mould.service.face.IvodTvModuleInterface;


@Controller 
@RequestMapping("/vod/tv/vodmouldinfo")
public class VodTvMouldinfoController {			


	@Autowired
	private IvodTvModuleInterface   vodTvModuleInterface;
	@Autowired
	private IVodTvChannelService   IVodTvChannelService;
	@Autowired
	private IvodMasterplateInterface  vodTvMasterplateInterface;
	//@Autowired
	//private ITvOnlineService tvOnlinService;



	/**
	 * 首次进入
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/index", ""})
	public String index(Model model) {
		Map<String, Object> params=new HashMap<>();
		List<Map<String, Object>> items=vodTvModuleInterface.find(params);
		JSONArray array = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> info=new HashMap<>();//子集map
		Map<String, Object> map=new HashMap<>();//父级map
		for (int j = 0;j < items.size(); j++) {
			String flag="0";//父级更新标记 默认为0
			map = items.get(j);
			String mapid=map.get("id").toString();
			if(map.get("pid").toString().equals("0")){//只遍历父级数据
				for (int i = 0; i < items.size(); i++) {
					info = items.get(i);
					String pid=info.get("pid").toString();
					//只遍历子集数据 且子集数据归属于上一父级
					if(mapid.equals(pid)){
						jsonObject = new JSONObject();
						jsonObject.put("id", Long.parseLong(info.get("id").toString()));
						jsonObject.put("pId",  Long.parseLong(info.get("pid").toString()));
						jsonObject.put("name", info.get("name"));
						String open=info.get("open").toString();
						jsonObject.put("open", open.equals("1")?true:false);
						jsonObject.put("type", "tv");
						String infoflag= info.get("flag").toString();
						jsonObject.put("flag", infoflag);
						jsonObject.put("sequence", info.get("sequence"));
						if(infoflag.equals("1")){//如果子集有更新标记  更改父级更新标记
							flag="1";
						}
						array.add(jsonObject);
					}
				}
				jsonObject = new JSONObject();
				jsonObject.put("id", Long.parseLong(map.get("id").toString()));
				jsonObject.put("pId",  Long.parseLong(map.get("pid").toString()));
				jsonObject.put("name", map.get("name"));
				String open=map.get("open").toString();
				jsonObject.put("open", open.equals("1")?true:false);
				jsonObject.put("type", "tv");
				jsonObject.put("flag", flag);
				jsonObject.put("sequence", map.get("sequence"));
				array.add(jsonObject);
			}
		}
		model.addAttribute("modlulist", array.toString());
		return "/vod/tv/mould/index";
	}


	/**
	 * 返回树 
	 * @return
	 */
	@RequestMapping(value="/getModuleList")
	@ResponseBody
	public String getModuleList(){
		Map<String, Object> params=new HashMap<>();
		List<Map<String, Object>> items=vodTvModuleInterface.find(params);
		JSONArray array = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> info=new HashMap<>();//子集map
		Map<String, Object> map=new HashMap<>();//父级map
		for (int j = 0;j < items.size(); j++) {
			String flag="0";//父级更新标记 默认为0
			map = items.get(j);
			String mapid=map.get("id").toString();
			if(map.get("pid").toString().equals("0")){//只遍历父级数据
				for (int i = 0; i < items.size(); i++) {
					info = items.get(i);
					String pid=info.get("pid").toString();
					//只遍历子集数据 且子集数据归属于上一父级
					if(mapid.equals(pid)){
						jsonObject = new JSONObject();
						jsonObject.put("id", Long.parseLong(info.get("id").toString()));
						jsonObject.put("pId",  Long.parseLong(info.get("pid").toString()));
						jsonObject.put("name", info.get("name"));
						String open=info.get("open").toString();
						jsonObject.put("open", open.equals("1")?true:false);
						jsonObject.put("type", "tv");
						String infoflag= info.get("flag").toString();
						jsonObject.put("flag", infoflag);
						jsonObject.put("sequence", info.get("sequence"));
						if(infoflag.equals("1")){//如果子集有更新标记  更改父级更新标记
							flag="1";
						}
						array.add(jsonObject);
					}
				}
				jsonObject = new JSONObject();
				jsonObject.put("id", Long.parseLong(map.get("id").toString()));
				jsonObject.put("pId",  Long.parseLong(map.get("pid").toString()));
				jsonObject.put("name", map.get("name"));
				String open=map.get("open").toString();
				jsonObject.put("open", open.equals("1")?true:false);
				jsonObject.put("type", "tv");
				jsonObject.put("flag", flag);
				jsonObject.put("sequence", map.get("sequence"));
				array.add(jsonObject);
			}
		}
		return array.toString();
	}

	/**
	 * 新增模块
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/addmould/{curid}"})
	public String AddFile(Model model,@PathVariable("curid") String curid) {
		model.addAttribute("channel",curid);
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("moduleId", curid);
		VodTvModule vodTvModule = vodTvModuleInterface.load(queryParams);
		if(vodTvModule != null){
			model.addAttribute("Describes",vodTvModule.getDescribes());
			model.addAttribute("Masterplateid",vodTvModule.getMasterplateid());
			model.addAttribute("Name",vodTvModule.getName());
			model.addAttribute("Sequence",vodTvModule.getSequence());
			model.addAttribute("channel",vodTvModule.getChannel());
			model.addAttribute("moduleId",vodTvModule.getModuleid());
		}
		List<vodMasterplate> data  = vodTvMasterplateInterface.find(queryParams);
		model.addAttribute("masterplatelist",data);
		return "/vod/tv/mould/addmouldPage";
	}
	/**
	 * 首次加载 预览
	 */
	@RequestMapping(value = "/yulanmouldforfirst/{code}")
	public String yulanmouldforfirst(Model model,@PathVariable("code") String code) {
		model.addAttribute("type","tv");
		if(code != null && "1".equals(code) ){
			model.addAttribute("urlStr","http://192.168.0.166:8081/tv/vod/module/");
			model.addAttribute("channel",1147304005);
			model.addAttribute("zhanshi","本地");
		}else if(code != null && "2".equals(code) ){
			model.addAttribute("urlStr","http://192.168.0.220:8083/tv/vod/module/");
			model.addAttribute("channel",1147304005);
			model.addAttribute("zhanshi","线上");
		}
		VodTvchannel vodTvchannel =IVodTvChannelService.loadone();
		//	model.addAttribute("channel",vodTvchannel.getChannelCode());
		model.addAttribute("moduleSequence",null);
		return "/vod/bossModule/index"+code;
	}

	/**
	 * 根据频道 加载 预览
	 */
	@RequestMapping(value = {"/yulanmould/{code}/{channel}/{channelname}"})
	public String yulanmould(Model model,@PathVariable("code") String code,@PathVariable("channel") String channel,@PathVariable("channelname") String channelname) {
		model.addAttribute("type","tv");
		model.addAttribute("channel",channel);
		if(channel == null || channel.isEmpty()){
			channel = String.valueOf(IVodTvChannelService.loadone().getChannelCode());
		}
		if(channelname == null || channelname.isEmpty()){
			channelname =IVodTvChannelService.loadbychannel(channel).getChannelName();
		}
		model.addAttribute("channelname",channelname);
		if(code != null && "1".equals(code) ){
			model.addAttribute("urlStr","http://192.168.0.35:8080/tv/vod/module/");
			model.addAttribute("channel",channel);
			model.addAttribute("zhanshi","本地");
		}else if(code != null && "2".equals(code) ){
			model.addAttribute("urlStr","http://192.168.0.220:8083/tv/vod/module/");
			model.addAttribute("channel",channel);
			model.addAttribute("zhanshi","线上");

		}
		return  "/vod/bossModule/index"+code;
	}
	/**
	 * 根据模块id加载 预览的位置
	 */
	@RequestMapping(value = {"/yulanmouldbyid/{code}/{moduleId}"})
	public String yulanmouldbyid(Model model,@PathVariable("code") String code,@PathVariable("moduleId") String moduleId) {
		model.addAttribute("type","tv");
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("moduleId", moduleId);
		VodTvModule vodTvModule = vodTvModuleInterface.load(queryParams);
		if(vodTvModule != null ){
			model.addAttribute("channel",vodTvModule.getChannel());
			model.addAttribute("channelname",vodTvModule.getName()+"模块");
			model.addAttribute("moduleSequence",vodTvModule.getSequence());
			if(code != null && "1".equals(code) ){
				model.addAttribute("urlStr","http://192.168.0.35:8080/tv/vod/module/");
				model.addAttribute("zhanshi","本地");
			}else if(code != null && "2".equals(code) ){
				model.addAttribute("urlStr","http://192.168.0.220:8083/tv/vod/module/");
				model.addAttribute("zhanshi","线上");
			}
		}
		return  "/vod/bossModule/index"+code;
	}

	/**
	 * 跳转模版页
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/getmaster/{curid}"})
	public String NewFile(Model model,@PathVariable("curid") String curid) {
		Map<String, Object> params=new HashMap<>();
		params.put("moduleId", curid);
		VodTvModule vodTvModule  = vodTvModuleInterface.load(params);
		model.addAttribute("curid",curid);
		model.addAttribute("type","tv");
		return "/vod/system/masterdemo/demo"+vodTvModule.getMasterplateid();
	}



	/**
	 *新增/编辑模版
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Map<String,Object> addmodule(HttpServletRequest request,Model model) throws ServletException { 
		Map<String,Object> jsonMap=new HashMap<String,Object>();
		Map<String,Object> param=new HashMap<String,Object>();
		String channel=request.getParameter("channel");
		param.put("channel",channel );
		String name=request.getParameter("name");
		String masterplateId=request.getParameter("masterplateId");
		String describes=request.getParameter("describes");
		String sequence=String.valueOf(vodTvModuleInterface.findMaxModuleSeq(param));
		String moduleId=request.getParameter("moduleId");
		VodTvModule vodTvModule = new VodTvModule(channel,name,masterplateId,describes,sequence,moduleId);
		try{
			if(moduleId == null || "".equals(moduleId)){
				vodTvModuleInterface.insert(vodTvModule);
				jsonMap.put("code", 1);
				jsonMap.put("message", "添加成功");
			}else{
				vodTvModuleInterface.update(vodTvModule);
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonMap.put("code", 1);
			jsonMap.put("message", "添加成功");
		}
		/*Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("moduleId", moduleId);
		queryParams.put("channel", vodTvModule.getChannel());
		queryParams.put("flag", 1);
		vodTvModuleInterface.updateflag(queryParams);*/

		return jsonMap;
	}

	/**
	 * 删除模块
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/deletemould/{curid}"})
	public String deletemould(Model model,@PathVariable("curid") String curid) {
		model.addAttribute("channel",curid);
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("moduleId", curid);
		VodTvModule vodTvModule = vodTvModuleInterface.load(queryParams);
		if(vodTvModule != null){
			vodTvModuleInterface.deletemodule(queryParams);
		}
		queryParams.put("channel", vodTvModule.getChannel());
		queryParams.put("flag", 1);
		vodTvModuleInterface.updateflag(queryParams);

		Map<String, Object> params=new HashMap<>();
		List<Map<String, Object>> items=vodTvModuleInterface.find(params);
		JSONArray array = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> info=new HashMap<>();
		for (int i = 0; i < items.size(); i++) {
			info = items.get(i);
			jsonObject = new JSONObject();
			jsonObject.put("id", Long.parseLong(info.get("id").toString()));
			jsonObject.put("pId",  Long.parseLong(info.get("pid").toString()));
			jsonObject.put("name", info.get("name"));
			String open=info.get("open").toString();
			jsonObject.put("open", open.equals("1")?true:false);
			jsonObject.put("type", "tv");
			array.add(jsonObject);
		}
		model.addAttribute("modlulist", array.toString());
		return "/vod/tv/mould/index";
	}

	/**
	 *跳转绑定专辑页面getalbumforh5
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/getalbum/{curid}/{num}"})
	public String updateUrl(Model model,@PathVariable("curid") String curid ,@PathVariable("num") int num,HttpServletResponse resp) {
		model.addAttribute("curid",curid);//模块id
		model.addAttribute("num",num);//模板位
		return "/vod/tv/mould/getalbum";
	}

	//	/**
	//	 *跳转绑定专辑页面getalbumforh5
	//	 * @return 视图路径
	//	 */
	//	@RequestMapping(value = {"/getalbumforh5/{curid}/{num}"})
	//	public String getalbumforh5(Model model,@PathVariable("curid") String curid ,@PathVariable("num") int num,HttpServletResponse resp) {
	//		
	//		model.addAttribute("curid",curid);//模块id
	//		model.addAttribute("num",num);//模板位
	//		return "/vod/tv/mould/getalbum";
	//	}

	/**
	 * 模糊获取符合查询条件的分页记录
	 * *跳转绑定专辑页面 查询
	 * @param request 查询参数
	 * @return 记录列表
	 */
	@ResponseBody
	@RequestMapping("/albuminfopage")
	public PageResult albuminfopage(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		int total = this.vodTvModuleInterface.countalbum(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.vodTvModuleInterface.pagealbum(queryParams);
		return new PageResult(total, data);

	}


	/**
	 * GET 创建
	 *添加绑定专辑信息
	 * @return 视图路径
	 */
	@RequestMapping("/addalbuminfo/{curid}/{num}")
	@ResponseBody
	public int addalbuminfo(@PathVariable("curid") String curid ,@PathVariable("num") int num ,HttpServletRequest request,Model model) {
		String albumid=request.getParameter("albumid")==null?"":request.getParameter("albumid");
		String[] albumidlist =albumid.split(",");
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("moduleId", curid);
		queryParams.put("masterplateNum", num);
		for (String s : albumidlist) {
			queryParams.put("seriesCode", s);
			int n=vodTvModuleInterface.countalbum(queryParams);
			if(n<1){
				vodTvModuleInterface.insertalbum(queryParams);
				queryParams.put("flag", 1);
				vodTvModuleInterface.updateflag(queryParams);
			}
		}
		return 1;
	}

	/**
	 *删除绑定专辑信息
	 * @return 视图路径
	 */
	@RequestMapping("/deletealbuminfo/{curid}/{num}")
	@ResponseBody
	public int deletealbuminfo(@PathVariable("curid") String curid ,@PathVariable("num") int num ,HttpServletRequest request,Model model) {
		String albumid=request.getParameter("albumid")==null?"":request.getParameter("albumid");
		String[] albumidlist =albumid.split(",");
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("moduleId", curid);
		queryParams.put("masterplateNum", num);
		for (String s : albumidlist) {
			queryParams.put("seriesCode", s);
			vodTvModuleInterface.deletealbuminfo(queryParams);
			queryParams.put("flag", 1);
			vodTvModuleInterface.updateflag(queryParams);
		}
		queryParams.put("flag", 1);
		vodTvModuleInterface.updateflag(queryParams);
		return 1;
	}
	/**
	 * 模块
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/synchro/{curid}"})
	public String synchro(Model model,@PathVariable("curid") String curid) {
		model.addAttribute("channel",curid);
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("moduleId", curid);
		VodTvModule vodTvModule = vodTvModuleInterface.load(queryParams);
		if(vodTvModule != null){
			model.addAttribute("Describes",vodTvModule.getDescribes());
			model.addAttribute("Masterplateid",vodTvModule.getMasterplateid());
			model.addAttribute("Name",vodTvModule.getName());
			model.addAttribute("Sequence",vodTvModule.getSequence());
			model.addAttribute("channel",vodTvModule.getChannel());
			model.addAttribute("moduleId",vodTvModule.getModuleid());
		}
		List<vodMasterplate> data  = vodTvMasterplateInterface.find(queryParams);
		model.addAttribute("masterplatelist",data);
		return "/vod/tv/mould/addmould";
	}

	@LogAnnotation(operationInfo = "点播-phone-栏目管理-更改栏目图片操作")
	@ResponseBody
	@RequestMapping(value = {"/editimg/{code}"})
	public int editimg(HttpServletRequest request,@PathVariable("code") String code) throws ServletException  {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		switch (code) {
		case "one":
			queryParams.put("imgurl1", queryParams.get("imgUrl"));
			break;
		case "two":
			queryParams.put("imgurl2", queryParams.get("imgUrl"));
			break;
		case "three":
			queryParams.put("imgurl3", queryParams.get("imgUrl"));
			break;
		default:
			queryParams.put("imgurl4", queryParams.get("imgUrl"));
			break;
		}
		vodTvModuleInterface.updateimg(queryParams);
		queryParams.put("flag", 1);
		vodTvModuleInterface.updateflag(queryParams);
		return 1;
	}


	@LogAnnotation(operationInfo = "点播-phone-栏目管理-更改栏目图片操作")
	@ResponseBody
	@RequestMapping(value = {"/shangxianalbuminfo"})
	public int shangxianalbuminfo(HttpServletRequest request) throws ServletException  {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.vodTvModuleInterface.updateshangxian(queryParams);
	}


	/**
	 * 更新模板顺序
	 * @param request
	 * @return
	 */
	@LogAnnotation(operationInfo = "点播-tv-更改排序")
	@RequestMapping(value="/updateMoudleSeq")
	@ResponseBody
	public Map<String,Object> updateMoudleSeq(HttpServletRequest request){
		String nodes=request.getParameter("nodes");
		String treeNodeArr=request.getParameter("treeNodeArr");
		String targetNodeArr=request.getParameter("targetNodeArr");
		Map<String,Object> jsonMap=new HashMap<String,Object>();
		try {
			this.vodTvModuleInterface.updateMoudleSeq(nodes,treeNodeArr,targetNodeArr);
			jsonMap.put("code", 1);
			jsonMap.put("message", "排序成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("code", 0);
			jsonMap.put("message", "排序失败");
		}
		return jsonMap;
	}


	/**
	 * 同步模块&绑定专辑信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/synMoudleInfo")
	@ResponseBody
	public Map<String,Object> synMoudleInfo(HttpServletRequest request){
		String moduleId=request.getParameter("curid");
		Map<String,Object> param=new HashMap<String,Object>();
		Map<String,Object> jsonMap=new HashMap<String,Object>();
		param.put("moduleId",moduleId );
		try {
			//tvOnlinService.resetSingleMoudle(param);
			jsonMap.put("code", 1);
			jsonMap.put("message", "同步成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("code", 0);
			jsonMap.put("message", "同步失败");
		}
		return jsonMap;
	}
	
	/**
	 * 删除某个模块
	 * @param moduleId
	 * @return
	 */
	@RequestMapping(value="deleteSingleModule")
	@ResponseBody
	public Map<String,Object> deleteSingleModule(@RequestParam("id") String moduleId){
		Map<String,Object> jsonMap=new HashMap<String,Object>();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("moduleId", moduleId);
		param.put("flag", 1);
		try{
			this.vodTvModuleInterface.updateflag(param); //标记修改
			this.vodTvModuleInterface.deletemodule(param);
			//this.tvOnlinService.resetSingleMoudle(param); //线上该条删除
			jsonMap.put("code", 1);
			jsonMap.put("message", "删除成功");
		}catch(Exception e){
			e.printStackTrace();
			jsonMap.put("code", 0);
			jsonMap.put("message", "删除失败");
		}
		return jsonMap;
	}
	
	/**
	 * 重命名单个模块
	 * @param moduleId
	 * @param name
	 * @return
	 */
	@RequestMapping(value="renameSingleModule")
	@ResponseBody
	public Map<String,Object> renameSingleModule(@RequestParam("id") String moduleId,@RequestParam("name") String name){
		Map<String,Object> jsonMap=new HashMap<String,Object>();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("moduleId", moduleId);
		VodTvModule vodTvModule=new VodTvModule();
		try{
			vodTvModule.setModuleid(Long.valueOf(moduleId));
			vodTvModule.setName(name);
			this.vodTvModuleInterface.update(vodTvModule);
			param.put("flag", 1);
			this.vodTvModuleInterface.updateflag(param); //标记修改
			//this.tvOnlinService.resetSingleMoudle(param);
			jsonMap.put("code", 1);
			jsonMap.put("message", "修改成功");
		}catch(Exception e){
			e.printStackTrace();
			jsonMap.put("code", 0);
			jsonMap.put("message", "修改失败");
		}
		return jsonMap;
	}
	
	

	/**
	 *跳转绑定专辑页面getalbumforh5
	 * @return 视图路径/pbtd-manager-hebei/src/main/webapp/WEB-INF/views/vod/bossModule
	 */
	@RequestMapping(value = {"/basePage"})
	public String basePage(HttpServletRequest request,Model model) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		if(queryParams != null ){
			for (Entry<String, Object> entry : queryParams.entrySet()) {  
				model.addAttribute(entry.getKey(),entry.getValue());
			}  
		}
		return "/vod/bossModule/basePage";
	}
	/**
	 *跳转绑定专辑页面getalbumforh5
	 * @return 视图路径/pbtd-manager-hebei/src/main/webapp/WEB-INF/views/vod/bossModule
	 */
	@RequestMapping(value = {"/getchannelflag"})
	@ResponseBody
	public Integer getchannelflag(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		System.out.println(queryParams.get("moduleId"));
		VodTvModule vodTvModule = vodTvModuleInterface.load(queryParams);
		if(vodTvModule != null ){
			return vodTvModule.getFlag();
		}
		return 0;
	}

}
