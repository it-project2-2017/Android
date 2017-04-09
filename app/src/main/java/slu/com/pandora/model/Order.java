package slu.com.pandora.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("list")
    @Expose
    private List<OrderProdList> list = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("tablenum")
    @Expose
    private Integer tablenum;

    public List<OrderProdList> getList() {
        return list;
    }

    public void setList(List<OrderProdList> list) {
        this.list = list;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTablenum() {
        return tablenum;
    }

    public void setTablenum(Integer tablenum) {
        this.tablenum = tablenum;
    }

    @Override
    public String toString() {
        return "Order{" +
                "list=" + list +
                ", id=" + id +
                ", total=" + total +
                ", tablenum=" + tablenum +
                '}';
    }
}

