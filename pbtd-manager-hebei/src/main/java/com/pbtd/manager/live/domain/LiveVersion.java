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
public class LiveVersion implements Serializable{
	private static final long serialVersionUID = 1L;
    private Integer versionid;

    private String versionname;

    private Integer isplaycontinue;

    private Integer showtype;

    private Integer issupportseqnum;

    private Integer isshowlivelist;

    private String delaytime;

    private Integer state;

    private Date createtime;

    private Date updatetime;

    private Integer isshowchnname;

    private Integer isnumchanage;

    private Date modifytime;

}