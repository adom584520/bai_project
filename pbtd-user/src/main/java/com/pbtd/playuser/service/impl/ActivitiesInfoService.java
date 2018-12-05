package com.pbtd.playuser.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.playuser.component.ConstantBeanConfig;
import com.pbtd.playuser.domain.ActivitiesBaseInfo;
import com.pbtd.playuser.domain.ActivitiesBaseOutput;
import com.pbtd.playuser.domain.ActivitiesSignIn;
import com.pbtd.playuser.domain.ActivityPrizesInfo;
import com.pbtd.playuser.domain.PlayHistoryInfo;
import com.pbtd.playuser.domain.UserActivitiesInfo;
import com.pbtd.playuser.domain.UserBaseInfo;
import com.pbtd.playuser.domain.WinRecordList;
import com.pbtd.playuser.mapper.ActivitiesBaseInfoMapper;
import com.pbtd.playuser.mapper.ActivitiesSignInMapper;
import com.pbtd.playuser.mapper.ActivityPrizesInfoMapper;
import com.pbtd.playuser.mapper.PlayHistoryInfoPhoneMapper;
import com.pbtd.playuser.mapper.UserActivitiesInfoMapper;
import com.pbtd.playuser.mapper.UserBaseInfoMapper;
import com.pbtd.playuser.mapper.WinRecordListMapper;
import com.pbtd.playuser.mapper.ZhuanpanPlayseriescodeMapper;
import com.pbtd.playuser.page.ActivityFLHDOutPut;
import com.pbtd.playuser.page.ActivityObjectOutPut;
import com.pbtd.playuser.page.ActivityOtherObject;
import com.pbtd.playuser.page.ActivityResult;
import com.pbtd.playuser.page.ActivityZPHDOutPut;
import com.pbtd.playuser.service.IActivitiesInfoService;
import com.pbtd.playuser.util.ActivitiesConstant;
import com.pbtd.playuser.util.JsonMessage;
import com.pbtd.playuser.util.LotteryUtil;
import com.pbtd.playuser.util.MyDate;

/**
 * 
 * @author vic
 * @param <T>
 * @desc UserBaseInfo service
 * 
 */
