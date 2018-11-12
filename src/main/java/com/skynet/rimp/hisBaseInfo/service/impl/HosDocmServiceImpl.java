package com.skynet.rimp.hisBaseInfo.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.hisBaseInfo.dao.HosDocmInfoDao;
import com.skynet.rimp.hisBaseInfo.service.IHosDocmService;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;

/**
 * 
 * <p>Title: 医生管理service实现</p>
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
@Service
public class HosDocmServiceImpl extends BaseServiceImpl<HosDocmInfo> implements IHosDocmService {

	
	@Autowired
	private HosDocmInfoDao docmDao;
	
	@Override
	public List<HosDocmInfo> findAll() throws Exception {
		// TODO Auto-generated method stub
		return docmDao.findAll();
	}

	@Override
	public List<HosDocmInfo> findByCondition(SearchParams params)
			throws Exception {
		return docmDao.findByCondition(params);
	}

	/**
	 * 更新医生状态
	 */
	@Override
	public int updateState(HosDocmInfo record) throws Exception {
		HosDocmInfo vo = docmDao.selectByPrimaryKey(record.getDocmId());
		vo.setDocmState(record.getDocmState());
		vo.setUpdateUser(UserUtil.getUserId());
		vo.setUpdateDate(new Date());
		return docmDao.updateByPrimaryKey(vo);

	}
	
	/**
	 * 删除医生
	 */
	@Override
	public int deleteByDocmId(String docmId) throws Exception {
		return  docmDao.deleteByPrimaryKey(docmId);
	}
	
	/**
	 * 医生信息保存
	 * @param hosDocmInfo
	 */
	@Override
	public void save(HosDocmInfo hosDocmInfo) {
		hosDocmInfo.setDocmId(UUIDGenerator.getUUID());
		hosDocmInfo.setOrgId(UserUtil.getAuthCode());
		hosDocmInfo.setCreateDate(new Date());
		hosDocmInfo.setCreateUser(UserUtil.getUserId());
		
		this.docmDao.insert(hosDocmInfo);
	}
	
	/**
	 * 医生信息修改
	 * @param hosDocmInfo
	 */
	@Override
	public void update(HosDocmInfo hosDocmInfo) {
		hosDocmInfo.setUpdateDate(new Date());
		hosDocmInfo.setUpdateUser(UserUtil.getUserId());
		docmDao.updateByPrimaryKey(hosDocmInfo);
		
	}
	
	@Override
	public HosDocmInfo selectByPrimaryKey(String docmId) {
		// TODO Auto-generated method stub
		return this.docmDao.selectByPrimaryKey(docmId);
	}
	
	@Override
	public int deleteByKeyArr(String[] docmIdArr) throws Exception {
		return docmDao.deleteByKeyArr(docmIdArr);
	}


}
