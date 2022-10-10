package me.dodeedoo.magicpit.classes;

import it.unimi.dsi.fastutil.Hash;
import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.PitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PitClassHandler {

    public static List<PitClass> classList = new ArrayList<>();
    public static HashMap<Player, PitClass> utilListForPreviousClass = new HashMap<>();

    public static void select(Player player, PitClass pitClass) {
        PitPlayer.playerMap.get(player).setPlayerClass(pitClass);
        if (!pitClass.getDataMap().containsKey(player)) {
            pitClass.getDataMap().put(player, new PitClassData(pitClass.getNodeMap()));
        }
    }

    public static void startClassWorker() {
        Bukkit.getScheduler().runTaskTimer(MagicPitCore.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (PitClass pitClass : classList) {
                    PitClassData data = pitClass.getDataMap().get(player);
                    data.setTotalPoints(PitPlayer.playerMap.get(player).level);
                    pitClass.getDataMap().put(player, data);
                }
                if (!utilListForPreviousClass.containsKey(player)) {
                    utilListForPreviousClass.put(player, PitPlayer.playerMap.get(player).playerClass);
                    PitPlayer.playerMap.get(player).playerClass.refreshNodeMap();
                    for (PitClassProperty pitClassProperty : PitPlayer.playerMap.get(player).playerClass.getNodeMap().keySet()) {
//                        Bukkit.broadcastMessage(pitClassProperty.skillClassName + " apply 1");
//                        check if active
//                        Bukkit.broadcastMessage(PitPlayer.playerMap.get(player).playerClass.getNodeMap().get(pitClassProperty).toString());
//                        Bukkit.broadcastMessage(PitPlayer.playerMap.get(player).playerClass.getDataMap().get(player).tree.isActivated(PitPlayer.playerMap.get(player).playerClass.getNodeMap().get(pitClassProperty)).toString());
//                        Bukkit.broadcastMessage(PitPlayer.playerMap.get(player).playerClass.getNodeMap().values() + " " + PitPlayer.playerMap.get(player).playerClass.getFancyName());
                        if (PitPlayer.playerMap.get(player).playerClass.getDataMap().get(player).tree.isActivated(PitPlayer.playerMap.get(player).playerClass.getNodeMap().get(pitClassProperty))) {
                            Bukkit.broadcastMessage(pitClassProperty.skillClassName + " applied");
                            pitClassProperty.apply(player);
                        }
                    }
                }
                if (utilListForPreviousClass.get(player) != PitPlayer.playerMap.get(player).playerClass) {
                    PitPlayer.playerMap.get(player).playerClass.refreshNodeMap();
                    for (PitClassProperty pitClassProperty : utilListForPreviousClass.get(player).getNodeMap().keySet()) {
                        //Bukkit.broadcastMessage(pitClassProperty.skillClassName + " remove");
                        //check if active
                        if (utilListForPreviousClass.get(player).getDataMap().get(player).tree.isActivated(utilListForPreviousClass.get(player).getNodeMap().get(pitClassProperty))) {
                            pitClassProperty.remove(player);
                        }
                    }
                    utilListForPreviousClass.put(player, PitPlayer.playerMap.get(player).playerClass);
                    PitPlayer.playerMap.get(player).playerClass.refreshNodeMap();
                    for (PitClassProperty pitClassProperty : PitPlayer.playerMap.get(player).playerClass.getNodeMap().keySet()) {
                        //Bukkit.broadcastMessage(pitClassProperty.skillClassName + " apply 2");
                        //check if active
                        if (PitPlayer.playerMap.get(player).playerClass.getDataMap().get(player).tree.isActivated(PitPlayer.playerMap.get(player).playerClass.getNodeMap().get(pitClassProperty))) {
                            pitClassProperty.apply(player);
                        }
                    }
                }
            }
//            for (PitClass pitClass : classList) {
//                for (Player player : Bukkit.getOnlinePlayers()) {
//                    if (PitPlayer.playerMap.get(player).playerClass.equals(pitClass)) {
//                        for (PitClassProperty property : pitClass.getNodeMap().keySet()) {
//                            if (pitClass.getDataMap().containsKey(player)) {
//                                if (pitClass.getDataMap().get(player).tree.isActivated(pitClass.getNodeMap().get(property))) {
//                                    property.apply(player);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }, 30, 30);
    }

    public static void activateNode(Player player, List<Integer> index) {
        PitPlayer.playerMap.get(player).playerClass.getDataMap().get(player).tree.activate(index);
    }

    public static void activateBase(Player player) {
        PitPlayer.playerMap.get(player).playerClass.getDataMap().get(player).tree.baseNode.setActivated(true);
    }

    public static void loadData(Player player) {
        for (PitClass pitClass : PitClassHandler.classList) {
            Bukkit.getLogger().info(pitClass.getFancyName());
            if (!pitClass.getData().contains("players." + player.getUniqueId())) {
                FileConfiguration cfg = pitClass.getData();
                cfg.set("players." + player.getUniqueId(), new PitClassData(pitClass.getNodeMap()));
                pitClass.saveData(cfg);
            }
            pitClass.getDataMap().put(player, (PitClassData) pitClass.getData().get("players." + player.getUniqueId()));
        }
    }

    public static void unloadData(Player player) {
        for (PitClass pitClass : PitClassHandler.classList) {
            Bukkit.getLogger().info(pitClass.getFancyName());
            FileConfiguration cfg = pitClass.getData();
            cfg.set("players." + player.getUniqueId(), pitClass.getDataMap().get(player));
            pitClass.saveData(cfg);
            pitClass.getDataMap().remove(player);
        }
    }

    public static void unloadAllData() {
        for (PitClass pitClass : PitClassHandler.classList) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                FileConfiguration cfg = pitClass.getData();
                cfg.set("players." + player.getUniqueId(), pitClass.getDataMap().get(player));
                pitClass.saveData(cfg);
                pitClass.getDataMap().remove(player);
            }
        }
    }


}
