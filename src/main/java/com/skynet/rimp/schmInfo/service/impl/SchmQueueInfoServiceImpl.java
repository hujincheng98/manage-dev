package com.skynet.rimp.schmInfo.service.impl;

import com.skynet.common.SearchParams;
import com.skynet.his.common.SysConfig;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.schmInfo.dao.SchmQueueInfoDao;
import com.skynet.rimp.schmInfo.service.ISchmQueueInfoService;
import com.skynet.rimp.schmInfo.vo.SchmMainInfo;
import com.skynet.rimp.schmInfo.vo.SchmQueueInfo;
import com.skynet.rimp.schmInfo.vo.SchmQueueInfoKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SchmQueueInfoServiceImpl extends BaseServiceImpl<SchmQueueInfo> implements
		ISchmQueueInfoService {

	@Autowired
	private SchmQueueInfoDao schmQueueInfoDao;
	
	@Override
	public List<SchmQueueInfo> findByCondition(SearchParams params)
			throws Exception {
		return schmQueueInfoDao.findByCondition(params);
	}

	@Override
	public List<SchmQueueInfo> findAll() throws Exception {
		return schmQueueInfoDao.findAll();
	}

	@Override
	public List<SchmQueueInfo> insertQueue(SchmMainInfo schmMainInfo) {
		if (schmMainInfo.getSchmRegiSum() == null) {
			return null;
		}
		
		List<SchmQueueInfo> list =  null;
		//就诊间隔的判断处理
		
		if(schmMainInfo!= null && schmMainInfo.getDiagnosisInte()!=null && schmMainInfo.getDiagnosisInte() != 0){
			//采用时间间隔生成时间点
			list = genereteInte(schmMainInfo.getStartDate(), schmMainInfo.getEndDate(), schmMainInfo.getSchmRegiSum(),schmMainInfo.getDiagnosisInte(), schmMainInfo.getSchmId(), schmMainInfo.getHosId());
		}else{
			//采用默认方式进行平均分配时间点
			list = generete(schmMainInfo.getStartDate(), schmMainInfo.getEndDate(), schmMainInfo.getSchmRegiSum(), schmMainInfo.getSchmId(), schmMainInfo.getHosId());
		}
		 
		if (list != null && list.size() > 0) {
			for (SchmQueueInfo schmQueueInfo : list) {
				schmQueueInfo.setCreateDate(new Date());
				schmQueueInfo.setCreateUser(UserUtil.getUserId());
				schmQueueInfo.setOrgId(UserUtil.getAuthCode());
				schmQueueInfoDao.insert(schmQueueInfo);//插入数据库
			}
		}
		return list;
	}
	@Override
	public List<SchmQueueInfo> generete(SchmMainInfo schmMainInfo) {
		if (schmMainInfo.getSchmRegiSum() == null || schmMainInfo.getSchmRegiSum() == 0) {
			return null;
		}
		List<SchmQueueInfo> list =  null;
		//就诊间隔的判断处理
		
		if(schmMainInfo!= null && schmMainInfo.getDiagnosisInte()!=null && schmMainInfo.getDiagnosisInte() != 0){
			//采用时间间隔生成时间点
			list = genereteInte(schmMainInfo.getStartDate(), schmMainInfo.getEndDate(), schmMainInfo.getSchmRegiSum(),schmMainInfo.getDiagnosisInte(), schmMainInfo.getSchmId(), schmMainInfo.getHosId());
		}else{
			//采用默认方式进行平均分配时间点
			list = generete(schmMainInfo.getStartDate(), schmMainInfo.getEndDate(), schmMainInfo.getSchmRegiSum(), schmMainInfo.getSchmId(), schmMainInfo.getHosId());
		}
		
		return list;
	}
	
	//默认方式进行平均分配时间点
	private List<SchmQueueInfo> generete(Date start, Date end, Integer num, String schmId, String hosId) {
		if (!"1".equals(this.getQueuePushMark(hosId))) {
			return null;
		}
		if (num == null) {
			return null;
		}
		long diff = (end.getTime() - start.getTime()) ;//相差的毫秒数
		if (diff <= 0) {
			return null;
		}
		int interval = (int) (diff / num);
		Calendar calendar = Calendar.getInstance();
		Calendar calendarFormat = Calendar.getInstance();
		calendar.setTime(start);
		List<SchmQueueInfo> list = new ArrayList<SchmQueueInfo>();
		int i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		for (; i < num; i++) {
			calendarFormat.setTime(calendar.getTime());
			calendarFormat.set(Calendar.SECOND, 0);//丢掉秒
			
			SchmQueueInfo schmQueueInfo = new SchmQueueInfo();
			schmQueueInfo.setQueueNum(i+1);
			schmQueueInfo.setQueueDate(sdf.format(calendarFormat.getTime()));
			schmQueueInfo.setQueueState("0");
			schmQueueInfo.setSchmId(schmId);
			schmQueueInfo.setCreateDate(new Date());
			schmQueueInfo.setCreateUser(UserUtil.getUserId());
			schmQueueInfo.setOrgId(UserUtil.getAuthCode());
			list.add(schmQueueInfo);
			
			calendar.add(Calendar.MILLISECOND, interval);//时间累加
		}
		return list;
	}
	
	//就诊间隔的判断处理
	private List<SchmQueueInfo> genereteInte(Date start, Date end, Integer num,Integer diagnosisInte, String schmId, String hosId) {
		if (!"1".equals(this.getQueuePushMark(hosId))) {
			return null;
		}
		if (num == null) {
			return null;
		}
		
		Integer interval = diagnosisInte;
		Calendar calendar = Calendar.getInstance();
		Calendar calendarFormat = Calendar.getInstance();
		calendar.setTime(start);
		List<SchmQueueInfo> list = new ArrayList<SchmQueueInfo>();
		int i = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		for (; i < num; i++) {
			calendarFormat.setTime(calendar.getTime());
			
			SchmQueueInfo schmQueueInfo = new SchmQueueInfo();
			schmQueueInfo.setQueueNum(i+1);
			
			//当生成时间点超出排班班次截止时间时，超出的点都以班次截止时间作为时间点
			Date queueDate = calendarFormat.getTime();
			if(queueDate.after(end)){
				schmQueueInfo.setQueueDate(sdf.format(end));
			}else{
				schmQueueInfo.setQueueDate(sdf.format(queueDate));
			}
			schmQueueInfo.setQueueState("0");
			schmQueueInfo.setSchmId(schmId);
			schmQueueInfo.setCreateDate(new Date());
			schmQueueInfo.setCreateUser(UserUtil.getUserId());
			schmQueueInfo.setOrgId(UserUtil.getAuthCode());
			list.add(schmQueueInfo);
			
			calendar.add(Calendar.SECOND, interval);//时间累加
		}
		return list;
	}
	

	@Override
	public int delete(SchmQueueInfoKey schmQueueInfoKey) {
		return schmQueueInfoDao.deleteByPrimaryKey(schmQueueInfoKey);
	}


	@Override
	public List<SchmQueueInfo> updateQueue(SchmMainInfo schmMainInfo) {
		//先删除
		schmQueueInfoDao.deleteBySchmId(schmMainInfo.getSchmId());
		//重新生成
		return insertQueue(schmMainInfo);
	}

	@Override
	public int deleteBySchmId(String schmId) {
		return this.schmQueueInfoDao.deleteBySchmId(schmId);
	}
	@Override
	public int deleteBySchmIds(String[] schmId) {
		return this.schmQueueInfoDao.deleteBySchmIds(schmId);
	}



	@Override
	public int update(SchmQueueInfo schmQueueInfo) {
		return schmQueueInfoDao.updateByPrimaryKey(schmQueueInfo);
	}


	@Override
	public String getQueuePushMark(String hosid){
		if (hosid == null) {
			return "0";
		}
		//获取医院所有规则信息
		Map<String, Map> ruleMap = SysConfig.HOS_RULE_MAP;
		if (ruleMap == null || ruleMap.isEmpty()) {
			return "0";
		}
		if (!ruleMap.containsKey(hosid)) {
			return "0";
		}
		Hashtable<String, String> hosruleMap = (Hashtable<String, String>) ruleMap.get(hosid);
		String schmQueuePushMark = "0" ; //查询不到规则数据为0默认不推送排队号信息
		if (hosruleMap == null || hosruleMap.isEmpty()) {
			return "0";
		}
		if (!hosruleMap.containsKey("schmQueuePushMark")) {
			return "0";
		}
		return schmQueuePushMark = hosruleMap.get("schmQueuePushMark");
	}
}
