package com.skynet.rimp.hisBaseInfo.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.common.utils.file.ImageConverter;
import com.skynet.rimp.hisBaseInfo.service.IHosAreaInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosAreaInfo;

/**
 * 
 * @author huyang
 *
 */
@Controller
@RequestMapping(value = "/rimp/hosarea/")
public class HosAreaAction {
	
	private static final Logger logger = Logger.getLogger(HosAreaAction.class);
	
	@Autowired
	private IHosAreaInfoService service;

	@InitBinder
	protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	/**
	 * 跳转到院区首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "hisBaseInfo/hosarea_index";
	}
	
	/**
	 * 跳转到院区图片上传页面
	 * @return
	 */
	@RequestMapping(value = "upload.do", method = RequestMethod.GET)
	public String upload() {
		return "hisBaseInfo/hosarea_upload";
	}
	
	@RequestMapping(value = "listAll.json", method = RequestMethod.POST)
	@ResponseBody
	public List<HosAreaInfo> listAll() {
		List<HosAreaInfo> pageList = null;
		try {
			pageList = service.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}
	
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<HosAreaInfo> list(SearchParams params) {
		Pagination<HosAreaInfo> pageList = null;
		try {
			pageList = service.findPageByCondition(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}

	@RequestMapping(value = "save.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HosAreaInfo hosAreaInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			switch (service.save(hosAreaInfo)) {
			case 0:
				map.put("success", false);
				map.put("msg", "参数不能为空");
				break;
			case 1:
				map.put("success", true);
				map.put("msg", "保存成功");
				break;
			case 2:
				map.put("success", true);
				map.put("msg", "修改成功");
				break;
			case 3:
				map.put("success", false);
				map.put("msg", "该院区名称已经存在");
				break;
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "系统出错");
			logger.error(e.getMessage());
		}
		return map;
	}
	
	/**
	 * 上传图片
	 * @param hosAreaInfo
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "upload.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HosAreaInfo hosAreaInfo, @RequestParam MultipartFile file) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			hosAreaInfo.setHosAreaPhoto(ImageConverter.toBase64(file.getInputStream(), 1000, 1000));
			this.service.update(hosAreaInfo);
			map.put("success", true);
			map.put("msg", "上传成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("success", false);
			map.put("msg", "上传失败");
		}
		return map;
	}
	
	/**
	 * 校验院区名称是否存在
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "checkAreaName.json", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkAreaName(@RequestParam("param") String param){
		if(StringUtils.isNotEmpty(param)){
			param = param.replace(" ", "");
		}
		HosAreaInfo p = this.service.getByAreaName(param);
		if(p!=null){
			return false;
		}else{
			return true;
		}
	}
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(HosAreaInfo hosAreaInfo) {
		System.err.println(hosAreaInfo);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			switch (service.save(hosAreaInfo)) {
			case 0:
				map.put("success", false);
				map.put("msg", "参数不能为空");
				break;
			case 1:
				map.put("success", true);
				map.put("msg", "保存成功");
				break;
			case 2:
				map.put("success", true);
				map.put("msg", "修改成功");
				break;
			case 3:
				map.put("success", false);
				map.put("msg", "该院区名称已经存在");
				break;
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "系统出错");
			logger.error(e.getMessage());
		}
		return map;
	}
	@RequestMapping(value = "delete.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("ids[]") List<String> ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer("");
		List<String> deleted = new ArrayList<String>();
		for (String areaId : ids) {
			try {
				if(service.delete(areaId)==0){
					HosAreaInfo hosAreaInfo = service.getByAreaId(areaId);
					if(hosAreaInfo!=null){
						sb.append("[" +hosAreaInfo.getAreaName() + "]  ");
					}
				}else{
					deleted.add(areaId);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		map.put("deleted", deleted);
		if(!"".equals(sb.toString())){
			map.put("success", false);
			map.put("msg", sb.toString()+"已存在科室，不能删除");
		}else{
			map.put("success", true);
			map.put("msg", "删除成功");
		}
		return map;
	}
}
