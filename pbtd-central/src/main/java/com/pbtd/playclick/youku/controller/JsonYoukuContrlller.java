package com.pbtd.playclick.youku.controller;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pbtd.playclick.youku.service.impl.YouKualbumService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.VmacMediamessageGetsRequest;
import com.taobao.api.request.VmacNormalPagevideoGetRequest;
import com.taobao.api.request.VmacNormalShowGetRequest;
import com.taobao.api.request.VmacNormalVideoGetRequest;
import com.taobao.api.request.VmacShowpageGetRequest;
import com.taobao.api.response.VmacMediamessageGetsResponse;
import com.taobao.api.response.VmacMediamessageGetsResponse.MediaMessageOuterDto;
import com.taobao.api.response.VmacNormalPagevideoGetResponse;
import com.taobao.api.response.VmacNormalPagevideoGetResponse.VideoPageResultDto;
import com.taobao.api.response.VmacNormalShowGetResponse;
import com.taobao.api.response.VmacNormalShowGetResponse.ShowOuterDto;
import com.taobao.api.response.VmacNormalVideoGetResponse;
import com.taobao.api.response.VmacNormalVideoGetResponse.VideoOuterDto;
import com.taobao.api.response.VmacShowpageGetResponse;
import com.taobao.api.response.VmacShowpageGetResponse.Show;
import com.youku.openapi.PlayPolicyMsgDto;
import com.youku.openapi.ResultDto;
import com.youku.openapi.YoukuPlayPolicySDK;

/**
 * 优酷媒资爬取
 * @author admin *
 */
