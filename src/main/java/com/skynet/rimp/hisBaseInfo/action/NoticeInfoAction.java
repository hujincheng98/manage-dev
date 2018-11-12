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

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.common.utils.CommonUtil;
import com.skynet.rimp.common.utils.file.ImageConverter;
import com.skynet.rimp.common.utils.string.StringUtil;
import com.skynet.rimp.common.utils.time.DateUtil;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.hisBaseInfo.service.IHosInfoService;
import com.skynet.rimp.hisBaseInfo.service.INoticeInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;
import com.skynet.rimp.hisBaseInfo.vo.NoticeInfo;
import com.skynet.rimp.schmInfo.vo.SchmBaseMainInfoEntity;
import com.skynet.rimp.schmInfo.vo.SchmMainInfo;


/**
 * 
 * <p>Title: 医院通知公告管理</p>
 * <p>Description: </p>
 *
 * @author liuletian
 * @version 1.00.00 创建时间：2017-03-13 16:10:45
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 *
 */
 
@Controller
@RequestMapping(value = "/rimp/hosNotice/")
public class NoticeInfoAction
{    
    protected Logger logger = Logger.getLogger(this.getClass().getName());
    
    @Autowired
    private INoticeInfoService noticeInfoService;
    
    @Autowired
    private IHosInfoService hosInfoService;
    
    
    /**
	 * 跳转到通知公告首页
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "hisBaseInfo/notice_index";
	}
    
	/**
	 * 公告分页展示
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<NoticeInfo> list(SearchParams params) {
		//获取机构编码
		String code = UserUtil.getAuthCode();
		//0表示admin
		HosInfo hf = this.hosInfoService.findByHosOrgId(code);
		String hosId = null;
		if(hf!=null){
			hosId = hf.getHosId();
			if(params.getSearchParams()== null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("hoscoding", hosId);
				params.setSearchParams(map);
			}else{
				params.getSearchParams().put("hoscoding", hosId);
			}
		}				
				
		Pagination<NoticeInfo> pageList = null;
		if(params.getSearchParams()!=null && params.getSearchParams().get("noticename")!=null){
			String m_temp = params.getSearchParams().get("noticename").toString();
			if(!m_temp.equals("")){
				char[] charStr = m_temp.toString().toCharArray();
				if(CommonUtil.containStr(charStr)){
					params.getSearchParams().put("noticename", "");
				}
			}
		}
		try {
			pageList = noticeInfoService.findPageByCondition(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}
	
	/**
	 * 跳转到公告新增页面
	 * @return
	 */
	@RequestMapping(value = "notice_add.do", method = RequestMethod.GET)
	public String docm_add() {
		return "hisBaseInfo/notice_add";
	}
	
