package com.skynet.rimp.common.utils.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class DateTimeSeqUtil {
	/**
	 * 
	 * @param start
	 * @param end
	 * @param num
	 * @return
	 */
	public static List<RimpDate> generete(Date start, Date end, int num) {
		if (num <= 0) {
			return null;
		}

		long diff = (end.getTime() - start.getTime()) ;//相差的毫秒数
		if (diff <= 0) {
			return null;
		}
		int interval = (int) (diff / num);
		Calendar calendar = Calendar.getInstance();
		Calendar calendarFormat = Calendar.getInstance();
		calendar.setTime(start);
		List<RimpDate> list = new ArrayList<RimpDate>();
		int i = 0;
		for (; i < num; i++) {
			calendarFormat.setTime(calendar.getTime());
			calendarFormat.set(Calendar.SECOND, 0);
			list.add(new RimpDate(i+1, calendarFormat.getTime()));
			calendar.add(Calendar.MILLISECOND, interval);
		}
		return list;
	}
	/**
	 * 
	 * @param start
	 * @param end
	 * @param timePattern
	 * @param num
	 * @return
	 * @throws ParseException
	 */
	public static List<RimpDate> generete(String start, String end, String timePattern,int num) throws ParseException {
		if(StringUtils.isEmpty(timePattern)){
			timePattern = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(timePattern);
		return generete(sdf.parse(start),sdf.parse(end),num);
	}

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<RimpDate> list = DateTimeSeqUtil.generete(sdf.parse("2015-8-1 10:00:00"), sdf.parse("2015-8-1 10:15:00"), 4);
		for (RimpDate rimpDate : list) {
			System.out.println(rimpDate.getIndex()+"|"+sdf.format(rimpDate.getDate()));
		}
	}
}
