package com.pbtd.playclick.youku.domain;
import java.io.Serializable;

import com.taobao.api.response.VmacNormalShowGetResponse.PersonOuterDto;
import com.taobao.api.response.VmacNormalShowGetResponse.ShowOuterDto;
import com.taobao.api.response.VmacShowpageGetResponse.Person;
import com.taobao.api.response.VmacShowpageGetResponse.Show;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class YouKualbum implements Serializable{
	private static final long serialVersionUID = 1L;

	
	private String  area;//地区 （美国、日本。。）
	private String	category;//类别  （电影、电视剧。。。。）
	private String	categoryId;
	private String	check_state_list;//????
	private String	description;//描述
	private String	director_list;//制片人集合
	private String	episode_count;//集数
	private String	count;//集数
	private int	exclusive;//是否独家
	private String	huge_thumb_url;//大图 横图
	private String	huge_vthumb_url;//大图 树图
	private String	keyword;//关键词
	private String	keyword_list;//关键词集合   
	private String	license_list;//许可证
	private Long	paid;//是否需要支付 1代表是 
	private String	pay_type_list;//支付类型
	private String	performer_list;//演员
	private Long	price;//价格
	private String	release_date;//首映日期  1998-05-20 00:00:00
	private String	show_id;// 节目唯一标识  cc00faac962411de83b1",
	private String	small_thumb_url;//横图 小图": "http:\/\/cn-vmc-images.alicdn.com\/vmac\/10000000050C0000598AA5DE859B5C04AC0800A2?x-oss-process=image\/resize,p_45",
	private String	small_vthumb_url;//树图 小图": "http:\/\/cn-vmc-images.alicdn.com\/vmac\/10000000050E0000598AA5F1859B5E02F9086893?x-oss-process=image\/resize,p_45",
	private String	status;//状态 在线
	private String	sub_category;//标签  科幻、惊悚、动作",
	private String	sub_categoryId;
	private String	title;//标题  
	private Long	try_episodes; //再次获取集数
	private Long	try_time;//再次获取时间毫秒
	private String	try_type;//再次获取类型
	private int	vod_fullprice;//  0半价 1 全价  ,
	private int	vod_ticket;//  点播是否可以用券          0不可用 1可用,
	private String	thumb_url;//图片 横图
	private String	vthumb_url;//图片 树图 http:\/\/cn-vmc-images.alicdn.com\/vmac\/10000000050E0000598AA5F1859B5E02F9086893",
	private String	web_url;//http:\/\/www.soku.com\/detail\/show\/XODMzMTI=",
	private String	youku_rating;//评分 7.700"
	private String storagetime;//入库时间
	private int isStorage;//是否入库
	private String create_time;
	private String  update_time;
	private int	status_next;
	private int	paid_next;
	//优酷媒资获取的付费实体转换
	public YouKualbum  (Show  showouterdto) {
		this.area=showouterdto.getArea();
		this.category = showouterdto.getCategory();//频道
				if(showouterdto.getCheckStateList()!=null){
					StringBuffer value = new StringBuffer();
					for(int i=0;i<showouterdto.getCheckStateList().size();i++){
						String v=showouterdto.getCheckStateList().get(i);
						value.append(","+v);
					}
					this.check_state_list =value.toString().substring(1, value.toString().length());
				}else{
					this.check_state_list ="";
				}
		this.description = showouterdto.getDescription();
		if(showouterdto.getDirectorList()!=null){//导演  验证是否存在 不存在添加/存在   媒资中保存演员名称   
			StringBuffer value = new StringBuffer();
			for(int i=0;i<showouterdto.getDirectorList().size();i++){
				Person v=showouterdto.getDirectorList().get(i);
				value.append(","+v.getName());
			}
			this.director_list =value.toString().substring(1, value.toString().length());
		}else{
			this.director_list ="";
		}
		this.episode_count = showouterdto.getEpisodeCount();
		this.exclusive = showouterdto.getExclusive()==true?1:0;
		this.huge_thumb_url = showouterdto.getHugeThumbUrl();
		this.huge_vthumb_url = showouterdto.getHugeVthumbUrl();
		if(showouterdto.getKeyword()!=null){
			this.keyword_list = showouterdto.getKeyword().replace("r", "").replace("[", "").replace("]", "").replace("\"", "").replaceAll("\\\\", "");//keyword_list
			this.keyword = showouterdto.getKeyword().replace("r", "").replace("[", "").replace("]", "").replace("\"", "").replaceAll("\\\\", "");
		}
		if(showouterdto.getLicenseList()!=null){
			StringBuffer value = new StringBuffer();
			for(int i=0;i<showouterdto.getLicenseList().size();i++){
				String v=showouterdto.getLicenseList().get(i);
				String  v1=v.replace("[", "").replace("]", "").replace("\"", "");
				value.append(","+v1);
			}
			this.license_list =value.toString().substring(1, value.toString().length());
		}else{
			this.license_list ="";
		}
		this.paid = showouterdto.getPaid()==null?0:showouterdto.getPaid();
		
		if(showouterdto.getPayTypeList()!=null){
			StringBuffer value = new StringBuffer();
			for(int i=0;i<showouterdto.getPayTypeList().size();i++){
				String v=showouterdto.getPayTypeList().get(i);
				String  v1=v.replace("[", "").replace("]", "").replace("\"", "");
				value.append(","+v1);
			}
			this.pay_type_list =value.toString().substring(1, value.toString().length());
		}else{
			this.pay_type_list ="";
		}
		
		
		if(showouterdto.getPerformerList()!=null){//演员  验证是否存在 不存在添加/存在   媒资中保存演员名称   
			StringBuffer value = new StringBuffer();
			for(int i=0;i<showouterdto.getPerformerList().size();i++){
				Person v=showouterdto.getPerformerList().get(i);
				value.append(","+v.getName());
			}
			this.performer_list =value.toString().substring(1, value.toString().length());
		}else{
			this.performer_list ="";
		}
		this.price = showouterdto.getPrice();
		this.release_date = showouterdto.getReleaseDate();
		this.show_id = showouterdto.getShowId();
		this.small_thumb_url = showouterdto.getSmallThumbUrl();
		this.small_vthumb_url = showouterdto.getSmallVthumbUrl();
		this.status = showouterdto.getStatus()==null?"在线":showouterdto.getStatus().toString().trim().equals("在线")?"1":"0";
		this.sub_category = showouterdto.getSubCategory();//标签
		this.title = showouterdto.getTitle();
		this.try_episodes = showouterdto.getTryEpisodes();
		this.try_time = showouterdto.getTryTime();
		this.try_type = showouterdto.getTryType();
		this.vod_fullprice = showouterdto.getVodFullprice()==true?1:0;
		this.vod_ticket = showouterdto.getVodTicket()==true?1:0;
		this.thumb_url = showouterdto.getThumbUrl();
		this.vthumb_url = showouterdto.getVthumbUrl();
		this.web_url = showouterdto.getWebUrl();
		this.youku_rating = showouterdto.getYoukuRating();
	}
	
	 
	
	//优酷媒资获取的付费实体转换  增量
	public YouKualbum  (ShowOuterDto  showouterdto) {
		this.area=showouterdto.getArea();
		this.category = showouterdto.getCategory();//频道
				if(showouterdto.getCheckStateList()!=null){
					StringBuffer value = new StringBuffer();
					for(int i=0;i<showouterdto.getCheckStateList().size();i++){
						String v=showouterdto.getCheckStateList().get(i);
						value.append(","+v);
					}
					this.check_state_list =value.toString().substring(1, value.toString().length());
				}else{
					this.check_state_list ="";
				}
		this.description = showouterdto.getDescription();
		if(showouterdto.getDirectorList()!=null){//导演  验证是否存在 不存在添加/存在   媒资中保存演员名称   
			StringBuffer value = new StringBuffer();
			for(int i=0;i<showouterdto.getDirectorList().size();i++){
				PersonOuterDto v=showouterdto.getDirectorList().get(i);
				value.append(","+v.getName());
			}
			this.director_list =value.toString().substring(1, value.toString().length());
		}else{
			this.director_list ="";
		}
		this.episode_count = showouterdto.getEpisodeCount();
		 if(showouterdto.getExclusive() != null&& showouterdto.getExclusive()=="true" ){
			 this.exclusive = 1;}
		 else{
				 this.exclusive = 0;}
		this.huge_thumb_url = showouterdto.getHugeThumbUrl();
		this.huge_vthumb_url = showouterdto.getHugeVthumbUrl();
		if(showouterdto.getKeyword()!=null){
			this.keyword_list = showouterdto.getKeyword().replace("r", "").replace("[", "").replace("]", "").replace("\"", "").replaceAll("\\\\", "");//keyword_list
			this.keyword = showouterdto.getKeyword().replace("r", "").replace("[", "").replace("]", "").replace("\"", "").replaceAll("\\\\", "");
		}
		if(showouterdto.getLicenseList()!=null){
			StringBuffer value = new StringBuffer();
			for(int i=0;i<showouterdto.getLicenseList().size();i++){
				String v=showouterdto.getLicenseList().get(i);
				String  v1=v.replace("[", "").replace("]", "").replace("\"", "");
				value.append(","+v1);
			}
			this.license_list =value.toString().substring(1, value.toString().length());
		}else{
			this.license_list ="";
		}
		this.paid = showouterdto.getPaid()==null?0:showouterdto.getPaid();
		if(showouterdto.getPayTypeList()!=null){
			StringBuffer value = new StringBuffer();
			for(int i=0;i<showouterdto.getPayTypeList().size();i++){
				String v=showouterdto.getPayTypeList().get(i);
				String  v1=v.replace("[", "").replace("]", "").replace("\"", "");
				value.append(","+v1);
			}
			this.pay_type_list =value.toString().substring(1, value.toString().length());
		}else{
			this.pay_type_list ="";
		}
		
		
		if(showouterdto.getPerformerList()!=null){//演员  验证是否存在 不存在添加/存在   媒资中保存演员名称   
			StringBuffer value = new StringBuffer();
			for(int i=0;i<showouterdto.getPerformerList().size();i++){
				PersonOuterDto v=showouterdto.getPerformerList().get(i);
				value.append(","+v.getName());
			}
			this.performer_list =value.toString().substring(1, value.toString().length());
		}else{
			this.performer_list ="";
		}
		this.price = showouterdto.getPrice();
		this.release_date = showouterdto.getReleaseDate();
		this.show_id = showouterdto.getShowId();
		this.small_thumb_url = showouterdto.getSmallThumbUrl();
		this.small_vthumb_url = showouterdto.getSmallVthumbUrl();
		this.status = showouterdto.getStatus()==null?"在线":showouterdto.getStatus().toString().trim().equals("在线")?"1":"0";
		this.sub_category = showouterdto.getSubCategory();//标签
		this.title = showouterdto.getTitle();
		this.try_episodes = showouterdto.getTryEpisodes();
		this.try_time = showouterdto.getTryTime();
		this.try_type = showouterdto.getTryType();
		this.vod_fullprice = showouterdto.getVodFullprice()==null?0:1;
		this.vod_ticket = showouterdto.getVodTicket()==null?0:1;
		this.thumb_url = showouterdto.getThumbUrl();
		this.vthumb_url = showouterdto.getVthumbUrl();
		this.web_url = showouterdto.getWebUrl();
		this.youku_rating = showouterdto.getYoukuRating();
	}
}
