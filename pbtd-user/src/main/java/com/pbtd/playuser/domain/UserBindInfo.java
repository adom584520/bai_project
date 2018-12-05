package com.pbtd.playuser.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class UserBindInfo {
    private Long id;

    private Long userid;

    private Long loginid;

    private String mac;

    private Long updatetime;

    private Long createtime;

}