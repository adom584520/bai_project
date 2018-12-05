package com.pbtd.playclick.yinhe.service;

import com.pbtd.playclick.yinhe.domain.AlbumsWithBLOBs;
import com.pbtd.playclick.yinhe.domain.VodcpxxWithBLOBs;

/**
 * 获取银河数据service
 * @author Administrator
 *
 */

public interface QuartzService {

	public void httpRequest( VodcpxxWithBLOBs vodcpxx,AlbumsWithBLOBs albums,String albumId);
	
	public void truncate();
	
	public void updateDramacode();
	
	public void updateSequence();
	
	public int delDrama();
}
