package com.pbtd.manager.live.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class LiveGroup implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer groupid;

    private String groupname;

    private Integer grouporder;

    private Date createtime;

    private Date updatetime;

    private Integer isdelete;


}