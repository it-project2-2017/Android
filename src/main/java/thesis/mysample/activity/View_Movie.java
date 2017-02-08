package thesis.mysample.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thesis.mysample.R;
import thesis.mysample.model.Movie;
import thesis.mysample.model.MovieResponse;
import thesis.mysample.rest.ApiClient;
import thesis.mysample.rest.ApiInterface;

public class View_Movie extends AppCompatActivity {
    //to access the api of a server
    private final static String API_KEY = "28543bb1c130369ff11ad5985fc1de70";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__movie);


        if (API_KEY.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                int statusCode = response.code();

                //retrieves the data from the server.
                List<Movie> movies = response.body().getResults();

                //get the id of editText in the xml
                TextView id = (TextView)findViewById(R.id.ID);
                TextView movie = (TextView)findViewById(R.id.title);
                TextView video = (TextView)findViewById(R.id.video);
                Movie move = movies.get(1);

                //set the editText in xml.
                id.setText(String.valueOf(move.getId()));
                movie.setText(move.getTitle());
                video.setText(move.getOverview());



            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

    }
}
