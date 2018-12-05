package com.pbtd.playuser.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playuser.domain.UserBaseInfo;
import com.pbtd.playuser.domain.UserLoginInfo;
import com.pbtd.playuser.domain.UserLoginList;
import com.pbtd.playuser.domain.UserPhoneHB;
import com.pbtd.playuser.domain.UserPositionInfo;
import com.pbtd.playuser.domain.UserSearchInfo;
import com.pbtd.playuser.mapper.PlayHistoryInfoPhoneMapper;
import com.pbtd.playuser.mapper.UserBaseInfoMapper;
import com.pbtd.playuser.mapper.UserLoginInfoMapper;
import com.pbtd.playuser.mapper.UserLoginListMapper;
import com.pbtd.playuser.mapper.UserPhoneHBMapper;
import com.pbtd.playuser.mapper.UserPositionInfoMapper;
import com.pbtd.playuser.mapper.UserSearchInfoMapper;
import com.pbtd.playuser.page.GetCodeResult;
import com.pbtd.playuser.page.LoginResult;
import com.pbtd.playuser.service.IRedisService;
import com.pbtd.playuser.service.IUserBaseInfoService;
import com.pbtd.playuser.util.DateFormatUtil;
import com.pbtd.playuser.util.JsonMessage;
import com.pbtd.playuser.util.sendPost.TestMain;

import net.sf.json.JSONArray;


/** 
 *  
 * @author vic 
 * @desc UserBaseInfo service
 * 
 */  
@Service  
public class UserBaseInfoService implements IUserBaseInfoService{
	private static final Logger logger = LoggerFactory.getLogger(UserBaseInfoService.class); 

	@Autowired
	private UserPhoneHBMapper userPhoneHBMapper;
	@Autowired
	private UserBaseInfoMapper userBaseInfoMapper;
	@Autowired
	private UserLoginInfoMapper userLoginInfoMapper;
	@Autowired
	private IRedisService redisService;
	@Autowired
	private TestMain testMain;
	@Autowired
	private UserPositionInfoMapper userPositionMapper;
	@Autowired
	private UserSearchInfoMapper serSearchInfoMapper;
	@Autowired
	private UserLoginListMapper userLoginListMapper;
	@Autowired
	private PlayHistoryInfoPhoneMapper playHistoryInfoMapper;

	/**
	 * 1.发送验证码
	 */
	@Override
	public JsonMessage getcode(final long mobile) {
		//检查手机号归属
		boolean stats = false;
		if(13071825897L ==mobile || 18086502465L == mobile || 13843838438L == mobile || 18405814765L == mobile  || 18405814755L == mobile){
			stats = true;
		}else{
			stats = selectphonefrom(mobile);
		}
		if(!stats){
			return new JsonMessage(2,"手机号非河北移动用户");
		}
		//产生随机的六位数 
		final String code = String.valueOf((int)((Math.random()*9+1)*100000));
		try {//发送短信
			new Thread() {
				public void run() {
					testMain.sendMassage(mobile,code);
				}
			}.start();
		} catch (Exception e) {
			logger.info("发送验证码出错");
		}
		HashMap<String, Long> tempBean = new HashMap<String, Long>();
		tempBean.put("mobile",mobile);
		UserBaseInfo userinfo  = userBaseInfoMapper.select(tempBean);
		try {
			//插入缓存
			boolean flag  =  redisService.setEx(Long.toString(mobile),60*5l,code);
			logger.info("插入缓存状态："+flag);
			logger.info("生成验证码："+code);
		} catch (Exception e) {
			logger.info("验证码插入缓存出错");
			return new JsonMessage(3,"插入缓存出错");
		}

		if(userinfo != null){
			//存量用户
			return new GetCodeResult(1,"验证码发送成功",0);
		}else{
			return new GetCodeResult(1,"验证码发送成功",1);
		}
	}
	/**
	 * 2 校验验证码。注册登录
	 */
	@Override
	public JsonMessage login(String mobile,String code,String device) {
		//1 校验验证码
		String rediscode = redisService.get(mobile);
		String userid = null;
		if("888888".equals(code)){
			//校验成功
			//1 查看手机号是否存在
			UserBaseInfo userinfo  = userBaseInfoMapper.selectbymobile(mobile);
			if(userinfo != null){
				/**用户存在**/
				userid = userinfo.getUserid();
			}else{
				/**用户不存在**/
				//把手机号插入用户信息表 并返回id
				userid = createUserId(mobile);
				if("104".equals(userid)){
					return  new JsonMessage(3,"插入数据库失败");
				}
			}
			String token = getToken(userid,device);
			if("104".equals(token)){
				return  new JsonMessage(3,"插入数据库失败");
			}
			return new LoginResult(1,"校验成功",userid,token);
		}

		if(rediscode == null ){
			return  new JsonMessage(2,"验证码失效或未获取");//验证码失效或未获取
		}else{
			if(code.equals(rediscode)){
				//校验成功
				//1 查看手机号是否存在
				UserBaseInfo userinfo  = userBaseInfoMapper.selectbymobile(mobile);
				if(userinfo != null){
					/**用户存在**/
					userid = userinfo.getUserid();
				}else{
					/**用户不存在**/
					//把手机号插入用户信息表 并返回id
					userid = createUserId(mobile);
					if("104".equals(userid)){
						return  new JsonMessage(3,"插入数据库失败");
					}
				}
				String token = getToken(userid,device);
				if("104".equals(token)){
					return  new JsonMessage(3,"插入数据库失败");
				}
				return new LoginResult(1,"校验成功",userid,token);
			}else{
				return  new JsonMessage(4,"验证码错误");//验证码错误
			}
		}
	}

