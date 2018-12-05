package com.pbtd.manager.user.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pbtd.manager.system.domain.UserKeep;
import com.pbtd.manager.system.mapper.UserKeepMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.system.mapper.SysCountChannelMapper;
import com.pbtd.manager.system.mapper.SysCountSeriesMapper;
import com.pbtd.manager.system.mapper.SysCountUserMapper;
import com.pbtd.manager.user.domain.SysCountSeries;
import com.pbtd.manager.user.domain.SysCountUser;
import com.pbtd.manager.user.mapper.UserBaseInfoMapper;
import com.pbtd.manager.user.mapper.UserLoginListMapper;
import com.pbtd.manager.user.mapper.UserPlayHistoryInfoPhoneMapper;
import com.pbtd.manager.user.page.UpgradeQueryObject;
import com.pbtd.manager.user.service.SysCountService;
import com.pbtd.manager.user.util.DateFormaUtil;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.vod.phone.album.domain.Vodalbuminfo;
import com.pbtd.manager.vod.phone.album.mapper.VodAlbuminfoMapper;

@Service
public class SysCountServiceImpl implements SysCountService {
	private static final Logger logger = LoggerFactory.getLogger(SysCountServiceImpl.class);

	@Autowired
	private UserBaseInfoMapper userbaseinfoMapper;
	@Autowired
	private UserLoginListMapper userloginlistMapper;
	@Autowired
	private UserPlayHistoryInfoPhoneMapper playhistoryphoneMapper;
	@Autowired
	private SysCountUserMapper sysuserMapper;
	@Autowired
	private SysCountSeriesMapper seriesMapper;
	@Autowired
	private SysCountChannelMapper channelMapper;
	@Autowired
	private VodAlbuminfoMapper vodPhoneAlbumMapper;
	@Autowired
	private UserKeepMapper userKeepMapper;




	@Override
	public PageResult queryList(UpgradeQueryObject qo) {
		return new PageResult();
	}	

	@Override
	public List<Map<String, Object>> find(Map<String, Object> queryParams) {
		
		 List<Map<String, Object>> list = sysuserMapper.find(queryParams);
		 Map<String, Object> allmap = new HashMap<>();
		 String createtime = "统计";
		 long usercount = 100;
		 long  addusercount = 0;
		 long playusercount = 0;
		 String addplayusercount = "/";
		 long playcount  = 0;
		 String addplaycount = "/";
		 long activecount = 0;
		 String addactivecount = "/";
		 long activeusercount  = 0;
		 String  addactiveusercount = "/";
		 float playtime = 0;
	//	 float   activedegree  = 0;
		 float  playactivedegree  = 0;
		 float fourgplaytime  = 0;
		 float  wifiplaytime  = 0;
		 float playtimetoone = 0;
		 long fourgplayusercount = 0;
		 long wifiplayusercount = 0;
		 for (Map<String, Object> map : list) {
			 usercount = Long.valueOf( String.valueOf(map.get("usercount")));
			 addusercount += Long.valueOf( String.valueOf(map.get("addusercount")));
			 playusercount+= Long.valueOf( String.valueOf(map.get("playusercount")));
			 playcount +=  Long.valueOf( String.valueOf(map.get("playcount")));
			 activecount +=  Long.valueOf( String.valueOf(map.get("activecount")));
			 activeusercount +=  Long.valueOf( String.valueOf(map.get("activeusercount")));
			 playtime +=  Float.valueOf(map.get("playtime").toString().replace(",", ""));
			// activedegree +=  Long.valueOf( String.valueOf(map.get("activedegree")));
			// playactivedegree +=  Long.valueOf( String.valueOf(map.get("playactivedegree")));
			 fourgplaytime +=  Float.valueOf(map.get("fourgplaytime").toString().replace(",", ""));
			 wifiplaytime +=  Float.valueOf(map.get("wifiplaytime").toString().replace(",", ""));
			// playtimetoone +=  Long.valueOf( String.valueOf(map.get("playtimetoone")));
			 fourgplayusercount +=  Long.valueOf( String.valueOf(map.get("fourgplayusercount")));
			 wifiplayusercount +=  Long.valueOf( String.valueOf(map.get("wifiplayusercount")));
		}
		 allmap.put("createtime",createtime);
		 allmap.put("usercount",usercount);
		 allmap.put("addusercount",addusercount);
		 allmap.put("playusercount",playusercount);
		 allmap.put("addplayusercount",addplayusercount);
		 allmap.put("playcount",playcount);
		 allmap.put("addplaycount",addplaycount);
		 allmap.put("activecount",activecount);
		 allmap.put("addactivecount",addactivecount);
		 allmap.put("activeusercount",activeusercount);
		 allmap.put("addactiveusercount",addactiveusercount);
		 DecimalFormat df = new DecimalFormat("0.00");
		 String aa = df.format((float)playtime);

		 allmap.put("playtime",aa);
		// allmap.put("activedegree",activeusercount/);
		 String ss = df.format((float)playusercount/activeusercount*100);
		 allmap.put("playactivedegree",ss);
		 allmap.put("fourgplaytime",fourgplaytime);
		 allmap.put("wifiplaytime",wifiplaytime);
		 allmap.put("playtimetoone", (float)(Math.round((float)playtime/playusercount*100))/100);
		 allmap.put("fourgplayusercount",fourgplayusercount);
		 allmap.put("wifiplayusercount",wifiplayusercount);
		 list.add(allmap);
		return list;
	}	
	
