package net.juligames.party.listener;

import net.juligames.party.api.PartyAPI;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;

public class DisconectListenr implements Listener {

    public void onLeave(PlayerDisconnectEvent event) {
        if (PartyAPI.inparty.containsKey(event.getPlayer().getName())) {
            PartyAPI.leave(event.getPlayer());
        } else {
            //Nothing happends
        }
    }

}
