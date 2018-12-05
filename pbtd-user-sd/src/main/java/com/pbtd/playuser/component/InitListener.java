package com.pbtd.playuser.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.pbtd.playuser.util.LotteryUtil;

@Component
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger LOGGER = LoggerFactory.getLogger(InitListener.class);
	@Autowired
	private ConstantBeanConfig constantBeanConfig;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		LOGGER.info("初始化静态常量值......");
		LOGGER.info("初始化转盘最大中奖概率......");
		LotteryUtil.maxProbability = constantBeanConfig.maxProbability;
		LOGGER.info("转盘最大中奖概率为：" + LotteryUtil.maxProbability);
		LOGGER.info("初始化数据成功！");
	}
}