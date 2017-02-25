package slu.com.pandora.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.adapter.ConfirmOrderAdapter;
import slu.com.pandora.adapter.OrderAdapter;
import slu.com.pandora.adapter.ProductAdapter;
import slu.com.pandora.model.Product;
import slu.com.pandora.model.UserResponse;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Product> orders = new ArrayList<Product>();

    PopupWindow popupWindow;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);
        getProduct();

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

    public void getProduct(){

        ApiInterface webServiceInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Product>> call = webServiceInterface.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, final Response<List<Product>> response) {
                if (response.isSuccessful()){

                    GridView homeGV = (GridView)findViewById(R.id.productGV);
                    final List<Product> product = response.body();
                    ProductAdapter adapter = new ProductAdapter(MainActivity.this, R.layout.product_grid_row, product);
                    homeGV.setAdapter(adapter);

                    homeGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            final Product product = new Product();

                            product.setId(response.body().get(i).getId());
                            product.setName(response.body().get(i).getName());
                            product.setDescription(response.body().get(i).getDescription());
                            product.setPrice(response.body().get(i).getPrice());
                            product.setQty(response.body().get(i).getQty());
                            product.setEmpid(response.body().get(i).getEmpid());

                            if (!orders.contains(product)) {
                                orders.add(product);
                                product.setQty(1);
                            } else {
                                for (int z = 0; z < orders.size(); z++) {
                                    if (orders.get(z).getId() == product.getId()) {
                                        orders.get(z).setQty(orders.get(z).getQty() + 1);
                                    }
                                }
                            }

                            ListView popUp = (ListView)findViewById(R.id.orderLV);
                            OrderAdapter adapter = new OrderAdapter(MainActivity.this, R.layout.order_list_view_row, orders);
                            popUp.setAdapter(adapter);

                            //for total price
                            TextView orderTotalPrice = (TextView)findViewById(R.id.orderTotalPriceTV);
                            orderTotalPrice.setText(sum(orders) + "");
                            orderTotalPrice.invalidate();

                            //for testing
                            TextView test = (TextView) findViewById(R.id.test);
                            test.setText(orders + "");
                            test.invalidate();

                            relativeLayout = (RelativeLayout)findViewById(R.id.rl);
                            Button sendOrder = (Button)findViewById(R.id.sendOrderBtn);

                            sendOrder.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popUpConfirmOrder();
                                }
                            });

                        }

                    });
                } else {
                    Toast.makeText(MainActivity.this, + response.code() + " Failed to retrieve products !" + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage() + " Failed to Connect !", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void popUpConfirmOrder(){
        LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popup_send_order, null);

        popupWindow = new PopupWindow(customView);
        popupWindow.setHeight(1200);
        popupWindow.setWidth(900);

        ListView listView = (ListView) customView.findViewById(R.id.confirmOrderLV);
        ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(MainActivity.this, R.layout.confirm_order_list_view_row, orders);
        listView.setAdapter(adapter);

        TextView orderTotalPrice = (TextView)customView.findViewById(R.id.totalTV);
        orderTotalPrice.setText(sum(orders) + "");

        Button confirmOrder = (Button)customView.findViewById(R.id.confirmOrderBtn);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to do
            }
        });

        Button cancelOrder = (Button)customView.findViewById(R.id.cancelOrderBtn);
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(customView.findViewById(R.id.confirmOrderLV), Gravity.CENTER, 0,0);

    }

    private static int sum (List<Product> list) {
        int sum = 0;
        for (int i = 0; i < list.size(); i++){
            sum += list.get(i).getPrice() * list.get(i).getQty();
        }
        return sum;
    }

}
