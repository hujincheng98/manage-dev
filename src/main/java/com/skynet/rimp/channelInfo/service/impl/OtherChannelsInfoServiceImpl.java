package com.skynet.rimp.channelInfo.service.impl;

import com.skynet.common.SearchParams;
import com.skynet.his.common.SysConfig;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.channelInfo.dao.OtherChannelsInfoDao;
import com.skynet.rimp.channelInfo.service.IOtherChannelsInfoService;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OtherChannelsInfoServiceImpl extends BaseServiceImpl<OtherChannelsInfo> implements IOtherChannelsInfoService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OtherChannelsInfoServiceImpl.class);

	@Autowired
	private OtherChannelsInfoDao otherChannelsInfoDao;
	
	@Override
	public List<OtherChannelsInfo> findByCondition(SearchParams params) throws Exception {
		return this.otherChannelsInfoDao.findByCondition(params);
	}

	@Override
	public List<OtherChannelsInfo> findAll() throws Exception {
		return this.otherChannelsInfoDao.findAll();
	}
	
	@Override
	public void save(OtherChannelsInfo otherChannelsInfo) {
		try {
			otherChannelsInfo.setChId(UUIDGenerator.getUUID());
			otherChannelsInfo.setToken(UUIDGenerator.getUUID());
			otherChannelsInfo.setOrgId(UserUtil.getAuthCode());
			otherChannelsInfo.setCreateUser(UserUtil.getUserId());
			otherChannelsInfo.setCreateDate(new Date());
			//String appId = otherChannelsInfo.getAppId();
			otherChannelsInfo.setChRegiNum(0);
			/*if(appId.equals("")){
				appId = "";
			}
			otherChannelsInfo.setAppId(appId);*/
			if ("HIS".equals(otherChannelsInfo.getExt1())) { // 如果新增信息是HIS渠道，则查询之前是否有设置为HIS的渠道，有的话则更新为非HIS渠道
				SearchParams params = new SearchParams();
				Map<String, Object> searchParams = new HashMap<String, Object>();
				searchParams.put("hosId", otherChannelsInfo.getHosId());
				searchParams.put("ext1", "HIS");
				params.setSearchParams(searchParams); 
				List<OtherChannelsInfo> list = this.otherChannelsInfoDao.findByCondition(params);
				if (list != null && list.size() == 1) {
					OtherChannelsInfo record = list.get(0);
					record.setExt1("");
					this.otherChannelsInfoDao.update(record);
				}
				if (SysConfig.HIS_CHANNELS.containsKey(otherChannelsInfo.getHosId())){
					SysConfig.HIS_CHANNELS.remove(otherChannelsInfo.getHosId());
				}
				SysConfig.HIS_CHANNELS.put(otherChannelsInfo.getHosId(), otherChannelsInfo);
			}
			this.otherChannelsInfoDao.insert(otherChannelsInfo);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public void delete(String chId) {
		OtherChannelsInfo otherChannelsInfo = this.otherChannelsInfoDao.findById(chId);
		if (otherChannelsInfo != null) {
			if ("HIS".equals(otherChannelsInfo.getExt1()) && SysConfig.HIS_CHANNELS.containsKey(otherChannelsInfo.getHosId())){
				SysConfig.HIS_CHANNELS.remove(otherChannelsInfo.getHosId());
			}
			this.otherChannelsInfoDao.delete(chId);
		}
	}
	
	@Override
	public void update(OtherChannelsInfo otherChannelsInfo) {
		try {
			otherChannelsInfo.setUpdateDate(new Date());
			otherChannelsInfo.setUpdateUser(UserUtil.getUserId());
			if ("HIS".equals(otherChannelsInfo.getExt1())) { // 如果要更新为HIS渠道，则查询之前是否有设置其他HIS渠道，有的话则更新为非HIS渠道
				SearchParams params = new SearchParams();
				Map<String, Object> searchParams = new HashMap<String, Object>();
				searchParams.put("hosId", otherChannelsInfo.getHosId());
				searchParams.put("ext1", "HIS");
				params.setSearchParams(searchParams); 
				List<OtherChannelsInfo> list = this.otherChannelsInfoDao.findByCondition(params);
				if (list != null && list.size() == 1) {
					OtherChannelsInfo record = list.get(0);
					if (!record.getChId().equals(otherChannelsInfo.getChId())) {
						record.setExt1("");
						this.otherChannelsInfoDao.update(record);
					}
				}
				if ("state_1".equals(otherChannelsInfo.getChState()) && SysConfig.HIS_CHANNELS.containsKey(otherChannelsInfo.getHosId())){
					SysConfig.HIS_CHANNELS.remove(otherChannelsInfo.getHosId());
				}
				SysConfig.HIS_CHANNELS.put(otherChannelsInfo.getHosId(), otherChannelsInfo);
			}
			this.otherChannelsInfoDao.update(otherChannelsInfo);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	
	@Override
	public int saveNumBypk(OtherChannelsInfo otherChannelsInfo) throws Exception{
		if(otherChannelsInfo==null){
			return 0;
		}
		
		otherChannelsInfo.setUpdateDate(new Date());
		otherChannelsInfo.setUpdateUser(UserUtil.getUserId());
		return otherChannelsInfoDao.update(otherChannelsInfo);
	}

	@Override
	public List<OtherChannelsInfo> findListByOrgId(String orgId) {
		
		try {
			return otherChannelsInfoDao.findListByOrgId(orgId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteByKeyArr(String[] chIdArr) throws Exception {
		for(String chid:chIdArr){
			OtherChannelsInfo otherChannelsInfo = this.otherChannelsInfoDao.findById(chid);
			if (otherChannelsInfo != null) {
				if ("HIS".equals(otherChannelsInfo.getExt1()) && SysConfig.HIS_CHANNELS.containsKey(otherChannelsInfo.getHosId())){
					SysConfig.HIS_CHANNELS.remove(otherChannelsInfo.getHosId());
				}
			}
		}
		this.otherChannelsInfoDao.deleteByKeyArr(chIdArr);
//		OtherChannelsInfo otherChannelsInfo = this.otherChannelsInfoDao.findById(chId);
//		if (otherChannelsInfo != null) {
//			if ("HIS".equals(otherChannelsInfo.getExt1()) && SysConfig.HIS_CHANNELS.containsKey(otherChannelsInfo.getHosId())){
//				SysConfig.HIS_CHANNELS.remove(otherChannelsInfo.getHosId());
//			}
//			this.otherChannelsInfoDao.delete(chId);
//		}
		
	}
	@Override
	public OtherChannelsInfo findByHosId(String hosId) {
		return this.otherChannelsInfoDao.findByHosId(hosId);
	}
	

}
