package com.pbtd.playclick.yinhe.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.util.URLSend;
import com.pbtd.playclick.yinhe.domain.AlbumsWithBLOBs;
import com.pbtd.playclick.yinhe.domain.GitvvideoWithBLOBs;
import com.pbtd.playclick.yinhe.domain.YhChannel;
import com.pbtd.playclick.yinhe.mapper.AlbumsMapper;
import com.pbtd.playclick.yinhe.mapper.GitvvideoMapper;
import com.pbtd.playclick.yinhe.mapper.YhChannelMapper;
import com.pbtd.playclick.yinhe.service.UpdateService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 获取银河数据service
 * 
 * @author Administrator
 *
 */
@Service
@PropertySource(value = { "classpath:config/central.properties" })
public class UpdateServiceImpl implements UpdateService {
	public static Logger log = Logger.getLogger(UpdateServiceImpl.class);
	// #剧集地址1
	@Value("${yh_albuminfovideo1}")
	public String yh_albuminfovideo1;
	// #剧集地址2
	@Value("${yh_albuminfovideo2}")
	public String yh_albuminfovideo2;
	// #鉴权路径1
	@Value("${yh_TokenUrl1}")
	public String yh_TokenUrl1;
	// #鉴权路径2
	@Value("${yh_TokenUrl2}")
	public String yh_TokenUrl2;
	// 专辑详情表
	@Autowired
	private AlbumsMapper albumsMapper;
	// 专辑播放地址表
	@Autowired
	private GitvvideoMapper gitvvideoMapper;
	// 频道表
	@Autowired
	private YhChannelMapper channelMapper;

