package com.skynet.rimp.schmInfo.action;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.his.utils.DateUtils;
import com.skynet.rimp.common.utils.time.DateEditor;
import com.skynet.rimp.hisBaseInfo.service.IHosDeptInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosDeptInfo;
import com.skynet.rimp.schmInfo.service.ISchmMainInfoService;
import com.skynet.rimp.schmInfo.service.ISchmReleLimitService;
import com.skynet.rimp.schmInfo.service.ISchmShiftInfoService;
import com.skynet.rimp.schmInfo.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>Title: 处理医生排班控制器</p>
 * <p>Description: </p>
 *
 * @author Torlay
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 *
**/

@Controller
@RequestMapping(value = "/rimp/schm/")
public class SchmAction
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SchmAction.class);

    @Autowired
    private ISchmMainInfoService service;

    @Autowired
    private IHosDeptInfoService servicedept;

    @Autowired
    private ISchmShiftInfoService serviceshit;

    @Autowired
    private IHosDeptInfoService deptInfoService;

    @Autowired
    private ISchmReleLimitService schmReleLimitService;

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder)
    {
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    /**
   	 * 跳转到医生排班新增
   	 * @return
   	 */
   	@RequestMapping(value = "add.do", method = RequestMethod.GET)
   	public String add() {
   		return "schmInfo/schm_add";
   	}


    /**
	 * 跳转到医生排班首页
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "schmInfo/schm_index";
	}

	/**
     * 班次维护 - 编辑
     * @param schmId
     * @param model
     * @return
     */
    @RequestMapping(value="showTimesEdit.do",method=RequestMethod.GET)
    public String edit(String schmId,ModelMap model){
    	model.put("schmId", schmId);
    	return "schmInfo/schm_edit";
    }

  /**
   *医生排班查询分页
   * @param params
   * @return
   */
    @RequestMapping(value = "list.json", method = RequestMethod.POST)
    @ResponseBody
    public Pagination<SchmMainInfo> list(SearchParams params)
    {
        Pagination<SchmMainInfo> pageList = null;
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
    /**
     *查询医生排班
     * @param params
     * @return
     */
    @RequestMapping(value = "listSchm.json", method = RequestMethod.POST)
    @ResponseBody
    public List<SchmMainInfo> listSchm(SearchParams params)
    {
        List<SchmMainInfo> schmMainInfos = null;
        try
        {
            schmMainInfos = service.findByCondition(params);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return schmMainInfos;
    }


    /**
     *
     * @return
     */
    @RequestMapping(value = "listDept.json", method = RequestMethod.POST)
    @ResponseBody
    public Pagination<HosDeptInfo> listdept(SearchParams params)
    {
        Pagination<HosDeptInfo> pageList = null;
        try
        {
            pageList = servicedept.findPageByCondition(params);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return pageList;
    }

    /**
     * 更新状态信息
     * @return
     */
    @RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public String update(String[] schmId, String schmState)
    {
        int result = 0;
        try
        {
        	for (String id : schmId) {
        		SchmMainInfo record = new SchmMainInfo();
        		record.setSchmId(id);
        		record.setSchmState(schmState);
        		result = service.updateState(record);
			}

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
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
     * 查询班次信息，根据班次id
     * @return
     */
    @RequestMapping(value = "listbyshitid.json", method = RequestMethod.POST)
    @ResponseBody
    public List<SchmTislInfoEntity> listByshitid(String shiftId)
    {
        try
        {
            if (shiftId != null && !"".equals(shiftId))
            {
                SchmShiftInfoEntity bean = serviceshit.findById(shiftId);
                if (bean != null)
                {
                    return bean.getTisls();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 手工新增排班
     * @param schmMainInfovo
     * @return
     */
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public String save(SchmMainInfoVo schmMainInfovo)
    {
        String result = "";
        try
        {
            result = service.saveAll(schmMainInfovo);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改挂号类别
     * @param schmMainInfovo
     * @return
     */
    @RequestMapping(value = "updateRegCategory.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateRegCategory(SchmMainInfoVo schmMainInfovo)
    {
        Map<String, Object> map = new HashMap<String, Object>();

            try
            {
                SchmMainInfo mainInfo = this.service.findById(schmMainInfovo.getSchmId());
                if(compare(mainInfo)){
                    map.put("state", "used");
                    map.put("message", "此排班已放号,不可修改");
                    return map;
                }
                //判断是否只是修改挂号类别
                if(!(mainInfo.getDocmId().equals(schmMainInfovo.getDocmId()) && mainInfo.getSchmDeptId().equals(schmMainInfovo.getSchmDeptIDtocontext()))){
                    // 同排班日期同医生同班次不能重复
                    int resultex = service.findByShiftIdByDocmDate(schmMainInfovo);

                    if (resultex > 0) {
                        map.put("state", "exist");
                        map.put("message", "不能修改为和已有排班相同");
                        return map;
                    }
                }
                /**
                 * 如果有修改[线下挂号限数] 和 [线上预约限数]
                 */
                System.out.println(schmMainInfovo.getSchmOnSum()+"----"+mainInfo.getSchmOnSum()+"-----"+schmMainInfovo.getSchmOnSum().equals(String.valueOf(mainInfo.getSchmOnSum())));
                if(!schmMainInfovo.getSchmOnSum().equals(String.valueOf(mainInfo.getSchmOnSum()))){
                    // [线下挂号限数]可修改任意值 ，[线上预约限数]只能修改等于[线上已预约数]
                    if(!schmMainInfovo.getSchmOnSum().equals(String.valueOf(mainInfo.getSchmUpreNum()))){
                        map.put("state", "notschm");
                        map.put("message", "[线下挂号限数]可修改任意值 ，[线上预约限数]只能修改等于[线上已预约数]!");
                        return map;
                    }
                }

                this.service.updateRegCategory(schmMainInfovo,mainInfo);
                map.put("state", "succ");
                map.put("message", "更新成功");
            }
            catch (Exception e)
            {
                map.put("state", "error");
                map.put("message", "更新失败");
                logger.error(e.getMessage());
            }
        return map;
    }
    /**
     * 更新明细信息
     * @param schmMainInfovo
     * @return
     */
    @RequestMapping(value = "update_detail.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(SchmMainInfoVo schmMainInfovo)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
        	SchmMainInfo mainInfo = this.service.findById(schmMainInfovo.getSchmId());
        	if (compare(mainInfo)) {
        		 map.put("state", "error");
        		 map.put("message", "此排班已放号,不可修改");
        	} else {
        		service.updateMainDetail(schmMainInfovo);
        		map.put("state", "success");
        		map.put("message", "修改成功");
        	}
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
            map.put("state", "error");
    		map.put("message", "修改失败");
        }
        return map;
    }

    /**
     * 删除排班
     * @param ids
     * @return
     */
    @RequestMapping(value = "delete.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String[] ids)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
        	for (String id : ids) {
        		SchmMainInfo mainInfo = this.service.findById(id);
        		if(compare(mainInfo)){
        			 map.put("state", "error");
            		 map.put("message", "此排班已放号,不可删除");
            		 return map;
        		}
			}
        	for (String schmId : ids) {
        		service.deleteBySchmId(schmId);
			}
        	map.put("state", "success");
    		map.put("message", "删除成功");
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	logger.error(e.getMessage());
            map.put("state", "error");
            map.put("message", "删除失败");
        }
        return map;
    }
    /**
     * 批量删除排班
     * @param schmId
     * @return
     */
    @RequestMapping(value = "deleteBatch.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteBatch(String[] schmId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            String id = schmId[0];
            SchmMainInfo mainInfo = this.service.findById(id);
            if(compareSchmDate(mainInfo)){
                map.put("state", "error");
                map.put("message", "此排班已放号,不可删除");
                return map;
            }
            service.deleteBatchBySchmId(schmId);
            map.put("state", "success");
            map.put("message", "删除成功");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error(e.getMessage());
            map.put("state", "error");
            map.put("message", "删除失败");
        }
        return map;
    }
    private Boolean compareSchmDate(SchmMainInfo mainInfo) {
        //获得放号时间的设置值 releDayNum >>> 放号天数 releDate >>> 放号开始时间
        int releDayNum = 7; // 放号天数限制
        String releDate = "23:59:59"; //放号时间没有填写默认为23:59:59，放号天数默认为7天
        SchmReleLimit relelimit = this.schmReleLimitService.getSchmReleLimitByHosId(mainInfo.getHosId());
        if (relelimit != null) {
            if (relelimit.getReleDate() != null) {
                releDate = DateUtils.getDate(relelimit.getReleDate(), "HH:mm:ss");
            }
            if (relelimit.getReleDayNum() != null && relelimit.getReleDayNum() != 0) {
                releDayNum = relelimit.getReleDayNum();
            }
        }
        Integer maxReleDayNum = this.deptInfoService.selectMaxReleDayNum(mainInfo.getHosId());
        if (maxReleDayNum != null && maxReleDayNum > releDayNum){
            releDayNum = maxReleDayNum;
        }
        //计算获得结束日期
        String nowDate = DateUtils.getDate(new Date(), "");
        nowDate = nowDate + " " + releDate;//根据放号时间获得放号具体时间
        String m_date = DateUtils.getDate(new Date(), "yyyy-MM-dd HH:mm:ss");
        if(!DateUtils.compare_date(m_date, nowDate , "yyyy-MM-dd HH:mm:ss")){
            releDayNum+=1;
        }
        String endDate = DateUtils.dateAddDays(new Date(), "", releDayNum); //可预约的结束日期

        if (DateUtils.compare_date(endDate, DateUtils.getDate(mainInfo.getSchmDate(), "yyyy-MM-dd"), "yyyy-MM-dd")) {
            return true;
        }
        return false;
    }

    /**
     * 比较日期范围
     * @param mainInfo  当前日期加放号天数 + 1 大于 排班日期 返回true
     * @return
     */
    private Boolean compare(SchmMainInfo mainInfo) {
    	//获得放号时间的设置值 releDayNum >>> 放号天数 releDate >>> 放号开始时间
    	int releDayNum = 7; // 放号天数限制
		String releDate = "23:59:59"; //放号时间没有填写默认为23:59:59，放号天数默认为7天
		SchmReleLimit relelimit = this.schmReleLimitService.getSchmReleLimitByHosId(mainInfo.getHosId());
		if (relelimit != null) {
			if (relelimit.getReleDate() != null) {
				releDate = DateUtils.getDate(relelimit.getReleDate(), "HH:mm:ss");
			}
			if (relelimit.getReleDayNum() != null && relelimit.getReleDayNum() != 0) {
				releDayNum = relelimit.getReleDayNum();
			}
		}
    	if (StringUtils.isNotEmpty(mainInfo.getSchmDeptId())) {
    		HosDeptInfo deptInfo = this.deptInfoService.selectByPrimaryKey(mainInfo.getSchmDeptId());
    		if (deptInfo != null && deptInfo.getReleDayNum() != null) {
    			releDayNum = deptInfo.getReleDayNum();
    		}
    	}
    	//计算获得结束日期
		String nowDate = DateUtils.getDate(new Date(), "");
    	nowDate = nowDate + " " + releDate;//根据放号时间获得放号具体时间
		String m_date = DateUtils.getDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		if(!DateUtils.compare_date(m_date, nowDate , "yyyy-MM-dd HH:mm:ss")){
			releDayNum+=1;
		}
		String endDate = DateUtils.dateAddDays(new Date(), "", releDayNum); //可预约的结束日期

		if (DateUtils.compare_date(endDate, DateUtils.getDate(mainInfo.getSchmDate(), "yyyy-MM-dd"), "yyyy-MM-dd")) {
			return true;
		}
		return false;
    }

    /**
     * 删除明细信息
     * @param schmDetailId
     * @return
     */
    @RequestMapping(value = "delete_detail.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteDetail(String schmDetailId, String schmId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isBlank(schmDetailId))
        {
            try
            {
            	SearchParams params = new SearchParams();
    			Map<String, Object> searchParams = new HashMap<String, Object>();
    			searchParams.put("schmId", schmId);
    			params.setSearchParams(searchParams);
            	List<SchmDetailInfo> list = this.service.findDetailsBySchmId(schmId);
            	if (list != null && list.size() == 1) {
            		map.put("state", "error");
        			map.put("message", "请至少保留一条时段明细");
            	} else {
            		SchmMainInfo mainInfo = this.service.findById(schmId);
                	if (compare(mainInfo)) {
                		 map.put("state", "error");
                		 map.put("message", "此排班已放号,不可删除");
                	} else {
                		int num = service.deleteDetailByDetailId(schmDetailId);
                		if (num > 0)
                		{
                			map.put("state", "success");
                			map.put("message", "删除成功");
                		}
                	}
            	}
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 自动排版请求
     * @param schmAutoSchmInfoVo
     * @return
     */
    @RequestMapping(value = "auto_schm.json", method = RequestMethod.POST)
    @ResponseBody
    public String autoSchm(SchmAutoSchmInfoVo schmAutoSchmInfoVo)
    {

        if (schmAutoSchmInfoVo != null)
        {
            try
            {
                return service.saveAutoSchm(schmAutoSchmInfoVo);
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 根据排班id查询排班
     * @param schmId
     * @return
     */
    @RequestMapping(value = "select.json", method = RequestMethod.POST)
    @ResponseBody
    public SchmMainInfo select(String schmId)
    {
        try
        {
            if (schmId != null && !"".equals(schmId))
            {
                SchmMainInfo schmMainInfo = service.findById(schmId);
                if (schmMainInfo != null)
                {
                    return schmMainInfo;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据排班id查询排班明细
     * @param schmId
     * @return
     */
    @RequestMapping(value = "selectDeatils.json", method = RequestMethod.POST)
    @ResponseBody
    public List<SchmDetailInfo> selectSchmDetailBySchmId(String schmId)
    {
        try
        {
            if (schmId != null && !"".equals(schmId))
            {
                return service.findDetailsBySchmId(schmId);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取下周的日期范围
     * @return
     */
    @RequestMapping(value = "nextWeekDate.json", method = RequestMethod.POST)
    @ResponseBody
    public SchmAutoSchmInfoVo selectnextWeekDate()
    {

    	Calendar calc = Calendar.getInstance();
		calc.add(Calendar.WEEK_OF_YEAR, 1);
		int initDay = calc.getFirstDayOfWeek();
		calc.set(Calendar.DAY_OF_WEEK, initDay + 1);
		Date startDate = calc.getTime();
		Date endDate = calc.getTime();

		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.DAY_OF_MONTH, 6);
		endDate = c.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SchmAutoSchmInfoVo vo = new SchmAutoSchmInfoVo();
		vo.setStartDate(formatter.format(startDate));
		vo.setEndDate(formatter.format(endDate));

		return vo;


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
            this.service.getExportExcel(request, response,
                    params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
