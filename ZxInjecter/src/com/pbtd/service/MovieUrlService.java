package com.pbtd.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.pbtd.dao.seriesDetail.SeriesDetailMapper;
import com.pbtd.entity.SeriesDetail;
import com.pbtd.util.BeanFactory;
import com.pbtd.util.OpenApiUtil;
import com.pbtd.util.PropertiesUtil;

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
	
	/**
	 * 从ftp地址解析id
	 * @param resultFileUrl
	 * @return
	 */
	public Integer getIdFromFtpUrl(String resultFileUrl){
		Integer id=null;
		int inx=resultFileUrl.lastIndexOf("rsp_");
		if(inx!=-1){
			String strId=resultFileUrl.substring(inx+4,resultFileUrl.length()-4);
			if(strId!=null && !"".equals(strId)){
				id=Integer.valueOf(strId);
			}
		}
		return id;
	}
	
	/**
	 * 根据Id获取playUrl
	 * 更新到DB
	 * @param id
	 * @return
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public String getMovieUrlById(int id) throws NumberFormatException, SQLException{
		String playUrl="";
		SeriesDetail sd=new SeriesDetail(); 
		sd.setId(id);
		sd=this.seriesDetailMapper.getInfoById(sd);
		String bunchCode=sd.getBunchcode();
		String zxCdnVideoUrlPrefix=PropertiesUtil.getValue("zxCdnVideoUrlPrefix");
		String zxCdnVideoUrlSuffix=PropertiesUtil.getValue("zxCdnVideoUrlSuffix");
		playUrl=zxCdnVideoUrlPrefix+bunchCode+zxCdnVideoUrlSuffix;
		
		logger.info("playUrl:"+playUrl);
		sd.setMovieUrl(playUrl);
		this.seriesDetailMapper.updateMovieUrlByProgramId(sd);
		return playUrl;
	}
	
	
	//根据programId获取playUrl
	public String getMovieUrlByProgramId(String programId) throws NumberFormatException, SQLException{
		String playUrl="";
		SeriesDetail sd=new SeriesDetail(); 
		sd.setProgramId(programId);
		sd=this.seriesDetailMapper.getInfoByProgramId(sd);
		String bunchCode=sd.getBunchcode();
		String zxCdnVideoUrlPrefix=PropertiesUtil.getValue("zxCdnVideoUrlPrefix");
		String zxCdnVideoUrlSuffix=PropertiesUtil.getValue("zxCdnVideoUrlSuffix");
		playUrl=zxCdnVideoUrlPrefix+bunchCode+zxCdnVideoUrlSuffix;
		
		logger.info("playUrl:"+playUrl);
		sd.setMovieUrl(playUrl);
		this.seriesDetailMapper.updateMovieUrlByProgramId(sd);
		return playUrl;
	}
	
	
	public boolean getRunState(){
		return isRunning;
	}
}
