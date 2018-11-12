/** 
 * Project Name:rimp 
 * File Name:reply.java 
 * Package Name:com.skynet.rimp.tran.action 
 * Date:2017-4-19上午9:41:18 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */  
package com.skynet.rimp.tran.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skynet.common.Pagination;
import com.skynet.his.utils.HttpRequest;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.tran.vo.HosFeedback;
import com.skynet.rimp.tran.vo.PatFeedback;
import com.skynet.rimp.tran.vo.PatFeedbackQuestion;

/**
 * @ClassName:		reply.java
 * @Description:	TODO用一句话描述该文件做什么 
 * @Date:           2017-4-19 上午9:41:18 
 * 
 * @author:			Administrator
 * @version:		 
 * @since :			JDK 1.7 
 */
@Controller
@RequestMapping(value = "/rimp/reply/")
public class reply {
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	/**
	 * 跳转到回复页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "doctorBeedback/reply";
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
