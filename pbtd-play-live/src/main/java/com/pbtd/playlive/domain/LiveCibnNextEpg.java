package com.pbtd.playlive.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.sf.json.JSONObject;

@Setter
@Getter
@AllArgsConstructor @NoArgsConstructor
public class LiveCibnNextEpg  {
	
	

	private int  videoId;
	private String  chnCode;
    private String  epgName;
    private long startTime;
    private long endTime;
    private String typeName;
    private String  nextEpgName;
    private long nextStartTime;
    private long nextEndTime;
	@Override
	public String toString() {
		return "{\"videoId\":\"" + videoId + "\",\"chnCode\":\"" + chnCode + "\",\"epgName\":\"" + epgName
				+ "\",\"startTime\":\"" + startTime + "\",\"endTime\":\"" + endTime + "\",\"typeName\":\"" + typeName
				+ "\",\"nextEpgName\":\"" + nextEpgName + "\",\"nextStartTime\":\"" + nextStartTime
				+ "\",\"nextEndTime\":\"" + nextEndTime + "\"}  ";
	}
    
    
//    public String toString() {
//    	JSONObject AAA =  JSONObject.fromObject(jsonObject);
//    }
  
}