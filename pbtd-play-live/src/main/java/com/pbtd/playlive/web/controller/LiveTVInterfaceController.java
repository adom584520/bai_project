package com.pbtd.playlive.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playlive.config.LiveInterfaceUtil;
import com.pbtd.playlive.domain.LiveChannel;
import com.pbtd.playlive.domain.LiveTag;
import com.pbtd.playlive.domain.LiveVideo;
import com.pbtd.playlive.page.PageResult;
import com.pbtd.playlive.service.ILiveChannelService;
import com.pbtd.playlive.service.ILivePackageService;
import com.pbtd.playlive.service.ILiveTagService;
import com.pbtd.playlive.service.ILiveVersionService;
import com.pbtd.playlive.service.ILiveVideoService;


/**
 * 用户操作
 * 
 * @author JOJO
 *
 */
@RequestMapping("/tv/live")
@Controller
public class LiveTVInterfaceController {
	@Autowired
	private ILiveChannelService liveChannelService;
	@Autowired
	private ILiveTagService liveTagService;
	@Autowired
	private ILiveVersionService	 liveVersionService;
	@Autowired
	private ILivePackageService	 livePackageService;
	@Autowired
	private ILiveVideoService	 liveVideoService;

	private static final Logger logger = LoggerFactory.getLogger(LivePhoneInterfaceController.class); 
	
