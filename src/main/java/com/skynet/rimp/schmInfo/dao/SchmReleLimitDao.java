package com.skynet.rimp.schmInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.skynet.rimp.schmInfo.vo.SchmReleLimit;

/***
 * 
 * <p>Title: 放号管理dao</p>
 * <p>Description: </p>
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
public interface SchmReleLimitDao
{
    public List<SchmReleLimit> findAll();
    
    int deleteByPrimaryKey(String releId);
    
    int insert(SchmReleLimit record);
    
    int insertSelective(SchmReleLimit record);
    
    SchmReleLimit selectByPrimaryKey(String releId);
    
    SchmReleLimit selectByHosId(String hosId);
    
    int updateByPrimaryKeySelective(SchmReleLimit record);
    
    int updateByPrimaryKey(SchmReleLimit record);
}