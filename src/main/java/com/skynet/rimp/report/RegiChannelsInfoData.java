package com.skynet.rimp.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.skynet.common.excel.db.DBRecord;
import com.skynet.common.excel.db.DBRecordSet;
import com.skynet.common.excel.db.DBSheet;
import com.skynet.common.excel.report.ReportComponent;
import com.skynet.rimp.registerInfo.vo.RegiChannelsInfo;

/**
 * <p>Title: 预约信息导出数据源</p>
 * <p>Description: </p>
 *
 * @author Liujian
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
public class RegiChannelsInfoData extends ReportComponent {

	private Boolean boo;
	
	private List<RegiChannelsInfo> dataList; // 数据源
	
	public RegiChannelsInfoData(Boolean boo, List<RegiChannelsInfo> dataList) {
		this.boo = boo;
		this.dataList = dataList;
	}
	
	@Override
	protected List<DBSheet> initDBSheet() {
		List<DBSheet> list = new ArrayList<DBSheet>();
		DBSheet dbSheet = new DBSheet();
		dbSheet.setSheetName("sheet_regi_channels");// sheetId
		dbSheet.setDisplayName("预约信息");
		
		if (!dataList.isEmpty()) {
			
			DBRecordSet recordSet = new DBRecordSet();
			for (RegiChannelsInfo record : dataList) {
				DBRecord dbRecord = new DBRecord();
				dbRecord.put("patientname", StringUtils.isNotEmpty(record.getPatientname()) ? record.getPatientname() : "");
				dbRecord.put("identitycard", StringUtils.isNotEmpty(record.getIdentitycard()) ? record.getIdentitycard() : "");
				dbRecord.put("telephone", StringUtils.isNotEmpty(record.getTelephone()) ? record.getTelephone() : "");
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
				String endDate = "";
				if (boo) {
					endDate = dateFormat.format(record.getBespeakdate()) + " " 
							+ (record.getQueueDate() == null ? "00:00" : record.getQueueDate()) + ":00";
				} else {
					endDate = dateFormat.format(record.getBespeakdate()) + " " 
							+ (record.getEndtime() == null ? "00:00" : timeFormat.format(record.getEndtime())) + ":00";
				}
				dbRecord.put("bespeakdate", endDate);
				dbRecord.put("bespeakid", record.getBespeakid() == null ? "" : record.getBespeakid());
				if (StringUtils.isNotEmpty(record.getHosId())) {
					dbRecord.put("hosName", record.getHosName());
				}
				dbRecord.put("bespeakdoctorid", record.getBespeakdoctorid());
				dbRecord.put("bespeakofficeid", record.getBespeakofficeid());
				dbRecord.put("worktype", record.getWorktype());
				if (StringUtils.isNotEmpty(record.getRegistate())) {
					dbRecord.put("registate", record.getRegistateName());
				}
				dbRecord.put("bespeakchannels", record.getBespeakchannels());
				recordSet.add(dbRecord);
			}
			dbSheet.setRecordSet(recordSet);
		}
		list.add(dbSheet);
		return list;
	}

	public List<RegiChannelsInfo> getDataList() {
		return dataList;
	}

	public void setDataList(List<RegiChannelsInfo> dataList) {
		this.dataList = dataList;
	}
	
}
