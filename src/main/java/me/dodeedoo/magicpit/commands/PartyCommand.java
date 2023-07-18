package me.dodeedoo.magicpit.commands;

import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.social.party.PartySize;
import me.dodeedoo.magicpit.social.party.PartySystem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicReference;

public class PartyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = (Player) commandSender;
        if (!command.getLabel().equals("p") && !command.getLabel().equals("party")) {
            return false;
        }
        switch (args[0]) {
            case "create": {
                if (args.length < 2) {
                    PartySystem.createParty(player, PartySize.SQUAD);
                    player.sendMessage(Component.text(Util.colorize("&aParty created")));
                    return true;
                }else{
                    switch (args[1]) {
                        case "squad": {
                            PartySystem.createParty(player, PartySize.SQUAD);
                            player.sendMessage(Component.text(Util.colorize("&aParty created")));
                            return true;
                        }
                        case "group": {
                            PartySystem.createParty(player, PartySize.GROUP);
                            player.sendMessage(Component.text(Util.colorize("&aParty created")));
                            return true;
                        }
                        case "raid": {
                            PartySystem.createParty(player, PartySize.RAID);
                            player.sendMessage(Component.text(Util.colorize("&aParty created")));
                            return true;
                        }
                    }
                    player.sendMessage(Component.text(Util.colorize("&cPossible party sizes: squad, group, raid")));
                }
                break;
            }
            case "disband": {
                if (PartySystem.isInParty(player) && PartySystem.getParty(player).getLeader() == player) {
                    PartySystem.disbandParty(PartySystem.getParty(player));
                    player.sendMessage(Component.text(Util.colorize("&cParty disbanded")));
                    return true;
                }
                player.sendMessage(Component.text(Util.colorize("&cNot allowed")));
                break;
            }
            case "join": {
                if (args.length > 1 && Bukkit.getPlayer(args[1]) != null && PartySystem.getParty(Bukkit.getPlayer(args[1])) != null) {
                    Player partyMember = Bukkit.getPlayer(args[1]);
                    PartySystem.joinParty(PartySystem.getParty(partyMember), player);
                    return true;
                }
                player.sendMessage(Component.text(Util.colorize("&cUsage /p join <name> OR player not online")));
                break;
            }
            case "leave": {
                PartySystem.leaveParty(player);
                player.sendMessage(Component.text(Util.colorize("&cYou left the party")));
                return true;
            }
            case "invite": {
                if (PartySystem.isInParty(player) && PartySystem.getParty(player).getLeader() == player) {
                    if (args.length > 1 && Bukkit.getPlayer(args[1]) != null) {
                        PartySystem.invitePlayer(PartySystem.getParty(player), Bukkit.getPlayer(args[1]));
                        return true;
                    }else{
                        player.sendMessage(Component.text(Util.colorize("&cUsage /p invite <name of online player>")));
                        return false;
                    }
                }
                player.sendMessage(Component.text(Util.colorize("&cNot allowed")));
                break;
            }
            case "kick": {
                if (PartySystem.isInParty(player) && PartySystem.getParty(player).getLeader() == player) {
                    if (args.length > 1 && Bukkit.getPlayer(args[1]) != null) {
                        PartySystem.leaveParty(Bukkit.getPlayer(args[1]));
                        PartySystem.PartyRegistry.get(PartySystem.getParty(player)).forEach((player1 -> {
                            player1.sendMessage(Component.text(Util.colorize("&c" + args[1] + " was kicked from the party")));
                        }));
                        return true;
                    }
                }
                player.sendMessage(Component.text(Util.colorize("&cNot allowed")));
                break;
            }
            case "list": {
                if (PartySystem.isInParty(player)) {
                    AtomicReference<String> playerList = new AtomicReference<>("");
                    PartySystem.PartyRegistry.get(PartySystem.getParty(player)).forEach((p) -> {
                        playerList.set(playerList + "&e" + p.getName() + "\n");
                    });
                    Component message = Component.text(Util.colorize(playerList.toString()));
                    player.sendMessage(message);
                }
                break;
            }
        }

        return false;
    }
}
