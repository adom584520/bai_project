package com.pbtd.manager.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 绑定关系实体
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BindingInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pUserId;// 手机端用户id

	private String tvUserId;// TV端用户id

	private String pSystem;// 手机端系统类型

	private String tvSystem;// TV端系统类型

	private String pToken;// 手机端token

	private String tvToken;// TV端token

	private String pProName;// 手机端项目名

	private String tvProName;// TV端项目名

	private String pNum;// 手机号

	private String tvMac;// tv端mac地址

	private String pName;// 手机型名

	private String tvName;// TV型号

	private String cpId;// 内容来源ID

	private Date create_time;// 创建时间

	private Date update_time;// 更新时间

	private String status;// 绑定状态

	private String bz;// 备用字段

	@Override
	public String toString() {
		return "PushUser [pUserId=" + pUserId + ", tvUserId=" + tvUserId + ", pSystem=" + pSystem + ", tvSystem="
				+ tvSystem + ", pToken=" + pToken + ", tvToken=" + tvToken + ", pProName=" + pProName + ", tvProName="
				+ tvProName + ", pNum=" + pNum + ", tvMac=" + tvMac + ", pName=" + pName + ", tvName=" + tvName
				+ ", cpId=" + cpId + ", create_time=" + create_time + ", update_time=" + update_time + ", status="
				+ status + ", bz=" + bz + "]";
	}

}