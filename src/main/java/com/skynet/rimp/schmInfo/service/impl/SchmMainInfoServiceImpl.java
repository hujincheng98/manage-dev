package com.skynet.rimp.schmInfo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skynet.common.SearchParams;
import com.skynet.his.common.SysConfig;
import com.skynet.his.dto.SchmDTO;
import com.skynet.his.dto.SchmDetailDTO;
import com.skynet.his.dto.SchmMainDTO;
import com.skynet.his.dto.SchmQueueDTO;
import com.skynet.his.thread.PushSchmThread;
import com.skynet.his.utils.PushHisHttpRequest;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.channelInfo.service.IOtherChannelsInfoService;
import com.skynet.rimp.channelInfo.service.IReqHisLogService;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import com.skynet.rimp.channelInfo.vo.ReqHisLogInfo;
import com.skynet.rimp.common.utils.MessPushConfig;
import com.skynet.rimp.common.utils.time.DateUtil;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.hisBaseInfo.service.IHosDeptInfoService;
import com.skynet.rimp.hisBaseInfo.service.IHosDocmService;
import com.skynet.rimp.hisBaseInfo.vo.HosDeptInfo;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;
import com.skynet.rimp.messPush.dto.MessResponseDataDTO;
import com.skynet.rimp.messPush.dto.SchmStatePushDTO;
import com.skynet.rimp.messPush.service.MessPushMessForwardService;
import com.skynet.rimp.schmInfo.dao.*;
import com.skynet.rimp.schmInfo.service.ISchmMainInfoService;
import com.skynet.rimp.schmInfo.service.ISchmQueueInfoService;
import com.skynet.rimp.schmInfo.service.ISchmShiftInfoService;
import com.skynet.rimp.schmInfo.vo.*;
import jxl.Workbook;
import jxl.write.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * <p>
 * Title: 排班管理服务
 * </p>
 * <p>
 * Description: 根据班次等相关信息进行新增排班、自动排版、删除、启用、停用
 * </p>
 * 
 * @author Torlay
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 * 
 */

