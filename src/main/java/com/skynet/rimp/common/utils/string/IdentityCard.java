package com.skynet.rimp.common.utils.string;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: 身份证解析  <br>
 * Description:解析身份证号，获取性别，年龄，地区，出生年月 <br>
 *
 * @author gaowei
 * @date  2017-09-30
 */
public class IdentityCard {
  private Integer sexCode; // 性别编码
  private Date birthday; // 出生年月
  private int age; //周岁
  private int mage; // 医学年龄，需结合ageUnit
  private String ageUnit;// 年龄单位
  private String provinceCode; // 省（直辖市、直辖区）代码
  private String provinceName; // 省（直辖市、直辖区）代码
  private String cityCode;//地级市（自治州）代码
  private String countyCode;//区县代码
  private Map<String, String> privceMap = new HashMap<String, String>() {
    {
      this.put("11", "北京");
      this.put("12", "天津");
      this.put("13", "河北");
      this.put("14", "山西");
      this.put("15", "内蒙古");
      this.put("21", "辽宁");
      this.put("22", "吉林");
      this.put("23", "黑龙江");
      this.put("31", "上海");
      this.put("32", "江苏");
      this.put("33", "浙江");
      this.put("34", "安徽");
      this.put("35", "福建");
      this.put("36", "江西");
      this.put("37", "山东");
      this.put("41", "河南");
      this.put("42", "湖北");
      this.put("43", "湖南");
      this.put("44", "广东");
      this.put("45", "广西");
      this.put("46", "海南");
      this.put("50", "重庆");
      this.put("51", "四川");
      this.put("52", "贵州");
      this.put("53", "云南");
      this.put("54", "西藏");
      this.put("61", "陕西");
      this.put("62", "甘肃");
      this.put("63", "青海");
      this.put("64", "宁夏");
      this.put("65", "新疆");
      this.put("71", "台湾");
      this.put("81", "香港");
      this.put("82", "澳门");
      this.put("91", "国外");
    }
  };

  /**
   * 初始化
   *
   * @param identityCard
   */
  public IdentityCard(String identityCard) {
    int len = identityCard.length();
    String date = "";
    String sex = "";
    if (len == 18) {
      date = identityCard.substring(6, 14);
      this.provinceCode = identityCard.substring(0, 2);
      this.cityCode = identityCard.substring(2, 4);
      this.countyCode = identityCard.substring(4, 6);
      this.provinceName= this.privceMap.get(this.provinceCode);
      sex = identityCard.substring(16,17);
    } else if (len == 15) {
      date = "19" + identityCard.substring(6, 11);
      sex = identityCard.substring(13);
    }
    if(!sex.equals("")){
    	this.sexCode = Integer.parseInt(sex) % 2;
    }
    try {
      if(!date.equals("")){
    	  this.birthday = DateUtils.StrFormatDT(date, "yyyyMMdd");
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    this.setAge();
  }

  /**
   * 获取年龄
   *
   * @return
   */
  public int getAge() {
    return this.age;
  }

  /**
   * 获取医学年龄
   *
   * @return
   */
  public int getMedicalAge() {
    return this.mage;
  }

  /**
   * 获取性别
   *
   * @return 中文性别
   */
  public String getSex() {
	  if(this.sexCode!= null){
		  return this.sexCode == 1 ? "男" : "女";
	  }
    return null;
  }

  /**
   * 获取性别代码
   *
   * @return 性别代码：1-男，2-女
   */
  public int getSexCode() {
    return this.sexCode == 1 ? 1 : 2;
  }

  /**
   * 获取省份代码
   * @return 省份代码
   */
  public String getProvinceCode(){
    return  this.provinceCode;
  }
  /**
   * 获取省份名称
   * @return 获取省份名称
   */
  public String getProvinceName(){
    return this.provinceName;
  }

  /**
   *  获取年龄单位
   * @return 年龄单位
   */
  public  String getAgeUnit(){
    return this.ageUnit;
  }
  /**
   *  获取城市代码
   * @return 城市代码
   */
  public  String getCityCode(){
    return this.cityCode;
  }
  /**
   *  获取区县代码
   * @return 区县代码
   */
  public String getCountyCode(){
    return this.countyCode;
  }
  /**
   * 计算年龄
   */
  private void setAge() {
    Date now = new Date();
    if(this.birthday!=null ){
        this.age = DateUtils.yearBetween(this.birthday, now);
        this.mage = this.age;
        // 若不满一岁则计算月领
        if (this.age > 0) {
          this.ageUnit = "岁";
          this.age = age;
        } else {
          int mouth = DateUtils.monthBetween(this.birthday, now);
          // 若不满一月则计算日龄
          if (mouth > 0) {
            this.mage = mouth;
            this.ageUnit = "月";
          } else {
            int day = 0;
            try {
              day = DateUtils.daysBetween(this.birthday, now);
            } catch (ParseException e) {
              e.printStackTrace();
            }
            this.mage = day;
            this.ageUnit = "天";
          }
        }
    }
  }

  /**
   * 数字验证
   *
   * @param str 目标字符串
   * @return
   */
  private boolean isDigital(String str) {
    return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
  }

}
