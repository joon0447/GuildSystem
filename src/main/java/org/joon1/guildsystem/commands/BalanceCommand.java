package org.joon1.guildsystem.commands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.joon1.guildsystem.hook.VaultHook;

public class BalanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Economy economy = VaultHook.getEconomy();
            player.sendMessage("현재 돈 : " + economy.getBalance(player));
        }
        return false;
    }
}
