package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orders {

    @SerializedName("orderList")
    @Expose
    private OrderList orderList;

    public OrderList getOrderList() {
        return orderList;
    }

    public void setOrderList(OrderList orderList) {
        this.orderList = orderList;
    }

}