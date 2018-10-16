package kz.desh.snowballs;

import kz.desh.snowballs.server.Server;
import kz.desh.snowballs.server.control.battle.BattleStarter;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SnowballsApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(SnowballsApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        context.getBean(Server.class).start();
    }
}