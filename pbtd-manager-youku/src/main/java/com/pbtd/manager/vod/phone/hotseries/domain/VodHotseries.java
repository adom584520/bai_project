package com.pbtd.manager.vod.phone.hotseries.domain;



import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VodHotseries {
      private int id;
      private String name;
      private int channelCode;
      private int count;
      private int status;
      private int type;
      private String create_user;
      private Date create_time;
      private String update_user;
      private Date update_time;
}
