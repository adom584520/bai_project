package com.pbtd.manager.vod.tv.album.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.util.DateUtil;
import com.pbtd.manager.base.common.util.excelTools.ExcelUtils;
import com.pbtd.manager.base.common.util.excelTools.JsGridReportBase;
import com.pbtd.manager.base.common.util.excelTools.TableData;
import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PinYinUtil;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.tv.album.domain.VodTvAlbuminfo;
import com.pbtd.manager.vod.tv.album.service.face.IVodTvAlbuminfoService;
import com.pbtd.manager.util.SequenceUtil;


@Controller 
@RequestMapping("/vod/tv/vodalbuminfo")
public class VodTvAlbuminfoController {

    @Autowired
    private IVodTvAlbuminfoService vodTvAlbuminfoService;
    @Autowired
    private SequenceUtil  sequenceUtil;
    
    @Autowired
    private IDictionaryService dictionaryService;
    /**
     * GET 查询
     *
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index(Model model) {
    	Map<String, Object> queryParams=new HashMap<>();
    	queryParams.put("levels", 1);
    	
    	queryParams.put("navigationtype", 1);
    	model.addAttribute("channellist", dictionaryService.findDictionaryMap(queryParams, "choosechanneltv"));
    	queryParams.put("levels", 1);
    	queryParams.put("navigationtype", 2);
    	model.addAttribute("channellist2", dictionaryService.findDictionaryMap(queryParams, "choosechanneltv"));
    	return "/vod/tv/album/vodtvalbuminfo/index";
    }

    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/tv/album/vodtvalbuminfo/create";
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
    	params.put("status", 1);
    	params.put("navigationtype", 1);
    	model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechanneltv"));
    	params.put("levels", 1);
    	params.put("navigationtype", 2);
    	model.addAttribute("channellist2", dictionaryService.findDictionaryMap(params, "choosechanneltv"));
		model.addAttribute("cornerlist", dictionaryService.findDictionaryMap(params, "choosecorner"));
		model.addAttribute("feelist", dictionaryService.findDictionaryMap(params, "choosecollectfeesbag"));
        return "/vod/tv/album/vodtvalbuminfo/edit";
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
        return this.vodTvAlbuminfoService.count(RequestUtil.asMap(request, false));
    }

    /**
     * 模糊获取符合查询条件的分页记录
     * 专辑
     * @param page 页码
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     */
    @ResponseBody
    @RequestMapping("/page")
    public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
    	List<VodTvAlbuminfo> data = new ArrayList<VodTvAlbuminfo>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodTvAlbuminfoService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodTvAlbuminfoService.find(queryParams);
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
    public VodTvAlbuminfo load(@PathVariable("id") int id) {
    	VodTvAlbuminfo vodTvAlbuminfo = null;
        if (id > 0) {
        	vodTvAlbuminfo = this.vodTvAlbuminfoService.load(id);
        } 
        else {
        	vodTvAlbuminfo = new  VodTvAlbuminfo(-1);
        }
        return vodTvAlbuminfo;
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
    	List<VodTvAlbuminfo> vodTvAlbuminfo = this.vodTvAlbuminfoService.find(queryParams);
    	if (vodTvAlbuminfo.size() == 1) {
    		return vodTvAlbuminfo.get(0).getId() == id ? 0 : 1;
    	} else {
    		return vodTvAlbuminfo.size();
    	}
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "点播-tv-节目管理-添加专辑操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(VodTvAlbuminfo vodTvAlbuminfo, HttpServletRequest request) {
    	//添加拼音
    	if (vodTvAlbuminfo.getSeriesName() !=null && !"".equals(vodTvAlbuminfo.getSeriesName()) ) {
    		String name = vodTvAlbuminfo.getSeriesName();
    		// 清除掉所有特殊字符
    		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
    		Pattern p = Pattern.compile(regEx);
    		Matcher m = p.matcher(name);
    		name = m.replaceAll("").trim();
    		String pingYin = PinYinUtil.getPingYin(name).toLowerCase();
    		String headChar = PinYinUtil.getPinYinHeadChar(name).toLowerCase();
    		vodTvAlbuminfo.setPinyin(pingYin);
    		vodTvAlbuminfo.setPinyinsuoxie(headChar);
		}
      this.vodTvAlbuminfoService.insert(vodTvAlbuminfo);
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
    @LogAnnotation(operationInfo = "点播-tv-节目管理-更改专辑操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, VodTvAlbuminfo vodTvAlbuminfo,HttpServletRequest request) throws ServletException {
       vodTvAlbuminfo.setUpdate_user(request.getSession().getAttribute("username").toString() );
     //添加拼音
   	if (vodTvAlbuminfo.getSeriesName() !=null && !"".equals(vodTvAlbuminfo.getSeriesName()) ) {
   		String name = vodTvAlbuminfo.getSeriesName();
   		// 清除掉所有特殊字符
   		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
   		Pattern p = Pattern.compile(regEx);
   		Matcher m = p.matcher(name);
   		name = m.replaceAll("").trim();
   		String pingYin = PinYinUtil.getPingYin(name).toLowerCase();
   		String headChar = PinYinUtil.getPinYinHeadChar(name).toLowerCase();
   		vodTvAlbuminfo.setPinyin(pingYin);
   		vodTvAlbuminfo.setPinyinsuoxie(headChar);
		}
    	return this.vodTvAlbuminfoService.update(vodTvAlbuminfo);
    }
    /**
     * 批量上线下线
     * @Param seriesCode标识
     */
    @LogAnnotation(operationInfo = "点播-tv-节目管理-更改专辑状态操作")
    @ResponseBody
    @RequestMapping("/updatestatus")
    public int updatestatus(HttpServletRequest request) throws ServletException{
    	String code=request.getParameter("seriesCode");
    	String[] codes=code.split(",");
    	int result=0;
    	Map<String,Object> queryParams=new HashMap<String,Object>();
    	for(String num:codes){
    		int seriesCode=Integer.parseInt(num);
    		int status=Integer.parseInt(request.getParameter("status"));
    		queryParams.put("seriesCode", seriesCode);
    		queryParams.put("status",status);
    		result=this.vodTvAlbuminfoService.updatestatus(queryParams);
    	}
		return result;
    	
    }
    

    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "点播-tv-节目管理-删除专辑操作")
    @ResponseBody
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    public int deletes(@RequestBody List<Integer> ids) {
    	   Map<String, Object> queryParams = new HashMap<String, Object>();
    	 if (ids == null || ids.size() == 0)
         {
    		 queryParams.put("id", ids);
         }else{
        	 queryParams.put("id_", ids);
         }
        return this.vodTvAlbuminfoService.deletes(queryParams);
    }
    /**
     * 查看视图
     * @param id
     * @param model
     * @param request
     * @return
     */
    
