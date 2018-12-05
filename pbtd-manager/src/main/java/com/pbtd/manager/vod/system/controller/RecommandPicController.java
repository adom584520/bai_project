package com.pbtd.manager.vod.system.controller;

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
import com.pbtd.manager.util.ResultBean;
import com.pbtd.manager.util.SequenceUtil;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.phone.special.domain.VodSpecial;
import com.pbtd.manager.vod.system.domain.RecommandPic;
import com.pbtd.manager.vod.system.service.face.RecommandPicService;

@Controller
@RequestMapping("/vod/system/recommandpic")
public class RecommandPicController {
	@Autowired
	private RecommandPicService recommandService;
	
    @Autowired
    private IDictionaryService dictionaryService;
    @Autowired
	private SequenceUtil sequenceUtil;

		/**
		 * 角色页面跳转	
		 * 
		 * @return
		 */
		@RequestMapping("/index")
		public String recommandPicPage(Model model) {
			Map<String, Object> params=new HashMap<>();
			model.addAttribute("channellist", dictionaryService.findDictionaryMap(params, "choosechannel"));	     
			return "/vod/system/recommandPic/index";
		}
		
		/**
		 * get 编辑
		 * id 标识
		 * @return 视图路径
		 */
		@RequestMapping("/edit/{id}")
		public String edit(@PathVariable("id")int id,Model model) {
			return "/vod/system/recommandPic/edit";
		}
	
		
		 /**
	     * 根据标识获取记录
	     *
	     * @param id 标识
	     * @return 记录
	     */
	    @ResponseBody
	    @RequestMapping("/load/{id}")
	    
	    
	    public RecommandPic load(@PathVariable("id") int id) {
	    	RecommandPic recommandpic = null;
	        if (id > 0) {
	        	recommandpic = this.recommandService.load(id);
	        } 
	        else {
	        	int c=this.recommandService.generatePosition(null);
	        	recommandpic =new RecommandPic(-1);
	        }
	        return recommandpic;
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
	        return this.recommandService.count(RequestUtil.asMap(request, false));
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
	    	List<RecommandPic> data = new ArrayList<RecommandPic>();
	    	Map<String, Object> queryParams = RequestUtil.asMap(request, false);
	        int total = this.recommandService.count(queryParams);
			if (total == 0) {
				return new PageResult(total, Collections.EMPTY_LIST);
			}
			QueryObject qo=new QueryObject();
			qo.setPage(page);
			qo.setRows(limit);
			queryParams.put("start", qo.getStart());
			queryParams.put("pageSize", qo.getPageSize());
				data =this.recommandService.find(queryParams);
				return new PageResult(total, data);		
	    }	
	    
