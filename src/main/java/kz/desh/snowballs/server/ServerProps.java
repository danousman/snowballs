package kz.desh.snowballs.server;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ServerProps {
    private final int port;

    public ServerProps(@Value("${snowballs.server.port}") int port) {
        this.port = port;
    }
}