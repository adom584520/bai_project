package com.pbtd.playclick.yinhe.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playclick.guoguang.controller.JsonContrlller;
import com.pbtd.playclick.integrate.controller.StrategyxController;
import com.pbtd.playclick.util.PropertyUtil;
import com.pbtd.playclick.util.URLSend;
import com.pbtd.playclick.yinhe.domain.AlbumsWithBLOBs;
import com.pbtd.playclick.yinhe.domain.VodcpxxWithBLOBs;
import com.pbtd.playclick.yinhe.service.IYhLabelService;
import com.pbtd.playclick.yinhe.service.IYhactorsService;
import com.pbtd.playclick.yinhe.service.QuartzService;
import com.pbtd.playclick.yinhe.service.UpdateService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@PropertySource(value = { "classpath:config/central.properties" })
@RequestMapping("/yinhe/QuartzController")
public class QuartzController {
	public static Logger log = Logger.getLogger(QuartzController.class);
	@Autowired
	private JsonContrlller jsoncontrller;
	// #银河频道地址
	@Value("${yh_album}")
	public String yh_album;
	// #更新地址
	@Value("${yh_albumUp}")
	public String yh_albumUp;
	// #增量地址
	@Value("${yh_albumUrl}")
	public String yh_albumUrl;
	// #专辑地址
	@Value("${yh_albuminfo}")
	public String yh_albuminfo;
	// #鉴权路径1
	@Value("${yh_TokenUrl1}")
	public String yh_TokenUrl1;
	// #鉴权路径2
	@Value("${yh_TokenUrl2}")
	public String yh_TokenUrl2;
	@Autowired
	private QuartzService quartzService;
	@Autowired
	private UpdateService updateService;
	@Autowired
	private IYhactorsService yhactorsService;
	@Autowired
	private IYhLabelService yhlabelService;
	@Autowired
	private StrategyxController strategyxController;

	/**
	 * 获取全部节目列表 20150405改成新增节目时先处于下线状态 等全部剧集导入完毕后再上线s
	 */
	@RequestMapping("/quartz/list")
	public void syncAlbumsQuartz() {
		log.info("同步GITV数据&开始");
		String url = yh_album;
		String rtsp = "";
		JSONObject json = new JSONObject();
		JSONArray result = new JSONArray();
		AlbumsWithBLOBs albums = new AlbumsWithBLOBs();
		// 节目变量设置
		String aiUrl = yh_albuminfo;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String timespan = sdf.format(new Date());
		// 清空vodcpxx表
		quartzService.truncate();

		for (int i = 0; i < 14; i++) {
			// Integer i = 0;
			Integer pageNo = 1, pageCapacity = 60, total = 0, chnId = 1000001;
			while (true) {
				String params = "partnerCode=LNYD&token=" + ncAuth();
				// 入库
				try {
					rtsp = URLSend.sendGet(url + (chnId + i) + "/" + pageNo + "/", params);
					json = JSONObject.fromObject(rtsp);
					JSONObject jsonObj = JSONObject.fromObject(json.get("data"));
					log.info("分页码：" + pageNo + "\t总记录数：" + jsonObj.get("total"));
					total = Integer.parseInt(jsonObj.get("total").toString()); // 获取总记录数
					result = jsonObj.getJSONArray("list");
					if (result.size() > 0) {
						for (int ii = 0; ii < result.size(); ii++) {
							try {
								JSONObject job = result.getJSONObject(ii);
								VodcpxxWithBLOBs vodcpxx = this.JSONtoVodcpxx(job);
								log.info(job.get("albumId").toString() + "....." + job.get("albumName").toString());
								rtsp = URLSend.sendHttpClientGet(aiUrl + job.get("albumId").toString() + "/?" + params);
								JSONObject albumsTemp = JSONObject.fromObject(rtsp);
								if (albumsTemp.get("code").equals("A000000")) {
									// 封装到实体类里
									albums = this.JSONtoAlbums(albumsTemp, timespan);
									// 演员,导演表
									yhactorsService.addActors(albums.getActors());
									// 标签表
									yhlabelService.addLabel(albums.getLeafTags(), albums.getChnId());
									// 专辑解析
									albums = Albums(albums);
									quartzService.httpRequest(vodcpxx, albums, job.get("albumId").toString());
								} else {
									continue;
								}

							} catch (Exception ex) {
								log.error("专辑异常: " + ex);
								continue;
							}
						}
					}
					// 当分页记录数大于等于总记录数时，直接跳出循环
					if (pageNo * pageCapacity >= total) {
						break;
					} else {
						pageNo = pageNo + 1;
						// break; //测试记录
					}

				} catch (Exception e) {
					log.error("专辑请求地址异常: " + e);
				}
			}
		} //
		try {
		// 更新dramacode
		quartzService.updateDramacode();
		// 删除重复的剧集
		while (true) {
			int delDrama = quartzService.delDrama();
			if (delDrama == 0) {
				break;
			}
		}
		//银河剧集排序增量sequence
		quartzService.updateSequence();
		} catch (Exception e) {
			log.error("银河剧集处理异常: " + e);
		}
		log.info("同步GITV数据&结束");
	}

