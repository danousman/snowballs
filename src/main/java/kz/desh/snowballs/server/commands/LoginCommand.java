package kz.desh.snowballs.server.commands;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoginCommand implements Command {
    public static final String COMMAND = "00001";

    private static final int LOGIN_ELEMENT = 0;

    @Override
    public String execute(String command) {
        val login = splitCommand(command)[LOGIN_ELEMENT];
        log.info("Login command from client: {}", login);
        return "Command was send " + login;
    }
}