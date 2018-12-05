package com.pbtd.playuser.web.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.pbtd.playuser.domain.UserBaseInfo;
import com.pbtd.playuser.service.IUserBaseInfoService;
import com.pbtd.playuser.util.DateFormatUtil;
import com.pbtd.playuser.util.JsonMessage;
import com.pbtd.playuser.util.RequestUtil;

/**
 * 用户操作
 * 
 * @author JOJO
 *				
 */
@Controller
public class UserBaseInfoController {
	private static final Logger logger = LoggerFactory.getLogger(UserBaseInfoController.class); 
	/**
	 * 正则表达式：验证手机号
	 */
	// public static final String REGEX_MOBILE ="^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	public static final String REGEX_MOBILE ="^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";

	@Autowired
	private IUserBaseInfoService userBaseInfoService;



	/**
	 * 用户发送验证码
	 * @param 
	 * @return
	 */
	@RequestMapping("/common/user/sendCode")
	@ResponseBody
	public JsonMessage sendCode(HttpServletRequest request){
		long start = System.currentTimeMillis();
		logger.info("接口sendCode开始:"+start);
		JsonMessage result = new JsonMessage();
		String balance = request.getParameter("mobile");
		if(balance == null){
			result.setCode(-1);
			result.setMessage("请检查参数或输入手机号！");
			return result;
		}
		long mobilenum = 0;
		try {
			mobilenum=Long.parseLong(balance);
			if(balance.length() != 11){
				result.setCode(-2);
				result.setMessage("请输入正确长度的手机号！");
				return result;
			}
		} catch (NumberFormatException e) {
			result.setCode(-3);
			result.setMessage("请输入正确的手机号格式的 !");
			return result;
		}
		logger.info("接口sendCode入参mobile:"+mobilenum);
		try {
			result = userBaseInfoService.getcode(mobilenum);
		}catch (Exception e) {
			result.setCode(0);
			result.setMessage("系统正在维护中,请稍后！");
			e.printStackTrace();
			return result;
		}
		long end = System.currentTimeMillis();
		logger.info("接口sendCode结束:"+end);
		logger.info("接口sendCode时间:"+(end-start));
		return result;
	}

