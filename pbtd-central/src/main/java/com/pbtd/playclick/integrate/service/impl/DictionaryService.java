package com.pbtd.playclick.integrate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.integrate.domain.Dictionary;
import com.pbtd.playclick.integrate.service.face.IDictionaryService;
import com.pbtd.playclick.base.common.mapper.DictionaryMapper;

@Service
public class DictionaryService implements IDictionaryService{

	   @Autowired
	 private DictionaryMapper dictionaryMapper;	
	
	   @Override
	public List<Dictionary> findDictionary(Map<String, Object> params,
			String obj) {
		   if(obj.equals("choosechanneltv")){
			   return this.dictionaryMapper.choosechannelphone(params);
		   }else if(obj.equals("choosechannelphone")){
			   return this.dictionaryMapper.choosechannelphone(params);
		   }else if(obj.equals("choosechannelyh")){
			   return this.dictionaryMapper.choosechannelyh(params);
		   }else if(obj.equals("choosechannellabelyh")){
			   return this.dictionaryMapper.choosechannellabelyh(params);
		   }else if(obj.equals("choosechannelgg")){
			   return this.dictionaryMapper.choosechannelgg(params);
		   }else if(obj.equals("choosechannellabelgg")){
			   return this.dictionaryMapper.choosechannellabelgg(params);
		   }else if(obj.equals("choosechannelguttv")){
			   return this.dictionaryMapper.choosechannelguttv(params);
		   }else if(obj.equals("choosechannellabelguttv")){
			   return this.dictionaryMapper.choosechannellabelguttv(params);
		   } else if(obj.equals("choosechannelyk")){
			   return this.dictionaryMapper.choosechannelyk(params);
		   }else if(obj.equals("choosechannellabelyk")){
			   return this.dictionaryMapper.choosechannellabelyk(params);
		   }else if(obj.equals("chooselabeltype")){
			   return this.dictionaryMapper.chooselabeltype(params);
		   }
		   else{
			   return null;
		   }
	}
	   
	   

	@Override
	public List<Map<String, Object>> findDictionaryname(Map<String, Object> params, String obj) {
		  if(obj.equals("chooseAlbums")){
			   return this.dictionaryMapper.choosename(params);
		   }else if(obj.equals("choosecpsource")){
			   return this.dictionaryMapper.choosecpsource(params);
		   }else{
			   return null;
		   }
	}
	
}
