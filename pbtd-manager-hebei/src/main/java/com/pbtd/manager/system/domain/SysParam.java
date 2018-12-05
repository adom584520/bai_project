package com.pbtd.manager.system.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统参数实体类
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysParam {

	private Integer id;

    private Integer platform;

    private String keydata;

    private String keyname;

    private String valuedata;

    private String valuename;

    private Integer status;

    private String paramdescribe;

  
}