	@RequestMapping("/show/{id}")
	public String edit(@PathVariable("id") String id,ModelMap model,HttpServletRequest request) {
		model.addAttribute("id",id);
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("seriesCode", id);
		List<Map<String, Object>> gitlist=vodTvAlbuminfoService.findAlbumsinfovideo(queryParams);
		model.addAttribute("gitlist",gitlist);
		return "/vod/tv/album/vodtvalbuminfo/show";
	}
	
	@RequestMapping("/showvideo/{id}")
	public String video(@PathVariable("id") String id,
			ModelMap model,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("id", id);
		List<Map<String, Object>> gitvideomap=vodTvAlbuminfoService.findAlbumsinfovideo(queryParams);
		model.addAttribute("map",gitvideomap.get(0));
		return "/vod/tv/album/vodtvalbuminfo/showvideo";
	}
	
	/**
	 * 编辑剧集
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/editvideo/{id}")
	public String editvideo(@PathVariable("id") String id,ModelMap model,HttpServletRequest request) {
		model.addAttribute("id",id);
		model.addAttribute("seriesCode",id);
		return "/vod/tv/album/vodtvalbuminfo/editvideo";
	}
	
    /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    @ResponseBody
    @RequestMapping("/loadvideo/{id}")
    public  Map<String, Object> loadvideo(@PathVariable("id") String id) {
    	Map<String, Object> video=new HashMap<String,Object>();
        if (!id.equals("0") && !id.equals("-1")) {
        	Map<String, Object> queryParams = new HashMap<>();
    		queryParams.put("id", id);
    		List<Map<String, Object>> gitvideomap=vodTvAlbuminfoService.findAlbumsinfovideo(queryParams);
    		if(gitvideomap.size()>0){
	    			video= gitvideomap.get(0);
    		}
    		else {
    	          video=new HashMap<String, Object>(null);
    	        }
        } 
        else {
          video=new HashMap<String, Object>(null);
        }
        return video;
    }
	
    /**
	 * 分页查询
	 * @param page
	 * @param limit
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/videopage/{id}")
	public PageResult videopage(@PathVariable("id")int id,@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("seriesCode", id);
		int total = this.vodTvAlbuminfoService.countAlbumsinfovideo(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.vodTvAlbuminfoService.findAlbumsinfovideo(queryParams);
		return new PageResult(total, data);
	}
	
	/**
	 * GET 编辑
	 *剧集新增
	 * @param id 标识
	 * @return 编辑剧集视图路径
	 */
		@RequestMapping("/video/edit/{seriesCode}/{code}")
		public String videocreate(@PathVariable("seriesCode")String seriesCode,@PathVariable("code")String code ,ModelMap model,HttpServletRequest request) {
			if("1".equals(code)){
				model.addAttribute("num","");
				}else{
					model.addAttribute("num",code);
				}
			model.addAttribute("seriesCode",seriesCode);
			model.addAttribute("feelist", dictionaryService.findDictionaryMap(null, "choosecollectfeesbag"));
		return "vod/tv/album/vodtvalbuminfo/videocreate";
	}
	
