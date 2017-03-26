package slu.com.pandora.rest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import slu.com.pandora.model.OrderResponse;
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
            "User-Agent: Cofmat"
    })
    Call<UserResponse> login(@Field("name") String name, @Field("password") String password);

    @GET("products/food/")
    Call<ProductResponse> getProduct();

    @GET("products/{category}/")
    Call<ProductResponse> getProducts(@Path("category") String category);

    @POST("sendorder")
    @Headers({
            "Content-Type: application/json"
    })
    Call<String> sendOrder(@Body OrderResponse orderResponse);

    @GET("orders/unpaid")
    Call<OrderResponse> getQueue();

}
