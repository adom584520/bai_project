package com.pbtd.playclick.integrate.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.pbtd.playclick.base.common.web.RequestUtil;
import com.pbtd.playclick.integrate.domain.VodAlbuminfo;
import com.pbtd.playclick.integrate.domain.VodChannel;
import com.pbtd.playclick.integrate.domain.Vodactors;
import com.pbtd.playclick.integrate.service.face.IVodactorsService;
import com.pbtd.playclick.integrate.service.face.IVodphoneChannelService;
import com.pbtd.playclick.integrate.service.impl.VodAlbuminfoService;

import net.sf.json.JSONObject;

@Controller("integrate.OutputJsonController")
@RequestMapping("/integrate/outputjson")
public class OutputJsonController {
	 
	  @Autowired
		private VodAlbuminfoService vodalbuminfoService;
	  
	  @Autowired
		private   IVodphoneChannelService vodphoneChannelService;
	  
	  @Autowired
			private   IVodactorsService   vodactorsService ;
	  
	    //手机 运营调用节目集 获取下发数据
	    @RequestMapping( "/phonealbumsinfo")
		 public  String phonealbumsinfo(HttpServletRequest request,HttpServletResponse response) throws IOException  {
			 JSONObject json = new JSONObject();
	      			response.setContentType("text/html;charset=utf-8");
				Map<String, Object> queryParams = RequestUtil.asMap(request, false);
				String ids=queryParams.get("id")==null?"":queryParams.get("id").toString();
				int start=queryParams.get("start")==null?1:Integer.parseInt(queryParams.get("start").toString());
				if(ids!=""){
					String[] id=queryParams.get("id").toString().split(",");
		    		queryParams.put("centralcode_", id);
	    		}/*else if(start>0){ 
	    			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	    			queryParams.put("updatetime",df.format(new Date()));
	    		}*/
				queryParams.put("channelstatus",1);//限制上线的频道数据下发
				int count=vodalbuminfoService.count(queryParams);
				json.accumulate("size", count);
	    		queryParams.put("start", (start-1)*1000);
				queryParams.put("pageSize",1000 );
				List<VodAlbuminfo> list=vodalbuminfoService.find(queryParams);
				response.setContentType("text/html;charset=utf-8");
				Double d=(double) count;
				json.accumulate("count",Math.ceil(d/1000));
				json.accumulate("body", JSON.toJSONString(list));
				response.getWriter().write(json.toString());
				return null;
	    }
	    
	    //  手机 运营调用节目集播放数据 获取下发数据
	    @RequestMapping( "/phonealbumsinfovideo")
		 public  String phonealbumsinfovideo(HttpServletRequest request,HttpServletResponse response) throws IOException  {
	    	 JSONObject json = new JSONObject();
				response.setContentType("text/html;charset=utf-8");
				Map<String, Object> queryParams = RequestUtil.asMap(request, false);
				String ids=queryParams.get("id")==null?"":queryParams.get("id").toString();
				int start=queryParams.get("start")==null?1:Integer.parseInt(queryParams.get("start").toString());
				String automatic=queryParams.get("automatic")==null?"":queryParams.get("automatic").toString();//是否自动下发数据
				if(ids!=""){
					String[] id=queryParams.get("id").toString().split(",");
		    		queryParams.put("centralcode_", id);
	    		}else{
	    			//queryParams.put("currtime",1);
	    		}
				queryParams.put("id", null);
				queryParams.put("start", (start-1)*1000);
				queryParams.put("pageSize",1000); 
				int count=vodalbuminfoService.countAlbumsinfovideo(queryParams);
				json.accumulate("size", count);
				List<Map<String, Object>> list=	vodalbuminfoService.findAlbumsinfovideo(queryParams);
				response.setContentType("text/html;charset=utf-8");
				Double d=(double) count;
				json.accumulate("count",Math.ceil(d/1000));
				json.accumulate("body", JSON.toJSONString(list));
				response.getWriter().write(json.toString());
				return null;
	    }
	    
	    //演员接口
	    
	    @RequestMapping( "/getactors")
		 public  String getactors(HttpServletRequest request,HttpServletResponse response) throws IOException  {
	    	 JSONObject json = new JSONObject();
				response.setContentType("text/html;charset=utf-8");
				Map<String, Object> queryParams = RequestUtil.asMap(request, false);
				queryParams.put("levels", 2);
				queryParams.put("start", 0);
				queryParams.put("pageSize",1);
				List<Vodactors> list=vodactorsService.page(1, 1, queryParams);
				response.setContentType("text/html;charset=utf-8");
				json.accumulate("body", JSON.toJSONString(list));
				response.getWriter().write(json.toString());
				return null;
	    }
	    //tv频道接口
	    @RequestMapping( "/gettvchannel")
		 public  String gettvchannel(HttpServletRequest request,HttpServletResponse response) throws IOException  {
	    	 JSONObject json = new JSONObject();
				response.setContentType("text/html;charset=utf-8");
				Map<String, Object> queryParams = RequestUtil.asMap(request, false);
				queryParams.put("levels", 1);
				queryParams.put("start", 0);
				queryParams.put("pageSize",1);
				List<VodChannel> list=vodphoneChannelService.page(0, 1, queryParams);
				response.setContentType("text/html;charset=utf-8");
				json.accumulate("body", JSON.toJSONString(list));
				response.getWriter().write(json.toString());
				return null;
	    }
	    
	    //手机标签接口
	    @RequestMapping( "/getphonelabel")
		 public  String getphonelabel(HttpServletRequest request,HttpServletResponse response) throws IOException  {
	    	 JSONObject json = new JSONObject();
				response.setContentType("text/html;charset=utf-8");
				Map<String, Object> queryParams = RequestUtil.asMap(request, false);
				queryParams.put("levels", 2);
				queryParams.put("start", 0);
				queryParams.put("pageSize",1);
				List<VodChannel> list=vodphoneChannelService.page(1, 1, queryParams);
				response.setContentType("text/html;charset=utf-8");
				json.accumulate("body", JSON.toJSONString(list));
				response.getWriter().write(json.toString());
				return null;
	    }
	    //手机频道接口
	    @RequestMapping( "/getphonechannel")
		 public  String getphonechannel(HttpServletRequest request,HttpServletResponse response) throws IOException  {
	    	 JSONObject json = new JSONObject();
				response.setContentType("text/html;charset=utf-8");
				Map<String, Object> queryParams = RequestUtil.asMap(request, false);
				queryParams.put("levels", 1);
				queryParams.put("start", 0); 
				queryParams.put("pageSize",1);
				List<VodChannel> list=vodphoneChannelService.page(0, 1, queryParams);
				response.setContentType("text/html;charset=utf-8");
				json.accumulate("body", JSON.toJSONString(list));
				response.getWriter().write(json.toString());
				return null;
	    }
}
