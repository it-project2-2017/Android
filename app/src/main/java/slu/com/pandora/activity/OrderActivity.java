package slu.com.pandora.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import slu.com.pandora.R;
import slu.com.pandora.adapter.ConfirmOrderAdapter;
import slu.com.pandora.adapter.OrderAdapter;
import slu.com.pandora.adapter.ProductAdapter;
import slu.com.pandora.model.Order;
import slu.com.pandora.model.OrderProdList;
import slu.com.pandora.model.OrderResponse;
import slu.com.pandora.model.Pl;
import slu.com.pandora.model.Product;
import slu.com.pandora.model.ProductResponse;
import slu.com.pandora.model.Try;
import slu.com.pandora.rest.ApiClient;
import slu.com.pandora.rest.ApiInterface;

/**
 * Created by vince on 3/8/2017.
 */

public class OrderActivity extends AppCompatActivity{

    ArrayList<Product> orders = new ArrayList<Product>();
    ArrayList<Pl> plorders = new ArrayList<Pl>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        Intent intent = getIntent();

        //AppBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getOrder();

    }

    public void getOrder(){
        ApiInterface webServiceInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ProductResponse> call = webServiceInterface.getProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, final Response<ProductResponse> response) {
                if (response.isSuccessful()){

                    //Toast.makeText(OrderActivity.this, response.body().getProductList().getList().toString(), Toast.LENGTH_LONG).show();

                    GridView productListGV = (GridView)findViewById(R.id.productListGV);
                    List<Product> product = response.body().getProductList().getList();
                    final ProductAdapter adapter = new ProductAdapter(OrderActivity.this, R.layout.product_list_grid_row, product);
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

                            final Pl pl = new Pl();

                            pl.setId(response.body().getProductList().getList().get(i).getId());
                            pl.setName(response.body().getProductList().getList().get(i).getName());
                            pl.setPrice(response.body().getProductList().getList().get(i).getPrice());
                            pl.setQty(response.body().getProductList().getList().get(i).getQty());
                            pl.setEmpid(response.body().getProductList().getList().get(i).getEmpid());

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

                            if (!plorders.contains(pl)) {
                                plorders.add(pl);
                                product.setQty(1);
                            } else {
                                for (int z = 0; z < plorders.size(); z++) {
                                    if (plorders.get(z).getId() == product.getId()) {
                                        plorders.get(z).setQty(plorders.get(z).getQty() + 1);
                                    }
                                }
                            }

                            ListView orderListLV = (ListView) findViewById(R.id.orderListLV);
                            final OrderAdapter adapter = new OrderAdapter(OrderActivity.this, R.layout.order_list_view_row, orders);
                            orderListLV.setAdapter(adapter);
                            orderListLV.refreshDrawableState();

                            Button clear = (Button)findViewById(R.id.clearOrderBtn);

                            clear.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    orders.clear();
                                    adapter.notifyDataSetChanged();
                                }
                            });

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
                    Toast.makeText(OrderActivity.this, + response.code() + " Failed to retrieve products !" + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Toast.makeText(OrderActivity.this, t.getMessage() + " Failed to Connect !", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void popUpConfirmOrder(){
        LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popup_send_order, null);

        final PopupWindow popupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setHeight(1200);
        popupWindow.setWidth(900);
        popupWindow.setOutsideTouchable(false);

        ListView popUpList = (ListView) customView.findViewById(R.id.confirmOrderLV);
        ConfirmOrderAdapter adapter = new ConfirmOrderAdapter(OrderActivity.this, R.layout.confirm_order_list_view_row, orders);
        popUpList.setAdapter(adapter);

        TextView orderTotalPrice = (TextView)customView.findViewById(R.id.totalTV);
        orderTotalPrice.setText(sum(orders) + "");

        Button confirmOrder = (Button)customView.findViewById(R.id.confirmOrderBtn);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOrders();

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

    private double sum (List<Product> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++){
            sum += list.get(i).getPrice() * list.get(i).getQty();
        }
        return sum;
    }

    public void goToKitchenActivity() {
        Intent intent = new Intent(this, KitchenActivity.class);
        startActivity(intent);
    }

    public void sendOrders(){

        ApiInterface webServiceInterface = ApiClient.getClient().create(ApiInterface.class);

        //HashMap<String, Integer> hp = new HashMap<String, Integer>();
        OrderResponse orderResponse = new OrderResponse();
        List<OrderProdList> orderProdListList = new ArrayList<OrderProdList>();

        for (Product product : orders){
            OrderProdList orderProdList = new OrderProdList();
           // hp.put(product.getName(), product.getQty());
            orderProdList.setKey(product.getName());
            orderProdList.setValue(product.getQty());

            orderProdListList.add(orderProdList);
        }


        Order order = new Order();
        order.setOrderProdList(orderProdListList);
        //order.setTotal((int) sum(orders));
        //Try ti = new Try();
        orderResponse.setOrder(order);
        System.out.println(orderResponse);



        /*Try trys = new Try();
        trys.setTotal(sum(orders));
        trys.setProdlist(hp);
        System.out.println(trys);*/
        //order.setOrderProdList();


        /*for (Product product : orders){
            OrderProdList orderProdList = new OrderProdList();
            orderProdList.setKey(product.getName());
            orderProdList.setValue(product.getQty());
            productLists.add(orderProdList);
        }

        Order order = new Order();
        int total = sum(orders);

        order.setOrderProdList(productLists);
        order.setTotal(total);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrder(order);
        Toast.makeText(OrderActivity.this, orderResponse.getOrder().toString(), Toast.LENGTH_LONG).show();*/
       // System.out.println(orderResponse.toString());

        /*AndroidOrderResponse androidOrderResponse = new AndroidOrderResponse();
        AndroidOrder androidOrder = new AndroidOrder();
        androidOrder.setPl(plorders);

        androidOrderResponse.setAndroidOrder(androidOrder);*/

       /* List<Pl> plList = new ArrayList<Pl>();
        Pl pl = new Pl();
        AndroidOrder androidOrder = new AndroidOrder();
        AndroidOrderResponse androidOrderResponse = new AndroidOrderResponse();

        pl.setName("Bacon Roast (Pixie Enchanted Pizza)");
        pl.setQty(0);
        pl.setEmpid(0);
        pl.setId(22);
        pl.setPrice(198);
        plList.add(pl);

        pl.setName("Aloha Wave (Pixie Enchanted Pizza)");
        pl.setQty(0);
        pl.setEmpid(0);
        pl.setId(23);
        pl.setPrice(198);


        plList.add(pl);
        androidOrder.setPl(plList);
        androidOrderResponse.setAndroidOrder(androidOrder);
        System.out.println(androidOrderResponse);
        */

        Try ti = new Try();
        Call<ResponseBody> call = webServiceInterface.sendOrder(ti);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OrderActivity.this, " Welcome " + response.raw(), Toast.LENGTH_LONG).show();
                    System.out.println(response.toString());
                } else {
                    Toast.makeText(OrderActivity.this, " Woah mali " + response.message() + response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(OrderActivity.this, t.getMessage() + " Failed to connect !", Toast.LENGTH_LONG).show();
            }
        });

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
            case R.id.action_logout:
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_show_orders:
                Intent intentOrder = new Intent(this, ShowOrderMenuActivity.class);
                startActivity(intentOrder);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
