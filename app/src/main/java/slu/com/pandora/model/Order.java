package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vince on 2/19/2017.
 */

public class Order {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("prodlist")
    @Expose
    private List<Product> prodlist = null;
    @SerializedName("total")
    @Expose
    private Integer total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Product> getProdlist() {
        return prodlist;
    }

    public void setProdlist(List<Product> prodlist) {
        this.prodlist = prodlist;
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
                ", prodlist=" + prodlist +
                ", total=" + total +
                '}';
    }
}
