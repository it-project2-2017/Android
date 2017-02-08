package thesis.mysample.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by matt on 1/13/2017.
 */
//connection between the webservice
public class ApiClient {
    //public static final String BASE_URL = "http://api.themoviedb.org/3/";
    //public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";
    public static final String BASE_URL = "http://192.168.1.5:8010/CofMatSVR/webresources/cofmat/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
