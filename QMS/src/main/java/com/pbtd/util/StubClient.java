package com.pbtd.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.huawei.cimp.QueryContentServiceSoapBindingStub;
import com.huawei.cimp.bean.ResultBean;
import com.pbtd.dto.Constant;

@Service
public class StubClient {
	
	public ResultBean queryContent(String contentName,int contentType) throws MalformedURLException, RemoteException{
		
		int returnCode=-1;
		String returnMsg="";
		URL endpointURL=new URL("http://117.148.130.131:8582/cmsgw/services/QueryContentService");
		QueryContentServiceSoapBindingStub stub=new QueryContentServiceSoapBindingStub(endpointURL, null);
//		System.out.println("开始查询");
		ResultBean resultBean=stub.queryContent(contentName, contentType);
		if(resultBean!=null){
			returnCode=resultBean.getReturnCode();
			returnMsg=resultBean.getReturnMessage();
			System.out.println("returnCode："+returnCode+",returnMsg："+returnMsg);
		}
		return resultBean;
	}
	
	/**
	 * 对方更新，此方法暂时不用
	 * @param resultBean
	 * @return 是否存在字段
	 */
	public int getExistCode(ResultBean resultBean){
		int existCode=Constant.LOCAL_QUERY_EXISTCODE_OTHER;
		if(resultBean!=null){
			String returnMessage=resultBean.getReturnMessage();
			if(returnMessage!=null && !"".equals(returnMessage.trim())){
				int firstPostInx=returnMessage.indexOf(".");
				String firstMessage=returnMessage.substring(0, firstPostInx);
				if(firstMessage.length()>0){
					if(firstMessage.contains("not exist")){
						existCode=Constant.LOCAL_QUERY_EXISTCODE_NO;
					}else if(firstMessage.contains("exist")){
						existCode=Constant.LOCAL_QUERY_EXISTCODE_YES;
					}
				}
			}
		}
		return existCode;
	}
	
}
