package com.pbtd.playclick.youku.domain;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pbtd.playclick.integrate.domain.Vodactors;
import com.pbtd.playclick.integrate.mapper.VodactorsMapper;
import com.pbtd.playclick.youku.mapper.YouKualbumMapper;

@Component
public class staticmap implements Serializable{
	private static final long serialVersionUID = 1L;
	@Autowired
	private    YouKualbumMapper youkualbummapper;
	@Autowired
	private      VodactorsMapper   vpdactorsmapper;
	
	public  HashMap<String,Object> Lab_list;
	public  HashMap<String,Object> Chn_list;
	public  HashMap<String,Object> Ac_list;
	
		 
	public  void setLab_list() {
		Lab_list = getLab_lists();
	}
	public  void setChn_list() {
		Chn_list = getChn_lists();
	}
	public  void setAc_list() {
		Ac_list = getAc_lists();
	}
	public  HashMap<String,Object> getLab_lists() {
		HashMap<String, Object>   map=new HashMap<>(500);
		try {
			List<Map<String, Object>> m=youkualbummapper.findlabellist(null);
			for (Map<String, Object> m1 : m) { 
				map.put(m1.get("tagName").toString()+m1.get("chnId").toString(),m1.get("tagId"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public   HashMap<String,Object> getChn_lists() {
		HashMap<String, Object> map=new HashMap<>(100);
		try {
			List<Map<String, Object>> m=youkualbummapper.findchannellist(null);
			map = new HashMap<String,Object>(100);
			for (Map<String, Object> m1 : m) { 
				map.put(m1.get("chnName").toString(),m1.get("chnId"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public 	HashMap<String,Object> getAc_lists () {
		HashMap<String, Object> map=new HashMap<>(500000);
		try {
			List<Vodactors> m=vpdactorsmapper.findlist(map);
			for (Vodactors vodactors : m) {
				map.put(vodactors.getName(),vodactors.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	 
}
