package slu.com.pandora.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderResponse {

        @SerializedName("order")
        @Expose
        private Order order;

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "order=" + order +
                '}';
    }
}