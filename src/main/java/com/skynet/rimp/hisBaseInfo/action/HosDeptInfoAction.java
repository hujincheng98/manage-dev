package com.skynet.rimp.hisBaseInfo.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.common.utils.CommonUtil;
import com.skynet.rimp.hisBaseInfo.service.IHosDeptInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosDeptInfo;
import com.skynet.rimp.schmInfo.service.ISchmBaseMainInfoService;
import com.skynet.rimp.schmInfo.service.ISchmMainInfoService;
import com.skynet.rimp.schmInfo.vo.SchmBaseMainInfoEntity;
import com.skynet.rimp.schmInfo.vo.SchmMainInfo;

@Controller
@RequestMapping(value = "/rimp/dept/")
public class HosDeptInfoAction {

	@Autowired
	private IHosDeptInfoService deptService;
	
	@Autowired
	private ISchmBaseMainInfoService schmBaseMainInfoService;
	
	@Autowired
	private ISchmMainInfoService schmMainInfoService;

	/**
	 * 科室列表首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "hisBaseInfo/dept_Index";
	}
	/**
	 * 跳转到新增页面
	 * @return
	 */
	@RequestMapping(value = "dept_add.do", method = RequestMethod.GET)
	public String add(){
		return "hisBaseInfo/dept_add";
	}
	/**
	 * 跳转到修改页面
	 * @return
	 */
	@RequestMapping(value = "dept_edit.do", method = RequestMethod.GET)
	public String edit(){
		return "hisBaseInfo/dept_edit";
	}
	/**
	 * 跳转到明细页面
	 * @return
	 */
	@RequestMapping(value = "dept_detail.do", method = RequestMethod.GET)
	public String detail(){
		return "hisBaseInfo/dept_detail";
	}
	
	/**
	 * 保存方法
	 * @param dept
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HosDeptInfo dept){
		Map<String, Object> model = new HashMap<String, Object>();
		int result = this.deptService.insert(dept);
		if(result>0){
			model.put("state", "0");
			model.put("info", "保存成功");
		}else{
			if(result==-97){
				model.put("state", "-97");
				model.put("info", "科室名称重复");
			}else{
				model.put("state", "-1");
				model.put("info", "保存失败");
			}
		}
		return model;
	}
	
	/**
	 * 更新方法
	 * @param dept
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(HosDeptInfo dept){
		Map<String, Object> model = new HashMap<String, Object>();
		//设置编号
		int result = this.deptService.updateByPrimaryKey(dept);
		if(result>0){
			model.put("state", "0");
			model.put("info", "保存成功");
		}else{
			model.put("state", "-1");
			model.put("info", "保存失败");
		}
		return model;
	}
	
	/**
	 * 更新方法
	 * @param dept
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateState.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(String[] ids, String deptState){
		Map<String, Object> map = new HashMap<String, Object>();
		boolean boo = false; // 标识位默认false；
		for (String deptId : ids) {
			HosDeptInfo deptInfo = new HosDeptInfo();
			deptInfo.setDeptId(deptId);
			deptInfo.setDeptState(deptState);
			int result = this.deptService.updateByPrimaryKey(deptInfo);
			if (result < 0) {
				boo = true;
			}
		}
		//设置编号
		if(!boo){
			map.put("state", "0");
			map.put("info", "保存成功");
		}else{
			map.put("state", "-1");
			map.put("info", "保存失败");
		}
		return map;
	}
	
	/**
	 * 删除方法
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "delete.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String[] ids){
		Map<String, Object> map = new HashMap<String, Object>();
		//设置编号
		try {
			boolean boo = false; // 标识位默认false
			for (String deptId : ids) {
				SearchParams params = new SearchParams();
				Map<String, Object> searchParams = new HashMap<String, Object>();
				searchParams.put("deptId", deptId);
				params.setSearchParams(searchParams);
				List<SchmBaseMainInfoEntity> schmBaseMainList = this.schmBaseMainInfoService.findByConditionBydel(params);
				if (schmBaseMainList != null && schmBaseMainList.size() > 0) {
					boo = true;
					break;
				}
				List<SchmMainInfo> schmMainList = this.schmMainInfoService.findByConditionBydel(params);
				if (schmMainList != null && schmMainList.size() > 0) {
					boo = true;
					break;
				}
			}
			if (!boo) {
				int result = this.deptService.deleteByKeyArr(ids);
				map.put("state", result);
				map.put("message", "删除成功！");
			} else {
				map.put("state", "0");
				map.put("message", "该科室已生成排班数据，不能删除！");
			}
		} catch (Exception e) {
			map.put("state", "-1");
			map.put("message", "删除失败！");
		}
		return map;
	}
	/**
	 * 列表数据
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<HosDeptInfo> list(SearchParams params) {
		Pagination<HosDeptInfo> pageList = null;
		try {
			if(params.getSearchParams()!=null&&params.getSearchParams().get("deptName")!=null){
				String m_temp = params.getSearchParams().get("deptName").toString();
				if(!m_temp.equals("")){
					char[] charStr = m_temp.toString().toCharArray();
					if(CommonUtil.containStr(charStr)){
						params.getSearchParams().put("deptName", "");
						params.getSearchParams().put("deptPinyCode",m_temp);
					}
				}
			}
			pageList = this.deptService.findPageByCondition(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}

	@RequestMapping(value = "listChildren.json", method = RequestMethod.POST)
	@ResponseBody
	public List<HosDeptInfo> listChildren(String parentId) {
		try {
			return this.deptService.findAllByParentId(parentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * TREE
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("listRoot.json")
	public List<HosDeptInfo> listRoot(String id) throws Exception {
		List<HosDeptInfo> list = deptService.findByParentId(id);
		return list;
	}
	
	@ResponseBody
    @RequestMapping("list_deptTree.json")
    public List<Map<String, Object>> list_deptTree(String id) throws Exception
    {
        List<HosDeptInfo> list = this.deptService.findByParentId(id);
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (HosDeptInfo dept : list)
        {
            Map<String, Object> node = new HashMap<String, Object>();
            node.put("id", dept.getDeptId());
            node.put("text", dept.getDeptName());
            node.put("hosId", dept.getHosId());
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("remarks", dept.getRemarks());
            node.put("attributes", attributes);
            if (dept.isIsParent())
            {
                node.put("state", "closed");
            }
            returnList.add(node);
        }
        return returnList;
    }
	
	@ResponseBody
    @RequestMapping("list_deptTreeExpAll.json")
    public List<Map<String, Object>> list_deptTreeAll(String id) throws Exception
    {
		return this.deptService.findListTreeExp();
    }
	
}
