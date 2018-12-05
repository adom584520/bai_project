package com.yh.search.phone.mapper;

import java.util.List;

import com.yh.mybatis.sqlmap.SqlMapper;
import com.yh.search.phone.bean.PingYinSearch;

public interface PingYinMapper extends SqlMapper {
	List<PingYinSearch> getItemList();//查所有数据到索引库
	PingYinSearch getItemById(String seriesCode);//查单个数据到索引库

}
