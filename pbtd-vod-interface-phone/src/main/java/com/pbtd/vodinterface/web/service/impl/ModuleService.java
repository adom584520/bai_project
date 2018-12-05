package com.pbtd.vodinterface.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.vodinterface.web.domain.VodMasterplateSon;
import com.pbtd.vodinterface.web.domain.VodPhoneModule;
import com.pbtd.vodinterface.web.mapper.CommonMapper;
import com.pbtd.vodinterface.web.mapper.VodMasterplateMapper;
import com.pbtd.vodinterface.web.mapper.VodMasterplateSonMapper;
import com.pbtd.vodinterface.web.mapper.VodPhoneModuleAlbumMapper;
import com.pbtd.vodinterface.web.mapper.VodPhoneModuleMapper;
import com.pbtd.vodinterface.web.service.face.IModuleService;
@Service
public class ModuleService implements IModuleService {

	@Autowired
	private VodPhoneModuleMapper moduleMapper;
	@Autowired
	private VodMasterplateMapper vodMasterplateMapper;
	@Autowired
	private VodMasterplateSonMapper vodMasterplateSonMapper;
	@Autowired
	private VodPhoneModuleAlbumMapper vodPhonModuleAlbumMapper;
	@Autowired
	private CommonMapper commonmapper;
	@Override
	public List<Map<String, Object>> getModuleAlbum(Map<String, Object> queryParams) {
		List<Map<String, Object>> outputlist = new ArrayList<>();
		//第一步  查询当前频道下的模块
		List<VodPhoneModule> list = moduleMapper.select(queryParams);
		for (VodPhoneModule VodPhoneModule : list) {
			//第二步 查询当前模块下的运营位vodMasterplateSon
			List<VodMasterplateSon> sonlist = vodMasterplateSonMapper.selectByPrimaryKey(VodPhoneModule.getMasterplateid());
			Map<String, Object> modulemap = new HashMap<>();
			modulemap.put("moduleName", VodPhoneModule.getName());
			modulemap.put("moduleSequence", VodPhoneModule.getSequence());
			Integer moduleid = VodPhoneModule.getMasterplateid();
			modulemap.put("moduleId", VodPhoneModule.getMasterplateid());
			modulemap.put("mId", VodPhoneModule.getModuleid());
			modulemap.put("img", VodPhoneModule.getModulepic());
			modulemap.put("imgStatus", VodPhoneModule.getPicstatus());
			modulemap.put("textrecommendpic",VodPhoneModule.getTextrecommendpic());
			modulemap.put("textpicstatus",VodPhoneModule.getTextpicstatus());
			modulemap.put("isShowLeft", VodPhoneModule.getIsshowleft());
			modulemap.put("isShowRight", VodPhoneModule.getIsshowright());
			modulemap.put("moduleStatus", VodPhoneModule.getModulestatus());
			List<Object> yunyinweilist = new ArrayList<>();
			Long channelcode = null;
			for (VodMasterplateSon vodMasterplateSon : sonlist) {
				Map<String, Object> yunyuneweimap = new HashMap<>();
				HashMap<String, Object> param = new HashMap<>();
				param.put("moduleId", VodPhoneModule.getModuleid());
				param.put("masterplateNum", 1);
				//param.put("masterplateNum", vodMasterplateSon.getMasterplatenum());
				param.put("size", vodMasterplateSon.getCount());
				param.put("pageStart", vodMasterplateSon.getMasterplatenum()-1);
				//第三步 根据模块id 运营位的id 查询 绑定专辑集合
				List<Map<String,Object>> vodPhoneModuleAlbumlist = vodPhonModuleAlbumMapper.select(param);
				for (Map<String, Object> map : vodPhoneModuleAlbumlist) {
					channelcode =  (Long) map.get("seriesCode");
					int flag = (int) map.get("imgStatus"); 
					if(flag == 0){
						map.remove("imgStatus");
						map.remove("imgurl1");
						map.remove("imgurl2");
						map.remove("imgurl3");
						map.remove("imgurl4");
					}else{
						map.remove("imgStatus");
						map.put("pictureurl1",map.get("imgurl1"));
						map.put("pictureurl2",map.get("imgurl2"));
						map.put("pictureurl3",map.get("imgurl3"));
						map.put("pictureurl4",map.get("imgurl4"));
					}
				}	
				if(vodPhoneModuleAlbumlist == null || vodPhoneModuleAlbumlist.isEmpty()){
					Map<String,Object>  aa = new HashMap<>();
					vodPhoneModuleAlbumlist.add(aa);
				}
				yunyuneweimap.put("itemdata",vodPhoneModuleAlbumlist);
				yunyinweilist.add(yunyuneweimap);
			}
			modulemap.put("data", yunyinweilist);
			if(moduleid != null &&( moduleid == 108 || moduleid == 107 ||  moduleid == 106) ){
				Integer modulelinktype =VodPhoneModule.getModulelinktype();
				modulemap.put("modulelinktype",modulelinktype);
				modulemap.put("modulelinkchannel",VodPhoneModule.getModulelinkchannel());
				modulemap.put("modulelinkspecial",VodPhoneModule.getModulelinkspecial());
				modulemap.put("modulelinkurl",VodPhoneModule.getModulelinkurl());
				modulemap.put("moduleviewpoint",VodPhoneModule.getModuleviewpoint());
				modulemap.put("viewpointstatus",VodPhoneModule.getViewpointstatus());
				modulemap.put("modulebigpiicture",VodPhoneModule.getPicture());
				modulemap.put("modulebigpiicturestatus",VodPhoneModule.getPicturestatus());
				if(moduleid == 108 ){
					if(modulelinktype == 0){
						modulemap.put("modulelinkalbum",channelcode);
					}
				}
				
				if(modulelinktype == 3){
					//special_id
					Map<String,Object> specialmap = new HashMap<>();
					specialmap.put("special_id",VodPhoneModule.getModulelinkspecial() );
					List<Map<String,Object>> speciallist = commonmapper.getpgetspecial(specialmap);
					if(speciallist != null && speciallist.size()>0){
						modulemap.put("modulelinkspecialtype",speciallist.get(0).get("type"));
					}
				}
			}else if(moduleid != null ){
				modulemap.put("leftlinkChannel",VodPhoneModule.getLinkchannel());
				modulemap.put("leftlinkLabel",VodPhoneModule.getLinklabel());
				modulemap.put("leftlinkStatus",VodPhoneModule.getLinkstatus());
			}
			outputlist.add(modulemap);
		}
		return outputlist;
	}



