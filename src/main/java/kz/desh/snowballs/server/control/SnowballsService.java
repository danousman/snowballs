package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class SnowballsService {
    public void createSnowballs(PlayerEntity player) {
        val currentTime = LocalDateTime.now();
        val actionEntity = player.getActionEntity();
        val storageEntity = player.getStorageEntity();
        val snowballs = storageEntity.getSnowballs();
        val storageTypeSize = storageEntity.getType().getSize();

        if (snowballs < storageTypeSize &&
                currentTime.isAfter(actionEntity.getEndDate())) {
            storageEntity.addSnowball(1);
            val timePassed = actionEntity.getEndDate().until(currentTime, ChronoUnit.MILLIS);
            val needToCreateSnowballs = Math.min((storageTypeSize - (snowballs + 1)), longToInt(timePassed / GameProperties.timeToCreateSnowball));
            val timeRemainder = GameProperties.timeToCreateSnowball - (timePassed % GameProperties.timeToCreateSnowball);
            storageEntity.addSnowball(needToCreateSnowballs);
            actionEntity.setEndDate(currentTime.plus(timeRemainder, ChronoUnit.MILLIS));
        } else if (snowballs == storageTypeSize) {
            actionEntity.setEndDate(currentTime.plus(GameProperties.timeToCreateSnowball, ChronoUnit.MILLIS));
        }

        actionEntity.setStartDate(currentTime);
    }

    private static int longToInt(long value) {
        return (int) Math.max(Math.min(Integer.MAX_VALUE, value), Integer.MIN_VALUE);
    }
}