	private VodcpxxWithBLOBs JSONtoVodcpxx(JSONObject job) {
		VodcpxxWithBLOBs vodcpxx = new VodcpxxWithBLOBs();
		vodcpxx.setAlbumId(job.containsKey("albumId") ? job.getString("albumId") : "");
		vodcpxx.setAlbumName(job.containsKey("albumName") ? job.getString("albumName") : "");
		vodcpxx.setChnId(job.containsKey("chnId") ? job.getString("chnId") : "");
		vodcpxx.setCpId(job.containsKey("cpId") ? job.getString("cpId") : "");
		vodcpxx.setCurrShowPlayOrder(job.containsKey("currShowPlayOrder") ? job.getString("currShowPlayOrder") : "");
		vodcpxx.setIs3d(job.containsKey("is3d") ? job.getString("is3d") : "");
		vodcpxx.setPic(job.containsKey("pic") ? job.getString("pic") : "");
		vodcpxx.setScore(job.containsKey("score") ? job.getString("score") : "");
		vodcpxx.setSets(job.containsKey("sets") ? job.getString("sets") : "");
		vodcpxx.setSuperScripts(job.containsKey("superScripts") ? job.getString("superScripts") : "");

		return vodcpxx;
	}

	private AlbumsWithBLOBs JSONtoAlbums(JSONObject albumsTemp, String timespan) {
		AlbumsWithBLOBs albums = new AlbumsWithBLOBs();
		JSONObject albumsJson = JSONObject.fromObject(albumsTemp.get("data"));

		albums.setActors(albumsJson.containsKey("actors") ? albumsJson.getString("actors") : "");
		albums.setAlbumAttributes(
				albumsJson.containsKey("albumAttributes") ? albumsJson.getString("albumAttributes") : "");
		albums.setAlbumDesc(albumsJson.containsKey("albumDesc") ? albumsJson.getString("albumDesc") : "");
		albums.setAlbumId(albumsJson.containsKey("albumId") ? albumsJson.getString("albumId") : "");
		albums.setAlbumName(albumsJson.containsKey("albumName") ? albumsJson.getString("albumName") : "");
		albums.setChnId(albumsJson.containsKey("chnId") ? albumsJson.getString("chnId") : "");
		albums.setChnName(albumsJson.containsKey("chnName") ? albumsJson.getString("chnName") : "");

		albums.setCpId(albumsJson.containsKey("cpId") ? albumsJson.getString("cpId") : "");
		albums.setCpList(albumsJson.containsKey("cpList") ? albumsJson.getString("cpList") : "");
		albums.setCurrShowPlayOrder(
				albumsJson.containsKey("currShowPlayOrder") ? albumsJson.getString("currShowPlayOrder") : "");
		albums.setDuration(albumsJson.containsKey("duration") ? albumsJson.getString("duration") : "");
		albums.setFocus(albumsJson.containsKey("focus") ? albumsJson.getString("focus") : "");
		albums.setIs3d(albumsJson.containsKey("is3d") ? albumsJson.getString("is3d") : "");
		albums.setIsPurchase(albumsJson.containsKey("isPurchase") ? albumsJson.getString("isPurchase") : "");
		albums.setIsPurchaseOwn(albumsJson.containsKey("isPurchaseOwn") ? albumsJson.getString("isPurchaseOwn") : "");
		albums.setIsSeries(albumsJson.containsKey("isSeries") ? albumsJson.getString("isSeries") : "");

		albums.setLeafTags(albumsJson.containsKey("leafTags") ? albumsJson.getString("leafTags") : "");
		albums.setMaxSet(albumsJson.containsKey("maxSet") ? albumsJson.getString("maxSet") : "");
		albums.setPhase(albumsJson.containsKey("phase") ? albumsJson.getString("phase") : "");
		albums.setPicUrl(albumsJson.containsKey("picUrl") ? albumsJson.getString("picUrl") : "");
		albums.setPid(albumsJson.containsKey("pid") ? albumsJson.getString("pid") : "");
		albums.setPlayCnt(albumsJson.containsKey("playCnt") ? albumsJson.getString("playCnt") : "");
		albums.setScore(albumsJson.containsKey("score") ? albumsJson.getString("score") : "");
		albums.setScoreLabel(albumsJson.containsKey("scoreLabel") ? albumsJson.getString("scoreLabel") : "");
		albums.setSeason(albumsJson.containsKey("season") ? albumsJson.getString("season") : "");
		albums.setSets(albumsJson.containsKey("sets") ? albumsJson.getString("sets") : "");
		albums.setShowDate(albumsJson.containsKey("showDate") ? albumsJson.getString("showDate") : "");
		albums.setSuperScripts(albumsJson.containsKey("superScripts") ? albumsJson.getString("superScripts") : "");

		albums.setTimestamp(timespan);
		return albums;
	}

