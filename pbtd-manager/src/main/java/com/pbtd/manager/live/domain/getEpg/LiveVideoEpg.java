package com.pbtd.manager.live.domain.getEpg;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor @NoArgsConstructor
public class LiveVideoEpg  implements Serializable{
	private static final long serialVersionUID = 1L;
	public int id;   //供应商EPG的ID
	public String cpEpgId;   //供应商EPG的ID
    public String packageId;   //暂时不用
    public String packageCode;  //节目包代码
    public String packageName;  //节目包名称
    public String title;    //标题(显示字段)
    public long startTime;  //开始时间
    public long endTime;   //结束时间
    public String tag;   //标签
    public String chnCode;  //频道代码
    public String chnName;   //频道名称
    public int isModified;   //是否修改过数据(1:已修改 0:未修改)
    public int playOrder;    //播放顺序
    public String playUrl;   //播放地址(暂时不用)
    public String playUrl2;   //播放地址(暂时不用)
    public String backPlayUrl;   //回看播放地址
    public String epgPoster; //海报直播截图
    public String backPoster;  //海报时移图片序列
    public String epgStatus;  //状态 1:有效 0:无效
    public int programNum;  //数据选台 台标号 （0、1、2、3）
    public String packageCover;//
    public int isSave;//
    public long createTime;//
    public long updateTime;//
	private String  chnImage;
    
}
