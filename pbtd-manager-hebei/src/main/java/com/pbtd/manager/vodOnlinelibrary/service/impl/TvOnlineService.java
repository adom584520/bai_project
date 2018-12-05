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
import com.pbtd.manager.vod.tv.common.domain.VodTvchannel;
import com.pbtd.manager.vod.tv.search.controller.SearchTvController;
import com.pbtd.manager.vodOnlinelibrary.mapper.PhoneOnlineMapper;
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
	private PhoneOnlineMapper phoneMapper;

	@Autowired
	private SearchTvController   searchtv;

	// 移动融合平台是否启用
	@Value("${resetalbum_open}")
	public String resetalbum_open;
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
		if(resetalbum_open != null && !"".equals(resetalbum_open) && "YES".equals(resetalbum_open)){
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
		}
		StringBuffer string=new  StringBuffer();
		for (Map<String, Object> map2 : list) {
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
				map2.put("imgtitle", map.get("imgtitle"));
				final List<Map<String, Object>> listvideo = resetmpper.tfindvideo(map2);
			
				if(resetalbum_open != null && !"".equals(resetalbum_open) && "YES".equals(resetalbum_open)){

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
				}
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
					searchtv.tvIndexAdd(sb.toString());
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

				map2.put("type", "tv");
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

	/**
	 * 同步频道下的module
	 * @param map
	 * @return
	 */
	@Override
	public int resetmodule(Map<String, Object> map) {
		//第一步：获取频道下的模块信息；
		List<Map<String, Object>> list = resetmpper.tfindchannelmodule(map);
		if(list != null && list.size()>0){ 
			//同步到线上
			tvMapper.deletechannelmodule(map);
			for (Map<String, Object> map2 : list) {
				try {
					tvMapper.insertchannelmodule(map2);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}

		
		//第二步：获取频道模块下所有绑定的专辑信息
		List<Map<String, Object>> albumlist = resetmpper.tfindchannelmodulealbum(map);
		if(albumlist != null && albumlist.size()>0){
			//同步到线上
			tvMapper.deletechannelmodulealbum(map);
			for (Map<String, Object> map3 : albumlist) {
				try {
					tvMapper.insertchannelmodulealbum(map3);
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
		List<Map<String, Object>> list = resetmpper.tfindchannelmodule(map);
		if(list != null && list.size()>0){
			//同步到线上
			tvMapper.deletechannelmodule(map);         
			for (Map<String, Object> map2 : list) {
				try {
					tvMapper.insertchannelmodule(map2);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}

		
		//第二步：获取频道模块下所有绑定的专辑信息
		List<Map<String, Object>> albumlist = resetmpper.tfindchannelmodulealbum(map);
		if(albumlist != null && albumlist.size()>0){
			//同步到线上
			tvMapper.deletechannelmodulealbum(map);
			for (Map<String, Object> map3 : albumlist) {
				try {
					tvMapper.insertchannelmodulealbum(map3);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
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
		this.tvMapper.deleteSingleModule(map);  //先删除
		List<Map<String,Object>> listModule=this.resetmpper.tfindSingleMoudle(map);
		if(listModule!=null && listModule.size()>0){
			for(Map<String,Object> moudle:listModule){
				try{
					this.tvMapper.insertSingleModule(moudle);
				}catch(Exception e){
					e.printStackTrace();
					throw new Exception("同步模块时：",e);
				}
			}
		}
		
		//同步模块关联的专辑
		this.tvMapper.deleteSingleModuleAlbum(map);//先删除
		List<Map<String,Object>> listModuleAlbum=this.resetmpper.tfindSingleMoudleAlbum(map);
		if(listModuleAlbum!=null && listModuleAlbum.size()>0){
			for(Map<String,Object> moduleAlbum:listModuleAlbum){
				try{
					this.tvMapper.insertSingleModuleAlbum(moduleAlbum);
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
			this.tvMapper.updateChannelSeq(vodTvchannel);
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
			this.tvMapper.updateModuleSeq(map);
		}catch(Exception e){
			throw new Exception("同步模块排序时：",e);
		}
		return 1;
	}
	

}
