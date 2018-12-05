package com.pbtd.manager.vod.common.mould.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class vodMasterplateSon implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;

	private Integer masterplateid;

	private Integer count;

	private Integer masterplatenum;
	
	private String  masterplatename;
	
	private String  name;
}