	private  static String PRO_ID =LiveInterfaceUtil.PROJ_ID;
	
	
	/**
	 * 1、台标控制显示接口    
	 * 192.168.0.54/tv/live/version/load?proj_id=cmcc
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getVersion")
	@ResponseBody
	public PageResult<?> LiveversionInfo(HttpServletRequest request) {
		long start = System.currentTimeMillis();
		logger.info("开始时间："+start);
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-2);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问tv getVersion 接口；入参proj_id:"+proj_id);
		params.put("proj_id", proj_id);
		try {
			pageResult.setMessage("响应成功");
			pageResult = liveVersionService.querylistallL(params);
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		long end = System.currentTimeMillis();

		logger.info("接口结束："+end);
		logger.info("接口持续时间："+(end-start));
		logger.info("访问getVersion返回值"+pageResult.getCode());
		return pageResult;
	}
	
	/**
	 *  2	标签接口   
	 * 192.168.0.54/tv/live/tag/load?proj_id=cmcc
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getTag")
	@ResponseBody
	public PageResult<?> LiveTagInfo(HttpServletRequest request) {
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-2);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问 TV getTag 接口 入参proj_id:"+proj_id);
		params.put("proj_id", proj_id);
		try {
			pageResult.setMessage("响应成功");
			pageResult = liveTagService.querylistallLiveTag();
			if(pageResult.getData() == null ||pageResult.getData().equals("")){
				pageResult.setCode(-3);
				pageResult.setMessage("获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		logger.info("访问getTag返回值"+pageResult.getCode());
		return pageResult;
	}

	/**
	 *  3	根据标签分页查询节目包信息 前 30 个节目包以手动排序为主，后面的数据按照时间倒序。
	 * 192.168.0.59/tv/live/package/load?proj_id=cmcc&tagId=1
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getPackageList")
	@ResponseBody
	public PageResult<?> LivePackageInfo(HttpServletRequest request) {
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		Integer tagId = null;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-2);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		try {
			tagId = Integer.valueOf(request.getParameter("tagId"));
			if(tagId == null ){
				pageResult.setCode(-1);
				pageResult.setMessage("频道tagId不能为空");
				return pageResult;
			}
			LiveTag liveTag= liveTagService.selectbykey(tagId);
			if( liveTag == null && tagId != -1){
				pageResult.setCode(-2);
				pageResult.setMessage("频道tagId不正确");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-3);
			pageResult.setMessage("分组ID有误");
			return pageResult;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问 getPackageList 接口；入参proj_id:"+proj_id+"tagId:"+tagId);
		params.put("proj_id", proj_id);
		params.put("tagId", tagId);
		try {
			pageResult = livePackageService.querylistallPackage(params);
			pageResult.setMessage("响应成功");
			if(pageResult.getData() == null ||pageResult.getData().equals("")){
				pageResult.setCode(-3);
				pageResult.setMessage("获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		logger.info("访问getPackageList返回值"+pageResult.getCode());
		return pageResult;
	}
	   
	/**
	 *  4	节目包接口  packageList
	 * 192.168.0.59/tv/live/packageList/load?proj_id=cmcc&packageCode=5965e74235db5e441d8b4574_G_SHANDONG-HD
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getPackagelistByPackageCode")
	@ResponseBody
	public PageResult<?> LivePackageListInfo(HttpServletRequest request) {
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		String packageCode = null;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-2);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		List<LiveVideo> list = null;
		try {
			packageCode = request.getParameter("packageCode");
			if(packageCode == null ){
				pageResult.setCode(-1);
				pageResult.setMessage("频道packageCode不能为空");
				return pageResult;
			}
			list= liveVideoService.selectbykey(packageCode);
			if( list == null || list.size() == 0){
				pageResult.setCode(-2);
				pageResult.setMessage("节目包code不正确或无数据");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-3);
			pageResult.setMessage("节目包code有误");
			return pageResult;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问接口 getPackagelistByPackageCode 接口；入参proj_id:"+proj_id+"packageCode:"+packageCode);
		params.put("proj_id", proj_id);
		params.put("voidelist", list);
		try {
			pageResult.setMessage("响应成功");
			pageResult = liveVideoService.querylistallL(params);
			if(pageResult.getData() == null ||pageResult.getData().equals("")){
				pageResult.setCode(-3);
				pageResult.setMessage("获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		logger.info("访问getPackagelistByPackageCode返回值"+pageResult.getCode());
		return pageResult;
	}
	/**
	 *  5	获取正在播出的节目列表   
	 * 192.168.0.59/tv/live/nowepg/load?proj_id=cmcc
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getAllChannelNowEpg")
	@ResponseBody
	public PageResult<?> LiveNowEpgInfo(HttpServletRequest request) {
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-2);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问接口 getAllChannelNowEpg；入参proj_id:"+proj_id);
		params.put("proj_id", proj_id);
		try {
			pageResult.setMessage("响应成功");
			pageResult = liveChannelService.queryAllChannle(params);
			if(pageResult.getData() == null ||pageResult.getData().equals("")){
				pageResult.setCode(-3);
				pageResult.setMessage("获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		logger.info("访问getAllChannelNowEpg返回值"+pageResult.getCode());
		return pageResult;
	}
	
	/**
	 *  6	当前频道正在播放的节目接口  chnPlayEpg
	 * 192.168.0.59/tv/live/chnPlayEpg/load?proj_id=cmcc&chnCode=CCTV-1
	 * @param qo
	 * @return
	 */
	@RequestMapping("/getChannelNowEpg")
	@ResponseBody
	public PageResult<?> LivechnPlayEpgInfo(HttpServletRequest request) {
		PageResult<?> pageResult = new PageResult<>();
		String proj_id =null;
		String chnCode = null;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("项目ID不能为空");
				return pageResult;
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				pageResult.setCode(-2);
				pageResult.setMessage("项目ID有误");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("项目ID有误");
			return pageResult;
		}
		LiveChannel liveChannel  = new LiveChannel();
		try {
			chnCode = request.getParameter("chnCode");
			if(chnCode.isEmpty()){
				pageResult.setCode(-1);
				pageResult.setMessage("频道CODE不能为空");
				return pageResult;
			}
			liveChannel = liveChannelService.selectLiveChannel(chnCode);
			if( liveChannel == null){
				pageResult.setCode(-2);
				pageResult.setMessage("频道CODE不正确");
				return pageResult;
			}
		} catch (Exception e1) {
			pageResult.setCode(-2);
			pageResult.setMessage("频道CODE有误");
			return pageResult;
		}
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问接口 getChannelNowEpg ；入参proj_id:"+proj_id+"chnCode:"+chnCode);
		params.put("proj_id", proj_id);
		params.put("liveChannel", liveChannel);
		try {
			pageResult.setMessage("响应成功");
			pageResult = liveChannelService.queryOneChannle(params);
			if(pageResult.getData() == null ||pageResult.getData().equals("")){
				pageResult.setCode(-3);
				pageResult.setMessage("获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			pageResult.setCode(0);
			pageResult.setMessage("服务器异常");
		}
		logger.info("访问getChannelNowEpg返回值"+pageResult.getCode());
		return pageResult;
	}
	
	/**
	 *  7	频道回看直播节目单 接口  chnReviewEpg
	 * @param qo
	 * @return
	 */
	@RequestMapping("/chnReviewEpg")
	@ResponseBody
	public PageResult<?> LiveChnReviewEpgInfo(HttpServletRequest request) {
		String proj_id =null;
		String chnCode = null;
		try {
			proj_id = request.getParameter("proj_id");
			if(proj_id.isEmpty()){
				return new PageResult<>(-1,"项目ID不能为空");
			}else if(!PRO_ID.equalsIgnoreCase(proj_id)){
				return new PageResult<>(-2,"项目ID有误");
			}
		} catch (Exception e1) {
			return new PageResult<>(-2,"项目ID有误");
		}
		LiveChannel liveChannel  = new LiveChannel();
		try {
			chnCode = request.getParameter("chnCode");
			if(chnCode.isEmpty()){
				return new PageResult<>(-3,"频道CODE不能为空");
			}
			liveChannel = liveChannelService.selectLiveChannel(chnCode);
			if( liveChannel == null){
				return new PageResult<>(-4,"频道CODE不正确");
			}
		} catch (Exception e1) {
			return new PageResult<>(-5,"频道CODE有误");
		}
		Map<String,Object> params = new HashMap<String, Object>();
		logger.info("访问 chnReviewEpg 接口 ；入参proj_id:"+proj_id+"chnCode:"+chnCode);
		params.put("proj_id", proj_id);
		params.put("liveChannel", liveChannel);
		try {
			PageResult<?> pageResult = new PageResult<>();
			pageResult = liveChannelService.queryChnPlayEpgInfo(params);
			if(pageResult.getData() == null ||pageResult.getData().equals("")){
				return new PageResult<>(-6,"获取失败");
			}
			logger.info("访问getChannelNowEpg返回值"+pageResult.getCode());
			return pageResult;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("访问getChannelNowEpg返回值"+0);
			return new PageResult<>(0,"服务器异常");
		}
	}
	
}