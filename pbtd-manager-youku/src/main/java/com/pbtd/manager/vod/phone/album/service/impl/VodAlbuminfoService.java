package com.pbtd.manager.vod.phone.album.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.phone.album.domain.Vodalbuminfo;
import com.pbtd.manager.vod.phone.album.mapper.VodAlbuminfoMapper;
import com.pbtd.manager.vod.phone.album.service.face.IVodAlbuminfoService;
@Service
public class VodAlbuminfoService implements IVodAlbuminfoService {
		@Autowired
		private VodAlbuminfoMapper vodAlbuminfoMapper;
	
	@Override
	public int count(Map<String, Object> queryParams) {
	
		return vodAlbuminfoMapper.count(queryParams);
	}

	@Override
	public List<Vodalbuminfo> page(int start, int limit, Map<String, Object> queryParams) {
	
		return vodAlbuminfoMapper.page(start, limit, queryParams);
	}

	@Override
	public List<Vodalbuminfo> find(Map<String, Object> queryParams) {
	
		return vodAlbuminfoMapper.find(queryParams);
	}

	@Override
	public Vodalbuminfo load(int id) {
	     Map<String,Object> map=new HashMap<String,Object>();
	     map.put("id", id);
		return vodAlbuminfoMapper.load(map);
	}

	@Override
	public int insert(Vodalbuminfo vodalbuminfo) {
		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("channel", vodalbuminfo.getChannel());
		queryParams.put("seriesCode",vodalbuminfo.getSeriesCode());
		//获取排序序号
		int newsequence=vodalbuminfo.getSequence()==0?150:vodalbuminfo.getSequence();
			queryParams.put("maxnum",newsequence);
			vodAlbuminfoMapper.addsequce( queryParams);
		return vodAlbuminfoMapper.insert(vodalbuminfo);
	}

	@Override
	public int update(Vodalbuminfo vodalbuminfo) {
		  Map<String,Object> queryParams=new HashMap<String,Object>();
		queryParams.put("channel", vodalbuminfo.getChannel());
		queryParams.put("seriesCode",vodalbuminfo.getSeriesCode());
	/*	if(vodalbuminfo.getChannel()!=null&& vodalbuminfo.getSeriesCode() !=null){
		//获取原始排序序号
		Vodalbuminfo map1=vodAlbuminfoMapper.load(queryParams);
		if(map1!=null   ){
			if(vodalbuminfo.getSequence()!=0){
				int currsequence=map1.getSequence()==null?1:map1.getSequence();
				int newsequence=vodalbuminfo.getSequence();
				//比对序号是变大还是变小
				if(currsequence>newsequence){
					queryParams.put("plus", "1");
					queryParams.put("minnum", newsequence);
					queryParams.put("maxnum",currsequence);
				}else{
					queryParams.put("minnum", currsequence);
					queryParams.put("maxnum",newsequence);
				}
				vodAlbuminfoMapper.updatesequce( queryParams);
			}
		}
		}*/
		return vodAlbuminfoMapper.update(vodalbuminfo);
	}

	@Override
	public int deletes(Map<String, Object> ids) {
	
		return vodAlbuminfoMapper.deletes(ids);
	}

	@Override
	public int updatesequce(Map<String, Object> queryParams) {
	
		return vodAlbuminfoMapper.updatesequce(queryParams);
	}

	@Override
	public int addsequce(Map<String, Object> queryParams) {
	
		return vodAlbuminfoMapper.addsequce(queryParams);
	}
	
	@Override
	public List<Map<String, Object>> findAlbumsinfovideo(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.findAlbumsinfovideo(queryParams);
	}
	
	@Override
	public int countAlbumsinfovideo(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.countAlbumsinfovideo(queryParams);
	}
	@Override
	public int insertvideo(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.insertvideo(queryParams);
	}

	@Override
	public int deletesvideo(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.deletesvideo(queryParams);
	}

	@Override
	public Map<String, Object> loadVideo(int id){
		return vodAlbuminfoMapper.loadVideo(id);
	}

	@Override
	public int updatevideo(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.updatevideo(queryParams);
	}

	@Override
	public List<Map<String, Object>> pagealbum(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.pagealbum(queryParams);
	}

	@Override
	public int updatealbumsequence(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.updatealbumsequence(queryParams);
	}

	@Override
	public int countalbum(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.countalbum(queryParams);
	}

	@Override
	public int addalbum(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.addalbum(queryParams);
	}

	@Override
	public int deletealbum(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.deletealbum(queryParams);
	}
	
	/**
     * 专辑绑定角标
     */
	@Override
	public int updatecorner(String albumid, String cornerid) {
		String[] seriesCodes = albumid.split(",");
		if (seriesCodes != null) {
			for (String seriesCode : seriesCodes) {
				vodAlbuminfoMapper.updatecorner(Integer.parseInt(seriesCode),cornerid);
			}
		}
		return 0;
	}
	
	/**
     * 专辑绑定付费包
     */
	@Override
	public int updatecollectfeesbag(String albumid, String collectid) {
		String[] seriesCodes = albumid.split(",");
		if (seriesCodes != null) {
			for (String seriesCode : seriesCodes) {
				vodAlbuminfoMapper.updatecollectfeesbag(Integer.parseInt(seriesCode),collectid);
			}
		}
		return 0;
	}

	@Override
	public int insertjson(Vodalbuminfo vodalbuminfo) {
		return vodAlbuminfoMapper.insertjson(vodalbuminfo);
	}

	@Override
	public int updatestatus(Vodalbuminfo vodalbuminfo) {
		return vodAlbuminfoMapper.updatestatus(vodalbuminfo);
	}

	@Override
	public int insertvideojson(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.insertvideojson(queryParams);
	}

	@Override
	public int updateisshow(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.updateisshow(queryParams);
	}

	@Override
	public List<Map<String, Object>> findalbumsequence(Map<String, Object> map) {
		return vodAlbuminfoMapper.findalbumsequence(map);
	}

	@Override
	public Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map) {
		return vodAlbuminfoMapper.findalbummaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams) {
		return vodAlbuminfoMapper.findalbumsequencesum(queryParams);
	}

	@Override
	public int updateisPositive(Map<String, Object> map) {
		return vodAlbuminfoMapper.updateisPositive(map);
	}

}
