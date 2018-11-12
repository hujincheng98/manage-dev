package com.skynet.rimp.common.utils;

import com.skynet.his.common.SysConfig;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * 
 * <p>Title: token 医院渠道接入验证</p>
 * <p>Description: </p>
 *
 * @author Torlay
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
*
 */

public class TokenValidateUtil {
	
	
    /**
     * 验证tomken和医院id
     * @param requestJson
     * @return
     */
    public static boolean ValidateTokenAndHosID(String requestJson){
    	
    	if(StringUtils.isEmpty(requestJson)){
    		return false;
    	}
    	
    	JSONObject  obj= JSONObject.fromObject(requestJson);
    	
    	String token=(String) obj.get("token");
    	String hosId=(String) obj.get("hosId");
    	
    	if(StringUtils.isEmpty(token)){
    		return false;
    	}
    	
    	if(StringUtils.isEmpty(hosId)){
    		return false;
    	}
    	if(SysConfig.OTHER_CHANNELS_INFO.containsKey(token)
    			&& StringUtils.equals(SysConfig.OTHER_CHANNELS_INFO.get(token).getHosId(), hosId) ){
			OtherChannelsInfo othch = SysConfig.OTHER_CHANNELS_INFO.get(token);
    		//通过后验证当前渠道是否启用
    		boolean falg1=false;
    		if(othch.getChState().equals("state_1")){
    			falg1 = true;
    		}
    		boolean falg2=false;
    		//验证当前渠道是否在有效时间内
    		String currDate = DateUtils.getDate(new Date(), "yyyy-MM-dd");
    		String validDate = DateUtils.getDate(othch.getValidDate(), "yyyy-MM-dd");
    		if(DateUtils.compare_date(validDate, currDate, "yyyy-MM-dd")){
    			falg2= true;
    		}
    		//状态启用，并且在有效期内可以使用
    		if(falg1 && falg2){
    			return true;
    		}
    	}
    	return false;
    }
}
