package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by vince on 3/15/2017.
 */

public class Try {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("prodlist")
    @Expose
    private HashMap<String, Integer> prodlist;

    @SerializedName("total")
    @Expose
    private double total;

    public Try() {

    }

    public Try(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public HashMap<String, Integer> getProdlist() {
        return prodlist;
    }

    public void setProdlist(HashMap<String, Integer> prodlist) {
        this.prodlist = prodlist;
    }

    @Override
    public String toString() {
        return "Try{" +
                "id=" + id +
                ", prodlist=" + prodlist +
                ", total=" + total +
                '}';
    }
}
