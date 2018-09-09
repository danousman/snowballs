package kz.desh.snowballs.server;

import kz.desh.snowballs.server.commands.executor.CommandExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.ServerSocket;

@Slf4j
@Component
@RequiredArgsConstructor
public class Server {
    private final CommandExecutor commandExecutor;

    private ServerSocket serverSocket;

    public void start(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            listenClients();
        } catch (IOException e) {
            log.error("Exception occurs. Server will stop.", e);
        }
    }

    private void listenClients() {
        while (true) {
            try {
                new ClientHandler(this.serverSocket.accept(), this.commandExecutor).start();
            } catch (IOException e) {
                log.error("Exception occurred during accept new socket.", e);
            }
        }
    }

    @PreDestroy
    public void stop() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            log.error("Exception occurs during stop server.", e);
        }
    }
}