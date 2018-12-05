package com.pbtd.playclick.base.common.util;

import java.util.UUID;

public class UUIDUtil {
	public static String getGuid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	public static void main(String[] args) {
		System.out.println(getGuid());
	}
}
