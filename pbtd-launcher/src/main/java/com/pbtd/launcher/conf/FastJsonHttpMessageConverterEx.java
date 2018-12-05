package com.pbtd.launcher.conf;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * fastjson转换器
 * 
 * @author JOJO
 *
 */
public class FastJsonHttpMessageConverterEx extends FastJsonHttpMessageConverter {
	@Override
	protected boolean supports(Class<?> clazz) {
		return super.supports(clazz);
	}
}
