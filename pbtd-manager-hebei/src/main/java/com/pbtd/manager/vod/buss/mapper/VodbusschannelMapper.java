package com.pbtd.manager.vod.buss.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.buss.domain.Vodbusschannel;
@Mapper
public interface VodbusschannelMapper {
	   List<Vodbusschannel> find(Map<String, Object> queryParams);
	   List<Vodbusschannel> find2(Map<String, Object> queryParams);
	   int insert(Vodbusschannel vodbusschannel);
	   int deletes(Map<String, Object> queryParams);
	   int maxbussId(Map<String, Object> queryParams);
}
