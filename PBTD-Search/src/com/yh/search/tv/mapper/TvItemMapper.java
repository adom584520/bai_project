package com.yh.search.tv.mapper;

import java.util.List;

import com.yh.mybatis.sqlmap.SqlMapper;
import com.yh.search.tv.bean.SearchItem;

public interface TvItemMapper extends SqlMapper {
	List<SearchItem> getItemList();//查所有数据到索引库
	SearchItem getItemById(String id_jmmd);//查单个数据到索引库

}
