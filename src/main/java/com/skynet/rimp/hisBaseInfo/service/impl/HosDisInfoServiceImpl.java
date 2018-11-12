package com.skynet.rimp.hisBaseInfo.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.hisBaseInfo.dao.HosDeptInfoDao;
import com.skynet.rimp.hisBaseInfo.dao.HosDisAreaInfoDao;
import com.skynet.rimp.hisBaseInfo.service.IHosDisInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosDisAreaInfo;

@Service
public class HosDisInfoServiceImpl extends BaseServiceImpl<HosDisAreaInfo> implements IHosDisInfoService {
	
	@Autowired
	private HosDisAreaInfoDao hosDisAreaInfoDao;

	@Override
	public List<HosDisAreaInfo> findByCondition(SearchParams params) throws Exception {
		return this.hosDisAreaInfoDao.findByCondition(params);
	}

	@Override
	public List<HosDisAreaInfo> findAll() throws Exception {
		return this.hosDisAreaInfoDao.findAll();
	}
	/*
	 * (non-Javadoc)
	 * @see com.skynet.rimp.hisBaseInfo.service.IHosDisInfoService#save(com.skynet.rimp.hisBaseInfo.vo.HosDisAreaInfo)
	 * 返回值：
	 * 0：参数为空
	 * 1：新增成功
	 * 2：病区名称重复
	 */
	@Override
	public int save(HosDisAreaInfo hosDisAreaInfo) {
		if(hosDisAreaInfo==null){
			return 0;
		}
		//检查病区名称是否重复
		List<HosDisAreaInfo> list = this.hosDisAreaInfoDao.findByDisName(hosDisAreaInfo.getDisName());
		if(list !=null && list.size()>0){
			return 2;
		}
		hosDisAreaInfo.setDisId(UUIDGenerator.getUUID());
		hosDisAreaInfo.setCreateDate(new Date());
		hosDisAreaInfo.setCreateUser(UserUtil.getUserId());
		hosDisAreaInfo.setDisOrgCode(UserUtil.getAuthCode());
		this.hosDisAreaInfoDao.insert(hosDisAreaInfo);
		return 1;
	}
	@Autowired
	private HosDeptInfoDao  hosDeptInfoDao;
	@Override
	public int delete(String disId) {
		//检查科室是否依赖
		int dis = hosDeptInfoDao.findByDisId(disId);
		if(dis>0){
			return 0;
		}
		return this.hosDisAreaInfoDao.delete(disId);
	}
	/*
	 * (non-Javadoc)
	 * @see com.skynet.rimp.hisBaseInfo.service.IHosDisInfoService#update(com.skynet.rimp.hisBaseInfo.vo.HosDisAreaInfo)
	 * 返回值：
	 * 0：参数为空
	 * 1：更新成功
	 * 2：病区名称重复
	 */
	@Override
	public int update(HosDisAreaInfo hosDisAreaInfo) {
		if(hosDisAreaInfo==null){
			return 0;
		}
		//检查病区名称是否重复
		List<HosDisAreaInfo> list = this.hosDisAreaInfoDao.findByDisName(hosDisAreaInfo.getDisName());
		if(list !=null && list.size()>0 && !list.get(0).getDisId().equals(hosDisAreaInfo.getDisId())){
			return 2;
		}
		hosDisAreaInfo.setUpdateDate(new Date());
		hosDisAreaInfo.setUpdateUser(UserUtil.getUserId());
		this.hosDisAreaInfoDao.update(hosDisAreaInfo);
		return 1;
	}   

	@Override
	public HosDisAreaInfo getByDisId(String disId) {
		List<HosDisAreaInfo> list = this.hosDisAreaInfoDao.findByDisId(disId);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public HosDisAreaInfo getByDisName(String disName) {
		return this.hosDisAreaInfoDao.getByDisName(disName);
	}
}
