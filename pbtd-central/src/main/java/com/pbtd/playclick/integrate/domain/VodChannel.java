package com.pbtd.playclick.integrate.domain;

import java.io.Serializable;
import java.util.Date;

import com.pbtd.playclick.page.QueryObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * VodChannel( ) 表的映射
 *   
 * @author admin
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class VodChannel extends QueryObject implements Serializable  {
    private static final long serialVersionUID = 1L;
    // 
    private Integer id;
    //父级id
    private String parentcode;
    //父级name
    private String parentname;
    //级别
    private Integer levels;
    // 
    private String categorycode;
    //名称
    private String name;
    //别名
    private String aliasname;
    // 
    private String description;
    // 
    private String servicegroupcode;
    // 
    private String packagecodes;
    // 
    private Integer packagecount;
    //排序
    private Integer sequence;
    // 
    private Integer bj;
    // 
    private Integer status;
    //状态
    private String bz;
    //创建日期
    private Date starttime;
    //创建用户
    private String staruser;
    //更新日期
    private Date updatetime;
    //更新用户
    private String updateuser;
    private Integer type;

       
}
