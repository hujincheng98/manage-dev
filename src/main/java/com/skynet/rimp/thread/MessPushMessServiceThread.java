package com.skynet.rimp.thread;

import com.skynet.his.utils.PushHisHttpRequest;
import com.skynet.rimp.common.utils.JsonlUtils;
import com.skynet.rimp.common.utils.MessPushConfig;
import com.skynet.rimp.messPush.dto.MessPushBusiInfo;
import com.skynet.rimp.messPush.dto.MessPushBusiLogInfo;
import com.skynet.rimp.messPush.service.MessPushLogService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import com.skynet.rimp.common.utils.DateUtils;
import java.util.Date;
/**
 * 
 * <p>Title: 消息推送服务进程</p>
 * 
*
 */
public class MessPushMessServiceThread extends Thread{
	
	private MessPushBusiInfo messPushBusiInfo = null;
	private MessPushLogService messPushLogService = null;
	
	public MessPushMessServiceThread(MessPushBusiInfo messPushBusiInfo, MessPushLogService messPushLogService) {
		this.messPushBusiInfo = messPushBusiInfo;
		this.messPushLogService = messPushLogService;
	}
	
	//启动一个线程处理数据
	public void run() {
		
		try {
			//线程推送判断
			if(messPushBusiInfo == null){
				return ;
			}
			//判断推送地址
			if(StringUtils.isBlank(messPushBusiInfo.getPushUrl())){
				return ;
			}
			//判断渠道token
			if(StringUtils.isBlank(messPushBusiInfo.getChToken())){
				return ;
			}
			//判断推送数据
			if(StringUtils.isBlank(messPushBusiInfo.getPushData())){
				return ;
			}
			//判断推送数据交易码
			String transactionCode = messPushBusiInfo.getTransactionCode();
			if(StringUtils.isBlank(transactionCode)){
				return ;
			}
			
			//日志数据进行处理
			MessPushBusiLogInfo loginfo = new MessPushBusiLogInfo();
			loginfo.setPushUrl(messPushBusiInfo.getPushUrl());
			loginfo.setChToken(messPushBusiInfo.getChToken());
			loginfo.setPushDate(DateUtils.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			loginfo.setTransactionCode(transactionCode);
			String transactionCodeName = "未知交易码";
			if(MessPushConfig.MESS_PUSH_INFO.containsKey(transactionCode)){
				transactionCodeName = MessPushConfig.MESS_PUSH_INFO.get(transactionCode);
			}
			loginfo.setTransactionName(transactionCodeName);
			loginfo.setPushData(messPushBusiInfo.getPushData());
			loginfo.setChId(messPushBusiInfo.getChId());
			loginfo.setHosId(messPushBusiInfo.getHosId());
			loginfo.setOrgId(messPushBusiInfo.getOrgId());
				
			
			//开始进行数据传送
			String result = PushHisHttpRequest.doMessPost(messPushBusiInfo.getPushUrl(), MessPushConfig.MESS_PUSH_SERVICE_LDE+messPushBusiInfo.getPushData());
			
			//处理返回数据
			if(!StringUtils.isBlank(result)){
				//由于应用服务器报错返回html字符，需要进行json有效验证
				if(!JsonlUtils.validateFromJson(result)){
					loginfo.setPushRespCode("1");
					loginfo.setPushRespData(result);
				}else{
					JSONObject resultJson = JSONObject.fromObject(result);
					loginfo.setPushRespCode(resultJson.getString("code"));
					loginfo.setPushRespData(resultJson.getString("responseData"));
				}
			}else{
				loginfo.setPushRespCode("2");
				loginfo.setPushRespData("返回结果为空");
			}
			
			//插入日志信息表
			messPushLogService.messPushLog(loginfo);
			
		}catch (Exception e) {
			MessPushBusiLogInfo loginfofail = new MessPushBusiLogInfo();
			loginfofail.setPushUrl(messPushBusiInfo.getPushUrl());
			loginfofail.setChToken(messPushBusiInfo.getChToken());
			loginfofail.setPushDate(DateUtils.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			String transactionCode = messPushBusiInfo.getTransactionCode();
			loginfofail.setTransactionCode(transactionCode);
			String transactionCodeName = "未知交易码";
			if(MessPushConfig.MESS_PUSH_INFO.containsKey(transactionCode)){
				transactionCodeName = MessPushConfig.MESS_PUSH_INFO.get(transactionCode);
			}
			loginfofail.setTransactionName(transactionCodeName);
			loginfofail.setPushData(messPushBusiInfo.getPushData());
			loginfofail.setChId(messPushBusiInfo.getChId());
			loginfofail.setHosId(messPushBusiInfo.getHosId());
			loginfofail.setOrgId(messPushBusiInfo.getOrgId());
			
			loginfofail.setPushRespCode("1");
			//异常处理
			String  exce = "未知异常";
			if(e!=null){
				exce = e.getMessage();
			}
			loginfofail.setPushRespData(exce);
			//插入日志信息表
			messPushLogService.messPushLog(loginfofail);
			
		}
		
		
	}
	
}
