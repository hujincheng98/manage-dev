/** 
 * Project Name:rimp 
 * File Name:backStageManagement.java 
 * Package Name:com.skynet.rimp.tran.action 
 * Date:2017-4-19上午9:38:14 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */  
package com.skynet.rimp.tran.action;
import com.skynet.his.utils.HttpRequest;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.hisBaseInfo.service.IHosInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;
import com.skynet.rimp.tran.service.BackStageManagementService;
import com.skynet.rimp.tran.vo.PatFeedback;
import com.skynet.rimp.tran.vo.PatFeedbackQuestion;

/**
 * @ClassName:		backStageManagement.java
 * @Description:	TODO用一句话描述该文件做什么 
 * @Date:           2017-4-19 上午9:38:14 
 * 
 * @author:			Administrator
 * @version:		 
 * @since :			JDK 1.7 
 */
@Controller
@RequestMapping(value = "/rimp/backStageManagement/")
public class backStageManagement {
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Autowired
	private BackStageManagementService backStageManagementService;
	
	@Autowired
	private IHosInfoService hosInfoService;
	
	/**
	 * 跳转到后台管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest req) {
		ModelAndView view = new ModelAndView();
		view.setViewName("doctorBeedback/backStageManagement");
		return view;
	}
	/**
	 * 转发分页查询
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Object list(SearchParams params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Pagination<T> pageList = new Pagination<T>();
		//获取机构编码
		String code = UserUtil.getAuthCode();
		//0表示admin
		if(!code.trim().equals("0")){
			if(params.getSearchParams()== null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orgid", code);
				params.setSearchParams(map);
			}else{
				params.getSearchParams().put("orgid", code);
			}
		}
		String Objec = JSONObject.toJSONString(params);
			String result = "";
			Properties prop = new Properties();
			InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
			prop.load(in);
			String param = prop.getProperty("url").trim();
			//String param = "http://localhost:8089/";
			System.out.println(Objec.toString());
			result = HttpRequest.sendPost(param+ "shs/patfeedback/findpage", Objec);
			pageList = JSONObject.toJavaObject(JSONObject.parseObject(result), Pagination.class);
			return pageList;
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
			String result = "";
			Properties prop = new Properties();
			InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
			prop.load(in);
			String param = prop.getProperty("url").trim();
			//String param = "http://localhost:8131/";
			if(ids!=null){
				if(ids.length == 1){ //只删除一个
					PatFeedback patFeedback = new PatFeedback();
					patFeedback.setPfid(ids[0]);
					result = HttpRequest.sendPost(param+ "shs/patfeedback/delete", JSONObject.toJSONString(patFeedback));
				}
				if(ids.length>1){ //删除多个 
					for (int i = 0; i < ids.length; i++) {
						PatFeedback p = new PatFeedback();
						p.setPfid(ids[i]);
						result = HttpRequest.sendPost(param+ "shs/patfeedback/delete", JSONObject.toJSONString(p));
					}			
			    }
				JSONObject oj = JSONObject.parseObject(result);
				if (oj.containsKey("message")) {
					if (oj.getString("message").equals("删除成功")) {
						map.put("state", "success");
						map.put("message", "删除成功");
					}
				} else {
					map.put("state", "fail");
					map.put("message", "删除失败");
				}
		   }
			
		} catch (Exception e) {
			map.put("state", "-1");
			map.put("message", "删除失败！");
		}
		
		return map;
	}
	/**
	 * 跳转至未回复或已回复
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "isreply.do")
	public ModelAndView  isreply(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView();
		try {
			String state = request.getParameter("state");
			String pfid = request.getParameter("pfid");
			PatFeedback pat = new PatFeedback();
			pat.setPfid(pfid);
			String Objec = JSONObject.toJSONString(pat);
			String result = "";
			Properties prop = new Properties();
			InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
			prop.load(in);
			String param = prop.getProperty("url").trim();
			//String param = "http://localhost:8131/";
			System.out.println(Objec.toString());
			result = HttpRequest.sendPost(param+ "shs/patfeedback/findbykey", Objec);
			if(!result.trim().equals("") && result!=null){
			PatFeedback patFeedback = JSONObject.parseObject(result, PatFeedback.class);
			//就医反馈
			String content = patFeedback.getContent();
			if(content!=null){
				content = content.replace("\\n", "");
				patFeedback.setContent(content);
			}
			//回复内容
			String reply = patFeedback.getReply();
			if(reply!=null){
				reply = reply.replace("\\n", "");	
				patFeedback.setReply(reply);
			}
			
			view.addObject("patFeedback", patFeedback);
			
			//判断是否为打印页面
			String printView = request.getParameter("printView");
			if(printView!=null && printView.trim().equals("1")){
				String hosName = "";
				String hosId = patFeedback.getHosid();
				//logger.info(hosId+"################医院ID#################");
				if(!hosId.trim().equals("") && hosId!=null){
					HosInfo hosInfo = hosInfoService.findByhosId(hosId);
					if(hosInfo!=null){					
						hosName = hosInfo.getHosName()+"\"就医反馈\"单";
						view.addObject("hosName", hosName);
					}else{
						view.addObject("hosName", "");
					}
				}else{
					view.addObject("hosName", "");
				}
				view.setViewName("doctorBeedback/printView");
			}
	
			if(state.trim().equals("0")){ //未回复
				view.setViewName("doctorBeedback/notReply");
			}
			if(state.trim().equals("1")){ //已回复
				view.setViewName("doctorBeedback/alreadyReply");
			}
		  }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return view;
	}
	/**
	 * 获得渠道下拉列表
	 * @param PatFeedback
	 * @return
	 */
	@RequestMapping(value = "getChild.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getChild() throws IOException{
		String orgId = UserUtil.getAuthCode();
		OtherChannelsInfo otherChannelsInfo = new OtherChannelsInfo();
		//0表示admin
	    if(orgId.trim().equals("0")){
	    	otherChannelsInfo.setOrgId(null);
	    }else{
	    	otherChannelsInfo.setOrgId(orgId);
	    }
		String Objec = JSONObject.toJSONString(otherChannelsInfo);
		String result = "";
		Properties prop = new Properties();
		InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
		prop.load(in);
		String param = prop.getProperty("url").trim();
		//String param = "http://localhost:8089/";
		System.out.println(Objec.toString());
		result = HttpRequest.sendPost(param+ "shs/otherChannelsInfo/findChid", Objec);		
		JSONArray array = JSONArray.parseArray(result);		
		return array;
	}
	/**
	 * 导出excel
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "makePatFeedback.json")
	public ModelAndView  exportExcel(SearchParams params, HttpServletRequest request, HttpServletResponse response){
		try {
			backStageManagementService.getExportExcel(request, response, params);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存题库维护信息
	 * @param HosFeedback
	 * @return
	 */
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(PatFeedback patFeedback, HttpServletRequest request)
    {   
		//获取机构代码
		patFeedback.setOrgid(UserUtil.getAuthCode());
		//已答复 0:表示未答复，1:表示已答复
		patFeedback.setIsreply(1);
		patFeedback.setStatus("bcak_02");
		//答复时间
		patFeedback.setReplydate(new Date());
		//答复人
		patFeedback.setReplyuser(UserUtil.getUserId());
		Map<String, Object> map = new HashMap<String, Object>();
		 try
	    {
		request.setCharacterEncoding("UTF-8");
		String result = "";
		Properties prop = new Properties();
		InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
		prop.load(in);
		String param = prop.getProperty("url").trim();
		//String param = "http://localhost:8131/";
		result = HttpRequest.sendPost(param+ "shs/patfeedback/update", JSONObject.toJSONString(patFeedback));
		JSONObject oj = JSONObject.parseObject(result);
		if (oj.containsKey("message")) {
			if (oj.getString("message").equals("更新成功")) {
				map.put("state", "success");
				map.put("message", "提交成功");
			}

		} else {
			map.put("state", "fail");
			map.put("message", "提交失败");
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
	 * 查询答题信息
	 * @param HosFeedback
	 * @return
	 */
	@RequestMapping(value = "PatFeedbackQuestion.json")
    @ResponseBody
    public List<PatFeedbackQuestion> PatFeedbackQuestion(HttpServletRequest request){
		String pfid = request.getParameter("pfid");
		PatFeedbackQuestion pat = new PatFeedbackQuestion();
		pat.setPfid(pfid);
		List<PatFeedbackQuestion> hosArr = new ArrayList<PatFeedbackQuestion>();
		try {
			String result = "";
			Properties prop = new Properties();
			InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
			prop.load(in);
			String param = prop.getProperty("url").trim();
			//String param = "http://localhost:8131/";
			result = HttpRequest.sendPost(param+ "shs/patfeedbackquestion/findbycon", JSONObject.toJSONString(pat));
			hosArr = JSONArray.parseArray(result, PatFeedbackQuestion.class);
		} catch (Exception e) {
			 logger.error(e.getMessage());
		}
		return hosArr;
	}
}
