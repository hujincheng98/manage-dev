package com.skynet.his.common;

import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import com.skynet.his.thread.ThreadInitData;
import com.skynet.his.utils.DateUtils;
import com.skynet.rimp.blackListInfo.service.IPabaRuleInfoService;
import com.skynet.rimp.channelInfo.service.IOtherChannelsInfoService;

@Service
public class InitDataListener implements InitializingBean, ServletContextAware{
	
	protected transient Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private IOtherChannelsInfoService channelsInfoService;
	
	@Autowired
	private IPabaRuleInfoService pabaRuleInfoService;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		logger.info(" >>> 初始化数据开始，当前时间：" + DateUtils.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		//获取HIS的渠道信息
		new ThreadInitData(channelsInfoService,pabaRuleInfoService);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
