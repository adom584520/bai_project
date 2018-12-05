package com.pbtd.manager.launcher.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 图片实体类
 * @author JOJO
 *
 */
@Setter@Getter
public class BaseImage  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String imageUrl;
	private Integer sort;
}
