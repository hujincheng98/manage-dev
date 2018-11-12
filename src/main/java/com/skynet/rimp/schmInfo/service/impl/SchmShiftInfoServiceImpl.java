package com.skynet.rimp.schmInfo.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.schmInfo.dao.SchmBaseMainInfoDao;
import com.skynet.rimp.schmInfo.dao.SchmDetailInfoDao;
import com.skynet.rimp.schmInfo.dao.SchmMainInfoDao;
import com.skynet.rimp.schmInfo.dao.SchmShiftInfoDao;
import com.skynet.rimp.schmInfo.dao.SchmTislInfoDao;
import com.skynet.rimp.schmInfo.service.ISchmShiftInfoService;
import com.skynet.rimp.schmInfo.vo.SchmShiftInfoEntity;
import com.skynet.rimp.schmInfo.vo.SchmTislInfoEntity;

/**
 * <p>Title: 排班ServiceImpl</p>
 * <p>Description: </p>
 *
 * @author wangshen
 * @version 1.00.00
 * @date 2015-7-1 上午11:51:52 
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Service
public class SchmShiftInfoServiceImpl extends BaseServiceImpl<SchmShiftInfoEntity> implements ISchmShiftInfoService {

	@Autowired
	private SchmShiftInfoDao dao;

	@Autowired
	private SchmTislInfoDao tislDao;
	@Autowired
	private SchmBaseMainInfoDao baseMainInfoDao;
	@Autowired
	private SchmMainInfoDao mainInfoDao;
	@Autowired
	private SchmDetailInfoDao detailDao;

	@Override
	public List<SchmShiftInfoEntity> findAll() throws Exception {
		return dao.findAll();
	}

	@Override
	public List<SchmShiftInfoEntity> findByCondition(SearchParams params)
			throws Exception {
		return dao.findByCondition(params);
	}

	public SchmShiftInfoEntity save(SchmShiftInfoEntity shift) throws Exception {
		List<SchmTislInfoEntity> tisls = shift.getTisls();
		String shiftId = UUIDGenerator.getUUID();
		if (shift != null && StringUtils.isBlank(shift.getShiftId())) {
			shift.setShiftId(shiftId);
			shift.setCreateDate(new Date());
			shift.setOrgId(UserUtil.getAuthCode());//设置建权编码
			shift.setCreateUser(UserUtil.getUserId());//设置创建用户
			dao.insert(shift);
		} else {
			shift.setUpdateDate(new Date());
			shift.setUpdateUser(UserUtil.getUserId());//设置更新用户
			shiftId = shift.getShiftId();
			dao.updateByPrimaryKeySelective(shift);
		}

		if (shift.getTisls() == null || shift.getTisls().size() == 0) {
			SchmTislInfoEntity tisl = new SchmTislInfoEntity();
			tisl.setShiftId(shiftId);
			tisl.setTislEndDate(shift.getShiftEndDate());
			tisl.setTislStartDate(shift.getShiftStartDate());
			tisl.setTislOffline(100);
			tisl.setTislOnline(100);
			tisl.setTislId(UUIDGenerator.getUUID());
			tisl.setCreateDate(new Date());
			tisl.setOrgId(UserUtil.getAuthCode());//设置建权编码
			tisl.setCreateUser(UserUtil.getUserId());//设置创建用户
			tislDao.insert(tisl);
		} else {
			for (SchmTislInfoEntity tisl : tisls) {
				if (StringUtils.isBlank(tisl.getTislId())
						&& StringUtils.isBlank(tisl.getShiftId())) {
					tisl.setShiftId(shiftId);
					tisl.setTislId(UUIDGenerator.getUUID());
					tisl.setCreateDate(new Date());
					tisl.setOrgId(UserUtil.getAuthCode());//设置建权编码
					tisl.setCreateUser(UserUtil.getUserId());//设置创建用户
					tislDao.insert(tisl);
				} else {
					tisl.setUpdateDate(new Date());
					tisl.setUpdateUser(UserUtil.getUserId());//设置更新用户
					tislDao.updateByPrimaryKeySelective(tisl);
				}
			}
		}
		return shift;
	}

	@Override
	public SchmShiftInfoEntity findById(String id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public int deleteTisl(String tislId) {
		return tislDao.deleteByPrimaryKey(tislId);
	}

	public int delete(String shiftId) throws Exception {
		int detailNum = detailDao.findByShiftId(shiftId);
		int mainNum = mainInfoDao.findByShiftId(shiftId);
		int baseNum = baseMainInfoDao.findByShiftId(shiftId);
		if (baseNum > 0 || mainNum > 0 || detailNum > 0) {
			return 0;
		} else {
			tislDao.deleteByShiftId(shiftId);
			return dao.deleteByPrimaryKey(shiftId);
		}
	}

	public SchmShiftInfoEntity update(SchmShiftInfoEntity arg0) {
		return null;
	}

	@Override
	public List<SchmShiftInfoEntity> findByShiftName(String shiftName) {
		return dao.findByShiftName(shiftName);
	}
	
	@Override
	public List<SchmTislInfoEntity> selectByShiftId(String shiftId) {
		return this.tislDao.selectByShiftId(shiftId);
	}

}
