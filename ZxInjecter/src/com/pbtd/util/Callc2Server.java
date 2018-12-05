package com.pbtd.util;


import java.util.UUID;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.pbtd.client.UpclentDemo3;



public class Callc2Server extends HttpServlet {
	static final Logger logger = Logger.getLogger(Callc2Server.class);

	private static final long serialVersionUID = 1L;
	
	PropertiesUtil prop = new PropertiesUtil();

	/**
	 * 调用对方服务端入口方法。
	 * 
	 * @param id
	 * @param xmllocationdir
	 * @param correlateId
	 */
	public void requestZx(String id,String xmllocationdir,String correlateId) {
		

		int result=0;
		String param1 = prop.getValue("CSPID");
		String param2 = prop.getValue("LSPID");
		//String param3 = "ftp://pbtd:123456@120.221.38.157:21/data/pbtd/newxml/";//dianboyangli2.xml
		String param3 = prop.getValue("CmdFileURL");
		param3+=xmllocationdir.substring(10);//  xmllocationdir = /home/data/xml/20170804152134/==>/xml/20170804152134/
		param3+=id;
		param3+=".xml";
		//String param3 ="ftp://lzl:123456@47.93.10.26:21/xml/20170826170901/459352.xml";
	//	String param3 ="ftp://HBlzl:lzl123456@111.63.144.250:21/xml/20170826170901/405805.xml";
	//	String param4 = prop.getValue("CorrelateID");
	//	String param4= UUID.randomUUID().toString().replace("-", "");
		String param4=correlateId;
		// String serviceAddress =
		// "http://127.0.0.1:8080/services/demo3/iptv/CSPRequest/ExecCmdRequest";
		
//		String serviceAddress = "http://127.0.0.1:8080/Demo6/services/CSPRequestService";
		String serviceAddress = "http://111.63.144.37:35100/cms/services/ctms";
//		String serviceAddress = "http://111.63.144.37:35100/cms/services/CCTVctms";
	
		// 创建客户端类。
		UpclentDemo3 client = new UpclentDemo3();

		// 设置消息体内容。
		client.setParam1(param1);
		client.setParam2(param2);
		client.setParam3(param3);
		client.setParam4(param4);

		// 设置服务地址。
		client.setServiceAddress(serviceAddress);
		logger.info("开始调用对方服务器接口 ");
		logger.info("请求参数cid "+param1);
		logger.info("请求参数lid "+param2);
		logger.info("请求参数url "+param3);
		logger.info("请求参数corrid "+param4);
		// 发送消息获取，结果。
		try{
			result = client.sendAttach();
		}catch(Exception e){
			e.printStackTrace();
		}
		

		if (result == 0) {
			logger.info("调用对方服务端成功,响应状态码为:" + result);
		}

		// 打印结果。
		System.out.println(result);

	}
	
	
	/**
	 * 请求C2接口
	 * @param id
	 * @param xmlLocation 父目录位置
	 * @param corRelateId 
	 */
	public void requestC2(String id,String xmlLocation,String corRelateId) {
		
		int result=-1;
		String CSPID = PropertiesUtil.getValue("CSPID");
		String LSPID = PropertiesUtil.getValue("LSPID");
		String CmdFileURL = PropertiesUtil.getValue("CmdFileURL");
		CmdFileURL+=xmlLocation.substring(10);//  xmllocationdir = /home/data/xml/20170804152134/==>/xml/20170804152134/
		CmdFileURL+=id+".xml";
		
		String serviceAddress = PropertiesUtil.getValue("serviceAddress");
		// 创建客户端类。
		UpclentDemo3 client = new UpclentDemo3();

		// 设置消息体内容。
		client.setParam1(CSPID);
		client.setParam2(LSPID);
		client.setParam3(CmdFileURL);
		client.setParam4(corRelateId);

		// 设置服务地址。
		client.setServiceAddress(serviceAddress);
		logger.info("开始调用对方服务器接口 ");
		logger.info("请求参数CSPID "+CSPID);
		logger.info("请求参数LSPID "+LSPID);
		logger.info("请求参数CmdFileURL "+CmdFileURL);
		logger.info("请求参数corRelateId "+corRelateId);
		// 发送消息获取，结果。
		try{
			result = client.sendAttach();
			logger.info("调用对方服务端成功,响应状态码为:" + result);
			
		}catch(Exception e){
			logger.error("调用C2接口异常",e);
		}
		
	}
	
	
}
