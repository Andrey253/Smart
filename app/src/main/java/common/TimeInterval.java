package common;

import java.text.DecimalFormat;

/**
 * Класс для хранения данных по временному интервалу
 * @author DushinovSV
 * Created by house on 13.02.2018.
 */
public class TimeInterval {

    /**
     * Отрицательный временной интервал
     */
    private boolean isNegative;

    /**
     * Количество часов
     */
    private int hours;

    /**
     * Количество минут
     */
    private int minutes;

    /**
     * Количество секунд
     */
    private int seconds;


    /**
     * Конструктор по умолчанию
     */
    public TimeInterval() {
        isNegative = false;
        hours = 0;
        minutes = 0;
        seconds = 0;
    }

    /**
     * Рабочий конструктор
     * @param isNegative Отрицательный временной интервал
     * @param hours Количество часов
     * @param minutes Количество минут
     * @param seconds Количество секунд
     */
    public TimeInterval(boolean isNegative, int hours, int minutes, int seconds) {
        this.isNegative = isNegative;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }


    /**
     * Метод создает пустой временной интервал
     * @return
     */
    public static TimeInterval createTimeIntervalEmpty() {
        return new TimeInterval();
    }

    /**
     * Метод отвечает на вопрос является ли временной интервал пустым
     * @param timeInterval Временной интервал
     * @return
     */
    public static boolean isEmpty(TimeInterval timeInterval) {
        return timeInterval.getHours() == 0 &&
                timeInterval.getMinutes() == 0 &&
                timeInterval.getSeconds() == 0;
    }

    /**
     * Переопределение для отображения в интерфейсе
     */
    @Override
    public String toString() {
        DecimalFormat myFormatter = new DecimalFormat("00");
        StringBuilder result = new StringBuilder();
        if(isNegative) {
            result.append("-");
        }
        result.append(myFormatter.format(hours)).append(":");
        result.append(myFormatter.format(minutes)).append(":");
        result.append(myFormatter.format(seconds));
        return result.toString();
    }


    public void setIsNegative(boolean isNegative) {
        this.isNegative = isNegative;
    }
    public boolean getIsNegative() {
        return this.isNegative;
    }


    public void setHours(int hours) {
        this.hours = hours;
    }
    public int getHours() {
        return this.hours;
    }


    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    public int getMinutes() {
        return this.minutes;
    }


    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
    public int getSeconds() {
        return this.seconds;
    }
}