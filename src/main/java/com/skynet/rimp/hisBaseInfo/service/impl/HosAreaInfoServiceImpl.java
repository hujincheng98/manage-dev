package com.skynet.rimp.hisBaseInfo.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.hisBaseInfo.dao.HosAreaInfoDao;
import com.skynet.rimp.hisBaseInfo.dao.HosDeptInfoDao;
import com.skynet.rimp.hisBaseInfo.service.IHosAreaInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosAreaInfo;
@Service
public class HosAreaInfoServiceImpl extends BaseServiceImpl<HosAreaInfo> implements
		IHosAreaInfoService {
	private Logger logger = Logger.getLogger(HosAreaInfoServiceImpl.class);
	@Autowired
	private HosAreaInfoDao  hosAreaInfoDao;
	@Override
	public List<HosAreaInfo> findByCondition(SearchParams params) throws Exception {
		return hosAreaInfoDao.findByCondition(params);
	}

	@Override
	public List<HosAreaInfo> findAll() throws Exception {
		return hosAreaInfoDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * @see com.skynet.rimp.hisBaseInfo.service.IHosAreaInfoService#save(com.skynet.rimp.hisBaseInfo.vo.HosAreaInfo)
	 * 返回值：
	 * 0 参数为空
	 * 1新增成功
	 * 2修改成功
	 * 3院区名称重复
	 */
	@Override
	public int save(HosAreaInfo hosAreaInfo) throws Exception{
		if(hosAreaInfo==null){
			return 0;
		}
		List<HosAreaInfo> list = hosAreaInfoDao.findByAreaName(hosAreaInfo.getAreaName());
		//新增
		if(hosAreaInfo.getAreaId() == null || StringUtils.isBlank(hosAreaInfo.getAreaId())){
			//检查名称是否重复
			if(list !=null && list.size()>0){//存在重名
				return 3;
			}
			hosAreaInfo.setAreaId(UUIDGenerator.getUUID());
			hosAreaInfo.setCreateDate(new Date());
			hosAreaInfo.setCreateUser(UserUtil.getUserId());
			hosAreaInfo.setOrgId(UserUtil.getAuthCode());
			hosAreaInfoDao.insert(hosAreaInfo);
			return 1;
		}else{//更新
			if(list !=null && list.size()>0 && !list.get(0).getAreaId().equals(hosAreaInfo.getAreaId())){//存在非本身的重名，
				return 3;
			}
			hosAreaInfo.setUpdateDate(new Date());
			hosAreaInfo.setUpdateUser(UserUtil.getUserId());
			hosAreaInfoDao.updateByPk(hosAreaInfo);
			return 2;
		}
	}
	
	@Override
	public HosAreaInfo getByAreaId(String areaId) {
		List<HosAreaInfo> list = hosAreaInfoDao.findByAreaId(areaId);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Autowired
	private HosDeptInfoDao  hosDeptInfoDao;
	/*
	 * @see com.skynet.rimp.hisBaseInfo.service.IHosAreaInfoService#delete(java.lang.String)
	 * 返回值
	 * 0：已经被使用
	 */
	@Override
	public int delete(String areaId) throws Exception {
		//检查科室是否有依赖关系
		int dept = hosDeptInfoDao.findByAreaId(areaId);
		if(dept>0){
			return 0;
		}
		return hosAreaInfoDao.deleteByPk(areaId);
	}

	@Override
	public HosAreaInfo getByAreaName(String areaName) {
		return this.hosAreaInfoDao.getByAreaName(areaName);
	}
	
	@Override
	public void update(HosAreaInfo hosAreaInfo) throws Exception {
		this.hosAreaInfoDao.updateByPk(hosAreaInfo);
	}
}
