package slu.com.pandora.rest;

import android.support.annotation.RawRes;
import android.util.TypedValue;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import slu.com.pandora.model.AndroidOrderResponse;
import slu.com.pandora.model.Order;
import slu.com.pandora.model.OrderResponse;
import slu.com.pandora.model.Orders;
import slu.com.pandora.model.Product;
import slu.com.pandora.model.ProductResponse;
import slu.com.pandora.model.Try;
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
    Call<ProductResponse> getProducts();

    @GET("products/{category}")
    Call<ProductResponse> getProduct(@Path("category") String category);

    @GET("orders/{status}")
    Call<Orders> getOrders(@Path("status") String status);

    @POST("sendorder")
    @Headers({
            "Content-Type: application/json"
    })
    Call<ResponseBody> sendOrder(@Body Try orderResponse);//ID, List, Total

}
