/*
Build:
./gradlew build
Run:
java -jar build/libs/gs-rest-service-0.1.0.jar
 */



package ads;

import org.springframework.boot.SpringApplication;
import java.sql.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception{

        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa","");
        Statement stmt = conn.createStatement();
        //stmt.executeUpdate("DROP TABLE IF EXISTS Advertisers");
        //stmt.executeUpdate("CREATE TABLE Advertisers(Name VARCHAR(255) NOT NULL UNIQUE,ContactName VARCHAR(255), creditlimit INT NOT NULL)");
        conn.close();
        SpringApplication.run(Application.class, args);
    }
}