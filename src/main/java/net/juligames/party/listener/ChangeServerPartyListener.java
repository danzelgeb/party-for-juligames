package net.juligames.party.listener;

import net.juligames.party.api.PartyAPI;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChangeServerPartyListener implements Listener {

    @EventHandler
    public void onChangeServer(ServerSwitchEvent event) {
        ProxiedPlayer p = event.getPlayer();
        if (PartyAPI.partyfuehrer.contains(p.getName())) {
            ServerInfo si = p.getServer().getInfo();
            p.sendMessage("$7Die Party hat den Server $c" + si.getName() + "$7 betreten.");
            for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
                if (!PartyAPI.inparty.containsKey(x.getName()) || PartyAPI.inparty.get(x.getName()) != p.getName()) continue;
                p.sendMessage("$7Die Party hat den Server $d" + si.getName() + "$7 betreten.");
                x.connect(si);
            }
        }
    }

}
