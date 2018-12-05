package com.pbtd.manager.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 文章图片实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
public class ArticleImage {
	private Long id;
	private String imageUrl;// 图片URL
	private Long articleId;// 文章ID
	private Integer sequence;// 序号
}
