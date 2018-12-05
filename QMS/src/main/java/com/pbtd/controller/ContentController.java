package com.pbtd.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.huawei.cimp.bean.ResultBean;
import com.pbtd.dto.Constant;
import com.pbtd.dto.ReturnResult;
import com.pbtd.util.StubClient;

@RequestMapping(value="/Content")
@Controller
public class ContentController {

	@Autowired
	private StubClient stubClient;
	
	@RequestMapping(value="/queryContent")
	@ResponseBody
	public ReturnResult QueryContent(HttpServletRequest request){
		ReturnResult returnResult=null;
		String strName=request.getParameter("contentName");
		System.out.println("before-contentName:"+strName);
		String contentName=null;
		try {
			//contentName = new String(strName.getBytes("iso-8859-1"),"utf-8");
			contentName = strName;
			System.out.println("after-contentName:"+contentName);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		String strType=request.getParameter("contentType");
		System.out.println("after-strType:"+strType);
		int contentType=-1; //0单片 1剧集
		if(contentName==null || "".equals(contentName)){
			returnResult=new ReturnResult();
			returnResult.setCode(Constant.LOCAL_QUERY_CODE_FAIL); // 0失败  1成功
			returnResult.setMessage("contentName为空");
			return returnResult;
		}
		if(strType==null || "".equals(strType)){
			returnResult=new ReturnResult();
			returnResult.setCode(Constant.LOCAL_QUERY_CODE_FAIL);
			returnResult.setMessage("contentType为空");
			return returnResult;
		}else{
			try{
				contentType=Integer.valueOf(strType);
				ResultBean resultBean=stubClient.queryContent(contentName, contentType);
				returnResult=new ReturnResult();
				returnResult.setCode(Constant.LOCAL_QUERY_CODE_SUCCESS);
				returnResult.setMessage("查询成功");
				returnResult.setData(resultBean);
				return returnResult;
				
			}catch(NumberFormatException e){
				returnResult=new ReturnResult();
				returnResult.setCode(Constant.LOCAL_QUERY_CODE_FAIL);
				returnResult.setMessage("contentType不是数字");
				return returnResult;
				
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
				returnResult=new ReturnResult();
				returnResult.setCode(Constant.LOCAL_QUERY_CODE_FAIL);
				returnResult.setMessage("请求CP方端口异常");
				return returnResult;
			} catch( RemoteException e2){
				e2.printStackTrace();
				returnResult=new ReturnResult();
				returnResult.setCode(Constant.LOCAL_QUERY_CODE_FAIL);
				returnResult.setMessage("请求CP方端口异常");
				return returnResult;
			}
		}
	}
}
