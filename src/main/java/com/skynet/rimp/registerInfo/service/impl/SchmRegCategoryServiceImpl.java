package com.skynet.rimp.registerInfo.service.impl;


import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.registerInfo.dao.SchmRegCategoryDao;
import com.skynet.rimp.registerInfo.service.ISchmRegCategoryService;
import com.skynet.rimp.registerInfo.vo.SchmRegCategoryInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * @author LiYang
 * @version 1.00.00
 * @createTime 2018/7/5 15:15:33
 *
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * <pre>
 */

@Service
public class SchmRegCategoryServiceImpl extends BaseServiceImpl<SchmRegCategoryInfo> implements ISchmRegCategoryService {

    //实体对应dao
    @Autowired
    private SchmRegCategoryDao iSchmRegCategoryDao;

    //根据条件查询数据
    @Override
    public List<SchmRegCategoryInfo> findByCondition(SearchParams params) throws Exception {
         return this.iSchmRegCategoryDao.findByCondition(params);
    }

    //查询所有数据
    @Override
    public List<SchmRegCategoryInfo> findAll() throws Exception {
        return this.iSchmRegCategoryDao.findAll();
    }

    //保存数据
    @Override
    public void save(SchmRegCategoryInfo record) {
        record.setId(UUIDGenerator.getUUID());
        record.setOrgId(UserUtil.getAuthCode());

        this.iSchmRegCategoryDao.insert(record);
    }

    //删除数据
    @Override
    public void delete(String id) {
        iSchmRegCategoryDao.deleteByPrimaryKey(id);
    }

    //修改数据
    @Override
    public void update(SchmRegCategoryInfo record) {
        this.iSchmRegCategoryDao.update(record);

    }
}
