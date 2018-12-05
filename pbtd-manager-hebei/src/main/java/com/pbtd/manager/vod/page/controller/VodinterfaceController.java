package com.pbtd.manager.vod.page.controller;

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
public class VodinterfaceController {
	public static Logger log = Logger.getLogger(VodinterfaceController.class);
	@Autowired
	private IJsonInterfaceService jsonInterfaceService;
	//手机端 专辑路径
		//@Value("${phone_album}")
		public String  phone_album;	
		
		
	//#访问汇聚库ip地址配置
	@Value("${ccmp_url}")
	public String  ccmp_url;	
	
	
	@Value("${comp_url__proj}")
	public  void setPhone_album(String comp_url__proj) {
		String [] list =  comp_url__proj.split(",");
		String url = list[0];
		String proj = list[1];
		phone_album = url+"/integrate/outputjson/phone/getAlbum?proj="+proj+"&curtime=";
		//System.out.println("phone_album的值为："+phone_album);
	}



	//手机端剧集路径
	//@Value("${phone_albumvideo}")
	public  String phone_albumvideo;
	@Value("${comp_url}")
	public  void setPhone_albumvideo(String comp_url) {
		phone_albumvideo = comp_url+"/integrate/outputjson/phone/getalbumvideo?curtime=";
		//System.out.println("phone_albumvideo的值为："+phone_albumvideo);
	}

	//手机端关联推荐路径
	//@Value("${phone_albumrecommend}")
	public  String phone_albumrecommend;
	@Value("${comp_url}")
	public  void setPhone_albumrecommend(String comp_url) {
		phone_albumrecommend = comp_url+"/integrate/outputjson/phone/getrecommandalbum?curtime=";
		//System.out.println("phone_albumrecommend的值为："+phone_albumrecommend);
	}

	//手机端 频道路径
	//@Value("${phone_channel}")
	public  String phone_channel;
	@Value("${comp_url}")
	public  void setPhone_channel(String comp_url) {
		phone_channel = comp_url+"/integrate/outputjson/phone/getchannel?curtime=";
		//System.out.println("phone_channel的值为："+phone_channel);
	}

	//手机端 频道关联专辑路径
	//@Value("${phone_channelalbum}")
	public String phone_channelalbum;
	@Value("${comp_url}")
	public  void setPhone_channelalbum(String comp_url) {
		phone_channelalbum = comp_url+"/integrate/outputjson/phone/getchannelalbum?curtime=";
		//System.out.println("phone_channelalbum的值为："+phone_channelalbum);
	}

	//手机端 频道关联标签路径
	//@Value("${phone_channellabel}")
	public String phone_channellabel;
	@Value("${comp_url}")
	public  void setPhone_channellabel(String comp_url) {
		phone_channellabel = comp_url+"/integrate/outputjson/phone/getchannellabel?curtime=";
		//System.out.println("phone_channellabel的值为："+ phone_channellabel);
	}
	//手机端标签路径
	//@Value("${phone_label}")
	public  String phone_label;
	@Value("${comp_url}")
	public  void setPhone_label(String comp_url) {
		phone_label = comp_url+"/integrate/outputjson/phone/getlabel?curtime=";
		//System.out.println("phone_label的值为："+ phone_label);
	}

	//手机端标签绑定频道路径
	//@Value("${phone_labelchannel}")
	public  String phone_labelchannel;
	@Value("${comp_url}")
	public  void setPhone_labelchannel(String comp_url) {
		phone_labelchannel = comp_url+"/integrate/outputjson/phone/getlabelchannel?curtime=";
		//System.out.println("phone_labelchannel的值为："+ phone_labelchannel);
	}

	//手机端标签分类路径
	//@Value("${phone_labeltype}")
	public  String phone_labeltype;
	@Value("${comp_url}")
	public  void setPhone_labeltype(String comp_url) {
		phone_labeltype = comp_url+"/integrate/outputjson/phone/getlabeltype?curtime=";
		//System.out.println("phone_labeltype的值为："+ phone_labeltype);
	}

	//热搜
	//	@Value("${hotsearch}")
	public  String hotsearch;
	@Value("${comp_url}")
	public  void setHotsearch(String comp_url) {
		hotsearch = comp_url+"/integrate/outputjson/phone/gethotsearch?curtime=";
		//System.out.println("hotsearch的值为："+ hotsearch);
	}

	//角标
	//@Value("${corner}")
	public  String corner;
	@Value("${comp_url}")
	public  void setCorner(String comp_url) {
		corner = comp_url+"/integrate/outputjson/phone/getcorner?curtime=";
		//System.out.println("corner的值为："+ corner);
	}

