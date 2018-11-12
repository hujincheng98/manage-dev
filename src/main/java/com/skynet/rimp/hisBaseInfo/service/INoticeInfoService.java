package com.skynet.rimp.hisBaseInfo.service;

import com.skynet.platform.common.service.BaseService;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;
import com.skynet.rimp.hisBaseInfo.vo.NoticeInfo;

/**
 * 
 * <p>Title: 医院通知公告管理service</p>
 * <p>Description: </p>
 *
 * @author liuletian	
 * @version 1.00.00 创建时间：2017-03-13 15:50:45
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 *
 */
public interface INoticeInfoService extends BaseService<NoticeInfo> {
	
	public void save(NoticeInfo noticeInfo);
	
	/**
	 * 新建的预约说明和停诊通知进行唯一性判断
	 * @param 
	 * @return
	 */
	public boolean findByNoticeTypeAndHosId(String noticetype, String hoscoding);
	
	/**
	 * 相同所属医院、通告类型的通告标题不能重复
	 * @param record
	 * @return
	 */
	public boolean findBynoticeNameAndHosId(String noticename, String hoscoding);
	
	/**
	 * 修改通告
	 * @param record
	 * @return
	 */
	public void update(NoticeInfo noticeInfo);
	
	/**
	 * 根据id得到公告
	 * @param record
	 * @return
	 */
	public NoticeInfo getNoticeInfoById(String id);
	
	/**
	 * 根据id删除公告
	 * @param record
	 * @return
	 */
	int deleteByKeyArr(String[] noticeIdArr) throws Exception;

}
