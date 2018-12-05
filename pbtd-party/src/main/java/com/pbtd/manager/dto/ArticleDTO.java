package com.pbtd.manager.dto;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleDTO {
	private Long id;
	private String title;
	private Long bussId;
	private Integer classId;
	private String content;
	private String createDate;
	private String images;
	private List<String> pics = new ArrayList<>(10);

	public List<String> getPics() {
		if (this.images != null && this.images.length() > 0) {
			return JSON.parseArray(this.images, String.class);
		}
		return pics;
	}

	public String getClassName() {
		if (this.classId == null) {
			return "";
		} else if (this.classId == 1) {
			return "党务";
		} else if (this.classId == 2) {
			return "村务";
		} else if (this.classId == 3) {
			return "财务务";
		}
		return "";
	}
}
