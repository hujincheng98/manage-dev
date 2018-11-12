/** 
 * Project Name:rimp_V1.0 
 * File Name:backStageManagementServiceImpl.java 
 * Package Name:com.skynet.rimp.tran.service.impl 
 * Date:2017-5-2上午9:22:55 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */  
package com.skynet.rimp.tran.service.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.his.utils.HttpRequest;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.tran.action.databaseMaintenance;
import com.skynet.rimp.tran.service.BackStageManagementService;
import com.skynet.rimp.tran.vo.PatFeedback;
import com.skynet.rimp.tran.vo.PatFeedbackQuestion;

/**
 * @ClassName:		backStageManagementServiceImpl.java
 * @Description:	TODO用一句话描述该文件做什么 
 * @Date:           2017-5-2 上午9:22:55 
 * 
 * @author:			Administrator
 * @version:		 
 * @since :			JDK 1.7 
 */
@Service
public class BackStageManagementServiceImpl implements BackStageManagementService {
	
	/**
	 * 导出excel表格
	 */
	public void getExportExcel(HttpServletRequest request,HttpServletResponse response,SearchParams params) throws Exception {		    								
		//获取查询条件参数
		String createdateStartDate = request.getParameter("replydateStartDate");
		String createdateEndDate = request.getParameter("replydateEndDate");
		String department = request.getParameter("department");
		String isreply = request.getParameter("isreply");
		String fb = request.getParameter("fbtype");
		String chid = request.getParameter("chid");
		if(chid.trim().equals("null") || chid.trim().equals("undefined")){
			chid = "";
		}
		//获取机构编码
		String code = UserUtil.getAuthCode();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		if(code !=null){
			//0表示admin
			if(code.trim().equals("0")){
				searchParams.put("orgid", "");
			}else{
				searchParams.put("orgid", code);
			}
		}
		searchParams.put("createdateStartDate", createdateStartDate);
		searchParams.put("createdateEndDate", createdateEndDate);
		searchParams.put("department", department);
		searchParams.put("isreply", isreply);
		searchParams.put("chid", chid);
		searchParams.put("fbtype", fb);
		
		params.setSearchParams(searchParams);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		params.setPage(1);
		params.setRows(5000);
		Pagination<PatFeedback> pageList = new Pagination<PatFeedback>();
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
		List<PatFeedback> patFeedbacks = JSONArray.parseArray(JSONObject.toJSONString(pageList.getRows()), PatFeedback.class);				
		//开始excel导出
		String fname = "就医反馈信息表";
	    OutputStream os = response.getOutputStream();//取得输出流
	    response.reset();//清空输出流	    
	    //下面是对中文文件名的处理
	    response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
	    //fname = java.net.URLEncoder.encode(fname,"UTF-8");
	    //response.setHeader("Content-Disposition","attachment;filename="+new String(fname.getBytes("UTF-8"),"GBK")+".xls");
	    response.setHeader("Content-Disposition", "attachment;filename=" + new String(fname.getBytes(),"iso-8859-1") + ".xls");  
	    response.setContentType("application/msexcel");//定义输出类型
		//创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("First Sheet",0);   
        //设置字体种类和格式
        WritableFont bold = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
        WritableCellFormat wcfFormat = new WritableCellFormat(bold);
        //wcfFormat.setAlignment(jxl.format.Alignment.CENTRE);//单元格中的内容水平
        
        //设置第3列单元格宽
        //sheet.setColumnView(4, 50);
        //设置第三行单元格高
        //sheet.setRowView(4, 50);       
        //创建要显示的内容,创建一个单元格，第一个参数为列坐标，第二个参数为行坐标，第三个参数为内容
        //表头
        Label title = new Label(0,0,"标题",wcfFormat);
        sheet.addCell(title);
        //设置某个单元格宽
	    sheet.setColumnView(0, 20);
        Label fbtype = new Label(1,0,"反馈类型",wcfFormat);
        sheet.addCell(fbtype);
        Label patientname = new Label(2,0,"患者姓名",wcfFormat);
        sheet.addCell(patientname);
        //设置某个单元格宽
	    sheet.setColumnView(2, 10);
        Label telephone = new Label(3,0,"电话",wcfFormat);
        sheet.addCell(telephone);
        //设置某个单元格宽
	    sheet.setColumnView(3, 20);
        Label departments = new Label(4,0,"科室名称",wcfFormat);
        //设置某个单元格宽
	    sheet.setColumnView(4, 20);
        sheet.addCell(departments);
        Label reply = new Label(5,0,"反馈方式",wcfFormat);
        sheet.addCell(reply);
        Label replydate = new Label(6,0,"反馈时间",wcfFormat);
        sheet.addCell(replydate);
        //设置某个单元格宽
	    sheet.setColumnView(6, 30);
        Label isreplys = new Label(7,0,"状态",wcfFormat);
        sheet.addCell(isreplys);
        Label content = new Label(8,0,"反馈意见",wcfFormat);
        sheet.addCell(content);
        Label replyOpp = new Label(9,0,"回复内容",wcfFormat);
        sheet.addCell(replyOpp);
        sheet.setColumnView(9, 70);
        
        //存放每位患者的问题
        List<PatFeedbackQuestion> onePeopQueArr = new ArrayList<PatFeedbackQuestion>();
        //存放所有患者的问题
        List<List<PatFeedbackQuestion>> allPeopQueArr = new ArrayList<List<PatFeedbackQuestion>>();
        //某个患者回答问题最多的个数
        int maxNum = 0;
        //获取某患者下最多问题的总数
        for (int i = 0; i < patFeedbacks.size(); i++) {
        	//查询某患者对应答题数
        	String pfid = patFeedbacks.get(i).getPfid();
        	PatFeedbackQuestion pat = new PatFeedbackQuestion();
        	pat.setPfid(pfid);
        	//调用查询接口
        	result = HttpRequest.sendPost(param+ "shs/patfeedbackquestion/findbycon", JSONObject.toJSONString(pat));
        	if(!result.trim().equals("") && result!= null){
        		//每位患者对应的答题
            	onePeopQueArr = JSONArray.parseArray(result, PatFeedbackQuestion.class);
        	}
        	if(onePeopQueArr.size()>0){
        		if(onePeopQueArr.size() > maxNum){
            		maxNum = onePeopQueArr.size();
            	}
            	allPeopQueArr.add(onePeopQueArr);   
        	}
        }
        
        System.out.println("最大个数"+maxNum);
       if(maxNum>0){
    	   int queNum = 1;
    	   for (int k = 10; k < (maxNum*2)+10; k+=2) {
    		  //遍历每一道题问题和答案
    		  Label que = new Label(k,0,"第"+queNum+"题问题",wcfFormat);
    	      sheet.addCell(que);
    	      //设置某个单元格宽
    	      sheet.setColumnView(k, 70);
    	      //设置某个单元格高
    	      //sheet.setRowView(7+k, 100);  
    	      Label answer = new Label(k+1,0,"第"+queNum+"题答案",wcfFormat);
    	      sheet.addCell(answer);
    	      sheet.setColumnView(k+1, 50);
    	      queNum++;
   		}
    	   queNum = 1;
       }
        
        //填充每行数据
        for (int j = 1; j <= patFeedbacks.size(); j++) {
        	//标题
            if(patFeedbacks.get(j-1).getTitle() !=null){
            	Label titl = new Label(0,j,patFeedbacks.get(j-1).getTitle(),wcfFormat);
       			sheet.addCell(titl);
            }else{
            	Label titl = new Label(0,j,"",wcfFormat);
       			sheet.addCell(titl);
            }	
        	
       	   //反馈类型
            if(patFeedbacks.get(j-1).getFbtype() !=null){
            	String fbType = patFeedbacks.get(j-1).getFbtype();
            	//adtype_01:咨询,adtype_02:建议,adtype_03:投诉,adtype_04:其他
            	switch (fbType) {
				case "adtype_01":
					fbType = "咨询";
					break;
				case "adtype_02":
					fbType = "建议";
					break;
				case "adtype_03":
					fbType = "投诉";
					break;
				case "adtype_04":
					fbType = "其他";
					break;                       
				default:
					fbType = "";
					break;
				}
            	Label fbTyp = new Label(1,j,fbType,wcfFormat);
       			sheet.addCell(fbTyp);
            }else{
            	Label fbTyp = new Label(1,j,"",wcfFormat);
       			sheet.addCell(fbTyp);
            }
        	
        	Label pname = new Label(2,j,patFeedbacks.get(j-1).getPatientname(),wcfFormat);
        	sheet.addCell(pname);
        	Label phone = new Label(3,j,patFeedbacks.get(j-1).getTelephone(),wcfFormat);
        	sheet.addCell(phone);
        	Label dept = new Label(4,j,patFeedbacks.get(j-1).getDepartment(),wcfFormat);
        	sheet.addCell(dept);
        	Label child = new Label(5,j,patFeedbacks.get(j-1).getChName(),wcfFormat);
        	sheet.addCell(child);
        	//格式化时间 
        	Date sd = patFeedbacks.get(j-1).getCreatedate();
        	if(sd!=null){
        		String d = sdf.format(patFeedbacks.get(j-1).getCreatedate());
	       		Label date = new Label(6,j,d,wcfFormat);
	       		sheet.addCell(date);
        	}else{
        		Label date = new Label(6,j,"",wcfFormat);
        		sheet.addCell(date);
        	}       		
         	//是否回复
       		String isReply = "";
       		if(patFeedbacks.get(j-1).getIsreply()!=null){
       			if(patFeedbacks.get(j-1).getIsreply() == 0){
	       			 isReply = "未回复";
	       		 }else{
	       			 isReply = "已回复";
	       		 }
	       		Label isRe = new Label(7,j,isReply,wcfFormat);
	       		sheet.addCell(isRe);
       		}else{
       			Label isRe = new Label(7,j,"",wcfFormat);
	       		sheet.addCell(isRe);
       		}
       		//反馈意见
       		if(patFeedbacks.get(j-1).getContent() !=null){
       			Label con = new Label(8,j,patFeedbacks.get(j-1).getContent(),wcfFormat);
	       		sheet.addCell(con);
       		}else{
       			Label con = new Label(8,j,"",wcfFormat);
	       		sheet.addCell(con);
       		}
       		//回复内容
       		if(patFeedbacks.get(j-1).getReply() !=null){
       			String re = patFeedbacks.get(j-1).getReply();
       			//截取回复内容中文
       			String reg = "[^\u4e00-\u9fa5]";
       			re = re.replaceAll(reg, "");
       			String  nowReply = re.replaceAll("微软雅黑", "");
                System.out.println(nowReply);
       			Label rep = new Label(9,j,nowReply,wcfFormat);
       			sheet.addCell(rep);
       		}else{
       			Label rep = new Label(9,j,"",wcfFormat);
       			sheet.addCell(rep);
       		}
       	}
       
        //填充每行患者对应回答的问题
   		if(allPeopQueArr.size()>0){
   			for (int x = 1; x <= allPeopQueArr.size(); x++) {
   				List<PatFeedbackQuestion> plist = allPeopQueArr.get(x-1);
   				int patQueNum = 0;
   				for (int g =10; g < (plist.size()*2)+10; g+=2) {
       				//循环每个问题
   					String eque = plist.get(patQueNum).getQuestion();
   					//答案
   					String evalue =  plist.get(patQueNum).getValue();
   					//满意：1,基本满意：2, 一般：3, 不满意：4
   					switch (evalue) {
					case "1":
						evalue = "满意";
						break;
					case "2":
						evalue = "基本满意";
						break;
					case "3":
						evalue = "一般";
						break;
					case "4":
						evalue = "不满意";
						break;                       
					default:
						evalue = "";
						break;
					}
   					Label everyQuesion = new Label(g,x,"问题:"+eque,wcfFormat);
   					sheet.addCell(everyQuesion);
   					Label everyAnswer = new Label(g+1,x,"答案:"+evalue,wcfFormat);
   					sheet.addCell(everyAnswer);
   					patQueNum++;
    			}
			}
   		}
        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();	
	}	
}