	/**
	 * 编辑剧集
	 * @param id标识
	 */
	@LogAnnotation(operationInfo = "点播-tv-节目管理-更改剧集操作")
	@RequestMapping("/updatevideo/{id}")
	@ResponseBody
	public int videoupdate(@PathVariable("id")String id,HttpServletRequest request)throws ServletException{
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		queryParams.put("id", id);
		queryParams.put("update_user",request.getSession().getAttribute("username").toString());
		return this.vodTvAlbuminfoService.updatevideo(queryParams);		
	}
	
	/**
	 * 插入记录
	 * *剧集新增
	 * @return 被插入的记录标识
	 */
	@LogAnnotation(operationInfo = "点播-tv-节目管理-添加剧集操作")
	@ResponseBody
	@RequestMapping(value = "/insertvideo/{seriesCode}", method = RequestMethod.POST)
	public int create(@PathVariable("seriesCode") String seriesCode,ModelMap model,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("seriesCode", seriesCode);
		queryParams.put("create_user",request.getSession().getAttribute("username").toString());
		this.vodTvAlbuminfoService.insertvideo(queryParams);
		return 1;
	}
	/**
	 * POST 剧集删除多条
	 * @param ids 标识列表
	 * @return 被删除的记录条数
	 */
	@LogAnnotation(operationInfo = "点播-tv-节目管理-删除剧集操作")
	@ResponseBody
	@RequestMapping(value = "/video/deletes", method = RequestMethod.POST)
	public int videodeletes( HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.vodTvAlbuminfoService.deletesvideo(queryParams);
	}
	/**
	 * 更改剧集图片
	 * @param id标识
	 */
	@LogAnnotation(operationInfo = "点播-tv-节目管理-更改剧集图片操作")
	@RequestMapping("/updatevideopic")
	@ResponseBody
	public int videoupdatepic(HttpServletRequest request)throws ServletException{
		String pic=request.getParameter("imgUrl");
		String  id= request.getParameter("id")==null?"": request.getParameter("id").toString();
		Map<String,Object> queryParams=new HashMap<String,Object>();
		queryParams.put("pic", pic);
		queryParams.put("id", id);
		return this.vodTvAlbuminfoService.updatevideo(queryParams);		
	}
	/**
	 *导演 演员
	 *
	 * @param id 标识
	 * @return 视图路径
	 */
	@RequestMapping("/actorslist")
	public String actorslist() {
		return "/vod/tv/album/vodtvalbuminfo/actorslist";
	}
	
	/**
	 * get 节目关联推荐节目
	 * @param id标识
	 * @return 视图路径
	 */
	@RequestMapping("/albuminforecommand/{id}")
	public String albuminforecommand(@PathVariable("id")int id){
		return "/vod/tv/album/vodtvalbuminfo/albuminfo";		
	}           
	/**
	 * 查询专辑关联推荐节目
	 */
	@ResponseBody
	@RequestMapping("/albuminfopage/{id}")
    public PageResult albuminfopage(@PathVariable("id")int id,@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request){
		List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
		Map<String,Object> queryParams=RequestUtil.asMap(request,false);
		queryParams.put("id", id);
		long total=vodTvAlbuminfoService.countalbum(queryParams);
		if (total == 0) {	
		return new PageResult(total, Collections.EMPTY_LIST);
	}	
	QueryObject qo=new QueryObject();	
	qo.setPage(page);	
	qo.setRows(limit);	
	queryParams.put("start", qo.getStart());	
	queryParams.put("pageSize", qo.getPageSize());	
		data =this.vodTvAlbuminfoService.pagealbum(queryParams);
		return new PageResult(total, data);		
	}
	
