package com.pbtd.manager.util;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class ActivemqUtils {

	public static void testQueueProducer(String tcp,String queueName,String text ) throws Exception {
		// 1、创建一个连接工程ConnectionFactory对象。指定服务的ip及端口号
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(tcp);
		// 2、使用ConnectionFactory对象创建一个Connection对象。
		Connection connection = connectionFactory.createConnection();
		// 3、开启连接，调用start方法
		connection.start();
		// 4、使用Connection对象创建一个Session对象。
		// 第一个参数：是否开启事务，一般不开启。当参数为false时，第二个参数才有意义。
		// 第二个参数：消息的应答模式。手动应答和自动应答。一般使用自动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5、使用Session对象创建一个Destination，目的地有两种queue、topic
		Queue queue = session.createQueue(queueName);
		// 6、使用Session对象创建一个Producer对象。
		MessageProducer producer = session.createProducer(queue);
		// 7、使用producer发送消息。
		TextMessage textMessage = new ActiveMQTextMessage();
		textMessage.setText(text);
		// TextMessage textMessage2 = session.createTextMessage("使用activemq发送的队列消息");
		producer.send(textMessage);
		// 8、关闭资源。
		producer.close();
		session.close();
		connection.close();
	}

	public static void testTopicProducer(String tcp,String topicName,String text) throws Exception {
		// 创建一个连接工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(tcp);
		// 使用连接工厂对象创建一个连接
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();
		// 使用连接对象创建一个session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 使用session对象创建一个topic
		Topic topic = session.createTopic(topicName);
		// 使用session创建一个producer，指定目的地时候topic。
		MessageProducer producer = session.createProducer(topic);
		// 创建一个TextMessage对象
		TextMessage message = session.createTextMessage(text);
		// 使用producer对象发送消息。
		producer.send(message);
		// 关闭资源
		producer.close();
		session.close();
		connection.close();

	}

}
