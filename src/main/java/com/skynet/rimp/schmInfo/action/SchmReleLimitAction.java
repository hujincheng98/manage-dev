package com.skynet.rimp.schmInfo.action;

import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.schmInfo.service.ISchmReleLimitService;
import com.skynet.rimp.schmInfo.vo.SchmReleLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>Title: 放号管理控制器</p>
 * <p>Description: 放号管理控制器，主要包括更新和新增的方法</p>
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
@RequestMapping(value = "/rimp/releLimit/")
public class SchmReleLimitAction
{
    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    @Autowired
    private ISchmReleLimitService service;
    /**
	 * 跳转到放号管理首页
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "schmInfo/schm_rele_limit_index";
	}
    
    /**
     * 放号查询方法
     * @return
     */
    @RequestMapping(value = "select.json")
    @ResponseBody
    public SchmReleLimit select()
    {
    	SchmReleLimit rele = null;
        try
        {
            List<SchmReleLimit> list = service.findAll();
            if (list != null && list.size() > 0)
            {
            	rele = list.get(0);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if(rele == null){
        	rele = new SchmReleLimit();
        	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        	try {
				rele.setRuleDate(sdf.parse("23:59"));
				rele.setReleDate(sdf.parse("23:59"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        	rele.setRuleFlag("0");
        	rele.setOrgId(UserUtil.getAuthCode());
        	rele.setReleDayNum(7);
        }
        return rele;
    }
    
    /**
     * 放号更新方法
     * @param rele
     * @return
     */
    @RequestMapping(value = "update.json")
    @ResponseBody
    public Map<String, String> update(SchmReleLimit rele)
    {
        Map<String, String> dataMap = new HashMap<String, String>();
        try
        {
            if (rele != null)
            {
                service.update(rele);
                dataMap.put("state", "success");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return dataMap;
    }
    
}