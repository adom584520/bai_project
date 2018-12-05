package com.pbtd.manager.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@PropertySource(value = { "classpath:config/activemq.properties" })
public class MQInterfaceBeanConfig {

	public  static String activemq_Flag;
	public  static String activeMQTCP;
	
	public  static String queue_Name;
	public  static String topic_Name;
	public  static String topicName_ID;
	
	public  static String queue_NameTV;
	public  static String topic_NameTV;
	public  static String topic_NameTV_ID;
	
	public  static String topicName_Live;
	public  static String topicName_LiveDel;
	
		
	// Activemq 是否启用
	@Value("${activemqFlag}")
	public void setActivemq_Flag(String activemqFlag) {
		activemq_Flag = activemqFlag;
	}
	// Activemq地址
	@Value("${activemqTCP}")
	public void setActiveMQTCP(String activemqTCP) {
		activeMQTCP = activemqTCP;
	}

	// live直播数据发送名称
	@Value("${topicNameLive}")
	public  void setTopicName_Live(String topicNameLive) {
		topicName_Live = topicNameLive;
	}

	// live直播数据删除发送名称
	@Value("${topicNameLiveDel}")
	public  void setTopicName_LiveDel(String topicNameLiveDel) {
		topicName_LiveDel = topicNameLiveDel;
	}
	// 广播发送名称
	@Value("${topicName}")
	public  void setTopic_Name(String topicName) {
		topic_Name = topicName;
	}
	// 广播发送名称
	@Value("${topicNameTV}")
	public  void setTopic_NameTV(String topicNameTV) {
		topic_NameTV = topicNameTV;
	}
	// TV广播专辑id发送名称
	@Value("${topicNameTVid}")
	public  void setTopic_NameTV_ID(String topicNameTVid) {
		topic_NameTV_ID = topicNameTVid;
	}
	// 广播专辑id发送名称
	@Value("${topicNameID}")
	public  void setTopicName_ID(String topicNameID) {
		topicName_ID = topicNameID;
	}
	// 点对点发送名称
	@Value("${queueName}")
	public  void setQueue_Name(String queueName) {
		queue_Name = queueName;
	}
	// TV点对点发送名称
	@Value("${queueNameTV}")
	public  void setQueue_NameTV(String queueNameTV) {
		queue_NameTV = queueNameTV;
	}

}
