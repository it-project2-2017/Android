package thesis.mysample.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import thesis.mysample.R;
import thesis.mysample.model.Post;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void logMeIn(View view){
        //Intent is where you call another activity.
        //Just change the second parameter for the name of the activity.
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        //Intent intent = new Intent(this, PostSample.class);
        //Intent  intent = new Intent(this, LoginAct.class);
        Intent intent = new Intent(this, ProductActivity.class);

        EditText user = (EditText)findViewById(R.id.username);
        EditText pass = (EditText)findViewById(R.id.password);
        String message;
        String admin = "admin";
        String password = "password";

        //test by going straight to post.
        intent.putExtra(EXTRA_MESSAGE, "message" );
        startActivity(intent);


        if(user.getText().toString().equals(admin) && pass.getText().toString().equals(password)){
            message="Successful";
            intent.putExtra(EXTRA_MESSAGE, message );
            startActivity(intent);
        }else{
            if(user.getText().toString().equals("")){
                user.setError("Error! no username");
            }else{
                user.setError("Incorrect!");
            }

            if(pass.getText().toString().equals("")){
                pass.setError("Error! no password");
            }else{
                pass.setError("Incorrect!");
            }
        }




        /*
        EditText user = (EditText)findViewById(R.id.username);
        EditText pass = (EditText)findViewById(R.id.password);
        String admin = "admin";
        String password = "password";



        if(user.getText().toString().equals(admin) && pass.getText().toString().equals(password)){
            //correct password
        }else{
            //incorrect password
        }*/
    }
}
