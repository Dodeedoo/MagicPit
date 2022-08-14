package me.dodeedoo.magicpit.actionbar;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.component.ToggleButton;
import me.dodeedoo.magicpit.GuiUtil;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayGui {

    public static void showDisplayGuiToPlayer(Player player) {
        ChestGui gui = new ChestGui(5, "Attributes Display Settings");
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.addPane(GuiUtil.getGuiFill(Material.GRAY_STAINED_GLASS_PANE, 5, 9));

        Integer x = -2;
        Integer y = 1;
        for (Attribute attribute : AttributesHandler.Attributes.values()) {
            String attrname = " ";
            switch (attribute.getClass().getName().replace("me.dodeedoo.magicpit.attributes.", "")) {
                case "Strength": {
                    attrname = "&4❀Strength ";
                    break;
                }
                case "Weight": {
                    attrname = "&8●Weight ";
                    break;
                }
                case "Regeneration": {
                    attrname = "&d✿Regeneration ";
                    break;
                }
                case "Maxmana": {
                    attrname = "&b✿Mana ";
                    break;
                }
                case "Knowledge": {
                    attrname = "&3❂Knowledge ";
                    break;
                }
                case "Health": {
                    attrname = "&c❤Health ";
                    break;
                }
                case "Defense": {
                    attrname = "&2⛨Defense ";
                    break;
                }
                case "CritChance": {
                    attrname = "&9✯Crit Chance ";
                    break;
                }
                case "Crit": {
                    attrname = "&1♢Crit ";
                    break;
                }
            }

            if (!attribute.getClass().getName().replace("me.dodeedoo.magicpit.attributes.", "").equals("Mana")) {
                if (x >= 8 || y >= 8) {
                    x = 0;
                    y = y + 2;
                } else {
                    x = x + 2;
                }

                gui.addPane(getAttrToggle(attribute, player, Util.colorize(attrname), x, y));
            }
        }

        gui.show(player);
    }

    public static Pane getAttrToggle(Attribute attribute, Player player, String name, Integer x, Integer y) {
        ToggleButton testpane2 = new ToggleButton(x, y, 1, 1);

        ItemStack yes = new ItemStack(Material.LIME_WOOL);
        ItemMeta meta = yes.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Util.colorize("&8Click to toggle"));
        lore.add(Util.colorize("&aEnabled"));
        meta.setDisplayName(name);
        meta.setLore(lore);
        yes.setItemMeta(meta);

        ItemStack no = new ItemStack(Material.RED_WOOL);
        meta = no.getItemMeta();
        lore = new ArrayList<>();
        lore.add(Util.colorize("&8Click to toggle"));
        lore.add(Util.colorize("&cDisabled"));
        meta.setDisplayName(name);
        meta.setLore(lore);
        no.setItemMeta(meta);

        String name2 = attribute.getClass().getName().replace("me.dodeedoo.magicpit.attributes.", "");
        List<Object> obj = AttributeDisplay.preferences.get(player);

        testpane2.setEnabledItem(new GuiItem(yes, event -> {
            obj.remove(name2);
            AttributeDisplay.preferences.put(player, obj);
        }));
        testpane2.setDisabledItem(new GuiItem(no, event -> {
            obj.add(name2);
            AttributeDisplay.preferences.put(player, obj);
        }));

        if (AttributeDisplay.preferences.get(player).contains(attribute.getClass().getName().replace("me.dodeedoo.magicpit.attributes.", ""))) {
            testpane2.toggle();
        }

        return testpane2;
    }
}
