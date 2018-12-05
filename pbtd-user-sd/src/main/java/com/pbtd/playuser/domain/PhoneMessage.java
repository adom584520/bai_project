package com.pbtd.playuser.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * iOS审核状态实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
public class PhoneMessage {
	public static Integer STATUS_IN_AUDIT = 1;
	public static Integer STATUS_NOT_AUDIT = 2;
	public static Integer TYPE_IOS = 1;
	private Long id;
	private Integer status;// 审核状态，1.审核中，2.未审核
	private Integer type;// 手机类型：1.iOS
}
