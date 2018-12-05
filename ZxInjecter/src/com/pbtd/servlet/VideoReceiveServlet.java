package com.pbtd.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.pbtd.service.VideoReceiveService;
import com.pbtd.util.PropertiesUtil;
import com.pbtd.util.web.RequestUtil;

/**
 * phone媒资
 * @author jiaren
 *
 */

@WebServlet("/videoReceive")
public class VideoReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VideoReceiveService videoReceiveService=new VideoReceiveService(); 
	private static final Logger logger=Logger.getLogger(VideoReceiveServlet.class);
	
	Date d = new Date();  
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String dateNowStr = sdf.format(d);  


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		  
		  logger.info("收到分平台下发通知:");  
		  Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		  final Object start =queryParams.get("start");
		  final Object ids=queryParams.get("ids");
		  final Object curtime =queryParams.get("curtime");
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  try {
			  Thread th = new Thread(){
					@Override
					public void run() {
						try {
							//根据日期获取专辑信息
							Map<String, Object> map=new HashMap<>();
							map.put("start",start);
							map.put("curtime", curtime);
							map.put("ids", ids);
							
							String url=PropertiesUtil.getValue("subWebHost")+PropertiesUtil.getValue("subWebPhoneVideoUrl");
							map.put("url",url);
							videoReceiveService.receivePhoneAlbumVideo(map); 
							
						} catch (IllegalArgumentException ec) {
							interrupted();
						}  
					}
				};
				th.start();
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

}
