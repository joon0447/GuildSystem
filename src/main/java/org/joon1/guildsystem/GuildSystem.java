package org.joon1.guildsystem;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;
import org.joon1.guildsystem.Listener.CheckGuild;
import org.joon1.guildsystem.Listener.GuildMenuListener;
import org.joon1.guildsystem.commands.BalanceCommand;
import org.joon1.guildsystem.commands.GuildMenuCommand;
import org.joon1.guildsystem.commands.TestCommand;
import org.joon1.guildsystem.data.DataManager;
import org.joon1.guildsystem.hook.VaultHook;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public final class GuildSystem extends JavaPlugin {
    private VaultHook vaultHook = new VaultHook();
    private DataManager dataManager = new DataManager();
    public File dataFolder = this.getDataFolder();

    public static HashMap<UUID, String> playerGuildMap = new HashMap<>();

    @Override
    public void onEnable() {
        vaultHook.setVault();
        dataManager.createFile(this);
        dataManager.loadFile(this);
        getCommand("bal").setExecutor(new BalanceCommand());
        getCommand("test").setExecutor(new TestCommand());
        getCommand("길드").setExecutor(new GuildMenuCommand(this));

        Bukkit.getPluginManager().registerEvents(new CheckGuild(), this);
        Bukkit.getPluginManager().registerEvents(new GuildMenuListener(), this);
    }

    public void putPlayerGuildMap(UUID uuid, String guild){
        playerGuildMap.put(uuid, guild);
    }

    @Override
    public void onDisable() {
        System.out.println("종료");
    }

}
