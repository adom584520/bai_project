package com.pbtd.manager.vod.phone.common.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.phone.common.domain.Vodchannel;
import com.pbtd.manager.vod.phone.common.service.face.IVodChannelService;
import com.pbtd.manager.vod.phone.special.domain.VodSpecial;
import com.pbtd.manager.util.SequenceUtil;
/**
 * 手机频道
 * @author zr
 */
@Controller 
@RequestMapping("/vod/phone/Vodchannel")
public class VodChannelController {

    @Autowired
    private IVodChannelService vodchannelService;
    
    @Autowired
    private IDictionaryService dictionaryService;
    @Autowired
    private SequenceUtil  sequenceUtil;
    /**
     * GET 查询
     *
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index(Model model) {
    	Map<String, Object> params=new HashMap<>();
    	params.put("levels", 1);
		model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));
    	return "/vod/phone/common/vodchannel/index";
    }

    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/phone/common/vodchannel/create";
    }

    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model) {
    	Map<String, Object> params=new HashMap<>();
    	params.put("levels", 1);
		model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));
		model.addAttribute("labellist", dictionaryService.findDictionaryMap(params, "chooselabel"));
        return "/vod/phone/common/vodchannel/edit";
    }

    /**
     * 模糊统计符合查询条件的记录总数
     *
     * @param request 查询参数
     * @return 记录总数
     */
    @ResponseBody
    @RequestMapping("/count")
    public int count(HttpServletRequest request) {
        return this.vodchannelService.count(RequestUtil.asMap(request, false));
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
    	List<Vodchannel> data = new ArrayList<Vodchannel>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodchannelService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodchannelService.find(queryParams);
			return new PageResult(total, data);
		
    }

    /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    @ResponseBody
    @RequestMapping("/load/{id}")
    public Vodchannel load(@PathVariable("id") int id) {
    	Vodchannel vodchannel = null;
        if (id > 0) {
        	vodchannel = this.vodchannelService.load(id);
        } 
        else {
        	int c=this.vodchannelService.generatePosition(null);
        	vodchannel = new Vodchannel(-1,c, null, 0, null, 0,
        			0, 0, 0, 0, null, 
        			null, null, null,0,null,null,0,0,0);
        }
        return vodchannel;
    }

    /**
     * 精确判断是否存在记录
     * 
     * @param id 标识
     * @param request 查询参数
     * @return 记录条数
     */
    @RequestMapping("/exist/{id}")
    @ResponseBody
    public int exist(@PathVariable("id")int id, HttpServletRequest request) {
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    	List<Vodchannel> vodchannel = this.vodchannelService.find(queryParams);
    	if (vodchannel.size() == 1) {
    		return vodchannel.get(0).getId() == id ? 0 : 1;
    	} else {
    		return vodchannel.size();
    	}
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "点播-phone-频道管理-添加频道操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(Vodchannel Vodchannel, HttpServletRequest request) {
    	Map<String,Object> queryParams=new HashMap();
    	queryParams.put("levels",request.getParameter("levels"));
    	queryParams.put("parentCode", request.getParameter("parentCode"));
    	Map<String, Object> maxmap=vodchannelService.findsequence(queryParams);//查询更改排序的最大最小原始值
    	int curmax= 0;
    	if(maxmap!=null){
    		curmax=Integer.parseInt(maxmap.get("max")==null?"0":maxmap.get("max").toString());//原始数据排序最大值
    	}
    	Vodchannel.setSequence(curmax+=1);
    	this.vodchannelService.insert(Vodchannel);
        return 1;
    }

