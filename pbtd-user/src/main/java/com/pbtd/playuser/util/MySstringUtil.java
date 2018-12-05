package com.pbtd.playuser.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MySstringUtil {
	private MySstringUtil() {
	}

	public static List<Integer> stringToList(String data, String separator) {
		String[] split = data.split(separator);
		List<Integer> list = new ArrayList<Integer>();
		if (split.length > 0) {
			for (int i = 0; i < split.length; i++) {
				list.add(Integer.valueOf(split[i]));
			}
		}
		return Collections.EMPTY_LIST;
	}

	public static boolean validateNotEmpty(String str) {
		return str != null && str.length() > 0;
	}
}
