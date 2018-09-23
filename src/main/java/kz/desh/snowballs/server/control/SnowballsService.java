package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class SnowballsService {
    private static final int TIME_FOR_CREATE_SNOWBALL = 10000;

    public void createSnowballs(PlayerEntity player) {
        val currentTime = LocalDateTime.now();
        val action = player.getActionEntity();
        val storage = player.getStorageEntity();
        val snowballsCount = storage.getSnowballs();
        val storageTypeSize = storage.getType().getSize();

        if (snowballsCount < storageTypeSize) {
            val timePassed = action.getStartDate().until(currentTime, ChronoUnit.MILLIS);
            val needToCreateSnowballs = Math.min((storageTypeSize - snowballsCount), longToInt(timePassed / TIME_FOR_CREATE_SNOWBALL));
            storage.addSnowball(needToCreateSnowballs);
        }

        action.setStartDate(currentTime);
    }

    private static int longToInt(long value) {
        return (int) Math.max(Math.min(Integer.MAX_VALUE, value), Integer.MIN_VALUE);
    }
}