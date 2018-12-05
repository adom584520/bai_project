package com.pbtd.playlive.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playlive.domain.LiveChannel;
import com.pbtd.playlive.domain.LiveChannelSource;
import com.pbtd.playlive.domain.LiveChannelTvEpg;
import com.pbtd.playlive.domain.LiveCibnEpg;
import com.pbtd.playlive.domain.LiveCibnListEpg;
import com.pbtd.playlive.domain.LiveCibnNextEpg;
import com.pbtd.playlive.domain.LiveCibnPlayEpg;
import com.pbtd.playlive.domain.LiveProgram;
import com.pbtd.playlive.mapper.LiveChannelMapper;
import com.pbtd.playlive.mapper.LiveChannelSourceMapper;
import com.pbtd.playlive.mapper.LiveCibnEpgMapper;
import com.pbtd.playlive.mapper.LiveProgramMapper;
import com.pbtd.playlive.page.DataEpgResult;
import com.pbtd.playlive.page.DataResult;
import com.pbtd.playlive.page.PageResult;
import com.pbtd.playlive.service.ILiveChannelService;
import com.pbtd.playlive.util.DataFormat;
import com.pbtd.playlive.util.JsonServlet;

@Service
public class LiveChannelService implements ILiveChannelService{
	private static final Logger logger = LoggerFactory.getLogger(LiveChannelService.class); 

	@Autowired
	private LiveChannelMapper liveChannelMapper;
	@Autowired
	private LiveChannelSourceMapper liveChannelSourceMapper;
	@Autowired
	private LiveProgramMapper liveProgramMapper;
	@Autowired
	private LiveCibnEpgMapper liveCibnEpgMapper;
	@Autowired
	private JsonServlet JsonServlet;

	/**
	 * 1
	 */
	@Override
	public LiveChannel selectLiveChannel(String code) {
		return liveChannelMapper.select(code);
	}

	/**
	 * 2查出所有频道信息（表格分页）
	 */
	@Override
	public PageResult<DataResult<List<LiveChannel>>> querylistallLiveChannel(Map<String,Object> params) {
		LiveChannel livechannel = new LiveChannel();
		Integer groupid = (Integer) params.get("groupid");
		if(groupid != null){
			livechannel.setGroupid((Integer) params.get("groupid"));
			List<LiveChannel> list = liveChannelMapper.selectByKey(livechannel);
			return new PageResult<DataResult<List<LiveChannel>>>(new DataResult<List<LiveChannel>>((int)list.size(), list));
		}else{
			List<LiveChannel> list = liveChannelMapper.selectByKey(livechannel);
			return new PageResult<DataResult<List<LiveChannel>>>(new DataResult<List<LiveChannel>>((int)list.size(), list));
		}
	}

