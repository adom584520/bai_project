package com.pbtd.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class HttpClientUtil {
	
	private static final Logger logger = Logger.getLogger(HttpClientUtil.class);
    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param header  请求头  accessToken
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    @SuppressWarnings("deprecation")
	public static String httpPost(String url,JSONObject jsonParam,Map<String,Object> header, boolean noNeedResponse){
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,60000);//连接超时60s        
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,60000); //数据超时60s
        
        //HttpPost hpost=new HttpPost(url);
        //hpost.addHeader("Authorization","OAuth2 0F3E34B835616117337CEDCF75327C31");
        
        JSONObject jsonResult = null;
        String strResult = "";
        HttpPost httpPost = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
                if(header!=null && header.size()>0){
                	httpPost.addHeader("Authorization", header.get("authorization").toString()); //accessToken
                }
                httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
                //method.addHeader("Content-Length", "");
            }
            HttpResponse result = httpClient.execute(httpPost);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                try {
                    /**读取服务器返回过来的json字符串数据**/
                	strResult = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    /**把json字符串转换成json对象**/
                    //暂且注销
                    //jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
                     logger.error("post请求提交失败Exception:" + url, e);
                	//System.out.println("post请求提交失败:" + url);
                }
            }else{
            	logger.error("post请求提交失败statusCode"+result.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            logger.error("post请求提交失败IOException:" + url, e);
        	//System.out.println("post请求提交失败:" + url);
        }finally{
        	httpClient.close();
        }
        return strResult;
    }
 
 
    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static String httpGet(String url){
        //get请求返回结果
        JSONObject jsonResult = null;
        String strResult="";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,60000);//连接超时60s        
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,60000); //数据超时60s
            
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
 
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                //暂时注销
                //jsonResult = JSONObject.fromObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                 logger.error("get请求提交失败statusCode："+response.getStatusLine().getStatusCode()+" , "+ url);
            	//System.out.println("get请求提交失败:" + url);
            }
        } catch (IOException e) {
        	logger.error("get请求提交失败IOException:" + url, e);
        	//System.out.println("get请求提交失败:" + url);
        }finally{
        	httpClient.close();
        }
        return strResult;
    }
}
