package com.skynet.rimp.channelInfo.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.his.common.RedisConfig;
import com.skynet.rimp.channelInfo.dto.ApiResDTO;
import com.skynet.rimp.channelInfo.service.IApiResAuthService;
import com.skynet.rimp.channelInfo.service.IApiResService;
import com.skynet.rimp.channelInfo.service.IOtherChannelsInfoService;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import com.skynet.rimp.common.utils.RedisService;
import com.skynet.rimp.hisBaseInfo.service.IHosInfoService;
import com.skynet.rimp.messPush.dto.MessChInfoMappingDTO;
import com.skynet.rimp.messPush.service.IMessChInfoMappingService;
import com.skynet.rimp.messPush.service.IMessPushService;
import com.skynet.rimp.messPush.vo.MessChInfoMapping;
import com.skynet.rimp.messPush.vo.MessPushInfo;

/**
 * <p>Title: 第三方渠道管理</p>
 * <p>Description: </p>
 *
 * @author Liujian
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Controller
@RequestMapping("/rimp/otherChannels")
public class OtherChannelsAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OtherChannelsAction.class);

	@Autowired
	private IOtherChannelsInfoService otherChannelsInfoService;
	
	@Autowired
	private IMessPushService messPushService;
	
	@Autowired
	private IMessChInfoMappingService messChInfoMappingService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private IApiResService apiResService;
	
	@Autowired
	private IApiResAuthService apiResAuthService;
	
	@Autowired
	private IHosInfoService hosInfoService;
	
	/**
	 * 跳转到渠道管理首页
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "channelInfo/other_channels_index";
	}
	
	/**
	 * 跳转到渠道管理新增页面
	 * @return
	 */
	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	public String add() {
		return "channelInfo/other_channels_add";
	}
	
	/**
	 * 跳转到渠道管理修改页面
	 * @return
	 */
	@RequestMapping(value = "edit.do", method = RequestMethod.GET)
	public String edit() {
		return "channelInfo/other_channels_edit";
	}
	
	/**
	 * 返回列表json数据
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public Pagination<OtherChannelsInfo> list(SearchParams params) {
		Pagination<OtherChannelsInfo> pageList = null;
		try {
			pageList = this.otherChannelsInfoService.findPageByCondition(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return pageList;
	}
	
	/**
	 * 返回列表json数据
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "messList.json", method = RequestMethod.GET)
	@ResponseBody
	public List<MessPushInfo> messList() {
		List<MessPushInfo> list = null;
		try {
			list = this.messPushService.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	/**
	 * 保存渠道信息
	 * @param otherChannelsInfo
	 * @return
	 */
	@RequestMapping(value = "messSave.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> messSave(String[] messCodes, String chId, String hosId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 删除渠道消息定制
			this.messChInfoMappingService.deleteByChId(chId);
			for (String messCode : messCodes) {
				MessChInfoMapping mapping = new MessChInfoMapping();
				mapping.setChId(chId);
				mapping.setHosId(hosId);
				mapping.setMessCode(messCode);
				this.messChInfoMappingService.insert(mapping);
			}
			// 更新Redis缓存
			List<MessChInfoMappingDTO> dtoList = this.messChInfoMappingService.findMessChInfoRedisByChId(chId);
			if (dtoList != null && dtoList.size() > 0) {
				redisService.delHashValKey(RedisConfig.CHANNELS_MESS_PUSH_KEY, chId);
				redisService.setHashValKey(RedisConfig.CHANNELS_MESS_PUSH_KEY, chId, JSONArray.fromObject(dtoList).toString());
			}
			map.put("state", "success");
			map.put("message", "保存成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 查询已定制消息
	 * @param chId
	 * @param hosId
	 * @return
	 */
	@RequestMapping(value = "findMessCodes.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findMessCodes(String chId) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<MessChInfoMapping> list = this.messChInfoMappingService.findMessChInfoByChId(chId);
			map.put("state", "success");
			map.put("message", list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "结果为空");
		}
		return map; 
	}
	
	/**
	 * 保存渠道信息
	 * @param otherChannelsInfo
	 * @return
	 */
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(@RequestBody OtherChannelsInfo otherChannelsInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.otherChannelsInfoService.save(otherChannelsInfo);
			map.put("state", "success");
			map.put("message", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	
	/**
	 * 删除渠道信息
	 * @param chId
	 * @return
	 */
	@RequestMapping(value = "delete.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String[] chIdArr) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			boolean boo = false; // 标识位默认false
			for(String chid:chIdArr){
				SearchParams params = new SearchParams();
				Map<String, Object> searchParams = new HashMap<String, Object>();
				searchParams.put("chId", chid);
				searchParams.put("chState", "state_1");
				params.setSearchParams(searchParams);
				List<OtherChannelsInfo> list = this.otherChannelsInfoService.findByCondition(params);
				if (list != null && list.size() > 0) {
					boo = true;
					break;
				}
			}
			if (!boo) {
				this.otherChannelsInfoService.deleteByKeyArr(chIdArr);
				// 删除渠道消息定制
				this.messChInfoMappingService.deleteByKeyArr(chIdArr);
				// 更新Redis缓存
				redisService.delHashValKey(RedisConfig.CHANNELS_MESS_PUSH_KEY, chIdArr);
				map.put("state", "success");
				map.put("message", "删除成功");
			} else {
				map.put("state", "fail");
				map.put("message", "有启用状态渠道信息，不能删除");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "删除失败");
		}
		return map;
	}
	
	/**
	 * 更新渠道信息
	 * @param otherChannelsInfo
	 * @return
	 */
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(@RequestBody OtherChannelsInfo otherChannelsInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.otherChannelsInfoService.update(otherChannelsInfo);
			map.put("state", "success");
			map.put("message", "更新成功");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "更新失败");
		}
		return map;
	}
	
	/**
	 * 跳转到接口权限设置页面
	 * @return
	 */
	@RequestMapping(value = "resAuthView.do", method = RequestMethod.GET)
	public ModelAndView resAuthView(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		String chId = request.getParameter("chId");
		String token = request.getParameter("token");
		//渠道信息表中对应该渠道的hosId
		String chaHosId = request.getParameter("chaHosId");
		view.addObject("chId", chId);
		view.addObject("token", token);
		view.addObject("chaHosId", chaHosId);
		view.setViewName("channelInfo/res_auth_view");
		return view;
	}
	
	/**
	 * 保存接口权限设置
	 * @param otherChannelsInfo
	 * @return
	 */
	@RequestMapping(value = "saveResAuth.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveResAuth(ApiResDTO apiResDTO) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.apiResAuthService.saveApiResAuth(apiResDTO);				
 			
			Map<String, String> apiResAuth = this.apiResAuthService.findApiResAuthAll();
			if(apiResAuth!=null){
				// 更新Redis缓存
				redisService.setHashVal(RedisConfig.API_RES_AUTH, apiResAuth);
			}
			map.put("state", "success");
			map.put("message", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			map.put("state", "fail");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * 加载接口数据
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "loadRes.json", method = RequestMethod.GET)
	@ResponseBody
	public List<ApiResDTO> loadRes(HttpServletRequest request) {
		List<ApiResDTO> list = this.apiResService.loadApiResData(request);
		return list;
	}
	
}