	/**
	 * 3 获取节目正在播放和下一个播放的节目信息
	 */
	@Override
	public PageResult<DataResult<List<LiveCibnNextEpg>>> queryLiveChannelplay(Map<String, Object> params) {
		LiveChannel livechannel = new LiveChannel();
		livechannel.setGroupid((Integer) params.get("groupid"));
		List<LiveChannel> channelList = liveChannelMapper.selectByKey(livechannel);
		List<LiveCibnNextEpg> list = new ArrayList<>();
		for (LiveChannel liveChannel : channelList) {
			Integer video = liveChannel.getVideoid();
			LiveCibnNextEpg liveepg = JsonServlet.getCibnEpg(String.valueOf(video));
			if(liveepg == null ){
				//获取频道当前节目epg
				LiveCibnPlayEpg  liveCibnPlayEpg = getLiveChannelplayNowepg(liveChannel);
				long nowtime = System.currentTimeMillis();
				if(liveCibnPlayEpg != null){
					LiveCibnNextEpg livecibn = new LiveCibnNextEpg();
					livecibn.setVideoId(liveChannel.getVideoid());
					livecibn.setChnCode(liveChannel.getChncode());
					livecibn.setStartTime(liveCibnPlayEpg.getStartTime());
					livecibn.setEndTime(liveCibnPlayEpg.getEndTime());
					livecibn.setEpgName(liveCibnPlayEpg.getEpgName());
					//获取频道下一个节目单
					LiveProgram liveProgram = new LiveProgram();
					liveProgram.setStarttime(liveCibnPlayEpg.getStartTime()); 
					LiveProgram Program  = liveProgramMapper.selectByPrimaryKey(liveProgram);
					if(Program != null){
						livecibn.setNextStartTime(Program.getStarttime());
						livecibn.setNextEndTime(Program.getEndtime());
						livecibn.setNextEpgName(Program.getProgramname());
					}else{
						livecibn.setNextStartTime(0);
						livecibn.setNextEndTime(0);
						livecibn.setNextEpgName("暂无下一个节目");
					}
					list.add(livecibn);
				}else{
					LiveCibnNextEpg livecibn = new LiveCibnNextEpg();
					livecibn.setVideoId(liveChannel.getVideoid());
					livecibn.setChnCode(liveChannel.getChncode());
					livecibn.setStartTime(nowtime/1000-1000);
					livecibn.setEndTime(nowtime/1000+1000);
					livecibn.setEpgName("未获取到数据");
					livecibn.setNextStartTime(0);
					livecibn.setNextEndTime(0);
					livecibn.setNextEpgName("暂无下一个节目");
					list.add(livecibn);
				}
			}else{
				liveepg.setChnCode(liveChannel.getChncode());
				list.add(liveepg);
			}
		}
		return new PageResult<DataResult<List<LiveCibnNextEpg>>>(new DataResult<List<LiveCibnNextEpg>>((int)list.size(), list));
	}

