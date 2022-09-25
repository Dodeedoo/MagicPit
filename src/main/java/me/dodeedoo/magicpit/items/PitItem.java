package me.dodeedoo.magicpit.items;

import me.dodeedoo.magicpit.MagicPitCore;
import me.dodeedoo.magicpit.PitPlayer;
import me.dodeedoo.magicpit.Util;
import me.dodeedoo.magicpit.attributes.Attribute;
import me.dodeedoo.magicpit.attributes.AttributesHandler;
import me.dodeedoo.magicpit.classes.PitClass;
import me.dodeedoo.magicpit.scoreboard.ScoreboardManager;
import me.dodeedoo.magicpit.skills.Skill;
import me.dodeedoo.magicpit.skills.SkillHandler;
import me.dodeedoo.magicpit.skills.list.EggBall;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PitItem {

    public HashMap<Attribute, Object> attributeMap = new HashMap<>();
    public List<Skill> abilities = new ArrayList<>();
    public List<String> lore;
    public Player user;
    public Integer levelreq;
    public Material material;
    public ItemType type = ItemType.BASIC;
    public String name = "NONE";
    public PitClass classReq = null;

    public PitItem(List<String> lore, Player user, Material material, String name) {
        this.lore = lore;
        this.user = user;
        this.material = material;
        this.name = name;
    }

    public PitItem(List<String> lore, Player user, Material material, String name, HashMap<Attribute, Object> attributeMap, List<Skill> abilities, Integer levelreq, ItemType type) {
        this.lore = lore;
        this.abilities = abilities;
        this.attributeMap = attributeMap;
        this.user = user;
        this.levelreq = levelreq;
        this.material = material;
        this.type = type;
        this.name = name;
    }

    public void setClassReq(PitClass classReq) {
        this.classReq = classReq;
    }

    public void setAbilities(List<Skill> abilities) {
        this.abilities = abilities;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setAttributeMap(HashMap<Attribute, Object> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public void setLevelreq(Integer levelreq) {
        this.levelreq = levelreq;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void apply() {
        if (PitPlayer.playerMap.get(this.user).level < this.levelreq) {
            this.user.sendMessage(Util.colorize("&cYou arent high enough level to use this item!"));
            return;
        }
        if (!PitPlayer.playerMap.get(this.user).playerClass.getClass().getSimpleName().equals(this.classReq.getClass().getSimpleName())) {
            this.user.sendMessage(Util.colorize("&c" + this.classReq.getClass().getSimpleName() + " class required to use this item!!!"));
            return;
        }
        if (!this.abilities.isEmpty()) {
            for (Skill skill : this.abilities) {
                HashMap<Skill, String[]> tempMap = SkillHandler.playerSkills.get(this.user);
                try {
                    tempMap.put(skill.getClass().newInstance(), new String[]{});
                } catch (Exception e) {
                    e.printStackTrace();
                    this.user.sendMessage(Util.colorize("&cA critical error has happened regarding skill loading, please contact admins"));
                }
                SkillHandler.playerSkills.put(this.user, tempMap);
            }
        }
        if (!this.attributeMap.isEmpty()) {
            for (Attribute attribute : this.attributeMap.keySet()) {
                attribute.getPlayerStats().put(this.user, ((int) attribute.getPlayer(this.user)) + (int) this.attributeMap.get(attribute));
            }
        }
    }

    public void remove() {
        if (PitPlayer.playerMap.get(this.user).level < this.levelreq) {
            return;
        }
        if (!PitPlayer.playerMap.get(this.user).playerClass.getClass().getSimpleName().equals(this.classReq.getClass().getSimpleName())) {
            return;
        }
        if (!this.abilities.isEmpty()) {
            for (Skill skill : this.abilities) {
                for (Skill appliedSkill : SkillHandler.playerSkills.get(this.user).keySet()) {
                    if (appliedSkill.getClass().getName().equals(skill.getClass().getName())) {
                        SkillHandler.playerSkills.get(this.user).remove(appliedSkill);
                        ScoreboardManager.refreshSkill(this.user);
                    }
                }
            }
        }
        if (!this.attributeMap.isEmpty()) {
            for (Attribute attribute : this.attributeMap.keySet()) {
                attribute.getPlayerStats().put(this.user, ((int) attribute.getPlayer(this.user)) - (int) this.attributeMap.get(attribute));
            }
        }
    }

    public static PitItem itemFromItemStack(ItemStack itemStack, Player player) {
        PitItem newitem = new PitItem(
                itemStack.getLore().subList(0, 0),
                player,
                itemStack.getType(),
                itemStack.getItemMeta().getDisplayName()
        );
        if (!itemStack.getItemMeta().getPersistentDataContainer().isEmpty()) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta.getPersistentDataContainer().has(new NamespacedKey(MagicPitCore.getInstance(), "levelreq"))) {
                newitem.setLevelreq(meta.getPersistentDataContainer().get(new NamespacedKey(MagicPitCore.getInstance(), "levelreq"), PersistentDataType.INTEGER));
            }
            if (meta.getPersistentDataContainer().has(new NamespacedKey(MagicPitCore.getInstance(), "classreq"))) {
                String value = meta.getPersistentDataContainer().get(
                        new NamespacedKey(MagicPitCore.getInstance(), "classreq"),
                        PersistentDataType.STRING);
                try {
                    newitem.setClassReq((PitClass) Class.forName(value).newInstance());
                }catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            if (meta.getPersistentDataContainer().has(new NamespacedKey(MagicPitCore.getInstance(), "type"))) {
                newitem.setType(ItemType.valueOf(meta.getPersistentDataContainer().get(new NamespacedKey(MagicPitCore.getInstance(), "type"), PersistentDataType.STRING)));
            }
            HashMap<Attribute, Object> tempMap = new HashMap<>();
            List<Skill> tempList = new ArrayList<>();
            for (NamespacedKey key : meta.getPersistentDataContainer().getKeys()) {
                String keyName = key.getKey();
                if (keyName.contains("attr_")) {
                    String value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    tempMap.put(AttributesHandler.Attributes.get(value.split("/")[0]), Integer.parseInt(value.split("/")[1]));
                }
                if (keyName.contains("skill_")) {
                    String value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    try {
                        tempList.add((Skill) Class.forName(value).newInstance());
                    }catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            newitem.setAbilities(tempList);
            newitem.setAttributeMap(tempMap);
        }
        return newitem;
    }

    public static ItemStack itemStackFromItem(PitItem item) {
        ItemStack newitem = new ItemStack(item.material);
        ItemMeta meta = newitem.getItemMeta();
        List<String> lore = new ArrayList<>();
        for (String line : item.lore) {
            lore.add(Util.colorize(line));
        }
        lore.add(Util.colorize("&e"));
        meta.setDisplayName(Util.colorize(item.name));
        NamespacedKey key = new NamespacedKey(MagicPitCore.getInstance(), "levelreq");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, item.levelreq);

        key = new NamespacedKey(MagicPitCore.getInstance(), "type");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, item.type.toString());

        for (Attribute attribute : item.attributeMap.keySet()) {
            String className = attribute.getClass().getSimpleName();
            //Bukkit.broadcastMessage(className);
            key = new NamespacedKey(MagicPitCore.getInstance(), "attr_" + className);
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING,
                    className + "/" + item.attributeMap.get(attribute));
            if ((int) item.attributeMap.get(attribute) > 0) {
                lore.add(Util.colorize("&7" + className + ": &a" + item.attributeMap.get(attribute)));
            }else{
                lore.add(Util.colorize("&7" + className + ": &c" + item.attributeMap.get(attribute)));
            }
        }

        lore.add(Util.colorize("&e"));

        for (Skill skill : item.abilities) {
            key = new NamespacedKey(MagicPitCore.getInstance(), "skill_" + skill.getClass().getSimpleName());
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, skill.getClass().getName());
            String line = "";
            lore.add(Util.colorize("&e&l" + skill.getAction().toString().replace("_", " ")));
            for (String skillLine : skill.getLore()) {
                lore.add(Util.colorize(skillLine));
            }
            lore.add(Util.colorize("&e"));
        }

        lore.add(Util.colorize("&7Level " + item.levelreq + " required to use"));
        if (item.classReq != null) {
            key = new NamespacedKey(MagicPitCore.getInstance(), "classreq");
            meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, item.classReq.getClass().getName());
            lore.add(Util.colorize("&7" + item.classReq.getClass().getSimpleName() + " item"));
        }
        lore.add(Util.colorize("&8Type " + item.type.toString().replace("_", " ")));

        meta.setLore(lore);
        newitem.setItemMeta(meta);
        return newitem;
    }

    public static ItemStack updateLoreOfItemStack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        List<String> lore = meta.getLore();

        if (!itemStack.getItemMeta().getPersistentDataContainer().isEmpty()) {
            lore.clear();
            lore.add(Util.colorize("&e"));
            HashMap<Attribute, Object> tempMap = new HashMap<>();
            List<Skill> tempList = new ArrayList<>();
            for (NamespacedKey key : meta.getPersistentDataContainer().getKeys()) {
                String keyName = key.getKey();
                if (keyName.contains("attr_")) {
                    String value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    tempMap.put(AttributesHandler.Attributes.get(value.split("/")[0]), Integer.parseInt(value.split("/")[1]));
                }
                if (keyName.contains("skill_")) {
                    String value = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    try {
                        tempList.add((Skill) Class.forName(value).newInstance());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            for (Attribute attribute : tempMap.keySet()) {
                String className = attribute.getClass().getSimpleName();
                //Bukkit.broadcastMessage(className);
                if ((int) tempMap.get(attribute) > 0) {
                    lore.add(Util.colorize("&7" + className + ": &a" + tempMap.get(attribute)));
                }else{
                    lore.add(Util.colorize("&7" + className + ": &c" + tempMap.get(attribute)));
                }
            }

            lore.add(Util.colorize("&e"));

            for (Skill skill : tempList) {
                String line = "";
                lore.add(Util.colorize("&e&l" + skill.getAction().toString().replace("_", " ")));
                for (String skillLine : skill.getLore()) {
                    lore.add(Util.colorize(skillLine));
                }
                lore.add(Util.colorize("&e"));
            }

            if (meta.getPersistentDataContainer().has(new NamespacedKey(MagicPitCore.getInstance(), "levelreq"))) {
                lore.add(Util.colorize("&7Level " + meta.getPersistentDataContainer().get(new NamespacedKey(MagicPitCore.getInstance(), "levelreq"), PersistentDataType.INTEGER) + " required to use"));
            }
            if (meta.getPersistentDataContainer().has(new NamespacedKey(MagicPitCore.getInstance(), "classreq"))) {
                String value = meta.getPersistentDataContainer().get(
                        new NamespacedKey(MagicPitCore.getInstance(), "classreq"),
                        PersistentDataType.STRING);
                lore.add(Util.colorize("&7" + value.replace("me.dodeedoo.magicpit.classes.", "") + " item"));
            }
            if (meta.getPersistentDataContainer().has(new NamespacedKey(MagicPitCore.getInstance(), "type"))) {
                lore.add(Util.colorize("&8Type: " + meta.getPersistentDataContainer().get(new NamespacedKey(MagicPitCore.getInstance(), "type"), PersistentDataType.STRING).replace("_", " ")));
            }
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