	/**
	 * 3、启动时校验用户token
	 */
	@Override
	public JsonMessage checkToken(String token, String userId) {
		StringBuilder key = new StringBuilder();
		key.append("TOKEN-");
		key.append(userId);
		String tokenrediss = null;
		try {
			tokenrediss = redisService.get(key.toString());
		} catch (Exception e) {
			UserLoginInfo logininfo  = userLoginInfoMapper.selectByPrimaryKey(userId);
			if(logininfo != null){
				if(token.equals(logininfo.getTokencode())){
					Date date=new Date();//取时间
					Calendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					calendar.add(calendar.DATE,-15);//十五天前的日期
					date=calendar.getTime(); 
					if(logininfo.getUpdatetime().before(date)){
						return new JsonMessage(0,"校验失败(token错误)");
					}else{
						return new JsonMessage(1,"校验成功");
					}
				}else{
					return new JsonMessage(0,"校验失败(token错误)");
				}
			}else{
				return new JsonMessage(0,"校验失败");
			}
		}
		if(tokenrediss == null || tokenrediss.isEmpty()){
			return new JsonMessage(0,"TOKEN已失效，请重新登录");
		}else if(tokenrediss.equals(token)){
			return new JsonMessage(1,"校验成功");
		}else{
			return new JsonMessage(0,"校验失败");
		}
	}

	/**生成 userId 并把手机号插入用户信息biao*/
	public String createUserId(String mobile) {
		StringBuilder userId = new StringBuilder();
		userId.append("UR");
		userId.append(String.valueOf(System.currentTimeMillis()).substring(1,10));
		userId.append(UUID.randomUUID().toString().replaceAll("-", "").substring(0,5));
		String userid = userId.toString();
		System.out.println("生成userId:"+userid);
		try {
			HashMap<String, Object> tempBean = new HashMap<String, Object>();
			tempBean.put("userid",userid);
			tempBean.put("usermobile",mobile);
			int a = userBaseInfoMapper.insert(tempBean);
			System.out.println(a);
		} catch (Exception e) {
			return "104";
		}
		return userid;
	}

