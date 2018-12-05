package com.pbtd.playclick.integrate.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.pbtd.playclick.integrate.service.face.IFileUploadService;


@Controller("vod.FileUpload")
@RequestMapping("/vod/FileUpload")
public class FileUpload {
	public static Logger log = Logger.getLogger(FileUpload.class);

	@Autowired
	private IFileUploadService fileUploadService;
	//爬取入库后的数据图片
	@RequestMapping("/imgurl")
	public   String imgurl() {
		Map<String, Object> params=new HashMap<>();
		fileUploadService.importvod_albumid(params);
		return null;
	}  
	//爬取入库后的图片 根据id爬取 
	@RequestMapping("/imguid")
	public   String imguid(String id) {
		Map<String, Object> params=new HashMap<>();
		params.put("id", id);
		fileUploadService.importvod_albumid(params);
		return "";
	} 
	//爬取剧集图片
	@RequestMapping("/videoimg")
	public void  videoimg(){
		 String path=System.getProperty("user.dir")+"/images" ;
		 Map<String, Object> params=new HashMap<>();
		fileUploadService.importalbumvideo(params,path);
	}

	/**
	 * 爬取自动更新的图片 国广银河 合一图片
	 */
	@RequestMapping("/imgalbuminfo_strategy")
	public   String imgalbuminfo_strategy() {
		Map<String, Object> params=new HashMap<>();
		fileUploadService.importvod_album_strategy(params);
		return "";

	}
	/**
	 * 爬取自动更新的图片优酷一图片
	 */
	@RequestMapping("/importyouku_album_strategy")
	public   String importyouku_album_strategy() {
		Map<String, Object> params=new HashMap<>();
		fileUploadService.importyouku_album_strategy(params);
		return "";

	}


}