	    /**
	     * 更改imgUrl
	     * @param request
	     * @return
	     */
	    @LogAnnotation(operationInfo = "点播-phone专区推荐页图片管理-更改图片操作")
		@RequestMapping("/recommandPic_updateImg")
		@ResponseBody
		public ResultBean<RecommandPic> updateImg(HttpServletRequest request){
		String imgUrl=request.getParameter("imgUrl");
		int id=Integer.parseInt(request.getParameter("id"));
		
		ResultBean resultBean=new ResultBean();
		try{
		RecommandPic recommandPic=new RecommandPic();
		recommandPic.setId(id);
		recommandPic.setImageUrl(imgUrl);	
		recommandService.updateImg(recommandPic);
		resultBean.setMessage("更新成功");
		resultBean.setSuccess(true);
		}catch(Exception e){
			resultBean.setMessage("更新失败");
			resultBean.setSuccess(false);
			e.printStackTrace();
		}
		return resultBean;
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
			List<RecommandPic> RecommandPic = this.recommandService.find(queryParams);
			if (RecommandPic.size() == 1) {
				return RecommandPic.get(0).getId() == id ? 0 : 1;
			} else {
				return RecommandPic.size();
			}
		}
		/**
		 * 插入记录
		 *
		 * @param vodChannel VodChannel实例
		 * @return 被插入的记录标识
		 */
			@LogAnnotation(operationInfo = "点播-phone专区推荐页图片管理-添加操作")
			@ResponseBody
			@RequestMapping(value ="/create", method = RequestMethod.POST)
			public int create(RecommandPic recommandPic, HttpServletRequest request) {
			    try{
			    	Map<String, Object> queryParams = new HashMap();
					Map<String, Object> maxmap = recommandService.findmaxVSminsequence(queryParams);// 查询更改排序的最大最小原始值
					int curmax = 0;
					if (maxmap != null) {
						curmax = Integer.parseInt(maxmap.get("max") == null ? "0" : maxmap.get("max").toString());// 原始数据排序最大值
					}
					recommandPic.setSequence(curmax +=1);
			    	this.recommandService.add(recommandPic);
			    }catch(Exception e){
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
			@LogAnnotation(operationInfo = "点播-phone专区推荐页图片管理-编辑操作")
			@ResponseBody
			@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
			public int edit(@PathVariable("id") int id, RecommandPic recommandPic) throws ServletException {
				int newmax =recommandPic.getSequence();
				int newmin = 0;
				int max = newmax;// 排序最大值
				int min = newmin;// 排序最小值
				Map<String, Object> mapsequence = new HashMap<>();
				mapsequence.put("id", Integer.toString(id));
				Map<String, Object> maxmap = recommandService.findmaxVSminsequence(mapsequence);// 查询更改排序的最大最小原始值
				int curmax = 0;
				int curmin = 0;
				if (maxmap != null) {
					curmax = Integer.parseInt(maxmap.get("max") == null ? "0" : maxmap.get("max").toString());// 原始数据排序最大值
					curmin = Integer.parseInt(maxmap.get("min") == null ? "0" : maxmap.get("min").toString());// 原始数据排序最大值
				}
				if (newmax < curmax) {//// 如果原始数据最大值比更改排序的最大值大 复制为最大值
					max = curmax;
				}
				if (newmin > curmin) {// 如果原始数据最小值比更改排序的最小值小 复制为最小值
					min = curmin;
				}
				mapsequence.put("max", max);
				mapsequence.put("min", min);
				List<Map<String, Object>> list = recommandService.findsequence(mapsequence);
				Map<String, Object> map = new HashMap<>();
				String[] newsequences = sequenceUtil.Sortball(Integer.toString(newmax));
				for (int i = 0; i < list.size(); i++) {// 自动更改排序递增递减
					map = list.get(i);
					min += 1;
					int j = newsequences.length;
					for (int jj = 0; jj < j; jj++) {// 修改排序=原始排序+jj
													// jj为循环次数（修改改值与原始值的间隔值）
						if (min == Integer.parseInt(newsequences[jj])) {
							min += 1;
						}
					}
					// 修改排序=原始排序+jj jj为循环次数（修改改值与原始值的间隔值）
					String code = map.get("id").toString();
					RecommandPic recomandpic = new RecommandPic();
					recomandpic.setId(Integer.parseInt(code));
					recomandpic.setSequence(min);
					recomandpic.setStatus(-1);
					recommandService.modify(recomandpic);
				}
				// 更改编辑的排序信息
				recommandPic.setId(id);
				recommandPic.setStatus(-1);
				return this.recommandService.modify(recommandPic);
			}
			
			/**
			 * POST 删除多条
			 *
			 * @param ids 标识列表
			 * @return 被删除的记录条数
			 */
			@LogAnnotation(operationInfo = "点播-phone专区推荐页图片管理-删除操作")
			@ResponseBody
			@RequestMapping(value = "/deletes", method = RequestMethod.GET)
			public int deletes(HttpServletRequest request) {
				Map<String, Object> queryParams = new HashMap<String, Object>();
				String ids = request.getParameter("id");
				String[] idarray = ids.split(",");
				queryParams.put("id", ids);
				// 查询更改排序的原始值
				List<Map<String, Object>> oldsequencelist =recommandService.findsequencesum(queryParams);
				int min = Integer.parseInt(oldsequencelist.get(0).get("sequence") == null ? "1"
						: oldsequencelist.get(0).get("sequence").toString());
				queryParams.put("min", min);
				List<Map<String, Object>> list = recommandService.findsequence(queryParams);
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
					RecommandPic recomandpic = new RecommandPic();
					recomandpic.setId(Integer.parseInt(map.get("id").toString()));
					recomandpic.setSequence(curmin);
					recomandpic.setStatus(-1);
					recommandService.modify(recomandpic);
				}
				// 删除
				for (String num : idarray) {
					int id = Integer.parseInt(num);
					queryParams.put("id", id);
			       this.recommandService.deletes(queryParams);
				}
				return 1;
			}
			
			/**
			 * 查询频道列表
			 * 
			 */
			  @ResponseBody
			  @RequestMapping("/queryChannelInfo")
			  public List<Map<String,Object>> queryChannelInfo(){
				  return recommandService.queryChannelInfo();
				  
			  }
			  
			  //批量上线下线
			  @LogAnnotation(operationInfo = "点播-phone专区推荐页图片管理-更改状态操作")
			  @ResponseBody
			  @RequestMapping("/updateStatus")
			  public int updateStatus(HttpServletRequest request){
			  	int result=0;
			  	Map<String,Object> map=new HashMap<String,Object>();
			      String id=request.getParameter("id");
			      String[] ids=id.split(",");
			      for(String idNew:ids){
			      	int id1=Integer.parseInt(idNew);
			      	int status=Integer.parseInt(request.getParameter("status"));
			      	map.put("id", id1);
			      	map.put("status", status);
			      	result=recommandService.updateStatus(map);       	
			      }
					return result;    	
			  }
			}
