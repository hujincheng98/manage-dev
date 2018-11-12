package com.skynet.his.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skynet.common.SearchParams;
import com.skynet.his.common.SysConfig;
import com.skynet.his.dto.SchmDTO;
import com.skynet.his.dto.SchmDetailDTO;
import com.skynet.his.dto.SchmMainDTO;
import com.skynet.his.dto.SchmQueueDTO;
import com.skynet.his.utils.PushHisHttpRequest;
import com.skynet.rimp.channelInfo.service.IReqHisLogService;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import com.skynet.rimp.channelInfo.vo.ReqHisLogInfo;
import com.skynet.rimp.common.utils.time.DateUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.hisBaseInfo.service.IHosDeptInfoService;
import com.skynet.rimp.hisBaseInfo.service.IHosDocmService;
import com.skynet.rimp.hisBaseInfo.vo.HosDeptInfo;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;
import com.skynet.rimp.schmInfo.service.ISchmShiftInfoService;
import com.skynet.rimp.schmInfo.vo.SchmDetailInfo;
import com.skynet.rimp.schmInfo.vo.SchmMainInfo;
import com.skynet.rimp.schmInfo.vo.SchmQueueInfo;
import com.skynet.rimp.schmInfo.vo.SchmShiftInfoEntity;

/**
 * 排班推送任务处理线程
 * @author wangshen
 *
 */
public class PushSchmThread extends Thread {
	
	protected transient Logger logger = Logger.getLogger(this.getClass().getName());
	
	private List<SchmMainInfo> schmMainList = null;//主排班数据
	private List<SchmDetailInfo> schmDetailList = null;//排班明细数据
	private List<SchmQueueInfo> schmQueueList = null; // 排队号明细
	private IHosDeptInfoService deptService = null;
	private IHosDocmService docmService = null;
	private ISchmShiftInfoService shiftInfoService = null;
	private IReqHisLogService reqHisLogService = null;
	private String operationType = null;//排班业务操作类型
	
	
	
