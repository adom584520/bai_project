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
import com.pbtd.manager.util.SequenceUtil;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.phone.common.domain.VodLabeltype;
import com.pbtd.manager.vod.phone.common.service.face.IVodLabeltypeService;

/**
 * @author zr
 */
@Controller
@RequestMapping("/vod/phone/VodLabeltype")
public class VodLabeltypeController {

    @Autowired
    private IVodLabeltypeService vodlabeltypeService;
    
    
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
    	return "/vod/phone/common/vodlabeltype/index";
    }

    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/phone/common/vodlabeltype/create";
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
        return "/vod/phone/common/vodlabeltype/edit";
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
        return this.vodlabeltypeService.count(RequestUtil.asMap(request, false));
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
    	List<VodLabeltype> data = new ArrayList<VodLabeltype>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodlabeltypeService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodlabeltypeService.page(queryParams);
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
    public VodLabeltype load(@PathVariable("id") int id) {
    	VodLabeltype vodlabel = null;
        if (id > 0) {
        	vodlabel = this.vodlabeltypeService.load(id);
        } 
        else {
        	vodlabel = new VodLabeltype(-1, null, 1,null);
        }
        return vodlabel;
    }

    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "点播-phone-标签分类管理-添加操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(VodLabeltype vodlabel, HttpServletRequest request) {
    	  Map<String,Object> queryParams=new HashMap();
          Map<String,Object> maxMap=vodlabeltypeService.findmaxVSminsequence(queryParams);
          int curMax=0;
          if(maxMap!=null){
        	  curMax=Integer.parseInt(maxMap.get("max")==null?"0":maxMap.get("max").toString());
          }
          vodlabel.setSequence(curMax+=1);
    	this.vodlabeltypeService.insert(vodlabel);
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
    @LogAnnotation(operationInfo = "点播-phone-标签分类管理-更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, VodLabeltype vodlabel) throws ServletException {
    	 int newmax=vodlabel.getSequence();
         int newmin=0;
         int max=newmax;//排序最大值
      	int min=newmin;//排序最小值
      	Map<String, Object> mapsequence=new HashMap<>();
      	 mapsequence.put("id", Integer.toString(id));
      	 Map<String, Object> maxmap=vodlabeltypeService.findmaxVSminsequence(mapsequence);//查询更改排序的最大最小原始值
      	 int curmax= 0;int curmin=0;
      	 if(maxmap!=null){
      		 curmax=Integer.parseInt(maxmap.get("max")==null?"0":maxmap.get("max").toString());//原始数据排序最大值
      		 curmin=Integer.parseInt(maxmap.get("min")==null?"0":maxmap.get("min").toString());//原始数据排序最小值
      	 }
      	 if(newmax<curmax){////如果原始数据最大值比更改排序的最大值大  复制为最大值
           	max=curmax;
           }
           if(newmin>curmin){//如果原始数据最小值比更改排序的最小值小  复制为最小值
           	min=curmin;
           }
         mapsequence.put("max", max);
        	mapsequence.put("min", min);
        	List<Map<String, Object>> list=vodlabeltypeService.findsequence(mapsequence);
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
        		VodLabeltype vodlabel1=new VodLabeltype();
        		vodlabel1.setId(Integer.parseInt(code));
        		vodlabel1.setSequence(min);
        		vodlabeltypeService.update(vodlabel1);
              }
      	 //编辑更改的信息
        	vodlabel.setId(id);
    	return this.vodlabeltypeService.update(vodlabel);
    }
    
   
    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "点播-phone-标签分类管理-删除操作")
    @ResponseBody
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    public int deletes(@RequestBody List<Integer> ids) {
    	Map<String, Object> queryParams = new HashMap<String, Object>();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("id_", ids);
        //查询排序的原始值
        List<Map<String,Object>> oldsequencelist=vodlabeltypeService.findsequencesum(map);
        int min=Integer.parseInt(oldsequencelist.get(0).get("sequence")==null?"1":oldsequencelist.get(0).get("sequence").toString());
      	map.put("min", min);
      	List<Map<String, Object>> list=vodlabeltypeService.findsequence(map);
        	Map<String, Object> map1 = new HashMap<>();
        	for(int i=0;i<list.size();i++){//自动更改排序递增递减
        		map1=list.get(i);
        		int curmin=Integer.parseInt(map1.get("sequence")==null?"1":map1.get("sequence").toString());
        		 int j=oldsequencelist.size() ;
        		 int  c=1;
        		 for(int jj=0;jj<j;jj++){
        			if(curmin>Integer.parseInt(oldsequencelist.get(jj).get("sequence")==null?"1":oldsequencelist.get(jj).get("sequence").toString())){
        				c=jj+1;
        			} 
        		}
        		 curmin-=c;
        		VodLabeltype vodlabel1=new VodLabeltype();
        		vodlabel1.setId(Integer.parseInt(map1.get("id").toString()));
        		vodlabel1.setSequence(curmin);
        		vodlabeltypeService.update(vodlabel1);
              }
         //删除
    	 if (ids == null || ids.size() == 0)
         {
    		 queryParams.put("id", ids);
         }else{
        	 queryParams.put("id_", ids);
         }
        return this.vodlabeltypeService.deletes(queryParams);
    }
    
}
