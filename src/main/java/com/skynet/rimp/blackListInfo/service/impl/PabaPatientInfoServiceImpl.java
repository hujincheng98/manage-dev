package com.skynet.rimp.blackListInfo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.blackListInfo.dao.PabaPatientInfoDao;
import com.skynet.rimp.blackListInfo.dao.PabaRuleInfoDao;
import com.skynet.rimp.blackListInfo.service.IPabaPatientInfoService;
import com.skynet.rimp.blackListInfo.service.IPabaRuleInfoService;
import com.skynet.rimp.blackListInfo.vo.PabaPatientInfo;
import com.skynet.rimp.blackListInfo.vo.PabaRuleInfo;
import com.skynet.rimp.common.utils.userUtil.UserUtil;

/**
 * <p>Title: 黑名单</p>
 * <p>Description: </p>
 *
 * @author wangshen
 * @version 1.00.00
 * @date 2015-7-11 下午3:04:51 
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Service
public class PabaPatientInfoServiceImpl extends BaseServiceImpl<PabaPatientInfo> implements IPabaPatientInfoService{
	
	@Autowired
	private PabaPatientInfoDao pabadao;
	
	@Autowired
	private PabaRuleInfoDao  pabaruleInfoDao;
	
	@Autowired
	private IPabaRuleInfoService pabaRuleInfoService;
	
	

	@Override
	public List<PabaPatientInfo> findByCondition(SearchParams params)
			throws Exception {
		return this.pabadao.findByCondition(params);
	}

	@Override
	public List<PabaPatientInfo> findAll() throws Exception {
		return this.pabadao.findAll();
	}

	@Override
	public int deleteByPrimaryKey(String pabaPatientId) {
		return this.pabadao.deleteByPrimaryKey(pabaPatientId);
	}

	@Override
	public int insert(PabaPatientInfo record) {
		return this.pabadao.insert(record);
	}

	@Override
	public int updateByPrimaryKeySelective(PabaPatientInfo record) {
		
		String state = record.getPabaPatientState();
		if(state.equals("PABA_STATE_1")){
			record.setPabaOnDate(new Date());
		}else{
			record.setPabaOffDate(new Date());
		}
		record.setUpdateDate(new Date());
		record.setUpdateUser(UserUtil.getUserId());
		return this.pabadao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(PabaPatientInfo record) {
		return 0;
	}

	@Override
	public PabaPatientInfo selectByPrimaryKey(String pabaPatientId) {
		return null;
	}

	@Override
	public int updateByPrimaryKey(PabaPatientInfo record) {
		return 0;
	}
	
	/**
	 * 排班定时任务回滚
	 */
	@Override
	public void updateTaskPaba() throws Exception{
		
		//根据渠道所有接入的医院进行循环处理，黑名单解锁信息
		
		// 根据渠道提取所有接入的医院id
		List<PabaRuleInfo> hosIdList = this.pabaruleInfoDao.findHosBychanne();
		
		//获取医院所有规则信息
		Map<String, Map> ruleMap =this.pabaRuleInfoService.initAllRul();
		
		if (hosIdList != null && hosIdList.size() > 0) {
			// 循环处理规则
			for (PabaRuleInfo pabaruleinfo : hosIdList) {
				//获取黑名单锁定天数，天数已过通过自动解锁
				String hosid = pabaruleinfo.getHosId();
				Hashtable<String, String> HosruleMap = (Hashtable<String, String>) ruleMap.get(hosid);
				int lockNum = 0 ; //查询不到规则数据为0默认解锁当前日期的患者
				if(HosruleMap !=null && HosruleMap.get("lockDayNum")!=null){
					lockNum = Integer.parseInt(HosruleMap.get("lockDayNum").toString());
				}
				//计算日期，通过系统设置不可预约天数
				PabaPatientInfo record = new PabaPatientInfo();
				SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
				Date beginDate = new Date();
				Calendar date = Calendar.getInstance();
				date.setTime(beginDate);
				date.set(Calendar.DATE, date.get(Calendar.DATE) - lockNum);
				Date endDate;
				try {
					endDate = dft.parse(dft.format(date.getTime()));
					//相关字段
					record.setHosId(hosid);
					record.setPabaOffDateTask(endDate);//锁定日期，查询条件
					record.setPabaOnDate(new Date());//解锁日期
					record.setUpdateDate(new Date());
					record.setUpdateUser("TASK_TIMER");
					pabadao.updateTaskPaba(record);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				
			}
		}
		
	}

}