	/**
	 * 通过构造方法将
	 * @param schmMainList
	 * @param schmDetailList
	 */
	public PushSchmThread(List<SchmMainInfo> schmMainList, List<SchmDetailInfo> schmDetailList, 
			List<SchmQueueInfo> schmQueueList, 
			IHosDeptInfoService deptService,IHosDocmService docmService,
			ISchmShiftInfoService shiftInfoService, String operationType,
			IReqHisLogService reqHisLogService) {
		this.schmMainList = schmMainList;
		this.schmDetailList = schmDetailList;
		this.schmQueueList = schmQueueList;
		this.deptService = deptService;
		this.docmService = docmService;
		this.shiftInfoService = shiftInfoService;
		this.operationType = operationType;
		this.reqHisLogService = reqHisLogService; 
	}
	/**
	 * 任务处理
	 */
	public void run(){
		
		//对应his渠道标志是否存在不存在返回不处理，不同步
		String hosId = "";
		String param = "";
		
		
		//查询医生和科室信息
		try {
			
			if(schmMainList!=null && schmMainList.size()>0){
				hosId = schmMainList.get(0).getHosId();//设置HOSID
				if(!SysConfig.HIS_CHANNELS.containsKey(hosId)){
					return;
				}
				OtherChannelsInfo channelsInfo = SysConfig.HIS_CHANNELS.get(hosId);
				if (channelsInfo.getSchmPushMark() == null || channelsInfo.getSchmPushMark() == 0) {
					return;
				}
			}
			
			//=========将科室、医生、班次转换成map==========//
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("hosId", hosId);
			SearchParams searchParams = new SearchParams();
			searchParams.setSearchParams(params);
			//取相应医院的科室信息
			List<HosDeptInfo> deptInfos = deptService.findByCondition(searchParams);
			//取相应医院的医生信息
			List<HosDocmInfo> docmInfos = docmService.findByCondition(searchParams);
			
			//取相应医院的班次信息
			List<SchmShiftInfoEntity> shitfs = shiftInfoService.findAll();
			
			Map<String, HosDeptInfo> deptMap = new HashMap<String, HosDeptInfo>();
			Map<String, HosDocmInfo> docmMap = new HashMap<String, HosDocmInfo>();
			Map<String, SchmShiftInfoEntity> shiftMap = new HashMap<String, SchmShiftInfoEntity>();
			for (HosDocmInfo hosDocmInfo : docmInfos) {
				docmMap.put(hosDocmInfo.getDocmId(), hosDocmInfo);
			}
			for (HosDeptInfo deptInfo : deptInfos) {
				deptMap.put(deptInfo.getDeptId(), deptInfo);
			}
			for (SchmShiftInfoEntity entity : shitfs) {
				shiftMap.put(entity.getShiftId(), entity);
			}
			//======将生成的排班数据转为对象======//
			
			List<SchmMainDTO> schmMains = new ArrayList<SchmMainDTO>();
			for (SchmMainInfo schmMain : schmMainList) {
				List<SchmDetailDTO> detailList = new ArrayList<SchmDetailDTO>();
				List<SchmQueueDTO> queueList = new ArrayList<SchmQueueDTO>();
				//将排班明细数据与排班主表数据关联
				for (SchmDetailInfo schmDetail : schmDetailList) {
					SchmDetailDTO detail = null;
					if(schmDetail.getSchmId().equals(schmMain.getSchmId())){
						//封装子表数据
						detail = new SchmDetailDTO(schmDetail.getSchmDetailId(), 
								schmDetail.getSchmOnNum()==null?null:schmDetail.getSchmOnNum()+"", 
								schmDetail.getSchmDownNum()==null?null:schmDetail.getSchmDownNum()+"", 
								schmDetail.getSchmId(), 
								schmDetail.getShiftId(), 
								DateUtil.format(schmDetail.getTislStartDate(),"HH:mm:ss"), 
								DateUtil.format(schmDetail.getTislEndDate(),"HH:mm:ss"), 
								schmDetail.getDetailUpreNum()==null?null:schmDetail.getDetailUpreNum()+"", 
								schmDetail.getDetailDownreNum()==null?null:schmDetail.getDetailDownreNum()+"");
						detailList.add(detail);
					}
				}
				if (schmQueueList !=null && schmQueueList.size() > 0) {
					for (SchmQueueInfo queueInfo : schmQueueList) {
						SchmQueueDTO queueDTO = null;
						if (queueInfo.getSchmId().equals(schmMain.getSchmId())) {
							queueDTO = new SchmQueueDTO(queueInfo.getSchmId(), 
									queueInfo.getQueueNum(), 
									queueInfo.getQueueDate(), 
									queueInfo.getQueueState());
							queueList.add(queueDTO);
						}
					}
				}
				HosDocmInfo docm = docmMap.get(schmMain.getDocmId());
				HosDeptInfo dept = deptMap.get(schmMain.getSchmDeptId());
				SchmShiftInfoEntity shift = shiftMap.get(schmMain.getShiftId());
				//封装主表数据
				try {
					SchmMainDTO schmMainDTO = new SchmMainDTO(schmMain.getSchmId(), 
									DateUtil.format(schmMain.getSchmDate()),
									schmMain.getDocmId(), 
									docm.getDocmName(), 
									docm.getDocmPosition(), 
									schmMain.getSchmDeptId(),
									dept.getDeptName(), 
									schmMain.getSchmWeek(), 
									schmMain.getShiftId(),
									shift.getShiftName(),
									DateUtil.format(schmMain.getSchmOnWork(),"HH:mm:ss"), 
									DateUtil.format(schmMain.getSchmOffWork(),"HH:mm:ss"), 
									schmMain.getSchmOnSum()==null?null:schmMain.getSchmOnSum()+"", 
									schmMain.getSchmDownSum()==null?null:schmMain.getSchmDownSum()+"", 
									schmMain.getSchmOnRegiSum()==null?null:schmMain.getSchmOnRegiSum()+"", 
									schmMain.getSchmDownRegiSum()==null?null:schmMain.getSchmDownRegiSum()+"", 
									schmMain.getSchmRegiOnNum()==null?null:schmMain.getSchmRegiOnNum()+"", 
									schmMain.getSchmRegiDownNum()==null?null:schmMain.getSchmRegiDownNum()+"", 
									schmMain.getSchmUpreNum()==null?null:schmMain.getSchmUpreNum()+"", 
									schmMain.getSchmDownreNum()==null?null:schmMain.getSchmDownreNum()+"", 
									dept.getDeptHisCode(),
									docm.getDocmHisCode(), 
									detailList, queueList,
									schmMain.getRegCategoryId(),
									schmMain.getServCoding(),
									schmMain.getRegcategory());
									schmMains.add(schmMainDTO);
				} catch (Exception e) {
				    e.printStackTrace();
					continue;
				}
			}
			ObjectMapper mapper = new ObjectMapper();
			//转换为需要发送的数据
			SchmDTO schm = new SchmDTO(operationType, schmMains);
			param = mapper.writeValueAsString(schm);
			//System.out.println("\n\n\n\n\n"+param+"\n\n\n\n\n");
			//调用HIS接口将数据推送给HIS
			String result = PushHisHttpRequest.doPost(SysConfig.HIS_CHANNELS.get(hosId).getChUrl(), param);
			logger.info(" >>> HIS返回结果  === "+result);//消息目前打印出来，调用统一接口记录日志
			
			//记录his调用日志
			ReqHisLogInfo hisLogInfo = new ReqHisLogInfo();
			if(result.contains("http-his-respCode")){
				hisLogInfo.setRespCode("1");
				hisLogInfo.setRespData(result);
			}else if(StringUtils.isBlank(result)){
				hisLogInfo.setRespCode("1");
				hisLogInfo.setRespData("HIS调用失败网络不通");
			}else{
				JSONObject resultJson = JSONObject.fromObject(xmlParse(result));
				hisLogInfo.setRespCode(resultJson.getString("code"));
				hisLogInfo.setRespData(resultJson.getString("responseData"));
			}
			
			hisLogInfo.setId(UUIDGenerator.getUUID());
			hisLogInfo.setReqUrl(SysConfig.HIS_CHANNELS.get(hosId).getChUrl());
			hisLogInfo.setReqDate(new Date());
			hisLogInfo.setTransactioncode(SchmDTO.T_CODE);
			hisLogInfo.setOperationtype(operationType);
			hisLogInfo.setRequestdata("not record request");
			reqHisLogService.insert(hisLogInfo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			ReqHisLogInfo hisLogInfo = new ReqHisLogInfo();
			hisLogInfo.setId(UUIDGenerator.getUUID());
			hisLogInfo.setReqUrl(SysConfig.HIS_CHANNELS.get(hosId).getChUrl());
			hisLogInfo.setReqDate(new Date());
			hisLogInfo.setTransactioncode(SchmDTO.T_CODE);
			hisLogInfo.setOperationtype(operationType);
			hisLogInfo.setRequestdata("not record request");
			hisLogInfo.setRespCode("1");
			hisLogInfo.setRespData("未知异常！");
			if(e!=null){
				hisLogInfo.setRespData(e.getMessage());
			}
			
			reqHisLogService.insert(hisLogInfo);
		}
	}
	
	
	/**
	 * 解析HIS返回结果得到json
	 * @param xml
	 * @return
	 */
	private static String xmlParse(String xml){
		try {
			Document document = DocumentHelper.parseText(xml);
			Element node = document.getRootElement();
			return node.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
