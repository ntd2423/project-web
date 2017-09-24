package com.ntd.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Instant;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日期时间工具类2
 *
 */
public class DateTimeUtil {
	
	public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static final DateTimeFormatter HH_MM_SS = DateTimeFormat.forPattern("HH:mm:ss");
	
	
	/**
	 * 字符串转换为日期
	 * @return yyyy-MM-dd 00:00:00
	 */
	public static Date parseToDate(String date) {
		return YYYY_MM_DD.parseDateTime(date).toDate();
	}
	/**
	 * 字符串转换为日期
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date parseToDateTime(String date) {
		return YYYY_MM_DD_HH_MM_SS.parseDateTime(date).toDate();
	}
	/**
	 * 字符串转换为日期
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		return DateTimeFormat.forPattern(pattern).parseDateTime(date).toDate();
	}
	
	/**
	 * 当前时间 yyyy-mm-dd hh:mm:ss
	 * @return
	 */
	public static String getDateTime() {
		return YYYY_MM_DD_HH_MM_SS.print(DateTime.now());
	}
	/**
	 * yyyy-mm-dd hh:mm:ss
	 * @return
	 */
	public static String getDateTime(Date date) {
		return YYYY_MM_DD_HH_MM_SS.print(new DateTime(date.getTime()));
	}
	
	/**
	 * 当前时间 yyyy-mm-dd
	 * @return
	 */
	public static String getDate() {
		return YYYY_MM_DD.print(DateTime.now());
	}
	/**
	 * yyyy-mm-dd
	 * @return
	 */
	public static String getDate(Date date) {
		return YYYY_MM_DD.print(new DateTime(date.getTime()));
	}
	
	/**
	 * 当前时间hh:mm:ss
	 * @return
	 */
	public static String getTime() {
		return HH_MM_SS.print(DateTime.now());
	}
	/**
	 * hh:mm:ss
	 * @return
	 */
	public static String getTime(Date date) {
		return HH_MM_SS.print(new DateTime(date.getTime()));
	}
	/**
	 * 格式化当前时间
	 * @return
	 */
	public static String format(String pattern) {
		return DateTimeFormat.forPattern(pattern).print(DateTime.now());
	}
	/**
	 * 格式化指定时间
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return DateTimeFormat.forPattern(pattern).print(new DateTime(date.getTime()));
	}
	
	/**
	 * 昨天
	 * @return
	 */
	public static Date getYestoday() {
		return DateTime.now().minusDays(1).toDate();
	}
	/**
	 * 明天
	 * @return
	 */
	public static Date getTomorrow() {
		return DateTime.now().plusDays(1).toDate();
	}
	
	/**
	 * 增加秒数
	 * @return
	 */
	public static Date plusSeconds(Date date, int seconds) {
		return new DateTime(date.getTime()).plusSeconds(seconds).toDate();
	}
	/**
	 * 增加分钟数
	 * @return
	 */
	public static Date plusMinutes(Date date, int minutes) {
		return new DateTime(date.getTime()).plusMinutes(minutes).toDate();
	}
	/**
	 * 增加小时数
	 * @return
	 */
	public static Date plusHours(Date date, int hours) {
		return new DateTime(date.getTime()).plusHours(hours).toDate();
	}
	/**
	 * 增加天数
	 * @return
	 */
	public static Date plusDays(Date date, int days) {
		return new DateTime(date.getTime()).plusDays(days).toDate();
	}
	/**
	 * 增加月数
	 * @return
	 */
	public static Date plusMonths(Date date, int months) {
		return new DateTime(date.getTime()).plusMonths(months).toDate();
	}
	/**
	 * 增加星期数
	 * @return
	 */
	public static Date plusWeeks(Date date, int weeks) {
		return new DateTime(date.getTime()).plusWeeks(weeks).toDate();
	}
	
	/**
	 * 指定时间是否在days天之内
	 * @return
	 */
	public static boolean isWithinDays(Date date, int days) {
		return days > 0 && DateTime.now().isBefore(new DateTime(date.getTime()).plusDays(days));
	}
	
