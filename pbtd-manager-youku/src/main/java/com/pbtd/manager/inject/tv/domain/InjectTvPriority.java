package com.pbtd.manager.inject.tv.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 注入优先级实体bean
 * @author Administrator
 *
 */
@Getter@Setter@NoArgsConstructor@AllArgsConstructor
public class InjectTvPriority {
	private int id	;  //主键
	private int priority;	//优先级
	private int channel_id	;	//关联频道id	
	private Date update_time;	//更新时间
	private String channel_name; //频道名称
	private String modifier; //修改人

	
}
