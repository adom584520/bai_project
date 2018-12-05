package com.pbtd.playclick.integrate.domain;

import java.io.Serializable;

import com.pbtd.playclick.page.QueryObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class VodMapping  extends QueryObject implements Serializable  {
    private static final long serialVersionUID = 1L;
			private int id ;
			private  String cp_chnId;
			private String cp_tagId;
			private String phone_chnId;
			private String tv_tagId;
			private String phone_tagId;
			private String bz;
			private int status;
			private String tv_chnId;
			private String cpcode;
}
