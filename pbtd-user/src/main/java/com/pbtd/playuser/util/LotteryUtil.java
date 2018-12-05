package com.pbtd.playuser.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.pbtd.playuser.domain.ActivityPrizesInfo;

/**
 * 中奖概率工具类
 * 
 * @author JOJO
 *
 */
public class LotteryUtil {
	private LotteryUtil() {
	}

	/**
	 * 最大概率
	 */
	public static int maxProbability = 100000;
	/**
	 * 未中奖Prizecode
	 */
	public static final int NOT_WINNERS_CODE = 0;
	/**
	 * 未中奖信息1
	 */
	public static final String THANK_YOU_PATRONIZE_ONE = "哎呦！就差一点点！";
	/**
	 * 未中奖信息2
	 */
	public static final String THANK_YOU_PATRONIZE_TWO = "就差一点点，继续加油！";

	/**
	 * 根据奖品列表随机中奖奖品
	 * 
	 * @param aps
	 * @return
	 */
	public static ActivityPrizesInfo lottery(List<ActivityPrizesInfo> aps) {
		// 总的概率区间
		float totalPro = 0f;
		// 存储每个奖品新的概率区间
		List<Float> proSection = new ArrayList<Float>();
		proSection.add(0f);
		// 设置奖品概率区间
		Iterator<ActivityPrizesInfo> iterator = aps.iterator();
		while (iterator.hasNext()) {
			ActivityPrizesInfo next = iterator.next();
			if (next.getPrizeresnum() == 0 || next.getProbability() == 0) {
				iterator.remove();
			}
		}
		for (ActivityPrizesInfo ap : aps) {
			totalPro += ap.getProbability();
			proSection.add(totalPro);
		}
		// 获取总的概率区间中的随机数
		Random random = new Random();
		float randomPro = (float) random.nextInt(maxProbability);
		for (int i = 0; i < proSection.size() - 1; i++) {
			if (randomPro >= proSection.get(i) && randomPro < proSection.get(i + 1)) {
				return aps.get(i);
			}
		}
		ActivityPrizesInfo ap = new ActivityPrizesInfo();
		ap.setPrizecode(NOT_WINNERS_CODE);
		ap.setLanguage((int) (Math.random() * 10) % 2 == 0 ? THANK_YOU_PATRONIZE_ONE : THANK_YOU_PATRONIZE_TWO);
		return ap;
	}
	
	public static void main(String[] args) {
		try {
			String encode = URLEncoder.encode("测试", "UTF-8");
			String encode2 = URLEncoder.encode("ADSFS", "UTF-8");
			System.out.println(encode);
			System.out.println(encode2);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
