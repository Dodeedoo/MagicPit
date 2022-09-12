package me.dodeedoo.magicpit.skills;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SkillHandler {

    public static HashMap<Player, HashMap<Skill, String[]>> playerSkills = new HashMap<>();

    public static void invokeSkill(Skill skill, Player player, String[] args) {
        if (!skill.getCooldownMap().containsKey(player)) {
            skill.getCooldownMap().put(player, TimeUnit.SECONDS.toSeconds(System.currentTimeMillis()));
        }
        long timeleft = TimeUnit.SECONDS.toSeconds(System.currentTimeMillis()) - skill.getCooldownMap().get(player);
        Bukkit.broadcastMessage(String.valueOf(timeleft));
        if (timeleft >= skill.getCooldown()) {
            Boolean afford = false;
            switch (skill.getCostType()) {
                case COIN: {
                    afford = true;
                    break;
                }
                case MANA: {
                    if ((int) AttributesHandler.Attributes.get("Mana").getPlayer(player) >= skill.getCostAmount()) {
                        AttributesHandler.Attributes.get("Mana").getPlayerStats().put(player,
                                (int) AttributesHandler.Attributes.get("Mana").getPlayer(player) - skill.getCostAmount());
                        afford = true;
                    }
                    break;
                }
                case HEALTH: {
                    if (player.getHealth() >= skill.getCostAmount()) {
                        player.setHealth(player.getHealth() - skill.getCostAmount());
                        afford = true;
                    }
                    break;
                }
            }
            if (afford) {
                HashMap<Player, Long> cdmap = skill.getCooldownMap();
                cdmap.put(player, TimeUnit.SECONDS.toSeconds(System.currentTimeMillis()));
                skill.setCooldownMap(cdmap);
                skill.execute(player, args);
            }else{
                player.sendMessage(Util.colorize("&cCant use this skill! no Mana/Health/Coins!"));
            }
        }else{
            player.sendMessage(Util.colorize("&con cooldown! " + timeleft + " seconds left"));
        }
    }

    public static void handleAction(SkillExecuteAction action, Player player) {
        for (Skill skill : playerSkills.get(player).keySet()) {
            if (skill.getAction() == action) {
                invokeSkill(skill, player, playerSkills.get(player).get(skill));
            }
        }
    }

    public static HashMap<Player, Integer> playerClickDelayMap = new HashMap<>();
    public static HashMap<Player, Integer> playerHeldKeyTicks = new HashMap<>();

    public static void handleClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerClickDelayMap.containsKey(player)) {
            playerClickDelayMap.put(player, 1);
            Bukkit.broadcastMessage("holding....");
            if (playerHeldKeyTicks.containsKey(player))
            if (playerHeldKeyTicks.get(player) > 10) {
                handleAction(SkillExecuteAction.HOLD_RIGHT_CLICK, player);
            }
        }else{
            playerClickDelayMap.put(player, 1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    playerClickDelayMap.put(player, 1 + playerClickDelayMap.get(player));
                    if (playerHeldKeyTicks.containsKey(player)) {
                        playerHeldKeyTicks.put(player, 2 + playerHeldKeyTicks.get(player));
                    }else{
                        playerHeldKeyTicks.put(player, 2);
                    }
                    if (playerClickDelayMap.get(player) > 3) {
                        if (playerHeldKeyTicks.get(player) <= 7) {
                            Bukkit.broadcastMessage("Registered Normal Click");
                            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                                handleAction(SkillExecuteAction.LEFT_CLICK, player);
                            }
                            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                handleAction(SkillExecuteAction.RIGHT_CLICK, player);
                            }
                        }else{
                            Bukkit.broadcastMessage("stopped holding held for " + playerHeldKeyTicks.get(player) + " ticks");
                        }
                        playerClickDelayMap.remove(player);
                        playerHeldKeyTicks.remove(player);
                        cancel();
                    }
                }
            }.runTaskTimer(MagicPitCore.getInstance(), 2, 2);
        }
    }

}
