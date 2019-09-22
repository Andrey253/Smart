package model;

import java.io.Serializable;

import common.SystemConstants;

/**
 * Класс содержит информацию об автобусе из очереди на конечной остановке для android приложения
 * @author DushinovSV
 */
public class EndStationBusQueueItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Идентификатор автобуса
     */
    private String busId;

    /**
     * Гос.номер автобуса
     */
    private String busStateNumber;

    /**
     * Предварительное время выезда автобуса
     */
    private String assignTime;

    /**
     * Время до выезда автобуса на маршрут
     */
    private String departureTimeInterval;

    /**
     * Номер автобуса в очереди
     */
    private int numberQueue;


    public EndStationBusQueueItem() {
        busId = SystemConstants.EMPTY_STRING;
        busStateNumber = SystemConstants.EMPTY_STRING;
        assignTime = SystemConstants.EMPTY_STRING;
        departureTimeInterval = SystemConstants.EMPTY_STRING;
        numberQueue = 0;
    }
    public EndStationBusQueueItem(int numberqueue, String nbs) {
        busId = SystemConstants.EMPTY_STRING;
        busStateNumber = nbs;
        assignTime = "08:20:00";
        departureTimeInterval = "03:05";
        numberQueue = numberqueue;
    }


    public void setBusId(String busId) {
        this.busId = busId;
    }
    public String getBusId() {
        return busId;
    }

    public void setBusStateNumber(String busStateNumber) {
        this.busStateNumber = busStateNumber;
    }
    public String getBusStateNumber() {
        return busStateNumber;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }
    public String getAssignTime() {
        return assignTime;
    }

    public void setDepartureTimeInterval(String departureTimeInterval) { this.departureTimeInterval = departureTimeInterval; }
    public String getDepartureTimeInterval() {
        return departureTimeInterval;
    }

    public void setNumberQueue(int numberQueue) { this.numberQueue = numberQueue; }
    public int getNumberQueue() {
        return numberQueue;
    }
}