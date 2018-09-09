package kz.desh.snowballs;

import kz.desh.snowballs.server.Server;
import lombok.val;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SnowballsApplication {
    public static void main(String[] args) {
        val context = new SpringApplicationBuilder(SnowballsApplication.class).web(false).run(args);
        val server = context.getBean(Server.class);
        server.start(2600);
    }
}