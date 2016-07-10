package superapki.beerbuddies;

import android.app.Application;

import superapki.beerbuddies.networking.ServiceClient;

/**
 * Created by Kuba on 2016-07-09.
 */
public class BeerBuddies extends Application {
    ServiceClient client = new ServiceClient();

}
