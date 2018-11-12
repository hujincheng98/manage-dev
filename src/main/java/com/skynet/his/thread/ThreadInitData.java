package com.skynet.his.thread;

import com.skynet.his.common.SysConfig;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.rimp.blackListInfo.service.IPabaRuleInfoService;
import com.skynet.rimp.channelInfo.service.IOtherChannelsInfoService;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import org.apache.log4j.Logger;
import java.util.Map;
import java.util.Hashtable;
import java.util.List;

public class ThreadInitData {
	
	protected transient static Logger logger = Logger.getLogger(ThreadInitData.class.getName());

	public boolean isRun = true;// 线程运行标志
	
	public ThreadInitData(final IOtherChannelsInfoService service,final IPabaRuleInfoService pabaRuleInfoService) {
		new Thread(){
			public void run(){
				logger.info(" >>> 初始化数据线程开始运行..");
				while(isRun){
					boolean isComplete = CacheCoreUtil.getSystemStartState()==null?false:CacheCoreUtil.getSystemStartState();
					if(isComplete){
						try {
							List<OtherChannelsInfo> others = service.findAll();
							for (OtherChannelsInfo channelsInfo : others) {
								//取得ext1字段标记为HIS的渠道信息
								if(channelsInfo.getExt1()==null||!channelsInfo.getExt1().equals("HIS")){
									continue;
								}
								//存放各医院的HIS渠道信息
								SysConfig.HIS_CHANNELS.put(channelsInfo.getHosId(), channelsInfo);
								//输出结果
								logger.info(" >>> HIS总数 ==== "+SysConfig.HIS_CHANNELS.size());
							}
							Map<String, OtherChannelsInfo> map = null;
							if (others != null && others.size() > 0) {
								map = new Hashtable<String, OtherChannelsInfo>();
								for (OtherChannelsInfo otherChannels : others) {
									map.put(otherChannels.getToken(), otherChannels);
								}
							}
							if (map != null) {
								SysConfig.OTHER_CHANNELS_INFO = map;
								logger.info(" >>> 第三方渠道信息总数：" + SysConfig.OTHER_CHANNELS_INFO.size());
							} else {
								logger.error(" >>> 第三方渠道信息获取失败.");
							}
							//加载规则信息HOS_RULE_MAP
							SysConfig.HOS_RULE_MAP = pabaRuleInfoService.initAllRul();
							
							//输出结果
							logger.info(" >>> 医院规则信息加载完成 ==== "+SysConfig.HOS_RULE_MAP.size());
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						isRun = false;//加载完毕，停止
					}
				}
			}
		}.start();
	}
}
