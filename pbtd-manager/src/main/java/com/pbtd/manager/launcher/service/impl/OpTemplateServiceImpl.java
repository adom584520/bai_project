package com.pbtd.manager.launcher.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.launcher.domain.OpTemplate;
import com.pbtd.manager.launcher.domain.OperationPosition;
import com.pbtd.manager.launcher.mapper.OpTemplateMapper;
import com.pbtd.manager.launcher.page.OpTemplateQueryObject;
import com.pbtd.manager.launcher.service.OpTemplateService;
import com.pbtd.manager.launcher.service.OperationPositionService;
import com.pbtd.manager.launcher.util.LauncherConstant;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.util.PageResult;

@Service
public class OpTemplateServiceImpl implements OpTemplateService {
	@Autowired
	private OpTemplateMapper opTemplateMapper;
	@Autowired
	private OperationPositionService operationPositionService;

	@Override
	public PageResult queryList(OpTemplateQueryObject qo) {
		Long count = opTemplateMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<OpTemplate> data = opTemplateMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(OpTemplate temp) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		temp.setCreateTime(new Date());
		temp.setLoginInfoName(current.getUsername());
		temp.setModifyTime(new Date());
		int row = opTemplateMapper.insert(temp);
		if (row < 1) {
			throw new JsonMessageException("保存失败！");
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		OpTemplate op = opTemplateMapper.queryById(id);
		if (op == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		if (LauncherConstant.UPLINE_STATUS.equals(op.getStatus())) {
			throw new JsonMessageException("已上线信息不能删除！");
		}
		int row = opTemplateMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
	}

	@Override
	@Transactional
	public void update(OpTemplate temp) {
		temp.setModifyTime(new Date());
		int row = opTemplateMapper.update(temp);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Override
	public void uplineOrDownLine(Long id, Integer status) {
		if (LauncherConstant.UPLINE_STATUS.equals(status)) {
			OpTemplate op = opTemplateMapper.queryById(id);
			if (op == null) {
				throw new JsonMessageException("请刷新后重试！");
			}
			OpTemplate temp = opTemplateMapper.queryByNavIdUpline(op.getNavId(), LauncherConstant.UPLINE_STATUS);
			if (temp != null) {
				throw new JsonMessageException("该模板所属的导航中已有上线的模板！");
			}
			// 这里有一个BUG，就是上线模板会上线模板下所有的运营位，如果运营位位置相同，也会上线
		}
		operationPositionService.updateStatusByTempId(id, status);
		int row = opTemplateMapper.uplineOrDownLine(id, status, new Date());
		if (row < 1) {
			throw new JsonMessageException("该数据不存在，操作失败！");
		}
	}

	@Override
	@Transactional
	public void deleteBatch(List<Long> ids) {
		int row = opTemplateMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("该数据不存在，操作失败！");
		}
	}

	@Override
	public OpTemplate queryByNavIdUpline(Long navId, Integer status) {
		return opTemplateMapper.queryByNavIdUpline(navId, status);
	}

	@Override
	public List<OpTemplate> queryListByNavId(Long navId) {
		return opTemplateMapper.queryListByNavId(navId);
	}

	@Override
	public OpTemplate queryById(Long id) {
		OpTemplate ot = opTemplateMapper.queryById(id);
		if (ot == null) {
			throw new JsonMessageException("该数据不存在，请刷新后重试！");
		}
		return ot;
	}

	@Override
	@Transactional
	public void copy(OpTemplate temp) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		List<OperationPosition> opList = operationPositionService.queryByTempId(temp.getId());
		temp.setCreateTime(new Date());
		temp.setModifyTime(new Date());
		temp.setId(null);
		temp.setLoginInfoName(current.getUsername());
		temp.setStatus(LauncherConstant.DOWNLINE_STATUS);
		opTemplateMapper.insert(temp);
		if(opList.size()<1){
			return;
		}
		for (int i = 0; i < opList.size(); i++) {
			OperationPosition op = opList.get(i);
			op.setTempId(temp.getId());
			op.setCreateTime(new Date());
			op.setModifyTime(new Date());
			op.setLoginInfoName(current.getUsername());
			op.setGroupId(temp.getGroupId());
			op.setNavId(temp.getNavId());
			op.setStatus(LauncherConstant.DOWNLINE_STATUS);
		}
		operationPositionService.batchInsert(opList);
	}
}