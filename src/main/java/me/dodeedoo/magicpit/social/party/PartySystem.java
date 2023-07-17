package me.dodeedoo.magicpit.social.party;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartySystem {

    public static HashMap<PartyIdentifier, List<Player>> PartyRegistry = new HashMap<>();
    public static HashMap<Player, PartyIdentifier> PlayerRegistry = new HashMap<>();

    public static @Nullable PartyIdentifier createParty(Player player, PartySize size) {
        if (PlayerRegistry.containsKey(player)) {
            player.sendMessage(Component.text(Util.colorize("&cAlready in a party")));
            return null;
        }
        PartyIdentifier ident = new PartyIdentifier(player, size);
        List<Player> list = new ArrayList<>();
        list.add(player);
        PartyRegistry.put(ident, list);
        PlayerRegistry.put(player, ident);
        return ident;
    }

    public static void disbandParty(PartyIdentifier party) {
        if (PartyRegistry.containsKey(party)) {
            PartyRegistry.get(party).forEach((player) -> {
                PlayerRegistry.remove(player);
            });
            PartyRegistry.remove(party);
        }
    }

    public static void invitePlayer(PartyIdentifier party, Player player) {
        if (isInParty(player)) {
            party.getLeader().sendMessage(Component.text(Util.colorize("&cThey're already in a party")));
            return;
        }

        switch (party.getSize()) {
            case SQUAD: {
                if (PartyRegistry.get(party).size() >= 4) {
                    party.getLeader().sendMessage(Component.text(Util.colorize("&cSquad Size limit reached (4)")));
                    return;
                }
                break;
            }
            case GROUP: {
                if (PartyRegistry.get(party).size() >= 8) {
                    party.getLeader().sendMessage(Component.text(Util.colorize("&cGroup Size limit reached (8)")));
                    return;
                }
                break;
            }
            case RAID: {
                if (PartyRegistry.get(party).size() >= 15) {
                    party.getLeader().sendMessage(Component.text(Util.colorize("&cRaid Size limit reached (15)")));
                    return;
                }
                break;
            }
        }

        party.getInvitelist().add(player);
        new BukkitRunnable() {
            @Override
            public void run() {
                party.getInvitelist().remove(player);
                if (!PartyRegistry.get(party).contains(player)) {
                    party.getLeader().sendMessage(Component.text(
                            Util.colorize("&cParty invite from " + party.getLeader().getName() + " expired")));
                }
            }
        }.runTaskLater(MagicPitCore.getInstance(), 1200);

        TextComponent clickable = Component.
                text(Util.colorize("&7|| &eClick &7to join &b" + party.getLeader().getName() + "'s &7party ||")).
                clickEvent(ClickEvent.runCommand("/party join " + party.getLeader().getName())).
                hoverEvent(HoverEvent.showText(Component.text(Util.colorize("&7Click to join"))));

        player.sendMessage(clickable);

    }

    public static void joinParty(PartyIdentifier party, Player player) {
        if (!party.getInvitelist().contains(player)) {
            player.sendMessage(Component.text(Util.colorize("&cYou don't have an invite")));
            return;
        }
        PlayerRegistry.put(player, party);
        party.getInvitelist().remove(player);
        PartyRegistry.get(party).add(player);
        PartyRegistry.get(party).forEach((player1 -> {
            player1.sendMessage(Component.text(Util.colorize("&a" + player.getName() + " has joined the party")));
        }));
    }

    public static void leaveParty(Player player) {
        if (!PlayerRegistry.containsKey(player)) {
            player.sendMessage(Component.text(Util.colorize("&cNot in a party")));
            return;
        }
        PartyIdentifier party = PlayerRegistry.get(player);
        if (party.getLeader() == player) {
            disbandParty(party);
            return;
        }
        PartyRegistry.get(party).remove(player);
        PartyRegistry.get(party).forEach((player1 -> {
            player1.sendMessage(Component.text(Util.colorize("&c" + player.getName() + " has left the party")));
        }));

    }

    public static Boolean isInParty(Player player) {
        return PlayerRegistry.containsKey(player);
    }

    public static PartyIdentifier getParty(Player player) {
        return PlayerRegistry.get(player);
    }

    public static boolean sameParty(Player player, Player player2) {
        return PlayerRegistry.get(player) == PlayerRegistry.get(player2);
    }

}
