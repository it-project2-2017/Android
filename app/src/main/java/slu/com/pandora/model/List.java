package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("prodlist")
    @Expose
    private java.util.List<ProdList> prodlist = null;
    @SerializedName("total")
    @Expose
    private Double total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public java.util.List<ProdList> getProdlist() {
        return prodlist;
    }

    public void setProdlist(java.util.List<ProdList> prodlist) {
        this.prodlist = prodlist;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}