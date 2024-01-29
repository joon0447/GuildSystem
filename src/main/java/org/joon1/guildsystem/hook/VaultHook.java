package org.joon1.guildsystem.hook;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.joon1.guildsystem.GuildSystem;

import static org.bukkit.Bukkit.getServer;

public class VaultHook {
    private GuildSystem guildSystem;
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    public void setVault(){
        if (!setupEconomy() ) {
            System.out.println("Vault 플러그인이 없습니다!");
            getServer().getPluginManager().disablePlugin(this.guildSystem);
            return;
        }
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }
}
