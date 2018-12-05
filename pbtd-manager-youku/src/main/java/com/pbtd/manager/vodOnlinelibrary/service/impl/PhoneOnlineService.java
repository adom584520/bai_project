package com.pbtd.manager.vodOnlinelibrary.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pbtd.manager.util.HttpClientUtil;
import com.pbtd.manager.vod.page.mapper.ResetMapper;
import com.pbtd.manager.vod.phone.search.controller.SearchPhoneController;
import com.pbtd.manager.vodOnlinelibrary.mapper.PhoneOnlineMapper;
import com.pbtd.manager.vodOnlinelibrary.service.face.IPhoneOnlineService;

import net.sf.json.JSONObject;

@Service
@PropertySource(value = { "classpath:config/chinamobile.properties" })
public class PhoneOnlineService implements IPhoneOnlineService {

	@Autowired
	private PhoneOnlineMapper phoneMapper;
	@Autowired
	private ResetMapper resetmpper;
	@Autowired
	private SearchPhoneController searchphonel;

	// 同步专辑到移动融合路径
	@Value("${resetalbum_url}")
	public String resetalbum_url;

	// 同步剧集到移动融合路径
	@Value("${resetalbumvideo_url}")
	public String resetalbumvideo_url;

	// 根据接口获取数据 并返回
	public String importurl(String urlpath, JSONObject json) {
		String requestUrl = urlpath;
		String requestMethod = "POST";
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

	@Override
	public int resetalbum(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		final List<Map<String, Object>> list = resetmpper.pfindalbum(map);
		new Thread() {
			public void run() {
				try {
					// 同步专辑到移动融合库-手机端
					System.out.println("===============================================================");
					System.out.println("===============================================================");
					HashMap<String, String> params = new HashMap<>();
					params.put("data", JSON.toJSONString(list));
					String doPost = HttpClientUtil.doPost(resetalbum_url, params, "UTF-8");
					System.out.println("===============================================================");
					System.out.println("===============================================================");
				} catch (IllegalArgumentException ec) {
					ec.printStackTrace();
				}
			}
		}.start();
		StringBuffer string = new StringBuffer();
		for (Map<String, Object> map2 : list) {
			map2.put("imgtitle", map.get("imgtitle"));
			string.append(map2.get("seriesCode").toString() + ",");
			phoneMapper.deletealbum(map2);// 删除节目
			System.out.println("1-------");
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) {
				phoneMapper.insertalbum(map2);// 新增节目
				final List<Map<String, Object>> listvideo = resetmpper.pfindvideo(map2);
				new Thread() {
					public void run() {
						System.out.println("1111---------------");
						try {
						// 同步剧集到移动融合库-手机端
							HashMap<String, String> params1 = new HashMap<>();
							params1.put("data", JSON.toJSONString(listvideo));
							System.out.println("移动融合同步剧集接口----------------");
					    	HttpClientUtil.doPost(resetalbumvideo_url, params1, "UTF-8");
						
						} catch (IllegalArgumentException ec) {
							ec.printStackTrace();
						}
					}
				}.start();
				phoneMapper.deletesvideo(map2);// 删除剧集
				for (Map<String, Object> map3 : listvideo) {
					if (map3.get("isShow").toString().equals("1")) {
						try {
							phoneMapper.insertvideo(map3);// 新增剧集
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				}
				List<Map<String, Object>> listrecommend = resetmpper.pfindalbumrecommend(map2);
				phoneMapper.deletealbumrecommend(map2);// 删除关联
				for (Map<String, Object> maprecommend : listrecommend) {
					try {
						phoneMapper.addalbumrecommend(maprecommend);
						// 新增关联
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}

				}
				/*
				 * phoneMapper.updatealbumrecommend(map2);//更新关联推荐中的专辑信息
				 * phoneMapper.updatechannelalbuminfo(map2);//更新二级栏目下绑定的专辑信息
				 * phoneMapper.updatespecialalbuminfo(map2);//更新专题下绑定的专辑信息
				 * phoneMapper.updatehotsearchalbuminfo(map2);//更新热搜下绑定的专辑信息
				 * phoneMapper.updatehotseriesalbuminfo(map2);//更新热播推荐下绑定的专辑信息
				 */ } else {
				phoneMapper.deletesvideo(map2);// 删除剧集
				/*
				 * phoneMapper.deletealbumrecommend(map2);// 删除关联
				 * phoneMapper.updatealbumrecommend(map2);//更新关联推荐中的专辑信息
				 * phoneMapper.updatechannelalbuminfo(map2);//更新二级栏目下绑定的专辑信息
				 * phoneMapper.updatespecialalbuminfo(map2);//更新专题下绑定的专辑信息
				 * phoneMapper.updatehotsearchalbuminfo(map2);//更新热搜下绑定的专辑信息
				 * phoneMapper.updatehotseriesalbuminfo(map2);//更新热播推荐下绑定的专辑信息
				 */
			}
		}

		try {
			// 调用solr
			searchphonel.phoneIndexAdd(string.toString());
			searchphonel.phoneDelRedisKey(string.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int resetchannel(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		List<Map<String, Object>> list = resetmpper.pfindchannel(map);
		for (Map<String, Object> map2 : list) {
			phoneMapper.deleteschannelalbum(map2);// 删除
			phoneMapper.deleteschannel(map2);// 删除
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) {
				try {
					phoneMapper.insertchannel(map2);// 新增
				} catch (Exception e1) {
					e1.printStackTrace();
					continue;
				}
				List<Map<String, Object>> listalbum = resetmpper.pfindchannelalbum(map2);

				for (Map<String, Object> map3 : listalbum) {
					try {
						phoneMapper.insertchannelalbum(map3);// 新增
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
			}
		}
		return 0;
	}

	@Override
	public int resetlabel(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		List<Map<String, Object>> list = resetmpper.pfindlabel(map);
		for (Map<String, Object> map2 : list) {
			phoneMapper.deleteslabel(map2);
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) {
				try {
					phoneMapper.insertlabel(map2);
					List<Map<String, Object>> channellist = resetmpper.pfindlabelchannel(map2);
					phoneMapper.deleteslabelchannel(map2);
					for (Map<String, Object> map3 : channellist) {
						try {
							phoneMapper.insertlabelchannel(map3);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			} else {
				phoneMapper.deleteslabelchannel(map2);
			}

		}
		return 0;
	}

	@Override
	public int resetspecial(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		List<Map<String, Object>> list = resetmpper.pfindspecial(map);
		if (list.size() > 0) {
			for (Map<String, Object> map2 : list) {
				phoneMapper.deletesspecial(map2);
				if (map2.get("status").toString().equals("1")) {
					try {
						phoneMapper.insertspecial(map2);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
					List<Map<String, Object>> listalbum = resetmpper.pfindspecialalbum(map2);
					phoneMapper.deletesspecialalbum(map2);
					for (Map<String, Object> map3 : listalbum) {
						try {
							phoneMapper.insertspecialalbum(map3);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				} else {
					phoneMapper.deletesspecialalbum(map2);
				}
			}

		}
		return 0;
	}

	@Override
	public int resethotsearch(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		List<Map<String, Object>> list = resetmpper.pfindhotsearch(map);
		if (list.size() > 0) {
			phoneMapper.deleteshotsearch(null);
			for (Map<String, Object> map2 : list) {
				// if (map2.get("status").toString().equals("1")) {
				try {
					phoneMapper.inserthotsearch(map2);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				// }
			}
		}
		return 0;
	}

	@Override
	public int resetrecommandpic(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		List<Map<String, Object>> list = resetmpper.pfindrecommandpic(map);
		if (list.size() > 0) {
			for (Map<String, Object> curmap : list) {
				phoneMapper.deleterecommandpic(curmap);
				if (curmap.get("status").toString().equals("1")) {
					try {
						phoneMapper.insertrecommandpic(curmap);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}

			}
		}
		return 1;
	}

	@Override
	public int resetbottomnavigation(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		List<Map<String, Object>> list = resetmpper.pfindbottomnavigation(map);
		if (list.size() > 0) {
			phoneMapper.deletebottomnavigation(map);
			for (Map<String, Object> curmap : list) {
				try {
					phoneMapper.insertbottomnavigation(curmap);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int resettextrecommendation(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		List<Map<String, Object>> list = resetmpper.pfindtextrecommendation(map);
		phoneMapper.deletetextrecommendation(map);// 删除
		for (Map<String, Object> map2 : list) {
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) {
				try {
					phoneMapper.inserttextrecommendation(map2);// 新增
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return 0;
	}

	@Override
	public int resetlogo(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		List<Map<String, Object>> list = resetmpper.pfindlogo(map);
		if (list.size() > 0) {
			phoneMapper.deletelogo(map);
			for (Map<String, Object> curmap : list) {
				try {
					phoneMapper.insertlogo(curmap);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int resethotseries(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		List<Map<String, Object>> list = resetmpper.pfindhotseries(map);

		if (list.size() > 0) {
			phoneMapper.deletehotseries(map);
			for (Map<String, Object> curmap : list) {
				try {
					phoneMapper.inserthotseries(curmap);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		List<Map<String, Object>> list2 = resetmpper.pfindhotseriesalbum(map);
		if (list2.size() > 0) {
			phoneMapper.deletehotseriesalbum(map);
			for (Map<String, Object> curmap : list2) {
				try {
					phoneMapper.inserthotseriesalbum(curmap);
				} catch (Exception e) {
					continue;
				}
			}
		}
		return 1;
	}

	@Override
	public int resetslideshow(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		List<Map<String, Object>> list = resetmpper.pfindslideshow(map);
		phoneMapper.deleteslideshow(map);// 删除
		for (Map<String, Object> map2 : list) {
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) {
				try {
					phoneMapper.insertslideshow(map2);// 新增
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return 0;
	}

	@Override
	public int resetstartslideshow(Map<String, Object> map) {
		String ids = map.get("ids") == null ? "" : map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curtime = sdf.format(d);
		if (!"".equals(ids)) {
			int index = ids.lastIndexOf(",");
			if (index != -1) {
				ids = ids.substring(0, index);
			}
			map.put("ids", ids);
		} else {
			map.put("updatetime", curtime);
		}
		List<Map<String, Object>> list = resetmpper.pfindstartslideshow(map);
		phoneMapper.deletestartslideshow(map);// 删除
		for (Map<String, Object> map2 : list) {
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) {
				try {
					phoneMapper.insertstartslideshow(map2);// 新增
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return 0;
	}

	@Override
	public int resetmovieurl(Map<String, Object> map) {
		List<Map<String, Object>> list = resetmpper.findmovieurl(map);
		phoneMapper.deletemovieurl(map);// 删除
		for (Map<String, Object> map2 : list) {
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) {
				try {
					phoneMapper.insertmovieurl(map2);// 新增
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return 0;
	}

	@Override
	public int resetlabeltype(Map<String, Object> map) {
		List<Map<String, Object>> list = resetmpper.findlabeltype(map);
		phoneMapper.deletelabeltype(map);// 删除
		for (Map<String, Object> map2 : list) {
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			/* if (status.equals("1")) { */
			try {
				phoneMapper.insertlabeltype(map2);// 新增
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			// }
		}
		return 0;
	}
}