	/**
	 * 获取前一天更新数据
	 */
	@RequestMapping("/updateAlbums/list")
	public void syncUpdateAlbumsQuartz() {
		log.info("同步GITV更新数据&开始");
		String params = "partnerCode=LNYD&token=" + ncAuth();
		Integer pageNo = 1, pageCapacity = 500;
		String rtsp = "";
		JSONObject json = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		AlbumsWithBLOBs albums = new AlbumsWithBLOBs();
		// 节目变量设置
		// String Url = yh_albumUp;
		String Url = yh_albumUrl;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = getYesterday();
		String date1 = date + "000000";
		String date2 = date + "235959";
		String timespan = sdf.format(new Date());
		PropertyUtil pro = null;
		try {
			pro = new PropertyUtil("src/main/resources/config/yinhe.properties");
			if (!"null".equals(pro.getProperty("key"))) {
				String[] split = pro.getProperty("key").split(",");
				date1 = split[0] + "000000";
				date2 = split[0] + "235959";
				pageNo = Integer.parseInt(split[1]);
			}
		} catch (Exception e) {
			log.error("增量配置文件读取异常: " + e);
		}
		while (true) {
			// 专辑状态更新接口
			rtsp = URLSend.sendHttpClientGet(
					Url + "/" + pageNo + "/" + pageCapacity + "/" + date1 + "/" + date2 + "/?" + params);
			log.info("rtsp:" + rtsp);
			json = JSONObject.fromObject(rtsp);
			log.info("分页码：" + pageNo + "\t总记录数：" + json.get("recordTotal"));
			Integer total = Integer.valueOf(json.get("recordTotal").toString()); // 获取总记录数
			try {
				pro.WriteProperties("src/main/resources/config/yinhe.properties", "key",
						date1.replace("000000", "") + "," + pageNo);
			} catch (IOException e) {
				log.error("增量配置文件插入异常: " + e);
				continue;
			}
			if (json.get("code").equals("A000000")) {
				jsonArr = JSONArray.fromObject(json.get("data"));
				for (Object temp : jsonArr.toArray()) {
					// for (int j = 0; j < jsonArr.size(); j++) {
					try {
						String albumId = temp.toString();
						// JSONObject jsonTemp = jsonArr.getJSONObject(j);
						// String albumId =
						// jsonTemp.get("cpAlbumId").toString();
						albums = this.UpdateAlbumsQuartz(albumId, timespan);
						if (albums == null) {
							log.info(albumId + ":  获取专辑详情为空!!!!!!");
							continue;
						} else {
							// 演员,导演表
							yhactorsService.addActors(albums.getActors());
							// 标签表
							yhlabelService.addLabel(albums.getLeafTags(), albums.getChnId());
							// 专辑解析
							albums = Albums(albums);
							updateService.UpdateAlbums(albums, albumId);
						}
					} catch (Exception ex) {
						log.error("专辑异常: " + ex);
						continue;
					}
				}
				// 当分页记录数大于等于总记录数时，直接跳出循环
				if (pageNo * pageCapacity >= total) {
					break;
				} else {
					pageNo = pageNo + 1;
					// break; //测试记录
				}
			} else {
				break;
			}
		}
		// 更新dramacode
		quartzService.updateDramacode();
		// 删除重复的剧集
		while (true) {
			int delDrama = quartzService.delDrama();
			if (delDrama == 0) {
				break;
			}
		}
		log.info("同步GITV更新数据&结束");
		try {
			pro.WriteProperties("src/main/resources/config/yinhe.properties", "key", "null");
		} catch (IOException e) {
			log.error("增量配置文件插入异常: " + e);
		}
	}

