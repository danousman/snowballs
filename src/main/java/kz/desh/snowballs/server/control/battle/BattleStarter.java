package kz.desh.snowballs.server.control.battle;

import kz.desh.snowballs.server.control.player.Players;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BattleStarter extends Thread {
    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);
                BattleQueue.getBattlePairs()
                        .forEach((key, value) -> {
                            val player1 = Players.getPlayer(key);
                            val player2 = Players.getPlayer(value);
                            val oneToOneBattle = new OneToOneBattle(player1, player2);
                            player1.setBattle(oneToOneBattle);
                            player2.setBattle(oneToOneBattle);

                            String responseFormat = "20004 %s %d";
                            player1.getOut().println(String.format(responseFormat, player2.getLogin(), player2.getAllHeat()));
                            player2.getOut().println(String.format(responseFormat, player1.getLogin(), player1.getAllHeat()));
                        });
            }
        } catch (InterruptedException e) {
            log.error("Battle starter failed", e);
        }
    }
}