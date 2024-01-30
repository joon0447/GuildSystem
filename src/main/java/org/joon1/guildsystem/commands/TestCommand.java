package org.joon1.guildsystem.commands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joon1.guildsystem.hook.VaultHook;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Economy eco = VaultHook.getEconomy();
            Player player = (Player) commandSender;
            eco.depositPlayer(player, 10.0);
            player.sendMessage("10원 증가");
        }
        return false;
    }
}