	/**
	 * 4 获取指定频道的7天的节目信息(手机)
	 */
	@Override
	public PageResult<?> queryLiveChannelplayepg(Map<String, Object> params) {
		LiveChannel livechannel = (LiveChannel) params.get("liveChannel");
		//从liveCibnEpg 数据库查询
		List<DataEpgResult<List<LiveCibnListEpg>>> li = new ArrayList<>();
		long tom = DataFormat.gettimetDate(1);
		long tod = DataFormat.gettimetDate(0);
		long yes = DataFormat.gettimetDate(-1);
		long bef = DataFormat.gettimetDate(-2);
		long hea = DataFormat.gettimetDate(-3);
		long las = DataFormat.gettimetDate(-4);
		long fin = DataFormat.gettimetDate(-5);
		List<LiveCibnListEpg> tomlist = new ArrayList<>();
		List<LiveCibnListEpg> todlist = new ArrayList<>();
		List<LiveCibnListEpg> yeslist = new ArrayList<>();
		List<LiveCibnListEpg> beflist = new ArrayList<>();
		List<LiveCibnListEpg> Healist = new ArrayList<>();
		List<LiveCibnListEpg> laslist = new ArrayList<>();
		List<LiveCibnListEpg> finaist = new ArrayList<>();
		List<LiveCibnEpg> cibnlist = new ArrayList<>();
		List<LiveCibnListEpg> epglist = new ArrayList<>();
		//查询国广数据源
		HashMap<String,Object> tempBean = new HashMap<>();
		tempBean.put("starttime", DataFormat.gettimetDate(-5));
		tempBean.put("endtime", DataFormat.gettimetDate(2));
		tempBean.put("chncode", livechannel.getChncode());
		cibnlist = liveCibnEpgMapper.selectBytime(tempBean);
		if(cibnlist != null && cibnlist.size() != 0){
			for (LiveCibnEpg LiveCibnEpg : cibnlist) {
				LiveCibnListEpg liveCibnListEpg = new LiveCibnListEpg();
				liveCibnListEpg.setProepgId(LiveCibnEpg.getEpgid());
				liveCibnListEpg.setStartTime(LiveCibnEpg.getStarttime());
				liveCibnListEpg.setEndTime(LiveCibnEpg.getEndtime());
				liveCibnListEpg.setDuration(LiveCibnEpg.getEndtime()-LiveCibnEpg.getStarttime());
				liveCibnListEpg.setEpgName(LiveCibnEpg.getEpgname());
				liveCibnListEpg.setShowName(LiveCibnEpg.getShowname());
				liveCibnListEpg.setWeekDay(LiveCibnEpg.getWeekday());
				liveCibnListEpg.setStartDate(LiveCibnEpg.getStartdate());
				liveCibnListEpg.setChnCode(livechannel.getChncode());
				liveCibnListEpg.setPlayUrl(livechannel.getPlayurl());	
				epglist.add(liveCibnListEpg);
			}
		}else{
			//查询爬虫数据库
			List<LiveProgram> programList =  liveProgramMapper.selectProList(tempBean);
			if(programList != null && programList.size() != 0){
				for (LiveProgram liveProgram : programList) {
					LiveCibnListEpg liveCibnListEpg = new LiveCibnListEpg();
					liveCibnListEpg.setProepgId(liveProgram.getProgramid());
					liveCibnListEpg.setStartTime(liveProgram.getStarttime());
					liveCibnListEpg.setEndTime(liveProgram.getEndtime());
					liveCibnListEpg.setDuration(liveProgram.getEndtime()-liveProgram.getStarttime());
					liveCibnListEpg.setEpgName(liveProgram.getProgramname());
					liveCibnListEpg.setShowName(liveProgram.getProgramname());
					liveCibnListEpg.setWeekDay(liveProgram.getRealtime());
					liveCibnListEpg.setStartDate(liveProgram.getRealtime());
					liveCibnListEpg.setChnCode(livechannel.getChncode());
					liveCibnListEpg.setPlayUrl(livechannel.getPlayurl());	
					epglist.add(liveCibnListEpg);
				}
			}
		}
		for (LiveCibnListEpg liveCibnEpg : epglist) {
			long starttime = liveCibnEpg.getStartTime();
			if(starttime>tom){
				tomlist.add(liveCibnEpg);
			}else if(starttime>tod){
				if(starttime < (System.currentTimeMillis()/1000)){
					String oldplayUrl = liveCibnEpg.getPlayUrl().replace("live.m3u8","history.m3u8");
					liveCibnEpg.setPlayUrl(oldplayUrl);
				}
				todlist.add(liveCibnEpg);
			}else if(starttime>yes){
				String oldplayUrl = liveCibnEpg.getPlayUrl().replace("live.m3u8","history.m3u8");
				liveCibnEpg.setPlayUrl(oldplayUrl);
				yeslist.add(liveCibnEpg);
			}else if(starttime>bef){
				String oldplayUrl = liveCibnEpg.getPlayUrl().replace("live.m3u8","history.m3u8");
				liveCibnEpg.setPlayUrl(oldplayUrl);
				beflist.add(liveCibnEpg);
			}else if(starttime>hea){
				String oldplayUrl = liveCibnEpg.getPlayUrl().replace("live.m3u8","history.m3u8");
				liveCibnEpg.setPlayUrl(oldplayUrl);
				Healist.add(liveCibnEpg);
			}else if(starttime>las){
				String oldplayUrl = liveCibnEpg.getPlayUrl().replace("live.m3u8","history.m3u8");
				liveCibnEpg.setPlayUrl(oldplayUrl);
				laslist.add(liveCibnEpg);
			}else{
				String oldplayUrl = liveCibnEpg.getPlayUrl().replace("live.m3u8","history.m3u8");
				liveCibnEpg.setPlayUrl(oldplayUrl);
				finaist.add(liveCibnEpg);
			}
		}
		DataEpgResult<List<LiveCibnListEpg>>  tomres = new DataEpgResult<List<LiveCibnListEpg>>("明天",tomlist.size(),tomlist);
		DataEpgResult<List<LiveCibnListEpg>>  todres = new DataEpgResult<List<LiveCibnListEpg>>("今天",todlist.size(),todlist);
		DataEpgResult<List<LiveCibnListEpg>>  yesres = new DataEpgResult<List<LiveCibnListEpg>>("昨天",yeslist.size(),yeslist);
		DataEpgResult<List<LiveCibnListEpg>>  befres = new DataEpgResult<List<LiveCibnListEpg>>(DataFormat.longToString(bef),beflist.size(),beflist);
		DataEpgResult<List<LiveCibnListEpg>>  Heares = new DataEpgResult<List<LiveCibnListEpg>>(DataFormat.longToString(hea),Healist.size(),Healist);
		DataEpgResult<List<LiveCibnListEpg>>  lasres = new DataEpgResult<List<LiveCibnListEpg>>(DataFormat.longToString(las),laslist.size(),laslist);
		DataEpgResult<List<LiveCibnListEpg>>  finres = new DataEpgResult<List<LiveCibnListEpg>>(DataFormat.longToString(fin),finaist.size(),finaist);
		li.add(tomres);
		li.add(todres);
		li.add(yesres);
		li.add(befres);
		li.add(Heares);
		li.add(lasres);
		li.add(finres);
		return new PageResult<>(new DataResult<List<DataEpgResult<List<LiveCibnListEpg>>>>((int)li.size(), li));
	}

