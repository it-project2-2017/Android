package thesis.mysample.model;

/**
 * Created by matt on 2/5/2017.
 */

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import java.util.List;

public class ProductResponse {
    @Expose
    @SerializedName("product")
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