    /**
     * POST 编辑
     *
     * @param id 标识
     * @param vodChannel VodChannel实例
     * @return int 被修改的记录条数
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "点播-phone-频道管理-更改频道操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, Vodchannel vodchannel) throws ServletException {
        int newmax=vodchannel.getSequence();
        int newmin=0;
        int max=newmax;//排序最大值
    	int min=newmin;//排序最小值
    	Map<String, Object> mapsequence=new HashMap<>();
        mapsequence.put("id", Integer.toString(id));
        Map<String, Object> maxmap=vodchannelService.findchannelmaxVSminsequence(mapsequence);//查询更改排序的最大最小原始值
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
    	mapsequence.put("levels",vodchannel.getLevels());
    	mapsequence.put("parentCode",vodchannel.getParentCode());
    	List<Map<String, Object>> list=vodchannelService.findchannelsequence(mapsequence);
    	Map<String, Object> map = new HashMap<>();
    	 String[] newsequences=sequenceUtil.Sortball(Integer.toString(newmax));
    	for(int i=0;i<list.size();i++){//自动更改排序递增递减
    		map=list.get(i);
    		min+=1;
    		 int j=newsequences.length ;
    		 for(int jj=0;jj<j;jj++){// 修改排序=原始排序+jj  jj为循环次数（修改改值与原始值的间隔值）
    			if(min==Integer.parseInt(newsequences[jj])){
    				min+=1;
    			} 
    		}
            // 修改排序=原始排序+jj  jj为循环次数（修改改值与原始值的间隔值）
    		String code=map.get("id").toString();
            Vodchannel vodchannel1=new Vodchannel();
            vodchannel1.setId(Integer.parseInt(code));
            vodchannel1.setSequence(min);
            vodchannel1.setStatus(-1);
    		vodchannelService.update(vodchannel1);
          }
    	//更改编辑的排序信息
    	vodchannel.setId(id);
    	return this.vodchannelService.update(vodchannel);
    }

    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "点播-phone-频道管理-删除频道操作")
    @ResponseBody
    @RequestMapping("/deletes")
    public int deletes(HttpServletRequest request) {
     Map<String, Object> queryParams = new HashMap<String, Object>();
  	   String ids=request.getParameter("id");
  	   String [] idarray=ids.split(",");
  	   String levels=request.getParameter("levels");
	   String parentCode=request.getParameter("parentCode");
	   String [] levelarray=levels.split(",");
	   String [] parentCodearray=parentCode.split(",");
	    int result=0;
	    for(int f=0;f<idarray.length;f++){
		queryParams.put("id",idarray[f]);
		queryParams.put("levels",levelarray[f]);
		String num=parentCodearray[f].equals("1")?"":parentCodearray[f];
		queryParams.put("parentCode",num);
    	//查询更改排序的原始值
    	List<Map<String, Object>>  oldsequencelist=vodchannelService.findchannelsequencesum(queryParams);
    	int min=Integer.parseInt(oldsequencelist.get(0).get("sequence")==null?"1":oldsequencelist.get(0).get("sequence").toString());
    	queryParams.put("min", min);
		List<Map<String, Object>> list=vodchannelService.findchannelsequence(queryParams);
    	Map<String, Object> map = new HashMap<>();
    	for(int i=0;i<list.size();i++){//自动更改排序递增递减
    		map=list.get(i);
    		int curmin=Integer.parseInt(map.get("sequence")==null?"1":map.get("sequence").toString());
    		 int j=oldsequencelist.size() ;
    		 int  c=1;
    		 for(int jj=0;jj<j;jj++){
    			if(curmin>Integer.parseInt(oldsequencelist.get(jj).get("sequence")==null?"1":oldsequencelist.get(jj).get("sequence").toString())){
    				c=jj+1;
    			} 
    		}
    		 curmin-=c;
    		Vodchannel vodchannel1=new Vodchannel();
    		vodchannel1.setId(Integer.parseInt(map.get("id").toString()));
    		vodchannel1.setSequence(curmin);
    		vodchannel1.setStatus(-1);
        	result=vodchannelService.update(vodchannel1);
          }
	    }
         for(String num:idarray){
         queryParams.clear();
         int id=Integer.parseInt(num);
         queryParams.put("id", id);
         this.vodchannelService.deletes(queryParams);
         }
         return 1;         
    }
    
    /**
     * 批量上线下线操作
     */
    @LogAnnotation(operationInfo = "点播-phone-频道管理-更改频道状态操作")
    @ResponseBody
    @RequestMapping("/updateStatus")
    public int updateStatus(HttpServletRequest request){
     Map<String,Object> map=new HashMap<String,Object>();
    	int result=0;
    	String id=request.getParameter("id");
    	String[] ids=id.split(",");
    	for(String idNew:ids){
    		int id1=Integer.parseInt(idNew);
    		int status=Integer.parseInt(request.getParameter("status"));
            map.put("id", id1);
            map.put("status", status);
    		result=vodchannelService.updatestatus(map);
    	}   	
		return result;    	
    }
    
    
    /**
     * GET 创建
     *跳转绑定专辑信息
     * @return 视图路径
     */
    @RequestMapping("/albuminfo/{id}")
    public String albuminfo(@PathVariable("id") int id ,HttpServletRequest request,Model model) {
    	String channelName=request.getParameter("channelName");
    	String parentCode=request.getParameter("parentCode");
    	String levels=request.getParameter("levels");
    	String channel=request.getParameter("channel");
    	model.addAttribute("channelName",channelName);
    	model.addAttribute("parentCode",parentCode);
    	model.addAttribute("levels", levels);
        model.addAttribute("channel", channel);
    	if(levels.equals("1")){
    	model.addAttribute("channelid", id);
    	}else{
    	model.addAttribute("channelid", channel);
    	}
        return "/vod/phone/common/vodchannel/albuminfo";
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
    @RequestMapping("/albuminfopage")
    public PageResult albuminfopage(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
    	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodchannelService.countalbum(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodchannelService.pagealbum(queryParams);
			return new PageResult(total, data);
		
    }
    /**
     *添加绑定专辑信息
     * @param request
     * @return
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "点播-phone-频道管理-添加绑定专辑操作")
    @ResponseBody
    @RequestMapping(value = "/addalbuminfo")
    public int addalbuminfo(HttpServletRequest request) throws ServletException { 
    	String id=request.getParameter("id");
    	int levels=Integer.parseInt(request.getParameter("levels"));
    	String parentCode=request.getParameter("parentCode")==null?"":request.getParameter("parentCode");
    	String albumids=request.getParameter("albumid");
    	String[] albumid=albumids.split(",");   
    	int result=0;
    	Map<String, Object> map = new HashMap<>();
    	int curmax1= 0;int curmax2=0;
    	for(String idnew:albumid){
    		map.put("seriesCode", idnew);
    	    if(levels==2){
    	    	 map.put("channelCode",parentCode);
    	    	Map<String, Object> maxmap2=vodchannelService.findalbummaxVSminsequence(map);//查询更改排序的最大最小原始值
    	        if(maxmap2!=null){
    	        curmax2=Integer.parseInt(maxmap2.get("max")==null?"0":maxmap2.get("max").toString());//原始数据排序最大值
    	        }
    	    }
    	
        	 map.put("channelCode",id);
        	 Map<String, Object> maxmap1=vodchannelService.findalbummaxVSminsequence(map);//查询更改排序的最大最小原始值
 	        if(maxmap1!=null){
 	        curmax1=Integer.parseInt(maxmap1.get("max")==null?"0":maxmap1.get("max").toString());//原始数据排序最大值
 	        }
    		
    		List list=vodchannelService.pagealbum(map);
			if(list.size()==0){
				if(levels==2){
	    			map.put("channelCode",parentCode);
	    			map.put("sequence",curmax2+=1);
	    			vodchannelService.insertalbum(map);
	   		    }
	   		    map.put("channelCode", id);
	   		    map.put("sequence",curmax1+=1);
				vodchannelService.insertalbum(map);
			}
          }
		 return result;
    }
    
    /**
     *删除绑定专辑信息
     * @param request
     * @return
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "点播-phone-频道管理-删除绑定专辑操作")
    @ResponseBody
    @RequestMapping(value = "/delalbuminfo")
    public int delalbuminfo(HttpServletRequest request) throws ServletException { 
    	String id=request.getParameter("id");
    	String albumids=request.getParameter("albumid");
    	String[] albumid=albumids.split(",");   
    	int result=0;
    	Map<String, Object> map = new HashMap<>();
    	Map<String, Object> mapsequence=new HashMap<>();
    	mapsequence.put("albumid",albumids);
    	mapsequence.put("channelCode", id);
    	//查询更改排序的原始值
    	List<Map<String, Object>>  oldsequencelist=vodchannelService.findalbumsequencesum(mapsequence);
    	int min=Integer.parseInt(oldsequencelist.get(0).get("sequence")==null?"1":oldsequencelist.get(0).get("sequence").toString());
    	mapsequence.put("min", min);
		List<Map<String, Object>> list=vodchannelService.findalbumsequence(mapsequence);
    	for(int i=0;i<list.size();i++){//自动更改排序递增递减
    		map=list.get(i);
    		int curmin=Integer.parseInt(map.get("sequence")==null?"1":map.get("sequence").toString());
    		 int j=oldsequencelist.size() ;
    		 int  c=1;
    		 for(int jj=0;jj<j;jj++){
    			if(curmin>Integer.parseInt(oldsequencelist.get(jj).get("sequence")==null?"1":oldsequencelist.get(jj).get("sequence").toString())){
    				c=jj+1;
    			} 
    		}
    		 curmin-=c;
    		String seriesCode=map.get("seriesCode").toString();
    		map.clear();
    		map.put("sequence", curmin);
    		map.put("channelCode",id);
    		map.put("seriesCode", seriesCode);
        	result=vodchannelService.updatealbumsequence(map);
          }
    	//删除
    	for(String idnew:albumid){
    		map.clear();
    		map.put("channelCode", id);
    		map.put("seriesCode", idnew);
    		vodchannelService.deletesalbum(map);
          }
		 return result;
    }
    /**
     * 更改绑定专辑排序
     * @param request
     * @return
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "点播-phone-频道管理-更改绑定专辑排序操作")
    @ResponseBody
    @RequestMapping(value = "/updatealbumsequence")
    public int updatealbumsequence(HttpServletRequest request) throws ServletException { 
    	String channelCode=request.getParameter("channelCode");
    	String sequence=request.getParameter("sequence");
    	String albumid=request.getParameter("albumid");
    	String[] albumids=albumid.split(",");   
    	String[] sequences=sequence.split(",");
    	String[] channelCodes=channelCode.split(",");
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
    	mapsequence.put("channelCode", channelCode);
    	Map<String, Object> maxmap=vodchannelService.findalbummaxVSminsequence(mapsequence);//查询更改排序的最大最小原始值
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
		List<Map<String, Object>> list=vodchannelService.findalbumsequence(mapsequence);
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
    		map.put("channelCode",channelCode);
    		map.put("seriesCode", seriesCode);
        	result=vodchannelService.updatealbumsequence(map);
          }
    	////更改编辑保存的排序信息
    	for(int i=0;i<albumids.length;i++){
    		map.clear();
    		map.put("sequence", sequences[i]);
    		map.put("channelCode",channelCodes[i]);
    		map.put("seriesCode", albumids[i]);
        	result=vodchannelService.updatealbumsequence(map);
          }
		 return result;
    } 

    /**
     * GET 创建
     *
     * @return 频道绑定标签视图路径
     */
    @RequestMapping("/getchannellabel/{id}")
    public String getchannellabel(@PathVariable("id") int id,HttpServletRequest request,Model model) {
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    	model.addAttribute("labeltypelist", dictionaryService.findDictionaryMap(queryParams, "chooselabeltype"));
    	model.addAttribute("channel",id);
        return "/vod/phone/common/vodchannel/getchannellabel";
    }
    
    /**
     * GET 创建
     *
     * @return 添加标签视图路径
     */
    @RequestMapping("/addchannellabel/{id}")
    public String addchannellabel(@PathVariable("id") int id,HttpServletRequest request,Model model) {
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    	model.addAttribute("channel",id);
    	model.addAttribute("labeltypelist", dictionaryService.findDictionaryMap(queryParams, "chooselabeltype"));
        return "/vod/phone/common/vodchannel/addchannellabel";
    }
    
    /**
     * GET page
     *
     * @return 查询频道绑定标签数据页
     */
 
    
    @ResponseBody
    @RequestMapping("/getchannellabelpage")
		    public PageResult getchannellabelpage(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		    int total = this.vodchannelService.countlabelforchannel(queryParams);
			if (total == 0) {
				return new PageResult(total, Collections.EMPTY_LIST);
			}
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(limit);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
			data =this.vodchannelService.pagelabelforchannel(queryParams);
			return new PageResult(total, data);	
}
    /**
     *添加绑定专辑信息
     * @param request
     * @return
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "点播-phone-频道管理-添加绑定标签操作")
    @ResponseBody
    @RequestMapping(value = "/addbindinglabel")
    public int addbindinglabel(HttpServletRequest request) throws ServletException { 
    	String id=request.getParameter("id");
    	String[] ids=id.split(",");
    	String labeltype=request.getParameter("labeltype");
    	String[] labeltypes=labeltype.split(",");
    	String channel=request.getParameter("channel");
    	Map<String, Object> map = new HashMap<>();
    	String str[]=new String[]{};//定义这个数用用于接收去重后的标签分类的长度用于循环添加
    	if(labeltypes.length>1){//如果有多个标签分类
        	//标签分类去重,因为下面更改重新排序方法是按标签分类for循环更改
        	List<String> listlabeltype = new ArrayList<>(); 
        	for(int i=0;i<labeltypes.length;i++){
        			if(!listlabeltype.contains(labeltypes[i])){
        				listlabeltype.add(labeltypes[i]);
        			}
        	}
        	str = (String[]) listlabeltype.toArray(new String[listlabeltype.size()]);//去重后的标签分类集合
        	}else{
        		str=labeltypes;
        	}
               	for(int i=0;i<str.length;i++){//按去重后标签分类的数量循环
    		            for(int j=0;j<ids.length;j++){//按标签的数量循环添加
    			        if(labeltypes[j].equals(str[i])){
    				    map.put("typeid",labeltypes[j]);
    		    	    Map<String, Object> maxmap1=vodchannelService.findchannellabelmaxVSminsequence(map);//查询更改排序的最大最小原始值
    		    	    int curmax= 0;
    		 	        if(maxmap1!=null){
    		 	        curmax=Integer.parseInt(maxmap1.get("max")==null?"0":maxmap1.get("max").toString());//原始数据排序最大值
    		 	        }
    		 	         map.put("label", Integer.parseInt(ids[j]));
    		    		int count=vodchannelService.countlabelforchannel(map);
    					if(count==0){
    			    			map.put("sequence",curmax+=1);
    			    			vodchannelService.addbindinglabel(map);
    					}
    			 }
    	}
    	}
		 return 1;
    }
    
    /**
     *删除绑定专辑信息
     * @param request
     * @return
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "点播-phone-频道管理-删除绑定标签操作")
    @ResponseBody
    @RequestMapping(value = "/deletebindinglabel")
    public int deletebindinglabel(HttpServletRequest request) throws ServletException { 
    	String id=request.getParameter("id");
    	String[] labelid=id.split(",");
    	String labeltype=request.getParameter("labeltype");
    	String[] labeltypes=labeltype.split(",");
    	String channel=request.getParameter("channel");
    	Map<String, Object> map = new HashMap<>();
    	Map<String, Object> mapsequence=new HashMap<>();
    	mapsequence.put("channel",channel);
    	mapsequence.put("type","phone");
    	mapsequence.put("id", id);
    	//查询更改排序的原始值
    	List<Map<String, Object>>  oldsequencelist=vodchannelService.findchannellabelsequencesum(mapsequence);
    	int min=Integer.parseInt(oldsequencelist.get(0).get("sequence")==null?"1":oldsequencelist.get(0).get("sequence").toString());
    	mapsequence.put("min", min);
    	if(labeltypes.length>1){//如果有多个标签分类
    	//标签分类去重,因为下面更改重新排序方法是按标签分类for循环更改
    	List<String> list1 = new ArrayList<>(); 
    	for(int i=0;i<labeltypes.length;i++){
			if(!list1.contains(labeltypes[i])){
				list1.add(labeltypes[i]);
			}
		}
		labeltypes = (String[]) list1.toArray(new String[list1.size()]);//去重后的标签分类集合
		}
    	for(int ii=0;ii<labeltypes.length;ii++){
    	mapsequence.put("typeid", labeltypes[ii]);
		List<Map<String, Object>> list=vodchannelService.findchannellabelsequence(mapsequence);
    	for(int i=0;i<list.size();i++){//自动更改排序递增递减
    		map=list.get(i);
    		int curmin=Integer.parseInt(map.get("sequence")==null?"1":map.get("sequence").toString());
    		 int j=oldsequencelist.size() ;
    		 int  c=1;
    		 for(int jj=0;jj<j;jj++){
    			if(curmin>Integer.parseInt(oldsequencelist.get(jj).get("sequence")==null?"1":oldsequencelist.get(jj).get("sequence").toString())){
    				c=jj+1;
    			} 
    		}
    		 curmin-=c;
    		int id1=Integer.parseInt(list.get(i).get("id").toString()); 
    		map.clear();
    		map.put("sequence", curmin);
    		map.put("id",id1);
        	vodchannelService.updatebindinglabel(map);
          }
    	}
    	//删除
    	for(String id2:labelid){
    		map.clear();
    		map.put("id", Integer.parseInt(id2));
    		vodchannelService.deletebindinglabel(map);
          }
		 return 1;
    }
    
    /**
     * 更改绑定标签排序
     * @param request
     * @return
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "点播-phone-频道管理-更改绑定标签排序操作")
    @ResponseBody
    @RequestMapping(value = "/updatelabelsequence")
    public int updatelabelsequence(HttpServletRequest request) throws ServletException { 
    	String id=request.getParameter("id");
    	String sequence=request.getParameter("sequence");
    	String channel=request.getParameter("channel");
    	String labeltype=request.getParameter("labeltype");
    	String[] ids=id.split(",");   
    	String[] sequences=sequence.split(",");
    	String[] labeltypes=labeltype.split(",");
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
    	mapsequence.put("id",id);
    	Map<String, Object> maxmap=vodchannelService.findchannellabelmaxVSminsequence(mapsequence);//查询更改排序的最大最小原始值
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
    	mapsequence.put("channel",channel);
    	if(labeltypes.length>1){//如果有多个标签分类
    	//标签分类去重,因为下面更改重新排序方法是按标签分类for循环更改
    	List<String> list1 = new ArrayList<>(); 
    	for(int i=0;i<labeltypes.length;i++){
			if(!list1.contains(labeltypes[i])){
				list1.add(labeltypes[i]);
			}
		}
		labeltypes = (String[]) list1.toArray(new String[list1.size()]);//去重后的标签分类集合
		}
    	for(int ii=0;ii<labeltypes.length;ii++){
    	mapsequence.put("typeid",labeltypes[ii]);
		List<Map<String, Object>> list=vodchannelService.findchannellabelsequence(mapsequence);
    	for(int i=0;i<list.size();i++){//自动更改排序递增递减
    		map=list.get(i);
    		min+=1;
    		 int j=newsequences.length ;
    		 for(int jj=0;jj<j;jj++){// 修改排序=原始排序+jj  jj为循环次数（修改改值与原始值的间隔值）
    			if(min==Integer.parseInt(newsequences[jj])){
    				min+=1;
    			} 
    		}
    		String id1=map.get("id").toString();
    		map.clear();
    		map.put("sequence", min);
    		map.put("id", id1);
        	result=vodchannelService.updatebindinglabel(map);
          }
    	}
    	////更改编辑保存的排序信息
    	for(int i=0;i<ids.length;i++){
    		map.clear();
    		map.put("sequence", sequences[i]);
    		map.put("id",ids[i]);
        	result=vodchannelService.updatebindinglabel(map);
          }
		 return result;
    }


    
    @LogAnnotation(operationInfo = "点播-phone-栏目管理-更改栏目图片操作")
    @ResponseBody
    @RequestMapping(value = "/editimg", method = RequestMethod.POST)
    public int editimg(HttpServletRequest request) throws ServletException  {
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.vodchannelService.updateimg(queryParams);
    }

}
