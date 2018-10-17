package kz.desh.snowballs.server.control.battle;

import kz.desh.snowballs.server.control.player.Players;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BattleStarter extends Thread {
    private static final int SEARCH_INTERVAL = 1000;
    private static final String START_BATTLE_COMMAND = "20004" +
            " %s" +  //name
            " %d";   //heat

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(SEARCH_INTERVAL);

                BattleQueue.getBattlePairs()
                        .forEach((key, value) -> {
                            val player1 = Players.getPlayer(key);
                            val player2 = Players.getPlayer(value);
                            val oneToOneBattle = new OneToOneBattle(player1, player2);
                            player1.setBattle(oneToOneBattle);
                            player2.setBattle(oneToOneBattle);

                            sendStartBattleCommandForPlayer(player1, player2);

                            oneToOneBattle.start();
                        });
            }
        } catch (InterruptedException e) {
            log.error("Battle starter failed", e);
        }
    }

    private static void sendStartBattleCommandForPlayer(PlayerEntity player1, PlayerEntity player2) {
        player1.getOut().println(String.format(START_BATTLE_COMMAND, player2.getLogin(), player2.getAllHeat()));
        player2.getOut().println(String.format(START_BATTLE_COMMAND, player1.getLogin(), player1.getAllHeat()));
    }
}