package com.pbtd.manager.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pbtd.playclick.integrate.controller.ConstantBeanConfig;
import com.pbtd.playclick.util.ImgCompressUtil;
@Controller
public class ImportImg {
	
	public static Logger log = Logger.getLogger(ImportImg.class);
	public static final String IMAGE_FILES_KEY = "files";
	public static final String IMAGE_URL_KEY = "uri";
	@Autowired
	private ConstantBeanConfig constantBeanConfig;
	@Autowired
	private ImgCompressUtil imgcompressuril;
	
	 public   void  downLoadFromUrl(String urlStr,String fileName,String savePath,String type,String pictype,String code) throws IOException{  
	        URL url = new URL(urlStr);    
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	        HttpServletRequest request=null;
	                //设置超时间为3秒  
	        conn.setConnectTimeout(3*1000);  
	        //防止屏蔽程序抓取而返回403错误  
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	    	File file =null;
	        //得到输入流  
			InputStream inputStream=null;
			FileOutputStream fos=null;
			try {
				inputStream = conn.getInputStream();    
			 //获取自己数组  
				byte[] getData = readInputStream(inputStream);     
				//文件保存位置  
				File saveDir = new File(savePath);  
				if(!saveDir.exists()){  
				    saveDir.mkdir();  
				}  
				  file = new File(saveDir+File.separator+fileName);      
				fos = new FileOutputStream(file);       
				 fos.write(getData); 
				 String newfile=file.getPath();
				 if(pictype.equals("1")||pictype.equals("2")){
					 //调用压缩
					   newfile= upload(file.getPath(),pictype);
				 }
				 
				 FileInputStream input = new FileInputStream(newfile);
			 MultipartFile multipartFile =  new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
				String filenamenew= upurlimg(multipartFile,request);
				if(filenamenew==null || filenamenew==""){
					filenamenew=urlStr;
				}
				 /*Map<String, Object> params=new HashMap<>();
				 params.put("imgurl", urlStr);
				 params.put("newimgurl", filenamenew);
				 params.put("type", type);
				 params.put("pictype", pictype);
				if(type.equals("phonevideo")){
					params.put("dramacode",code);
					fileUploadService.updatealbumphonevideoimg(params);//更改小图图片地址
				}else{
					params.put("seriesCode",code);
					fileUploadService.updatealbumphone(params);//更改小图图片地址
				}
				fileUploadService.insertvodcpimg(params);//新增图片上传记录  
*/				input.close();
			} catch (Exception e) {
				//e.printStackTrace();
				log.warn("图片下载"+urlStr);
			}finally {
				 if(fos!=null){  
			            fos.close();    	
			        }  
			        if(inputStream!=null){  
			            inputStream.close();  
			        } 
					file.delete();
			} 
			return ;
	  
	    }  

