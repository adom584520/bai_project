package com.pbtd.manager.vod.page.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.pbtd.manager.vod.page.service.face.IJsonInterfaceService;

/**
 *中心运营平台接口
 * @author zr
 *
 */
@Configuration
@PropertySource(value = {"classpath:config/vodinterface.properties" })
public class VodcentralController {
	public static Logger log = Logger.getLogger(VodcentralController.class);
	@Autowired
	private IJsonInterfaceService jsonInterfaceService;

	//手机端 专辑路径
	@Value("${phone_album}")
	public String  phone_album;
	//手机端剧集路径
	@Value("${phone_albumvideo}")
	public  String phone_albumvideo;
	//手机端关联推荐路径
	@Value("${phone_albumrecommend}")
	public  String phone_albumrecommend;
	//手机端 频道路径
	@Value("${phone_channel}")
	public  String phone_channel;
	//手机端 频道关联专辑路径
	@Value("${phone_channelalbum}")
	public String phone_channelalbum;
	//手机端标签路径
	@Value("${phone_label}")
	public  String phone_label;
	//手机端标签绑定频道路径
	@Value("${phone_labelchannel}")
	public  String phone_labelchannel;
	//手机端标签分类路径
	@Value("${phone_labeltype}")
	public  String phone_labeltype;
	//热搜
	@Value("${hotsearch}")
	public  String hotsearch;
	//角标
	@Value("${corner}")
	public  String corner;
	//付费包
	@Value("${collectfeesbag}")
	public  String collectfeesbag;
	//手机端获取下发专题接口 入参curtime 日期， limit页数
	@Value("${phone_special}")
	public  String phone_special;
	//手机端获取下发专题绑定专辑接口  无参
	@Value("${phone_specialvideo}")
	public  String phone_specialvideo;
	//演员路径
	@Value("${actors}")
	public String actors;
	//下发频道内推荐轮播图接口
	@Value("${phone_recommandpic}")
	public String recommandpic;
	//#手机端获取下发专区推荐轮播图接口
	@Value("${phone_slideshow}")
	public String slideshow;
	//#手机端获取下发开机轮播图接口  
	@Value("${phone_startslideshow}")
	public String startslideshow;
	//#手机端获取下发热播推荐接口
	@Value("${phone_hotseries}")
	public String hotseries;
	//#手机端获取下发文字推荐接口
	@Value("${phone_textrecommendation}")
	public String textrecommendation;
	//#手机端标签频道路径
	@Value("${phone_labelchannel}")
    public String labelchannel;
	//#手机端标签分类路径
	@Value("${phone_labeltype}")
	public String labeltype;
	//#手机端标签分类路径
	@Value("${phone_hotseriesalbum}")
	public String hotseriesalbum;
	//#手机端cp源路径
	@Value("${phone_cpsourcetype}")
	public String phonecpsourcetype;
	




	//tv端 专辑路径
	@Value("${tv_album}")
	public String  tv_album;
	//tv端剧集路径
	@Value("${tv_albumvideo}")
	public  String tv_albumvideo;
	//tv端关联推荐路径
	@Value("${tv_albumrecommend}")
	public  String tv_albumrecommend;
	//tv端 频道路径
	@Value("${tv_channel}")
	public  String tv_channel;
	//tv端 频道关联专辑路径
	@Value("${tv_channelalbum}")
	public String tv_channelalbum;
	//tv端标签路径
	@Value("${tv_label}")
	public  String tv_label;
	//tv端获取下发专题接口 入参curtime 日期， limit页数
	@Value("${tv_special}")
	public  String tv_special;
	//tv端获取下发专题绑定专辑接口  无参
	@Value("${tv_specialvideo}")
	public  String tv_specialvideo;


}
