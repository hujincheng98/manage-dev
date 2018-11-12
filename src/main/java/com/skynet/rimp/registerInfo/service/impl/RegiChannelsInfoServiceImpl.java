package com.skynet.rimp.registerInfo.service.impl;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.skynet.rimp.common.utils.string.IdentityCard;
import com.skynet.rimp.common.utils.string.StringUtil;
import com.skynet.rimp.registerInfo.dao.RegiChannelsInfoDao;
import com.skynet.rimp.registerInfo.service.IRegiChannelsInfoService;
import com.skynet.rimp.registerInfo.vo.RegiChannelsInfo;

@Service
public class RegiChannelsInfoServiceImpl extends BaseServiceImpl<RegiChannelsInfo> implements IRegiChannelsInfoService {

	@Autowired
	private RegiChannelsInfoDao regiChannelsInfoDao;
	
	@Override
	public List<RegiChannelsInfo> findAll() throws Exception {
		return this.regiChannelsInfoDao.findAll();
	}

	@Override
	public List<RegiChannelsInfo> findByCondition(SearchParams params) throws Exception {
		return this.regiChannelsInfoDao.findByCondition(params);
	}

	@Override
	public int insert(RegiChannelsInfo regiChannelsInfo) {
		return this.insert(regiChannelsInfo);
	}

	@Override
	public int delete(String regiId) {
		return this.delete(regiId);
	}

	@Override
	public int update(RegiChannelsInfo regiChannelsInfo) {
		return this.update(regiChannelsInfo);
	}

