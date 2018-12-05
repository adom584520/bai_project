package com.pbtd.manager.launcher.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.launcher.domain.Navigation;
import com.pbtd.manager.launcher.mapper.NavigationMapper;
import com.pbtd.manager.launcher.page.NavigationQueryObject;
import com.pbtd.manager.launcher.service.NavigationService;
import com.pbtd.manager.launcher.util.LauncherConstant;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.util.MyStringUtil;
import com.pbtd.manager.util.PageResult;

@Service
public class NavigationServiceImpl implements NavigationService {
	@Autowired
	private NavigationMapper navigationMapper;

	@Override
	public PageResult queryList(NavigationQueryObject qo) {
		Long count = navigationMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<Navigation> data = navigationMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(Navigation nav) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		String paramKey = nav.getParamKey();
		if (paramKey != null && paramKey.length() > 0) {
			nav.setParamKey(MyStringUtil.stringSliptArrayToString(paramKey));
		}
		String paramValue = nav.getParamValue();
		if (paramValue != null && paramValue.length() > 0) {
			nav.setParamValue(MyStringUtil.stringSliptArrayToString(paramValue));
		}
		nav.setCreateTime(new Date());
		nav.setLoginInfoName(current.getUsername());
		nav.setModifyTime(new Date());
		nav.setStatus(LauncherConstant.DOWNLINE_STATUS);
		int row = navigationMapper.insert(nav);
		if (row < 1) {
			throw new JsonMessageException("保存失败！");
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// 已上线信息不能删除
		Navigation nav = navigationMapper.queryById(id);
		if (nav == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		if (nav.getStatus() == null || LauncherConstant.UPLINE_STATUS.equals(nav.getStatus())) {
			throw new JsonMessageException("已上线信息不能删除！");
		}
		int row = navigationMapper.delete(id, LauncherConstant.DOWNLINE_STATUS);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
	}

	@Override
	@Transactional
	public void update(Navigation nav) {
		String paramKey = nav.getParamKey();
		if (paramKey != null && paramKey.length() > 0) {
			nav.setParamKey(MyStringUtil.stringSliptArrayToString(paramKey));
		}
		String paramValue = nav.getParamValue();
		if (paramValue != null && paramValue.length() > 0) {
			nav.setParamValue(MyStringUtil.stringSliptArrayToString(paramValue));
		}
		nav.setModifyTime(new Date());
		int row = navigationMapper.update(nav);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Override
	@Transactional
	public void uplineOrDownLine(Navigation nav) {
		if (nav.getStatus().equals(LauncherConstant.DOWNLINE_STATUS)
				|| nav.getStatus().equals(LauncherConstant.UPLINE_STATUS)) {
			// 如果是上线，判断当前分组下是否相同位置的已上线导航
			if (nav.getStatus().equals(LauncherConstant.UPLINE_STATUS)) {
				Navigation newNav = navigationMapper.queryByGroupIdSortpos(nav.getGroupId(), nav.getSortpos(),
						LauncherConstant.UPLINE_STATUS);
				if (newNav != null) {
					throw new JsonMessageException("该分组相同导航位置上已有已上线的导航！");
				}
			}
			Navigation newNav = new Navigation();
			newNav.setId(nav.getId());
			newNav.setStatus(nav.getStatus());
			newNav.setModifyTime(new Date());
			int row = navigationMapper.uplineOrDownLine(newNav);
			if (row < 1) {
				throw new JsonMessageException("修改失败！");
			}
			return;
		}
		throw new JsonMessageException("修改失败！");
	}

	@Override
	public List<Navigation> queryListByGroupId(Long groupId) {
		List<Navigation> data = navigationMapper.queryListByGroupId(groupId);
		if (data.size() < 1) {
			throw new JsonMessageException("无数据！");
		}
		return data;
	}

	@Override
	public void deleteBatch(List<Long> ids) {
		int row = navigationMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("删除失败,请刷新后重试！");
		}
	}
}