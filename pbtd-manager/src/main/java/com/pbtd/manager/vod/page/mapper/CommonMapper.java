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
	List<Map<String,Object>>  findbusschannel(Map<String,Object> params);
	List<Map<String,Object>> chooselabeltype(Map<String,Object> params);
	List<Map<String,Object>> choosecpsource(Map<String,Object> params);
	
	

	
	//查询数据集合
		List<Map<String,Object>>  findalbum(Map<String,Object> params);
		//根据条件查询集合
		List<Map<String,Object>>  findalbumhot(Map<String,Object> params);
		//添加专辑管理数据
		int inserthot(Map<String,Object> params);
		
		
		//查询爬取偏移值
		Map<String,Object> getoffset(Map<String,Object> params);
		int updateoffset(Map<String,Object> params);
}
