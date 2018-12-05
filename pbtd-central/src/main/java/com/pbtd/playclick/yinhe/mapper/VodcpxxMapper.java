package com.pbtd.playclick.yinhe.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.yinhe.domain.Vodcpxx;
import com.pbtd.playclick.yinhe.domain.VodcpxxWithBLOBs;
@Mapper
public interface VodcpxxMapper {
	
    int deleteByPrimaryKey(String albumid);

    int insert(VodcpxxWithBLOBs record);

    int insertSelective(VodcpxxWithBLOBs record);

    VodcpxxWithBLOBs selectByPrimaryKey(String albumid);

    int updateByPrimaryKeySelective(VodcpxxWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(VodcpxxWithBLOBs record);

    int updateByPrimaryKey(Vodcpxx record);
    
    void truncateTable();
}