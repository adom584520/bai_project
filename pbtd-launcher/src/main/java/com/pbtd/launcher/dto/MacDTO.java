package com.pbtd.launcher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MacDTO {
	private Long id;
	private String macName; // mac名称
	private Long groupId; // 分组id
}
