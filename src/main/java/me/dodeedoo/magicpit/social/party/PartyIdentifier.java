package me.dodeedoo.magicpit.social.party;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PartyIdentifier {

    public Player leader;
    public UUID uuid;
    public PartySize size;

    public PartyIdentifier(Player creator, PartySize size) {
        this.leader = creator;
        this.size = size;
        this.uuid = UUID.randomUUID();
    }

    public Player getLeader() {
        return leader;
    }

    public UUID getUuid() {
        return uuid;
    }

    public PartySize getSize() {
        return size;
    }
}
