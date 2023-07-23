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
        Integer entityId = random.nextInt(9999);
        Location loc = event.getEntity().getLocation();
        PacketContainer packet = MagicPitCore.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY);
        packet.getIntegers().write(0, entityId); //entity id
        packet.getIntegers().write(1, 2); //entity type armor stand
        packet.getDoubles().write(0, loc.getX() + random.nextDouble());
        packet.getDoubles().write(1, loc.getY() + random.nextDouble());
        packet.getDoubles().write(2, loc.getZ() + random.nextDouble());

        PacketContainer data = MagicPitCore.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
        data.getIntegers().write(0, entityId);

        WrappedDataWatcher metadata = new WrappedDataWatcher();
        metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0,
                WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20); //invis
        metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3,
                WrappedDataWatcher.Registry.get(Boolean.class)), true); //custom name visible
        metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(5,
                WrappedDataWatcher.Registry.get(Boolean.class)), true); //no gravity
        metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(15,
                WrappedDataWatcher.Registry.get(Byte.class)), (byte) (0x08 | 0x10)); //isSmall, noBasePlate, set Marker
        Optional<?> opt = Optional.of(WrappedChatComponent.fromChatMessage(Util.colorize(colorCode + event.getDamage()))[0].getHandle());
        metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2,
                WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt); //name

        data.getWatchableCollectionModifier().write(0, metadata.getWatchableObjects());

        List<Player> players = new ArrayList<>();
        for (Entity player : loc.getNearbyEntities(30, 30, 30)) {
            if (player instanceof Player) {
                players.add((Player) player);
                MagicPitCore.getProtocolManager().sendServerPacket((Player) player, packet);
                MagicPitCore.getProtocolManager().sendServerPacket((Player) player, data);
            }
        }

        Bukkit.getScheduler().runTaskLater(MagicPitCore.getInstance(), () -> {

        }, 25);
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
