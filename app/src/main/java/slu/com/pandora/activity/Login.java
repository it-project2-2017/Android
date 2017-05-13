package slu.com.pandora.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//para sa finished products
/*import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import slu.com.pandora.model.ItemObject;
import slu.com.pandora.adapter.RecyclerViewAdapter;*/

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.fragment.CurrentOrdersFragment;
import slu.com.pandora.model.UserResponse;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

public class Login extends AppCompatActivity{
    public String userName;
    public static final String getUser = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
        public void userLogin(View view){
            ApiInterface webServiceInterface = ApiClient.getClient().create(ApiInterface.class);

            final EditText usernameET = (EditText)findViewById(R.id.usernameET);
            final EditText passwordET = (EditText)findViewById(R.id.passwordET);
            final String username = usernameET.getText().toString();
            final String password = passwordET.getText().toString();

            Call<UserResponse> call = webServiceInterface.login(username, password);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful()){
                        if(username.equals("")&& password.equals("")){
                            usernameET.setError("Please Enter a Username");
                            passwordET.setError("Please Enter a Password");
                        }else if(username.isEmpty())
                            usernameET.setError("Please Enter a Username");
                        else if(password.isEmpty())
                            passwordET.setError("Please Enter a Password");

                        if (response.body().getUser().getPosition().toString().equalsIgnoreCase("cashier")) {
                            Toast.makeText(Login.this, " Welcome " + response.body().getUser().getName() + "!", Toast.LENGTH_LONG).show();
                            userName = response.body().getUser().getName();
                            goToOrderActivity();
                        } else if (response.body().getUser().getPosition().toString().equalsIgnoreCase("barista") || response.body().getUser().getPosition().toString().equalsIgnoreCase("cook")) {
                            Toast.makeText(Login.this, " Welcome " + response.body().getUser().getName() + "!", Toast.LENGTH_LONG).show();
                            goToKitchenActivity();
                        }else{
                            Toast.makeText(Login.this, "Incorrect Credentials", Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(Login.this, + response.code() + " Failed to login !" + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(Login.this, t.getMessage() + " Failed to connect !", Toast.LENGTH_LONG).show();
                }
            });

    }

    public void goToOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(getUser, userName);
        startActivity(intent);
        finish();
    }

    public void goToKitchenActivity() {
        Intent intent = new Intent(this, KitchenActivity.class);
        startActivity(intent);
        finish();
    }

}
