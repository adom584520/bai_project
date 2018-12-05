package com.pbtd.manager.live.domain.getEpg;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class LivePackageEpg  implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String packageName;
	private String isSave;
	private String packageContent;
	private long startTime;
	private long endTime;
	private String chnCode;
	private String chnName;
	private int tagId;
	private String totalSeries;
	private String currSeries;
	private String playUrl;
	private String playUrl2;
	private int packageOrder;
	private String packagePoster;
	private int packageStatus;
	private long createTime;
	private long updateTime;
	private String tagName;
	private String packageRegex;
	private String packageCode;
	private String packageCover;
	private String cpPackageCode;
	private int  hot;
}
