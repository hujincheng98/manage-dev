package com.skynet.rimp.channelInfo.service.impl;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.channelInfo.dao.OtherHisChannelsInfoDao;
import com.skynet.rimp.channelInfo.service.IOtherHisChannelsInfoService;
import com.skynet.rimp.channelInfo.vo.OtherHisChannelsInfo;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/15.
 */
@Service
public class OtherHisChannelsInfoServiceImpl extends BaseServiceImpl<OtherHisChannelsInfo> implements IOtherHisChannelsInfoService {
    private final Logger logger = Logger.getLogger(getClass());
    @Autowired
    private OtherHisChannelsInfoDao otherHisChannelsInfoDao;
    @Override
    public void save(OtherHisChannelsInfo otherHisChannelsInfo) {
        otherHisChannelsInfo.setChHisId(UUIDGenerator.getUUID());
        otherHisChannelsInfo.setCreateDate(new Date());
        otherHisChannelsInfo.setCreateUser(UserUtil.getUserId());
        otherHisChannelsInfo.setOrgId(UserUtil.getAuthCode());
        otherHisChannelsInfoDao.insert(otherHisChannelsInfo);
    }

    @Override
    public void delete(String chHisId) {
        otherHisChannelsInfoDao.deleteByPrimaryKey(chHisId);
    }

    @Override
    public void update(OtherHisChannelsInfo otherHisChannelsInfo) {
        otherHisChannelsInfo.setUpdateDate(new Date());
        otherHisChannelsInfo.setUpdateUser(UserUtil.getUserId());
        otherHisChannelsInfoDao.updateByPrimaryKeySelective(otherHisChannelsInfo);
    }

    @Override
    public int saveNumBypk(OtherHisChannelsInfo otherHisChannelsInfo) throws Exception {
        return 0;
    }

    @Override
    public List<OtherHisChannelsInfo> findByCondition(SearchParams params) throws Exception {
        return otherHisChannelsInfoDao.findByCondition(params);
    }

    @Override
    public List<OtherHisChannelsInfo> findAll() throws Exception {
        return otherHisChannelsInfoDao.findAll();
    }
}
