package com.pbtd.vodinterface.web.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Setter@Getter
public class Log  implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String clientIp;// 请求的IP
	private String operationInfo;// 操作信息
	private String url;// 请求的路径
	private String type;// 请求的类型
	private String method;// 请求的方式
	private Long startTime;// 请求时间，保存毫秒，在前端再进行解析
	private Long returnTime;// 响应时间，保存毫秒，在前端再进行解析
	private Integer consumingTime;// 请求耗时，单位秒
	private String httpStatusCode;// 响应状态码
	private String paramData;// 请求参数
	private String returnData;// 响应参数
	private Date createTime;// 操作时间
}
