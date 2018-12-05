package com.pbtd.manager.live.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveChannelService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.QueryObject;
import com.pbtd.manager.vod.phone.special.domain.VodSpecial;


/**
 * 用户操作
 * 
 * @author JOJO
 *
 */
@RequestMapping("/live/Channel")
@Controller
public class LiveChannelController {
	@Autowired
	private ILiveChannelService liveChannelService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
    /**
     * GET 查询
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index() {
    	return "/live/channelManage/index";
    }
    
    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id) {
        return "/live/channelManage/edit";
    }
	/**
	 * 频道列表查询
	 * /live/Channel/queryChannellist
	 * @param qo
	 * @return
	 */
	@RequestMapping("/queryChannellist")
	@ResponseBody
	public PageResult LiveChannelInfo() {
		PageResult pageResult = null;
		try {
			pageResult = liveChannelService.querylistallLiveChannel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageResult;
	}
	   
    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
	@LogAnnotation(operationInfo = "直播-频道管理-删除操作")
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
        return this.liveChannelService.deletes(queryParams);
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
	@LogAnnotation(operationInfo = "直播-频道管理-添加操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(LiveChannel liveChannel, HttpServletRequest request) {
      this.liveChannelService.insert(liveChannel);
      return 1;
    }
	
	  /**
     * 模糊获取符合查询条件的分页记录
     * 
     * @param page 页码LiveChannel liveChannel
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     * /live/Version/page?page=1&rows=10
     */
    @ResponseBody
    @RequestMapping("/page")
    public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, @RequestParam(value="chnname")String chnname) {
    	List<LiveChannel> data = new ArrayList<LiveChannel>();
    	Map<String, Object> queryParams =new HashMap<String, Object>();
    	if(chnname != null && ! chnname.equals("")){
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		queryParams.put("chnname",  "%"+chnname+"%");
            int total = this.liveChannelService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveChannelService.find(queryParams);
    			return new PageResult(total, data);
    	}else{
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
    		
            int total = this.liveChannelService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.liveChannelService.find(queryParams);
    			return new PageResult(total, data);
    	}
    }
    
    /**
     * 根据标识获取记录
     *
     * @param id 标识
     * @return 记录
     */
    @ResponseBody
    @RequestMapping("/load/{id}")
    public LiveChannel load(@PathVariable("id") int id) {
    	LiveChannel liveChannel = null;
        if (id > 0) {
        	liveChannel = this.liveChannelService.load(id);
        }
        else {
        	liveChannel = new LiveChannel(
        			0
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
            );
        }
        return liveChannel;
    }
    
    /**
     * POST 编辑
     *
     * @param id 标识
     * @param vodChannel VodChannel实例
     * @return int 被修改的记录条数
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "直播-频道管理-更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id,LiveChannel liveChannel) throws ServletException {
        return this.liveChannelService.update(liveChannel);
    }
    
    
    /**
     * 根据版本号获取该有的上线频道
     * @param id 标识
     * @return 记录
     * /live/Channel/channelinterface/SD_CMCC_OTT_HUAWEI
     */
    @ResponseBody
    @RequestMapping("/channelinterface/{pro_id}")
    public List<LiveChannel> channelinterface(@PathVariable("pro_id") String str) {
    	List <LiveChannel> list = new ArrayList<>();
        if (str != null && str.length() != 0) {
        	try {
				list = this.liveChannelService.getchannel(str);
			} catch (Exception e) {
				logger.info("请输入正确的参数 ");
			}
        }else{
        	logger.info("请输入参数 pro_id");  
        }
        return list;
    }
    /**
     * 批量上线下线
     * @param request
     * @return
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "直播--频道管理-更改频道状态操作")
    @ResponseBody
    @RequestMapping(value = "/editzt")
    public int editzt(HttpServletRequest request) throws ServletException { 
    	String id=request.getParameter("id");
    	String[] ids=id.split(",");   
    	int result=0;
    	for(String idnew:ids){
    		int status=Integer.parseInt(request.getParameter("status"));
    		int id1=Integer.parseInt(idnew);
        	result=liveChannelService.updatezt(id1,status);
          }
		 return result;
    }
    
    @LogAnnotation(operationInfo = "直播--频道管理-更改专题图片操作")
    @ResponseBody
    @RequestMapping(value = "/editimg/{id}", method = RequestMethod.POST)
    public int editimg(@PathVariable("id") int id ,HttpServletRequest request) throws ServletException  {
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
    	String imgUrl=queryParams.get("imgUrl").toString();
    	String imgtype=queryParams.get("imgtype").toString();
    	LiveChannel liveChannel=new LiveChannel();
        if(imgtype.equals("imgurl")){
        	liveChannel.setPackagecover(imgUrl);
        }else{
        	liveChannel.setChannelcovertv(imgUrl);
        }
        liveChannel.setChannelid(id);
		return this.liveChannelService.updateimg(liveChannel);
    }
    
}