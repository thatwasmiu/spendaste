package daste.spendaste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpendasteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpendasteApplication.class, args);
        System.out.println(
                "Picked up JAVA_TOOL_OPTIONS: -Duser.timezone=Asia/Ho_Chi_Minh\n" +
                "Default JVM TimeZone: " + java.util.TimeZone.getDefault().getID()
        );
    }

}
