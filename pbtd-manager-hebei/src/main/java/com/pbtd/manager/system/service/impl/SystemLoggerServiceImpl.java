package com.pbtd.manager.system.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.domain.SystemLogger;
import com.pbtd.manager.system.mapper.SystemLoggerMapper;
import com.pbtd.manager.system.page.SystemLoggerQueryObject;
import com.pbtd.manager.system.service.SystemLoggerService;
import com.pbtd.manager.system.util.LoginInfoConstant;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.system.util.MyDateUtil;
import com.pbtd.manager.util.PageResult;

@Service
public class SystemLoggerServiceImpl implements SystemLoggerService {
	private static final Logger logger = LoggerFactory.getLogger(SystemLoggerServiceImpl.class);
	@Autowired
	private SystemLoggerMapper systemLoggerMapper;

	@Override
	public PageResult queryList(SystemLoggerQueryObject qo) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("登录信息失效，请重新登录！");
		}
		// 当账号是超级管理员，直接查询所有的角色信息，不是则查询该账号拥有的所有角色信息
		if (!(LoginInfoConstant.ADMIN_SYSTEM_MANAGER.equals(current.getLevel()))) {
			qo.setLoginInfoName(current.getUsername());
		}
		Long count = systemLoggerMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<SystemLogger> data = systemLoggerMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	public void insert(SystemLogger logger) {
		logger.setCreateTime(new Date());
		int row = systemLoggerMapper.insert(logger);
		if (row < 1) {
			throw new JsonMessageException("保存失败！");
		}
	}

	@Override
	public void delete(Long id) {
		int row = systemLoggerMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("删除失败，请刷新后重试！");
		}
	}

	@Override
	public void deleteBatch(List<Long> ids) {
		int row = systemLoggerMapper.deleteBatch(ids);
		if (row < 1) {
			throw new JsonMessageException("删除失败，请刷新后重试！");
		}
	}

	@Override
	public void deleteCondition(SystemLoggerQueryObject qo) {
		if(qo.getStartTime()==null&&qo.getEndTime()==null){
			throw new JsonMessageException("请输入删除条件！");
		}
		int row = systemLoggerMapper.deleteCondition(qo);
		if (row < 1) {
			throw new JsonMessageException("删除失败，请刷新后重试！");
		}
	}

	/**
	 * 定时任务，删除当前时间一个月之前的所有数据
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional
	public void deleteTiming() {
		logger.info("系统管理-日志管理-定时删除日志-开始删除");
		SystemLoggerQueryObject qo = new SystemLoggerQueryObject();
		String formatter = "yyyy-MM-dd KK:mm:ss";
		qo.setEndTime(MyDateUtil.getBeforeMonth(formatter));
		try {
			systemLoggerMapper.deleteCondition(qo);
			logger.info("系统管理-日志管理-定时删除日志-删除成功");
		} catch (Exception e) {
			logger.error("系统管理-日志管理-定时删除日志", e);
		}
	}
}
