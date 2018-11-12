package com.skynet.rimp.messPush.service;


import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import com.skynet.rimp.messPush.dto.MessChInfoMappingDTO;
import com.skynet.rimp.messPush.dto.RedisInfo;

import java.util.List;
import java.util.Map;

/**
 * 
 * <p>Title: 缓存redis业务相关信息查询</p>
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
public interface RedisInfoService {
	
	/**
	 * 根据医院id查询缓存redis的渠道信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	List<OtherChannelsInfo> queryOtherChnnelsRedis(RedisInfo info) throws Exception;

	/**
	 * 获取渠道对应的消息推送对应信息
	 * @param info
	 * @return
	 * @throws Exception
	 */
	Map<String, MessChInfoMappingDTO> queryChnnelsMessPushRedis(RedisInfo info) throws Exception;
}
