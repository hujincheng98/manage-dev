package com.skynet.rimp.registerInfo.service;

import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.registerInfo.vo.SchmRegCategoryInfo;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * @author wangzhihua
 * @version 1.00.00
 * @createTime 2018/7/5 15:14:55
 *
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * <pre>
 */
public interface ISchmRegCategoryService extends BaseService<SchmRegCategoryInfo> {

    /**
     * 新增 数据
     * @param SchmRegCategory
     */
    public void save(SchmRegCategoryInfo record) throws Exception;

    /**
     * 删除 数据
     * @param SchmRegCategory
     */
    public void delete(String id) throws Exception;

    /**
     * 更新 数据
     * @param SchmRegCategory
     */
    public void update(SchmRegCategoryInfo record) throws Exception;


}
