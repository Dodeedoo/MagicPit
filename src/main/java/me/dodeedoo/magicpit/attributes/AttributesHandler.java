package me.dodeedoo.magicpit.attributes;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class AttributesHandler {

    public static HashMap<String, Attribute> Attributes = new HashMap<>();

    public static void addAttribute(Attribute attribute, String name) { Attributes.put(name, attribute); }

    public static void handleSecond(Player player) {
        for (Attribute attribute : Attributes.values()) {
            attribute.secondModifier(player);
        }
    }

    public static void handleThreeSecond(Player player) {
        for (Attribute attribute : Attributes.values()) {
            attribute.threeSecondModifier(player);
        }
    }

    public static void handleKill(EntityDeathEvent event) {
        for (Attribute attribute : Attributes.values()) {
            attribute.killModifier(event);
        }
    }

    public static void handleDeath(EntityDeathEvent event) {
        for (Attribute attribute : Attributes.values()) {
            attribute.deathModifier(event);
        }
    }

    public static void handleHit(EntityDamageByEntityEvent event) {
        for (Attribute attribute : Attributes.values()) {
            attribute.attackModifier(event);
        }
    }

    public static void handleDamaged(EntityDamageByEntityEvent event) {
        for (Attribute attribute : Attributes.values()) {
            attribute.damagedModifier(event);
        }
    }

}
