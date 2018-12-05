package com.pbtd.helpBean;

public class AccessTokenBean {
	//{"access_token":"0F3185066A62616A317C52C1AB327C31","expires_in":7200,
	// "refresh_token":"0F3185066A62616A317C52C1AB327C31","platform_type":"1","heartbit_interval":900}
	private String  access_token;
	private String expires_in;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
}