	/**
	 * 保存公告信息
	 * @param hosInfo
	 * @return
	 */
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(NoticeInfo noticeInfo, @RequestParam MultipartFile file)
    {
		Map<String, Object> map = new HashMap<String, Object>();
		 try
	        {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = sdf.parse(sdf.format(new Date()));
		//获取下线时间
		Date offlinedate = noticeInfo.getOfflinedate();
		//求当前日期和指定字符串日期的相差天数
		long num = currentDate.getTime() - offlinedate.getTime();
		//校验创建该通知公告次日及以后的时间
		if(num >= 0){ 
			map.put("state", "validatedDate");
            map.put("message", "下线时间必须大于当前时间");
            return map;
		}
		String noticename = noticeInfo.getNoticename();
		String hosId = noticeInfo.getAffiliatedhos();
		boolean flag = noticeInfoService.findBynoticeNameAndHosId(noticename, hosId);
		//校验相同所属医院、通告类型的通告标题不能重复
		if(!flag){
			map.put("state", "validatedNoticeName");
            map.put("message", "相同所属医院、通告类型的通告标题不能重复");
            return map;
		}
       
        if (!file.isEmpty()) {
        		noticeInfo.setUploadpictures(ImageConverter.toBase64(file.getInputStream(), 200, 200));
        	}
        this.noticeInfoService.save(noticeInfo);
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
	 * 新增相同医院下新建的预约说明和停诊通知进行唯一性判断
	 * 预约说明-noticetype_01,停诊通知-noticetype_02,排班信息-noticetype_03,公告发布-noticetype_04
	 * @param hosInfo
	 * @return
	 */
	@RequestMapping(value = "checkNoticeUnique.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkNoticeUnique(String noticetype, String hosId, String state, String id)
    {
		Map<String, Object> map = new HashMap<String, Object>();
		if(state.trim().equals("save")){ //保存时校验
			boolean flag = noticeInfoService.findByNoticeTypeAndHosId(noticetype, hosId);
			if(!flag){
				map.put("state", "fail");
	            map.put("message", "同一个医院下预约说明或者停诊通知只能有一条");
			}
		}
		if(state.trim().equals("update")){
			String noticeInfoId = id;
			NoticeInfo notice = noticeInfoService.getNoticeInfoById(noticeInfoId);
			String oldNoticetype = notice.getNoticetype();
			String oldHosId = notice.getHoscoding();
			//判断hosId是医院名称还是医院id
			boolean isChinese = StringUtil.isChinese(hosId);
			if(isChinese){ //是医院名称
				hosId = notice.getHoscoding();
			}			
			//判断如果通告类型和所属医院未作出修改：不需要校验,相同医院下新建的预约说明和停诊通知进行唯一性判断
			if(!noticetype.trim().equals(oldNoticetype.trim()) || !hosId.trim().equals(oldHosId.trim())){ //通告类型或所属医院发生变化，需要校验
				boolean flag = noticeInfoService.findByNoticeTypeAndHosId(noticetype, hosId);
				if(!flag){
					map.put("state", "fail");
		            map.put("message", "同一个医院下预约说明或者停诊通知只能有一条");
				}
			}
		}
        return map;
    }
	
	/**
	 * 跳转到公告修改页面
	 * @return
	 */
	@RequestMapping(value = "notice_edit.do", method = RequestMethod.GET)
	public String notice_edit() {
		return "hisBaseInfo/notice_edit";
	}
	/**
	 * 修改公告信息
	 * @param hosInfo
	 * @return
	 */
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateAll(NoticeInfo noticeInfo, @RequestParam MultipartFile file)
    {
		Map<String, Object> map = new HashMap<String, Object>();
        try
        {
        	
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		Date currentDate = sdf.parse(sdf.format(new Date()));
    		//获取下线时间
    		Date offlinedate = noticeInfo.getOfflinedate();
    		//求当前日期和指定字符串日期的相差天数
    		long num = currentDate.getTime() - offlinedate.getTime();
    		//校验创建该通知公告次日及以后的时间
    		if(num >= 0){ 
    			map.put("state", "validatedDate");
                map.put("message", "下线时间必须大于当前时间");
                return map;
    		}
    		//判断通告名称是否做出修改，未修改不做：校验相同所属医院、通告类型的通告标题不能重复
    		String noticename = noticeInfo.getNoticename().trim();
    		String noticeId = noticeInfo.getId();
    		NoticeInfo noticei = noticeInfoService.getNoticeInfoById(noticeId);
    		String oldNoticeName = noticei.getNoticename().trim();
    		if(!oldNoticeName.equals(noticename)){ //公告名字已修改:做校验相同所属医院、通告类型的通告标题不能重复
    			String Affiliatedhos = noticeInfo.getAffiliatedhos();
        		//hosName值是字符串id还是中文
        		boolean isChinese = StringUtil.isChinese(Affiliatedhos);
        		String hosId = "";
        		if(isChinese){ //是医院名称
        			List<HosInfo> hosList = hosInfoService.findByHosName(Affiliatedhos);   		
            		if(hosList.size()>0){
            			hosId = hosList.get(0).getHosId();
            		}	
        		}else{ //是医院id
        			hosId= Affiliatedhos;
        		}
        		
        		boolean flag = noticeInfoService.findBynoticeNameAndHosId(noticename, hosId);
        		//校验相同所属医院、通告类型的通告标题不能重复
        		if(!flag){
        			map.put("state", "validatedNoticeName");
                    map.put("message", "相同所属医院、通告类型的通告标题不能重复");
                    return map;
        		}
    		}
    		
        	if (!file.isEmpty()) {
        		noticeInfo.setUploadpictures(ImageConverter.toBase64(file.getInputStream(), 200, 200));
        	}
            this.noticeInfoService.update(noticeInfo);
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
	
	/**
	 * 删除方法
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "delete.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String[] ids){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int result = this.noticeInfoService.deleteByKeyArr(ids);
			if(result == 0){
				map.put("state", result);
				map.put("message", "删除失败！");
			}else{
				map.put("state", result);
				map.put("message", "删除成功！");
			}
			
		} catch (Exception e) {
			map.put("state", "-1");
			map.put("message", "删除失败！");
		}
		
		return map;
	}
	/**
	 * 查询医院下拉列表
	 */
	@RequestMapping(value = "listByOrgId.json", method = RequestMethod.POST)
	@ResponseBody
	public Object listByOrgId() {
		List<HosInfo> pageList = new ArrayList<HosInfo>();
		//获取机构编码
		String code = UserUtil.getAuthCode();
		//0表示admin
		if(code.trim().equals("0")){
			//查询所有医院
		  try {
			pageList = this.hosInfoService.findAll();
			return pageList;
		   } catch (Exception e) {
			logger.error(e.getMessage());
		  }
		}
		HosInfo hf = this.hosInfoService.findByHosOrgId(code);
		HosInfo hosInfo = null;
		if(hf!=null){
			String hosId = hf.getHosId();
			hosInfo = this.hosInfoService.findByhosId(hosId);
			pageList.add(hosInfo);
		}
		return pageList;
	}
	
}
