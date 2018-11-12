package com.skynet.rimp.messPush.service.impl;

import com.skynet.his.common.RedisConfig;
import com.skynet.rimp.common.utils.JsonlUtils;
import com.skynet.rimp.common.utils.RedisService;
import com.skynet.rimp.messPush.dto.MessChInfoMappingDTO;
import com.skynet.rimp.messPush.dto.RedisInfo;
import com.skynet.rimp.messPush.service.RedisInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


/**
 * 
 * <p>Title: 缓存redis业务相关信息查询</p>
 * 
*
 */
@Service("redisInfoService")
public class RedisInfoServiceImpl implements RedisInfoService {

	@Autowired
	private RedisService redisService;
	
	@Override
	public List<OtherChannelsInfo> queryOtherChnnelsRedis(RedisInfo info) throws Exception{
		if(info == null){
			return null;
		}
		String hosId = info.getHosId();
		if(StringUtils.isBlank(hosId)){
			return null;
		}
		
		//通过redis获取医院对应的渠道信息
    	List<String>  otherChnnelsRedis = redisService.getHashVal(RedisConfig.CHANNELS_HOS_INFO_KEY, hosId);
    	//存储规则按照，医院id存储String json
    	List<OtherChannelsInfo>  otherChannelsInfols = null;
    	if(otherChnnelsRedis !=null && otherChnnelsRedis.size()>0){
    		String otherChInfo = otherChnnelsRedis.get(0);
    		if(StringUtils.isNotBlank(otherChInfo)){
    			otherChannelsInfols = JsonlUtils.getListFromJsonArrStr(otherChInfo, OtherChannelsInfo.class);
    		}
    	}
    	return otherChannelsInfols;
		
	}
	
	
	@Override
	public Map<String, MessChInfoMappingDTO> queryChnnelsMessPushRedis(RedisInfo  info) throws Exception{
		if(info == null){
			return null;
		}
		String chId = info.getChId();
		if(StringUtils.isBlank(chId)){
			return null;
		}
		
		//通过redis获取渠道对应交易码信息
		List<String>  messPushChnnelsRedis = redisService.getHashVal(RedisConfig.CHANNELS_MESS_PUSH_KEY, chId);
    	//存储规则按照，渠道id存储String json
    	List<MessChInfoMappingDTO>  messotherChannelsInfols = null;
    	if(messPushChnnelsRedis !=null && messPushChnnelsRedis.size()>0){
    		String messOtherChInfo = messPushChnnelsRedis.get(0);
    		if(StringUtils.isNotBlank(messOtherChInfo)){
    			messotherChannelsInfols = JsonlUtils.getListFromJsonArrStr(messOtherChInfo, MessChInfoMappingDTO.class);
    		}
    	}
    	
    	//通过交易码code存储，渠道对应消息推送信息关系
    	Map<String, MessChInfoMappingDTO> messChmap = new Hashtable<String, MessChInfoMappingDTO>(); 
    	if(messotherChannelsInfols !=null && messotherChannelsInfols.size()>0){
    		for(MessChInfoMappingDTO messchinfo : messotherChannelsInfols){
    			messChmap.put(messchinfo.getMessCode(), messchinfo);
    		}
    	}
    	
    	
    	return messChmap;
		
	}

}
