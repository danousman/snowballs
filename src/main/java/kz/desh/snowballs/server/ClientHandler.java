package kz.desh.snowballs.server;

import kz.desh.snowballs.server.commands.executor.CommandExecutor;
import kz.desh.snowballs.server.control.PlayerSaveService;
import kz.desh.snowballs.server.control.Players;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

@Slf4j
public class ClientHandler extends Thread {
    private CommandExecutor commandExecutor;
    private Socket clientSocket;
    private PlayerSaveService playerSaveService;

    private PlayerEntity player;

    ClientHandler(Socket socket, CommandExecutor commandExecutor, PlayerSaveService playerSaveService) {
        this.clientSocket = socket;
        this.commandExecutor = commandExecutor;
        this.playerSaveService = playerSaveService;
    }

    public void run() {
        try (PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()))) {
            listenCommands(in, out);
        } catch (SocketException e) {
            finishClientSession();
        } catch (IOException e) {
            log.error("Exception occurred during cooperation with client", e);
            finishClientSession();
        }
    }

    private void listenCommands(BufferedReader in, PrintWriter out) throws IOException {
        String command;
        while ((command = in.readLine()) != null) {
            out.println(this.commandExecutor.execute(player, command, (args) -> this.player = (PlayerEntity) args[0]));
        }
    }

    private void finishClientSession() {
        this.playerSaveService.savePlayer(this.player);
        Players.removePlayer(this.player);
    }
}