package com.pbtd.manager.system.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pbtd.manager.conf.ConstantBeanConfig;
import com.pbtd.manager.util.FileUpload;
import com.pbtd.manager.util.ResultBean;

/**
 * 文件上传操作
 * 
 * @author JOJO
 *
 */
@Controller
@RequestMapping("/fileupload")
public class BaseFileUploadController {
	private static final Logger logger = LoggerFactory.getLogger(BaseFileUploadController.class);
	@Autowired
	private ConstantBeanConfig constantBeanConfig;

	@RequestMapping("/image")
	@ResponseBody
	public ResultBean<Object> login(MultipartFile file,HttpServletRequest request) {
		ResultBean<Object> json = new ResultBean<>();
		if (!file.isEmpty()) {
			try {
				String filePath = request.getSession().getServletContext().getRealPath(constantBeanConfig.uploadImageWebPath);
				String updateFileName = FileUpload.updateFileName(constantBeanConfig.uploadImageUrl,file,filePath);
				//String filename = constantBeanConfig.uploadImageLookUrl + updateFileName;
				String filename =  updateFileName;
				json.setMessage(filename);
			} catch (Exception e) {
				json.setSuccess(false);
				json.setMessage("上传失败，请联系管理员！");
				logger.error(e.getMessage(),e);
			}
		}
		return json;
	}
}