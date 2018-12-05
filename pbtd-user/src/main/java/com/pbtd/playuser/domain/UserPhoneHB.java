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
public class UserPhoneHB {
    private Integer id;

    private String phoneNo;

    private String regionCode;

    private Integer size;

}