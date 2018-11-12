package com.skynet.rimp.hisBaseInfo.service;

import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.hisBaseInfo.vo.HosDeptInfo;

import java.util.List;
import java.util.Map;

public interface IHosDeptInfoService extends BaseService<HosDeptInfo> {
	
    List<HosDeptInfo> findByParentId(String parentId);
  
    List<HosDeptInfo> findAllChildrenByParentId(String parentId);
    
    List<HosDeptInfo> findAllByParentId(String parentId);
    
    List<HosDeptInfo> listChildren(String parentId) throws Exception;
    
    List<Map<String, Object>> findListTreeExp() throws Exception;
    
    int insert(HosDeptInfo record);
    
    HosDeptInfo selectByPrimaryKey(String id);

    Integer selectMaxReleDayNum(String hosId);
    
    HosDeptInfo selectByHisCode(String hisCode);
    
    int updateByPrimaryKey(HosDeptInfo record);
    
    int deleteByPrimaryKey(String id);
    
    int deleteByKeyArr(String[] deptIdArr) throws Exception;
}
