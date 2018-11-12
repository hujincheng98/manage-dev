package com.skynet.rimp.hisBaseInfo.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.platform.core.entity.AreaEntity;
import com.skynet.platform.core.service.AreaService;
import com.skynet.rimp.common.utils.file.FileUtil;
import com.skynet.rimp.common.utils.file.ImageConverter;
import com.skynet.rimp.hisBaseInfo.service.IHosAreaInfoService;
import com.skynet.rimp.hisBaseInfo.service.IHosInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosAreaInfo;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;

@Controller
@RequestMapping(value = "/rimp/hos/")
public class HosAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HosAction.class);
	
	@Autowired
	private IHosInfoService hosInfoService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private IHosAreaInfoService hosAreaInfoService;
	
	/**
	 * 跳转到医院信息首页
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "hisBaseInfo/hos_index";
	}
	
	/**
	 * 跳转到医院新增页面
	 * @return
	 */
	@RequestMapping(value = "hos_add.do", method = RequestMethod.GET)
	public String hos_add() {
		return "hisBaseInfo/hos_add";
	}
	
	/**
	 * 跳转到医院修改页面
	 * @return
	 */
	@RequestMapping(value = "hos_edit.do", method = RequestMethod.GET)
	public String hos_edit() {
		return "hisBaseInfo/hos_edit";
	}
	
	/**
	 * 返回列表数据
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<HosInfo> list(SearchParams params) {
		Pagination<HosInfo> pageList = null;
		try {
			pageList = this.hosInfoService.findPageByCondition(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}
	
	
	/**
	 * 返回列表数据
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "listAll.json", method = RequestMethod.POST)
	@ResponseBody
	public List<HosInfo> listAll() {
		List<HosInfo> pageList = null;
		try {
			pageList = this.hosInfoService.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}
	
	
	/**
	 * 保存医院信息
	 * @param hosInfo
	 * @return
	 */
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(HosInfo hosInfo,@RequestParam MultipartFile file)
    {
		Map<String, Object> map = new HashMap<String, Object>();
        try
        {
        	List<HosInfo> list = this.hosInfoService.findByHosName(hosInfo.getHosName());
        	if (list.isEmpty()) {
        		hosInfo.setHosPropOne(ImageConverter.toBase64(file.getInputStream(), 1000, 1000));
        		this.hosInfoService.save(hosInfo);
        		map.put("state", "success");
        		map.put("message", "保存成功");
        	} else {
        		map.put("state", "fail");
        		map.put("message", "保存失败，医院名称不能重复");
        	}
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            map.put("state", "fail");
            map.put("message", "保存失败");
        }
        return map;
    }
	
	/**
	 * 更新医院信息
	 * @param hosInfo
	 * @return
	 */
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(HosInfo hosInfo,@RequestParam MultipartFile file)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			if(file.getInputStream().available()>0){//换了照片
				hosInfo.setHosPropOne(ImageConverter.toBase64(file.getInputStream(), 1000, 1000));
			}
			this.hosInfoService.update(hosInfo);
			map.put("state", "success");
			map.put("message", "更新成功");
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "更新失败");
		}
		return map;
	}
	
	/**
	 * 删除医院信息
	 * @param hosId
	 * @return
	 */
	@RequestMapping(value = "delete.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String hosId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SearchParams params = new SearchParams();
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("hosId", hosId);
			params.setSearchParams(searchParams);
			List<HosAreaInfo> list = this.hosAreaInfoService.findByCondition(params);
			if (list.isEmpty()) {
				this.hosInfoService.delete(hosId);
				map.put("state", "success");
				map.put("message", "删除成功");
			} else {
				map.put("state", "fail");
	            map.put("message", "对不起，此医院不能删除");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("state", "fail");
            map.put("message", "删除失败");
		}
		return map;
	}
	
	/**
	 * 返回区域列表
	 * @param parentAreaId
	 * @return
	 */
	@RequestMapping(value = "findArea.json", method = RequestMethod.POST)
	@ResponseBody
	public List<AreaEntity> findArea(String parentAreaId) {
		try {
			return this.areaService.findByParentAreaCode(parentAreaId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}
	
    @RequestMapping(value = "download.do", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(String filePath, String fileName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<byte[]>(FileUtil.readFileAsByteArray(new File(filePath)), headers, HttpStatus.CREATED);
    }
	
}
