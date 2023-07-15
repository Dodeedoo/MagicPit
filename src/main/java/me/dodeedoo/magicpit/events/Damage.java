package me.dodeedoo.magicpit.events;

import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.items.PitItem;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import particles.LocationLib;

import java.time.LocalDateTime;

public class Damage implements Listener {
    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {

        //display

        if (event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE)) {

            if (event.getEntity() instanceof Player) {
                if (LocationLib.isInSpawn((Player) event.getEntity())) {
                    event.setCancelled(true);
                    return;
                }
                AttributesHandler.handleDamaged(event);
                SkillHandler.handleAction(SkillExecuteAction.DAMAGED, (Player) event.getEntity());

                //invoke item type properties
                try {
                    PitItem.itemFromItemStack(
                            ((Player) event.getEntity()).getInventory().getItemInMainHand(),
                            (Player) event.getEntity()
                    ).type.invoke((Player) event.getEntity(), event);
                } catch (Exception ignored) {
                }
            }

            Projectile projectile = (Projectile) event.getDamager();

            if (projectile.getShooter() instanceof Player) {
                if (LocationLib.isInSpawn((Player) projectile.getShooter())) {
                    event.setCancelled(true);
                    return;
                }
                event.setDamage(0);
                EntityDamageByEntityEvent newEvent = new EntityDamageByEntityEvent((Entity) projectile.getShooter(), event.getEntity(), EntityDamageEvent.DamageCause.DRYOUT, event.getDamage());
                AttributesHandler.handleHit(newEvent);
                ((LivingEntity) event.getEntity()).damage(newEvent.getDamage());

                try {
                    PitItem.itemFromItemStack(
                            ((Player) projectile.getShooter()).getInventory().getItemInMainHand(),
                            (Player) projectile.getShooter()
                    ).type.invoke((Player) projectile.getShooter(), event);
                } catch (Exception ignored) {
                }
            }


            return;
        }
        EntityDamageEvent.DamageCause cause = event.getCause();
        if (event.getEntity() instanceof Player) {
            if (LocationLib.isInSpawn((Player) event.getEntity())) {
                event.setCancelled(true);
                return;
            }
            AttributesHandler.handleDamaged(event);
            SkillHandler.handleAction(SkillExecuteAction.DAMAGED, (Player) event.getEntity());

            //invoke item type properties
            try {
                PitItem.itemFromItemStack(
                        ((Player) event.getEntity()).getInventory().getItemInMainHand(),
                        (Player) event.getEntity()
                ).type.invoke((Player) event.getEntity(), event);
            } catch (Exception ignored) {
            }
        }
        if (cause.equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK) || cause.equals(EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)) {
            if (event.getDamager() instanceof Player) {
                if (LocationLib.isInSpawn((Player) event.getDamager())) {
                    event.setCancelled(true);
                    return;
                }
                AttributesHandler.handleHit(event);

                try {
                    PitItem.itemFromItemStack(
                            ((Player) event.getDamager()).getInventory().getItemInMainHand(),
                            (Player) event.getDamager()
                    ).type.invoke((Player) event.getDamager(), event);
                } catch (Exception ignored) {
                }
            }
        }
    }

    @EventHandler
    public void death(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            AttributesHandler.handleDeath(event);
            SkillHandler.handleAction(SkillExecuteAction.DEATH, (Player) event.getEntity());

            //invoke item type properties
            try {
                PitItem.itemFromItemStack(
                        ((Player) event.getEntity()).getInventory().getItemInMainHand(),
                        (Player) event.getEntity()
                ).type.invoke((Player) event.getEntity(), event);
            }catch (Exception ignored) { }
        }
        if (event.getEntity().getKiller() != null) {
            AttributesHandler.handleKill(event);

            try {
                PitItem.itemFromItemStack(
                        event.getEntity().getKiller().getInventory().getItemInMainHand(),
                        event.getEntity().getKiller()
                ).type.invoke(event.getEntity().getKiller(), event);
            }catch (Exception ignored) { }
        }
    }

    @EventHandler
    public void itemdamage(PlayerItemDamageEvent e) {
        e.setCancelled(true);
    }
}
