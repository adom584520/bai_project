package com.pbtd.manager.util;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Controller集合参数封装对象
 * 
 * @author JOJO
 *
 * @param <T>
 */
@Setter
@Getter
public class CollectionVO<T> {
	private List<Long> list = new ArrayList<>();
	private List<String> listStr = new ArrayList<>();
}
