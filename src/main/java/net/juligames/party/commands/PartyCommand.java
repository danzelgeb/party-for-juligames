package net.juligames.party.commands;

import net.juligames.party.api.PartyAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.HashMap;

public class PartyCommand extends Command {

    public PartyCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer p = (ProxiedPlayer) commandSender;
        if (!(commandSender.hasPermission("juligames.party"))) commandSender.sendMessage("§cDazu besitzt du keine Rechte.");
        if (!(commandSender instanceof ProxiedPlayer)) commandSender.sendMessage("§cDieser Command ist nich für die Konsole geeignet!");

        if (!(args.length > 1)) commandSender.sendMessage("§cUsage: /party <create|join|invite|leave|end|kick> [player]");

        if (args[0].equalsIgnoreCase("create")) {
            PartyAPI.neueParty(p);
        } else if (args[0].equalsIgnoreCase("join")) {

        }

    }
}
