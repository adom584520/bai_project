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
import com.pbtd.manager.vod.phone.common.domain.Vodchannel;
import com.pbtd.manager.vod.phone.common.domain.Vodlabel;
import com.pbtd.manager.vod.phone.common.service.face.IVodLabelService;

/**
 * @author zr
 */
@Controller
@RequestMapping("/vod/phone/Vodlabel")
public class VodLabelController {

    @Autowired
    private IVodLabelService vodlabelService;
    
    
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
    		model.addAttribute("labeltypelist", dictionaryService.findDictionaryMap(params, "chooselabeltype"));
    	return "/vod/phone/common/vodlabel/index";
    }

    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/phone/common/vodlabel/create";
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
		model.addAttribute("labeltypelist", dictionaryService.findDictionaryMap(params, "chooselabeltype"));
        return "/vod/phone/common/vodlabel/edit";
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
        return this.vodlabelService.count(RequestUtil.asMap(request, false));
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
    	List<Vodlabel> data = new ArrayList<Vodlabel>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodlabelService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodlabelService.find(queryParams);
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
    public Vodlabel load(@PathVariable("id") int id) {
    	Vodlabel vodlabel = null;
        if (id > 0) {
        	vodlabel = this.vodlabelService.load(id);
        } 
        else {
        	vodlabel = new Vodlabel(-1, null, 0, 0, 0,
        			0, null, null, null, null, null,null);
        }
        return vodlabel;
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
    	List<Vodlabel> vodlabel = this.vodlabelService.find(queryParams);
    	if (vodlabel.size() == 1) {
    		return vodlabel.get(0).getId() == id ? 0 : 1;
    	} else {
    		return vodlabel.size();
    	}
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "点播-phone-标签管理-添加操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(Vodlabel vodlabel, HttpServletRequest request) {
    	Map<String,Object> queryParams=new HashMap();
    	int type=vodlabel.getType();
    	queryParams.put("type", type);
    	Map<String, Object> maxmap=vodlabelService.findmaxVSminsequence(queryParams);//查询更改排序的最大最小原始值
    	int curmax= 0;
    	if(maxmap!=null){
    		curmax=Integer.parseInt(maxmap.get("max")==null?"0":maxmap.get("max").toString());//原始数据排序最大值
    	}
    	vodlabel.setSequence(curmax+=1);
      this.vodlabelService.insert(vodlabel);
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
    @LogAnnotation(operationInfo = "点播-phone-标签管理-更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, Vodlabel vodlabel) throws ServletException {
    	 int newmax=vodlabel.getSequence();
    	 int type=vodlabel.getType();
         int newmin=0;
         int max=newmax;//排序最大值
     	int min=newmin;//排序最小值
     	Map<String, Object> mapsequence=new HashMap<>();
         mapsequence.put("id", Integer.toString(id));
         mapsequence.put("type",type);
         Map<String, Object> maxmap=vodlabelService.findmaxVSminsequence(mapsequence);//查询更改排序的最大最小原始值
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
     	List<Map<String, Object>> list=vodlabelService.findlabelsequence(mapsequence);
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
     		Vodlabel vodlabel1=new Vodlabel();
     		vodlabel1.setId(Integer.parseInt(code));
     		vodlabel1.setSequence(min);
     		vodlabel1.setStatus(-1);
     		vodlabelService.update(vodlabel1);
           }
     	//更改编辑的排序信息
     	vodlabel.setId(id);
        return this.vodlabelService.update(vodlabel);
    }
    
    /**
     * POST 批量上线下线
     *
     * @param id 标识
     * @param vodChannel VodChannel实例
     * @return int 被修改的记录条数
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "点播-phone-标签管理-更改状态操作")
    @ResponseBody
    @RequestMapping("/updateStatus")
    public int updateStatus(HttpServletRequest request) throws ServletException {
    	Map<String,Object> map=new HashMap<String,Object>();
    	int result=0;
    	String id=request.getParameter("id");
    	String[] ids=id.split(",");
    	for(String idNew:ids){
    		int id1=Integer.parseInt(idNew);
    		int status=Integer.parseInt(request.getParameter("status"));
    		map.put("id", id1);
    		map.put("status", status);
    		result=vodlabelService.updateStatus(map);
    	}    	  	
        return result;
    }

    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "点播-phone-标签管理-删除操作")
    @ResponseBody
    @RequestMapping(value = "/deletes", method = RequestMethod.GET)
	public int deletes(HttpServletRequest request) {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String ids = request.getParameter("id");
		String[] idarray = ids.split(",");
		List<Integer> idlist = new ArrayList<>();
		queryParams.put("id", ids);
		// 查询类型
		String type = vodlabelService.getType(queryParams);
		queryParams.put("type", type);
		// 查询更改排序的原始值
		List<Map<String, Object>> oldsequencelist = vodlabelService.findsequencesum(queryParams);
		int min = Integer.parseInt(oldsequencelist.get(0).get("sequence") == null ? "1"
				: oldsequencelist.get(0).get("sequence").toString());
		queryParams.put("min", min);
		List<Map<String, Object>> list = vodlabelService.findlabelsequence(queryParams);
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {// 自动更改排序递增递减
			map = list.get(i);
			int curmin = Integer.parseInt(map.get("sequence") == null ? "1" : map.get("sequence").toString());
			int j = oldsequencelist.size();
			int c = 1;
			for (int jj = 0; jj < j; jj++) {
				if (curmin > Integer.parseInt(oldsequencelist.get(jj).get("sequence") == null ? "1"
						: oldsequencelist.get(jj).get("sequence").toString())) {
					c = jj + 1;
				}
			}
			curmin -= c;
			Vodlabel vodlabel1 = new Vodlabel();
			vodlabel1.setId(Integer.parseInt(map.get("id").toString()));
			vodlabel1.setSequence(curmin);
			vodlabel1.setStatus(-1);
			vodlabelService.update(vodlabel1);
		}
		// 删除
		for (String num : idarray) {
			int id = Integer.parseInt(num);
			queryParams.put("id", id);
			  queryParams.clear();
			this.vodlabelService.deletes(queryParams, idlist);
		}
		return 1;
	}

    /**
     * GET 编辑
     *绑定频道
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/channel/{id}")
    public String channel(@PathVariable("id") int id,Model model) {
    	model.addAttribute("label", id);
        return "/vod/phone/common/vodlabel/channel";
    }
    @RequestMapping("/addchannel/{label}")
    public String addchannel(@PathVariable("label") int label,Model model) {
    	Map<String, Object> params=new HashMap<>();
    	model.addAttribute("label", label);
		model.addAttribute("labeltypelist", dictionaryService.findDictionaryMap(params, "choosechannel"));
        return "/vod/phone/common/vodlabel/addchannel";
    }
    
    @LogAnnotation(operationInfo = "点播-phone-标签管理-添加频道操作")
    @ResponseBody
    @RequestMapping("/addchannel")
    public int addchannel(HttpServletRequest request) throws ServletException {
    	Map<String,Object> map=new HashMap<String,Object>();
    	int result=0;
    	String id=request.getParameter("id");
    	String type=request.getParameter("type");
    	String label=request.getParameter("label");
    	String[] ids=id.split(",");
    	for(String idNew:ids){
    		int id1=Integer.parseInt(idNew);
    		map.put("channel", id1);
    		map.put("type", type);
    		map.put("label", label);
    		vodlabelService.deletelabelchannel(map);
    		result=vodlabelService.addlabelchannel(map);
    	}    	  	
        return result;
    }
    @LogAnnotation(operationInfo = "点播-phone-标签管理-删除频道操作")
    @ResponseBody
    @RequestMapping("/deletelabelchannel")
    public int deletelabelchannel (HttpServletRequest request) {
 	   Map<String, Object> queryParams = new HashMap<String, Object>();
 	  /*String id=queryParams.get("ids").toString();
 	   * */
 	  String id=request.getParameter("ids");
  	String[] ids=id.split(",");
  	Map<String, Object> map=new HashMap<>();
  	for(String idNew:ids){
  		int id1=Integer.parseInt(idNew);
		map.put("id", id1);
  		vodlabelService.deletelabelchannel(map);
  	}    
     return 1;
 }
    
    /**
     * 模糊获取符合查询条件的分页记录
    *绑定频道
     * @return 记录列表
     */
    @ResponseBody
    @RequestMapping("/pagechannel")
    public PageResult pagechannel(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
    	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodlabelService.countlabelchannel(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.vodlabelService.pagelabelchannel(queryParams);
		return new PageResult(total, data);
		
    }
    
}
