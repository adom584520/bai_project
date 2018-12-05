package com.pbtd.manager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pbtd.manager.conf.ConstantBeanConfig;
import com.pbtd.manager.domain.Albuminfo;
import com.pbtd.manager.domain.BindingInfo;
import com.pbtd.manager.domain.CpAlbuminfo;
import com.pbtd.manager.domain.CpAlbuminfoJoin;
import com.pbtd.manager.domain.CpChannel;
import com.pbtd.manager.domain.CpInfo;
import com.pbtd.manager.domain.CpLiveFavorite;
import com.pbtd.manager.domain.CpVodFavorite;
import com.pbtd.manager.domain.CpVodPlayHistory;
import com.pbtd.manager.mapper.CpAlbuminfoJoinMapper;
import com.pbtd.manager.query.CpPlayHistoryQueryObject;
import com.pbtd.manager.service.AlbuminfoService;
import com.pbtd.manager.service.BindingInfoService;
import com.pbtd.manager.service.CpAlbuminfoService;
import com.pbtd.manager.service.CpChannelService;
import com.pbtd.manager.service.CpInfoService;
import com.pbtd.manager.service.CpLiveFavoriteService;
import com.pbtd.manager.service.CpVodFavoriteService;
import com.pbtd.manager.service.CpVodPlayHistoryService;
import com.pbtd.manager.service.UniformInterfaceService;
import com.pbtd.manager.util.AlbuminfoConstant;
import com.pbtd.manager.util.MessageGetUtil;
import com.pbtd.manager.util.MyStringUtil;
@Service
public class UniformInterfaceServiceImpl implements UniformInterfaceService{
	private static final Logger LOGGER = LoggerFactory.getLogger(UniformInterfaceServiceImpl.class);
	@Autowired
	private CpInfoService cpInfoService;
	@Autowired
	private CpAlbuminfoJoinMapper cajMapper;
	@Autowired
	private CpAlbuminfoService cpAlbuminfoService;
	@Autowired
	private CpChannelService cpChannelService;
	@Autowired
	private CpVodPlayHistoryService cpVodPlayHistoryService;
	@Autowired
	private BindingInfoService bindingInfoService;
	@Autowired
	private CpLiveFavoriteService cpLiveFavoriteService;
	@Autowired
	private CpVodFavoriteService cpVodFavoriteService;
	@Autowired
	private ConstantBeanConfig constant;
	@Autowired
	private AlbuminfoService albuminfoService;

	@Override
	public String validataAlbum(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		String cpCode = jsonObject.getString("cpCode");
		Long seriesCode = jsonObject.getLong("seriesCode");
		Integer drama = jsonObject.getInteger("drama");
		Integer deviceType = jsonObject.getInteger("deviceType");
		//是否存在该CP方
		CpInfo cpInfo = cpInfoService.queryByCpCode(cpCode);
		if (constant.cpCode.equals(cpCode)) {
			//自己albuminfo中是否存在
			Albuminfo albuminfo = albuminfoService.queryBySeriesCode(seriesCode);
			
			deviceType = deviceType == 1 || deviceType == 2 ? 1 : deviceType;
			//判断被推送方  是否媒资上线了
			//判断是否 1,2,3状态   是，可上线
			if (deviceType.equals(albuminfo.getDeviceType())
					|| AlbuminfoConstant.ALBUMINFO_DEVICE_TYPE_ALL.equals(albuminfo.getDeviceType())) {
				json.put("code", 1);
				json.put("message", "查询成功！");
				HashMap<String, Object> data = new HashMap<>();
				data.put("cpSeriesCode", seriesCode);
				data.put("cpDrama", drama);
				json.put("data", data);
				return JSON.toJSONString(json);
			} else {
				json.put("code", 0);
				json.put("message", MessageGetUtil.message.get(cpCode));
				return JSON.toJSONString(json);
			}
		} else {
			// 判断拥有该cp方的版权什么类型版权
			//默认按照其他app手机、tv同时上下线
			if (AlbuminfoConstant.CP_INFO_STATUS_ALL.equals(cpInfo.getStatus())
					|| AlbuminfoConstant.CP_INFO_STATUS_VOD.equals(cpInfo.getStatus())) {
				//其他CP中的媒资
				CpAlbuminfo ca = cpAlbuminfoService.queryBySeriesCode(seriesCode, cpCode);
				if (ca != null) {
					//媒资是否上线
					if (AlbuminfoConstant.ALBUMINFO_STATUS_UPLINE.equals(ca.getStatus())) {
						// 查询对应剧集
						CpAlbuminfoJoin caj = cajMapper.queryBySeriesCodeDrama(seriesCode, drama, cpCode);
						if (caj != null) {
							HashMap<String, Object> data = new HashMap<>();
							json.put("code", 1);
							json.put("message", "查询成功！");
							json.put("data", data);
							data.put("cpSeriesCode", caj.getCpSeriesCode());
							data.put("cpDrama", caj.getCpDrama());
							return JSON.toJSONString(json);
						} else {
							LOGGER.info("无绑定关系！");
						}
					} else {
						LOGGER.info("cp方该专辑已下线！");
					}
				} else {
					LOGGER.info("cp方无该专辑！");
				}
			} else {
				LOGGER.info("cp方版权关闭！");
			}
		}
		json.put("message", MessageGetUtil.message.get(cpCode));
		json.put("code", 0);
		return JSON.toJSONString(json);
	}

