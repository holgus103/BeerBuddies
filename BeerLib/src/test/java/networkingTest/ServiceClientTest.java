/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkingTest;

import networking.ServiceClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kuba
 */

public class ServiceClientTest {
    private ServiceClient client = new ServiceClient();
    
//    @BeforeClass
//    public static void init(){
//        ths
//    }
    
    @Test
    public void getBuddiesTest(){
        try{
            JSONArray tmp = this.client.getBuddies(10.0);
            if(tmp != null)
                Assert.assertTrue(tmp.length() != 0);
            else
                Assert.fail();
        }
        catch(JSONException e){
            Assert.fail();
        }
    }
}
