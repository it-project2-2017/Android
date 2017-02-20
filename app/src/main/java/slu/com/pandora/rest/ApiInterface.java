package slu.com.pandora.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import slu.com.pandora.model.Product;
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

    @GET("products")
    Call<List<Product>> getProducts();
}
