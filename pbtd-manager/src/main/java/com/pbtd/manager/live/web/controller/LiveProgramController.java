package com.pbtd.manager.live.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.pbtd.manager.live.domain.LiveGroup;
import com.pbtd.manager.live.domain.LiveProgram;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveProgramService;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.util.QueryObject;

/**
 * 直播节目单管理
 * @author YFENG
 *
 */
@RequestMapping("/live/Program")
@Controller
public class LiveProgramController {
	@Autowired
	private ILiveProgramService LiveProgramService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

	
	/**
	 * /live/Program/list
	 * @param qo
	 * @return
	 * 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageResult Live() {
		PageResult pageResult = null;
		try {
			pageResult = LiveProgramService.querylistallLiveProgram();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageResult;
	}
	
    /**
     * GET 查询
     * @return 视图路径
     */
    @RequestMapping(value = {"/index", ""})
    public String index() {
    	return "/live/programManage/index";
    }
    
    /**
     * GET 编辑
     *
     * @param id 标识
     * @return 视图路径
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id) {
        return "/live/programManage/edit";
    }
    
    /**
     * POST 删除多条
     *
     * @param ids 标识列表
     * @return 被删除的记录条数
     */
    @LogAnnotation(operationInfo = "直播-节目单管理 -删除操作")
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
        return this.LiveProgramService.deletes(queryParams);
    }
    /**
     * 插入记录
     *
     * @param vodChannel VodChannel实例
     * @return 被插入的记录标识
     */
    @LogAnnotation(operationInfo = "直播-节目单管理 -添加操作")
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public int create(LiveProgram LiveProgram, HttpServletRequest request){
      this.LiveProgramService.insert(LiveProgram);
      return 1;
    }
	
	  /**
     * 模糊获取符合查询条件的分页记录
     * 
     * @param page 页码
     * @param limit 页长
     * @param request 查询参数
     * @return 记录列表
     * /live/Version/page?page=1&rows=10
     */
    @ResponseBody
    @RequestMapping("/page")
    public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit,HttpServletRequest request) {
    	List<LiveProgram> data = new ArrayList<LiveProgram>();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   
        Date date = null;
        Long sttime = null;
        String time = request.getParameter("starttime");
        if(time != null & !time.equals("")){
        	try {
    			date = sdf.parse(time);
    			sttime = date.getTime();
    		} catch (ParseException e) {
    			e.printStackTrace();
    		}
        }
    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		if(sttime != null ){
			queryParams.put("starttime", sttime/1000);
		}
    		QueryObject qo=new QueryObject();
    		qo.setPage(page);
    		qo.setRows(limit);
    		queryParams.put("start", qo.getStart());
    		queryParams.put("pageSize", qo.getPageSize());
            int total = this.LiveProgramService.count(queryParams);
    		if (total == 0) {
    			return new PageResult(total, Collections.EMPTY_LIST);
    		}
    			data =this.LiveProgramService.find(queryParams);
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
    public LiveProgram load(@PathVariable("id") int id) {
    	LiveProgram LiveProgram = null;
        if (id > 0) {
        	LiveProgram = this.LiveProgramService.load(id);
        }
        else {
        	LiveProgram = new LiveProgram(
        			0
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
        			,null
            );
        }
        return LiveProgram;
    }
    
    /**
     * POST 编辑
     *
     * @param id 标识
     * @param vodChannel VodChannel实例
     * @return int 被修改的记录条数
     * @throws ServletException
     */
    @LogAnnotation(operationInfo = "直播-节目单管理 -更改操作")
    @ResponseBody
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public int edit(@PathVariable("id") int id, LiveProgram LiveProgram) throws ServletException {
        return this.LiveProgramService.update(LiveProgram);
    }
    
    
    /**
     * 根据版本号获取手机节目单
     *
     * @param id 标识
     * @return 记录
     * /live/Program/programinterface/SD_CMCC_OTT_HUAWEI
     */
    @ResponseBody
    @RequestMapping("/programinterface/{pro_id}")
    public List<LiveProgram> programinterface(@PathVariable("pro_id") String str) {
    	List <LiveProgram> list = new ArrayList<>();
        if (str != null && str.length() != 0) {
        	try {
				list = this.LiveProgramService.getprogram(str);
			} catch (Exception e) {
				logger.info("请输入正确的参数 ");
			}
        }
        else{
        	logger.info("请输入参数 pro_id");  
        }
        return list;
    }

}