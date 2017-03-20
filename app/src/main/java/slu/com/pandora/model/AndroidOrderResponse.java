package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vince on 3/16/2017.
 */

public class AndroidOrderResponse {

    @SerializedName("androidOrder")
    @Expose
    private AndroidOrder androidOrder;

    public AndroidOrder getAndroidOrder() {
        return androidOrder;
    }

    public void setAndroidOrder(AndroidOrder androidOrder) {
        this.androidOrder = androidOrder;
    }

    @Override
    public String toString() {
        return "AndroidOrderResponse{" +
                "androidOrder=" + androidOrder +
                '}';
    }
}
