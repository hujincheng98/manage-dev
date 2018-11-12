package com.skynet.rimp.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * <p>
 * Title: json工具类
 * </p>
 */

public class JsonlUtils {
  /**
   * 将string转换成listBean ,class 需要默认无参数构造方法
   *
   * @param jsonArrStr 需要反序列化的字符串
   * @param clazz      被反序列化之后的类
   * @return 实体list
   */
  public static List getListFromJsonArrStr(String jsonArrStr, Class clazz) {
    JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
    List list = new ArrayList();
    for (int i = 0; i < jsonArr.size(); i++) {
      list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz));
    }
    return list;
  }
  /**
   * 验证字符串格式是否有效
   *
   * @param jsonStr
   * @return
   */
  public static boolean validateFromJson(String jsonStr) {

    boolean ret = true;

    if (StringUtils.isBlank(jsonStr)) {
      return false;
    }
    // 验证字符串格式
    try {
      JSONObject jsonObject = JSONObject.fromObject(jsonStr);

    } catch (Exception e) {
      ret = false;
    }

    return ret;

  }
}