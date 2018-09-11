package kz.desh.snowballs.server;

import kz.desh.snowballs.server.commands.executor.CommandExecutor;
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

    ClientHandler(Socket socket, CommandExecutor commandExecutor) {
        this.clientSocket = socket;
        this.commandExecutor = commandExecutor;
    }

    public void run() {
        try (PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()))) {
            listenCommands(in, out);
        } catch (SocketException e) {
            log.warn("Connection finished");
            finishClientSession();
        } catch (IOException e) {
            log.error("Exception occurred during cooperation with client");
        }
    }

    private void listenCommands(BufferedReader in, PrintWriter out) throws IOException {
        String command;
        while ((command = in.readLine()) != null) {
            out.println(this.commandExecutor.execute(command));
        }
    }

    private void finishClientSession() {
    }
}