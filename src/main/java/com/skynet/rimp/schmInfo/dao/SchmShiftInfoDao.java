package com.skynet.rimp.schmInfo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.skynet.common.SearchParams;
import com.skynet.rimp.schmInfo.vo.SchmShiftInfoEntity;

/**
 * <p>
 * Title: 排班DAO
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author wangshen
 * @version 1.00.00
 * @date 2015-7-1 上午11:33:16
 * 
 *       <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Repository
public interface SchmShiftInfoDao {

	public List<SchmShiftInfoEntity> findByCondition(SearchParams params);

	public int insert(SchmShiftInfoEntity record);

	public List<SchmShiftInfoEntity> findAll();

	public int updateByPrimaryKeySelective(SchmShiftInfoEntity record);

	public int deleteByPrimaryKey(String shiftId);

	public SchmShiftInfoEntity selectByPrimaryKey(String id);

	public List<SchmShiftInfoEntity> findByDocmAndWeek(
			Map<String, Object> mapDocmIdWeek);

	public List<SchmShiftInfoEntity> findAllByauto();

	public List<SchmShiftInfoEntity> findByShiftName(String shiftName);

}
