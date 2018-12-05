package com.pbtd.manager.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * cp数据源的信息实体
 * @author JOJO
 *
 */
@Setter
@Getter
@ToString
public class CpInfo {
	private Integer id;
	private String code;// cpCode
	private String name;// cp的名称
	private Integer status;//cp方媒资数据是否可用，1.拥有全部版权，2.拥有点播的版权，3.拥有直播的版权，4.无版权
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
}
