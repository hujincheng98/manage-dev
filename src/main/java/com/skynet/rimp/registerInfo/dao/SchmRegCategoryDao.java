package com.skynet.rimp.registerInfo.dao;

import com.skynet.common.SearchParams;
import com.skynet.rimp.registerInfo.vo.SchmRegCategoryInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 王坡
 * @version 1.0
 * @createTime 2018/7/5 15:14:26
 */
@Repository
public interface SchmRegCategoryDao {


    List<SchmRegCategoryInfo> findByCondition(SearchParams params);

    List<SchmRegCategoryInfo> findAll();

    void insert(SchmRegCategoryInfo record);

    void deleteByPrimaryKey(String id);

    void update(SchmRegCategoryInfo record);
}

