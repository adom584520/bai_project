package com.pbtd.playclick.pinyin.service.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbtd.playclick.pinyin.domain.Ypinyin;
import com.pbtd.playclick.pinyin.domain.Zpinyin;
import com.pbtd.playclick.pinyin.mapper.PinYinMapper;
import com.pbtd.playclick.pinyin.service.PinYinService;
import com.pbtd.playclick.util.PinYinUtil;

@Service
public class PinYinServiceImpl implements PinYinService {

	@Autowired
	private PinYinMapper minYinMapper;

	@Override
	public void getPinYin() {
		// 演员列表查询
		getYpinyin(minYinMapper.selectYpinyin());
		// 手机专辑列表查询
		getZpinyin(minYinMapper.selectZpinyin());

	}

	private void getYpinyin(List<Ypinyin> ypinyin) {
		for (Ypinyin ypin : ypinyin) {
			if (ypin.getPinyin() == null || "".equals(ypin.getPinyin())) {
				String name = ypin.getName();
				// 清除掉所有特殊字符
				String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(name);
				name = m.replaceAll("").trim();
				String pingYin = PinYinUtil.getPingYin(name).toLowerCase();
				String headChar = PinYinUtil.getPinYinHeadChar(name).toLowerCase();
				minYinMapper.updateYpinyin(pingYin, headChar, ypin.getCode());
			}
		}

	}

	private void getZpinyin(List<Zpinyin> zpinyin) {
		for (Zpinyin zpin : zpinyin) {
			if (zpin.getPinyin() == null || "".equals(zpin.getPinyin())) {
				String name = zpin.getSeriesName();
				// 清除掉所有特殊字符
				String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！\"\"@#￥%……&*（）——+|\\-{} 《》【】‘；：”“’。，、？·]";
				Pattern p = Pattern.compile(regEx);
				Matcher m = p.matcher(name);
				name = m.replaceAll("").trim();
				String pingYin = PinYinUtil.getPingYin(name).toLowerCase();
				String headChar = PinYinUtil.getPinYinHeadChar(name).toLowerCase();
				minYinMapper.updateZpinyin(pingYin, headChar, zpin.getSeriesCode());
			}
		}

	}

}
