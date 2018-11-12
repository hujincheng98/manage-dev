/** 
 * Project Name:rimp 
 * File Name:tranRestful.java 
 * Package Name:com.skynet.rimp.tran.action 
 * Date:2017-4-17下午1:32:28 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */
package com.skynet.rimp.tran.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.common.utils.HttpRequest;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.tran.vo.HosFeedback;
import com.skynet.rimp.tran.vo.PatFeedback;

/**
 * @ClassName: tranRestful.java
 * @Description: TODO用一句话描述该文件做什么
 * @Date: 2017-4-17 下午1:32:28
 * 
 * @author: Administrator
 * @version:
 * @since : JDK 1.7
 */
@Controller
@RequestMapping(value = "/rimp/questionBankMaintenance/")
public class databaseMaintenance {
	protected Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * 跳转到题库维护页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "doctorBeedback/qbank_index";
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
		result = HttpRequest.doPost(param + "shs/hosfeedback/findpage", Objec);
		pageList = JSONObject.toJavaObject(JSONObject.parseObject(result), Pagination.class);
		return pageList;
	}

	/**
	 * 跳转到新增页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "hosFeedback_add.do", method = RequestMethod.GET)
	public String hosFeedback_add() {
		//获取机构编码
		String code = UserUtil.getAuthCode();
		//0表示admin
		if(!code.trim().equals("0")){ // 普通用户 
			return "doctorBeedback/qbank_add";
		}else{
			return "doctorBeedback/qbank_add_forAdmin";
		}
	}

	/**
	 * 保存题库维护信息
	 * 
	 * @param HosFeedback
	 * @return
	 */
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(HosFeedback hosFeedback) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//获取机构编码
			String orgId = UserUtil.getAuthCode().trim();
		    if(!orgId.equals("0")){ //角色为：普通用户,如果是admin，orgId为对应的appId
		    	hosFeedback.setOrgid(orgId);
		    }
			String result = "";
			Properties prop = new Properties();
			InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
			prop.load(in);
			String param = prop.getProperty("url").trim();
			result = HttpRequest.post(param + "shs/hosfeedback/insert", JSONObject.toJSONString(hosFeedback));
			JSONObject oj = JSONObject.parseObject(result);
			if (oj.containsKey("message")) {
				if (oj.getString("message").equals("新增成功")) {
					map.put("state", "success");
					map.put("message", "保存成功");
				}

			} else {
				map.put("state", "fail");
				map.put("message", "保存失败");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "保存失败");
		}
		return map;
	}

	/**
	 * 跳转到题库维护修改页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "qbank_edit.do", method = RequestMethod.GET)
	public String qbank_edit(HttpServletRequest requeset) {	
			return "doctorBeedback/qbank_edit";

	}
	
	/**
	 * 跳转到题库维护修改页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "qbank_edit_for_appId.do", method = RequestMethod.GET)
	public String qbank_edit_for_appId(HttpServletRequest requeset) {	
		return "doctorBeedback/qbank_edit_forAdmin";
		
	}

	/**
	 * 修改题库维护信息
	 * 
	 * @param hosInfo
	 * @return
	 */
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(HosFeedback hosFeedback) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			//获取机构编码
			String orgId = UserUtil.getAuthCode().trim();
		    if(!orgId.equals("0")){ //角色为：普通用户,如果是admin，orgId为对应的appId
		    	hosFeedback.setOrgid(orgId);
		    }else{//角色：admin
		    	String appId = hosFeedback.getAppId();
		    	hosFeedback.setOrgid(appId);
		    }
			String result = "";
			Properties prop = new Properties();
			InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
			prop.load(in);
			String param = prop.getProperty("url").trim();
			String str = JSONObject.toJSONString(hosFeedback);
			result = HttpRequest.doPost(param + "shs/hosfeedback/update", JSONObject.toJSONString(hosFeedback));
			JSONObject oj = JSONObject.parseObject(result);
			if (oj.containsKey("message")) {
				if (oj.getString("message").equals("更新成功")) {
					map.put("state", "success");
					map.put("message", "修改成功");
				}

			} else {
				map.put("state", "fail");
				map.put("message", "修改失败");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "修改失败");
		}
		return map;
	}

	/**
	 * 删除方法
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "delete.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String[] ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String result = "";
			Properties prop = new Properties();
			InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
			prop.load(in);
			String param = prop.getProperty("url").trim();
			if(ids!=null){
				if(ids.length == 1){ //只删除一个
					HosFeedback hosFeedback = new HosFeedback();
					hosFeedback.setHfid(ids[0]);
					result = HttpRequest.doPost(param+ "shs/hosfeedback/delete", JSONObject.toJSONString(hosFeedback));
				}
				if(ids.length>1){ //删除多个 
					for (int i = 0; i < ids.length; i++) {
						HosFeedback h = new HosFeedback();
						h.setHfid(ids[i]);
						result = HttpRequest.doPost(param+ "shs/hosfeedback/delete", JSONObject.toJSONString(h));
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
}
