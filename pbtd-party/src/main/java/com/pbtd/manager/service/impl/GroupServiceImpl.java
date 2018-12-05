package com.pbtd.manager.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.domain.Group;
import com.pbtd.manager.domain.LoginInfo;
import com.pbtd.manager.domain.Mac;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.mapper.GroupMapper;
import com.pbtd.manager.mapper.MacMapper;
import com.pbtd.manager.query.GroupQueryObject;
import com.pbtd.manager.service.GroupService;
import com.pbtd.manager.util.LoginInfoContext;
import com.pbtd.manager.util.PageResult;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	private MacMapper macMapper;
	@Autowired
	private GroupMapper groupMapper;

	@Override
	public PageResult queryList(GroupQueryObject qo) {
		String macName = qo.getMacName();
		List<Long> groupIds = null;
		if (macName != null) {
			groupIds = macMapper.queryListGroupIdByMacName(macName);
		}
		if (groupIds != null && groupIds.size() > 0) {
			qo.setGroupIds(groupIds);
		}
		Long count = groupMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<Group> data = groupMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(Group group) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("登录信息失效，请重新登录！");
		}
		group.setCreateTime(new Date());
		group.setLoginInfoName(current.getUsername());
		group.setModifyTime(new Date());
		int row = groupMapper.insert(group);
		if (row < 1) {
			throw new JsonMessageException("添加失败！");
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		int row = groupMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败，请刷新后重试！");
		}
		List<Long> groupIds = new ArrayList<>();
		groupIds.add(id);
		macMapper.deleteByGroupIdList(groupIds);
		List<Long> ids = new ArrayList<>();
		ids.add(id);
	}

	@Override
	public void update(Group group) {
		group.setModifyTime(new Date());
		int row = groupMapper.update(group);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Override
	public List<Group> queryGroupListAll() {
		return groupMapper.queryGroupListAll();
	}

	@Override
	public Group queryById(Long id) {
		return groupMapper.queryById(id);
	}

	@Override
	@Transactional
	public void deleteBatch(List<Long> ids) {
		int row = groupMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
		macMapper.deleteByGroupIdList(ids);
	}

	@Override
	public Group queryByMac(String mac) {
		Mac newMac = macMapper.getMacByMacName(mac);
		if(newMac!=null){
			Group group = groupMapper.queryById(newMac.getGroupId());
			if(group!=null){
				return group;
			}
			return null;
		}
		return null;
	}
}
