package com.pbtd.manager.vod.page.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.page.mapper.BussChannelMapper;
import com.pbtd.manager.vod.page.service.face.BussChannelService;

@Service
public class BussChannelServiceImpl implements BussChannelService {
    
	@Autowired
	private BussChannelMapper bussChannelMapper;

	@Override
	public int insert(String channelString) {
		String[] channel = channelString.split(",");
		for (String num : channel) {
			Map<String, Object> map = new HashMap<>();
			try {
				int channelCode = Integer.parseInt(num);
				map.put("channelCode", channelCode);
				map.put("bussId",1);
				map.put("status", 1);
				bussChannelMapper.insert(map);
			} catch (Exception e) {
				System.out.println("添加失败");
				continue;
			}
		}
		return 1;
	}

	@Override
	public int del() {
		return bussChannelMapper.del();
	}
}