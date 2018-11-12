package com.skynet.rimp.messPush.service.impl;

import com.skynet.rimp.common.utils.MessPushConfig;
import com.skynet.rimp.common.utils.ResponseEntity;
import com.skynet.rimp.messPush.service.ValidateMessPushService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * 
 * <p>Title: 消息推送服务转发接口实现类</p>
*
 */
@Service("validateMessPushService")
public class ValidateMessPushServiceImpl implements ValidateMessPushService {

	/**
	 * 必输字段校验器，需要业务处理的 需要进行，校验器调用
	 */
	@Override
	public String validateMessPush(String requestJson) {
		
		try{
			//需要进行交易码的转发，需要先进行交易码的判断
			JSONObject obj = JSONObject.fromObject(requestJson);
			if(!obj.containsKey("transactionCode")){
				return ResponseEntity.createErrorJsonResponse("缺少交易码参数");
			}
			String transactionCode=obj.getString("transactionCode");
			if(StringUtils.isBlank(transactionCode)){
				return ResponseEntity.createErrorJsonResponse("缺少交易码参数");
			}
			
			//交易码服务转发，各自的校验器
			String  result = ""; 
			switch (transactionCode) {
				//停诊通知
				case MessPushConfig.MESS_PUSH_20000:
					result = this.validateMessPushPara(requestJson, MessPushConfig.MESS_PUSH_PARA_20000);
					break;
				default:
				  result =	 ResponseEntity.createErrorJsonResponse("没有对应的消息推送交易码");
					break;
			}
			//默认成功返回空字符
			return result;	
			
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.createErrorJsonResponse("程序处理异常");
		}
		
	}
	
	/**
	 * 验证必输项字段
	 */
	public String validateMessPushPara(String requestJson,Map<String,String> paraMap) {
		//参数字段
		String result = "";
		try{
			//全部采用集合方式传递数据
			JSONObject obj = JSONObject.fromObject(requestJson);
			JSONArray jsonArray = JSONArray.fromObject(obj.get("requestData"));//获取推送信息
			//JSONObject objData = JSONObject.fromObject(obj.get("requestData"));
//			if (objData == null){
//				return ResponseEntity.createErrorJsonResponse("推送消息内容为空");
//			}
//			//循环验证必输字段
//			Set<String> keySets = paraMap.keySet();
//			for (String key : keySets) {
//				if(!objData.containsKey(key)){
//					return ResponseEntity.createErrorJsonResponse("必输字段不能为空.字段名称:"+key+" "+paraMap.get(key));
//				}
//				if(StringUtils.isBlank(objData.getString(key))){
//					return ResponseEntity.createErrorJsonResponse("必输字段不能为空.字段名称:"+key+" "+paraMap.get(key));
//				}
//			}
			//判断信息量为空
			if(jsonArray == null || jsonArray.size()<=0){
				return ResponseEntity.createErrorJsonResponse("推送消息内容为空");
			}
			//循环处理验证
			for (Object object : jsonArray) {
				//单条信息
				JSONObject jsonObj = JSONObject.fromObject(object);
				
				//非空判断
				if(jsonObj == null){
					return ResponseEntity.createErrorJsonResponse("推送消息内容为空");
				}
				
				//循环验证必输字段
				Set<String> keySets = paraMap.keySet();
				for (String key : keySets) {
					if(!jsonObj.containsKey(key)){
						return ResponseEntity.createErrorJsonResponse("必输字段不能为空.字段名称:"+key+" "+paraMap.get(key));
					}
					if(StringUtils.isBlank(jsonObj.getString(key))){
						return ResponseEntity.createErrorJsonResponse("必输字段不能为空.字段名称:"+key+" "+paraMap.get(key));
					}
				}
			}
			//校验成功返回空字符
			return  result;	
			
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.createErrorJsonResponse("程序处理异常");
		}
			
	}
	

}
