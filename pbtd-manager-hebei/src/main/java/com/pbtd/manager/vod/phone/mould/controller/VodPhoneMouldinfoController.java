package com.pbtd.manager.vod.phone.mould.controller;


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
import com.pbtd.manager.conf.VodInterfaceBeanConfig;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.SequenceUtil;
import com.pbtd.manager.vod.common.mould.domain.vodMasterplate;
import com.pbtd.manager.vod.common.mould.service.face.IvodMasterplateInterface;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.phone.common.domain.Vodchannel;
import com.pbtd.manager.vod.phone.common.service.face.IVodChannelService;
import com.pbtd.manager.vod.phone.mould.domain.VodPhoneModule;
import com.pbtd.manager.vod.phone.mould.service.face.IVodPhoneLinkAlbumListInterface;
import com.pbtd.manager.vod.phone.mould.service.face.IVodPhoneModuleInterface;
import com.pbtd.manager.vodOnlinelibrary.service.face.IPhoneOnlineService;


@Controller 
@RequestMapping("/vod/phone/vodmouldinfo")
public class VodPhoneMouldinfoController {			

	@Autowired
	private IVodPhoneModuleInterface   vodPhoneModuleInterface;
	@Autowired
	private IVodPhoneLinkAlbumListInterface   vodPhoneLinkAlbumListInterface;
	@Autowired
	private IVodChannelService   vodChannelService;
	@Autowired
	private IvodMasterplateInterface  vodMasterplateInterface;
	@Autowired
	private IPhoneOnlineService phoneOnlinService;
	@Autowired
	private SequenceUtil  sequenceUtil;
	@Autowired
	private IDictionaryService dictionaryService;
	/**
	 * 首次进入
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/index", ""})
	public String index(Model model) {
		Map<String, Object> params=new HashMap<>();
		List<Map<String, Object>> items=vodPhoneModuleInterface.find(params);
		JSONArray array = new JSONArray();
		Map<String, Object> info=new HashMap<>();
		for (int j = 0;j < items.size(); j++) {
			info = items.get(j);
			String pid = info.get("pid").toString();
			JSONObject	jsonObject = new JSONObject();
			if(pid != null && "0".equals(pid)){
				jsonObject.put("id", "c_"+Long.parseLong(info.get("id").toString()));
				jsonObject.put("pId",  Long.parseLong(info.get("pid").toString()));	
			}else{
				jsonObject.put("id", ""+Long.parseLong(info.get("id").toString()));
				jsonObject.put("pId","c_"+Long.parseLong(info.get("pid").toString()));
			}
			jsonObject.put("name", info.get("name"));
			String open=info.get("open").toString();
			jsonObject.put("open", open.equals("1")?true:false);
			jsonObject.put("type", "phone");
			jsonObject.put("flag", info.get("flag"));
			jsonObject.put("sequence", info.get("sequence"));
			jsonObject.put("status",  info.get("status"));
			array.add(jsonObject);
		}
		//System.out.println(array.toString());
		model.addAttribute("modlulist", array.toString());
		return "/vod/phone/mould/index";
	}


	/**
	 * 返回树 
	 * @return
	 */
	@RequestMapping(value="/getModuleList")
	@ResponseBody
	public String getModuleList(){
		Map<String, Object> params=new HashMap<>();
		List<Map<String, Object>> items=vodPhoneModuleInterface.find(params);
		JSONArray array = new JSONArray();
		Map<String, Object> info=new HashMap<>();
		for (int j = 0;j < items.size(); j++) {
			info = items.get(j);
			String pid = info.get("pid").toString();
			JSONObject	jsonObject = new JSONObject();
			if(pid != null && "0".equals(pid)){
				jsonObject.put("id", "c_"+Long.parseLong(info.get("id").toString()));
				jsonObject.put("pId",  Long.parseLong(info.get("pid").toString()));
			}else{
				jsonObject.put("id", ""+Long.parseLong(info.get("id").toString()));
				jsonObject.put("pId","c_"+Long.parseLong(info.get("pid").toString()));
			}
			jsonObject.put("name", info.get("name"));
			String open=info.get("open").toString();
			jsonObject.put("open", open.equals("1")?true:false);
			jsonObject.put("type", "phone");
			jsonObject.put("flag", info.get("flag"));
			jsonObject.put("sequence", info.get("sequence"));
			jsonObject.put("status",  info.get("status"));
			array.add(jsonObject);
		}
		return array.toString();
	}

