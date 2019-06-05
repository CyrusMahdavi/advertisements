package ads;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

@RestController
public class AdvertiserController{

        @RequestMapping("/api/advertiser") //site to add a new advertiser to the database
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
        @RequestMapping("/api/checkcredits") //site to check if advertiser has enough credits
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
        @RequestMapping("/api/delete")
        public String delteAdvertiser(@RequestParam(name = "name")String name) throws Exception{
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test","sa","");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM advertisers WHERE name = \'" + name + "\'");
            return name+ " deleted.";
        }


}




