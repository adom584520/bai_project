package com.pbtd.manager.vod.system.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movieurl  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String title;
	private int status;
	private int levels;

}
