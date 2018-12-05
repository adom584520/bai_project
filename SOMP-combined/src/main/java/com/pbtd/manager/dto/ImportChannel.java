package com.pbtd.manager.dto;

import java.util.ArrayList;
import java.util.List;

import com.pbtd.manager.domain.CpChannel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImportChannel {
	private String cpCode;// cp方code
	private List<CpChannel> list = new ArrayList<>();
}