	//方法二： BigDecimal
    private static String big2(float d) {
        BigDecimal d1 = new BigDecimal(Double.toString(d));
        BigDecimal d2 = new BigDecimal(Integer.toString(1));
        // 四舍五入,保留2位小数
        return d1.divide(d2,2,BigDecimal.ROUND_HALF_UP).toString();
    }

	@Override
	public int count(Map<String, Object> queryParams) {
		return sysuserMapper.count(queryParams);
	}

	@Override
	public List<Map<String, String>> page(Map<String, Object> queryParams) {
		return sysuserMapper.page(queryParams);
	}

	/**
	 * 用户统计数据及保存
	 */
	@Override
	public void usercount(Date now) {
		Map<String,Object> queryParams = new HashMap<>();
		//1. 昨日的时间
		Date yesterday = DateFormaUtil.yesterday(now);
		//2. 今日之前的总用户数
		queryParams.put("endtime", DateFormaUtil.getNowDayStart(now));
		int usercount =  userbaseinfoMapper.usercount(queryParams);
		queryParams.put("starttime", DateFormaUtil.getNowDayStart(yesterday));
		//3. 昨日的点播数
		int playcount = playhistoryphoneMapper.playusercount(queryParams);
		//4.1  昨日的点播用户数
		queryParams.put("user","user");
		int playusercount = playhistoryphoneMapper.playusercount(queryParams);
		int fourgplayusercount = 0;
		int wifiusercount = 0;
		try {
			//4.2  昨日的4g点播用户数
			queryParams.put("usernetStatus", "0");
			fourgplayusercount = playhistoryphoneMapper.playusercount(queryParams);
			//4.3  昨日的点播用户数
			queryParams.put("user", "user");
			wifiusercount = playhistoryphoneMapper.playusercount(queryParams);
		} catch (Exception e) {
			logger.info("查询4g相关参数异常1"+e.toString());
		}
		//5.点击页面总次数	
		int activecount = userloginlistMapper.activecount(queryParams);
		//6.活跃用户
		queryParams.put("activeuser", "activeuser");
		int activeusercount = userloginlistMapper.activecount(queryParams);
		//7. 昨日新增用户数
		int addusercount =  userbaseinfoMapper.usercount(queryParams);
		//00000000. 获取前日的记录  .0000000
		queryParams.put("starttimestring", DateFormaUtil.beforeyestodayString(now).trim()+"%");
		SysCountUser yestodayusercount = sysuserMapper.selectforyestoday(queryParams);
		int addplaycount = 0;
		int addplayusercount = 0;
		int addactivecount = 0;
		int addactiveusercount = 0;
		if(yestodayusercount != null){
			//8.较昨天播放次数差
			addplaycount =  playcount - (yestodayusercount.getPlaycount() == null?0:yestodayusercount.getPlaycount());;
			//9.较昨日播放用户差
			addplayusercount =  playusercount - (yestodayusercount.getPlayusercount() == null?0:yestodayusercount.getPlayusercount());
			//10.较昨天页面点击次数差
			addactivecount = activecount-(yestodayusercount.getActivecount() == null?0:yestodayusercount.getActivecount());
			//11.较昨天活跃人数差
			addactiveusercount = activeusercount-(yestodayusercount.getActiveusercount() == null?0:yestodayusercount.getActiveusercount());
		}else{
			addplaycount =  playcount -0;
			addplayusercount =  playusercount - 0;
			addactivecount= activecount -0;
			addactiveusercount= activeusercount -0;
		}
		//12.1 昨日的播放总时长
		queryParams.remove("user");
		float playtime = playhistoryphoneMapper.playtimecount(queryParams);
		float fourgplaytime = 0;
		float wifiplaytime = 0;
		try {
			//12.2昨日4g播放总时长
			queryParams.put("netStatus","0");
			fourgplaytime = playhistoryphoneMapper.playtimecount(queryParams);
			//12.3昨日wifi播放总时长
			queryParams.put("netStatus","1");
			wifiplaytime = playhistoryphoneMapper.playtimecount(queryParams);
		} catch (Exception e) {
			logger.info("查询4g相关参数异常2"+e.toString());
		}
		//13. 活跃度
		float activedegree = (float)activeusercount/usercount;
		//14. 点播活跃度
		float playactivedegree = (float)playusercount/activeusercount;
		//14. 人均播放时长
		float playtimetoone = playtime/playusercount;

		//15.4g

		SysCountUser todayuser = new SysCountUser(null,yesterday,usercount,playcount,playusercount,activecount,activeusercount,addusercount,addplaycount,addplayusercount,addactivecount,addactiveusercount,playtime,activedegree,playactivedegree,playtimetoone,fourgplayusercount,wifiusercount,fourgplaytime,wifiplaytime);

		queryParams.put("starttimestring", DateFormaUtil.yestodayString(now).trim()+"%");
		SysCountUser todayusercount = sysuserMapper.selectforyestoday(queryParams);
		if(todayusercount == null){

		}else{
			sysuserMapper.deleteByPrimaryKey(queryParams);
		}
		//		sysuserMapper.insert(todayuser);
		try {
			sysuserMapper.insert(todayuser);
		} catch (Exception e) {
			logger.info("添加异常全量异常，尝试添加部分"+ e.getMessage());
			todayuser = new SysCountUser(null,yesterday,usercount,playcount,playusercount,activecount,activeusercount,addusercount,addplaycount,addplayusercount,addactivecount,addactiveusercount,playtime,activedegree,playactivedegree,playtimetoone);
			try {
				sysuserMapper.insert(todayuser);
				logger.info("添加部分异常，尝试添加不部分"+ e);
			} catch (Exception e2) {
				logger.info("添加异常部分异常。。。。。。"+ e);
			}
		}
	}

