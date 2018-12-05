package com.pbtd.playclick.youku.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pbtd.playclick.base.common.web.RequestUtil;
import com.pbtd.playclick.integrate.controller.FileUpload;
import com.pbtd.playclick.integrate.controller.centralController;
import com.pbtd.playclick.pinyin.service.PinYinService;
import com.pbtd.playclick.youku.service.face.IYoukuStorageService;

@Controller("youku.YoukuStorageController")
@RequestMapping("/youku/storage")
public class YoukuStorageController {

	@Autowired
	private  IYoukuStorageService youkustotsge;
	@Autowired
	private FileUpload fielupdate;
	@Autowired
	private  centralController central;
	@Autowired
	private PinYinService pinYinService;

	/**
	 * 优酷数据 根据选中id入库
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/savesisStorage" )
	public String savesisStorage(ModelMap model,HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		String[] id=queryParams.get("id").toString().split(",");
		for (String s : id) {
			queryParams.put("id", s);
			queryParams.put("seriescode", s);
			youkustotsge.insertsisStorageid(queryParams);
			youkustotsge.updatevodmappingid(queryParams);
		}
		return "";
	}
	/**
	 * 优酷数据自动汇聚入库
	 * @throws InterruptedException 
	 */
	@RequestMapping("/saveStrategyyouku")
	@ResponseBody
	public void saveStrategyyouku() throws InterruptedException { 
		Map<String, Object> queryParams=new HashMap<>();
		youkustotsge.saveStrategyyouku(queryParams);
	}

	/**
	 * 优酷数据自动汇聚入库 2小时入库一次
	 * @throws InterruptedException 
	 */
	@RequestMapping("/saveyoukutime")
	@ResponseBody
	public void saveyoukutime()  { 
		Map<String, Object> queryParams=new HashMap<>();
		youkustotsge.saveStrategyyouku(queryParams);
		try {
			//自动汇聚 到运营库接口
			Thread hth = new Thread(){
				@Override
				public void run() {
					try {
						central.gethttp( central.central_url);
					} catch (IllegalArgumentException  ec) {
						interrupted();
					}  
				}
			};
			hth.start();
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//自动下发  更新下发状态
		youkustotsge.updatevod_albumissue(queryParams);

	}
}
