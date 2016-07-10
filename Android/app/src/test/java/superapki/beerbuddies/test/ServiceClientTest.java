package superapki.beerbuddies.test;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import superapki.beerbuddies.networking.ServiceClient;

/**
 * Created by Kuba on 2016-07-09.
 */
public class ServiceClientTest {
    private ServiceClient client = new ServiceClient();

    @Test
    public void connectionTest(){
        try {
            Assert.assertTrue(client.getBuddies(10.0).length() != 0);
        }
        catch(JSONException e){
            Assert.fail();
        }
    }

    @Test
    public void getBuddiesTest(){

    }
}
