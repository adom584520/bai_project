package com.pbtd.playclick.youku.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.integrate.domain.Vodactors;
import com.pbtd.playclick.integrate.service.face.IVodactorsService;
import com.pbtd.playclick.youku.controller.JsonYoukuContrlller;
import com.pbtd.playclick.youku.domain.YouKualbum;
import com.pbtd.playclick.youku.domain.YouKualbumvideo;
import com.pbtd.playclick.youku.domain.staticmap;
import com.pbtd.playclick.youku.mapper.YouKualbumMapper;
import com.pbtd.playclick.youku.service.face.IYouKualbumService;
import com.taobao.api.response.VmacMediamessageGetsResponse.MediaMessageOuterDto;
import com.taobao.api.response.VmacNormalPagevideoGetResponse.VideoOuterDto;
import com.taobao.api.response.VmacNormalPagevideoGetResponse.VideoPageResultDto;
import com.taobao.api.response.VmacNormalShowGetResponse.PersonOuterDto;
import com.taobao.api.response.VmacNormalShowGetResponse.ShowOuterDto;
import com.taobao.api.response.VmacShowpageGetResponse.Person;
import com.taobao.api.response.VmacShowpageGetResponse.Show;
import com.youku.openapi.PlayPolicyMsgDto;

@Service
public class YouKualbumService implements IYouKualbumService {
	public static Logger log = Logger.getLogger(YouKualbumService.class);
	@Autowired
	private YouKualbumMapper youkualbummapper;
	@Autowired
	private JsonYoukuContrlller jsonyoukucontroller;
	@Autowired
	private IVodactorsService vpdactors;
	@Autowired
	private staticmap staticmap;

	//媒资信息管理
	@Override
	public int count(Map<String, Object> queryParams) {
		return youkualbummapper.count(queryParams);
	}
	@Override
	public List<YouKualbum> page(Map<String, Object> queryParams) {
		return youkualbummapper.page(queryParams);
	}
	@Override
	public List<YouKualbum> find(Map<String, Object> queryParams) {
		return youkualbummapper.find(queryParams);
	}
	@Override
	public YouKualbum load(Map<String, Object> queryParams) {
		return youkualbummapper.load(queryParams);
	}

	public  synchronized void insertvideo(YouKualbumvideo m) {
		youkualbummapper.insertvideo(m);
	}


