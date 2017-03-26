package slu.com.pandora.rest;

/**
 * Created by matt on 2/10/2017.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://192.168.1.5:8010/PanBox/webresources/panbox/";
    //public static final String BASE_URL = "http://192.168.1.13:28080/PanBox/webresources/panbox/";

    private static Retrofit retrofit = null;



    public static Retrofit getClient(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
