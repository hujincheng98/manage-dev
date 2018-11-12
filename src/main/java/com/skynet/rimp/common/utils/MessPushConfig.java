package com.skynet.rimp.common.utils;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * <p>Title: 消息推送常量</p>
*
 */
public class MessPushConfig {
	
	
	public static final String MESS_PUSH_SERVICE_LDE = "requestJson=";
	
	/**
	 *  20000 停诊通知
	 */
	public static final String MESS_PUSH_20000 = "20000";
	
	
	public static final Map<String, String> MESS_PUSH_INFO = new HashMap<String, String>();
	static {
		MESS_PUSH_INFO.put("20000","停诊通知");
		
	}
	//20000 停诊通知 --必输字段参数列表
	public static final Map<String, String> MESS_PUSH_PARA_20000 = new HashMap<String, String>();
	static {
		MESS_PUSH_PARA_20000.put("schmId","排班Id");
		/*MESS_PUSH_PARA_20000.put("schmHisId","HIS系统排班id");
		MESS_PUSH_PARA_20000.put("schmDate","排班日期");
		MESS_PUSH_PARA_20000.put("schmDeptName","排班科室");
		MESS_PUSH_PARA_20000.put("schmDocmName","排班医生");
		MESS_PUSH_PARA_20000.put("schmShiftName","班次名称");
		MESS_PUSH_PARA_20000.put("schmState","排班状态（启用、停用）");
		MESS_PUSH_PARA_20000.put("schmStopDesc","停诊原因");*/
		
	}

}
