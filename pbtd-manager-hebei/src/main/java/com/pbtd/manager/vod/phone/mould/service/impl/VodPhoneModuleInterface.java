package com.pbtd.manager.vod.phone.mould.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.pbtd.manager.conf.ConstantBeanConfig;
import com.pbtd.manager.system.service.IRealmNameService;
import com.pbtd.manager.vod.common.mould.domain.vodMasterplateSon;
import com.pbtd.manager.vod.common.mould.mapper.vodMasterplateSonMapper;
import com.pbtd.manager.vod.phone.mould.domain.VodPhoneModule;
import com.pbtd.manager.vod.phone.mould.mapper.VodPhoneModuleMapper;
import com.pbtd.manager.vod.phone.mould.service.face.IVodPhoneModuleInterface;
import com.pbtd.manager.vod.phone.common.domain.Vodchannel;
import com.pbtd.manager.vod.phone.common.mapper.VodChannelMapper;
import com.pbtd.manager.vod.phone.mould.domain.VodPhoneLinkAlbumList;
import com.pbtd.manager.vod.phone.mould.mapper.VodPhoneLinkAlbumListMapper;
@Service
public class VodPhoneModuleInterface implements IVodPhoneModuleInterface {

	@Autowired
	private VodPhoneModuleMapper moduleMapper;
	@Autowired
	private vodMasterplateSonMapper vodMasterplateSonMapper;
	@Autowired
	private VodPhoneLinkAlbumListMapper VodPhoneLinkAlbumListMapper;
	@Autowired
	public   IRealmNameService realmnameService;
	@Autowired
	private VodChannelMapper channelMapper;
	@Autowired
	private ConstantBeanConfig constantBeanConfig;

	@Override
	public int delete(Integer id) {
		return moduleMapper.delete(id);
	}
	@Override
	public int deletemodule(Map<String, Object> queryParams) {
		return moduleMapper.deletemodule(queryParams);
	}

	@Override
	public Long insert(VodPhoneModule m) {
		Map<String, Object>  map = new HashMap<>();
		map.put("moduleId", m.getModuleid());
		VodPhoneModule VodPhoneModule = moduleMapper.load(map);
		if(VodPhoneModule != null ){
			System.out.println( "修改");
			int aa = moduleMapper.deletemodule(map);
			System.out.println(aa);
		}
		this.moduleMapper.insert(m);
		int a =  moduleMapper.selectnewmoduleid();
		return (long) a;
	}

	@Override
	public VodPhoneModule load(Map<String, Object> queryParams) {
		return moduleMapper.load(queryParams);
	}

	@Override
	public int update(VodPhoneModule m) {
		return moduleMapper.update(m);
	}

	@Override
	public List<Map<String, Object>> find(Map<String, Object> queryParams) {
		return moduleMapper.find(queryParams);
	}

	public int findMaxModuleSeq(Map<String,Object> param){
		return moduleMapper.findMaxModuleSeq(param);
	}

	@Override
	public List<VodPhoneModule> page(Map<String, Object> queryParams) {
		return moduleMapper.page(queryParams);
	}

	@Override
	public int count(Map<String, Object> queryParams) {
		return moduleMapper.count(queryParams);
	}

	@Override
	public List<Map<String, Object>> pagealbum(Map<String, Object> queryParams) {
		return moduleMapper.pagealbum(queryParams);
	}

	@Override
	public int countalbum(Map<String, Object> queryParams) {
		return moduleMapper.countalbum(queryParams);
	}

	@Override
	public int insertalbum(Map<String, Object> queryParams) {
		return moduleMapper.insertalbum(queryParams);
	}

	@Override
	public int deletealbuminfo(Map<String, Object> queryParams) {
		return moduleMapper.deletealbuminfo(queryParams);
	}
	@Override
	public int updateimg(Map<String, Object> queryParams) {
		return moduleMapper.updateimg(queryParams);
	}
	@Override
	public int updateshangxian(Map<String, Object> queryParams) {
		return moduleMapper.updateshangxian(queryParams);
	}

