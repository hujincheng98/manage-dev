/** 
 * Project Name:rimp_V1.0 
 * File Name:MobilePaymentServiceImpl.java 
 * Package Name:com.skynet.rimp.tran.service.impl 
 * Date:2017-6-6上午9:39:59 
 * Copyright (c) 2017 by [天网软件股份有限公司] 
 * 
 */  
package com.skynet.rimp.tran.service.impl;

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

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.his.utils.HttpRequest;
import com.skynet.rimp.channelInfo.service.IOtherChannelsInfoService;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.hisBaseInfo.service.IHosInfoService;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;
import com.skynet.rimp.tran.action.databaseMaintenance;
import com.skynet.rimp.tran.service.MobilePaymentService;
import com.skynet.rimp.tran.vo.PayTrateOrder;

/**
 * @ClassName:		MobilePaymentServiceImpl.java
 * @Description:	TODO用一句话描述该文件做什么 
 * @Date:           2017-6-7 上午9:39:59 
 * 
 * @author:			llt
 * @version:		 
 * @since :			JDK 1.7 
 */
@Service
public class MobilePaymentServiceImpl implements MobilePaymentService {
	
	@Autowired
	private IOtherChannelsInfoService otherChannelsInfoService;
	
	@Autowired
	private IHosInfoService hosInfoService;
	

	//转发接口
	public String transmitInterface(String url,Object obj) throws IOException{
		String Objec = JSONObject.toJSONString(obj);
		String result = "";
		Properties prop = new Properties();
		InputStream in = databaseMaintenance.class.getResourceAsStream("/config.properties");
		prop.load(in);
		String param = prop.getProperty("url").trim(); 
		//String param = "http://localhost:8089/";
		System.out.println(Objec.toString());
		result = HttpRequest.sendPost(param+ url, Objec);	
		return result;
	}
	
	//格式化时间
	public String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
	//填充对应的组织机构编码
	public void fillingOrgIdByUser(SearchParams params){
		        //获取机构编码
				String code = UserUtil.getAuthCode();
				String hosid = null;
				//根据orgid获取医院id
				HosInfo hosInfo = hosInfoService.findByHosOrgId(code);
				if(hosInfo!=null){
					hosid = hosInfo.getHosId();
				}
				//0表示admin
				if(!code.trim().equals("0")){
					if(params.getSearchParams()== null){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("hosid", hosid);
						params.setSearchParams(map);
					}else{
						params.getSearchParams().put("hosid", hosid);
					}
				}
	}
	
