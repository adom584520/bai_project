package com.pbtd.manager.util;

import java.util.List;

public class MyString {
	private MyString() {
	}

	public static void stringToList(String str, List<String> list) {
		String[] split = str.split(",");
		if(split.length>0){
			for (int i = 0; i < split.length; i++) {
				list.add(split[i]);
			}
		}
	}
}
