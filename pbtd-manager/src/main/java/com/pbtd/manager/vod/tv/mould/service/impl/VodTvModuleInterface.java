package com.pbtd.manager.vod.tv.mould.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.pbtd.manager.system.service.IRealmNameService;
import com.pbtd.manager.vod.common.mould.domain.vodMasterplateSon;
import com.pbtd.manager.vod.common.mould.mapper.vodMasterplateSonMapper;
import com.pbtd.manager.vod.tv.common.domain.VodTvchannel;
import com.pbtd.manager.vod.tv.common.mapper.VodTvChannelMapper;
import com.pbtd.manager.vod.tv.mould.domain.VodTvLinkAlbumList;
import com.pbtd.manager.vod.tv.mould.domain.VodTvModule;
import com.pbtd.manager.vod.tv.mould.mapper.VodTvLinkAlbumListMapper;
import com.pbtd.manager.vod.tv.mould.mapper.VodTvModuleMapper;
import com.pbtd.manager.vod.tv.mould.service.face.IvodTvModuleInterface;

@Service
public class VodTvModuleInterface implements IvodTvModuleInterface {

	@Autowired
	private VodTvModuleMapper moduleMapper;
	@Autowired
	private vodMasterplateSonMapper vodMasterplateSonMapper;
	@Autowired
	private VodTvLinkAlbumListMapper VodTvLinkAlbumListMapper;
	@Autowired
	public   IRealmNameService realmnameService;
	@Autowired
	private VodTvChannelMapper channelMapper;
	/*@Autowired
	private ITvOnlineService tvOnlineService;*/


	@Override
	public int delete(Integer id) {
		return moduleMapper.delete(id);
	}
	@Override
	public int deletemodule(Map<String, Object> queryParams) {
		return moduleMapper.deletemodule(queryParams);
	}

	@Override
	public int insert(VodTvModule m) {
		Map<String, Object>  map = new HashMap<>();
		map.put("moduleId", m.getModuleid());
		VodTvModule vodTvModule = moduleMapper.load(map);
		if(vodTvModule != null ){
			System.out.println( "修改");
			int aa = moduleMapper.deletemodule(map);
			System.out.println(aa);
		}
		return moduleMapper.insert(m);
	}

	@Override
	public VodTvModule load(Map<String, Object> queryParams) {
		return moduleMapper.load(queryParams);
	}

	@Override
	public int update(VodTvModule m) {
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
	public List<VodTvModule> page(Map<String, Object> queryParams) {
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
		Map<String,Object> maprealm=realmnameService.findtitle(null);

		List<Map<String, Object>> outputlist = new ArrayList<>();
		//第一步  查询当前频道下的模块
		List<VodTvModule> list = moduleMapper.select(queryParams);
		for (VodTvModule VodTvModule : list) {
			//获取当前模版相关的数据
			//vodMasterplateMapper.selectByPrimaryKey(VodTvModule.getMasterplateid());
			//第二步 查询当前模块下的运营位vodMasterplateSon
			List<vodMasterplateSon> sonlist = vodMasterplateSonMapper.selectByPrimaryKey(VodTvModule.getMasterplateid());
			Map<String, Object> modulemap = new HashMap<>();
			modulemap.put("moduleName", VodTvModule.getName());
			modulemap.put("moduleSequence", VodTvModule.getSequence());
			modulemap.put("moduleId", VodTvModule.getMasterplateid());
			modulemap.put("mId", VodTvModule.getModuleid());
			List<Object> yunyinweilist = new ArrayList<>();
			for (vodMasterplateSon vodMasterplateSon : sonlist) {
				Map<String, Object> yunyuneweimap = new HashMap<>();
				HashMap<String, Object> param = new HashMap<>();
				param.put("moduleId", VodTvModule.getModuleid());
				param.put("masterplateNum", vodMasterplateSon.getMasterplatenum());
				param.put("size", vodMasterplateSon.getCount());
				param.put("imgtitle", maprealm.get("imgtitle"));
				//第三步 根据模块id 运营位的id 查询 绑定专辑集合
				List<Map<String,Object>> VodTvModuleAlbumlist = VodTvLinkAlbumListMapper.select(param);
				for (Map<String, Object> map : VodTvModuleAlbumlist) {
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
				if(VodTvModuleAlbumlist == null || VodTvModuleAlbumlist.isEmpty()){
					Map  aa = new HashMap<>();
					VodTvModuleAlbumlist.add(aa);
				}
				yunyuneweimap.put("itemdate",VodTvModuleAlbumlist);
				yunyinweilist.add(yunyuneweimap);
			}
			modulemap.put("data", yunyinweilist);
			outputlist.add(modulemap);
		}
		return outputlist;
	}

	@Override
	public List<Map<String, Object>> getModuleAlbumforOne(Map<String, Object> queryParams) {
		//		//已有模块id；查询出 模版id，
		//		// 第一步查询各个运营位的list
		//
		//		List<Map<String,Object>> list =  moduleMapper.selectForOne(queryParams);
		//		//第三步 根据模块id 运营位的id 查询 绑定专辑集合
		//		List<Map<String, Object>> yunyinweilist = new ArrayList<>();
		//
		//		for (Map<String, Object> map : list) {
		//			Map<String, Object> yunyuneweimap = new HashMap<>();
		//
		//			//第三步 根据模块id 运营位的id 查询 绑定专辑集合
		//			List<Map<String,Object>> VodTvModuleAlbumlist = vodTvModuleAlbumMapper.select(map);
		//			yunyuneweimap.put("data"+map.get("id"),VodTvModuleAlbumlist);
		//			yunyinweilist.add(yunyuneweimap);
		//		}
		return null;
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
				nodes[i][0]=Long.valueOf(objs[0].toString());//id
				nodes[i][1]=(objs[1]==null?null:Long.valueOf(objs[1].toString()));//pid
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
				VodTvchannel vodTvChannel=new VodTvchannel();
				vodTvChannel.setChannelCode(Integer.valueOf(channel[0].toString()));
				vodTvChannel.setSequence(i+1);
				try{
					channelMapper.updateSeqByChannelCode(vodTvChannel);
				//	this.tvOnlineService.resetChannelSeq(vodTvChannel);
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
		treeParam.put("moduleId",Long.valueOf(treeArr[1].toString()));
		treeParam.put("flag",1);
		
		JSONArray targetJsonArray=JSONArray.parseArray(targetNodeArr);
		Object[] targetArr= targetJsonArray.toArray();
		Map<String,Object> targetParam=new HashMap<String,Object>();
		targetParam.put("moduleId",Long.valueOf(treeArr[1].toString()));
		targetParam.put("flag", 1);
		try{
			this.updateflag(treeParam); //更新原始节点
			this.updateflag(targetParam); //更新目标节点
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
			VodTvLinkAlbumList vodTvLinkAlbumList = VodTvLinkAlbumListMapper.selectOne(queryParams);
			queryParams.put("moduleId", vodTvLinkAlbumList.getModuleid());
		}
		if(channel == null || channel.isEmpty()){
			VodTvModule vodTvModule = moduleMapper.selectbymoduleid(queryParams);
			queryParams.put("channel", vodTvModule.getChannel());
		}
		moduleMapper.updateflag1(queryParams);
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
			params.put("channel", channelCode); //字段中既有channel又有channelCode
			//params.put("flag", 0); //同步了的
			List<VodTvModule> listVodTvModule= moduleMapper.select(params);
			//标记某个频道下模块
			if(listVodTvModule!=null && listVodTvModule.size()>0){
				for(VodTvModule vodTvModule:listVodTvModule){
					params.put("moduleId",vodTvModule.getModuleid());
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