	//导出excel
	@SuppressWarnings("unchecked")
	@Override
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, SearchParams params) throws Exception {
		Pagination<PayTrateOrder> pageList = new Pagination<PayTrateOrder>();
		
		//填充对应的组织机构编码
		fillingOrgIdByUser(params);
		
		String result = transmitInterface("shs/paytrateorder/findpage", params);
		
		//String result = "{\"total\":1,\"rows\":[{\"ptoID\":\"AD5B7D00D1E64539AA4E036C8F1A3F94\",\"chToken\":\"c75d08d7fba64c0eae52f40d21d9863f\",\"hosID\":\"43d858f232bc41999cc7e79fb7328890\",\"orgID\":\"610001\",\"tradeID\":\"dc817D00D1E64539AA4E036C8F1c001\",\"hisTradeID\":\"a2345678069a1c2\",\"mchID\":\"sh0130125\",\"deviceInfo\":\"awc1q03c6\",\"businessType\":\"1\",\"payType\":\"1\",\"tradeType\":\"2\",\"tradeFee\":\"100\",\"authCode\":\"001\",\"chName\":\"西安医专附属医院微信\",\"tradeTime\":\"2017-06-07 15:33:01\",\"tradeExpireTime\":\"2017-06-08 15:33:01\",\"shopID\":\"001\",\"deviceID\":\"s126a3\",\"goodsTag\":\"商品标记\",\"operatorID\":\"user001306\",\"tradeState\":\"1\",\"attach\":\"his备注\",\"resultCode\":\"支付返回信息\",\"errMsg\":\"支付返回错误信息\",\"payResult\":\"0\",\"payInfo\":\"111\",\"nonceStr\":\"随机字符串\",\"tradeCancleTime\":\"2017-06-07 20:33:01\",\"noticeTime\":\"3\"}],\"footer\":null}";
		pageList = JSONObject.toJavaObject(JSONObject.parseObject(result), Pagination.class);
		List<PayTrateOrder> payTrateOrdersList = JSONArray.parseArray(JSONObject.toJSONString(pageList.getRows()), PayTrateOrder.class);
		if(payTrateOrdersList.size()>0){
			//开始excel导出
			String fname = "移动支付交易流水信息表";
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
			Label tradeTimeColumn = new Label(0,0,"订单生成时间",wcfFormat);
			sheet.addCell(tradeTimeColumn);
			//设置某个单元格宽
			sheet.setColumnView(0, 20);
			Label tradeIdColumn = new Label(1,0,"交易订单流水号",wcfFormat);
			sheet.addCell(tradeIdColumn);
			sheet.setColumnView(1, 40);
			Label hisTradeIdColumn = new Label(2,0,"HIS交易流水号",wcfFormat);
			sheet.addCell(hisTradeIdColumn);
			sheet.setColumnView(2, 40);
			//业务类型：也即商品描述，字典项business_type，1-预约取号；2.当日挂号；3-诊间支付；4-门诊预交金充值；5-住院预交金充值
			Label businessTypeColumn = new Label(3,0,"业务类型",wcfFormat);
			sheet.addCell(businessTypeColumn);
			sheet.setColumnView(3, 20);
			//支付类型：1-扫码支付，2-刷卡支付，3-公众号小程序支付，4-app支付，5-h5支付
			Label payTypeColumn = new Label(4,0,"支付类型",wcfFormat);
			sheet.addCell(payTypeColumn);
			sheet.setColumnView(4, 20);
			//支付方式：1-微信支付；2-支付宝支付；3-银联支付
			Label tradeTypeColumn = new Label(5,0,"支付方式",wcfFormat);
			sheet.addCell(tradeTypeColumn);
			sheet.setColumnView(5, 20);
			Label tradeFeeColumn = new Label(6,0,"支付金额",wcfFormat);
			sheet.addCell(tradeFeeColumn);
			sheet.setColumnView(6, 20);
			Label chNameColumn = new Label(7,0,"渠道名称",wcfFormat);
			sheet.addCell(chNameColumn);
			sheet.setColumnView(7, 20);
			//交易状态：0-初始状态，1-交易成功，2-支付成功，-1-支付失败，-2-云平台撤销发起，-3-云平台撤销成功，-4-his撤销发起，-5-his撤销成功
			Label tradeStateColumn = new Label(8,0,"交易状态",wcfFormat);
			sheet.addCell(tradeStateColumn);
			sheet.setColumnView(8, 20);
			
			//加载每行数据
			for (int i = 1; i <= payTrateOrdersList.size(); i++) {
				Date trTime = payTrateOrdersList.get(i-1).getTradeTime();
				if(trTime!=null){
					String tradeTimeStr = formatDate(payTrateOrdersList.get(i-1).getTradeTime());
					Label tradeTime = new Label(0,i,tradeTimeStr,wcfFormat);
					sheet.addCell(tradeTime);
				}
	        	Label tradeId = new Label(1,i,payTrateOrdersList.get(i-1).getTradeID(),wcfFormat);
	        	sheet.addCell(tradeId);
	        	Label hisTradeId = new Label(2,i,payTrateOrdersList.get(i-1).getHisTradeID(),wcfFormat);
	        	sheet.addCell(hisTradeId);
	        	//业务类型：也即商品描述，字典项business_type，1-预约取号；2.当日挂号；3-诊间支付；4-门诊预交金充值；5-住院预交金充值
	        	String busTypeStr = null;
	        	if(payTrateOrdersList.get(i-1).getBusinessType()!=null){
	        		int busType = payTrateOrdersList.get(i-1).getBusinessType();
		        	switch (busType) {
					case 1:
						busTypeStr = "预约取号";
						break;
					case 2:
						busTypeStr = "当日挂号";
						break;
					case 3:
						busTypeStr = "诊间支付";
						break;
					case 4:
						busTypeStr = "门诊预交金充值";
						break;
					case 5:
						busTypeStr = "住院预交金充值";
						break;

					default:
						break;
					}
	        	}
	        	Label businessType = new Label(3,i,busTypeStr,wcfFormat);
	        	sheet.addCell(businessType);
	        	//支付方式：1-扫码支付，2-刷卡支付，3-公众号小程序支付，4-app支付，5-h5支付
	        	String payTypeStr = null;
	        	if(payTrateOrdersList.get(i-1).getPayType()!=null){
	        		int paType = payTrateOrdersList.get(i-1).getPayType();
		        	switch (paType) {
					case 1:
						payTypeStr = "扫码支付";
						break;
					case 2:
						payTypeStr = "刷卡支付";
						break;
					case 3:
						payTypeStr = "公众号小程序支付";
						break;
					case 4:
						payTypeStr = "app支付";
						break;
					case 5:
						payTypeStr = "h5支付";
						break;

					default:
						break;
					}
	        	}
	        	Label payType = new Label(4,i,payTypeStr,wcfFormat);
	        	sheet.addCell(payType);
	        	//充值方式：1-微信支付；2-支付宝支付；3-银联支付
	        	String tradeTypeStr = null;
	        	if(payTrateOrdersList.get(i-1).getTradeType()!=null){
	        		int trType =  payTrateOrdersList.get(i-1).getTradeType();
		        	switch (trType) {
					case 1:
						tradeTypeStr = "微信支付";
						break;
					case 2:
						tradeTypeStr = "支付宝支付";
						break;
					case 3:
						tradeTypeStr = "银联支付";
						break;

					default:
						break;
					}
	        	}
				Label tradeType = new Label(5,i,tradeTypeStr,wcfFormat);
				sheet.addCell(tradeType);
				Label tradeFee = new Label(6,i,payTrateOrdersList.get(i-1).getTradeFee().toString(),wcfFormat);
				sheet.addCell(tradeFee);
				Label chName = new Label(7,i,payTrateOrdersList.get(i-1).getChNa(),wcfFormat);
				sheet.addCell(chName);
				//交易状态：0-初始状态，1-交易成功，2-支付成功，-1-支付失败，-2-云平台撤销发起，-3-云平台撤销成功，-4-his撤销发起，-5-his撤销成功,-9-"云平台关闭订单成功,-10-云平台关闭订单失败
				String tradeStateStr = null;
		        if(payTrateOrdersList.get(i-1).getTradeState()!=null){
		    		int traState = payTrateOrdersList.get(i-1).getTradeState();
					switch (traState) { 
					case 0:
						tradeStateStr = "初始状态";
						break;
					case 1:
						tradeStateStr = "交易成功";
						break;
					case 2:
						tradeStateStr = "支付成功";
						break;
					case -1:
						tradeStateStr = "支付失败";
						break;
					case -2:
						tradeStateStr = "云平台撤销发起";
						break;
					case -3:
						tradeStateStr = "云平台撤销成功";
						break;
					case -4:
						tradeStateStr = "his撤销发起";
						break;
					case -5:
						tradeStateStr = "his撤销成功";
						break;
					case -9:
						tradeStateStr = "云平台关闭订单成功";
						break;
					case -10:
						tradeStateStr = "云平台关闭订单失败";
						break;

					default:
						break;
					}
		        }
				Label tradeState = new Label(8,i,tradeStateStr,wcfFormat);
				sheet.addCell(tradeState);        	
			}
			
			//把创建的内容写入到输出流中，并关闭输出流
	        workbook.write();
	        workbook.close();
	        os.close();
		}
		
	}

	//分页列表展示
	@SuppressWarnings("unchecked")
	@Override
	public Object getListByPage(SearchParams params, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		//填充对应的组织机构编码
		fillingOrgIdByUser(params);
		
		Pagination<T> pageList = new Pagination<T>();
		
		String result = transmitInterface("shs/paytrateorder/findpage", params);
		
		//String result = "{\"total\":1,\"rows\":[{\"ptoID\":\"AD5B7D00D1E64539AA4E036C8F1A3F94\",\"chToken\":\"c75d08d7fba64c0eae52f40d21d9863f\",\"hosID\":\"43d858f232bc41999cc7e79fb7328890\",\"orgID\":\"610001\",\"tradeID\":\"dc817D00D1E64539AA4E036C8F1c001\",\"hisTradeID\":\"a2345678069a1c2\",\"mchID\":\"sh0130125\",\"deviceInfo\":\"awc1q03c6\",\"businessType\":\"1\",\"payType\":\"1\",\"tradeType\":\"2\",\"tradeFee\":\"100\",\"authCode\":\"001\",\"chName\":\"西安医专附属医院微信\",\"tradeTime\":\"2017-06-07 15:33:01\",\"tradeExpireTime\":\"2017-06-08 15:33:01\",\"shopID\":\"001\",\"deviceID\":\"s126a3\",\"goodsTag\":\"商品标记\",\"operatorID\":\"user001306\",\"tradeState\":\"1\",\"attach\":\"his备注\",\"resultCode\":\"支付返回信息\",\"errMsg\":\"支付返回错误信息\",\"payResult\":\"0\",\"payInfo\":\"支付结果信息\",\"nonceStr\":\"随机字符串\",\"tradeCancleTime\":\"2017-06-07 20:33:01\",\"noticeTime\":\"3\"}],\"footer\":null}";
		
		pageList = JSONObject.toJavaObject(JSONObject.parseObject(result), Pagination.class);
		return pageList;
	}

	//获取渠道名称下拉框数据
	@Override
	public List<OtherChannelsInfo> findListByOrgId() {
		//获取机构编码
		String orgId = UserUtil.getAuthCode();
		//0表示admin
		if(orgId.trim().equals("0")){
			orgId = null;
		}
		List<OtherChannelsInfo> pageList = otherChannelsInfoService.findListByOrgId(orgId);		
		return pageList;
	}

}
