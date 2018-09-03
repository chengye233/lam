package utils;

import java.util.Calendar;
import java.util.Date;

/**
 * java.util.Date转为yyyy-mm-dd格式的java.sql.Date
 * 进行日期加减等操作
 */
public class DateUtils
{
    /**
     * java.util.Date转为yyyy-mm-dd格式的java.sql.Date
     */
    public static java.sql.Date getSqlDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        java.sql.Date sqlDate = new java.sql.Date(calendar.get(Calendar.YEAR)-1900,
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        return sqlDate;
    }

    /**
     * 增加或者减少天数
     * @param date
     * @param days
     * @return
     */
    public static java.sql.Date updateDate(Date date, int days)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        java.sql.Date sqlDate = new java.sql.Date(calendar.get(Calendar.YEAR)-1900,
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        return sqlDate;
    }
}
