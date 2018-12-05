package com.pbtd.playclick.pinyin.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Zpinyin implements Serializable{
	private static final long serialVersionUID = 1L;
	 //节目集名称
    private String seriesName;
    //节目集code
    private String seriesCode;
	  //拼音
    private String pinyin;
    //拼音缩写
    private String pinyinsuoxie;
}
