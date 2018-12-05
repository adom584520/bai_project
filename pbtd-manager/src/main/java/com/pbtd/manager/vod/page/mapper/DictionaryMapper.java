package  com.pbtd.manager.vod.page.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.page.domain.Dictionary;

@Mapper
public interface DictionaryMapper  {

	/**
	 * 查询数据字典
	 * @param params 查询参数
	 * @return
	 */
	List<Dictionary> choosechannel(Map<String,Object> params);
	List<Dictionary> choosechanneltv(Map<String,Object> params);
	List<Dictionary> chooselabel(Map<String,Object> params);
	List<Dictionary> chooselabeltv(Map<String,Object> params);
	List<Dictionary> choosebusschannel(Map<String,Object> params);
	List<Dictionary> choosebusschannelno(Map<String,Object> params);
	
}
