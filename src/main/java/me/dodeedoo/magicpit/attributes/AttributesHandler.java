package me.dodeedoo.magicpit.attributes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;

public class AttributesHandler {

    public static HashMap<String, Attribute> Attributes = new HashMap<>();

    public static void addAttribute(Attribute attribute, String name) { Attributes.put(name, attribute); }

    public static void handlePriorityQueue(HashMap<Attribute, ModifierPriority> priorityHashMap, Object argument, String methodname, Class arg) {
        try {
            for (Attribute attribute : priorityHashMap.keySet()) {
                ModifierPriority priority = priorityHashMap.get(attribute);
                if (priority == ModifierPriority.LOWEST) {
                    //Bukkit.broadcastMessage(attribute.getClass().getMethods()[0].getName());
                    attribute.getClass().getMethod(methodname, arg).invoke(attribute, argument);
                }
            }
            for (Attribute attribute : priorityHashMap.keySet()) {
                ModifierPriority priority = priorityHashMap.get(attribute);
                if (priority == ModifierPriority.LOW) {
                    attribute.getClass().getMethod(methodname, arg).invoke(attribute, argument);
                }
            }
            for (Attribute attribute : priorityHashMap.keySet()) {
                ModifierPriority priority = priorityHashMap.get(attribute);
                if (priority == ModifierPriority.NORMAL) {
                    attribute.getClass().getMethod(methodname, arg).invoke(attribute, argument);
                }
            }
            for (Attribute attribute : priorityHashMap.keySet()) {
                ModifierPriority priority = priorityHashMap.get(attribute);
                if (priority == ModifierPriority.HIGH) {
                    attribute.getClass().getMethod(methodname, arg).invoke(attribute, argument);
                }
            }
            for (Attribute attribute : priorityHashMap.keySet()) {
                ModifierPriority priority = priorityHashMap.get(attribute);
                if (priority == ModifierPriority.HIGHEST) {
                    attribute.getClass().getMethod(methodname, arg).invoke(attribute, argument);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handleSecond(Player player) {
        HashMap<Attribute, ModifierPriority> priorityHashMap = new HashMap<>();
        for (Attribute attribute : Attributes.values()) priorityHashMap.put(attribute, attribute.getPriority());
        handlePriorityQueue(priorityHashMap, player, "secondModifier", Player.class);
    }

    public static void handleThreeSecond(Player player) {
        HashMap<Attribute, ModifierPriority> priorityHashMap = new HashMap<>();
        for (Attribute attribute : Attributes.values()) priorityHashMap.put(attribute, attribute.getPriority());
        handlePriorityQueue(priorityHashMap, player, "threeSecondModifier", Player.class);
    }

    public static void handleKill(EntityDeathEvent event) {
        HashMap<Attribute, ModifierPriority> priorityHashMap = new HashMap<>();
        for (Attribute attribute : Attributes.values()) priorityHashMap.put(attribute, attribute.getPriority());
        handlePriorityQueue(priorityHashMap, event, "killModifier", event.getClass());
    }

    public static void handleDeath(EntityDeathEvent event) {
        HashMap<Attribute, ModifierPriority> priorityHashMap = new HashMap<>();
        for (Attribute attribute : Attributes.values()) priorityHashMap.put(attribute, attribute.getPriority());
        handlePriorityQueue(priorityHashMap, event, "deathModifier", event.getClass());
    }

    public static void handleHit(EntityDamageByEntityEvent event) {
        HashMap<Attribute, ModifierPriority> priorityHashMap = new HashMap<>();
        for (Attribute attribute : Attributes.values()) priorityHashMap.put(attribute, attribute.getPriority());
        handlePriorityQueue(priorityHashMap, event, "attackModifier", event.getClass());
    }

    public static void handleDamaged(EntityDamageByEntityEvent event) {
        HashMap<Attribute, ModifierPriority> priorityHashMap = new HashMap<>();
        for (Attribute attribute : Attributes.values()) priorityHashMap.put(attribute, attribute.getPriority());
        handlePriorityQueue(priorityHashMap, event, "damagedModifier", event.getClass());
    }

}