	/**
	 * 5 获取指定频道正在播放的节目信息
	 */
	@Override
	public PageResult<LiveCibnPlayEpg> queryLiveChannelplayNowepg(Map<String, Object> params) {
		LiveChannel livechannel = (LiveChannel) params.get("liveChannel");
		LiveCibnPlayEpg liveCibnPlayEpg = new LiveCibnPlayEpg();
		LiveCibnNextEpg liveCibnEpg = JsonServlet.getCibnEpg(String.valueOf(livechannel.getVideoid().toString()));
		if(liveCibnEpg != null){
			//国广接口或取的数据存redis
			liveCibnPlayEpg.setChnCode(livechannel.getChncode());
			liveCibnPlayEpg.setStartTime(liveCibnEpg.getStartTime());
			liveCibnPlayEpg.setEndTime(liveCibnEpg.getEndTime());
			liveCibnPlayEpg.setDuration(liveCibnEpg.getEndTime() - liveCibnEpg.getStartTime());
			liveCibnPlayEpg.setEpgName(liveCibnEpg.getEpgName());
			liveCibnPlayEpg.setTypeName(liveCibnEpg.getTypeName());
			liveCibnPlayEpg.setShowName(liveCibnEpg.getEpgName());
			liveCibnPlayEpg.setPlayUrl(livechannel.getPlayurl());
		}else{
			liveCibnPlayEpg = getLiveChannelplayNowepg(livechannel);
		}
		return new PageResult<LiveCibnPlayEpg>(liveCibnPlayEpg);
	}