	/**
	 * 两个日期相隔秒数
	 * @return
	 */
	public static int secondsBetween(Date start, Date end) {
		return Math.abs(Seconds.secondsBetween(new Instant(start.getTime()), new Instant(end.getTime())).getSeconds());
	}
	/**
	 * 指定日期与当前时间相隔秒数
	 * @return
	 */
	public static int secondsBetween(Date date) {
		return Math.abs(Seconds.secondsBetween(new Instant(date.getTime()), new Instant()).getSeconds());
	}
	/**
	 * 两个日期相隔分钟数
	 * @return
	 */
	public static int minutesBetween(Date start, Date end) {
		return Math.abs(Minutes.minutesBetween(new Instant(start.getTime()), new Instant(end.getTime())).getMinutes());
	}
	/**
	 * 指定日期与当前时间相隔分钟数
	 * @return
	 */
	public static int minutesBetween(Date date) {
		return Math.abs(Minutes.minutesBetween(new Instant(), new Instant(date.getTime())).getMinutes());
	}
	
	/**
	 * 两个日期相隔小时数
	 * @return
	 */
	public static int hoursBetween(Date start, Date end) {
		return Math.abs(Hours.hoursBetween(new Instant(start.getTime()), new Instant(end.getTime())).getHours());
	}
	/**
	 * 指定日期与当前时间相隔小时数
	 * @return
	 */
	public static int hoursBetween(Date date) {
		return Math.abs(Hours.hoursBetween(new Instant(date.getTime()), new Instant()).getHours());
	}
	
	/**
	 * 两个日期相隔天数
	 * @return 忽略时分秒后相差天数绝对值
	 */
	public static int daysBetween(Date start, Date end) {
		return Math.abs(Days.daysBetween(new DateTime(start.getTime()).withTimeAtStartOfDay(), new DateTime(end.getTime()).withTimeAtStartOfDay()).getDays());
	}
	
	/**
	 * 指定日期与当前时间相隔天数
	 * @return 忽略时分秒后相差天数绝对值
	 */
	public static int daysBetween(Date date) {
		return Math.abs(Days.daysBetween(new DateTime(date.getTime()).withTimeAtStartOfDay(), DateTime.now().withTimeAtStartOfDay()).getDays());
	}
	
	/**
	 * 两个日期相隔月数
	 * @return
	 */
	public static int monthsBetween(Date start, Date end) {
		return Months.monthsBetween(new Instant(start.getTime()), new Instant(end.getTime())).getMonths();
	}
	/**
	 * 指定日期与当前时间相隔月数
	 * @return
	 */
	public static int monthsBetween(Date date) {
		return Months.monthsBetween(new Instant(date.getTime()), new Instant()).getMonths();
	}
	
	/**
	 * 截断日期到分钟
	 * @return
	 */
	public static Date truncateToMinute(Date date) {
		DateTime dateTime = new DateTime(date.getTime());
		return dateTime.withTime(dateTime.getHourOfDay(), dateTime.getMinuteOfHour(), 0, 0).toDate();
	}
	/**
	 * 截断日期到小时
	 * @return
	 */
	public static Date truncateToHour(Date date) {
		DateTime dateTime = new DateTime(date.getTime());
		return dateTime.withTime(dateTime.getHourOfDay(), 0, 0, 0).toDate();
	}
	/**
	 * 截断日期到天
	 * @return
	 */
	public static Date truncateToDay(Date date) {
		DateTime dateTime = new DateTime(date.getTime());
		return dateTime.withTimeAtStartOfDay().toDate();
	}
	/**
	 * 截断日期到月
	 * @return
	 */
	public static Date truncateToMonth(Date date) {
		return new DateTime(date.getTime()).dayOfMonth().withMinimumValue().withTimeAtStartOfDay().toDate();
	}

	/**
	 * 本周一
	 * @return
	 */
	public static String getMonday() {
		return YYYY_MM_DD_HH_MM_SS.print(getDayOfWeek(1));
	}
	/**
	 * 本周二
	 * @return
	 */
	public static String getTuesday() {
		return YYYY_MM_DD_HH_MM_SS.print(getDayOfWeek(2));
	}
	/**
	 * 本周三
	 * @return
	 */
	public static String getWednesday() {
		return YYYY_MM_DD_HH_MM_SS.print(getDayOfWeek(3));
	}
	/**
	 * 本周四
	 * @return
	 */
	public static String getThursday() {
		return YYYY_MM_DD_HH_MM_SS.print(getDayOfWeek(4));
	}
	/**
	 * 本周五
	 * @return
	 */
	public static String getFriday() {
		return YYYY_MM_DD_HH_MM_SS.print(getDayOfWeek(5));
	}
	/**
	 * 本周六
	 * @return
	 */
	public static String getSaturday() {
		return YYYY_MM_DD_HH_MM_SS.print(getDayOfWeek(6));
	}
	/**
	 * 本周日
	 * @return
	 */
	public static String getSunday() {
		return YYYY_MM_DD_HH_MM_SS.print(getDayOfWeek(7));
	}
	/**
	 * 本周一
	 * @return
	 */
	public static Date getMondayDate() {
		return getDayOfWeek(1).toDate();
	}
	/**
	 * 本周二
	 * @return
	 */
	public static Date getTuesdayDate() {
		return getDayOfWeek(2).toDate();
	}
	/**
	 * 本周三
	 * @return
	 */
	public static Date getWednesdayDate() {
		return getDayOfWeek(3).toDate();
	}
	/**
	 * 本周四
	 * @return
	 */
	public static Date getThursdayDate() {
		return getDayOfWeek(4).toDate();
	}
	/**
	 * 本周五
	 * @return
	 */
	public static Date getFridayDate() {
		return getDayOfWeek(5).toDate();
	}
	/**
	 * 本周六
	 * @return
	 */
	public static Date getSaturdayDate() {
		return getDayOfWeek(6).toDate();
	}
	/**
	 * 本周日
	 * @return
	 */
	public static Date getSundayDate() {
		return getDayOfWeek(7).toDate();
	}
	
