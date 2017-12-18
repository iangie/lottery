package com.ht.lottery.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期格式化工具类
 */
public class DateUtils {
    public static String addSubtractDay(DateFormat format, int n) {
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.DATE, n);//增加\减少天数
        return format.format(cd.getTime());
    }

    /**
     * 获取前后日期 i为正数 向后推迟i天，负数时向前提前i天
     * add by qianhao
     *
     * @param date
     * @param i
     * @return
     * @throws ParseException
     */
    public static String getDate(String date, int i) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(date));
        calendar.add(Calendar.DATE, i);
        return sdf.format(calendar.getTime());
    }
    
    public static String getDate(String date, int i, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sdf.parse(date));
        calendar.add(Calendar.DATE, i);
        return sdf.format(calendar.getTime());
    }

    /**
     * yyyyMMdd
     *
     * @return
     */
    //获得昨天日期
    public static String getYesterday() {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = DateUtils.addSubtractDay(format, -1);
        return date;
    }


    /**
     * yyyyMMdd
     *
     * @return
     */
    //获得前天日期
    public static String getDayBeforeYesterday() {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = DateUtils.addSubtractDay(format, -2);
        return date;
    }

    //获得当前日期
    public static String getToday() {
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String date = DateUtils.addSubtractDay(format, 0);
        return date;
    }
    
    public static String getToday(String f) {
        DateFormat format = new SimpleDateFormat(f);
        String date = DateUtils.addSubtractDay(format, 0);
        return date;
    }
    
    public static Date getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d = null;
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return d;
    }


    public static void main(String[] args) {
		System.out.println(DateUtils.getToday());
		System.out.println(DateUtils.getYesterday());
		System.out.println(DateUtils.getDayBeforeYesterday());
		
		Date d = DateUtils.getDate("2017-01-01 13:00:00");
		System.out.println(111);
		
	}
}
