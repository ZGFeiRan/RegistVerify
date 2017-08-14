package com.feiran.zg.core.base.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangguangfei on 2016/12/19.
 */
public class DateUtil {
    // 获取两个时间之间的时间差(单位是秒)
    public static long getSecondInterval(Date begin,Date end){
        return Math.abs((end.getTime() - begin.getTime())/1000);
    }

    public static Date getBeginDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 设置时
        c.set(Calendar.HOUR_OF_DAY,0);
        // 设置分
        c.set(Calendar.MINUTE,0);
        // 设置秒
        c.set(Calendar.SECOND,0);
        return c.getTime();
    }

    public static Date getEndDate(Date date){
        date = getBeginDate(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 把日期的天增加1
        c.add(Calendar.DAY_OF_MONTH,1);
        // 把日期的秒增加-1,即减1
        c.add(Calendar.SECOND,-1);
        return c.getTime();
    }
}
