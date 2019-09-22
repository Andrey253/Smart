package helpers;

import common.SystemConstants;

/**
 * Класс содержит вспомогательные методы для работы со строками
 * @author DushinovSV
 * Created by house on 12.02.2018.
 */
public class StringHelper {

    /**
     * Метод проверяет строку на пустоту
     * @param value Значение для проверки
     * @return
     */
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Метод устанавливает первую букву в строке заглавной
     * @param value Исходная строка
     * @return
     */
    public static String firstUpperCase(String value) {
        if(value == null || value.isEmpty()) {
            return SystemConstants.EMPTY_STRING;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    /**
     * Метод возвращает первую букву строки заглавной
     * @param value Исходная строка
     * @return
     */
    public static String getFirstUpperCaseSymbol(String value) {
        if(value == null || value.isEmpty()) {
            return SystemConstants.EMPTY_STRING;
        }
        return value.substring(0, 1).toUpperCase();
    }
}
