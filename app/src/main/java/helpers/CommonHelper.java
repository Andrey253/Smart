package helpers;

import android.widget.TextView;

import java.util.List;

import common.SystemConstants;

/**
 * Класс содержит общие вспомогательные методы
 * @author DushinovSV
 * Created by house on 09.12.2017.
 */
public class CommonHelper {

    /**
     * Метод отвечает на вопрос равен ли объект null
     * @param source Исходный объект
     * @return
     */
    public static boolean isNull(Object source) {
        return source == null;
    }

    /**
     * Метод отвечает на вопрос является ли строка пустой
     * @param source Исходный объект
     * @return
     */
    public static boolean isStringEmpty(String source) {
        return isNull(source) || source.equals(SystemConstants.EMPTY_STRING);
    }

    /**
     * Метод отвечает на вопрос является ли список пыстым
     * @param source Исходный список с данными
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isListEmpty(List source) {
        return isNull(source) || source.isEmpty();
    }

    /**
     * Метод возвращает хост для отправки запросов
     * @return
     */
    public static String getHostName() {
        return SystemConstants.IS_DEBUG_MODE ? SystemConstants.TEST_HOST_NAME : SystemConstants.PROD_HOST_NAME;
    }

    /**
     * Метод возвращает пустую строку если значение пустое
     * @param source Исходное значение
     * @return
     */
    public static String getValueOrEmptyString(String source) {
        return isStringEmpty(source) ? SystemConstants.EMPTY_STRING : source.trim();
    }

    /**
     * Метод возвращает пустую строку если значение пустое
     * @param textView Контрол для текста
     * @return
     */
    public static String getValueOrEmptyString(TextView textView) {
        return CommonHelper.isNull(textView.getText())
            ? SystemConstants.EMPTY_STRING
            : CommonHelper.getValueOrEmptyString(textView.getText().toString());
    }
}