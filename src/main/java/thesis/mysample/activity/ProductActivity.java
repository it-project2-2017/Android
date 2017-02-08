package thesis.mysample.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thesis.mysample.R;
import thesis.mysample.model.ProductResponse;
import thesis.mysample.rest.ApiClient;
import thesis.mysample.rest.ApiInterface;

public class ProductActivity extends AppCompatActivity {
    private LinearLayout layout;
    private TextView desc;
    int numOfProd;
    List<ProductResponse> products;
    ProductResponse productArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        final TextView id = (TextView)findViewById(R.id.id);


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<ProductResponse>> call = apiService.getProduct();
        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                int statusCode = response.code();

                //retrieves the data from the server.
                products = response.body();
                numOfProd = response.body().size();

                dynamic(numOfProd,products);
                //get the id of editText in the xml
                //TextView id = (TextView)findViewById(R.id.id);
                TextView name = (TextView)findViewById(R.id.name);
                desc = (TextView)findViewById(R.id.desc);
                TextView price = (TextView)findViewById(R.id.price);
                layout = (LinearLayout)findViewById(R.id.myLinearLayout);

                //get certain array.
                ProductResponse exampleprod = products.get(1);

                //set the editText in xml.
                id.setText(String.valueOf(numOfProd));
                //id.setText(String.valueOf(prod.getId()));
                //name.setText(String.valueOf(exampleprod.getProduct().getName()));
                //name.setText(String.valueOf(product.getProduct().getName()));
                //desc.setText(prod.getDesc());
                //price.setText(String.valueOf(prod.getPrice()));





            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {

                id.setText(String.valueOf(t));
            }
        });




    }

    private void dynamic(int num, List<ProductResponse> prod){
        desc = (TextView)findViewById(R.id.desc);
        layout = (LinearLayout)findViewById(R.id.myLinearLayout);

        for (int ctrl = 0; ctrl < num ; ctrl++){
            //create a new textview
            final TextView newText = new TextView(this);
            //add the textview to the linear layout
            layout.addView(newText);
            //get a certain product in the list
            productArray = prod.get(ctrl);

            //instantiate
            final TextView id_1 = new TextView(this);
            final TextView name_1 = new TextView(this);
            final TextView desc_1 = new TextView(this);
            final TextView price_1 = new TextView(this);

            //setText
            id_1.setText(String.valueOf(productArray.getProduct().getId()));
            name_1.setText(String.valueOf(productArray.getProduct().getName()));
            desc_1.setText(String.valueOf(productArray.getProduct().getDesc()));
            price_1.setText(String.valueOf(productArray.getProduct().getPrice()));

            //Add textview into the linearlayout

            layout.addView(id_1);
            layout.addView(name_1);
            layout.addView(desc_1);
            layout.addView(price_1);

            desc.setText("YES OK NA");

        }
    }
}
