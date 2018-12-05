package com.pbtd.playuser.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.playuser.domain.ActivitiesSignIn;

public interface ActivitiesSignInMapper {
	/**
	 * 查询是否有签到记录
	 * @param userId
	 * @param time
	 * @return
	 */
	long queryCountByUserIdTime(@Param("userId")String userId,@Param("time")String time);

	/**
	 * 添加用户签到记录
	 * @param signIn
	 */
	void insert(ActivitiesSignIn signIn);

	/**
	 * 查询用户当月的签到的所有记录
	 * @return
	 */
	long queryConutByUserId(String userId);

	/**
	 * 查询用户当月的签到的所有记录
	 * @param userId
	 * @return
	 */
	List<ActivitiesSignIn> queryListByUserId(String userId);
}