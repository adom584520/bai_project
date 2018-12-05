package com.pbtd.playuser.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityUserPlayHistoryOutPut {
	private String seriesname;//电影名称	
	private String seriescode;//电影code
	private Integer playTime;// 该电影真实时间

	public ActivityUserPlayHistoryOutPut(PlayHistoryInfo PlayHistoryInfo,ZhuanpanPlayseriescode ZhuanpanPlayseriescode ) {
		this.playTime = PlayHistoryInfo.getPlayTime();
		this.seriesname = ZhuanpanPlayseriescode.getSeriesname();
		this.seriescode = ZhuanpanPlayseriescode.getSeriescode();
	}
	public ActivityUserPlayHistoryOutPut(ZhuanpanPlayseriescode ZhuanpanPlayseriescode ) {
		this.playTime = 0;
		this.seriesname = ZhuanpanPlayseriescode.getSeriesname();
		this.seriescode = ZhuanpanPlayseriescode.getSeriescode();
	}

}