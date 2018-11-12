package com.skynet.rimp.messPush.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.messPush.dao.MessChInfoMappingDao;
import com.skynet.rimp.messPush.dto.MessChInfoMappingDTO;
import com.skynet.rimp.messPush.service.IMessChInfoMappingService;
import com.skynet.rimp.messPush.vo.MessChInfoMapping;
import com.skynet.rimp.messPush.vo.MessChInfoMappingKey;

@Service("messChInfoMappingService")
public class MessChInfoMappingServiceImpl extends BaseServiceImpl<MessChInfoMapping> implements IMessChInfoMappingService {

	@Autowired
	private MessChInfoMappingDao messChInfoMappingDao;

	@Override
	public List<MessChInfoMapping> findByCondition(SearchParams params) throws Exception {
		return this.messChInfoMappingDao.findByCondition(params);
	}

	@Override
	public List<MessChInfoMapping> findAll() throws Exception {
		return this.messChInfoMappingDao.findAll();
	}

	@Override
	public int insert(MessChInfoMapping record) {
		record.setCreateDate(new Date());
		record.setCreateUser(UserUtil.getUserId());
		record.setOrgId(UserUtil.getAuthCode());
		return this.messChInfoMappingDao.insert(record);
	}

	@Override
	public int delete(MessChInfoMappingKey key) {
		return this.messChInfoMappingDao.delete(key);
	}

	@Override
	public int update(MessChInfoMapping record) {
		record.setUpdateDate(new Date());
		record.setUpdateUser(UserUtil.getUserId());
		return this.messChInfoMappingDao.update(record);
	}

	@Override
	public MessChInfoMapping getMessChInfo(MessChInfoMappingKey key) {
		return this.messChInfoMappingDao.getMessChInfo(key);
	}

	@Override
	public List<MessChInfoMapping> findMessChInfoByChId(String chId) {
		return this.messChInfoMappingDao.findMessChInfoByChId(chId);
	}

	@Override
	public int deleteByChId(String chId) {
		return this.messChInfoMappingDao.deleteByChId(chId);
	}

	@Override
	public List<MessChInfoMappingDTO> findMessChInfoRedisByChId(String chId) {
		return this.messChInfoMappingDao.findMessChInfoRedisByChId(chId);
	}

	@Override
	public int deleteByKeyArr(String[] chIdArr) throws Exception {
		return this.messChInfoMappingDao.deleteByKeyArr(chIdArr);
		
	}

}
