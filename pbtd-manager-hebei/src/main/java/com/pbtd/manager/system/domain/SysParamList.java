package com.pbtd.manager.system.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统参数详细的实体类
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysParamList {
    private Integer id;

    private Integer flag;

    private String keydata;

    private String keyname;

    private String valuedata;

    private String valuename;
}