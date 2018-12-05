package com.pbtd.manager.user.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysCountSeries {
    private Integer id;

    private Date createtime;

    private String seriescode;

    private String seriesname;

    private Float playtime;

    private Integer playcount;

    private Integer playusercount;

    private String channel;

    private String channlename;

}