package com.skynet.rimp.channelInfo.action;

import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.channelInfo.service.IOtherChannelsInfoService;
import com.skynet.rimp.channelInfo.service.IOtherHisChannelsInfoService;
import com.skynet.rimp.channelInfo.vo.OtherChannelsInfo;
import com.skynet.rimp.channelInfo.vo.OtherHisChannelsInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: 业务渠道管理</p>
 * <p>Description: </p>
 *
 * @author Huyang
 * @version 1.00.00
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 */
@Controller
@RequestMapping("/rimp/otherHisChannels")
public class OtherHisChannelsAction {
	/**
	 * Logger for this class
	 */
	private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private IOtherHisChannelsInfoService otherHisChannelsInfoService;

	/**
	 * 跳转到渠道管理首页
	 * @return
	 */
	@RequestMapping(value = "index.do", method = RequestMethod.GET)
	public String index() {
		return "channelInfo/other_his_channels_index";
	}
    @RequestMapping(value = "add.do", method = RequestMethod.GET)
    public String add() {
        return "channelInfo/other_his_channels_add";
    }
    /**
     * 跳转到渠道管理修改页面
     * @return
     */
    @RequestMapping(value = "edit.do", method = RequestMethod.GET)
    public String edit() {
        return "channelInfo/other_his_channels_edit";
    }
    /**
     * 返回列表json数据
     * @param params
     * @return
     */
    @RequestMapping(value = "list.json", method = RequestMethod.POST)
    @ResponseBody
    public Pagination<OtherHisChannelsInfo> list(SearchParams params) {
        Pagination<OtherHisChannelsInfo> pageList = null;
        try {
            pageList = otherHisChannelsInfoService.findPageByCondition(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pageList;
    }
    /**
     * 保存渠道信息
     * @param otherHisChannelsInfo
     * @return
     */
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@RequestBody OtherHisChannelsInfo otherHisChannelsInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            this.otherHisChannelsInfoService.save(otherHisChannelsInfo);
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
     * @param chHisId
     * @return
     */
    @RequestMapping(value = "delete.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String chHisId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            SearchParams params = new SearchParams();
            Map<String, Object> searchParams = new HashMap<String, Object>();
            searchParams.put("chHisId", chHisId);
            searchParams.put("chState", "state_1");
            params.setSearchParams(searchParams);
            List<OtherHisChannelsInfo> list = this.otherHisChannelsInfoService.findByCondition(params);
            if (list.isEmpty()) {
                this.otherHisChannelsInfoService.delete(chHisId);
                map.put("state", "success");
                map.put("message", "删除成功");
            } else {
                map.put("state", "fail");
                map.put("message", "此渠道为启用状态，不能删除");
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
     * @param otherHisChannelsInfo
     * @return
     */
    @RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@RequestBody OtherHisChannelsInfo otherHisChannelsInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            this.otherHisChannelsInfoService.update(otherHisChannelsInfo);
            map.put("state", "success");
            map.put("message", "更新成功");
        } catch (Exception e) {
            logger.error(e.getMessage());
            map.put("state", "fail");
            map.put("message", "更新失败");
        }
        return map;
    }
}
