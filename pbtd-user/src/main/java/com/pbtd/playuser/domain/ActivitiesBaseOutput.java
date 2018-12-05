package com.pbtd.playuser.domain;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ActivitiesBaseOutput {
    private String activecode;//活动code
    private String activename;//活动名称
    private Integer activetype;//活动类型
    private Integer activefloat;//活动浮标状态
    public ActivitiesBaseOutput(String  activecode,String activename,Integer activetype,Integer activefloat ) {
		this.activecode = activecode;
		this.activename = activename;
		this.activetype = activetype;
		this.activefloat = activefloat;
	}
}