package kz.desh.snowballs.server.commands;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import kz.desh.snowballs.server.LoginActor;
import lombok.val;

public class LoginCommand implements Command {
    private static final int LOGIN_ELEMENT = 0;

    private ActorRef loginActor;

    public LoginCommand(ActorSystem system) {
        this.loginActor = system.actorOf(LoginActor.props(), "login");
    }

    @Override
    public void execute(ActorRef sender, String command) {
        val login = splitCommand(command)[LOGIN_ELEMENT];
        this.loginActor.tell(new LoginActor.LoginActorCommand(login), sender);
    }
}