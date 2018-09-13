package kz.desh.snowballs;

import kz.desh.snowballs.server.Server;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SnowballsApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SnowballsApplication.class)
                .web(false)
                .run(args)
                .getBean(Server.class)
                .start();
    }
}