	/**
	 * 新增模块
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/addmould/{curid}"})
	public String AddFile(Model model,@PathVariable("curid") String channel) {
		model.addAttribute("channel",channel.substring(channel.indexOf("_")+1,channel.length()));
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("moduleId", channel.substring(channel.indexOf("_")+1,channel.length()));
		List<vodMasterplate> data  = vodMasterplateInterface.find(queryParams);
		model.addAttribute("masterplatelist",data);
		List<Map<String, Object>> labellist  =  dictionaryService.findDictionaryMap(queryParams, "chooselabel");
		model.addAttribute("linklabelllist",labellist);
		queryParams.put("levels", 1);
		List<Map<String, Object>> channellist  = dictionaryService.findDictionaryMap(queryParams, "choosechannel");
		model.addAttribute("linkchannellist",channellist);
		List<Map<String, Object>> speciallist = dictionaryService.findDictionaryMap(queryParams, "choosespecialtype");
		model.addAttribute("speciallist",speciallist);
		return "/vod/phone/mould/addmould";
	}

	/**
	 * 编辑模块
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/updatemould/{curid}"})
	public String updateFile(Model model,@PathVariable("curid") String curid) {
		model.addAttribute("channel",curid);
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("moduleId", curid);
		VodPhoneModule vodPhoneModule = vodPhoneModuleInterface.load(queryParams);
		if(vodPhoneModule != null){
			model.addAttribute("Describes",vodPhoneModule.getDescribes());
			model.addAttribute("masterplateid",vodPhoneModule.getMasterplateid());
			model.addAttribute("Name",vodPhoneModule.getName());
			model.addAttribute("Sequence",vodPhoneModule.getSequence());
			model.addAttribute("channel",vodPhoneModule.getChannel());
			model.addAttribute("moduleId",vodPhoneModule.getModuleid());
			model.addAttribute("modulepic",vodPhoneModule.getModulepic() );
			model.addAttribute("picstatus", vodPhoneModule.getPicstatus());
			model.addAttribute("modulestatus", vodPhoneModule.getModulestatus());
			model.addAttribute("isshowleft", vodPhoneModule.getIsshowleft());
			model.addAttribute("isshowright", vodPhoneModule.getIsshowright());
			model.addAttribute("linkchannel",vodPhoneModule.getLinkchannel());
			model.addAttribute("linklabel",vodPhoneModule.getLinklabel());
			model.addAttribute("linkstatus",vodPhoneModule.getLinkstatus());
			model.addAttribute("linktype",vodPhoneModule.getLinktype());
			model.addAttribute("picture",vodPhoneModule.getPicture());
			model.addAttribute("picturestatus",vodPhoneModule.getPicturestatus());
			model.addAttribute("modulelinktype",vodPhoneModule.getModulelinktype());
			model.addAttribute("modulelinkchannel",vodPhoneModule.getModulelinkchannel());
			model.addAttribute("modulelinkspecial",vodPhoneModule.getModulelinkspecial());
			model.addAttribute("modulelinkurl",vodPhoneModule.getModulelinkurl());
			model.addAttribute("moduleviewpoint",vodPhoneModule.getModuleviewpoint());
			model.addAttribute("viewpointstatus",vodPhoneModule.getViewpointstatus());
			model.addAttribute("textpicstatus",vodPhoneModule.getTextpicstatus());
			model.addAttribute("textrecommendpic",vodPhoneModule.getTextrecommendpic());
		}
		List<vodMasterplate> data  = vodMasterplateInterface.find(queryParams);
		model.addAttribute("masterplatelist",data);
		queryParams.put("levels", 1);
		List<Map<String, Object>> channellist  = dictionaryService.findDictionaryMap(queryParams, "choosechannel");
		model.addAttribute("linkchannellist",channellist);
		List<Map<String, Object>> speciallist = dictionaryService.findDictionaryMap(queryParams, "choosespecialtype");
		model.addAttribute("speciallist",speciallist);
		queryParams.put("channelCode", vodPhoneModule.getLinkchannel());
		List<Map<String, Object>> labellist  =  dictionaryService.findDictionaryMap(queryParams, "chooselabel");
		model.addAttribute("linklabelllist",labellist);
		return "/vod/phone/mould/addmouldPage";
	}

/*	
	 * 数据字典下拉框方式展示
	 * @param obj 数据库对象名例如T_ZXBZ_ZZMM,则obj为Zzmc
	 * @param request
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value="/getlablesforchannel")
	public List<Object> getlablesforchannel(){
		List<Object> list = new ArrayList<>();
		for (int j = 0; j < 3; j++) {
			List citylist  = new ArrayList<>(); 
			for (int i = 0; i < 10; i++) {
				City city = new City("北京"+j+i,"1000"+j+i);
				citylist.add(city);
			}
			Province Province = new Province("中国"+j,citylist);
			list.add(Province);
		}
		return list;
	}*/


