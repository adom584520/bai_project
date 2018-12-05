package com.yh.push.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yh.mybatis.sqlmap.SqlMapper;
import com.yh.push.bean.PushUser;

public interface PushUserMapper extends SqlMapper {

	int deleteByPrimaryKey(@Param("src_token")String src_token,@Param("des_token")String des_token);
	
	int deletePhone(@Param("src_userID")String src_userID);
	
	int deleteTv(@Param("des_userID")String des_userID);

	int insert(PushUser record);

	List<PushUser> selectByPrimaryKey(@Param("src_token")String src_token,@Param("des_token")String des_token);
	
	List<PushUser> selectPhoneToken(@Param("src_userID")String src_userID);
	
	List<PushUser> selectTvToken(@Param("des_userID")String des_userID);
	
	List<PushUser> selectDel(@Param("des_token")String des_token);
	
	int deleteToken(@Param("des_token")String des_token);

	int updateByPrimaryKey(PushUser record);

	int updatePrimary(PushUser record);

	int UpdatePhoneToken(@Param("src_userID")String src_userID,@Param("src_token")String src_token,@Param("src_type")String src_type);
	
	int UpdateTvToken(@Param("des_userID")String des_userID,@Param("des_token")String des_token);
	
}