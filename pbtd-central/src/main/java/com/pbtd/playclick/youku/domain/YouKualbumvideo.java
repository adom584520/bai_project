package com.pbtd.playclick.youku.domain;
import java.io.Serializable;

import com.taobao.api.response.VmacNormalPagevideoGetResponse.VideoOuterDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class YouKualbumvideo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String	category;//类别  （电影、电视剧。。。。）
	private String	definition_list ;//string: ["DEF_NORMAL", "DEF_HIGH", "DEF_SUPER"]
	private Long	duration;//时长 6526,
	private String	license_list;//许可证string": ["CIBN", "WASU"]
	private Long	paid;//是否需要支付 1代表是 
	private int	panorama;//全景   ????   false,
	private String	sequence;//排序 1
	private String	show_id;//节目唯一标识cbfa03fa962411de83b1",
	private String	small_thumb_url;//小图  横图 http:\/\/cn-vmc-images.alicdn.com\/vmac\/11000000054102015A18719F8B78D21F020C77B1?x-oss-process=image\/resize,p_45",
	private String	source_type;// 提供源 YOUKU
	private String	status;//状态   发布
	private String	thumb_url;//横图  http:\/\/cn-vmc-images.alicdn.com\/vmac\/11000000054102015A18719F8B78D21F020C77B1
	private String	title;//标题 杀人是我的职业亲爱的
	private String	video_id;// 视频唯一标识  XNjAxNzk1NDcy
	private Long	video_stage;//  视频集数   1,
	private String	video_type;// 视频类型  正片
	private String	web_url;//  http:\/\/v.youku.com\/v_show\/id_XNjAxNzk1NDcy.html"
	private String dramacode;
	private String dramasequence;
	private String create_time;
	private String  update_time;

	private int	status_next;
	private int	paid_next;
	public YouKualbumvideo(VideoOuterDto video){
		this.category = video.getCategory();
		if(video.getDefinitionList()!=null){
			StringBuffer value = new StringBuffer();
			for(int i=0;i<video.getDefinitionList().size();i++){
				String v=video.getDefinitionList().get(i);
				String  v1=v.replace("[", "").replace("]", "").replace("\"", "");
				value.append(","+v1);
			}
			this.definition_list =value.toString().substring(1, value.toString().length());
		}else{
			this.definition_list ="";
		}
		this.duration = video.getDuration();

		if(video.getLicenseList()!=null){
			StringBuffer value = new StringBuffer();
			for(int i=0;i<video.getLicenseList().size();i++){
				String v=video.getLicenseList().get(i);
				String  v1=v.replace("[", "").replace("]", "").replace("\"", "");
				value.append(","+v1);
			}
			this.license_list =value.toString().substring(1, value.toString().length());
		}else{
			this.license_list ="";
		}
		this.paid = video.getPaid()==null?0:video.getPaid();
		this.panorama = video.getPanorama()==true?1:0;
		this.sequence = video.getSequence();
		this.show_id = video.getShowId();
		this.small_thumb_url = video.getSmallThumbUrl();
		this.source_type = video.getSourceType();
		String d="";
		if(video.getStatus()==null){
			d="0";
		}else{
			d=video.getStatus().toString().trim().equals("发布")?"1":"0";
		}
		this.status = d;
		this.thumb_url = video.getThumbUrl();
		this.title = video.getTitle();
		this.video_id = video.getVideoId();
		this.video_stage = video.getVideoStage();
		this.video_type = video.getVideoType();
		this.web_url = video.getWebUrl();
	} 


	public YouKualbumvideo(com.taobao.api.response.VmacNormalVideoGetResponse.VideoOuterDto   video){
		this.category = video.getCategory();
		if(video.getDefinitionList()!=null){
			StringBuffer value = new StringBuffer();
			for(int i=0;i<video.getDefinitionList().size();i++){
				String v=video.getDefinitionList().get(i);
				String  v1=v.replace("[", "").replace("]", "").replace("\"", "");
				value.append(","+v1);
			}
			this.definition_list =value.toString().substring(1, value.toString().length());
		}else{
			this.definition_list ="";
		}
		this.duration = video.getDuration();

		if(video.getLicenseList()!=null){
			StringBuffer value = new StringBuffer();
			for(int i=0;i<video.getLicenseList().size();i++){
				String v=video.getLicenseList().get(i);
				String  v1=v.replace("[", "").replace("]", "").replace("\"", "");
				value.append(","+v1);
			}
			this.license_list =value.toString().substring(1, value.toString().length());
		}else{
			this.license_list ="";
		}
		this.paid = video.getPaid()==null?0:video.getPaid();
		this.panorama = video.getPanorama()==true?1:0;;
		this.sequence = video.getSequence();
		this.show_id = video.getShowId();
		this.small_thumb_url = video.getSmallThumbUrl();
		this.source_type = video.getSourceType();
		this.status = video.getStatus()==null?"发布":video.getStatus().toString().trim().equals("发布")?"1":"0";
		this.thumb_url = video.getThumbUrl();
		this.title = video.getTitle();
		this.video_id = video.getVideoId();
		this.video_stage = video.getVideoStage();
		this.video_type = video.getVideoType();
		this.web_url = video.getWebUrl();
	} 

	
	
	
}
