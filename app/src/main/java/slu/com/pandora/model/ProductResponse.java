package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductResponse {

    @SerializedName("prodList")//prodList
    @Expose
    private ProductList productList;

    public ProductList getProductList() {
        return productList;
    }

    public void setProductList(ProductList productList) {
    this.productList = productList;
}

    @Override
    public String toString() {
        return "ProductResponse{" +
                "productList=" + productList +
                '}';
    }
}