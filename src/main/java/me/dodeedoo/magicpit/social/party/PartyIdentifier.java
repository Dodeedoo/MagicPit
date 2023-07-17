package me.dodeedoo.magicpit.social.party;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class PartyIdentifier {

    public Player leader;
    public UUID uuid;
    public PartySize size;
    public List<Player> invitelist;

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

    public List<Player> getInvitelist() {
        return invitelist;
    }

    public void setInvitelist(List<Player> invitelist) {
        this.invitelist = invitelist;
    }
}
