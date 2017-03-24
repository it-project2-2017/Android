package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.*;

public class OrderList {

    @SerializedName("list")
    @Expose
    private java.util.List<ListOrder> list = null;

    public java.util.List<ListOrder> getListOrder() {
        return list;
    }

    public void setList(java.util.List<ListOrder> list) {
        this.list = list;
    }

}