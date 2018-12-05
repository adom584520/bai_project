package com.pbtd.manager.live.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.live.domain.LiveBussChnCodePackage;
import com.pbtd.manager.live.domain.LiveChannel;
import com.pbtd.manager.live.mapper.LiveBussChnCodePackageMapper;
import com.pbtd.manager.live.mapper.LiveChannelMapper;
import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.live.service.ILiveBussChnCodePackageService;

@Service
public class LiveBussChnCodePackageService implements ILiveBussChnCodePackageService{
	@Autowired
	private LiveBussChnCodePackageMapper liveBussChnCodePackageMapper;
	@Autowired
	private LiveChannelMapper liveChannelMapper;
	/**
	 * 查出所有商家信息（表格分页）
	 */
	@Override
	public PageResult querylistallLiveBussChnCodePackage() {
		List<LiveBussChnCodePackage> list = liveBussChnCodePackageMapper.selectByPrimaryKey(null);
		return new PageResult((long) list.size(), list);
	}


	@Override
	public int count(Map<String, Object> queryParams) {
		return this.liveBussChnCodePackageMapper.count(queryParams);
	}

	@Override
	public List<LiveBussChnCodePackage> page(int start, int limit, Map<String, Object> queryParams) {
		return this.liveBussChnCodePackageMapper.page(start, limit, queryParams);
	}

	@Override
	public List<LiveBussChnCodePackage> find(Map<String, Object> queryParams) {
		return this.liveBussChnCodePackageMapper.find(queryParams);
	}
	
	@Override
	public List<LiveChannel> finds(Map<String, Object> queryParams) {
		return this.liveBussChnCodePackageMapper.finds(queryParams);
	}
	@Override
	public int counts(Map<String, Object> queryParams) {
		return this.liveBussChnCodePackageMapper.counts(queryParams);
	}



	@Override
	public LiveBussChnCodePackage load(int id) {
		return this.liveBussChnCodePackageMapper.load(id);
	}
	@Override
	@Transactional
	public int deletes(Map<String, Object> ids) {
		return this.liveBussChnCodePackageMapper.deletes(ids);
	}
	@Override
	@Transactional
	public int creates(Map<String, Object> ids) {
		String bussidstr  = (String) ids.get("bussid");
		int bussid  =  Integer.parseInt(bussidstr);
		String[] id = (String[]) ids.get("id_");
		//获取频道id  和频道code
		for (String string : id) {
			LiveChannel record = new LiveChannel();
			record.setChannelid(Integer.parseInt(string));
			List<LiveChannel> list = new ArrayList<>();
			list = liveChannelMapper.selectByPrimaryKey(record);
			if(list != null  && list.size() > 0){
				ids.put("chncode", list.get(0).getChncode());
				ids.put("channelid",Integer.parseInt(string));
				//生成最大的频道序号
				int num = liveBussChnCodePackageMapper.getMaxBussNum(bussid);
				ids.put("chncodenum",num+1);
				liveBussChnCodePackageMapper.creates(ids);
			}
		}
		
		return 1;
	}

	@Override
	@Transactional
	public int insert(LiveBussChnCodePackage liveBussChnCodePackage) {
		this.liveBussChnCodePackageMapper.insert(liveBussChnCodePackage);
		return liveBussChnCodePackage.getId();
	}
	   @Override
	    @Transactional
	    public int update(LiveBussChnCodePackage liveBussChnCodePackage) {
	        return this.liveBussChnCodePackageMapper.update(liveBussChnCodePackage);
	    }

}
