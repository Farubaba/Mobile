package com.silence.rootfeature.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateUtil {
	public static final long ONE_DAY = 1000L * 60L * 60L * 24L;
	/**
	 * 视频列表 视频时长转化
	 */
	public static String parseDuration(int duration) {
		long temp = duration * 1000;
		return parseDuration(temp);
	}

	public static String parseDuration(long duration) {
		SimpleDateFormat formater = new SimpleDateFormat("mm:ss");
		return formater.format(duration);
	}

	public static String parseDuration(long duration, String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return formater.format(duration);
	}

	public static String getYear(String dateString, String pattern) {
		if (dateString != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date date = null;
			try {
				date = sdf.parse(dateString);
				sdf.applyPattern("yyyy");
				return sdf.format(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		return "";
	}

	/**
	 * 判断两个日期是否是同一天
	 * @param thisDay
	 * @param thatDay
	 * @return
	 */
	public static boolean isTheSameDay(Long thisDay, Long thatDay){
		if(thisDay == null || thatDay == null){
			return false;
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
			String thisDayString = sdf.format(new Date(thisDay));
			String thatDayString = sdf.format(new Date(thatDay));
			if(thisDayString.equals(thatDayString)){
				return true;
			}else{
				return false;
			}
		}
	}

	/**
	 * 获取两个日期之间相差多少天，根据时钟时间来计算。23点和凌晨2点之间相差1天，而不是0天。
	 * @param first
	 * @param second
	 * @return
	 */
	public static int getDaysApart(long first, long second){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(first);
		int day1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTimeInMillis(second);
		int day2 = calendar.get(Calendar.DAY_OF_YEAR);
		return Math.abs(day2 - day1);
	}

	/**
	 * 获取两个日期之间相差多少天，以24小时为一天，不足24小时舍弃。
	 * @param first
	 * @param second
	 * @return
	 */
	public static int getFullDaysApart(long first, long second){
		long distance =  Math.abs(first - second);
		int rest = (int)(distance % ONE_DAY);
		int days = (int)(distance / ONE_DAY);
		return days;
	}

	/**
	 * long 格式数据返回 2016-03-23格式
	 * @param time
	 * @return
	 */
	public static String getFormatDateFromLong(long time){
		Calendar recordCal = Calendar.getInstance();
		recordCal.setTimeInMillis(time);

		int year = recordCal.get(Calendar.YEAR);
		int month = recordCal.get(Calendar.MONTH) + 1;
		int day = recordCal.get(Calendar.DAY_OF_MONTH);

		String dateKey = year + "-" + month + "-" + day;
		return dateKey;
	}

	/**
	 * 判断是否是周末
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(long date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date);

		int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if(6 == weekday || 0 == weekday){
			return true;
		}
		return false;
	}


}
