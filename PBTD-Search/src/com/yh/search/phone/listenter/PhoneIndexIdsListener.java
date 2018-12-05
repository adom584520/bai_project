package com.yh.search.phone.listenter;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.yh.search.phone.service.PhoneIndexDelIds;

public class PhoneIndexIdsListener implements MessageListener {

	public static Logger log = Logger.getLogger(PhoneIndexIdsListener.class);
	
	@Resource
	private PhoneIndexDelIds phoneIndexDelIds;

	@Override
	public void onMessage(Message message) {
		// 从消息中取专辑id
		try {
			TextMessage textMessage = (TextMessage) message;
			// 监听消息类容
			String ids = textMessage.getText();
			// 根据专辑Id查询专辑消息
			phoneIndexDelIds.ImportDel(ids);
		} catch (Exception e) {
			log.error("phone添加ids专辑activemq监听错误" + e);
		}

	}

}
