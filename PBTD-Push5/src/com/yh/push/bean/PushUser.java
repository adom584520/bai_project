package com.yh.push.bean;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("all")
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class PushUser implements Serializable{
	private String pUserId;

    private String tvUserId;
	
    private String pSystem;

    private String tvSystem;

    private String pToken;

    private String tvToken;

    private String pProName;

    private String tvProName;

    private String pNum;

    private String tvMac;

    private String pName;

    private String tvName;

    private String cpId;

    private Date create_time;

    private Date update_time;

    private String status;

    private String bz;

	@Override
	public String toString() {
		return "PushUser [pUserId=" + pUserId + ", tvUserId=" + tvUserId + ", pSystem=" + pSystem + ", tvSystem="
				+ tvSystem + ", pToken=" + pToken + ", tvToken=" + tvToken + ", pProName=" + pProName + ", tvProName="
				+ tvProName + ", pNum=" + pNum + ", tvMac=" + tvMac + ", pName=" + pName + ", tvName=" + tvName
				+ ", cpId=" + cpId + ", create_time=" + create_time + ", update_time=" + update_time + ", status="
				+ status + ", bz=" + bz + "]";
	}
    
    

}