	// 增量获取专辑公共方法
	public AlbumsWithBLOBs UpdateAlbumsQuartz(String albumId, String timespan) {
		// 节目变量设置
		// String aiUrl = "http://sync.api.gitv.tv/sync/ai/";
		String aiUrl = yh_albuminfo;
		String params = "partnerCode=SCIPTV&token=" + ncAuth();
		String rtsp = URLSend.sendHttpClientGet(aiUrl + albumId + "/?" + params);
		JSONObject json = JSONObject.fromObject(rtsp);
		// 判断是否获取到专辑
		if (json.get("code").equals("A000000")) {
			JSONObject albumsTemp = JSONObject.fromObject(rtsp);
			JSONObject jsonObj = JSONObject.fromObject(albumsTemp.get("data"));
			log.info(jsonObj.get("albumId").toString() + "------" + jsonObj.get("albumName").toString());
			// 封装到实体类里
			AlbumsWithBLOBs albums = this.JSONtoAlbums(albumsTemp, timespan);
			return albums;
		} else {
			String paramsz = "partnerCode=LNYD&token=" + ncAuth();
			String rtspz = URLSend.sendHttpClientGet(aiUrl + albumId + "/?" + paramsz);
			JSONObject jsonz = JSONObject.fromObject(rtspz);
			// 判断是否获取到专辑
			if (jsonz.get("code").equals("A000000")) {
				JSONObject albumsTemp = JSONObject.fromObject(rtspz);
				JSONObject jsonObj = JSONObject.fromObject(albumsTemp.get("data"));
				log.info(jsonObj.get("albumId").toString() + "======" + jsonObj.get("albumName").toString());
				// 封装到实体类里
				AlbumsWithBLOBs albums = this.JSONtoAlbums(albumsTemp, timespan);
				return albums;
			} else {
				return null;
			}
		}

	}

