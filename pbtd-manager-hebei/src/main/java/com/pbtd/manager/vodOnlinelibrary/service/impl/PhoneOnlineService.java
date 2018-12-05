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
import com.pbtd.manager.vod.page.mapper.JsonInterfaceMapper;
import com.pbtd.manager.vod.page.mapper.ResetMapper;
import com.pbtd.manager.vod.phone.search.controller.SearchPhoneController;
import com.pbtd.manager.vod.tv.common.domain.VodTvchannel;
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
	@Autowired
	private JsonInterfaceMapper jsonInterface;
	// 同步专辑到移动融合路径
	@Value("${resetalbum_open}")
	public String resetalbum_open;
	
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
	
		if(resetalbum_open != null && !"".equals(resetalbum_open) && "YES".equals(resetalbum_open)){
			new Thread() {
				public void run() {
					try {
						// 同步专辑到移动融合库-手机端
						//System.out.println("===============================================================");
						//System.out.println("===============================================================");
						HashMap<String, String> params = new HashMap<>();
						params.put("data", JSON.toJSONString(list));
						String doPost = HttpClientUtil.doPost(resetalbum_url, params, "UTF-8");
						//System.out.println("===============================================================");
						//System.out.println("===============================================================");
					} catch (IllegalArgumentException ec) {
						ec.printStackTrace();
					}
				}
			}.start();
		}
		
		
		StringBuffer string = new StringBuffer();
		for (Map<String, Object> map2 : list) {
			map2.put("imgtitle", map.get("imgtitle"));
			string.append(map2.get("seriesCode").toString() + ",");
	  	     phoneMapper.deletealbum(map2);// 删除节目
			int albuminterfacecount=jsonInterface.countalbuminterface(map2);//获取当前专辑是否已经同步在线上库
			//System.out.println("1-------");
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) {//当前专辑下线状态  同步专辑表删除该专辑
				phoneMapper.insertalbum(map2);// 新增节目
				if(albuminterfacecount>0){//当前专辑存在线上库 更新同步时间  否则添加至线上库
					jsonInterface.updatealbuminterface(map2);
				}else{
					jsonInterface.insertalbuminterface(map2);
				}
			
				final List<Map<String, Object>> listvideo = resetmpper.pfindvideo(map2);
				if(resetalbum_open != null && !"".equals(resetalbum_open) && "YES".equals(resetalbum_open)){
				new Thread() {
					public void run() {
						try {
						// 同步剧集到移动融合库-手机端
							HashMap<String, String> params1 = new HashMap<>();
							params1.put("data", JSON.toJSONString(listvideo));
							//System.out.println("移动融合同步剧集接口----------------");
					    	HttpClientUtil.doPost(resetalbumvideo_url, params1, "UTF-8");
						
						} catch (IllegalArgumentException ec) {
							ec.printStackTrace();
						}
					}
				}.start();
				}
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
				  } else {
				jsonInterface.deletealbuminterface(map2);//当前专辑下线状态  同步专辑表删除该专辑
				phoneMapper.deletesvideo(map2);// 删除剧集
			}
		 }

		try {
			// 调用solr
			String[] sizes=string.toString().split(",");
			int jj=0;
			StringBuffer sb = new StringBuffer();
			for(int ii=0;ii<500 && ii+jj < sizes.length ;ii++){
				String s = sizes[jj+ii];
				sb.append(s + ",");
				if( ii==500-1 || ii+jj ==sizes.length-1 ){
				if(ii+jj ==sizes.length-1){
					sb.append("0");
				}
					 searchphonel.phoneIndexAdd(sb.toString());
					 sb = new StringBuffer("");
				}
				if(sizes.length>jj+ii && ii==500-1){
					ii=-1;
					jj+=500;
					continue;
				}
			}
			
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
				map2.put("type", "phone");
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
			phoneMapper.deleterecommandpic(null);
			for (Map<String, Object> curmap : list) {
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
	
	
	
	
	/**
	 * 同步单条module
	 * @param map
	 * @return
	 */
	@Override
	public int resetSingleMoudle(Map<String, Object> map) throws Exception {
		//同步模块
		this.phoneMapper.deleteSingleModule(map);  //先删除
		List<Map<String,Object>> listModule=this.resetmpper.tfindSingleMoudle(map);
		if(listModule!=null && listModule.size()>0){
			for(Map<String,Object> moudle:listModule){
				try{
					this.phoneMapper.insertSingleModule(moudle);
				}catch(Exception e){
					e.printStackTrace();
					throw new Exception("同步模块时：",e);
				}
			}
		}
		
		//同步模块关联的专辑
		this.phoneMapper.deleteSingleModuleAlbum(map);//先删除
		List<Map<String,Object>> listModuleAlbum=this.resetmpper.tfindSingleMoudleAlbum(map);
		if(listModuleAlbum!=null && listModuleAlbum.size()>0){
			for(Map<String,Object> moduleAlbum:listModuleAlbum){
				try{
					this.phoneMapper.insertSingleModuleAlbum(moduleAlbum);
				}catch(Exception e){
					e.printStackTrace();
					throw new Exception("同步模块关联的专辑时：",e);
				}
			}
		}
		return 0;
	}
	
	
	/**
	 * 同步频道排序
	 */
	public int resetChannelSeq(VodTvchannel vodTvchannel) throws Exception {
		try{
			this.phoneMapper.updateChannelSeq(vodTvchannel);
		}catch(Exception e){
			throw new Exception("同步频道排序时：",e);
		}
		return 1;
	}
	
	
	/**
	 * 同步模块排序
	 */
	public int resetModuleSeq(Map<String,Object> map) throws Exception{
		try{
			this.phoneMapper.updateModuleSeq(map);
		}catch(Exception e){
			throw new Exception("同步模块排序时：",e);
		}
		return 1;
	}
	
	/**
	 * 同步频道下的module
	 * @param map
	 * @return
	 */
	@Override
	public int resetmodule(Map<String, Object> map) {
		//第一步：获取频道下的模块信息；
		List<Map<String, Object>> list = resetmpper.tfindphonechannelmodule(map);
		if(list != null && list.size()>0){ 
			//同步到线上
			phoneMapper.deletechannelmodule(map);
			for (Map<String, Object> map2 : list) {
				try {
					phoneMapper.insertchannelmodule(map2);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}

		//第二步：获取频道模块下所有绑定的专辑信息
		List<Map<String, Object>> albumlist = resetmpper.tfindphonechannelmodulealbum(map);
		if(albumlist != null && albumlist.size()>0){
			//同步到线上
			phoneMapper.deletechannelmodulealbum(map);
			for (Map<String, Object> map3 : albumlist) {
				try {
					phoneMapper.insertchannelmodulealbum(map3);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return 0;
	}

	
	/**
	 * 同步全部模块&绑定专辑
	 * @param map
	 * @return
	 */
	public int resetAllmodule(Map<String, Object> map) {
		//第一步：获取频道下的模块信息；
		List<Map<String, Object>> list = resetmpper.tfindphonechannelmodule(map);
		if(list != null && list.size()>0){
			//同步到线上
			phoneMapper.deletechannelmodule(map);         
			for (Map<String, Object> map2 : list) {
				try {
					phoneMapper.insertchannelmodule(map2);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}

		
		//第二步：获取频道模块下所有绑定的专辑信息
		List<Map<String, Object>> albumlist = resetmpper.tfindphonechannelmodulealbum(map);
		if(albumlist != null && albumlist.size()>0){
			//同步到线上
			phoneMapper.deletechannelmodulealbum(map);
			for (Map<String, Object> map3 : albumlist) {
				try {
					phoneMapper.insertchannelmodulealbum(map3);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return 0;
	}

	@Override
	public int resetphonepaidalbum(Map<String, Object> map) {
		List<Map<String, Object>> list = resetmpper.pfindpaidalbum(map);
		phoneMapper.deletepaidAlbum(map);// 删除
		for (Map<String, Object> map2 : list) {
				try {
					phoneMapper.insertpaidAlbum(map2);// 新增
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
		}
		return 0;
	}

	@Override
	public int resetphonealbumvideo_interface(Map<String, Object> map) {
		final List<Map<String, Object>> listvideo = resetmpper.pfindvideointerface(map);
		for (Map<String, Object> map3 : listvideo) {
				try {
					phoneMapper.insertvideo(map3);// 新增剧集
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
		}
		return 0;
	}

	@Override
	public int resetphonealbum_interface(Map<String, Object> map) {
		final List<Map<String, Object>> list = resetmpper.pfindalbuminterface(map);
		if(resetalbum_open != null && !"".equals(resetalbum_open) && "YES".equals(resetalbum_open)){
			new Thread() {
				public void run() {
					try {
						// 同步专辑到移动融合库-手机端
						//System.out.println("===============================================================");
						HashMap<String, String> params = new HashMap<>();
						params.put("data", JSON.toJSONString(list));
						String doPost = HttpClientUtil.doPost(resetalbum_url, params, "UTF-8");
					} catch (IllegalArgumentException ec) {
						ec.printStackTrace();
					}
				}
			}.start();
		}
		
		
		StringBuffer string = new StringBuffer();
		for (Map<String, Object> map2 : list) {
			map2.put("imgtitle", map.get("imgtitle"));
			string.append(map2.get("seriesCode").toString() + ",");
			phoneMapper.deletealbum(map2);// 删除节目
			String status = map2.get("status") == null ? "" : map2.get("status").toString();
			if (status.equals("1")) { 
			 //当前专辑存在线上库 更新同步时间   
				phoneMapper.insertalbum(map2);// 新增节目
				jsonInterface.updatealbuminterface(map2);
			}  
		}

		try {
			// 调用solr
			String[] sizes=string.toString().split(",");
			int jj=0;
			StringBuffer sb = new StringBuffer();
			for(int ii=0;ii<500 && ii+jj < sizes.length ;ii++){
				String s = sizes[jj+ii];
				sb.append(s + ",");
				if( ii==500-1 || ii+jj ==sizes.length-1 ){
					if(ii+jj ==sizes.length-1){
						sb.append("0");
					}
					 searchphonel.phoneIndexAdd(sb.toString());
					 sb = new StringBuffer("");
				}
				if(sizes.length>jj+ii && ii==500-1){
					ii=-1;
					jj+=500;
					continue;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
