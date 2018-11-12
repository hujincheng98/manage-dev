package com.skynet.rimp.messPush.service.impl;

import com.skynet.his.common.SysConfig;
import com.skynet.rimp.channelInfo.service.IReqHisLogService;
import com.skynet.rimp.channelInfo.vo.ReqHisLogInfo;
import com.skynet.rimp.common.utils.JsonlUtils;
import com.skynet.rimp.common.utils.MessPushConfig;
import com.skynet.rimp.common.utils.ResponseEntity;
import com.skynet.rimp.common.utils.TokenValidateUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.messPush.service.MessPushMessForwardService;
import com.skynet.rimp.messPush.service.MessPushMessService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 
 * <p>Title: 消息推送服务转发接口实现类</p>
 *
*
 */
@Service("messPushMessForwardService")
public class MessPushMessForwardServiceImpl implements MessPushMessForwardService {
	protected transient Logger logger = Logger.getLogger(this.getClass().getName());
	@Autowired
	private MessPushMessService messPushMessService;
	@Autowired
	private IReqHisLogService reqHisLogService;

	@Override
	public String messForward(String requestJson) {
		//验证json串是否有效
		if(!JsonlUtils.validateFromJson(requestJson)){
			return ResponseEntity.createErrorJsonResponse("参数json格式无效");
		}
		// 服务验证，服务入口进行token认证
		if (!TokenValidateUtil.ValidateTokenAndHosID(requestJson)) {
			return ResponseEntity.createErrorJsonResponse("token、hosId错误，渠道未启用，有效期过期");
		}
		
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
			//进行交易码的判断
			if(!MessPushConfig.MESS_PUSH_INFO.containsKey(transactionCode)){
				return ResponseEntity.createErrorJsonResponse("没有对应的消息推送交易码");
			}
			//消息验证及推送
			String  result = ""; 
			result = messPushMessService.messPush(requestJson);
			
			return result;	
			
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
