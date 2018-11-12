package com.skynet.rimp.schmInfo.dao;

import com.skynet.rimp.schmInfo.vo.SchmDetailInfo;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 
 * <p>Title: 医生排班明细dao</p>
 * <p>Description: 医生排班明细dao xml对应schmDetailInfoMapper.xml </p>
 *
 * @author Torlay
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
*
 */
@Repository
public interface SchmDetailInfoDao
{
    
    int deleteByPrimaryKey(String schmDetailId);

    int findByShiftId(String shiftId);
    
    int insert(SchmDetailInfo record);
    
    int insertSelective(SchmDetailInfo record);
    
    SchmDetailInfo selectByPrimaryKey(String schmDetailId);
    
    int updateByPrimaryKeySelective(SchmDetailInfo record);
    
    int updateByPrimaryKey(SchmDetailInfo record);
    
    public List<SchmDetailInfo> findAll();
    
    int deleteBySchmId(String schmId);

    int deleteBySchmIds(String[] schmId);
    
    int insertBatch(List<SchmDetailInfo> recordls);
    
    public List<SchmDetailInfo> findDetailInfoBySchmId(String schmId);
}
