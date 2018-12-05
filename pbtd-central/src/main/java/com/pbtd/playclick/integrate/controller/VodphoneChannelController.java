package com.pbtd.playclick.integrate.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.pbtd.playclick.base.common.mvc.MutilCustomDateEditor;
import com.pbtd.playclick.base.common.web.RequestUtil;
import com.pbtd.playclick.integrate.domain.VodChannel;
import com.pbtd.playclick.integrate.service.face.IDictionaryService;
import com.pbtd.playclick.integrate.service.face.IVodphoneChannelService;
import com.pbtd.playclick.page.PageResult;
import com.pbtd.playclick.page.QueryObject;

/**
 * VodphoneChannelController( ) 控制器
 *
 * @author admin
 */
@Controller("integrate.VodphoneChannelController")
@RequestMapping("/integrate/vodphonechannel")
public class VodphoneChannelController {

    @Autowired
    private IVodphoneChannelService vodChannelService;
    
    @Autowired
    private IDictionaryService dictionaryService;
    
    @Autowired
    private  centralController central;
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new MutilCustomDateEditor("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd" ));
    }

    /**
     * GET 查询
     *
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index() {
    	return "/vod/vodphonechannel/index";
    }

    /**
     * GET 创建
     *
     * @return 视图路径
     */
    @RequestMapping("/create")
    public String create() {
        return "/vod/vodphonechannel/create";
    }

    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id) {
        return "/vod/vodphonechannel/edit";
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
        return this.vodChannelService.count(RequestUtil.asMap(request, false));
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
    	List<VodChannel> data = new ArrayList<VodChannel>();
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
        int total = this.vodChannelService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		VodChannel d=new VodChannel();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
			data =this.vodChannelService.find(queryParams);
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
    public VodChannel load(@PathVariable("id") int id) {
        VodChannel vodChannel = null;
        if (id > 0) {
            vodChannel = this.vodChannelService.load(id);
        } 
        else {
        	Map<String, Object> queryParams =new HashMap<>();
        	queryParams.put("levels", 1);
        	int c;
			try {
				c = this.vodChannelService.generatePosition(queryParams);
			} catch (Exception e) {
				e.printStackTrace();
				c=10;
			}
        	 
            vodChannel = new VodChannel(
            	-1,
            	null,	
            	null,
            	1,	
            	c+"",	
            	null,	
            	null,	
            	null,	
            	null,	
            	null,	
            	null,	
            	null,	
            	null,	
            	null,	
            	null,	
            	null,	
            	null,	
            	null,null,0

	
            );
        }
        return vodChannel;
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
    	List<VodChannel> vodChannels = this.vodChannelService.find(queryParams);
    	if (vodChannels.size() == 1) {
    		return vodChannels.get(0).getId() == id ? 0 : 1;
    	} else {
    		return vodChannels.size();
    	}
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(VodChannel vodChannel, HttpServletRequest request) {
      this.vodChannelService.insert(vodChannel);
      final int levels=vodChannel.getLevels();
      	try {
			if(levels==1){
			         central.gethttp(central.phone_channel);
			         central.gethttp(central.tv_channel);
			  	}else{
			  		  central.gethttp(central.phone_label);
			  	}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
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
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, VodChannel vodChannel) throws ServletException {
        return this.vodChannelService.update(vodChannel);
    }

    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
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
        return this.vodChannelService.deletes(queryParams);
    }
    /**
     * 更改排序
     * @param id 标识
     * @param request 查询参数
     */
    @RequestMapping("/save")
    @ResponseBody
    public int save(@RequestBody Map<String,Object> map) {
    	return this.vodChannelService.save(map);
    	 
    }
    /**
     * 更改上线及下线状态
     * @param id 标识
     * @param request 查询参数
     */
    @RequestMapping("/savestatus")
    @ResponseBody
    public int savestatus(@RequestBody Map<String,Object> map) {
    	String ids =  map.get("ids").toString();
    	//@RequestParam("ids") int ids,@RequestParam("type") int type,
    	String type=map.get("type")==null?"-1":map.get("type").toString();
    	String[] id=ids.substring(1, ids.length()-1).trim().split(",");    			
    	VodChannel vodChannel =new VodChannel();
    	for (String i : id) {
    		 vodChannel.setId(Integer.parseInt(i.trim()));
    		 vodChannel.setStatus(Integer.parseInt(type));
        	 vodChannel.setLevels(-1);
        	 vodChannel.setPackagecount(-1);
        	 vodChannel.setSequence(-1);
        	 vodChannel.setBj(-1);	
        	 this.vodChannelService.update(vodChannel);
		}
    	 
    	return 1;
    	 
    }
    
}
