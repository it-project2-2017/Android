package slu.com.pandora.rest;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import slu.com.pandora.model.OrderResponse;
import slu.com.pandora.model.Orders;
import slu.com.pandora.model.ProductResponse;
import slu.com.pandora.model.UserResponse;

/**
 * Created by vince on 2/11/2017.
 */

public interface ApiInterface {

    @POST("login")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json",
    })
    Call<UserResponse> login(@Field("name") String name, @Field("password") String password);

    @GET("products/{category}/")
    Call<ProductResponse> getProducts(@Path("category") String category);

    @POST("sendorder")
    @Headers({
            "Content-Type: application/json"
    })
    Call<OrderResponse> sendOrder(@Body OrderResponse orderResponse);

    @GET("orders/unpaid")
    Call<OrderResponse> getQueue();

    @GET("orders/{status}")
    Call<Orders> getOrders(@Path("status") String status);

    @POST("changestatus")
    @FormUrlEncoded
    @Headers({
            "Accept: text/plain"
    })
    Call<String> changeStatus(@Field("orderid") int orderid, @Field("status") String status);

    @POST("pendingstatus")
    @FormUrlEncoded
    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "Accept: text/plain"
    })
    Call<String> pendingStatus(@Field("orderid") int orderid);

    @POST("reserveproduct")
    @FormUrlEncoded
    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "Accept: text/plain"
    })
    Call<String> reserveProduct(@Field("pid") int id);

    @POST("decreservation")
    @FormUrlEncoded
    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "Accept: text/plain"
    })
    Call<String> decReservation(@Field("pname") String name);

    @POST("clearres")
    @FormUrlEncoded
    @Headers({
            "Content-Type:application/x-www-form-urlencoded",
            "Accept: text/plain"
    })
    Call<String> clearRes(@Field("pname") String name, @Field("qty") int qty);

    @GET("allproducts")
    Call<ProductResponse> getAllProducts();

}
