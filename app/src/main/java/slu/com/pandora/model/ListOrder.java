package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.*;
import java.util.List;

public class ListOrder {

    //@SerializedName("prodlist")
    @SerializedName("list")
    @Expose
    private List<ProdList> prodlist = null;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("tablenum")
    @Expose
    private int tablenum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ProdList> getProdlist() {
        return prodlist;
    }

    public void setProdlist(List<ProdList> prodlist) {
        this.prodlist = prodlist;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getTablenum() {
        return tablenum;
    }

    public void setTablenum(int tablenum) {
        this.tablenum = tablenum;
    }

}