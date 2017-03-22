package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderList {

    @SerializedName("list")
    @Expose
    private java.util.List<slu.com.pandora.model.List> list = null;

    public java.util.List<slu.com.pandora.model.List> getList() {
        return list;
    }

    public void setList(java.util.List<slu.com.pandora.model.List> list) {
        this.list = list;
    }

}