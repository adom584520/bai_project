package com.pbtd.manager.vod.phone.album.controller;

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
import com.pbtd.manager.util.SequenceUtil;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.controller.VodinterfaceController;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.phone.album.domain.Vodalbuminfo;
import com.pbtd.manager.vod.phone.album.service.face.IVodAlbuminfoService;
/**
 * @author zr
 */
@Controller 
@RequestMapping("/vod/phone/vodalbuminfo")
public class VodAlbuminfoController {

    @Autowired
    private IVodAlbuminfoService vodAlbuminfoService;
    
    
    @Autowired
    private VodinterfaceController vodinterfaceController;
    
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
    	params.put("navigationtype", 1);
    	model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));
    	params.put("levels", 1);
    	params.put("navigationtype", 2);
    	model.addAttribute("channellist2", dictionaryService.findDictionaryMap(params, "choosechannel"));
    	model.addAttribute("sourceTypelist", dictionaryService.findDictionaryMap(params,"choosesourcetype"));
    	model.addAttribute("ccmp_url",vodinterfaceController.ccmp_url);
    	return "/vod/phone/album/vodalbuminfo/index";
    }

    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/phone/album/vodalbuminfo/create";
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
    	params.put("navigationtype", 1);
		model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));
		model.addAttribute("cornerlist", dictionaryService.findDictionaryMap(params, "choosecorner"));
		model.addAttribute("feelist", dictionaryService.findDictionaryMap(params, "choosecollectfeesbag"));

		params.put("levels", 1);
    	params.put("navigationtype", 2);
    	model.addAttribute("channellist2", dictionaryService.findDictionaryMap(params, "choosechannel"));
        return "/vod/phone/album/vodalbuminfo/edit";
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
        return this.vodAlbuminfoService.count(RequestUtil.asMap(request, false));
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
    	List<Vodalbuminfo> data = new ArrayList<Vodalbuminfo>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    	String injectState=queryParams.get("injectstatus")==null?"":queryParams.get("injectstatus").toString();
    	if(!"".equals(injectState)){
    		queryParams.put("hwInjectState", Integer.parseInt(injectState));
    	}
        int total = this.vodAlbuminfoService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodAlbuminfoService.find(queryParams);
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
    public Vodalbuminfo load(@PathVariable("id") int id) {
    	Vodalbuminfo vodalbuminfo = null;
        if (id > 0) {
        	vodalbuminfo = this.vodAlbuminfoService.load(id);
        } 
        else {
        	vodalbuminfo = new  Vodalbuminfo(-1);
        }
        return vodalbuminfo;
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
    	List<Vodalbuminfo> vodalbuminfo = this.vodAlbuminfoService.find(queryParams);
    	if (vodalbuminfo.size() == 1) {
    		return vodalbuminfo.get(0).getId() == id ? 0 : 1;
    	} else {
    		return vodalbuminfo.size();
    	}
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "点播-phone-节目管理-新增专辑操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(Vodalbuminfo vodalbuminfo, HttpServletRequest request) {
      this.vodAlbuminfoService.insert(vodalbuminfo);
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
    @LogAnnotation(operationInfo = "点播-phone-节目管理-更改专辑操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, Vodalbuminfo vodalbuminfo,HttpServletRequest request) throws ServletException {
    	vodalbuminfo.setUpdate_user(request.getSession().getAttribute("username").toString() );
    	//添加拼音
    	if (vodalbuminfo.getSeriesName() !=null && !"".equals(vodalbuminfo.getSeriesName()) ) {
    		String name = vodalbuminfo.getSeriesName();
    		// 清除掉所有特殊字符
    		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
    		Pattern p = Pattern.compile(regEx);
    		Matcher m = p.matcher(name);
    		name = m.replaceAll("").trim();
    		String pingYin = PinYinUtil.getPingYin(name).toLowerCase();
    		String headChar = PinYinUtil.getPinYinHeadChar(name).toLowerCase();
    		vodalbuminfo.setPinyin(pingYin);
    		vodalbuminfo.setPinyinsuoxie(headChar);
		}
        return this.vodAlbuminfoService.update(vodalbuminfo);
    }
    
    /**
     * 更改PC竖图片
     */
    @LogAnnotation(operationInfo = "点播-phone-节目管理-更改专辑图片操作")
    @ResponseBody
    @RequestMapping("/updatepic")
    public int updatePic1(HttpServletRequest request) throws ServletException {
       	String pictureurl=request.getParameter("imgUrl");
       	int picType=Integer.parseInt(request.getParameter("imgtype"));
    	int id=Integer.parseInt(request.getParameter("id"));
    	Vodalbuminfo vodalbuminfo=new Vodalbuminfo();
    	vodalbuminfo.setId(id);
    	if(picType==1){
    		vodalbuminfo.setPictureurl1(pictureurl);
    	}
    	if(picType==2){
    		vodalbuminfo.setPictureurl2(pictureurl);
    	}
    	if(picType==3){
    		vodalbuminfo.setPictureurl3(pictureurl);
    	}
    	if(picType==4){
    		vodalbuminfo.setPictureurl4(pictureurl);
    	}   	
    	if(picType==5){
    		vodalbuminfo.setPictureurl5(pictureurl);
    	} 
    	if(picType==6){
    		vodalbuminfo.setPictureurl6(pictureurl);
    	}  
    	if(picType==7){
    		vodalbuminfo.setPictureurl7(pictureurl);
    	} 
    	if(picType==8){
    		vodalbuminfo.setPictureurl8(pictureurl);
    	} 
        return this.vodAlbuminfoService.update(vodalbuminfo);
    }
    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "点播-phone-节目管理-删除专辑操作")
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
        return this.vodAlbuminfoService.deletes(queryParams);
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
		List<Map<String, Object>> gitlist=vodAlbuminfoService.findAlbumsinfovideo(queryParams);
		model.addAttribute("gitlist",gitlist);
		return "/vod/phone/album/vodalbuminfo/show";
	}
	
	@RequestMapping("/showvideo/{id}")
	public String video(@PathVariable("id") String id,
		ModelMap model,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("id", id);
		List<Map<String, Object>> gitvideomap=vodAlbuminfoService.findAlbumsinfovideo(queryParams);
		model.addAttribute("map",gitvideomap.get(0));
		return "/vod/phone/album/vodalbuminfo/showvideo";
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
		model.addAttribute("ccmp_url",vodinterfaceController.ccmp_url);
		return "/vod/phone/album/vodalbuminfo/editvideo";
	}
	
    /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    @ResponseBody
    @RequestMapping("/loadvideo/{id}")
    public Map<String, Object> loadvideo(@PathVariable("id") String id) {
    	Map<String, Object> video=new HashMap<String,Object>();
        if (!id.equals("0") && !id.equals("-1")) {
        	Map<String, Object> queryParams = new HashMap<>();
    		queryParams.put("id", id);
    		List<Map<String, Object>> gitvideomap=vodAlbuminfoService.findAlbumsinfovideo(queryParams);
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
		int total = this.vodAlbuminfoService.countAlbumsinfovideo(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.vodAlbuminfoService.findAlbumsinfovideo(queryParams);
		return new PageResult(total, data);
	}
	
	/**
	 * GET 编辑
	 *剧集新增
	 * @param id 标识
	 * @return 编辑剧集视图路径
	 */
	@RequestMapping("/video/edit/{seriesCode}/{code}")
	public String videocreate(@PathVariable("seriesCode")String seriesCode ,@PathVariable("code")String code ,ModelMap model,HttpServletRequest request) {
		if("0".equals(code)){
			model.addAttribute("num","");
			}else{
				model.addAttribute("num",code);
			}
		model.addAttribute("seriesCode",seriesCode);
		model.addAttribute("feelist", dictionaryService.findDictionaryMap(null, "choosecollectfeesbag"));
		return "/vod/phone/album/vodalbuminfo/videocreate";
	}
	/**
	 * 新增剧集
	 * 
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-新增剧集操作")
	@RequestMapping("/insertvideo/{seriesCode}")
	@ResponseBody
	public int videoinsert(@PathVariable("seriesCode")int seriesCode,HttpServletRequest request)throws ServletException{
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		queryParams.put("seriesCode", seriesCode);
		queryParams.put("create_user",request.getSession().getAttribute("username") );
		return this.vodAlbuminfoService.insertvideo(queryParams);	
	}
	
	/**
	 * 编辑剧集
	 * @param id标识
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-更改剧集操作")
	@RequestMapping("/updatevideo/{id}")
	@ResponseBody
	public int videoupdate(@PathVariable("id")String id,HttpServletRequest request)throws ServletException{
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		queryParams.put("id", id);
		queryParams.put("update_user",request.getSession().getAttribute("username") );
		return this.vodAlbuminfoService.updatevideo(queryParams);		
	}
	
	/**
	 * 更改剧集图片
	 * @param id标识
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-更改剧集图片操作")
	@RequestMapping("/updatevideopic")
	@ResponseBody
	public int videoupdatepic(HttpServletRequest request)throws ServletException{
		String pic=request.getParameter("imgUrl");
		String  id= request.getParameter("id")==null?"": request.getParameter("id").toString();
		Map<String,Object> queryParams=new HashMap<String,Object>();
		queryParams.put("pic", pic);
		queryParams.put("id", id);
		return this.vodAlbuminfoService.updatevideo(queryParams);		
	}

	/**
	 * POST 剧集删除多条
	 * @param ids 标识列表
	 * @return 被删除的记录条数
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-删除剧集操作")
	@ResponseBody
	@RequestMapping(value = "/video/deletes", method = RequestMethod.POST)
	public int videodeletes( HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.vodAlbuminfoService.deletesvideo(queryParams);
	}
	
	
	/**
	 * POST 更改剧集是否显示
	 * @param ids 标识列表
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-更改剧集是否显示操作")
	@ResponseBody
	@RequestMapping(value = "/video/updateisshow", method = RequestMethod.POST)
	public int videoupdateisshow( HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.vodAlbuminfoService.updateisshow(queryParams);
	}
	/**
	 * POST 更改剧集是否收费
	 * @param ids 标识列表
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-更改剧集是否收费操作")
	@ResponseBody
	@RequestMapping(value = "/video/updatevideopaid", method = RequestMethod.POST)
	public int updatevideopaid( HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.vodAlbuminfoService.updatevideopaid(queryParams);
	}
	
	
	/**
	 *导演 演员
	 *
	 * @param id 标识
	 * @return 视图路径
	 */
	@RequestMapping("/actorslist")
	public String actorslist() {
		return "/vod/phone/album/vodalbuminfo/actorslist";
	}
	
	/**
	 * get 节目关联推荐节目
	 * @param id标识
	 * @return 视图路径
	 */
	@RequestMapping("/albuminforecommand/{id}")
	public String albuminforecommand(@PathVariable("id")int id){
		return "/vod/phone/album/vodalbuminfo/albuminfo";		
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
		long total=vodAlbuminfoService.countalbum(queryParams);
		if (total == 0) {	
		return new PageResult(total, Collections.EMPTY_LIST);
	}	
	QueryObject qo=new QueryObject();	
	qo.setPage(page);	
	qo.setRows(limit);	
	queryParams.put("start", qo.getStart());	
	queryParams.put("pageSize", qo.getPageSize());	
		data =this.vodAlbuminfoService.pagealbum(queryParams);
		return new PageResult(total, data);		
	}
	
	/**
	 * get 添加关联推荐节目
	 * @param id标识
	 * @return 视图路径
	 */
	
	@RequestMapping("/addalbuminfor/{id}")
	public String addalbuminfor(@PathVariable("id")int id,Model model){
		Map<String, Object> params=new HashMap<>();
    	params.put("levels", 1);
    	params.put("navigationtype", 1);
		model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));
		params.put("levels", 1);
    	params.put("navigationtype", 2);
    	model.addAttribute("channellist2", dictionaryService.findDictionaryMap(params, "choosechannel"));
		return "/vod/phone/album/vodalbuminfo/addalbuminfo";		
	}

	/**
	 * 添加关联推荐节目
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-添加关联推荐操作")
	@RequestMapping("/addalbumrecommand")
	@ResponseBody
	public int addalbum(HttpServletRequest request){
	 int result=0;
	 Map<String,Object> queryParams=new HashMap<String,Object>();
	 String codes=request.getParameter("seriesCode");
	 String[]  code=codes.split(",");
	 int curmax= 0;	 
	 for(String num:code){
		 int id=Integer.parseInt(request.getParameter("id"));
		 int seriesCode=Integer.parseInt(num);
		 queryParams.put("id", id);
		 queryParams.put("seriesCode", seriesCode);
		 queryParams.put("status", 1);
		 Map<String, Object> maxmap=vodAlbuminfoService.findalbummaxVSminsequence(queryParams);//查询更改排序的最大最小原始值
	      	if(maxmap!=null){
	     		curmax=Integer.parseInt(maxmap.get("max")==null?"0":maxmap.get("max").toString());//原始数据排序最大值
	     	}
	      	 queryParams.put("sequence",curmax+=1);
	 		int i=vodAlbuminfoService.countalbum(queryParams);
	 		if(i<1){
	 			vodAlbuminfoService.addalbum(queryParams);
	 		}
		 }			
		return 	result;	
	}
	/**
	 * 更改关联荐荐节目排序
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-更改关联推荐排序操作")
	@ResponseBody
	@RequestMapping("/updatealbumrecommand")
	public int updatealbumsequence(HttpServletRequest request){
		Map<String,Object> queryParams=RequestUtil.asMap(request);
		String id=request.getParameter("id");
    	String sequence=request.getParameter("sequence");
    	String albumid=request.getParameter("seriesCode");
    	String[] albumids=albumid.split(",");   
    	String[] sequences =sequence.split(",");
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
    	Map<String, Object> maxmap=vodAlbuminfoService.findalbummaxVSminsequence(mapsequence);//查询更改排序的最大最小原始值
    	int curmax= 0;int curmin=0;
    	if(maxmap!=null){
    		curmax= Integer.parseInt(maxmap.get("max")==null?"0":maxmap.get("max").toString());//原始数据排序最大值
    		curmin= Integer.parseInt(maxmap.get("min")==null?"0":maxmap.get("min").toString());//原始数据排序最小值
    	}
    if(newmax<curmax){////如果原始数据最大值比更改排序的最大值大  复制为最大值
    	max=curmax;
    }
    if(newmin>curmin){//如果原始数据最小值比更改排序的最小值小  复制为最小值
    	min=curmin;
    }
    	mapsequence.put("max", max);
    	mapsequence.put("min", min);
		List<Map<String, Object>> list=vodAlbuminfoService.findalbumsequence(mapsequence);
    	int result=0;
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
     		map.clear();
     		map.put("sequence", min);
     		map.put("id",id);
     		map.put("seriesCode", seriesCode);		 
		    result=vodAlbuminfoService.updatealbumsequence(map);		
	}
    	for(int i=0;i<albumids.length;i++){//更改编辑保存的排序信息
    		map.clear();
    		map.put("sequence", sequences[i]);
    		map.put("id",id);
    		map.put("seriesCode", albumids[i]);
        	result=vodAlbuminfoService.updatealbumsequence(map);
          }
		 return result;
		
	}
	
	/**
	 * 删除关联推荐节目
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-删除剧集操作")
	@RequestMapping("/deletealbumrecommand")
	@ResponseBody
	public int deletalbum(HttpServletRequest request){
		String ids=request.getParameter("id");
		int result=0;
		Map<String,Object> queryParams=new HashMap<String,Object>();
		String code=request.getParameter("seriesCode");
		String[] codes=code.split(",");
    	Map<String, Object> mapsequence=new HashMap<>();
    	mapsequence.put("albumid",code);
    	mapsequence.put("id", ids);
    	//查询更改排序的原始值
    	List<Map<String, Object>>  oldsequencelist=vodAlbuminfoService.findalbumsequencesum(mapsequence);
    	int min=Integer.parseInt(oldsequencelist.get(0).get("sequence")==null?"1":oldsequencelist.get(0).get("sequence").toString());
    	mapsequence.put("min", min);
		List<Map<String, Object>> list=vodAlbuminfoService.findalbumsequence(mapsequence);
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
    		map.put("id",ids);
    		map.put("seriesCode", seriesCode);
        	result=vodAlbuminfoService.updatealbumsequence(map);
          }
		
		//删除
		for(String num:codes){
			int seriesCode=Integer.parseInt(num);
			int id=Integer.parseInt(ids);
			queryParams.put("id", id);
			queryParams.put("seriesCode", seriesCode);
			result=this.vodAlbuminfoService.deletealbum(queryParams);
		}
		return 	result;	
	}
	
	/**
	 * 专辑绑定角标
	 * @param albumid
	 * @param cornerid
	 * @return
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-专辑绑定角标操作")
	@ResponseBody
	@RequestMapping("/updateAlbumCorner")
	public String updateAlbumCorner(@RequestParam(value="albumid")String albumid, @RequestParam(value="cornerid")String cornerid) {
		System.out.println("专辑id: "+albumid);
		System.out.println("角标id: "+cornerid);
		vodAlbuminfoService.updatecorner(albumid, cornerid);
		return null;
	}
	
	/**
	 * 专辑绑定付费包
	 * @param albumid
	 * @param cornerid
	 * @return
	 */
	@LogAnnotation(operationInfo = "点播-节目管理-专辑绑定付费包操作")
	@ResponseBody
	@RequestMapping("/updateCollectfeesbag")
	public String updateCollectfeesbag(@RequestParam(value="albumid")String albumid, @RequestParam(value="collectid")String collectid) {
		System.out.println("专辑id: "+albumid);
		System.out.println("付费包id: "+collectid);
		vodAlbuminfoService.updatecollectfeesbag(albumid, collectid);
		return null;
	}
	
	 /**
     *批量更改上下线
     * @param vodChannel VodChannel实例
     */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-更改专辑状态操作")
    @ResponseBody
    @RequestMapping(value = "/updateStatus")
    public int updateStatus(  HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String,Object>();
       	int result=0;
       	String id=request.getParameter("id");
       	String[] ids=id.split(",");
       	Vodalbuminfo vodalbuminfo=new Vodalbuminfo();
       	if(request.getParameter("status")!=null){
   			int status=Integer.parseInt(request.getParameter("status"));
   			vodalbuminfo.setStatus(status);
   		}else{
   			int tvstatus=Integer.parseInt(request.getParameter("tvstatus"));
   			vodalbuminfo.setTvstatus(tvstatus);
   		}
       	for(String idNew:ids){
       		Integer id1=Integer.parseInt(idNew);
       		vodalbuminfo.setId(id1);
       		result=vodAlbuminfoService.updatestatus(vodalbuminfo);
       	}   	
   		return result;    
    }
	
	 /**
     *导出
     * @param vodChannel VodChannel实例
     */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-导出操作")
    @ResponseBody
    @RequestMapping(value = "/exportalbum")
    public void exportalbum( HttpServletRequest request,HttpServletResponse response) {
		List<Vodalbuminfo> data = new ArrayList<Vodalbuminfo>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        data =this.vodAlbuminfoService.find(queryParams);
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
	String subject = "phone节目--"+DateUtil.format(new Date(),DateUtil.dateFormat5);
		TableData td = ExcelUtils.createTableData(data, ExcelUtils.createTableHeader(titles),fields);
		try {
			JsGridReportBase report = new JsGridReportBase(request, response);
			report.exportToExcel(subject, " ", td);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    }
	
	/**
	 * 剧集更改是否正片状态操作
	 * @param seriesCode
	 * @param 
	 * @return
	 */
	@LogAnnotation(operationInfo = "点播-phone-节目管理-编辑剧集-是否正片编辑操作")
	@ResponseBody
	@RequestMapping(value="/video/updateisPositive",method = RequestMethod.POST)
	public int updateisPositive(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return vodAlbuminfoService.updateisPositive(queryParams);
	}
}


