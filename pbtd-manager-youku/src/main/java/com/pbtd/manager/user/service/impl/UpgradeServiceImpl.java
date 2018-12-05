package com.pbtd.manager.user.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.user.domain.Upgrade;
import com.pbtd.manager.user.mapper.UpgradeMapper;
import com.pbtd.manager.user.page.UpgradeQueryObject;
import com.pbtd.manager.user.service.UpgradeService;
import com.pbtd.manager.user.util.UpgradeConstant;
import com.pbtd.manager.util.PageResult;

@Service
public class UpgradeServiceImpl implements UpgradeService {
	@Autowired
	private UpgradeMapper upgradeMapper;

	@Override
	public PageResult queryList(UpgradeQueryObject qo) {
		Long count = upgradeMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<Upgrade> data = upgradeMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(Upgrade upgrade) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		Upgrade newUpgrade = upgradeMapper.queryByType(upgrade.getType(), UpgradeConstant.UPGRADE_STATUS_NEW);
		if (newUpgrade != null) {
			throw new JsonMessageException("该类型已有版本信息！");
		}
		upgrade.setStatus(UpgradeConstant.UPGRADE_STATUS_NEW);
		upgrade.setLogininfoName(current.getUsername());
		upgrade.setModifyTime(new Date());
		upgrade.setCreateTime(new Date());
		int row = upgradeMapper.insert(upgrade);
		if (row < 1) {
			throw new JsonMessageException("添加失败，请刷新后重试！");
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		int row = upgradeMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败，请刷新后重试！");
		}
	}

	@Override
	@Transactional
	public void update(Upgrade upgrade) {
		upgradeMapper.updateByType(upgrade.getType(), UpgradeConstant.UPGRADE_STATUS_FORMER);
		insert(upgrade);
	}
}