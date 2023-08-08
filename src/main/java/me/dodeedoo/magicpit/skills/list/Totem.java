package me.dodeedoo.magicpit.skills.list;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillCost;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillIndicator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Totem implements Skill {

    public static HashMap<Player, Long> cooldownmap = new HashMap<>();

    @Override
    public void execute(Player player, String[] args) {
        Location playerLoc = player.getLocation();

        Location armorStandLoc = playerLoc.add(playerLoc.getDirection().clone().multiply(-1).multiply(2));

        ArmorStand centerStand = (ArmorStand) playerLoc.getWorld().spawnEntity(new Location(playerLoc.getWorld(), 0, 0, 0), EntityType.ARMOR_STAND);
        centerStand.setVisible(false);
        centerStand.setGravity(true);
        centerStand.setMarker(true);
        centerStand.setItem(EquipmentSlot.HEAD, new ItemStack(Material.GREEN_WOOL));
        centerStand.teleport(armorStandLoc);

        List<Double> offsetList = new ArrayList<>();
        offsetList.add(0.5);
        offsetList.add(-0.5);
        offsetList.add(1.5);
        offsetList.add(-1.5);

        for (int o = 1; o < 2; o++) {

            for (double i : offsetList) {
                ArmorStand armorStand = (ArmorStand) playerLoc.getWorld().spawnEntity(new Location(playerLoc.getWorld(), 0, 0, 0), EntityType.ARMOR_STAND);
                armorStand.setVisible(false);
                armorStand.setGravity(true);
                armorStand.setMarker(true);
                centerStand.setItem(EquipmentSlot.HEAD, new ItemStack(Material.OAK_WOOD));

                Location tempArmorStandLoc = armorStandLoc.clone().add(0, -0.6 * o, 0);

                if (i > 1) {
                    i = i - 1;
                    tempArmorStandLoc = tempArmorStandLoc.add(i, 0, 0);
                } else {
                    tempArmorStandLoc = tempArmorStandLoc.add(0, 0, i);
                }

                armorStand.teleport(tempArmorStandLoc);

                Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), armorStand::remove, 40);
            }
        }

        Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), centerStand::remove, 40);
    }

    @Override
    public List<String> getLore() {
        List<String> lore = new ArrayList<>();
        lore.add("dldfga");
        return lore;
    }

    @Override
    public SkillExecuteAction getAction() {
        return SkillExecuteAction.RIGHT_CLICK;
    }

    @Override
    public SkillCost getCostType() {
        return SkillCost.MANA;
    }

    @Override
    public Integer getCostAmount() {
        return 50;
    }

    @Override
    public Long getCooldown() {
        return 5L;
    }

    @Override
    public SkillIndicator getIndicator() {
        return new SkillIndicator(SkillIndicator.indicatorType.MESSAGE, "&aTotem", 0);
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
