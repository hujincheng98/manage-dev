package com.skynet.rimp.task;

import java.util.Date;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import com.skynet.rimp.blackListInfo.service.IPabaPatientInfoService;
import com.skynet.rimp.schmInfo.service.ISchmMainInfoService;

/**
 * 
 * <p>Title: 定时器处理，排班数据自动回滚、自动处理解锁功能</p>
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


public class RimpTask extends TimerTask {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RimpTask.class);
	
	//服务注入
	private IPabaPatientInfoService pabaInfoService;
	private ISchmMainInfoService schmMainservice;
	private long time;
	
	//构造方法
	public RimpTask(IPabaPatientInfoService pabaInfoService, ISchmMainInfoService schmMainservice,long time){
			this.pabaInfoService=pabaInfoService;
			this.schmMainservice=schmMainservice;
			this.time = time;
	}

	//定时器执行
	public void run() {
		
			while(true){
				try {
					logger.info("定时器执行开始，执行日期"+new Date().toString());
					
					//排班回滚,根据省妇幼的情况，将挂号和预约都放在了线上预约限数上，不能进行回退，回退后，所有限号都归到线下挂号限数。
					//根据以后医院上线，如果采用了线上预约限数、线下挂号限数，是否需要进行回滚在另行配置参数来处理，根据医院id进行数据范围限制
					//schmMainservice.updateTaskSchm();
					//黑名单解锁
					pabaInfoService.updateTaskPaba();
					
					logger.info("定时器执行完成，执行日期"+new Date().toString());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
				
				//定时器等待执行时间
				
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					logger.error(e.getMessage());
				}
			}
		
	}
	
	//get与set方法
	public IPabaPatientInfoService getPabaInfoService() {
		return pabaInfoService;
	}



	public void setPabaInfoService(IPabaPatientInfoService pabaInfoService) {
		this.pabaInfoService = pabaInfoService;
	}



	public ISchmMainInfoService getSchmMainservice() {
		return schmMainservice;
	}



	public void setSchmMainservice(ISchmMainInfoService schmMainservice) {
		this.schmMainservice = schmMainservice;
	}


}
