package com.pbtd.manager.launcher.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.launcher.domain.LauncherUI;
import com.pbtd.manager.launcher.mapper.LauncherUIMapper;
import com.pbtd.manager.launcher.page.LauncherUIQueryObject;
import com.pbtd.manager.launcher.service.LauncherUIService;
import com.pbtd.manager.launcher.util.LauncherConstant;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.util.PageResult;

@Service
public class LauncherUIServiceImpl implements LauncherUIService {
	@Autowired
	private LauncherUIMapper launcherUIMapper;

	@Override
	public PageResult queryList(LauncherUIQueryObject qo) {
		Long count = launcherUIMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<LauncherUI> data = launcherUIMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Transactional
	public void insert(LauncherUI launcherUI) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		launcherUI.setLoginInfoName(current.getUsername());
		launcherUI.setStatus(LauncherConstant.DOWNLINE_STATUS);
		launcherUI.setCreateTime(new Date());
		launcherUI.setModifyTime(new Date());
		int row = launcherUIMapper.insert(launcherUI);
		if (row < 1) {
			throw new JsonMessageException("添加失败！");
		}
	}

	@Transactional
	public void delete(Long id) {
		LauncherUI launcherUI = launcherUIMapper.queryLauncherUI(id);
		if (launcherUI == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		if (launcherUI.getStatus() == null || !LauncherConstant.DOWNLINE_STATUS.equals(launcherUI.getStatus())) {
			throw new JsonMessageException("已上线信息不能删除！");
		}
		int row = launcherUIMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
	}

	@Transactional
	public void update(LauncherUI launcherUI) {
		// 已上线信息不能删除
		LauncherUI newUI = launcherUIMapper.queryLauncherUI(launcherUI.getId());
		if (newUI == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		if (newUI.getStatus() == null || LauncherConstant.UPLINE_STATUS.equals(newUI.getStatus())) {
			throw new JsonMessageException("已上线信息不能修改！");
		}
		// 将需要修改的数据状态调整为已下线，防止修改分组时有两个已上线信息(前提是修改时可以重新选择分组)
		launcherUI.setStatus(LauncherConstant.DOWNLINE_STATUS);
		launcherUI.setModifyTime(new Date());
		int row = launcherUIMapper.update(launcherUI);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Transactional
	public void uplineOrDownline(Long id, Long groupId, Integer status) {
		if (status == LauncherConstant.UPLINE_STATUS) {
			LauncherUI launcherUI = launcherUIMapper.queryGroupDetailByStatus(groupId, status);
			if (launcherUI != null) {
				throw new JsonMessageException("该分组已有上线信息！");
			}
		}
		LauncherUI launcherUI = new LauncherUI();
		launcherUI.setModifyTime(new Date());
		launcherUI.setStatus(status);
		launcherUI.setId(id);
		int row = launcherUIMapper.uplineOrDownline(launcherUI);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Override
	public LauncherUI queryById(Long id) {
		return launcherUIMapper.queryById(id);
	}

	@Transactional
	public void deleteBatch(List<Long> ids) {
		int row = launcherUIMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}
}