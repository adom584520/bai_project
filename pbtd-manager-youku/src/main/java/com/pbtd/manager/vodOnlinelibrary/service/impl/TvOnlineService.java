package com.pbtd.manager.vodOnlinelibrary.service.impl;

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
import com.pbtd.manager.vod.tv.search.controller.SearchTvController;
import com.pbtd.manager.vodOnlinelibrary.mapper.TvOnlineMapper;
import com.pbtd.manager.vodOnlinelibrary.service.face.ITvOnlineService;

@Service
@PropertySource(value = { "classpath:config/chinamobile.properties" })
public class TvOnlineService implements ITvOnlineService {

	@Autowired
	private TvOnlineMapper tvMapper;
	@Autowired
	private ResetMapper resetmpper;
	
	@Autowired
	private SearchTvController   searchtv;

	// 同步专辑到移动融合路径
		@Value("${resetTvalbum_url}")
		public String resetTvalbum_url;

		// 同步剧集到移动融合路径
		@Value("${resetalbumvideo_url}")
		public String resetalbumvideo_url;
	
	
	@Override
	public int resetalbum(Map<String, Object> map) {
		String ids=map.get("ids")==null?"":map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  String curtime = sdf.format(d);
		  if(!"".equals(ids)){
			  int index=ids.lastIndexOf(",");
			  if(index!=-1){
				  ids=ids.substring(0, index)+ids.substring(index+1, ids.length());
			  }
			    map.put("ids",ids);
			}else{
			map.put("updatetime", curtime);
			}
		final List<Map<String, Object>> list = resetmpper.tfindalbum(map);
		new Thread() {
			public void run() {
				try {
					// 同步专辑到移动融合库-tv端
					System.out.println("===============================================================");
					System.out.println("===============================================================");
					HashMap<String, String> params = new HashMap<>();
					params.put("data", JSON.toJSONString(list));
					String doPost = HttpClientUtil.doPost(resetTvalbum_url, params, "UTF-8");
					System.out.println("===============================================================");
					System.out.println("===============================================================");
				} catch (IllegalArgumentException ec) {
					ec.printStackTrace();
				}
			}
		}.start();
		StringBuffer string=new  StringBuffer();
		for (Map<String, Object> map2 : list) {
			map2.put("imgtitle", map.get("imgtitle"));
			string.append(map2.get("seriesCode").toString()+",");
			tvMapper.deletealbum(map2);// 删除节目
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) {
				try {
					tvMapper.insertalbum(map2);// 新增节目
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				final List<Map<String, Object>> listvideo = resetmpper.tfindvideo(map2);
				new Thread() {
					public void run() {
						System.out.println("1111---------------");
						try {
						// 同步剧集到移动融合库-TV端
							HashMap<String, String> params1 = new HashMap<>();
							params1.put("data", JSON.toJSONString(listvideo));
							System.out.println("移动融合同步剧集接口----------------");
					    	HttpClientUtil.doPost(resetalbumvideo_url, params1, "UTF-8");
						
						} catch (IllegalArgumentException ec) {
							ec.printStackTrace();
						}
					}
				}.start();
				tvMapper.deletesvideo(map2);// 删除剧集
				for (Map<String, Object> map3 : listvideo) {
					if (map3.get("isShow").toString().equals("1")) {
						try {
							tvMapper.insertvideo(map3);// 新增剧集
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				}
				List<Map<String, Object>> listrecommend = resetmpper.tfindalbumrecommend(map2);
				tvMapper.deletealbumrecommend(map2);// 删除关联
				for (Map<String, Object> maprecommend : listrecommend) {
					try {
						tvMapper.addalbumrecommend(maprecommend);;// 新增关联
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}

				}
		/*		tvMapper.updatealbumrecommend(map2);//更新关联推荐中的专辑信息
				tvMapper.updatechannelalbuminfo(map2);//更新二级栏目中的专辑信息
				tvMapper.updatespecialalbuminfo(map2);//更新专题下的专辑信息
*/			}else{
				tvMapper.deletesvideo(map2);// 删除剧集
				/*tvMapper.deletealbumrecommend(map2);// 删除关联
				tvMapper.updatechannelalbuminfo(map2);//更新二级栏目中的专辑信息
				tvMapper.updatespecialalbuminfo(map2);//更新专题下的专辑信息
*/			}
		}
	try {
			//调用solr 
		searchtv.tvIndexAdd(string.toString());
		searchtv.tvDelRedisKey(string.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int resetchannel(Map<String, Object> map) {
		String ids=map.get("ids")==null?"":map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  String curtime = sdf.format(d);
		  if(!"".equals(ids)){
			  int index=ids.lastIndexOf(",");
			  if(index!=-1){
				  ids=ids.substring(0, index)+ids.substring(index+1, ids.length());
			  }
			    map.put("ids",ids);
			}else{
	    	map.put("updatetime", curtime);
			}
		List<Map<String, Object>> list = resetmpper.tfindchannel(map);
		for (Map<String, Object> map2 : list) {
			tvMapper.deleteschannelalbum(map2);// 删除
			tvMapper.deleteschannel(map2);// 删除
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) {
				try {
					tvMapper.insertchannel(map2);// 新增
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				List<Map<String, Object>> listalbum = resetmpper.tfindchannelalbum(map2);
				for (Map<String, Object> map3 : listalbum) {
					try {
						tvMapper.insertchannelalbum(map3);// 新增
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
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  String curtime = sdf.format(d);
		map.put("updatetime", curtime);
		List<Map<String, Object>> list = resetmpper.tfindlabel(map);
		for (Map<String, Object> map2 : list) {
			tvMapper.deleteslabel(map2);
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) {
				try {
					tvMapper.insertlabel(map2);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}

		}
		return 0;
	}

	@Override
	public int resetspecial(Map<String, Object> map) {
		String ids=map.get("ids")==null?"":map.get("ids").toString();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  String curtime = sdf.format(d);
		  if(!"".equals(ids)){
			  int index=ids.lastIndexOf(",");
			  if(index!=-1){
				  ids=ids.substring(0, index)+ids.substring(index+1, ids.length());
			  }
			    map.put("ids",ids);
			}else{
		    map.put("updatetime", curtime);
			}
		List<Map<String, Object>> list = resetmpper.tfindspecial(map);
		if (list.size() > 0) {
			for (Map<String, Object> map2 : list) {
				tvMapper.deletesspecial(map2);
				if (map2.get("status").toString().equals("1")) {
					try {
						tvMapper.insertspecial(map2);
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
					List<Map<String, Object>> listalbum = resetmpper.tfindspecialalbum(map2);
					tvMapper.deletesspecialalbum(map2);
					for (Map<String, Object> map3 : listalbum) {
						try {
							tvMapper.insertspecialalbum(map3);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				}else{
					tvMapper.deletesspecialalbum(map2);
				}
			}

		}
		return 0;
	}

}
