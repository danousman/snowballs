package kz.desh.snowballs.server;

import kz.desh.snowballs.server.commands.CommandExecutor;
import kz.desh.snowballs.server.control.battle.BattleStarter;
import kz.desh.snowballs.server.control.player.PlayerSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class Server {
    private final CommandExecutor commandExecutor;
    private final ServerProps serverProps;
    private final PlayerSaveService playerSaveService;
    private final BattleStarter battleStarter;

    private ServerSocket serverSocket;

    public void start() {
        try {
            this.serverSocket = new ServerSocket(this.serverProps.getPort());
            this.battleStarter.start();
            listenClients();
        } catch (IOException e) {
            log.error("Exception occurs. Server will stop.", e);
        }
    }

    private void listenClients() {
        while (true) {
            try {
                new ClientHandler(this.serverSocket.accept(), this.commandExecutor, this.playerSaveService).start();
            } catch (IOException e) {
                log.error("Exception occurred during accept new socket.", e);
            }
        }
    }

    @PreDestroy
    public void stop() {
        try {
            if (Objects.nonNull(this.serverSocket)) {
                this.serverSocket.close();
            }
        } catch (IOException e) {
            log.error("Exception occurs during stop server.", e);
        }
    }
}