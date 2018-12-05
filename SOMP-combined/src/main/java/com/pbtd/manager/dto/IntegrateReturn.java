package com.pbtd.manager.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class IntegrateReturn {
	private Integer code;
	private List<IntegrateAlbuminfo> data = new ArrayList<>();
	private Integer numsum;
}
