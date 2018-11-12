/** 
 * Project Name:rimp_V1.0 
 * File Name:signDoctor.java 
 * Package Name:com.skynet.rimp.tran.action 
 * Date:2017-5-18下午5:08:26 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */  
package com.skynet.rimp.tran.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
import com.skynet.rimp.common.utils.HttpRequest;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.hisBaseInfo.service.IHosInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;
import com.skynet.rimp.tran.vo.SignDoctor;
import com.skynet.rimp.tran.vo.SignDoctorApply;
import com.skynet.rimp.tran.vo.VacPatAppointmentListForRimp;

/**
 * @ClassName:		signDoctor.java
 * @Description:	TODO用一句话描述该文件做什么 
 * @Date:           2017-5-18 下午5:08:26 
 * 
 * @author:			Administrator
 * @version:		 
 * @since :			JDK 1.7 
 */
@Controller
@RequestMapping(value = "/rimp/signDoctor/")
public class signDoctor {
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	@Autowired
	private IHosInfoService hosInfoService;
	
	private String result = "";
	
	Pagination<T> pageList = new Pagination<T>();
	
	/**
	 * 分页查询签约申请页面
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public Object list(SearchParams params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ModelAndView view = new ModelAndView();
		String code = UserUtil.getAuthCode();
		if(!code.trim().equals("0")){
			HosInfo hosInfo=hosInfoService.findByHosOrgId(code);
			view.addObject("hostId", hosInfo.getHosId());
		}
		view.setViewName("signDoctor/sign_doctor_index");
		return view;
	}
	
	/**
	 * 分页查询签约详情
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "info.json", method = RequestMethod.POST)
	@ResponseBody
	public Object info(SearchParams params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//获取机构编码
		String code = UserUtil.getAuthCode();
		
		//0表示admin
		if(!code.trim().equals("0")){
			HosInfo hosInfo=hosInfoService.findByHosOrgId(code);
			if(params.getSearchParams()== null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("hosId", hosInfo.getHosId());
				params.setSearchParams(map);
			}else{
			    params.getSearchParams().put("hosId", hosInfo.getHosId());
			}
		}
		String Objec = JSONObject.toJSONString(params);
		Properties prop = new Properties();
		InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
		prop.load(in);
		String param = prop.getProperty("urlDhp").trim();
		result = HttpRequest.doPost(param + "dhp/signDoctor/applay/list", Objec);
		pageList = JSONObject.toJavaObject(JSONObject.parseObject(result), Pagination.class);
		return pageList;
	}
	
	//写入excel简单示例,前端不能用ajax请求
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "excel.json")
	public ModelAndView ExcelWrite(HttpServletRequest request, HttpServletResponse response){
		try {
			String fname = "家庭医生信息";
		    OutputStream os = response.getOutputStream();//取得输出流
		    response.reset();//清空输出流	    
		    
		    //下面是对中文文件名的处理
		    response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
		
		    response.setHeader("Content-Disposition", "attachment;filename=" + new String(fname.getBytes(),"iso-8859-1") + ".xls");  
		    response.setContentType("application/msexcel");//定义输出类型
			//创建工作薄
	        WritableWorkbook workbook = Workbook.createWorkbook(os);
	        
			pageList = JSONObject.toJavaObject(JSONObject.parseObject(result), Pagination.class);
			List<SignDoctor> signDoctors = new ArrayList<SignDoctor>();
			signDoctors = JSONArray.parseArray(JSONObject.toJSONString(pageList.getRows()), SignDoctor.class);
	        //创建新的一页
	        WritableSheet sheet = workbook.createSheet("First Sheet",0);
	        //设置字体种类和格式
	        WritableFont bold = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
	        WritableCellFormat wcfFormat = new WritableCellFormat(bold);
	        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
	        Label l1 = new Label(0,0,"签约人姓名",wcfFormat);
	        sheet.addCell(l1);
	        sheet.setColumnView(0, 15);
	        Label l2 = new Label(1,0,"签约人身份证号",wcfFormat);
	        sheet.addCell(l2);
	        sheet.setColumnView(1, 25);
	        Label l3 = new Label(2,0,"签约人联系电话",wcfFormat);
	        sheet.addCell(l3);
	        sheet.setColumnView(2, 17);
	        Label l4 = new Label(3,0,"签约人所在地区",wcfFormat);
	        sheet.addCell(l4);
	        sheet.setColumnView(3, 30);
	        Label l5 = new Label(4,0,"签约人详细地址",wcfFormat);
	        sheet.addCell(l5);
	        sheet.setColumnView(4, 40);
	        Label l6 = new Label(5,0,"签约预约日期",wcfFormat);
	        sheet.addCell(l6);
	        sheet.setColumnView(5, 20);
	        Label l7 = new Label(6,0,"签约医生姓名",wcfFormat);
	        sheet.addCell(l7);
	        sheet.setColumnView(6, 15);
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        for (int i = 0; i < signDoctors.size(); i++) {
	        	SignDoctor sign = new SignDoctor();	        
	        	sign = signDoctors.get(i);
	        	if(sign != null)
	        	{
		        	Label userName = new Label(0,i+1,sign.getUserName(),wcfFormat);
   					sheet.addCell(userName);  					
		        	Label idCard = new Label(1,i+1,sign.getIdCard(),wcfFormat);
   					sheet.addCell(idCard);
		        	Label telePhone = new Label(2,i+1,sign.getTelePhone(),wcfFormat);
   					sheet.addCell(telePhone);
		        	Label streetName = new Label(3,i+1,sign.getStreetName(),wcfFormat);
   					sheet.addCell(streetName);
		        	Label detailedAddress = new Label(4,i+1,sign.getDetailedAddress(),wcfFormat);
   					sheet.addCell(detailedAddress);
   					
   					//格式化时间 
   		        	Date sd = sign.getSignedTime();
   		        	if(sd!=null){
   		        		String d = sdf.format(sd);
   		        		Label signedTime = new Label(5,i+1,d,wcfFormat);
   	   					sheet.addCell(signedTime);
   		        	}else{
   		        		Label signedTime = new Label(5,i+1,"",wcfFormat);
   	   					sheet.addCell(signedTime);
   		        	} 	   							
		        	Label docName = new Label(6,i+1,sign.getDocName(),wcfFormat);
   					sheet.addCell(docName);
	        	} 					
	        }
	        
	        //把创建的内容写入到输出流中，并关闭输出流
	        workbook.write();
	        workbook.close();
	        os.close();
		} catch (Exception e) {
			// TODO: handle exception
		}		
		return null;
	}
}
