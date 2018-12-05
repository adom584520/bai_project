package com.pbtd.manager.live.spider;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.pbtd.manager.live.domain.LiveProgram;
import com.pbtd.manager.live.mapper.LiveProgramMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class LiveProgremByBaidu {

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@Autowired
	private LiveProgramMapper liveProgramMapper;

	static  Map<String,String> map() {
		Map<String,String> map=new HashMap<String,String>();    
		map.put("KAKU"  ,"btv%E5%8D%A1%E9%85%B7%E5%B0%91%E5%84%BF&co=data%5Btabid%3D");
		map.put("CETV-1",           "%E6%95%99%E8%82%B21%E5%8F%B0&co=data%5Btabid%3D");
		map.put("CETV-2",           "%E6%95%99%E8%82%B22%E5%8F%B0&co=data%5Btabid%3D");
		return map;
	}
	static  Map<String,String> map2() {
		Map<String,String> map=new HashMap<String,String>();    
		map.put("btv%E5%8D%A1%E9%85%B7%E5%B0%91%E5%84%BF&co=data%5Btabid%3D","jQuery11020698733221047579_");
		map.put("%E6%95%99%E8%82%B21%E5%8F%B0&co=data%5Btabid%3D","jQuery110209046142589108657_");
		map.put("%E6%95%99%E8%82%B22%E5%8F%B0&co=data%5Btabid%3D","jQuery110207682605630978796_");
		return map;
	}
	//@Test
	public void ChncodeMans()  throws InterruptedException, ParseException {
		logger.info(System.currentTimeMillis()+"--直播通过BAIDU获取节目--");
		//每执行 获取7天的
		for (int i=1;i<=7;i++) {
			for (String chnCode : map().keySet()) {
				doOperate(chnCode,i);
			}
			logger.info(System.currentTimeMillis()+"结束从百度 获取节目单--end+"+i+"+--");
		}
		logger.info(System.currentTimeMillis()+"--------------------------end--------------------------");
	}


	public void doOperate(String chnCode, int i) throws InterruptedException, ParseException {
		//定时获取更新数据
		List<LiveProgram> livePackage = null;
		livePackage = importzxjmmd(chnCode,i);
		//添加时间：
		logger.info("集合的长度::::"+livePackage.size());
		if(livePackage.size()>0){
			//遍历插入数据库
			String realtime = livePackage.get(0).getRealtime().substring(0,10)+"%";
			logger.info("集合的长度::::"+livePackage.size());
			logger.info("::::::::::::::::::::::::::::::::::::");
			logger.info("::::::::::::::::::::::::::::::::::::");
			logger.info(realtime);
			logger.info("::::::::::::::::::::::::::::::::::::");
			logger.info("::::::::::::::::::::::::::::::::::::");
			for (int a = 0 ;a < (livePackage.size()-1);a++ ){
				livePackage.get(a).setEndtime(livePackage.get(a+1).getStarttime());
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			long twelve = simpleDateFormat.parse(livePackage.get(0).getRealtime().substring(0, 10)+" 23:59").getTime()/1000;
			livePackage.get(livePackage.size()-1).setEndtime(twelve);
			Map<String, Object> map = new HashMap<>();
			map.put("realtime", realtime);
			map.put("chnCode", chnCode);
			liveProgramMapper.deletebyreal(map);
			for (LiveProgram dsadasd : livePackage) {
				logger.info(dsadasd.getStarttime()+"  时间 : "+dsadasd.getEndtime()+"   节目: "+ dsadasd.getProgramname()+" |||| 日期："+dsadasd.getRealtime() +"    来源"+ dsadasd.getSource());
				try {
					LiveProgram liveprogram = liveProgramMapper.selectByrecord(dsadasd);
					if(liveprogram != null){
						logger.info("数据库存在该条数据:"+liveprogram.getProgramname());
					}else{
						liveProgramMapper.insert(dsadasd);
					}
				} catch (Exception e) {
					logger.info("插入数据库失败"+e.getMessage());
				}
				//Thread.sleep(1000);
			}
		}else{
			logger.info("******************无"+chnCode+"节目******************");
		}
		logger.info("******************"+chnCode+"1111******************");
	}
	// 根据接口获取数据
	public List<LiveProgram>  importzxjmmd(String chnCode,int a) {
		String requestUrl1 = map().get(chnCode);
		String requestUrl2 = map2().get(requestUrl1);
		long shijianchuo = System.currentTimeMillis();
		StringBuffer  url = new StringBuffer();
		url.append("https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=");
		url.append(requestUrl1);
		url.append(a);
		url.append("%5D&resource_id=12520&t=");
		url.append(shijianchuo+889);
		url.append("&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=");
		url.append(requestUrl2);
		url.append(shijianchuo);
		url.append("&_=");
		url.append(shijianchuo+332);
		logger.info(url.toString());
		List<LiveProgram> liveProgramlist  = new LinkedList<>();
		try {
			//使用Jsoup的解析方法进行填装Dom
			Thread.sleep(300);
			WebClient webClient = new WebClient(BrowserVersion.CHROME);//BrowserVersion.CHROME
			webClient.setAjaxController(new NicelyResynchronizingAjaxController()); //设置支持AJAX
			webClient.setJavaScriptTimeout(100000);//设置JS执行的超时时间
			webClient.getOptions().setJavaScriptEnabled(true);//很重要，启用JS
			webClient.getOptions().setCssEnabled(true);//是否启用CSS
			webClient.getOptions().setActiveXNative(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常
			webClient.getOptions().setTimeout(60000);//设置“浏览器”的请求超时时间
			//HtmlPage rootPage = webClient.getPage(relUrl.toString()); //设置一个运行JavaScript的时间
			UnexpectedPage  rootPage = webClient.getPage(url.toString()); //设置一个运行JavaScript的时间
			//String html = rootPage.asXml();
			String html = rootPage.getWebResponse().getContentAsString();
			String page = html.substring(html.indexOf("{"), html.length()-2);
			System.out.println(page.toString());

			//得到数据 转换json
			JSONObject jsonObj1 = JSONObject.fromObject(page.toString());
			JSONArray result = jsonObj1.getJSONArray("data");
			JSONObject jsonObj2 = JSONObject.fromObject(result.get(0));
			JSONArray result1 = jsonObj2.getJSONArray("data");
			for (Object object : result1) {
				LiveProgram  live = new LiveProgram();
				JSONObject baiduObj = JSONObject.fromObject(object.toString());
				String title = baiduObj.getString("title");
				String times = baiduObj.getString("times");
				live.setChncode(chnCode);
				live.setSource(1);
				live.setRealtime(times);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				long starttime = simpleDateFormat.parse(times).getTime()/1000;
		//		long endtime = simpleDateFormat.parse(baiduObj.getString("times").).getTime()/1000;
				live.setProgramname(title);
				live.setStarttime(starttime);
				live.setStarttime(starttime);
				liveProgramlist.add(live);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return liveProgramlist;
	}


	@Test
	public void  parsePageaa() throws ParseException {
		try {
			//使用Jsoup的解析方法进行填装Dom
			String uaa = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=btv%E5%8D%A1%E9%85%B7%E5%B0%91%E5%84%BF&co=data%5Btabid%3D7%5D&resource_id=12520&t=1513942579409&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery11020698733221047579_1513941129057&_=1513941129077";
			//String uaa = "https://e.starschina.com/api/currentepgs?appOs=Android&appVer=1.1&appKey=ZjQxMzQ2ZmYwZTU5";
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);//BrowserVersion.CHROME
			webClient.setAjaxController(new NicelyResynchronizingAjaxController()); //设置支持AJAX
			webClient.setJavaScriptTimeout(100000);//设置JS执行的超时时间
			webClient.getOptions().setJavaScriptEnabled(true);//很重要，启用JS
			webClient.getOptions().setCssEnabled(true);//是否启用CSS
			webClient.getOptions().setActiveXNative(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常
			webClient.getOptions().setTimeout(60000);//设置“浏览器”的请求超时时间
			//HtmlPage rootPage = webClient.getPage(relUrl.toString()); //设置一个运行JavaScript的时间
			UnexpectedPage  rootPage = webClient.getPage(uaa); //设置一个运行JavaScript的时间
			webClient.waitForBackgroundJavaScript(2000);
			//	String html = rootPage.asXml();
			String html = rootPage.getWebResponse().getContentAsString();
			String page = html.substring(html.indexOf("{"), html.length()-2);
			System.out.println(page.toString());
			//得到数据 转换json
			JSONObject jsonObj1 = JSONObject.fromObject(page.toString());
			JSONArray result = jsonObj1.getJSONArray("data");
			JSONObject jsonObj2 = JSONObject.fromObject(result.get(0));
			JSONArray result1 = jsonObj2.getJSONArray("data");
			System.out.println(result1.toString());
			for (Object object : result1) {
				System.out.println(object.toString());
				JSONObject baiduObj = JSONObject.fromObject(object.toString());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				long aaaa = simpleDateFormat.parse(baiduObj.getString("times")).getTime()/1000;
				long bbbb = simpleDateFormat.parse(baiduObj.getString("times").substring(0, 10)+" 23:59").getTime()/1000;
				System.out.println(aaaa);
				System.out.println();
			}
			//获取频道列表
			//			Elements novelList1 = doc.select(".item");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