@DisallowConcurrentExecution
@Controller("youku.JsonYoukuContrlller")
@RequestMapping("/jsonYouku")
public class JsonYoukuContrlller {
	public static Logger log = Logger.getLogger(JsonYoukuContrlller.class);
	@Autowired
	private YoukuProperties YoukuP;
	@Autowired
	private YouKualbumService  youkualbumserbice;
	private final int zr=3;//优酷接口网络超时 再次请求次数
	private final String path=System.getProperty("user.dir")+"/youkufile" ;
	private final String mediamessage="/mediamessage" ;
	private final String getPlayPolicyMsgs="/PlayPolicyMsgs" ;
	private final String album="/album" ;
	private int sumcount=0;
	// (获取节目分页)
	@RequestMapping(value = {"/showpageget"})
	public void showpageget(){
		Map<String,Object> offsetmap=youkualbumserbice.findoffset();
		Long offset=offsetmap.get("value")==null?0:Long.valueOf(offsetmap.get("value").toString());
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacShowpageGetRequest req = new VmacShowpageGetRequest();
		req.setOffset(offset);
		req.setPageSize(YoukuP.pay_showpage_page_size);
		VmacShowpageGetResponse rsp = null;
		Map<String, Object> map=new HashMap<>();
		try {
			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					log.warn(e);
				}
			}
			//System.out.println(rsp.getBody());
			if(rsp.getResult().getSuccess()){
				Long offsetcont=rsp.getResult().getValue().getOffset();
				List<Show> show=rsp.getResult().getValue().getShowList();
				if(show!=null){
					//System.out.println("媒资========"+rsp.getBody());
					youkualbumserbice.importyouku(show);

				}
				map.put("value", offsetcont);
				youkualbumserbice.updateoffset(map);
				if( !"".equals(offsetcont) && offsetcont>1){
					showpageget();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//(按照show_ID获取normal视频信息)
	@RequestMapping(value = {"/pagevideoget"})
	public VideoPageResultDto pagevideoget(String show_id,Long offset){
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacNormalPagevideoGetRequest req = new VmacNormalPagevideoGetRequest();
		req.setAppId(YoukuP.app_key);
		req.setShowId(show_id);
		req.setPageSize(YoukuP.pay_showpage_page_size);
		req.setOffset(offset);
		VmacNormalPagevideoGetResponse rsp = null;
		VideoPageResultDto video=new VideoPageResultDto();
		try {
			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					log.warn(e);
				}
			}
			//System.out.println("视频"+show_id+":==="+rsp.getBody());
			if(rsp.getResult().getSuccess()){
				video = rsp.getResult().getValue();
				return video;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**#
	 *---------------------------------------------------------------------优酷免费媒资--------------------------------------------------------------------
	 */
	//(获取媒资消息) 增量接口
	@RequestMapping(value = {"/mediamessage"})
	public void mediamessage(){
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacMediamessageGetsRequest req = new VmacMediamessageGetsRequest();
		Map<String,Object> offsetmap=youkualbumserbice.findoffset();
		Long media_minoffset=offsetmap.get("media_minoffset")==null?0:Long.valueOf(offsetmap.get("media_minoffset").toString());
		req.setOffset(media_minoffset);
		req.setPageSize(YoukuP.pay_showpage_page_size);
		VmacMediamessageGetsResponse rsp = null;
		Map<String, Object> map=new HashMap<>();
		try {

			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					log.warn(e);
				}
			}

			if(YoukuP.issavefile==1){
				try {
					//当前时间格式
					Date d = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
					String dateNowStr = sdf.format(d);

					SimpleDateFormat sdfyyyy = new SimpleDateFormat("yyyyMMdd");
					String dateNowStryyyy = sdfyyyy.format(d);
					File filedate = new File(path+File.separator+dateNowStryyyy+File.separator+mediamessage);
					if(!filedate.getParentFile().exists()){
						filedate.getParentFile().mkdirs();
					}
					//System.out.println(dateNowStr);
					// 1：利用File类找到要操作的对象
					File file = new File(filedate+ File.separator + dateNowStr+"_"+media_minoffset+".txt");
					if(!file.getParentFile().exists()){
						file.getParentFile().mkdirs();
					}
					//2：准备输出流
					Writer out = new FileWriter(file);
					out.write(rsp.getBody());
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(rsp.getResult().getSuccess()){
				List<MediaMessageOuterDto> ShowOuterDtolist=rsp.getResult().getValue().getMessageList();
				if(ShowOuterDtolist.size()>0){
					Long c=	youkualbumserbice.importyoukumediamessage(ShowOuterDtolist);
					Map<String,Object> offsetmap_n=youkualbumserbice.findoffset();
					media_minoffset=offsetmap_n.get("media_minoffset")==null?0:Long.valueOf(offsetmap_n.get("media_minoffset").toString());
					map.put("media_minoffset", c);
					youkualbumserbice.updateoffset(map);
					if(media_minoffset<c){
						mediamessage();
					} else{

					}
				}
			}
		} catch (Exception e) {
			Map<String, Object> logmap=new HashMap<>();
			logmap.put("operation_info", "访问媒资增量消息");
			logmap.put("param_data", "offset:"+media_minoffset);
			logmap.put("return_data", rsp.getBody());
			logmap.put("url", YoukuP.url);
			logmap.put("exception", (e.getStackTrace()==null?"":e.getStackTrace()[0].toString()));
			youkualbumserbice.insertyouklogger(logmap);
			//e.printStackTrace();
			log.warn("爬取优酷增量媒资结束");
			//爬取结束删除多余剧集
			youkualbumserbice.deleteyoukviedeo();
		}
	} 

	// 增量(获取媒资节目信息) taobao.vmac.normal.show.get 
	public ShowOuterDto mediamessage_show(String  show_id ){
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacNormalShowGetRequest req = new VmacNormalShowGetRequest();
		req.setAppId(YoukuP.app_key);
		req.setShowId(show_id);
		ShowOuterDto show =new ShowOuterDto();
		try {
			VmacNormalShowGetResponse rsp = client.execute(req);
			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}else{
						Map<String, Object> logmap=new HashMap<>();
						logmap.put("operation_info", "访问媒资增量专辑消息");
						logmap.put("param_data", "show_id:"+show_id);
						logmap.put("return_data", rsp.getBody());
						logmap.put("url", YoukuP.url);
						logmap.put("exception","访问媒资增量专辑消息返回数据错误");
						youkualbumserbice.insertyouklogger(logmap);
						break;
					}
				} catch (Exception e) {
					Map<String, Object> logmap=new HashMap<>();
					logmap.put("operation_info", "访问媒资增量专辑消息");
					logmap.put("param_data", "show_id:"+show_id);
					logmap.put("return_data", rsp.getBody());
					logmap.put("url", YoukuP.url);
					logmap.put("exception", (e.getStackTrace()==null?"":e.getStackTrace()[0].toString()));
					youkualbumserbice.insertyouklogger(logmap);
					log.warn(e);
				}
			}
			if(YoukuP.issavefile==1){
				try {
					//当前时间格式
					Date d = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
					String dateNowStr = sdf.format(d);

					SimpleDateFormat sdfyyyy = new SimpleDateFormat("yyyyMMdd");
					String dateNowStryyyy = sdfyyyy.format(d);
					File filedate = new File(path+File.separator+dateNowStryyyy+File.separator+album);
					if(!filedate.getParentFile().exists()){
						filedate.getParentFile().mkdirs();
					}
					// 1：利用File类找到要操作的对象
					File filealbum = new File(filedate+ File.separator +show_id);//专辑文件夹
					if(!filealbum.getParentFile().exists()){
						filealbum.getParentFile().mkdirs();
					}
					File file = new File(filealbum+ File.separator + show_id+"_"+dateNowStr+".txt");//专辑txt文件
					if(!file.getParentFile().exists()){
						file.getParentFile().mkdirs();
					}
					//2：准备输出流
					Writer out = new FileWriter(file);
					out.write(rsp.getBody());
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(rsp.getResult().getSuccess()){
				show =rsp.getResult().getValue().getShow();
				return show;
			}else{
				Map<String, Object> logmap=new HashMap<>();
				logmap.put("operation_info", "访问媒资增量专辑消息");
				logmap.put("param_data", "show_id:"+show_id);
				logmap.put("return_data", rsp.getBody());
				logmap.put("url", YoukuP.url);
				logmap.put("exception","访问媒资增量专辑消息返回数据错误");
				youkualbumserbice.insertyouklogger(logmap);
			}
		} catch (Exception e) {
			Map<String, Object> logmap=new HashMap<>();
			logmap.put("operation_info", "访问媒资增量专辑消息");
			logmap.put("param_data", "show_id:"+show_id);
			logmap.put("return_data", "接口访问出错");
			logmap.put("url", YoukuP.url);
			logmap.put("exception", (e.getStackTrace()==null?"":e.getStackTrace()[0].toString()));
			youkualbumserbice.insertyouklogger(logmap);
			e.printStackTrace();
		}
		return null;
	}

	//按照videoid获取normal视频信息
	@RequestMapping(value = {"/getvideo"})
	public VideoOuterDto getvideo(String show_id,String videoid,Long offset){
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacNormalVideoGetRequest req = new VmacNormalVideoGetRequest();
		req.setSourceType("YOUKU");
		req.setAppId(YoukuP.app_key);
		req.setVideoId(videoid);
		VideoOuterDto video=new VideoOuterDto();
		try {
			VmacNormalVideoGetResponse rsp = null;
			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					Map<String, Object> logmap=new HashMap<>();
					logmap.put("operation_info", "访问媒资增量专辑剧集消息");
					logmap.put("param_data", "video_id:"+videoid);
					logmap.put("return_data", rsp.getBody());
					logmap.put("url", YoukuP.url);
					logmap.put("exception", (e.getStackTrace()==null?"":e.getStackTrace()[0].toString()));
					youkualbumserbice.insertyouklogger(logmap);
					log.warn(e);
				}
			}


			if(YoukuP.issavefile==1){
				try {
					//当前时间格式
					Date d = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
					String dateNowStr = sdf.format(d);

					SimpleDateFormat sdfyyyy = new SimpleDateFormat("yyyyMMdd");
					String dateNowStryyyy = sdfyyyy.format(d);
					File filedate = new File(path+File.separator+dateNowStryyyy+File.separator+album);
					if(!filedate.getParentFile().exists()){
						filedate.getParentFile().mkdirs();
					}

					// 1：利用File类找到要操作的对象
					File filealbum = new File(filedate+ File.separator +show_id+File.separator +"video");//专辑文件夹
					if(!filealbum.getParentFile().exists()){
						filealbum.getParentFile().mkdirs();
					}
					File file = new File(filealbum+ File.separator + videoid+"_"+dateNowStr+".txt");//专辑txt文件
					if(!file.getParentFile().exists()){
						file.getParentFile().mkdirs();
					}
					//2：准备输出流
					Writer out = new FileWriter(file);
					out.write(rsp.getBody());
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}       

			if(rsp.getResult().getSuccess()){
				video = rsp.getResult().getValue().getVideo();
				return video;
			}else{
				Map<String, Object> logmap=new HashMap<>();
				logmap.put("operation_info", "访问媒资增量视频消息");
				logmap.put("param_data", "video_id:"+videoid);
				logmap.put("return_data", rsp.getBody());
				logmap.put("url", YoukuP.url);
				logmap.put("exception","消息返回错误");
				youkualbumserbice.insertyouklogger(logmap);
			}
		} catch (Exception e) {
			Map<String, Object> logmap=new HashMap<>();
			logmap.put("operation_info", "访问媒资增量视频消息");
			logmap.put("param_data", "video_id:"+videoid);
			logmap.put("return_data", "接口返回出错");
			logmap.put("url", YoukuP.url);
			logmap.put("exception", (e.getStackTrace()==null?"":e.getStackTrace()[0].toString()));
			youkualbumserbice.insertyouklogger(logmap);
			e.printStackTrace();
		}
		return null;
	}

	// 爬取已有节目的视频信息 
	@RequestMapping(value = {"/showget/{showid}"})
	public void showget(@PathVariable("showid") String showid ) throws ApiException{
		Map<String, Object> queryParams=new HashMap<>();
		String m=showid;
		queryParams.put("isStorage","0");
		//			queryParams.put("import", 1);
		//			List<YouKualbum> list=youkualbumserbice.find(queryParams);
		//			for (YouKualbum youKualbum : list) {
		//				 String m = youKualbum.getShow_id();
		try {
			youkualbumserbice.showgetalbum(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//			}


	}

	//爬取底量的节目信息 视频暂不爬取	
	@RequestMapping(value = {"/importsum"})
	public void importsum(){
		Map<String,Object> offsetmap=youkualbumserbice.findoffset();
		Long offset=offsetmap.get("value")==null?0:Long.valueOf(offsetmap.get("value").toString());
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacShowpageGetRequest req = new VmacShowpageGetRequest();
		req.setOffset(offset);
		req.setPageSize(YoukuP.pay_showpage_page_size);
		VmacShowpageGetResponse rsp = null;
		Map<String, Object> map=new HashMap<>();
		try {
			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					log.warn(e);
				}
			}
			if(rsp.getResult().getSuccess()){
				Long offsetcont=rsp.getResult().getValue().getOffset();
				List<Show> show=rsp.getResult().getValue().getShowList();
				//	sumcount+=show.size();
				//System.out.println(sumcount);
				//Long  l1=System.currentTimeMillis();
				youkualbumserbice.importyoukualbum(show);
				//Long  l2=System.currentTimeMillis()-l1;
				//System.out.println("爬取offsetcont:"+offsetcont+"结束数据条数："+show.size());
				map.put("value", offsetcont);
				youkualbumserbice.updateoffset(map);
				if(offsetcont>0){
					importsum();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = {"/getPlayPolicyMsgs"})
	public void getPlayPolicyMsgs(){
		//最大数据   超时时间毫秒  socket超时时间
		//获取offset值
		Map<String, Object> offsetmap =youkualbumserbice.findPlayPolicyMsgsoffset();
		int oldoffset=  (int) offsetmap.get("offset");
		Long offset =(long) oldoffset;
		YoukuPlayPolicySDK driver =  YoukuPlayPolicySDK.create(10, 2000, 8000);
		ResultDto rs;
		int minCnt=100;
		try {
			rs = driver.getPlayPolicyMsgs(offset, minCnt);
			if(YoukuP.issavefile==1){	
				try {
					//当前时间格式
					Date d = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
					String dateNowStr = sdf.format(d);


					SimpleDateFormat sdfyyyy = new SimpleDateFormat("yyyyMMdd");
					String dateNowStryyyy = sdfyyyy.format(d);
					File filedate = new File(path+File.separator+dateNowStryyyy+File.separator+getPlayPolicyMsgs);
					if(!filedate.getParentFile().exists()){
						filedate.getParentFile().mkdirs();
					}
					//System.out.println(dateNowStr);
					// 1：利用File类找到要操作的对象
					File file = new File(filedate+ File.separator + dateNowStr+"_"+offset+".txt");
					if(!file.getParentFile().exists()){
						file.getParentFile().mkdirs();
					}
					//2：准备输出流
					Writer out = new FileWriter(file);
					out.write( JSON.toJSONString(rs) );
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			List<PlayPolicyMsgDto> list=rs.getList();
			if(list.size()>0){
				youkualbumserbice.PlayPolicyMsgsoffset(list);
			}
			offsetmap.put("offset", rs.getOffset());
			youkualbumserbice.updatePlayPolicyMsgsoffset(offsetmap);
			if(rs.getOffset()>offset){
				getPlayPolicyMsgs();
			}else{
				youkualbumserbice.deletePlayPolicyMsgs3(null);
				getPlayPolicyMsgs_falg();
			}
		} catch (Exception e) {
			youkualbumserbice.deletePlayPolicyMsgs3(null);
			getPlayPolicyMsgs_falg();
			//e.printStackTrace();
			log.warn("爬取播控结束");
		}
	}	
	//验证是否被播控
	public void getPlayPolicyMsgs_falg(){
		Map<String, Object> queryParams=new HashMap<>();
		queryParams.put("status", 3);
		List<Map<String, Object>> list=youkualbumserbice.pagePlayPolicyMsgs(queryParams);
		List<Map<String, Object>> liststatus=new  ArrayList<>();
		for (Map<String, Object> map : list) {
			String type=map.get("type").toString();
			map.put("status", "1");
			String id="";
			if(type.equals("1")){
				id=map.get("showid").toString();
				if(getPlayPolicyMsgs_show(id)==null){
					map.put("play", "0")	;//被播控
				}else{
					map.put("play", "1")	;//
				}
			}else if(type.equals("2")){
				id=map.get("videoid").toString();
				if(getPlayPolicyMsgs_getvideo("",id,0L)==null){
					map.put("play", "0")	;//被播控
				}else{
					map.put("play", "1")	;//
				}
			}
			liststatus.add(map);
		}
		if(list.size()>0){
			youkualbumserbice.udpatePlayPolicyMsgsstatus(liststatus);
		}
	}
	//按照showid获取normal信息 验证是否播控
	public ShowOuterDto getPlayPolicyMsgs_show(String  show_id ){
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacNormalShowGetRequest req = new VmacNormalShowGetRequest();
		req.setAppId(YoukuP.app_key);
		req.setShowId(show_id);
		ShowOuterDto show =new ShowOuterDto();
		try {
			VmacNormalShowGetResponse rsp =null;
			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					log.warn(e);
				}
			}
			rsp = client.execute(req);
			//System.out.println(rsp.getBody());
			if(rsp.getResult().getSuccess()){
				if(rsp.getResult().getValue().getShow().getStatus().equals("下线")){
					return null;
				}
				show =rsp.getResult().getValue().getShow();
				return show;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	//按照videoid获取normal视频信息 验证是否播控
	public VideoOuterDto getPlayPolicyMsgs_getvideo(String show_id,String videoid,Long offset){
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacNormalVideoGetRequest req = new VmacNormalVideoGetRequest();
		req.setSourceType("YOUKU");
		req.setAppId(YoukuP.app_key);
		req.setVideoId(videoid);
		VideoOuterDto video=new VideoOuterDto();
		try {
			VmacNormalVideoGetResponse rsp = null;
			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					log.warn(e);
				}
			}
			if(rsp.getResult().getSuccess()){
				String  status=rsp.getResult().getValue().getVideo().getStatus();
				if(status.equals("下线") && status!=null){
					return null;
				}
				video = rsp.getResult().getValue().getVideo();
				return video;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//按照videoid获取normal视频信息 验证是否播控
	//供分平台访问验证优酷数据是否上线下线
	@RequestMapping(value = {"/somp_getvideo/{videoid}"})
	@ResponseBody
	public Map<String,Object>  somp_getvideo (@PathVariable("videoid") String videoid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacNormalVideoGetRequest req = new VmacNormalVideoGetRequest();
		req.setSourceType("YOUKU");
		req.setAppId(YoukuP.app_key);
		req.setVideoId(videoid);
		Map<String,Object> m=new HashMap<>();
		try {
			VmacNormalVideoGetResponse rsp = null;
			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					log.warn(e);
				}
			}
			if(rsp.getResult().getSuccess()){
				String  status=rsp.getResult().getValue().getVideo().getStatus();
				if(status.equals("下线") && status!=null){
					m.put("status", 0);
					return m;
				}else{
					m.put("status", 1);
					m.put("paid", rsp.getResult().getValue().getVideo().getPaid());
					return m;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		m.put("status", 0);
		return m;
	}
	//按照showid获取normal信息 验证是否播控
	//供分平台访问验证优酷数据是否上线下线
	@RequestMapping(value = {"/somp_getalbum/{showid}"})
	@ResponseBody
	public Map<String,Object>  somp_getalbum (@PathVariable("showid") String showid,HttpServletRequest request,HttpServletResponse response) throws IOException{
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacNormalShowGetRequest req = new VmacNormalShowGetRequest();
		req.setAppId(YoukuP.app_key);
		req.setShowId(showid);
		ShowOuterDto show =new ShowOuterDto();
		Map<String,Object> m=new HashMap<>();
		try {
			VmacNormalShowGetResponse rsp = null;
			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					log.warn(e);
				}
			}
			if(rsp.getResult().getSuccess()){
				if(rsp.getResult().getValue().getShow().getStatus().equals("下线")){
					m.put("status", 0);
					return m;
				}else{
					m.put("status", 1);
					m.put("paid", rsp.getResult().getValue().getShow().getPaid());
					return m;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		m.put("status", 0);
		return m;
	}



	//按照showid获取normal信息 验证是否播控
	//访问验证优酷最新数据
	@RequestMapping(value = {"/gettitlealbum/{showid}"})
	public String  gettitlealbum (@PathVariable("showid") String showid,
			Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacNormalShowGetRequest req = new VmacNormalShowGetRequest();
		req.setAppId(YoukuP.app_key);
		req.setShowId(showid);
		try {
			VmacNormalShowGetResponse rsp = null;
			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					log.warn(e);
				}
			}
			if(rsp.getResult().getSuccess()){
				if(rsp.getResult().getValue().getShow().getStatus().equals("下线")){
					model.addAttribute("albumjson","{}");
				}else{
					model.addAttribute("albumjson",rsp.getBody());
				}
			}
		} catch (Exception e) {
			model.addAttribute("albumjson","{}");
			e.printStackTrace();
		}
		return "/youku/albumsinfo/showalbum";
	}

	//按照videoid获取normal视频信息  
	//访问验证优酷最新数据
	@RequestMapping(value = {"/gettitlealbumvideo/{videoid}"})
	public String   gettitlealbumvideo (@PathVariable("videoid") String videoid,
			Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacNormalVideoGetRequest req = new VmacNormalVideoGetRequest();
		req.setSourceType("YOUKU");
		req.setAppId(YoukuP.app_key);
		req.setVideoId(videoid);
		try {
			VmacNormalVideoGetResponse rsp = null;
			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					log.warn(e);
				}
			}
			if(rsp.getResult().getSuccess()){
				String  status=rsp.getResult().getValue().getVideo().getStatus();
				if(status.equals("下线") && status!=null){
					model.addAttribute("albumjson","{}");
				}else{
					model.addAttribute("albumjson",rsp.getBody());
				}
			}
		} catch (Exception e) {
			model.addAttribute("albumjson","{}");
			e.printStackTrace();
		}
		return "/youku/albumsinfo/showalbum";
	}








	/**
	 * 
	 * -----------------------------------重构增量方法获取数据--------------------------------
	 */
	//(获取媒资消息) 增量接口
	@RequestMapping(value = {"/mediamessagenew"})
	public void mediamessagenew(){
		TaobaoClient client = new DefaultTaobaoClient(YoukuP.url, YoukuP.app_key, YoukuP.secret);
		VmacMediamessageGetsRequest req = new VmacMediamessageGetsRequest();
		Map<String,Object> offsetmap=youkualbumserbice.findoffset();
		Long media_minoffset=offsetmap.get("media_minoffset")==null?0:Long.valueOf(offsetmap.get("media_minoffset").toString());
		req.setOffset(media_minoffset);
		req.setPageSize(YoukuP.pay_showpage_page_size);
		VmacMediamessageGetsResponse rsp = null;
		Map<String, Object> map=new HashMap<>();
		try {

			for (int j = 0; j < zr; j++) {
				try {
					rsp = client.execute(req);
					if(rsp.getResult().getSuccess()){
						break;
					}
				} catch (Exception e) {
					log.warn(e);
				}
			}

			if(YoukuP.issavefile==1){
				try {
					//当前时间格式
					Date d = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
					String dateNowStr = sdf.format(d);

					SimpleDateFormat sdfyyyy = new SimpleDateFormat("yyyyMMdd");
					String dateNowStryyyy = sdfyyyy.format(d);
					File filedate = new File(path+File.separator+dateNowStryyyy+File.separator+mediamessage);
					if(!filedate.getParentFile().exists()){
						filedate.getParentFile().mkdirs();
					}
					//System.out.println(dateNowStr);
					// 1：利用File类找到要操作的对象
					File file = new File(filedate+ File.separator + dateNowStr+"_"+media_minoffset+".txt");
					if(!file.getParentFile().exists()){
						file.getParentFile().mkdirs();
					}
					//2：准备输出流
					Writer out = new FileWriter(file);
					out.write(rsp.getBody());
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(rsp.getResult().getSuccess()){
				List<MediaMessageOuterDto> ShowOuterDtolist=rsp.getResult().getValue().getMessageList();
				Long c=0L;
				for (MediaMessageOuterDto mediaMessageOuterDto : ShowOuterDtolist) {
					try {
						youkualbumserbice.save_mediamessage(mediaMessageOuterDto);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(c<mediaMessageOuterDto.getOffset()){
						c=mediaMessageOuterDto.getOffset();
					}
				}
				Map<String,Object> offsetmap_n=youkualbumserbice.findoffset();
				media_minoffset=offsetmap_n.get("media_minoffset")==null?0:Long.valueOf(offsetmap_n.get("media_minoffset").toString());
				map.put("media_minoffset", c);
				youkualbumserbice.updateoffset(map);
				if( ShowOuterDtolist.size()>0 && media_minoffset<c ){
					mediamessagenew();
				} else{

				}

			}
		} catch (Exception e) {
			Map<String, Object> logmap=new HashMap<>();
			logmap.put("operation_info", "访问媒资增量消息");
			logmap.put("param_data", "offset:"+media_minoffset);
			logmap.put("return_data", rsp.getBody());
			logmap.put("url", YoukuP.url);
			logmap.put("exception", (e.getStackTrace()==null?"":e.getStackTrace()[0].toString()));
			youkualbumserbice.insertyouklogger(logmap);
			//e.printStackTrace();
			log.warn("爬取优酷增量媒资结束");
		}
	} 
	//增量接口爬取结束后，开始处理增量数据
	@RequestMapping(value = {"/mediamessagenew_start"})
	public void mediamessagenew_start(){
		youkualbumserbice.mediamessagenew_start();

	}
	public int update_quarzt(Map<String, Object> m) {
		return youkualbumserbice.update_quarzt(m);
	}
	public Map<String, Object> find_quarzt(Map<String, Object> m) {
		return youkualbumserbice.find_quarzt(m);
	}
}
