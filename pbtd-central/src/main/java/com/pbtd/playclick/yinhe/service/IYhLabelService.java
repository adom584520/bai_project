package com.pbtd.playclick.yinhe.service;

import com.pbtd.playclick.yinhe.domain.YhLabel;

public interface IYhLabelService {
	int insert(YhLabel label);
	YhLabel select(String cnhId);
    int update(YhLabel label);
    int deletes(int ids);
    void addLabel(String label,String cnhId);
}
