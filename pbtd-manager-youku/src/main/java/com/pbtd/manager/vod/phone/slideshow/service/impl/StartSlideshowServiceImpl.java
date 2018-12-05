package com.pbtd.manager.vod.phone.slideshow.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.vod.phone.slideshow.domain.StartSlideshow;
import com.pbtd.manager.vod.phone.slideshow.mapper.StartSlideshowMapper;
import com.pbtd.manager.vod.phone.slideshow.query.StartSlideshowQueryObject;
import com.pbtd.manager.vod.phone.slideshow.service.StartSlideshowService;
import com.pbtd.manager.vod.phone.slideshow.util.SlideshowConstant;

@Service
public class StartSlideshowServiceImpl implements StartSlideshowService {
	@Autowired
	private StartSlideshowMapper startSlideshowMapper;

	@Override
	public PageResult queryList(StartSlideshowQueryObject qo) {
		Long count = startSlideshowMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<StartSlideshow> data = startSlideshowMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(StartSlideshow startShow) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		startShow.setUpdateUser(current.getUsername());
		startShow.setUpdateTime(new Date());
		int row = startSlideshowMapper.insert(startShow);
		if (row < 1) {
			throw new JsonMessageException("保存失败！");
		}
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if (id == null) {
			throw new JsonMessageException("id不能为空！");
		}
		StartSlideshow startShow = startSlideshowMapper.queryById(id);
		if (startShow == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		int row = startSlideshowMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("请刷新后重试！");
		}
	}

	@Override
	@Transactional
	public void update(StartSlideshow startShow) {
		if (startShow.getId() == null) {
			throw new JsonMessageException("id不能为空！");
		}
		StartSlideshow newStartShow = startSlideshowMapper.queryById(startShow.getId());
		if (newStartShow == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		startShow.setUpdateUser(current.getUsername());
		startShow.setUpdateTime(new Date());
		int row = startSlideshowMapper.update(startShow);
		if (row < 1) {
			throw new JsonMessageException("请刷新后重试！");
		}
	}

	@Override
	@Transactional
	public void uplineOrDownLine(Integer id, Integer status) {
		if (SlideshowConstant.UPLINE_STATUS.equals(status)) {
			StartSlideshow startShow = startSlideshowMapper.queryByStatus(SlideshowConstant.UPLINE_STATUS);
			if(startShow!=null){
				throw new JsonMessageException("已有上线信息！");
			}
		}
		int row = startSlideshowMapper.uplineOrDownLine(id, status);
		if (row < 1) {
			throw new JsonMessageException("请刷新后重试！");
		}
	}

	@Override
	public StartSlideshow queryById(Integer id) {
		return startSlideshowMapper.queryById(id);
	}

	@Override
	@Transactional
	public void updateImageUrl(Integer id, String url) {
		int row = startSlideshowMapper.updateImageUrl(id, url);
		if (row < 1) {
			throw new JsonMessageException("请重新刷新！");
		}

	}

	@Override
	@Transactional
	public int deleteBatch(List<Integer> ids) {
		return startSlideshowMapper.deleteBatch(ids);
	}

	@Override
	public int insertjson(StartSlideshow startShow) {
		return startSlideshowMapper.insertjson(startShow);
	}
}
