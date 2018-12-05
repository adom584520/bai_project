package com.pbtd.vodinterface.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.vodinterface.web.domain.VodMasterplateSon;
import com.pbtd.vodinterface.web.domain.VodTvModule;
import com.pbtd.vodinterface.web.domain.VodTvModuleAlbum;
import com.pbtd.vodinterface.web.mapper.VodMasterplateMapper;
import com.pbtd.vodinterface.web.mapper.VodMasterplateSonMapper;
import com.pbtd.vodinterface.web.mapper.VodTvModuleAlbumMapper;
import com.pbtd.vodinterface.web.mapper.VodTvModuleMapper;
import com.pbtd.vodinterface.web.service.face.IModuleService;
@Service
public class ModuleService implements IModuleService {

	@Autowired
	private VodTvModuleMapper moduleMapper;
	@Autowired
	private VodMasterplateMapper vodMasterplateMapper;
	@Autowired
	private VodMasterplateSonMapper vodMasterplateSonMapper;
	@Autowired
	private VodTvModuleAlbumMapper vodTvModuleAlbumMapper;

	@Override
	public List<Map<String, Object>> getModuleAlbum(Map<String, Object> queryParams) {
		List<Map<String, Object>> outputlist = new ArrayList<>();
		//第一步  查询当前频道下的模块
		List<VodTvModule> list = moduleMapper.select(queryParams);
		for (VodTvModule VodTvModule : list) {
			//获取当前模版相关的数据
			//vodMasterplateMapper.selectByPrimaryKey(VodTvModule.getMasterplateid());
			//第二步 查询当前模块下的运营位
			List<VodMasterplateSon> sonlist = vodMasterplateSonMapper.selectByPrimaryKey(VodTvModule.getMasterplateid());
			Map<String, Object> modulemap = new HashMap<>();
			modulemap.put("moduleName", VodTvModule.getName());
			modulemap.put("moduleId", VodTvModule.getMasterplateid());
			List<Object> yunyinweilist = new ArrayList<>();
			for (VodMasterplateSon vodMasterplateSon : sonlist) {
				Map<String, Object> yunyuneweimap = new HashMap<>();
				HashMap<String, Object> param = new HashMap<>();
				param.put("moduleId", VodTvModule.getModuleid());
				param.put("masterplateNum", vodMasterplateSon.getMasterplatenum());
				param.put("size", vodMasterplateSon.getCount());
				//第三步 根据模块id 运营位的id 查询 绑定专辑集合
				List<Map<String,Object>> VodTvModuleAlbumlist = vodTvModuleAlbumMapper.select(param);
				yunyuneweimap.put("itemdate",VodTvModuleAlbumlist);
				yunyinweilist.add(yunyuneweimap);
			}
			modulemap.put("data", yunyinweilist);
			outputlist.add(modulemap);
		}
		return outputlist;
	}

	@Override
	public List<Map<String, Object>> getModuleAlbumforOne(Map<String, Object> queryParams) {
		//已有模块id；查询出 模版id，
		// 第一步查询各个运营位的list

		List<Map<String,Object>> list =  moduleMapper.selectForOne(queryParams);
		//第三步 根据模块id 运营位的id 查询 绑定专辑集合
		List<Map<String, Object>> yunyinweilist = new ArrayList<>();

		for (Map<String, Object> map : list) {
			Map<String, Object> yunyuneweimap = new HashMap<>();

			//第三步 根据模块id 运营位的id 查询 绑定专辑集合
			List<Map<String,Object>> VodTvModuleAlbumlist = vodTvModuleAlbumMapper.select(map);
			yunyuneweimap.put("data"+map.get("id"),VodTvModuleAlbumlist);
			yunyinweilist.add(yunyuneweimap);
		}
		return yunyinweilist;
	}
}