	@Override
	public List<Map<String, Object>> getModuleAlbum(Map<String, Object> queryParams) {
		//获取图片域名
		Map<String,Object> maprealm=realmnameService.findtitle(null);

		List<Map<String, Object>> outputlist = new ArrayList<>();
		//第一步  查询当前频道下的模块
		List<VodPhoneModule> list = moduleMapper.select(queryParams);
		for (VodPhoneModule VodPhoneModule : list) {
			//获取当前模版相关的数据
			//vodMasterplateMapper.selectByPrimaryKey(VodPhoneModule.getMasterplateid());
			//第二步 查询当前模块下的运营位vodMasterplateSon
			List<vodMasterplateSon> sonlist = vodMasterplateSonMapper.selectByPrimaryKey(VodPhoneModule.getMasterplateid());
			Map<String, Object> modulemap = new HashMap<>();
			modulemap.put("moduleName", VodPhoneModule.getName());
			modulemap.put("moduleSequence", VodPhoneModule.getSequence());
			Integer moduleid = VodPhoneModule.getMasterplateid();
			modulemap.put("moduleId",moduleid);
			modulemap.put("mId",VodPhoneModule.getModuleid());
			modulemap.put("img", constantBeanConfig.uploadImageLookUrl+VodPhoneModule.getModulepic());
			modulemap.put("textpicstatus", VodPhoneModule.getTextpicstatus());
			if(moduleid == 108){
				modulemap.put("textrecommendpic", constantBeanConfig.uploadImageLookUrl+VodPhoneModule.getTextrecommendpic());
			}
			modulemap.put("imgStatus", VodPhoneModule.getPicstatus());
			modulemap.put("isShowLeft", VodPhoneModule.getIsshowleft());
			modulemap.put("isShowRight", VodPhoneModule.getIsshowright());
			modulemap.put("moduleStatus", VodPhoneModule.getModulestatus());
			modulemap.put("nameStatus", VodPhoneModule.getNamestatus());
			List<Object> yunyinweilist = new ArrayList<>();
			for (vodMasterplateSon vodMasterplateSon : sonlist) {
				Map<String, Object> yunyuneweimap = new HashMap<>();
				HashMap<String, Object> param = new HashMap<>();
				param.put("moduleId", VodPhoneModule.getModuleid());
				param.put("masterplateNum", 1);
				//param.put("masterplateNum", vodMasterplateSon.getMasterplatenum());
				param.put("size", vodMasterplateSon.getCount());
				param.put("pageStart", vodMasterplateSon.getMasterplatenum()-1);
				param.put("imgtitle", maprealm.get("imgtitle"));
				//第三步 根据模块id 运营位的id 查询 绑定专辑集合
				List<Map<String,Object>> vodPhoneModuleAlbumlist = VodPhoneLinkAlbumListMapper.select(param);
				for (Map<String, Object> map : vodPhoneModuleAlbumlist) {
					int flag = (int) map.get("imgStatus"); 
					if(flag == 0){
						map.remove("imgStatus");
						map.remove("imgurl1");
						map.remove("imgurl2");
						map.remove("imgurl3");
						map.remove("imgurl4");
					}else{
						map.remove("imgStatus");
						map.put("pictureurl1",map.get("imgurl1"));
						map.put("pictureurl2",map.get("imgurl2"));
						map.put("pictureurl3",map.get("imgurl3"));
						map.put("pictureurl4",map.get("imgurl4"));
					}
				}	
				if(vodPhoneModuleAlbumlist == null || vodPhoneModuleAlbumlist.isEmpty()){
					Map  aa = new HashMap<>();
					vodPhoneModuleAlbumlist.add(aa);
				}
				yunyuneweimap.put("itemdata",vodPhoneModuleAlbumlist);
				yunyinweilist.add(yunyuneweimap);
			}
			modulemap.put("data", yunyinweilist);
			if(moduleid != null && moduleid == 108){
				modulemap.put("linkChannel",VodPhoneModule.getLinkchannel());
				modulemap.put("linkLabel",VodPhoneModule.getLinklabel());
				modulemap.put("linkStatus",VodPhoneModule.getLinkstatus());
			}
			outputlist.add(modulemap);
		}
		return outputlist;
	}



