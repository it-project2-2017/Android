package slu.com.pandora.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.adapter.ProductAdapter;
import slu.com.pandora.model.ProductResponse;
import slu.com.pandora.model.UserResponse;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_v1);
        //getProduct();
    }

    public void userLogin(){
        Button loginBtn = (Button)findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiInterface webServiceInterface = ApiClient.getClient().create(ApiInterface.class);

                EditText usernameET = (EditText)findViewById(R.id.usernameET);
                EditText passwordET = (EditText)findViewById(R.id.passwordET);
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                Call<UserResponse> call = webServiceInterface.login(username, password);
                call.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body().getUser().getId() == 1) {
                                Toast.makeText(MainActivity.this, " Welcome " + response.body().getUser().getName() + "!", Toast.LENGTH_LONG).show();
                                //homeActivity();
                            } else {
                                Toast.makeText(MainActivity.this, " Invalid username or password! ", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, + response.code() + " Failed to login !" + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage() + " Failed to connect !", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    /*public void getProduct(){
        ApiInterface webServiceInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<ProductResponse>> call = webServiceInterface.getProducts();
        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, final Response<List<ProductResponse>> response) {
                if (response.isSuccessful()){

                    GridView homeGV = (GridView)findViewById(R.id.productGV);
                    List<ProductResponse> product = response.body();
                    ProductAdapter adapter = new ProductAdapter(MainActivity.this, R.layout.product_grid_row, product);
                    homeGV.setAdapter(adapter);

                    homeGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            //to do...
                        }

                    });
                } else {
                    Toast.makeText(MainActivity.this, + response.code() + " Failed to retrieve products !" + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage() + " Failed to Connect !", Toast.LENGTH_LONG).show();
            }
        });
    }*/

}
