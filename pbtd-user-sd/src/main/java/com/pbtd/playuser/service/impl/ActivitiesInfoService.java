package com.pbtd.playuser.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playuser.domain.ActivitiesBaseInfo;
import com.pbtd.playuser.domain.ActivityPrizesInfo;
import com.pbtd.playuser.domain.ActivityPrizesOutPut;
import com.pbtd.playuser.domain.UserActivitiesInfo;
import com.pbtd.playuser.domain.UserBaseInfo;
import com.pbtd.playuser.domain.WinRecordList;
import com.pbtd.playuser.mapper.ActivitiesBaseInfoMapper;
import com.pbtd.playuser.mapper.ActivityPrizesInfoMapper;
import com.pbtd.playuser.mapper.UserActivitiesInfoMapper;
import com.pbtd.playuser.mapper.UserBaseInfoMapper;
import com.pbtd.playuser.mapper.WinRecordListMapper;
import com.pbtd.playuser.page.ActivityObject;
import com.pbtd.playuser.page.ActivityResult;
import com.pbtd.playuser.service.IActivitiesInfoService;
import com.pbtd.playuser.util.JsonMessage;
import com.pbtd.playuser.util.LotteryUtil;

/**
 * 
 * @author vic
 * @param <T>
 * @desc UserBaseInfo service
 * 
 */
@Service
public class ActivitiesInfoService<T> implements IActivitiesInfoService {
	private static final Logger logger = LoggerFactory.getLogger(ActivitiesInfoService.class);
	@Autowired
	private UserActivitiesInfoMapper userActivitiesInfoMapper;
	@Autowired
	private ActivityPrizesInfoMapper activityPrizesInfoMapper;
	@Autowired
	private ActivitiesBaseInfoMapper activitiesBaseInfoMapper;
	@Autowired
	private WinRecordListMapper winRecordListMapper;
	@Autowired
	private UserBaseInfoMapper userBaseInfoMapper;

	/**
	 * 是否参与活动
	 */
	@Override
	public JsonMessage isnotJoinActivities(HashMap<String, Object> params) {
		String activeName = (String) params.get("activeName");
		List<ActivityPrizesInfo> list = activityPrizesInfoMapper.queryAll(activeName);
		if(list == null || list.size() == 0 ){
			logger.info("-2,活动ID有误");
			return new JsonMessage(-2,"活动ID有误");
		}
		UserBaseInfo user = userBaseInfoMapper.select(params);
		if(user == null){
			logger.info("-4,用户不存在");
			return new JsonMessage(-4,"用户不存在");
		}
		params.put("userMoblie", user.getUsermobile());
		UserActivitiesInfo useractivety = userActivitiesInfoMapper.selectByPrimary(params);
		if (useractivety != null) {//玩过
			int residcode = useractivety.getActivenum();
			// 判断是否今天玩了三次
			if (residcode >= 3) {
				logger.info("0,不要贪心哦，明天再来吧！");
				return new ActivityResult<T>(0,"不要贪心哦，明天再来吧！",0);
			} else {
				//参与活动次数+1
				params.put("residcode", residcode);
				userActivitiesInfoMapper.updateByPrimary(params);
			}
			// 中过奖了
			if (useractivety.getIsnotwin() == 1) {
				//	return new ActivityMessage(0,3-residcode, "哎呦！就差一点点!");
				logger.info("0,哎呦！就差一点点!");
				return new ActivityResult<T>(0,"哎呦！就差一点点!",2-residcode);
			}
		} else{
			//第一次
			userActivitiesInfoMapper.insert(params);
		}
		ActivityPrizesInfo actevityinfo = null;
		// 进行抽奖操作 判断是否成功
		try {
			actevityinfo = activitiesGetPrize(activeName);
		} catch (Exception e) {
			logger.info("-4,哎呦！系统异常");
			return new JsonMessage(-4, "哎呦！系统异常");
		}
		// 0 ,未中奖
		if (actevityinfo.getPrizecode() == 0) {
			int residcodes = params.get("residcode")==null ? 0 : (int)params.get("residcode") ;
			return new ActivityResult<T>(0,actevityinfo.getLanguage(),2-residcodes);
		} else {
			// 中奖后操作
			return yesPrizeoPerate(params, actevityinfo);
		}
	}

