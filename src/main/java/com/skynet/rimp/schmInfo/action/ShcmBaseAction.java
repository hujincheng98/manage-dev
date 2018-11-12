package com.skynet.rimp.schmInfo.action;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.schmInfo.service.ISchmBaseMainInfoService;
import com.skynet.rimp.schmInfo.vo.SchmBaseMainInfoEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: 基础排班(排班模板)</p>
 * <p>Description: 包括排班模板的主页跳转、</p>
 *
 * @author Liujian
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Controller
@RequestMapping(value = "/rimp/schmbase/")
public class ShcmBaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ShcmBaseAction.class);
	
	@Autowired
	private ISchmBaseMainInfoService schmBaseMainInfoService;

	/**
	 * 跳转到排班模板首页面
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "schmInfo/schmbase_index";
	}
	
	/**
	 * 排班信息列表json
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
    @ResponseBody
    public Pagination<SchmBaseMainInfoEntity> list(SearchParams params)
    {
        Pagination<SchmBaseMainInfoEntity> pageList = null;
        try
        {
            pageList = this.schmBaseMainInfoService.findPageByCondition(params);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return pageList;
    }
	
	/**
	 * 新增基础排班
	 * @param schmMainInfovo
	 * @return
	 */
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(@RequestBody SchmBaseMainInfoEntity basemain) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (schmBaseMainInfoService.checkDocmShiftTime(basemain)) {
				this.schmBaseMainInfoService.save(basemain);
				map.put("schmId", basemain.getSchmId());
				map.put("state", "success");
				map.put("message", "保存成功");
			} else {
				map.put("state", "error");
				map.put("message", "该医生" + basemain.getSchmWeek() + "已有排班");
			}
		} catch (Exception e) {
			map.put("state", "error");
			map.put("message", "保存失败");
			logger.error(e.getMessage());
		}
		return map;
	}

	/**
	 * 更新基础排班
	 * @param basemain
	 * @return
	 */
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@RequestBody SchmBaseMainInfoEntity basemain)
    {
		Map<String, Object> map = new HashMap<String, Object>();
		if(schmBaseMainInfoService.checkDocmShiftTime(basemain)){
			try
	        {
	        	this.schmBaseMainInfoService.update(basemain);
	        	map.put("state", "success");
	        	map.put("message", "更新成功");
	        }
	        catch (Exception e)
	        {
	        	map.put("state", "error");
	        	map.put("message", "更新失败");
	            logger.error(e.getMessage());
	        }
		}else{
			map.put("state", "error");
			map.put("message", "该医生" + basemain.getSchmWeek() + "已有排班");
		}
        return map;
    }
	/**
	 * 删除基础排班
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delete.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String[] schmIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (schmIds == null || schmIds.length == 0) {
			map.put("state", "error");
		} else {
			try {
				this.schmBaseMainInfoService.deleteByKeyArr(schmIds);
				map.put("state", "success");
				map.put("message", "删除成功");
			} catch (Exception e) {
				map.put("state", "error");
				map.put("message", "删除失败");
				logger.error(e.getMessage());
			}
		}
		return map;
	}

	/**
	 * 导出excel
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "exportExcel.do", method = RequestMethod.GET)
	public String exportExcel(SearchParams params, HttpServletRequest request, HttpServletResponse response) {
		try {
			this.schmBaseMainInfoService.getExportExcel(request, response,
					params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