	/**
	 * 获取本周第n天
	 * @return
	 */
	public static DateTime getDayOfWeek(int n) {
		n = n > 0 ? n : (n == 0 ? 7 : 1);
		return DateTime.now().weekOfWeekyear().roundFloorCopy().plusDays((n - 1) % 7);
	}
	
	/**
	 * 获取当天日期区间
	 * such as 2015-08-20 00:00:00,000 to 2015-08-21 00:00:00,000 
	 * @return
	 */
	public static DateTimeRange getTodayRange() {
		DateTime now = DateTime.now();
		return new DateTimeRange(now.withTimeAtStartOfDay(), now.plusDays(1).withTimeAtStartOfDay());
	}

	/**
	 * 获取昨天区间
	 * such as 2015-08-20 00:00:00,000 to 2015-08-21 00:00:00,000 
	 * @return
	 */
	public static DateTimeRange getYestodayRange() {
		return new DateTimeRange(DateTime.now().minusDays(1).withTimeAtStartOfDay(), DateTime.now().withTimeAtStartOfDay());
	}
	
	public static DateTimeRange getCurrentWeekRange() {
		return new DateTimeRange(DateTime.now().weekOfWeekyear().roundFloorCopy(), DateTime.now().weekOfWeekyear().roundCeilingCopy());
	}
	
	/**
	 * 获取当前小时区间
	 * such as 2015-08-20 11:00:00,000 to 2015-08-20 12:00:00,000 
	 * @return
	 */
	public static DateTimeRange getCurrentHourRange() {
		DateTime now = DateTime.now();
		DateTime start = now.withTime(now.getHourOfDay(), 0, 0, 0);
		DateTime end = start.plusHours(1);
		return new DateTimeRange(start, end);
	}
	
	/**
	 * 设置当前日期的时分秒
	 * @return
	 */
	public static Date setTime(int hour, int minute, int second) {
		return DateTime.now().withTime(hour, minute, second, 0).toDate();
	}
	
	/**
	 * 设置日期的时分秒
	 * @return
	 */
	public static Date setTime(Date date, int hour, int minute, int second) {
		return new DateTime(date.getTime()).withTime(hour, minute, second, 0).toDate();
	}
	
	/**
	 * 获取上一个小时的区间
	 * @return
	 */
	public static DateTimeRange getPreviousHourRange() {
		DateTime previous = DateTime.now().minusHours(1);
		DateTime start = previous.withTime(previous.getHourOfDay(), 0, 0, 0);
		DateTime end = start.plusHours(1);
		return new DateTimeRange(start, end);
	}
	
	/**
	 * 获取当月日期区间
	 * @return 2015-08-01 00:00:00 to 2015-09-01 00:00:00
	 */
	public static DateTimeRange getCurrentMonthRange() {
		DateTime now = DateTime.now();
		return new DateTimeRange(now.dayOfMonth().withMinimumValue().withTimeAtStartOfDay(), 
				now.dayOfMonth().withMaximumValue().plusDays(1).withTimeAtStartOfDay());
	}
	
	/**
	 * 获取当月第一天
	 * @return 2015-09-01 00:00:00
	 */
	public static Date getStartOfMonth() {
		return DateTime.now().dayOfMonth().withMinimumValue().withTimeAtStartOfDay().toDate();
	}
	
