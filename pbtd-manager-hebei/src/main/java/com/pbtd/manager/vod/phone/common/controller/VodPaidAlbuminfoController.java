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
import com.pbtd.manager.vod.phone.common.domain.VodPaidSAlbuminfo;
import com.pbtd.manager.vod.phone.common.service.face.IVodPaidAlbumService;
import com.pbtd.manager.vod.phone.hotsearch.domain.VodHotSearchInfo;

/**
 * @author zr
 */
@Controller
@RequestMapping("/vod/phone/Vodpaidalbum")
public class VodPaidAlbuminfoController {

    @Autowired
    private IVodPaidAlbumService vodpaidalbumservice;
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
    	return "/vod/phone/common/vodpaidalbum/index";
    }

    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/phone/common/vodpaidalbum/create";
    }

    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id,Model model) {
        return "/vod/phone/common/vodpaidalbum/edit";
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
        return this.vodpaidalbumservice.count(RequestUtil.asMap(request, false));
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
    	List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodpaidalbumservice.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodpaidalbumservice.page(queryParams);
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
    public VodPaidSAlbuminfo load(@PathVariable("id") int id) {
    	VodPaidSAlbuminfo m = null;
        if (id > 0) {
        	m = this.vodpaidalbumservice.load(id);
        } 
        else {
        	m = new VodPaidSAlbuminfo( null,null,-1);
        }
        return m;
    }

    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "点播-phone-收费专辑管理-添加操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(VodPaidSAlbuminfo m, HttpServletRequest request) {
      this.vodpaidalbumservice.insert(m);
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
    @LogAnnotation(operationInfo = "点播-phone-收费专辑管理-更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, VodPaidSAlbuminfo m) throws ServletException {
    	return this.vodpaidalbumservice.update(m);
    }
    
   
    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "点播-phone-收费专辑管理-删除操作")
    @ResponseBody
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    public int deletes(@RequestBody List<Integer> ids) {
      Map<String, Object> queryParams = new HashMap<String, Object>();
      queryParams.put("id_", ids);
        return this.vodpaidalbumservice.deletes(queryParams);
    }
    @LogAnnotation(operationInfo = "点播-phone-收费专辑管理-添加操作")
	@RequestMapping("/addAll")
	@ResponseBody
	public int addAll(HttpServletRequest request){
		String albumids=request.getParameter("albumid");
    	String[] albumid=albumids.split(",");   
    	VodPaidSAlbuminfo m =new VodPaidSAlbuminfo();
    	Map<String, Object> map = new HashMap<>();
    	for(String idnew:albumid){
    		m.setSeriesCode(idnew);
        	map.put("seriesCode", idnew);
    		int i=vodpaidalbumservice.count(map);
    		if(i<1){
    			vodpaidalbumservice.insert(m);
    		}
          }
		 return 1;
	}
	 
}
