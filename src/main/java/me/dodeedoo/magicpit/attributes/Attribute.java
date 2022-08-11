package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;

import java.util.HashMap;

public interface Attribute {
    HashMap<Player, Object> getPlayerStat();

    void setName();

}
