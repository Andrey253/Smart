package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import helpers.CommonHelper;

public class User extends SugarRecord implements Serializable {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("patronymic")
    @Expose
    private String patronymic;
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("buses")
    @Expose
    private List<Bus> buses= null;
    private final static long serialVersionUID = -7660064229332144915L;


    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param buses
     * @param token
     * @param roles
     * @param name
     * @param surname
     * @param login
     * @param patronymic
     */
    public User(String token, String login, String surname, String name, String patronymic, List<String> roles, List<Bus> buses) {
        super();
        this.token = token;
        this.login = login;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.roles = roles;
        this.buses = buses;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public void setBuses(List<Bus> buses) {
        this.buses = buses;
    }

    public String getBus(String needBusId) {
        for(Bus bi : buses) {
            if(bi.getBusId().trim().equals(needBusId)) {
                return bi.getBusState().trim();
            }
        }
        return null;
    }

    public String getBusId(String needBusNumber) {
        for(Bus bi : buses) {
            if(bi.getBusState().trim().equals(needBusNumber)) {
                return bi.getBusId();
            }
        }
        return null;
    }
    public List<Bus> getSortBusNumberList() {
        List result = new ArrayList(buses);
        Collections.sort(result, new Comparator<Bus>() {
            @Override
            public int compare(Bus e1, Bus e2) {
                return e1.getBusState().compareTo(e2.getBusState());
            }
        });
        return result;
    }
    /**
     * Метод проверяет является ли объект пустым
     * @return Результат проверки
     */
    public boolean isEmptyUser() {
        return this == null ||
                (CommonHelper.isStringEmpty(this.getToken()) &&
                        CommonHelper.isStringEmpty(this.getLogin()) &&
                        CommonHelper.isStringEmpty(this.getSurname()) &&
                        CommonHelper.isStringEmpty(this.getName()) &&
                        CommonHelper.isStringEmpty(this.getPatronymic()));
    }
}