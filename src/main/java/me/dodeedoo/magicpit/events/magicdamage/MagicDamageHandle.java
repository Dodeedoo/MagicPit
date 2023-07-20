package me.dodeedoo.magicpit.events.magicdamage;

import com.destroystokyo.paper.ParticleBuilder;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.social.party.PartySystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;
import particles.LocationLib;

import java.util.HashMap;

public class MagicDamageHandle implements Listener {

    public static HashMap<LivingEntity, EntityDamageByEntityEvent> lastMagicDamage = new HashMap<>();

    @EventHandler
    public void magicdamage(MagicDamage event) {
        lastMagicDamage.remove(event.victim);

        //check same party
        if (event.victim instanceof Player && PartySystem.sameParty((Player) event.victim, event.attacker)) {
            return;
        }

        event.value *= ((int) AttributesHandler.Attributes.get("Knowledge").getPlayer(event.attacker) / 5);
        if (event.victim instanceof Player) {
            event.value /= ((int) AttributesHandler.Attributes.get("MagicDefense").getPlayer((Player) event.victim) / 5);
        }

        EntityDamageByEntityEvent damageEvent = new EntityDamageByEntityEvent(event.attacker, event.victim, EntityDamageEvent.DamageCause.CUSTOM, event.value);
        event.victim.setLastDamageCause(damageEvent);
        Bukkit.getServer().getPluginManager().callEvent(damageEvent);
        lastMagicDamage.put(event.victim, damageEvent);
        event.victim.damage(event.value);

        if (LocationLib.isInSpawn(event.attacker)) {
            return;
        }
        if (event.victim instanceof Player) {
            if (LocationLib.isInSpawn((Player) event.victim)) {
                return;
            }
        }
        if (event.victim.isDead()) return;
        switch (event.type) {
            case ICE: {
                int amp;
                int dur;
                if (event.value > 300) {
                    amp = 3;
                    dur = 30;
                }else{
                    amp = event.value / 100;
                    dur = event.value / 10;
                }
                event.victim.addPotionEffect(PotionEffectType.SLOW.createEffect(dur, amp));
                return;
            }
            case FIRE: {
                Location location = event.victim.getLocation();
                for (Location loc : LocationLib.getHelix(new Location[]{location}, 1, 2.5, 1, 5)) {
                    if (Math.random() < 0.2) new ParticleBuilder(Particle.FLAME).location(loc).spawn();
                }
                if (!(event.victim instanceof Player)) {
                    return;
                }
                double tmp = ((int)AttributesHandler.Attributes.get("Scorch").getPlayer((Player) event.victim) / 5D);
                event.victim.damage(event.value * tmp, event.attacker);
                int scorchamount;
                if (event.value > 1000) {
                    scorchamount = 10;
                }else{
                    scorchamount = event.value / 100;
                    if (scorchamount < 2) scorchamount = 2;
                }
                AttributesHandler.Attributes.get("Scorch").getPlayerStats().put((Player) event.victim,
                        scorchamount + (int) AttributesHandler.Attributes.get("Scorch").getPlayer((Player) event.victim));
                return;
            }
            case CURSE: {
                if (!(event.victim instanceof Player)) {
                    return;
                }
                int curseamount;
                if (event.value > 500) {
                    curseamount = 10;
                }else{
                    curseamount = event.value / 50;
                    if (curseamount < 3) curseamount = 3;
                }
                AttributesHandler.Attributes.get("Curse").getPlayerStats().put((Player) event.victim, curseamount +
                        (int) AttributesHandler.Attributes.get("Curse").getPlayer((Player) event.victim));
            }
            case ARCANE: {

            }
            case EARTH: {

            }
        }
    }
}
