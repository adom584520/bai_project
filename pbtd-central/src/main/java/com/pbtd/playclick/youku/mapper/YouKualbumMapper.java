package com.pbtd.playclick.youku.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.pbtd.playclick.youku.domain.YouKualbum;
import com.pbtd.playclick.youku.domain.YouKualbumvideo;
import com.taobao.api.response.VmacMediamessageGetsResponse.MediaMessageOuterDto;
@Mapper
public interface YouKualbumMapper {
	//获取 媒资
	int insert(YouKualbum albums);
	int update(YouKualbum albums);
	int insertvideo(YouKualbumvideo albumvideo);
	int updatevideo(YouKualbumvideo albumvideo);
	Map<String,Object> findoffset();//获取最后一次爬取媒资偏移值
	int updateoffset(Map<String,Object> map);
	Map<String,Object>  findchannel(Map<String,Object> channel);
	List<Map<String,Object>>  findchannellist(Map<String,Object> channel);
	int insertchannel(Map<String,Object>  queryParams);
	Map<String,Object> findlabel(Map<String,Object>  queryParams);
	List<Map<String,Object>> findlabellist(Map<String,Object>  queryParams);
	int insertlabel(Map<String,Object>  queryParams);


	//节目
	int count(Map<String, Object> queryParams);
	List<YouKualbum> page( Map<String, Object> queryParams);
	List<YouKualbum> find(Map<String, Object> queryParams);
	YouKualbum load(Map<String, Object> queryParams);
	int updatealbumstatus(YouKualbum album);
	int updatealbumpaid(YouKualbum album);
	int updatealbumnext(Map<String, Object> queryParams);

	//视频
	int countvideo(Map<String, Object> queryParams);
	List<YouKualbumvideo> pagevideo( Map<String, Object> queryParams);
	YouKualbumvideo loadvideo(Map<String, Object> queryParams);
	List<YouKualbumvideo> findvideo(Map<String, Object> queryParams);
	int updatevideostatus(YouKualbumvideo albumvideo);
	int updatevideopaid(YouKualbumvideo albumvideo);
	List<Map<String, Object>> findvideoalbum(Map<String, Object> queryParams);
	int updatealbumvideonext(Map<String, Object> queryParams);
	//查询当节目最大正片剧集
	int  getmaxvideoid(Map<String, Object> queryParams);
	//更新当前剧集
	int updatecount(Map<String, Object> queryParams);
	//根据showid更新剧集
	int updategetcount(Map<String, Object> queryParams);


	//删除优酷多余剧集
	int deleteyoukviedeo();


	//获取播控的offset
	Map<String,Object> findPlayPolicyMsgsoffset();
	int  updatePlayPolicyMsgsoffset(Map<String, Object> queryParams);
	//查询播控数据
	int countPlayPolicyMsgs(Map<String, Object> queryParams);
	List<Map<String,Object>> pagePlayPolicyMsgs( Map<String, Object> queryParams);
	//添加播控数据
	int insertPlayPolicyMsgs(Map<String,Object> m);
	//删除播控无效
	int  deletePlayPolicyMsgs3(Map<String, Object> queryParams);
	//更改播控zt
	int udpatePlayPolicyMsgs(Map<String,Object> m);
	//批量更改 播控
	int udpatePlayPolicyMsgsstatus(List<Map<String,Object>> m);

	//添加优酷访问错误日志
	int insertyouklogger(Map<String,Object> m);



	//保存增量接口数据 查询  更新 删除
	int save_mediamessage(MediaMessageOuterDto mediaMessageOuterDto);
	List<Map<String,Object>> find_mediamessagevideo(Map<String,Object> m);
	List<Map<String,Object>>  find_mediamessageshow(Map<String,Object> m);
	int update_mediamessageshow(Map<String,Object> m);
	int update_mediamessagevideo(Map<String,Object> m);
	int delete_mediamessage(Map<String,Object> m);

	//定时任务是否启动中
	int update_quarzt(Map<String,Object> m);
	Map<String,Object> find_quarzt(Map<String,Object> m);

}

