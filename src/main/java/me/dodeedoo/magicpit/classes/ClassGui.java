package me.dodeedoo.magicpit.classes;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.dodeedoo.magicpit.GuiUtil;
import me.dodeedoo.magicpit.PitPlayer;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.classes.list.ExampleClass2;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassGui {

    public static void showMainGui(Player player) {
        ChestGui gui = new ChestGui(5, "Player Classes");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.addPane(GuiUtil.getGuiFill(Material.GRAY_STAINED_GLASS_PANE, 5, 9));

        Integer x = -2;
        Integer y = 1;

        for (PitClass pitClass : PitClassHandler.classList) {
            if (!pitClass.isTestClass()) {
                if (x >= 8 || y >= 8) {
                    x = 0;
                    y = y + 2;
                } else {
                    x = x + 2;
                }
                if (PitPlayer.playerMap.get(player).playerClass.getClass().equals(pitClass.getClass())) {
                    gui.addPane(getClassPane(pitClass, x, y, player, true));
                }else{
                    gui.addPane(getClassPane(pitClass, x, y, player, false));
                }
            }
        }

        gui.show(player);

    }

    public static Pane getClassPane(PitClass pitClass, Integer x, Integer y, Player player, Boolean isSelected) {
        StaticPane pane = new StaticPane(x, y, 1, 1, Pane.Priority.HIGHEST);

        ItemStack it = new ItemStack(pitClass.getGuiMaterial());
        ItemMeta meta = it.getItemMeta();
        List<String> lore = new ArrayList<>();
        for (String line : pitClass.getGuiLore()) {
            lore.add(Util.colorize(line));
        }
        lore.add(" ");
        lore.add(Util.colorize("&7Left Click to select"));
        lore.add(Util.colorize("&7Right Click to view tree"));
        meta.setLore(lore);
        meta.setDisplayName(Util.colorize(pitClass.getFancyName()));
        if (isSelected) {
            meta.setDisplayName(Util.colorize(pitClass.getFancyName() + " &a&o(Selected)"));
            meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        it.setItemMeta(meta);

        pane.setOnClick(event -> {
            if (!isSelected) {
                if (event.isLeftClick()) {
                    //select
                    PitClassHandler.select(player, pitClass);

                    //enchant glow
                    showMainGui(player);
                }
            }
            if (event.isRightClick()) {
                if (PitPlayer.playerMap.get(player).playerClass == pitClass) {
                    showClassTreeGui(player, pitClass);
                }
            }
        });

        pane.addItem(new GuiItem(it), 0, 0);
        pane.setVisible(true);
        return pane;
    }


    public static void showClassTreeGui(Player player, PitClass pitClass) {
        ChestGui gui = new ChestGui(6, "Class tree");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.addPane(GuiUtil.getGuiFill(Material.GRAY_STAINED_GLASS_PANE, 6, 9));

        Integer startingX = 4;
        Integer startingY = 0;

        pitClass.refreshNodeMap();
        for (PitClassProperty property : pitClass.getNodeMap().keySet()) {
            List<Integer> coords = pitClass.getNodeMap().get(property);
            if (coords.isEmpty()) {
               gui.addPane(getNodePane(pitClass, property, player, startingX, startingY));
            }else{
                Integer x = startingX;
                Integer y = startingY;
                for (int index = 0; index < coords.size(); index++) {
                    Integer coordinate = coords.get(index);
                    y = y + 1;
                    if (coordinate == 0) {
                        x = x + 1;
                    }else if (coordinate == 1) {
                        x = x - 1;
                    }
                }
                gui.addPane(getNodePane(pitClass, property, player, x, y));
            }
        }
        gui.addPane(getResetPane(player, pitClass, 0, 0));

        gui.show(player);
    }

    public static Pane getNodePane(PitClass pitClass, PitClassProperty property, Player player, Integer x, Integer y) {
        pitClass.refreshNodeMap();
        StaticPane pane = new StaticPane(x, y, 1, 1, Pane.Priority.HIGHEST);

        Boolean isActivated = pitClass.getDataMap().get(player).tree.isActivated(pitClass.getNodeMap().get(property));
//        Bukkit.broadcast(Component.text(isActivated.toString() + " " + property.name));
        Boolean beforeIsActivated = true;
        pitClass.refreshNodeMap();
        try {
            if (!Objects.equals(pitClass.getDataMap().get(player).tree.baseNode.name, property.name)) {
                List<Integer> tempList = pitClass.getNodeMap().get(property);
                tempList.remove(tempList.size() - 1);
                pitClass.refreshNodeMap();
                beforeIsActivated = pitClass.getDataMap().get(player).tree.isActivated(tempList);
            }else{
                beforeIsActivated = true;
            }
        }catch (Exception ignored) {}
        pitClass.refreshNodeMap();

        ItemStack it = new ItemStack(property.guiMaterial);
        if (property.attribute != null) {
            if (isActivated) {
                it = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
            }else{
                it = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            }
        }
        ItemMeta meta = it.getItemMeta();
        List<String> lore = new ArrayList<>();
        for (String line : property.getLore()) {
            lore.add(Util.colorize(line));
        }
        meta.setDisplayName(Util.colorize(property.name));
        //Bukkit.broadcastMessage(Util.colorize(property.name) + " " + x + " " + y);
        if (property.type == PropertyType.ATTRIBUTE) {
            lore.add(" ");
            if ((int) property.amount < 0) {
                lore.add(Util.colorize("&7" + property.attribute.getClass().getSimpleName() + " &c" + property.amount));
            }else {
                lore.add(Util.colorize("&7" + property.attribute.getClass().getSimpleName() + " &a+" + property.amount));
            }
        }else{
            lore.add(" ");
            lore.add(Util.colorize(property.stringApplyType()));
            for (String line : property.getSkillLore()) {
                lore.add(Util.colorize(line));
            }
        }
        if (!isActivated) {
            lore.add(" ");
            if (beforeIsActivated) {
                lore.add(Util.colorize("&7Click to activate (1 pt)"));
                lore.add(Util.colorize("&8&o(You have " + (pitClass.getDataMap().get(player).totalPoints - pitClass.getDataMap().get(player).assignedPoints) + " points)"));
            }else{
                lore.add(Util.colorize("&cPrevious node required to activate!"));
            }
        }else{
            meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.setLore(lore);
        it.setItemMeta(meta);

        Boolean finalBeforeIsActivated = beforeIsActivated;
        pane.setOnClick(event -> {
            if (!isActivated) {
                if (finalBeforeIsActivated) {
                    pitClass.refreshNodeMap();
                    if (pitClass.getDataMap().get(player).assignedPoints < pitClass.getDataMap().get(player).totalPoints) {
                        pitClass.getDataMap().get(player).setAssignedPoints(pitClass.getDataMap().get(player).assignedPoints + 1);
                        pitClass.refreshNodeMap();
                        pitClass.getDataMap().get(player).tree.activate(pitClass.getNodeMap().get(property));
                        pitClass.refreshNodeMap();
                        property.apply(player);
                        showClassTreeGui(player, pitClass);

                        //workaround
                        pitClass.refreshNodeMap();
                        for (PitClassProperty property1 : pitClass.getNodeMap().keySet()) {
                            if (!(property1 == property)) {
                                pitClass.refreshNodeMap();
                                List<Integer> tempList = pitClass.getNodeMap().get(property1);
                                List<Integer> propertyKey = pitClass.getNodeMap().get(property);
//                                Bukkit.broadcast(Component.text(tempList.subList(0, propertyKey.size()).toString()));
//                                Bukkit.broadcast(Component.text(propertyKey.toString()));
                                if (tempList.size() > propertyKey.size() && tempList.subList(0, propertyKey.size()) == propertyKey) {
                                    Bukkit.broadcast(Component.text(property1.name + " being deactivated"));
                                    pitClass.getDataMap().get(player).tree.indexTo(pitClass.getNodeMap().get(property1)).setActivated(false);
                                }
                            }

                        }
                    }else{
                        player.sendMessage(Util.colorize("&cNot enough points!"));
                    }
                }
            }
        });

        pane.addItem(new GuiItem(it), 0, 0);
        return pane;
    }

    public static Pane getResetPane(Player player, PitClass pitClass, Integer x, Integer y) {
        StaticPane pane = new StaticPane(x, y, 1, 1, Pane.Priority.HIGHEST);

        pane.setOnClick(event -> {
            pitClass.refreshNodeMap();
            for (PitClassProperty pitClassProperty : pitClass.getNodeMap().keySet()) {
                //check if active
                if (pitClass.getDataMap().get(player).tree.isActivated(pitClass.getNodeMap().get(pitClassProperty))) {
                    pitClass.getDataMap().get(player).tree.indexTo(pitClass.getNodeMap().get(pitClassProperty)).setActivated(false);
                    pitClassProperty.remove(player);
                }
            }
            pitClass.refreshNodeMap();
            pitClass.getDataMap().put(player, new PitClassData(pitClass.getNodeMap(),
                    pitClass.getDataMap().get(player).exp, pitClass.getDataMap().get(player).level));
            player.sendMessage(Util.colorize("&cReset!"));
            player.closeInventory();
        });

        ItemStack it = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = it.getItemMeta();
        meta.setLore(Util.returnStringList(Util.colorize("&7Reset all nodes and get points back"), Util.colorize("&8Click to reset")));
        meta.setDisplayName(Util.colorize("&cReset Tree"));
        it.setItemMeta(meta);

        pane.addItem(new GuiItem(it), 0, 0);
        return pane;
    }


}
