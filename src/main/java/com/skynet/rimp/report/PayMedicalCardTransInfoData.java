package com.skynet.rimp.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.skynet.common.excel.db.DBRecord;
import com.skynet.common.excel.db.DBRecordSet;
import com.skynet.common.excel.db.DBSheet;
import com.skynet.common.excel.report.ReportComponent;
import com.skynet.rimp.registerInfo.vo.PayMedicalCardTransInfo;

/**
 * <p>Title: 预交金充值数据源</p>
 * <p>Description: </p>
 *
 * @author Liujian
 * @version 1.00.00
 *          <pre>
 *          修改记录:
 *          版本号    修改人        修改日期       修改内容
 */
public class PayMedicalCardTransInfoData extends ReportComponent {

    private List<PayMedicalCardTransInfo> dataList; // 数据源

    public PayMedicalCardTransInfoData(List<PayMedicalCardTransInfo> dataList) {
        this.dataList = dataList;
    }

    @Override
    protected List<DBSheet> initDBSheet() {
        List<DBSheet> list = new ArrayList<DBSheet>();
        DBSheet dbSheet = new DBSheet();
        dbSheet.setSheetName("sheet_pay_medicalcard_trans");// sheetId
        dbSheet.setDisplayName("预交金充值信息");
        if (!dataList.isEmpty()) {
            DBRecordSet recordSet = new DBRecordSet();
            for (PayMedicalCardTransInfo record : dataList) {
                DBRecord dbRecord = new DBRecord();
                dbRecord.put("chTransNum", StringUtils.isNotEmpty(record.getChTransNum()) ? record.getChTransNum() : "");
                dbRecord.put("hisTransNum", StringUtils.isNotEmpty(record.getHisTransNum()) ? record.getHisTransNum() : "");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dbRecord.put("transTime", dateFormat.format(record.getTransTime()));
                dbRecord.put("transMedicardName", StringUtils.isNotEmpty(record.getTransMedicardName()) ? record.getTransMedicardName() : "");
                dbRecord.put("transMedicardIdencard", StringUtils.isNotEmpty(record.getTransMedicardIdencard()) ? record.getTransMedicardIdencard() : "");
                dbRecord.put("transMedicardPhone", StringUtils.isNotEmpty(record.getTransMedicardPhone()) ? record.getTransMedicardPhone() : "");
                dbRecord.put("transMedicardId", StringUtils.isNotEmpty(record.getTransMedicardId()) ? record.getTransMedicardId() : "");
                dbRecord.put("transAmount", record.getTransAmount());
                String transType = "";
                if ("1".equals(record.getTransType())) {
                    transType = "微信支付";
                } else if ("2".equals(record.getTransType())) {
                    transType = "支付宝支付";
                } else if ("3".equals(record.getTransType())) {
                    transType = "银联支付";
                } else if ("4".equals(record.getTransType())) {
                    transType = "现金";
                } else {
                    transType = "未知";
                }
                dbRecord.put("transType", transType);
                dbRecord.put("transState", record.getTransStateName());
                dbRecord.put("chName", StringUtils.isNotEmpty(record.getChName()) ? record.getChName() : "");
                recordSet.add(dbRecord);
            }
            dbSheet.setRecordSet(recordSet);
        }
        list.add(dbSheet);
        return list;
    }

    public List<PayMedicalCardTransInfo> getDataList() {
        return dataList;
    }

    public void setDataList(List<PayMedicalCardTransInfo> dataList) {
        this.dataList = dataList;
    }


}
