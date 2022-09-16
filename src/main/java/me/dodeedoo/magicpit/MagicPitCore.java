package me.dodeedoo.magicpit;

import me.dodeedoo.magicpit.actionbar.AttributeDisplay;
import me.dodeedoo.magicpit.attributes.*;
import me.dodeedoo.magicpit.commands.setStrength;
import me.dodeedoo.magicpit.events.Connection;
import me.dodeedoo.magicpit.events.Damage;
import me.dodeedoo.magicpit.events.Interact;
import me.dodeedoo.magicpit.events.magicdamage.MagicDamageHandle;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagicPitCore extends JavaPlugin {

    private static MagicPitCore instance;

    @Override
    public void onEnable() {
        instance = this;

        // Register Attributes
        AttributesHandler.addAttribute(new Strength(), "Strength"); //dmg event DONE
        AttributesHandler.addAttribute(new Weight(), "Weight"); //second DONE
        AttributesHandler.addAttribute(new Regeneration(), "Regeneration"); //3 second DONE
        AttributesHandler.addAttribute(new Maxmana(), "Maxmana"); //second DONE
        AttributesHandler.addAttribute(new Mana(), "Mana");
        AttributesHandler.addAttribute(new Knowledge(), "Knowledge"); //magic dmg
        AttributesHandler.addAttribute(new Health(), "Health"); //second DONE
        AttributesHandler.addAttribute(new Defense(), "Defense"); //dmg event DONE
        AttributesHandler.addAttribute(new CritChance(), "Critchance"); //dmg event DONE
        AttributesHandler.addAttribute(new Crit(), "Crit"); //dmg event DONE
        AttributesHandler.addAttribute(new Scorch(), "Scorch");
        AttributesHandler.addAttribute(new Curse(), "Curse");

        //Register Commands
        this.getCommand("setStrength").setExecutor(new setStrength());

        //Register Listeners
        Bukkit.getPluginManager().registerEvents(new Damage(), this);
        Bukkit.getPluginManager().registerEvents(new Connection(), this);
        Bukkit.getPluginManager().registerEvents(new MagicDamageHandle(), this);
        Bukkit.getPluginManager().registerEvents(new Interact(), this);

        //Periodical Loops
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                StringBuilder msg = new StringBuilder();
                try {
                    for (Object name : AttributeDisplay.preferences.get(player)) {
                        switch (name.toString()) {
                            case "Strength": {
                                Attribute strength = AttributesHandler.Attributes.get("Strength");
                                msg.append("&4❀Strength ").append(strength.getPlayer(player).toString()).append(" &7// ");
                                break;
                            }
                            case "Weight": {
                                Attribute attr = AttributesHandler.Attributes.get("Weight");
                                msg.append("&8●Weight ").append(attr.getPlayer(player).toString()).append(" &7// ");
                                break;
                            }
                            case "Regeneration": {
                                Attribute attr = AttributesHandler.Attributes.get("Regeneration");
                                msg.append("&d✿Regeneration ").append(attr.getPlayer(player).toString()).append(" &7// ");
                                break;
                            }
                            case "Maxmana": {
                                Attribute attr = AttributesHandler.Attributes.get("Maxmana");
                                Attribute attr2 = AttributesHandler.Attributes.get("Mana");
                                msg.append("&b✿Mana ").append(attr2.getPlayer(player).toString()).append("/").append(attr.getPlayer(player).toString()).append(" &7// ");
                                break;
                            }
                            case "Knowledge": {
                                Attribute attr = AttributesHandler.Attributes.get("Knowledge");
                                msg.append("&3❂Knowledge ").append(attr.getPlayer(player).toString()).append(" &7// ");
                                break;
                            }
                            case "Health": {
                                Attribute attr = AttributesHandler.Attributes.get("Health");
                                Double maxhp = (Integer) attr.getPlayer(player) + player.getMaxHealth();
                                msg.append("&c❤Health ").append(((int) player.getHealth())).append("/").append(maxhp.intValue()).append(" &7// ");
                                break;
                            }
                            case "Defense": {
                                Attribute attr = AttributesHandler.Attributes.get("Defense");
                                msg.append("&2⛨Defense ").append(attr.getPlayer(player).toString()).append(" &7// ");
                                break;
                            }
                            case "CritChance": {
                                Attribute attr = AttributesHandler.Attributes.get("Critchance");
                                msg.append("&9✯Crit Chance ").append(attr.getPlayer(player).toString()).append("% &7// ");
                                break;
                            }
                            case "Crit": {
                                Attribute attr = AttributesHandler.Attributes.get("Crit");
                                msg.append("&1♢Crit ").append(attr.getPlayer(player).toString()).append("% &7// ");
                                break;
                            }

                        }

                    }
                }catch (NullPointerException exc) {
                    ConfigUtil.handlePlayerConfig(player);
                    AttributeDisplay.handlePreferenceLoad(player);
                }

                try {
                    player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Util.colorize(msg.toString())));
                }catch (Exception e) {
                    getLogger().info("error");
                }
            }
        }, 10, 10);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                AttributesHandler.handleSecond(player);
            }
        }, 20, 20);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                AttributesHandler.handleThreeSecond(player);
            }
        }, 60, 60);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player player : Bukkit.getOnlinePlayers()) {
            AttributeDisplay.handlePreferenceUnload(player);
        }
    }

    public static MagicPitCore getInstance() {
        return instance;
    }
}
