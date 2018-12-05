package com.pbtd.manager.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping("/SequenceUtil")
public class SequenceUtil {


	//获取数组最大值
	public static int getMax(String[] arr)  
	{  
		int max = Integer.parseInt(arr[0]);  
		for(int i=0;i<arr.length;i++)  
		{  
			if(Integer.parseInt(arr[i])>max)  
				max = Integer.parseInt(arr[i]);  
		}  
		return max;  
	}  
	//获取数组最小值
	public static int getMin(String[] arr)  
	{  
		int min = Integer.parseInt(arr[0]);  
		for(int i=0;i<arr.length;i++)  
		{  
			if(Integer.parseInt(arr[i])<min)  
				min = Integer.parseInt(arr[i]);  
		}  
		return min;  
	}  
	public String[] Sortball(String sequences ){   //将数组中的数从小到大排序
		String[] array2 =sequences.split(",");
		for(int i = 0 ; i < array2.length ; i ++) {
			for(int j = i +1 ; j < array2.length ; j ++) {
				if(Integer.parseInt(array2[i]) > Integer.parseInt(array2[j])) {
					String temp = array2[i];
					array2[i] = array2[j];
					array2[j] = temp;
				}
			}
		}
		return array2;
	}
}
