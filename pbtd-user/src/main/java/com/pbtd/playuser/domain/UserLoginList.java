package com.pbtd.playuser.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/***
 * 
 * @author YangF  登录记录流水表
 *
 */
@Setter
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class UserLoginList {
    private Integer id;

    private String usermobile;

    private Integer sourceflag;

    private Date creattime;
    
    public UserLoginList(String  usermobile,Integer sourceflag) {
  		this.usermobile = usermobile;
  		this.sourceflag = sourceflag;
  	}
}