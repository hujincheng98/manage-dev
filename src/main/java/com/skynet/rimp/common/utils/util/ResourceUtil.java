package com.skynet.rimp.common.utils.util;

import java.util.ResourceBundle;

public class ResourceUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	private void ResourceUtil() {
		
	}

	/**
	 * 
	 * @return
	 */
	public static final String getValueByKey(String key) {
		return bundle.getString(key);
	}

}
