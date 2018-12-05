package com.pbtd.playclick.integrate.domain;

import java.io.Serializable;

import com.pbtd.playclick.page.QueryObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
	public class Cpsource extends QueryObject implements Serializable{
		private static final long serialVersionUID = 1L;
	private String code;
	private String name;
	private String bz;
	private int status;
	private int id;
}
