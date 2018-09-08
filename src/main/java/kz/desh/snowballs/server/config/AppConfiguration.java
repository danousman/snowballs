package kz.desh.snowballs.server.config;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import kz.desh.snowballs.server.LoginActor;
import kz.desh.snowballs.server.ServerActor;
import kz.desh.snowballs.server.commands.LoginCommand;
import kz.desh.snowballs.server.commands.executor.CommandExecutor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static kz.desh.snowballs.server.config.SpringExtension.SPRING_EXTENSION_PROVIDER;

@Configuration
@ComponentScan()
public class AppConfiguration {
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public ActorSystem actorSystem() {
        val system = ActorSystem.create("snowballs");
        SPRING_EXTENSION_PROVIDER.get(system).initialize(applicationContext);
        return system;
    }

    @Bean
    public ActorRef serverActor(ActorSystem actorSystem, CommandExecutor commandExecutor) {
        return actorSystem.actorOf(ServerActor.props(commandExecutor), "server");
    }

    @Bean
    public ActorRef loginActor(ActorSystem actorSystem) {
        return actorSystem.actorOf(LoginActor.props(), "login");
    }

    @Bean
    public CommandExecutor commandExecutor(LoginCommand loginCommand) {
        return new CommandExecutor(loginCommand);
    }
}