	//付费包
	//@Value("${collectfeesbag}")
	public  String collectfeesbag;
	@Value("${comp_url}")
	public  void setCollectfeesbag(String comp_url) {
		collectfeesbag = comp_url+"/integrate/outputjson/phone/getcollectfeesbag?curtime=";
		//System.out.println("collectfeesbag的值为："+ collectfeesbag);
	}

	//手机端获取下发专题接口 入参curtime 日期， limit页数
	//@Value("${phone_special}")
	public  String phone_special;
	@Value("${comp_url}")
	public  void setPhone_special(String comp_url) {
		phone_special = comp_url+"/integrate/outputjson/phone/getspecial?curtime=";
		//System.out.println("phone_special的值为："+ phone_special);
	}

	//手机端获取下发专题绑定专辑接口  无参
	//@Value("${phone_specialvideo}")
	public  String phone_specialvideo;
	@Value("${comp_url}")
	public  void setPhone_specialvideo(String comp_url) {
		phone_specialvideo = comp_url+"/integrate/outputjson/phone/getspecialvideo?curtime=";
		//System.out.println("phone_specialvideo的值为："+ phone_specialvideo);
	}
	//演员路径
	//@Value("${actors}")
	public String actors;
	@Value("${comp_url}")
	public  void setActors(String comp_url) {
		actors = comp_url+"/integrate/outputjson/phone/getactor?curtime=";
		//System.out.println("actors的值为："+ actors);
	}
	//下发频道内推荐轮播图接口
	//@Value("${phone_recommandpic}")
	public String recommandpic;
	@Value("${comp_url}")
	public  void setRecommandpic(String comp_url) {
		recommandpic = comp_url+"/integrate/outputjson/phone/getrecommandpic?curtime=";
		//System.out.println("recommandpic的值为："+ recommandpic);
	}
	//#手机端获取下发专区推荐轮播图接口
	//@Value("${phone_slideshow}")
	public String slideshow;
	@Value("${comp_url}")
	public  void setSlideshow(String comp_url) {
		slideshow = comp_url+"/integrate/outputjson/phone/getslideshow?curtime=";
		//System.out.println("slideshow的值为："+ slideshow);
	}
	//#手机端获取下发开机轮播图接口  
	//@Value("${phone_startslideshow}")
	public String startslideshow;
	@Value("${comp_url}")
	public  void setStartslideshow(String comp_url) {
		startslideshow = comp_url+"/integrate/outputjson/phone/getstartshow?curtime=";
		//System.out.println("startslideshow的值为："+ startslideshow);
	}
	//#手机端获取下发热播推荐接口
	//@Value("${phone_hotseries}")
	public String hotseries;
	@Value("${comp_url}")
	public  void setHotseries(String comp_url) {
		hotseries = comp_url+"/integrate/outputjson/phone/gethotseries?curtime=";
		//System.out.println("hotseries的值为："+ hotseries);
	}
	//#手机端获取下发文字推荐接口
	//@Value("${phone_textrecommendation}")
	public String textrecommendation;
	@Value("${comp_url}")
	public  void setTextrecommendation(String comp_url) {
		textrecommendation = comp_url+"/integrate/outputjson/phone/gettextrecommendation?curtime=";
		//System.out.println("textrecommendation的值为："+ textrecommendation);
	}
	//#手机端标签频道路径
	//@Value("${phone_labelchannel}")
	public String labelchannel;
	@Value("${comp_url}")
	public  void setLabelchannel(String comp_url) {
		labelchannel = comp_url+"/integrate/outputjson/phone/getlabelchannel?curtime=";
		//System.out.println("labelchannel的值为："+ labelchannel);
	}
	//#手机端标签分类路径
	//@Value("${phone_labeltype}")
	public String labeltype;
	@Value("${comp_url}")
	public  void setLabeltype(String comp_url) {
		labeltype = comp_url+"/integrate/outputjson/phone/getlabeltype?curtime=";
		//System.out.println("labeltype的值为："+ labeltype);
	}
	//#手机端标签分类路径
	//@Value("${phone_hotseriesalbum}")
	public String hotseriesalbum;
	@Value("${comp_url}")
	public  void hotHeriesalbum(String comp_url) {
		hotseriesalbum = comp_url+"/integrate/outputjson/phone/gethotseriesalbum?curtime=";
		//System.out.println("hotseriesalbum的值为："+ hotseriesalbum);
	}
	//#手机端cp源路径
	//@Value("${phone_cpsourcetype}")
	public String phonecpsourcetype;
	@Value("${comp_url}")
	public  void setPhonecpsourcetype(String comp_url) {
		phonecpsourcetype = comp_url+"/integrate/outputjson/phone/getsourcetype?curtime=";
		//System.out.println("phonecpsourcetype的值为："+ phonecpsourcetype);
	}

