package com.pbtd.manager.live.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.domain.LiveCibnEpg;
import com.pbtd.manager.live.domain.LiveGroup;
import com.pbtd.manager.live.domain.LivePackage;
import com.pbtd.manager.live.domain.LiveProgram;
import com.pbtd.manager.live.domain.LiveTag;
import com.pbtd.manager.live.domain.LiveVersion;
import com.pbtd.manager.live.domain.LiveVideo;
import com.pbtd.manager.live.service.ILiveChannelService;
import com.pbtd.manager.live.service.ILiveCibnEpgService;
import com.pbtd.manager.live.service.ILiveGroupService;
import com.pbtd.manager.live.service.ILivePackageService;
import com.pbtd.manager.live.service.ILiveProgramService;
import com.pbtd.manager.live.service.ILiveTagService;
import com.pbtd.manager.live.service.ILiveVersionService;
import com.pbtd.manager.live.service.ILiveVideoService;
import com.pbtd.manager.live.util.JSONUtil;
import com.pbtd.manager.live.util.UtilDateProcessor;

import net.sf.json.JSONObject;
import net.sf.json.processors.JsonValueProcessor;

/**
 * 直播版本管理
 * @author YFENG
 *
 */
@RequestMapping("/live/outputjson")
@Controller
public class LiveOutPutController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@Autowired
	private ILiveChannelService liveChannelService;
	@Autowired
	private ILiveGroupService liveGroupService;
	@Autowired
	private ILivePackageService LivePackageService;
	@Autowired
	private ILiveProgramService LiveProgramService;
	@Autowired
	private ILiveTagService liveTagService;
	@Autowired
	private ILiveVersionService liveVersionService;
	@Autowired
	private ILiveVideoService liveVideoService;
	@Autowired
	private ILiveCibnEpgService liveCibnEpgService;
	private String longFormat = "yyyy-MM-dd hh:mm:ss";  




	/**
	 * 1 根据版本号获取该有的上线频道
	 *
	 * @param id 标识
	 * @return 记录
	 * /live/Channel/channelinterface/SD_CMCC_OTT_HUAWEI
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/channelinterface/{pro_id}")
	public List<LiveChannel> channelinterface(@PathVariable("pro_id") String str,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		// 定义一个类型转化器集合，键是需要转换的类型全路径，值是用于转换的类型转换器  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.util.Date", new UtilDateProcessor(longFormat));
		//有了2-3种时间转换器，那么我们设计时，就可以短时间格式用Date,长时间格式就是用Timestamp 
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject(); 
		List <LiveChannel> list = new ArrayList<>();
		if (str != null && str.length() != 0) {
			try {
				list = this.liveChannelService.getchannel(str);
				if(list.size() == 0){
					json.accumulate("code", 0);
					json.accumulate("data", "{}");
				}else{
					List<Object> newList = new ArrayList<>();
					for (LiveChannel liveChannel : list) {
						try {
							json = JSONUtil.toJson(liveChannel, processors);  
						} catch (Exception e) {
							e.getMessage();
						}
						newList.add(json);
					}
					json1.accumulate("code", 1);
					json1.accumulate("data", newList);
					response.getWriter().write(json1.toString());
					return null;
				}
			} catch (Exception e) {
				logger.info("请输入正确的参数 ");
				e.printStackTrace();
				json.accumulate("code", 0);
			}
		}else{
			logger.info("请输入参数 pro_id");  
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 2 根据版本号获取手机分组
	 *
	 * @param id 标识
	 * @return 记录
	 * /live/Group/groupinterface/SD_CMCC_OTT_HUAWEI
	 */
	@ResponseBody
	@RequestMapping("/groupinterface/{pro_id}")
	public List<LiveGroup> groupinterface(@PathVariable("pro_id") String str,HttpServletResponse response) throws IOException  {
		response.setContentType("text/html;charset=utf-8");
		// 定义一个类型转化器集合，键是需要转换的类型全路径，值是用于转换的类型转换器  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.util.Date", new UtilDateProcessor(longFormat));
		//有了2-3种时间转换器，那么我们设计时，就可以短时间格式用Date,长时间格式就是用Timestamp 
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		List <LiveGroup> list = new ArrayList<>();
		if (str != null && str.length() != 0) {
			try {
				list = this.liveGroupService.getgroup(str);
				if(list.size() == 0){
					json.accumulate("code", 0);
					json.accumulate("data", "{}");
				}else{
					List<Object> newList = new ArrayList<>();
					for (LiveGroup LiveGroup : list) {
						try {
							json = JSONUtil.toJson(LiveGroup, processors);  
						} catch (Exception e) {
							json.accumulate("code",-1);
						}
						newList.add(json);
					}
					json1.accumulate("code", 1);
					json1.accumulate("data", newList);
					response.getWriter().write(json1.toString());
					return null;
				}
			} catch (Exception e) {
				logger.info("请输入正确的参数 ");
			}
		}
		else{
			logger.info("请输入参数 pro_id");  
		}
		response.getWriter().write(json.toString());
		return null;
	}


	/**
	 * 3 根据版本号获取节目包
	 *
	 * @param id 标识
	 * @return 记录
	 * /live/Package/packageinterface/SD_CMCC_OTT_HUAWEI
	 */
	@ResponseBody
	@RequestMapping("/packageinterface/{pro_id}")
	public List<LivePackage> packageinterface(@PathVariable("pro_id") String str,HttpServletResponse response) throws IOException   {
		response.setContentType("text/html;charset=utf-8");
		// 定义一个类型转化器集合，键是需要转换的类型全路径，值是用于转换的类型转换器  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.util.Date", new UtilDateProcessor(longFormat));
		//有了2-3种时间转换器，那么我们设计时，就可以短时间格式用Date,长时间格式就是用Timestamp 
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		List <LivePackage> list = new ArrayList<>();
		if (str != null && str.length() != 0) {
			try {
				list = this.LivePackageService.getpackage(str);
				if(list.size() == 0){
					json.accumulate("code", 0);
					json.accumulate("data", "{}");
				}else{
					List<Object> newList = new ArrayList<>();
					for (LivePackage LivePackage : list) {
						try {
							json = JSONUtil.toJson(LivePackage, processors);  
						} catch (Exception e) {
							json.accumulate("code",-1);
						}
						newList.add(json);
					}
					json1.accumulate("code", 1);
					json1.accumulate("data", newList);
					response.getWriter().write(json1.toString());
					return null;
				}
			} catch (Exception e) {
				logger.info("请输入正确的参数 ");
			}
		}
		else{
			logger.info("请输入参数 pro_id");  
		}
		response.getWriter().write(json.toString());
		return null;
	}



	/**
	 * 4 根据版本号获取手机节目单
	 *
	 * @param id 标识
	 * @return 记录
	 * /live/Program/programinterface/SD_CMCC_OTT_HUAWEI
	 */
	@ResponseBody
	@RequestMapping("/programinterface/{pro_id}")
	public List<LiveProgram> programinterface(@PathVariable("pro_id") String str,HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		// 定义一个类型转化器集合，键是需要转换的类型全路径，值是用于转换的类型转换器  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.util.Date", new UtilDateProcessor(longFormat));
		//有了2-3种时间转换器，那么我们设计时，就可以短时间格式用Date,长时间格式就是用Timestamp 
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		List <LiveProgram> list = new ArrayList<>();
		if (str != null && str.length() != 0) {
			try {
				list = this.LiveProgramService.getprogram(str);
				if(list.size() == 0){
					json.accumulate("code", 0);
					json.accumulate("data", "{}");
				}else{
					List<Object> newList = new ArrayList<>();
					for (LiveProgram LiveProgram : list) {
						try {
							json = JSONUtil.toJson(LiveProgram, processors);  
						} catch (Exception e) {
							json.accumulate("code",-1);
						}
						newList.add(json);
					}
					json1.accumulate("code", 1);
					json1.accumulate("data", newList);
					response.getWriter().write(json1.toString());
					return null;
				}
			} catch (Exception e) {
				logger.info("请输入正确的参数 ");
			}
		}
		else{
			logger.info("请输入参数 pro_id");  
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 5 根据版本号获取tv分组
	 *
	 * @param id 标识
	 * @return 记录
	 * /live/Tag/taginterface/SD_CMCC_OTT_HUAWEI
	 */
	@ResponseBody
	@RequestMapping("/taginterface/{pro_id}")
	public List<LiveTag> taginterface(@PathVariable("pro_id") String str,HttpServletResponse response) throws IOException  {
		response.setContentType("text/html;charset=utf-8");
		// 定义一个类型转化器集合，键是需要转换的类型全路径，值是用于转换的类型转换器  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.util.Date", new UtilDateProcessor(longFormat));
		//有了2-3种时间转换器，那么我们设计时，就可以短时间格式用Date,长时间格式就是用Timestamp 
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		List <LiveTag> list = new ArrayList<>();
		if (str != null && str.length() != 0) {
			try {
				list = this.liveTagService.gettag(str);
				if(list.size() == 0){
					json.accumulate("code", 0);
					json.accumulate("data", "{}");
				}else{
					List<Object> newList = new ArrayList<>();
					for (LiveTag LiveTag : list) {
						try {
							json = JSONUtil.toJson(LiveTag, processors);  
						} catch (Exception e) {
							json.accumulate("code",-1);
						}
						newList.add(json);
					}
					json1.accumulate("code", 1);
					json1.accumulate("data", newList);
					response.getWriter().write(json1.toString());
					return null;
				}
			} catch (Exception e) {
				logger.info("请输入正确的参数 ");
			}
		}
		else{
			logger.info("请输入参数 pro_id");  
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 6 根据版本号获取节目包剧集
	 *
	 * @param id 标识
	 * @return 记录
	 * /live/Version/versioninterface/SD_CMCC_OTT_HUAWEI
	 */
	@ResponseBody
	@RequestMapping("/versioninterface/{pro_id}")
	public LiveVersion versioninterface(@PathVariable("pro_id") String str,HttpServletResponse response) throws IOException  {
		response.setContentType("text/html;charset=utf-8");
		// 定义一个类型转化器集合，键是需要转换的类型全路径，值是用于转换的类型转换器  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.util.Date", new UtilDateProcessor(longFormat));
		//有了2-3种时间转换器，那么我们设计时，就可以短时间格式用Date,长时间格式就是用Timestamp 
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		if (str != null && str.length() != 0) {
			try {
				LiveVersion LiveVersion = this.liveVersionService.getversion(str);
				if(LiveVersion == null){
					json.accumulate("code", 0);
					json.accumulate("data", "{}");
				}else{
					try {
						json = JSONUtil.toJson(LiveVersion, processors);  
					} catch (Exception e) {
						json.accumulate("code",-1);
					}
					json1.accumulate("code", 1);
					json1.accumulate("data", json);
					response.getWriter().write(json1.toString());
					return null;
				}
			} catch (Exception e) {
				logger.info("请输入正确的参数 ");
			}
		}else{
			logger.info("请输入参数 pro_id");  
		}
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 7 根据版本号获取节目包剧集
	 *
	 * @param id 标识
	 * @return 记录
	 * /live/Video/videointerface/SD_CMCC_OTT_HUAWEI
	 */
	@ResponseBody
	@RequestMapping("/videointerface/{pro_id}")
	public List<LiveVideo> videointerface(@PathVariable("pro_id") String str,HttpServletResponse response) throws IOException  {
		response.setContentType("text/html;charset=utf-8");
		// 定义一个类型转化器集合，键是需要转换的类型全路径，值是用于转换的类型转换器  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.util.Date", new UtilDateProcessor(longFormat));
		//有了2-3种时间转换器，那么我们设计时，就可以短时间格式用Date,长时间格式就是用Timestamp 
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		List <LiveVideo> list = new ArrayList<>();
		if (str != null && str.length() != 0) {
			try {
				list = this.liveVideoService.getvideo(str);
				if(list.size() == 0){
					json.accumulate("code", 0);
					json.accumulate("data", "{}");
				}else{
					List<Object> newList = new ArrayList<>();
					for (LiveVideo LiveVideo : list) {
						try {
							json = JSONUtil.toJson(LiveVideo, processors);  
						} catch (Exception e) {
							json.accumulate("code",-1);
						}
						newList.add(json);
					}
					json1.accumulate("code", 1);
					json1.accumulate("title", newList.size());
					json1.accumulate("data", newList);
					response.getWriter().write(json1.toString());
					return null;
				}
			} catch (Exception e) {
				logger.info("请输入正确的参数 ");
			}
		}else{
			logger.info("请输入参数 pro_id");  
		}
		response.getWriter().write(json.toString());
		return null;
	}
	/**
	 * 8 国广节目单
	 *
	 * @param id 标识
	 * @return 记录
	 * /live/Video/cibnepginterface/SD_CMCC_OTT_HUAWEI
	 */
	@ResponseBody
	@RequestMapping("/cibnepginterface/{pro_id}")
	public List<LiveVideo> cibnepginterface(@PathVariable("pro_id") String str,HttpServletResponse response) throws IOException  {
		response.setContentType("text/html;charset=utf-8");
		// 定义一个类型转化器集合，键是需要转换的类型全路径，值是用于转换的类型转换器  
		Map<String, JsonValueProcessor> processors = new HashMap<String, JsonValueProcessor>();  
		processors.put("java.util.Date", new UtilDateProcessor(longFormat));
		//有了2-3种时间转换器，那么我们设计时，就可以短时间格式用Date,长时间格式就是用Timestamp 
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		List <LiveCibnEpg> list = new ArrayList<>();
		if (str != null && str.length() != 0) {
			try {
				list = this.liveCibnEpgService.getcibnepg();
				if(list.size() == 0){
					json.accumulate("code", 0);
					json.accumulate("data", "{}");
				}else{
					List<Object> newList = new ArrayList<>();
					for (LiveCibnEpg liveCibnEpg : list) {
						try {
							json = JSONUtil.toJson(liveCibnEpg);  
						} catch (Exception e) {
							json.accumulate("code",-1);
						}
						newList.add(json);
					}
					json1.accumulate("code", 1);
					json1.accumulate("title", newList.size());
					json1.accumulate("data", newList);
					response.getWriter().write(json1.toString());
					return null;
				}
			} catch (Exception e) {
				logger.info("请输入正确的参数 ");
			}
		}else{
			logger.info("请输入参数 pro_id");  
		}
		response.getWriter().write(json.toString());
		return null;
	}


}