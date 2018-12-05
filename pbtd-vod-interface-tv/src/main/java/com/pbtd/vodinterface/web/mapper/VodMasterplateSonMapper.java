package com.pbtd.vodinterface.web.mapper;

import java.util.List;

import com.pbtd.vodinterface.web.domain.VodMasterplateSon;

public interface VodMasterplateSonMapper {

    List<VodMasterplateSon>  selectByPrimaryKey(Integer id);

}