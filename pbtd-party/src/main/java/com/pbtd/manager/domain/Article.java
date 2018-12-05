package com.pbtd.manager.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 文章实体
 * 
 * @author JOJO
 *
 */
@Getter
@Setter
public class Article {
	private Long id;
	private String title;// 标题
	private String content;// 内容
	private Integer type;// 类型名称
	private Long groupId;// 分组ID
	private String groupName;// 分组名称
	private List<String> images = new ArrayList<>(10);
	private String image;
	private String logininfoName;// 创建账号名称
	private Date createTime;// 创建时间
	private Date modifyTime;// 修改时间

	public List<String> getImages() {
		return this.image != null && this.image.length() > 0 ? JSON.parseArray(this.image, String.class) : this.images;
	}
}
