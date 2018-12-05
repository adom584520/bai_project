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

import com.pbtd.service.VideoReceiveTvService;
import com.pbtd.util.PropertiesUtil;
import com.pbtd.util.web.RequestUtil;

/**
 * tv媒资
 * @author jiaren
 *
 */

@WebServlet("/videoReceiveTv")
public class VideoReceiveTvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VideoReceiveTvService tvVideoReceiveService=new  VideoReceiveTvService();
	private static final Logger logger=Logger.getLogger(VideoReceiveTvServlet.class);
	
	Date d = new Date();  
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String dateNowStr = sdf.format(d);  


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		  
		  Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		  final Object start =queryParams.get("start");
		  final Object ids=queryParams.get("ids");
		  final Object curtime =queryParams.get("curtime");
		  logger.info("收到分平台下发tv通知:");
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
							
							String url=PropertiesUtil.getValue("subWebHost")+PropertiesUtil.getValue("subWebTvVideoUrl");
							map.put("url",url);
							tvVideoReceiveService.receivePhoneAlbumVideo(map); 
							
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
