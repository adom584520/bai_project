package com.pbtd.launcher.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LauncherGroupDTO {
	private Long groupId;
	private String groupName;
	private List<EasyNavigationDTO> navList = new ArrayList<>();
}
