package com.skynet.rimp.schmInfo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.schmInfo.service.ISchmTislInfoService;
import com.skynet.rimp.schmInfo.vo.SchmTislInfoEntity;
/**
 * <p>Title: 时段Service</p>
 * <p>Description: </p>
 *
 * @author wangshen
 * @version 1.00.00
 * @date 2015-7-1 上午11:53:21 
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Service
public class SchmTislInfoServiceImpl extends BaseServiceImpl<SchmTislInfoEntity> implements ISchmTislInfoService {

	@Override
	public List<SchmTislInfoEntity> findAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SchmTislInfoEntity> findByCondition(SearchParams arg0)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public SchmTislInfoEntity update(SchmTislInfoEntity arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int delete(String arg0) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public SchmTislInfoEntity save(SchmTislInfoEntity arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
