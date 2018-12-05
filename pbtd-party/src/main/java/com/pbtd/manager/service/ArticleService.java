package com.pbtd.manager.service;

import java.util.List;

import com.pbtd.manager.domain.Article;
import com.pbtd.manager.dto.ArticleDTO;
import com.pbtd.manager.dto.ArticleListDTO;
import com.pbtd.manager.query.ArticleQueryObject;
import com.pbtd.manager.util.PageResult;

public interface ArticleService {
	/**
	 * 高级查询加分页
	 * @param qo
	 * @return
	 */
	PageResult queryList(ArticleQueryObject qo);

	/**
	 * 添加article
	 * @param mac
	 */
	void insert(Article article);

	/**
	 * 删除article
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 修改article
	 * @param id
	 */
	void update(Article article);

	/**
	 * 批量删除
	 * @param ids
	 */
	void deleteBatch(List<Long> ids);

	ArticleListDTO queryArticleList(Integer classId, Long groupId);

	ArticleDTO queryArticleById(Long id);
}
