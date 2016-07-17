/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkingTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
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
    private static Connection con;
    @BeforeClass
    public static void init(){
        ServiceClientTest.con = null;
        try {
         Class.forName("org.postgresql.Driver");
        ServiceClientTest.con = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/beerbuddies",
            "postgres", "qwe123");
        }
        catch(Exception e){} 
    }
    
    
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
    
    @Test
    public void updateLocationTest(){
            double lat = 10.0;
            double lon = 10.0;
            JSONObject obj = this.client.updateLocation(lon, lat);
            try{
                if(Integer.parseInt(obj.get("status").toString()) == 1){
                    Statement st = ServiceClientTest.con.createStatement();
                    ResultSet rs = st.executeQuery("select * from \"BeerBuddy\".locations l join \"BeerBuddy\".profiles p on l.profileid = p.profileid where iscurrent = '1' and" +
                            " latitude = " + lat + " and longitude = " + lon );
                    Assert.assertTrue(rs.next());
                }
                else{
                    Assert.fail();
                }
            }
            catch(NullPointerException e){
                Assert.fail();
            }
            catch(SQLException e){
                Assert.fail();
            }
    }
    
    @Test
    public void registerTest(){
        JSONObject obj = this.client.register("dummy", "asd", "asd@asd.pl");
        try{
            Statement statement = ServiceClientTest.con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from \"BeerBuddy\".PROFILES WHERE username = 'dummy' and password = 'asd' and email = 'asd@asd.pl'");
            Assert.assertTrue(rs.next());
            statement.execute("DELETE from \"BeerBuddy\".PROFILES WHERE username = 'dummy' and password = 'asd' and email = 'asd@asd.pl'");
        }
        catch(SQLException e){
            Assert.fail();
        }
       
    }
    
    @Test
    public void createMeetingWithInvalidDateTest(){
        JSONObject obj = this.client.createMeeting(new Date(26, 9, 12), new Date(26, 9, 11), (short)0);
        Assert.assertNull(obj);
    }
    
    @Test
    public void createMeetingTest(){
        Date start = new Date(115, 9, 11);
        Date end = new Date(116, 9, 12);
        short type = 0;
        JSONObject obj = this.client.createMeeting(start, end, type);
        if(Integer.parseInt(obj.get("status").toString()) == 1){
            try{
                Statement statement = ServiceClientTest.con.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * from \"BeerBuddy\".MEETINGS where meetingstart = '" + start.toGMTString() + "' and meetingend = '" + end.toGMTString() + "' and type = " + type);
                Assert.assertTrue(rs.next());
                statement.execute("DELETE from \"BeerBuddy\".MEETINGS where meetingstart = '" + start.toGMTString() + "' and meetingend = '" + end.toGMTString() + "' and type = " + type);
            }
            catch(SQLException e){
                Assert.fail();
            }
        }
        else{
            Assert.fail();
        }
    }
}
