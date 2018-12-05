package com.yh.search.tv.listenter;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.yh.search.tv.service.TvLiveIndexIds;

public class TvLiveIndexIdsListener implements MessageListener {

	public static Logger log = Logger.getLogger(TvLiveIndexIdsListener.class);
	
	@Resource
	private TvLiveIndexIds liveIndexIds;

	@Override
	public void onMessage(Message message) {
		// 从消息中取专辑id
		try {
			TextMessage textMessage = (TextMessage) message;
			// 监听消息类容
			String ids = textMessage.getText();
			// 根据专辑Id查询专辑消息
			System.out.println("Tv直播数据:"+ids);
			if (ids!=null && !"".equals(ids)) {
				liveIndexIds.liveImport(ids);
			}
		} catch (Exception e) {
			log.error("live添加ids专辑activemq监听错误" + e);
		}

	}

}
