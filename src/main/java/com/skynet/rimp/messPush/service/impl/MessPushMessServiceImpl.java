package com.skynet.rimp.messPush.service.impl;


import com.skynet.his.common.SysConfig;
import com.skynet.rimp.channelInfo.service.IReqHisLogService;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import com.skynet.rimp.channelInfo.vo.ReqHisLogInfo;
import com.skynet.rimp.common.utils.MessPushConfig;
import com.skynet.rimp.common.utils.ResponseEntity;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.messPush.dto.MessChInfoMappingDTO;
import com.skynet.rimp.messPush.dto.MessPushBusiInfo;
import com.skynet.rimp.messPush.dto.RedisInfo;
import com.skynet.rimp.messPush.service.MessPushLogService;
import com.skynet.rimp.messPush.service.MessPushMessService;
import com.skynet.rimp.messPush.service.RedisInfoService;
import com.skynet.rimp.messPush.service.ValidateMessPushService;
import com.skynet.rimp.thread.MessPushMessServiceThread;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("messPushMessService")
public class MessPushMessServiceImpl implements MessPushMessService {
	protected transient Logger logger = Logger.getLogger(this.getClass().getName());
	@Autowired
	private ValidateMessPushService validateMessPushService;
	
	@Autowired
	private MessPushLogService messPushLogService;

	@Autowired
	private RedisInfoService redisInfoService;

	@Autowired
	private IReqHisLogService reqHisLogService;

	/**
	 * 必输字段校验器，需要业务处理的 需要进行，校验器调用
	 */
	@Override
	public String messPush(String requestJson) {
		
		try{
			
			JSONObject obj = JSONObject.fromObject(requestJson);
			
			String result = "";
			//进行消息校验
			result = validateMessPushService.validateMessPush(requestJson);
			//校验没有通过
			if(StringUtils.isNotBlank(result)){
				return result;
			}
			//调用之前交易码做过验证，直接获取
			String transactionCode=obj.getString("transactionCode");
			String token=(String) obj.get("token");
	    	String hosId=(String) obj.get("hosId");
			
	    	//redis中获取医院对应的渠道信息
	    	RedisInfo info = new RedisInfo();
	    	info.setHosId(hosId);
	    	List<OtherChannelsInfo> otherChannelsInfols = redisInfoService.queryOtherChnnelsRedis(info);
	    	
	    	//进行医院对应渠道的循环处理发送信息，当churl不为空的时候认为需要推送消息
	    	if(otherChannelsInfols !=null && otherChannelsInfols.size()>0){
	    		//循环处理发送信息
	    		for(OtherChannelsInfo otherChennel : otherChannelsInfols){
	    			//排除掉HIS渠道 ,并且消息推送服务地址不为空
	    			if(otherChennel!=null && !StringUtils.equals("HIS", otherChennel.getExt1()) && StringUtils.isNotBlank(otherChennel.getChUrl())){
	    				//通过redis获取医院渠道对应消息推送关系，通过渠道编号
	    				RedisInfo chinfo = new RedisInfo(); 
	    				chinfo.setChId(otherChennel.getChId());
	    				Map<String, MessChInfoMappingDTO> messChmap = redisInfoService.queryChnnelsMessPushRedis(chinfo);
	    				//判断是否存在交易码，存在进行消息推送
	    				if(messChmap.containsKey(transactionCode)){
	    					MessPushBusiInfo messPushBusiInfo = new MessPushBusiInfo();
	    					String churl=otherChennel.getChUrl();
							messPushBusiInfo.setPushUrl(churl);
							messPushBusiInfo.setPushData(requestJson);
							messPushBusiInfo.setChToken(otherChennel.getToken());
							messPushBusiInfo.setTransactionCode(transactionCode);
							messPushBusiInfo.setChId(otherChennel.getChId());
							messPushBusiInfo.setHosId(otherChennel.getHosId());
							messPushBusiInfo.setOrgId(otherChennel.getOrgId());
							//封装线程数据,启动线程进行消息推送服务
							MessPushMessServiceThread push = new MessPushMessServiceThread(messPushBusiInfo,messPushLogService);
							push.start();
	    				}
	    			}
	    		}
	    	}else{
	    		return ResponseEntity.createErrorJsonResponse("渠道信息为空");
	    	}
	    	//返回成功信息，不等待渠道返回信息，并携带交易名称，提示消息推送方
			String transactionCodeName = "未知交易码";
			if(MessPushConfig.MESS_PUSH_INFO.containsKey(transactionCode)){
				transactionCodeName = MessPushConfig.MESS_PUSH_INFO.get(transactionCode);
			}
			return ResponseEntity.createNormalJsonResponse(transactionCodeName+"推送成功");
		}catch (Exception e) {
			logger.error(e.getMessage());
			ReqHisLogInfo hisLogInfo = new ReqHisLogInfo();
			hisLogInfo.setId(UUIDGenerator.getUUID());
			hisLogInfo.setReqUrl(SysConfig.HIS_CHANNELS.get(JSONObject.fromObject(requestJson).getString("hosId")).getChUrl());
			hisLogInfo.setReqDate(new Date());
			hisLogInfo.setTransactioncode(JSONObject.fromObject(requestJson).getString("transactionCode"));
			hisLogInfo.setOperationtype("停诊通知");
			hisLogInfo.setRequestdata("not record request");
			hisLogInfo.setRespCode("1");
			hisLogInfo.setRespData("未知异常！");
			if(e!=null){
				hisLogInfo.setRespData(e.getMessage());
			}
			reqHisLogService.insert(hisLogInfo);
			return ResponseEntity.createErrorJsonResponse("程序处理异常");
		}
		
	}
	

}