	/**
	 * 专辑统计数据及保存
	 */
	@Override
	public void seriescount(Date now) {
		Map<String,Object> queryParams = new HashMap<>();
		String yestoday = DateFormaUtil.yestodayString(now).trim();
		queryParams.put("starttime", yestoday+"%");
		//1、获取昨日的专辑排行榜 字段都有：seriesCode,dramaname,channel,channelName,con,pos,usercon
		List<Map<String,Object>>  list = playhistoryphoneMapper.select(queryParams);
		if(list != null || list.size()>0){
			seriesMapper.delete(queryParams);
		}
		//2、遍历添加
		for (Map<String, Object> map : list) {
			String seriesCode = map.get("seriesCode").toString();
			Integer playcount = Integer.valueOf((map.get("con").toString()));
			Float playtime = Float.valueOf(map.get("pos").toString().trim().replace(",", ""));
			Integer playusercount =  Integer.valueOf(map.get("usercon").toString());
			Vodalbuminfo vodalbuminfo = vodPhoneAlbumMapper.load(map);
			String seriesname = "";
			String channel = "";
			String channelname = "";
			if(vodalbuminfo != null){
				seriesname = vodalbuminfo.getSeriesName();
				channel = vodalbuminfo.getChannel();
				channelname = vodalbuminfo.getChannelName();
			}
			Date yesterday = DateFormaUtil.yesterday(now);
			SysCountSeries sysCountSeries = new SysCountSeries(null,yesterday,seriesCode,seriesname,playtime,playcount,playusercount,channel,channelname);
			try {
				seriesMapper.insert(sysCountSeries);
			} catch (Exception e) {
				System.out.println("重复添加~");			
			}
		}

	}

	@Override
	public List<Map<String, Object>> seriespagefind(Map<String, Object> queryParams) {
		return seriesMapper.find(queryParams);
	}

