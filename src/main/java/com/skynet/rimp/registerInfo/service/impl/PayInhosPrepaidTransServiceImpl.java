/*
 * @(#) PayInhosPrepaidTransServiceImpl  2017-06-27 11:27:43
 *
 * Copyright 2003 by TiuWeb Corporation.
 * 51 zhangba six Road, xian, PRC 710065 // Replace with xian’s address
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * TiuWeb Corporation.  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with TiuWeb.
 */
package com.skynet.rimp.registerInfo.service.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.string.StringUtil;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.registerInfo.dao.PayInhosPrepaidTransDao;
import com.skynet.rimp.registerInfo.service.IPayInhosPrepaidTransService;
import com.skynet.rimp.registerInfo.vo.PayInhosPrepaidTrans;

/**
 * 
 * <p>Title: his日志</p>
 * <p>Description: TODO his日志Impl层
 *
 * @author llt
 * @version 1.00.00 创建时间：2017-06-27 11:27:43
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */ 
@Service("payInhosPrepaidTransService")
public class PayInhosPrepaidTransServiceImpl extends BaseServiceImpl<PayInhosPrepaidTrans> implements IPayInhosPrepaidTransService {

	@Autowired
	private PayInhosPrepaidTransDao payInhosPrepaidTransDao;

	@Override
	public List<PayInhosPrepaidTrans> findByCondition(SearchParams params) throws Exception {
		// TODO Auto-generated method stub
		return this.payInhosPrepaidTransDao.findByCondition(params);
	}

	//填充对应的组织机构编码
	public void fillingOrgIdByUser(SearchParams params){
		//获取机构编码
		String code = UserUtil.getAuthCode();
		//0表示admin
		if(!code.trim().equals("0")){
			 if(params.getSearchParams()== null){
			 Map<String, Object> map = new HashMap<String, Object>();
			 map.put("orgId", code);
			 params.setSearchParams(map);
			 }else{
			    params.getSearchParams().put("orgId", code);
			}
		}
	}
	
