package com.pbtd.manager.vod.phone.album.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VodAlbuminfoStatusMapper  {
 
    
 
    int updatestatus(Map<String,Object> m);
     
    int updatepaid(Map<String,Object> m);
 
}
