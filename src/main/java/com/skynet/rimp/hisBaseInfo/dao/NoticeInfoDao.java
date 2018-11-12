package com.skynet.rimp.hisBaseInfo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.skynet.common.SearchParams;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;
import com.skynet.rimp.hisBaseInfo.vo.NoticeInfo;


/**
 * 
 * <p>Title: info管理mapper</p>
 * <p>Description: </p>
 *
 * @author fengyuan
 * @version 1.00.00 创建时间：2017-03-13 15:08:45
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 *
 */
@Repository
public interface NoticeInfoDao {
	
	/**
	 * 条件分页查询
	 * @param params
	 * @return
	 */
	public List<NoticeInfo> findByCondition(SearchParams params);
		
	/**
	 * 全部查询
	 * @return
	 */
	public List<NoticeInfo> findAll();
	
	/**
	 * 保存
	 * @param record
	 * @return
	 */
	int insert(NoticeInfo noticeInfo);
	
	/**
	 * 新建的预约说明和停诊通知进行唯一性判断
	 * @param record
	 * @return
	 */
    int findByNoticeTypeAndHosId(Map<String, Object> params);
    
    /**
	 * 相同所属医院、通告类型的通告标题不能重复
	 * @param record
	 * @return
	 */
    int findBynoticeNameAndHosId(Map<String, Object> params);
    
    /**
	 * 根据主键更新状态信息
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(NoticeInfo noticeInfo);
	
	/**
	 * 根据id得到NoticeInfo
	 * @param record
	 * @return
	 */
	NoticeInfo findByPrimaryKey(String id);
	
	/**
	 * 根据id删除NoticeInfo
	 * @param record
	 * @return
	 */
	public int deleteByKeyArr(String[] noticeIdArr);
	
}