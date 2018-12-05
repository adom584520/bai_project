package com.pbtd.manager.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImageDTO {
	private Long id = 0L;
	private boolean message = true;
	private String picUrl = "";
	private Integer playOrder = 0;
}