	//tv端 专辑路径
	//@Value("${tv_album}")
	public String  tv_album;
	@Value("${comp_url__proj}")
	public  void setTv_album(String comp_url__proj) {
		String [] list =  comp_url__proj.split(",");
		String url = list[0];
		String proj = list[1];
		tv_album = url+"/integrate/outputjson/tv/getAlbum?proj="+proj+"&curtime=";
		//System.out.println("tv_album的值为："+ tv_album);
	}
	//tv端剧集路径
	//@Value("${tv_albumvideo}")
	public  String tv_albumvideo;
	@Value("${comp_url__proj}")
	public  void setTv_albumvideo(String comp_url__proj) {
		String [] list =  comp_url__proj.split(",");
		String url = list[0];
		String proj = list[1];
		tv_albumvideo = url+"/integrate/outputjson/tv/getalbumvideo?proj="+proj+"&curtime=";
		//System.out.println("tv_albumvideo的值为："+ tv_albumvideo);
	}
	//tv端关联推荐路径
	//@Value("${tv_albumrecommend}")
	public  String tv_albumrecommend;
	@Value("${comp_url__proj}")
	public  void setTv_albumrecommend(String comp_url__proj) {
		String [] list =  comp_url__proj.split(",");
		String url = list[0];
		String proj = list[1];
		tv_albumrecommend = url+"/integrate/outputjson/tv/getrecommandalbum?proj="+proj+"&curtime=";
		//System.out.println("tv_albumrecommend的值为："+ tv_albumrecommend);
	}
	//tv端 频道路径
	//@Value("${tv_channel}")
	public  String tv_channel;
	@Value("${comp_url__proj}")
	public  void setTv_channel(String comp_url__proj) {
		String [] list =  comp_url__proj.split(",");
		String url = list[0];
		String proj = list[1];
		tv_channel = url+"/integrate/outputjson/tv/getchannel?proj="+proj+"&curtime=";
		//System.out.println("tv_channel 的值为："+ tv_channel);
	}
	//tv端 频道关联专辑路径
	//@Value("${tv_channelalbum}")
	public String tv_channelalbum;
	@Value("${comp_url__proj}")
	public  void setTv_channelalbum(String comp_url__proj) {
		String [] list =  comp_url__proj.split(",");
		String url = list[0];
		String proj = list[1];
		tv_channelalbum = url+"/integrate/outputjson/tv/getchannelalbum?proj="+proj+"&curtime=";
		//System.out.println("tv_channelalbum的值为："+ tv_channelalbum);
	}
	//tv端 频道关联标签路径
	//@Value("${tv_channellabel}")
	public String tv_channellabel;
	@Value("${comp_url}")
	public  void setTv_channellabel(String comp_url) {
		tv_channellabel = comp_url+"/integrate/outputjson/tv/getchannellabel?curtime=";
		//System.out.println("tv_channellabel的值为："+ tv_channellabel);
	}
	//tv端标签路径
	//@Value("${tv_label}")
	public  String tv_label;
	@Value("${comp_url__proj}")
	public  void setTv_label(String comp_url__proj) {
		String [] list =  comp_url__proj.split(",");
		String url = list[0];
		String proj = list[1];
		tv_label = url+"/integrate/outputjson/tv/getlabel?proj="+proj+"&curtime=";
		//System.out.println("tv_label的值为："+ tv_label);
	}
	//tv端获取下发专题接口 入参curtime 日期， limit页数
	//@Value("${tv_special}")
	public  String tv_special;
	@Value("${comp_url__proj}")
	public  void setTv_special(String comp_url__proj) {
		String [] list =  comp_url__proj.split(",");
		String url = list[0];
		String proj = list[1];
		tv_special = url+"/integrate/outputjson/tv/getspecial?proj="+proj+"&curtime=";
		//System.out.println("tv_special的值为："+ tv_special);
	}
	//tv端获取下发专题绑定专辑接口  无参
	//@Value("${tv_specialvideo}")
	public  String tv_specialvideo;
	@Value("${comp_url__proj}")
	public  void setTv_specialvideo(String comp_url__proj) {
		String [] list =  comp_url__proj.split(","); 
		String url = list[0];
		String proj = list[1];
		tv_specialvideo = url+"/integrate/outputjson/tv/getspecialvideo?proj="+proj+"&curtime=";
		//System.out.println("tv_specialvideo的值为："+ tv_specialvideo);
	}

	//验证爱看是否存在节目
	//@Value("${aikanseriesname}")
	public  String aikanseriesname;
	@Value("${aikanseriesname}")
	public  void setAikanseriesname(String aikan_url) {
		aikanseriesname = aikan_url+"/QMS/Content/queryContent?";
		//System.out.println("aikanseriesname的值为："+ aikanseriesname);
	}


}