	@Override
	public String validataChannel(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		String cpCode = jsonObject.getString("cpCode");
		String chnCode = jsonObject.getString("channelCode");
		CpInfo cpInfo = cpInfoService.queryByCpCode(cpCode);
		if (constant.cpCode.equals(cpCode)) {
			json.put("code", 1);
			json.put("message", "查询成功！");
			HashMap<String, Object> data = new HashMap<>();
			data.put("cpChannelCode", chnCode);
			json.put("data", data);
			return JSON.toJSONString(json);
		}
		// 判断是否拥有该cp方的版权
		if (AlbuminfoConstant.CP_INFO_STATUS_ALL.equals(cpInfo.getStatus())
				|| AlbuminfoConstant.CP_INFO_STATUS_LIVE.equals(cpInfo.getStatus())) {
			CpChannel cc = cpChannelService.queryByChnCode(chnCode, cpCode);
			if (cc != null) {
				if (AlbuminfoConstant.ALBUMINFO_STATUS_UPLINE.equals(cc.getStatus())) {
					HashMap<String, Object> data = new HashMap<>();
					json.put("code", 1);
					json.put("message", "查询成功！");
					json.put("data", data);
					data.put("cpChannelCode", cc.getCpChnCode());
					return JSON.toJSONString(json);
				} else {
					json.put("message", "无版权！");
				}
			} else {
				json.put("message", "无版权！");
			}
		} else {
			json.put("message", "无版权");
		}
		json.put("code", 0);
		return JSON.toJSONString(json);
	}

	@Override
	@Transactional
	public String initVodPlayHistorys(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		Integer deviceType = jsonObject.getInteger("deviceType");
		String deviceId = jsonObject.getString("deviceId");
		BindingInfo bd = null;
		if (AlbuminfoConstant.DEVICE_TYPE_ANDROID.equals(deviceType)
				|| AlbuminfoConstant.DEVICE_TYPE_IOS.equals(deviceType)) {
			bd = bindingInfoService.queryByPhoneId(deviceId);
		} else if (AlbuminfoConstant.DEVICE_TYPE_TV.equals(deviceType)) {
			bd = bindingInfoService.queryByTvId(deviceId);
		} else {
			json.put("message", "设备类型错误！");
		}
		if (bd != null) {
			cpVodPlayHistoryService.deleteByDeviceId(deviceId);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			if (jsonArray != null) {
				List<CpVodPlayHistory> data = jsonArray.toJavaList(CpVodPlayHistory.class);
				if (data != null && data.size() > 0) {
					if (data.size() > constant.total) {
						for (int i = data.size(); i >= constant.total; i--) {
							data.remove(i);
						}
					}
					cpVodPlayHistoryService.insertBatch(data, deviceType, deviceId, bd.getCpId());

				}
				json.put("code", 1);
				json.put("message", "初始化成功！");
				return JSON.toJSONString(json);
			} else {
				json.put("message", "参数错误！");
			}
		} else {
			json.put("message", "设备未绑定！");
		}
		json.put("code", 0);
		return JSON.toJSONString(json);
	}

