package com.pbtd.manager.vod.tv.search.domain;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 添加到专辑提示库
 * @author 程先生
 *
 */
@SuppressWarnings("all")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class PingYinSearch implements Serializable {

	//节目集名称
    private String seriesName;
    //节目集code
    private String seriesCode;
	//拼音
    private String pinyin;
    //拼音缩写
    private String pinyinsuoxie;
    //专辑状态
    private Long zt;    
	

	
}
