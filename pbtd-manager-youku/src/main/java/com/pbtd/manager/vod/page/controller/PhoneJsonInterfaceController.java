package com.pbtd.manager.vod.page.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pbtd.manager.base.common.web.RequestUtil;
import com.pbtd.manager.vod.page.service.face.BussChannelService;
import com.pbtd.manager.vod.page.service.face.IJsonInterfaceService;

/**
 * 中心平台通知分平台接口 phone
 * 
 * @author zr
 *
 */
@Controller("pbtdController.PhoneJsonInterfaceController")
@RequestMapping("/integrate/outputjson")
public class PhoneJsonInterfaceController {

	@Autowired
	private VodcentralController vodcentral;

	@Autowired
	private IJsonInterfaceService jsonInterfaceService;

	@Autowired
	private BussChannelService bussChannelService;

	// 专辑
	@RequestMapping(value = "/phone/album")
	public String phonealbum(Model model, HttpServletRequest request) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		final String ids = queryParams.get("ids") == null ? "" : queryParams.get("ids").toString();
		String s1 = "";
		if(ids.equals("")){
			Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
			s1=sdf.format(date); // 2015-02-09 format()才是格式化
		}
		final String curtime = s1 == "" ? "" : s1;
		try {
			Thread hth = new Thread() {
				@Override
				public void run() {
					try {
						// 根据日期获取专辑信息
						Map<String, Object> map = new HashMap<>();
						map.put("curtime", curtime);
						map.put("ids",ids);
						map.put("url", vodcentral.phone_album);
						jsonInterfaceService.phonealbuminfo(map);
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
		return "1";
	}

	// 频道
	@RequestMapping(value = "/phone/channel")
	public String phonechannel(Model model, HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
		String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
		final String curtime = s1 == "" ? dateNowStr : s1;

		try {
			Thread hth = new Thread() {
				@Override
				public void run() {
					try {
						// 根据日期获取频道信息
						Map<String, Object> map = new HashMap<>();
						map.put("curtime", curtime);
						map.put("url", vodcentral.phone_channel);
						jsonInterfaceService.phonechannel(map);
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
		return "1";
	}

	// 标签
	@RequestMapping(value = "/phone/label")
	public String phonelabel(Model model, HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
		String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
		final String curtime = s1 == "" ? dateNowStr : s1;
		try {
			Thread hth = new Thread() {
				@Override
				public void run() {
					try {
						// 根据日期获取标签信息
						Map<String, Object> map = new HashMap<>();
						map.put("curtime", curtime);
						map.put("url", vodcentral.phone_label);
						jsonInterfaceService.phonelabel(map);
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
		return "1";
	}

	// 演员
	@RequestMapping(value = "/phone/actors")
	public String phoneactors(Model model, HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
		String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
		final String curtime = s1 == "" ? dateNowStr : s1;
		try {
			Thread hth = new Thread() {
				@Override
				public void run() {
					try {
						// 根据日期获取演员信息
						Map<String, Object> map = new HashMap<>();
						map.put("curtime", curtime);
						map.put("url", vodcentral.actors);
						jsonInterfaceService.actors(map);
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
		return "1";
	}

	// 角标vod_corner
	@RequestMapping(value = "/phone/corner")
	public String phonecorner(Model model, HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
		String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
		final String curtime = s1 == "" ? dateNowStr : s1;
		try {
			Thread hth = new Thread() {
				@Override
				public void run() {
					try {
						// 根据日期获取角标信息
						Map<String, Object> map = new HashMap<>();
						map.put("curtime", curtime);
						map.put("url", vodcentral.corner);
						jsonInterfaceService.corner(map);
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
		return "1";
	}

	// 付费包vod_collectfeesbag
	@RequestMapping(value = "/phone/collectfeesbag")
	public String phonecollectfeesbag(Model model, HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
		String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
		final String curtime = s1 == "" ? dateNowStr : s1;
		try {
			Thread hth = new Thread() {
				@Override
				public void run() {
					try {
						// 根据日期获取付费包信息
						Map<String, Object> map = new HashMap<>();
						map.put("curtime", curtime);
						map.put("url", vodcentral.collectfeesbag);
						jsonInterfaceService.collectfeesbag(map);
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
		return "1";
	}

	// vod_phone_hotsearch推荐
	@RequestMapping(value = "/phone/hotsearch")
	public String hotsearch(Model model, HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
		String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
		final String curtime = s1 == "" ? dateNowStr : s1;
		try {
			Thread hth = new Thread() {
				@Override
				public void run() {
					try {
						// 根据日期获取热搜信息
						Map<String, Object> map = new HashMap<>();
						map.put("curtime", curtime);
						map.put("url", vodcentral.hotsearch);
						jsonInterfaceService.phonehotsearch(map);
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
		return "1";
	}

	// vod_phone_special 专题
	@RequestMapping(value = "/phone/special")
	public String special(Model model, HttpServletRequest request) {
		Map<String, Object> queryParams = RequestUtil.asMap(request, false);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(d);
		Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
		String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
		final String curtime = s1 == "" ? dateNowStr : s1;
		try {
			Thread hth = new Thread() {
				@Override
				public void run() {
					try {
						// 根据日期获取专题信息
						Map<String, Object> map = new HashMap<>();
						map.put("curtime", curtime);
						map.put("url", vodcentral.phone_special);
						jsonInterfaceService.phonespecial(map);
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
		return "1";
	}

	// vod_buss_channel 项目下的频道
	@RequestMapping(value = "/phone/bussChannel")
	public String bussChannel(Model model, HttpServletRequest request) {
		String channelString = request.getParameter("channelCodes") == null ? ""
				: request.getParameter("channelCodes").toString();
		if ("".equals(channelString)) {
			return "0";
		}
		//删除原有频道
		bussChannelService.del();
		//保存项目频道
		bussChannelService.insert(channelString);
		return "1";
	}
	// vod_phone_recommandpic 频道轮播图
		@RequestMapping(value = "/phone/recommandpic")
		public String recommandpic(Model model, HttpServletRequest request) {
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateNowStr = sdf.format(d);
			Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
			String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
			final String curtime = s1 == "" ? dateNowStr : s1;
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 根据日期获取专题信息
							Map<String, Object> map = new HashMap<>();
							map.put("curtime", curtime);
							map.put("url", vodcentral.recommandpic);
							jsonInterfaceService.phonerecommandpic(map);
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
			return "1";
		}
		//  开机轮播图
		@RequestMapping(value = "/phone/startslideshow")
		public String startslideshow(Model model, HttpServletRequest request) {
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateNowStr = sdf.format(d);
			Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
			String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
			final String curtime = s1 == "" ? dateNowStr : s1;
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 根据日期获取专题信息
							Map<String, Object> map = new HashMap<>();
							map.put("curtime", curtime);
							map.put("url", vodcentral.startslideshow);
							jsonInterfaceService.phonestartslideshow(map);
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
			return "1";
		}
		//  专区推荐轮播图
		@RequestMapping(value = "/phone/slideshow")
		public String slideshow(Model model, HttpServletRequest request) {
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateNowStr = sdf.format(d);
			Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
			String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
			final String curtime = s1 == "" ? dateNowStr : s1;
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 根据日期获取专题信息
							Map<String, Object> map = new HashMap<>();
							map.put("curtime", curtime);
							map.put("url", vodcentral.slideshow);
							jsonInterfaceService.phoneslideshow(map);
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
			return "1";
		}
		//  热播 推荐
		@RequestMapping(value = "/phone/hotseries")
		public String hotseries(Model model, HttpServletRequest request) {
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateNowStr = sdf.format(d);
			Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
			String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
			final String curtime = s1 == "" ? dateNowStr : s1;
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 根据日期获取专题信息
							Map<String, Object> map = new HashMap<>();
							map.put("curtime", curtime);
							map.put("url", vodcentral.hotseries);
							jsonInterfaceService.phonehotseries(map);
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
			return "1";
		}
		// 文字 推荐
		@RequestMapping(value = "/phone/textrecommendation")
		public String textrecommendation(Model model, HttpServletRequest request) {
			Map<String, Object> queryParams = RequestUtil.asMap(request, false);
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateNowStr = sdf.format(d);
			Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
			String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
			final String curtime = s1 == "" ? dateNowStr : s1;
			try {
				Thread hth = new Thread() {
					@Override
					public void run() {
						try {
							// 根据日期获取专题信息
							Map<String, Object> map = new HashMap<>();
							map.put("curtime", curtime);
							map.put("url", vodcentral.textrecommendation);
							jsonInterfaceService.phonetextrecommendation(map);
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
			return "1";
		}
		
//		//  #手机端标签频道路径
//				@RequestMapping(value = "/phone/labelchannel")
//				public String labelchannel(Model model, HttpServletRequest request) {
//					Map<String, Object> queryParams = RequestUtil.asMap(request, false);
//					Date d = new Date();
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					String dateNowStr = sdf.format(d);
//					Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
//					String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
//					final String curtime = s1 == "" ? dateNowStr : s1;
//					try {
//						Thread hth = new Thread() {
//							@Override
//							public void run() {
//								try {
//									// 根据日期获取专题信息
//									Map<String, Object> map = new HashMap<>();
//									map.put("curtime", curtime);
//									map.put("url", vodcentral.labelchannel);
//									jsonInterfaceService.labelchannel(map);
//								} catch (IllegalArgumentException ec) {
//									interrupted();
//								}
//							}
//						};
//						hth.start();
//						Thread.sleep(200);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					return "1";
//				}
//	
			//  #手机端标签频道路径
							@RequestMapping(value = "/phone/labeltype")
							public String labeltype(Model model, HttpServletRequest request) {
								Map<String, Object> queryParams = RequestUtil.asMap(request, false);
								Date d = new Date();
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								String dateNowStr = sdf.format(d);
								Date date = new Date(queryParams.get("curtime") == null ? "" : queryParams.get("curtime").toString());
								String s1 = sdf.format(date); // 2015-02-09 format()才是格式化
								final String curtime = s1 == "" ? dateNowStr : s1;
								try {
									Thread hth = new Thread() {
										@Override
										public void run() {
											try {
												// 根据日期获取专题信息
												Map<String, Object> map = new HashMap<>();
												map.put("curtime", curtime);
												map.put("url", vodcentral.labeltype);
												jsonInterfaceService.labeltype(map);
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
								return "1";
							}
							
						//  #手机端cp源路径
							@RequestMapping(value = "/phone/cpsource")
							public String cpsource(Model model, HttpServletRequest request) {
								Map<String, Object> queryParams = RequestUtil.asMap(request, false);
								try {
									Thread hth = new Thread() {
										@Override
										public void run() {
											try {
												// 根据日期获取专题信息
												Map<String, Object> map = new HashMap<>();
												map.put("url", vodcentral.phonecpsourcetype);
												jsonInterfaceService.cpsourcetype(map);
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
								return "1";
							}
}
