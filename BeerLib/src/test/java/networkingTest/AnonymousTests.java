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

public class AnonymousTests {
    private ServiceClient client = new ServiceClient();
    private static Connection con;
    @BeforeClass
    public static void init(){
        AnonymousTests.con = null;
        try {
         Class.forName("org.postgresql.Driver");
        AnonymousTests.con = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/beerbuddies",
            "postgres", "qwe123");
        }
        catch(Exception e){} 
    }
        
    @Test
    public void registerTest(){
        JSONObject obj = this.client.register("dummy", "asd", "asd@asd.pl");
        try{
            Statement statement = AnonymousTests.con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * from \"BeerBuddy\".PROFILES WHERE username = 'dummy' and password = 'asd' and email = 'asd@asd.pl'");
            Assert.assertTrue(rs.next());
            int id = rs.getInt("profileId");
            statement.execute("DELETE from \"BeerBuddy\".PROFILES WHERE profileid = " + id);
        }
        catch(SQLException e){
            Assert.fail();
        }
       
    }
    

}
