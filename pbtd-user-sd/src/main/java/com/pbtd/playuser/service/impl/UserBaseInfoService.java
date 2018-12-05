package com.pbtd.playuser.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playuser.domain.UserBaseInfo;
import com.pbtd.playuser.domain.UserLoginInfo;
import com.pbtd.playuser.domain.UserPhoneHB;
import com.pbtd.playuser.mapper.UserBaseInfoMapper;
import com.pbtd.playuser.mapper.UserLoginInfoMapper;
import com.pbtd.playuser.mapper.UserPhoneHBMapper;
import com.pbtd.playuser.page.GetCodeResult;
import com.pbtd.playuser.page.LoginResult;
import com.pbtd.playuser.service.IRedisService;
import com.pbtd.playuser.service.IUserBaseInfoService;
import com.pbtd.playuser.util.JsonMessage;
import com.pbtd.playuser.util.sendPost.TestMain;


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

	/**
	 * 1.发送验证码
	 */
	@Override
	public JsonMessage getcode(final long mobile) {
		//检查手机号归属
		boolean stats = true;
//		if(13071825897L ==mobile || 18086502465L == mobile || 13843838438L == mobile || 18405814765L == mobile  || 18405814755L == mobile){
//			stats = true;
//		}else{
//			stats = selectphonefrom(mobile);
//		}
//		if(!stats){
//			return new JsonMessage(2,"手机号非河北移动用户");
//		}
		//产生随机的六位数 
		final String code = String.valueOf((int)((Math.random()*9+1)*100000));
//		try {//发送短信
//			new Thread() {
//				public void run() {
//					testMain.sendMassage(mobile,code);
//				}
//			}.start();
//		} catch (Exception e) {
//			logger.info("发送验证码出错");
//		}
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
	//	String rediscode = redisService.get(mobile);
		String userid = null;
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

}  