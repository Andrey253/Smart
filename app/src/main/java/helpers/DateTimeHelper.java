package helpers;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import common.SystemConstants;

/**
 * Класс содержит общие вспомогательные методы по работе с датами
 * @author DushinovSV
 * Created by house on 01.02.2018.
 */
public class DateTimeHelper {

    /**
     * Метод представляет дату в виде строки нужного формата
     * @param format Формат
     * @param date Дата для представления
     * @return
     */
    public static String toString(String format, Calendar date) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date(date.getTimeInMillis()));
    }

    /**
     * Метод представляет дату в виде строки с временем формата
     * @param date Дата для представления
     * @return
     */
    public static String toStringHourMinuteFormat(Calendar date) {
        return toString(SystemConstants.HOURS_MINUTES_TIME_FORMAT, date);
    }

    /**
     * Метод представляет дату в виде строки с временем формата с деталями
     * Например, 12 час 30 мин
     * @param date Дата для представления
     * @return
     */
    public static String toStringHourMinuteDetailsFormat(Calendar date) {
        DecimalFormat formatter = new DecimalFormat("00");
        StringBuilder result = new StringBuilder();
        result.append(formatter.format(date.get(Calendar.HOUR_OF_DAY))).append(" час ");
        result.append(formatter.format(date.get(Calendar.MINUTE))).append(" мин");
        return result.toString();
    }

    /**
     * Метод создает календарь
     * @param milliseconds Количество миллисекунд
     * @return
     */
    public static Calendar createCalendar(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return calendar;
    }

    /**
     * Метод возвращает текущее время
     * @return
     */
    public static Calendar now() {
        return Calendar.getInstance();
    }
}