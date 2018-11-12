package com.skynet.rimp.blackListInfo.service.impl;

import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.platform.common.util.CacheCoreUtil;
import com.skynet.platform.core.entity.DictionaryEntity;
import com.skynet.rimp.blackListInfo.dao.PabaRuleInfoDao;
import com.skynet.rimp.blackListInfo.service.IPabaRuleInfoService;
import com.skynet.rimp.blackListInfo.vo.PabaRuleInfo;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;

@Service
public class PabaRuleInfoServiceimpl extends BaseServiceImpl<PabaRuleInfo>
		implements IPabaRuleInfoService {

	@Autowired
	private PabaRuleInfoDao pabaRuleInfoDao;

	@Override
	public void insert(PabaRuleInfo pabaRuleInfo) {
		pabaRuleInfo.setPabaId(UUIDGenerator.getUUID());
		pabaRuleInfo.setOrgId(UserUtil.getAuthCode());
		pabaRuleInfo.setCreateUser(UserUtil.getUserId());
		pabaRuleInfo.setCreateDate(new Date());
		if (StringUtils.isNotEmpty(pabaRuleInfo.getPabaRuleType()))
        {
            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(pabaRuleInfo.getPabaRuleType());
            if (dictionary != null)
            {
                pabaRuleInfo.setPabaRuleName(dictionary.getDictName());
            }
        }
		this.pabaRuleInfoDao.insert(pabaRuleInfo);
	}

	@Override
	public void delete(String pabaId) {
		this.pabaRuleInfoDao.delete(pabaId);
	}

	@Override
	public void update(PabaRuleInfo pabaRuleInfo) {
		pabaRuleInfo.setUpdateDate(new Date());
		pabaRuleInfo.setUpdateUser(UserUtil.getUserId());
		if (StringUtils.isNotEmpty(pabaRuleInfo.getPabaRuleType()))
        {
            DictionaryEntity dictionary = CacheCoreUtil.getDictionaryByValue(pabaRuleInfo.getPabaRuleType());
            if (dictionary != null)
            {
                pabaRuleInfo.setPabaRuleName(dictionary.getDictName());
            }
        }
		this.pabaRuleInfoDao.update(pabaRuleInfo);
	}

	@Override
	public List<PabaRuleInfo> findAll() throws Exception {
		return this.pabaRuleInfoDao.findAll();
	}

	@Override
	public List<PabaRuleInfo> findByCondition(SearchParams params)
			throws Exception {
		return this.pabaRuleInfoDao.findByCondition(params);
	}

	/**
	 * 处理所有和规则相关的方法
	 */
	@Override
	public Map<String, Map> initAllRul() {
		// 查询默认系统admin的系统规则
		Map<String, String> defulerulMap = new Hashtable<String, String>();; // 系统默认admin规则
		String defuletrulsql = "SELECT PABA_RULE_TYPE,PABA_RULE_NUM FROM paba_rule_info where HOS_ID  is null ";
		List<PabaRuleInfo> defuletrulList = this.pabaRuleInfoDao.finddefault();

		// 将系统规则放入到map中
		if (defuletrulList.size() > 0) {
			for (PabaRuleInfo rul : defuletrulList) {
				defulerulMap.put(rul.getPabaRuleType(), rul.getPabaRuleNum());
			}
		}

		// 根据渠道提取所有接入的医院id
		List<PabaRuleInfo> hosIdList = this.pabaRuleInfoDao.findHosBychanne();

		// 进行规则整理
		Map<String, Map> HosrulMap = new Hashtable<String, Map>(); // 存储医院渠道规则map

		Map<String, String> rulMap = null;// 存储各自医院内规则

		if (hosIdList != null && hosIdList.size() > 0) {
			// 循环处理规则
			for (PabaRuleInfo pabainfo : hosIdList) {

				String hosid = pabainfo.getHosId();

				// 查询医院固定的规则
				List<PabaRuleInfo> hosrulList = this.pabaRuleInfoDao.findHosRule(hosid);

				// 将存在的规则放入map
				if (hosrulList != null && hosrulList.size() > 0) {
					// 循环把规则放入map
					rulMap = new Hashtable<String, String>();
					for (PabaRuleInfo rulhos : hosrulList) {
						rulMap.put(rulhos.getPabaRuleType(),rulhos.getPabaRuleNum());
					}
					// 循环系统通过规则，如果没有放入到医院map中
					for (Iterator it = defulerulMap.keySet().iterator(); it.hasNext();) {
						String key = (String) it.next();
						if (!rulMap.containsKey(key)) {
							rulMap.put(key, defulerulMap.get(key));
						}
					}
					// 处理完成所有的规则放入统一的医院中
					HosrulMap.put(hosid, rulMap);

					// 全部不存在放入系统默认
				} else {
					HosrulMap.put(hosid, defulerulMap);
				}
			}

		}
		return HosrulMap;

	}
}
