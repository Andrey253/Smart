package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BusState implements Serializable
{

    @SerializedName("busLocationId")
    @Expose
    private Integer busLocationId;
    @SerializedName("routeNumber")
    @Expose
    private String routeNumber;
    @SerializedName("currentRealTime")
    @Expose
    private String currentRealTime;
    @SerializedName("beforeBusNumber")
    @Expose
    private String beforeBusNumber;
    @SerializedName("beforeBusTimeInterval")
    @Expose
    private String beforeBusTimeInterval;
    @SerializedName("afterBusNumber")
    @Expose
    private String afterBusNumber;
    @SerializedName("afterBusTimeInterval")
    @Expose
    private String afterBusTimeInterval;
    @SerializedName("beforeStationName")
    @Expose
    private String beforeStationName;
    @SerializedName("beforeStationArrivalTime")
    @Expose
    private String beforeStationArrivalTime;
    @SerializedName("busState")
    @Expose
    private String busState;
    @SerializedName("busStateCode")
    @Expose
    private Integer busStateCode;
    @SerializedName("numberQueue")
    @Expose
    private Integer numberQueue;
    @SerializedName("queueBuses")
    @Expose
    private List<EndStationBusQueueItem> queueBuses = null;
    private final static long serialVersionUID = 1001459102831591302L;

    public Integer getBusLocationId() {
        return busLocationId;
    }

    public void setBusLocationId(Integer busLocationId) {
        this.busLocationId = busLocationId;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getCurrentRealTime() {
        return currentRealTime;
    }

    public void setCurrentRealTime(String currentRealTime) {
        this.currentRealTime = currentRealTime;
    }

    public String getBeforeBusNumber() {
        return beforeBusNumber;
    }

    public void setBeforeBusNumber(String beforeBusNumber) {
        this.beforeBusNumber = beforeBusNumber;
    }

    public String getBeforeBusTimeInterval() {
        return beforeBusTimeInterval;
    }

    public void setBeforeBusTimeInterval(String beforeBusTimeInterval) {
        this.beforeBusTimeInterval = beforeBusTimeInterval;
    }

    public String getAfterBusNumber() {
        return afterBusNumber;
    }

    public void setAfterBusNumber(String afterBusNumber) {
        this.afterBusNumber = afterBusNumber;
    }

    public String getAfterBusTimeInterval() {
        return afterBusTimeInterval;
    }

    public void setAfterBusTimeInterval(String afterBusTimeInterval) {
        this.afterBusTimeInterval = afterBusTimeInterval;
    }

    public String getBeforeStationName() {
        return beforeStationName;
    }

    public void setBeforeStationName(String beforeStationName) {
        this.beforeStationName = beforeStationName;
    }

    public String getBeforeStationArrivalTime() {
        return beforeStationArrivalTime;
    }

    public void setBeforeStationArrivalTime(String beforeStationArrivalTime) {
        this.beforeStationArrivalTime = beforeStationArrivalTime;
    }

    public String getBusState() {
        return busState;
    }

    public void setBusState(String busState) {
        this.busState = busState;
    }

    public Integer getBusStateCode() {
        return busStateCode;
    }

    public void setBusStateCode(Integer busStateCode) {
        this.busStateCode = busStateCode;
    }

    public Integer getNumberQueue() {
        return numberQueue;
    }

    public void setNumberQueue(Integer numberQueue) {
        this.numberQueue = numberQueue;
    }

    public List<EndStationBusQueueItem> getQueueBuses() {
        return queueBuses;
    }

    public void setQueueBuses(List<EndStationBusQueueItem> queueBuses) {
        this.queueBuses = queueBuses;
    }

}