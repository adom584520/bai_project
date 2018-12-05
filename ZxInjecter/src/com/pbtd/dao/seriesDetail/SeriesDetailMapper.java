package com.pbtd.dao.seriesDetail;

import java.util.List;

import com.pbtd.entity.SeriesDetail;

public interface SeriesDetailMapper {

	public int getInjectionNum();
	
	public List<SeriesDetail> getInjectionList();
	
	public int getInjectWithoutUrlNum();
	
	public List<SeriesDetail>  getTop100ReInjectList();
	
	public int getReInjectNum();
	
	/**
	 * 获取已经注入，还没有获取播放地址的数据
	 */
	public List<SeriesDetail> getTop1000InjectWithoutUrlList();
	
	/**
	 * 根据programId获取该条记录
	 */
	public SeriesDetail getInfoByProgramId(SeriesDetail seriesDetail);
	
	/**
	 * 根据Id获取一条记录
	 */
	public SeriesDetail getInfoById(SeriesDetail seriesDetail);
	
	
	public Integer getInfoNumBySeriesAndCodeAndVersionAndTerminal(SeriesDetail seriesDetail);
	
	/**
	 * 根据cpSeriesCode，movieCode更新数据 
	 * @param seriesDetail
	 */
	public void updateInfoBySeriesAndCodeAndVersion(SeriesDetail seriesDetail);
	
	/**
	 *  更改注入状态 
	 * @param seriesDetail
	 */
	public void updateStateById(SeriesDetail seriesDetail);
	
	/**
	 *  根据Id修改isInsert状态 
	 * @param seriesDetail id,isInsert
	 */
	public void updateIsInsertById(SeriesDetail seriesDetail);
	
	/**
	 * 获取Url地址后调用
	 * 注入成功后，可以获取到Url，则设置为0，否则设置为-1； 同时设置movieUr
	 */
	public void updateStateForUrl(SeriesDetail seriesDetail);
	
	/**
	 * 根据programId更新playUrl
	 */
	public void updateMovieUrlByProgramId(SeriesDetail seriesDetail);
	
	/**
	 * 更新cdn返回注入是否成功字段
	 */
	public void updateStateForCdnReturn(SeriesDetail seriesDetail);
	
	/**
	 * 保存媒资信息
	 */
	public void saveInfo(SeriesDetail seriesDetail);
}
