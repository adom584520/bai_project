package com.pbtd.manager.live.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.domain.LivePackage;
import com.pbtd.manager.live.domain.LiveVideo;
import com.pbtd.manager.live.domain.getEpg.LivePackageEpg;
import com.pbtd.manager.live.domain.getEpg.LiveVideoEpg;
import com.pbtd.manager.live.getEpg.LivePackageEpgMapper;
import com.pbtd.manager.live.getEpg.LiveVideoEpgMapper;
import com.pbtd.manager.live.mapper.LiveChannelMapper;
import com.pbtd.manager.live.mapper.LivePackageMapper;
import com.pbtd.manager.live.mapper.LiveVideoMapper;


@Service
public class GetPackageVideoListService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@Autowired
	private LivePackageEpgMapper livePackageEpgMapper;
	@Autowired
	private LivePackageMapper livePackageMapper;
	@Autowired
	private LiveChannelMapper liveChannelMapper;
	@Autowired
	private LiveVideoEpgMapper liveVideoEpgMapper;
	@Autowired
	private LiveVideoMapper liveVideoMapper;

//	public void selectPackage() throws ParseException{
//		//获取 live_epg 数据库 live_package 更新的数据	
//		List<LivePackageEpg> list =  new ArrayList<>();
//		list = 	livePackageEpgMapper.select();
//		if(list != null && list.size() > 0){
//			for (LivePackageEpg livePackageEpg : list) {
//				LivePackage livePackage = new LivePackage();
//				livePackage.setPackageid(livePackageEpg.getId());
//				livePackage.setPackagename(livePackageEpg.getPackageName());
//				livePackage.setStarttime(DataFormat.LongToDare(livePackageEpg.getStartTime()/1000));
//				livePackage.setEndtime(DataFormat.LongToDare(livePackageEpg.getEndTime()/1000));
//				livePackage.setChncode(livePackageEpg.getChnCode());
//				livePackage.setPackageorder(livePackageEpg.getPackageOrder());
//				livePackage.setPackageposter(livePackageEpg.getPackagePoster());
//				livePackage.setPackagestats(livePackageEpg.getPackageStatus());
//				livePackage.setPackagecode(livePackageEpg.getPackageCode());
//				livePackage.setPackagecover(livePackageEpg.getPackageCover());
//				livePackage.setTagid(livePackageEpg.getTagId());
//				List<LivePackage> aa = livePackageMapper.selectByPrimaryKey(livePackage);
//				if(aa != null && aa.size() != 0){
//					logger.info("数据库package已有数据");					
//				}else{
//					try {
//						LiveChannel liveChannel = new LiveChannel();
//						liveChannel.setOldchncode(livePackageEpg.getChnCode());
//						List<LiveChannel> channlelist = liveChannelMapper.selectByPrimaryKey(liveChannel);
//						livePackage.setChncode(channlelist.get(0).getChncode());
//						//Integer maxCount = livePackageMapper.count(null);
//						//livePackage.setPackageorder(maxCount++);
//						livePackageMapper.insert(livePackage);
//					} catch (Exception e) {
//						logger.info("数据库package插入失败");					
//						logger.info(e.getMessage());					
//					}
//				}
//			}
//		}
//	}

//	public void selectVideo() throws ParseException{
//		//获取 live_epg 数据库更新的数据
//		List<LiveVideoEpg> list = liveVideoEpgMapper.select();
//		if(list != null && list.size() > 0){
//			for (LiveVideoEpg liveVideoEpg : list) {
//				LiveVideo liveVideo = new LiveVideo();
//				liveVideo.setVideoid(liveVideoEpg.getId());
//				liveVideo.setTitle(liveVideoEpg.getTitle());
//				liveVideo.setChncode(liveVideoEpg.getChnCode());
//				liveVideo.setStarttime(DataFormat.LongToDare(liveVideoEpg.getStartTime()/1000));
//				liveVideo.setEndtime(DataFormat.LongToDare(liveVideoEpg.getEndTime()/1000));
//				liveVideo.setPackagecode(liveVideoEpg.getPackageCode());
//				List<LiveVideo> aa = liveVideoMapper.selectByPrimaryKey(liveVideo);
//				if(aa != null  && aa.size() != 0 ){
//					logger.info("数据库video已有数据");					
//				}else{
//					try {
//						LiveChannel liveChannel = new LiveChannel();
//						liveChannel.setOldchncode(liveVideoEpg.getChnCode());
//						List<LiveChannel> channlelist = liveChannelMapper.selectByPrimaryKey(liveChannel);
//						liveVideo.setChncode(channlelist.get(0).getChncode());
//						liveVideoMapper.insert(liveVideo);
//					} catch (Exception e) {
//						logger.info("数据库video插入失败");			
//						logger.info(e.getMessage());
//					}
//				}
//			}
//		}
//	}

}

