package com.pbtd.playuser.page;

import com.pbtd.playuser.component.ConstantBeanConfig;
import com.pbtd.playuser.util.JsonMessage;
import com.pbtd.playuser.util.ServiceInfoUtil;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回消息实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
public class GetCodeResult extends JsonMessage{
	public int flag;//返回的用户是新增用户与否
	public String termsUrl;//返回的用户是新增用户与否
	
	public GetCodeResult() {
	}
	public GetCodeResult(int code,String message) {
		this.code = code;
		this.message = message;
	}
	public GetCodeResult(int code,String message,int flag) {
		this.code = code;
		this.flag = flag;
		this.message = message;
		if(flag == 0){
			this.termsUrl = null;
		}else{
			this.termsUrl =ConstantBeanConfig.LOCAL_URL+"/userAgreement";
		}
	}
}