	@Override
	@Transactional
	public String initLiveFavorites(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		Integer deviceType = jsonObject.getInteger("deviceType");
		String deviceId = jsonObject.getString("deviceId");
		BindingInfo bd = null;
		if (AlbuminfoConstant.DEVICE_TYPE_ANDROID.equals(deviceType)
				|| AlbuminfoConstant.DEVICE_TYPE_IOS.equals(deviceType)) {
			bd = bindingInfoService.queryByPhoneId(deviceId);
		} else if (AlbuminfoConstant.DEVICE_TYPE_TV.equals(deviceType)) {
			bd = bindingInfoService.queryByTvId(deviceId);
		} else {
			json.put("message", "设备类型错误！");
		}
		if (bd != null) {
			cpLiveFavoriteService.deleteByDeviceId(deviceId);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			if (jsonArray != null) {
				List<CpLiveFavorite> data = jsonArray.toJavaList(CpLiveFavorite.class);
				if (data != null && data.size() > 0) {
					if (data.size() > constant.total) {
						for (int i = data.size(); i >= constant.total; i--) {
							data.remove(i);
						}
					}
					cpLiveFavoriteService.insertBatch(data, deviceType, deviceId, bd.getCpId());

				}
				json.put("code", 1);
				json.put("message", "初始化成功！");
				return JSON.toJSONString(json);
			} else {
				json.put("message", "参数错误！");
			}
		} else {
			json.put("message", "设备未绑定！");
		}
		json.put("code", 0);
		return JSON.toJSONString(json);
	}

	@Override
	@Transactional
	public String initVodFavorites(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		Integer deviceType = jsonObject.getInteger("deviceType");
		String deviceId = jsonObject.getString("deviceId");
		BindingInfo bd = null;
		if (AlbuminfoConstant.DEVICE_TYPE_ANDROID.equals(deviceType)
				|| AlbuminfoConstant.DEVICE_TYPE_IOS.equals(deviceType)) {
			bd = bindingInfoService.queryByPhoneId(deviceId);
		} else if (AlbuminfoConstant.DEVICE_TYPE_TV.equals(deviceType)) {
			bd = bindingInfoService.queryByTvId(deviceId);
		} else {
			json.put("message", "设备类型错误！");
		}
		if (bd != null) {
			cpVodFavoriteService.deleteByDeviceId(deviceId);
			JSONArray jsonArray = jsonObject.getJSONArray("list");
			if (jsonArray != null) {
				List<CpVodFavorite> data = jsonArray.toJavaList(CpVodFavorite.class);
				if (data != null && data.size() > 0) {
					if (data.size() > constant.total) {
						for (int i = data.size(); i >= constant.total; i--) {
							data.remove(i);
						}
					}
					cpVodFavoriteService.insertBatch(data, deviceType, deviceId, bd.getCpId());

				}
				json.put("code", 1);
				json.put("message", "初始化成功！");
				return JSON.toJSONString(json);
			} else {
				json.put("message", "参数错误！");
			}
		} else {
			json.put("message", "设备设备未绑定！");
		}
		json.put("code", 0);
		return JSON.toJSONString(json);
	}

