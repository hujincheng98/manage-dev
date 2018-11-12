package com.skynet.rimp.hisBaseInfo.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.skynet.common.SearchParams;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;

/**
 * 
 * <p>Title: 医生信息管理dao</p>
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
public interface HosDocmInfoDao
{
	/**
	 * 条件分页查询
	 * @param params
	 * @return
	 */
	List<HosDocmInfo> findByCondition(SearchParams params);
	
	/**
	 * 全部查询
	 * @return
	 */
	List<HosDocmInfo> findAll();
	
	/**
	 * 根据主键查询医生信息
	 * @param docmId
	 * @return
	 */
	HosDocmInfo selectByPrimaryKey(String docmId);
	
	 /**
     * 根据主键更新信息
     * @param record
     * @return
     */
	int updateByPrimaryKey(HosDocmInfo record);
	
	/**
	 * 根据主键更新状态信息
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeytoState(HosDocmInfo record);
	
	/**
	 * 根据主键删除医生信息
	 * @param docmId
	 * @return
	 */
	int deleteByPrimaryKey(String docmId);
	
	/**
	 * 保存
	 * @param record
	 * @return
	 */
	int insert(HosDocmInfo record);
	
	/**
	 * 根据医生id批量删除
	 * @param docmIdArr
	 * @return
	 */
	public int deleteByKeyArr(String[] docmIdArr);
	
}