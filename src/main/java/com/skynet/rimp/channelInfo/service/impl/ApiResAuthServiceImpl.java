/*
 * @(#) ApiResAuthServiceImpl  2017-08-07 15:34:25
 *
 * Copyright 2003 by TiuWeb Corporation.
 * 51 zhangba six Road, xian, PRC 710065 //  Replace with xian’s address
 * 
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * TiuWeb Corporation.  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with TiuWeb.
 */
package com.skynet.rimp.channelInfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.channelInfo.dao.ApiResAuthDao;
import com.skynet.rimp.channelInfo.dao.ApiResDao;
import com.skynet.rimp.channelInfo.dto.ApiResDTO;
import com.skynet.rimp.channelInfo.service.IApiResAuthService;
import com.skynet.rimp.channelInfo.vo.ApiRes;
import com.skynet.rimp.channelInfo.vo.ApiResAuth;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.common.utils.uuid.UUIDGenerator;
import com.skynet.rimp.hisBaseInfo.dao.HosInfoDao;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;
/**
 * 
 * <p>
 * Title: 接口权限表
 * </p>
 * <p>
 * Description: TODO 日志表Impl层
 * 
 * @author llt
 * @version 1.00.00 创建时间：2017-08-07 10:34:25
 * 
 *          <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */
