package com.pbtd.manager.inject.phone.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.manager.inject.phone.domain.InjectPriority;

public interface IInjectPhonePriorityService {

	/**
	 * 统计数量
	 * @param map
	 * @return
	 */
    int count(Map<String,Object> map);
    
    /**
     * 查询列表
     * @param map
     * @return
     */
    List<Map<String,Object>> find(Map<String,Object> map);
     
    /**
     * 查询已录入频道列表
     * @param map
     * @return
     */
    List<Map<String,Object>> findChannels();
    
    /**
     * 查询部分频道列表 除去已保存的
     * @return
     */
    List<Map<String,Object>> findPartChannels();
    
    /**
     * 查询全部频道列表
     * @return
     */
    List<Map<String,Object>> findAllChannels();
    
    /**
     * 保存频道优先级
     * @param map
     * @return
     */
    int savePriority(Map<String,Object> map);
    
    /**
     * 根据Id查询频道优先级列表
     * @param map
     * @return
     */
    Map<String,Object> findById(Map<String,Object> map);
    
    /**
     * 删除频道优先级
     * @param map
     * @return
     */
    int deletePriority(Map<String,Object> map);
    
    /**
     * 更新频道优先级
     * @param map
     * @return
     */
    int updatePriority(Map<String,Object> map);
}
