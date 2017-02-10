package slu.com.pandora.model;

/**
 * Created by vince on 2/11/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("admin")
    @Expose
    private Boolean admin;
    @SerializedName("barista")
    @Expose
    private Boolean barista;
    @SerializedName("cashier")
    @Expose
    private Boolean cashier;
    @SerializedName("cook")
    @Expose
    private Boolean cook;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getBarista() {
        return barista;
    }

    public void setBarista(Boolean barista) {
        this.barista = barista;
    }

    public Boolean getCashier() {
        return cashier;
    }

    public void setCashier(Boolean cashier) {
        this.cashier = cashier;
    }

    public Boolean getCook() {
        return cook;
    }

    public void setCook(Boolean cook) {
        this.cook = cook;
    }

}