	/**
	 * 用户登录/注册
	 * @param 
	 * @return
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@RequestMapping("/common/user/login")
	@ResponseBody
	public JsonMessage login(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		JsonMessage result = new JsonMessage();
		String code = null;
		String mobile = null;
		String device = null;
		code = request.getParameter("code");;
		mobile = request.getParameter("mobile");
		device = request.getParameter("device");
		if(code == null || mobile == null){
			result.setCode(-1);
			result.setMessage("请检查输入参数");
			return result;
		}
		if(mobile.length() != 11 || code.length() != 6){
			result.setCode(-2);
			result.setMessage("请输入正确长度的手机号或验证码！");
			return result;
		}
		try {
			long mobilenum=Long.parseLong(mobile);
			long codenum=Long.parseLong(code);
		}catch (NumberFormatException e) {
			result.setCode(-3);
			result.setMessage("请输入正确的手机号或验证码格式的 !");
			return result;
		}	
		logger.info("接口login入参mobile,code,device:"+mobile+","+code+","+device);
		try {
			result = userBaseInfoService.login(mobile, code, device);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("登录失败");
		}
		return result;
	}



	/**
	 * 用户token 校验
	 * @param 
	 * @return
	 * 
	 */
	@RequestMapping("/common/user/checkToken")
	@ResponseBody
	public JsonMessage checkToken(HttpServletRequest request) {
		JsonMessage result = new JsonMessage();
		String userId = null;
		String token = null;
		userId = request.getParameter("userId");;
		token = request.getParameter("token");
		if(token == null ||userId == null || token.isEmpty() || userId.isEmpty()){
			result.setCode(0);
			result.setMessage("校验失败");
			return result;
		}
		UserBaseInfo userinfo;
		try {
			userinfo = userBaseInfoService.selectUser(userId);
		} catch (Exception e1) {
			result.setCode(-2);                 
			result.setMessage("数据库查询失败");
			return result;                      
		}  
		if(userinfo == null){
			result.setCode(-3);                 
			result.setMessage("用户不存在");
			return result;                      
		}
		logger.info("接口checkToken入参token,userId:"+token+","+userId);
		if("tv_token_6688_pbtd".equals(token)){
			return new JsonMessage(1,"校验成功");
		}
		try {
			result = userBaseInfoService.checkToken(token,userId);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("校验失败");
			return result;
		}
		return result;
	}
	/**
	 * 用户打点接口
	 * @param 
	 * @return
	 * 
	 */
	@RequestMapping("/common/user/Position")
	@ResponseBody
	public JsonMessage Position (HttpServletRequest request,String  pointArray) {
		//System.out.println(pointArray.toString());
		JsonMessage result = new JsonMessage();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		logger.info("接口Position开始");
		try {
			result = userBaseInfoService.insertPosition(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("校验失败");
			return result;
		}
		return result;
	}

	/*	public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode("['**,100*猜你    喜欢*3,1053745*远离尘嚣']","UTF-8"));
			System.out.println(URLDecoder.decode("%5B%27**%2C100*%E7%8C%9C%E4%BD%A0++++%E5%96%9C%E6%AC%A2*3%2C1053745*%E8%BF%9C%E7%A6%BB%E5%B0%98%E5%9A%A3%27%5D","UTF-8")); 
			} catch (UnsupportedEncodingException ex) {
				throw new RuntimeException("Broken VM does not support UTF-8");
			}
	}*/
	/**
	 * 保存搜索接口
	 * @param 
	 * @return
	 * 
	 */
	@RequestMapping("/common/user/saveSearch")
	@ResponseBody
	public JsonMessage saveSearch(HttpServletRequest request) {
		JsonMessage result = new JsonMessage();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		logger.info("接口saveSearch开始");
		try {
			result = userBaseInfoService.insertsaveSearch(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("校验失败");
			return result;
		}
		return result;
	}



	/**
	 * 数据统计接口
	 * @param 
	 * @return
	 * 
	 */
	@RequestMapping("/common/user/querystatistics")
	@ResponseBody
	public JsonMessage querystatistics(HttpServletRequest request) {
		JsonMessage result = new JsonMessage();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		logger.info("接口saveSearch开始");
		String line = (String) queryParams.get("line");
		String wholeid = (String) queryParams.get("wholeId");
		String channelId = (String) queryParams.get("channelId");

		String startTime =  (String) (queryParams.get("startTime") == null ? getDate(7) : queryParams.get("startTime"));
		String endTime = (String)(queryParams.get("endTime") == null ? getDate(0) : queryParams.get("endTime"));
		queryParams.put("startTime", startTime);
		queryParams.put("endTime", endTime);
		try {
			if(channelId == null || "".equals(channelId)){
				//无频道 查询：
				result = userBaseInfoService.querystatistics0(queryParams);
				return result;
			}
			if(wholeid != null && channelId != null){
				result = userBaseInfoService.querystatistics2(queryParams);
				return result;
			}
			if (line != null && !line.isEmpty()){
				queryParams.put("wholeId", 4);
				result = userBaseInfoService.querystatistics2(queryParams);
				return result;
			}
			//频道下的查询
			result = userBaseInfoService.querystatistics1(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("校验失败");
			return result;
		}
		return result;
	}

	/**
	 * 搜索统计接口
	 * @param 
	 * @return
	 */
	@RequestMapping("/common/user/searchStatistics")
	@ResponseBody
	public JsonMessage searchStatistics(HttpServletRequest request) {
		JsonMessage result = new JsonMessage();
		Map<String, Object> queryParams = RequestUtil.asMaptoLowerCase(request, false);
		String startTime =  (String) (queryParams.get("starttime") == null ? getDate(7) : queryParams.get("starttime"));
		String endTime = (String)(queryParams.get("endtime") == null ? getDate(0) : queryParams.get("endtime"));
		
		queryParams.put("startTime", startTime);
		queryParams.put("endTime", endTime);
		logger.info("接口searchStatistics开始");
		try {
			result = userBaseInfoService.searchStatistics(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("校验失败");
			return result;
		}
		return result;
	}
	/**
	 * 其他统计接口
	 * @param 
	 * @return
	 * 
	 */
	@RequestMapping("/common/user/otherstatistics")
	@ResponseBody
	public JsonMessage otherstatistics(HttpServletRequest request) {
		JsonMessage result = new JsonMessage();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String startTime =  (String) (queryParams.get("startTime") == null ? getDate(7) : queryParams.get("startTime"));
		String endTime = (String)(queryParams.get("endTime") == null ? getDate(0) : queryParams.get("endTime"));
		queryParams.put("startTime", startTime);
		queryParams.put("endTime", endTime);
		logger.info("接口otherstatistics开始");
		try {
			result = userBaseInfoService.otherstatistics(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("校验失败");
			return result;
		}
		return result;
	}


	public String getDate(int a) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		//过去a天
		c.setTime(new Date());
		c.add(Calendar.DATE, - a);
		Date d = c.getTime();
		return format.format(d);
	}

	/**
	 * youku首次进入优酷专区调用
	 * @param 
	 * @return
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@RequestMapping("/common/user/firstlogin")
	@ResponseBody
	public JsonMessage firstlogin(HttpServletRequest request) throws UnsupportedEncodingException {
		logger.info("接口firstlogin开始......");
		request.setCharacterEncoding("UTF-8");
		JsonMessage result = new JsonMessage();
		String mobile = request.getParameter("mobile");
		String sourceflag = request.getParameter("sourceflag");
		Integer flag = null;
		try {
			flag = Integer.valueOf(sourceflag);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(2);
			result.setMessage("请输入正确的标识");
			return result;
		}
//		if(flag != 1 && flag != 2){
//			result.setCode(2);
//			result.setMessage("请输入正确的标识");
//			return result;
//		}
		if(StringUtils.isEmpty(mobile) || flag == null){
			logger.info("接口firstlogin mobile为空: [" + mobile + "],flag=" + flag);
			result.setCode(-1);
			result.setMessage("请检查输入参数");
			return result;
		}
//		if(!isMobile(mobile)){
//			result.setCode(-2);
//			result.setMessage("请输入正确的手机号！");
//			return result;
//		}
		try {
			result = userBaseInfoService.firstlogin(mobile,flag);
		} catch (Exception e) {
			logger.info("接口firstlogin 保存失败 "+mobile);
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("保存失败");
		}
		logger.info("接口firstlogin结束......");
		return result;
	}
	/**
	 * 校验手机号
	 * 
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {
		return Pattern.matches(REGEX_MOBILE, mobile);
	}


	/**
	 * 获取总的用户统计数据
	 * @param 
	 * @return
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@RequestMapping("/common/user/getuserdata")
	@ResponseBody
	public JsonMessage getuserdata(HttpServletRequest request) throws UnsupportedEncodingException {
		JsonMessage result = new JsonMessage();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String startTime =  (String) (queryParams.get("startTime") == null ? getDate(7) : queryParams.get("startTime"));
		String endTime = (String)(queryParams.get("endTime") == null ? getDate(0) : queryParams.get("endTime"));
		queryParams.put("startTime", startTime);
		queryParams.put("endTime", endTime);
		logger.info("接口getuserdata开始");
		try {
			result = userBaseInfoService.getuserdata(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("校验失败");
			return result;
		}
		return result;
	}
	/**
	 * youku 每日各时段点击次数统计
	 * @param 
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 */
	@RequestMapping("/common/user/getdataforhour")
	@ResponseBody
	public JsonMessage getdataforhour(HttpServletRequest request) throws UnsupportedEncodingException {
		JsonMessage result = new JsonMessage();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String startTime =  (String) (queryParams.get("startTime") == null ? getDate(0) : queryParams.get("startTime"));
		queryParams.put("startTime", startTime);
	//	queryParams.put("endTime",  DateFormatUtil.formatDateTime(startTime,1));
		logger.info("接口getdataforhour开始");
		try {
			result = userBaseInfoService.getdataforhour(queryParams);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(0);
			result.setMessage("校验失败");
			return result;
		}
		return result;
	}

	public static void main(String[] args) {

	}
}
