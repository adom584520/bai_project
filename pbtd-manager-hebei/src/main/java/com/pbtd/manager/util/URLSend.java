package com.pbtd.manager.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.HttpURLConnection;

/**
 * @author
 *
 */
public class URLSend {

	/*
	 * @SuppressWarnings("static-access") public static void main(String[] args)
	 * { try { //SP代码$用户手机号$业务代码$定购时间 Des3 des3 = new Des3(); byte[]
	 * key="120140227715929081188500".getBytes("UTF-8"); byte[] src =
	 * "10655911$13032834193$DG$2014-02-28 09:30:00".getBytes("UTF-8"); String
	 * encryptData = new BASE64Encoder().encode( des3.des3EncodeECB(key, src) );
	 * String time = java.net.URLEncoder.encode("2014-02-28 09:30:00", "UTF-8");
	 * 
	 * System.out.println("encryptData:   "+encryptData); System.out.println(
	 * "time:   "+time);
	 * 
	 * System.out.println("测试：");
	 * 
	 * System.out.println(
	 * sendGet("http://119.6.251.137:7002/subscription/serviceredir.aspx",
	 * "SpNumber=10655911&AccessTime="+time+"&EncodeStr="+encryptData)); } catch
	 * (Exception e) { e.printStackTrace(); } }
	 */

	public static String sendHttpClientGet(String url) {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		// 定义一个输入流
		InputStream ins = null;
		// 定义文件流
		BufferedReader br = null;
		String result = "";
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("方法失败: " + getMethod.getStatusLine());
			}
			// 使用getResponseBodyAsStream读取页面内容，
			// 这个方法对于目标地址中有大量数据需要传输是最佳的。
			ins = getMethod.getResponseBodyAsStream();
			String charset = getMethod.getResponseCharSet();
			if (charset.toUpperCase().equals("ISO-8859-1")) {
				charset = "gbk";
			}
			// 按服务器编码字符集构建文件流，这里的CHARSET要根据实际情况设置
			br = new BufferedReader(new InputStreamReader(ins, getMethod.getResponseCharSet()));
			StringBuffer sbf = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sbf.append(line);
			}
			result = new String(sbf.toString().getBytes(getMethod.getResponseCharSet()), charset);
			/*
			 * // 输出内容 System.out.println(result); // 服务器编码
			 * System.out.println("服务器编码是：" + getMethod.getResponseCharSet());
			 */

		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("请检查您所提供的HTTP地址！");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 关闭流，释放连接
			try {
				if (ins != null) {
					ins.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendGet(String url, String params) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url + "?" + params;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段

			/*
			 * Map<String, List<String>> map = conn.getHeaderFields(); //
			 * 遍历所有的响应头字段 for (String key : map.keySet()) {
			 * System.out.println(key + "--->" + map.get(key)); }
			 */

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendPost(String url, String params) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			// conn.setRequestProperty("enctype", "multipart/form-data");

			/*
			 * 模拟页面提交，servlet对没有指定content-type的request请求，封装时候作了一些处理，
			 * 导致无法在servlet中获取request.getInputStream()和 request.getReader()
			 */
			conn.setRequestProperty("content-type", "text/html");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendPost(String url, String params, String sessionId) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Cookie", sessionId);

			// conn.setRequestProperty("enctype", "multipart/form-data");

			/*
			 * 模拟页面提交，servlet对没有指定content-type的request请求，封装时候作了一些处理，
			 * 导致无法在servlet中获取request.getInputStream()和 request.getReader()
			 */
			// conn.setRequestProperty("content-type", "text/html");
			// conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;
			// MSIE 6.0; Windows NT 5.1; SV1)");

			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static String ishttp(String url) {
		StringBuilder json = new StringBuilder();
		BufferedReader in = null;
		try {
			URL urlObject = new URL(url);
			URLConnection uc = urlObject.openConnection();
			in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return json.toString();
	}

}