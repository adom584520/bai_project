package com.pbtd.playclick.pinyin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.pbtd.playclick.pinyin.domain.Ypinyin;
import com.pbtd.playclick.pinyin.domain.Zpinyin;

public interface PinYinMapper {

	List<Ypinyin> selectYpinyin();

	int updateYpinyin(@Param("pinyin")String pinyin,@Param("pinyinsuoxie")String pinyinsuoxie,@Param("code")String code);

	List<Zpinyin> selectZpinyin();

	int updateZpinyin(@Param("pinyin")String pinyin,@Param("pinyinsuoxie")String pinyinsuoxie,@Param("seriesCode")String seriesCode);

	

}