	/**
	 * 获取频道当前节目epg
	 * */
	private LiveCibnPlayEpg getLiveChannelplayNowepg(LiveChannel livechannel){
		LiveCibnPlayEpg liveCibnPlayEpg = new LiveCibnPlayEpg();
		liveCibnPlayEpg.setChnCode(livechannel.getChncode());
		liveCibnPlayEpg.setPlayUrl(livechannel.getPlayurl());
		long nowtime = System.currentTimeMillis();
		LiveChannelSource  liveChannelSource = liveChannelSourceMapper.selectByPrimaryKey(livechannel.getOldchncode());
		if(liveChannelSource != null ){
			//银河爬虫获取：
			liveCibnPlayEpg.setStartTime(liveChannelSource.getStarttime()/1000);
			liveCibnPlayEpg.setEndTime(liveChannelSource.getEndtime()/1000);
			liveCibnPlayEpg.setDuration(liveChannelSource.getEndtime()/1000 - liveChannelSource.getStarttime()/1000);
			liveCibnPlayEpg.setEpgName(liveChannelSource.getTitle());
			liveCibnPlayEpg.setTypeName(liveChannelSource.getTag());
			liveCibnPlayEpg.setShowName(liveChannelSource.getTitle());
		}else{
			//国广提供的接口
			LiveCibnEpg liveCibnEpg = liveCibnEpgMapper.selectByPrimaryKey(new LiveCibnEpg(livechannel.getChncode(),null,nowtime/1000));
			if(liveCibnEpg != null){
				liveCibnPlayEpg.setStartTime(liveCibnEpg.getStarttime());
				liveCibnPlayEpg.setEndTime(liveCibnEpg.getEndtime());
				liveCibnPlayEpg.setDuration(liveCibnEpg.getEndtime() - liveCibnEpg.getStarttime());
				liveCibnPlayEpg.setEpgName(liveCibnEpg.getEpgname());
				liveCibnPlayEpg.setTypeName(null);
				liveCibnPlayEpg.setShowName(liveCibnEpg.getShowname());
			}else{
			//百度、峰哥接口提供的节目单爬去中获取
			LiveProgram liveprogram = new LiveProgram(livechannel.getChncode(),null,nowtime/1000);
			LiveProgram listProgram = liveProgramMapper.selectByPrimaryKey(liveprogram);
			if(listProgram !=null){
				liveCibnPlayEpg.setStartTime(listProgram.getStarttime());
				liveCibnPlayEpg.setEndTime(listProgram.getEndtime());
				liveCibnPlayEpg.setDuration(listProgram.getEndtime() - listProgram.getStarttime());
				liveCibnPlayEpg.setEpgName(listProgram.getProgramname());
				liveCibnPlayEpg.setTypeName(null);
				liveCibnPlayEpg.setShowName(listProgram.getProgramname());
			}else{
				
				liveCibnPlayEpg.setStartTime(nowtime/1000-1000);
				liveCibnPlayEpg.setEndTime(nowtime/1000+1000);
				liveCibnPlayEpg.setDuration(2000);
				liveCibnPlayEpg.setEpgName("暂无节目");
				liveCibnPlayEpg.setTypeName(null);
				liveCibnPlayEpg.setShowName("暂无节目");
			}
			}
		}
		return liveCibnPlayEpg;
	}


	/**
	 * 1tv  获取所有频道正在播放的节目信息
	 */
	@Override
	public PageResult<?> queryAllChannle(Map<String, Object> params) {
		List<LiveChannel> channellist = new ArrayList<>();
		List<LiveChannelTvEpg> list = new ArrayList<>();
		channellist = liveChannelMapper.selectByKey(null);
		for (LiveChannel liveChannel : channellist) {
			LiveChannelTvEpg liveChannelTvEpg = new LiveChannelTvEpg();
			liveChannelTvEpg.setChnCode(liveChannel.getChncode());
			liveChannelTvEpg.setChnName(liveChannel.getChnname());
			liveChannelTvEpg.setPlayUrl(liveChannel.getPlayurl());
			liveChannelTvEpg.setPlayUrl2(liveChannel.getPlayurl2());
			liveChannelTvEpg.setChnImage(liveChannel.getPackagecover());
			liveChannelTvEpg.setPlayOrder(liveChannel.getDefaultnum());
			LiveChannelSource  liveChannelSource = liveChannelSourceMapper.selectByPrimaryKey(liveChannel.getOldchncode());
			if(liveChannelSource != null){
				liveChannelTvEpg.setStartTime(liveChannelSource.getStarttime()/1000);
				liveChannelTvEpg.setEndTime(liveChannelSource.getEndtime()/1000);
				liveChannelTvEpg.setDuration(liveChannelSource.getEndtime()/1000 - liveChannelSource.getStarttime()/1000);
				liveChannelTvEpg.setTitle(liveChannelSource.getTitle());
				liveChannelTvEpg.setTag(liveChannelSource.getTag());
			}else{
				LiveCibnPlayEpg liveCibnPlayEpg = getLiveChannelplayNowepg(liveChannel);
				liveChannelTvEpg.setStartTime(liveCibnPlayEpg.getStartTime());
				liveChannelTvEpg.setEndTime(liveCibnPlayEpg.getEndTime());
				liveChannelTvEpg.setDuration(liveCibnPlayEpg.getEndTime() - liveCibnPlayEpg.getStartTime());
				liveChannelTvEpg.setTitle(liveCibnPlayEpg.getEpgName());
				liveChannelTvEpg.setTag("0");
			}
			list.add(liveChannelTvEpg);
		}
		return new PageResult<DataResult<List<LiveChannelTvEpg>>>(new DataResult<List<LiveChannelTvEpg>>((int)list.size(), list));
	}

