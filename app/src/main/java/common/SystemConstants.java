package common;

/**
 * Класс содержит основные константы приложения
 * @author DushinovSV
 * Created by house on 12.02.2018.
 */
public class SystemConstants {

    /**
     * Режим работы системы
     */
    public static final boolean IS_DEBUG_MODE = false;

    /**
     * Пустая строка
     */
    public static final String EMPTY_STRING = "";

    /**
     * Строка с пробелом
     */
    public static final String SPACE_STRING = " ";

    /**
     * Строка с точкой
     */
    public static final String POINT_STRING = ".";

    /**
     * Название кодировки UTF-8
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * Название алгоритм SHA-1
     */
    public static final String SHA_1 = "SHA-1";

    /**
     * Хост для отправки запросов для разработки
     */
    public final static String TEST_HOST_NAME = "http://192.168.0.102:8080";

    /**
     * Хост для отправки запросов для прод базы
     */
    public final static String PROD_HOST_NAME = "https://www.smartcds.ru";

    /**
     * Тег для сообщений в лог
     */
    public static final String LOG_TAG = "SmartDispatcher";

    /**
     * Стандартный формат для отображения времени
     */
    public static final String STANDART_TIME_FORMAT = "HH:mm:ss";

    /**
     * Стандартный формат для отображения даты
     */
    public static final String STANDART_DATE_FORMAT = "dd.MM.yyyy";

    /**
     * Формат для отображения времени в виде часов и минут
     */
    public static final String HOURS_MINUTES_TIME_FORMAT = "HH:mm";

    /**
     * Формат для отображения времени в виде часов и минут в деталях
     */
    public static final String HOURS_MINUTES_DETAILS_TIME_FORMAT = "HH час mm мин";

    /**
     * Формат для отображения полной даты и времени
     */
    public static final String FULL_DATETIME_FORMAT = "dd.MM.yyyy HH:mm:ss.S";

    /**
     * Формат для отображения даты и времени без учета секунд
     */
    public static final String DATETIME_WITHOUT_SECONDS_FORMAT = "dd.MM.yyyy HH:mm";

    /**
     * Количество секунд для обновления данных
     */
    public static final int UPDATE_DATA_SECONDS = 15;

    /**
     * Местоположение автобуса на конечной остановке
     */
    public static final int BUS_END_STATION_LOCATION = 100;

    /**
     * Местоположение автобуса на маршруте
     */
    public static final int BUS_ROUTE_LOCATION = 200;

    /**
     * Местоположение автобуса не на маршруте
     */
    public static final int BUS_NOT_FOUND_ROUTE_LOCATION = 300;


    /**
     * Название параметра "Результат операции"
     */
    public static String RESULT_OPERATION_PARAMETER_NAME = "result_operation";

    /**
     * Название параметра "Токен"
     */
    public static String USER_TOKEN_PARAMETER_NAME = "token";

    /**
     * Название параметра "Логин пользователя"
     */
    public static String USER_LOGIN_PARAMETER_NAME = "login";

    /**
     * Название параметра "Фамилия пользователя"
     */
    public static String USER_SURNAME_PARAMETER_NAME = "surname";

    /**
     * Название параметра "Имя пользователя"
     */
    public static String USER_NAME_PARAMETER_NAME = "name";

    /**
     * Название параметра "Отчество пользователя"
     */
    public static String USER_PATRONYMIC_PARAMETER_NAME = "patronymic";

    /**
     * Название параметра "Роли пользователя"
     */
    public static String USER_ROLES_PARAMETER_NAME = "roles";

    /**
     * Название параметра "Список автобусов"
     */
    public static String DRIVER_BUSES_PARAMETER_NAME = "buses";

    /**
     * Название параметра "Номер маршрута"
     */
    public static String ROUTE_NUMBER_PARAMETER_NAME = "routeNumber";

    /**
     * Название параметра "Текущее время"
     */
    public static String CURRENT_REAL_TIME_PARAMETER_NAME = "currentRealTime";

    /**
     * Название параметра "Плановое время выезда с конечной остановки"
     */
    public static String ASSIGN_TIME_PARAMETER_NAME = "assignTime";

    /**
     * Название параметра "Порядковый номер автобуса в очереди"
     */
    public static String NUMBER_QUEUE_PARAMETER_NAME = "numberQueue";

    /**
     * Название параметра "Очередь автобусов"
     */
    public static String QUEUE_BUSES_PARAMETER_NAME = "queueBuses";

    /**
     * Название параметра "Временной интервал до выезда автобуса с конечной остановки"
     */
    public static String DEPARTURE_TIME_INTERVAL_PARAMETER_NAME = "departureTimeInterval";

    /**
     * Название параметра "Гос.номер впереди идущего автобусв"
     */
    public static String BEFORE_BUS_NUMBER_PARAMETER_NAME = "beforeBusNumber";

    /**
     * Название параметра "Время отставания от впереди идущего"
     */
    public static String BEFORE_BUS_TIME_INTERVAL_PARAMETER_NAME = "beforeBusTimeInterval";

    /**
     * Название параметра "Гос.номер сзади идущего автобусв"
     */
    public static String AFTER_BUS_NUMBER_PARAMETER_NAME = "afterBusNumber";

    /**
     * Название параметра "Время отставания от сзади идущего"
     */
    public static String AFTER_BUS_TIME_INTERVAL_PARAMETER_NAME = "afterBusTimeInterval";

    /**
     * Название параметра "Название следующей оставновки"
     */
    public static String BEFORE_STATION_NAME_PARAMETER_NAME = "beforeStationName";

    /**
     * Название параметра "Плановое время прибытия на следующую остановку"
     */
    public static String BEFORE_STATION_ARRIVAL_TIME_PARAMETER_NAME = "beforeStationArrivalTime";

    /**
     * Название параметра "Идентификатор автобуса"
     */
    public static String BUS_STATE_PARAMETER_ID = "busId";

    /**
     * Название параметра "Гос номер автобуса"
     */
    public static String BUS_STATE_PARAMETER_NAME = "busState";

    /**
     * Название параметра "Гос номер автобуса" для конечной добавлено Бойко А.А. 23,06,2019
     */
    public static String BUS_STATE_PARAMETER_NAME_ON_END = "busStateNumber";

    /**
     * Название параметра "Текущее положения автобуса на маршруте (код)"
     */
    public static String BUS_STATE_CODE_PARAMETER_NAME = "busStateCode";

    /**
     * Название параметра "Местоположение автобуса на маршруте"
     */
    public static String BUS_LOCATION_PARAMETER_NAME = "busLocationId";


    /**
     * Фраза "вы спешите"
     */
    public static String PHRASE_YOU_HURRY = "Вы спешите";
    /**
     * Фраза "вы в норме"
     */
    public static String PHRASE_YOU_NORM = "Вы в норме";

    /**
     * Фраза "вы опаздываете"
     */
    public static String PHRASE_YOU_LATENESS = "Вы опаздываете";

    /**
     * Фраза "Конечная ост."
     */
    public static String PHRASE_END_STATION_LOCATION_TITLE = "Конечная остановка";

    /**
     * Фраза "На маршруте"
     */
    public static String PHRASE_ROUTE_LOCATION_TITLE = "На маршруте";

    /**
     * Фраза "Информация"
     */
    public static String PHRASE_INFORMATION_TITLE = "Информация";

    /**
     * Название свойства для сохранения данных для приложения
     */
    public static String APPLICATION_SHARED_PREFERENCES = "SmartDispatcherPreferences";
}