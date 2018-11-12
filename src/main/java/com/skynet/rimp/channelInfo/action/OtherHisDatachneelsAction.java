package com.skynet.rimp.channelInfo.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.channelInfo.service.IOtherHisDatachneelsService;
import com.skynet.rimp.channelInfo.vo.OtherHisDatachneelsInfo;

/**
 * <p>Title: 数据服务同步</p>
 * <p>Description: </p>
 *
 * @author Liujian
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Controller
@RequestMapping("/rimp/otherHisDatachneels")
public class OtherHisDatachneelsAction {

	private static final Logger logger = Logger.getLogger(OtherHisDatachneelsAction.class);

	@Autowired
	private IOtherHisDatachneelsService otherHisDatachneelsService;
	
	/**
	 * 跳转到数据服务首页
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "channelInfo/other_hisDatachneels_index";
	}
	
	/**
	 * 跳转到数据服务新增页面
	 * @return
	 */
	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	public String add() {
		return "channelInfo/other_hisDatachneels_add";
	}
	
	/**
	 * 跳转到数据服务修改页面
	 * @return
	 */
	@RequestMapping(value = "edit.do", method = RequestMethod.GET)
	public String edit() {
		return "channelInfo/other_hisDatachneels_edit";
	}
	
	/**
	 * 返回列表json数据
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<OtherHisDatachneelsInfo> list(SearchParams params) {
		Pagination<OtherHisDatachneelsInfo> pageList = null;
		try {
			pageList = this.otherHisDatachneelsService.findPageByCondition(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}
	
	/**
	 * 服务保存
	 * @return
	 */
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(@RequestBody OtherHisDatachneelsInfo record) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.otherHisDatachneelsService.save(record);
			map.put("state", "success");
			map.put("message", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 服务更新
	 * @return
	 */
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(@RequestBody OtherHisDatachneelsInfo record) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.otherHisDatachneelsService.update(record);
			map.put("state", "success");
			map.put("message", "更新成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "更新失败");
		}
		return map;
	}
	
	/**
	 * 服务删除
	 * @return
	 */
	@RequestMapping(value = "delete.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.otherHisDatachneelsService.delete(id);
			map.put("state", "success");
			map.put("message", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "删除失败");
		}
		return map;
	}
	
	/**
	 * 同步科室
	 * @return
	 */
	@RequestMapping(value = "syncDept.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncDept(Integer id) {
		return this.otherHisDatachneelsService.syncDept(id);
	}
	
	/**
	 * 同步医生
	 * @return
	 */
	@RequestMapping(value = "syncDocm.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncDocm(Integer id) {
		return this.otherHisDatachneelsService.syncDocm(id);
	}
	
}
