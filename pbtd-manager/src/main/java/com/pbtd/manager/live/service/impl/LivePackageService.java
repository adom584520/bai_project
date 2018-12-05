package com.pbtd.manager.live.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.domain.LivePackage;
import com.pbtd.manager.live.mapper.LiveBussInfoMapper;
import com.pbtd.manager.live.mapper.LivePackageMapper;
import com.pbtd.manager.live.mapper.LiveSysParamMapper;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILivePackageService;

@Service
public class LivePackageService implements ILivePackageService {
	@Autowired
	private LivePackageMapper livePackageMapper;
	@Autowired
	private LiveBussInfoMapper liveBussInfoMapper;
	/**
	 * 
	 */
	@Override
	public PageResult querylistallLivePackage() {
		List<LivePackage> list = livePackageMapper.selectByPrimaryKey(null);
		return new PageResult((long) list.size(), list);
	}


	@Override
	public int count(Map<String, Object> queryParams) {
		return this.livePackageMapper.count(queryParams);
	}

	@Override
	public List<LivePackage> page(int start, int limit, Map<String, Object> queryParams) {
		return this.livePackageMapper.page(start, limit, queryParams);
	}

	@Override
	public List<LivePackage> find(Map<String, Object> queryParams) {
		return this.livePackageMapper.find(queryParams);
	}


	@Override
	public LivePackage load(int id) {
		return this.livePackageMapper.load(id);
	}
	
	
	@Override
	@Transactional
	public int deletes(Map<String, Object> ids) {
		return this.livePackageMapper.deletes(ids);
	}

	@Override
	@Transactional
	public int insert(LivePackage LivePackage) {
		this.livePackageMapper.insert(LivePackage);
		return LivePackage.getPackageid();
	}
	@Override
	@Transactional
	public int update(LivePackage LivePackage) {
		return this.livePackageMapper.update(LivePackage);
	}
	
	@Override
	public int updateupdown(int id,int status) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("status", status);
		return livePackageMapper.updateupdown(map);
	}
	@Override
	@Transactional
	public int updateTop(int id) {

		Map<String, Object> queryParams =new HashMap<>();
		queryParams.put("id", 1); 
		//获取原始排序序号 比较是要变大还是变小
		LivePackage map1=livePackageMapper.load(id);
		int currsequence=map1.getPackageorder();
		int newsequence=1;
		//比对序号是变大还是变小
		if(currsequence>newsequence){
			queryParams.put("plus", "1");
			queryParams.put("minnum", newsequence);
			queryParams.put("maxnum",currsequence);
		}else{
			queryParams.put("minnum", currsequence);
			queryParams.put("maxnum",newsequence);
		}
		//修改其他需要变更的排序
		livePackageMapper.updatesequce(queryParams);
		livePackageMapper.updateleven(11);
		
		return this.livePackageMapper.updatebyId(id);
	}

	@Override
	public List<LivePackage> getpackage(String id) {
		int bussid = liveBussInfoMapper.selectByValue(id).getBussid();
		return this.livePackageMapper.getpackage(bussid);
	}

}
