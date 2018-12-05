package com.pbtd.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import iptv.CSPRequestServiceStub;
import iptv.CSPResult;
import iptv.ExecCmd;
import iptv.ExecCmdResponse;

public class UpclentDemo3 {

	/**
	 * 发送消息主题字符串。
	 */
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	/**
	 * 设置消息体的主题内容。
	 * 
	 * @param param
	 *            主题内容。
	 */
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}



	/**
	 * 服务地址。
	 */
	private String serviceAddress;

	/**
	 * 声明客户端对象。
	 */
	private CSPRequestServiceStub stub;



	/**
	 * 设置消息的服务地址，即你的消息要发到哪儿去。
	 * 
	 * @param serviceAddress
	 *            服务地址。
	 */
	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

	/**
	 * 发送消息的方法。
	 * 
	 * @return 发送成功的响应。
	 */
	public int sendAttach() {
		CSPResult result = null;
		ExecCmdResponse response = null;
		int result2 = 3 ;

		// 1、根据服务地址，创建一个发送消息的客户端。
		try {
			stub = new CSPRequestServiceStub(serviceAddress);
		} catch (AxisFault e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		org.apache.axis2.databinding.types.soapencoding.String s1 = new org.apache.axis2.databinding.types.soapencoding.String();
		org.apache.axis2.databinding.types.soapencoding.String s2 = new org.apache.axis2.databinding.types.soapencoding.String();
		org.apache.axis2.databinding.types.soapencoding.String s3 = new org.apache.axis2.databinding.types.soapencoding.String();
		org.apache.axis2.databinding.types.soapencoding.String s4 = new org.apache.axis2.databinding.types.soapencoding.String();
		
		s1.setString(param1);
		s2.setString(param2);
		s3.setString(param3);
		s4.setString(param4);
		
		// 2、创建一个发送消息的请求消息体。
		ExecCmd  execCmd = new ExecCmd();
		execCmd.setCSPID(s1);
		execCmd.setLSPID(s2);
		execCmd.setCmdFileURL(s3);
		execCmd.setCorrelateID(s4);

		// 3、根据请求消息体，发送消息并获取响应。
		try {
			response = stub.execCmd(execCmd);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 4、如果获取的响应不为空，获取响应的字符串内容。
		if (response != null) {
			result = response.getExecCmdReturn();
			result2 = result.getResult();
			org.apache.axis2.databinding.types.soapencoding.String errorDescription = result.getErrorDescription();
			if(errorDescription!=null){
				System.out.println("-1:"+errorDescription.toString());
			}
		}

		return result2;
	}
}
