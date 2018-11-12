package com.skynet.rimp.common.utils.string;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

  private static final String DEFAULT_PATTERN = "yyyyMMddHHmmss";

  private static final String[] WEEK = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

  /**
   * @param time
   * @param pattern
   * @return
   */
  public static String timestamp2str(Timestamp time, String pattern) {
    if (time == null) {
      throw new IllegalArgumentException("Timestamp is null");
    }
    if (pattern != null && !"".equals(pattern)) {
      if (!"yyyyMMddHHmmss".equals(pattern) && !"yyyy-MM-dd HH:mm:ss".equals(pattern) && !"yyyy-MM-dd".equals(pattern) && !"MM/dd/yyyy".equals(pattern)) {
        throw new IllegalArgumentException("Date format [" + pattern + "] is invalid");
      }
    } else {
      pattern = DEFAULT_PATTERN;
    }

    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
    cal.setTime(time);
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.format(cal.getTime());
  }

  public static Date str2Date(String timeStr, String pattern) {
    if (timeStr == null) {
      throw new IllegalArgumentException("Timestamp is null");
    }
    if (pattern != null && !"".equals(pattern)) {
      if (!"yyyyMMddHHmmss".equals(pattern) && !"yyyy-MM-dd HH:mm:ss".equals(pattern) && !"MM/dd/yyyy HH:mm:ss".equals(pattern) && !"yyyy-MM-dd".equals(pattern) && !"MM/dd/yyyy".equals(pattern)
          && !"HH:mm".equals(pattern)) {
        throw new IllegalArgumentException("Date format [" + pattern + "] is invalid");
      }
    } else {
      pattern = DEFAULT_PATTERN;
    }
    Date d = null;
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      d = sdf.parse(timeStr);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return d;
  }

  public static Timestamp str2Timestamp(String timeStr, String pattern) {
    Date d = str2Date(timeStr, pattern);
    Timestamp result = new Timestamp(d.getTime());
    return result;
  }

  /**
   * 根据日期计算当前星期几
   *
   * @param pTime 日期
   * @return 星期
   * @throws Exception
   */
  public static String dayForWeek(String pTime) {

    int dayForWeek = 0;
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      Calendar c = Calendar.getInstance();
      c.setTime(format.parse(pTime));
      dayForWeek = 0;
      if (c.get(Calendar.DAY_OF_WEEK) == 1) {
        dayForWeek = 7;
      } else {
        dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return WEEK[dayForWeek - 1];
  }

  /**
   * 获取日期
   *
   * @param date
   * @param pattern
   * @return
   */
  public static String getDate(Date date, String pattern) {
    if (pattern == null || pattern.equals("")) {
      pattern = "yyyy-MM-dd";
    }
    SimpleDateFormat sf = new SimpleDateFormat(pattern);
    return sf.format(date);
  }

  /**
   * 日期加减,按天
   *
   * @param date 原始日期
   * @param pattern 返回日期格式
   * @param day 增加值
   * @return 运算后日期
   */
  public static String dateAddDays(Date date, String pattern, int day) {
    if (pattern == null || pattern.equals("")) {
      pattern = "yyyy-MM-dd";
    }
    SimpleDateFormat sf = new SimpleDateFormat(pattern);
    Calendar c = Calendar.getInstance();
    c.setTime(date); // 设置当前日期
    c.add(Calendar.DATE, day); // 日期分钟加1,Calendar.DATE(天),Calendar.HOUR(小时)
    date = c.getTime(); // 结果
    return sf.format(date);
  }

  /**
   * 比较两个日期的大小
   *
   * @param releDate 放号时间
   * @param nowDate  当前时间
   * @return 放号时间大于当前时间返回true 反之false
   * @throws Exception
   */
  public static boolean compare_date(String releDate, String nowDate, String pattern) {
    DateFormat df = new SimpleDateFormat(pattern);
    try {
      Date d1 = df.parse(releDate);
      Date d2 = df.parse(nowDate);
      if (d1.getTime() > d2.getTime()) {
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * 将微信返回的支付时间转换0000-00-00 00:00:00
   *
   * @param time 20150529121245
   * @return
   */
  public static String wxstrToDate(String time) {
    String n, y, d, s, f, m;
    n = time.substring(0, 4);
    y = time.substring(4, 6);
    d = time.substring(6, 8);
    s = time.substring(8, 10);
    f = time.substring(10, 12);
    m = time.substring(12, 14);
    return n + "-" + y + "-" + d + " " + s + ":" + f + ":" + m;
  }

  public static void main(String[] args) {

    System.out.println(wxstrToDate("20150529121245"));
  }

  // ===============冯渊Add ↓↓========================================
  // [start] 时间格式化 返回Date类型

  /**
   * 格式化时间
   *
   * @param date 日期
   * @param format 格式化字符串 eg."yyyy-MM-dd HH:mm:ss"
   * @return 返回Date类型的日期
   * @throws ParseException
   */
  public static Date DTFormat(Date date, String format) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    String dateStr = sdf.format(date);
    date = sdf.parse(dateStr);
    return date;
  }

  /**
   * 格式化时间
   *
   * @param date   需要格式化的日期
   * @param format 为格式化字符串 eg."yyyy-MM-dd HH:mm:ss"
   * @return 返回String类型的日期
   */
  public static String DTFormatStr(Date date, String format) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }


  /**
   * 字符串转换为时间 并 格式化
   *
   * @param dateStr 需要格式化的日期
   * @param format  为格式化字符串 eg."yyyy-MM-dd HH:mm:ss"
   * @return 返回String类型的日期
   * @throws ParseException
   */
  public static Date StrFormatDT(String dateStr, String format) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date date = sdf.parse(dateStr);
    return date;
  }
  /**
   * 计算两个字符串的日期格式的之间相差的毫秒数
   *
   * @param begin 较小的时间
   * @param end  较大的时间
   * @return 相差毫秒数
   * @throws ParseException
   * @author gaowei
   */
  public static long msBetween(String begin, String end) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    cal.setTime(sdf.parse(begin));
    long time1 = cal.getTimeInMillis();
    cal.setTime(sdf.parse(end));
    long time2 = cal.getTimeInMillis();
    return time2-time1;
  }

  /**
   * 计算两个字符串的日期格式的之间相差的天数
   *
   * @param smdate 较小的时间
   * @param bdate  较大的时间
   * @return
   * @throws ParseException
   * @author 冯渊
   */
  public static int daysBetween(String smdate, String bdate) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    cal.setTime(sdf.parse(smdate));
    long time1 = cal.getTimeInMillis();
    cal.setTime(sdf.parse(bdate));
    long time2 = cal.getTimeInMillis();
    long between_days = (time2 - time1) / (1000 * 3600 * 24);
    return Integer.parseInt(String.valueOf(between_days));
  }

  /**
   * @param smdate 较小的时间
   * @param bdate  较大的时间
   * @return 相差天数
   * @throws ParseException
   * @author 冯渊
   */
  public static int daysBetween(Date smdate, Date bdate) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return daysBetween(sdf.format(smdate), sdf.format(bdate));
  }

  /**
   * 计算两个日期相差年数
   * @param begin 开始时间
   * @param end 结束时间
   * @return 相差年数
   */
  public static int yearBetween(Date begin, Date end){
    Calendar cal = Calendar.getInstance();
    cal.setTime(begin);
    int year=cal.get(Calendar.YEAR);
    int month =cal.get(Calendar.MONTH);
    int day =cal.get(Calendar.DAY_OF_MONTH);
    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(end);
    int year2=cal2.get(Calendar.YEAR);
    int month2 =cal2.get(Calendar.MONTH);
    int day2 =cal2.get(Calendar.DAY_OF_MONTH);
    int age= 0;
    if(year2>year){
       age=year2-year;
       if(month2<month){
         --age;
       }else{
         if(day2<day){
           --age;
         }
       }
    }
    return age;
  }

  /**
   * 计算两个日期相差月数
   * @param begin 开始时间
   * @param end 结束时间
   * @return 相差年数
   */
  public static int monthBetween(Date begin, Date end){
    Calendar cal = Calendar.getInstance();
    cal.setTime(begin);
    int year=cal.get(Calendar.YEAR);
    int month =cal.get(Calendar.MONTH);
    int day =cal.get(Calendar.DAY_OF_MONTH);
    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(end);
    int year2=cal2.get(Calendar.YEAR);
    int month2 =cal2.get(Calendar.MONTH);
    int day2 =cal2.get(Calendar.DAY_OF_MONTH);
    int num= 0;
    if(year2>=year){
      num=(year2-year)*12;
      if(month2>month){
        num+=month2-month;
        if(day2<day){
          --num;
        }
      }else{
        num= num- (month2-month);
        if(day2<day){
          --num;
        }
      }
    }
    return num;
  }
}
