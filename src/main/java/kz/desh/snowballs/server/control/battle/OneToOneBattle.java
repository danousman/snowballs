package kz.desh.snowballs.server.control.battle;

import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class OneToOneBattle extends Thread {
    private static final int BATTLE_INTERVAL = 100;
    private static final int MAXIMUM_ENERGY = 100;

    private static final String PLAYER_BATTLE_CHARACTERISTICS_COMMAND = "20005" +
            " %d" +     //current player heat
            " %.1f" +   //current player energy
            " %d" +     //enemy player heat
            " %.1f";    //enemy player energy

    private PlayerEntity player1;
    private PlayerEntity player2;

    private Characteristics player1Characteristics;
    private Characteristics player2Characteristics;

    private boolean player1Ready;
    private boolean player2Ready;

    OneToOneBattle(PlayerEntity player1, PlayerEntity player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.player1Characteristics = new Characteristics(
                player1.getAllHeat(),
                player1.getAllDodge(),
                player1.getAllStrength(),
                0.5d,
                0d);
        this.player2Characteristics = new Characteristics(
                player2.getAllHeat(),
                player2.getAllDodge(),
                player2.getAllStrength(),
                0.5d,
                0d);
    }

    public void playerReady(PlayerEntity player) {
        if (player.getId() == this.player1.getId()) {
            this.player1Ready = true;
        } else {
            this.player2Ready = true;
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(BATTLE_INTERVAL);

                if (this.player1Ready && this.player2Ready) {
                    provideBattle();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void provideBattle() {
        calculatePlayersCharacteristics();
    }

    private void calculatePlayersCharacteristics() {
        this.player1Characteristics.increaseEnergy();
        this.player2Characteristics.increaseEnergy();
        this.player1.getOut().println(prepareCharacteristicsCommand(this.player1Characteristics, this.player2Characteristics));
        this.player2.getOut().println(prepareCharacteristicsCommand(this.player2Characteristics, this.player1Characteristics));
    }

    private String prepareCharacteristicsCommand(Characteristics player1Characteristics, Characteristics player2Characteristics) {
        return String.format(PLAYER_BATTLE_CHARACTERISTICS_COMMAND,
                player1Characteristics.getHeat(),
                player1Characteristics.getEnergy(),
                player2Characteristics.getHeat(),
                player2Characteristics.getEnergy());
    }

    @Setter
    @Getter
    @AllArgsConstructor
    private static class Characteristics {
        private int heat;
        private double dodge;
        private double strength;
        private double energyRecoverySpeed;
        private double energy;

        void increaseEnergy() {
            if (this.energy >= MAXIMUM_ENERGY) {
                this.energy = MAXIMUM_ENERGY;
            } else {
                this.energy += this.energyRecoverySpeed;
            }
        }
    }
}