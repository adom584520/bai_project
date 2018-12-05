package com.pbtd.playclick.integrate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbtd.playclick.base.common.easyui.ComboBoxOptionModel;
import com.pbtd.playclick.integrate.domain.VodChannel;
import com.pbtd.playclick.integrate.mapper.VodphoneChannelMapper;
import com.pbtd.playclick.integrate.service.face.IVodphoneChannelService;

/**
 * vod_channel( ) 服务实现
 *
 * @author admin
 */
@Service
public class VodphoneChannelService implements IVodphoneChannelService {
   
    @Autowired
    private VodphoneChannelMapper ChannelMapper;
    
    @Override
    public int count(Map<String, Object> queryParams) {
        return this.ChannelMapper.count(queryParams);
    }

    @Override
    public List<VodChannel> page(int start, int limit, Map<String, Object> queryParams) {
        return this.ChannelMapper.page( queryParams);
    }

    @Override
    public List<VodChannel> find(Map<String, Object> queryParams) {
        return this.ChannelMapper.find(queryParams);
    }

    @Override
    public int generatePosition(Map<String, Object> queryParams) {
        return this.ChannelMapper.generatePosition(queryParams);
    }

    @Override
    public VodChannel load(int id) {
        return this.ChannelMapper.load(id);
    }

    @Override
    @Transactional
    public int insert(VodChannel vodChannel) {
        this.ChannelMapper.insert(vodChannel);
        return vodChannel.getId();
    }

    @Override
    @Transactional
    public int update(VodChannel vodChannel) {
        return this.ChannelMapper.update(vodChannel);
    }

	@Override
    @Transactional
    public int deletes(Map<String, Object> ids) {
        return this.ChannelMapper.deletes(ids);
    }

	@Override
	public List<ComboBoxOptionModel> choosechannel(Map<String, Object> queryParams) {
		return this.ChannelMapper.choosechannel(queryParams);
	}

	@Override
	public int save(Map<String, Object> params) {
		List<Map<String,Object>> rows = (List<Map<String,Object>>)params.get("rows");
		int id;
		int sequence;
		VodChannel vodChannel=new VodChannel();
		for(Map<String,Object> map :rows){
			id = Integer.parseInt(map.get("id").toString());
			sequence =map.get("sequence")==null?-1:Integer.parseInt(map.get("sequence").toString());
	    	 vodChannel.setLevels(-1);
	    	 vodChannel.setPackagecount(-1);
	    	 vodChannel.setBj(-1);
			 vodChannel.setId(id);
			 vodChannel.setSequence(sequence);
			   this.ChannelMapper.update(vodChannel);
		}
		return 1;
	}

	@Override
	public int generatePositiontype(Map<String, Object> map) {
		return ChannelMapper.generatePositiontype(map);
	}

    
}
