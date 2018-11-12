package com.skynet.rimp.common.utils;

public class CommonUtil {
	
	/**
	 * 拼音码字符
	 */
	private final static String CHAR_STR = "abcdefghijklmnopqrstuvwxyz";
	
	
	/**
	 * 判断用户输入的是名称还是拼音码
	 * @param charStr
	 * @return
	 */
	public static boolean containStr(char[] charStr){
		for (int i = 0; i < charStr.length; i++) {
			String m_temp = charStr[i]+"";
			if(!CHAR_STR.contains(m_temp)){
				return false;
			}
		}
		return true;
	}
}
