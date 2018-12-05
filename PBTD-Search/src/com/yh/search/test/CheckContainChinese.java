package com.yh.search.test;

import static java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS;
import static java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS;
import static java.lang.Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A;
import static java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B;

import org.apache.commons.lang3.StringUtils;  
  
public class CheckContainChinese {  
  
    public static void main(String[] args) {  
        String containChinese = "test,我有中文";  
        String containNoChiese = "test, i don't contain chinese100";  
        System.out.println("containChinese 是否包含中文 :" + checkStringContainChinese(containChinese));  
        System.out.println("containNoChiese 是否包含中文 :" + checkStringContainChinese(containNoChiese));  
    }  
  
    private static boolean checkStringContainChinese(String checkStr){  
        if(StringUtils.isNotBlank(checkStr)){  
            char[] checkChars = checkStr.toCharArray();  
            for(int i = 0; i < checkChars.length; i++){  
                char checkChar = checkChars[i];  
                if(checkCharContainChinese(checkChar)){  
                    return true;  
                }  
            }  
        }  
        return false;  
    }  
  
    private static boolean checkCharContainChinese(char checkChar){  
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(checkChar);  
        if(CJK_UNIFIED_IDEOGRAPHS == ub || CJK_COMPATIBILITY_IDEOGRAPHS == ub || CJK_COMPATIBILITY_FORMS == ub ||  
                CJK_RADICALS_SUPPLEMENT == ub || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A == ub || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B == ub){  
            return true;  
        }  
        return false;  
    }  
}  
