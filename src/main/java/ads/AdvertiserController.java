package ads;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import java.sql.*;

@Controller
public class AdvertiserController{



        @RequestMapping(path = "/api/advertiser/advertiser", method = RequestMethod.POST) //site to add a new advertiser to the database
        public String newAdvertiser(@RequestParam(name = "name", defaultValue = "Name") String name,
                              @RequestParam(name = "contactName",defaultValue = "N/A") String contactName,
                              @RequestParam(name = "creditLimit", defaultValue = "0") long creditLimit) throws Exception{

            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa","");

            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO advertisers VALUES(\'" +
                    name + "\', \'" + contactName + "\', " + creditLimit + ")");
            ResultSet rs = stmt.executeQuery("SELECT name FROM advertisers");
            String s= "";
            while(rs.next())
                s+="  " + rs.getString(1);
            conn.close();
            return "why";//s;
        }
        @RequestMapping(path = "/api/advertiser/checkcredits", method = RequestMethod.GET) //site to check if advertiser has enough credits
        public String checkCredits(@RequestParam(name = "name")String name) throws Exception{
            boolean enough;
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa","");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM advertisers WHERE name = \'" + name + "\'");
            rs.next();
            if(rs.getInt(3)>0) enough = true;
                else enough = false;
            return "why";//enough?"This advertieser has enough credits":"This advertiser does not have enough credits";
        }
        @RequestMapping(path = "/api/advertiser/delete", method = RequestMethod.DELETE) //delete advertiser
        public String deleteAdvertiser(@RequestParam(name = "name")String name) throws Exception{
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test","sa","");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM advertisers WHERE name = \'" + name + "\'");
            return "why";//name+ " deleted.";
        }
        @RequestMapping(path = "/api/advertiser/get", method = RequestMethod.GET) //view advertiser credits, name, and contactName
        public String viewAdvertiser(@RequestParam(name = "name")String name) throws Exception{
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test","sa","");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM advertisers  WHERE name = \'" + name + "\'");
            if(!rs.next()){ //only look at contactname if name not found
                rs = stmt.executeQuery("SELECT * FROM advertisers  WHERE contactname = \'" + name + "\'");
                rs.next();
            }
            String s = "Name: " + rs.getString(1) + " Contact name: " + rs.getString(2) + " Credits: " + rs.getInt(3);
            conn.close();
            return "why";//s;

        }

        @RequestMapping(path = "api/advertiser/update", method = RequestMethod.PUT) //update advertiser credits and/or contactName
        public String updateAdvertiser(@RequestParam(name = "name")String name,
                                       @RequestParam(name = "contactName", defaultValue = "")String contactName,
                                       @RequestParam(name = "creditLimit", defaultValue = "-1")long creditLimit) throws Exception{
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test","sa","");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM advertisers WHERE name = \'" + name + "\'");
            rs.next();
            String curContact = rs.getString(2);
            long curCreditLimit  = rs.getInt(3);
            System.out.println("Contact name to be changed: " + curContact+ "***********************************");
            if(contactName.equals("")) contactName = curContact;
            if(creditLimit ==-1) creditLimit = curCreditLimit;
            stmt.executeUpdate("UPDATE advertisers SET contactname = \'" + contactName + "\', creditlimit = " +creditLimit + " WHERE name = \'"+ name + "\'");
            String s = "Updated from:  Contact Name: " + curContact + " Credit Limit: " + curCreditLimit +
                    "\nTo:   Contact Name: " + contactName + " Credit Limit: " + creditLimit;
            conn.close();
            return "why";//s;
        }


    @GetMapping("/hi")
        public String index() {
            return "why";
        }



}

