package com.pbtd.manager.launcher.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.launcher.domain.Advertisement;
import com.pbtd.manager.launcher.mapper.AdvertisementMapper;
import com.pbtd.manager.launcher.page.AdvertisementQueryObject;
import com.pbtd.manager.launcher.service.AdvertisementService;
import com.pbtd.manager.launcher.service.LauncherVersionService;
import com.pbtd.manager.launcher.util.LauncherConstant;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.util.PageResult;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {
	@Autowired
	private AdvertisementMapper advertisementMapper;
	@Autowired
	private LauncherVersionService launcherVersionService;

	@Override
	public PageResult queryList(AdvertisementQueryObject qo) {
		Long count = advertisementMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<Advertisement> data = advertisementMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(Advertisement adv) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		adv.setCreateTime(new Date());
		adv.setLoginInfoName(current.getUsername());
		adv.setModifyTime(new Date());
		adv.setStatus(LauncherConstant.DOWNLINE_STATUS);
		int row = advertisementMapper.insert(adv);
		if (row < 1) {
			throw new JsonMessageException("保存失败,请刷新后重试！");
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Advertisement adv = advertisementMapper.queryById(id);
		if (adv == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		if (adv.getStatus() == null || LauncherConstant.UPLINE_STATUS.equals(adv.getStatus())) {
			throw new JsonMessageException("上线广告不能删除！");
		}
		int row = advertisementMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败,请刷新后重试！");
		}
	}

	@Override
	@Transactional
	public void update(Advertisement adv) {
		if (adv.getId() == null) {
			throw new JsonMessageException("id不能为空！");
		}
		// 判断需要修改的信息是否处于上线状态
		Advertisement newAdv = advertisementMapper.queryById(adv.getId());
		if (newAdv == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		if (LauncherConstant.UPLINE_STATUS.equals(newAdv.getStatus())) {
			throw new JsonMessageException("已上线信息不能修改！");
		}
		adv.setModifyTime(new Date());
		int row = advertisementMapper.update(adv);
		if (row < 1) {
			throw new JsonMessageException("修改失败,请刷新后重试！");
		}
	}

	@Override
	@Transactional
	public void uplineOrDownLine(Long id,Integer status) {
		Advertisement target = advertisementMapper.queryById(id);
		if(target==null){
			throw new JsonMessageException("操作失败，请刷新后重试！");
		}
		if (status.equals(LauncherConstant.DOWNLINE_STATUS) || status.equals(LauncherConstant.UPLINE_STATUS)) {
			if(status.equals(LauncherConstant.UPLINE_STATUS)){
				//一个分组下，只能有一个类型的广告
				Advertisement newAdv = advertisementMapper.queryByGroupIdAndType(target.getGroupId(), target.getType(),LauncherConstant.UPLINE_STATUS);
				if (newAdv != null) {
					throw new JsonMessageException("该分组下已有同类型的广告位上线！");
				}
			}
			Advertisement adv = new Advertisement();
			adv.setId(id);
			adv.setStatus(status);
			adv.setModifyTime(new Date());
			int row = advertisementMapper.uplineOrDownLine(adv);
			if (row < 1) {
				throw new JsonMessageException("修改失败,请刷新后重试！");
			}
			
			//更新版本号  group_id + type   确定同一条记录
			if(LauncherConstant.ADVERTISEMENT_TYPE_LAUNCHER_START.equals(target.getType())){
				launcherVersionService.update(target.getGroupId(),LauncherConstant.LAUNCHER_VERSION_TYPE_START_LAUNCHER);
			}
			if(LauncherConstant.ADVERTISEMENT_TYPE_STARTING_UP.equals(target.getType())){
				launcherVersionService.update(target.getGroupId(),LauncherConstant.LAUNCHER_VERSION_TYPE_START_ADV);
			}
			return;
		}
		throw new JsonMessageException("修改失败！");
	}

	@Override
	public void deleteBatch(List<Long> ids) {
		int row = advertisementMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("删除失败,请刷新后重试！");
		}
	}
	@Override
	@Transactional
	public void updateDataStatus(List<Long> list, Integer dataStatus) {
		advertisementMapper.updateDataStatus(list,dataStatus);
	}
}