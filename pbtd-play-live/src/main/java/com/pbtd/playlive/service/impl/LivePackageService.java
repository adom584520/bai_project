package com.pbtd.playlive.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playlive.domain.LivePackage;
import com.pbtd.playlive.domain.LivePackageOutPut;
import com.pbtd.playlive.mapper.LivePackageMapper;
import com.pbtd.playlive.page.DataResult;
import com.pbtd.playlive.page.PageResult;
import com.pbtd.playlive.service.ILivePackageService;


@Service
public class LivePackageService implements ILivePackageService {
	@Autowired
	private LivePackageMapper livePackageMapper;
	
	@Override
	public PageResult<?> querylistallPackage(Map<String,Object> params) {
		//List<LivePackage> list = livePackageMapper.selectByPrimaryKey(null);
		Integer tagId = (Integer)params.get("tagId");
		HashMap<Object, Object> tempBean = new HashMap<>();
		
		tempBean.put("tagid", tagId);
		if(tagId == -1){
			tempBean.put("tagid", null);
		}
		List<LivePackage> livepackages=livePackageMapper.queryAllPackage(tempBean);
		List<LivePackageOutPut> list1  = new ArrayList<>();
		for (LivePackage livePackage : livepackages) {
			LivePackageOutPut livepackageOutput = new LivePackageOutPut();
			livepackageOutput.setPackageCode(livePackage.getPackagecode());
			livepackageOutput.setPackageName(livePackage.getPackagename());
			livepackageOutput.setPackageCover(livePackage.getPackagecover());
			livepackageOutput.setStartTime(livePackage.getStarttime()/1000);
			livepackageOutput.setEndTime(livePackage.getEndtime()/1000);
			livepackageOutput.setChnCode(livePackage.getChncode());
			livepackageOutput.setTagId(livePackage.getTagid());
			livepackageOutput.setTotalSeries(0);
			livepackageOutput.setCurrSeries(0);
			livepackageOutput.setPackageOrder(livePackage.getPackageorder());
			livepackageOutput.setPackagePoster(livePackage.getPackageposter());
			livepackageOutput.setPackageStatus(livePackage.getPackagestats());
			list1.add(livepackageOutput);
		}
	
		return new PageResult<Object>(new DataResult<List<LivePackageOutPut>>(list1.size(), list1));
	}



}
