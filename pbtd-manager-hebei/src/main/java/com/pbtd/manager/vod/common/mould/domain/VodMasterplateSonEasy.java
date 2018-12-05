package com.pbtd.manager.vod.common.mould.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter@Getter@NoArgsConstructor@AllArgsConstructor
public class VodMasterplateSonEasy implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer bj;
	private Integer count;
	private Integer masterplatenum;
}