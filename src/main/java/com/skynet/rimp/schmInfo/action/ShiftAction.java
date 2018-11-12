package com.skynet.rimp.schmInfo.action;

import java.text.SimpleDateFormat;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.schmInfo.service.ISchmShiftInfoService;
import com.skynet.rimp.schmInfo.vo.SchmShiftInfoEntity;
import com.skynet.rimp.schmInfo.vo.SchmTislInfoEntity;
import com.skynet.util.StringUtil;

/**
 * 班次维护
 * 
 */

@Controller
@RequestMapping(value = "/rimp/shift/")
public class ShiftAction
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ShiftAction.class);
    
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    @Autowired
    private ISchmShiftInfoService service;
    
    /**
     * 班次首页
     * @return
     */
    @RequestMapping(value="index.do",method=RequestMethod.GET)
    public String index(){
    	return "schmInfo/shift_Index";
    }
    /**
     * 班次维护 - 编辑
     * @param shiftid
     * @param model
     * @return
     */
    @RequestMapping(value="edit.do",method=RequestMethod.GET)
    public String edit(String shiftid,ModelMap model){
    	model.put("shiftid", shiftid);
    	return "schmInfo/shift_Edit";
    }
    
    /**
     * 班次维护 - 新增
     * @return
     */
    @RequestMapping(value="add.do",method=RequestMethod.GET)
    public String add(){
    	return "schmInfo/shift_Add";
    }
    
    
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(SchmShiftInfoEntity shift)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
    		service.save(shift);
    		map.put("shiftId", shift.getShiftId());
    		map.put("success", true);
    		map.put("message", "保存成功");
        }
        catch (Exception e)
        {
            map.put("success", false);
            map.put("message", "保存失败");
            map.put("shiftId", null);
            logger.error(e.getMessage());
        }
        return map;
    }
    
    @RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(SchmShiftInfoEntity shift)
    {
    	Map<String, Object> map = new HashMap<String, Object>();
    	try
    	{
			service.save(shift);
			map.put("shiftId", shift.getShiftId());
			map.put("success", true);
			map.put("message", "保存成功");
    	}
    	catch (Exception e)
    	{
    		map.put("success", false);
    		map.put("message", "保存失败");
    		map.put("shiftId", null);
    		logger.error(e.getMessage());
    	}
    	return map;
    }
    
    @RequestMapping(value = "delete.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String ids)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(ids))
        {
            map.put("state", "error");
            map.put("msg", "id不能为空");
        }
        else
        {
            try
            {
                String noDeleteShiftNames = "";
                if(!StringUtil.isEmpty(ids))
                {
                    String[] split = ids.split(",");
                    for (String id : split)
                    {
                        if (service.delete(id) == 0)
                        {
                            SchmShiftInfoEntity shiftInfoEntity = service.findById(id);
                            noDeleteShiftNames += "[" + shiftInfoEntity.getShiftName() + "]  ";
                        }
                    }
                    if (!"".equals(noDeleteShiftNames))
                    {
                        map.put("state", "error");
                        map.put("msg", noDeleteShiftNames + "班次信息已经被使用，不能删除。");
                    }
                    else
                    {
                        map.put("state", "success");
                        map.put("msg", "删除成功。");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                map.put("state", "error");
                map.put("msg", "删除异常，请联系管理员。");
            }
        }
        return map;
    }
    
    @RequestMapping(value = "delete_tisl.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteTisl(String tislId, String shiftId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(tislId))
        {
            return null;
        }
        else
        {
            try
            {
            	SearchParams params = new SearchParams();
    			Map<String, Object> searchParams = new HashMap<String, Object>();
    			searchParams.put("shiftId", shiftId);
    			params.setSearchParams(searchParams);
            	List<SchmTislInfoEntity> list = this.service.selectByShiftId(shiftId);
            	if (list != null && list.size() == 1) {
            		map.put("state", "error");
        			map.put("message", "请至少保留一条时段明细");
            	} else {
            		int num = service.deleteTisl(tislId);
            		if (num > 0)
            		{
            			map.put("state", "success");
            			map.put("message", "删除成功");
            		}
            	}
            }
            catch (Exception e)
            {
            	e.printStackTrace();
            	map.put("state", "error");
    			map.put("message", "删除失败");
            	logger.error(e.getMessage());
            }
        }
        return map;
    }
    
    @RequestMapping(value = "list.json", method = RequestMethod.POST)
    @ResponseBody
    public Pagination<SchmShiftInfoEntity> list(SearchParams params)
    {
        Pagination<SchmShiftInfoEntity> pageList = null;
        try
        {
            pageList = service.findPageByCondition(params);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return pageList;
    }
    
    
    @RequestMapping(value = "select_shift_name.json", method = RequestMethod.POST)
    @ResponseBody
    public boolean selectByShiftName(String shiftName)
    {
        if (StringUtils.isBlank(shiftName))
        {
            return false;
        }
        else
        {
            try
            {
                List<SchmShiftInfoEntity> bean = service.findByShiftName(shiftName);
                if (bean != null && bean.size() > 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            catch (Exception e)
            {
                return false;
            }
        }
    }

        
    @RequestMapping(value = "select.json", method = RequestMethod.POST)
    @ResponseBody
    public SchmShiftInfoEntity select(String id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(id))
        {
            map.put("state", "error");
        }
        else
        {
            try
            {
                SchmShiftInfoEntity bean = service.findById(id);
                return bean;
            }
            catch (Exception e)
            {
                map.put("state", "error");
            }
        }
        return null;
    }
    
    @RequestMapping(value = "selectTisl.json", method = RequestMethod.POST)
    @ResponseBody
    public List<SchmTislInfoEntity> selectTisl(String id) {
    	try {
			return service.findById(id).getTisls();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
}
