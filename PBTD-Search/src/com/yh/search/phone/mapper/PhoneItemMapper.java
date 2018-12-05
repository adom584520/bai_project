package com.yh.search.phone.mapper;

import java.util.List;

import com.yh.mybatis.sqlmap.SqlMapper;
import com.yh.search.phone.bean.PhoneSearchItem;

public interface PhoneItemMapper extends SqlMapper {
	List<PhoneSearchItem> getItemList();//查所有数据到索引库
	PhoneSearchItem getItemById(String id_jmmd);//查单个数据到索引库

}
