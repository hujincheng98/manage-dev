package com.skynet.rimp.channelInfo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skynet.common.SearchParams;
import com.skynet.his.utils.HisServiceUrlConfig;
import com.skynet.his.utils.PushHisHttpRequest;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.channelInfo.dao.OtherHisDatachneelsInfoDao;
import com.skynet.rimp.channelInfo.dto.HosDeptInfoDTO;
import com.skynet.rimp.channelInfo.dto.HosDocmInfoDTO;
import com.skynet.rimp.channelInfo.dto.HosInfoDTO;
import com.skynet.rimp.channelInfo.service.IOtherHisDatachneelsService;
import com.skynet.rimp.channelInfo.service.IReqHisLogService;
import com.skynet.rimp.channelInfo.vo.OtherHisDatachneelsInfo;
import com.skynet.rimp.channelInfo.vo.ReqHisLogInfo;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.hisBaseInfo.service.IHosDeptInfoService;
import com.skynet.rimp.hisBaseInfo.service.IHosDocmService;
import com.skynet.rimp.hisBaseInfo.vo.HosDeptInfo;
import com.skynet.rimp.hisBaseInfo.vo.HosDocmInfo;

@Service("otherHisDatachneelsService")
public class OtherHisDatachneelsServiceImpl extends BaseServiceImpl<OtherHisDatachneelsInfo> implements IOtherHisDatachneelsService {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OtherHisDatachneelsServiceImpl.class);

	private ObjectMapper objectMapper = new  ObjectMapper();  
	
	@Autowired
	private OtherHisDatachneelsInfoDao otherHisDatachneelsInfoDao;
	
	@Autowired
	private IHosDeptInfoService hosDeptInfoService;
	
	@Autowired
	private IHosDocmService hosDocmService;
	
	@Autowired
	private IReqHisLogService reqHisLogService;
	
	@Override
	public List<OtherHisDatachneelsInfo> findByCondition(SearchParams params) throws Exception {
		return this.otherHisDatachneelsInfoDao.findByCondition(params);
	}

	@Override
	public List<OtherHisDatachneelsInfo> findAll() throws Exception {
		return this.otherHisDatachneelsInfoDao.findAll();
	}

	@Override
	public int save(OtherHisDatachneelsInfo record) {
		record.setChHisToken(UUIDGenerator.getUUID());
		record.setOrgId(UserUtil.getAuthCode());
		record.setCreateUser(UserUtil.getUserId());
		record.setCreateDate(new Date());
		return this.otherHisDatachneelsInfoDao.insert(record);
	}

	@Override
	public int update(OtherHisDatachneelsInfo record) {
		record.setUpdateUser(UserUtil.getUserId());
		record.setUpdateDate(new Date());
		return this.otherHisDatachneelsInfoDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(Integer id) {
		return this.otherHisDatachneelsInfoDao.deleteByPrimaryKey(id);
	}

	@Override
	public Map<String, Object> syncDept(Integer id) {
		String requestData = "";//请求参数
		String result = "";
		Map<String, Object> map = new HashMap<String, Object>();
		String urlPath = "";
		try {
			OtherHisDatachneelsInfo record = this.otherHisDatachneelsInfoDao.selectByPrimaryKey(id);
			HosInfoDTO hosInfoDTO = new HosInfoDTO();
			hosInfoDTO.setHosId(record.getHosId());
			requestData = HisServiceUrlConfig.PRE_SERVICE_LDE + JSONObject.fromObject(hosInfoDTO).toString();
			urlPath = record.getChHisUrl() + HisServiceUrlConfig.DEPT_INFO;
			result = PushHisHttpRequest.doPost(urlPath, requestData);
			if(StringUtils.isBlank(result)) { // 查询结果为空
				map.put("success", false);
				map.put("message", "返回结果为空");
			} else {
				JSONObject resultJson = JSONObject.fromObject(result);
				String code = resultJson.getString("code");
				//不为空返回参数code不为0，为失败信息
				if(!StringUtils.equals("0", code)){
					map.put("success", false);
					map.put("message", resultJson.get("responseData").toString());
				} else {
					String responseData = resultJson.get("responseData").toString();
					List<HosDeptInfo> deptInfoList = this.hosDeptInfoService.findAll();
					List<HosDeptInfoDTO> dtoList = objectMapper.readValue(responseData, getCollectionType(objectMapper, List.class, HosDeptInfoDTO.class));
					// 定义科室ID为Key的map集合
					Map<String, HosDeptInfo> oldDataMap = new HashMap<String, HosDeptInfo>();
					// 新增的科室
					List<HosDeptInfo> addList = new ArrayList<HosDeptInfo>();
					// 更新的科室
					List<HosDeptInfo> editList = new ArrayList<HosDeptInfo>();
					for (HosDeptInfo deptInfo : deptInfoList) {
						if(StringUtils.isNotBlank(deptInfo.getDeptHisCode())) {
							oldDataMap.put(deptInfo.getDeptHisCode(), deptInfo);
						}
					}
					if (!dtoList.isEmpty()) {
						for (HosDeptInfoDTO dto : dtoList) {
							if (oldDataMap.containsKey(dto.getDeptId())) {
								HosDeptInfo deptInfo = oldDataMap.get(dto.getDeptId());
								deptInfo.setDeptName(dto.getDeptName());
								deptInfo.setDeptPinyCode(dto.getDeptPinyCode());
								deptInfo.setDeptTelep(dto.getDeptTelep());
								editList.add(deptInfo);
							} else {
								HosDeptInfo deptInfo = new HosDeptInfo();
								deptInfo.setDeptHisCode(dto.getDeptId());
								deptInfo.setDeptName(dto.getDeptName());
								deptInfo.setDeptPinyCode(dto.getDeptPinyCode());
								deptInfo.setDeptTelep(dto.getDeptTelep());
								deptInfo.setDeptIntro(dto.getDeptIntro());
								deptInfo.setDeptType("medical_01");
								deptInfo.setHosId(record.getHosId());
								deptInfo.setExt3("HIS");
								deptInfo.setDeptState("dept_state_1");
								addList.add(deptInfo);
							}
						}
					}
					if (!addList.isEmpty()) {
						for (HosDeptInfo deptInfo : addList) {
							this.hosDeptInfoService.insert(deptInfo);
						}
					}
					if (!editList.isEmpty()) {
						for (HosDeptInfo deptInfo : editList) {
							this.hosDeptInfoService.updateByPrimaryKey(deptInfo);
						}
					}
					
					map.put("success", true);
					map.put("message", "同步成功");
					
					ReqHisLogInfo hisLogInfo = new ReqHisLogInfo();
					hisLogInfo.setId(UUIDGenerator.getUUID());
					hisLogInfo.setReqUrl(urlPath);
					hisLogInfo.setReqDate(new Date());
					hisLogInfo.setTransactioncode("40002");
					hisLogInfo.setRequestdata(requestData);
					hisLogInfo.setRespCode("0");
					hisLogInfo.setRespData(responseData);
					hisLogInfo.setOperationtype("同步科室");
					reqHisLogService.insert(hisLogInfo);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ReqHisLogInfo hisLogInfo = new ReqHisLogInfo();
			hisLogInfo.setId(UUIDGenerator.getUUID());
			hisLogInfo.setReqUrl(urlPath);
			hisLogInfo.setReqDate(new Date());
			hisLogInfo.setTransactioncode("40002");
			hisLogInfo.setRequestdata("not record request");
			hisLogInfo.setOperationtype("同步科室");
			hisLogInfo.setRespCode("1");
			hisLogInfo.setRespData("未知异常！");
			if(e!=null){
				hisLogInfo.setRespData(e.getMessage());
			}
			reqHisLogService.insert(hisLogInfo);
			
			map.put("success", false);
			map.put("message", "同步失败");
			
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public Map<String, Object> syncDocm(Integer id) {
		String requestData = "";//请求参数
		String result = "";
		Map<String, Object> map = new HashMap<String, Object>();
		String urlPath = "";
		try {
			OtherHisDatachneelsInfo record = this.otherHisDatachneelsInfoDao.selectByPrimaryKey(id);
			HosInfoDTO hosInfoDTO = new HosInfoDTO();
			hosInfoDTO.setHosId(record.getHosId());
			requestData = HisServiceUrlConfig.PRE_SERVICE_LDE + JSONObject.fromObject(hosInfoDTO).toString();
			urlPath = record.getChHisUrl() + HisServiceUrlConfig.DOCM_INFO;
			result = PushHisHttpRequest.doPost(urlPath, requestData);
			if(StringUtils.isBlank(result)) { // 查询结果为空
				map.put("success", false);
				map.put("message", "返回结果为空");
			} else {
				JSONObject resultJson = JSONObject.fromObject(result);
				String code = resultJson.getString("code");
				//不为空返回参数code不为0，为失败信息
				if(!StringUtils.equals("0", code)){
					map.put("success", false);
					map.put("message", resultJson.get("responseData").toString());
				} else {
					String responseData = resultJson.get("responseData").toString();
					List<HosDocmInfo> docmInfoList = this.hosDocmService.findAll();
					List<HosDocmInfoDTO> dtoList = objectMapper.readValue(responseData, getCollectionType(objectMapper, List.class, HosDocmInfoDTO.class));
					// 定义科室ID为Key的map集合
					Map<String, HosDocmInfo> oldDataMap = new HashMap<String, HosDocmInfo>();
					// 新增的医生
					List<HosDocmInfo> addList = new ArrayList<HosDocmInfo>();
					// 更新的医生
					List<HosDocmInfo> editList = new ArrayList<HosDocmInfo>();
					for (HosDocmInfo docmInfo : docmInfoList) {
						if (StringUtils.isNotBlank(docmInfo.getDocmHisCode())) {
							oldDataMap.put(docmInfo.getDocmHisCode(), docmInfo);
						}
					}
					if (!dtoList.isEmpty()) {
						for (HosDocmInfoDTO dto : dtoList) {
							if (oldDataMap.containsKey(dto.getDocmId())) {
								HosDocmInfo docmInfo = oldDataMap.get(dto.getDocmId());
								docmInfo.setDocmName(dto.getDocmName());
								docmInfo.setDocmPinyCode(dto.getDocmPinyCode());
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
								docmInfo.setDocmBirthDate(format.parse(dto.getDocmBirthDate()));
								docmInfo.setDocmWageNum(dto.getDocmTelep());
								docmInfo.setDocmPosition(dto.getDocmPosition());
								editList.add(docmInfo);
							} else {
								HosDocmInfo docmInfo = new HosDocmInfo();
								docmInfo.setDocmName(dto.getDocmName());
								docmInfo.setDocmPinyCode(dto.getDocmPinyCode());
								docmInfo.setDocmHisCode(dto.getDocmId());
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
								docmInfo.setDocmBirthDate(format.parse(dto.getDocmBirthDate()));
								docmInfo.setDocmWageNum(dto.getDocmTelep());
								docmInfo.setDocmPosition(dto.getDocmPosition());
								docmInfo.setDocmIntro(dto.getDocmIntro());
								docmInfo.setDocmSpec(dto.getDocmSpec());
								docmInfo.setDocmCredType("CredType_01");
								docmInfo.setHosId(record.getHosId());
								docmInfo.setExt3("HIS");
								docmInfo.setDocmState("docm_state_2");
								HosDeptInfo deptInfo = this.hosDeptInfoService.selectByHisCode(dto.getDocmId());
								if (deptInfo != null) {
									docmInfo.setDeptId(deptInfo.getDeptId());
								}
								addList.add(docmInfo);
							}
						}
					}
					if (!addList.isEmpty()) {
						for (HosDocmInfo deptInfo : addList) {
							this.hosDocmService.save(deptInfo);
						}
					}
					if (!editList.isEmpty()) {
						for (HosDocmInfo deptInfo : editList) {
							this.hosDocmService.update(deptInfo);
						}
					}
					
					map.put("success", true);
					map.put("message", "同步成功");
					
					ReqHisLogInfo hisLogInfo = new ReqHisLogInfo();
					hisLogInfo.setId(UUIDGenerator.getUUID());
					hisLogInfo.setReqUrl(urlPath);
					hisLogInfo.setReqDate(new Date());
					hisLogInfo.setTransactioncode("40003");
					hisLogInfo.setRequestdata(requestData);
					hisLogInfo.setRespCode("0");
					hisLogInfo.setRespData(responseData);
					hisLogInfo.setOperationtype("同步医生");
					reqHisLogService.insert(hisLogInfo);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ReqHisLogInfo hisLogInfo = new ReqHisLogInfo();
			hisLogInfo.setId(UUIDGenerator.getUUID());
			hisLogInfo.setReqUrl(urlPath);
			hisLogInfo.setReqDate(new Date());
			hisLogInfo.setTransactioncode("40003");
			hisLogInfo.setRequestdata("not record request");
			hisLogInfo.setRespCode("1");
			hisLogInfo.setOperationtype("同步医生");
			hisLogInfo.setRespData("未知异常！");
			if(e!=null){
				hisLogInfo.setRespData(e.getMessage());
			}
			reqHisLogService.insert(hisLogInfo);
			
			map.put("success", false);
			map.put("message", "同步失败");
			
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取泛型的Collection Type
	 * 
	 * @param collectionClass
	 *            泛型的Collection
	 * @param elementClasses
	 *            元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	@SuppressWarnings("deprecation")
	public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

}
