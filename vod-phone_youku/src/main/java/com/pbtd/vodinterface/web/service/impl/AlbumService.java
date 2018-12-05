package com.pbtd.vodinterface.web.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.vodinterface.web.mapper.AlbumMapper;
import com.pbtd.vodinterface.web.service.face.IAlbumService;

@Service
public class AlbumService implements IAlbumService {
	@Autowired
	private AlbumMapper albummapper;

	@Override
	public List<Map<String, Object>> pgetAlbum(Map<String, Object> map) {
		return albummapper.pgetAlbum(map);
	}

	@Override
	public List<Map<String, Object>> pgetchannel2Album(Map<String, Object> map) {
		return albummapper.pgetchannel2Album(map);
	}

	@Override
	public List<Map<String, Object>> pgetchannelAlbum(Map<String, Object> map) {
		return albummapper.pgetchannelAlbum(map);
	}

	@Override
	public Map<String, Object> pgetAlbuminfo(Map<String, Object> map) {
		return albummapper.pgetAlbuminfo(map);
	}

	@Override
	public List<Map<String, Object>> pgetAlbuminfovideo(Map<String, Object> map) {
		return albummapper.pgetAlbuminfovideo(map);
	}

	@Override
	public List<Map<String, Object>> pgetAlbuminforecommend(Map<String, Object> map) {
		return albummapper.pgetAlbuminforecommend(map);
	}

	@Override
	public List<Map<String, Object>> pgetAlbumhotsearch(Map<String, Object> map) {
		return albummapper.pgetAlbumhotsearch(map);
	}

	@Override
	public List<Map<String, Object>> findchannelhot(Map<String, Object> map) {
		return albummapper.findchannelhot(map);
	}

	@Override
	public List<Map<String, Object>> findhotseriescode(Map<String, Object> map) {
		return albummapper.findhotseriescode(map);
	}

	@Override
	public Map<String, Object> findpalbumforuser(Map<String, Object> map) {
		return albummapper.findpalbumforuser(map);
	}

	@Override
	public List<Map<String, Object>> findalbum(String seriesCode, List<Map<String, Object>> list) {
		Map<String, Object> map;
		String actorName = "";
		String writerName = "";
		String ChannelName = "";
		try {
			map = albummapper.album(seriesCode);
			actorName = map.get("actorName").toString();
			writerName = map.get("writerName").toString();
			ChannelName = (String) map.get("ChannelName");
		} catch (Exception e) {
			return list;
		}
		String[] splitActor = null;
		String[] splitWriter = null;
		if (actorName != null && !"".equals(actorName)) {
			splitActor = actorName.split("\\||,");
		}
		if (writerName != null && !"".equals(writerName)) {
			splitWriter = writerName.split("\\||,");
		}
		Map<String, Object> queryParams = new HashMap<String, Object>();
		list.add(map);
		int count = list.size();// 取10条
		// 判断演员是否为空
		if (splitActor == null) {
			queryParams.put("actorName1", null);
		} else {// 演员不为空
			if (splitActor.length == 1) {
				queryParams.put("actorName1", splitActor[0]);
			} else if (splitActor.length == 2) {
				queryParams.put("actorName1", splitActor[0]);
				queryParams.put("actorName2", splitActor[1]);
			} else if (splitActor.length >= 3) {
				queryParams.put("actorName1", splitActor[0]);
				queryParams.put("actorName2", splitActor[1]);
				queryParams.put("actorName3", splitActor[2]);
			}
		}

		// 查询演员
		List<Map<String, Object>> one = albummapper.findalbumOne(queryParams);
		// 添加专辑
		for (int i = 0; i < one.size() && count <= 10; i++) {
			int listNum = 0;
			for (int j = 0; j < list.size(); j++) {
				if (one.get(i).get("seriesCode").toString().equals(list.get(j).get("seriesCode").toString())) {
					listNum++;
				}
			}
			if (listNum == 0) {
				list.add(one.get(i));
				count++;
			}
		}
		if (count >= 10) {
			Iterator<Map<String, Object>> iteratorOne = list.iterator();
			while (iteratorOne.hasNext()) {
				if (seriesCode.equals(iteratorOne.next().get("seriesCode").toString())) {
					iteratorOne.remove();
				}
			}
			return list.subList(0, 9);
		}

		// 判断导演是否为空
		if (splitWriter != null) {
			queryParams.put("writerName", splitWriter[0]);
		} else {
			queryParams.put("writerName", null);
		}
		// 查询导演
		List<Map<String, Object>> two = albummapper.findalbumTwo(queryParams);
		// 添加专辑
		for (int i = 0; i < two.size() && count <= 10; i++) {
			int listNum = 0;
			for (int j = 0; j < list.size(); j++) {
				if (two.get(i).get("seriesCode").toString().equals(list.get(j).get("seriesCode").toString())) {
					listNum++;
				}
			}
			if (listNum == 0) {
				list.add(two.get(i));
				count++;
			}

		}
		if (count >= 10) {
			Iterator<Map<String, Object>> iteratorTwo = list.iterator();
			while (iteratorTwo.hasNext()) {
				if (seriesCode.equals(iteratorTwo.next().get("seriesCode").toString())) {
					iteratorTwo.remove();
				}
			}
			return list.subList(0, 9);
		}

		// 查频道
		List<Map<String, Object>> three = albummapper.findalbumThree(ChannelName);
		// 添加专辑
		for (int i = 0; i < three.size() && count <= 10; i++) {
			int listNum = 0;
			for (int j = 0; j < list.size(); j++) {
				if (three.get(i).get("seriesCode").toString().equals(list.get(j).get("seriesCode").toString())) {
					listNum++;
				}
			}
			if (listNum == 0) {
				list.add(three.get(i));
				count++;
			}

		}
		if (count >= 10) {
			Iterator<Map<String, Object>> iteratorThree = list.iterator();
			while (iteratorThree.hasNext()) {
				if (seriesCode.equals(iteratorThree.next().get("seriesCode").toString())) {
					iteratorThree.remove();
				}
			}
			return list.subList(0, 9);
		}
		return list;
	}

	@Override
	public int findAlbuminfovideocount(Map<String, Object> map) {
		return albummapper.findAlbuminfovideocount(map);
	}

	@Override
	public List<Map<String, Object>> findcount(Map<String, Object> map) {
		return albummapper.findcount(map);
	}

	@Override
	public int findchannel2Albumcount(Map<String, Object> map) {
		return albummapper.findchannel2Albumcount(map);
	}

	@Override
	public int findhotseriescount(Map<String, Object> map) {
		return albummapper.findhotseriescount(map);
	}

	@Override
	public int findchannelAlbumcount(Map<String, Object> map) {
		return albummapper.findchannelAlbumcount(map);
	}

	@Override
	public List<Map<String, Object>> pfindmovieurl(Map<String, Object> map) {
		return albummapper.pfindmovieurl(map);
	}

	@Override
	public List<Map<String, Object>> findsearchalbum(Map<String, Object> map) {
		return albummapper.findsearchalbum(map);
	}

	@Override
	public int findsearchalbumcount(Map<String, Object> map) {
		return albummapper.findsearchalbumcount(map);
	}
}