	@Override
	@Transactional
	public String uploadVodPlayHistorys(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		String deviceId = jsonObject.getString("deviceId");
		Integer deviceType = jsonObject.getInteger("deviceType");
		String cpCode = jsonObject.getString("cpCode");
		CpVodPlayHistory cvph = jsonObject.getObject("playHistory", CpVodPlayHistory.class);
		long row  = cpVodPlayHistoryService.queryCountByDeviceIdSeriesCode(deviceId,cvph.getSeriesCode());
		if(row==1){
			LOGGER.info("uploadVodPlayHistorys查询播放记录："+row);
			cvph.setDeviceId(deviceId);
			cvph.setUpdateTime(new Date());
			cpVodPlayHistoryService.update(cvph);
		}else{
			if(row>1){
				cpVodPlayHistoryService.deleteBySeriesCode(deviceId, cvph.getSeriesCode());
			}
			cvph.setCpCode(cpCode);
			cvph.setDeviceId(deviceId);
			cvph.setDeviceType(deviceType);
			cpVodPlayHistoryService.insert(cvph);
			// 当数据超过配置的条数，将数据库最后一条数据创建时间之前的数据删除
			long count = cpVodPlayHistoryService.queryContByDeviceId(deviceId);
			if (count > constant.total) {
				CpPlayHistoryQueryObject qo = new CpPlayHistoryQueryObject();
				qo.setPage(2);
				qo.setRows(constant.total);
				qo.setDeviceId(deviceId);
				List<CpVodPlayHistory> cvphList = cpVodPlayHistoryService.queryExcessList(qo);
				List<Long> ids = new ArrayList<>();
				for (int i = 0; i < cvphList.size(); i++) {
					ids.add(cvphList.get(i).getId());
				}
				cpVodPlayHistoryService.deleteBatch(ids);
			}
		}
		json.put("code", 1);
		json.put("message", "添加成功！");
		return JSON.toJSONString(json);
	}

	@Override
	public String getVodPlayHistorys(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		Integer deviceType = jsonObject.getInteger("deviceType");
		String deviceId = jsonObject.getString("deviceId");
		CpPlayHistoryQueryObject qo = new CpPlayHistoryQueryObject();
		qo.setPage(1);
		qo.setRows(constant.total);
		if (AlbuminfoConstant.DEVICE_TYPE_ANDROID.equals(deviceType)
				|| AlbuminfoConstant.DEVICE_TYPE_IOS.equals(deviceType)) {
			BindingInfo bindingInfo = bindingInfoService.queryByTvId(deviceId);
			if (bindingInfo == null) {
				json.put("code", 0);
				json.put("message", "设备设备未绑定！");
				return JSON.toJSONString(json);
			}
			qo.setDeviceId(bindingInfo.getPUserId());
		} else if (AlbuminfoConstant.DEVICE_TYPE_TV.equals(deviceType)) {
			BindingInfo bindingInfo = bindingInfoService.queryByPhoneId(deviceId);
			if (bindingInfo == null) {
				json.put("code", 0);
				json.put("message", "设备设备未绑定！");
				return JSON.toJSONString(json);
			}
			qo.setDeviceId(bindingInfo.getTvUserId());
		} else {
			json.put("code", 0);
			json.put("message", "设备类型错误！");
			return JSON.toJSONString(json);
		}
		List<CpVodPlayHistory> data = cpVodPlayHistoryService.queryExcessList(qo);
		if (data == null || data.size() < 1) {
			json.put("code", 0);
			json.put("message", "无数据！");
			return JSON.toJSONString(json);
		}
		json.put("code", 1);
		json.put("message", "查询成功！");
		json.put("data", data);
		return JSON.toJSONString(json);
	}

	@Override
	@Transactional
	public String uploadLiveFavorites(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		String deviceId = jsonObject.getString("deviceId");
		Integer deviceType = jsonObject.getInteger("deviceType");
		String cpCode = jsonObject.getString("cpCode");
		CpLiveFavorite clf = jsonObject.getObject("favorite", CpLiveFavorite.class);
		CpLiveFavorite clf2 = cpLiveFavoriteService.queryByChnCode(deviceId, clf.getChnCode());
		if (clf2 != null) {
			clf.setDeviceId(deviceId);
			cpLiveFavoriteService.update(clf);
		} else {
			clf.setCpCode(cpCode);
			clf.setDeviceId(deviceId);
			clf.setDeviceType(deviceType);
			cpLiveFavoriteService.insert(clf);
			long count = cpLiveFavoriteService.queryContByDeviceId(deviceId);
			if (count > constant.total) {
				CpPlayHistoryQueryObject qo = new CpPlayHistoryQueryObject();
				qo.setPage(2);
				qo.setRows(constant.total);
				qo.setDeviceId(deviceId);
				List<CpLiveFavorite> cvphList = cpLiveFavoriteService.queryExcessList(qo);
				List<Long> ids = new ArrayList<>();
				for (int i = 0; i < cvphList.size(); i++) {
					ids.add(cvphList.get(i).getId());
				}
				cpLiveFavoriteService.deleteBatch(ids);
			}
		}
		json.put("code", 1);
		json.put("message", "添加成功！");
		return JSON.toJSONString(json);
	}

