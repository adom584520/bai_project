package com.pbtd.playuser.mapper;

import java.util.List;

import com.pbtd.playuser.domain.ZhuanpanPlayseriescode;

public interface ZhuanpanPlayseriescodeMapper {

	ZhuanpanPlayseriescode selectByPrimaryKey(Integer id);
	List<ZhuanpanPlayseriescode> selectAll();

}