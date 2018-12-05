package com.pbtd.manager.vod.page.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.inject.page.controller.InjectPhoneOutPutController;
import com.pbtd.manager.inject.page.controller.InjectTvOutPutController;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.vod.common.actors.domain.Vodactors;
import com.pbtd.manager.vod.common.actors.service.face.IVodactorsService;
import com.pbtd.manager.vod.common.corner.domain.VodCollectfeesbag;
import com.pbtd.manager.vod.common.corner.domain.VodCorner;
import com.pbtd.manager.vod.common.corner.service.face.IVodCollectfeesbagService;
import com.pbtd.manager.vod.common.corner.service.face.IVodCornerService;

import com.pbtd.manager.vod.page.controller.VodcentralController;
import com.pbtd.manager.vod.page.mapper.JsonInterfaceMapper;
import com.pbtd.manager.vod.page.service.face.IJsonInterfaceService;
import com.pbtd.manager.vod.phone.album.domain.Vodalbuminfo;
import com.pbtd.manager.vod.phone.album.service.face.IVodAlbuminfoService;
import com.pbtd.manager.vod.phone.common.domain.VodLabeltype;
import com.pbtd.manager.vod.phone.common.domain.Vodchannel;
import com.pbtd.manager.vod.phone.common.domain.Vodlabel;
import com.pbtd.manager.vod.phone.common.service.face.IVodChannelService;
import com.pbtd.manager.vod.phone.common.service.face.IVodLabelService;
import com.pbtd.manager.vod.phone.common.service.face.IVodLabeltypeService;
import com.pbtd.manager.vod.phone.hotsearch.domain.VodHotSearchInfo;
import com.pbtd.manager.vod.phone.hotsearch.service.face.IVodHotSearchService;
import com.pbtd.manager.vod.phone.hotseries.domain.VodHotseries;
import com.pbtd.manager.vod.phone.hotseries.service.IVodHotseriesService;
import com.pbtd.manager.vod.phone.slideshow.domain.Slideshow;
import com.pbtd.manager.vod.phone.slideshow.domain.StartSlideshow;
import com.pbtd.manager.vod.phone.slideshow.query.SlideshowQueryObject;
import com.pbtd.manager.vod.phone.slideshow.query.StartSlideshowQueryObject;
import com.pbtd.manager.vod.phone.slideshow.service.SlideshowService;
import com.pbtd.manager.vod.phone.slideshow.service.StartSlideshowService;
import com.pbtd.manager.vod.phone.special.domain.VodSpecial;
import com.pbtd.manager.vod.phone.special.service.face.IVodSpecialService;
import com.pbtd.manager.vod.system.domain.Cpsource;
import com.pbtd.manager.vod.system.domain.RecommandPic;
import com.pbtd.manager.vod.system.domain.Textrecommendation;
import com.pbtd.manager.vod.system.service.face.CpsourceService;
import com.pbtd.manager.vod.system.service.face.ITextrecommendationService;
import com.pbtd.manager.vod.system.service.face.RecommandPicService;
import com.pbtd.manager.vod.tv.album.domain.VodTvAlbuminfo;
import com.pbtd.manager.vod.tv.album.service.face.IVodTvAlbuminfoService;
import com.pbtd.manager.vod.tv.common.domain.VodTvchannel;
import com.pbtd.manager.vod.tv.common.domain.VodTvlabel;
import com.pbtd.manager.vod.tv.common.service.face.IVodTvChannelService;
import com.pbtd.manager.vod.tv.common.service.face.IVodTvLabelService;
import com.pbtd.manager.vod.tv.special.domain.VodTvSpecial;
import com.pbtd.manager.vod.tv.special.service.face.IVodTvSpecialService;

