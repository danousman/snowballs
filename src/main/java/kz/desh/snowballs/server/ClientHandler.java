package kz.desh.snowballs.server;

import kz.desh.snowballs.server.commands.executor.CommandExecutor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

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
        try {
            val out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            val in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            listenCommands(in, out);
            in.close();
            out.close();
            this.clientSocket.close();
        } catch (SocketException e) {
            log.warn("Connection finished");
        } catch (IOException e) {
            log.error("Exception occurred during cooperation with client");
        }
    }

    private void listenCommands(BufferedReader in, PrintWriter out) throws IOException {
        String command;
        while ((command = in.readLine()) != null) {
            out.write(this.commandExecutor.execute(command));
        }
    }
}