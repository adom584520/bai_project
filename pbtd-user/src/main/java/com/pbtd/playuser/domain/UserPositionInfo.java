package com.pbtd.playuser.domain;

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
public class UserPositionInfo {
    private Integer id;

    private Long mobilenum;

    private Long channelid;

    private String channelname;

    private Integer channelspot;

    private Integer wholeid;

    private String wholename;

    private String wholedescribe;
    
    private Integer wholespot;

    private String positionnum;

    private String positioncode;

    private String describe;

    private Date createtime;

}