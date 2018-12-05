package com.pbtd.manager.vod.page.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.easyui.ComboBoxOptionModel;
import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.vod.page.domain.Dictionary;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;

@Controller("pbtdController.DictionaryController")
@RequestMapping("/pbtd")
public class DictionaryController {

	@Autowired
	private IDictionaryService dictionaryService;
	
	/**
	 * 数据字典下拉框方式展示
	 * @param obj 数据库对象名例如T_ZXBZ_ZZMM,则obj为Zzmc
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dictionary/{obj}")
	public List<ComboBoxOptionModel> findDictionary(@PathVariable("obj")String obj,HttpServletRequest request){
		Map<String,Object> params = RequestUtil.asMap(request);
		List<ComboBoxOptionModel> comboBox = new ArrayList<ComboBoxOptionModel>();
		ComboBoxOptionModel option;
		List<Dictionary> dics = this.dictionaryService.findDictionary(params, obj);
		for(Dictionary d:dics){
			option = new ComboBoxOptionModel();
			option.setText(d.getName());
			option.setValue(d.getId()+"");
			option.setExtraField1(d.getExtract());
			comboBox.add(option);
		}
		return comboBox;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/findDictionaryMap/{obj}")
	public List<Map<String,Object>> findDictionaryMap(@PathVariable("obj")String obj,HttpServletRequest request){
		Map<String,Object> params = RequestUtil.asMap(request);
		List<Map<String,Object>> dics = this.dictionaryService.findDictionaryMap(params, obj);
		return dics;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/find")
	public List<Map<String,Object>> find(HttpServletRequest request){
		Map<String,Object> params =new HashMap<>();
		Map<String,Object> addmap =new HashMap<>();
		List<Map<String, Object>> list=dictionaryService.findalbum(params);
		for (Map<String, Object> map : list) {
			int count=0;
			
			String writer=map.get("writer")==null?" ":map.get("writer").toString();
			String actor=map.get("actor")==null?" ":map.get("actor").toString();
			String channel=map.get("channel")==null?" ":map.get("channel").toString();
			params.put("writer", writer);
			params.put("seriesCode", map.get("seriesCode"));
			List<Map<String, Object>> hotmap1=dictionaryService.findalbumhot(params);
			params=new HashMap<>();
			params.put("actor", actor);
			params.put("seriesCode", map.get("seriesCode"));
			List<Map<String, Object>> hotmap2=dictionaryService.findalbumhot(params);
			params=new HashMap<>();
			params.put("channel", channel);
			params.put("seriesCode", map.get("seriesCode"));
			List<Map<String, Object>> hotmap3=dictionaryService.findalbumhot(params);
			if(hotmap1.size()>1){
				int s=5-count;
				for(int j=0;j<s && j<hotmap1.size();j++){
					addmap.put("id", map.get("seriesCode"));
					addmap.put("seriesCode", hotmap1.get(j).get("seriesCode"));
					dictionaryService.inserthot(addmap);
					count+=1;
				}
				
			}
			if(hotmap2.size()>1 && count<5){
				int s=5-count;
				for(int j=0;j<s && j<hotmap2.size();j++){
					addmap.put("id", map.get("seriesCode"));
					addmap.put("seriesCode", hotmap2.get(j).get("seriesCode"));
					dictionaryService.inserthot(addmap);
					count+=1;
				}
				
			}
			if(hotmap3.size()>1 && count<5){
				int s=5-count;
				for(int j=0;j<s && j<hotmap3.size();j++){
					addmap.put("id", map.get("seriesCode"));
					addmap.put("seriesCode", hotmap3.get(j).get("seriesCode"));
					dictionaryService.inserthot(addmap);
					count+=1;
				}
				
			}
			
		}
		return null;
	}
}
