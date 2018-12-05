package com.pbtd.playlive.util.getDate;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pbtd.playlive.domain.LiveChannel;
import com.pbtd.playlive.domain.LiveGroup;
import com.pbtd.playlive.domain.LiveTag;
import com.pbtd.playlive.domain.LiveVersion;
import com.pbtd.playlive.mapper.LiveChannelMapper;
import com.pbtd.playlive.mapper.LiveGroupMapper;
import com.pbtd.playlive.mapper.LiveTagMapper;
import com.pbtd.playlive.mapper.LiveVersionMapper;


/**
 * 从中心平台 手动下发的数据的类
 * @author PBTD
 *
 */
@Service
public class GetManualData{
	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

	@Autowired
	private GethttpObjectUtil gethttpObjectUtil;	
	@Autowired
	private LiveGroupMapper liveGroupMapper;
	@Autowired
	private LiveTagMapper liveTagMapper;	
	@Autowired
	private LiveVersionMapper liveVersionMapper;	
	@Autowired
	private LiveChannelMapper liveChannelMapper;
	
	/**
	 * 手机分组信息
	 * @throws JSONException
	 */
	public void getGroup() throws JSONException{
		String jsonString = gethttpObjectUtil.gethttpObject("/groupinterface/");
		if (!jsonString.equals("") && jsonString != null) {
			JSONObject json =new  JSONObject(jsonString);
			String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
			logger.info("响应code值:"+code);
			if (code.equals("1")) {
				JSONArray jsonObjlist = json.getJSONArray("data");
				for (int i = 0; i < jsonObjlist.length(); i++) {
					JSONObject job = jsonObjlist.getJSONObject(i);
					LiveGroup liveGroup = JSON.parseObject(job.toString(), LiveGroup.class);
					if(liveGroup != null){
						List<LiveGroup>  list = liveGroupMapper.selectByPrimaryKey(liveGroup);
						if(list.size() != 0 ){
							logger.info("已存在该手机分组数据:"+liveGroup.getGroupname());
						}else{
							liveGroupMapper.insert(liveGroup);
						}
					}
				}
			}
		}else{
			logger.info("响应错误");
		}
	}


	/**
	 *tv分组信息
	 * @throws JSONException
	 */
	public void getTag() throws JSONException{
		String jsonString = gethttpObjectUtil.gethttpObject("/taginterface/");
		if (!jsonString.equals("") && jsonString != null) {
			JSONObject json =new  JSONObject(jsonString);
			String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
			logger.info("响应code值:"+code);
			if (code.equals("1")) {
				JSONArray jsonObjlist = json.getJSONArray("data");
				for (int i = 0; i < jsonObjlist.length(); i++) {
					JSONObject job = jsonObjlist.getJSONObject(i);
					LiveTag liveTag = JSON.parseObject(job.toString(), LiveTag.class);
					if(liveTag != null){
						List<LiveTag>  list = liveTagMapper.selectByPrimaryKey(liveTag);
						if(list.size() != 0 ){
							logger.info("已存在该tv分组数据:"+liveTag.getTagname());
						}else{
							liveTagMapper.insert(liveTag);
						}
					}
				}
			}
		}else {
			logger.info("响应错误");
		}
	
	}

	/**
	 * 项目版本信息
	 * @throws JSONException
	 */
	public void getVersion() throws JSONException{
		String jsonString = gethttpObjectUtil.gethttpObject("/versioninterface/");
		if (!jsonString.equals("") && jsonString != null) {
			JSONObject json =new  JSONObject(jsonString);
			String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
			logger.info("响应code值:"+code);
			if (code.equals("1")) {
				JSONObject jsonObjlist = json.getJSONObject("data");
					LiveVersion liveVersion = JSON.parseObject(jsonObjlist.toString(), LiveVersion.class);
					if(liveVersion != null){
						liveVersionMapper.truncateTable();
						liveVersionMapper.insert(liveVersion);
				}
			}
		}else {
			logger.info("响应错误");
		}
	}

	public void getChannel() throws JSONException{
		String jsonString = gethttpObjectUtil.gethttpObject("/channelinterface/");
		if (!jsonString.equals("") && jsonString != null) {
			JSONObject json =new  JSONObject(jsonString);
			String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
			logger.info("响应code值:"+code);
			if (code.equals("1")) {
				JSONArray jsonObjlist = json.getJSONArray("data");
				liveChannelMapper.truncateTable();
				for (int i = 0; i < jsonObjlist.length(); i++) {
					JSONObject job = jsonObjlist.getJSONObject(i);
					LiveChannel channel = JSON.parseObject(job.toString(), LiveChannel.class);
					if(channel != null){
							if(channel.getEpgstatus() != 0){//下线
								liveChannelMapper.delete(channel.getChncode());
							}else{
								liveChannelMapper.insert(channel);
							}
					}
				}
			} else {
				logger.info("响应错误");
			}
		}
	}


}
