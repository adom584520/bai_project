package com.pbtd.manager.live.spider;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pbtd.manager.live.domain.LiveProgram;
import com.pbtd.manager.live.mapper.LiveProgramMapper;

@Service	
public class LiveProgrem {

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@Autowired
	private LiveProgramMapper liveProgramMapper;

	/**
	 * 节目单的map集合
	 */
	static  Map<String,String> map() {
		Map<String,String> map=new HashMap<String,String>();    
		map.put("CCTV-5PLUS","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-cctv5-week-");
		map.put("CCTV-14","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-cctv14-week-");
		map.put("KAKU","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-btv10-week-");
		//map.put("GUANGDONG-HD","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-gdtv-week-");
		//map.put("HEILONGJIANG-HD","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-hljtv-week-");
		//map.put("ANHUI","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-ahtv-week-");
		//map.put("LIAONING-HD","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-lltv-week-");
		//map.put("SHANDONG-HD","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-sdtv-week-");
		//map.put("GUANGXI","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-gxtv-week-");
		map.put("LVYOU","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-tctc-week-");
		//map.put("CHONGQING-HD","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-cqtv-week-");
		//map.put("SICHUAN","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-sctv-week-");
		//map.put("HENAN","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-hntv-week-");
		map.put("DONGNAN","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-setv-week-");
		//map.put("TIANJIN-HD","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-tjtv-week-");
		//map.put("JIANGXI","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-jxntv-week-");
		//map.put("HUBEI-HD","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-hbtv-week-");
		//map.put("JILIN","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-jilintv-week-");
		//map.put("SHANXI","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-sxtvs-week-");
		//map.put("GUIZHOU","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-gztv-week-");
		//map.put("NEIMENGGU","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-nmtv-week-");
		map.put("YUNNAN","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-yntv-week-");
		//map.put("HEBEI","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-hbws-week-");
		//map.put("NINGXIA","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-nxtv-week-");
		//map.put("XINJIANG","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-xjtvs-week-");
		//map.put("GANSU","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-gstv-week-");
		map.put("CETV-1","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-centv-week-");
		map.put("CETV-2","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-centv2-week-");
		//map.put("GUANGDONG","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-gdtv-week-");
		//map.put("SHANDONG","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-sdtv-week-");
		//map.put("TIANJIN","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-tjtv-week-");
		//map.put("HUBEI","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-hbtv-week-");
		//map.put("CHONGQING","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-cqtv-week-");
		//map.put("LIAONING","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-lltv-week-");
		map.put("QINGHAI","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-qhtv-week-");
		map.put("CCTV-6","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-cctv6-week-");
		map.put("CCTV-6-HD","http://www.guatian.com/index.php?s=Home-Tv-getTvdetail-url-cctv6-week-");
		return map;
	}
	
	
	public void ChncodeMans()  throws InterruptedException {
		logger.info(System.currentTimeMillis()+"--开始从瓜田网获取节目单--");
		//每执行 获取7天的
		for (int i=1;i<=7;i++) {
			for (String chnCode : map().keySet()) {
				doOperate(chnCode,i);
			}
			logger.info(System.currentTimeMillis()+"结束获取节目单--end+"+i+"+--");
		}
	}

	/**
	 * 遍历map之后的操作
	 * @param fix： 
	 * @return
	 * @throws InterruptedException 
	 */
	public void doOperate(String chnCode,int i) throws InterruptedException {
		logger.debug("******************"+chnCode+"0000******************");
		Date today = new Date();
		Calendar c =Calendar.getInstance();
		c.setTime(today);
		int weekday=c.get(Calendar.DAY_OF_WEEK);
		
		List<LiveProgram> livePackage  = parsePage(getCurrentDate(i-weekday+1),i,chnCode);
		//添加时间：
		logger.info("集合的长度::::"+livePackage.size());
		if(livePackage.size()>0){
			for (int a = 0 ;a < (livePackage.size()-1);a++ ){
				livePackage.get(a).setEndtime(livePackage.get(a+1).getStarttime());
			}
			long current=System.currentTimeMillis();
			long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数  
			long twelve=(zero+24*60*60*1000-1)/1000;//今天23点59分59秒的毫秒数
			livePackage.get(livePackage.size()-1).setEndtime(twelve);
			//遍历插入数据库
			for (LiveProgram dsadasd : livePackage) {
				logger.info(dsadasd.getStarttime()+"  时间 : "+dsadasd.getEndtime()+"   节目: "+dsadasd.getProgramname()+"  |||| 日期："+dsadasd.getRealtime());
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
			}
		}else{
			logger.info("******************无"+chnCode+"节目******************");
		}
		logger.info("******************"+chnCode+"1111******************");
	}

	/**
	 * 爬取电视节目单 
	 */
	public static List<LiveProgram> parsePage(String strDate,int j,String chnCode) throws InterruptedException{
		List<LiveProgram> liveProgram = new LinkedList<>();
		try {
			//使用Jsoup的解析方法进行填装Dom
			StringBuffer relUrl = new StringBuffer();
			relUrl.append(map().get(chnCode));
			relUrl.append(j);
			Thread.sleep(300);
			WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_11);//BrowserVersion.CHROME
			webClient.setAjaxController(new NicelyResynchronizingAjaxController()); //设置支持AJAX
			webClient.setJavaScriptTimeout(100000);//设置JS执行的超时时间
			webClient.getOptions().setJavaScriptEnabled(true);//很重要，启用JS
			webClient.getOptions().setCssEnabled(true);//是否启用CSS
			webClient.getOptions().setActiveXNative(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常
			webClient.getOptions().setTimeout(60000);//设置“浏览器”的请求超时时间
			HtmlPage rootPage = webClient.getPage(relUrl.toString()); //设置一个运行JavaScript的时间
			webClient.waitForBackgroundJavaScript(2000);
			String html = rootPage.asXml();
			Document doc  = Jsoup.parse(html);
			//获取频道列表
			Elements novelList1 = doc.select(".item");
			for (Element element : novelList1) {
				LiveProgram  live = new LiveProgram();
				//获取节目的时间：
				Element authorElement = element.select(".am").first();
				if(authorElement ==null){
					authorElement = element.select(".pm").first();
				}
				if(authorElement == null){
					authorElement = element.select(".nt").first();                	
				}
				StringBuffer now = new StringBuffer();
				now.append(strDate);
				now.append(" ");
				now.append(authorElement.html());
				now.append(":00");
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date res = null;
				//节目单
				live.setSource(1);
				live.setRealtime(now.toString());
				try {
					res = simpleDateFormat.parse(now.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				long times = res.getTime()/1000; 
				if(authorElement != null) {
					live.setStarttime(times);
				}
				//获取节目名称
				Element descriElement = element.select(" #e2").first();
				String pacekgename = descriElement.html();
				if(pacekgename.indexOf("<a") >= 0){
					pacekgename =  descriElement.select("a").first().html();
				}
				live.setChncode(chnCode);
				live.setProgramname(pacekgename);
				liveProgram.add(live);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return liveProgram;
	}

	/**
	 *
	 * 相对当前时间的前后天数 返回日期 String
	 * @param fix： 
	 * @return
	 */
	public static String getCurrentDate(int fix) {
		Date date = new Date(System.currentTimeMillis() + fix * 24 * 60 * 60 * 1000);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(date);
	}


}
