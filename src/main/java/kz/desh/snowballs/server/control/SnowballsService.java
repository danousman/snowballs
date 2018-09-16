package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class SnowballsService {
    private static final int CHANCE_CREATE_MIDDLE_SNOWBALL = 15;
    private static final int CHANCE_CREATE_HARD_SNOWBALL = 5;
    private static final int TIME_FOR_CREATE_SNOWBALL = 10000;
    private static final int MAX_PERCENTS = 100;

    public void createSnowballs(PlayerEntity player) {
        val random = new Random();
        val currentTime = LocalDateTime.now();
        val action = player.getActionEntity();
        val storage = player.getStorageEntity();
        val snowballsCount = storage.snowballsCount();
        val storageTypeSize = storage.getType().getSize();

        if (snowballsCount < storageTypeSize) {
            val timePassed = action.getStartDate().until(currentTime, ChronoUnit.MILLIS);
            val needToCreateSnowballs = Math.min((storageTypeSize - snowballsCount), longToInt(timePassed / TIME_FOR_CREATE_SNOWBALL));
            for (int i = 0; i < needToCreateSnowballs; i++) {
                val randomValue = random.nextInt(MAX_PERCENTS);
                if (randomValue < CHANCE_CREATE_HARD_SNOWBALL) {
                    storage.addHardSnowball();
                } else if (randomValue < CHANCE_CREATE_MIDDLE_SNOWBALL) {
                    storage.addMiddleSnowball();
                } else {
                    storage.addSimpleSnowball();
                }
            }
        }

        action.setStartDate(currentTime);
    }

    private static int longToInt(long value) {
        return (int) Math.max(Math.min(Integer.MAX_VALUE, value), Integer.MIN_VALUE);
    }
}