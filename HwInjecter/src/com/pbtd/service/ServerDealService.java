package com.pbtd.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.pbtd.dao.seriesDetail.SeriesDetailMapper;
import com.pbtd.dao.version.VideoVersionMapper;
import com.pbtd.entity.SeriesDetail;
import com.pbtd.entity.VideoVersion;
import com.pbtd.util.BeanFactory;
import com.pbtd.util.HttpClientHelper;
import com.pbtd.util.PropertiesUtil;
import com.pbtd.util.RedisClient;
import net.sf.json.JSONObject;


/**
 * 作为服务端被对方C2请求  service
 * 请求播放地址  调用zr接口  发送调度地址  缓存redis
 * @author SJRen
 */
public class ServerDealService {

	private static final Logger logger=Logger.getLogger(ServerDealService.class);
	//private InjectionStateDealDao isdDao=new InjectionStateDealDao();
	private SeriesDetailMapper seriesDetailMapper=(SeriesDetailMapper)BeanFactory.getBean(SeriesDetailMapper.class);
	private VideoVersionMapper versionMapper=(VideoVersionMapper)BeanFactory.getBean(VideoVersionMapper.class);
	private MovieUrlService muService=MovieUrlService.getInstance();
	
	/**
	 * 处理cmd结果 
	 * 0：成功  获取地址   存入缓存  调用Zr接口
	 * 其他：失败  
	 */
	public void dealResponseResult(int cmdResult,String correlateId){
		
		logger.info("↓↓↓↓↓++++++++++处理返回值&播放地址++++++++++↓↓↓↓↓");
		if(cmdResult==0){
			//J:更新IsInsert为0  成功
			SeriesDetail seriesDetail=new SeriesDetail(); 
			seriesDetail.setIsInsert(0);
			seriesDetail.setProgramId(correlateId);
			this.seriesDetailMapper.updateStateForCdnReturn(seriesDetail);
			logger.info("okok：CDN返回[注入成功],已经标记字段：0");
			try{
				//J:获取地址 
				String playUrl=muService.getMovieUrlByProgramId(correlateId); //correlateId和programId生成xml时设置成了一样的
				//String playUrl="http://1234test/2.m3u8";
				SeriesDetail seriesDetailForProgramId=new SeriesDetail(); 
				seriesDetailForProgramId.setProgramId(correlateId);
				seriesDetailForProgramId=this.seriesDetailMapper.getInfoByProgramId(seriesDetailForProgramId);//前面programId和correlateId设置成了一样
				
				/*
				 * 处理华为坑爹接口返回注入成功，却获取不到地址
				 * J:发送zr接口  状态 地址
				 */
				if(playUrl.indexOf("http")!=-1){
					requestSubWebInjectInterface(seriesDetailForProgramId,1); //注入成功
					boolean flag=requestZrInterface(seriesDetailForProgramId,1);
					if(flag){
						setValueToRedis(playUrl,seriesDetailForProgramId);//保存到Redis中
					}
				}
				
			}catch(SQLException e){
				logger.error("报错getMovieUrlByProgramId：",e);
			}
		}else{
			//标记-1
			SeriesDetail seriesDetail=new SeriesDetail(); 
			seriesDetail.setIsInsert(-1);
			seriesDetail.setProgramId(correlateId);
			seriesDetail=this.seriesDetailMapper.getInfoByProgramId(seriesDetail);
			Integer injectTimes=seriesDetail.getInjectTimes()==null?0:seriesDetail.getInjectTimes();
			seriesDetail.setInjectTimes(injectTimes+1);
			this.seriesDetailMapper.updateStateForCdnReturn(seriesDetail);
			logger.info("nono：CDN返回[注入失败],已经标记字段-1");
			//通知subWeb
			requestSubWebInjectInterface(seriesDetail,-1); //注入失败
			logger.info("注入状态：已通知分平台");
		}
		
		logger.info("↑↑↑↑↑++++++++++处理返回值&播放地址++++++++++↑↑↑↑↑");
	}
	
	
	/**
	 * 调用Zr接口 发送注入成功地址
	 * @throws SQLException 
	 */
	public boolean requestZrInterface(SeriesDetail seriesDetail,int injectState) throws SQLException{
		/**
		 * http://vod.dispatcher.cibnhz.com/cibn_vod/${seriesId}/${movieId}/
		 */
		boolean flag=false; //调用zr接口是否成功
		Integer terminalType=seriesDetail.getTerminalType();
		String zrInterfaceUrl="";
		zrInterfaceUrl+=PropertiesUtil.getValue("subWebHost");
		//J:phone，tv接口
		if(terminalType!=null){
			if(terminalType==1){
				zrInterfaceUrl+=PropertiesUtil.getValue("subWebZrPhoneUrl");
			}else if(terminalType==2){
				zrInterfaceUrl+=PropertiesUtil.getValue("subWebZrTvUrl");
			}
		}
		String charset = "UTF-8";
		
		String defBitName="720P";
		String bitName=this.getVersionName(seriesDetail.getVersion());
		if(bitName!=null && !"".equals(bitName)){
			defBitName=bitName;
		}
		String dispatcherHostUrl=PropertiesUtil.getValue("dispatcherHostUrl");
		String dispatchUrl=dispatcherHostUrl+"/"+seriesDetail.getSeriesId()+"/"
						+seriesDetail.getMovieCode()+"/"+defBitName;
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("type", "hw");
		params.put("id", seriesDetail.getSubWebId()); //分平台id
		params.put("seriesCode", seriesDetail.getSeriesId()); //专辑Id
		params.put("drama", seriesDetail.getMovieCode()); //movieCode
		params.put("version",seriesDetail.getVersion() ); //码率
		params.put("dispatchUrl", dispatchUrl);//dispatchUrl
		
		logger.info("-------zr接口参数------↓↓↓");
		logger.info("zrInterfaceUrl:"+zrInterfaceUrl);
		logger.info("id:"+params.get("id"));
		logger.info("seriesCode:"+params.get("seriesCode"));
		logger.info("drama:"+params.get("drama"));
		logger.info("version:"+params.get("version"));
		logger.info("dispatchUrl:"+params.get("dispatchUrl"));
		logger.info("-------zr接口参数------↑↑↑");
		
		String respResult=HttpClientHelper.sendPost2(zrInterfaceUrl, params, charset);
		
		if (respResult != null) {
			// 这里要判断返回成功失败
			logger.info("zr接口返回值为:" + respResult);
			JSONObject jsonObject = JSONObject.fromObject(respResult);
			Object code = jsonObject.get("code");
			if(code!=null){
				if("1".equals(code.toString())){
					logger.info("调用zr接口成功，成功Id为："+seriesDetail.getId());
					flag=true;
					//更新字段
				}else{
					logger.info("调用zr接口失败，失败Id为："+seriesDetail.getId());
					//更新字段
					flag=false;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 注入状态更新接口  inject接口
	 * @param seriesDetail
	 */
	public void requestSubWebInjectInterface(SeriesDetail seriesDetail,int injectState){
		boolean flag=false;
		Map<String,Object> params=new HashMap<String,Object>();
		Integer terminalType=seriesDetail.getTerminalType();
		String subWebInjectUrl="";
		subWebInjectUrl+=PropertiesUtil.getValue("subWebHost");
		//J:phone，tv接口
		if(terminalType!=null){
			if(terminalType==1){
				subWebInjectUrl+=PropertiesUtil.getValue("subWebInjectPhoneUrl");
				
			}else if(terminalType==2){
				subWebInjectUrl+=PropertiesUtil.getValue("subWebInjectTvUrl");
			}
		}
		
		params.put("type", "hw");
		params.put("seriesCode", seriesDetail.getSeriesId()); //专辑Id
		params.put("drama", seriesDetail.getMovieCode()); //movieCode
		params.put("version", seriesDetail.getVersion()); //version
		params.put("injectState", injectState);  //手动传入接口参数
		String charset="utf-8";
		
		String respResult=HttpClientHelper.sendPost2(subWebInjectUrl, params, charset);
		if (respResult != null) {
			// 这里要判断返回成功失败
			logger.info("inject接口返回值为:" + respResult);
			JSONObject jsonObject = JSONObject.fromObject(respResult);
			Object code = jsonObject.get("code");
			if(code!=null){
				if("1".equals(code.toString())){
					logger.info("调用InjectState接口成功，成功Id为："+seriesDetail.getId());
					flag=true;
					//更新字段
				}else{
					logger.info("调用InjectState接口失败，失败Id为："+seriesDetail.getId());
					//更新字段
					flag=false;
				}
			}
		}
	}
	
	
	public void setValueToRedis(String playUrl,SeriesDetail seriesDetail){
		/*
		 * key=vod_ ${project}_ 1_${seriesId}_${movieId}_${bitsId}
		   value=中兴的playUrl
		   
		   key=vod_${project}_2_${seriesId}_${movieId}_${bitsId}
		   value=华为的播放鉴权url
		 */
		boolean flag=false;
		PropertiesUtil propertiesUtil =new PropertiesUtil();
		String redisKeyProject=propertiesUtil.getValue("redisKeyProject");  
		String defBitName="720P";
		String bitName=this.getVersionName(seriesDetail.getVersion());
		if(bitName!=null && !"".equals(bitName)){
			defBitName=bitName;
		}
		String key="vod_"+redisKeyProject+"_2_"+seriesDetail.getSeriesId()+"_"+seriesDetail.getMovieCode()+"_"+defBitName;
		flag=RedisClient.set(key, playUrl);
		if(flag){
			logger.info("已进行redis持久化保存，key："+key+" value:"+playUrl);
		}
	}
	
	/**
	 * 根据码率2,5  查询720P
	 * @param versionCode
	 * @return
	 */
	public String getVersionName(Integer versionCode){
		String versionName="";
		VideoVersion videoVersion=new VideoVersion();
		videoVersion.setCode(versionCode);
		videoVersion=this.versionMapper.findNameByCode(videoVersion);
		return videoVersion.getCodeName();
	}
	
	
	public static void main(String[] args) {
	
		ServerDealService serverDealService=new ServerDealService();
		//InjectionStateDealDao isdDao=new InjectionStateDealDao();
		List<SeriesDetail> list=null;
		/*
		try {
			list = isdDao.getInfoListForTest();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		*/
	
		/*for(int i=0;i<list.size();i++){
			SeriesDetail seriesDetail=list.get(i);
			String url="";
			boolean flag=false;
			try {
				flag=serverDealService.requestZrInterface(seriesDetail);
				System.out.println("flag:"+flag);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}*/
	}
	


}
