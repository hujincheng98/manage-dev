package com.skynet.rimp.schmInfo.service;

import java.util.List;
import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.schmInfo.vo.SchmShiftInfoEntity;
import com.skynet.rimp.schmInfo.vo.SchmTislInfoEntity;

/**
 * <p>
 * Title: 排班Service
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author wangshen
 * @version 1.00.00
 * @date 2015-7-1 上午11:49:06
 * 
 *       <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
public interface ISchmShiftInfoService extends BaseService<SchmShiftInfoEntity> {
	
	public SchmShiftInfoEntity findById(String id);

	public int deleteTisl(String id);

	public List<SchmShiftInfoEntity> findByShiftName(String shiftName);

	public SchmShiftInfoEntity save(SchmShiftInfoEntity shift) throws Exception;

	public int delete(String shiftId) throws Exception;
	
	List<SchmTislInfoEntity> selectByShiftId(String shiftId);
}