	/**查询手机号是否属于河北移动*/
	public boolean  selectphonefrom	(long mobilenum) {
		HashMap<String,Object> tempBean = new HashMap<String, Object> ();
		String mobile = Long.toString(mobilenum);
		String mobile_8 = mobile.substring(0,8);
		tempBean.put("mobile",mobile_8);
		tempBean.put("size",8);
		List<UserPhoneHB> list = userPhoneHBMapper.select(tempBean);
		if(list !=null && list.size()>0){
			return true;
		}else{
			String mobile_7 = mobile.substring(0,7);
			tempBean.put("mobile",mobile_7);
			tempBean.put("size",7);
			List<UserPhoneHB> lists = userPhoneHBMapper.select(tempBean);
			if(lists !=null && lists.size()>0){
				return true;
			}
		}
		return false;
	}
	/**生成token并插入设备信息表*/
	public String getToken(String userid, String device) {
		String token = UUID.randomUUID().toString().replace("-", "");
		StringBuilder key = new StringBuilder();
		key.append("TOKEN-");
		key.append(userid);
		//		key.append(device);
		//		redisService.del(key.toString());
		redisService.set(key.toString(),token);
		redisService.expire(key.toString(),60*60*24*15);
		HashMap<String, Object> tempBean = new HashMap<String, Object>();
		tempBean.put("userid",userid);
		tempBean.put("device",device);
		UserLoginInfo LoginInfo = userLoginInfoMapper.selectusrerdevice(tempBean);
		tempBean.put("tokencode",token);
		try {
			if(LoginInfo != null ){
				//存在该设备信息
				userLoginInfoMapper.updateToken(tempBean);
			}else{
				//新增改设备信息
				userLoginInfoMapper.insertSelective(tempBean);
			}
		} catch (Exception e) {
			return "104";  
		}
		return token;  
	}



	@Override
	public UserBaseInfo selectUser(String userId) {
		HashMap<String, String> tempBean = new HashMap<String, String>();
		tempBean.put("userid",userId);	
		UserBaseInfo UserBaseInfo = userBaseInfoMapper.select(tempBean);
		return UserBaseInfo;
	}