	/**
	 * 换一换接口
	 */
	@Override
	public List<Map<String, Object>> getModuleAlbumforOne(Map<String, Object> queryParams) {
		//获取图片域名
		//需要查看的页码：
		int pageNum = Integer.parseInt((String) queryParams.get("pageNum"));
		Map<String,Object> maprealm=realmnameService.findtitle(null);
		List<Map<String, Object>> outputlist = new ArrayList<>();

		//第一步  根据模块id 查询模块信息
		VodPhoneModule VodPhoneModule = moduleMapper.load(queryParams);
		//获取当前模版相关的数据
		//vodMasterplateMapper.selectByPrimaryKey(VodPhoneModule.getMasterplateid());
		//第二步 查询当前模块下的运营位vodMasterplateSon
		List<vodMasterplateSon> sonlist = vodMasterplateSonMapper.selectByPrimaryKey(VodPhoneModule.getMasterplateid());
		Map<String, Object> modulemap = new HashMap<>();
		modulemap.put("moduleName", VodPhoneModule.getName());
		modulemap.put("moduleSequence", VodPhoneModule.getSequence());
		modulemap.put("moduleId", VodPhoneModule.getMasterplateid());
		modulemap.put("mId", VodPhoneModule.getModuleid());
		modulemap.put("img", VodPhoneModule.getModulepic());
		modulemap.put("imgStatus", VodPhoneModule.getPicstatus());
		modulemap.put("isShowLeft", VodPhoneModule.getIsshowleft());
		modulemap.put("isShowRight", VodPhoneModule.getIsshowright());
		modulemap.put("moduleStatus", VodPhoneModule.getModulestatus());
		List<Object> yunyinweilist = new ArrayList<>();
		for (vodMasterplateSon vodMasterplateSon : sonlist) {
			Map<String, Object> yunyuneweimap = new HashMap<>();
			HashMap<String, Object> param = new HashMap<>();
			param.put("moduleId", VodPhoneModule.getModuleid());
			param.put("masterplateNum", vodMasterplateSon.getMasterplatenum());
			int size = vodMasterplateSon.getCount();
			param.put("size", vodMasterplateSon.getCount());
			param.put("pageStart", pageNum*size);
			param.put("imgtitle", maprealm.get("imgtitle"));
			//第三步 根据模块id 运营位的id 查询 绑定专辑集合
			List<Map<String,Object>> vodPhoneModuleAlbumlist = VodPhoneLinkAlbumListMapper.select(param);
			if(vodPhoneModuleAlbumlist == null || vodPhoneModuleAlbumlist.size() < 1){
				param.put("pageStart", 0);
				vodPhoneModuleAlbumlist = VodPhoneLinkAlbumListMapper.select(param);
			}
			for (Map<String, Object> map : vodPhoneModuleAlbumlist) {
				int flag = (int) map.get("imgStatus"); 
				if(flag == 0){
					map.remove("imgStatus");
					map.remove("imgurl1");
					map.remove("imgurl2");
					map.remove("imgurl3");
					map.remove("imgurl4");
				}else{
					map.remove("imgStatus");
					map.put("pictureurl1",map.get("imgurl1"));
					map.put("pictureurl2",map.get("imgurl2"));
					map.put("pictureurl3",map.get("imgurl3"));
					map.put("pictureurl4",map.get("imgurl4"));
				}
			}	
			if(vodPhoneModuleAlbumlist == null || vodPhoneModuleAlbumlist.isEmpty()){
				Map  aa = new HashMap<>();
				vodPhoneModuleAlbumlist.add(aa);
			}
			yunyuneweimap.put("itemdata",vodPhoneModuleAlbumlist); 
			yunyinweilist.add(yunyuneweimap);
		}
		modulemap.put("data", yunyinweilist);
		outputlist.add(modulemap);
		return outputlist;
	}

	@Override
	public int updateMoudleSeq(String strNodes,String treeNodeArr,String targetNodeArr) throws Exception {
		if(strNodes!=null){
			JSONArray jsonArray=JSONArray.parseArray(strNodes);
			Object[] objArr= jsonArray.toArray();
			int len=objArr.length;
			Long [][] nodes=new Long[len][3];
			for(int i=0;i<len;i++){
				Object obj=objArr[i];
				JSONArray jsonObj=JSONArray.parseArray(obj.toString());
				Object[] objs=jsonObj.toArray();
				nodes[i][0]=Long.valueOf(objs[0].toString().substring(objs[0].toString().indexOf("_")+1,objs[0].toString().length()));//id
				nodes[i][1]=(objs[1]==null?null:Long.valueOf(objs[1].toString().substring(objs[1].toString().indexOf("_")+1,objs[1].toString().length())));//pid
				nodes[i][2]=Long.valueOf(objs[2].toString());//seq
			}

			List<Long[]> channelList=new ArrayList<Long[]>();
			for(int i=0;i<len;i++){
				if(nodes[i][1]==null){
					channelList.add(nodes[i]);
				}
			}
			//更新频道顺序
			for(int i=0;i<channelList.size();i++){
				Map<String,Object> params=new HashMap<String,Object>();
				Long[] channel=channelList.get(i);
				/*params.put("id", channel[0]);
				params.put("sequence",i+1 ); //新排序替代全局排序
				 */
				Vodchannel vodChannel=new Vodchannel();
				vodChannel.setChannelCode(Integer.valueOf(channel[0].toString()));
				vodChannel.setSequence(i+1);
				try{
					channelMapper.updateSeqByChannelCode(vodChannel);
					//	this.tvOnlineService.resetChannelSeq(VodPhoneChannel);
				}catch(Exception e){
					throw new Exception("更新频道顺序时：",e);
				}
			}


			int channelLen=channelList.size();
			for(int i=0;i<channelLen;i++){
				List<Long[]> moudleList=new ArrayList<Long[]>();
				Long id=channelList.get(i)[0];
				for(int j=0;j<len;j++){
					if(nodes[j][1]!=null){
						if(id.longValue()==nodes[j][1].longValue()){ //Long对象比较
							moudleList.add(nodes[j]);
						}
					}
				}
				//更新模块顺序
				for(int x=0;x<moudleList.size();x++){
					Map<String,Object> params=new HashMap<String,Object>();
					Long[] moudle=moudleList.get(x);
					params.put("moduleId", moudle[0]);
					params.put("channel", moudle[1]);
					params.put("sequence", x+1); //新排序代替全局排序
					try{
						this.moduleMapper.updateMoudleSeq(params);
						//this.tvOnlineService.resetModuleSeq(params);
					}catch(Exception e){
						throw new Exception("更新模块顺序时：",e);
					}
				}
			}
		}	


		//更新标记
		JSONArray treeJsonArray=JSONArray.parseArray(treeNodeArr);
		Object[] treeArr= treeJsonArray.toArray();
		Map<String,Object> treeParam=new HashMap<String,Object>();
		String moduleid = treeArr[1].toString();
		treeParam.put("channel",Long.valueOf(moduleid.substring(moduleid.indexOf("_")+1,moduleid.length())));
		treeParam.put("flag",1);

		JSONArray targetJsonArray=JSONArray.parseArray(targetNodeArr);
		Object[] targetArr= targetJsonArray.toArray();
		Map<String,Object> targetParam=new HashMap<String,Object>();
		String moduleid2 = targetArr[1].toString();

		targetParam.put("channel",Long.valueOf(moduleid2.substring(moduleid2.indexOf("_")+1,moduleid2.length())));
		targetParam.put("flag", 1);
		try{
			this.updatechannelflag(treeParam); //更新原始节点
			this.updatechannelflag(targetParam); //更新目标节点
		}catch(Exception e){
			throw new Exception("更新原始目标节点时：",e);
		}
		return 0;
	}

