package com.pbtd.playuser.component;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyTimer {
	@Autowired
    @Scheduled(cron = "0 24 16 * * ?")
    //@Scheduled(fixedRate = 3000)
    public void timerRate() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(sdf.format(new Date()));
    }
    @Scheduled(cron = "0 27 16 * * ?")
    public void timerRate2() {
        System.out.println(27);
    }

}
