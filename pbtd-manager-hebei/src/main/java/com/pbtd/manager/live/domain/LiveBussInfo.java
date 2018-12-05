package com.pbtd.manager.live.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;




/**
 * 商家基本信息com.pbtd.manager.live.domain.LiveBuss
 * @author dzy
 *
 */
@Setter
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class LiveBussInfo implements Serializable {
	private static final long serialVersionUID = 1L;
    private Integer bussid;

    private Integer groupid;

    private String name;

    private String address;

    private String bussuser;

    private String bussphone;

    private Date createtime;

    private Date updatetime;

    private String userid;

    private String parentid;

  
}