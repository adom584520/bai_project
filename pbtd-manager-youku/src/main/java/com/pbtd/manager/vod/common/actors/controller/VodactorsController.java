package com.pbtd.manager.vod.common.actors.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.PinYinUtil;
import com.pbtd.manager.vod.common.actors.domain.Vodactors;
import com.pbtd.manager.vod.common.actors.service.face.IVodactorsService;
import com.pbtd.manager.vod.common.corner.domain.VodCollectfeesbag;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;

/**
 * vod_actors( ) 控制器
 * @author zr
 */
@Controller("integrate.VodactorsController")
@RequestMapping("/vod/vodactors")
public class VodactorsController {

    @Autowired
    private IVodactorsService vodactorsService;
    
    @Autowired
    private IDictionaryService dictionaryService;
    
    
    /**
     * GET 查询
     *
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index() {
    	return "/vod/actors/index";
    }

    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/actors/create";
    }

    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id) {
        return "/vod/actors/edit";
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
        return this.vodactorsService.count(RequestUtil.asMap(request, false));
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
    	List<Vodactors> data = new ArrayList<Vodactors>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodactorsService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodactorsService.find(queryParams);
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
    public Vodactors load(@PathVariable("id") int id) {
    	Vodactors vodactors = null;
        if (id > 0) {
        	vodactors = this.vodactorsService.load(id);
        } 
        else {
        	vodactors = new Vodactors(-1, null, null, null, null, null,null,null, null, null, null,null,null,null,null);
        }
        return vodactors;
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
    	List<Vodactors> vodactors = this.vodactorsService.find(queryParams);
    	if (vodactors.size() == 1) {
    		return vodactors.get(0).getId() == id ? 0 : 1;
    	} else {
    		return vodactors.size();
    	}
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "点播-演员管理-新增操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(Vodactors vodactors, HttpServletRequest request) {
    	//添加拼音
    	if (vodactors.getName() !=null && !"".equals(vodactors.getName()) ) {
    		String name = vodactors.getName();
    		// 清除掉所有特殊字符
    		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
    		Pattern p = Pattern.compile(regEx);
    		Matcher m = p.matcher(name);
    		name = m.replaceAll("").trim();
    		String pingYin = PinYinUtil.getPingYin(name).toLowerCase();
    		String headChar = PinYinUtil.getPinYinHeadChar(name).toLowerCase();
    		vodactors.setPinyin(pingYin);
    		vodactors.setPinyinsuoxie(headChar);
		}
      this.vodactorsService.insert(vodactors);
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
    @LogAnnotation(operationInfo = "点播-演员管理-更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, Vodactors vodactors) throws ServletException {
    	//添加拼音
    	if (vodactors.getName() !=null && !"".equals(vodactors.getName()) ) {
    		String name = vodactors.getName();
    		// 清除掉所有特殊字符
    		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
    		Pattern p = Pattern.compile(regEx);
    		Matcher m = p.matcher(name);
    		name = m.replaceAll("").trim();
    		String pingYin = PinYinUtil.getPingYin(name).toLowerCase();
    		String headChar = PinYinUtil.getPinYinHeadChar(name).toLowerCase();
    		vodactors.setPinyin(pingYin);
    		vodactors.setPinyinsuoxie(headChar);
		}
        return this.vodactorsService.update(vodactors);
    }

    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "点播-演员管理-删除操作")
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
        return this.vodactorsService.deletes(queryParams);
    }
    
    @LogAnnotation(operationInfo = "点播-演员管理-更改图片操作")
    @ResponseBody
    @RequestMapping(value = "/editimg/{id}", method = RequestMethod.POST)
    public int editimg(@PathVariable("id") int id ,HttpServletRequest request) throws ServletException  {
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    	String imgUrl=queryParams.get("imgUrl").toString();
    	String imgtype=queryParams.get("imgtype").toString();
        Vodactors vodactors=new Vodactors();
        if(imgtype.equals("imgportrait")){
        	vodactors.setImgportrait(imgUrl);
        }else{
        	vodactors.setBackgroundimg(imgUrl);
        }
        vodactors.setId(id);
		return this.vodactorsService.update(vodactors);
    }
    
}
