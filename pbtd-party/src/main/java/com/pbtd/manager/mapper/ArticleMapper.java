package com.pbtd.manager.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pbtd.manager.domain.Article;
import com.pbtd.manager.dto.ArticleDTO;
import com.pbtd.manager.query.ArticleQueryObject;

public interface ArticleMapper {
	int insert(Article article);

	List<Article> queryList(ArticleQueryObject qo);

	long queryCount(ArticleQueryObject qo);

	int update(Article article);

	int delete(Long id);

	int deleteBatch(List<Long> ids);

	ArticleDTO queryOne(@Param("classId")Integer classId,@Param("groupId")Long groupId);

	/**
	 * 查询所有当前用户和当前分类下的所有文章时间和对应的id
	 * @param classId
	 * @param macAddress
	 * @return
	 */
	List<String> queryArticleListTime(@Param("classId")Integer classId, @Param("groupId")Long groupId);

	/**
	 * 查询所有当前用户和当前分类下的所有文章时间id
	 * @param classId
	 * @param macAddress
	 * @return
	 */
	List<Long> queryArticleListId(@Param("classId")Integer classId, @Param("groupId")Long groupId);

	ArticleDTO queryArticleById(Long id);
}