	/**
	 * 换一换接口
	 */
	@Override
	public List<Map<String, Object>> getModuleAlbumforOne(Map<String, Object> queryParams) {
		List<Map<String, Object>> outputlist = new ArrayList<>();
		//第一步  根据模块id 查询模块信息
		VodPhoneModule VodPhoneModule = moduleMapper.load(queryParams);
		if(VodPhoneModule == null){
			return outputlist;
		}
		//获取当前模版相关的数据
		//vodMasterplateMapper.selectByPrimaryKey(VodPhoneModule.getMasterplateid());
		//第二步 查询当前模块下的运营位vodMasterplateSon
		List<VodMasterplateSon> sonlist = vodMasterplateSonMapper.selectByPrimaryKey(VodPhoneModule.getMasterplateid());
		Map<String, Object> modulemap = new HashMap<>();
		modulemap.put("moduleName", VodPhoneModule.getName());
		modulemap.put("moduleSequence", VodPhoneModule.getSequence());
		modulemap.put("moduleId", VodPhoneModule.getMasterplateid());
		modulemap.put("mId", VodPhoneModule.getModuleid());
		modulemap.put("img", VodPhoneModule.getModulepic());
		modulemap.put("imgStatus", VodPhoneModule.getPicstatus());
		modulemap.put("isShowLeft", VodPhoneModule.getIsshowleft());
		modulemap.put("isShowRight", VodPhoneModule.getIsshowright());
		modulemap.put("moduleStatus", VodPhoneModule.getModulestatus());
		List<Object> yunyinweilist = new ArrayList<>();
		//需要查看的页码：
		int pageNum = Integer.parseInt((String) queryParams.get("pageNum"))-1<0?0:Integer.parseInt((String) queryParams.get("pageNum"))-1;
		//一页的长度
		int limitsize = sonlist.size();
		
		HashMap<String, Object> param = new HashMap<>();
		param.put("moduleId", VodPhoneModule.getModuleid());
		param.put("size", limitsize);
		param.put("pageStart", pageNum*limitsize);
		List<Map<String,Object>> vodPhoneModuleAlbumlist = vodPhonModuleAlbumMapper.selectitemdata(param);
		if(vodPhoneModuleAlbumlist.size() < limitsize && vodPhoneModuleAlbumlist.size() != 0){
			vodPhoneModuleAlbumlist = null;
			/*int count  = vodPhonModuleAlbumMapper.count(param);
			param.put("pageStart", count-limitsize);
			vodPhoneModuleAlbumlist = vodPhonModuleAlbumMapper.selectlastitemdata(param);*/
		}
		if(vodPhoneModuleAlbumlist != null && vodPhoneModuleAlbumlist.size() > 0){
			for (Map<String, Object> map : vodPhoneModuleAlbumlist) {
				Map<String, Object> yunyuneweimap = new HashMap<>();
				int flag = (int) map.get("imgStatus"); 
				if(flag == 0){
					map.remove("imgStatus");
					map.remove("imgurl1");
					map.remove("imgurl2");
					map.remove("imgurl3");
					map.remove("imgurl4");
				}else{
					map.remove("imgStatus");
					map.put("pictureurl1",map.get("imgurl1"));
					map.put("pictureurl2",map.get("imgurl2"));
					map.put("pictureurl3",map.get("imgurl3"));
					map.put("pictureurl4",map.get("imgurl4"));
				}
				List list = new ArrayList<>();
				list.add(map);
				yunyuneweimap.put("itemdata",list); 
				yunyinweilist.add(yunyuneweimap);
			}
		}
		if(vodPhoneModuleAlbumlist == null || vodPhoneModuleAlbumlist.isEmpty()){
			modulemap.put("data", null);
			outputlist.add(modulemap);
		}else{
			modulemap.put("data", yunyinweilist);
			outputlist.add(modulemap);
		}
		return outputlist;
	}
}
