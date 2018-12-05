package com.pbtd.manager.vod.buss.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.util.LogAnnotation;
import com.pbtd.manager.vod.buss.domain.Vodbussinfo;
import com.pbtd.manager.vod.buss.service.face.IVodbussinfoService;
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;

/**
 * @author zr
 */
@Controller
@RequestMapping("/vod/vodbussinfo")
@PropertySource(value = { "classpath:config/tvJsonInterface.properties" })
public class VodbussinfoController {

	@Value("${tv_busslUrl}")
	private String tvBusslUrl;
	@Autowired
	private IVodbussinfoService vodbussinfoservice;
	@Autowired
	private IDictionaryService dictionaryService;

	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = { "/index", "" })
	public String index() {
		return "/vod/buss/vodbussinfo/index";
	}

	/**
	 * GET 创建
	 *
	 * @return 视图路径
	 */
	@RequestMapping("/create")
	public String create() {
		return "/vod/buss/vodbussinfo/create";
	}

	/**
	 * GET 编辑
	 *
	 * @param id
	 *            标识
	 * @return 视图路径
	 */
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<Map<String, Object>> m=dictionaryService.findDictionaryMap(null,"choosecpsource");
		model.addAttribute("cplist", m);
		return "/vod/buss/vodbussinfo/edit";
	}

	/**
	 * 模糊统计符合查询条件的记录总数
	 *
	 * @param request
	 *            查询参数
	 * @return 记录总数
	 */
	@ResponseBody
	@RequestMapping("/count")
	public int count(HttpServletRequest request) {
		return this.vodbussinfoservice.count(RequestUtil.asMap(request, false));
	}

	/**
	 * 模糊获取符合查询条件的分页记录
	 * 
	 * @param page
	 *            页码
	 * @param limit
	 *            页长
	 * @param request
	 *            查询参数
	 * @return 记录列表
	 */
	@ResponseBody
	@RequestMapping("/page")
	public PageResult page(@RequestParam(value = "page") int page, @RequestParam(value = "rows") int limit,
			HttpServletRequest request) {
		List<Vodbussinfo> data = new ArrayList<Vodbussinfo>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		int total = this.vodbussinfoservice.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo = new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data = this.vodbussinfoservice.find(queryParams);
		return new PageResult(total, data);

	}

	/**
	 * 根据标识获取记录
	 *
	 * @param id
	 *            标识
	 * @return 记录
	 */
	@ResponseBody
	@RequestMapping("/load/{id}")
	public Vodbussinfo load(@PathVariable("id") int id) {
		Vodbussinfo vodbussinfo = null;
		if (id > 0) {
			vodbussinfo = this.vodbussinfoservice.load(id);
		} else {
			int c = this.vodbussinfoservice.generatePosition(null);
			vodbussinfo = new Vodbussinfo(-1, 0, null, null, null, null, null, null, null, null, null, null, null, 0,null);
		}
		return vodbussinfo;
	}

	/**
	 * 精确判断是否存在记录
	 * 
	 * @param id
	 *            标识
	 * @param request
	 *            查询参数
	 * @return 记录条数
	 */
	@RequestMapping("/exist/{id}")
	@ResponseBody
	public int exist(@PathVariable("id") int id, HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		List<Vodbussinfo> vodbussinfo = this.vodbussinfoservice.find(queryParams);
		if (vodbussinfo.size() == 1) {
			return vodbussinfo.get(0).getBussId() == id ? 0 : 1;
		} else {
			return vodbussinfo.size();
		}
	}

	/**
	 * 插入记录
	 *
	 * @param vodChannel
	 *            VodChannel实例
	 * @return 被插入的记录标识
	 */
	@LogAnnotation(operationInfo = "点播-分组管理-新增操作")
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public int create(Vodbussinfo vodbussinfo, HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		queryParams.put("status", 1);
		this.vodbussinfoservice.insert(vodbussinfo, queryParams);
		return 1;
	}

	/**
	 * POST 编辑
	 *
	 * @param id
	 *            标识
	 * @param vodChannel
	 *            VodChannel实例
	 * @return int 被修改的记录条数
	 * @throws ServletException
	 */
	@LogAnnotation(operationInfo = "点播-分组管理-更改操作")
	@ResponseBody
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public int edit(@PathVariable("id") int id, Vodbussinfo vodbussinfo, HttpServletRequest request)
			throws ServletException {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.vodbussinfoservice.update(vodbussinfo, queryParams);
	}

	/**
	 * 上下线操作
	 */
	@ResponseBody
	@RequestMapping("/updateSta")
	public int updateSta(Vodbussinfo vodbussinfo, HttpServletRequest request) throws ServletException {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		return this.vodbussinfoservice.update(vodbussinfo, queryParams);
	}

	/**
	 * 频道同步
	 */
	@ResponseBody
	@RequestMapping("/searchBussId/{ids}")
	public int searchBussId(@PathVariable("ids") String ids) throws ServletException {
		System.out.println("下发频道同步");
		Map<String, Object> queryParams = new HashMap<String, Object>();
		if (ids != null && !",".equals(ids) && !"".equals(ids)) {
			queryParams.put("bussId_", ids.split(","));
			List<Map<String, String>> list = vodbussinfoservice.findHttp(queryParams);
			for (Map<String, String> m : list) {
				final String address = (String) m.get("address");
				final String channelCode = (String) m.get("channelCode");
				try {
					Thread hth = new Thread() {
						@Override
						public void run() {
							try {
								// 同步到分平台接口
								String url = address + tvBusslUrl + channelCode;
								notice(url);
							} catch (IllegalArgumentException ec) {
								interrupted();
							}
						}
					};
					hth.start();
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
		return 1;
	}

	/**
	 * POST 删除多条
	 *
	 * @param ids
	 *            标识列表
	 * @return 被删除的记录条数
	 */
	@LogAnnotation(operationInfo = "点播-分组管理-删除操作")
	@ResponseBody
	@RequestMapping(value = "/deletes", method = RequestMethod.POST)
	public int deletes(HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String[] id = queryParams.get("id").toString().split(",");
		queryParams.put("seriescode_", id);
		if (id == null || id.length == 1) {
			queryParams.put("bussId", id[0]);
		} else {
			queryParams.put("bussId_", id);
		}
		return this.vodbussinfoservice.deletes(queryParams);
	}

	protected int notice(String requestUrl) {
		// String requestUrl =central.actors;
		String requestMethod = "GET";
		String jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStream inputStream = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = buffer.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return 0;
		} catch (ProtocolException e) {
			e.printStackTrace();
			return 0;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;

	}

}
