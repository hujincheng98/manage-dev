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
import com.skynet.rimp.hisBaseInfo.dao.HosInfoDao;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;
import com.skynet.rimp.schmInfo.dao.SchmReleLimitDao;
import com.skynet.rimp.schmInfo.service.ISchmReleLimitService;
import com.skynet.rimp.schmInfo.vo.SchmReleLimit;
/**
 * 
 * <p>Title: 放号管理服务</p>
 * <p>Description: 放号管理的服务，提供新增和更新服务</p>
 *
 * @author Torlay
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
*
 */

@Service
public class SchmReleLimitServiceImpl extends BaseServiceImpl<SchmReleLimit> implements ISchmReleLimitService
{
    @Autowired
    private SchmReleLimitDao dao;
    
    @Autowired
    private HosInfoDao hosInfoDao;

    @Override
    public List<SchmReleLimit> findAll()
        throws Exception
    {
        return dao.findAll();
    }


    @Override
    public SchmReleLimit update(SchmReleLimit record)
    {
        int num = -1;
        if (StringUtils.isBlank(record.getReleId()))
        {//新增
            record.setReleId(UUIDGenerator.getUUID());
            record.setOrgId(UserUtil.getAuthCode());
            record.setCreateDate(new Date());
            record.setCreateUser(UserUtil.getUserId());
            
            //设置医院id
            List<HosInfo> list = hosInfoDao.findAll();
			if(list!=null && list.size()==1){
				record.setHosId(list.get(0).getHosId());
			}
            num = dao.insertSelective(record);
        }
        else
        {
            record.setUpdateDate(new Date());
            record.setUpdateUser(UserUtil.getUserId());
            num = dao.updateByPrimaryKeySelective(record);
        }
        if (num > 0)
        {
            return record;
        }
        return null;
    }


	@Override
	public List<SchmReleLimit> findByCondition(SearchParams arg0)
			throws Exception {
		return null;
	}

	@Override
	public SchmReleLimit getSchmReleLimitByHosId(String hosId) {
		return this.dao.selectByHosId(hosId);
	}
}