	@Override
	@Transactional
	public String uploadVodFavorites(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		String deviceId = jsonObject.getString("deviceId");
		Integer deviceType = jsonObject.getInteger("deviceType");
		String cpCode = jsonObject.getString("cpCode");
		CpVodFavorite cvf = jsonObject.getObject("favorite", CpVodFavorite.class);
		long row  = cpVodFavoriteService.queryCountByDeviceIdSeriesCode(deviceId,cvf.getSeriesCode());
		if(row==1){
			cvf.setDeviceId(deviceId);
			cpVodFavoriteService.update(cvf);
		}else{
			if(row>1){
				cpVodFavoriteService.deleteBySeriesCode(deviceId, cvf.getSeriesCode());
			}
			cvf.setCpCode(cpCode);
			cvf.setDeviceId(deviceId);
			cvf.setDeviceType(deviceType);
			cpVodFavoriteService.insert(cvf);
			long count = cpVodFavoriteService.queryContByDeviceId(deviceId);
			if (count > constant.total) {
				CpPlayHistoryQueryObject qo = new CpPlayHistoryQueryObject();
				qo.setPage(2);
				qo.setRows(constant.total);
				qo.setDeviceId(deviceId);
				List<CpVodFavorite> cvphList = cpVodFavoriteService.queryExcessList(qo);
				List<Long> ids = new ArrayList<>();
				for (int i = 0; i < cvphList.size(); i++) {
					ids.add(cvphList.get(i).getId());
				}
				cpVodFavoriteService.deleteBatch(ids);
			}
		}
		json.put("code", 1);
		json.put("message", "添加成功！");
		return JSON.toJSONString(json);
	}

	@Override
	public String getLiveFavorites(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		Integer deviceType = jsonObject.getInteger("deviceType");
		String deviceId = jsonObject.getString("deviceId");
		CpPlayHistoryQueryObject qo = new CpPlayHistoryQueryObject();
		qo.setPage(1);
		qo.setRows(constant.total);
		if (AlbuminfoConstant.DEVICE_TYPE_ANDROID.equals(deviceType)
				|| AlbuminfoConstant.DEVICE_TYPE_IOS.equals(deviceType)) {
			BindingInfo bindingInfo = bindingInfoService.queryByTvId(deviceId);
			if (bindingInfo == null) {
				json.put("code", 0);
				json.put("message", "设备未绑定！");
				return JSON.toJSONString(json);
			}
			qo.setDeviceId(bindingInfo.getPUserId());
		} else if (AlbuminfoConstant.DEVICE_TYPE_TV.equals(deviceType)) {
			BindingInfo bindingInfo = bindingInfoService.queryByPhoneId(deviceId);
			if (bindingInfo == null) {
				json.put("code", 0);
				json.put("message", "设备未绑定！");
				return JSON.toJSONString(json);
			}
			qo.setDeviceId(bindingInfo.getTvUserId());
		} else {
			json.put("code", 0);
			json.put("message", "设备类型错误！");
			return JSON.toJSONString(json);
		}
		List<CpLiveFavorite> data = cpLiveFavoriteService.queryExcessList(qo);
		if (data == null || data.size() < 1) {
			json.put("code", 0);
			json.put("message", "无数据！");
			return JSON.toJSONString(json);
		}
		json.put("code", 1);
		json.put("message", "查询成功！");
		json.put("data", data);
		return JSON.toJSONString(json);
	}