	@Override
	public int seriespagecount(Map<String, Object> queryParams) {
		return seriesMapper.count(queryParams);
	}

	@Override
	public List<Map<String, Object>> findallchannel() {
		return seriesMapper.findallchannel();
	}

	@Override
	public List<UserKeep> getUserKeep(Map<String, Object> queryParams) {
		return userKeepMapper.getUserKeep(queryParams);
	}

	@Override
	public int insertUserKeep(Map<String, Object> queryParams) {

		try{
			String endTime = (String) queryParams.get("endTime");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date baseEndTime = sdf.parse(endTime);
			Date baseStartTime = baseEndTime;

			for(int i = 0 ; i < 31; i++){
				baseStartTime = DateFormaUtil.yesterday(baseStartTime);
				insertUserKeep(baseStartTime, baseEndTime);
			}
			return 0;

		}catch (Exception e){

		}
		return 1;
	}

	private void insertUserKeep(Date baseStartTime, Date baseEndTime) {
		if(isUserKeepExist(baseStartTime, baseEndTime)){
			return;
		}

		Map<String, Object> queryParams = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		queryParams.put("baseStartTime", sdf.format(baseStartTime));
		queryParams.put("baseEndTime", sdf.format(baseEndTime));

		int keepUser = userloginlistMapper.selectactiveuserkeepcount(queryParams);
		int endUser = userloginlistMapper.getUserCount(queryParams);
		int startUser = userbaseinfoMapper.getUserCount(queryParams);
		float keepPercent = (startUser == 0 ? 0 : (float) keepUser/startUser);

		Map<String, Object> insertParams = new HashMap<>();
		insertParams.put("baseStartTime", baseStartTime);
		insertParams.put("baseEndTime", baseEndTime);
		insertParams.put("startUser",startUser);
		insertParams.put("endUser", endUser);
		insertParams.put("keepUser", keepUser);
		insertParams.put("keepPercent", keepPercent);
		userKeepMapper.insertData(insertParams);
		logger.info("*************************keepPercent: " + keepPercent + ",startUser: " + endUser);
	}

	private boolean isUserKeepExist(Date baseStartTime, Date baseEndTime) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("baseStartTime", baseStartTime);
		queryParams.put("baseEndTime", baseEndTime);
		int i = userKeepMapper.isUserKeepExist(queryParams);
		if(i > 0){
			return true;
		}
		return false;
	}


	@Override
	public int channelpagecount(Map<String, Object> queryParams) {
		return channelMapper.count(queryParams);
	}

	@Override
	public List<Map<String, Object>> channelpagefind(Map<String, Object> queryParams) {
		return channelMapper.find(queryParams);
	}

	@Override
	public List<Map<String, Object>> mobilepagefind(Map<String, Object> queryParams) {
		return playhistoryphoneMapper.selectmobilecount(queryParams);
	}

	@Override
	public int mobilepagecount(Map<String, Object> queryParams) {
		return playhistoryphoneMapper.mobilecount(queryParams);
	}

	@Override
	public List<Map<String, Object>> mobileseriespagefind(Map<String, Object> queryParams) {
		return playhistoryphoneMapper.selectmobileseriescount(queryParams);
	}

	@Override
	public List<Map<String, Object>> userkeeppagefind(Map<String, Object> queryParams) {
		for(Map.Entry entry : queryParams.entrySet()){
			System.out.println("----------------------------------queryParams  key:" + entry.getKey());
			System.out.println("----------------------------------queryParams  value:" + entry.getValue());
		}

		int keepusercount = userloginlistMapper.selectactiveuserkeepcount(queryParams);
		queryParams.put("activeuser", "activeuser");
		queryParams.put("endtime", queryParams.get("startendtime"));
		int startusercount  = userloginlistMapper.activecount(queryParams);
		DecimalFormat df = new DecimalFormat("0.00");//格式化小数  
		String keepdegree = df.format((float)keepusercount/startusercount*100);//返回的是String类型 
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("keepusercount", keepusercount);
		map.put("startusercount", startusercount);
		map.put("keepdegree", keepdegree);
		list.add(map);
		return list;
	}

	@Override
	public int mobileseriespagecount(Map<String, Object> queryParams) {
		return playhistoryphoneMapper.mobileseriespagecount(queryParams);
	}


}