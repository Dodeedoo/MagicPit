package me.dodeedoo.magicpit.classes;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.dodeedoo.magicpit.GuiUtil;
import me.dodeedoo.magicpit.PitPlayer;
import me.dodeedoo.magicpit.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

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
        List<String> lore = pitClass.getGuiLore();
        lore.add(" ");
        lore.add(Util.colorize("&7Left Click to select"));
        lore.add(Util.colorize("&7Right Click to view tree"));
        meta.setLore(lore);
        meta.setDisplayName(Util.colorize(pitClass.getFancyName()));
        if (isSelected) {
            meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        it.setItemMeta(meta);

        pane.setOnClick(event -> {
            if (!isSelected) {
                if (event.isLeftClick()) {

                    //enchant glow
                    showMainGui(player);

                    //select
                    PitClassHandler.select(player, pitClass);
                }
            }
            if (event.isRightClick()) {

            }
        });

        pane.addItem(new GuiItem(it), 0, 0);
        pane.setVisible(true);
        return pane;
    }


}