	// 专辑解析
	private AlbumsWithBLOBs Albums(AlbumsWithBLOBs albums) {
		// 导演和演员列表
		String str = albums.getActors().toString();
		if (str.equals("[]")) {
			albums.setActorname("未知");
			albums.setDirectorname("未知");
		} else {
			JSONArray result = JSONArray.fromObject(str);
			// 演员和导演
			StringBuffer actorDisplay = new StringBuffer();
			StringBuffer directorDisplay = new StringBuffer();
			StringBuffer actorids = new StringBuffer();
			StringBuffer directorids = new StringBuffer();
			for (int i = 0; i < result.size(); i++) {
				JSONObject jsonTemp = result.getJSONObject(i);
				if (jsonTemp.get("type").toString().equals("1")) {
					directorDisplay.append(jsonTemp.get("name").equals("") ? "" : (jsonTemp.get("name") + "|"));
					directorids.append(jsonTemp.get("code").equals("") ? "" : (jsonTemp.get("code") + ","));
				} else if (jsonTemp.get("type").toString().equals("3")) {
					actorDisplay.append(jsonTemp.get("name").equals("") ? "" : (jsonTemp.get("name") + "|"));
					actorids.append(jsonTemp.get("code").equals("") ? "" : (jsonTemp.get("code") + ","));
				}
			}
			albums.setActorname(actorDisplay.toString().equals("") ? "未知"
					: actorDisplay.toString().substring(0, actorDisplay.toString().length() - 1));
			albums.setActorids(actorids.toString().equals("") ? ""
					: actorids.toString().substring(0, actorids.toString().length() - 1));
			albums.setDirectorname(directorDisplay.toString().equals("") ? "未知"
					: directorDisplay.toString().substring(0, directorDisplay.toString().length() - 1));
			albums.setDirectorids(directorids.toString().equals("") ? ""
					: directorids.toString().substring(0, directorids.toString().length() - 1));
		}

		// 地区和标签列表
		String jdc = albums.getLeafTags().toString();
		if (jdc.equals("[]")) {
			albums.setOriginalCountry("未知");
			albums.setTags("未知");
			albums.setBz("免费");
		} else {
			JSONArray result = JSONArray.fromObject(jdc);
			// 地区和标签
			StringBuffer originalCountry = new StringBuffer();
			StringBuffer tags = new StringBuffer();
			StringBuffer originaids = new StringBuffer();
			StringBuffer tagsids = new StringBuffer();
			for (int i = 0; i < result.size(); i++) {
				JSONObject jsonTemp = result.getJSONObject(i);
				if (jsonTemp.get("typeId").toString().equals("1")) {
					originalCountry.append(jsonTemp.get("tagName").equals("") ? "" : (jsonTemp.get("tagName") + ","));
					originaids.append(jsonTemp.get("tagId").equals("") ? "" : (jsonTemp.get("tagId") + ","));
				} else if (jsonTemp.get("typeId").toString().equals("2")
						|| jsonTemp.get("typeId").toString().equals("3")
						|| jsonTemp.get("typeId").toString().equals("6")) {
					tags.append(jsonTemp.get("tagName").equals("") ? "" : (jsonTemp.get("tagName") + ","));
					tagsids.append(jsonTemp.get("tagId").equals("") ? "" : (jsonTemp.get("tagId") + ","));
				} else if (jsonTemp.get("typeId").toString().equals("8")) {
					albums.setBz(jsonTemp.get("tagName").toString());
				}
			}
			albums.setOriginalCountry(originalCountry.toString().equals("") ? "未知"
					: originalCountry.toString().substring(0, originalCountry.toString().length() - 1));
			albums.setOriginaids(originaids.toString().equals("") ? ""
					: originaids.toString().substring(0, originaids.toString().length() - 1));
			albums.setTags(
					tags.toString().equals("") ? "未知" : tags.toString().substring(0, tags.toString().length() - 1));
			albums.setTagsids(tagsids.toString().equals("") ? ""
					: tagsids.toString().substring(0, tagsids.toString().length() - 1));
		}
		return albums;
	}

	@RequestMapping("/quartz/addItem/{id}")
	public void addItem(@PathVariable("id") String id) {
		log.info("同步GITV数据&开始");
		String rtsp = "";
		AlbumsWithBLOBs albums = new AlbumsWithBLOBs();
		// 节目变量设置
		String aiUrl = yh_albuminfo;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String timespan = sdf.format(new Date());
		String params = "partnerCode=LNYD&token=" + ncAuth();
		try {
			rtsp = URLSend.sendHttpClientGet(aiUrl + id + "/?" + params);
			JSONObject albumsTemp = JSONObject.fromObject(rtsp);
			if (albumsTemp.get("code").equals("A000000")) {
				// 封装到实体类里
				albums = this.JSONtoAlbums(albumsTemp, timespan);
				// 演员,导演表
				yhactorsService.addActors(albums.getActors());
				// 标签表
				yhlabelService.addLabel(albums.getLeafTags(), albums.getChnId());
				// 专辑解析
				albums = Albums(albums);
				quartzService.httpRequest(null, albums, id);
			}
		} catch (Exception ex) {
			log.error("专辑异常: " + ex);
		}
		log.info("同步GITV数据ID&结束");
	}

