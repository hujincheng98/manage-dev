package com.skynet.rimp.schmInfo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.skynet.rimp.schmInfo.vo.SchmTislInfoEntity;

/**
 * <p>
 * Title: 班次DAO
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author wangshen
 * @version 1.00.00
 * @date 2015-7-1 上午11:32:45
 * 
 *       <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Repository
public interface SchmTislInfoDao {
	
	int deleteByPrimaryKey(String tislId);

	int deleteByShiftId(String shiftId);

	int insert(SchmTislInfoEntity record);

	int insertSelective(SchmTislInfoEntity record);

	SchmTislInfoEntity selectByPrimaryKey(String tislId);

	int updateByPrimaryKeySelective(SchmTislInfoEntity record);

	int updateByPrimaryKey(SchmTislInfoEntity record);
	
	List<SchmTislInfoEntity> selectByShiftId(String shiftId);
}