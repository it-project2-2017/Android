package thesis.mysample.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import thesis.mysample.model.Login;
import thesis.mysample.model.MovieResponse;
import thesis.mysample.model.Post;
import thesis.mysample.model.ProductArray;
import thesis.mysample.model.ProductResponse;


/**
 * Created by matt on 1/13/2017.
 */

//Class used to access resources of the webserver
public interface ApiInterface {


    @GET("login")
    Call<Login> login ();

    @GET("products")
    //Call<ProductResponse> getProduct();
    Call<List<ProductResponse>> getProduct();

    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("title") String title,
                        @Field("body") String body,
                        @Field("userId") long userId);



    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);
}
