package com.pbtd.manager.live.util;


import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.live.mapper.LivePackageMapper;
import com.pbtd.manager.live.mapper.LiveProgramMapper;
import com.pbtd.manager.live.mapper.LiveVideoMapper;


/**
 * 从中心平台 自动获取数据的类
 * @author PBTD
 *
 */
@Service
public class DeleteAutoData{
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

	@Autowired
	private LivePackageMapper livePackageMapper;
	@Autowired
	private LiveVideoMapper liveVideoMapper;
	@Autowired
	private LiveProgramMapper liveProgramMapper;

	
	public void deleteAll() throws JSONException{
		int aa  = livePackageMapper.deleteTimeOut();
		logger.info("数据库package删除"+aa+"条数据" );		
		int bb  = liveVideoMapper.deleteTimeOut();
		logger.info("数据库video删除"+bb+"条数据" );		
		int cc  = liveProgramMapper.deleteTimeOut();
		logger.info("数据库program删除"+cc+"条数据" );		
	}


}
