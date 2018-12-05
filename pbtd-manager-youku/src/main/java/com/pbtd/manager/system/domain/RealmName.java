package com.pbtd.manager.system.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RealmName {
	private int id	;
	private  String name	;
	private int type	;
	private  String oldtitle	;
	private String   newtitle	;

}
