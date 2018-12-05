package com.pbtd.manager.vod.phone.special.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
import com.pbtd.manager.vod.page.PageResult;
import com.pbtd.manager.vod.page.QueryObject;
import com.pbtd.manager.vod.page.service.face.IDictionaryService;
import com.pbtd.manager.vod.phone.special.domain.VodSpecial;
import com.pbtd.manager.vod.phone.special.service.face.IVodSpecialService;
import com.pbtd.manager.util.SequenceUtil;

/**
 * @author zr
 */
@Controller
@RequestMapping("/vod/phone/vodSpecial")
public class VodSpecialController {

	@Autowired
	private IVodSpecialService vodspecialService;

	@Autowired
	private IDictionaryService dictionaryService;
	@Autowired
	private SequenceUtil sequenceUtil;

	/**
	 * GET 查询
	 *
	 * @return 视图路径
	 */
	@RequestMapping(value = { "/index", "" })
	public String index() {
		return "/vod/phone/special/index";
	}

	/**
	 * GET 创建
	 *
	 * @return 视图路径
	 */
	@RequestMapping("/create")
	public String create() {
		return "/vod/phone/special/create";
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
		return "/vod/phone/special/edit";
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
		return this.vodspecialService.count(RequestUtil.asMap(request, false));
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
		List<VodSpecial> data = new ArrayList<VodSpecial>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		int total = this.vodspecialService.count(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo = new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data = this.vodspecialService.find(queryParams);
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
	public VodSpecial load(@PathVariable("id") int id) {
		VodSpecial vodSpecial = null;
		if (id > 0) {
			vodSpecial = this.vodspecialService.load(id);
		} else {
			// int c=this.vodspecialService.generatePosition(null);
			vodSpecial = new VodSpecial(-1, null, null, null, null, null, null, null, null, null, 0, 1, null, 0, 0, 0);
		}
		return vodSpecial;
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
		List<VodSpecial> vodSpecial = this.vodspecialService.find(queryParams);
		if (vodSpecial.size() == 1) {
			return vodSpecial.get(0).getId() == id ? 0 : 1;
		} else {
			return vodSpecial.size();
		}
	}

	/**
	 * 插入记录
	 *
	 * @param vodChannel
	 *            VodChannel实例
	 * @return 被插入的记录标识
	 */
	@LogAnnotation(operationInfo = "点播-phone-专题管理-添加专题操作")
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public int create(VodSpecial vodSpecial, HttpServletRequest request) {
		Map<String, Object> queryParams = new HashMap();
		Map<String, Object> maxmap = vodspecialService.findmaxVSminsequence(queryParams);// 查询更改排序的最大最小原始值
		int curmax = 0;
		if (maxmap != null) {
			curmax = Integer.parseInt(maxmap.get("max") == null ? "0" : maxmap.get("max").toString());// 原始数据排序最大值
		}
		vodSpecial.setSequence(curmax += 1);
		this.vodspecialService.insert(vodSpecial);
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
	@LogAnnotation(operationInfo = "点播-phone-专题管理-更改专题操作")
	@ResponseBody
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public int edit(@PathVariable("id") int id, VodSpecial vodSpecial) throws ServletException {
		int newmax = vodSpecial.getSequence();
		int type = vodSpecial.getType();
		int newmin = 0;
		int max = newmax;// 排序最大值
		int min = newmin;// 排序最小值
		Map<String, Object> mapsequence = new HashMap<>();
		mapsequence.put("id", Integer.toString(id));
		mapsequence.put("type", type);
		Map<String, Object> maxmap = vodspecialService.findmaxVSminsequence(mapsequence);// 查询更改排序的最大最小原始值
		int curmax = 0;
		int curmin = 0;
		if (maxmap != null) {
			curmax = Integer.parseInt(maxmap.get("max") == null ? "0" : maxmap.get("max").toString());// 原始数据排序最大值
			curmin = Integer.parseInt(maxmap.get("min") == null ? "0" : maxmap.get("min").toString());// 原始数据排序最大值
		}
		if (newmax < curmax) {//// 如果原始数据最大值比更改排序的最大值大 复制为最大值
			max = curmax;
		}
		if (newmin > curmin) {// 如果原始数据最小值比更改排序的最小值小 复制为最小值
			min = curmin;
		}
		mapsequence.put("max", max);
		mapsequence.put("min", min);
		List<Map<String, Object>> list = vodspecialService.findsequence(mapsequence);
		Map<String, Object> map = new HashMap<>();
		String[] newsequences = sequenceUtil.Sortball(Integer.toString(newmax));
		for (int i = 0; i < list.size(); i++) {// 自动更改排序递增递减
			map = list.get(i);
			min += 1;
			int j = newsequences.length;
			for (int jj = 0; jj < j; jj++) {// 修改排序=原始排序+jj
											// jj为循环次数（修改改值与原始值的间隔值）
				if (min == Integer.parseInt(newsequences[jj])) {
					min += 1;
				}
			}
			// 修改排序=原始排序+jj jj为循环次数（修改改值与原始值的间隔值）
			String code = map.get("id").toString();
			VodSpecial vodSpecial1 = new VodSpecial();
			vodSpecial1.setId(Integer.parseInt(code));
			vodSpecial1.setSequence(min);
			vodSpecial1.setStatus(-1);
			vodSpecial1.setTextcolor(-1);
			vodspecialService.update(vodSpecial1);
		}
		// 更改编辑的排序信息
		vodSpecial.setId(id);
		vodSpecial.setStatus(-1);
		vodSpecial.setTextcolor(-1);
		return this.vodspecialService.update(vodSpecial);
	}

	/**
	 * POST 删除多条
	 *
	 * @param ids
	 *            标识列表
	 * @return 被删除的记录条数
	 */
	@LogAnnotation(operationInfo = "点播-phone-专题管理-删除专题操作")
	@ResponseBody
	@RequestMapping(value = "/deletes", method = RequestMethod.GET)
	public int deletes(HttpServletRequest request) {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		String ids = request.getParameter("id");
		String[] idarray = ids.split(",");
		queryParams.put("id", ids);
		// 查询更改排序的原始值
		List<Map<String, Object>> oldsequencelist = vodspecialService.findsequencesum(queryParams);
		int min = Integer.parseInt(oldsequencelist.get(0).get("sequence") == null ? "1"
				: oldsequencelist.get(0).get("sequence").toString());
		queryParams.put("min", min);
		List<Map<String, Object>> list = vodspecialService.findsequence(queryParams);
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {// 自动更改排序递增递减
			map = list.get(i);
			int curmin = Integer.parseInt(map.get("sequence") == null ? "1" : map.get("sequence").toString());
			int j = oldsequencelist.size();
			int c = 1;
			for (int jj = 0; jj < j; jj++) {
				if (curmin > Integer.parseInt(oldsequencelist.get(jj).get("sequence") == null ? "1"
						: oldsequencelist.get(jj).get("sequence").toString())) {
					c = jj + 1;
				}
			}
			curmin -= c;
			VodSpecial vodSpecial1 = new VodSpecial();
			vodSpecial1.setId(Integer.parseInt(map.get("id").toString()));
			vodSpecial1.setSequence(curmin);
			vodSpecial1.setStatus(-1);
			vodSpecial1.setTextcolor(-1);
			vodspecialService.update(vodSpecial1);
		}
		// 删除
		for (String num : idarray) {
			int id = Integer.parseInt(num);
			queryParams.put("id", id);
			this.vodspecialService.deletes(queryParams);
		}
		return 1;
	}

	@LogAnnotation(operationInfo = "点播-phone-专题管理-更改专题图片操作")
	@ResponseBody
	@RequestMapping(value = "/editimg/{id}", method = RequestMethod.POST)
	public int editimg(@PathVariable("id") int id, HttpServletRequest request) throws ServletException {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String imgUrl = queryParams.get("imgUrl").toString();
		String imgtype = queryParams.get("imgtype").toString();
		VodSpecial vodSpecial = new VodSpecial();
		if (imgtype.equals("imgurl")) {
			vodSpecial.setImgurl(imgUrl);
		} else {
			vodSpecial.setBackgroundimg(imgUrl);
		}
		vodSpecial.setId(id);
		return this.vodspecialService.updateimg(vodSpecial);
	}

	/**
	 * 批量上线下线
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@LogAnnotation(operationInfo = "点播-phone-专题管理-更改专题状态操作")
	@ResponseBody
	@RequestMapping(value = "/editzt")
	public int editzt(HttpServletRequest request) throws ServletException {
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		int result = 0;
		for (String idnew : ids) {
			int status = Integer.parseInt(request.getParameter("status"));
			int id1 = Integer.parseInt(idnew);
			result = vodspecialService.updatezt(id1, status);
		}
		return result;
	}

	/**
	 * GET 创建 跳转绑定专辑信息
	 * 
	 * @return 视图路径
	 */
	@RequestMapping("/albuminfo/{id}")
	public String albuminfo(@PathVariable("id") int id, HttpServletRequest request, Model model) {
		model.addAttribute("specialid", id);
		return "/vod/phone/special/albuminfo";
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
	@RequestMapping("/albuminfopage")
	public PageResult albuminfopage(@RequestParam(value = "page") int page, @RequestParam(value = "rows") int limit,
			HttpServletRequest request) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		int total = this.vodspecialService.countalbum(queryParams);
		if (total == 0) {
			return new PageResult(total, Collections.EMPTY_LIST);
		}
		QueryObject qo = new QueryObject();
		qo.setPage(page);
		qo.setRows(limit);
		queryParams.put("start", qo.getStart());
		queryParams.put("pageSize", qo.getPageSize());
		data = this.vodspecialService.pagealbum(queryParams);
		return new PageResult(total, data);

	}

	/**
	 * GET 创建 跳转绑定专辑信息
	 * 
	 * @return 视图路径
	 */
	@RequestMapping("/addalbuminfo/{id}")
	public String addalbuminfo(@PathVariable("id") int id, HttpServletRequest request, Model model) {
		String channelName = request.getParameter("channelName") == null ? "" : request.getParameter("channelName");
		String parentCode = request.getParameter("parentCode") == null ? "" : request.getParameter("parentCode");
		String levels = request.getParameter("levels") == null ? "" : request.getParameter("levels");
		if (levels.equals("2")) {
			model.addAttribute("channelCode", parentCode);
		} else if (levels.equals("1")) {
			model.addAttribute("channelCode", id);
		} else {
			model.addAttribute("channelCode", "");
		}
		model.addAttribute("channelName", channelName);
		model.addAttribute("parentCode", parentCode);

		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("levels", 1);
		model.addAttribute("specialid", id);

		queryParams.put("navigationtype", 1);
		model.addAttribute("channellist", dictionaryService.findDictionaryMap(queryParams, "choosechannel"));
		queryParams.put("levels", 1);
		queryParams.put("navigationtype", 2);
		model.addAttribute("channellist2", dictionaryService.findDictionaryMap(queryParams, "choosechannel"));

		return "/vod/phone/special/addalbuminfo";
	}

	/**
	 * 添加绑定专辑信息
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@LogAnnotation(operationInfo = "点播-phone-专题管理-添加绑定专辑操作")
	@ResponseBody
	@RequestMapping(value = "/addalbuminfo")
	public int addalbuminfo(HttpServletRequest request) throws ServletException {
		String id = request.getParameter("id");
		String albumids = request.getParameter("albumid");
		String[] albumid = albumids.split(",");
		int result = 0;
		Map<String, Object> map = new HashMap<>();
		int curmax = 0;
		for (String idnew : albumid) {
			map.clear();
			map.put("special_id", id);
			map.put("seriesCode", idnew);
			Map<String, Object> maxmap = vodspecialService.findalbummaxVSminsequence(map);// 查询更改排序的最大最小原始值
			if (maxmap != null) {
				curmax = Integer.parseInt(maxmap.get("max") == null ? "0" : maxmap.get("max").toString());// 原始数据排序最大值
			}
			map.put("sequence", curmax += 1);
			int i = vodspecialService.countalbum(map);
			if (i < 1) {
				vodspecialService.insertalbum(map);
			}
		}
		return result;
	}

	/**
	 * 删除绑定专辑信息
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@LogAnnotation(operationInfo = "点播-phone-专题管理-删除绑定专辑操作")
	@ResponseBody
	@RequestMapping(value = "/delalbuminfo")
	public int delalbuminfo(HttpServletRequest request) throws ServletException {
		String id = request.getParameter("id");
		String albumids = request.getParameter("albumid");
		String[] albumid = albumids.split(",");
		int result = 0;
		Map<String, Object> mapsequence = new HashMap<>();
		mapsequence.put("albumid", albumids);
		mapsequence.put("special_id", id);
		// 查询更改排序的原始值
		List<Map<String, Object>> oldsequencelist = vodspecialService.findalbumsequencesum(mapsequence);
		int min = Integer.parseInt(oldsequencelist.get(0).get("sequence") == null ? "1"
				: oldsequencelist.get(0).get("sequence").toString());
		mapsequence.put("min", min);
		List<Map<String, Object>> list = vodspecialService.findalbumsequence(mapsequence);
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {// 自动更改排序递增递减
			map = list.get(i);
			int curmin = Integer.parseInt(map.get("sequence") == null ? "1" : map.get("sequence").toString());
			int j = oldsequencelist.size();
			int c = 1;
			for (int jj = 0; jj < j; jj++) {
				if (curmin > Integer.parseInt(oldsequencelist.get(jj).get("sequence") == null ? "1"
						: oldsequencelist.get(jj).get("sequence").toString())) {
					c = jj + 1;
				}
			}
			curmin -= c;
			String seriesCode = map.get("seriesCode").toString();
			map.clear();
			map.put("sequence", curmin);
			map.put("special_id", id);
			map.put("seriesCode", seriesCode);
			result = vodspecialService.updatealbumsequence(map);
		}
		// 删除
		for (String idnew : albumid) {
			map.clear();
			map.put("special_id", id);
			map.put("seriesCode", idnew);
			vodspecialService.deletesalbum(map);
		}
		return result;
	}

	/**
	 * 更改绑定专辑排序
	 * 
	 * @param request
	 * @return
	 * @throws ServletException
	 */
	@LogAnnotation(operationInfo = "点播-phone-专题管理-更改绑定专辑排序操作")
	@ResponseBody
	@RequestMapping(value = "/updatealbumsequence")
	public int updatealbumsequence(HttpServletRequest request) throws ServletException {
		String id = request.getParameter("id");
		String sequence = request.getParameter("sequence");
		String albumid = request.getParameter("albumid");
		String[] albumids = albumid.split(",");
		String[] sequences = sequence.split(",");
		int result = 0;
		Map<String, Object> map = new HashMap<>();
		String[] newsequences = sequenceUtil.Sortball(sequence);
		int newmax = sequenceUtil.getMax(newsequences);// 更改排序最大值
		int newmin = sequenceUtil.getMin(newsequences);// 更改排序最小值
		if (newmax == newmin) {
			newmin = 0;
		}
		int max = newmax;// 排序最大值
		int min = newmin;// 排序最小值
		Map<String, Object> mapsequence = new HashMap<>();
		mapsequence.put("albumid", albumid);
		mapsequence.put("special_id", id);
		Map<String, Object> maxmap = vodspecialService.findalbummaxVSminsequence(mapsequence);// 查询更改排序的最大最小原始值
		int curmax = 0;
		int curmin = 0;
		if (maxmap != null) {
			curmax = Integer.parseInt(maxmap.get("max") == null ? "0" : maxmap.get("max").toString());// 原始数据排序最大值
			curmin = Integer.parseInt(maxmap.get("min") == null ? "0" : maxmap.get("min").toString());// 原始数据排序最大值
		}
		if (newmax < curmax) {//// 如果原始数据最大值比更改排序的最大值大 复制为最大值
			max = curmax;
		}
		if (newmin > curmin) {// 如果原始数据最小值比更改排序的最小值小 复制为最小值
			min = curmin;
		}
		mapsequence.put("max", max);
		mapsequence.put("min", min);
		List<Map<String, Object>> list = vodspecialService.findalbumsequence(mapsequence);
		for (int i = 0; i < list.size(); i++) {// 自动更改排序递增递减
			map = list.get(i);
			min += 1;
			int j = newsequences.length;
			for (int jj = 0; jj < j; jj++) {// 修改排序=原始排序+jj
											// jj为循环次数（修改改值与原始值的间隔值）
				if (min == Integer.parseInt(newsequences[jj])) {
					min += 1;
				}
			}
			String seriesCode = map.get("seriesCode").toString();
			map.clear();
			map.put("sequence", min);
			map.put("special_id", id);
			map.put("seriesCode", seriesCode);
			result = vodspecialService.updatealbumsequence(map);
		}
		// 更改保存编辑的信息
		for (int i = 0; i < albumids.length; i++) {
			map.clear();
			map.put("sequence", sequences[i]);
			map.put("special_id", id);
			map.put("seriesCode", albumids[i]);
			result = vodspecialService.updatealbumsequence(map);
		}
		return result;
	}
}
