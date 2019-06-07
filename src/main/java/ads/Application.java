/*
Build:
./gradlew build
Run:
java -jar build/libs/gs-rest-service-0.1.0.jar
 */



package ads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}