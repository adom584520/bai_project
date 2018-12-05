package com.pbtd.manager.vod.page.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.page.domain.Dictionary;
import com.pbtd.manager.vod.page.mapper.CommonMapper;
import com.pbtd.manager.vod.page.mapper.DictionaryMapper;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;

@Service
public class DictionaryService implements IDictionaryService{

	   @Autowired
	 private DictionaryMapper dictionaryMapper;	
	   
	   @Autowired
		 private CommonMapper commonMapper;	
	
	   //字典类型
	   @Override
		public List<Dictionary> findDictionary(Map<String, Object> params, String obj) {
			   if(obj.equals("choosechannel")){
				   return this.dictionaryMapper.choosechannel(params);
			   }else if(obj.equals("choosechanneltv")){
				   return this.dictionaryMapper.choosechanneltv(params);
			   }else  if(obj.equals("chooselabel")){
				   return this.dictionaryMapper.chooselabel(params);
			   }else if(obj.equals("chooselabeltv")){
				   return this.dictionaryMapper.chooselabeltv(params);
			   }else if(obj.equals("choosebusschannel")){
				   return this.dictionaryMapper.choosebusschannel(params);
			   }else if(obj.equals("choosebusschannelno")){
				   return this.dictionaryMapper.choosebusschannelno(params);
			   }else{
				   return null;
			   }
		}
	   
	   //map类型
		@Override
		public List<Map<String, Object>> findDictionaryMap(Map<String, Object> params, String obj) {
			   if(obj.equals("choosechannel")){//手机频道
				   return this.commonMapper.choosechannel(params);
			   }else if(obj.equals("choosechanneltv")){//tv频道
				   return this.commonMapper.choosechanneltv(params);
			   }else  if(obj.equals("chooselabel")){//手机标签
				   return this.commonMapper.chooselabel(params);
			   }else if(obj.equals("chooselabeltv")){//tv标签
				   return this.commonMapper.chooselabeltv(params);
			   }else  if(obj.equals("choosecorner")){//角标
				   return this.commonMapper.choosecorner(params);
			   }else if(obj.equals("choosecollectfeesbag")){//付费包
				   return this.commonMapper.choosecollectfeesbag(params);
			   }else if(obj.equals("chooselabeltype")){
				   return this.commonMapper.chooselabeltype(params);
			   }else if(obj.equals("choosecpsource")){
				   return this.commonMapper.choosecpsource(params);
			   }
			   else{  
				   return null;
			   }
		}

		@Override
		public List<Map<String, Object>> findbusschannel(Map<String, Object> params) {
			return commonMapper.findbusschannel(params);
		}

		@Override
		public List<Map<String, Object>> findalbum(Map<String, Object> params) {
			
			return commonMapper.findalbum(params);
		}

		@Override
		public List<Map<String, Object>> findalbumhot(Map<String, Object> params) {
			
			return commonMapper.findalbumhot(params);
		}

		@Override
		public int inserthot(Map<String, Object> params) {
			
			return commonMapper.inserthot(params);
		}

		@Override
		public Map<String, Object> getoffset(Map<String, Object> params) {
			return commonMapper.getoffset(params);
		}

		@Override
		public int updateoffset(Map<String, Object> params) {
			return commonMapper.updateoffset(params);
		}
	
}
