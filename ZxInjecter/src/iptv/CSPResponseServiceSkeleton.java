/**
 * CSPResponseServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package iptv;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.pbtd.service.ServerDealService;


/**
 * CSPResponseServiceSkeleton java skeleton for the axisService
 */
public class CSPResponseServiceSkeleton implements
		CSPResponseServiceSkeletonInterface {
	static final Logger logger = Logger.getLogger(CSPResponseServiceSkeleton.class);
	static private int successtime = 0;
	//DealIngect  dealIngect = new DealIngect();
	
	/**
	 * Auto generated method signature
	 * @param resultNotify0
	 * @return resultNotifyResponse1
	 */

	public iptv.ResultNotifyResponse resultNotify(
			iptv.ResultNotify resultNotify0) {
		int cmdResult = resultNotify0.getCmdResult();
		String correlateId=resultNotify0.getCorrelateID().toString();
		String resultFileUrl = resultNotify0.getResultFileURL().toString();		
		
		logger.info("------------Injecter端获取到的参数为:"+resultNotify0.getCSPID());
		logger.info("------------Injecter端获取到的参数为:"+resultNotify0.getLSPID()); 
		logger.info("------------Injecter端获取到的参数为:"+correlateId);
		logger.info("------------Injecter端获取到的参数为:"+cmdResult);
		logger.info("------------Injecter端获取到的参数为:"+resultFileUrl);
		
		ServerDealService sdService=new ServerDealService(); 
		sdService.dealResponseResult(cmdResult, correlateId,resultFileUrl); //处理CDN返回状态【成功|失败】
		 
		CSPResult result = new CSPResult();
		int result2 = result.getResult();
		logger.info("----------->>>Injecter返回的响应状态码为:"+ result2);
        ResultNotifyResponse response = new ResultNotifyResponse();  
        response.setResultNotifyReturn(result);
        return response;  
	}
	
	public static void main(String[] args) {
		ServerDealService sdService=new ServerDealService(); 
		sdService.dealResponseResult(0, "2002","rsp_429.xml"); 
		//sdService.dealResponseResult(0, "2001","rsp_20.xml"); 
	}

}
