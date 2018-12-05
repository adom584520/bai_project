package com.yh.search.tv.listenter;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.yh.search.tv.service.TvSearchItemService;

/**
 * TV端搜索索引监听
 * @author Administrator
 *
 */
public class TvIndexListener implements MessageListener {

	public static Logger log = Logger.getLogger(TvIndexListener.class);

	@Resource
	private TvSearchItemService tvSearchItemService;

	@Override
	public void onMessage(Message message) {
		// 从消息中取专辑id
		try {
			TextMessage textMessage = (TextMessage) message;
			// 监听消息类容
			String str = textMessage.getText();
			System.out.println("tv监听消息类容: " + str);
			// 根据类容判断
			if ("0".equals(str)) {
				tvSearchItemService.deleteIndex();
			} else if ("1".equals(str)) {
				tvSearchItemService.importAllIndex();
			} else if ("2".equals(str)) {
				tvSearchItemService.optimizeIndex();
			} /*else if ("3".equals(str)) {
				searchItemTvService.importAllTitleIndex();
			} else if ("4".equals(str)) {
				searchItemTvService.deleteTitleIndex();
			} else if ("5".equals(str)) {
				searchItemTvService.optimizeTitleIndex();
			}*/

		} catch (Exception e) {
			log.error("activemq监听错误" + e);
		}
	}

}
