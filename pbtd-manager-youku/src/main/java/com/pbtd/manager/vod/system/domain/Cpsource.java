package com.pbtd.manager.vod.system.domain;

import java.io.Serializable;

import com.pbtd.manager.vod.page.QueryObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
	public class Cpsource extends QueryObject implements Serializable  {
	private String code;
	private String name;
	private String bz;
	private int status;
	private int id;
}
