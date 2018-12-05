package com.pbtd.manager.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pbtd.manager.conf.ConstantBeanConfig;

public class FileUpload {
	public static final String IMAGE_FILES_KEY = "files";
	public static final String IMAGE_URL_KEY = "uri";
	
	@Autowired
	private ConstantBeanConfig constantBeanConfig;
	
	private FileUpload() {

	};

	private static Logger logger = Logger.getLogger(FileUpload.class);

	public static String fileload(String url, File file) {
		String body = null;

		if (url == null || url.equals("")) {
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
			logger.warn("上传文件异常", e);
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return body;
	}

	/**
	 * 图片上传
	 * 
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

	public static String updateTemp2Excel(String filePath, String suffix) {
		String newFilePath = filePath.substring(0, filePath.lastIndexOf("."));
		String newFileName = newFilePath + suffix;
		String fileExcelName = null;
		try {
			File file = new File(filePath);
			if (!file.exists() || file.isDirectory()) {
				throw new RuntimeException("文件路径错误！");
			}
			File fileExcel = new File(newFileName);
			boolean renameTo = file.renameTo(fileExcel);
			fileExcel.setWritable(true, false);
			if (!renameTo) {
				throw new RuntimeException("文件改名失败！");
			}
			fileExcelName = fileExcel.getAbsolutePath();
		} catch (RuntimeException r) {
			throw new RuntimeException(r.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileExcelName;
	}


	@RequestMapping("/image")
	@ResponseBody
	public ResultBean<Object> login(MultipartFile file,HttpServletRequest request) {
		ResultBean<Object> json = new ResultBean<>();
		if (!file.isEmpty()) {
			try {
				String filePath = request.getSession().getServletContext().getRealPath(constantBeanConfig.uploadImageWebPath);
				String updateFileName = FileUpload.updateFileName(constantBeanConfig.uploadImageUrl,file,filePath);
				//String filename = constantBeanConfig.uploadImageLookUrl + updateFileName;
				String filename =   updateFileName;
				json.setMessage(filename);
			} catch (Exception e) {
				json.setSuccess(false);
				json.setMessage("上传失败！");
				e.printStackTrace();
			}
		}
		return json;
	}
}
