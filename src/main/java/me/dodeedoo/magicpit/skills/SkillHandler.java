package me.dodeedoo.magicpit.skills;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class SkillHandler {

    public static HashMap<Player, HashMap<Skill, String[]>> playerSkills = new HashMap<>();

    public static void invokeSkill(Skill skill, Player player, String[] args) {
        if (!skill.getCooldownMap().containsKey(player)) {
            skill.getCooldownMap().put(player, System.currentTimeMillis());
        }
        long timeleft = System.currentTimeMillis() - skill.getCooldownMap().get(player);
        //Bukkit.broadcastMessage(String.valueOf(timeleft));
        if (TimeUnit.MILLISECONDS.toSeconds(timeleft) >= skill.getCooldown()) {
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
                if (skill.getAction() == SkillExecuteAction.HOLD_RIGHT_CLICK) {
                    if (skill.getHeldTicksMap().containsKey(player)) {
                        skill.getHeldTicksMap().put(player, 5L + skill.getHeldTicksMap().get(player));
                    }else{
                        skill.getHeldTicksMap().put(player, 5L);
                    }
                }
//                HashMap<Player, Long> cdmap = skill.getCooldownMap();
//                cdmap.put(player, TimeUnit.SECONDS.toSeconds(System.currentTimeMillis()));
//                skill.setCooldownMap(cdmap);
                skill.execute(player, args);
            }else{
                String type = "UNKNOWN";
                switch (skill.getCostType()) {
                    case HEALTH: {
                        type = "Health";
                        break;
                    }
                    case MANA: {
                        type = "Mana";
                        break;
                    }
                    case COIN: {
                        type = "Coins";
                        break;
                    }
                }
                if (skill.getAction() != SkillExecuteAction.DAMAGED) {
                    player.sendMessage(Util.colorize("&cCant use this skill! not enough " + type));
                }
            }
        }else{
            if (skill.getAction() != SkillExecuteAction.DAMAGED) {
                player.sendMessage(Util.colorize("&con cooldown! " + (skill.getCooldown() - TimeUnit.MILLISECONDS.toSeconds(timeleft)) + " seconds left"));
            }
        }
    }

    public static void handleAction(SkillExecuteAction action, Player player) {
        for (Skill skill : playerSkills.get(player).keySet()) {
            if (skill.getAction() == action) {
                invokeSkill(skill, player, playerSkills.get(player).get(skill));
            }
        }
    }

    public static HashMap<Player, Long> playerClickDelayMap = new HashMap<>();
    public static HashMap<Player, Long> playerHeldKeyTicks = new HashMap<>();
    public static HashMap<Player, Boolean> playerIsHeld = new HashMap<>();

    public static void handleClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (player.isSneaking()) {
                handleAction(SkillExecuteAction.SNEAK_LEFT_CLICK, player);
                return;
            }
            handleAction(SkillExecuteAction.LEFT_CLICK, player);
            return;
        }
        if (playerClickDelayMap.containsKey(player)) {
            //Bukkit.broadcastMessage(String.valueOf(playerClickDelayMap.get(player)));
            if (playerClickDelayMap.get(player) == 5 || playerClickDelayMap.get(player) == 1) {
                if (!playerIsHeld.containsKey(player)) {
                    playerIsHeld.put(player, false);
                }
                if (playerIsHeld.get(player)) {
                    if (playerHeldKeyTicks.containsKey(player)) {
                        playerHeldKeyTicks.put(player, playerClickDelayMap.get(player) + playerHeldKeyTicks.get(player));
                    }else{
                        playerHeldKeyTicks.put(player, playerClickDelayMap.get(player));
                    }
                    if (player.isSneaking()) {
                        handleAction(SkillExecuteAction.HOLD_SNEAK_RIGHT_CLICK, player);
                        return;
                    }
                    handleAction(SkillExecuteAction.HOLD_RIGHT_CLICK, player);
                    //Bukkit.broadcastMessage("holding " + playerHeldKeyTicks.get(player));
                }else{
                    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        if (player.isSneaking()) {
                            handleAction(SkillExecuteAction.SNEAK_RIGHT_CLICK, player);
                        }else {
                            handleAction(SkillExecuteAction.RIGHT_CLICK, player);
                        }
                    }
                    playerIsHeld.put(player, true);
                }
            }else{
                if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                    if (player.isSneaking()) {
                        handleAction(SkillExecuteAction.SNEAK_LEFT_CLICK, player);
                    }else {
                        handleAction(SkillExecuteAction.LEFT_CLICK, player);
                    }
                }
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (player.isSneaking()) {
                        handleAction(SkillExecuteAction.SNEAK_RIGHT_CLICK, player);
                    }else {
                        handleAction(SkillExecuteAction.RIGHT_CLICK, player);
                    }
                }
                playerIsHeld.put(player, false);
                playerHeldKeyTicks.put(player, 0L);
            }
            playerClickDelayMap.put(player, 1L);
        }else{
            playerClickDelayMap.put(player, 1L);
            new BukkitRunnable() {

                @Override
                public void run() {
                    playerClickDelayMap.put(player, 1 + playerClickDelayMap.get(player));
                    if (playerClickDelayMap.get(player) > 20) {
                        playerClickDelayMap.remove(player);
                        cancel();
                    }
                }
            }.runTaskTimer(MagicPitCore.getInstance(), 1, 1);
        }
        /*
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
                    Bukkit.broadcastMessage(String.valueOf(playerClickDelayMap.get(player)));
                    //Bukkit.broadcastMessage(String.valueOf(playerHeldKeyTicks.get(player)));
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

         */
    }

}
