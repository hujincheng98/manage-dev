package com.skynet.rimp.hisBaseInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.skynet.common.SearchParams;
import com.skynet.rimp.hisBaseInfo.vo.HisDeptInfo;
import com.skynet.rimp.hisBaseInfo.vo.HisDeptInfoKey;

@Repository
public interface HisDeptInfoDao {
    int deleteByPrimaryKey(HisDeptInfoKey key);

    int insert(HisDeptInfo record);

    int insertSelective(HisDeptInfo record);

    HisDeptInfo selectByPrimaryKey(HisDeptInfoKey key);

    int updateByPrimaryKeySelective(HisDeptInfo record);

    int updateByPrimaryKey(HisDeptInfo record);

	List<HisDeptInfo> findByCondition(SearchParams params);

	List<HisDeptInfo> findAll();
}