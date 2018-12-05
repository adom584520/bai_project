package com.pbtd.manager.vod.phone.common.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 收费节目
 * @author admin
 *
 */
@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class VodPaidSAlbuminfo {

	private String seriesCode	;
	private Date create_time	;
	private int id;

}
