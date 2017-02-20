/**
 * Created by matt on 2/14/2017.
 */

package slu.com.pandora.activity;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;
        import slu.com.pandora.R;
        import slu.com.pandora.model.UserResponse;
        import slu.com.pandora.rest.ApiClient;
        import slu.com.pandora.rest.ApiInterface;

        import static slu.com.pandora.R.layout.sample;

public class sampleXML extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_order1);
    }
}
