package net.juligames.party.api;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class PartyAPI {
    public static ArrayList<String> partyfuehrer = new ArrayList();
    public static HashMap<String, String> inparty = new HashMap();
    public static HashMap<String, Long> invitetime = new HashMap();
    public static HashMap<String, String> invite = new HashMap();
    static Integer maxparty = 10;


    public static void neueParty(ProxiedPlayer p) {
        if (inparty.containsKey(p.getName())) {
            p.sendMessage("§7Du bist in keiner §cParty§7.");
            return;
        }
        if (partyfuehrer.contains(p.getName())) {
            p.sendMessage("§7Du bist bereits in einer §cParty§7.");
            return;
        }
        partyfuehrer.add(p.getName());
        p.sendMessage("§7Du hast eine §cParty§7 erstellt.");
    }

    public static void leave(ProxiedPlayer p) {
        if (inparty.containsKey(p.getName())) {
            p.sendMessage("§7Du hast die Party verlassen.");
            for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
                if (inparty.get(x.getName()) != inparty.get(p.getName())) continue;
                x.sendMessage("§c" + p.getName() + "§7 hat die Party verlassen.");
            }
            inparty.remove(p.getName());
            return;
        }
        if (partyfuehrer.contains(p.getName())) {
            p.sendMessage("§7Die Party wurde aufgrund fehlender Spieler aufgelöst.");
            partyfuehrer.remove(p.getName());
            for (ProxiedPlayer x : ProxyServer.getInstance().getPlayers()) {
                if (!inparty.containsKey(x.getName()) || inparty.get(x.getName()) != p.getName()) continue;
                p.sendMessage("§7Die Party wurde aufgrund fehlender Spieler aufgel\u00f6st.");
                inparty.remove(x.getName());
            }
            return;
        }
        if (!inparty.containsKey(p.getName()) && !partyfuehrer.contains(p.getName())) {
            p.sendMessage("§7Du bist in keiner Party.");
        }
    }


    public static void invite(ProxiedPlayer p, ProxiedPlayer z) {
        long aktuell = System.currentTimeMillis();
        if (partyfuehrer.contains(p.getName())) {
            Integer iparty = 0;
            for (ProxiedPlayer i : ProxyServer.getInstance().getPlayers()) {
                if (!inparty.containsKey(i.getName()) || inparty.get(i.getName()) != p.getName()) continue;
                iparty = iparty + 1;
            }
            if (iparty >= maxparty) {
                p.sendMessage(" §7Die Party ist voll.");
                return;
            }
            if (inparty.containsKey(z.getName())) {
                p.sendMessage("§7Dieser Spieler befindet sich bereits in einer Party.");
                return;
            }
            if (partyfuehrer.contains(z.getName())) {
                p.sendMessage("§7Dieser Spieler befindet sich bereits in einer Party.");
                return;
            }
            if (!inparty.containsKey(z.getName()) && !partyfuehrer.contains(z.getName())) {
                invite.put(z.getName(), p.getName());
                invitetime.put(z.getName(), aktuell);
                p.sendMessage("§7Du hast den Spieler §c" + z.getName() + " §7eingeladen.");
                z.sendMessage("§c" + p.getName() + " §7hat dich zu seiner Party eingeladen.");
                z.sendMessage("§7Betrete die Party mit §c/party accept " + p.getName() + "§7.");
                return;
            }
        } else if (inparty.containsKey(p.getName())) {
            p.sendMessage("§7 Du darfst keine Spieler einladen.");
        } else if (!inparty.containsKey(p.getName()) && !partyfuehrer.contains(p.getName())) {
            neueParty(p);
            invite(p, z);
        }
    }

    public static void accept(ProxiedPlayer p) {
        if (partyfuehrer.contains(p.getName()) | inparty.containsKey(p.getName())) {
            p.sendMessage("§7Du bist bereits in einer Party");
        } else if (invite.containsKey(p.getName())) {
            Long aktuell = System.currentTimeMillis();
            Long diff = aktuell / 1000 - invitetime.get(p.getName()) / 1000;
            if (diff > 60) {
                p.sendMessage("§7Diese Einladung ist bereits abgelaufen.");
            } else {
                ProxiedPlayer Leiter = ProxyServer.getInstance().getPlayer(invite.get(p.getName()));
                Leiter.sendMessage("§c" + p.getName() + " §7hat die Party betreten.");
                invite.remove(p.getName());
                invitetime.remove(p.getName());
                inparty.put(p.getName(), Leiter.getName());
                p.sendMessage("§7Du hast die Party von §c" + Leiter.getName() + "§7 betreten.");
            }
        } else {
            p.sendMessage("§7Du wurdest nicht eingeladen.");
        }
    }

    public static void kick(ProxiedPlayer pl, ProxiedPlayer p) {
        if (partyfuehrer.contains(pl.getName())) {
            if (inparty.containsKey(p.getName()) && inparty.get(p.getName()) == pl.getName()) {
                inparty.remove(p.getName());
                p.sendMessage("§7Du wurdest von §c" + pl.getName() + " §7aus der Party geworfen.");
                pl.sendMessage(" §7Der Spieler §c" + p.getName() + " §7wurde gekickt.");
                for (ProxiedPlayer ip : ProxyServer.getInstance().getPlayers()) {
                    if (!inparty.containsKey(ip.getName()) || inparty.get(ip.getName()) != pl.getName()) continue;
                    ip.sendMessage("§c" + p.getName() + " §7wurde von §c" + pl.getName() + " §7aus der Party geworfen.");
                }
            } else {
                pl.sendMessage("§7Dieser Spieler ist in keiner Party.");
            }
        } else {
            pl.sendMessage("§7Du bist in keiner Party.");
        }
    }
}
