package com.skynet.rimp.registerInfo.action;


import com.skynet.common.Pagination;
import com.skynet.common.SearchParams;
import com.skynet.rimp.registerInfo.service.ISchmRegCategoryService;
import com.skynet.rimp.registerInfo.vo.SchmRegCategoryInfo;
import com.skynet.rimp.schmInfo.service.ISchmMainInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * @author wangzhihua
 * @version 1.00.00
 * @createTime 2018/7/5 15:13:45
 *
 * <pre>
 * 修改记录:
 * 版本号    修改人        修改日期       修改内容
 * <pre>
 */


@Controller
@RequestMapping(value = "/rimp/regcategory")
public class SchmRegCategoryAction {

    private static final Logger logger = Logger.getLogger(SchmRegCategoryAction.class);

    @Autowired
    private ISchmRegCategoryService iSchmRegCategoryService;
    @Autowired
    private ISchmMainInfoService schmMainInfoService;

    /**
     * 跳转首页
     * @return
     */
    @RequestMapping(value = "index.do", method = RequestMethod.GET)
    public String index() {
        return "registerInfo/schm_reg_category_index";
    }

    /**
     * 跳转新增页面
     * @return
     */
    @RequestMapping(value = "add.do", method = RequestMethod.GET)
    public String add() {
        return "registerInfo/schm_reg_category_add";
    }

    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping(value = "edit.do", method = RequestMethod.GET)
    public String edit() {
        return "registerInfo/schm_reg_category_edit";
    }


    /**
     * 挂号类别查询分页
     * @param params
     * @return
     */
    @RequestMapping(value = "list.json", method = RequestMethod.POST)
    @ResponseBody
    public Pagination<SchmRegCategoryInfo> list(SearchParams params) {
        Pagination<SchmRegCategoryInfo> pageList = null;
        try {
            pageList = this.iSchmRegCategoryService.findPageByCondition(params);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return pageList;
    }

    /**
     * 新增信息
     * @param record
     * @return
     */
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> save(@RequestBody SchmRegCategoryInfo record) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            this.iSchmRegCategoryService.save(record);
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
     * 删除信息
     * @param ids
     * @return
     */
    @RequestMapping(value = "delete.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(String[] ids) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            for(String id : ids ){
                if (schmMainInfoService.findByRegCategoryId(id).isEmpty()){
                    this.iSchmRegCategoryService.delete(id);
                    map.put("state", "success");
                    map.put("message", "删除成功");
                }else{
                    map.put("state", "fail");
                    map.put("message", "该挂号类别已生成排班数据，无法删除！");
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            map.put("state", "err");
            map.put("message", "删除失败");
        }
        return map;
    }


    /**
     * 更新信息
     * @param record
     * @return
     */
    @RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(@RequestBody SchmRegCategoryInfo record) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            this.iSchmRegCategoryService.update(record);
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

