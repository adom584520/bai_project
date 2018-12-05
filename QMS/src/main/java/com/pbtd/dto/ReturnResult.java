package com.pbtd.dto;

import com.huawei.cimp.bean.ResultBean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class ReturnResult {

	private int code;
	private String message ;
	//private int existCode;
	private ResultBean data;
	
	
}
