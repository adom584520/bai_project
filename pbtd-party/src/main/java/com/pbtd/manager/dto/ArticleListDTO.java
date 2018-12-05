package com.pbtd.manager.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleListDTO {
	private ArticleDTO article;
	private List<Long> ids = new ArrayList<>(10);
	private List<String> publishTimes = new ArrayList<>(10);
}
