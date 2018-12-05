package com.pbtd.playclick.youku.service.face;

import java.util.List;
import java.util.Map;

import com.pbtd.playclick.youku.domain.YouKualbum;
import com.pbtd.playclick.youku.domain.YouKualbumvideo;
import com.taobao.api.response.VmacMediamessageGetsResponse.MediaMessageOuterDto;
import com.taobao.api.response.VmacNormalShowGetResponse.ShowOuterDto;
import com.taobao.api.response.VmacShowpageGetResponse.Show;
public interface IYouKualbumService {


	//爬取媒资
	//int importyouku(List<ShowOuterDto> ShowOuterDto    );
	//int importyoukumediamessage(List<MediaMessageOuterDto> ShowOuterDtolist) ;
	Map<String,Object> findoffset();//获取最后一次爬取媒资偏移值
	int updateoffset(Map<String,Object> map);
	Long importyoukumediamessage(List<MediaMessageOuterDto> ShowOuterDtolist);//增量数据爬取



	//节目
	int count(Map<String, Object> queryParams);
	List<YouKualbum> page( Map<String, Object> queryParams);
	List<YouKualbum> find(Map<String, Object> queryParams);
	YouKualbum load(Map<String, Object> queryParams);
	int updatealbumnext(Map<String, Object> queryParams);

	//视频
	int countvideo(Map<String, Object> queryParams);
	List<YouKualbumvideo> pagevideo( Map<String, Object> queryParams);
	YouKualbumvideo loadvideo(Map<String, Object> queryParams);
	List<YouKualbumvideo> findvideo(Map<String, Object> queryParams);
	int importyouku(List<Show> showlist);
	List<Map<String, Object>> findvideoalbum(Map<String, Object> queryParams);
	int updatealbumvideonext(Map<String, Object> queryParams);



	//单独获取媒资
	Long showgetalbum(String show);

	int importyoukualbum(List<Show> showlist);


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
	int updatePlayPolicyMsgs(Map<String, Object> m);
	//批量更改 播控
	int udpatePlayPolicyMsgsstatus(List<Map<String,Object>> m);

	//添加优酷访问错误日志
	int insertyouklogger(Map<String,Object> m);


	//保存增量接口数据
	int save_mediamessage(MediaMessageOuterDto mediaMessageOuterDto);

	//定时任务是否启动中
	int update_quarzt(Map<String,Object> m);
	Map<String,Object> find_quarzt(Map<String,Object> m);
}
