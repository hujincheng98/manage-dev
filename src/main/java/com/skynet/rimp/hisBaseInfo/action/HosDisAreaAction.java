package com.skynet.rimp.hisBaseInfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.hisBaseInfo.service.IHosDisInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosDisAreaInfo;

/**
 * <p>
 * Title: 医院病区信息管理
 * </p>
 * <p>
 * Description:
 * </p>
 *
 * @author huyang
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Controller
@RequestMapping(value = "rimp/hosDis")
public class HosDisAreaAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(HosDisAreaAction.class);

	@Autowired
	private IHosDisInfoService hosDisInfoService;

	/**
	 * 跳转到医院病区首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "hisBaseInfo/hos_dis_index";
	}

	/**
	 * 跳转到病区新增页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "hosDis_add.do", method = RequestMethod.GET)
	public String hos_add() {
		return "hisBaseInfo/hos_dis_add";
	}

	/**
	 * 跳转到病区修改页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "hosDis_edit.do", method = RequestMethod.GET)
	public String hos_edit() {
		return "hisBaseInfo/hos_dis_edit";
	}

	/**
	 * 返回列表数据
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<HosDisAreaInfo> list(SearchParams params) {
//		if (params != null) {
//			System.err.println(params.getSearchParams());
//		}
		Pagination<HosDisAreaInfo> pageList = null;
		try {
			pageList = this.hosDisInfoService.findPageByCondition(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}

	/**
	 * 返回列表数据
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "listAll.json", method = RequestMethod.POST)
	@ResponseBody
	public List<HosDisAreaInfo> listAll() {
		List<HosDisAreaInfo> pageList = null;
		try {
			pageList = this.hosDisInfoService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}

	/**
	 * 保存病区信息
	 * 
	 * @param hosDisAreaInfo
	 * @return
	 */
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HosDisAreaInfo hosDisAreaInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		int statusCode = this.hosDisInfoService.save(hosDisAreaInfo);
		switch (statusCode) {
		case 0:
			map.put("state", "fail");
			map.put("message", "禁止访问");
			break;
		case 1:
			map.put("state", "success");
			map.put("message", "保存成功");
			break;
		case 2:
			map.put("state", "fail");
			map.put("message", "该病区名称已经存在");
			break;
		}
		return map;
	}

	/**
	 * 更新病区信息
	 * 
	 * @param hosDisAreaInfo
	 * @return
	 */
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(HosDisAreaInfo hosDisAreaInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		int statusCode = this.hosDisInfoService.update(hosDisAreaInfo);
		switch (statusCode) {
		case 0:
			map.put("state", "fail");
			map.put("message", "禁止访问");
			break;
		case 1:
			map.put("state", "success");
			map.put("message", "修改成功");
			break;
		case 2:
			map.put("state", "fail");
			map.put("message", "该病区名称已存在");
			break;
		}
		return map;
	}
	/**
	 * 校验病区名称是否重复
	 * @return
	 */
	@RequestMapping(value = "checkDisName.json", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkDisName(@RequestParam("param") String param){
		if(StringUtils.isNotEmpty(param)){
			param = param.replace(" ", "");
		}
		HosDisAreaInfo p = this.hosDisInfoService.getByDisName(param);
		if(p!=null){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 删除病区信息
	 * 
	 * @param disId
	 * @return
	 */
	@RequestMapping(value = "delete.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("ids[]") List<String> ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer("");
		for (String disId : ids) {
			try {
				if (hosDisInfoService.delete(disId) == 0) {
					HosDisAreaInfo hosDisAreaInfo = hosDisInfoService
							.getByDisId(disId);
					if (hosDisAreaInfo != null) {
						sb.append("[" + hosDisAreaInfo.getDisName() + "]  ");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isEmpty(sb.toString())){
			map.put("success", false);
			map.put("msg", sb.toString() + "已经被使用，不能被删除");
		}else{
			map.put("success", true);
			map.put("msg", "删除完成");
		}
		return map;
	}

}
