package com.pbtd.manager.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.manager.domain.Article;
import com.pbtd.manager.domain.LoginInfo;
import com.pbtd.manager.dto.ArticleDTO;
import com.pbtd.manager.dto.ArticleListDTO;
import com.pbtd.manager.exception.JsonMessageException;
import com.pbtd.manager.mapper.ArticleMapper;
import com.pbtd.manager.query.ArticleQueryObject;
import com.pbtd.manager.service.ArticleService;
import com.pbtd.manager.util.LoginInfoContext;
import com.pbtd.manager.util.PageResult;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleMapper articleMapper;

	@Override
	public PageResult queryList(ArticleQueryObject qo) {
		long count = articleMapper.queryCount(qo);
		if (count < 1) {
			return new PageResult(count, Collections.EMPTY_LIST);
		}
		List<Article> data = articleMapper.queryList(qo);
		return new PageResult(count, data);
	}

	@Override
	@Transactional
	public void insert(Article article) {
		LoginInfo current = LoginInfoContext.getCurrent();
		if (current == null) {
			throw new JsonMessageException("登录信息已失效，请重新登录！");
		}
		article.setCreateTime(new Date());
		article.setModifyTime(new Date());
		article.setLogininfoName(current.getUsername());
		articleMapper.insert(article);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		articleMapper.delete(id);
	}

	@Override
	@Transactional
	public void update(Article article) {
		article.setModifyTime(new Date());
		articleMapper.update(article);

	}

	@Override
	@Transactional
	public void deleteBatch(List<Long> ids) {
		articleMapper.deleteBatch(ids);
	}

	@Override
	public ArticleListDTO queryArticleList(Integer classId, Long groupId) {
		ArticleDTO dto = articleMapper.queryOne(classId, groupId);
		List<Long> ids = articleMapper.queryArticleListId(classId, groupId);
		List<String> times = articleMapper.queryArticleListTime(classId, groupId);
		ArticleListDTO articleListDTO = new ArticleListDTO();
		if (dto != null) {
			articleListDTO.setArticle(dto);
			if (ids != null) {
				articleListDTO.setIds(ids);
			}
			if (times != null) {
				articleListDTO.setPublishTimes(times);
			}
			return articleListDTO;
		}
		return null;
	}

	@Override
	public ArticleDTO queryArticleById(Long id) {
		return articleMapper.queryArticleById(id);
	}
}