import ch.qos.logback.classic.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class JsonInterfaceService implements IJsonInterfaceService {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(JsonInterfaceService.class);
	@Autowired
	private VodcentralController vodcentral;

	@Autowired
	private JsonInterfaceMapper jsonInterfaceMapper;

	@Autowired
	private IVodChannelService vodchannelService;

	@Autowired
	private IVodLabelService vodlabelService;

	@Autowired
	private IVodHotSearchService vodHotSearchService;

	@Autowired
	private IVodSpecialService vodspecialService;

	@Autowired
	private IVodactorsService vodactorsService;

	@Autowired
	private IVodCornerService vodCornerService;

	@Autowired
	private IVodCollectfeesbagService vodCollectfeesbagService;

	@Autowired
	private IVodAlbuminfoService vodAlbuminfoService;

	@Autowired
	private IVodTvChannelService vodtvchannelService;

	@Autowired()
	private IVodTvLabelService vodtvlabelService;

	@Autowired
	private IVodTvAlbuminfoService vodTvAlbuminfoService;

	@Autowired
	private IVodTvSpecialService vodtvspecialService;
	@Autowired
	private RecommandPicService recommandService;
	@Autowired
	private ITextrecommendationService textrecommendationService;
	@Autowired
	private IVodHotseriesService vodHotseriesService;
	@Autowired
	private SlideshowService slideshowService;
	@Autowired
	private StartSlideshowService startSlideshowService;
	@Autowired
    private IVodLabeltypeService vodlabeltypeService;
	@Autowired
	private CpsourceService cpsourceService;
	
	//自动下发到注入平台
	@Autowired
	private InjectPhoneOutPutController injectPhoneOutPut;

	@Autowired
	private InjectTvOutPutController injectTvOutPut;

	@Override
	public int phonealbuminfo(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "phonealbuminfo",
					map.get("ids").toString());
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					albuminfoJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "phonealbuminfo", map.get("ids").toString());
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									albuminfoJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
			//自动下发到注入平台
			injectPhoneOutPut.phoneInject();
		} catch (Exception e) {
		//	e.printStackTrace();
			logger.warn("手机中心下发到分平台专辑出现错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析手机专辑数据信息 保存数据
	private int albuminfoJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Vodalbuminfo map = JSON.parseObject(job.toString(), Vodalbuminfo.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("seriesCode", map.getSeriesCode());
					List<Vodalbuminfo> list = vodAlbuminfoService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodAlbuminfoService.insertjson(map);
					} else {
						vodAlbuminfoService.update(map);
					}
					phonealbuminfovideo(queryParams);
					phonealbuminforecommend(queryParams);
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("手机中心下发到分平台专辑数据保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int phonealbuminfovideo(Map<String, Object> map) {
		try {
			String jsonString = importurl(vodcentral.phone_albumvideo, "", 1, "phonealbuminfovideo",
					map.get("seriesCode").toString());
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					abuminfovideoJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(vodcentral.phone_albumvideo, "", i, "phonealbuminfovideo",
									map.get("seriesCode").toString());
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									abuminfovideoJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("手机中心下发到分平台专辑剧集数据获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析手机专辑剧集数据信息 保存数据
	private int abuminfovideoJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Map<String, Object> map = (Map<String, Object>) JSON.parse(job.toString());
				/*	Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("seriesCode", map.get("seriesCode"));
					queryParams.put("drama", map.get("drama"));
					queryParams.put("dramasequence", map.get("dramasequence"));
					List<Map<String, Object>> list = vodAlbuminfoService.findAlbumsinfovideo(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
*/						vodAlbuminfoService.insertvideojson(map);
					/*}*/
				} catch (Exception e) {
				//	e.printStackTrace();
					logger.warn("手机中心下发到分平台专辑剧集数据保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	// 专辑关联推荐数据
	@Override
	public int phonealbuminforecommend(Map<String, Object> map) {
		try {
			String jsonString = importurl(vodcentral.phone_albumrecommend, "", 1, "phonealbuminfovideo",
					map.get("seriesCode").toString());
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					Map<String, Object> queryParams = new HashMap<>();
					queryParams.put("seriesCode", map.get("seriesCode").toString());
					vodAlbuminfoService.deletealbum(queryParams);
					phonealbuminforecommendJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(vodcentral.phone_albumrecommend, "", i,
									"phonealbuminfovideo", map.get("seriesCode").toString());
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									phonealbuminforecommendJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("手机中心下发到分平台专辑关联推荐数据获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析手机专辑关联推荐专辑数据信息 保存数据
	private int phonealbuminforecommendJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Map<String, Object> map = (Map<String, Object>) JSON.parse(job.toString());
					vodAlbuminfoService.addalbum(map);
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("手机中心下发到分平台专辑关联推荐数据保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int phonechannel(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "phonechannel",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					phonechannelJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "phonechannel", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									phonechannelJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			logger.warn("手机中心下发到分平台频道信息错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析手机频道数据信息 保存数据
	private int phonechannelJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Vodchannel map = JSON.parseObject(job.toString(), Vodchannel.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("channelCode", map.getChannelCode());
					List<Vodchannel> list = vodchannelService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodchannelService.insertjson(map);
						phonechannelalbum(map);
					}
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("手机中心下发到分平台频道信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	// 频道专辑关联数据
	public int phonechannelalbum(Vodchannel map) {
		try {
			String jsonString = importurl(vodcentral.phone_channelalbum, "", 1, "phonechannelalbum",
					map.getChannelCode() + "");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					Map<String, Object> queryParams = new HashMap<>();
					queryParams.put("channelCode", map.getChannelCode());
					vodchannelService.deletesalbum(queryParams);
					phonechannelalbumJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(vodcentral.phone_channelalbum, "", i, "phonechannelalbum",
									map.getChannelCode() + "");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									phonechannelalbumJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("手机中心下发到分平台频道关联推荐信息获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析手机频道专辑关联数据信息 保存数据
	private int phonechannelalbumJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Map<String, Object> map = (Map<String, Object>) JSON.parse(job.toString());
					vodchannelService.insertalbum(map);
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("手机中心下发到分平台频道关联专辑信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int phonelabel(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "phonelabel",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					phonelabelJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "phonelabel", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									phonelabelJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("手机中心下发到分平台标签信息获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析手机标签数据信息 保存数据
	private int phonelabelJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Vodlabel map = JSON.parseObject(job.toString(), Vodlabel.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("id", map.getId());
					List<Vodlabel> list = vodlabelService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodlabelService.insertjson(map);
						String ids=String.valueOf(map.getId());
						getlabelchannel(ids);
						
					}
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("手机中心下发到分平台标签信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int phonehotsearch(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "phonehotsearch",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					vodHotSearchService.deletes(null);// 存在更新数据 删除原有数据
					JSONArray jsonObj = json.getJSONArray("data");
					hotsearchJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "phonehotsearch", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									hotsearchJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("手机中心下发到分平台热播获取错误"+map);
			
			return 0;
		}
		return 1;
	}

	// 解析热搜数据信息 保存数据
	private int hotsearchJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					VodHotSearchInfo map = JSON.parseObject(job.toString(), VodHotSearchInfo.class);
					vodHotSearchService.insert(map);
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("手机中心下发到分平台热播保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int phonespecial(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "phonespecial",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					specialJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "phonespecial", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									specialJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("手机中心下发到分平台专题信息获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析专题数据信息 保存数据
	private int specialJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					VodSpecial map = JSON.parseObject(job.toString(), VodSpecial.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("id", map.getId());
					List<VodSpecial> list = vodspecialService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodspecialService.insertjson(map);
						String nextjsonString = importurl(vodcentral.phone_specialvideo, "", 1, "phonespecialalbum",
								map.getId() + "");
						if (!nextjsonString.equals("") && nextjsonString != null) {
							JSONObject json = JSONObject.fromObject(nextjsonString);
							String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
							int numsum = json.get("numsum") == null ? 1
									: Integer.parseInt(json.get("numsum").toString());// 数据总页数
							if (code.equals("1")) {
								JSONArray jsonspecialalbum = json.getJSONArray("data");
								Map<String, Object> specialmap=new HashMap<>();
								specialmap.put("special_id", map.getId());
								vodspecialService.deletesalbum(specialmap);
								jsonspecialalbumJson(jsonspecialalbum);
							}
						}
					}else{
						vodspecialService.update(map);
						String nextjsonString = importurl(vodcentral.phone_specialvideo, "", 1, "phonespecialalbum",
								map.getId() + "");
						if (!nextjsonString.equals("") && nextjsonString != null) {
							JSONObject json = JSONObject.fromObject(nextjsonString);
							String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
							int numsum = json.get("numsum") == null ? 1
									: Integer.parseInt(json.get("numsum").toString());// 数据总页数
							if (code.equals("1")) {
								JSONArray jsonspecialalbum = json.getJSONArray("data");
								Map<String, Object> specialmap=new HashMap<>();
								specialmap.put("special_id", map.getId());
								vodspecialService.deletesalbum(specialmap);
								jsonspecialalbumJson(jsonspecialalbum);
							}
						}
					}
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("手机中心下发到分平台专题信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	// 解析专题绑定数据数据信息 保存数据
	private int jsonspecialalbumJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Map<String, Object> map = (Map<String, Object>) JSON.parse(job.toString());
					map.put("id", map.get("special_id"));
					vodspecialService.insertalbum(map);
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("手机中心下发到分平台专题绑定信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int actors(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "actors", "0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					actorsJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "phonespecial", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									actorsJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("手机中心下发到分平台演员信息获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析演员数据信息 保存数据
	private int actorsJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Vodactors map = JSON.parseObject(job.toString(), Vodactors.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("code", map.getCode());
					List<Vodactors> list = vodactorsService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodactorsService.insert(map);
					}
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("手机中心下发到分平台演员信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int corner(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "corner", "0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					cornerJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "phonespecial", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									cornerJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("手机中心下发到分平台角标获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析角标数据信息 保存数据
	private int cornerJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					VodCorner map = JSON.parseObject(job.toString(), VodCorner.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("id", map.getId());
					List<VodCorner> list = vodCornerService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodCornerService.insertjson(map);
					}
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("手机中心下发到分平台角标信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int collectfeesbag(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "collectfeesbag",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					collectfeesbagJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "phonespecial", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									collectfeesbagJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("手机中心下发到分平台付费包获取失败"+map);
			return 0;
		}
		return 1;
	}

	// 解析付费包数据信息 保存数据
	private int collectfeesbagJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					VodCollectfeesbag map = JSON.parseObject(job.toString(), VodCollectfeesbag.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("id", map.getId());
					List<VodCollectfeesbag> list = vodCollectfeesbagService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodCollectfeesbagService.insert(map);
					}
				} catch (Exception e) {
				//e.printStackTrace();
					logger.warn("手机中心下发到分平台付费信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	

	@Override
	public int phonerecommandpic(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "recommandpic",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					recommandpicJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "phonerecommandpic", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									recommandpicJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("手机中心下发到分平台频道轮播图获取失败"+map);
			return 0;
		}
		return 1;
	}
	// 解析频道推荐轮播图数据信息 保存数据
		private int recommandpicJson(JSONArray jsonObj) {
			if (jsonObj.size() > 0) {
				for (int i = 0; i < jsonObj.size(); i++) {
					try {
						JSONObject job = jsonObj.getJSONObject(i);
						RecommandPic map = JSON.parseObject(job.toString(), RecommandPic.class);
						Map<String, Object> queryParams = new HashMap<String, Object>();
						queryParams.put("id", map.getId());
						List<RecommandPic> list = recommandService.find(queryParams);
						if (list.size() < 1) {// 不存在 则添加数据
							recommandService.insertjson(map);
						}
					} catch (Exception e) {
					    e.printStackTrace();
						logger.warn("手机中心下发到分平台频道推荐轮播图信息保存冲突"+jsonObj.getJSONObject(i));
						continue;
					}
				}
			}
			return 1;
		}

		@Override
		public int phoneslideshow(Map<String, Object> map) {
			try {
				String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "recommandpic",
						"0");
				if (!jsonString.equals("") && jsonString != null) {
					JSONObject json = JSONObject.fromObject(jsonString);
					String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
					int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
					if (code.equals("1")) {
						JSONArray jsonObj = json.getJSONArray("data");
						slideshowJson(jsonObj);
						if (numsum > 1) {
							for (int i = 2; i <= numsum; i++) {
								String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
										i, "phoneslideshow", "0");
								if (!nextjsonString.equals("") && nextjsonString != null) {
									JSONObject nextjson = JSONObject.fromObject(nextjsonString);
									String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
									if (nextcode.equals("1")) {
										JSONArray nextjsonObj = nextjson.getJSONArray("data");
										slideshowJson(nextjsonObj);
									} else {
										continue;
									}
								} else {
									continue;
								}
							}
						}
					} else {
						return 1;
					}
				}
			} catch (Exception e) {
				//e.printStackTrace();
				logger.warn("手机中心下发到分平台专区推荐轮播图获取失败"+map);
				return 0;
			}
			return 1;
		}
		// 解析专区推荐轮播图数据信息 保存数据
			private int slideshowJson(JSONArray jsonObj) {
				if (jsonObj.size() > 0) {
					for (int i = 0; i < jsonObj.size(); i++) {
						try {
							JSONObject job = jsonObj.getJSONObject(i);
							Slideshow map = JSON.parseObject(job.toString(), Slideshow.class);
							SlideshowQueryObject queryObject=new SlideshowQueryObject();
							queryObject.setId( map.getId());
							Slideshow slide= slideshowService.queryById(map.getId());
							if (slide==null) {// 不存在 则添加数据
								slideshowService.insertjson(map);
							}
						} catch (Exception e) {
						//e.printStackTrace();
							logger.warn("手机中心下发到分平台频道推荐轮播图信息保存冲突"+jsonObj.getJSONObject(i));
							continue;
						}
					}
				}
				return 1;
			}
			
			@Override
			public int phonestartslideshow(Map<String, Object> map) {
				try {
					String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "recommandpic",
							"0");
					if (!jsonString.equals("") && jsonString != null) {
						JSONObject json = JSONObject.fromObject(jsonString);
						String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
						int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
						if (code.equals("1")) {
							JSONArray jsonObj = json.getJSONArray("data");
							startslideshowJson(jsonObj);
							if (numsum > 1) {
								for (int i = 2; i <= numsum; i++) {
									String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
											i, "phonestartslideshow", "0");
									if (!nextjsonString.equals("") && nextjsonString != null) {
										JSONObject nextjson = JSONObject.fromObject(nextjsonString);
										String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
										if (nextcode.equals("1")) {
											JSONArray nextjsonObj = nextjson.getJSONArray("data");
											startslideshowJson(nextjsonObj);
										} else {
											continue;
										}
									} else {
										continue;
									}
								}
							}
						} else {
							return 1;
						}
					}
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("手机中心下发到分平台开机轮播图获取失败"+map);
					return 0;
				}
				return 1;
			}
			// 解析开机轮播图数据信息 保存数据
						private int startslideshowJson(JSONArray jsonObj) {
							if (jsonObj.size() > 0) {
								for (int i = 0; i < jsonObj.size(); i++) {
									try {
										JSONObject job = jsonObj.getJSONObject(i);
										StartSlideshow map = JSON.parseObject(job.toString(),StartSlideshow.class);
										StartSlideshowQueryObject queryObject=new StartSlideshowQueryObject();
										queryObject.setId( map.getId());
										StartSlideshow startslideshow= startSlideshowService.queryById(map.getId());
										if ( startslideshow==null) {// 不存在 则添加数据
											startSlideshowService.insertjson(map);
										}
									} catch (Exception e) {
									//e.printStackTrace();
										logger.warn("手机中心下发到分平台开机轮播图信息保存冲突"+jsonObj.getJSONObject(i));
										continue;
									}
								}
							}
							return 1;
						}
						
	
	@Override
	public int phonetextrecommendation(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "recommandpic",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					textrecommendationJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "phonetextrecommendation", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									textrecommendationJson(jsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			logger.warn("手机中心下发到分平台文字推荐获取失败"+map);
			return 0;
		}
		return 1;
	}
	// 解析文字推荐数据信息 保存数据
	private int textrecommendationJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Textrecommendation map = JSON.parseObject(job.toString(), Textrecommendation.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("id", map.getId());
					List<Textrecommendation> list = textrecommendationService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						textrecommendationService.insertjson(map);
					}
				} catch (Exception e) {
					logger.warn("手机中心下发到分平台文字推荐信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
		
	}
	
	
	@Override
	public int phonehotseries(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "recommandpic",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					hotseriesJson(jsonObj);
			 }
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("手机中心下发到分平台热播推荐获取失败"+map);
			return 0;
		}
		return 1;
	}
	// 解析热播推荐数据信息 保存数据
	private int hotseriesJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
		   for (int i = 0; i < jsonObj.size(); i++) {
					try {
					JSONObject job = jsonObj.getJSONObject(i);
					Map<String,Object> map = JSON.parseObject(job.toString(), Map.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("id",map.get("id"));
					List<Map<String,Object>> list = vodHotseriesService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodHotseriesService.insertjson(map);
						hotseriesalbumJson(map);
					}
				} catch (Exception e) {
					logger.warn("手机中心下发到分平台热播推荐信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
		
	}
	// 解析热播推荐数据信息 保存数据
		private int hotseriesalbumJson(Map map) {
			try {
				String hot_id=map.get("id").toString();
				String jsonString = importurl(vodcentral.hotseriesalbum,"",1,"hotseriesalbumJson",
						hot_id);
				if (!jsonString.equals("") && jsonString != null) {
					JSONObject json = JSONObject.fromObject(jsonString);
					String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
					if (code.equals("1")) {
						JSONArray jsonObj = json.getJSONArray("data");
						hotseriesvideoJson(jsonObj);
						if(jsonObj.size()>1){
							String nextjsonString =  importurl(vodcentral.hotseriesalbum,"",1,"hotseriesalbumJson",
									hot_id);
							for (int i = 2; i <= jsonObj.size(); i++) {
								if (!nextjsonString.equals("") && nextjsonString != null) {
									JSONObject nextjson = JSONObject.fromObject(nextjsonString);
									String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
									if (nextcode.equals("1")) {
										JSONArray nextjsonObj = nextjson.getJSONArray("data");
										hotseriesvideoJson(nextjsonObj);
									} else {
										continue;
									}
								} else {
									continue;
								}
							}
						}

					} else {
						return 1;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.warn("手机中心下发到分平台热播推荐获取失败"+map);
				return 0;
			}
			
			
			
			return 1;
			
		}
		//解析热播下绑定的专辑信息并保存
		private int hotseriesvideoJson(JSONArray jsonObj){
             if (jsonObj.size()>0) {
				
				for (int i = 0; i < jsonObj.size(); i++) {
					try {
						JSONObject job = jsonObj.getJSONObject(i);
						Map<String,Object> map = JSON.parseObject(job.toString(), Map.class);
						Map<String, Object> queryParams = new HashMap<String, Object>();
						queryParams.put("hot_id",map.get("hot_id"));
						queryParams.put("seriesCode",map.get("seriesCode"));
						List<Map<String,Object>> list = vodHotseriesService.pagealbum(queryParams);
						if (list.size() < 1) {// 不存在 则添加数据
							vodHotseriesService.addalbuminfo(map);
						}
					} catch (Exception e) {
						logger.warn("手机中心下发到分平台热播绑定的专辑信息保存冲突"+jsonObj.getJSONObject(i));
						continue;
					}
				}
			}
			
			
			return 0;
			
		}
		
		
		
	// 根据接口获取数据 并返回
	public String importurl(String urlpath, String curtime, int limit, String type, String id) {
		String requestUrl = urlpath + curtime + "&limit=" + limit;
		if (type.equals("phonespecialalbum") || type.equals("tvspecialalbum")) {
			requestUrl += "&special_id=" + id;
		} else if (type.equals("phonealbuminfovideo") || type.equals("tvalbuminfovideo")) {
			requestUrl += "&seriesCode=" + id;
		} else if (type.equals("phonechannelalbum") || type.equals("tvchannelalbum")) {
			requestUrl += "&channelCode=" + id;
		}else if(type.equals("phonealbuminfo")){
			requestUrl += "&ids=" + id;
		}else if(type.equals("labelchannel")){
			requestUrl += "&label=" + id;
		}else if(type.equals("hotseriesalbumJson")){
			requestUrl += "&hot_id=" + id;
		}else if(type.equals("cpsourcetype")){
			requestUrl+="&id="+id;
		}
		else{
			
		}
		String requestMethod = "GET";
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = buffer.toString();

		} catch (ConnectException ce) {
			ce.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}
		}
		return jsonObject;
	}

	// ----------------------------------------------------------------------------------------------------------------
	// tv数据处理

	@Override
	public int tvalbuminfo(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "tvalbuminfo",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					tvalbuminfoJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "tvalbuminfo", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									tvalbuminfoJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
			//tv自动下发到注入平台
			injectTvOutPut.tvInject();
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("tv中心下发到分平台专辑信息获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析tv专辑数据信息 保存数据
	private int tvalbuminfoJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					VodTvAlbuminfo map = JSON.parseObject(job.toString(), VodTvAlbuminfo.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("seriesCode", map.getSeriesCode());
					List<VodTvAlbuminfo> list = vodTvAlbuminfoService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodTvAlbuminfoService.insertjson(map);
					} else {
						vodTvAlbuminfoService.update(map);
					}
					tvalbuminfovideo(queryParams);
					tvalbuminforecommend(queryParams);
				} catch (Exception e) {
					//e.printStackTrace();
					logger.warn("tv中心下发到分平台专辑信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int tvalbuminfovideo(Map<String, Object> map) {
		try {
			String jsonString = importurl(vodcentral.tv_albumvideo, "", 1, "tvalbuminfovideo",
					map.get("seriesCode").toString());
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					tvabuminfovideoJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(vodcentral.tv_albumvideo, "", i, "tvalbuminfovideo",
									map.get("seriesCode").toString());
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									tvabuminfovideoJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
		//	e.printStackTrace();
			logger.warn("tv中心下发到分平台剧集获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析tv专辑剧集数据信息 保存数据
	private int tvabuminfovideoJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Map<String, Object> map = (Map<String, Object>) JSON.parse(job.toString());
				/*	Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("seriesCode", map.get("seriesCode"));
					queryParams.put("drama", map.get("drama"));
					queryParams.put("dramasequence", map.get("dramasequence"));
					List<Map<String, Object>> list = vodTvAlbuminfoService.findAlbumsinfovideo(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
*/						vodTvAlbuminfoService.insertvideojson(map);
					/*}*/
				} catch (Exception e) {
					//e.printStackTrace();
					logger.info("数据已存在"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int tvalbuminforecommend(Map<String, Object> map) {
		try {
			String jsonString = importurl(vodcentral.tv_albumrecommend, "", 1, "phonealbuminfovideo",
					map.get("seriesCode").toString());
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					Map<String, Object> queryParams = new HashMap<>();
					queryParams.put("seriesCode", map.get("seriesCode").toString());
					vodTvAlbuminfoService.deletealbum(queryParams);
					tvalbuminforecommendJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(vodcentral.tv_albumrecommend, "", i,
									"phonealbuminfovideo", map.get("seriesCode").toString());
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									tvalbuminforecommendJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("tv中心下发到分平台获取专辑关联推荐错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析tv专辑关联推荐专辑数据信息 保存数据
	private int tvalbuminforecommendJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Map<String, Object> map = (Map<String, Object>) JSON.parse(job.toString());
					vodTvAlbuminfoService.addalbum(map);
				} catch (Exception e) {
				//	e.printStackTrace();
					logger.warn("tv中心下发到分平台专辑关联推荐信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int tvchannel(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "tvchannel",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					tvchannelJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "tvchannel", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									tvchannelJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
		//	e.printStackTrace();
			logger.warn("tv中心下发到分平台频道获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析tv频道数据信息 保存数据
	private int tvchannelJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					VodTvchannel map = JSON.parseObject(job.toString(), VodTvchannel.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("channelCode", map.getChannelCode());
					List<VodTvchannel> list = vodtvchannelService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodtvchannelService.insertjson(map);
						tvchannelalbum(map);
					}
				} catch (Exception e) {
				//	e.printStackTrace();
					logger.warn("tv中心下发到分平台频道信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	// tv频道专辑关联数据
	public int tvchannelalbum(VodTvchannel map) {
		try {
			String jsonString = importurl(vodcentral.tv_channelalbum, "", 1, "tvchannelalbum",
					map.getChannelCode() + "");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					Map<String, Object> queryParams = new HashMap<>();
					queryParams.put("channelCode", map.getChannelCode());
					vodtvchannelService.delChannelAlbum(queryParams);
					tvchannelalbumJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(vodcentral.tv_channelalbum, "", i, "tvchannelalbum",
									map.getChannelCode() + "");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									tvchannelalbumJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("tv中心下发到分平台频道关联专辑信息获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析tv频道专辑关联数据信息 保存数据
	private int tvchannelalbumJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Map<String, Object> map = (Map<String, Object>) JSON.parse(job.toString());
					vodtvchannelService.saveChannelAlbum(map);
				} catch (Exception e) {
				//	e.printStackTrace();
					logger.warn("tv中心下发到分平台频道关联专辑信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int tvlabel(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "tvlabel", "0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					tvlabelJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "tvlabel", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									tvlabelJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("tv中心下发到分平台标签获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析tv标签数据信息 保存数据
	private int tvlabelJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					VodTvlabel map = JSON.parseObject(job.toString(), VodTvlabel.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("id", map.getId());
					List<VodTvlabel> list = vodtvlabelService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodtvlabelService.insertjson(map);
					}
				} catch (Exception e) {
				//e.printStackTrace();
					logger.warn("tv中心下发到分平台标签信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int tvhotsearch(Map<String, Object> map) {

		return 0;
	}

	@Override
	public int tvspecial(Map<String, Object> map) {

		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "tvspecial",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					tvspecialJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "tvspecial", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									tvspecialJson(nextjsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("tv中心下发到分平台专题获取错误"+map);
			return 0;
		}
		return 1;
	}

	// 解析tv专题数据信息 保存数据
	private int tvspecialJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					VodTvSpecial map = JSON.parseObject(job.toString(), VodTvSpecial.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("id", map.getId());
					List<VodTvSpecial> list = vodtvspecialService.find(queryParams);
					if (list.size() < 1) {// 不存在 则添加数据
						vodtvspecialService.insertjson(map);
						String nextjsonString = importurl(vodcentral.tv_specialvideo, "", 1, "tvspecialalbum",
								map.getId() + "");
						if (!nextjsonString.equals("") && nextjsonString != null) {
							JSONObject json = JSONObject.fromObject(nextjsonString);
							String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
							int numsum = json.get("numsum") == null ? 1
									: Integer.parseInt(json.get("numsum").toString());// 数据总页数
							if (code.equals("1")) {
								JSONArray jsonspecialalbum = json.getJSONArray("data");
								Map<String, Object> specialmap=new HashMap<>();
								specialmap.put("special_id", map.getId());
								vodtvspecialService.deletesalbum(specialmap);
								tvjsonspecialalbumJson(jsonspecialalbum);
							}
						}
					}else{
						vodtvspecialService.update(map);
						String nextjsonString = importurl(vodcentral.tv_specialvideo, "", 1, "tvspecialalbum",
								map.getId() + "");
						if (!nextjsonString.equals("") && nextjsonString != null) {
							JSONObject json = JSONObject.fromObject(nextjsonString);
							String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
							int numsum = json.get("numsum") == null ? 1
									: Integer.parseInt(json.get("numsum").toString());// 数据总页数
							if (code.equals("1")) {
								JSONArray jsonspecialalbum = json.getJSONArray("data");
								Map<String, Object> specialmap=new HashMap<>();
								specialmap.put("special_id", map.getId());
								vodtvspecialService.deletesalbum(specialmap);
								tvjsonspecialalbumJson(jsonspecialalbum);
							}
						}
					}
				} catch (Exception e) {
					logger.warn("tv中心下发到分平台专题信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}

	// 解析tv专题绑定数据数据信息 保存数据
	private int tvjsonspecialalbumJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Map<String, Object> map =(Map<String, Object>) JSON.parse(job.toString());
					map.put("id", map.get("special_id"));
					vodtvspecialService.insertalbum(map);
				} catch (Exception e) {
				//e.printStackTrace();
					logger.warn("tv中心下发到分平台专题关联专辑保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}


	private int getlabelchannel(String id ) {
		try {
			String jsonString = importurl(vodcentral.labelchannel, null, 1, "labelchannel",
					id);
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
						for (int i = 0; i < jsonObj.size(); i++) {
							try {
								JSONObject job = jsonObj.getJSONObject(i);
								Map<String,Object> map = JSON.parseObject(job.toString(),Map.class);
								Map<String, Object> queryParams = new HashMap<String, Object>();
								queryParams.put("type", map.get("type"));
								queryParams.put("channel", map.get("channel"));
								queryParams.put("label", map.get("label"));
								int count = vodlabelService.countchannel(queryParams);
								if (count==0) {// 不存在 则添加数据
									vodlabelService.addlabelchannel(map);
									}
							} catch (Exception e) {
								//e.printStackTrace();
								logger.warn("中心下发到分平台标签频道信息保存冲突"+jsonObj.getJSONObject(i));
								continue;
							}
						}
				
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("中心下发到分平台标签频道获取错误");
			return 0;
		}
		return 1;
	}
	private int labelchannelJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					Map<String,Object> map = JSON.parseObject(job.toString(),Map.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("type", map.get("type"));
					queryParams.put("channel", map.get("channel"));
					queryParams.put("label", map.get("label"));
					int count = vodlabelService.countchannel(queryParams);
					if (count==0) {// 不存在 则添加数据
						vodlabelService.addlabelchannel(map);
						}
				} catch (Exception e) {
					logger.warn("tv中心下发到分平台标签频道信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
	}
}
		return 1;
	
}
	@Override
	public int labeltype(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(), map.get("curtime").toString(), 1, "labeltype",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					labeltypeJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(), map.get("curtime").toString(),
									i, "labeltype", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									labeltypeJson(jsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("中心下发到分平台标签分类获取错误"+map);
			return 1;
		}
		
		return 0;
	}
	
	// 解析标签分类数据信息 保存数据
	private int labeltypeJson(JSONArray jsonObj) {
		if (jsonObj.size() > 0) {
			for (int i = 0; i < jsonObj.size(); i++) {
				try {
					JSONObject job = jsonObj.getJSONObject(i);
					VodLabeltype map = JSON.parseObject(job.toString(), VodLabeltype.class);
					Map<String, Object> queryParams = new HashMap<String, Object>();
					queryParams.put("id", map.getId());
					int count = vodlabeltypeService.count(queryParams);
					if (count==0) {// 不存在 则添加数据
						vodlabeltypeService.insertjson(map);
					}
				} catch (Exception e) {
				//e.printStackTrace();
					logger.warn("tv中心下发到分平台标签分类信息保存冲突"+jsonObj.getJSONObject(i));
					continue;
				}
			}
		}
		return 1;
	}
	
	@Override
	public int cpsourcetype(Map<String, Object> map) {
		try {
			String jsonString = importurl(map.get("url").toString(),"", 1, "cpsourcetype",
					"0");
			if (!jsonString.equals("") && jsonString != null) {
				JSONObject json = JSONObject.fromObject(jsonString);
				String code = json.get("code") == null ? "0" : json.get("code").toString();// 是否返回正确数据
				int numsum = json.get("numsum") == null ? 1 : Integer.parseInt(json.get("numsum").toString());// 数据总页数
				if (code.equals("1")) {
					JSONArray jsonObj = json.getJSONArray("data");
					cpsourceJson(jsonObj);
					if (numsum > 1) {
						for (int i = 2; i <= numsum; i++) {
							String nextjsonString = importurl(map.get("url").toString(),"",
									i, "cpsourcetype", "0");
							if (!nextjsonString.equals("") && nextjsonString != null) {
								JSONObject nextjson = JSONObject.fromObject(nextjsonString);
								String nextcode = nextjson.get("code") == null ? "0" : nextjson.get("code").toString();// 是否返回正确数据
								if (nextcode.equals("1")) {
									JSONArray nextjsonObj = nextjson.getJSONArray("data");
									cpsourceJson(jsonObj);
								} else {
									continue;
								}
							} else {
								continue;
							}
						}
					}
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.warn("中心下发到分平台cp源获取错误"+map);
			return 1;
		}
		return 0;		
	}
	// 解析cp源数据信息 保存数据
		private int cpsourceJson(JSONArray jsonObj) {
			if (jsonObj.size() > 0) {
				for (int i = 0; i < jsonObj.size(); i++) {
					try {
						JSONObject job = jsonObj.getJSONObject(i);
						Cpsource map = JSON.parseObject(job.toString(), Cpsource.class);
						Map<String, Object> queryParams = new HashMap<String, Object>();
						queryParams.put("code", map.getCode());
						int count = cpsourceService.count(queryParams);
						if (count==0) {// 不存在 则添加数据
							cpsourceService.insert(map);
						}
					} catch (Exception e) {
					//e.printStackTrace();
						logger.warn("中心下发到分平台cp源信息保存冲突"+jsonObj.getJSONObject(i));
						continue;
					}
				}
			}
			return 1;
		}

		


}
