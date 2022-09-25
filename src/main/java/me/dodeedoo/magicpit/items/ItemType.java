package me.dodeedoo.magicpit.items;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public enum ItemType {
    BASIC,
    AXE
    ;

    //supported events damaged, hit, death, kill

    public void invoke(Player player, Event event) {
        switch (this) {
            case BASIC: {
                break;
            }
        }
    }
}
