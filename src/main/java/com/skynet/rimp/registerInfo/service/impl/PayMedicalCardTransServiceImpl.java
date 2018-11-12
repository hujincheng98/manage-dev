package com.skynet.rimp.registerInfo.service.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.common.utils.string.StringUtil;
import com.skynet.rimp.registerInfo.dao.PayMedicalCardTransInfoDao;
import com.skynet.rimp.registerInfo.service.IPayMedicalCardTransService;
import com.skynet.rimp.registerInfo.vo.PayMedicalCardTransInfo;

@Service("payMedicalCardTransService")
public class PayMedicalCardTransServiceImpl extends BaseServiceImpl<PayMedicalCardTransInfo> implements IPayMedicalCardTransService {

	@Autowired
	private PayMedicalCardTransInfoDao payMedicalCardTransInfoDao;

	@Override
	public List<PayMedicalCardTransInfo> findByCondition(SearchParams params) throws Exception {
		return this.payMedicalCardTransInfoDao.findByCondition(params);
	}

	@Override
	public List<PayMedicalCardTransInfo> findAll() throws Exception {
		return this.payMedicalCardTransInfoDao.findAll();
	}

	@Override
	public int insert(PayMedicalCardTransInfo record) {
		return this.payMedicalCardTransInfoDao.insert(record);
	}

	@Override
	public int delete(Integer transId) {
		return this.payMedicalCardTransInfoDao.delete(transId);
	}

	@Override
	public int update(PayMedicalCardTransInfo record) {
		return this.payMedicalCardTransInfoDao.update(record);
	}

	@Override
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, SearchParams params) throws Exception {
		Map<String, Object> searchParams = params.getSearchParams();
		if (searchParams.get("startDate") != null && !"".equals(searchParams.get("startDate"))) {
			searchParams.put("startDate", searchParams.get("startDate") + " 00:00:00");
		}
		if (searchParams.get("endDate") != null && !"".equals(searchParams.get("endDate"))) {
			searchParams.put("endDate", searchParams.get("endDate") + " 23:59:59");
		}
		List<PayMedicalCardTransInfo> list = findByCondition(params);
		if(list.size()>0){
			//开始excel导出
			String fname = "预交金充值信息";
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
			Label chTransNumColumn = new Label(0,0,"支付流水号",wcfFormat);
			sheet.addCell(chTransNumColumn);
			//设置某个单元格宽
			sheet.setColumnView(0, 20);
			
			Label hisTransNumColumn = new Label(1,0,"his充值流水号",wcfFormat);
			sheet.addCell(hisTransNumColumn);
			//设置某个单元格宽
			sheet.setColumnView(1, 20);
			
			Label transTimeColumn = new Label(2,0,"充值时间",wcfFormat);
			sheet.addCell(transTimeColumn);
			//设置某个单元格宽
			sheet.setColumnView(2, 20);
			
			Label transMedicardNameColumn = new Label(3,0,"患者姓名",wcfFormat);
			sheet.addCell(transMedicardNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(3, 20);
			
			Label transMedicardIdencardColumn = new Label(4,0,"身份证号",wcfFormat);
			sheet.addCell(transMedicardIdencardColumn);
			//设置某个单元格宽
			sheet.setColumnView(4, 20);
			
			Label transMedicardPhoneColumn = new Label(5,0,"联系电话",wcfFormat);
			sheet.addCell(transMedicardPhoneColumn);
			//设置某个单元格宽
			sheet.setColumnView(5, 20);
			
			Label transMedicardIdColumn = new Label(6,0,"就诊卡卡号",wcfFormat);
			sheet.addCell(transMedicardIdColumn);
			//设置某个单元格宽
			sheet.setColumnView(6, 20);
			
			Label transAmountColumn = new Label(7,0,"充值金额",wcfFormat);
			sheet.addCell(transAmountColumn);
			//设置某个单元格宽
			sheet.setColumnView(7, 20);
			
			Label transTypeNameColumn = new Label(8,0,"充值方式",wcfFormat);
			sheet.addCell(transTypeNameColumn);
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
			for (int i = 1; i <= list.size(); i++) {
				Label chTransNum = new Label(0,i,list.get(i-1).getChTransNum(),wcfFormat);
				sheet.addCell(chTransNum);
				Label hisTransNum = new Label(1,i,list.get(i-1).getHisTransNum(),wcfFormat);
				sheet.addCell(hisTransNum);
				Date pd = list.get(i-1).getTransTime();
				if(pd!=null){
					Label transTime = new Label(2,i,formatDate(pd),wcfFormat);
					sheet.addCell(transTime);	
				}
				Label transMedicardName = new Label(3,i,list.get(i-1).getTransMedicardName(),wcfFormat);
				sheet.addCell(transMedicardName);
				
				//身份证号屏蔽
				String idCard = list.get(i-1).getTransMedicardIdencard();
				String ic = idCard;//StringUtil.screenIdentityCard(idCard);
				Label identityCard = new Label(4,i,ic,wcfFormat);
				sheet.addCell(identityCard);
				
				//电话号屏蔽四位
				String tel = list.get(i-1).getTransMedicardPhone();
				String telStr = tel;//StringUtil.screenTelephone(tel);
				Label transMedicardPhone = new Label(5,i,telStr,wcfFormat);
				sheet.addCell(transMedicardPhone);
				
				Label transMedicardId = new Label(6,i,list.get(i-1).getTransMedicardId(),wcfFormat);
				sheet.addCell(transMedicardId);
				
				if(list.get(i-1).getTransAmount()!=null){
					Label transAmount = new Label(7,i,list.get(i-1).getTransAmount().toString(),wcfFormat);
					sheet.addCell(transAmount);
				}
				Label transTypeName = new Label(8,i,list.get(i-1).getTransTypeName(),wcfFormat);
				sheet.addCell(transTypeName);
				Label transStateName = new Label(9,i,list.get(i-1).getTransStateName(),wcfFormat);
				sheet.addCell(transStateName);
				Label chName = new Label(10,i,list.get(i-1).getChName(),wcfFormat);
				sheet.addCell(chName);				
			}
			//把创建的内容写入到输出流中，并关闭输出流
	        workbook.write();
	        workbook.close();
	        os.close();
			
		}
	}

	public String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
}
