package me.dodeedoo.magicpit.commands;

import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class setWeight implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        try {
            AttributesHandler.Attributes.get("Weight").getPlayerStats().put(player, Integer.parseInt(strings[0]));
        }catch (IndexOutOfBoundsException e) {
            player.sendMessage(Util.colorize("&cusage: /setWeight <number>"));
        }catch (Exception e) {
            e.printStackTrace();
            player.sendMessage(Util.colorize("&cunknown error occured! contact dodeedoo (eqi)"));
        }
        return false;
    }
}
