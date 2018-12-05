package com.pbtd.playlive.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class LivePackageOutPut {
		public String packageCode;  //节目包代码
	    public String packageName;  //节目包名称
	    public String packageCover;   //节目包封面
	    public long startTime;  //开始时间
	    public long endTime;   //结束时间
	    public String chnCode;  //频道代码
	    public long tagId;  //标签关联 ID
	    public int totalSeries;  //总集数
	    public int currSeries;  //当前集数
	//  public String playUrl;  //播放地址
	//  public String playUrl2;  //播放地址
	    public int packageOrder;   //包的顺序，为空时按照更新时 间倒序排列
	    public String packagePoster;   //节目包海报
	    public int packageStatus; //状态 1:有效 0:无效
	}