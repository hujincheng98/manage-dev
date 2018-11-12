/*
 * @(#) ApiResServiceImpl  2017-08-07 15:34:15
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
package com.skynet.rimp.channelInfo.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skynet.common.SearchParams;
import com.skynet.platform.common.service.impl.BaseServiceImpl;
import com.skynet.rimp.channelInfo.dao.ApiResAuthDao;
import com.skynet.rimp.channelInfo.dao.ApiResDao;
import com.skynet.rimp.channelInfo.dto.ApiResDTO;
import com.skynet.rimp.channelInfo.service.IApiResService;
import com.skynet.rimp.channelInfo.vo.ApiRes;
import com.skynet.rimp.channelInfo.vo.ApiResAuth;
import com.skynet.rimp.common.utils.userUtil.UserUtil;
import com.skynet.rimp.hisBaseInfo.dao.HosInfoDao;
import com.skynet.rimp.hisBaseInfo.vo.HosInfo;
/**
 * 
 * <p>
 * Title: 接口表
 * </p>
 * <p>
 * Description: TODO 日志表Impl层
 * 
 * @author llt
 * @version 1.00.00 创建时间：2017-08-07 09:34:15
 * 
 *          <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * 
 */
@Service
public class ApiResServiceImpl extends BaseServiceImpl<ApiRes> implements
		IApiResService {

	@Autowired
	private ApiResDao apiResDao;
	
	@Autowired
	private ApiResAuthDao apiResAuthDao;
	
	@Autowired
	private HosInfoDao hosInfoDao;

	@Override
	public List<ApiRes> findByCondition(SearchParams params) throws Exception {
		return null;
	}

	@Override
	public List<ApiRes> findAll() throws Exception {
		return this.apiResDao.findAll();
	}

	// list去除重复值
	@SuppressWarnings("rawtypes")
	public List removeDuplicate(List list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).equals(list.get(i))) {
					list.remove(j);
				}
			}
		}
		return list;
	}
	
	//根据orgId获取hosId
	public String getHosIdByOrgId(){
		String hosId = null;
		//获取机构编码
		String code = UserUtil.getAuthCode();
		//0表示admin
		if(!code.trim().equals("0")){
			HosInfo hosInfo = this.hosInfoDao.findByHosOrgId(code);
			if(hosInfo!=null){
				hosId = hosInfo.getHosId();
			}		
		}
		return hosId;
	}

	// 加载接口数据
	@SuppressWarnings({"unchecked","unused"})
	@Override
	public List<ApiResDTO> loadApiResData(HttpServletRequest request) {
		List<ApiResDTO> list = new ArrayList<ApiResDTO>();
		List<String> models = new ArrayList<String>();
		String chId = request.getParameter("chId");
		String token = request.getParameter("token");
		String hosId = getHosIdByOrgId();		
		// 查出接口表所有数据
		List<ApiRes> apiResList = this.apiResDao.findAll();
		if (apiResList.size() > 0) {
			for (ApiRes apiRes : apiResList) {
				if (apiRes.getModel() != null && !apiRes.getModel().equals("")) {
					String model = apiRes.getModel().trim();
					models.add(model);
				}
			}		
		// 共有多少组模块
		models = removeDuplicate(models);		

		ApiResAuth apiResAuthModel = new ApiResAuth();
		apiResAuthModel.setToken(token);
		//1:拥有权限
		apiResAuthModel.setAuth(1);
		
		//根据token获取对应api_res_auth表已拥有权限的接口
		List<ApiResAuth> apiResAuthList = null;
		apiResAuthList = this.apiResAuthDao.findByConditionForUser(apiResAuthModel);
		
		//该渠道中所有接口都没有权限 
		if(apiResAuthList.size() == 0){
			for (ApiRes api : apiResList) {
				api.setChecked("false");
			}
		}		
		//该渠道中有些接口有权限
		if(apiResAuthList.size()>0){
			for (ApiResAuth apiResAuth : apiResAuthList) {
				if(apiResAuth.getApiUrl()!=null){
					String araUrl = apiResAuth.getApiUrl().trim();
					for (ApiRes apiRes : apiResList) {
						if(apiRes.getApiUrl()!=null){
							String arUrl = apiRes.getApiUrl().trim();
							if(araUrl.equals(arUrl)){
								//该接口有权限
								apiRes.setChecked("true");
								break;
							}
						}
					}
				}
			}
		}
		
		//封装整合数据返回给页面
		if(models.size()>0){
			for (int i = 0; i < models.size(); i++) {
				ApiResDTO apiResDTO = new ApiResDTO();
				List<ApiRes> childList = new ArrayList<ApiRes>();
				String model = models.get(i).trim();
				apiResDTO.setModel(model);
				apiResDTO.setChId(chId);
				apiResDTO.setToken(token);
				for (int j = 0; j < apiResList.size(); j++) {
					String childModel = apiResList.get(j).getModel().trim();
					if(model.equals(childModel)){
						//属于相同模块下
						childList.add(apiResList.get(j));
					}
				}
				apiResDTO.setChildren(childList);
				//判断某一模块下的子接口是否全部选中
				List<ApiRes> childs = apiResDTO.getChildren();
				for (int k = 0; k < childs.size(); k++) {
					String childChecked = childs.get(k).getChecked().trim();
					if(childChecked.equals("false")){
						apiResDTO.setChecked("false");
						break;
					}
					if(childChecked.equals("true")){
						apiResDTO.setChecked("true");
					}
				}
				list.add(apiResDTO);
			}
		}		
	  }
		return list;
	}

}
