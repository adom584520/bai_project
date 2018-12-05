package com.pbtd.manager.vod.phone.hotseries.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.phone.common.domain.Vodchannel;
import com.pbtd.manager.vod.phone.hotseries.domain.VodHotseries;
import com.pbtd.manager.vod.phone.hotseries.service.IVodHotseriesService;
import com.pbtd.manager.util.SequenceUtil;
/**
 * 热播推荐管理
 * @author molly
 *
 */

@Controller 
@RequestMapping("/vod/phone/vodhotseries")
public class VodHotseriesController {
      
	 @Autowired
	 private IVodHotseriesService vodHotseriesService;
	 
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
	    	return "/vod/phone/hotseries/index";
	    }
	    
	    /**
	     * GET 编辑
	     *
	     * @param id 标识
	     */
	    @RequestMapping("/edit/{id}")
	    public String edit(@PathVariable("id") int id,Model model) {
	    	Map<String, Object> params=new HashMap<>();
	    	params.put("levels", 1);
	    	params.put("navigationtype", 1);
			model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));
			
			params.put("navigationtype", 2);
			model.addAttribute("channellist2", dictionaryService.findDictionaryMap(params, "choosechannel"));
	        return "/vod/phone/hotseries/edit";
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
	        return this.vodHotseriesService.count(RequestUtil.asMap(request, false));
	    }
	    
	    /**
	     * 根据标识获取记录
	     *
	     * @param 
	     * @return 记录
	     */
	    @ResponseBody
	    @RequestMapping("/page")
	    public List<Map<String,Object>> page(HttpServletRequest request) {
	    	Map<String,Object> queryParams=RequestUtil.asMap(request, false);
	    	int total=vodHotseriesService.count(queryParams);
	    	List<Map<String,Object>> list=null;
	        if (total==0) {
	        	list=Collections.emptyList();
	        } 
	        else {
	        	list=vodHotseriesService.find(queryParams);	        			
	        }
	        return list;
	    }

	    /**
	     * 根据标识获取记录
	     *
	     * @param id 标识
	     * @return 记录
	     */
	    @ResponseBody
	    @RequestMapping("/load/{id}")
	    public VodHotseries load(@PathVariable("id") int id) {
	    	VodHotseries vodHotseries = null;
	        if (id > 0) {
	        	vodHotseries = this.vodHotseriesService.load(id);
	        } 
	        else {
	        	vodHotseries = new VodHotseries(0,null, 0, 0,
	        			0, 0,null,null,null,null);
	        }
	        return vodHotseries;
	    }
	    
	    /**
	     * 插入记录
	     *
	     * @param
	     * @return 被插入的记录标识
	     */
	    @LogAnnotation(operationInfo = "点播-phone-热播推荐管理-添加热播操作")
	    @ResponseBody
	    @RequestMapping(value = "/create", method = RequestMethod.POST)
	    public int add(HttpServletRequest request) {
	      Map<String,Object> queryParams=RequestUtil.asMap(request, false);
	      this.vodHotseriesService.add(queryParams);
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
	    @LogAnnotation(operationInfo = "点播-phone-热播推荐管理-更改热播操作")
	    @ResponseBody
	    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	    public int edit(@PathVariable("id") int id,HttpServletRequest request) throws ServletException {
	    	 Map<String,Object> queryParams=RequestUtil.asMap(request, false);
	    	return this.vodHotseriesService.modify(queryParams);
	    }

	    /**
	     * POST 删除多条
	     *
	     * @param ids 标识列表
	     * @return 被删除的记录条数
	     */
	    @LogAnnotation(operationInfo = "点播-phone-热播推荐管理-删除热播操作")
	    @ResponseBody
	    @RequestMapping("/deletes")
	    public int deletes(HttpServletRequest request) {
	    	Map<String, Object> queryParams = new HashMap<String, Object>();
	    	   String ids=request.getParameter("id");
	    	   String [] idarray=ids.split(",");
	           for(String num:idarray){
	           int id=Integer.parseInt(num);
	           queryParams.put("id", id);
	           this.vodHotseriesService.deletealbuminfo(queryParams);
	           this.vodHotseriesService.deletes(queryParams);
	           }
	           return 1;
	    }
	    
	    /**
	     * 批量上线下线操作
	     */
	    @LogAnnotation(operationInfo = "点播-phone-热播推荐管理-更改状态操作")
	    @ResponseBody
	    @RequestMapping("/updateStatus")
	    public int updateStatus(HttpServletRequest request){
	     Map<String,Object> queryParams=new HashMap<String,Object>();
	    	int result=0;
	    	String id=request.getParameter("id");
	    	int status=Integer.parseInt(request.getParameter("status"));
	    	if(status==1){
	    		queryParams.put("status", 0);
	    		vodHotseriesService.updateStatus(queryParams);
	    	}
	    	    queryParams.put("status", status);
	    		int id1=Integer.parseInt(id);
	    		queryParams.put("id", id1);	    		
	    		result=vodHotseriesService.updateStatus(queryParams);
			return 1;    	
	    }
	    /**
	     * 绑定定专辑视图
	     */
	    @RequestMapping("/addalbum/{id}")
	    public String  addalbum(Model model,@PathVariable("id") int id) {
	    	model.addAttribute("hot_id",id);
	    	return "/vod/phone/hotseries/albuminfo";
	    }
	    /**
	     * 查询绑定专辑LIST
	     */
	    @ResponseBody
	    @RequestMapping("/pagealbum/{hot_id}")
	    public List<Map<String,Object>> pagealbum(@PathVariable("hot_id") int hot_id) {
	    	Map<String,Object> queryParams=new HashMap<String,Object>();
	    	queryParams.put("hot_id", hot_id);
	    	int total=vodHotseriesService.countalbum(queryParams);
	    	List<Map<String,Object>> list=null;
	        if (total==0) {
	        	list=Collections.emptyList();
	        } 
	        else {
	        	list=vodHotseriesService.pagealbum(queryParams);        			
	        }
	        return list;
	    }
	    
	    /**
	     * 添加绑定专辑视图
	     */
	    @RequestMapping("/addalbumvideo/{id}")
	    public String  addalbumvideo(Model model,@PathVariable("id") int id) {
	    	model.addAttribute("hot_id",id);
	    	Map<String, Object> params=new HashMap<>();
        	params.put("levels", 1);
        	params.put("navigationtype", 1);
        	model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));
        	
        	params.put("navigationtype", 2);
        	model.addAttribute("channellist2", dictionaryService.findDictionaryMap(params, "choosechannel"));
	    	return "/vod/phone/hotseries/addalbuminfo";
	    }
	    /**
	     * 添加绑定专辑
	     */
	    @LogAnnotation(operationInfo = "点播-phone-热播推荐管理-添加热播绑定专辑操作")
	    @ResponseBody
	    @RequestMapping("/addalbuminfo")
	    public int addalbuminfo(HttpServletRequest request) {
	      Map<String,Object> queryParams=RequestUtil.asMap(request, false);
	      String code=queryParams.get("codes").toString();
	      String[] codes=code.split(",");
	      int result=0;
	    	int curmax= 0;
	      for(String num:codes){
	    	  int seriesCode=Integer.parseInt(num);    	
	    	  queryParams.put("seriesCode", seriesCode);
	    	  Map<String, Object> maxmap=vodHotseriesService.findalbummaxVSminsequence(queryParams);//查询更改排序的最大最小原始值
	        	if(maxmap!=null){
	        		curmax=Integer.parseInt(maxmap.get("max")==null?"0":maxmap.get("max").toString());//原始数据排序最大值
	        	}
	        	queryParams.put("sequence",curmax+=1);
	    	    List<Map<String,Object>> list1=vodHotseriesService.pagealbum(queryParams);
	          if(list1.size()>0){
	    	  vodHotseriesService.deletealbuminfo(queryParams);
	    	  }
	    	  vodHotseriesService.addalbuminfo(queryParams);
	      }
	      return result;
	    }
	    
	    /**
	     * POST 编辑
	     *
	     * @param id 标识
	     * @param vodChannel VodChannel实例
	     * @return int 被修改的记录条数
	     * @throws ServletException
	     */
	    @LogAnnotation(operationInfo = "点播-phone-热播推荐管理-更改热播绑定专辑操作")
	    @ResponseBody
	    @RequestMapping("/editalbuminfo")
	    public int editalbuminfo(HttpServletRequest request) throws ServletException {
	    	 Map<String,Object> queryParams=RequestUtil.asMap(request, false);
	    	 String seriesCode=request.getParameter("seriesCode");
	    	 String hot_id=request.getParameter("hot_id");
	    	 String sequence=request.getParameter("sequence");
	    	 String[] seriesCodeArr=seriesCode.split(",");
	    	 String[] sequenceArr=sequence.split(",");	    	 
	    	 Map<String,Object> params=new HashMap<String,Object>();
	    	 params.put("hot_id", hot_id);
 	    	int result=0;
	    	 if(seriesCodeArr!=null){
		    	 int len=seriesCodeArr.length;
		    	 if(len>0){
		    		 String[] newsequences=sequenceUtil.Sortball(sequence);
		    	    	int newmax=sequenceUtil.getMax(newsequences);//更改排序最大值
		    	    	int newmin=sequenceUtil.getMin(newsequences);//更改排序最小值
		    	    	if(newmax==newmin){
		    	    		newmin=0;
		    	    	}
		    	    	int max=newmax;//排序最大值
		    	    	int min=newmin;//排序最小值
		    	    	Map<String, Object> mapsequence=new HashMap<>();
		    	    	mapsequence.put("albumid",seriesCode);
		    	    	mapsequence.put("hot_id", hot_id);
		    	    	Map<String, Object> maxmap=vodHotseriesService.findalbummaxVSminsequence(mapsequence);//查询更改排序的最大最小原始值
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
		    			List<Map<String, Object>> list=vodHotseriesService.findalbumsequence(mapsequence);
		    	    	Map<String, Object> map = new HashMap<>();
		    	    	for(int i=0;i<list.size();i++){//自动更改排序递增递减
		    	    		map=list.get(i);
		    	    		min+=1;
		    	    		 int j=newsequences.length ;
		    	    		 for(int jj=0;jj<j;jj++){// 修改排序=原始排序+jj  jj为循环次数（修改改值与原始值的间隔值）
		    	    			if(min==Integer.parseInt(newsequences[jj])){
		    	    				min+=1;
		    	    			} 
		    	    		}
		    	    		String code=map.get("seriesCode").toString();
		    	    		map.clear();
		    	    		map.put("sequence", min);
		    	    		map.put("hot_id",hot_id);
		    	    		map.put("seriesCode", code);
		    	        	vodHotseriesService.updatesequence(map);
		    	          }
		    	    	//更改编辑的排序信息
		    		 for(int i=0;i<len;i++){
		    			 params.put("seriesCode", seriesCodeArr[i]);
		    			 params.put("sequence", sequenceArr[i]);
		    			 vodHotseriesService.updatesequence(params);
		    	 }
	    	 }
	    }
			return 1;
	    }
	    /**
	     * POST 删除多条
	     *
	     * @param ids 标识列表
	     * @return 被删除的记录条数
	     */
	    @LogAnnotation(operationInfo = "点播-phone-热播推荐管理-删除热播绑定专辑操作")
	    @ResponseBody
	    @RequestMapping("/deletealbum")
	    public int deletealbum(HttpServletRequest request) {
	    	   Map<String, Object> queryParams =RequestUtil.asMap(request);
	    	   String code=request.getParameter("codes");
	    	   String hotid=request.getParameter("hot_id");
	    	   String[] codes=code.split(",");
	    	   int result=0;
	       	Map<String, Object> mapsequence=new HashMap<>();
	       	mapsequence.put("albumid",code);
	       	mapsequence.put("hot_id",hotid);
	       	//查询更改排序的原始值
	       	List<Map<String, Object>>  oldsequencelist=vodHotseriesService.findalbumsequencesum(mapsequence);
	       	int min=Integer.parseInt(oldsequencelist.get(0).get("sequence")==null?"1":oldsequencelist.get(0).get("sequence").toString());
	       	mapsequence.put("min", min);
	   		List<Map<String, Object>> list=vodHotseriesService.findalbumsequence(mapsequence);
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
	       		String seriesCode=map.get("seriesCode").toString();
	       		map.clear();
	       		map.put("sequence", curmin);
	       		map.put("hot_id",hotid);
	       		map.put("seriesCode", seriesCode);
	           	result= vodHotseriesService.updatesequence(map);
	             }
	    	   
	    	   for(String num:codes){
	    		   int seriesCode=Integer.parseInt(num);
	    		   queryParams.put("seriesCode", seriesCode);
	    		   result=vodHotseriesService.deletealbuminfo(queryParams);
	    	   }
	        return 1;
	    }
	 
}
