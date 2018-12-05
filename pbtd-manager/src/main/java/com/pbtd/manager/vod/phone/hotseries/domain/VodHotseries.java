package com.pbtd.manager.vod.phone.hotseries.domain;




import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VodHotseries  implements Serializable{
	private static final long serialVersionUID = 1L;
      private int id;
      private String name;
      private int channelCode;
      private int count;
      private int status;
      private int type;
      private Date update_time;
      private String update_user;
      private Date create_time;
      private String create_user;
      
}