	@Override
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, SearchParams params) throws Exception {
		List<RegiChannelsInfo> list = findByCondition(params);
		if(list.size()>0){
			//开始excel导出
			String fname = "预约信息";
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
			Label bespeakIdColumn = new Label(0,0,"预约号",wcfFormat);
			sheet.addCell(bespeakIdColumn);
			//设置某个单元格宽
			sheet.setColumnView(0, 20);
			
			Label bespeakDateColumn = new Label(1,0,"就诊时间",wcfFormat);
			sheet.addCell(bespeakDateColumn);
			//设置某个单元格宽
			sheet.setColumnView(1, 20);
			
			Label patientTypeColumn = new Label(2,0,"患者类型",wcfFormat);
			sheet.addCell(patientTypeColumn);
			//设置某个单元格宽
			sheet.setColumnView(2, 20);
			
			Label patientNameColumn = new Label(3,0,"患者姓名",wcfFormat);
			sheet.addCell(patientNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(3, 20);
			
			Label patientSexColumn = new Label(4,0,"患者性别",wcfFormat);
			sheet.addCell(patientSexColumn);
			//设置某个单元格宽
			sheet.setColumnView(4, 20);
			
			Label guarNameColumn = new Label(5,0,"监护人姓名",wcfFormat);
			sheet.addCell(guarNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(5, 20);
			
			Label identityCardColumn = new Label(6,0,"患者身份证号",wcfFormat);
			sheet.addCell(identityCardColumn);
			//设置某个单元格宽
			sheet.setColumnView(6, 20);
			
			Label telephoneColumn = new Label(7,0,"联系电话",wcfFormat);
			sheet.addCell(telephoneColumn);
			//设置某个单元格宽
			sheet.setColumnView(7, 20);
			
			Label hosNameColumn = new Label(8,0,"预约医院",wcfFormat);
			sheet.addCell(hosNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(8, 20);
			
			Label bespeakOfficeIdColumn = new Label(9,0,"预约科室",wcfFormat);
			sheet.addCell(bespeakOfficeIdColumn);
			//设置某个单元格宽
			sheet.setColumnView(9, 20);
			
			Label bespeakDoctorIdColumn = new Label(10,0,"预约医生",wcfFormat);
			sheet.addCell(bespeakDoctorIdColumn);
			//设置某个单元格宽
			sheet.setColumnView(10, 20);
			
			Label workTypeColumn = new Label(11,0,"预约班次",wcfFormat);
			sheet.addCell(workTypeColumn);
			//设置某个单元格宽
			sheet.setColumnView(11, 20);
			
			Label bespeakChannelsColumn = new Label(12,0,"渠道名称",wcfFormat);
			sheet.addCell(bespeakChannelsColumn);
			//设置某个单元格宽
			sheet.setColumnView(12, 20);
			
			Label regiStateNameColumn = new Label(13,0,"预约状态",wcfFormat);
			sheet.addCell(regiStateNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(13, 20);
			
			Label dataTypeColumn = new Label(14,0,"预约类型",wcfFormat);
			sheet.addCell(dataTypeColumn);
			//设置某个单元格宽
			sheet.setColumnView(14, 20);
			
			Label startTimeColumn = new Label(15,0,"预约开始时段",wcfFormat);
			sheet.addCell(startTimeColumn);
			//设置某个单元格宽
			sheet.setColumnView(15, 20);
			
			Label endTimeColumn = new Label(16,0,"预约结束时段",wcfFormat);
			sheet.addCell(endTimeColumn);
			//设置某个单元格宽
			sheet.setColumnView(16, 20);
			
			Label queueNumColumn = new Label(17,0,"排队号",wcfFormat);
			sheet.addCell(queueNumColumn);
			//设置某个单元格宽
			sheet.setColumnView(17, 20);
			
			Label queueDateColumn = new Label(18,0,"排队时间点",wcfFormat);
			sheet.addCell(queueDateColumn);
			//设置某个单元格宽
			sheet.setColumnView(18, 20);
			
			//加载每行数据
			for (int i = 1; i <= list.size(); i++) {
				Label bespeakId = new Label(0,i,list.get(i-1).getBespeakid(),wcfFormat);
				sheet.addCell(bespeakId);
				
				Date besDate = list.get(i-1).getBespeakdate();
				if(besDate !=null){
					Label bespeakDate = new Label(1,i,formatDate(besDate),wcfFormat);
					sheet.addCell(bespeakDate);					
				}
				
				Label patientType = null;
				if(list.get(i-1).getPatientType()!=null){
					int pt = list.get(i-1).getPatientType();
					String patType = "";
					if(pt == 1){
						patType = "成人";
					}else if(pt == 2){
						patType = "儿童";
					}
					patientType = new Label(2,i,patType,wcfFormat);
				}else{
					patientType = new Label(2,i,"",wcfFormat);
				}
				sheet.addCell(patientType);
				
				Label patientName = new Label(3,i,list.get(i-1).getPatientname(),wcfFormat);
				sheet.addCell(patientName);
				
				if(list.get(i-1).getPatientType()!=null){
					int patType = list.get(i-1).getPatientType();
					if(patType == 1){ //成人
						String identityCard = list.get(i-1).getIdentitycard();
						if(identityCard != null){
							IdentityCard ic = new IdentityCard(identityCard);
							
							String sex = ic.getSex();			
							Label patientSex = new Label(4,i,sex,wcfFormat);
							sheet.addCell(patientSex);   
							
							/*int idCardNum = identityCard.length();
							if(idCardNum == 15){
								if (Integer.parseInt(identityCard.substring(14, 15)) % 2 == 0) {// 判断性别  
									Label patientSex = new Label(4,i,"女",wcfFormat);
									sheet.addCell(patientSex);   
							        } else {  
							        	Label patientSex = new Label(4,i,"男",wcfFormat);
										sheet.addCell(patientSex);   
							        }  
							}
							if(idCardNum == 18){
								 if (Integer.parseInt(identityCard.substring(16).substring(0, 1)) % 2 == 0) {// 判断性别  
									 Label patientSex = new Label(4,i,"女",wcfFormat);
									 sheet.addCell(patientSex);   
							        } else {  
							        	Label patientSex = new Label(4,i,"男",wcfFormat);
										sheet.addCell(patientSex);
							        }  
							}*/
						}
					}
				}
				
				Label guarName = new Label(5,i,list.get(i-1).getGuarName(),wcfFormat);
				sheet.addCell(guarName);
				
				//身份证号屏蔽,2017-11-15暂时放开屏蔽，需导入预约信息给his
				String idCard = list.get(i-1).getIdentitycard();
				Label identityCard = null;
				try {
					String ic = idCard;//StringUtil.screenIdentityCard(idCard);
					identityCard = new Label(6,i,ic,wcfFormat);
				} catch (Exception e) {
					identityCard = new Label(6,i,idCard,wcfFormat);
				}
				sheet.addCell(identityCard);
				
				//电话号屏蔽四位,2017-10-31暂时放开屏蔽，客户要求放开
				String tel = list.get(i-1).getTelephone();
				Label telephone = null;
				try {
					String telStr = tel;// StringUtil.screenTelephone(tel);
					telephone = new Label(7,i,telStr,wcfFormat);	
				} catch (Exception e) {
					telephone = new Label(7,i,tel,wcfFormat);
				}				
				sheet.addCell(telephone); 
				
				Label hosName = new Label(8,i,list.get(i-1).getHosName(),wcfFormat);
				sheet.addCell(hosName);
				
				Label bespeakOfficeId = new Label(9,i,list.get(i-1).getBespeakofficeid(),wcfFormat);
				sheet.addCell(bespeakOfficeId);
				
				Label bespeakDoctorId = new Label(10,i,list.get(i-1).getBespeakdoctorid(),wcfFormat);
				sheet.addCell(bespeakDoctorId);
				
				Label workType = new Label(11,i,list.get(i-1).getWorktype(),wcfFormat);
				sheet.addCell(workType);
				
				Label bespeakChannels = new Label(12,i,list.get(i-1).getBespeakchannels(),wcfFormat);
				sheet.addCell(bespeakChannels);
				
				Label regiStateName = new Label(13,i,list.get(i-1).getRegistateName(),wcfFormat);
				sheet.addCell(regiStateName);
				
				Label dataType = new Label(14,i,list.get(i-1).getDataType(),wcfFormat);
				sheet.addCell(dataType);
				
				Date st = list.get(i-1).getStarttime();
				if(st != null){
					Label startTime = new Label(15,i,formatHourAndMinute(st),wcfFormat);
					sheet.addCell(startTime);
				}
				
				Date et = list.get(i-1).getEndtime();
				if(et != null){
					Label startTime = new Label(16,i,formatHourAndMinute(et),wcfFormat);
					sheet.addCell(startTime);
				}
				
				Label queueNum = new Label(17,i,list.get(i-1).getQueueNum(),wcfFormat);
				sheet.addCell(queueNum);
				
				Label queueDate = new Label(18,i,list.get(i-1).getQueueDate(),wcfFormat);
				sheet.addCell(queueDate);
				
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
	
	public String formatHourAndMinute(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String dateStr = sdf.format(date);
		return dateStr;
	}
	
}
