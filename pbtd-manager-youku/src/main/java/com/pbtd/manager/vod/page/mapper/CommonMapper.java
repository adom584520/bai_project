package  com.pbtd.manager.vod.page.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.manager.vod.page.domain.Dictionary;

@Mapper
public interface CommonMapper  {
	List<Map<String,Object>> choosechannel(Map<String,Object> params);
	List<Map<String,Object>> choosechanneltv(Map<String,Object> params);
	List<Map<String,Object>> chooselabel(Map<String,Object> params);
	List<Map<String,Object>> chooselabeltv(Map<String,Object> params);
	List<Map<String,Object>> choosecorner(Map<String,Object> params);
	List<Map<String,Object>> choosecollectfeesbag(Map<String,Object> params);
	List<Map<String,Object>> chooselabeltype(Map<String,Object> params);
	List<Map<String,Object>> choosesourcetype(Map<String,Object> params);
	
}
