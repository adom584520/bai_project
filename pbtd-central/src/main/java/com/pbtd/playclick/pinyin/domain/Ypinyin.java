package com.pbtd.playclick.pinyin.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Ypinyin implements Serializable{
	private static final long serialVersionUID = 1L;
	//演员code
	private String code;
	//演员名称
	private String name;
	  //拼音
    private String pinyin;
    //拼音缩写
    private String pinyinsuoxie;
}
