package com.skynet.rimp.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.skynet.rimp.blackListInfo.service.IPabaPatientInfoService;
import com.skynet.rimp.common.utils.util.ResourceUtil;
import com.skynet.rimp.schmInfo.service.ISchmMainInfoService;
import com.skynet.rimp.task.RimpTask;

/**
 * 
 * <p>Title: 定时任务监听器</p>
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
public class TaskListener implements ServletContextListener {

	private Timer timer = null;
	
	private static final long PERIOD_DAY =  24 * 60 * 60 * 1000;  
	
	public static String falg ; //定时任务开关,0是关闭,1是打开,请务必采用此2个字段
	
	public static int hour ; //格式数字，24小时
	
	public static int minute ; //格式数字 ，60分钟
	
	public static int second ; //格式数字，60秒
	
	
	 

	public void contextInitialized(ServletContextEvent event) {
		//定时器无法获得服务实例，需要通过应用上下文获得
		ServletContext servletContext = event.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		IPabaPatientInfoService pabaInfoService=wac.getBean(IPabaPatientInfoService.class);
		ISchmMainInfoService schmMainservice=wac.getBean(ISchmMainInfoService.class);
		
		//实例化定时器
		timer = new Timer(true);
		
		//读取执行配置
		 falg=ResourceUtil.getValueByKey("falg").trim();
         hour = Integer.parseInt(ResourceUtil.getValueByKey("hour").trim());  
         minute = Integer.parseInt(ResourceUtil.getValueByKey("minute").trim());  
         second = Integer.parseInt(ResourceUtil.getValueByKey("second").trim());  
        
         //定时任务开关关闭，返回不启动定时器
         if(falg ==null || falg.equals("0")){
        	 return;
         }
         
		// 设置执行时间

         Calendar calendar = Calendar.getInstance();  
		calendar.set(Calendar.HOUR_OF_DAY, hour);  
		calendar.set(Calendar.MINUTE, minute);  
		calendar.set(Calendar.SECOND, second);  
		Date time = calendar.getTime();  
		
		//如果第一次执行定时任务的时间 小于 当前的时间
		//此时要在 第一次执行定时任务的时间 加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		 if (time.before(new Date())) {
			 time = this.addDay(time, 1);
		 }
		 
		timer = new Timer();  
		System.out.println("定时任务启动，启动时间为："+time.toString());
		timer.schedule(new RimpTask(pabaInfoService,schmMainservice,PERIOD_DAY), time);
	}

	public void contextDestroyed(ServletContextEvent event) {
		if(timer!=null){
			timer.cancel();
		}
		
	}
	
	 // 增加或减少天数
	 public Date addDay(Date date, int num) {
	  Calendar startDT = Calendar.getInstance();
	  startDT.setTime(date);
	  startDT.add(Calendar.DAY_OF_MONTH, num);
	  return startDT.getTime();
	 }
	 
}