@Service
public class ApiResAuthServiceImpl extends BaseServiceImpl<ApiResAuth>
		implements IApiResAuthService {

	@Autowired
	private ApiResAuthDao apiResAuthDao;

	@Autowired
	private ApiResDao apiResDao;

	@Autowired
	private HosInfoDao hosInfoDao;

	@Override
	public List<ApiResAuth> findByCondition(SearchParams params)
			throws Exception {

		return null;
	}

	// 根据orgId获取hosId
	public String getHosIdByOrgId() {
		String hosId = null;
		// 获取机构编码
		String code = UserUtil.getAuthCode();
		// 0表示admin
		if (!code.trim().equals("0")) {
			HosInfo hosInfo = this.hosInfoDao.findByHosOrgId(code);
			if (hosInfo != null) {
				hosId = hosInfo.getHosId();
			}
		}
		return hosId;
	}
	
	// ApiRes数据合并到ApiResAuth
	public ApiResAuth apiResToApiResAuth(ApiResDTO apiResDTO,ApiRes apiRes){
		String hosId = getHosIdByOrgId();
		ApiResAuth ara = new ApiResAuth();
		ara.setId(UUIDGenerator.getUUID());
		ara.setToken(apiResDTO.getToken());
		ara.setModel(apiRes.getModel());
		ara.setApiName(apiRes.getApiName());
		ara.setApiUrl(apiRes.getApiUrl().trim());
		if(hosId!=null){
			ara.setHosId(hosId);
		}
		// 是否拥有权限（默认为0）：1-有；0-没有；
		ara.setAuth(1);
		ara.setCreateDate(new Date());
		ara.setCreateUser(UserUtil.getUserId());
		return ara;
	}

	@Override
	// 保存接口权限
	public void saveApiResAuth(ApiResDTO apiResDTO) throws Exception {
		String hosId = getHosIdByOrgId();
		String resId = apiResDTO.getResId();
		String[] data = null;
		// 储存勾选中的接口
		List<ApiRes> checkedApis = new ArrayList<ApiRes>();
		ApiResAuth apiResAuthModel = new ApiResAuth();
		apiResAuthModel.setToken(apiResDTO.getToken());
		// 查出当前渠道下所有授权的接口
		List<ApiResAuth> apiResAuthList = this.apiResAuthDao.findByConditionForUser(apiResAuthModel);
		// 勾选了部分接口
		if (resId != null && !resId.equals("")) {
			data = resId.split(",");
			for (int i = 0; i < data.length; i++) {
				ApiRes apiRes = apiResDao.findByPrimaryKey(data[i]);
				checkedApis.add(apiRes);
			}
			if(apiResAuthList.size() == 0){// 该角色下没有授权的接口
				// 循环插入授权接口
				for (ApiRes apiRes : checkedApis) {
					// ApiRes数据合并到ApiResAuth
					ApiResAuth ara = apiResToApiResAuth(apiResDTO, apiRes);					
					// 新增授权接口
					this.apiResAuthDao.insert(ara);					
				}				
			}
			if(apiResAuthList.size() >0){ // 该角色下有授权的接口
				ApiResAuth apiResAuthSear = new ApiResAuth();
				for (ApiRes ar : checkedApis) {
					String apiResUrl = ar.getApiUrl().trim();
					// 根据ApiUrl和hosId获取ApiResAuth
					apiResAuthSear.setApiUrl(apiResUrl);
					apiResAuthSear.setHosId(hosId);
					apiResAuthSear.setToken(apiResDTO.getToken());
					List<ApiResAuth> list = null;
					list = this.apiResAuthDao.findByConditionForUser(apiResAuthSear);
					if(list.size() == 0){ // ApiRes中有该接口而ApiResAuth没有
						// ApiRes数据合并到ApiResAuth
						ApiResAuth ara = apiResToApiResAuth(apiResDTO, ar);
						// 新增授权接口
						this.apiResAuthDao.insert(ara);
					}
					if(list.size()> 0){ // ApiResAuth有该接口
						for (int i = 0; i < list.size(); i++) {
							ApiResAuth ara = list.get(i);			
							if(ara.getAuth()== 0){
								// 更新该接口
								ara.setAuth(1);
								ara.setUpdateDate(new Date());
								ara.setUpdateUser(UserUtil.getUserId());
								this.apiResAuthDao.update(ara);
							}
						}
					}											
				}

				// 存储被选中的apiUrl
				List<String> apiUrl = new ArrayList<>();
				for (ApiRes apiRes : checkedApis) {
					String url = apiRes.getApiUrl().trim();
					apiUrl.add(url);
				}
				List<ApiResAuth> outList = new ArrayList<>();
				// 查出当前渠道下apiResAuth中所有接口
				ApiResAuth araSer = new ApiResAuth();
				araSer.setToken(apiResDTO.getToken());
				List<ApiResAuth> allara = this.apiResAuthDao.findByConditionForUser(araSer);		
				for (int i = 0; i < apiUrl.size(); i++) {
					// 每个被选中的接口url
					String checkedUrl = apiUrl.get(i).trim();
					for (int j = 0; j < allara.size(); j++) {
						if(checkedUrl.equals(allara.get(j).getApiUrl().trim())){ // 被选中的接口url等于当前接口url
							allara.remove(j);
							break;
						}
					}						
				}
				outList = allara;				
				if(outList.size()>0){
					// 除了选中之外的接口权限全部设置成0-没有
					for (ApiResAuth apiResAuth : outList) {
						apiResAuth.setAuth(0);
						apiResAuth.setUpdateDate(new Date());
						apiResAuth.setUpdateUser(UserUtil.getUserId());
						// 更新该接口
						this.apiResAuthDao.update(apiResAuth);
					}
				}				
			}			
		} else {
			// 全部接口都没有勾选
			for (ApiResAuth apiResAuth : apiResAuthList) {
				if(apiResAuth.getAuth() == 1){
					apiResAuth.setAuth(0);
					apiResAuth.setUpdateDate(new Date());
					apiResAuth.setUpdateUser(UserUtil.getUserId());
					// 更新该接口
					this.apiResAuthDao.update(apiResAuth);	
				}
			}
		}		
	}

	@Override
	public List<ApiResAuth> findByConditionForAdm(ApiResAuth apiResAuth) {

		return apiResAuthDao.findByConditionForAdm(apiResAuth);
	}

	@Override
	public List<ApiResAuth> findByConditionForUser(ApiResAuth apiResAuth) {
		
		return apiResAuthDao.findByConditionForUser(apiResAuth);
	}

	@Override
	// 用于更新Redis缓存封装数据
	public Map<String, String> findApiResAuthAll() {
		// 查询ApiResAuth表，有权限的所有接口
		List<ApiResAuth> apiResAuth = this.apiResAuthDao.findAll();
		Map<String, JSONArray> api = new Hashtable<String, JSONArray>(); 
        if(apiResAuth.size()>0 && apiResAuth!=null){
        	for(ApiResAuth resAuth:apiResAuth){
        		String tokenUrl = resAuth.getToken()+resAuth.getApiUrl();
        		if(api.containsKey("tokenUrl")){
        			JSONArray  jsonarry = api.get(tokenUrl);
					jsonarry.add(resAuth);
					api.put(tokenUrl, jsonarry);
				}else{
					JSONArray jsonarry = new JSONArray();
					jsonarry.add(resAuth);
					api.put(tokenUrl, jsonarry);
				}
        	}
        }
		Map<String, String> resultmap = new Hashtable<String, String>();
		// 循环处理
		Set<String> keySets = api.keySet();
		for (String key : keySets) {
		JSONArray jsonarry = api.get(key);
		resultmap.put(key, jsonarry.toString());
		}
		return resultmap;
	}

	@Override
	public List<ApiResAuth> findAll() throws Exception {		
		return this.apiResAuthDao.findAll();
	}

}
