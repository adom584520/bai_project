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
import com.pbtd.manager.launcher.mapper.OperationPositionMapper;
import com.pbtd.manager.launcher.page.OperationPositionQueryObject;
import com.pbtd.manager.launcher.service.OpTemplateService;
import com.pbtd.manager.launcher.service.OperationPositionService;
import com.pbtd.manager.launcher.util.LauncherConstant;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.util.MyStringUtil;
import com.pbtd.manager.util.PageResult;

@Service
public class OperationPositionServiceImpl implements OperationPositionService {
	@Autowired
	private OperationPositionMapper operationPositionMapper;
	@Autowired
	private OpTemplateService opTemplateService;

	@Override
	public PageResult queryList(OperationPositionQueryObject qo) {
		Long count = operationPositionMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<OperationPosition> data = operationPositionMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(OperationPosition operationPosition) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		String paramKey = operationPosition.getParamKey();
		if (paramKey != null && paramKey.length() > 0) {
			operationPosition.setParamKey(MyStringUtil.stringSliptArrayToString(paramKey));
		}
		String paramValue = operationPosition.getParamValue();
		if (paramValue != null && paramValue.length() > 0) {
			operationPosition.setParamValue(MyStringUtil.stringSliptArrayToString(paramValue));
		}
		operationPosition.setCreateTime(new Date());
		operationPosition.setModifyTime(new Date());
		operationPosition.setStatus(LauncherConstant.DOWNLINE_STATUS);
		operationPosition.setLoginInfoName(current.getUsername());
		int row = operationPositionMapper.insert(operationPosition);
		if (row < 1) {
			throw new JsonMessageException("保存失败！");
		}
	}

	@Override
	@Transactional
	public void delete(Long id) {
		OperationPosition op = operationPositionMapper.queryById(id);
		if (op == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		if (LauncherConstant.UPLINE_STATUS.equals(op.getStatus())) {
			throw new JsonMessageException("已上线信息不能修改！");
		}
		int row = operationPositionMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败！");
		}
	}

	@Override
	@Transactional
	public void update(OperationPosition operationPosition) {
		String paramKey = operationPosition.getParamKey();
		if (paramKey != null && paramKey.length() > 0) {
			operationPosition.setParamKey(MyStringUtil.stringSliptArrayToString(paramKey));
		}
		String paramValue = operationPosition.getParamValue();
		if (paramValue != null && paramValue.length() > 0) {
			operationPosition.setParamValue(MyStringUtil.stringSliptArrayToString(paramValue));
		}
		operationPosition.setModifyTime(new Date());
		int row = operationPositionMapper.update(operationPosition);
		if (row < 1) {
			throw new JsonMessageException("修改失败！");
		}
	}

	@Override
	public void uplineOrDownLine(OperationPosition op) {
		if (LauncherConstant.UPLINE_STATUS.equals(op.getStatus())) {
			OperationPosition tempOp = operationPositionMapper.queryById(op.getId());
			if (tempOp == null) {
				throw new JsonMessageException("该数据不存在，请刷新后重试！");
			}
			OpTemplate temp = opTemplateService.queryByNavIdUpline(tempOp.getNavId(), LauncherConstant.UPLINE_STATUS);
			if (temp != null) {
				if (!tempOp.getTempId().equals(temp.getId())) {
					throw new JsonMessageException("该导航已有上线模板，不能上线其它模板的运营位！");
				}
			}
			OperationPosition newOp = operationPositionMapper.queryByNavIdSortpos(op.getNavId(), op.getSortpos(),
					LauncherConstant.UPLINE_STATUS);
			if (newOp != null) {
				throw new JsonMessageException("该导航相同位置已有上线的运营位！");
			}
		}
		op.setModifyTime(new Date());
		int row = operationPositionMapper.uplineOrDownLine(op);
		if (row < 1) {
			throw new JsonMessageException("该数据不存在，请刷新后重试！");
		}
	}

	@Override
	@Transactional
	public void deleteBatch(List<Long> ids) {
		int row = operationPositionMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("该数据不存在，请刷新后重试！");
		}
	}

	@Override
	@Transactional
	public void updateStatusByTempId(Long tempId, Integer status) {
		operationPositionMapper.updateStatusByTempId(tempId, status, new Date());
	}

	@Override
	public OperationPosition queryById(Long id) {
		OperationPosition op = operationPositionMapper.queryById(id);
		if (op == null) {
			throw new JsonMessageException("该数据不存在，请刷新后重试！");
		}
		return op;
	}

	@Transactional
	public void copy(OperationPosition operationPosition) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		operationPosition.setLoginInfoName(current.getUsername());
		operationPosition.setId(null);
		operationPosition.setCreateTime(new Date());
		operationPosition.setModifyTime(new Date());
		operationPosition.setStatus(LauncherConstant.DOWNLINE_STATUS);
		String paramKey = operationPosition.getParamKey();
		if (paramKey != null && paramKey.length() > 0) {
			operationPosition.setParamKey(MyStringUtil.stringSliptArrayToString(paramKey));
		}
		String paramValue = operationPosition.getParamValue();
		if (paramValue != null && paramValue.length() > 0) {
			operationPosition.setParamValue(MyStringUtil.stringSliptArrayToString(paramValue));
		}
		int row = operationPositionMapper.insert(operationPosition);
		if (row < 1) {
			throw new JsonMessageException("该数据不存在，请刷新后重试！");
		}
	}

	@Override
	public List<OperationPosition> queryByTempId(Long tempId) {
		return operationPositionMapper.queryByTempId(tempId);
	}

	@Override
	@Transactional
	public void batchInsert(List<OperationPosition> opList) {
		int row = operationPositionMapper.batchInsert(opList);
		if (row < 1) {
			throw new JsonMessageException("保存失败！");
		}
		return;
	}

	@Override
	public void updateStatusByNavId(Long navId, Integer status) {
		operationPositionMapper.updateStatusByNavId(navId, status, new Date());
	}

	@Override
	@Transactional
	public void updateDataStatus(List<Long> list, Integer dataStatus) {
		operationPositionMapper.updateDataStatus(list, dataStatus);
	}

	@Override
	@Transactional
	public void updateDataStatusByNavId(List<Long> list, Integer dataStatus) {
		operationPositionMapper.updateDataStatusByNavId(list, dataStatus);
	}
}