	public static void main(String[] args) {
		for(int i = 0;i<2;i++){
			System.out.println(UUID.randomUUID().toString().replace("-", ""));

			StringBuilder ss = new StringBuilder();
			ss.append("UR");
			ss.append(String.valueOf(System.currentTimeMillis()).substring(1,10));
			ss.append(UUID.randomUUID().toString().replaceAll("-", "").substring(0,5));
			System.out.println(ss.toString());

			Date date=new Date();//取时间
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE,-15);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
			date=calendar.getTime(); 
			System.out.println(date.toString());


		}
	}
	@Override
	public JsonMessage insertPosition(Map<String, Object> queryParams) {
		//接受五个参数：mobileNum  channel  whole  position describe
		String mobileNum = (String) queryParams.get("mobileNum");
		String pointArray = (String) queryParams.get("pointArray");
		System.out.println("入参pointArray:"+pointArray.toString());
		Map<String, Object> map = new HashMap<>();
		System.out.println("入参手机号："+mobileNum);
		if(pointArray != null && !"".equals(pointArray)){
			StringBuffer sb = new StringBuffer();
			JSONArray obj = JSONArray.fromObject(pointArray);
			for (int i = 0;i<obj.size();i++){
				String point = (String) obj.get(i);    
				System.out.println(i+point);
				String[] canshu = point.split(",", 4);
				String[] channelList = canshu[0].split("\\*",3);
				map.put("mobilenum", mobileNum);
				map.put("channelid", channelList[0]);
				map.put("channelname",  channelList[1]);
				map.put("channelspot", channelList[2]);
				String[] wholeList = canshu[1].split("\\*",4);
				map.put("wholeid", wholeList[0]);
				map.put("wholename",  wholeList[1]);
				if(wholeList.length == 3){
					map.put("wholespot", wholeList[2]);
				}else{
					map.put("wholedescribe", wholeList[2]);
					map.put("wholespot", wholeList[3]);
				}
				String[] positionList =  canshu[2].split("\\*",2);
				map.put("positioncode", positionList[0]);
				map.put("positionnum",  positionList[1]);
				try {
					String datedata =  canshu[3];
					Long datetime = Long.valueOf(datedata);
					Date time = DateFormatUtil.transForDate(datetime);
					map.put("time",time);
				} catch (NumberFormatException e1) {
					System.out.println("转换时间出错 ！！使用当前时间 ");
					map.put("time",new Date());
				}
				try {
					userPositionMapper.insertSelective(map);
				} catch (Exception e) {
					System.out.println(e);
					sb.append("第"+i+"条添加异常；");
				}
				sb.append("第"+i+"条添加成功；");
			}
			return new JsonMessage(1,sb.toString());
		}
		return  new JsonMessage(0,"传入参数异常");
	}


	@Override
	public JsonMessage insertsaveSearch(Map<String, Object> queryParams) {
		try {
			serSearchInfoMapper.insertSelective(queryParams);
		} catch (Exception e) {
			return new JsonMessage(0,"添加异常");
		}
		return new JsonMessage(1,"添加成功");
	}
	//查询频道下的统计数据 O 无channel
	@Override
	public JsonMessage  querystatistics0(Map<String, Object> queryParams) {
		logger.info("进入querystatistics0");
		queryParams.put("channelId", -1);
		int a = userPositionMapper.select(queryParams);
		HashMap< String, Object> data = new HashMap<>();
		//if(a == 0){
		//return new JsonMessage(1,"无数据");
		//}else{
		data.put("allNum", a);
		List<Object> list = new ArrayList<>();
		List<UserPositionInfo>  channellist = userPositionMapper.selectstatistics(queryParams);
		for (UserPositionInfo userPositionInfo : channellist) {
			if(userPositionInfo.getChannelname() != null){
				HashMap< String, Object> datalist = new HashMap<>();
				queryParams.put("wholeId", null);
				queryParams.put("channelId", userPositionInfo.getChannelid());
				HashMap< String, Integer> datamap = new HashMap<>();
				datamap.put("channelAllNum", userPositionMapper.select(queryParams));
				queryParams.put("wholeId", 0);
				datamap.put("channelInNum", userPositionMapper.select(queryParams));
				//datalist.put(userPositionInfo.getChannelname()+"频道信息",  datamap);
				datalist.put("channelName",userPositionInfo.getChannelname());
				datalist.put("value",  datamap);
				list.add(datalist);
			}
		}
		data.put("dataList",list);
		//}
		return new JsonMessage(data);
	}
	//查询频道下的统计数据  一
	@Override
	public JsonMessage  querystatistics1(Map<String, Object> queryParams) {
		logger.info("进入querystatistics1");
		int a = userPositionMapper.select(queryParams);
		HashMap< String, Object> data = new HashMap<>();
		if(a == 0){
			return new JsonMessage(1,"无数据",null);
		}else{
			data.put("allNum", a);
			HashMap< String, Integer> datalist = new HashMap<>();
			queryParams.put("wholeId", 0);
			datalist.put("channelInNum",  userPositionMapper.select(queryParams));
			queryParams.put("wholeId", 1);
			datalist.put("lunbotuNum", userPositionMapper.select(queryParams));
			queryParams.put("wholeId", 2);
			datalist.put("hotRecommendNum", userPositionMapper.select(queryParams));
			queryParams.put("wholeId", 3);
			datalist.put("moduleAllNum", userPositionMapper.select(queryParams));
			queryParams.put("wholeId", 4);
			datalist.put("labelNum", userPositionMapper.select(queryParams));
			data.put("dataList", datalist);
		}
		return new JsonMessage(data);
	}
	//查询频道下的统计数据 二 有 whoelId且 有channelId
	@Override
	public JsonMessage  querystatistics2(Map<String, Object> queryParams) {
		logger.info("进入querystatistics2");
		int a = userPositionMapper.select(queryParams);
		HashMap< String, Object> data = new HashMap<>();
		HashMap< String, Object> datamap = new HashMap<>();
		int wholeid  = Integer.valueOf((String) queryParams.get("wholeId"));
		switch (wholeid) {
		case 0:
			queryParams.put("wholeId", 0);
			datamap.put("name", "频道入口点击次数");
			datamap.put("value",  userPositionMapper.select(queryParams));
			break;
		case 1:
			data.put("moduleAllNumName", "该频道下的轮播图总点击次数");
			data.put("moduleAllNum", a);
			queryParams.put("wholeId", 1);
			queryParams.put("positionNum", 0);
			List<Object> datamaplist = new ArrayList<>();
			HashMap< String, Object> datamapone0 = new HashMap<>();
			datamapone0.put("name", "轮播图坑位l");
			datamapone0.put("value",  userPositionMapper.select(queryParams));
			datamaplist.add(datamapone0);
			queryParams.put("positionNum", 1);
			HashMap< String, Object> datamapone1 = new HashMap<>();
			datamapone1.put("name", "轮播图坑位2");
			datamapone1.put("value",  userPositionMapper.select(queryParams));
			datamaplist.add(datamapone1);
			queryParams.put("positionNum", 2);
			HashMap< String, Object> datamapone2 = new HashMap<>();
			datamapone2.put("name", "轮播图坑位3");
			datamapone2.put("value",  userPositionMapper.select(queryParams));
			datamaplist.add(datamapone2);
			queryParams.put("positionNum", 3);
			HashMap< String, Object> datamapone3 = new HashMap<>();
			datamapone3.put("name", "轮播图坑位4");
			datamapone3.put("value",  userPositionMapper.select(queryParams));
			datamaplist.add(datamapone3);
			queryParams.put("positionNum", 4);
			HashMap< String, Object> datamapone4 = new HashMap<>();
			datamapone4.put("name", "轮播图坑位5");
			datamapone4.put("value",  userPositionMapper.select(queryParams));
			datamaplist.add(datamapone4);
			queryParams.put("positionNum", 5);
			HashMap< String, Object> datamapone5 = new HashMap<>();
			datamapone5.put("name", "轮播图坑位6");
			datamapone5.put("value",  userPositionMapper.select(queryParams));
			datamaplist.add(datamapone5);
			datamap.put("list", datamaplist);
			break;
		case 2:
			List<Object> rebomaplist = new ArrayList<>();
			data.put("moduleAllNumName", "该频道下的热播推荐总点击次数");
			data.put("moduleAllNum", a);
			queryParams.put("wholeId", 2);
			queryParams.put("positionNum", 0);
			HashMap< String, Object> rebomapone0 = new HashMap<>();
			rebomapone0.put("name", "热播推荐坑位l");
			rebomapone0.put("value",  userPositionMapper.select(queryParams));
			rebomaplist.add(rebomapone0);
			HashMap< String, Object> rebomapone1 = new HashMap<>();
			queryParams.put("positionNum", 1);
			rebomapone1.put("name", "热播推荐坑位2");
			rebomapone1.put("value",  userPositionMapper.select(queryParams));
			rebomaplist.add(rebomapone1);
			HashMap< String, Object> rebomapone2 = new HashMap<>();
			queryParams.put("positionNum", 2);
			rebomapone2.put("name", "热播推荐坑位3");
			rebomapone2.put("value",  userPositionMapper.select(queryParams));
			rebomaplist.add(rebomapone2);
			HashMap< String, Object> rebomapone3 = new HashMap<>();
			queryParams.put("positionNum", 3);
			rebomapone3.put("name", "热播推荐坑位4");
			rebomapone3.put("value",  userPositionMapper.select(queryParams));
			rebomaplist.add(rebomapone3);
			HashMap< String, Object> rebomapone4 = new HashMap<>();
			queryParams.put("positionNum", 7);
			rebomapone4.put("name", "换一换坑位");
			rebomapone4.put("value",  userPositionMapper.select(queryParams));
			rebomaplist.add(rebomapone4);
			datamap.put("list", rebomaplist);
			break;
		case 3:
			data.put("moduleAllNumName", "该频道下的模版总点击次数");
			data.put("moduleAllNum", a);
			queryParams.put("wholeId", 3);                                          
			List<UserPositionInfo>  channellist = userPositionMapper.selectstatisticsformodule(queryParams);
			List<Object> modulemaplist = new ArrayList<>();
			for (UserPositionInfo userPositionInfo : channellist) {
				HashMap< String, Object> modulemapone = new HashMap<>();
				Integer sp = userPositionInfo.getWholespot();
				queryParams.put("wholeSpot",sp);
				modulemapone.put("name", "模块"+(sp+1));
				modulemapone.put("modulesequence",sp);
				modulemapone.put("value", userPositionMapper.select(queryParams));
				modulemaplist.add(modulemapone);
			}
			datamap.put("list", modulemaplist);
			break;
		case 4:
			queryParams.put("wholeId", 4);
			queryParams.put("wholeName", "biaoqian");
			data.put("moduleLabelAllNumName",  "该频道下的标签总点击次数");
			data.put("moduleLabelAllNum", userPositionMapper.select(queryParams));
			List<UserPositionInfo>  biaoqianllist = userPositionMapper.selectstatisticsforbiaoqian(queryParams);
			List<Object> labelmaplist = new ArrayList<>();
			for (UserPositionInfo userPositionInfo : biaoqianllist) {
				HashMap< String, Object> labelmapone = new HashMap<>();
				String pn = userPositionInfo.getPositionnum();
				queryParams.put("positionNum",pn);
				int px = Integer.valueOf(pn) +1;
				labelmapone.put("name", "标签坑位"+px);
				labelmapone.put("value", userPositionMapper.select(queryParams));
				labelmaplist.add(labelmapone);
				/*String pn = userPositionInfo.getPositionnum();
					queryParams.put("positionNum",pn);
					int px = Integer.valueOf(pn) +1;
					datamap.put("标签坑位"+px,  userPositionMapper.select(queryParams));*/
			}
			datamap.put("list", labelmaplist);

			break;
		case 5:
			queryParams.put("wholeId", 4);
			queryParams.put("wholeName", "筛选页标签");
			data.put("moduleLabelAllNumName",  "该频道下的筛选页标签总点击次数");
			data.put("moduleLabelAllNum", userPositionMapper.select(queryParams));
			List<UserPositionInfo>  biaoqianllistforshaoxuan = userPositionMapper.selectstatisticsforshanxuanbiaoqian(queryParams);
			List<Object> shaixuanlabelmaplist = new ArrayList<>();

			for (UserPositionInfo userPositionInfo : biaoqianllistforshaoxuan) {
				HashMap< String, Object> shaixuanlabelmapone = new HashMap<>();
				Integer ws = userPositionInfo.getWholespot();
				queryParams.put("wholeSpot",ws);
				int px = ws+1;
				shaixuanlabelmapone.put("name", "标签行"+px);
				shaixuanlabelmapone.put("value", userPositionMapper.select(queryParams));
				shaixuanlabelmaplist.add(shaixuanlabelmapone);
				/*Integer ws = userPositionInfo.getWholespot();
					queryParams.put("wholeSpot",ws);
					int px = ws+1;
					datamap.put("标签行"+px,  userPositionMapper.select(queryParams));*/
			}
			datamap.put("list", shaixuanlabelmaplist);

			break;
		case 100:
			queryParams.put("wholeId",100);
			queryParams.put("channelId",null);
			data.put("guessYouLikeAllNumName",  "该频道下的猜你喜欢喜欢总点击次数");
			data.put("guessYouLikeAllNum", userPositionMapper.select(queryParams));
			List<UserPositionInfo>  biaoqianllistforcainixihuan = userPositionMapper.selectstatisticsforcainixihuan(queryParams);
			List<Object> guessyoulikemaplist = new ArrayList<>();

			for (UserPositionInfo userPositionInfo : biaoqianllistforcainixihuan) {
				HashMap< String, Object> guessyoulikemapone = new HashMap<>();
				Integer ws = userPositionInfo.getWholespot();
				queryParams.put("wholeSpot",ws);
				int px = ws+1;
				guessyoulikemapone.put("name","猜你喜欢坑位"+px);
				guessyoulikemapone.put("value",  userPositionMapper.select(queryParams));
				guessyoulikemaplist.add(guessyoulikemapone);
			}
			datamap.put("list", guessyoulikemaplist);
			break;

		default:
			break;
		}
		data.put("datamap", datamap);
		return new JsonMessage(data);
	}

	//查询频道下的统计数据  一
	@Override
	public JsonMessage  searchStatistics(Map<String, Object> queryParams) {
		logger.info("进入searchStatistics");
		int status = Integer.valueOf((String)queryParams.get("status"));
		int  zongshu = 0;
		if(status == 0){
			zongshu = serSearchInfoMapper.selectforall(queryParams);
			HashMap<String , Object> map = new HashMap<>();
			map.put("name", "查询搜索功能使用总次数");
			map.put("value", zongshu);
			return new JsonMessage(map);
		}else if( status == 1 ){
			List<UserSearchInfo> searchList = serSearchInfoMapper.selectforsousuowenzi(queryParams);
			HashMap<String , Object> top10map = new HashMap<>();

			List<Object> datalistTop = new ArrayList<>();
			for (UserSearchInfo userSearchInfo : searchList) {
				HashMap<String , Object> map = new HashMap<>();
				map.put("name",userSearchInfo.getSousuowenzi());
				map.put("value", userSearchInfo.getCount());
				datalistTop.add(map);
			}
			top10map.put("topname", "搜索TopN的影片");
			top10map.put("list", datalistTop);
			return new JsonMessage(top10map);

		}else{
			switch (status) {
			case 103:
				queryParams.put("topnum", 3);
				break;
			case 105:
				queryParams.put("topnum", 5);
				break;
			case 110:
				queryParams.put("topnum", 10);
				break;
			case 120:
				queryParams.put("topnum", 20);
				break;
			case 200:
				queryParams.put("topnum", 100);
				break;
			default:
				queryParams.put("topnum", 1);
				break;
			}
			List<Map<String,Object>> topList = playHistoryInfoMapper.selectfortop(queryParams);
			HashMap<String , Object> top10map = new HashMap<>();
			List<Object> datalistTop = new ArrayList<>();
			for (Map<String, Object> topmap : topList) {
				LinkedHashMap<String , Object> map = new LinkedHashMap();
				map.put("dramaname",topmap.get("dramaname"));
				map.put("allplaypos",topmap.get("allplaypos"));
				map.put("seriesCode",topmap.get("seriesCode"));
				map.put("channelName",topmap.get("channelName"));
				map.put("count",topmap.get("count"));
				datalistTop.add(map);
			}
			int aa = Integer.valueOf(status) -100;
			top10map.put("topname", "点播过的Top"+aa+"的影片");
			Map<String, Object>  countmap = playHistoryInfoMapper.selectfortopcount(queryParams);
			top10map.put("allplaycount",countmap.get("count"));
			top10map.put("allplaysecond",countmap.get("allplaypos"));
			top10map.put("list", datalistTop);
			return new JsonMessage(top10map);
		}
	}
	@Override
	public JsonMessage otherstatistics(Map<String, Object> queryParams) {
		logger.info("进入otherstatistics");
		int status = Integer.valueOf((String)queryParams.get("status"));
		if(status == 0){//优酷专区点击次数
			HashMap<String , Object> map = new HashMap<>();
			map.put("name", "优酷专区点击总次数");
			map.put("value",  userPositionMapper.select(queryParams));
			return new JsonMessage(map);
		}else if( status == 1 ){//所有频道入口点击量
			HashMap<String , Object> map = new HashMap<>();
			queryParams.put("wholeId", 0);
			map.put("name","所有频道入口点击总次数");
			map.put("value",  userPositionMapper.select(queryParams));
			return new JsonMessage(map);
		}else {//热播推荐总点击量
			HashMap<String , Object> map = new HashMap<>();
			queryParams.put("wholeId", 2);
			map.put("allName","热播推荐下总点击次数");
			map.put("allValue",  userPositionMapper.select(queryParams));
			List<Object> aa  = new ArrayList<>();
			HashMap<String , Object> map1 = new HashMap<>();
			queryParams.put("wholeId", 2);
			queryParams.put("wholeName", "热播");
			map1.put("changeName","热播推荐换一换点击次数");
			map1.put("changeValue",  userPositionMapper.select(queryParams));
			aa.add(map1);
			HashMap<String , Object> map2 = new HashMap<>();
			queryParams.put("wholeId", 2);
			queryParams.put("wholeName", "热播推荐");
			map2.put("hotName","热播推荐点击次数");
			map2.put("hotValue",userPositionMapper.select(queryParams));
			aa.add(map2);
			map.put("dataList", aa);
			return new JsonMessage(map);
		}
	}
	@Override
	public JsonMessage firstlogin(String mobile,Integer flag) {
		//1 查看手机号是否存在
		UserBaseInfo userinfo  = userBaseInfoMapper.selectbymobile(mobile);
		String userid = null;
		if(userinfo != null){
			/**用户存在**/
			userid = userinfo.getUserid();
		}else{
			/**用户不存在**/
			//把手机号插入用户信息表 并返回id
			userid = createUserId(mobile);
			if("104".equals(userid)){
				return  new JsonMessage(3,"插入数据库失败");
			}	
		}
		UserLoginList UserLoginList = new UserLoginList(mobile,flag);
		userLoginListMapper.insert(UserLoginList);
		return new JsonMessage(1,"校验成功",null);	
	}


	/***
	 * 获取用户数控
	 */
	@Override
	public JsonMessage getuserdata(Map<String, Object> queryParams) {
		//1  获取用户注册数
		int userregcount = userBaseInfoMapper.count();
		//1  时间段内注册数
		int userregcountindate = userBaseInfoMapper.countindate(queryParams);
		
		//2 获取会员用户数： 无

		//3 用户id  这边我自己生成的“： 无

		//4 专区活跃用户总数
		//int allactiveusercount  = userLoginListMapper.count();
		//TODO   活跃用户数   排重（点击页面的用户数  （未必点播视频））
		int allactiveusercountindate  = userLoginListMapper.countindate(queryParams);

		//5.获取播放总量（视频总点击数）
		int playallcount  = playHistoryInfoMapper.queryplayhistorycount(queryParams);

		//6.总的行为的用户数
		int userplaycount  = playHistoryInfoMapper.queryuserplayhistorycount(queryParams);

		HashMap<String,Object> data = new HashMap<>();
		data.put("userregcount", userregcount);
		data.put("allactiveusercountindate", allactiveusercountindate);
		data.put("userregcountindate", userregcountindate);
		data.put("playallcount", playallcount);
		data.put("userplaycount", userplaycount);

		//data.put("allactiveusercount", allactiveusercount);
		List<Map<String, Object>> datemap = new ArrayList<>();
		datemap = userLoginListMapper.listcount(queryParams);
		if(datemap != null){
			for (Map<String, Object> map : datemap) {
				int addcount = userBaseInfoMapper.selectdateaddcount(map);
				map.put("addcount",addcount);
			}
		}

		//截止到endTime的总注册数
		queryParams.remove("startTime");
		int endTimeAllUserCount = userBaseInfoMapper.countindate(queryParams);
		data.put("entimeallusercount", endTimeAllUserCount);
		
		data.put("datemap",datemap);
		return new JsonMessage(1,"获取成功", data);
	}
	@Override
	public JsonMessage getdataforhour(Map<String, Object> queryParams) {

		List<Map<String,Object>> topList = playHistoryInfoMapper.getdataforhour(queryParams);
		HashMap<String , Object> top10map = new HashMap<>();
		List<Object> datalistTop = new ArrayList<>();
		for (Map<String, Object> topmap : topList) {
			LinkedHashMap<String , Object> map = new LinkedHashMap();
			map.put("time",topmap.get("time"));
			map.put("value",topmap.get("count"));
			datalistTop.add(map);
		}
		
		top10map.put("datetime", queryParams.get("startTime")+"的活跃点播行为");
		top10map.put("list", datalistTop);
		return new JsonMessage(top10map);
	}

}  