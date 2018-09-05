package kz.desh.snowballs;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import kz.desh.snowballs.server.ServerActor;
import lombok.val;

import java.util.Scanner;

public class SnowballsApplication {
    private static final String STOP_SERVER_COMMAND = "stop";

    public static void main(String[] args) {
        val system = ActorSystem.create("snowballs");
        createServerActor(system);
        listenConsoleCommands(system);
    }

    private static void createServerActor(ActorSystem system) {
        val server = system.actorOf(ServerActor.props(system), "server");
        server.tell("00001 login", ActorRef.noSender());
    }

    private static void listenConsoleCommands(ActorSystem system) {
        System.out.print("Command: ");

        try (Scanner scanner = new Scanner(System.in)) {
            val inputString = scanner.nextLine();
            if (!STOP_SERVER_COMMAND.equals(inputString)) {
                listenConsoleCommands(system);
            } else {
                stopServer(system);
            }
        }
    }

    private static void stopServer(ActorSystem system) {
        system.terminate();
        System.exit(0);
    }
}