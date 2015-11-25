package superapki.beerbuddies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String EDIT_TEXT = "superapki.beerbuddies.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnSend(View view) {
        Intent intent = new Intent(this,MapsActivity.class);
//        EditText editText = (EditText) findViewById(R.id.acti);
//        intent.putExtra(EDIT_TEXT,editText.getText().toString());
        startActivity(intent);
    }
}
