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

	public iptv.ResultNotifyResponse resultNotify(iptv.ResultNotify resultNotify0) {
		
		int cmdResult = resultNotify0.getCmdResult();
		String correlateId=resultNotify0.getCorrelateID().toString();
		String url = resultNotify0.getResultFileURL().toString();
		logger.info("------------Injecter接收到的参数CSPID为:"+resultNotify0.getCSPID());
		logger.info("------------Injecter接收到的参数LSPID为:"+resultNotify0.getLSPID());
		logger.info("------------Injecter接收到的参数correlateId为:"+correlateId);
		logger.info("------------Injecter接收到的参数cmdResult为:"+cmdResult);
		logger.info("------------Injecter接收到的参数url为:"+url);
		
		ServerDealService sdService=new ServerDealService(); 
		//sdService.dealResponseResult(cmdResult, correlateId); //处理CDN返回状态【成功|失败】
		 
		//响应CDN
		CSPResult result = new CSPResult();
		int resultCodeResponse = result.getResult();
		logger.info("----------->>>Injecter返回状态码为:"+ resultCodeResponse);
        ResultNotifyResponse response = new ResultNotifyResponse(); 
        response.setResultNotifyReturn(result);
        return response;  
	}
	
	public static void main(String[] args) {
		ServerDealService sdService=new ServerDealService(); 
		sdService.dealResponseResult(0, "1002"); 
	}

}
