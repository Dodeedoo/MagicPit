package me.dodeedoo.magicpit.social.party;

import org.bukkit.entity.Player;

public class PartyMember {

    public Player player;
    public Boolean leader;

    public PartyMember(Player player, Boolean leader) {
        this.player = player;
        this.leader = leader;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Boolean isLeader() {
        return this.leader;
    }

}
