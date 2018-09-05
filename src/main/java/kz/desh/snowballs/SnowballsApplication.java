package kz.desh.snowballs;

import akka.actor.ActorSystem;
import lombok.val;

import java.util.Scanner;

public class SnowballsApplication {
    private static final String STOP_SERVER_COMMAND = "stop";

    public static void main(String[] args) {
        val system = ActorSystem.create("snowballs");
        listenConsoleCommands(system);
    }

    private static void listenConsoleCommands(ActorSystem system) {
        System.out.print("Command: ");

        try (Scanner scanner = new Scanner(System.in)) {
            String inputString = scanner.nextLine();
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