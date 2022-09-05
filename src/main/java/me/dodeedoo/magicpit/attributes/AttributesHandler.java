package me.dodeedoo.magicpit.attributes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class AttributesHandler {

    public static HashMap<String, Attribute> Attributes = new HashMap<>();

    public static void addAttribute(Attribute attribute, String name) { Attributes.put(name, attribute); }

    public static void handlePriorityQueue(HashMap<Attribute, ModifierPriority> priorityHashMap, Player player, String methodname) {
        try {
            for (Attribute attribute : priorityHashMap.keySet()) {
                ModifierPriority priority = priorityHashMap.get(attribute);
                if (priority == ModifierPriority.LOWEST) {
                    attribute.getClass().getMethod(methodname).invoke(player);
                }
            }
            for (Attribute attribute : priorityHashMap.keySet()) {
                ModifierPriority priority = priorityHashMap.get(attribute);
                if (priority == ModifierPriority.LOW) {
                    attribute.getClass().getMethod(methodname).invoke(player);
                }
            }
            for (Attribute attribute : priorityHashMap.keySet()) {
                ModifierPriority priority = priorityHashMap.get(attribute);
                if (priority == ModifierPriority.NORMAL) {
                    attribute.getClass().getMethod(methodname).invoke(player);
                }
            }
            for (Attribute attribute : priorityHashMap.keySet()) {
                ModifierPriority priority = priorityHashMap.get(attribute);
                if (priority == ModifierPriority.HIGH) {
                    attribute.getClass().getMethod(methodname).invoke(player);
                }
            }
            for (Attribute attribute : priorityHashMap.keySet()) {
                ModifierPriority priority = priorityHashMap.get(attribute);
                if (priority == ModifierPriority.HIGHEST) {
                    attribute.getClass().getMethod(methodname).invoke(player);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleSecond(Player player) {
        HashMap<Attribute, ModifierPriority> priorityHashMap = new HashMap<>();
        for (Attribute attribute : Attributes.values()) priorityHashMap.put(attribute, attribute.getPriority());
        handlePriorityQueue(priorityHashMap, player, "secondModifier");
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
            Bukkit.broadcastMessage(attribute.getClass().toString());
            attribute.attackModifier(event);
        }
    }

    public static void handleDamaged(EntityDamageByEntityEvent event) {
        for (Attribute attribute : Attributes.values()) {
            attribute.damagedModifier(event);
        }
    }

}