	@Override
	public String getVodFavorites(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		Integer deviceType = jsonObject.getInteger("deviceType");
		String deviceId = jsonObject.getString("deviceId");
		CpPlayHistoryQueryObject qo = new CpPlayHistoryQueryObject();
		qo.setPage(1);
		qo.setRows(constant.total);
		if (AlbuminfoConstant.DEVICE_TYPE_ANDROID.equals(deviceType)
				|| AlbuminfoConstant.DEVICE_TYPE_IOS.equals(deviceType)) {
			BindingInfo bindingInfo = bindingInfoService.queryByTvId(deviceId);
			if (bindingInfo == null) {
				json.put("code", 0);
				json.put("message", "设备未绑定！");
				return JSON.toJSONString(json);
			}
			qo.setDeviceId(bindingInfo.getPUserId());
		} else if (AlbuminfoConstant.DEVICE_TYPE_TV.equals(deviceType)) {
			BindingInfo bindingInfo = bindingInfoService.queryByPhoneId(deviceId);
			if (bindingInfo == null) {
				json.put("code", 0);
				json.put("message", "设备未绑定！");
				return JSON.toJSONString(json);
			}
			qo.setDeviceId(bindingInfo.getTvUserId());
		} else {
			json.put("code", 0);
			json.put("message", "设备类型错误！");
			return JSON.toJSONString(json);
		}
		List<CpVodFavorite> data = cpVodFavoriteService.queryExcessList(qo);
		if (data == null || data.size() < 1) {
			json.put("code", 0);
			json.put("message", "无数据！");
			return JSON.toJSONString(json);
		}
		json.put("code", 1);
		json.put("message", "查询成功！");
		json.put("data", data);
		return JSON.toJSONString(json);
	}

	public String deleteVodFavorites(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		String deviceId = jsonObject.getString("deviceId");
		String seriesCode = jsonObject.getString("seriesCode");
		if (seriesCode != null && seriesCode.length() > 0) {
			if (seriesCode.indexOf(",") == -1) {
				cpVodFavoriteService.deleteBySeriesCode(deviceId, seriesCode);
			} else {
				List<String> list = new ArrayList<>();
				MyStringUtil.stringToList(seriesCode, list);
				cpVodFavoriteService.deleteBatchBySeriesCode(deviceId, list);
			}
		} else {
			cpVodFavoriteService.deleteByDeviceId(deviceId);
		}
		json.put("message", "删除成功！");
		json.put("code", 1);
		return JSON.toJSONString(json);
	}

	/**
	 * 单个、多个、全部删除 
	 */
	public String deleteVodPlayHistorys(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		String deviceId = jsonObject.getString("deviceId");
		String seriesCode = jsonObject.getString("seriesCode");
		if (seriesCode != null && seriesCode.length() > 0) {
			if (seriesCode.indexOf(",") == -1) {
				cpVodPlayHistoryService.deleteBySeriesCode(deviceId, seriesCode);
			} else {
				List<String> list = new ArrayList<>();
				MyStringUtil.stringToList(seriesCode, list);
				cpVodPlayHistoryService.deleteBatchBySeriesCode(deviceId, list);
			}
		} else {
			cpVodPlayHistoryService.deleteByDeviceId(deviceId);
		}
		json.put("code", 1);
		json.put("message", "删除成功！");
		return JSON.toJSONString(json);
	}

	public String deleteLiveFavorites(JSONObject jsonObject) {
		HashMap<String, Object> json = new HashMap<>();
		String deviceId = jsonObject.getString("deviceId");
		String channelCode = jsonObject.getString("channelCode");
		if (channelCode != null && channelCode.length() > 0) {
			if (channelCode.indexOf(",") == -1) {
				cpLiveFavoriteService.deleteByChnCode(deviceId, channelCode);
			} else {
				List<String> list = new ArrayList<>();
				MyStringUtil.stringToList(channelCode, list);
				cpLiveFavoriteService.deleteBatchByChnCode(deviceId, list);
			}
		} else {
			cpLiveFavoriteService.deleteByDeviceId(deviceId);
		}
		json.put("code", 1);
		json.put("message", "删除成功！");
		return JSON.toJSONString(json);
	}
}
