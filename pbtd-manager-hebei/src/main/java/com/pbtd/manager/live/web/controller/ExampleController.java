package com.pbtd.manager.live.web.controller;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.live.page.PageResult;
import com.pbtd.manager.util.RedisService;

import redis.clients.jedis.Jedis;

@RequestMapping("/redis")
@Controller
public class ExampleController {
	private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

	
	@Autowired
	private RedisService redisService;
	
	
	@RequestMapping("/redis/set")
	public String redisSet(@RequestParam("value")String value){
		boolean isOk = redisService.set("name", value);
		return isOk+"";
	}
	
	@RequestMapping("/redis/get")
	public String redisGet(){
		String name = redisService.get("name");
		return name;
	}
	
    /**
     * GET 查询
     * @return 视图路径
     */
    @RequestMapping(value = {"", ""})
    public String index() {
    	return "/redis/index";
    }
	
	/**
	 * /live/BussManage/list
	 * @param qo
	 * @return
	 * 
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public PageResult Live(HttpServletRequest request) {		
		String vassss = request.getParameter("interfaceName");
		System.out.println(vassss);
		logger.info(vassss);
//		try {
//			Jedis jedis = null; int num = 0;
//			try {
//				jedis = getJedis();
//				Set<String> set = jedis.keys("*"+tablekey+"*");  
//				Iterator<String> it = set.iterator();  
//				while(it.hasNext()){  
//					String keyStr = it.next();  
//					System.out.println("删除的key："+keyStr);  
//					jedis.del(keyStr);  
//					num++;
//				} 
//			}catch (Exception e) {
//				logger.debug("模糊删除key异常.");
//			}finally {
//				getColse(jedis);
//			}
//			return num;
//			redisService.del("");
//			redisService.getList(vassss, null);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return new PageResult();
	}
}
