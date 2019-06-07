package ads;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

@Controller
public class AdvertiserController{



        @RequestMapping("/api/advertiser/advertiser") //site to add a new advertiser to the database
        public String getName(@RequestParam(name = "name", defaultValue = "Name") String name,
                              @RequestParam(name = "contactName",defaultValue = "use name") String contactName,
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
            return s;
        }
        @RequestMapping("/api/advertiser/checkcredits") //site to check if advertiser has enough credits
        public String checkCredits(@RequestParam(name = "name")String name) throws Exception{
            boolean enough;
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa","");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM advertisers WHERE name = \'" + name + "\'");
            rs.next();
            if(rs.getInt(3)>0) enough = true;
                else enough = false;
            return enough?"This advertieser has enough credits":"This advertiser does not have enough credits";
        }
        @RequestMapping("/api/advertiser/delete")
        public String delteAdvertiser(@RequestParam(name = "name")String name) throws Exception{
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test","sa","");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM advertisers WHERE name = \'" + name + "\'");
            return name+ " deleted.";
        }
        @RequestMapping("/api/advertiser/get")
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
            return s;

        }

        @RequestMapping("/api/advertiser/update")
        public String updateAdvertiser(@RequestParam(name = "name")String name,
                                       @RequestParam(name = "contactName", defaultValue = "")String contactName,
                                       @RequestParam(name = "creditLimit", defaultValue = "-1")long creditLimit) throws Exception{
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test","sa","");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM advertisers WHERE name = \'" + name + "\'");
            rs.next();
            String curContact = rs.getString(2);
            long curCreditLimit  = rs.getInt(3);
            if(contactName == "") contactName = curContact;
            if(creditLimit ==-1) creditLimit = curCreditLimit;
            stmt.executeUpdate("UPDATE advertisers SET contactname = \'" + contactName + "\', creditlimit = " +creditLimit + " WHERE name = \'"+ name + "\'");
            String s = "Updated from:  Contact Name: " + curContact + " Credit Limit: " + curCreditLimit +
                    "\nTo:   Contact Name: " + contactName + " Credit Limit: " + creditLimit;
            conn.close();
            return s;
        }


    @GetMapping("/hi")
        public String index() {
            return "why";
        }



}

