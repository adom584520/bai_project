package com.pbtd.playclick.guoguang.domain;

import java.io.Serializable;

import com.pbtd.playclick.page.QueryObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * GgChannel( ) 表的映射
 *   
 * @author zr
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class GgChannel extends QueryObject implements Serializable  {
    private static final long serialVersionUID = 1L;
    private Integer chnId;
    private String chnName;
    private Integer  level;
    private String bz;
    private String status;

   
       
}
