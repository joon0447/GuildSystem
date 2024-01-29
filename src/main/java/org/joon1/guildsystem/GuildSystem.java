package org.joon1.guildsystem;


import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.joon1.guildsystem.commands.BalanceCommand;
import org.joon1.guildsystem.commands.TestCommand;
import org.joon1.guildsystem.hook.VaultHook;


public final class GuildSystem extends JavaPlugin {
    VaultHook vaultHook = new VaultHook();
    @Override
    public void onEnable() {
        vaultHook.setVault();
        getCommand("bal").setExecutor(new BalanceCommand());
        getCommand("test").setExecutor(new TestCommand());
    }


    @Override
    public void onDisable() {
        System.out.println("[GuildSystem] 종료");
    }

}
