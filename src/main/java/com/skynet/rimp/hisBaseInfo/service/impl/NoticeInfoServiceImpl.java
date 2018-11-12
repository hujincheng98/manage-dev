package com.skynet.rimp.hisBaseInfo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.string.StringUtil;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.hisBaseInfo.dao.HosInfoDao;
import com.skynet.rimp.hisBaseInfo.dao.NoticeInfoDao;
import com.skynet.rimp.hisBaseInfo.service.INoticeInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;
import com.skynet.rimp.hisBaseInfo.vo.NoticeInfo;


/**
 * 
 * <p>Title: 医院通知公告管理impl</p>
 * <p>Description: service 层所有业务判断逻辑通过ServiceRunException 抛出异常，通过枚举获取异常信息，并在restful层进行捕捉返回</p>
 *
 * @author liuletian
 * @version 1.00.00 创建时间：2017-03-13 15:50:45
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 *
 */

@Service
public class NoticeInfoServiceImpl extends BaseServiceImpl<NoticeInfo> implements INoticeInfoService {

	private Logger logger = Logger.getLogger(NoticeInfoServiceImpl.class);
	
	@Autowired
	private NoticeInfoDao noticeInfoDao;
	
	@Autowired
	private HosInfoDao hosInfoDao;

	@Override
	public List<NoticeInfo> findByCondition(SearchParams params)
			throws Exception {
		noticeInfoDao.findByCondition(params);
		return null;
	}

	@Override
	public List<NoticeInfo> findAll() throws Exception {
		return noticeInfoDao.findAll();
	}

	//保存通告
	@Override
	public void save(NoticeInfo noticeInfo) {
		noticeInfo.setOfflinedate(makeOfflineDate(noticeInfo.getOfflinedate()));
        noticeInfo.setId(UUIDGenerator.getUUID());
		String hosId = noticeInfo.getAffiliatedhos();
		if(!hosId.equals("")){
			//根据医院id得到对于医院信息
			HosInfo hosInfo = hosInfoDao.findByhosId(hosId);
			String hosName = hosInfo.getHosName();
			//所属医院
			noticeInfo.setAffiliatedhos(hosName);
			//所属医院编码
			noticeInfo.setHoscoding(hosId);			
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//创建时间 
		String createdate = sdf.format(new Date());
		noticeInfo.setCreatedate(createdate);
		//创建人id
		noticeInfo.setCreateuser(UserUtil.getUserId());
		//发布时间
		noticeInfo.setReleasedate(new Date());
		
		try {
			this.noticeInfoDao.insert(noticeInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 新建的预约说明和停诊通知进行唯一性判断
	 * 预约说明-noticetype_01,停诊通知-noticetype_02,排班信息-noticetype_03,公告发布-noticetype_04
	 * @param 
	 * @return
	 */
	@Override
	public boolean findByNoticeTypeAndHosId(String noticetype, String hoscoding) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("noticetype", noticetype);
		params.put("hoscoding", hoscoding);
		int number = noticeInfoDao.findByNoticeTypeAndHosId(params);
		if(number != 0){ //预约说明或者停诊通知已存在
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 相同所属医院、通告类型的通告标题不能重复
	 * @param record
	 * @return
	 */
	@Override
	public boolean findBynoticeNameAndHosId(String noticename, String hoscoding) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("noticename", noticename);
		params.put("hoscoding", hoscoding);
		int number = noticeInfoDao.findBynoticeNameAndHosId(params);
		if(number != 0){ //同一医院下的通告名称同名
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 修改通告
	 * @param record
	 * @return
	 */
	@Override
	public void update(NoticeInfo noticeInfo) {
		noticeInfo.setOfflinedate(makeOfflineDate(noticeInfo.getOfflinedate()));
		String hosName = "";
		String affiliatedhos = noticeInfo.getAffiliatedhos();
		boolean isChinese = StringUtil.isChinese(affiliatedhos);
		String hosId = "";
		if(isChinese){ //是医院名称
			hosName = affiliatedhos;
			List<HosInfo> hosList = hosInfoDao.findByHosName(hosName);
			if(hosList.size()>0){
				HosInfo hosI = hosList.get(0);
				hosId = hosI.getHosId();
			}
			
    			
		}else{ //是医院id
			hosId = affiliatedhos;
			HosInfo hosInfo = hosInfoDao.findByhosId(hosId);
			hosName = hosInfo.getHosName();
			
		}
			//所属医院
			noticeInfo.setAffiliatedhos(hosName);
			//所属医院编码
			noticeInfo.setHoscoding(hosId);			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//创建时间 
		String updatedate = sdf.format(new Date());
		noticeInfo.setUpdatedate(updatedate);
		//修改人id
		noticeInfo.setUpdateuser(UserUtil.getUserId());
		try {
			this.noticeInfoDao.updateByPrimaryKey(noticeInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据id得到公告
	 * @param record
	 * @return
	 */
	@Override
	public NoticeInfo getNoticeInfoById(String id) {
		
		return noticeInfoDao.findByPrimaryKey(id);
	}

	/**
	 * 根据id删除公告
	 * @param record
	 * @return
	 */
	@Override
	public int deleteByKeyArr(String[] noticeIdArr) throws Exception {
		return noticeInfoDao.deleteByKeyArr(noticeIdArr);
	}
	/**
	 * 处理下线时间格式
	 * @param record
	 * @return
	 */
	public Date makeOfflineDate(Date offlinedate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String newOfflineDateStr = sdf.format(offlinedate)+" 23:59:59";
		Date newOfflineDate = null;
		try {
			newOfflineDate = sdf1.parse(newOfflineDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newOfflineDate;
	}
	
	
}
