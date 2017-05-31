package slu.com.pandora.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.adapter.ConfirmOrderAdapter;
import slu.com.pandora.adapter.OrderAdapter;
import slu.com.pandora.adapter.ProductAdapter;
import slu.com.pandora.model.Category;
import slu.com.pandora.model.Order;
import slu.com.pandora.model.OrderProdList;
import slu.com.pandora.model.OrderResponse;
import slu.com.pandora.model.Product;
import slu.com.pandora.model.ProductResponse;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;


/**
 * Created by vince on 3/8/2017.
 */

public class OrderActivity extends AppCompatActivity{
    ArrayList<Product> orders = new ArrayList<Product>();
    Category category = new Category();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);

        /*Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        String empName = (String) bundle.get("getUser");*/

        //AppBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button foodBtn = (Button)findViewById(R.id.foodButton);
        Button beverageBtn = (Button)findViewById(R.id.beverageButton);


        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOrder("food");
                category.setCategory("food");
            }
        });

        beverageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOrder("beverage");
                category.setCategory("beverage");
            }
        });
        category.setCategory("food");

        getOrder(category.getCategory());
    }

    /*public void chuchu(){
        LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.order_list_view_row, null);

        final Button but = (Button)view.findViewById(R.id.addBtn);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < products.size(); i++){
                    if (!products.get(i).getAvailable()){
                        //but.setEnabled(false);
                        Toast.makeText(view.getContext(),"Not Available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }*/

    public void getOrder(String cat){
        ApiInterface webServiceInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ProductResponse> call = webServiceInterface.getProducts(cat);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, final Response<ProductResponse> response) {
                if (response.isSuccessful()){
                    final GridView productListGV = (GridView)findViewById(R.id.productListGV);
                    List<Product> products = response.body().getProductList().getList();
                    final ProductAdapter adapter = new ProductAdapter(OrderActivity.this, R.layout.product_list_grid_row, products);
                    productListGV.setAdapter(adapter);

                    productListGV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                            final Product product = new Product();

                            product.setId(response.body().getProductList().getList().get(i).getId());
                            product.setName(response.body().getProductList().getList().get(i).getName());
                            product.setPrice(response.body().getProductList().getList().get(i).getPrice());
                            product.setQty(response.body().getProductList().getList().get(i).getQty());
                            product.setEmpid(response.body().getProductList().getList().get(i).getEmpid());
                            product.setAvailable(response.body().getProductList().getList().get(i).getAvailable());

                            if (!orders.contains(product)) {
                                orders.add(product);
                                product.setQty(1);
                                reserveProduct(product.getId());
                            } else {
                                for (int z = 0; z < orders.size(); z++) {
                                    if (orders.get(z).getId() == product.getId()) {
                                        orders.get(z).setQty(orders.get(z).getQty() + 1);
                                        reserveProduct(product.getId());

                                    }
                                }
                            }

                            //chuchu();

                            ListView orderListLV = (ListView) findViewById(R.id.orderListLV);
                            final OrderAdapter adapter = new OrderAdapter(OrderActivity.this, R.layout.order_list_view_row, orders);
                            orderListLV.setAdapter(adapter);


                            Button clear = (Button)findViewById(R.id.clearOrderBtn);

                            clear.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    for(int i = 0; i < orders.size(); i++) {
                                        String name = orders.get(i).getName();
                                        int qty = orders.get(i).getQty();
                                        clearReservedProd(name, qty);
                                    }
                                        orders.clear();
                                        adapter.notifyDataSetChanged();

                                }
                            });

                            Button sendOrder = (Button)findViewById(R.id.sendOrderBtn);
                            sendOrder.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(orders.isEmpty()){
                                        Toast.makeText(OrderActivity.this, "No selected order", Toast.LENGTH_LONG).show();
                                    } else {
                                        popUpConfirmOrder();
                                    }

                                }
                            });
                        }
                    });


                } else {
                    Toast.makeText(OrderActivity.this, + response.code() + " Failed to retrieve products!" + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(OrderActivity.this, t.getMessage() + " Failed to Connect!", Toast.LENGTH_LONG).show();
            }
        });
    }
    Order order = new Order();
    public void sendOrders(){

        ApiInterface webServiceInterface = ApiClient.getClient().create(ApiInterface.class);
        OrderResponse orderResponse = new OrderResponse();
        List<OrderProdList> orderProdListList = new ArrayList<OrderProdList>();

        for (Product product : orders){
            OrderProdList orderProdList = new OrderProdList();
            orderProdList.setKey(product.getName());
            orderProdList.setValue(product.getQty());
            orderProdListList.add(orderProdList);
        }

        order.setList(orderProdListList);
        orderResponse.setOrder(order);

        Call<OrderResponse> call = webServiceInterface.sendOrder(orderResponse);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OrderActivity.this, "Order sent!", Toast.LENGTH_LONG).show();
                    System.out.println(response.toString());
                } else {
                    Toast.makeText(OrderActivity.this, "Ooops! there's something wrong." + response.message() + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Toast.makeText(OrderActivity.this, t.getMessage() + "Server Connection Lost", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void reserveProduct(final int id){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiInterface.reserveProduct(id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    getOrder(category.getCategory());
                    //Toast.makeText(OrderActivity.this, "Order Reserved" + id, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(OrderActivity.this, "Failed to reserve order" + id, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OrderActivity.this, t.getMessage() + "Server Connection Lost", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void clearReservedProd(String name, int qty){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<String> call = apiInterface.clearRes(name, qty);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    getOrder(category.getCategory());
                    //Toast.makeText(OrderActivity.this, "Cleared", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(OrderActivity.this, "Failed to clear reserved orders", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(OrderActivity.this, t.getMessage() + "Server Connection Lost", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void popUpConfirmOrder(){
        LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        final View customView = inflater.inflate(R.layout.popup_send_order, null);

        final PopupWindow popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setHeight(1200);
        popupWindow.setWidth(900);
        popupWindow.setOutsideTouchable(false);

        ListView popUpList = (ListView) customView.findViewById(R.id.confirmOrderLV);
        final ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(OrderActivity.this, R.layout.confirm_order_list_view_row, orders);
        popUpList.setAdapter(adapter);

        DecimalFormat formatter = new DecimalFormat("#,###,###.00");
        String formatted = formatter.format(sum(orders));

        TextView orderTotalPrice = (TextView)customView.findViewById(R.id.totalTV);
        orderTotalPrice.setText("Php " + formatted);

        EditText tableNumET = (EditText)findViewById(R.id.TableNum_ET);
        final String tableNumber = tableNumET.getText().toString();
        final TextView tableNum = (TextView)customView.findViewById(R.id.TableNo_TV);
        tableNum.setText(tableNumber);

        Button confirmOrder = (Button)customView.findViewById(R.id.confirmOrderBtn);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tableNumber.isEmpty()){
                    //order.setTablenum(0);
                    Toast.makeText(OrderActivity.this, "No Table No.", Toast.LENGTH_LONG).show();
                } else {
                    order.setTablenum(Integer.valueOf(tableNumber));
                    sendOrders();
                    orders.clear();
                    goToOrderActivity();
                    popupWindow.dismiss();
                }
            }

        });

        Button cancelOrder = (Button)customView.findViewById(R.id.cancelOrderBtn);
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //order.setTablenum(0);
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(customView.findViewById(R.id.confirmOrderLV), Gravity.CENTER, 0,0);
    }

    private double sum (List<Product> list) {
        double sum = 0.00;
        for (int i = 0; i < list.size(); i++){
            sum += list.get(i).getPrice() * list.get(i).getQty();
        }
        return sum;
    }

    //Adds res/menu/appbar_menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar_order, menu);
        return true;
    }

    //selection of action in the action bar.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case R.id.action_show_orders:


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void goToOrderActivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this, R.style.MyDialogStyle)
                .setTitle(R.string.title)
                .setMessage(R.string.description)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for(int j = 0; j < orders.size(); j++) {
                            String name = orders.get(j).getName();
                            int qty = orders.get(j).getQty();
                            clearReservedProd(name, qty);
                        }
                        orders.clear();

                        if (orders.isEmpty()){
                            finish();
                            OrderActivity.super.onBackPressed();
                        }
                    }
                }).create().show();
    }

}
