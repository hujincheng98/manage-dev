package com.skynet.rimp.hisBaseInfo.service.impl;

import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.hisBaseInfo.dao.HosDeptInfoDao;
import com.skynet.rimp.hisBaseInfo.dao.HosDocmInfoDao;
import com.skynet.rimp.hisBaseInfo.service.IHosDeptInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosDeptInfo;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author wangshen
 * @version 1.00.00
 * @date 2015-7-10 上午10:11:04 
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Service
public class HosDeptInfoServiceImpl extends BaseServiceImpl<HosDeptInfo> implements IHosDeptInfoService {

	@Autowired
	private HosDeptInfoDao deptDao;
	@Autowired
	private HosDocmInfoDao docmDao;

	@Override
	public List<HosDeptInfo> findAll() throws Exception {
		return deptDao.findAll();
	}
	
	@Override
	public List<HosDeptInfo> findByCondition(SearchParams params) throws Exception {
		return deptDao.findByCondition(params);
	}

	@Override
	public List<HosDeptInfo> findByParentId(String parentId) {
		List<HosDeptInfo> list = new ArrayList<HosDeptInfo>();
		list = deptDao.findByParentId(parentId);
		for (HosDeptInfo db : list) {
			db.setIsParent(this.deptDao.findCountByParentId(db.getDeptId()) > 0);
		}
		return list;
	}

	@Override
	public List<HosDeptInfo> findAllChildrenByParentId(String parentId) {
		List<HosDeptInfo> list = deptDao.findByParentId(parentId);
		for (HosDeptInfo db : list) {
			List<HosDeptInfo> children = findAllChildrenByParentId(db.getOrgId());
			if (children != null && children.size() > 0) {
				db.setIsParent(true);
				db.setChildren(children);
			} else {
				db.setIsParent(false);
			}
		}
		return list;
	}
	
	@Override
	public List<HosDeptInfo> findAllByParentId(String parentId) {
		List<HosDeptInfo> list = new ArrayList<HosDeptInfo>();
		SearchParams params = new SearchParams();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("parentDeptId", parentId);
		params.setSearchParams(searchParams);
		List<HosDeptInfo> parentList = this.deptDao.findByCondition(params);
		if (!parentList.isEmpty()) {
			list.addAll(parentList);
			for (HosDeptInfo _dept : parentList) {
				List<HosDeptInfo> listChildren = findAllByParentId(_dept.getDeptId());
				if (!listChildren.isEmpty()) {
					list.addAll(listChildren);
				}
			}
		}
		return list;
	}

	@Override
	public List<HosDeptInfo> listChildren(String parentId) throws Exception {
		if (StringUtils.isEmpty(parentId)) {
			parentId = "";
		}
		List<HosDeptInfo> list = deptDao.listChildren(parentId);
		for (HosDeptInfo _dept : list) {
			_dept.setIsParent(this.deptDao.getChildrenCount(_dept.getDeptId()) > 0);
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> findListTreeExp() throws Exception {
		List<Map<String, Object>> nodeList = new ArrayList<Map<String, Object>>();
		List<HosDeptInfo> list = this.findByParentId(null);
		for (HosDeptInfo dept : list) {
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("id", dept.getDeptId());
			node.put("text", dept.getDeptName());
			Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("remarks", dept.getRemarks());
            node.put("attributes", attributes);
            // 判断是否是父节点
            if (dept.isIsParent()) {
            	ListNodeRecursion(node, dept.getDeptId());
            }
            nodeList.add(node);
		}
		return nodeList;
	}
	
	/**
	 * 递归查询子节点
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> ListNodeRecursion(Map node, String pid) throws Exception {
		if (StringUtils.isEmpty(pid)) {
			pid = "";
		}
		List<HosDeptInfo> childrenDepts = this.listChildren(pid);
		List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
	    for (HosDeptInfo childrenDept : childrenDepts) {
			Map<String, Object> childNode = new HashMap<String, Object>();
			childNode.put("id", childrenDept.getDeptId());
			childNode.put("text", childrenDept.getDeptName());
			Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("remarks", childrenDept.getRemarks());
            node.put("attributes", attributes);
            // 判断是否是父节点
            if (childrenDept.isIsParent()) {
            	ListNodeRecursion(childNode, childrenDept.getDeptId());
            }
			children.add(childNode);
		}
	    node.put("state", "open");
        node.put("children", children);
        return children;
	}
	/**
	 * 新增
	 */
	@Override
	public int insert(HosDeptInfo record) {
		//设置主键ID
		record.setDeptId(UUIDGenerator.getUUID());
		//创建用户
        record.setCreateUser(UserUtil.getUserId());
        //创建时间
        record.setCreateDate(new Date());
        //建权编码
        record.setOrgId(UserUtil.getAuthCode());
        
        //验证当前科室是否存在
        SearchParams params = new SearchParams();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("deptName", record.getDeptName()); 
		if(!record.getParentDeptId().isEmpty()){
			searchParams.put("parentDeptId", record.getParentDeptId());
		}
		searchParams.put("hosId", record.getHosId());
		params.setRows(10);
		params.setPage(1);
		params.setSearchParams(searchParams);
        List<HosDeptInfo> deptList = deptDao.findExistByCondition(params);
        if(deptList.size()>0){
        	return -97;//名称重复
        }
		return this.deptDao.insert(record);
	}
	/**
	 * 根据主键ID查询
	 */
	@Override
	public HosDeptInfo selectByPrimaryKey(String id) {
		return this.deptDao.selectByPrimaryKey(id);
	}
	/**
	 * 查询所有科室最大放号天数
	 */
	@Override
	public Integer selectMaxReleDayNum(String hosId) {
		return this.deptDao.selectMaxReleDayNum(hosId);
	}



	/**
	 * 根据主键更新
	 */
	@Override
	public int updateByPrimaryKey(HosDeptInfo record) {
        record.setUpdateUser(UserUtil.getUserId());
        record.setUpdateDate(new Date());
		return this.deptDao.updateByPrimaryKey(record);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		int reslut = this.deptDao.findCountByParentId(id);
		if(reslut>0){
			return -99;//-99表示存在下级科室
		}
		SearchParams params = new SearchParams();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("deptId", id);
		params.setRows(10);
		params.setPage(1);
		params.setSearchParams(searchParams);
		List<HosDocmInfo> docmList =  docmDao.findByCondition(params);
		if(docmList.size()>0){
			return -98;//-98表示存在关联医生
		}
		return this.deptDao.deleteByPrimaryKey(id);
	}
	
	@Override
	public int deleteByKeyArr(String[] deptIdArr) throws Exception {
		for (String id : deptIdArr) {
			int reslut = this.deptDao.findCountByParentId(id);
			if(reslut>0){
				return -99;//-99表示存在下级科室
			}
			SearchParams params = new SearchParams();
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("deptId", id);
			params.setRows(10);
			params.setPage(1);
			params.setSearchParams(searchParams);
			List<HosDocmInfo> docmList =  docmDao.findByCondition(params);
			if(docmList.size()>0){
				return -98;//-98表示存在关联医生
			}
		}
		return deptDao.deleteByKeyArr(deptIdArr);
	}

	@Override
	public HosDeptInfo selectByHisCode(String hisCode) {
		return this.deptDao.selectByHisCode(hisCode);
	}

}