	@RequestMapping("/quartz/list2")
	public void syncAlbumsQuartzs() {
		log.info("同步GITV数据&开始");
		String url = yh_album;
		String rtsp = "";
		JSONObject json = new JSONObject();
		JSONArray result = new JSONArray();
		AlbumsWithBLOBs albums = new AlbumsWithBLOBs();
		// 节目变量设置
		String aiUrl = yh_albuminfo;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String timespan = sdf.format(new Date());
		// 清空vodcpxx表
		quartzService.truncate();

		//for (int i = 0; i < 14; i++) {
			 Integer i = 1;
			Integer pageNo = 1, pageCapacity = 60, total = 0, chnId = 1000001;
			while (true) {
				String params = "partnerCode=LNYD&token=" + ncAuth();
				// 入库
				try {
					rtsp = URLSend.sendGet(url + (chnId + i) + "/" + pageNo + "/", params);
					json = JSONObject.fromObject(rtsp);
					JSONObject jsonObj = JSONObject.fromObject(json.get("data"));
					log.info("分页码：" + pageNo + "\t总记录数：" + jsonObj.get("total"));
					total = Integer.parseInt(jsonObj.get("total").toString()); // 获取总记录数
					result = jsonObj.getJSONArray("list");
					if (result.size() > 0) {
						for (int ii = 0; ii < result.size(); ii++) {
							try {
								JSONObject job = result.getJSONObject(ii);
								VodcpxxWithBLOBs vodcpxx = this.JSONtoVodcpxx(job);
								log.info(job.get("albumId").toString() + "....." + job.get("albumName").toString());
								rtsp = URLSend.sendHttpClientGet(aiUrl + job.get("albumId").toString() + "/?" + params);
								JSONObject albumsTemp = JSONObject.fromObject(rtsp);
								if (albumsTemp.get("code").equals("A000000")) {
									// 封装到实体类里
									albums = this.JSONtoAlbums(albumsTemp, timespan);
									// 演员,导演表
									yhactorsService.addActors(albums.getActors());
									// 标签表
									yhlabelService.addLabel(albums.getLeafTags(), albums.getChnId());
									// 专辑解析
									albums = Albums(albums);
									quartzService.httpRequest(vodcpxx, albums, job.get("albumId").toString());
								} else {
									continue;
								}

							} catch (Exception ex) {
								log.error("专辑异常: " + ex);
								continue;
							}
						}
					}
					// 当分页记录数大于等于总记录数时，直接跳出循环
					if (pageNo * pageCapacity >= total) {
						break;
					} else {
						pageNo = pageNo + 1;
						// break; //测试记录
					}

				} catch (Exception e) {
					log.error("专辑请求地址异常: " + e);
				}
			}
			//} 
		try {
		// 更新dramacode
		quartzService.updateDramacode();
		// 删除重复的剧集
		while (true) {
			int delDrama = quartzService.delDrama();
			if (delDrama == 0) {
				break;
			}
		}
		//银河剧集排序增量sequence
		quartzService.updateSequence();
		} catch (Exception e) {
			log.error("银河剧集处理异常: " + e);
		}
		log.info("同步GITV数据&结束");
	}

	
	private String getYesterday() {
		// 获取当前日期的前一天
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat H = new SimpleDateFormat("HH");
		String timespan = H.format(new Date());
		int i = Integer.parseInt(timespan);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (i != 1) {
			calendar.add(Calendar.DATE, -0);
		} else {
			calendar.add(Calendar.DATE, -1);
		}
		date = calendar.getTime();
		return sdf.format(date);
	}

	/**
	 * 银河鉴权接口，定时 1小时健权一次
	 * 
	 * GET方式调用下面接口
	 * https://auth.api.gitv.tv/nc_auth/?partnerCode=LNYD&partnerKey=
	 * 61f6fed260a540fc824c4fae56ccf1b0&authenType=1
	 * 如果不用HttpClient的话需要你把该网站的CA证书导入到JDK的keystore中，代码不用修改就可以使用 步骤：
	 * 1.使用IE访问该网址,在弹出的安全警报中点击查看证书，在详细信息中点复制到文件,将该证书保存到本地。 2.然后使用keytool -import
	 * -file %刚才你保存的证书文件% -keystore
	 * (1.4是%JAVA_HOME%\jre\javaws\cacerts,1.5的地址是%JAVA_HOME%\jre\lib\security\
	 * cacerts) -keypass (默认为changeit) 3.然后你的代码就可以跑了
	 */
	public String ncAuth() {

		String url = yh_TokenUrl1;
		// String url = yh_TokenUrl2;
		String rtsp = URLSend.sendHttpClientGet(url);

		JSONObject json = JSONObject.fromObject(rtsp);

		JSONObject dataJson = JSONObject.fromObject(json.get("data"));

		return (String) dataJson.get("token");
	}
	
	
	/*
	 * 手动汇聚当天数据
	 */
	@RequestMapping("/quartz/curdayalbum")
	 @ResponseBody
	public int timerRate6() {
		log.info(new Date()+"更新  银河定时更新");
		syncAlbumsQuartz();
		log.info("银河更新结束 更新国广数据");
		jsoncontrller.show();
		log.info("汇聚更新"); 
		return 1;
	}

}
