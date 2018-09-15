package kz.desh.snowballs;

import kz.desh.snowballs.server.Server;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SnowballsApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SnowballsApplication.class)
                .web(WebApplicationType.NONE)
                .run(args)
                .getBean(Server.class)
                .start();
    }
}