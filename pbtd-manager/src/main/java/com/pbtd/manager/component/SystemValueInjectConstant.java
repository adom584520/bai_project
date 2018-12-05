package com.pbtd.manager.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * system模块注入值对象
 * 
 * @author JOJO
 *
 */
@Configuration
@PropertySource(value = { "classpath:config/systemConstant.properties" })
public class SystemValueInjectConstant {
	@Value("${charset}")
	public String charset;// 字符集
	@Value("${insertLoginInfo}")
	public String insertLoginInfo;// 添加账号
	@Value("${updateLoginInfo}")
	public String updateLoginInfo;// 编辑账号
	@Value("${deleteLoginInfo}")
	public String deleteLoginInfo;// 删除账号
	@Value("${updateStatus}")
	public String updateStatus;// 修改账号状态
	@Value("${resetPassword}")
	public String resetPassword;// 重置账号密码
	@Value("${updateSelfInfo}")
	public String updateSelfInfo;// 修改个人资料
	@Value("${updatePassword}")
	public String updatePassword;// 修改个人密码
}
