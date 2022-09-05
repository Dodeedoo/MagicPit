package me.dodeedoo.magicpit.events.magicdamage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MagicDamage extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    public Player attacker;
    public LivingEntity victim;
    public MagicDamageType type;
    public Integer value;

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public MagicDamage(Player attacker, LivingEntity victim, MagicDamageType type, Integer value) {
        this.attacker = attacker;
        this.victim = victim;
        this.type = type;
        this.value = value;
    }

}
