package org.joon1.guildsystem.commands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.joon1.guildsystem.GuildSystem;
import org.joon1.guildsystem.hook.VaultHook;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class TestCommand implements CommandExecutor {
    private GuildSystem guildSystem;

    public TestCommand(GuildSystem guildSystem){
        this.guildSystem = guildSystem;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Economy eco = VaultHook.getEconomy();
            Player player = (Player) commandSender;
            eco.depositPlayer(player, 10.0);
            player.sendMessage("10원 증가");
            player.sendMessage(Integer.toString(guildSystem.playerGuildMap.size()));
            for(UUID u : guildSystem.playerGuildMap.keySet()){
                player.sendMessage(u.toString());
                player.sendMessage(guildSystem.playerGuildMap.get(u));
            }

        }
        return false;
    }
}
