package com.pbtd.helpBean;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * tv
 * 分平台Map对应Bean
 * @author jiaren
 *
 */
@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class SubTvSeriesDetail {
	
	private Integer id;
	private String cpseriesCode;
	private Integer seriesCode;
	private String dramaname;
	private Integer drama;
	private String zxfileurl;
	private String createtime;
	
	private String hwversionlist;  //华为码率  2,4,5
	
	private Integer hwInjectState;
	private Integer hwPriority;
	
	private Integer zxInjectState;
	private Integer zxPriority;
	
}