	public String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		return dateStr;
	}
		
	@Override
	public List<PayInhosPrepaidTrans> findAll() throws Exception {
		// TODO Auto-generated method stub
		return this.payInhosPrepaidTransDao.findAll();
	}

	//返回列表json数据
	@Override
	public Pagination<PayInhosPrepaidTrans> list(SearchParams params) {
		fillingOrgIdByUser(params);
		Pagination<PayInhosPrepaidTrans> pageList = null;
		try {
			pageList = findPageByCondition(params);
			
			List<PayInhosPrepaidTrans> list = pageList.getRows();
			
			for (PayInhosPrepaidTrans pipt : list) {
				//屏蔽身份证号
	        	String idCard = pipt.getIdentityCard();
	        	if(idCard !=null && !idCard.equals("")){
	        		String ic = idCard;//StringUtil.screenIdentityCard(idCard);
		        	pipt.setIdentityCard(ic);
	        	}
	        	//屏蔽电话
	        	String telephone = pipt.getTelephone();
	        	if(telephone !=null && !telephone.equals("")){
	        		String telStr = telephone;//StringUtil.screenTelephone(telephone);
		        	pipt.setTelephone(telStr);
	        	}
			   }			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}

    //导出excel
	@Override
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, SearchParams params) throws Exception {
		fillingOrgIdByUser(params);
		Pagination<PayInhosPrepaidTrans> pageList = findPageByCondition(params);
		List<PayInhosPrepaidTrans> payInhosPrepaidTrans = pageList.getRows();
		if(payInhosPrepaidTrans.size()>0){
			//开始excel导出
			String fname = "住院预交金充值交易信息";
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
			
			//创建表头
			Label serialNumberColumn = new Label(0,0,"支付流水号",wcfFormat);
			sheet.addCell(serialNumberColumn);
			//设置某个单元格宽
			sheet.setColumnView(0, 20);
			
			Label hisserialNumberColumn = new Label(1,0,"his充值流水号",wcfFormat);
			sheet.addCell(hisserialNumberColumn);
			//设置某个单元格宽
			sheet.setColumnView(1, 20);
			
			Label inHosIdColumn = new Label(2,0,"住院号",wcfFormat);
			sheet.addCell(inHosIdColumn);
			//设置某个单元格宽
			sheet.setColumnView(2, 20);
			
			Label payDateColumn = new Label(3,0,"充值时间",wcfFormat);
			sheet.addCell(payDateColumn);
			//设置某个单元格宽
			sheet.setColumnView(3, 20);
			
			Label patientNameColumn = new Label(4,0,"患者姓名",wcfFormat);
			sheet.addCell(patientNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(4, 20);
			
			Label identityCardColumn = new Label(5,0,"身份证号",wcfFormat);
			sheet.addCell(identityCardColumn);
			//设置某个单元格宽
			sheet.setColumnView(5, 20);
			
			Label telephoneColumn = new Label(6,0,"联系电话",wcfFormat);
			sheet.addCell(telephoneColumn);
			//设置某个单元格宽
			sheet.setColumnView(6, 20);
			
			Label moneyColumn = new Label(7,0,"充值金额",wcfFormat);
			sheet.addCell(moneyColumn);
			//设置某个单元格宽
			sheet.setColumnView(7, 20);
			
			Label payTypeNameColumn = new Label(8,0,"充值方式",wcfFormat);
			sheet.addCell(payTypeNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(8, 20);
			
			Label transStateNameColumn = new Label(9,0,"充值状态",wcfFormat);
			sheet.addCell(transStateNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(9, 20);
			
			Label chNameColumn = new Label(10,0,"渠道名称",wcfFormat);
			sheet.addCell(chNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(10, 20);
									
			//加载每行数据
			for (int i = 1; i <= payInhosPrepaidTrans.size(); i++) {
				Label serialNumber = new Label(0,i,payInhosPrepaidTrans.get(i-1).getSerialNumber(),wcfFormat);
				sheet.addCell(serialNumber);
				Label hisserialNumber = new Label(1,i,payInhosPrepaidTrans.get(i-1).getHisserialNumber(),wcfFormat);
				sheet.addCell(hisserialNumber);
				Label inHosId = new Label(2,i,payInhosPrepaidTrans.get(i-1).getInHosId(),wcfFormat);
				sheet.addCell(inHosId);
				Date pd = payInhosPrepaidTrans.get(i-1).getPayDate();
				if(pd!=null){
					Label payDate = new Label(3,i,formatDate(pd),wcfFormat);
					sheet.addCell(payDate);	
				}
				Label patientName = new Label(4,i,payInhosPrepaidTrans.get(i-1).getPatientName(),wcfFormat);
				sheet.addCell(patientName);
				
				//身份证号屏蔽
				String idCard = payInhosPrepaidTrans.get(i-1).getIdentityCard();
				if(idCard!=null && !idCard.equals("")){
					String ic = idCard;//StringUtil.screenIdentityCard(idCard);
					Label identityCard = new Label(5,i,ic,wcfFormat);
					sheet.addCell(identityCard);
				}
				/*Label identityCard = new Label(5,i,payInhosPrepaidTrans.get(i-1).getIdentityCard(),wcfFormat);
				sheet.addCell(identityCard);*/
				
				//电话号屏蔽四位
				String tel = payInhosPrepaidTrans.get(i-1).getTelephone();
				if(tel!=null && !tel.equals("")){
					String telStr = tel;// StringUtil.screenTelephone(tel);
					Label telephone = new Label(6,i,telStr,wcfFormat);
					sheet.addCell(telephone);
				}
				/*Label telephone = new Label(6,i,payInhosPrepaidTrans.get(i-1).getTelephone(),wcfFormat);
				sheet.addCell(telephone);*/
				
				if(payInhosPrepaidTrans.get(i-1).getMoney()!=null){
					Label money = new Label(7,i,payInhosPrepaidTrans.get(i-1).getMoney().toString(),wcfFormat);
					sheet.addCell(money);
				}
				Label payTypeName = new Label(8,i,payInhosPrepaidTrans.get(i-1).getPayTypeName(),wcfFormat);
				sheet.addCell(payTypeName);
				Label transStateName = new Label(9,i,payInhosPrepaidTrans.get(i-1).getTransStateName(),wcfFormat);
				sheet.addCell(transStateName);
				Label chName = new Label(10,i,payInhosPrepaidTrans.get(i-1).getChName(),wcfFormat);
				sheet.addCell(chName);				
			}
			//把创建的内容写入到输出流中，并关闭输出流
	        workbook.write();
	        workbook.close();
	        os.close();
		}		
	}

}
