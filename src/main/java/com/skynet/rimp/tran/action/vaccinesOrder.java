/** 
 * Project Name:rimp_V1.0 
 * File Name:communityOrder.java 
 * Package Name:com.skynet.rimp.tran.action 
 * Date:2017-5-19下午3:16:43 
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

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
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
import com.skynet.rimp.tran.vo.VacPatAppointmentListForRimp;

/**
 * @ClassName:		communityOrder.java
 * @Description:	TODO用一句话描述该文件做什么 
 * @Date:           2017-5-19 下午3:16:43 
 * 
 * @author:			Administrator
 * @version:		 
 * @since :			JDK 1.7 
 */
@Controller
@RequestMapping(value = "/rimp/vaccinesOrder/")
public class vaccinesOrder {
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	@Autowired
	private IHosInfoService hosInfoService;
	
	private String result = "";
	
	Pagination<T> pageList = new Pagination<T>();
	
	/**
	 * 分页查询页面
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public Object info(SearchParams params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		ModelAndView view = new ModelAndView();
		String code = UserUtil.getAuthCode();
		if(!code.trim().equals("0")){
			HosInfo hosInfo=hosInfoService.findByHosOrgId(code);
			view.addObject("hostId", hosInfo.getHosId());
		}
		view.setViewName("vaccinesOrder/vaccines_order_index");
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
	public Object infoo(SearchParams params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//Pagination<T> pageList = new Pagination<T>();
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
		//String result = "";
		Properties prop = new Properties();
		InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
		prop.load(in);
		String param = prop.getProperty("urlDhp").trim();
		result = HttpRequest.doPost(param + "dhp/vaccines/appointment/all/list", Objec);
		pageList = JSONObject.toJavaObject(JSONObject.parseObject(result), Pagination.class);
		return pageList;
	}
	
	
	//写入excel简单示例,前端不能用ajax请求
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "excel.json")
	public ModelAndView ExcelWrite(HttpServletRequest request, HttpServletResponse response){
		try {
			String fname = "疫苗接种信息";
		    OutputStream os = response.getOutputStream();//取得输出流
		    response.reset();//清空输出流	    
		    
		    //下面是对中文文件名的处理
		    response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
		
		    response.setHeader("Content-Disposition", "attachment;filename=" + new String(fname.getBytes(),"iso-8859-1") + ".xls");  
		    response.setContentType("application/msexcel");//定义输出类型
			//创建工作薄
	        WritableWorkbook workbook = Workbook.createWorkbook(os);
	        
			pageList = JSONObject.toJavaObject(JSONObject.parseObject(result), Pagination.class);
			List<VacPatAppointmentListForRimp> VacPatList = new ArrayList<VacPatAppointmentListForRimp>();
			VacPatList = JSONArray.parseArray(JSONObject.toJSONString(pageList.getRows()), VacPatAppointmentListForRimp.class);
	        //创建新的一页
	        WritableSheet sheet = workbook.createSheet("First Sheet",0);
	        //设置字体种类和格式
	        WritableFont bold = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
	        WritableCellFormat wcfFormat = new WritableCellFormat(bold);
	        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
	        Label l1 = new Label(0,0,"接种人姓名",wcfFormat);
	        sheet.addCell(l1);
	        sheet.setColumnView(0, 15);
	        
	        Label l2 = new Label(1,0,"接种人出生日期",wcfFormat);
	        sheet.addCell(l2);
	        sheet.setColumnView(1, 20);
	        
	        Label l3 = new Label(2,0,"联系电话",wcfFormat);
	        sheet.addCell(l3);
	        sheet.setColumnView(2, 17);
	        
	        Label l4 = new Label(3,0,"接种完成日期",wcfFormat);
	        sheet.addCell(l4);
	        sheet.setColumnView(3, 20);
	        
	        Label l5 = new Label(4,0,"预约接种日期",wcfFormat);
	        sheet.addCell(l5);
	        sheet.setColumnView(4, 20);
	        
	        Label l6 = new Label(5,0,"接种疫苗内容",wcfFormat);
	        sheet.addCell(l6);
	        sheet.setColumnView(5, 60);
	        
	        Label l7 = new Label(6,0,"状态",wcfFormat);
	        sheet.addCell(l7);
	        sheet.setColumnView(6, 60);
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        for (int i = 0; i < VacPatList.size(); i++) {
	        	VacPatAppointmentListForRimp VacPat = new VacPatAppointmentListForRimp();
	        
	        	VacPat = VacPatList.get(i);
	        	if(VacPat != null)
	        	{
		        	Label userName = new Label(0,i+1,VacPat.getUserName(),wcfFormat);
   					sheet.addCell(userName);
   					
   		        	//格式化时间 
   		        	Date sd = VacPat.getUserBirthday();
   		        	if(sd!=null){
   		        		String d = sdf.format(sd);
   		        		Label userBirthday = new Label(1,i+1,d,wcfFormat);
	   					sheet.addCell(userBirthday);
   		        	}else{
   		        		Label userBirthday = new Label(1,i+1,"",wcfFormat);
	   					sheet.addCell(userBirthday);
   		        	} 	   					
   					
		        	Label telephone = new Label(2,i+1,VacPat.getUserTelephone(),wcfFormat);
   					sheet.addCell(telephone);
   					
   		        	//格式化时间 
   		        	Date vac = VacPat.getVacDate();
   		        	if(vac!=null){
   		        		String s = sdf.format(vac);
   		        		Label vacDate = new Label(3,i+1,s,wcfFormat);
	   					sheet.addCell(vacDate);
   		        	}else{
   		        		Label vacDate = new Label(3,i+1,"",wcfFormat);
	   					sheet.addCell(vacDate);
   		        	} 
   		        	
  		        	//格式化时间 
   		        	Date ap = VacPat.getApDate();
   		        	if(ap!=null){
   		        		String aps = sdf.format(ap);
   		        		Label apDate = new Label(4,i+1,aps,wcfFormat);
	   					sheet.addCell(apDate);
   		        	}else{
   		        		Label apDate = new Label(4,i+1,"",wcfFormat);
	   					sheet.addCell(apDate);
   		        	} 
   		        	
		        	Label vacName = new Label(5,i+1,VacPat.getVacName(),wcfFormat);
   					sheet.addCell(vacName);
   					
   					if(VacPat.getStates() == "0")
   					{
   						Label states = new Label(6,i+1,"已取消",wcfFormat);
   						sheet.addCell(states);
   					}
   					else if(VacPat.getStates() == "1")
   					{
   						Label states = new Label(6,i+1,"已预约",wcfFormat);
   						sheet.addCell(states);
   					}
   					else if(VacPat.getStates() == "2")
   					{
   						Label states = new Label(6,i+1,"已接种",wcfFormat);
   						sheet.addCell(states);
   					}
   					else
   					{
   						Label states = new Label(6,i+1,"全部",wcfFormat);
   						sheet.addCell(states);
   					} 					
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
