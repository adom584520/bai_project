package com.pbtd.manager.vod.phone.slideshow.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.system.domain.LoginInfo;
import com.pbtd.manager.system.util.LoginInfoContext;
import com.pbtd.manager.util.PageResult;
import com.pbtd.manager.vod.phone.slideshow.domain.Slideshow;
import com.pbtd.manager.vod.phone.slideshow.mapper.SlideshowMapper;
import com.pbtd.manager.vod.phone.slideshow.query.SlideshowQueryObject;
import com.pbtd.manager.vod.phone.slideshow.service.SlideshowService;

@Service
public class SlideshowServiceImpl implements SlideshowService {
	@Autowired
	private SlideshowMapper slideshowMapper;

	@Override
	public PageResult queryList(SlideshowQueryObject qo) {
		Long count = slideshowMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<Slideshow> data = slideshowMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(Slideshow slideshow) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		slideshow.setUpdateUser(current.getUsername());
		slideshow.setUpdateTime(new Date());
		int row = slideshowMapper.insert(slideshow);
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
		Slideshow newSlideshow = slideshowMapper.queryById(id);
		if (newSlideshow == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		if (Slideshow.UPLINE_STATUS.equals(newSlideshow.getStatus())) {
			throw new JsonMessageException("已上线信息不能修改！");
		}
		int row = slideshowMapper.delete(id);
		if (row < 1) {
			throw new JsonMessageException("请刷新后重试！");
		}
	}

	@Override
	@Transactional
	public void update(Slideshow slideshow) {
		if (slideshow.getId() == null) {
			throw new JsonMessageException("id不能为空！");
		}
		Slideshow newSlideshow = slideshowMapper.queryById(slideshow.getId());
		if (newSlideshow == null) {
			throw new JsonMessageException("请刷新后重试！");
		}
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("账号未登录，请重新登录！");
		}
		slideshow.setUpdateUser(current.getUsername());
		slideshow.setUpdateTime(new Date());
		int row = slideshowMapper.update(slideshow);
		if (row < 1) {
			throw new JsonMessageException("请刷新后重试！");
		}
	}

	@Override
	@Transactional
	public void uplineOrDownLine(List<Integer> ids, Integer status) {
		int row = slideshowMapper.uplineOrDownLine(ids, status);
		if (row < 1) {
			throw new JsonMessageException("请刷新后重试！");
		}
	}

	@Override
	public Slideshow queryById(Integer id) {
		return slideshowMapper.queryById(id);
	}

	@Override
	public void updateImageUrl(Integer id, String url) {
		int row = slideshowMapper.updateImageUrl(id, url);
		if (row < 1) {
			throw new JsonMessageException("请重新刷新！");
		}

	}

	@Override
	public int deleteBatch(List<Integer> ids) {
		return slideshowMapper.deleteBatch(ids);
	}

	@Override
	public long queryCount(SlideshowQueryObject qo) {
		return slideshowMapper.queryCount(qo);
	}

	@Override
	public List<Slideshow> queryListforinterface(SlideshowQueryObject qo) {
		return slideshowMapper.queryListforinterface(qo);
	}

	@Override
	public Map<String, Object> findmaxVSminsequence(Map<String, Object> map) {
		return slideshowMapper.findmaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findsequencesum(Map<String, Object> queryParams) {
		return slideshowMapper.findsequencesum(queryParams);
	}

	@Override
	public List<Map<String, Object>> findsequence(Map<String, Object> map) {
		return slideshowMapper.findsequence(map);
	}
}
