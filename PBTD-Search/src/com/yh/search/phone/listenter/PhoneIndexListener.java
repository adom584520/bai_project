package com.yh.search.phone.listenter;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.yh.search.phone.service.PhoneSearchItemService;

/**
 * 手机搜索索引监听
 * @author Administrator
 *
 */
public class PhoneIndexListener implements MessageListener {
	
	public static Logger log = Logger.getLogger(PhoneIndexListener.class);
	
	@Resource
	private PhoneSearchItemService phoneSearchItemService;

	@Override
	public void onMessage(Message message) {
		// 从消息中取专辑id
		try {
			TextMessage textMessage = (TextMessage) message;
			//监听消息类容
			String str = textMessage.getText();
			System.out.println("phone监听消息类容: " + str);
			// 根据类容判断
			if ("0".equals(str)) {
				phoneSearchItemService.deleteIndex();
			}else if ("1".equals(str)) {
				phoneSearchItemService.importAllIndex();
			}else if ("2".equals(str)) {
				phoneSearchItemService.optimizeIndex();
			}else if ("3".equals(str)) {
				phoneSearchItemService.importAllTitleIndex();
			}else if ("4".equals(str)) {
				phoneSearchItemService.deleteTitleIndex();
			}else if ("5".equals(str)) {
				phoneSearchItemService.optimizeTitleIndex();
			}
			
		} catch (Exception e) {
			log.error("activemq监听错误"+e);
		}
	}
}
