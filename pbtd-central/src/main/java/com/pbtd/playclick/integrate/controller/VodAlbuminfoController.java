package com.pbtd.playclick.integrate.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pbtd.playclick.base.common.web.RequestUtil;
import com.pbtd.playclick.integrate.domain.VodAlbuminfo;
import com.pbtd.playclick.integrate.service.impl.VodAlbuminfoService;
import com.pbtd.playclick.page.PageResult;
import com.pbtd.playclick.page.QueryObject;
import com.pbtd.playclick.util.PinYinUtil;

import net.sf.json.JSONObject;



@Controller("vod.VodAlbuminfoController")
@RequestMapping("/vod/albuminfo")
public class VodAlbuminfoController {
	@Autowired
	private StrategyxController strategyxController;
	@Autowired
	private centralController  central;

	@Autowired
	private VodAlbuminfoService vodalbuminfoService;
	@Autowired
	private DictionaryController dictionary;
	private String issueids=null;

	@RequestMapping(value = {"/index"})
	public String index(ModelMap model,HttpServletRequest request) {
		List<Map<String, Object>> cpsource=dictionary.findDictionaryname("choosecpsource", request);
		model.addAttribute("cpsource", cpsource);
		return "/vod/albumsinfo/index";
	}
	/**
	 * GET 编辑
	 *
	 * @param id 标识
	 * @return 视图路径
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id) {
		return "/vod/albumsinfo/edit";
	}
	/**
	 * 插入记录
	 *
	 * @return 被插入的记录标识
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public int create(VodAlbuminfo vodAlbuminfo, HttpServletRequest request) {
		vodAlbuminfo.setCpcode("3");
		vodAlbuminfo.setCpname("自增");
		//添加拼音
		if (vodAlbuminfo.getSeriesname() !=null && !"".equals(vodAlbuminfo.getSeriesname()) ) {
	   		String name = vodAlbuminfo.getSeriesname();
	   		// 清除掉所有特殊字符
	   		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
	   		Pattern p = Pattern.compile(regEx);
	   		Matcher m = p.matcher(name);
	   		name = m.replaceAll("").trim();
	   		String pingYin=PinYinUtil.getPingYin(name).toLowerCase();
	   		String headChar =PinYinUtil.getPinYinHeadChar(name).toLowerCase();
	   		vodAlbuminfo.setPinyin(pingYin);
	   		vodAlbuminfo.setPinyinsuoxie(headChar);
			}
		this.vodalbuminfoService.insert(vodAlbuminfo);
		return 1;
	}

	/**
	 * POST 编辑
	 *
	 * @param id 标识
	 * @return int 被修改的记录条数
	 * @throws ServletException
	 */
	@ResponseBody
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public int edit(@PathVariable("id") int id,VodAlbuminfo vodAlbuminfo) throws ServletException {
		return this.vodalbuminfoService.update(vodAlbuminfo);
	}

