package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;

public class Bus extends SugarRecord implements Serializable {

    @SerializedName("busId")
    @Expose
    private String busId;
    @SerializedName("routeNumber")
    @Expose
    private String routeNumber;
    @SerializedName("busState")
    @Expose
    private String busState;
    private final static long serialVersionUID = 3718274073561289025L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Bus() {
    }

    /**
     *
     * @param routeNumber
     * @param busId
     * @param busState
     */
    public Bus(String busId, String routeNumber, String busState) {
        super();
        this.busId = busId;
        this.routeNumber = routeNumber;
        this.busState = busState;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getBusState() {
        return busState;
    }

    public void setBusState(String busState) {
        this.busState = busState;
    }

}