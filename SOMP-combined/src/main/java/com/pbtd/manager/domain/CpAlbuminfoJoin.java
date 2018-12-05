package com.pbtd.manager.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 媒资专辑和剧集关联实体
 * 
 * @author JOJO
 *
 */
@Setter
@Getter
@ToString
public class CpAlbuminfoJoin {
	private Long id;
	private Long seriesCode;// 己方专辑ID
	private Long dramaId;// 己方剧集ID
	private Integer drama;// 己方剧集号
	private Long cpSeriesCode;// cp方专辑ID
	private Long cpDramaId;// cp方的剧集ID
	private Integer cpDrama;// cp方剧集号
	private String cpCode;// cp的唯一标识
}
