package com.pbtd.playclick.integrate.domain;

import java.io.Serializable;

import com.pbtd.playclick.page.QueryObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Strategy extends QueryObject implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id	;
	private String Name	;
	private int priority	;
	private String Field1	;
	private String FieldName1	;
	private String Field2	;
	private String FieldName2	;
	private String Field3	;
	private String FieldName3	;
	private String Field4	;
	private String FieldName4	;
	private String Cpcode	;
	private int  Status	;

}