	/**
	 * get 添加关联推荐节目
	 * @param id标识
	 * @return 视图路径
	 */
	@RequestMapping("/addalbuminfor/{id}")
	public String addalbuminfor(@PathVariable("id")int id,Model model){
		Map<String,Object> queryParams=new HashMap<String,Object>();
		queryParams.put("levels", 1);
		
		queryParams.put("navigationtype", 1);
    	model.addAttribute("channellist", dictionaryService.findDictionaryMap(queryParams, "choosechanneltv"));
    	queryParams.put("levels", 1);
    	queryParams.put("navigationtype", 2);
    	model.addAttribute("channellist2", dictionaryService.findDictionaryMap(queryParams, "choosechanneltv"));
		return "/vod/tv/album/vodtvalbuminfo/addalbuminfo";		
	}

	/**
	 * 添加关联推荐节目
	 */
	@LogAnnotation(operationInfo = "点播-tv-节目管理-添加关联推荐操作")
	@RequestMapping("/addalbumrecommand")
	@ResponseBody
	public int addalbum(HttpServletRequest request){
	 int result=0;
	 Map<String,Object> queryParams=new HashMap<String,Object>();
	 String codes=request.getParameter("seriesCode");
	 String[]  code=codes.split(",");
  	 Map<String, Object> map = new HashMap<>();
 	 int curmax= 0;
 	
	 for(String num:code){
		 int id=Integer.parseInt(request.getParameter("id"));
		 int seriesCode=Integer.parseInt(num);
		 queryParams.put("id", id);
		 queryParams.put("status", 1);
		 Map<String, Object> maxmap=vodTvAlbuminfoService.findalbummaxVSminsequence( queryParams);//查询更改排序的最大最小原始值
     	if(maxmap!=null){
     		curmax=Integer.parseInt(maxmap.get("max")==null?"0":maxmap.get("max").toString());//原始数据排序最大值
     	}
     	 queryParams.put("sequence",curmax+=1);
     	 queryParams.put("seriesCode",seriesCode);
 		int i=vodTvAlbuminfoService.countalbum(queryParams);
 		if(i<1){
 			result=this.vodTvAlbuminfoService.addalbum(queryParams);
 		}
	 }		
		return 	result;	
	}
	/**
	 * 更改关联荐荐节目排序
	 */
	@LogAnnotation(operationInfo = "点播-tv-节目管理-更改关联推荐排序操作")		
	@ResponseBody
	@RequestMapping("/updatealbumrecommand")
	public int updatealbumsequence(HttpServletRequest request){
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		String id=request.getParameter("id");
    	String sequence=queryParams.get("sequence").toString() ;
    	String albumid=queryParams.get("seriesCode").toString();
    	String[] albumids=albumid.split(",");   
    	String[] sequences=sequence.split(",");
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
    	mapsequence.put("id", id);
    	Map<String, Object> maxmap=vodTvAlbuminfoService.findalbummaxVSminsequence(mapsequence);//查询更改排序的最大最小原始值
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
		List<Map<String, Object>> list=vodTvAlbuminfoService.findalbumsequence(mapsequence);
    	int result=0;
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
    		map.put("id",id);
    		map.put("seriesCode", seriesCode);
        	result=vodTvAlbuminfoService.updatealbumsequence(map);
          }
    	
