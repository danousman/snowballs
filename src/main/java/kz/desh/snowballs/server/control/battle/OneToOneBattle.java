package kz.desh.snowballs.server.control.battle;

import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;

public class OneToOneBattle extends Thread {
    private static final int THREAD_SLEEP = 100;

    private PlayerEntity player1;
    private PlayerEntity player2;

    private Characteristics playerCharacteristics1;
    private Characteristics playerCharacteristics2;

    private boolean player1Ready;
    private boolean player2Ready;

    OneToOneBattle(PlayerEntity player1, PlayerEntity player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.playerCharacteristics1 = new Characteristics(
                player1.getAllHeat(),
                player1.getAllDodge(),
                player1.getAllStrength(),
                0.5d);
        this.playerCharacteristics2 = new Characteristics(
                player2.getAllHeat(),
                player2.getAllDodge(),
                player2.getAllStrength(),
                0.5d);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(THREAD_SLEEP);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RequiredArgsConstructor
    private static class Characteristics {
        private final int heat;
        private final double dodge;
        private final double strength;
        private final double energyRecoverySpeed;
    }
}