	/**
	 * 首次加载 预览
	 */
	@RequestMapping(value = "/yulanmouldforfirst/{code}")
	public String yulanmouldforfirst(Model model,@PathVariable("code") String code) {
		model.addAttribute("type","phone");
		if(code != null && "1".equals(code) ){
			model.addAttribute("urlStr",VodInterfaceBeanConfig.LOCALHOST_URL+"/phone/vod/module/");
			model.addAttribute("channel",1525982489);
			model.addAttribute("zhanshi","本地");
		}else if(code != null && "2".equals(code) ){
			model.addAttribute("urlStr",VodInterfaceBeanConfig.PHONE_ONLINE_URL+"/phone/vod/module/");
			model.addAttribute("channel",1525982489);
			model.addAttribute("zhanshi","线上");
		}
		Vodchannel vodchannel =vodChannelService.loadone();
		model.addAttribute("moduleSequence",null);
		return "/vod/bossModule/indexPhone"+code;
	}

	/**
	 * 根据频道 加载 预览
	 */
	@RequestMapping(value = {"/yulanmould/{code}/{channel}/{channelname}"})
	public String yulanmould(HttpServletRequest request,Model model,@PathVariable("code") String code,@PathVariable("channel") String channel,@PathVariable("channelname") String channelname) {
		model.addAttribute("type","phone");
		channel = channel.substring(channel.indexOf("_")+1,channel.length());
		model.addAttribute("channel",channel);
		if(channel == null || channel.isEmpty()){
			channel = String.valueOf(vodChannelService.loadone().getChannelCode());
		}
		if(channelname == null || channelname.isEmpty()){
			channelname =vodChannelService.loadbychannel(channel).getChannelName();
		}
		model.addAttribute("channelname",channelname);
		if(code != null && "1".equals(code) ){
			model.addAttribute("urlStr",VodInterfaceBeanConfig.LOCALHOST_URL+"/phone/vod/module/");
			model.addAttribute("channel",channel);
			model.addAttribute("zhanshi","本地");
		}else if(code != null && "2".equals(code) ){
			model.addAttribute("urlStr",VodInterfaceBeanConfig.PHONE_ONLINE_URL+"/phone/vod/module/");
			model.addAttribute("channel",channel);
			model.addAttribute("zhanshi","线上");
		}
		return  "/vod/bossModule/indexPhone"+code;
	}
	/**
	 * 根据模块id加载 预览的位置
	 */
	@RequestMapping(value = {"/yulanmouldbyid/{code}/{moduleId}"})
	public String yulanmouldbyid(HttpServletRequest request,Model model,@PathVariable("code") String code,@PathVariable("moduleId") String moduleId) {
		model.addAttribute("type","phone");
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("moduleId", moduleId);
		VodPhoneModule vodPhoenModule = vodPhoneModuleInterface.load(queryParams);
		if(vodPhoenModule != null ){
			model.addAttribute("channel",vodPhoenModule.getChannel());
			model.addAttribute("channelname",vodPhoenModule.getName()+"模块");
			model.addAttribute("moduleSequence",vodPhoenModule.getSequence());
			if(code != null && "1".equals(code) ){
				model.addAttribute("urlStr",VodInterfaceBeanConfig.LOCALHOST_URL+"/phone/vod/module/");
				model.addAttribute("zhanshi","本地");
			}else if(code != null && "2".equals(code) ){
				model.addAttribute("urlStr",VodInterfaceBeanConfig.PHONE_ONLINE_URL+"/phone/vod/module/");
				model.addAttribute("zhanshi","线上");
			}
		}
		return  "/vod/bossModule/indexPhone"+code;
	}

