package superapki.beerbuddies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import superapki.beerbuddies.global.BeerBuddies;

/**
 * Created by Kuba on 14/08/2016.
 */
public class BeerBuddiesActivity extends AppCompatActivity {
    protected BeerBuddies beerBuddies;

    protected void startActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        this.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        this.beerBuddies = BeerBuddies.getInstance();
    }
}
