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
public class LiveTag  implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer tagid;

    private String tagname;

    private Integer tagstatus;

    private Integer tagorder;

    private Date createtime;

    private Date updatetime;

 
}