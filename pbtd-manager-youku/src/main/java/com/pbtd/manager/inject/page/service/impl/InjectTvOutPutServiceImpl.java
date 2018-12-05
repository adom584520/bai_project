package com.pbtd.manager.inject.page.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.inject.page.mapper.InjectTvOutPutMapper;
import com.pbtd.manager.inject.page.service.face.InjectTvOutPutService;

@Service
public class InjectTvOutPutServiceImpl implements InjectTvOutPutService {

	@Autowired
	private InjectTvOutPutMapper injectTvOutPutMapper;

	@Override
	public int countHwAlbumsinfovideo(Map<String, Object> queryParams) {
		return injectTvOutPutMapper.countHwAlbumsinfovideo(queryParams);
	} 

	/**
	 * 注入获取剧集接口-HUAWEI
	 */
	@Override
	public List<Map<String, Object>> findHwAlbumsinfovideo(Map<String, Object> queryParams) {
		return injectTvOutPutMapper.findHwAlbumsinfovideo(queryParams);
	}


	@Override
	public int countZxAlbumsinfovideo(Map<String, Object> queryParams) {
		return injectTvOutPutMapper.countZxAlbumsinfovideo(queryParams);
	}

	/**
	 * 注入获取剧集接口-ZHONGXIN
	 */
	@Override
	public List<Map<String, Object>> findZxAlbumsinfovideo(Map<String, Object> queryParams) {
		return injectTvOutPutMapper.findZxAlbumsinfovideo(queryParams);
	}

	/**
	 * 插入调度地址
	 */
	@Override
	public void updateTvInject(Map<String, Object> queryParams) {
		int id = Integer.parseInt((String) queryParams.get("id"));
		System.out.println("插入调度地址的剧集:"+id);
		System.out.println(queryParams);
		if ("hw".equals(queryParams.get("types"))) {
			String HwVersion = injectTvOutPutMapper.selectHwVersion(id);
			if (HwVersion == null || "".equals(HwVersion)) {
				injectTvOutPutMapper.updateTvInject(queryParams);
			}else {
				String[] versions = HwVersion.split(",");
				boolean contains = Arrays.asList(versions).contains(queryParams.get("hwversion"));
				if (!contains) {
					queryParams.put("hwversion",(HwVersion+","+queryParams.get("hwversion")));
					injectTvOutPutMapper.updateTvInject(queryParams);
				}
			}
		}else if ("zx".equals(queryParams.get("types"))) {
			String ZxVersion = injectTvOutPutMapper.selectZxVersion(id);
			if (ZxVersion == null || "".equals(ZxVersion)) {
				injectTvOutPutMapper.updateTvInject(queryParams);
			}else {
				String[] versions = ZxVersion.split(",");
				boolean contains = Arrays.asList(versions).contains(queryParams.get("zxversion"));
				if (!contains) {
					queryParams.put("zxversion",(ZxVersion+","+queryParams.get("zxversion")));
					injectTvOutPutMapper.updateTvInject(queryParams);
				}
			}
		}
	
	}
}