	@Override
	public void UpdateAlbums(AlbumsWithBLOBs albums, String albumId) {
		// 添加频道表
		YhChannel channel = channelMapper.selectByPrimaryKey(albums.getChnId().toString());
		if (channel == null) {
			YhChannel vodChannel = new YhChannel();
			vodChannel.setChnId(albums.getChnId());
			vodChannel.setChnName(albums.getChnName());
			vodChannel.setLevel("1");
			channelMapper.insert(vodChannel);
		}

		log.info("同步剧集数据&开始");
		// 节目变量设置
		String aiUrl = yh_albuminfovideo2;
		Integer pageNo = 1, pageCapacity = 20, total = 0, sourceId = 32;
		JSONObject json = new JSONObject();
		JSONObject jsonObj = new JSONObject();
		JSONArray result = new JSONArray();
		JSONObject jsonTemp = new JSONObject();
		GitvvideoWithBLOBs video = new GitvvideoWithBLOBs();
		String rtsp = "";
		// 时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String timespan = sdf.format(new Date());
		while (true) {
			String params = "partnerCode=ZJYD&token=" + ncAuth();
			// 请求播放地址
			try {
				rtsp = URLSend.sendHttpClientGet(aiUrl + albumId + "/" + sourceId + "/" + pageNo + "/?" + params);
				json = JSONObject.fromObject(rtsp);
			} catch (Exception e) {
				log.error("播放地址解析失败:" + e);
				continue;
			}
			if (json.containsKey("code") && json.get("code").equals("A000000")) {
				try {
					jsonObj = JSONObject.fromObject(json.get("data"));
					log.info("分页码：" + pageNo + "\t总记录数：" + jsonObj.get("total"));
					total = Integer.parseInt(jsonObj.get("total").toString()); // 获取总记录数
					result = jsonObj.getJSONArray("list");
					String maxsionid = "2";// 默认码率
					if (result.size() > 0) {
						for (int ii = 0; ii < result.size(); ii++) {// 遍历播放剧集
							try {
								jsonTemp = result.getJSONObject(ii);
								video = this.JSONToVideo(jsonTemp, albumId, jsonObj.get("total").toString(), timespan);
								video = ToVideo(video, maxsionid);
								video.setBz((pageNo - 1) * pageCapacity + ii + 1 + "");
								video.setStatus((total - ((pageNo - 1) * pageCapacity + ii)) + "");
								video.setDramacode(System.currentTimeMillis()+""+(int)((Math.random()*9+1)*1000));
								gitvvideoMapper.insert(video);
							} catch (Exception e) {
								continue;
							}
						}

					}
				} catch (Exception e) {
					log.error("剧集异常:" + e);
					continue;
				}
				// 当分页记录数大于等于总记录数时，直接跳出循环
				if (pageNo * pageCapacity >= total) {
					break;
				} else {
					pageNo = pageNo + 1;
					// break; //测试记录
				}
			} else {
				log.info("iqiyi同步剧集数据&开始");
				// 节目变量设置
				String aiUrlz = yh_albuminfovideo1;
				Integer sourceIdz = 30;
				JSONObject jsonz = new JSONObject();
				String rtspz = "";
				// 时间
				SimpleDateFormat sdfz = new SimpleDateFormat("yyyyMMdd");
				String timespanz = sdfz.format(new Date());
				String paramsz = "partnerCode=SCIPTV&token=" + ncAuth();
				// 入库
				try {
					rtspz = URLSend
							.sendHttpClientGet(aiUrlz + albumId + "/" + sourceIdz + "/" + pageNo + "/?" + paramsz);
					jsonz = JSONObject.fromObject(rtspz);
				} catch (Exception e) {
					log.error("播放地址解析失败:" + e);
					continue;
				}
				if (jsonz.containsKey("code") && jsonz.get("code").equals("A000000")) {
					try {
						JSONObject jsonObjz = JSONObject.fromObject(jsonz.get("data"));
						log.info("分页码：" + pageNo + "\t总记录数：" + jsonObjz.get("total"));
						total = Integer.parseInt(jsonObjz.get("total").toString()); // 获取总记录数
						JSONArray resultz = jsonObjz.getJSONArray("list");
						String maxsionid = "2";// 默认码率
						if (resultz.size() > 0) {
							for (int iii = 0; iii < resultz.size(); iii++) {
								try {
									jsonTemp = resultz.getJSONObject(iii);
									video = this.JSONToVideo(jsonTemp, albumId, jsonObjz.get("total").toString(),
											timespanz);
									log.info(video.getAlbumName() + "..." + video.getSubTitle());
									video = ToVideo(video, maxsionid);
									video.setBz((pageNo - 1) * pageCapacity + iii + 1 + "");
									video.setStatus((total - ((pageNo - 1) * pageCapacity + iii)) + "");
									gitvvideoMapper.insert(video);
								} catch (Exception e) {
									continue;
								}
							}
						}
					} catch (Exception e) {
						log.error("剧集异常:" + e);
						continue;
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
		}

		albums.setCurrentCount(total.toString());// 获取当前更新剧集 赋值给专辑表
		// 查询当前剧集是否存在 存在更改 不存在添加
		AlbumsWithBLOBs mapalb = albumsMapper.selectByPrimaryKey(albums.getAlbumId());
		if (mapalb == null) {// 专辑为空时添加专辑
			albumsMapper.insert(albums);
		} else {
			if (!albums.getCurrentCount().equals(mapalb.getCurrentCount())) {
				albumsMapper.updateByPrimaryKeyWithBLOBs(albums);
			}
		}

	}

	private GitvvideoWithBLOBs ToVideo(GitvvideoWithBLOBs video, String maxsionid) throws Exception {

		String ml = video.getM3u8();
		if (ml.equals("[]")) {// 获取码率
			video.setVersionId("2");
		} else {
			JSONArray result1 = JSONArray.fromObject(ml);
			String versionId = "";// 码率拼接所有 源数据的码率
			for (int iiii = 0; iiii < result1.size(); iiii++) {
				JSONObject jsonTemp111 = result1.getJSONObject(iiii);
				versionId += jsonTemp111.get("versionId") + ",";
				maxsionid = jsonTemp111.get("versionId") + "";
			}
			video.setVersionId(versionId);
		}
		// i奇艺播放地址
		video.setTvurl("http://jsmeta.video.gitv.tv/" + video.getAlbumId() + "/" + video.getTvId() + "/" + maxsionid
				+ ".m3u8");
		// movie接口中的唯一标识
		String indexM3u8 = video.getIndexM3u8();
		if ("".equals(indexM3u8) || indexM3u8 == null) {
			video.setPalyurl("");
			video.setPid("");
		} else {
			// 解密播放地址及解析播放versionId
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			byte[] bt = decoder.decodeBuffer(video.getIndexM3u8());
			String m3u8string = new String(bt);
			// 将json字符串转换为json对象
			JSONObject jodata = JSONObject.fromObject(m3u8string);
			// 得到指定json key对象的value对象
			JSONArray joa = JSONArray.fromObject(jodata.get("data"));
			JSONObject O = joa.getJSONObject(0);
			// 播放地址
			video.setPalyurl(O.get("cdn").toString());
			// 唯一标识
			video.setPid(O.get("contentId").toString().replaceAll("p", "m"));
		}
		return video;
	}

	private GitvvideoWithBLOBs JSONToVideo(JSONObject jsonTemp, String albumId, String total, String timespan) {
		GitvvideoWithBLOBs video = new GitvvideoWithBLOBs();
		video.setParentId(albumId);
		video.setAlbumId(jsonTemp.containsKey("albumId") ? jsonTemp.getString("albumId") : "");
		video.setAlbumName(jsonTemp.containsKey("albumName") ? jsonTemp.getString("albumName") : "");
		video.setBeginTime(jsonTemp.containsKey("beginTime") ? jsonTemp.getString("beginTime") : "");
		video.setCpAlbumIdOld(jsonTemp.containsKey("cpAlbumIdOld") ? jsonTemp.getString("cpAlbumIdOld") : "");
		video.setCpId(jsonTemp.containsKey("cpId") ? jsonTemp.getString("cpId") : "");
		video.setDuration(jsonTemp.containsKey("duration") ? jsonTemp.getString("duration") : "");
		video.setEndTime(jsonTemp.containsKey("endTime") ? jsonTemp.getString("endTime") : "");
		video.setIndexM3u8(jsonTemp.containsKey("indexM3u8") ? jsonTemp.getString("indexM3u8") : "");
		video.setIsEffective(jsonTemp.containsKey("isEffective") ? jsonTemp.getString("isEffective") : "");
		video.setIsOnline(jsonTemp.containsKey("isOnline") ? jsonTemp.getString("isOnline") : "");
		video.setIsPurchaseYinheTv(
				jsonTemp.containsKey("isPurchaseYinheTv") ? jsonTemp.getString("isPurchaseYinheTv") : "");
		video.setM3u8(jsonTemp.containsKey("m3u8") ? jsonTemp.getString("m3u8") : "");
		video.setPic(jsonTemp.containsKey("pic") ? jsonTemp.getString("pic") : "");
		video.setPlayOrder(jsonTemp.containsKey("playOrder") ? jsonTemp.getString("playOrder") : "");
		video.setPositiveId(jsonTemp.containsKey("positiveId") ? jsonTemp.getString("positiveId") : "");
		video.setShowDate(jsonTemp.containsKey("showDate") ? jsonTemp.getString("showDate") : "");
		video.setSource(jsonTemp.containsKey("source") ? jsonTemp.getString("source") : "");
		video.setSourceChnId(jsonTemp.containsKey("sourceChnId") ? jsonTemp.getString("sourceChnId") : "");
		video.setSourceId(jsonTemp.containsKey("sourceId") ? jsonTemp.getString("sourceId") : "");
		video.setSubTitle(jsonTemp.containsKey("subTitle") ? jsonTemp.getString("subTitle") : "");

		video.setSuperScripts(jsonTemp.containsKey("superScripts") ? jsonTemp.getString("superScripts") : "");
		video.setTrySeeTimeTv(jsonTemp.containsKey("trySeeTimeTv") ? jsonTemp.getString("trySeeTimeTv") : "");
		video.setTvDesc(jsonTemp.containsKey("tvDesc") ? jsonTemp.getString("tvDesc") : "");
		video.setTvFocus(jsonTemp.containsKey("tvFocus") ? jsonTemp.getString("tvFocus") : "");
		video.setTvId(jsonTemp.containsKey("tvId") ? jsonTemp.getString("tvId") : "");
		video.setTvName(jsonTemp.containsKey("tvName") ? jsonTemp.getString("tvName") : "");
		video.setTvPhase(jsonTemp.containsKey("tvPhase") ? jsonTemp.getString("tvPhase") : "");
		video.setTvSeason(jsonTemp.containsKey("tvSeason") ? jsonTemp.getString("tvSeason") : "");
		video.setTvStatus(jsonTemp.containsKey("tvStatus") ? jsonTemp.getString("tvStatus") : "");
		video.setVideoUrl(jsonTemp.containsKey("videoUrl") ? jsonTemp.getString("videoUrl") : "");

		video.setTimestamp(timespan);
		return video;

	}

	/**
	 * 银河鉴权接口，定时 1小时健权一次
	 * 
	 * GET方式调用下面接口
	 * https://auth.api.gitv.tv/nc_auth/?partnerCode=ZJYDJH&partnerKey=
	 * 61f6fed260a540fc824c4fae56ccf1b0&authenType=1
	 * 如果不用HttpClient的话需要你把该网站的CA证书导入到JDK的keystore中，代码不用修改就可以使用 步骤：
	 * 1.使用IE访问该网址,在弹出的安全警报中点击查看证书，在详细信息中点复制到文件,将该证书保存到本地。 2.然后使用keytool -import
	 * -file %刚才你保存的证书文件% -keystore
	 * (1.4是%JAVA_HOME%\jre\javaws\cacerts,1.5的地址是%JAVA_HOME%\jre\lib\security\
	 * cacerts) -keypass (默认为changeit) 3.然后你的代码就可以跑了
	 */
	public String ncAuth() {
		// String url = yh_TokenUrl1;
		String url = yh_TokenUrl2;
		String rtsp = URLSend.sendHttpClientGet(url);
		JSONObject json = JSONObject.fromObject(rtsp);
		JSONObject dataJson = JSONObject.fromObject(json.get("data"));
		return (String) dataJson.get("token");
	}

}
