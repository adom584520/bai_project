package com.pbtd.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.pbtd.dao.seriesDetail.SeriesDetailMapper;
import com.pbtd.entity.SeriesDetail;
import com.pbtd.util.BeanFactory;
import com.pbtd.util.OpenApiUtil;

/**
 * 获取播放地址
 * @category 全量获取播放地址   根据programId获取播放地址(*)
 * @author jiaren
 *
 */
public class MovieUrlService {

	private final Logger logger=Logger.getLogger(MovieUrlService.class); 
	
	//private InjectionStateDealDao isdDao=new InjectionStateDealDao();
	private SeriesDetailMapper seriesDetailMapper=(SeriesDetailMapper)BeanFactory.getBean(SeriesDetailMapper.class);
	private OpenApiUtil oaUtil=OpenApiUtil.getInstance();
	
	private static final MovieUrlService muService=new MovieUrlService();
	private boolean isRunning=false;
	
	private MovieUrlService(){};
	public static MovieUrlService getInstance(){
		return muService; 
	}
	
	public void getMovieUrl() throws NumberFormatException, SQLException{
		String playUrl="";
		OpenApiUtil oaUtil=OpenApiUtil.getInstance();
		
		if(!isRunning){
			isRunning=true;
			while(seriesDetailMapper.getInjectWithoutUrlNum()>0){
				List<SeriesDetail> listData=this.seriesDetailMapper.getTop1000InjectWithoutUrlList();
				int length=listData.size();
				for(int i=0;i<length;i++){
					int id=listData.get(i).getId();
					String programId=listData.get(i).getProgramId();
					playUrl=oaUtil.getUrlFromOpenApi(programId);
					logger.info("id："+id);
					logger.info("programId："+programId);
					logger.info("playUrl："+playUrl);
					SeriesDetail seriesDetail=new SeriesDetail();
					seriesDetail.setId(id);
					if(playUrl==null || "".equals(playUrl)){
						
						seriesDetail.setIsInsert(-1);
						seriesDetail.setMovieUrl(null);
						this.seriesDetailMapper.updateStateForUrl(seriesDetail);
						
					}else{
						seriesDetail.setIsInsert(0);
						seriesDetail.setMovieUrl(playUrl);
						this.seriesDetailMapper.updateStateForUrl(seriesDetail);
					}
				}
			}
			isRunning=false;
		}
	}
	
	//根据programId获取playUrl
	public String getMovieUrlByProgramId(String programId) throws NumberFormatException, SQLException{
		String playUrl="";
		playUrl=oaUtil.getUrlFromOpenApi(programId);
		logger.info("playUrl:"+playUrl);
		SeriesDetail seriesDetail=new SeriesDetail();
		seriesDetail.setProgramId(programId);
		seriesDetail.setMovieUrl(playUrl);
		this.seriesDetailMapper.updateMovieUrlByProgramId(seriesDetail);
		return playUrl;
	}
	
	
	public boolean getRunState(){
		return isRunning;
	}
}
