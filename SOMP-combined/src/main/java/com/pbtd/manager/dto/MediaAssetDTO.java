package com.pbtd.manager.dto;

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
public class MediaAssetDTO {
	private String code;// 专辑code
	private String name;// 专辑名称
	private Integer volumnCount;// 总剧集
	private Integer currentNum;// 当前更新剧集
	private String status;
}
