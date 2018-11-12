package com.skynet.rimp.schmInfo.dao;

import com.skynet.common.SearchParams;
import com.skynet.rimp.schmInfo.vo.SchmMainInfo;
import com.skynet.rimp.schmInfo.vo.SchmMainInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 
 * <p>Title: 医生排班主表信息dao</p>
 * <p>Description: 医生排班主表信息dao xml对应schmDetailInfoMapper.xml</p>
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
public interface SchmMainInfoDao
{
    /**
     * 根据主键删除信息
     * @param schmId
     * @return
     */
    int deleteByPrimaryKey(String schmId);
    /**
     * 根据主键删除信息
     * @param schmId
     * @return
     */
    int deleteBySchmIds(String[] schmId);

    /**
     * 根据班次查询信息
     * @param shiftId
     * @return
     */
    int findByShiftId(String shiftId);
    
    /**
     * 根据排班日期查询
     * @param record
     * @return
     */
    public List<String> findByConditionByDate(SchmMainInfo record);
    
    /**
     * 批量插入
     * @param recordls
     * @return
     */
    int insertBatch(List<SchmMainInfo> recordls);
    
    /**
     * 单记录插入
     * @param record
     * @return
     */
    int insert(SchmMainInfo record);
    
    /**
     * 单记录插入
     * @param record
     * @return
     */
    int insertSelective(SchmMainInfo record);
    
    /**
     * 关联科室查询相关排班信息
     * @param schmId
     * @return
     */
    SchmMainInfo selectByPrimaryKey(String schmId);
    
    /**
     * 根据主键更新信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(SchmMainInfo record);
    
    /**
     * 根据主键更新信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(SchmMainInfo record);
    
    /**
     * 查询所有信息
     * @return
     */
    public List<SchmMainInfo> findAll();
    
    /**
     * 分页参数查询数据
     * @param params
     * @return
     */
    public List<SchmMainInfo> findByCondition(SearchParams params);
    
    /**
     * 查询排班日期内存在的排版
     * @param vo
     * @return
     */
    int findByShiftIdByDocmDate(SchmMainInfoVo vo);
    
    /**
     * 定时任务排班信息回滚
     * @param record
     * @return
     */
    int updateTaskSchm(SchmMainInfo record);
    
    /**
     * 医生id查询，排班
     */
    public List<SchmMainInfo> findByDocmId(String docmId);
    
    /**
     * 根据排班科室id、医生id查询是否存在排班信息
     * @param params
     * @return
     */
    public List<SchmMainInfo> findByConditionBydel(SearchParams params);


    public List<SchmMainInfo> findByRegCategoryId(String ext1);
    
}
