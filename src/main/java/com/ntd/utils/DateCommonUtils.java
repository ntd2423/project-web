package com.ntd.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间工具类1
 */
public class DateCommonUtils {
	
	public final static int CALCULATE_OF_DAY = 0;

	public final static int CALCULATE_OF_HOUR = 1;

	public final static int CALCULATE_OF_MINUTE = 2;
	
	public final static int CALCULATE_OF_SECOND = 3;
	
	private static SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdfYm = new SimpleDateFormat("yyyyMM");
	private static SimpleDateFormat sdfmd = new SimpleDateFormat("MMdd");

	/**
	 * 获取今日开始时间(00:00:00)
	 */
	public static Date getToDayBeginTime(Date time){
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);
        //获取00:00:00
        cal.set(Calendar.HOUR_OF_DAY, 0); 
        cal.set(Calendar.MINUTE, 0); 
        cal.set(Calendar.SECOND, 0); 
        return cal.getTime();
	}

	/**
	 * 获取今日结束时间(23:59:59)
	 */
	public static Date getToDayEndTime(Date time){
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);
        //获取23:59:59
        cal.set(Calendar.HOUR_OF_DAY, 23); 
        cal.set(Calendar.MINUTE, 59); 
        cal.set(Calendar.SECOND, 59); 
        return cal.getTime();
	}
	
	/**
	 * 获取当月的日期(yyyyMM)
	 */
	public static String getMonth() {
		Calendar cal = Calendar.getInstance(Locale.CHINA);

		return sdfYm.format(cal.getTime());
	}

	/**
	 * 获取传入时间所对应的周几日期(yyyyMMdd)
	 */
	public static String getWeekDay(Date time, int value) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, value);

		return sdfYmd.format(cal.getTime());
	}

	/**
	 * 获取当前日期(yyyyMMdd)
	 */
	public static String getCurrentDay() {

		return getCurrentDay(new Date());
	}

	/**
	 * 获取当前日期(yyyyMMdd)
	 */
	public static String getCurrentDay(Date time) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);

		return sdfYmd.format(cal.getTime());
	}

	/**
	 * 获取当前的日期(MMdd)
	 */
	public static String getDay() {

		return getDay(new Date());
	}

	/**
	 * 获取当前的日期(MMdd)
	 */
	public static String getDay(Date time) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);

		return sdfmd.format(cal.getTime());
	}

	/**
	 * 获取本周一的日期(yyyyMMdd)
	 */
	public static String getMonday() {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		return sdfYmd.format(cal.getTime());
	}

	/**
	 * 获取本周一的开始时间(00:00:00)
	 */
	public static Date getMondayTime(){
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //获取00:00:00
        cal.set(Calendar.HOUR_OF_DAY, 0); 
        cal.set(Calendar.MINUTE, 0); 
        cal.set(Calendar.SECOND, 0); 
        return cal.getTime();
	}
	
	/**
	 * 获取本周日的结束时间(23:59:59)
	 */
	public static Date getSunDayTime() {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		//cal.add(Calendar.WEEK_OF_YEAR, 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		//获取23:59:59
		cal.set(Calendar.HOUR_OF_DAY, 23); 
		cal.set(Calendar.MINUTE, 59); 
		cal.set(Calendar.SECOND, 59); 
		return cal.getTime();
	}

	/**
	 * 获取某天的开始时间(00:00:00)
	 */
	public static Date getSomedayBeginTime(int dayOfWeek){
		return getSomedayTime(dayOfWeek, 0, 0, 0);
	}

	/**
	 * 获取某天的结束时间(23:59:59)
	 */
	public static Date getSomedayEndTime(int dayOfWeek) {
		return getSomedayTime(dayOfWeek, 23, 59, 59);
	}

	/**
	 * 获取某天的某个时间(xx:xx:xx)
	 */
	public static Date getSomedayTime(int dayOfWeek, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * 获取某天的开始时间(00:00:00)
	 */
	public static Date getSomedayBeginTime(Date date){
		return getSomedayTime(date, 0, 0, 0);
	}

	/**
	 * 获取某天的结束时间(23:59:59)
	 */
	public static Date getSomedayEndTime(Date date) {
		return getSomedayTime(date, 23, 59, 59);
	}

	/**
	 * 获取某天的某个时间(xx:xx:xx)
	 */
	public static Date getSomedayTime(Date date, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}
	
	/**
	 * 获取当前小时
	 */
	public static int getCurrentHour(Date time) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);
		
        return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前周几
	 */
	public static int getCurrentWeekDay(Date time) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);

		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 判断当前分钟数是否在minute之内
	 */
	public static boolean checkMinuteInNum(Date time, int minute) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);
		
        return cal.get(Calendar.MINUTE) <= minute;
	}
	
	/**
	 * 获取传入时间所对应的指定时/分/秒时间
	 */
	public static Date getValueDayTime(Date time, int hourValue, int minuteValue, int secondValue) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, hourValue); 
        cal.set(Calendar.MINUTE, minuteValue); 
        cal.set(Calendar.SECOND, secondValue); 
        return cal.getTime();
	}
	
	/**
	 * 获取距离传入时间的value小时开始时间(xx:00:00)
	 */
	public static Date getValueHourBeginTime(Date time, int value) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);
		cal.add(Calendar.HOUR_OF_DAY, value);
		cal.set(Calendar.MINUTE, 00); 
		cal.set(Calendar.SECOND, 00); 
        return cal.getTime();
	}
	
	/**
	 * 获取距离传入时间的value小时结束时间(xx:59:59)
	 */
	public static Date getValueHourEndTime(Date time, int value) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);
		cal.add(Calendar.HOUR_OF_DAY, value);
		cal.set(Calendar.MINUTE, 59); 
		cal.set(Calendar.SECOND, 59); 
        return cal.getTime();
	}
	
	/**
	 * 获取距离传入时间的value小时指定的分钟秒数时间(xx:xx:xx)
	 */
	public static Date getValueHourMinSecTime(Date time, int hourValue, int minuteValue, int secondValue) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(time);
		cal.add(Calendar.HOUR_OF_DAY, hourValue);
		cal.set(Calendar.MINUTE, minuteValue); 
		cal.set(Calendar.SECOND, secondValue); 
        return cal.getTime();
	}
	
	/**
	 * 计算两个时间相差的天/小时数/分钟数/秒数
	 */
	public static long calculateDate(Date beginDate, Date endDate, int type) {
		long between = (endDate.getTime() - beginDate.getTime());
		if(type == DateCommonUtils.CALCULATE_OF_DAY)
			return between / (60 * 60 * 1000 * 24);
		else if(type == DateCommonUtils.CALCULATE_OF_HOUR)
			return between / (60 * 60 * 1000);
		else if(type == DateCommonUtils.CALCULATE_OF_MINUTE)
			return between / (60 * 1000);
		else if(type == DateCommonUtils.CALCULATE_OF_SECOND)
			return between / (1000);
		else
			return 0;
	}
	
	/**
	 * 将当前的时间加上/减去几周，并返回
	 */
	public static Date addWeeks(Date date, int value) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, value);
		return cal.getTime();
	}
	
	/**
	 * 将当前的时间加上/减去几天，并返回
	 */
	public static Date addDays(Date date, int value) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, value);
		return cal.getTime();
	}

	/**
	 * 将当前的时间加上/减去几小时，并返回
	 */
	public static Date addHours(Date date, int value) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, value);
		return cal.getTime();
	}
	
	/**
	 * 将当前的时间加上/减去几分钟，并返回
	 */
	public static Date addMinutes(Date date, int value) {
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		cal.setTime(date);
		cal.add(Calendar.MINUTE, value);
		return cal.getTime();
	}
	
	public static void main(String[] args) throws Exception {
		//System.out.println(DateUtils.addDays(new Date(), 1));
		System.out.println(getCurrentWeekDay(new Date()));
		System.out.println(getCurrentHour(new Date()));
	}
	
}
