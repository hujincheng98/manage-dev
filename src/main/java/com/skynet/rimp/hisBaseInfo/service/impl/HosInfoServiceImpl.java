package com.skynet.rimp.hisBaseInfo.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.CommonUtil;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.hisBaseInfo.dao.HosInfoDao;
import com.skynet.rimp.hisBaseInfo.service.IHosInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;

@Service
public class HosInfoServiceImpl extends BaseServiceImpl<HosInfo> implements IHosInfoService {

	@Autowired
	private HosInfoDao hosInfoDao;


	@Override
	public List<HosInfo> findAll() throws Exception {
		return this.hosInfoDao.findAll();
	}
	
	@Override
	public List<HosInfo> findByHosName(String hosName) {
		return this.hosInfoDao.findByHosName(hosName);
	}
	
	@Override
	public HosInfo findByHosOrgId(String OrgId) {
		return this.hosInfoDao.findByHosOrgId(OrgId);
	}

	@Override
	public List<HosInfo> findByCondition(SearchParams params) throws Exception {
		if (params != null) {
			String hosName = (String) params.getSearchParams().get("hosName");
			if(StringUtils.isNotEmpty(hosName)){
				char[] charStr = hosName.toCharArray();
				if(CommonUtil.containStr(charStr)){
					params.getSearchParams().put("hosName", "");
					params.getSearchParams().put("hosPinyCode", hosName);
				}
			}
		}
		return this.hosInfoDao.findByCondition(params);
	}

	@Override
	public void save(HosInfo hosInfo) {
		hosInfo.setHosId(UUIDGenerator.getUUID()); // 医院ID
		hosInfo.setOrgId(UserUtil.getAuthCode());
		hosInfo.setCreateUser(UserUtil.getUserId());
		hosInfo.setCreateDate(new Date());
		this.hosInfoDao.insert(hosInfo);
	}
	
	@Override
	public void delete(String hosId) {
		this.hosInfoDao.delete(hosId);
	}
	
	@Override
	public void update(HosInfo hosInfo) {
		hosInfo.setUpdateDate(new Date());
		hosInfo.setUpdateUser(UserUtil.getUserId());
		this.hosInfoDao.update(hosInfo);
	}

	@Override
	public HosInfo findByhosId(String hosId) {
		return this.hosInfoDao.findByhosId(hosId);
	}

	// 根据orgId获取hosId
	@Override
	public String getHosIdByOrgId() {
		String hosId = null;
		// 获取机构编码
		String code = UserUtil.getAuthCode();
		// 0表示admin
		if (!code.trim().equals("0")) {
			HosInfo hosInfo = this.hosInfoDao.findByHosOrgId(code);
			if (hosInfo != null) {
				hosId = hosInfo.getHosId();
			}
		}
		return hosId;
	}

}
