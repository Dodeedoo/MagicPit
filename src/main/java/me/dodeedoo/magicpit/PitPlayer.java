package me.dodeedoo.magicpit;

import me.dodeedoo.magicpit.classes.PitClass;
import me.dodeedoo.magicpit.items.PitItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;

public class PitPlayer {

    public static HashMap<Player, PitPlayer> playerMap = new HashMap<>();

    public static void handlePlayerLoad(Player player) {
        playerMap.put(player, new PitPlayer(0, ConfigUtil.getPlayerConfig(player).getInt("level"), 0,
                ConfigUtil.getPlayerConfig(player).getInt("exp"), new HashMap<>(), ConfigUtil.getPlayerConfig(player).getBoolean("protected")));
    }

    public static void handlePlayerUnload(Player player) {
        FileConfiguration conf = ConfigUtil.getPlayerConfig(player);
        conf.set("level", playerMap.get(player).level);
        conf.set("exp", playerMap.get(player).exp);
        conf.set("protected", playerMap.get(player).powerlevelfight);
        try {
            conf.save(ConfigUtil.buildSaveFile(player));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer balance;
    public Integer level;
    public Integer powerlevel;
    public Integer exp;
    public Boolean powerlevelfight;
    public HashMap<EquipmentSlot, PitItem> equipped = new HashMap<>();
    public PitClass playerClass;

    public void setPlayerClass(PitClass playerClass) {
        this.playerClass = playerClass;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setEquipped(HashMap<EquipmentSlot, PitItem> equipped) {
        this.equipped = equipped;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setPowerlevel(Integer powerlevel) {
        this.powerlevel = powerlevel;
    }

    public void setPowerlevelfight(Boolean powerlevelfight) {
        this.powerlevelfight = powerlevelfight;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
        if (this.exp >= (10 * (Math.pow(this.level, 3)))) {
            this.exp = 0;
            this.level += 1;
        }
    }

    public PitPlayer(Integer balance, Integer level, Integer powerlevel, Integer exp, HashMap<EquipmentSlot, PitItem> equipped, Boolean powerlevelfight) {
        this.balance = balance;
        this.level = level;
        this.exp = exp;
        this.powerlevel = powerlevel;
        this.equipped = equipped;
        this.powerlevelfight = powerlevelfight;
    }
}
