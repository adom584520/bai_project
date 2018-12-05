package com.pbtd.manager.vod.phone.hotsearch.controller;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.ResultBean;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.phone.hotsearch.domain.VodHotSearchInfo;
import com.pbtd.manager.vod.phone.hotsearch.service.face.IVodHotSearchService;
import com.pbtd.manager.util.SequenceUtil;
/**
 * @author shenjr
 */

@Controller
@RequestMapping("/vod/phone/vodHotSearch")
public class VodHostSearchController {
	
	@Autowired
	private IVodHotSearchService vodHotSearchService;
	 @Autowired
	 private SequenceUtil  sequenceUtil;
	
	//@RequestMapping("/index")
	@RequestMapping(value = {"/index", ""})
	public String index(){
		return "/vod/phone/hotsearch/vodhotsearch/index";
	}
	
	
	/**
	 * 搜索热搜信息
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping("/page")
	@ResponseBody
	public PageResult getHotSearchGrid(@RequestParam(value="page") int page,@RequestParam(value="rows") int rows,HttpServletRequest request ){
		PageResult pr=new PageResult();
		Map<String,Object> queryParams=RequestUtil.asMap(request); //前端参数  默认不空   
		int total=vodHotSearchService.count(queryParams);
		if(total==0){
			pr.setTotal(0L);
			pr.setRows(Collections.EMPTY_LIST);
			return pr;
		}else{
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(rows);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
			List<Map<String,Object>> data=this.vodHotSearchService.page(queryParams);
			pr.setTotal(Long.valueOf(total));
			pr.setRows(data);
			return pr;
		}
	}
	 

	@RequestMapping("/addAll")
	@ResponseBody
	@LogAnnotation(operationInfo = "点播-phone-热搜管理-添加操作")
	public int addAll(HttpServletRequest request){
    	String albumids=request.getParameter("albumid");
    	String[] albumid=albumids.split(",");   
    	VodHotSearchInfo vodHostSearchInfo=new VodHotSearchInfo();
    	int result=0;
    	Map<String, Object> map = new HashMap<>();
    	int curmax= 0;
    	for(String idnew:albumid){
    		vodHostSearchInfo.setSeriesCode(idnew);
    		vodHostSearchInfo.setStatus(1);
    		Map<String, Object> maxmap=vodHotSearchService.findalbummaxVSminsequence(map);//查询更改排序的最大最小原始值
        	if(maxmap!=null){
        		curmax=Integer.parseInt(maxmap.get("max")==null?"0":maxmap.get("max").toString());//原始数据排序最大值
        	}
        	vodHostSearchInfo.setSequence(curmax+1);
        	map.put("albumid", idnew);
    		int i=vodHotSearchService.count(map);
    		if(i<1){
    			vodHotSearchService.insert(vodHostSearchInfo);
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
	@LogAnnotation(operationInfo = "点播-phone-热搜管理-删除操作")
    @ResponseBody
    @RequestMapping(value = "/deletes")
    public int delalbuminfo(HttpServletRequest request) throws ServletException { 
    	String albumids=request.getParameter("albumid");
    	String[] albumid=albumids.split(",");   
    	int result=0;
    	VodHotSearchInfo vodHostSearchInfo=new VodHotSearchInfo();
    	Map<String, Object> mapsequence=new HashMap<>();
    	mapsequence.put("albumid",albumids);
    	//查询更改排序的原始值
    	List<Map<String, Object>>  oldsequencelist=vodHotSearchService.findalbumsequencesum(mapsequence);
    	int min=Integer.parseInt(oldsequencelist.get(0).get("sequence")==null?"1":oldsequencelist.get(0).get("sequence").toString());
    	mapsequence.put("min", min);
		List<Map<String, Object>> list=vodHotSearchService.findalbumsequence(mapsequence);
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
    		vodHostSearchInfo.setSequence( curmin);
    		vodHostSearchInfo.setSeriesCode(seriesCode);
        	result=vodHotSearchService.update(vodHostSearchInfo);
          }
    	//删除
    	for(String idnew:albumid){
    		vodHostSearchInfo.setSeriesCode(idnew);
    		vodHostSearchInfo.setStatus(1);
    		vodHostSearchInfo.setSequence(1);
    		vodHotSearchService.deletes(vodHostSearchInfo);
          }
		 return result;
    }

    /**
     * 更新排序
     * @param request
     * @return
     * @throws ServletException
     */
	@LogAnnotation(operationInfo = "点播-phone-热搜管理-更改排序操作")
    @ResponseBody
    @RequestMapping(value = "/updateSequence")
    public int updateSequence(HttpServletRequest request) throws ServletException { 
    	String albumids=request.getParameter("albumid");
    	String[] albumid=albumids.split(",");
    	String sequences=request.getParameter("sequences");
    	String[] sequenceArr=sequences.split(",");
    	int result=0;
    	VodHotSearchInfo vodHostSearchInfo=new VodHotSearchInfo();
    	String[] newsequences=sequenceUtil.Sortball(sequences);
    	int newmax=sequenceUtil.getMax(newsequences);//更改排序最大值
    	int newmin=sequenceUtil.getMin(newsequences);//更改排序最小值
    	if(newmax==newmin){
    		newmin=0;
    	}
    	int max=newmax;//排序最大值
    	int min=newmin;//排序最小值
    	Map<String, Object> mapsequence=new HashMap<>();
    	mapsequence.put("albumid",albumids);
    	Map<String, Object> maxmap=vodHotSearchService.findalbummaxVSminsequence(mapsequence);//查询更改排序的最大最小原始值
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
		List<Map<String, Object>> list=vodHotSearchService.findalbumsequence(mapsequence);
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
    		String seriesCode=map.get("seriesCode").toString();
    		vodHostSearchInfo.setSeriesCode(seriesCode);
    		vodHostSearchInfo.setSequence(min);
        	result=vodHotSearchService.update(vodHostSearchInfo);
          }
    	//更改编辑保存的排序信息
    	for(int i=0;i< albumid.length;i++){
    		vodHostSearchInfo.setSeriesCode(albumid[i]);
    		vodHostSearchInfo.setSequence(Integer.valueOf(sequenceArr[i]));
    		vodHotSearchService.update(vodHostSearchInfo);
          }
		 return result;
    }
    
	
}
