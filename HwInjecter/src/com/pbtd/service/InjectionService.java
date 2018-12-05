package com.pbtd.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.pbtd.dao.seriesDetail.SeriesDetailMapper;
import com.pbtd.entity.SeriesDetail;
import com.pbtd.util.BeanFactory;
import com.pbtd.util.Callc2Server;
import com.pbtd.util.FileUtil;
import com.pbtd.util.PropertiesUtil;
import com.pbtd.util.StateCode;
import com.pbtd.util.XmlUtil;

/**
 * 循环查询待注入数据  发送C2接口
 * @category 查询，发送，调用zr接口（注入中）
 * @author jiaren
 *
 */
public class InjectionService {
	
	private static final Logger logger=Logger.getLogger(InjectionService.class);
	private static SeriesDetailMapper sdMapper=(SeriesDetailMapper)BeanFactory.getBean(SeriesDetailMapper.class);
	private static Callc2Server c2Server=new Callc2Server();
	private static ServerDealService serverDealService=new ServerDealService();
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
	private static boolean isRunning=false;
	
	public static void beginInject(){
		if(!isRunning){
			isRunning=true;
			
			List<SeriesDetail> listDetail=null;
			SeriesDetail sd=null;
			while(sdMapper.getInjectionNum()>0){
				try{
					listDetail=sdMapper.getInjectionList();//20分钟100条
					
				}catch(Exception e){
					logger.error("查询待注入媒资异常：",e);
				}
				if(listDetail!=null && listDetail.size()>0){
					int len=listDetail.size();
					for(int i=0;i<len;i++){
						sd=listDetail.get(i);
						int id=sd.getId();
						String strId=String.valueOf(id);
						
						String xmlLocation=PropertiesUtil.getValue("xmlLocation");
						xmlLocation+=sdf.format(new Date())+File.separator;
						FileUtil.createDir(xmlLocation);
						String programId=UUID.randomUUID().toString().replace("-", "");
						String corRelateId=programId;			//置成一样 
						String bunchCode=UUID.randomUUID().toString().replace("-", "");
						XmlUtil.createXMLFileByEntity(sd, programId, bunchCode, xmlLocation);
						
						logger.info("--------第"+(i+1)+"条数据调用C2接口-------");
						logger.info("id:"+id);
						logger.info("programName:"+sd.getProgramName());
						logger.info("seriesId:"+sd.getSeriesId());
						logger.info("movieCode:"+sd.getMovieCode());
						logger.info("sourceUrl:"+sd.getSourceUrl());
						logger.info("programId:"+programId);
						logger.info("corRelateId:"+corRelateId);
						logger.info("childXmlLocation:"+xmlLocation+sd.getId()+".xml"); //  /home/data/xml/1343253343/34313.xml
						
						c2Server.requestC2(strId, xmlLocation, corRelateId);
						
						sd.setSendTime(new Date());
						sd.setIsInsert(StateCode.INJECT_SEND_REQUEST);
						sd.setBunchcode(bunchCode);
						sd.setProgramId(programId);
						sd.setXmlLocation(xmlLocation+sd.getId()+".xml");
						
						try{
							sdMapper.updateStateById(sd);
							serverDealService.requestSubWebInjectInterface(sd,2); //正在注入
							
						}catch(Exception e){
							logger.error("发送注入工单后，更新异常：",e);
						}
					}
				}
				
				String strThreadSleepTime=PropertiesUtil.getValue("threadSleepTime");
				int threadSleepTime=Integer.valueOf(strThreadSleepTime);  //毫秒
				int threadSleepMinute=threadSleepTime/(1000*60);
				try {
					logger.info("线程等待"+threadSleepMinute+"分钟");
					Thread.sleep(threadSleepTime);
					
				} catch (InterruptedException e) {
					logger.error("线程等待异常",e);
				}
			}
			
			isRunning=false;
		}
	}
	
	
	public static void main(String[] args) {
		beginInject();
	}

}
