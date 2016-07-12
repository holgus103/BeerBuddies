/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkingTest;

import networking.ServiceClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    
    
//    @Test
//    public void getBuddiesTest(){
//        try{
//            JSONArray tmp = this.client.getBuddies(10.0);
//            if(tmp != null)
//                Assert.assertTrue(tmp.length() != 0);
//            else
//                Assert.fail();
//        }
//        catch(JSONException e){
//            Assert.fail();
//        }
//    }
    
    @Test
    public void updateLocationTest(){
            double[] res = new double[2];
            double[] exp = {10.0, 10.0};
            JSONObject obj = this.client.updateLocation(10.0, 10.0);
            try{
                res[0] = Double.parseDouble(obj.get("longitude").toString());
                res[1] = Double.parseDouble(obj.get("latitude").toString());
                Assert.assertArrayEquals(exp, res, 0);
            }
            catch(NullPointerException e){
                Assert.fail();
            }
    }
}
