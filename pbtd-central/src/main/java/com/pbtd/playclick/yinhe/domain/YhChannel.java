package com.pbtd.playclick.yinhe.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * YhChannel( ) 表的映射
 *   
 * @author zr
 */

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class YhChannel implements Serializable  {
	private static final long serialVersionUID = 1L;
	
    private String chnId;
    
    private String chnName;
    
    private String level;
    
    private String bz;
    
    private String status;

   
       
}
