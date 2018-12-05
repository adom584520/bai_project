package  com.pbtd.playclick.base.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.pbtd.playclick.integrate.domain.Dictionary;

@Mapper
public interface DictionaryMapper  {

	/**
	 * 查询数据字典
	 * @param params 查询参数
	 * @return
	 */
	List<Dictionary> choosechannelphone(Map<String,Object> params);
	List<Dictionary> choosechannelyh(Map<String,Object> params);
	List<Dictionary> choosechannellabelyh(Map<String,Object> params);
	List<Dictionary> choosechannelgg(Map<String,Object> params);
	List<Dictionary> choosechannellabelgg(Map<String,Object> params);
	List<Map<String,Object>> choosename(Map<String,Object> params);
	List<Map<String,Object>> choosecpsource(Map<String,Object> params);
	List<Dictionary> choosechannelguttv(Map<String,Object> params);
	List<Dictionary> choosechannellabelguttv(Map<String,Object> params);
	List<Dictionary> choosechannelyk(Map<String,Object> params);
	List<Dictionary> choosechannellabelyk(Map<String,Object> params);
	List<Dictionary> chooselabeltype(Map<String,Object> params);
}