	/**
	 * 分页查询
	 * @param page
	 * @param limit
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/page")
	public PageResult page(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
		List<VodAlbuminfo> data = new ArrayList<VodAlbuminfo>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		int total = this.vodalbuminfoService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.vodalbuminfoService.find(queryParams);
		return new PageResult(total, data);
	}

	@RequestMapping("/show/{id}")
	public String edit(@PathVariable("id") String id,ModelMap model,HttpServletRequest request) {
		model.addAttribute("id",id);
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("centralcode", id);
		List<Map<String, Object>> gitlist=vodalbuminfoService.findAlbumsinfovideo(queryParams);
		model.addAttribute("gitlist",gitlist);
		return "/vod/albumsinfo/show";
	}

	@ResponseBody
	@RequestMapping("/load/{id}")
	public VodAlbuminfo load(@PathVariable("id") String id,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("centralcode", id);
		VodAlbuminfo albus = null;
		if (!"".equals(id) && id !=null && !id.equals("0") ) {
			albus = this.vodalbuminfoService.load(queryParams);
		} 
		else {
			albus  =new VodAlbuminfo();
			albus.setSeriescode(System.currentTimeMillis()+"");
		}
		return albus;
	}

	@RequestMapping("/showvideo/{id}")
	public String video(@PathVariable("id") String id,
			ModelMap model,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("id", id);
		List<Map<String, Object>> gitvideomap=vodalbuminfoService.findAlbumsinfovideo(queryParams);
		model.addAttribute("map",gitvideomap.get(0));
		return "/vod/albumsinfo/showvideo";
	}

	//手动下发数据到中心运行库
	@ResponseBody
	@RequestMapping("/savesissue")
	public int savesissue(ModelMap model,HttpServletRequest request) throws InterruptedException {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String[] id=queryParams.get("id").toString().split(",");
		queryParams.put("centralcode_", id);
		//调用中心运营接口
		issueids=queryParams.get("id").toString();	
		Thread hth = new Thread(){
			@Override
			public void run() {
				try {
					//手动下发 到运营库接口
					central.gethttp(central.phone_albumurl+issueids);
				} catch (IllegalArgumentException ec) {
					interrupted();
				}  
			}
		};
		hth.start();
		Thread.sleep(200);
		vodalbuminfoService.updateissue(queryParams);
		return 1;
	}
	//运营调用节目集 获取下发数据
	@RequestMapping( "/issuealbumsinfojson")
	public  String issuealbumsinfojson(HttpServletRequest request,HttpServletResponse response) throws IOException  {
		JSONObject json = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String[] id=queryParams.get("id").toString().split(",");
		queryParams.put("seriescode_", id);
		queryParams.put("start", 0);
		queryParams.put("pageSize",1000);
		List<VodAlbuminfo> list=	vodalbuminfoService.find(queryParams);
		response.setContentType("text/html;charset=utf-8");
		json.accumulate("body", JSON.toJSONString(list));
		response.getWriter().write(json.toString());
		return null;
	}
	//运营调用节目集播放数据 获取下发数据
	@RequestMapping( "/issuealbumsinfovideojson")
	public  String issuealbumsinfovideojson(HttpServletRequest request,HttpServletResponse response) throws IOException  {
		JSONObject json = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String[] id=queryParams.get("id").toString().split(",");
		queryParams.put("seriescode_", id);
		queryParams.put("id", null);
		List<Map<String, Object>> list=	vodalbuminfoService.findAlbumsinfovideo(queryParams);
		response.setContentType("text/html;charset=utf-8");
		json.accumulate("body", JSON.toJSONString(list));
		response.getWriter().write(json.toString());
		return null;
	}


	/**
	 *导演 演员
	 *
	 * @param id 标识
	 * @return 视图路径
	 */
	@RequestMapping("/actorslist")
	public String actorslist() {
		return "/vod/albumsinfo/actorslist";
	}
	/**
	 * POST 删除多条
	 *
	 * @param ids 标识列表
	 * @return 被删除的记录条数
	 */
	@ResponseBody
	@RequestMapping(value = "/deletes", method = RequestMethod.POST)
	public int deletes( HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String[] id=queryParams.get("id").toString().split(",");
		queryParams.put("seriescode_", id);
		return this.vodalbuminfoService.deletes(queryParams);
	}


	@RequestMapping("/editvideo/{seriesCode}/{centralcode}")
	public String editvideo(@PathVariable("seriesCode") String seriesCode,@PathVariable("centralcode") String centralcode,ModelMap model,HttpServletRequest request) {
		model.addAttribute("id",seriesCode);
		model.addAttribute("seriesCode",seriesCode);
		model.addAttribute("centralcode",centralcode);
		
		return "/vod/albumsinfo/editvideo";
	}

	/**
	 * 分页查询
	 * @param page
	 * @param limit
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/videopage")
	public PageResult videopage(@RequestParam(value="page")int page, @RequestParam(value="rows")int limit, HttpServletRequest request) {
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		int total = this.vodalbuminfoService.countAlbumsinfovideo(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo=new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data =this.vodalbuminfoService.findAlbumsinfovideo(queryParams);
		return new PageResult(total, data);
	}
	/**
	 * GET 编辑
	 *剧集新增
	 * @param id 标识
	 * @return 视图路径
	 */
	@RequestMapping("/video/create/{seriesCode}/{centralcode}")
	public String videocreate(@PathVariable("seriesCode") String seriesCode,@PathVariable("centralcode") String centralcode
			,ModelMap model,HttpServletRequest request) {
		model.addAttribute("seriesCode",seriesCode);
		List<Map<String, Object>> cpsource=dictionary.findDictionaryname("choosecpsource", request);
		model.addAttribute("cpsource", cpsource);
		model.addAttribute("centralcode", centralcode);
		return "/vod/albumsinfo/videocreate";
	}
	/**
	 * 插入记录
	 * *剧集新增
	 * @return 被插入的记录标识
	 */
	@ResponseBody
	@RequestMapping(value = "/video/insert/{seriesCode}/{centralcode}", method = RequestMethod.POST)
	public int create(@PathVariable("seriesCode") String seriesCode,@PathVariable("centralcode") String centralcode,
			ModelMap model,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("seriesCode", seriesCode);
		queryParams.put("centralcode",centralcode);
		this.vodalbuminfoService.insertvideo(queryParams);
		return 1;
	}
	/**
	 * POST 剧集删除多条
	 * @param ids 标识列表
	 * @return 被删除的记录条数
	 */
	@ResponseBody
	@RequestMapping(value = "/video/deletes", method = RequestMethod.POST)
	public int videodeletes( HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.vodalbuminfoService.deletesvideo(queryParams);
	}

}