	/**
	 * 获取当月最后一天
	 * @return 2015-09-30 00:00:00
	 */
	public static Date getEndOfMonth() {
		return DateTime.now().dayOfMonth().withMaximumValue().withTimeAtStartOfDay().toDate();
	}
	/**
	 * 获取当天开始时间
	 * @return yyyy-mm-dd 00:00:00
	 */
	public static Date getStartOfToday() {
		return DateTime.now().withTimeAtStartOfDay().toDate();
	}
	/**
	 * 获取当天结束时间
	 * @return yyyy-mm-dd 00:00:00
	 */
	public static Date getEndOfToday() {
		return DateTime.now().plusDays(1).withTimeAtStartOfDay().toDate();
	}

	/**
	 * 获取今天到自然日结束的秒数
	 * @return
	 */
	public static int getSecondsUtilEndOfToday(){
		Date now = new Date();
		Date endOfToay = getEndOfToday();
		return (int) ((endOfToay.getTime() - now.getTime()) / 1000);
	}
	
	/**
	 * 和当前时间是否在同一个月中
	 * @return
	 */
	public static boolean isWithinSameMonth(Date date) {
		return isWithinSameMonth(date, null);
	}
	/**
	 * 两个时间是否在同一个月中
	 * @return
	 */
	public static boolean isWithinSameMonth(Date date1, Date date2) {
		DateTime dateTime1 = new DateTime(date1.getTime());
		DateTime dateTime2 = date2 == null ? DateTime.now() : new DateTime(date2.getTime());
		return dateTime1.getYear() == dateTime2.getYear() && dateTime1.getMonthOfYear() == dateTime2.getMonthOfYear();
	}
	/**
	 * 和当前时间是否在同一天中
	 * @return
	 */
	public static boolean isWithinSameDay(Date date) {
		return isWithinSameDay(date, null);
	}
	/**
	 * 两个时间是否在同一天中
	 * @return
	 */
	public static boolean isWithinSameDay(Date date1, Date date2) {
		DateTime d1 = new DateTime(date1.getTime());
		DateTime d2 = date2 == null ? DateTime.now() : new DateTime(date2.getTime());
		return d1.getYear() == d2.getYear() && d1.getMonthOfYear() == d2.getMonthOfYear() && d1.getDayOfMonth() == d2.getDayOfMonth();
	}
	
	public static class DateTimeRange {
		public final DateTime start;
		public final DateTime end;
		
		public DateTimeRange (DateTime start, DateTime end) {
			this.start = start;
			this.end = end;
		}
		
		public DateTime getStart() {
			return start;
		}
		
		public DateTime getEnd() {
			return end;
		}
		
		/**
		 * yyyy-mm-dd HH:mm:ss
		 * @return
		 */
		public Tuple<String, String> toDateTimeString() {
			return parseToString(YYYY_MM_DD_HH_MM_SS);
		}
		/**
		 * yyyy-mm-dd
		 * @return
		 */
		public Tuple<String, String> toDateString() {
			return parseToString(YYYY_MM_DD);
		}
		/**
		 * HH:mm:ss
		 * @return
		 */
		public Tuple<String, String> toTimeString() {
			return parseToString(HH_MM_SS);
		}
		
		/**
		 * Java date
		 * @return
		 */
		public Tuple<Date, Date> toDate() {
			return new Tuple<Date, Date>(this.start.toDate(), this.end.toDate());
		}
		
		/**
		 * 自定义格式
		 * @return
		 */
		public Tuple<String, String> parseToString(DateTimeFormatter formatter) {
			return new Tuple<String, String>(formatter.print(this.start), formatter.print(this.end));
		}
		
		/**
		 * 自定义格式
		 * @return
		 */
		public Tuple<String, String> parseToString(String pattern) {
			DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
			return new Tuple<String, String>(formatter.print(this.start), formatter.print(this.end));
		}

		@Override
		public String toString() {
			return toDateTimeString().toString();
		}
	}

	public static DateTimeRange getLastWeekRange() {

		DateTime yesterday = DateTime.now().dayOfYear().addToCopy(-1);
		return new DateTimeRange(yesterday.weekOfWeekyear().roundFloorCopy(), yesterday.weekOfWeekyear().roundCeilingCopy());
	}

	/**
	 * 计算今天还剩下的秒数
	 * @return
	 */
	public static int getTodayLeftSeconds() {
		Calendar now = Calendar.getInstance();
		Calendar todayEnding = getDayEndingCalendar(Calendar.getInstance());
		long todayLeftMillis = todayEnding.getTimeInMillis() - now.getTimeInMillis();
		return (int) todayLeftMillis / 1000;
	}

	public static Calendar getDayEndingCalendar(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal;
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(getStartOfToday());
		System.out.println(getEndOfToday());
	}

}
