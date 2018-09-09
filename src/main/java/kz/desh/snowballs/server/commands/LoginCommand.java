package kz.desh.snowballs.server.commands;

import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class LoginCommand implements Command {
    public static final String COMMAND = "00001";

    private static final int LOGIN_ELEMENT = 0;

    @Override
    public void execute(String command) {
        val login = splitCommand(command)[LOGIN_ELEMENT];
    }
}