package ads;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdvertiserController{
        private String name,contactName;
        private long creditLimit;

        @RequestMapping("/api/advertiser")
        public String getName(@RequestParam(name = "name", defaultValue = "Name") String name,
                              @RequestParam(name = "contactName",defaultValue = "use name") String contactName,
                              @RequestParam(name = "creditLimit", defaultValue = "0") long creditLimit){
            this.name = name;
            this.contactName = contactName;
            this.creditLimit = creditLimit;


            return "Recieved:" + name + "  " + contactName + "  " + creditLimit;
        }

    //@RequestMapping("/api/advertiser/verify")
    //public String verify(@RequestParam(name = ))


}




