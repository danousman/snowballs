package kz.desh.snowballs.server.commands;

import akka.actor.ActorRef;
import kz.desh.snowballs.server.LoginActor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginCommand implements Command {
    private static final int LOGIN_ELEMENT = 0;

    private ActorRef loginActor;

    @Autowired
    public LoginCommand(ActorRef loginActor) {
        this.loginActor = loginActor;
    }

    @Override
    public void execute(ActorRef sender, String command) {
        val login = splitCommand(command)[LOGIN_ELEMENT];
        this.loginActor.tell(new LoginActor.LoginActorCommand(login), sender);
    }
}