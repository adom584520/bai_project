package com.pbtd.helpBean;

import java.util.List;

public class MovieUrlBean {
	private String returncode;
	private String description;
	private List<MovieUrlAddressBean> urls;
	
	public String getReturncode() {
		return returncode;
	}
	public void setReturncode(String returncode) {
		this.returncode = returncode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<MovieUrlAddressBean> getUrls() {
		return urls;
	}
	public void setUrls(List<MovieUrlAddressBean> urls) {
		this.urls = urls;
	}
	
}