@Service
public class SchmMainInfoServiceImpl extends BaseServiceImpl<SchmMainInfo>
		implements ISchmMainInfoService {
	protected transient Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private SchmMainInfoDao dao;

	@Autowired
	private SchmShiftInfoDao shitdao;

	@Autowired
	private SchmDetailInfoDao detaildao;

	@Autowired
	private SchmBaseMainInfoDao basemaindao;

	@Autowired
	private SchmShiftInfoDao shiftdao;
	
	@Autowired
	private SchmQueueInfoDao queueDao;
	
	@Autowired
	private IHosDeptInfoService deptService;
	
	@Autowired
	private IHosDocmService docmService;
	
	@Autowired
	private ISchmShiftInfoService shiftInfoService;
	
	@Autowired
	private IReqHisLogService reqHisLogService;
	
	@Autowired
	private ISchmQueueInfoService queueInfoService;

	@Autowired
	private IOtherChannelsInfoService otherChannelsInfoService;

	@Autowired
	private MessPushMessForwardService messPushMessForwardService;



	/**
	 * 无条件查询所有排班
	 */
	@Override
	public List<SchmMainInfo> findAll() throws Exception {
		return dao.findAll();
	}

	/**
	 * 分页查询
	 */
	@Override
	public List<SchmMainInfo> findByCondition(SearchParams params)
			throws Exception {
		return dao.findByCondition(params);
	}

	/**
	 * 更新排班状态
	 */
	@Override
	public int updateState(SchmMainInfo record) throws Exception {
		SchmMainInfo vo = dao.selectByPrimaryKey(record.getSchmId());
		vo.setSchmState(record.getSchmState());
		vo.setUpdateUser(UserUtil.getUserId());
		vo.setUpdateDate(new Date());
		int result = dao.updateByPrimaryKey(vo);
		if(result>0){
			List<SchmMainInfo> mainList = new ArrayList<SchmMainInfo>();
			List<SchmDetailInfo> detailList = findDetailsBySchmId(vo.getSchmId());
			SearchParams params = new SearchParams();
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("schmId", record.getSchmId());
			params.setSearchParams(searchParams);
			List<SchmQueueInfo> queueList = queueInfoService.findByCondition(params);
			mainList.add(vo);
			if(record.getSchmState().equals("state_0")){//停用
				new PushSchmThread(mainList, detailList, queueList, deptService, docmService, shiftInfoService, "00004", reqHisLogService).start();
				//停诊消息推送
				JSONObject obj = JSONObject.fromObject(vo);
				pushSchmStateMess(obj, MessPushConfig.MESS_PUSH_20000);
			}
			if(record.getSchmState().equals("state_1")){//启用
				new PushSchmThread(mainList, detailList, queueList, deptService, docmService, shiftInfoService, "00005",reqHisLogService).start();
			}
		}
		return result;
	}
	/**
	 * 消息推送服务
	 *
	 */
	public void pushSchmStateMess(JSONObject obj, String transactionCode) {
		// 组织消息推送参数
		MessResponseDataDTO messResponseDataDTO = new MessResponseDataDTO();
		messResponseDataDTO.setTransactionCode(transactionCode);
		messResponseDataDTO.setHosId(obj.getString("hosId"));
		String chUrl = "";
		try {
			String token = "";
			OtherChannelsInfo otherChannelsInfo = otherChannelsInfoService.findByHosId(obj.getString("hosId"));
			if(otherChannelsInfo!=null && !StringUtils.equals("HIS", otherChannelsInfo.getExt1()) && StringUtils.isNotBlank(otherChannelsInfo.getChUrl())) {
				token = otherChannelsInfo.getToken();
				chUrl = otherChannelsInfo.getChUrl();
				messResponseDataDTO.setToken(token);
				//封装推送给微信的参数
				SchmStatePushDTO schmStatePushDTO = new SchmStatePushDTO();
				schmStatePushDTO.setSchmId(obj.getString("schmId"));
				messResponseDataDTO.getRequestData().add(schmStatePushDTO);
				// 预约成功消息推送
				this.messPushMessForwardService.messForward(JSONObject.fromObject(messResponseDataDTO).toString());
			}
		}catch (Exception e){
			logger.error(e.getMessage());
			ReqHisLogInfo hisLogInfo = new ReqHisLogInfo();
			hisLogInfo.setId(UUIDGenerator.getUUID());
			hisLogInfo.setReqUrl(chUrl);
			hisLogInfo.setReqDate(new Date());
			hisLogInfo.setTransactioncode(transactionCode);
			hisLogInfo.setOperationtype("停诊通知");
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
	 * 新增排班信息
	 */
	@Override
	public String saveAll(SchmMainInfoVo schmMainInfoVo) throws Exception {
		
		List<SchmMainInfo> mainList = new ArrayList<SchmMainInfo>();
		List<SchmDetailInfo> detailList = new ArrayList<SchmDetailInfo>();
		List<SchmQueueInfo> queueList = new ArrayList<SchmQueueInfo>();
		// 同排班日期同医生同班次不能重复
		int resultex = dao.findByShiftIdByDocmDate(schmMainInfoVo);

		if (resultex > 0) {
			return "exist";
		}

		// 设置主表排班信息
		SchmMainInfo schmMainInfo = new SchmMainInfo();
		BeanUtils.copyProperties(schmMainInfoVo, schmMainInfo);
		String id = UUIDGenerator.getUUID();// 生成主键
		schmMainInfo.setSchmId(id);
		schmMainInfo.setDeptId(schmMainInfoVo.getDeptIdtocontext());
		schmMainInfo.setSchmDeptId(schmMainInfoVo.getSchmDeptIDtocontext());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		schmMainInfo.setSchmDate(formatter.parse(schmMainInfoVo.getSchmDate()));
		schmMainInfo.setDocmId(schmMainInfoVo.getDocmId());
		SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
		schmMainInfo.setSchmOffWork(formatter1.parse(schmMainInfoVo
				.getShiftEndDate()));
		schmMainInfo.setSchmOnWork(formatter1.parse(schmMainInfoVo
				.getShiftStartDate()));

		// 设置预约线数
		Integer a1 = Integer.valueOf(schmMainInfoVo.getSchmOnRegiSum());
		Integer a2 = Integer.valueOf(schmMainInfoVo.getSchmDownRegiSum());
		Integer a3 = Integer.valueOf(schmMainInfoVo.getSchmOnSum());
		Integer a4 = Integer.valueOf(schmMainInfoVo.getSchmDownSum());

		schmMainInfo.setSchmOnRegiSum(a1);
		schmMainInfo.setSchmDownRegiSum(a2);
		schmMainInfo.setSchmOnSum(a3);
		schmMainInfo.setSchmDownSum(a4);
		schmMainInfo.setSchmRegiSum(a4 + a3 + a2 + a1);
		// 已预约、已挂号默认为0
		schmMainInfo.setSchmUpreNum(new Integer(0));
		schmMainInfo.setSchmDownreNum(new Integer(0));
		schmMainInfo.setSchmUpgoNum(new Integer(0));
		schmMainInfo.setSchmDowngoNum(new Integer(0));
		schmMainInfo.setSchmRegiOnNum(new Integer(0));
		schmMainInfo.setSchmRegiDownNum(new Integer(0));
		
		schmMainInfo.setHosId(schmMainInfoVo.getHosId());
		// 设置状态
		schmMainInfo.setSchmState("state_1");
		// 设置创建时间和创建用户
		schmMainInfo.setCreateDate(new Date());
		schmMainInfo.setCreateUser(UserUtil.getUserId());
		schmMainInfo.setOrgId(UserUtil.getAuthCode());
		
		schmMainInfo.setStartDate(formatter1.parse(schmMainInfoVo
				.getShiftStartDate()));
		schmMainInfo.setEndDate(formatter1.parse(schmMainInfoVo
				.getShiftEndDate()));
		schmMainInfo.setExt1(schmMainInfoVo.getRegcategoryId());//设置挂号类别ID（插入schm_main_info用）
		schmMainInfo.setRegcategory(schmMainInfoVo.getRegcategory());//设置挂号类别
		schmMainInfo.setServCoding(schmMainInfoVo.getServcoding());//设置挂号类别HIS编码
		schmMainInfo.setRegCategoryId(schmMainInfoVo.getRegcategoryId());
		
		// 调用底层插入方法
		int result = dao.insert(schmMainInfo);
		
		// 查询班次和对应时段信息
		SchmShiftInfoEntity schmShiftInfoEntity = shitdao
				.selectByPrimaryKey(schmMainInfoVo.getShiftId());
		List<SchmTislInfoEntity> ls = schmShiftInfoEntity.getTisls();
		
		// 循环处理时段信息，时段不回很多，不回影响效率
		Integer onNumSum = 0;
		Integer onDownNum = 0;
		if (result > 0) {
			mainList.add(schmMainInfo);
			int i = 0;
			for (SchmTislInfoEntity schmTislInfoEntityto : ls) {
				i++;
				SchmDetailInfo schmDetailInfo = new SchmDetailInfo();
				schmDetailInfo.setSchmDetailId(UUIDGenerator.getUUID());
				// 线上预约和线下预约，等于主表信息中的数值乘以时段线数%，由于相乘有小数位的可能，采用最后一个通过总数相减而成。
				Integer schmOnNum = 0;
				Integer schmDownNum = 0;
				if (i == ls.size()) {
					schmOnNum = a3 - onNumSum;
					schmDownNum = a4 - onDownNum;
				} else {
					if (schmTislInfoEntityto.getTislOnline() != null) {
						schmOnNum = schmTislInfoEntityto.getTislOnline() * a3
								/ 100;
						onNumSum = onNumSum + schmOnNum;
					}

					if (schmTislInfoEntityto.getTislOffline() != null) {
						schmDownNum = schmTislInfoEntityto.getTislOffline()
								* a4 / 100;
						onDownNum = onDownNum + schmDownNum;
					}
				}

				// 设置明细时段信息
				schmDetailInfo.setSchmOnNum(schmOnNum);
				schmDetailInfo.setSchmDownNum(schmDownNum);
				schmDetailInfo.setShiftId(schmMainInfoVo.getShiftId());
				schmDetailInfo.setSchmId(id);
				schmDetailInfo.setTislStartDate(schmTislInfoEntityto
						.getTislStartDate());
				schmDetailInfo.setTislEndDate(schmTislInfoEntityto
						.getTislEndDate());
				schmDetailInfo.setSchmState("state_1");
				schmDetailInfo.setOrgId(UserUtil.getAuthCode());
				schmDetailInfo.setDetailUpreNum(new Integer(0));
				schmDetailInfo.setCreateDate(new Date());
				schmDetailInfo.setCreateUser(UserUtil.getUserId());
				detaildao.insert(schmDetailInfo);
				detailList.add(schmDetailInfo);

			}
			
			//生成排班时间点，查询医生的就诊间隔
			HosDocmInfo hosDocmInfo = docmService.selectByPrimaryKey(schmMainInfoVo.getDocmId());
			
			if(hosDocmInfo!=null){
				schmMainInfo .setDiagnosisInte(hosDocmInfo.getDiagnosisInte());
			}
			queueList = this.queueInfoService.insertQueue(schmMainInfo);
			
			// 返回成功
			if (result > 0) {
				new PushSchmThread(mainList, detailList, queueList, deptService, docmService, shiftInfoService, "00001", reqHisLogService).start();
				return "succ";
			}

		}
		// 返回失败信息
		return "fail";
	}

	/**
	 * 删除排班
	 */
	@Override
	public int deleteBySchmId(String schmId) throws Exception {
		//删除前获得要删除的数据
		List<SchmMainInfo> mainList = new ArrayList<SchmMainInfo>();
		mainList.add(findById(schmId));
		//删除主表
		int result = dao.deleteByPrimaryKey(schmId);
		if (result > 0) {
			//删除后调用接口，通知HIS
			//获取排班明细
			List<SchmDetailInfo> detailList = findDetailsBySchmId(schmId);
			// 查询排队号列表
			SearchParams params = new SearchParams();
			Map<String, Object> searchParams = new HashMap<String, Object>();
			searchParams.put("schmId", schmId);
			params.setSearchParams(searchParams);
			List<SchmQueueInfo> queueList = queueInfoService.findByCondition(params);
			new PushSchmThread(mainList, detailList, queueList, deptService, docmService, shiftInfoService, "00002",reqHisLogService).start();
			result = detaildao.deleteBySchmId(schmId);
			if (result > 0) {
				return queueInfoService.deleteBySchmId(schmId);
			}
		}
		return result;

	}
	public int deleteBatchBySchmId(String[] schmId) throws Exception{
		//删除主表
		int result = dao.deleteBySchmIds(schmId);
		if (result > 0) {
			//删除明细表
			result = detaildao.deleteBySchmIds(schmId);
			if (result > 0) {
				//删除排队号表
				return queueInfoService.deleteBySchmIds(schmId);
			}
		}
		return result;
	}

	/**
	 * 自动排班
	 * 
	 * 2015年9月22日11:28:05 >> 自动排班数据保存完成后，调用HIS接口将数据包装后送给his
	 */
	@Override
	public String saveAutoSchm(SchmAutoSchmInfoVo schmAutoSchmInfoVo)
			throws Exception {
		
		int autoSize = 100;

		// 存在的排班
		List<String> schmMainInfoList;
		
		// 新增的排班
		List<SchmMainInfo> addHisschmMainInfo = new ArrayList<SchmMainInfo>();
		// 新增排班明细
		List<SchmDetailInfo> addHisschmDetailInfo = new ArrayList<SchmDetailInfo>();
		// 新增的排队号列表
		List<SchmQueueInfo> addHisschmQueueInfo = new ArrayList<SchmQueueInfo>();

		// 新增的排班
		List<SchmMainInfo> addschmMainInfo = new ArrayList<SchmMainInfo>();
		// 新增排班明细
		List<SchmDetailInfo> addschmDetailInfo = new ArrayList<SchmDetailInfo>();
		// 新增的排队号列表
		List<SchmQueueInfo> addschmQueueInfo = new ArrayList<SchmQueueInfo>();

		// 基础排班
		List<SchmBaseMainInfoEntity> schmBaseMainInfoEntityList = new ArrayList<SchmBaseMainInfoEntity>();
		// 班次
		HashMap<String, SchmShiftInfoEntity> schmShiftInfoEntityMap = new HashMap<String, SchmShiftInfoEntity>();
		// 时段
		// HashMap<String,SchmTislInfoEntity> schmTislInfoEntitymap;

		// 日期处理
		String nextWeek = schmAutoSchmInfoVo.getNextWeek();

		// 星期日期处理
		Calendar calc = Calendar.getInstance();
		calc.add(Calendar.WEEK_OF_YEAR, 1);
		int initDay = calc.getFirstDayOfWeek();
		calc.set(Calendar.DAY_OF_WEEK, initDay + 1);
		Date startDate = calc.getTime();
		Date endDate = calc.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (!StringUtils.isEmpty(nextWeek) && StringUtils.endsWithIgnoreCase("1", nextWeek)) {
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DAY_OF_MONTH, 6);
			endDate = c.getTime();
		} else {

			startDate = formatter.parse(schmAutoSchmInfoVo.getStartDate());
			endDate = formatter.parse(schmAutoSchmInfoVo.getEndDate());
		}

		SchmMainInfo querycon = new SchmMainInfo();
		querycon.setStartDate(startDate);
		querycon.setEndDate(endDate);
		schmMainInfoList = dao.findByConditionByDate(querycon);
		
		if(schmMainInfoList==null){
			schmMainInfoList = new ArrayList<String>();
		}

		// 基础排班提取
		String alldept = schmAutoSchmInfoVo.getAlldept();

		if (!StringUtils.isEmpty(alldept) && StringUtils.endsWithIgnoreCase("1", alldept)) {

			// 查询所有
			schmBaseMainInfoEntityList = basemaindao.findAllAuto();

		} else {
			schmAutoSchmInfoVo.setSchmState("state_1");
			schmBaseMainInfoEntityList = basemaindao.findByConditionByAutoSchm(schmAutoSchmInfoVo);
		}
		
		
		//没有基础排班直接返回
		if(schmBaseMainInfoEntityList == null || schmBaseMainInfoEntityList.size()==0){
			return "failBase";
		}

		// 查询班次信息
		List<SchmShiftInfoEntity> schmShiftInfoEntityList = shiftdao.findAllByauto();
		if (schmShiftInfoEntityList != null && schmShiftInfoEntityList.size() > 0) {
			for (SchmShiftInfoEntity shiftInfo : schmShiftInfoEntityList) {
				schmShiftInfoEntityMap.put(shiftInfo.getShiftId(), shiftInfo);
			}
		}

		// 处理排班
		Calendar ca = Calendar.getInstance();
		
		int mainresult = 0;
		
		int detailresult = 0; 
		
		int queueresult = 0;
		
		while (startDate.compareTo(endDate) <= 0) {
			// 处理星期
			String week = "";
			if (startDate != null) {
				week = this.getWeek(startDate.getDay());
			}

			for (SchmBaseMainInfoEntity baseMainInfo : schmBaseMainInfoEntityList) {
				// 对星期进行处理

				if (!StringUtils.endsWithIgnoreCase(week,baseMainInfo.getSchmWeek())) {
					continue;

				}

				baseMainInfo.getDocmId();

				String falsgUpdate = formatter.format(startDate) + "$"+ baseMainInfo.getDocmId() + "$" + baseMainInfo.getSchmDeptId()+ "$" + baseMainInfo.getShiftId();
				if (schmMainInfoList.contains(falsgUpdate)) {
					continue;
				} else {
					// 处理新增数据主表
					SchmMainInfo schmMainInfoInsert = new SchmMainInfo();
					String schmid = UUIDGenerator.getUUID();
					schmMainInfoInsert.setSchmId(schmid);
					schmMainInfoInsert.setSchmDate(startDate);
					schmMainInfoInsert.setDocmId(baseMainInfo.getDocmId());
					schmMainInfoInsert.setDeptId(baseMainInfo.getDeptId());
					schmMainInfoInsert.setHosId(baseMainInfo.getDeptHosId());//取科室医院id
					schmMainInfoInsert.setSchmDeptId(baseMainInfo.getSchmDeptId());
					schmMainInfoInsert.setSchmWeek(week);
					schmMainInfoInsert.setShiftId(baseMainInfo.getShiftId());
					SchmShiftInfoEntity shiftInfoto = schmShiftInfoEntityMap.get(baseMainInfo.getShiftId());
					if (shiftInfoto != null) {
						schmMainInfoInsert.setSchmOnWork(shiftInfoto.getShiftStartDate());
						schmMainInfoInsert.setSchmOffWork(shiftInfoto.getShiftEndDate());
					}

					Integer schmOnSum = baseMainInfo.getSchmOnSum();
					Integer schmDownSum = baseMainInfo.getSchmDownSum();
					schmMainInfoInsert.setSchmRegiSum(baseMainInfo.getSchmRegiSum());
					schmMainInfoInsert.setSchmOnSum(schmOnSum);
					schmMainInfoInsert.setSchmDownSum(schmDownSum);
					schmMainInfoInsert.setSchmOnRegiSum(baseMainInfo.getSchmOnRegiSum());
					schmMainInfoInsert.setSchmDownRegiSum(baseMainInfo.getSchmDownRegiSum());

					schmMainInfoInsert.setSchmUpreNum(new Integer(0));

					schmMainInfoInsert.setSchmState("state_1");
					schmMainInfoInsert.setCreateDate(new Date());
					schmMainInfoInsert.setCreateUser(UserUtil.getUserId());
					schmMainInfoInsert.setOrgId(UserUtil.getAuthCode());
					schmMainInfoInsert.setStartDate(shiftInfoto.getShiftStartDate());
					schmMainInfoInsert.setEndDate(shiftInfoto.getShiftEndDate());
					schmMainInfoInsert.setExt1(baseMainInfo.getExt1());//设置挂号类别ID（插入schm_main_info用）
					schmMainInfoInsert.setServCoding(baseMainInfo.getServCoding());//设置挂号类别HIS编码
					schmMainInfoInsert.setRegcategory(baseMainInfo.getRegcategory());//设置挂号类别
                    schmMainInfoInsert.setRegCategoryId(baseMainInfo.getRegCategoryId());//挂号类别(传参用)
					
					addschmMainInfo.add(schmMainInfoInsert);

					// 处理明细数据
					List<SchmTislInfoEntity> tislinfols = null;

					if (shiftInfoto != null) {
						tislinfols = shiftInfoto.getTisls();
					}

					int i = 0;
					Integer schmOnDeteilSum = 0;
					Integer schmDownDeteilSum = 0;
					if (tislinfols != null && tislinfols.size() > 0) {
						for (SchmTislInfoEntity tislinfo : tislinfols) {
							i++;
							SchmDetailInfo detailInfo = new SchmDetailInfo();
							String schmDetailId = UUIDGenerator.getUUID();
							detailInfo.setSchmDetailId(schmDetailId);

							Integer a1 = new Integer(0);
							Integer a2 = new Integer(0);

							if (i == tislinfols.size()) {
								a1 = schmOnSum - schmOnDeteilSum;
								a2 = schmDownSum - schmDownDeteilSum;
							} else {
								if (tislinfo.getTislOnline() != null && schmOnSum != null) {
									a1 = tislinfo.getTislOnline() * schmOnSum/100;
									schmOnDeteilSum = schmOnDeteilSum + a1;
								}

								if (tislinfo.getTislOffline() != null && schmDownSum != null) {
									a2 = tislinfo.getTislOffline()* schmDownSum / 100;
									schmDownDeteilSum = schmDownDeteilSum + a2;
								}
							}

							detailInfo.setSchmOnNum(a1);
							detailInfo.setSchmDownNum(a2);

							detailInfo.setDetailUpreNum(new Integer(0));

							detailInfo.setSchmId(schmid);
							detailInfo.setShiftId(tislinfo.getShiftId());
							detailInfo.setTislStartDate(tislinfo.getTislStartDate());
							detailInfo.setTislEndDate(tislinfo.getTislEndDate());
							detailInfo.setSchmState("state_1");
							detailInfo.setCreateDate(new Date());
							detailInfo.setCreateUser(UserUtil.getUserId());
							detailInfo.setOrgId(UserUtil.getAuthCode());
							addschmDetailInfo.add(detailInfo);
							
							//明细数据提交
							if(addschmDetailInfo!=null && addschmDetailInfo.size()==autoSize){
								detailresult = detaildao.insertBatch(addschmDetailInfo);
								//明细加入his提交队列
								addHisschmDetailInfo.addAll(addschmDetailInfo);
								addschmDetailInfo.clear();
							}
						}
					}
					
					//生成时间点信息，设置就诊间隔秒
					schmMainInfoInsert.setDiagnosisInte(baseMainInfo.getDiagnosisInte());
					List<SchmQueueInfo> queueList = this.queueInfoService.generete(schmMainInfoInsert);
					
					//判空处理
					if(queueList != null && queueList.size() > 0){
						addHisschmQueueInfo.addAll(queueList);
						// 分批次处理排号列表数据
						for (SchmQueueInfo queueInfo : queueList) {
							addschmQueueInfo.add(queueInfo);
							if (addschmQueueInfo != null && addschmQueueInfo.size() == autoSize) {
								queueresult = this.queueDao.insertBatch(addschmQueueInfo);
								addschmQueueInfo.clear();
							}
						}
						
						if (addschmQueueInfo != null && addschmQueueInfo.size() > 0) {
							queueresult = this.queueDao.insertBatch(addschmQueueInfo);
							addschmQueueInfo.clear();
						}
					}
					
					
					
					
					
				}
			}

			// 处理完成后添加日期
			ca.setTime(startDate);
			ca.add(ca.DATE, 1);
			startDate = ca.getTime();
			
			//提交数据
			if(addschmMainInfo!=null && addschmMainInfo.size()==autoSize){
				mainresult = dao.insertBatch(addschmMainInfo);
				//主排班加入his提交队列
				addHisschmMainInfo.addAll(addschmMainInfo);
				addschmMainInfo.clear();
			}
		}
		
		//相同排班日期，医生、排班科室都相同
		if(addschmMainInfo== null || addschmMainInfo.size()==0){
			return "suuce";
		}

		//小于提交行数，数据重新提交
		if (addschmMainInfo != null && addschmMainInfo.size() > 0) {
			//主排班加入his提交队列
			addHisschmMainInfo.addAll(addschmMainInfo);
			mainresult = dao.insertBatch(addschmMainInfo);
		}
	
		if (mainresult > 0 && addschmDetailInfo != null && addschmDetailInfo.size() > 0) {
			//明细加入his提交队列
			addHisschmDetailInfo.addAll(addschmDetailInfo);
			detailresult = detaildao.insertBatch(addschmDetailInfo);
		}
		
		if (mainresult > 0 && detailresult > 0) {
			//自动排班插入成功，调用线程处理HIS推送
			new PushSchmThread(addHisschmMainInfo, addHisschmDetailInfo, addHisschmQueueInfo, deptService, docmService, shiftInfoService, "00001", reqHisLogService).start();
			return "suuce";
		} else {
			return "fail";
		}

	}

	/**
	 * 根据主键查询排班主表信息
	 */
	@Override
	public SchmMainInfo findById(String schmId) {
		return dao.selectByPrimaryKey(schmId);
	}

	/**
	 * 查询明细时段信息
	 */
	@Override
	public List<SchmDetailInfo> findDetailsBySchmId(String schmId) {
		if (schmId == null) {
			return null;
		}
		return detaildao.findDetailInfoBySchmId(schmId);
	}

	/**
	 * 删除明细时段信息
	 */
	@Override
	public int deleteDetailByDetailId(String detailId) throws Exception {
		return detaildao.deleteByPrimaryKey(detailId);
	}

	/**
	 * 更新主表限数、明细时段信息
	 * @throws ParseException 
	 */
	@Override
	public int updateMainDetail(SchmMainInfoVo schmMainInfovo) throws ParseException {
		//更新主表，限数信息
		SchmMainInfo vo = dao.selectByPrimaryKey(schmMainInfovo.getSchmId());
		
		Integer num1=Integer.valueOf(schmMainInfovo.getSchmOnRegiSum());
		Integer num2=Integer.valueOf(schmMainInfovo.getSchmDownRegiSum());
		Integer num3=Integer.valueOf(schmMainInfovo.getSchmOnSum());
		Integer num4=Integer.valueOf(schmMainInfovo.getSchmDownSum());
		vo.setSchmOnRegiSum(num1);
		vo.setSchmDownRegiSum(num2);
		vo.setSchmOnSum(num3);
		vo.setSchmDownSum(num4);
		Integer regisum = num1+num2+num3+num4;
		vo.setSchmRegiSum(regisum);
		vo.setUpdateUser(UserUtil.getUserId());
		vo.setUpdateDate(new Date());
		dao.updateByPrimaryKey(vo);
		//存放推送数据
		List<SchmMainInfo> mainList = new ArrayList<SchmMainInfo>();
		List<SchmDetailInfo> detailList = new ArrayList<SchmDetailInfo>();
		mainList.add(vo);
		//更新明细信息
		int count = 0;
		for (SchmDetailInfo detail : schmMainInfovo.getDetails()) {
			if (org.apache.commons.lang.StringUtils.isBlank(detail.getSchmDetailId())) {
				detail.setShiftId(schmMainInfovo.getShiftId());
				detail.setSchmDetailId(UUIDGenerator.getUUID());
				detail.setCreateDate(new Date());
				detail.setCreateUser(UserUtil.getUserId());
				
				detaildao.insertSelective(detail);
			} else {
				detail.setUpdateDate(new Date());
				detail.setUpdateUser(UserUtil.getUserId());
				count += detaildao.updateByPrimaryKeySelective(detail);
			}
			//添加推送明细
			detailList.add(detail);
		}
		SchmMainInfo schmMainInfo = new SchmMainInfo();
		BeanUtils.copyProperties(schmMainInfovo, schmMainInfo);
		SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
		schmMainInfo.setStartDate(formatter1.parse(schmMainInfovo.getShiftStartDate()));
		schmMainInfo.setEndDate(formatter1.parse(schmMainInfovo.getShiftEndDate()));
		schmMainInfo.setSchmRegiSum(regisum);
		schmMainInfo.setHosId(vo.getHosId());
		
		//生成排班时间点，查询医生的就诊间隔
		HosDocmInfo hosDocmInfo = docmService.selectByPrimaryKey(vo.getDocmId());
		
		if(hosDocmInfo!=null){
			schmMainInfo .setDiagnosisInte(hosDocmInfo.getDiagnosisInte());
		}
		
		
		List<SchmQueueInfo> queueList = this.queueInfoService.updateQueue(schmMainInfo);
		new PushSchmThread(mainList, detailList, queueList, deptService, docmService, shiftInfoService, "00003",reqHisLogService).start();
		return count;
	}

	/**
	 * 根据天数得到星期
	 * 
	 * @param day
	 * @return
	 */
	public String getWeek(int day) {
		String week = "";
		if (day == 0) {
			week = "星期日";
		}
		if (day == 1) {
			week = "星期一";
		}
		if (day == 2) {
			week = "星期二";
		}
		if (day == 3) {
			week = "星期三";
		}
		if (day == 4) {
			week = "星期四";
		}
		if (day == 5) {
			week = "星期五";
		}
		if (day == 6) {
			week = "星期六";
		}
		return week;
	}
	
	/**
	 * 排班定时任务回滚
	 */
	@Override
	public void updateTaskSchm() throws Exception {
		SchmMainInfo record = new SchmMainInfo();
		record.setSchmDate(new Date());
		record.setUpdateDate(new Date());
		record.setUpdateUser("TASK_TIMER");
		dao.updateTaskSchm(record);
	}
	
	/**
	 * 医生id查询排班
	 */
	@Override
	public List<SchmMainInfo> findByDocmId(String docmId) throws Exception{
		return dao.findByDocmId(docmId);
	}
	
	/**
	 * 根据排班科室id、医生id查询是否存在排班信息
	 */
	@Override
	public List<SchmMainInfo> findByConditionBydel(SearchParams params){
		return dao.findByConditionBydel(params);
	}

	@Override
	public void getExportExcel(HttpServletRequest request, HttpServletResponse response, SearchParams params) throws Exception {
		List<SchmMainInfo> list = findByCondition(params);
		if(list.size()>0){
			//开始excel导出
			String fname = "医生排班";
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
			Label schmDateColumn = new Label(0,0,"排班日期",wcfFormat);
			sheet.addCell(schmDateColumn);
			//设置某个单元格宽
			sheet.setColumnView(0, 20);

			Label schmWeekColumn = new Label(1,0,"星期",wcfFormat);
			sheet.addCell(schmWeekColumn);
			//设置某个单元格宽
			sheet.setColumnView(1, 20);

			Label docmNameColumn = new Label(2,0,"医生",wcfFormat);
			sheet.addCell(docmNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(2, 20);

			Label docmTitleNameColumn = new Label(3,0,"职称",wcfFormat);
			sheet.addCell(docmTitleNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(3, 20);

			Label schmDeptNameColumn = new Label(4,0,"排班科室",wcfFormat);
			sheet.addCell(schmDeptNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(4, 20);

			Label deptNameColumn = new Label(5,0,"所属科室",wcfFormat);
			sheet.addCell(deptNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(5, 20);

			Label shiftNameColumn = new Label(6,0,"班次",wcfFormat);
			sheet.addCell(shiftNameColumn);
			//设置某个单元格宽
			sheet.setColumnView(6, 20);

			Label schmOnWorkColumn = new Label(7,0,"开始时间",wcfFormat);
			sheet.addCell(schmOnWorkColumn);
			//设置某个单元格宽
			sheet.setColumnView(7, 20);

			Label schmOffWorkColumn = new Label(8,0,"截止时间",wcfFormat);
			sheet.addCell(schmOffWorkColumn);
			//设置某个单元格宽
			sheet.setColumnView(8, 20);

			Label schmRegiSumColumn = new Label(9,0,"总限数",wcfFormat);
			sheet.addCell(schmRegiSumColumn);
			//设置某个单元格宽
			sheet.setColumnView(9, 20);

			Label schmOnRegiSumColumn = new Label(10,0,"线上挂号限数",wcfFormat);
			sheet.addCell(schmOnRegiSumColumn);
			//设置某个单元格宽
			sheet.setColumnView(10, 20);

			Label schmRegiOnNumColumn = new Label(11,0,"线上已挂号数",wcfFormat);
			sheet.addCell(schmRegiOnNumColumn);
			//设置某个单元格宽
			sheet.setColumnView(11, 20);

			Label schmDownRegiSumColumn = new Label(12,0,"线下挂号限数",wcfFormat);
			sheet.addCell(schmDownRegiSumColumn);
			//设置某个单元格宽
			sheet.setColumnView(12, 20);

			Label schmRegiDownNumColumn = new Label(13,0,"线下已挂号数",wcfFormat);
			sheet.addCell(schmRegiDownNumColumn);
			//设置某个单元格宽
			sheet.setColumnView(13, 20);

			Label schmOnSumColumn = new Label(14,0,"线上预约限数",wcfFormat);
			sheet.addCell(schmOnSumColumn);
			//设置某个单元格宽
			sheet.setColumnView(14, 20);

			Label schmUpreNumColumn = new Label(15,0,"线上已预约数",wcfFormat);
			sheet.addCell(schmUpreNumColumn);
			//设置某个单元格宽
			sheet.setColumnView(15, 20);

			Label schmDownSumColumn = new Label(16,0,"线下预约限数",wcfFormat);
			sheet.addCell(schmDownSumColumn);
			//设置某个单元格宽
			sheet.setColumnView(16, 20);

			Label schmDownreNumColumn = new Label(17,0,"线下已预约数",wcfFormat);
			sheet.addCell(schmDownreNumColumn);
			//设置某个单元格宽
			sheet.setColumnView(17, 20);

			Label schmStateColumn = new Label(18,0,"状态",wcfFormat);
			sheet.addCell(schmStateColumn);
			//设置某个单元格宽
			sheet.setColumnView(18, 20);

			//加载每行数据
			for (int i = 1; i <= list.size(); i++) {

				Label schmDate = null;
				if (list.get(i-1).getSchmDate() != null) {
					schmDate = new Label(0, i, formatDate(list.get(i-1).getSchmDate()), wcfFormat);
				}
                sheet.addCell(schmDate);


				Label schmWeek = new Label(1,i,list.get(i-1).getSchmWeek(),wcfFormat);
				sheet.addCell(schmWeek);

				Label docmName = new Label(2,i,list.get(i-1).getDocmName(),wcfFormat);
				sheet.addCell(docmName);

				Label docmTitleName = new Label(3,i,list.get(i-1).getDocmTitleName(),wcfFormat);
				sheet.addCell(docmTitleName);

				Label schmDeptName = new Label(4,i,list.get(i-1).getSchmDeptName(),wcfFormat);
				sheet.addCell(schmDeptName);

				Label deptName = new Label(5,i,list.get(i-1).getDeptName(),wcfFormat);
				sheet.addCell(deptName);

				Label shiftName = new Label(6,i,list.get(i-1).getShiftName(),wcfFormat);
				sheet.addCell(shiftName);

				Label schmOnWork = null;
				if (list.get(i - 1).getSchmOnWork() != null) {
					schmOnWork = new Label(7, i, formatHourAndMinute(list.get(i - 1).getSchmOnWork()), wcfFormat);
				}
                sheet.addCell(schmOnWork);


				Label schmOffWork = null;
				if (list.get(i - 1).getSchmOffWork() != null) {
					schmOffWork = new Label(8, i, formatHourAndMinute(list.get(i - 1).getSchmOffWork()), wcfFormat);
				}
                sheet.addCell(schmOffWork);


                Label schmRegiSum = new Label(9,i,String.valueOf(list.get(i-1).getSchmRegiSum()),wcfFormat);
                sheet.addCell(schmRegiSum);


                Label schmOnRegiSum = new Label(10,i,String.valueOf(list.get(i-1).getSchmOnRegiSum()),wcfFormat);
				sheet.addCell(schmOnRegiSum);

                Label schmRegiOnNum = new Label(11,i,String.valueOf(list.get(i-1).getSchmRegiOnNum()),wcfFormat);
				sheet.addCell(schmRegiOnNum);

                Label schmDownRegiSum = new Label(12,i,String.valueOf(list.get(i-1).getSchmDownRegiSum()),wcfFormat);
				sheet.addCell(schmDownRegiSum);

                Label schmRegiDownNum = new Label(13,i,String.valueOf(list.get(i-1).getSchmRegiDownNum()),wcfFormat);
				sheet.addCell(schmRegiDownNum);

                Label schmOnSum = new Label(14,i,String.valueOf(list.get(i-1).getSchmOnSum()),wcfFormat);
				sheet.addCell(schmOnSum);


				Label schmUpreNum = new Label(15,i,String.valueOf(list.get(i-1).getSchmUpreNum()),wcfFormat);
				sheet.addCell(schmUpreNum);

				Label schmDownSum = new Label(16,i,String.valueOf(list.get(i-1).getSchmDownSum()),wcfFormat);
				sheet.addCell(schmDownSum);


				Label schmDownreNum = new Label(17,i,String.valueOf(list.get(i-1).getSchmDownreNum()),wcfFormat);
				sheet.addCell(schmDownreNum);

				Label schmState = new Label(18,i,list.get(i-1).getSchmStateName(),wcfFormat);
				sheet.addCell(schmState);

			}
			//把创建的内容写入到输出流中，并关闭输出流
			workbook.write();
			workbook.close();
			os.close();
		}

	}

	/**
	 *	更新挂号类别信息
	 */
	@Override
	public void updateRegCategory(SchmMainInfoVo schmMainInfovo,SchmMainInfo mainInfo) throws Exception {
		//存放推送信息
		List<SchmMainInfo> mainList = new ArrayList<SchmMainInfo>();
		List<SchmDetailInfo> detailList ;
		List<SchmQueueInfo> queueList;
		//更新主表,挂号类别ID、医生、排班科室
		SchmMainInfo vo = dao.selectByPrimaryKey(schmMainInfovo.getSchmId());
		vo.setExt1(schmMainInfovo.getRegcategoryId());
		vo.setRegCategoryId(schmMainInfovo.getRegcategoryId());
		vo.setRegcategory(schmMainInfovo.getRegcategory());
		vo.setServCoding(schmMainInfovo.getServcoding());
		vo.setDocmId(schmMainInfovo.getDocmId());
		vo.setDeptId(schmMainInfovo.getDeptIdtocontext());
		vo.setSchmDeptId(schmMainInfovo.getSchmDeptIDtocontext());
		vo.setUpdateUser(UserUtil.getUserId());
		vo.setUpdateDate(new Date());
		// 修改线上预约数和线下挂号数，线下预约数
		vo.setSchmOnSum(Integer.parseInt(schmMainInfovo.getSchmOnSum()));
		vo.setSchmDownSum(Integer.parseInt(schmMainInfovo.getSchmDownSum()));
		vo.setSchmDownRegiSum(Integer.parseInt(schmMainInfovo.getSchmDownRegiSum()));
		vo.setSchmRegiSum(Integer.parseInt(schmMainInfovo.getSchmOnSum())+Integer.parseInt(schmMainInfovo.getSchmDownSum())+Integer.parseInt(schmMainInfovo.getSchmOnRegiSum())+Integer.parseInt(schmMainInfovo.getSchmDownRegiSum()));

		//获取排班明细
		detailList = findDetailsBySchmId(schmMainInfovo.getSchmId());
		// 查询排队号列表
		SearchParams params = new SearchParams();
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("schmId", schmMainInfovo.getSchmId());
		params.setSearchParams(searchParams);
		queueList = queueInfoService.findByCondition(params);


		/**
		 * 如果有修改[线下挂号限数] 和 [线上预约限数]
		 */
		System.out.println(schmMainInfovo.getSchmOnSum()+"----"+mainInfo.getSchmOnSum()+"-----"+ schmMainInfovo.getSchmOnSum().equals(String.valueOf(mainInfo.getSchmOnSum())));
		if (!schmMainInfovo.getSchmOnSum().equals(String.valueOf(mainInfo.getSchmOnSum()))) {
			// 时间段处理
			for (SchmDetailInfo schmDetailInfo : detailList) {
				schmDetailInfo.setExt1(schmDetailInfo.getDetailUpreNum() + "");
				schmDetailInfo.setDetailUpreNum(schmDetailInfo.getSchmOnNum());
				detaildao.updateByPrimaryKey(schmDetailInfo);
			}

			for (SchmQueueInfo schmQueueInfo : queueList) {
				schmQueueInfo.setExt1(schmQueueInfo.getQueueState());
				schmQueueInfo.setQueueState("1");
				// 处理时间点信息
				queueInfoService.update(schmQueueInfo);
			}
			//获取排班明细
			detailList = findDetailsBySchmId(schmMainInfovo.getSchmId());
			// 查询排队号列表
			queueList = queueInfoService.findByCondition(params);
		}
		dao.updateByPrimaryKey(vo);
		mainList.add(vo);

		//推送删除
		pushDelete(mainList, detailList, queueList, deptService, docmService, shiftInfoService, "00002",reqHisLogService);
		//推送新增
		new PushSchmThread(mainList, detailList, queueList, deptService, docmService, shiftInfoService, "00001", reqHisLogService).start();



	}

	@Override
	public List<SchmMainInfo> findByRegCategoryId(String ext1) throws Exception {
		return dao.findByRegCategoryId(ext1);

	}

	@Override
	public int findByShiftIdByDocmDate(SchmMainInfoVo schmMainInfoVo) {
		return dao.findByShiftIdByDocmDate(schmMainInfoVo);
	}

	public String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	public String formatHourAndMinute(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 *	删除排班推送his(更新用)
	 */
	public void pushDelete(List<SchmMainInfo> schmMainList, List<SchmDetailInfo> schmDetailList,
						   List<SchmQueueInfo> schmQueueList,
						   IHosDeptInfoService deptService,IHosDocmService docmService,
						   ISchmShiftInfoService shiftInfoService, String operationType,
						   IReqHisLogService reqHisLogService){

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
