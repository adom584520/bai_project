package com.pbtd.manager.system.domain;

import java.io.Serializable;

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
public class RealmName  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id	;
	private  String name	;
	private int type	;
	private  String oldtitle	;
	private String   newtitle	;

}
