package me.dodeedoo.magicpit.skills.list;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Adrenaline implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        if (player.getHealth() <= player.getMaxHealth() * 0.3) {
            PacketContainer addHealth = MagicPitCore.getProtocolManager().createPacket(PacketType.Play.Server.UPDATE_HEALTH);
            addHealth.getIntegers().write(0, (int) (player.getMaxHealth() * 0.1));
            MagicPitCore.getProtocolManager().sendServerPacket(player, addHealth);

            Attribute attribute = AttributesHandler.Attributes.get("MagicDefense");
            attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) + 30);
            attribute = AttributesHandler.Attributes.get("Defense");
            attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) + 150);
            attribute = AttributesHandler.Attributes.get("Swiftness");
            attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) + 15);
            new BukkitRunnable() {
                @Override
                public void run() {
                    Attribute attribute = AttributesHandler.Attributes.get("MagicDefense");
                    attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) - 50);
                    attribute = AttributesHandler.Attributes.get("Defense");
                    attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) - 150);
                    attribute = AttributesHandler.Attributes.get("Swiftness");
                    attribute.getPlayerStats().put(player, ((int) attribute.getPlayer(player)) - 15);
                }
            }.runTaskLater(MagicPitCore.getInstance(), 100);
        }
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("&7Adrenaline rush, activates when");
        lore.add("&7under 30% total health");
        lore.add("&7heals 10% of max health");
        lore.add("&7+50 Magical Defense for 5 seconds");
        lore.add("&7+150 Defense for 5 seconds");
        lore.add("&7+15 Swiftness for 5 seconds");
        lore.add("&7Cooldown: 30 seconds");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.DAMAGED;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.HEALTH;
    }

    @Override
    public Integer getCostAmount() {
        return -50;
    }

    @Override
    public Long getCooldown() {
        return 30L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&aAdrenaline", 0);
    }

    @Override
    public HashMap<Player, Long> getCooldownMap() {
        return cooldownmap;
    }

    @Override
    public void setCooldownMap(HashMap<Player, Long> map) {
        cooldownmap = map;
    }

    @Override
    public Boolean overTime() {
        return null;
    }

    @Override
    public HashMap<Player, Long> getHeldTicksMap() {
        return null;
    }

    @Override
    public void initiateCooldown(Player player) {
        HashMap<Player, Long> cdmap = getCooldownMap();
        cdmap.put(player, TimeUnit.SECONDS.toSeconds(System.currentTimeMillis()));
        setCooldownMap(cdmap);
    }
}
