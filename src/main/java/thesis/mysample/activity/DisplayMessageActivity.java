package thesis.mysample.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import thesis.mysample.R;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);


        Intent intent = getIntent(); //getIntent grabs the intent that started the activity
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE); //getStringExtra retrieves the data from the first activity
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        /*You add the TextView to the layout identified by R.id.activity_display_message. You cast the layout to
         ViewGroup because it is the superclass of all layouts and contains the addView() method.*/
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }

    public void getMeBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