	/**
	 * 修改状态
	 */
	@Override
	public int updateflag(Map<String, Object> queryParams) {
		String moduleId = queryParams.get("moduleId")==null?"":queryParams.get("moduleId").toString();
		String channel =  queryParams.get("channel")==null?"":queryParams.get("channel").toString();
		if(moduleId == null || moduleId.isEmpty()){
			VodPhoneLinkAlbumList VodPhoneLinkAlbumList = VodPhoneLinkAlbumListMapper.selectOne(queryParams);
			queryParams.put("moduleId", VodPhoneLinkAlbumList.getModuleid());
		}
		if(channel == null || channel.isEmpty()){
			VodPhoneModule VodPhoneModule = moduleMapper.selectbymoduleid(queryParams);
			queryParams.put("channel", VodPhoneModule.getChannel());
		}
		moduleMapper.updateflag1(queryParams);
		moduleMapper.updateflag2(queryParams);
		return 0;
	}
	
	/**
	 * 修改状态
	 */
	@Override
	public int updatechannelflag(Map<String, Object> queryParams) {
		String channel =  queryParams.get("channel")==null?"":queryParams.get("channel").toString();
		if(channel == null || channel.isEmpty()){
			VodPhoneModule VodPhoneModule = moduleMapper.selectbymoduleid(queryParams);
			queryParams.put("channel", VodPhoneModule.getChannel());
		}
		moduleMapper.updateflag2(queryParams);
		return 0;
	}
	
	
	@Override
	public int updateflagforchannel(Map<String, Object> queryParams) {
		moduleMapper.updateflag3(queryParams);
		return 0;
	}

	/**
	 * 更新频道下模块  全部|部分  是否channelCode
	 * @return
	 * @param flag,[channelCode]
	 * @throws Exception
	 */
	public int updateChannelModuleFlag(Map<String, Object> params) throws Exception{	
		String channelCode=params.get("channelCode")==null?"":params.get("channelCode").toString();
		if(channelCode!=null && !"".equals(channelCode)){
			params.put("channel",  channelCode.substring(channelCode.indexOf("_")+1,channelCode.length())); //字段中既有channel又有channelCode
			//params.put("flag", 0); //同步了的
			List<VodPhoneModule> listVodPhoneModule= moduleMapper.select(params);
			//标记某个频道下模块
			if(listVodPhoneModule!=null && listVodPhoneModule.size()>0){
				for(VodPhoneModule VodPhoneModule:listVodPhoneModule){
					params.put("moduleId",VodPhoneModule.getModuleid());
					try{
						this.moduleMapper.updateflag1(params);
					}catch(Exception e){
						throw new Exception("同步某个频道下模块时：",e);
					}
				}
			}
			//标记某个频道下模块-专辑
			try{
				this.moduleMapper.updateflag2(params);
			}catch(Exception e){
				throw new Exception("同步某个频道下模块-专辑时：",e);
			}
		}else{//全部标记
			try{
				moduleMapper.updateflag1(params);
				moduleMapper.updateflag2(params);
			}catch(Exception e){
				throw new Exception("更新全部频道下模块时：",e);
			}
		}
		return 0;
	}



}
