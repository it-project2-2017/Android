package slu.com.pandora.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("prodlist")
@Expose
private List<OrderProdList> orderProdList;
@SerializedName("total")
@Expose
private Integer total;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public List<OrderProdList> getOrderProdList() {
return orderProdList;
}

public void setOrderProdList(List<OrderProdList> orderProdList) {
this.orderProdList = orderProdList;
}

public Integer getTotal() {
return total;
}

public void setTotal(Integer total) {
this.total = total;
}

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderProdList=" + orderProdList +
                ", total=" + total +
                '}';
    }
}

