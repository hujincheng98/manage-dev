package com.skynet.rimp.common.utils.check;

import com.skynet.rimp.common.utils.util.RegexUtil;



public class ValidaUtil {
	private static String regex = "[$^&<>'/]";

	/**
	 * 是否安全字符串(不包含$^&<>'/)
	 * @param str
	 * @return
	 */
	public static boolean isSecStr(String str){
		return !RegexUtil.isExist(regex, str);
	}
}
