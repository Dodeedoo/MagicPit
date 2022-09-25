package me.dodeedoo.magicpit.commands;

import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.items.ItemManager;
import me.dodeedoo.magicpit.items.ItemType;
import me.dodeedoo.magicpit.items.PitItem;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.list.EggBall;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = (Player) commandSender;
        switch (args[0]) {
            case "test": {
                List<Skill> skillList = new ArrayList<>();
                skillList.add(new EggBall());
                HashMap<Attribute, Object> map = new HashMap<>();
                map.put(AttributesHandler.Attributes.get("Strength"), 5);
                player.getInventory().addItem(PitItem.itemStackFromItem(
                        new PitItem(new ArrayList<>(), player, Material.DIAMOND, "test", map, skillList, 0, ItemType.BASIC)
                ));
                player.getInventory().addItem(PitItem.itemStackFromItem(
                        new PitItem(new ArrayList<>(), player, Material.IRON_CHESTPLATE, "test", map, skillList, 0, ItemType.BASIC)
                ));
                return true;
            }
            case "create": {
                /*
                arg 1 - material
                arg 2 - name
                arg 3 - lore (1 line for now)
                arg 4 - whether to register or not
                arg 5 - register key
                 */
                List<String> lore = new ArrayList<>();
                lore.add(args[3].replace("_", " "));
                PitItem item = new PitItem(lore, player, Material.valueOf(args[1]), args[2].replace("_", " "), new HashMap<>(), new ArrayList<>(), 3, ItemType.BASIC);
                player.getInventory().addItem(PitItem.itemStackFromItem(item));
                if (Boolean.parseBoolean(args[4])) {
                    ItemManager.registerItem(PitItem.itemStackFromItem(item), args[5]);
                }
                return true;
            }
            case "addstat": {
                /*
                arg 1 - key
                arg 2 - attribute class name
                arg 3 - value (remove if 0)
                 */
                ItemStack item = ItemManager.itemList.get(args[1]);
                PitItem pitItem = PitItem.itemFromItemStack(item, player);
                if (Integer.parseInt(args[3]) != 0) {
                    pitItem.attributeMap.put(AttributesHandler.Attributes.get(args[2]), Integer.valueOf(args[3]));
                }else{
                    pitItem.attributeMap.remove(AttributesHandler.Attributes.get(args[2]));
                }
                ItemManager.registerItem(PitItem.itemStackFromItem(pitItem), args[1]);
                return true;
            }
            case "modability": {
                /*
                arg 1 - key
                arg 2 - ability class name
                arg 3 - true/add false/remove
                 */
                ItemStack item = ItemManager.itemList.get(args[1]);
                PitItem pitItem = PitItem.itemFromItemStack(item, player);
                if (Boolean.parseBoolean(args[3])) {
                    try {
                        pitItem.abilities.add((Skill) Class.forName("me.dodeedoo.magicpit.skills.list." + args[2]).newInstance());
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    pitItem.abilities.removeIf(skill -> skill.getClass().getSimpleName().equals(args[2]));
                }
                ItemManager.registerItem(PitItem.itemStackFromItem(pitItem), args[1]);
                return true;
            }
            case "unregister": {
                ItemManager.unregisterItem(args[1]);
                player.sendMessage(Util.colorize("&c" + args[1] + " removed from file"));
                return true;
            }
            case "get": {
                player.getInventory().addItem(ItemManager.itemList.get(args[1]));
                return true;
            }
            case "listkeys": {
                player.sendMessage(Arrays.toString(ItemManager.getKeys().toArray()));
                return true;
            }
            case "setlevel": {
                ItemStack item = ItemManager.itemList.get(args[1]);
                PitItem pitItem = PitItem.itemFromItemStack(item, player);

                pitItem.setLevelreq(Integer.valueOf(args[2]));

                ItemManager.registerItem(PitItem.itemStackFromItem(pitItem), args[1]);
                return true;
            }

        }
        return false;
    }
}
