package backend.jambo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class JamboV2Application {

    public static void main(String[] args) {
        SpringApplication.run(JamboV2Application.class, args);
    }

}
