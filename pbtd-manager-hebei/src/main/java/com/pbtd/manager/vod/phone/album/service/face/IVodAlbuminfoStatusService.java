package com.pbtd.manager.vod.phone.album.service.face;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IVodAlbuminfoStatusService  {
 
    
 
    int updatestatus(Map<String,Object> m);
     
    int updatepaid(Map<String,Object> m);
 
}