	/**
	 * 跳转模版页
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/getmaster/{curid}"})
	public String NewFile(Model model,@PathVariable("curid") String curid) {
		Map<String, Object> params=new HashMap<>();
		params.put("moduleId", curid);
		VodPhoneModule vodPhoenModule  = vodPhoneModuleInterface.load(params);
		model.addAttribute("curid",curid);
		model.addAttribute("type","tv");
		return "/vod/system/masterdemo/demo"+vodPhoenModule.getMasterplateid();
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
		channel = channel.substring(channel.indexOf("_")+1,channel.length());
		param.put("channel",channel );
		String name=request.getParameter("name");
		String masterplateId=request.getParameter("masterplateId");
		String describes=request.getParameter("describes");
		String moduleId=request.getParameter("moduleId");
		String modulepic=request.getParameter("modulepic");
		String picstatus=request.getParameter("picstatus");
		String textpicstatus=request.getParameter("textpicstatus");
		String modulestatus=request.getParameter("modulestatus");
		String isshowleft=request.getParameter("isshowleft");
		String isshowright=request.getParameter("isshowright");
		String textrecommendpic=request.getParameter("textrecommendpic");
		String picture=request.getParameter("picture");
		String picturestatus=request.getParameter("picturestatus");

		String linkstatus=request.getParameter("linkstatus");
		String linktype=request.getParameter("linktype");
		String linkchannel=request.getParameter("linkchannel");
		String linklabel=request.getParameter("linklabel");
		String modulelinktype = request.getParameter("modulelinktype");
		String modulelinkchannel   = request.getParameter("modulelinkchannel");
		String modulelinkspecial  = request.getParameter("modulelinkspecial");
		String modulelinkurl  = request.getParameter("modulelinkurl");
		String viewpointstatus = request.getParameter("viewpointstatus");
		String moduleviewpoint = request.getParameter("moduleviewpoint");

		VodPhoneModule vodPhoneModule = new VodPhoneModule(channel,name,masterplateId,describes,moduleId);
		vodPhoneModule.setModulepic(modulepic);
		vodPhoneModule.setPicture(picture);
		vodPhoneModule.setPicstatus(Integer.valueOf(picstatus));
		vodPhoneModule.setTextpicstatus((Integer.valueOf(textpicstatus)));
		vodPhoneModule.setPicturestatus((Integer.valueOf(picturestatus)));
		vodPhoneModule.setModulestatus(Integer.valueOf(modulestatus));
		vodPhoneModule.setIsshowleft(Integer.valueOf(isshowleft));
		vodPhoneModule.setIsshowright(Integer.valueOf(isshowright));
		vodPhoneModule.setLinkchannel(Long.valueOf(linkchannel));
		vodPhoneModule.setLinklabel(Long.valueOf(linklabel));
		vodPhoneModule.setLinktype(Integer.valueOf(linktype));
		vodPhoneModule.setLinkstatus(linkstatus);
		vodPhoneModule.setModulelinktype(Integer.valueOf(modulelinktype));
		vodPhoneModule.setModulelinkchannel(Long.valueOf(modulelinkchannel));
		vodPhoneModule.setModulelinkspecial(Long.valueOf(modulelinkspecial));
		vodPhoneModule.setModulelinkurl(modulelinkurl);
		vodPhoneModule.setViewpointstatus(Integer.valueOf(viewpointstatus));
		vodPhoneModule.setModuleviewpoint(moduleviewpoint);
		if(textrecommendpic != null){
			vodPhoneModule.setTextrecommendpic(textrecommendpic);;
		}
		try{
			if(moduleId == null || "".equals(moduleId)){
				//更改排序
				String sequence=String.valueOf(vodPhoneModuleInterface.findMaxModuleSeq(param));
				vodPhoneModule.setSequence(Integer.valueOf(sequence));
				moduleId = vodPhoneModuleInterface.insert(vodPhoneModule).toString();
				jsonMap.put("code", 1);
				jsonMap.put("message", "添加成功");

			}else{
				vodPhoneModuleInterface.update(vodPhoneModule);
				jsonMap.put("code", 1);
				jsonMap.put("message", "添加成功");
			}
			Map<String, Object> queryParams =new HashMap<>();
			queryParams.put("moduleId", moduleId);
			queryParams.put("channel", channel);
			queryParams.put("flag", 1);
			vodPhoneModuleInterface.updateflag(queryParams);
		}catch(Exception e){
			e.printStackTrace();
			jsonMap.put("code", 1);
			jsonMap.put("message", "添加成功");
		}
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
		VodPhoneModule vodPhoenModule = vodPhoneModuleInterface.load(queryParams);
		if(vodPhoenModule != null){
			vodPhoneModuleInterface.deletemodule(queryParams);
		}
		queryParams.put("channel", vodPhoenModule.getChannel());
		queryParams.put("flag", 1);
		vodPhoneModuleInterface.updateflag(queryParams);

		Map<String, Object> params=new HashMap<>();
		List<Map<String, Object>> items=vodPhoneModuleInterface.find(params);
		JSONArray array = new JSONArray();
		Map<String, Object> info=new HashMap<>();
		for (int j = 0;j < items.size(); j++) {
			info = items.get(j);
			String pid = info.get("pid").toString();
			JSONObject	jsonObject = new JSONObject();
			if(pid != null && "0".equals(pid)){
				jsonObject.put("id", "c_"+Long.parseLong(info.get("id").toString()));
				jsonObject.put("pId",  Long.parseLong(info.get("pid").toString()));
			}else{
				jsonObject.put("id", ""+Long.parseLong(info.get("id").toString()));
				jsonObject.put("pId","c_"+Long.parseLong(info.get("pid").toString()));
			}
			jsonObject.put("name", info.get("name"));
			String open=info.get("open").toString();
			jsonObject.put("open", open.equals("1")?true:false);
			jsonObject.put("type", "phone");
			jsonObject.put("flag", info.get("flag"));
			jsonObject.put("sequence", info.get("sequence"));
			jsonObject.put("status",  info.get("status"));
			array.add(jsonObject);
		}
		model.addAttribute("modlulist", array.toString());
		return "/vod/phone/mould/index";
	}

	/**
	 *跳转绑定专辑页面getalbumforh5
	 * @return 视图路径
	 */
	@RequestMapping(value = {"/getalbum/{curid}/{num}"})
	public String updateUrl(Model model,@PathVariable("curid") String curid ,@PathVariable("num") int num,HttpServletResponse resp) {
		model.addAttribute("curid",curid);//模块id
		model.addAttribute("num",num);//模板位
		return "/vod/phone/mould/getalbum";
	}

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
		int total = this.vodPhoneModuleInterface.countalbum(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.vodPhoneModuleInterface.pagealbum(queryParams);
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
		int sequence = vodPhoneModuleInterface.countalbum(queryParams);
		for (String s : albumidlist) {
			queryParams.put("seriesCode", s);
			int n=vodPhoneModuleInterface.countalbum(queryParams);
			if(n<1){
				sequence = sequence+1;
				queryParams.put("sequence", sequence);

				vodPhoneModuleInterface.insertalbum(queryParams);
				queryParams.put("flag", 1);
				vodPhoneModuleInterface.updateflag(queryParams);
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
			vodPhoneModuleInterface.deletealbuminfo(queryParams);
			queryParams.put("flag", 1);
			vodPhoneModuleInterface.updateflag(queryParams);
		}
		queryParams.put("flag", 1);
		vodPhoneModuleInterface.updateflag(queryParams);
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
		VodPhoneModule vodPhoenModule = vodPhoneModuleInterface.load(queryParams);
		if(vodPhoenModule != null){
			model.addAttribute("Describes",vodPhoenModule.getDescribes());
			model.addAttribute("Masterplateid",vodPhoenModule.getMasterplateid());
			model.addAttribute("Name",vodPhoenModule.getName());
			model.addAttribute("Sequence",vodPhoenModule.getSequence());
			model.addAttribute("channel",vodPhoenModule.getChannel());
			model.addAttribute("moduleId",vodPhoenModule.getModuleid());
			model.addAttribute("linkchannel",vodPhoenModule.getLinkchannel());
			model.addAttribute("linklabel",vodPhoenModule.getLinklabel());
			model.addAttribute("linkstatus",vodPhoenModule.getLinkstatus());
			model.addAttribute("linktype",vodPhoenModule.getLinktype());
		}
		List<vodMasterplate> data  = vodMasterplateInterface.find(queryParams);
		model.addAttribute("masterplatelist",data);
		return "/vod/phone/mould/addmould";
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
		vodPhoneModuleInterface.updateimg(queryParams);
		queryParams.put("flag", 1);
		vodPhoneModuleInterface.updateflag(queryParams);
		return 1;
	}


	@LogAnnotation(operationInfo = "点播-phone-栏目管理-更改栏目图片操作")
	@ResponseBody
	@RequestMapping(value = {"/shangxianalbuminfo"})
	public int shangxianalbuminfo(HttpServletRequest request) throws ServletException  {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.vodPhoneModuleInterface.updateshangxian(queryParams);
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
			this.vodPhoneModuleInterface.updateMoudleSeq(nodes,treeNodeArr,targetNodeArr);
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
		Map<String,Object> jsonMap=new HashMap<String,Object>();
		/*Map<String,Object> param=new HashMap<String,Object>();
		param.put("moduleId",moduleId );*/
		Map<String, Object> param = RequestUtil.asMap(request, false);

		try {
			phoneOnlinService.resetSingleMoudle(param);
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
			this.vodPhoneModuleInterface.updateflag(param); //标记修改
			this.vodPhoneModuleInterface.deletemodule(param);
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
		VodPhoneModule vodPhoenModule=new VodPhoneModule();
		try{
			vodPhoenModule.setModuleid(Long.valueOf(moduleId));
			vodPhoenModule.setName(name);
			this.vodPhoneModuleInterface.update(vodPhoenModule);
			param.put("flag", 1);
			this.vodPhoneModuleInterface.updateflag(param); //标记修改
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
		return "/vod/bossModule/basePagePhone";
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
		VodPhoneModule vodPhoenModule = vodPhoneModuleInterface.load(queryParams);
		if(vodPhoenModule != null ){
			return vodPhoenModule.getFlag();
		}
		return 0;
	}

	/**
	 * 更改 模版绑定专辑排序
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@LogAnnotation(operationInfo = "点播-模版管理-更改绑定专辑排序操作")
	@ResponseBody
	@RequestMapping(value = "/updatemodulealbumsequence")
	public int updatemodulealbumsequence(HttpServletRequest request) throws ServletException { 
		String id=request.getParameter("id");
		String sequence=request.getParameter("sequence");
		String albumid=request.getParameter("albumid");
		String[] albumids=albumid.split(",");   
		String[] sequences=sequence.split(",");
		int result=0;
		Map<String, Object> map = new HashMap<>();
		String[] newsequences=sequenceUtil.Sortball(sequence);
		int newmax=sequenceUtil.getMax(newsequences);//更改排序最大值
		int newmin=sequenceUtil.getMin(newsequences);//更改排序最小值
		if(newmax==newmin){
			newmin=0;
		}
		int max=newmax;//排序最大值
		int min=newmin;//排序最小值
		Map<String, Object> mapsequence=new HashMap<>();
		mapsequence.put("albumid",albumid);
		mapsequence.put("moduleid", id);
		System.out.println("updatealbumsequence 接口  入参id"+id+"   ||sequence："+sequence+"   || albumid:"+albumid);
		Map<String, Object> maxmap=vodPhoneLinkAlbumListInterface.findalbummaxVSminsequence(mapsequence);//查询更改排序的最大最小原始值
		int curmax= 0;int curmin=0;
		if(maxmap!=null){
			curmax= Integer.parseInt(maxmap.get("max")==null?"0":maxmap.get("max").toString());//原始数据排序最大值
			curmin= Integer.parseInt(maxmap.get("min")==null?"0":maxmap.get("min").toString());//原始数据排序最大值
		}
		if(newmax<curmax){////如果原始数据最大值比更改排序的最大值大  复制为最大值
			max=curmax;
		}
		if(newmin>curmin){//如果原始数据最小值比更改排序的最小值小  复制为最小值
			min=curmin;
		}
		mapsequence.put("max", max);
		mapsequence.put("min", min);
		List<Map<String, Object>> list=vodPhoneLinkAlbumListInterface.findalbumsequence(mapsequence);
		for(int i=0;i<list.size();i++){//自动更改排序递增递减
			map=list.get(i);
			min+=1;
			int j=newsequences.length ;
			for(int jj=0;jj<j;jj++){// 修改排序=原始排序+jj  jj为循环次数（修改改值与原始值的间隔值）
				if(min==Integer.parseInt(newsequences[jj])){
					min+=1;
				} 
			}
			String seriesCode=map.get("seriesCode").toString();
			map.clear();
			map.put("sequence", min);
			map.put("moduleid",id);
			map.put("seriesCode", seriesCode);
			result=vodPhoneLinkAlbumListInterface.updatealbumsequence(map);
		}
		//更改保存编辑的排序
		for(int i=0;i<albumids.length;i++){
			map.clear();
			map.put("sequence", sequences[i]);
			map.put("moduleid",id);
			map.put("seriesCode", albumids[i]);
			result=vodPhoneLinkAlbumListInterface.updatealbumsequence(map);
		}
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("moduleId", id);
		param.put("flag", 1);
		vodPhoneModuleInterface.updateflag(param); //标记修改
		return result;
	}

}