	/**
	 * 2tv  获取当前频道正在播放的节目信息
	 */
	@Override
	public PageResult<?> queryOneChannle(Map<String, Object> params) {
		LiveChannel livechannel = (LiveChannel) params.get("liveChannel");
		LiveChannelTvEpg liveChannelTvEpg = new LiveChannelTvEpg();
		liveChannelTvEpg.setChnCode(livechannel.getChncode());
		liveChannelTvEpg.setChnName(livechannel.getChnname());
		liveChannelTvEpg.setPlayUrl(livechannel.getPlayurl());
		liveChannelTvEpg.setPlayUrl2(livechannel.getPlayurl2());
		liveChannelTvEpg.setChnImage(livechannel.getPackagecover());
		liveChannelTvEpg.setPlayOrder(livechannel.getDefaultnum());
		LiveChannelSource  liveChannelSource = liveChannelSourceMapper.selectByPrimaryKey(livechannel.getOldchncode());
		if(liveChannelSource != null){
			liveChannelTvEpg.setStartTime(liveChannelSource.getStarttime()/1000);
			liveChannelTvEpg.setEndTime(liveChannelSource.getEndtime()/1000);
			liveChannelTvEpg.setDuration(liveChannelSource.getEndtime()/1000 - liveChannelSource.getStarttime()/1000);
			liveChannelTvEpg.setTitle(liveChannelSource.getTitle());
			liveChannelTvEpg.setTag(liveChannelSource.getTag());
		}else{
			LiveCibnPlayEpg liveCibnPlayEpg = getLiveChannelplayNowepg(livechannel);
			liveChannelTvEpg.setStartTime(liveCibnPlayEpg.getStartTime());
			liveChannelTvEpg.setEndTime(liveCibnPlayEpg.getEndTime());
			liveChannelTvEpg.setDuration(liveCibnPlayEpg.getEndTime() - liveCibnPlayEpg.getStartTime());
			liveChannelTvEpg.setTitle(liveCibnPlayEpg.getEpgName());
			liveChannelTvEpg.setTag("0");
		}
		return new PageResult<DataResult<LiveChannelTvEpg>>(new DataResult<LiveChannelTvEpg>(1, liveChannelTvEpg));
	}

