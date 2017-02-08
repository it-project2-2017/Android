package thesis.mysample.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thesis.mysample.R;
import thesis.mysample.model.Login;
import thesis.mysample.rest.ApiClient;
import thesis.mysample.rest.ApiInterface;

public class LoginAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Login> call = apiService.login();
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                int id = response.body().getId();
                String name = response.body().getName();

                TextView id_1 = (TextView)findViewById(R.id.id);
                TextView name_1 = (TextView)findViewById(R.id.name);

                id_1.setText(String.valueOf(id));
                name_1.setText(name);
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }



}
