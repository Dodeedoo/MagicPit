package me.dodeedoo.magicpit;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiUtil {
    public static OutlinePane getGuiFill(Material material, Integer len, Integer wid) {
        OutlinePane pane = new OutlinePane(0, 0, wid, len, Pane.Priority.LOWEST);
        ItemMeta meta = new ItemStack(material).getItemMeta();
        ItemStack it = new ItemStack(material);
        meta.setDisplayName(Util.colorize("&e"));
        it.setItemMeta(meta);
        pane.addItem(new GuiItem(it));
        pane.setRepeat(true);
        return pane;
    }
}
