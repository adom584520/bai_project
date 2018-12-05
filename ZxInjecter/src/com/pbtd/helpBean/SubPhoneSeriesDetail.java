package com.pbtd.helpBean;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * phone
 * 分平台Map对应Bean
 * @author jiaren
 *
 */
@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class SubPhoneSeriesDetail {
	
	private Integer id;
	private String cpseriesCode;
	private Integer seriesCode;  // seriesCode+drama  唯一
	private String dramaname;
	private Integer drama;
	private String zxfileurl;
	private String createtime;  //对应到本库用Date类型
	
	private String zxversionlist;  //码率  2,4,5
	
	private Integer hwInjectState;
	private Integer hwPriority;
	
	private Integer zxInjectState;
	private Integer zxPriority;
	
}