@SuppressWarnings("all")
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
	@Autowired
	private ActivitiesSignInMapper activitiesSignInMapper;
	@Autowired
	private ConstantBeanConfig constantBeanConfig;
	@Autowired
	private ZhuanpanPlayseriescodeMapper zhuanpanPlayseriescodeMapper;
	@Autowired
	private PlayHistoryInfoPhoneMapper playHistoryInfoPhoneMapper;

	/**
	 * 获取所有活动的项目
	 */
	@Override
	public JsonMessage queryActivity(HashMap<String, Object> params) {
		List<ActivitiesBaseInfo> list = activitiesBaseInfoMapper.queryAll();
		if (list == null || list.size() == 0) {
			logger.info("-1,暂无活动");
			return new ActivityResult<Object>(0, "暂无活动", null);
		} else {
			List<ActivitiesBaseOutput> ActivitiesBaseOutputlist = new ArrayList<>();
			for (ActivitiesBaseInfo activitiesBaseInfo : list) {
				ActivitiesBaseOutputlist.add(
						new ActivitiesBaseOutput(activitiesBaseInfo.getActivecode(), activitiesBaseInfo.getActivename(),
								activitiesBaseInfo.getActivetype(), activitiesBaseInfo.getActivefloat()));
			}
			return new ActivityResult<Object>(ActivitiesBaseOutputlist);
		}
	}

	/**
	 * 获取转盘活动的具体描述
	 */

	@Override
	public JsonMessage queryActivityForZP(HashMap<String, Object> params) {
		HashMap<String, Object> output = new HashMap<>();
		ActivitiesBaseInfo active = activitiesBaseInfoMapper.queryOne(params);
		if (active == null) {
			logger.info("-1,暂无活动");
			return new ActivityResult<Object>(0, "暂无活动", null);
		} else {
			// 1.1、添加活动描述
			output.put("Active", new ActivityObjectOutPut(active));
			// 1.2、添加用户行为信息
			// 1.2.1获取用户是否满足条件
			ActivityZPHDOutPut activityZPHDOutPut = new ActivityZPHDOutPut();
			activityZPHDOutPut.setPlayneedtime("需观看视频：" + ConstantBeanConfig.PLAY_TIME / 60 + "min");
			params.put("startTime", active.getStarttime());
			PlayHistoryInfo playHistory = playHistoryInfoPhoneMapper.queryOneCode(params);
			// 查询有哪些需要参与的影片
			if (playHistory == null) {
				activityZPHDOutPut.setCode(2);
				activityZPHDOutPut.setMessage("不满足参与活动条件");
				activityZPHDOutPut.setResidcode(0);
			} else if (playHistory.getPlaypos() > ConstantBeanConfig.PLAY_TIME) {
				activityZPHDOutPut.setCode(1);
				activityZPHDOutPut.setMessage("满足活动条件");
				activityZPHDOutPut.setResidcode(cherkUseractivityresidcode(params));
			} else {
				activityZPHDOutPut.setCode(2);
				activityZPHDOutPut.setMessage("不满足参与活动条件");
				activityZPHDOutPut.setResidcode(0);
			}
			output.put("User", activityZPHDOutPut);
		}
		return new ActivityResult(output);

	}

	/**
	 * 参加转盘活动
	 */
	@Override
	public JsonMessage isnotJoinActivities(HashMap<String, Object> params) {
		String activeCode = (String) params.get("activeCode");
		List<ActivityPrizesInfo> list = activityPrizesInfoMapper.queryAll(activeCode);
		if (list == null || list.size() == 0) {
			logger.info("-2,活动ID有误");
			return new JsonMessage(-2, "活动ID有误");
		}
		UserBaseInfo user = userBaseInfoMapper.select(params);
		if (user == null) {
			logger.info("-4,用户不存在");
			return new JsonMessage(-4, "用户不存在");
		}
		params.put("userMoblie", user.getUsermobile());
		UserActivitiesInfo useractivety = userActivitiesInfoMapper.selectByPrimary(params);
		if (useractivety != null) {// 玩过
			int residcode = useractivety.getActivenum();
			// 判断是否今天玩了三次
			if (residcode >= 3) {
				logger.info("0,不要贪心哦，明天再来吧！");
				return new ActivityResult<T>(0, "不要贪心哦，明天再来吧！", 0);
			} else {
				// 参与活动次数+1
				params.put("residcode", residcode);
				userActivitiesInfoMapper.updateByPrimary(params);
			}
			// 中过奖了
			if (useractivety.getIsnotwin() == 1) {
				// return new ActivityMessage(0,3-residcode, "哎呦！就差一点点!");
				logger.info("0,哎呦！就差一点点!");
				return new ActivityResult<T>(0, "哎呦！就差一点点!", 2 - residcode);
			}
		} else {
			// 第一次
			userActivitiesInfoMapper.insert(params);
		}
		ActivityPrizesInfo actevityinfo = null;
		// 进行抽奖操作 判断是否成功
		try {
			actevityinfo = activitiesGetPrize(activeCode);
		} catch (Exception e) {
			logger.info("-4,哎呦！系统异常");
			return new JsonMessage(-4, "哎呦！系统异常");
		}
		// 0 ,未中奖
		if (actevityinfo.getPrizecode() == 0) {
			int residcodes = params.get("residcode") == null ? 0 : (int) params.get("residcode");
			return new ActivityResult<T>(0, actevityinfo.getLanguage(), 2 - residcodes);
		} else {
			// 中奖后操作
			return yesPrizeoPerate(params, actevityinfo);
		}
	}

	// 判断用户剩余参与活动次数
	public int cherkUseractivityresidcode(HashMap<String, Object> params) {
		// 用户剩余次数
		UserActivitiesInfo useracti = userActivitiesInfoMapper.selectByPrimary(params);
		int residcode = 0;
		if (useracti != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date zero = calendar.getTime();
			if (useracti.getUpdatetime().compareTo(zero) < 0) {
				// 昨天之前
				userActivitiesInfoMapper.updateReset(params);
				residcode = 0;
			} else {
				residcode = useracti.getActivenum();
			}
		} else {
			residcode = 3;
		}
		return 3 - residcode;
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
		// UserBaseInfo userinfo = userBaseInfoMapper.select(params);
		WinRecordList.setActivecode((String) params.get("activeCode"));
		WinRecordList.setUserid((String) params.get("userid"));
		WinRecordList.setUsermobile((String) params.get("userMoblie"));
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
		int residcodes = params.get("residcode") == null ? 0 : (int) params.get("residcode");
		return new ActivityResult<T>(1, actevityinfo.getLanguage(), 2 - residcodes, actevityinfo.getPrizecode());
	}

	/**
	 * 保存收货地址
	 */
	@Override
	public JsonMessage saveAddress(HashMap<String, Object> params) {
		WinRecordList winRecordList = winRecordListMapper.select(params);
		if (winRecordList != null) {
			winRecordListMapper.update(params);
			return new JsonMessage(1, "保存用户地址成功");
		}
		return new JsonMessage(0, "该用户未中奖");
	}

	/**
	 * 获取其他活动的具体描述
	 */
	@Override
	public JsonMessage queryActivityForOther(HashMap<String, Object> params) {
		HashMap<String, Object> output = new HashMap<>();
		ActivitiesBaseInfo active = activitiesBaseInfoMapper.queryOne(params);
		if (active == null) {
			logger.info("-1,暂无活动");
			return new ActivityResult<Object>(0, "暂无活动", null);
		} else {
			// 1.1、添加活动描述
			output.put("Active", new ActivityOtherObject(active));
			ActivityResult userresult = new ActivityResult<>();
			UserActivitiesInfo useractivety = userActivitiesInfoMapper.selectByPrimary(params);
			if (useractivety != null) {
				userresult.setCode(2);
				userresult.setMessage("已经参加过该活动!");
			} else {
				userresult.setCode(1);
				userresult.setMessage("可以参加该活动");
			}
			output.put("User", userresult);
			return new ActivityResult(output);
		}
	}

	@Override
	public JsonMessage upgradeActivities(HashMap<String, Object> params) {
		return null;
	}

	@Override
	@Transactional
	public JsonMessage signInActivities(HashMap<String, Object> params) {
		String userId = (String) params.get("userId");
		if (userId == null) {
			return new JsonMessage(0, "未输入userId");
		}
		ActivitiesBaseInfo activityInfo = activitiesBaseInfoMapper.queryOne(params);
		ActivityResult<Map<String, Object>> result = new ActivityResult<Map<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		long row = activitiesSignInMapper.queryCountByUserIdTime(userId, MyDate.getNowDate());
		int countDay = (int) activitiesSignInMapper.queryConutByUserId(userId);
		List<Integer> fluxList = new ArrayList<Integer>();
		int countFluxNumber = 0;
		if (countDay > 0) {
			if (countDay >= 15) {
				countFluxNumber = constantBeanConfig.fluxNumber * 5 * (1 + 2 + 4);
			} else if (countDay > 10 && countDay < 15) {
				countFluxNumber = constantBeanConfig.fluxNumber * 5 * (1 + 2)
						+ (countDay - 10) * constantBeanConfig.fluxNumber * 4;
			} else if (countDay > 5 && countDay <= 10) {
				countFluxNumber = constantBeanConfig.fluxNumber * 5
						+ (countDay - 5) * constantBeanConfig.fluxNumber * 2;
			} else if (countDay > 0 && countDay <= 5) {
				countFluxNumber = constantBeanConfig.fluxNumber * (countDay - 0);
			}
			int index = countDay % 7 == 0 ? countDay - 7 : countDay - countDay % 7;
			index++;
			for (int i = 1; i <= 7; i++) {
				if (index <= 5) {
					fluxList.add(constantBeanConfig.fluxNumber);
				} else if (index > 5 && index <= 10) {
					fluxList.add(2 * constantBeanConfig.fluxNumber);
				} else if (index > 10 && index <= 15) {
					fluxList.add(4 * constantBeanConfig.fluxNumber);
				} else {
					fluxList.add(0);
				}
				index++;
			}
		} else {
			int index = 0;
			for (int i = 1; i <= 7; i++) {
				if (index <= 5) {
					fluxList.add(constantBeanConfig.fluxNumber);
				} else if (index > 5 && index <= 10) {
					fluxList.add(2 * constantBeanConfig.fluxNumber);
				}
			}
		}
		HashMap<String, Object> user = new HashMap<String, Object>();
		HashMap<String, Object> activie = new HashMap<String, Object>();
		user.put("countFluxNumber", countFluxNumber + "M");
		user.put("countDay", countDay);
		user.put("fluxList", fluxList);
		if (activityInfo == null) {
			map.put("activityInfo", "活动已经结束！");
		} else {
			activie.put("activityCode", activityInfo.getActivecode());
			activie.put("activityName", activityInfo.getActivename());
			activie.put("activityDescribe", activityInfo.getActivedescribe());
		}
		map.put("User", user);
		map.put("Active", activie);
		if (row > 0L) {
			result.setCode(ActivitiesConstant.SIGN_IN_ACTIVITIES_ONE);
			result.setData(map);
			result.setMessage("今天已经签到过了，请明天再来吧！");
			return result;
		}
		result.setCode(1);
		result.setMessage("查询成功！");
		result.setData(map);
		return result;
	}

	@Override
	@Transactional
	public JsonMessage signInActivitiesJoin(HashMap<String, Object> params) {
		String userId = (String) params.get("userid");
		if (userId == null) {
			return new JsonMessage(0, "未输入userId");
		}
		ActivityResult<Map<String, Object>> result = new ActivityResult<Map<String, Object>>();
		long row = activitiesSignInMapper.queryCountByUserIdTime(userId, MyDate.getNowDate());
		Map<String, Object> map = new HashMap<String, Object>();
		int countDay = (int) activitiesSignInMapper.queryConutByUserId(userId);
		if (row > 0) {
			result.setCode(ActivitiesConstant.SIGN_IN_ACTIVITIES_ONE);
			result.setMessage("今天已经签到过了，请明天再来吧！");
			map.put("countDay", countDay);
			result.setData(map);
			return result;
		}
		ActivitiesBaseInfo activityInfo = activitiesBaseInfoMapper.queryOne(params);
		if (activityInfo == null) {
			result.setCode(ActivitiesConstant.SIGN_IN_ACTIVITIES_ONE);
			result.setMessage("活动已经结束！");
			map.put("countDay", countDay);
			result.setData(map);
			return result;
		}
		ActivitiesSignIn signIn = new ActivitiesSignIn();
		signIn.setCreateTime(new Date());
		signIn.setSignInTime(new Date());
		signIn.setUserId(userId);
		countDay++;
		if (countDay <= 5) {
			signIn.setFluxNumber(constantBeanConfig.fluxNumber);
		} else if (countDay > 5 && countDay <= 10) {
			signIn.setFluxNumber(2 * constantBeanConfig.fluxNumber);
		} else if (countDay > 10 && countDay <= 15) {
			signIn.setFluxNumber(4 * constantBeanConfig.fluxNumber);
		} else {
			signIn.setFluxNumber(0);
		}
		activitiesSignInMapper.insert(signIn);
		int newCountDay = (int) activitiesSignInMapper.queryConutByUserId(userId);

		List<Integer> fluxList = new ArrayList<Integer>();
		int countFluxNumber = 0;
		if (newCountDay > 0) {
			if (newCountDay >= 15) {
				countFluxNumber = constantBeanConfig.fluxNumber * 5 * (1 + 2 + 4);
			} else if (newCountDay > 10 && newCountDay < 15) {
				countFluxNumber = constantBeanConfig.fluxNumber * 5 * (1 + 2)
						+ (newCountDay - 10) * constantBeanConfig.fluxNumber * 4;
			} else if (newCountDay > 5 && newCountDay <= 10) {
				countFluxNumber = constantBeanConfig.fluxNumber * 5
						+ (newCountDay - 5) * constantBeanConfig.fluxNumber * 2;
			} else if (newCountDay > 0 && newCountDay <= 5) {
				countFluxNumber = constantBeanConfig.fluxNumber * (newCountDay - 0);
			}
			int index = newCountDay % 7 == 0 ? newCountDay - 7 : newCountDay - newCountDay % 7;
			index++;
			for (int i = 1; i <= 7; i++) {
				if (index <= 5) {
					fluxList.add(constantBeanConfig.fluxNumber);
				} else if (index > 5 && index <= 10) {
					fluxList.add(2 * constantBeanConfig.fluxNumber);
				} else if (index > 10 && index <= 15) {
					fluxList.add(4 * constantBeanConfig.fluxNumber);
				} else {
					fluxList.add(0);
				}
				index++;
			}
		}
		map.put("countFluxNumber", countFluxNumber + "M");
		map.put("countDay", newCountDay);
		map.put("fluxList", fluxList);
		map.put("flux", signIn.getFluxNumber() + "M");
		result.setMessage("签到成功！");
		result.setCode(1);
		result.setData(map);
		return result;
	}

	/**
	 * 获取首次注册 活动详细描述
	 */
	@Override
	public JsonMessage queryActivityForFL(HashMap<String, Object> params) {
		ActivitiesBaseInfo active = activitiesBaseInfoMapper.queryOne(params);
		HashMap<String, Object> output = new HashMap<>();
		if (active == null) {
			logger.info("-1,不在活动期间");
			return new ActivityResult<Object>(0, "暂无活动", null);
		} else {
			// 1.1、添加活动描述
			output.put("Active", new ActivityOtherObject<Object>(active));
			// 1.2、添加用户行为信息
			ActivityFLHDOutPut<?> activityFLHDOutPut = new ActivityFLHDOutPut();
			activityFLHDOutPut.setCode(0);
			UserActivitiesInfo userActivitiesInfo = userActivitiesInfoMapper.selectByPrimary(params);
			if (userActivitiesInfo == null) {
				activityFLHDOutPut = new ActivityFLHDOutPut(1, "输入推荐人手机号领取流量");
			} else {
				activityFLHDOutPut = new ActivityFLHDOutPut(userActivitiesInfo);
			}
			output.put("User", activityFLHDOutPut);
			return new ActivityResult<Object>(output);
		}
	}

	/**
	 * 参加拉新活动
	 */
	@Override
	public JsonMessage ActivityForFLJoin(HashMap<String, Object> params) {
		// TODO 赠送流量
		UserActivitiesInfo useractivety = userActivitiesInfoMapper.selectByPrimary(params);
		if (useractivety != null) {
			return new ActivityResult<Object>(2, "已经参加过该活动成功,已给" + useractivety.getCommendmobile() + "手机号赠送流量", null);
		}
		int stat = userActivitiesInfoMapper.insert(params);
		String mobile = (String) params.get("userMobile");
		return new ActivityResult<Object>(1, "参加活动成功,正在给" + mobile + "手机号赠送流量", null);
	}

}