	//视频信息管理
	@Override
	public int countvideo(Map<String, Object> queryParams) {
		return youkualbummapper.countvideo(queryParams);
	}
	@Override
	public List<YouKualbumvideo> pagevideo( Map<String, Object> queryParams) {
		return youkualbummapper.pagevideo(queryParams);
	}
	@Override
	public YouKualbumvideo loadvideo(Map<String, Object> queryParams) {
		return youkualbummapper.loadvideo(queryParams);
	}
	@Override
	public List<YouKualbumvideo> findvideo(Map<String, Object> queryParams) {
		return youkualbummapper.findvideo(queryParams);
	}
	@Override
	public List<Map<String, Object>> findvideoalbum(Map<String, Object> queryParams) {
		return youkualbummapper.findvideoalbum(queryParams);
	}
	@Override
	public int updatealbumnext(Map<String, Object> queryParams) {
		return youkualbummapper.updatealbumnext(queryParams);
	}
	@Override
	public int updatealbumvideonext(Map<String, Object> queryParams) {
		return youkualbummapper.updatealbumvideonext(queryParams);
	}
	@Override
	public Map<String,Object> findoffset() {
		return youkualbummapper.findoffset();
	}
	@Override
	public int updateoffset(Map<String, Object> map) {
		return youkualbummapper.updateoffset(map);
	}
	//增量获取媒资
	@Override
	public Long importyoukumediamessage(List<MediaMessageOuterDto> ShowOuterDtolist) {
		Long flag=1L;
		for (int i = 0; i < ShowOuterDtolist.size(); i++) {
			MediaMessageOuterDto dto=ShowOuterDtolist.get(i);
			flag=dto.getOffset();
			String show_id=dto.getShowId();
			String video_id=dto.getVideoId();
			//String video_source_type=dto.getVideoSourceType()==null?"YOUKU":dto.getVideoSourceType();
			Map<String, Object> queryParams=new HashMap<String, Object>();
			//获取增量视频
			ShowOuterDto show =jsonyoukucontroller.mediamessage_show(show_id);//专辑
			try {
				save_video_id(queryParams,1,show_id,video_id,0L);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(show!=null){ 
				importalbumOuterDto(show);
			}
		}
		return flag;
	}

	//优酷媒资获取
	@Override
	public  int importyouku(List<Show> showlist) {
		int flag=1;
		for (int i = 0; i < showlist.size(); i++) {
			importalbum(showlist.get(i));//保存节目信息
		}
		return flag;

	}
	//保存视频信息根据showid保存
	public  int  save_video(Map<String,Object>  queryParams,int  count,String show_id,Long offset ){
		VideoPageResultDto  videopageResult=jsonyoukucontroller.pagevideoget(show_id,offset);
		List<VideoOuterDto> resultlist=videopageResult.getVideoList();
		//log.warn(resultlist);
		YouKualbumvideo albumvideo=new YouKualbumvideo();
		if(resultlist==null && videopageResult.getOffset()==0){
			return -1;
		}
		if(resultlist!=null ){
			//log.warn("showid数据"+show_id+"获取"+resultlist.size()+"条数据");
			for (int j = 0; j < resultlist.size(); j++) {
				VideoOuterDto video = resultlist.get(j);
				if(video.getStatus().equals("下线")){
					//	log.warn("下线数据"+video.getVideoId());
					albumvideo.setStatus("0");
					albumvideo.setVideo_id(video.getVideoId());
					youkualbummapper.updatevideostatus(albumvideo);
				}else{
					String v=video.getVideoType();
					int videostage=Integer.parseInt(video.getVideoStage()+"");
					if(videostage<0){
						videostage=0;
					} 
					if(count<videostage &&  v.equals("正片")){
						count=videostage;
					} 
					Map<String,Object>  album=new HashMap<>();
					album.put("show_id",show_id);
					album.put("count",count+"");
					youkualbummapper.updatecount(album);
					try {
						queryParams.put("video_id", video.getVideoId());
						YouKualbumvideo oldvideo=youkualbummapper.loadvideo(queryParams);
						albumvideo=new YouKualbumvideo(video);
						if(oldvideo==null){
							String dramacode = System.currentTimeMillis() + "" + (int) ((Math.random() * 9 + 1) * 10);
							albumvideo.setDramacode(dramacode);
							insertvideo(albumvideo);//添加视频信息
						}else{
							if(albumvideo.getStatus()!=null){
								if(!albumvideo.getStatus().equals(oldvideo.getStatus())){
									youkualbummapper.updatevideostatus(albumvideo);
								}
							}
							if(albumvideo.getPaid()!=null){
								if(!albumvideo.getPaid().equals(oldvideo.getPaid())){
									youkualbummapper.updatevideopaid(albumvideo);
								}
							}
							//	youkualbummapper.updatevideo(albumvideo);//更新视频信息 
						}
					} catch (Exception e) {
						e.printStackTrace();
						log.warn("获取优酷视频信息添加失败，节目id："+video.getShowId()+"视频id："+video.getVideoId());
						continue;
					}
				} 
			}
			Long offset2=videopageResult.getOffset();
			if(offset2>offset  ){
				save_video(queryParams,count,show_id, offset2);
			} 
			return count;
		}else{
			Long offset2=videopageResult.getOffset();
			if(offset2>offset){
				save_video(queryParams,count,show_id, offset2);
			}  
			return -1;
		}
	}

	//保存视频信息 根据视频id查询
	public   int  save_video_id(Map<String,Object>  queryParams,int  count,String show_id,String videoid,Long offset ){
		com.taobao.api.response.VmacNormalVideoGetResponse.VideoOuterDto  videopageResult=jsonyoukucontroller.getvideo(show_id,videoid,offset);
		if(videopageResult!=null){
			com.taobao.api.response.VmacNormalVideoGetResponse.VideoOuterDto video = videopageResult;
			queryParams.put("video_id", video.getVideoId());
			YouKualbumvideo albumvideo=new YouKualbumvideo();
			if(video.getStatus().equals("下线")){
				albumvideo.setStatus("0");
				albumvideo.setVideo_id(video.getVideoId());
				youkualbummapper.updatevideostatus(albumvideo);
			}else{
				try {
					YouKualbumvideo oldvideo=youkualbummapper.loadvideo(queryParams);
					albumvideo=new YouKualbumvideo(video);
					if( video.getVideoType().equals("正片") ){
						count=Integer.parseInt(video.getVideoStage()+"");
					}
					if(oldvideo==null){
						String dramacode = System.currentTimeMillis() + "" + (int) ((Math.random() * 9 + 1) * 10);
						albumvideo.setDramacode(dramacode);
						insertvideo(albumvideo);//添加视频信息
					}else{
						if(!albumvideo.getStatus().equals(oldvideo.getStatus())){
							youkualbummapper.updatevideostatus(albumvideo);
						}
						if(!albumvideo.getPaid().equals(oldvideo.getPaid())){
							youkualbummapper.updatevideopaid(albumvideo);
						}
						//youkualbummapper.updatevideo(albumvideo);//更新视频信息 
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.warn("获取优酷视频信息添加失败，节目id："+video.getShowId()+"视频id："+video.getVideoId());
				}
			}
		}
		return count;
	}

	//单独获取节目媒资视频
	@Override
	public Long showgetalbum(String showid) {
		Map<String, Object> queryParams=new HashMap<String, Object>();
		Long  l1=System.currentTimeMillis(); 
		save_video(queryParams, 1,  showid,  0L );
		queryParams.put("show_id", showid);
		youkualbummapper.updategetcount(queryParams);
		// Long  l2=System.currentTimeMillis()-l1;
		//	 log.warn("获取"+showid+"视频耗时"+"结束:"+l2);
		return 0L;
	}

	//优酷媒资获取 只获取媒资
	@Override
	public  int importyoukualbum(List<Show> showlist) {
		int flag=1;
		for (int i = 0; i < showlist.size(); i++) {
			Long  l1=System.currentTimeMillis();
			Show show = showlist.get(i);
			int cu=show.getEpisodeCount()==null?0:Integer.parseInt(show.getEpisodeCount());
			if(true){
				if(!show.getStatus().equals("下线")){
					try {
						YouKualbum album=new YouKualbum(show);
						Map<String, Object> queryParams=new HashMap<String, Object>();
						int count=1;
						queryParams.put("show_id", album.getShow_id());
						album.setCount(count+"");
						YouKualbum c=youkualbummapper.load(queryParams);
						if(c==null){
							queryParams.clear(); 

							Vodactors actors=new Vodactors();
							if(show.getDirectorList()!=null){
								for(int ii=0;ii<show.getDirectorList().size();ii++){
									Person v=show.getDirectorList().get(ii);
									queryParams.put("name", v.getName());
									queryParams.put("start", 0);
									queryParams.put("pageSize", 10);
									//List<Vodactors> actorslist=vpdactors.find(queryParams);
									if(staticmap.Ac_list.get(v.getName())==null){
										actors.setDirector("1");
										actors.setActor("0");
										actors.setName(v.getName());
										actors.setImgportrait(v.getThumbUrl());
										String c1=System.currentTimeMillis()+"";
										actors.setCode(c1.substring(5, c1.length()));
										vpdactors.insert(actors);
									}
								}
							}
							if(show.getPerformerList()!=null){
								//演员
								queryParams.clear();
								actors=new Vodactors();
								for(int iii=0;iii<show.getPerformerList().size();iii++){
									Person v=show.getPerformerList().get(iii);
									queryParams.put("name", v.getName());
									queryParams.put("start", 0);
									queryParams.put("pageSize", 10);
									//List<Vodactors> actorslist=vpdactors.find(queryParams);
									if(staticmap.Ac_list.get(v.getName())==null){
										actors.setDirector("0");
										actors.setActor("1");
										actors.setName(v.getName());
										actors.setImgportrait(v.getThumbUrl());
										String c2=System.currentTimeMillis()+"";
										actors.setCode(c2.substring(5, c2.length()));
										vpdactors.insert(actors);
									}
								}
							}
							String lableids="";//标签id
							String chnids="";//频道id
							//更新频道
							String chnName=show.getCategory();
							Map<String,Object> channel=new HashMap<String,Object>();
							channel.put("chnName", chnName);
							//Map<String,Object> chnmap=youkualbummapper.findchannel(channel);
							Map<String,Object> chnmap=new HashMap<>();
							if(staticmap.Chn_list.get(chnName)==null){
								youkualbummapper.insertchannel(channel);
								chnmap=youkualbummapper.findchannel(channel);
								staticmap.Chn_list.put(chnmap.get("chnName").toString(),chnmap.get("chnId"));
							} else{
								chnmap.put("chnId",staticmap.Chn_list.get(chnName));
							}
							chnids=chnmap.get("chnId").toString();
							album.setCategoryId(chnids);
							//标签
							String bq=show.getSubCategory();
							if (bq != "") {// 更新标签
								album.setSub_category(bq.replace("、", ","));
								String[] bqs = null;
								if (bq.indexOf(",") > 0) {
									bqs = bq.split(",");
								} else if  (bq.indexOf("、") > 0){ 
									bqs = bq.split("、");
								}else{
									bqs = bq.split("\\|");
								}
								for (String name : bqs) {
									Map<String,Object> label=new HashMap<String,Object>();
									label.put("chnId", chnids);//p频道id
									label.put("tagName", name);
									Map<String,Object>  lablemap =new HashMap<>();
									//youkualbummapper.findlabel(label);
									if(staticmap.Lab_list.get(name+chnids)==null){
										youkualbummapper.insertlabel(label);
										lablemap = youkualbummapper.findlabel(label);
										staticmap.Lab_list.put(lablemap.get("tagName").toString()+lablemap.get("chnId").toString(),lablemap.get("tagId"));
									}else{
										lablemap.put("tagId", staticmap.Lab_list.get(name+chnids));
									}
									lableids+=lablemap.get("tagId").toString()+","; 
								}
								album.setSub_categoryId(lableids.substring(0,lableids.length()-1));
							}
							youkualbummapper.insert(album);//添加媒资节目
						}else{
							if(!album.getStatus().equals(c.getStatus())){
								youkualbummapper.updatealbumstatus(album);
							}
							if(!album.getPaid().equals(c.getPaid())){
								youkualbummapper.updatealbumpaid(album);
							}
							//	youkualbummapper.update(album);
						}
					} catch (Exception e) {
						e.printStackTrace();
						log.warn("获取优酷媒资添加"+show.getShowId());
						flag=0;
						continue;
					}
				}}
			Long  l2=System.currentTimeMillis()-l1;
			System.out.println("耗时"+"结束:"+l2);
		}
		return flag;

	}

	//优酷媒资获取节目 Show
	public void importalbum(Show show ){
		Long  l1=System.currentTimeMillis();
		int cu=show.getEpisodeCount()==null?0:Integer.parseInt(show.getEpisodeCount());
		if(true){
			if(!show.getStatus().equals("下线")){
				try {
					YouKualbum album=new YouKualbum(show);
					Map<String, Object> queryParams=new HashMap<String, Object>();
					int count=1;
					queryParams.put("show_id", album.getShow_id());
					album.setCount(count+"");
					YouKualbum c=youkualbummapper.load(queryParams);
					if(c==null){
						queryParams.clear(); 

						Vodactors actors=new Vodactors();
						if(show.getDirectorList()!=null){
							for(int ii=0;ii<show.getDirectorList().size();ii++){
								Person v=show.getDirectorList().get(ii);
								queryParams.put("name", v.getName());
								queryParams.put("start", 0);
								queryParams.put("pageSize", 10);
								//List<Vodactors> actorslist=vpdactors.find(queryParams);
								if(staticmap.Ac_list.get(v.getName())==null){
									actors.setDirector("1");
									actors.setActor("0");
									actors.setName(v.getName());
									actors.setImgportrait(v.getThumbUrl());
									String c1=System.currentTimeMillis()+"";
									actors.setCode(c1.substring(5, c1.length()));
									vpdactors.insert(actors);
								}
							}
						}
						if(show.getPerformerList()!=null){
							//演员
							queryParams.clear();
							actors=new Vodactors();
							for(int iii=0;iii<show.getPerformerList().size();iii++){
								Person v=show.getPerformerList().get(iii);
								queryParams.put("name", v.getName());
								queryParams.put("start", 0);
								queryParams.put("pageSize", 10);
								//List<Vodactors> actorslist=vpdactors.find(queryParams);
								if(staticmap.Ac_list.get(v.getName())==null){
									actors.setDirector("0");
									actors.setActor("1");
									actors.setName(v.getName());
									actors.setImgportrait(v.getThumbUrl());
									String c2=System.currentTimeMillis()+"";
									actors.setCode(c2.substring(5, c2.length()));
									vpdactors.insert(actors);
								}
							}
						}
						String lableids="";//标签id
						String chnids="";//频道id
						//更新频道
						String chnName=show.getCategory();
						Map<String,Object> channel=new HashMap<String,Object>();
						channel.put("chnName", chnName);
						//Map<String,Object> chnmap=youkualbummapper.findchannel(channel);
						Map<String,Object> chnmap=new HashMap<>();
						if(staticmap.Chn_list.get(chnName)==null){
							youkualbummapper.insertchannel(channel);
							chnmap=youkualbummapper.findchannel(channel);
							staticmap.Chn_list.put(chnmap.get("chnName").toString(),chnmap.get("chnId"));
						} else{
							chnmap.put("chnId",staticmap.Chn_list.get(chnName));
						}
						chnids=chnmap.get("chnId").toString();
						album.setCategoryId(chnids);
						//标签
						String bq=show.getSubCategory();
						if (bq != "") {// 更新标签
							album.setSub_category(bq.replace("、", ","));
							String[] bqs = null;
							if (bq.indexOf(",") > 0) {
								bqs = bq.split(",");
							} else if  (bq.indexOf("、") > 0){ 
								bqs = bq.split("、");
							}else{
								bqs = bq.split("\\|");
							}
							for (String name : bqs) {
								Map<String,Object> label=new HashMap<String,Object>();
								label.put("chnId", chnids);//p频道id
								label.put("tagName", name);
								Map<String,Object>  lablemap =new HashMap<>();
								//youkualbummapper.findlabel(label);
								if(staticmap.Lab_list.get(name+chnids)==null && name!=null && name!=""){
									youkualbummapper.insertlabel(label);
									lablemap = youkualbummapper.findlabel(label);
									staticmap.Lab_list.put(lablemap.get("tagName").toString()+lablemap.get("chnId").toString(),lablemap.get("tagId"));
								}else{
									lablemap.put("tagId", staticmap.Lab_list.get(name+chnids));
								}
								if(lablemap!=null)
								{
									lableids+=lablemap.get("tagId").toString()+","; 
								}
							}
							album.setSub_categoryId(lableids.substring(0,lableids.length()-1));
						}
						youkualbummapper.insert(album);//添加媒资节目
					}else{
						if(!album.getStatus().equals(c.getStatus())){
							youkualbummapper.updatealbumstatus(album);
						}
						if(!album.getPaid().equals(c.getPaid())){
							youkualbummapper.updatealbumpaid(album);
						}
						youkualbummapper.update(album);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.warn("获取优酷媒资添加"+show.getShowId());
				}
			}}
		Long  l2=System.currentTimeMillis()-l1;
		System.out.println("耗时"+"结束:"+l2);

	}

	//优酷媒资获取节目  ShowOuterDto
	public void importalbumOuterDto(ShowOuterDto show ){
		Long  l1=System.currentTimeMillis();
		int cu=show.getEpisodeCount()==null?0:Integer.parseInt(show.getEpisodeCount());
		if(true){
			if(!show.getStatus().equals("下线")){
				try {
					YouKualbum album=new YouKualbum(show);
					Map<String, Object> queryParams=new HashMap<String, Object>();
					queryParams.put("show_id", album.getShow_id());
					int count=youkualbummapper.getmaxvideoid(queryParams);
					album.setCount(count+"");
					YouKualbum c=youkualbummapper.load(queryParams);
					if(c==null){
						queryParams.clear(); 
						Vodactors actors=new Vodactors();
						if(show.getDirectorList()!=null){
							for(int ii=0;ii<show.getDirectorList().size();ii++){
								PersonOuterDto v=show.getDirectorList().get(ii);
								queryParams.put("name", v.getName());
								queryParams.put("start", 0);
								queryParams.put("pageSize", 10);
								//List<Vodactors> actorslist=vpdactors.find(queryParams);
								if(staticmap.Ac_list.get(v.getName())==null){
									actors.setDirector("1");
									actors.setActor("0");
									actors.setName(v.getName());
									actors.setImgportrait(v.getThumbUrl());
									String c1=System.currentTimeMillis()+"";
									actors.setCode(c1.substring(5, c1.length()));
									vpdactors.insert(actors);
								}
							}
						}
						if(show.getPerformerList()!=null){
							//演员
							queryParams.clear();
							actors=new Vodactors();
							for(int iii=0;iii<show.getPerformerList().size();iii++){
								PersonOuterDto v=show.getPerformerList().get(iii);
								queryParams.put("name", v.getName());
								queryParams.put("start", 0);
								queryParams.put("pageSize", 10);
								//List<Vodactors> actorslist=vpdactors.find(queryParams);
								if(staticmap.Ac_list.get(v.getName())==null){
									actors.setDirector("0");
									actors.setActor("1");
									actors.setName(v.getName());
									actors.setImgportrait(v.getThumbUrl());
									String c2=System.currentTimeMillis()+"";
									actors.setCode(c2.substring(5, c2.length()));
									vpdactors.insert(actors);
								}
							}
						}
						String lableids="";//标签id
						String chnids="";//频道id
						//更新频道
						String chnName=show.getCategory();
						Map<String,Object> channel=new HashMap<String,Object>();
						channel.put("chnName", chnName);
						//Map<String,Object> chnmap=youkualbummapper.findchannel(channel);
						Map<String,Object> chnmap=new HashMap<>();
						if(staticmap.Chn_list.get(chnName)==null){
							youkualbummapper.insertchannel(channel);
							chnmap=youkualbummapper.findchannel(channel);
							staticmap.Chn_list.put(chnmap.get("chnName").toString(),chnmap.get("chnId"));
						} else{
							chnmap.put("chnId",staticmap.Chn_list.get(chnName));
						}
						chnids=chnmap.get("chnId").toString();
						album.setCategoryId(chnids);
						//标签
						String bq=show.getSubCategory();
						if (bq != "") {// 更新标签
							album.setSub_category(bq.replace("、", ","));
							String[] bqs = null;
							if (bq.indexOf(",") > 0) {
								bqs = bq.split(",");
							} else if  (bq.indexOf("、") > 0){ 
								bqs = bq.split("、");
							}else{
								bqs = bq.split("\\|");
							}
							for (String name : bqs) {
								Map<String,Object> label=new HashMap<String,Object>();
								label.put("chnId", chnids);//p频道id
								label.put("tagName", name);
								Map<String,Object>  lablemap =new HashMap<>();
								//youkualbummapper.findlabel(label);
								if(staticmap.Lab_list.get(name+chnids)==null){
									youkualbummapper.insertlabel(label);
									lablemap = youkualbummapper.findlabel(label);
									staticmap.Lab_list.put(lablemap.get("tagName").toString()+lablemap.get("chnId").toString(),lablemap.get("tagId"));
								}else{
									lablemap.put("tagId", staticmap.Lab_list.get(name+chnids));
								}
								lableids+=lablemap.get("tagId").toString()+","; 
							}
							album.setSub_categoryId(lableids.substring(0,lableids.length()-1));
						}
						youkualbummapper.insert(album);//添加媒资节目
					}else{
						if(!album.getStatus().equals(c.getStatus())){
							youkualbummapper.updatealbumstatus(album);
						}
						if(!album.getPaid().equals(c.getPaid())){
							youkualbummapper.updatealbumpaid(album);
						}
						youkualbummapper.update(album);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.warn("获取优酷媒资添加"+show.getShowId());
				}
			}}
		/*Long  l2=System.currentTimeMillis()-l1;
					System.out.println("耗时"+"结束:"+l2);*/

	}
	@Override
	public int deleteyoukviedeo() {
		youkualbummapper.deleteyoukviedeo();
		return 0;
	}
	@Override
	public Map<String, Object> findPlayPolicyMsgsoffset() {
		return youkualbummapper.findPlayPolicyMsgsoffset();
	}
	@Override
	public int countPlayPolicyMsgs(Map<String, Object> queryParams) {
		return youkualbummapper.countPlayPolicyMsgs(queryParams);
	}
	@Override
	public List<Map<String, Object>> pagePlayPolicyMsgs(Map<String, Object> queryParams) {
		return youkualbummapper.pagePlayPolicyMsgs(queryParams);
	}
	@Override
	public int insertPlayPolicyMsgs(Map<String, Object> m) {
		return youkualbummapper.insertPlayPolicyMsgs(m);
	}
	@Override
	public int updatePlayPolicyMsgsoffset(Map<String, Object> queryParams) {
		return youkualbummapper.updatePlayPolicyMsgsoffset(queryParams);
	}

	public int PlayPolicyMsgsoffset(List<PlayPolicyMsgDto> list) {
		for (PlayPolicyMsgDto playPolicyMsgDto : list) {
			Map<String, Object> m=new HashMap<>();
			m.put("mediaId", playPolicyMsgDto.getMediaId());
			m.put("type",playPolicyMsgDto.getType());
			m.put("showid", playPolicyMsgDto.getShowId());
			m.put("videoid",playPolicyMsgDto.getVdoId());
			if(playPolicyMsgDto.getShowId()!=null && playPolicyMsgDto.getShowId()!=""){
				int c=youkualbummapper.countPlayPolicyMsgs(m);
				if(c>0){
					m.put("status", 3);
					youkualbummapper.udpatePlayPolicyMsgs(m);
				}else{
					youkualbummapper.insertPlayPolicyMsgs(m);
				}
			}
		}
		return 1;
	}
	@Override
	public int deletePlayPolicyMsgs3(Map<String, Object> queryParams) {
		return youkualbummapper.deletePlayPolicyMsgs3(queryParams);
	}
	@Override
	public int updatePlayPolicyMsgs (Map<String, Object> m) {
		return youkualbummapper.udpatePlayPolicyMsgs(m);
	}
	@Override
	public int insertyouklogger(Map<String, Object> m) {
		return youkualbummapper.insertyouklogger(m);
	}
	@Override
	public int udpatePlayPolicyMsgsstatus(List<Map<String, Object>> m) {
		return youkualbummapper.udpatePlayPolicyMsgsstatus(m);
	}
	@Override
	public int save_mediamessage(MediaMessageOuterDto mediaMessageOuterDto) {
		return youkualbummapper.save_mediamessage(mediaMessageOuterDto);
	}










	/**
	 * -----------------------------------重构增量方法获取数据--------------------------------
	 */
	public void mediamessagenew_start(){
		youkualbummapper.delete_mediamessage(null);
		try {
			//自动汇聚 到运营库接口
			mediamessagenew_start_video();//更新剧集信息
		} catch (Exception e) {
			e.printStackTrace();
		}
		mediamessagenew_start_show();//更新专辑信息

	}
	//更新剧集信息
	public   void  mediamessagenew_start_video(){
		final List<Map<String,Object>> listvideo=youkualbummapper.find_mediamessagevideo(null);
		final int j=10;//线程总数
		ExecutorService executorService=Executors.newFixedThreadPool(j);
		final int s=(int) Math.ceil(listvideo.size()/j);//每个线程需要处理的数据个数
		for (int i = 0; i < j; i++) {
			final int fromIndex;
			final int toIndex;
			switch (i) {
			case 0:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 1:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 2:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 3:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 4:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 5:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 6:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 7:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 8:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 9:
				fromIndex=i*s;
				toIndex=listvideo.size();

				break;
			default:
				fromIndex=i*s;
				toIndex=listvideo.size();
				break;
			}

			executorService.execute(new Runnable(){
				@Override
				public void run() {		
					try {	
						for (Map<String, Object> map : listvideo.subList(fromIndex, toIndex)) {
							String videoid=map.get("video_id").toString();
							String show_id=map.get("show_id").toString();
							save_video_id(map,0,show_id,videoid,0L );
							youkualbummapper.update_mediamessagevideo(map);
						}
						Thread.sleep(2000);		
					} catch (InterruptedException e) {
						e.printStackTrace();		
					}		
				}
			});
		}
		executorService.shutdown();//关闭线程池
		//判断是否所有的线程已经运行完
		while (!executorService.isTerminated()) {
		}
	}
	//更新专辑信息
	public void mediamessagenew_start_show(){
		final List<Map<String,Object>> listshow=youkualbummapper.find_mediamessageshow(null);
		final int j=10;//线程总数
		ExecutorService executorService=Executors.newFixedThreadPool(j);
		final int s=(int) Math.ceil(listshow.size()/j);//每个线程需要处理的数据个数
		for (int i = 0; i < j; i++) {
			final int fromIndex;
			final int toIndex;
			switch (i) {
			case 0:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 1:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 2:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 3:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 4:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 5:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 6:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 7:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 8:
				fromIndex=i*s;
				toIndex=s+i*s;
				break;
			case 9:
				fromIndex=i*s;
				toIndex=listshow.size();
				break;
			default:
				fromIndex=i*s;
				toIndex=listshow.size();
				break;
			}
			executorService.execute(new Runnable(){
				@Override
				public void run() {		
					try {	
						for (Map<String, Object> map : listshow.subList(fromIndex, toIndex)) {
							String show_id=map.get("show_id").toString();
							ShowOuterDto show =jsonyoukucontroller.mediamessage_show(show_id);//专辑
							if(show!=null){ 
								importalbumOuterDto(show);
							}
							youkualbummapper.update_mediamessageshow(map);
						}
						Thread.sleep(2000);		
					} catch (InterruptedException e) {
						e.printStackTrace();		
					}		
				}
			});
		}
		executorService.shutdown();//关闭线程池
		//判断是否所有的线程已经运行完
		while (!executorService.isTerminated()) {
		}
	}
		@Override
		public int update_quarzt(Map<String, Object> m) {
			return youkualbummapper.update_quarzt(m);
		}
		@Override
		public Map<String, Object> find_quarzt(Map<String, Object> m) {
			return youkualbummapper.find_quarzt(m);
		}

	}