	    /** 
	     * 从输入流中获取字节数组 
	     * @param inputStream 
	     * @return 
	     * @throws IOException 
	     */  
	    public    byte[] readInputStream(InputStream inputStream) throws IOException {    
	        byte[] buffer = new byte[1024];    
	        int len = 0;    
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
	        while((len = inputStream.read(buffer)) != -1) {    
	            bos.write(buffer, 0, len);    
	        }    
	        bos.close();    
	        return bos.toByteArray();    
	    }   
	    public static String fileload(String url, File file) {
			String body = null;

			if (url == null || "".equals(url)) {
				return "参数不合法";
			}
			if (!file.exists()) {
				return "要上传的文件名不存在";
			}
			PostMethod postMethod = new PostMethod(url);

			try {
				// FilePart：用来上传文件的类,file即要上传的文件
				FilePart fp = new FilePart("file", file);
				Part[] parts = { fp };

				// 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
				MultipartRequestEntity mre = new MultipartRequestEntity(parts, postMethod.getParams());
				postMethod.setRequestEntity(mre);
				HttpClient client = new HttpClient();
				// 由于要上传的文件可能比较大 , 因此在此设置最大的连接超时时间
				client.getHttpConnectionManager().getParams().setConnectionTimeout(100000);
				int status = client.executeMethod(postMethod);
				if (status == HttpStatus.SC_OK) {
					InputStream inputStream = postMethod.getResponseBodyAsStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

					StringBuffer stringBuffer = new StringBuffer();
					String str = "";
					while ((str = br.readLine()) != null) {
						stringBuffer.append(str);
					}
					body = stringBuffer.toString();
					JSONObject parseObject = JSON.parseObject(body);
					boolean containsKey = parseObject.containsKey(IMAGE_FILES_KEY);
					if (containsKey) {
						String files = parseObject.getString(IMAGE_FILES_KEY);
						List<String> parseArray = JSON.parseArray(files, String.class);
						JSONObject parseObject2 = JSON.parseObject(parseArray.get(0));
						body = parseObject2.getString(IMAGE_URL_KEY);
					}
				} else {
					body = null;
				}
			} catch (Exception e) {
			} finally {
				// 释放连接
				postMethod.releaseConnection();
			}
			return body;
		}
		/**
		 * 图片上传
		 * @param uploadImageUrl
		 *            上传图片服务器url
		 * @param targetFile
		 *            上传文件
		 * @param uploadImagePath
		 *            上传文件临时目录
		 * @return
		 * @throws Exception
		 */
		public static String updateFileName(String uploadImageUrl, MultipartFile targetFile, String uploadImagePath)
				throws Exception {
			if (targetFile.isEmpty()) {
				throw new RuntimeException("文件内容不能为空！");
			}
			String originalFilename = targetFile.getOriginalFilename();
			String suffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
			String filename = UUID.randomUUID().toString() + suffix;
			File file = new File(uploadImagePath + filename);
			FileUtils.copyToFile(targetFile.getInputStream(), file);
			if (!file.exists()) {
				throw new RuntimeException("文件写入失败！");
			}
			String fileload = fileload(uploadImageUrl, file);
			if(fileload==null){
				throw new RuntimeException("文件上传失败！");
			}
			file.delete();
			return fileload;
		}
		
		/**
		 * 图片上传到服务器
		 * @param file
		 * @param request
		 * @return zr
		 */
		@RequestMapping("/image")
		@ResponseBody
		public String upurlimg(MultipartFile file,HttpServletRequest request) {
			String filename ="";
			if (!file.isEmpty()) {
				try {
					String filePath =  constantBeanConfig.uploadImageWebPath;
					String updateFileName = updateFileName(constantBeanConfig.uploadImageUrl,file,filePath);
					//filename = constantBeanConfig.uploadImageLookUrl + updateFileName;
					filename =  updateFileName;
				} catch (Exception e) {
					filename ="";
					e.printStackTrace();
				}
			}
			return filename;
		}
		 //调用图片压缩方法
	    private String upload(String url,String type){
	    	//1  树图 其他横图 type
	    	String img=imgcompressuril.upload(url,type);
	    	return img;
	    }
	
	    
	    //上传图片 返回图片地址
	    public   String  downLoadFromUrl(String urlStr,String fileName,String savePath,int iscompressuril,String pictype,String code) throws IOException{  
	        URL url = new URL(urlStr);    
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
	        HttpServletRequest request=null;
	                //设置超时间为3秒  
	        conn.setConnectTimeout(3*1000);  
	        //防止屏蔽程序抓取而返回403错误  
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	    	File file =null;
	        //得到输入流  
			InputStream inputStream=null;
			String filenamenew=null;
			FileOutputStream fos=null;
			try {
				inputStream = conn.getInputStream();    
			 //获取自己数组  
				byte[] getData = readInputStream(inputStream);     
				//文件保存位置  
				File saveDir = new File(savePath);  
				if(!saveDir.exists()){  
				    saveDir.mkdir();  
				}  
				   file = new File(saveDir+File.separator+fileName);      
				fos = new FileOutputStream(file);       
				 fos.write(getData); 
				 String newfile=file.getPath();
				 if(iscompressuril>0){
					 //调用压缩
					   newfile= upload(file.getPath(),pictype);
				 }
				 
				 FileInputStream input = new FileInputStream(newfile);
			 MultipartFile multipartFile =  new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
				  filenamenew= upurlimg(multipartFile,request);
				if(filenamenew==null || filenamenew==""){
					filenamenew=urlStr;
				}
					input.close();
					if(inputStream!=null){  
			            inputStream.close();  
			        } 
				   if(fos!=null){  
			            fos.close();    	
			        }  
			        
					file.delete();
			} catch (Exception e) {
				//e.printStackTrace();
				log.warn("图片下载"+urlStr);
				return  filenamenew;
			} 
		    return  filenamenew;
	  
	    }
}
