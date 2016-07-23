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
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import networking.ServiceClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kuba
 */
public class RegisteredTests {
    
    private static Connection con;
    private static Statement statement;
    private static int userId;
    private static ArrayList<Integer> dummyUsers = new ArrayList<Integer>();
    private static ServiceClient client;
    
    @BeforeClass
    public static void init(){
        RegisteredTests.con = null;
        try {
            Class.forName("org.postgresql.Driver");
            RegisteredTests.con = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/beerbuddies",
                "postgres", "qwe123");

            RegisteredTests.statement = RegisteredTests.con.createStatement();
            // add our dummy user
            ResultSet rs = statement.executeQuery("INSERT INTO \"BeerBuddy\".profiles(password, username, email) VALUES('TestUser','TestUser','TestUser@user.pl') returning profileid");
            rs.next();
            RegisteredTests.userId = rs.getInt("profileId");
            RegisteredTests.dummyUsers.add(RegisteredTests.userId);
            // set dummy user location
            statement.execute("INSERT INTO \"BeerBuddy\".LOCATIONS(profileId, longitude, latitude,isCurrent) VALUES(" + RegisteredTests.userId + ", 0.2, 0.2,'1')");
            RegisteredTests.client = new ServiceClient("TestUser", "TestUser");
            for(int i = 0; i<15; i++){
                //add dummy users
                rs = statement.executeQuery("INSERT INTO \"BeerBuddy\".profiles(password, username, email) VALUES('TestUser" + i + "','TestUser" + i + "','TestUser" + i + "@user.pl') returning profileid");
                rs.next();
                int id = rs.getInt("profileId");
                RegisteredTests.dummyUsers.add(id);
                // add locations for dummy users
                statement.execute("INSERT INTO \"BeerBuddy\".LOCATIONS(profileId, longitude, latitude,isCurrent) VALUES(" + id + ", " + i + ", " + i + ",'1')");
            }
            // spawn actual meetings 
            for(int i = 0; i<5; i++){
                statement.execute("INSERT INTO \"BeerBuddy\".MEETINGS(profileId, longitude, latitude,isCurrent) VALUES(" + RegisteredTests.dummyUsers.get(i) + ", " + i + ", " + i + "'1')");
            }
        }
        catch(Exception e){
            System.out.printf(e.getMessage());
        } 
    }
    
    @Test
    public void getBuddiesTest(){
        try{
            JSONArray tmp = RegisteredTests.client.getBuddies(10.0);
            if(tmp != null)
                Assert.assertTrue(tmp.length() > 0);
            else
                Assert.fail();
        }
        catch(JSONException e){
            Assert.fail();
        }
    }
    
    @Test
    public void updateLocationTest(){
            double lat = 0.1;
            double lon = 0.1;
            JSONObject obj = RegisteredTests.client.updateLocation(lon, lat);
            try{
                if(Integer.parseInt(obj.get("status").toString()) == 1){
                    Statement st = RegisteredTests.con.createStatement();
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
    public void createMeetingWithInvalidDateTest(){
        JSONObject obj = RegisteredTests.client.createMeeting(new Date(26, 9, 12), new Date(26, 9, 11), (short)0);
        Assert.assertNull(obj);
    }
    
    @Test
    public void createMeetingTest(){
        Date start = new Date(115, 9, 11);
        Date end = new Date(116, 9, 12);
        short type = 0;
        JSONObject obj = RegisteredTests.client.createMeeting(start, end, type);
        if(Integer.parseInt(obj.get("status").toString()) == 1){
            try{
                Statement statement = RegisteredTests.con.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * from \"BeerBuddy\".MEETINGS where meetingstart = '" + start.toGMTString() + "' and meetingend = '" + end.toGMTString() + "' and type = " + type);
                Assert.assertTrue(rs.next());
            }
            catch(SQLException e){
                Assert.fail();
            }
        }
        else{
            Assert.fail();
        }
    }
    
    @Test
    public void getMeetingsTest(){
        JSONArray arr = RegisteredTests.client.getMeetings(10.0);
        if(arr != null)
            Assert.assertTrue(arr.length() > 0);
        else
            Assert.fail();
    }
    
    @AfterClass
    public static void destroy(){
        try{
            for(Integer val: RegisteredTests.dummyUsers){
                RegisteredTests.statement.execute("DELETE from \"BeerBuddy\".PROFILES WHERE profileid = " + val);
            }
        }
        catch(SQLException e){
            
        }
    }
}