	/**
	 * 3  tv 获取指定频道的4天回看的节目信息(TV端)
	 */
	@Override
	public PageResult<?> queryChnPlayEpgInfo(Map<String, Object> params) {
		LiveChannel livechannel = (LiveChannel) params.get("liveChannel");
		//从liveCibnEpg 数据库查询
		List<DataEpgResult<List<LiveCibnListEpg>>> li = new ArrayList<>();
		long tod = DataFormat.gettimetDate(0);
		long yes = DataFormat.gettimetDate(-1);
		long bef = DataFormat.gettimetDate(-2);
		long hea = DataFormat.gettimetDate(-3);
		long las = DataFormat.gettimetDate(-4);
		List<LiveCibnListEpg> todlist = new ArrayList<>();
		List<LiveCibnListEpg> yeslist = new ArrayList<>();
		List<LiveCibnListEpg> beflist = new ArrayList<>();
		List<LiveCibnListEpg> Healist = new ArrayList<>();
		List<LiveCibnListEpg> laslist = new ArrayList<>();
		List<LiveCibnEpg> cibnlist = new ArrayList<>();
		List<LiveCibnListEpg> epglist = new ArrayList<>();
		HashMap<String,Object> tempBean = new HashMap<>();
		tempBean.put("starttime", DataFormat.gettimetDate(-4));
		tempBean.put("endtime", System.currentTimeMillis()/1000);
		tempBean.put("chncode", livechannel.getChncode());
		cibnlist = liveCibnEpgMapper.selectBytime(tempBean);
		if(cibnlist != null && cibnlist.size() != 0){
			for (LiveCibnEpg LiveCibnEpg : cibnlist) {
				LiveCibnListEpg liveCibnListEpg = new LiveCibnListEpg();
				liveCibnListEpg.setProepgId(LiveCibnEpg.getEpgid());
				liveCibnListEpg.setStartTime(LiveCibnEpg.getStarttime());
				liveCibnListEpg.setEndTime(LiveCibnEpg.getEndtime());
				liveCibnListEpg.setDuration(LiveCibnEpg.getEndtime()-LiveCibnEpg.getStarttime());
				liveCibnListEpg.setEpgName(LiveCibnEpg.getEpgname());
				liveCibnListEpg.setShowName(LiveCibnEpg.getShowname());
				liveCibnListEpg.setWeekDay(LiveCibnEpg.getWeekday());
				liveCibnListEpg.setStartDate(LiveCibnEpg.getStartdate());
				liveCibnListEpg.setChnCode(livechannel.getChncode());
				liveCibnListEpg.setPlayUrl(livechannel.getPlayurl());	
				epglist.add(liveCibnListEpg);
			}
		}else{
			//查询爬虫数据库
			List<LiveProgram> programList =  liveProgramMapper.selectProList(tempBean);
			if(programList != null && programList.size() != 0){
				for (LiveProgram liveProgram : programList) {
					LiveCibnListEpg liveCibnListEpg = new LiveCibnListEpg();
					liveCibnListEpg.setProepgId(liveProgram.getProgramid());
					liveCibnListEpg.setStartTime(liveProgram.getStarttime());
					liveCibnListEpg.setEndTime(liveProgram.getEndtime());
					liveCibnListEpg.setDuration(liveProgram.getEndtime()-liveProgram.getStarttime());
					liveCibnListEpg.setEpgName(liveProgram.getProgramname());
					liveCibnListEpg.setShowName(liveProgram.getProgramname());
					liveCibnListEpg.setWeekDay(liveProgram.getRealtime());
					liveCibnListEpg.setStartDate(liveProgram.getRealtime());
					liveCibnListEpg.setChnCode(livechannel.getChncode());
					liveCibnListEpg.setPlayUrl(livechannel.getPlayurl());	
					epglist.add(liveCibnListEpg);
				}
			}
		}
		for (LiveCibnListEpg liveCibnEpg : epglist) {
			long starttime = liveCibnEpg.getStartTime();
			if(starttime>tod){
				todlist.add(liveCibnEpg);
			}else if(starttime>yes){
				yeslist.add(liveCibnEpg);
			}else if(starttime>bef){
				beflist.add(liveCibnEpg);
			}else if(starttime>hea){
				Healist.add(liveCibnEpg);
			}else{
				laslist.add(liveCibnEpg);
			}
		}
		DataEpgResult<List<LiveCibnListEpg>>  todres = new DataEpgResult<List<LiveCibnListEpg>>("今天",todlist.size(),todlist);
		DataEpgResult<List<LiveCibnListEpg>>  yesres = new DataEpgResult<List<LiveCibnListEpg>>("昨天",yeslist.size(),yeslist);
		DataEpgResult<List<LiveCibnListEpg>>  befres = new DataEpgResult<List<LiveCibnListEpg>>(DataFormat.longToString(bef),beflist.size(),beflist);
		DataEpgResult<List<LiveCibnListEpg>>  Heares = new DataEpgResult<List<LiveCibnListEpg>>(DataFormat.longToString(hea),Healist.size(),Healist);
		DataEpgResult<List<LiveCibnListEpg>>  lasres = new DataEpgResult<List<LiveCibnListEpg>>(DataFormat.longToString(las),laslist.size(),laslist);
		li.add(todres);
		li.add(yesres);
		li.add(befres);
		li.add(Heares);
		li.add(lasres);
		return new PageResult<>(new DataResult<List<DataEpgResult<List<LiveCibnListEpg>>>>((int)li.size(), li));
	}

}
