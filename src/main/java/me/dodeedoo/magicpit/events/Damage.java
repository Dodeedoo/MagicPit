package me.dodeedoo.magicpit.events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.events.magicdamage.MagicDamageHandle;
import me.dodeedoo.magicpit.items.PitItem;
import me.dodeedoo.magicpit.skills.SkillExecuteAction;
import me.dodeedoo.magicpit.skills.SkillHandler;
import me.dodeedoo.magicpit.social.party.PartySystem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import particles.LocationLib;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Damage implements Listener {
    @EventHandler
    public void damage(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof ArmorStand || event.getEntity().hasMetadata("NPC")) {
            return;
        }

        Bukkit.broadcast(Component.text(event.getCause().name()));
        String colorCode = "&7";

        switch (event.getCause()) {
            case CUSTOM: {
                colorCode = "&d";
                break;
            }
            case PROJECTILE: {
                colorCode = "&6";
                break;
            }
        }

        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player &&
                PartySystem.sameParty((Player) event.getDamager(), (Player) event.getEntity())) {
            event.setCancelled(true);
            return;
        }


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

        Random random = new Random();
        Location location = event.getEntity().getLocation().add(random.nextDouble(), random.nextDouble(), random.nextDouble());
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(new Location(location.getWorld(), 0, 0, 0), EntityType.ARMOR_STAND);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setMarker(true);
        int dmg = (int) event.getDamage();

        if (event.getDamager() instanceof Player) {
            armorStand.customName(Component.text(Util.colorize(colorCode + dmg)));
        }else if (event.getEntity() instanceof Player) {
            armorStand.customName(Component.text(Util.colorize("&c" + dmg)));
        }

        armorStand.setCustomNameVisible(true);
        armorStand.teleport(location);
        Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), armorStand::remove, 25);
    }

    @EventHandler
    public void death(EntityDeathEvent event) {
        //setting killer from magic damage
        if (event.getEntity().getKiller() == null && MagicDamageHandle.lastMagicDamage.containsKey(event.getEntity())) {
            event.getEntity().setKiller((Player) MagicDamageHandle.lastMagicDamage.get(event.getEntity()).getDamager());
            Bukkit.broadcast(Component.text(event.getEntity().getKiller().getName()));
        }

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
