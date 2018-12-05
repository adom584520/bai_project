package com.pbtd.manager.vod.tv.album.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.manager.vod.tv.album.domain.VodTvAlbuminfo;
import com.pbtd.manager.vod.tv.album.domain.VodTvAlbuminfoReturn;
import com.pbtd.manager.vod.tv.album.mapper.VodTvAlbuminfoMapper;
import com.pbtd.manager.vod.tv.album.service.face.IVodTvAlbuminfoService;

@Service
public class VodTvAlbuminfoService implements IVodTvAlbuminfoService {

	@Autowired
	private VodTvAlbuminfoMapper vodTvAlbuminfoMapper;

	@Override
	public int count(Map<String, Object> queryParams) {

		return vodTvAlbuminfoMapper.count(queryParams);
	}

	@Override
	public List<VodTvAlbuminfo> page(Map<String, Object> queryParams) {

		return vodTvAlbuminfoMapper.page(queryParams);
	}

	@Override
	public List<VodTvAlbuminfo> find(Map<String, Object> queryParams) {

		return vodTvAlbuminfoMapper.find(queryParams);
	}

	@Override
	public VodTvAlbuminfo load(int id) {

		return vodTvAlbuminfoMapper.load(id);
	}

	@Override
	public int insert(VodTvAlbuminfo VodTvAlbuminfo) {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("channel", VodTvAlbuminfo.getChannel());
		queryParams.put("seriesCode", VodTvAlbuminfo.getSeriesCode());
		// 获取排序序号
		int newsequence = VodTvAlbuminfo.getSequence() == 0 ? 150 : VodTvAlbuminfo.getSequence();
		queryParams.put("maxnum", newsequence);
		vodTvAlbuminfoMapper.addsequce(queryParams);
		return vodTvAlbuminfoMapper.insert(VodTvAlbuminfo);
	}

	@Override
	public int update(VodTvAlbuminfo VodTvAlbuminfo) {
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("channel", VodTvAlbuminfo.getChannel());
		queryParams.put("seriesCode", VodTvAlbuminfo.getSeriesCode());
		if (VodTvAlbuminfo.getChannel() != null && VodTvAlbuminfo.getSeriesCode() != null) {
			// 获取原始排序序号
			VodTvAlbuminfo map1 = vodTvAlbuminfoMapper.load(VodTvAlbuminfo.getId());
			if (map1 != null) {
				if (VodTvAlbuminfo.getSequence() != 0) {
					int currsequence = map1.getSequence() == null ? 1 : map1.getSequence();
					int newsequence = VodTvAlbuminfo.getSequence();
					// 比对序号是变大还是变小
					if (currsequence > newsequence) {
						queryParams.put("plus", "1");
						queryParams.put("minnum", newsequence);
						queryParams.put("maxnum", currsequence);
					} else {
						queryParams.put("minnum", currsequence);
						queryParams.put("maxnum", newsequence);
					}
					vodTvAlbuminfoMapper.updatesequce(queryParams);
				}
			}
		}
		return vodTvAlbuminfoMapper.update(VodTvAlbuminfo);
	}

	@Override
	public int deletes(Map<String, Object> ids) {

		return vodTvAlbuminfoMapper.deletes(ids);
	}

	@Override
	public int updatesequce(Map<String, Object> queryParams) {

		return vodTvAlbuminfoMapper.updatesequce(queryParams);
	}

	@Override
	public int addsequce(Map<String, Object> queryParams) {

		return vodTvAlbuminfoMapper.addsequce(queryParams);
	}

	@Override
	public List<Map<String, Object>> findAlbumsinfovideo(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.findAlbumsinfovideo(queryParams);
	}

	@Override
	public int countAlbumsinfovideo(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.countAlbumsinfovideo(queryParams);
	}

	@Override
	public int insertvideo(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.insertvideo(queryParams);
	}

	@Override
	public int deletesvideo(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.deletesvideo(queryParams);
	}

	@Override
	public List<Map<String, Object>> loadVideo(int id) {
		return vodTvAlbuminfoMapper.loadVideo(id);
	}

	@Override
	public int updatevideo(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.updatevideo(queryParams);
	}

	@Override
	public List<Map<String, Object>> pagealbum(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.pagealbum(queryParams);
	}

	@Override
	public int updatealbumsequence(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.updatealbumsequence(queryParams);
	}

	@Override
	public int countalbum(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.countalbum(queryParams);
	}

	@Override
	public int addalbum(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.addalbum(queryParams);
	}

	@Override
	public int deletealbum(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.deletealbum(queryParams);
	}

	/**
	 * 专辑绑定角标
	 */
	@Override
	public int updatecorner(String albumid, String cornerid) {
		String[] seriesCodes = albumid.split(",");
		if (seriesCodes != null) {
			for (String seriesCode : seriesCodes) {
				vodTvAlbuminfoMapper.updatecorner(Integer.parseInt(seriesCode), cornerid);
			}
		}
		return 0;
	}

	/**
	 * 专辑绑定付费包
	 */
	@Override
	public int updatecollectfeesbag(String albumid, String collectid) {
		String[] seriesCodes = albumid.split(",");
		if (seriesCodes != null) {
			for (String seriesCode : seriesCodes) {
				vodTvAlbuminfoMapper.updatecollectfeesbag(Integer.parseInt(seriesCode), collectid);
			}
		}
		return 0;
	}

	@Override
	public int updatestatus(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.updatestatus(queryParams);
	}

	@Override
	public List<Map<String, Object>> recommandalbum(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.recommandalbum(queryParams);
	}

	@Override
	public List<Map<String, Object>> pageAlbumsinfovideo(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.pageAlbumsinfovideo(queryParams);
	}

	@Override
	public List<Map<String, Object>> findIdByChannel(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.findIdByChannel(queryParams);
	}

	@Override
	public VodTvAlbuminfoReturn querySeriesCodeAndName(String seriesCode) {
		return vodTvAlbuminfoMapper.querySeriesCodeAndName(seriesCode);
	}

	@Override
	public List<Map<String, Object>> findalbumsequence(Map<String, Object> map) {
		return vodTvAlbuminfoMapper.findalbumsequence(map);
	}

	@Override
	public Map<String, Object> findalbummaxVSminsequence(Map<String, Object> map) {
		return vodTvAlbuminfoMapper.findalbummaxVSminsequence(map);
	}

	@Override
	public List<Map<String, Object>> findalbumsequencesum(Map<String, Object> queryParams) {
		return vodTvAlbuminfoMapper.findalbumsequencesum(queryParams);
	}

}
