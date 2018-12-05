package com.pbtd.manager.launcher.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.launcher.domain.LauncherGroup;
import com.pbtd.manager.launcher.mapper.LauncherGroupMapper;
import com.pbtd.manager.launcher.mapper.MacMapper;
import com.pbtd.manager.launcher.page.LauncherGroupQueryObject;
import com.pbtd.manager.launcher.service.LauncherGroupService;
import com.pbtd.manager.launcher.service.LauncherVersionService;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.util.PageResult;

@Service
public class LauncherGroupServiceImpl implements LauncherGroupService {
	@Autowired
	private MacMapper macMapper;
	@Autowired
	private LauncherGroupMapper launcherGroupMapper;
	@Autowired
	private LauncherVersionService launcherVersionService;

	@Override
	public PageResult queryList(LauncherGroupQueryObject qo) {
		String macName = qo.getMacName();
		List<Long> groupIds = null;
		if(macName!=null){
			groupIds = macMapper.queryListGroupIdByMacName(macName);
		}
		if(groupIds!=null&&groupIds.size()>0){
			qo.setGroupIds(groupIds);
		}
		Long count = launcherGroupMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<LauncherGroup> data = launcherGroupMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(LauncherGroup launcherGroup) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		launcherGroup.setCreateTime(new Date());
		launcherGroup.setLoginInfoName(current.getUsername());
		launcherGroup.setModifyTime(new Date());
		int row = launcherGroupMapper.insert(launcherGroup);
		if (row < 1) {
			throw new JsonMessageException("添加失败！");
		}
		launcherVersionService.insert(launcherGroup.getId());
	}

	@Override
	@Transactional
	public void delete(Long id) {
		int row = launcherGroupMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
	}

	@Override
	public void update(LauncherGroup launcherGroup) {
		launcherGroup.setModifyTime(new Date());
		int row = launcherGroupMapper.update(launcherGroup);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Override
	public List<LauncherGroup> queryGroupListAll() {
		return launcherGroupMapper.queryGroupListAll();
	}

	@Override
	public LauncherGroup queryById(Long id) {
		return launcherGroupMapper.queryById(id);
	}

	@Override
	@Transactional
	public void deleteBatch(List<Long> ids) {
		int row = launcherGroupMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
	}
}
