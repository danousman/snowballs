package kz.desh.snowballs;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SnowballsApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SnowballsApplication.class).web(false).run(args);
    }
}