    	for(int i=0;i<albumids.length;i++){
    		map.clear();
    		map.put("sequence", sequences[i]);
    		map.put("id",id);
    		map.put("seriesCode", albumids[i]);
    		 this.vodTvAlbuminfoService.updatealbumsequence(map);
          }
		return 1;
		
	}
	
	/**
	 * 删除关联推荐节目
	 */
	@LogAnnotation(operationInfo = "点播-tv-节目管理-删除关联推荐操作")
	@RequestMapping("/deletealbumrecommand")
	@ResponseBody
	public int deletalbum(HttpServletRequest request){
		int id=Integer.parseInt(request.getParameter("id"));
		int result=0;
		Map<String,Object> queryParams=new HashMap<String,Object>();
		String code=request.getParameter("seriesCode");
		String[] codes=code.split(",");
    	Map<String, Object> mapsequence=new HashMap<>();
    	mapsequence.put("albumid",code);
    	mapsequence.put("id", id);
    	//查询更改排序的原始值
    	List<Map<String, Object>>  oldsequencelist=vodTvAlbuminfoService.findalbumsequencesum(mapsequence);
    	int min=Integer.parseInt(oldsequencelist.get(0).get("sequence")==null?"1":oldsequencelist.get(0).get("sequence").toString());
    	mapsequence.put("min", min);
		List<Map<String, Object>> list=vodTvAlbuminfoService.findalbumsequence(mapsequence);
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
    		map.put("id",id);
    		map.put("seriesCode", seriesCode);
        	result=vodTvAlbuminfoService.updatealbumsequence(map);
          }
		for(String num:codes){
			int seriesCode=Integer.parseInt(num);
			queryParams.put("id", id);
			queryParams.put("seriesCode", seriesCode);
			result=this.vodTvAlbuminfoService.deletealbum(queryParams);
		}
		return 	result;	
	}
	
	/**
	 * 专辑绑定角标
	 * @param albumid
	 * @param cornerid
	 * @return
	 */
	@LogAnnotation(operationInfo = "点播-tv-节目管理-专辑绑定角标操作")
	@ResponseBody
	@RequestMapping("/updateAlbumCorner")
	public String updateAlbumCorner(@RequestParam(value="albumid")String albumid, @RequestParam(value="cornerid")String cornerid) {
		vodTvAlbuminfoService.updatecorner(albumid, cornerid);
		return null;
	}
	
	/**
	 * 专辑绑定付费包
	 * @param albumid
	 * @param cornerid
	 * @return
	 */
	@LogAnnotation(operationInfo = "点播-tv-节目管理-专辑绑定付费包操作")
	@ResponseBody
	@RequestMapping("/updateCollectfeesbag")
	public String updateCollectfeesbag(@RequestParam(value="albumid")String albumid, @RequestParam(value="collectid")String collectid) {
		vodTvAlbuminfoService.updatecollectfeesbag(albumid, collectid);
		return null;
	}
	
	 /**
     * 更改图片
     */
	@LogAnnotation(operationInfo = "点播-tv-节目管理-更改专辑图片操作")
	@ResponseBody
    @RequestMapping("/updatepic/{type}")
    public int updatePic1(@PathVariable("type")String type,HttpServletRequest request) throws ServletException {
    	String pic=request.getParameter("imgUrl");
    	int id=Integer.parseInt(request.getParameter("id"));
    	VodTvAlbuminfo vodalbuminfo=new VodTvAlbuminfo();
    	vodalbuminfo.setId(id);
    	if(type.equals("1")){
    		vodalbuminfo.setPictureurl1(pic);
    	}else if(type.equals("2")){
    		vodalbuminfo.setPictureurl2(pic);
    	}else if(type.equals("3")){
    		vodalbuminfo.setPictureurl3(pic);
    	}else if(type.equals("4")){
    		vodalbuminfo.setPictureurl4(pic);
    	}else{
    		vodalbuminfo.setPictureurl1(pic);
    	}
    	vodalbuminfo.setScore(-1);
        return this.vodTvAlbuminfoService.update(vodalbuminfo);
    }
    
	/**
	    *导出
	    * @param vodChannel VodChannel实例
	    */
		@LogAnnotation(operationInfo = "点播-tv-节目管理-导出操作")
	   @ResponseBody
	   @RequestMapping(value = "/exportalbum")
	   public void exportalbum( HttpServletRequest request,HttpServletResponse response) {
			List<VodTvAlbuminfo> data = new ArrayList<VodTvAlbuminfo>();
	   	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
	       data = this.vodTvAlbuminfoService.find(queryParams);
	       String[] fields = new String[9];
			String[] titles = new String[9];
			fields[0] = "cpName";
			titles[0] = "cp源";
			fields[1] = "seriesCode";
			titles[1] = "唯一标识";
			fields[2] = "seriesName";
			titles[2] = "名称";
			fields[3] = "actorName";
			titles[3] = "导演";
			fields[4] = "writerName";
			titles[4] = "演员";
			fields[5] = "channelName";
			titles[5] = "频道";
			fields[6] = "labelName";
			titles[6] = "标签";
			fields[7] = "volumncount";
			titles[7] = "总集数";
			fields[8] = "currentnum";
			titles[8] = "更新剧集";
		String subject = "tv节目--"+DateUtil.format(new Date(),DateUtil.dateFormat5);
			TableData td = ExcelUtils.createTableData(data, ExcelUtils.createTableHeader(titles),fields);
			try {
				JsGridReportBase report = new JsGridReportBase(request, response);
				report.exportToExcel(subject, " ", td);
			} catch (Exception e) {
				e.printStackTrace();
			} 
	   }
}
