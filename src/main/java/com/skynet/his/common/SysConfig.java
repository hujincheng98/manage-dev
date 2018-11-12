package com.skynet.his.common;

import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class SysConfig {
	
	//存放HIS渠道信息
	public static Map<String, OtherChannelsInfo> HIS_CHANNELS = new HashMap<String, OtherChannelsInfo>();
	
	//存放HIS渠道信息
	public static Map<String, Map> HOS_RULE_MAP = new Hashtable<String, Map>();

	/**
	 * 第三方渠道信息
	 */
	public static Map<String, OtherChannelsInfo> OTHER_CHANNELS_INFO = new Hashtable<String, OtherChannelsInfo>();
}
