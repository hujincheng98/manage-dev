package com.skynet.rimp.hisBaseInfo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.common.utils.CommonUtil;
import com.skynet.rimp.common.utils.file.ImageConverter;
import com.skynet.rimp.hisBaseInfo.service.IHisDocmInfoService;
import com.skynet.rimp.hisBaseInfo.service.IHosDeptInfoService;
import com.skynet.rimp.hisBaseInfo.service.IHosDocmService;
import com.skynet.rimp.hisBaseInfo.vo.HisDocmInfo;
import com.skynet.rimp.hisBaseInfo.vo.HosDeptInfo;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;
import com.skynet.rimp.schmInfo.service.ISchmBaseMainInfoService;
import com.skynet.rimp.schmInfo.service.ISchmMainInfoService;
import com.skynet.rimp.schmInfo.vo.SchmBaseMainInfoEntity;
import com.skynet.rimp.schmInfo.vo.SchmMainInfo;

/**
 * 
 * <p>Title: 医生信息管理</p>
 * <p>Description: </p>
 *
 * @author Torlay
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
*
 */

@Controller
@RequestMapping(value = "/rimp/hosdocm/")
public class HosDocmAction {
	
	private static final Logger logger = Logger.getLogger(HosAction.class);

	@Autowired
	private IHosDocmService docmService;
	
	@Autowired
	private IHosDeptInfoService deptService;
	
	@Autowired
	private ISchmMainInfoService mainInfoService;
	
	@Autowired
	private IHisDocmInfoService hisDocmInfoService;
	
	@Autowired
	private ISchmBaseMainInfoService schmBaseMainInfoService;
	
	@Autowired
	private ISchmMainInfoService schmMainInfoService;

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<HosDocmInfo> list(SearchParams params) {
		Pagination<HosDocmInfo> pageList = null;
		if(params.getSearchParams()!=null && params.getSearchParams().get("docmName")!=null){
			String m_temp = params.getSearchParams().get("docmName").toString();
			if(!m_temp.equals("")){
				char[] charStr = m_temp.toString().toCharArray();
				if(CommonUtil.containStr(charStr)){
					params.getSearchParams().put("docmName", "");
					params.getSearchParams().put("docmPinyCode",m_temp);
				}
			}
		}
		try {
			pageList = docmService.findPageByCondition(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}
	
	/**
	 * 查询业务编码
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "hisDocmList.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<HisDocmInfo> hisDocmList(SearchParams params) {
		Pagination<HisDocmInfo> pageList = null;
		try {
			pageList = this.hisDocmInfoService.findPageByCondition(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}

	/**
	 * 跳转到医生管理首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "hisBaseInfo/docm_index";
	}
	
	/**
	 * 跳转到医生新增页面
	 * @return
	 */
	@RequestMapping(value = "docm_add.do", method = RequestMethod.GET)
	public String docm_add() {
		return "hisBaseInfo/docm_add";
	}
	
	/**
	 * 跳转到医生修改页面
	 * @return
	 */
	@RequestMapping(value = "docm_edit.do", method = RequestMethod.GET)
	public String docm_edit() {
		return "hisBaseInfo/docm_edit";
	}
	
	
	/**
	 * 跳转到医生详情页面
	 * @return
	 */
	@RequestMapping(value = "docm_detail.do", method = RequestMethod.GET)
	public String docm_detail() {
		return "hisBaseInfo/docm_detail";
	}
	
	/**
	 * 科室tree
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
	
	 
    /**
     * 更新状态信息
     * @return
     */
    @RequestMapping(value = "updateState.json", method = RequestMethod.POST)
    @ResponseBody
    public String update(String[] ids,String docmState)
    {
        int result = 0;
        try
        {
        	for (String docmId : ids) {
        		HosDocmInfo record = new HosDocmInfo();
        		record.setDocmId(docmId);
        		record.setDocmState(docmState);
        		result = docmService.updateState(record);
			}
        	
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (result != 0)
        {
            return "success";
        }
        else
        {
            return "fail";
        }
        
    }
    
    /**
     * 删除医生信息
     * @param id
     * @return
     */
    @RequestMapping(value = "delete.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String[] ids)
    {
        Map<String, Object> map = new HashMap<String, Object>();
    
        try{
		        boolean boo = false; // 标识位默认false
		        for (String docmId : ids) {
					SearchParams params = new SearchParams();
					Map<String, Object> searchParams = new HashMap<String, Object>();
					searchParams.put("docmId", docmId);
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
					int result = this.docmService.deleteByKeyArr(ids);
					map.put("state", "success");
					map.put("message", "删除成功！");
				} else {
					map.put("state", "0");
					map.put("message", "该医生已生成排班数据，不能删除！");
				}
        	
            }catch (Exception e){
                map.put("state", "error");
                map.put("message", "删除失败");
                logger.error(e.getMessage());
            }
        return map;
    }
    
    /**
	 * 保存医生信息
	 * @param hosInfo
	 * @return
	 */
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(HosDocmInfo hosDocmInfo, @RequestParam MultipartFile file)
    {
		Map<String, Object> map = new HashMap<String, Object>();
        try
        {
        	if (!file.isEmpty()) {
        		hosDocmInfo.setDocmPhoto(ImageConverter.toBase64(file.getInputStream(), 200, 200));
        	}
            this.docmService.save(hosDocmInfo);
            map.put("state", "success");
            map.put("message", "保存成功");
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
	 * 修改医生信息
	 * @param hosInfo
	 * @return
	 */
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateAll(HosDocmInfo hosDocmInfo, @RequestParam MultipartFile file)
    {
		Map<String, Object> map = new HashMap<String, Object>();
        try
        {
        	if (StringUtils.isEmpty(hosDocmInfo.getDocmPhoto()) && !file.isEmpty()) {
        		hosDocmInfo.setDocmPhoto(ImageConverter.toBase64(file.getInputStream(), 200, 200));
        	}
            this.docmService.update(hosDocmInfo);
            map.put("state", "success");
            map.put("message", "修改成功");
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            map.put("state", "fail");
            map.put("message", "修改失败");
        }
        return map;
    }
    
}
