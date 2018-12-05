package com.pbtd.playlive.domain;


import com.pbtd.playlive.page.QueryObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商家基本信息
 * @author dzy
 *
 */
@Setter
@Getter
@ToString
public class LiveBuss extends QueryObject  {
	private String bussId;
	private String bussName;
	private String address;
	private String connUser;
	private String connPhone;
	private String lastVers;
	private String lastVersname;
	private String apkUrl;
	private String createDate;
	private String modifyDate;
	private String userId;
	private String parentId;
	private String _parentId;
	private String logoUrl;
	private String backgroudUrl;
	private String videoURL;
	private String imageURL;
	private String type;
	private String city;
}
