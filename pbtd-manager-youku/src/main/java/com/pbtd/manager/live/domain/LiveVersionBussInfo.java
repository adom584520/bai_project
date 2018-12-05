package com.pbtd.manager.live.domain;

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
public class LiveVersionBussInfo {
    private Integer id;

    private Integer bussid;

    private Integer versionid;

    private Date createtime;

    private Date updatetime;
    
    private String  name;
    
    private String versionname;

}