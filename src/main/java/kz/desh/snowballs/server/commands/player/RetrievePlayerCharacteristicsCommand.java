package kz.desh.snowballs.server.commands.player;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.item.Items;
import kz.desh.snowballs.server.entity.PlayerEntity;
import kz.desh.snowballs.server.entity.item.ItemEntity;
import kz.desh.snowballs.server.entity.skill.SkillType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class RetrievePlayerCharacteristicsCommand implements Command {
    public static final String COMMAND = "10009";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %d" +      //heat
            " %.2f" +    //dodge
            " %.2f";       //strength

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        log.info("Retrieve player characteristics command from client: {}", command);
        return createResponse(player);
    }

    private String createResponse(PlayerEntity player) {
        val clothes = player.getClothes().stream()
                .map(Items::getItem)
                .collect(Collectors.toSet());
        val clothesHeat = clothes.stream()
                .mapToInt(ItemEntity::getHeat)
                .sum();
        val clothesDodge = clothes.stream()
                .mapToDouble(ItemEntity::getDodge)
                .sum();
        val skillDodgeEntity = player.getSkill(SkillType.DODGE);
        val skillDodge = skillDodgeEntity.getType().getBonus(skillDodgeEntity.getCurrentLevel());
        val skillStrengthEntity = player.getSkill(SkillType.STRENGTH);
        val skillStrength = skillStrengthEntity.getType().getBonus(skillStrengthEntity.getCurrentLevel());
        val heat = player.getHeat() + clothesHeat;
        val dodge = player.getDodge() + clothesDodge + skillDodge;
        return String.format(RESPONSE_COMMAND,
                heat,
                dodge,
                skillStrength);
    }
}