package com.pbtd.manager.inject.page.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.inject.page.mapper.InjectPhoneOutPutMapper;
import com.pbtd.manager.inject.page.service.face.InjectPhoneOutPutService;

@Service
public class InjectPhoneOutPutServiceImpl implements InjectPhoneOutPutService {

	@Autowired
	private InjectPhoneOutPutMapper injectPhoneOutPutMapper;

	@Override
	public int countHwAlbumsinfovideo(Map<String, Object> queryParams) {
		return injectPhoneOutPutMapper.countHwAlbumsinfovideo(queryParams);
	} 

	/**
	 * 注入获取剧集接口-HUAWEI
	 */
	@Override
	public List<Map<String, Object>> findHwAlbumsinfovideo(Map<String, Object> queryParams) {
		return injectPhoneOutPutMapper.findHwAlbumsinfovideo(queryParams);
	}


	@Override
	public int countZxAlbumsinfovideo(Map<String, Object> queryParams) {
		return injectPhoneOutPutMapper.countZxAlbumsinfovideo(queryParams);
	}

	/**
	 * 注入获取剧集接口-ZHONGXIN
	 */
	@Override
	public List<Map<String, Object>> findZxAlbumsinfovideo(Map<String, Object> queryParams) {
		return injectPhoneOutPutMapper.findZxAlbumsinfovideo(queryParams);
	}

	/**
	 * 插入调度地址
	 */
	@Override
	public void updatePhoneInject(Map<String, Object> queryParams) {
		int id = Integer.parseInt((String) queryParams.get("id"));
		System.out.println("插入调度地址的剧集:"+id);
		System.out.println(queryParams);
		if ("hw".equals(queryParams.get("types"))) {
			String HwVersion = injectPhoneOutPutMapper.selectHwVersion(id);
			if (HwVersion == null || "".equals(HwVersion)) {
				injectPhoneOutPutMapper.updatePhoneInject(queryParams);
			}else {
				String[] versions = HwVersion.split(",");
				boolean contains = Arrays.asList(versions).contains(queryParams.get("hwversion"));
				if (!contains) {
					queryParams.put("hwversion",(HwVersion+","+queryParams.get("hwversion")));
					injectPhoneOutPutMapper.updatePhoneInject(queryParams);
				}
			}
		}else if ("zx".equals(queryParams.get("types"))) {
			String ZxVersion = injectPhoneOutPutMapper.selectZxVersion(id);
			if (ZxVersion == null || "".equals(ZxVersion)) {
				injectPhoneOutPutMapper.updatePhoneInject(queryParams);
			}else {
				String[] versions = ZxVersion.split(",");
				boolean contains = Arrays.asList(versions).contains(queryParams.get("zxversion"));
				if (!contains) {
					queryParams.put("zxversion",(ZxVersion+","+queryParams.get("zxversion")));
					injectPhoneOutPutMapper.updatePhoneInject(queryParams);
				}
			}
		}
	
	}

}