	// 是否中奖
	public ActivityPrizesInfo activitiesGetPrize(String activename) {
		List<ActivityPrizesInfo> aps = activityPrizesInfoMapper.queryAll(activename);
		ActivityPrizesInfo ap = LotteryUtil.lottery(aps);
		if (ap.getPrizecode() > 0) {
			ap.setUpdatetime(new Date());
			int row = activityPrizesInfoMapper.updateprizeresNum(ap);
			if (row < 1) {
				ap.setPrizecode(LotteryUtil.NOT_WINNERS_CODE);
				ap.setLanguage(LotteryUtil.THANK_YOU_PATRONIZE_ONE);
				return ap;
			}
		}
		return ap;
	}

	// 中奖后的操作
	public JsonMessage yesPrizeoPerate(HashMap<String, Object> params, ActivityPrizesInfo actevityinfo) {
		// 活动中奖记录表 新增消息
		WinRecordList WinRecordList = new WinRecordList();
		//UserBaseInfo userinfo = userBaseInfoMapper.select(params);
			WinRecordList.setActivename((String) params.get("activeName"));
			WinRecordList.setUserid((String) params.get("userid"));
			WinRecordList.setUsermobile((String)params.get("userMoblie"));
			WinRecordList.setPrizename(actevityinfo.getPrizename());
			WinRecordList.setPrizecode(actevityinfo.getPrizecode());
			try {
				winRecordListMapper.insert(WinRecordList);
				logger.info("用户" + (String) params.get("userid") + "中奖" + actevityinfo.getPrizename());
				// 休改用户中奖状态
				userActivitiesInfoMapper.update(params);
			} catch (Exception e) {
				logger.info("修改数据库数据失败");
			}
			int residcodes = params.get("residcode")==null ? 0 : (int)params.get("residcode") ;
			return new ActivityResult<T>(1,actevityinfo.getLanguage(),2-residcodes,actevityinfo.getPrizecode());
	}

	/**
	 * 保存收货地址
	 */
	@Override
	public JsonMessage saveAddress(HashMap<String, Object> params) {
		WinRecordList winRecordList = winRecordListMapper.select(params);
		if(winRecordList != null){
			winRecordListMapper.update(params);
			return new JsonMessage(1,"保存用户地址成功");
		}
		return new JsonMessage(0,"该用户未中奖");
	}

	/**
	 * 获取活动的项目
	 */
	@Override
	public JsonMessage queryActivity(HashMap<String, Object> params) {
		List<ActivitiesBaseInfo> list = activitiesBaseInfoMapper.queryAll();
		if(list == null || list.size() == 0 ){
			logger.info("-1,暂无活动");
			return new ActivityObject<Object>(0,"暂无活动");
		}else{
			ActivitiesBaseInfo  active = list.get(0);
			List<ActivityPrizesOutPut> prizelist = activityPrizesInfoMapper.queryForOutPut(active.getActivename());
			params.put("activeName", active.getActivename());
			UserActivitiesInfo useracti = userActivitiesInfoMapper.selectByPrimary(params);
			int residcode = 0;
			if(useracti != null){
				Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date zero = calendar.getTime();
				if(useracti.getUpdatetime().compareTo(zero) < 0){
					//昨天之前
					userActivitiesInfoMapper.updateReset(params);
				residcode = 0;
				}else{
					residcode = useracti.getActivenum();
				}
			}
			return new ActivityObject<Object>(1,active,prizelist,3-residcode);
		}
	}

}