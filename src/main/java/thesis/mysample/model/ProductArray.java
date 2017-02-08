package thesis.mysample.model;

/**
 * Created by matt on 2/6/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductArray {
    @Expose
    @SerializedName("")
    private ProductResponse [] arr;


    public ProductResponse[] getArr() {
        return arr;
    }

    public void setArr(ProductResponse[] arr) {
        this.arr = arr;
    }
}
