package kz.desh.snowballs.server.control.battle;

import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.util.Random;

public class OneToOneBattle extends Thread {
    private static final int BATTLE_INTERVAL = 100;
    private static final int MAXIMUM_ENERGY = 100;

    private static final String PLAYER_BATTLE_CHARACTERISTICS_COMMAND = "20005" +
            " %d" +     //current player heat
            " %.1f" +   //current player energy
            " %d" +     //enemy player heat
            " %.1f";    //enemy player energy


    private static final Random RANDOM = new Random();

    private PlayerEntity player1;
    private PlayerEntity player2;

    private Characteristics player1Characteristics;
    private Characteristics player2Characteristics;

    private boolean player1Ready;
    private boolean player2Ready;

    private UsedSkill player1DefaultSkill;
    private UsedSkill player2DefaultSkill;

    OneToOneBattle(PlayerEntity player1, PlayerEntity player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.player1Characteristics = new Characteristics(
                player1.getAllHeat(),
                player1.getAllDodge(),
                player1.getAllStrength(),
                0.15d,
                0d);
        this.player2Characteristics = new Characteristics(
                player2.getAllHeat(),
                player2.getAllDodge(),
                player2.getAllStrength(),
                0.15d,
                0d);

        this.player1DefaultSkill = new UsedSkill(
                1000,
                10,
                1000,
                false,
                false,
                3000,
                3000,
                false);

        this.player2DefaultSkill = new UsedSkill(
                1000,
                10,
                1000,
                false,
                false,
                3000,
                3000,
                false);
    }

    public void playerReady(PlayerEntity player) {
        if (isPlayer1(player)) {
            this.player1Ready = true;
        } else {
            this.player2Ready = true;
        }
    }

    private boolean isPlayer1(PlayerEntity player) {
        return player.getId() == this.player1.getId();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(BATTLE_INTERVAL);

                if (this.player1Ready && this.player2Ready) {
                    provideBattle();

                    if (battleFinished()) {
                        break;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void provideBattle() {
        processDefaultSkills();
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

    private void processDefaultSkills() {
        processDefaultSkill(this.player1DefaultSkill, this.player1Characteristics, this.player2Characteristics);
        processDefaultSkill(this.player2DefaultSkill, this.player2Characteristics, this.player1Characteristics);
    }

    private void processDefaultSkill(UsedSkill player1DefaultSkill, Characteristics player1Characteristics, Characteristics player2Characteristics) {
        if (!player1DefaultSkill.isPrepared()) { //Скилл не подготовлен
            val player2DodgeResult = player2Characteristics.getDodge() - player1Characteristics.getStrength(); //Уклонение второго минус сила броска первого

            if (player2DodgeResult < doubleRandom()) { //Если не увернулся
                player1DefaultSkill.hit(); //Попал
            } else { //Если увернулся
                player1DefaultSkill.miss(); //Промазал
            }

            player1DefaultSkill.decreaseCooldown(); //Уменьшаем время перезарядки скила
            player1DefaultSkill.prepare(); //Устанавливаем флаг, что скил подготовлен
        } else { //Если подготовлен
            if (player1DefaultSkill.getCooldownLeft() > 0) { //Скил ещё не перезарядился
                player1DefaultSkill.decreaseCooldown(); //Уменьшаем время перезарядки скила
            } else { //Перезарядка прошла
                if (!player1DefaultSkill.isThrown()) { //Если ещё не брошен
                    player1DefaultSkill.toThrow(); //Бросаем
                } else { //Если был брошен
                    if (player1DefaultSkill.getTimeLeft() > 0) { //Если скилл ещё не долетел
                        player1DefaultSkill.decreaseTimeLeft(); //Уменьшаем время полета
                    } else { //Если долетел
                        if (player1DefaultSkill.isHit()) { //Если должен был попасть
                            player2Characteristics.receivedDamage(player1DefaultSkill.getDamage()); //Получил урон
                        }

                        player1DefaultSkill.recharge(); //Перезаряжаем скилл
                    }
                }
            }
        }
    }

    private static double doubleRandom() {
        return RANDOM.nextDouble() * 100d;
    }

    private boolean battleFinished() {
        return !this.player1Characteristics.alive() || !this.player2Characteristics.alive();
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

        void receivedDamage(int damage) {
            this.heat -= damage;
        }

        boolean alive() {
            return heat > 0;
        }
    }

    @Setter
    @Getter
    @AllArgsConstructor
    private static class UsedSkill {
        private double flyTime;
        private int damage;
        private double timeLeft;
        private boolean thrown;
        private boolean hit;
        private double cooldown;
        private double cooldownLeft;
        private boolean prepared;

        void hit() {
            this.hit = true;
        }

        void miss() {
            this.hit = false;
        }

        void decreaseCooldown() {
            this.cooldownLeft -= BATTLE_INTERVAL;
        }

        void prepare() {
            this.prepared = true;
        }

        void toThrow() {
            this.thrown = true;
        }

        void decreaseTimeLeft() {
            this.timeLeft -= BATTLE_INTERVAL;
        }

        void recharge() {
            this.timeLeft = this.flyTime;
            this.thrown = false;
            this.hit = false;
            this.cooldownLeft = this.cooldown;
            this.prepared = false;
        }
    }
}