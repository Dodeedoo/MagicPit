package me.dodeedoo.magicpit.social.party;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PartySystem {

    public static HashMap<PartyIdentifier, List<PartyMember>> PartyRegistry = new HashMap<>();
    public static HashMap<Player, PartyIdentifier> PlayerRegistry = new HashMap<>();

    public static PartyIdentifier createParty(Player player, PartySize size) {
        PartyIdentifier ident = new PartyIdentifier(player, size);
        List<PartyMember> list = new ArrayList<>();
        list.add(new PartyMember(player, true));
        PartyRegistry.put(ident, list);
        PlayerRegistry.put(player, ident);
        return ident;
    }

    public static void invitePlayer(PartyIdentifier party, Player player) {
        if (isInParty(player)) {
            party.getLeader().sendMessage(Component.text("&cThey're already in a party"));
            return;
        }
        TextComponent clickable = Component.text(ChatColor.translateAlternateColorCodes('&', "&7|| &eClick &7to join &b" + party.getLeader().getName() + "'s &7party ||"));

        return;
    }

    public static Boolean isInParty(Player player) {
        return PlayerRegistry.containsKey(player);
    }

    public static PartyIdentifier getParty(Player player) {
        return PlayerRegistry.get(player);
    }

}
