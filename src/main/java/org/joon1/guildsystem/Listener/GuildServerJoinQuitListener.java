package org.joon1.guildsystem.Listener;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.joon1.guildsystem.GuildSystem;

import java.io.File;
import java.util.UUID;

public class GuildServerJoinQuitListener implements Listener {
    private GuildSystem guildSystem;
    public GuildServerJoinQuitListener(GuildSystem guildSystem){
        this.guildSystem = guildSystem;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        File file = new File(guildSystem.getDataFolder(), "PlayerList/" + uuid + ".yml");
        if(file.exists()){
            YamlConfiguration playerInfoYml = YamlConfiguration.loadConfiguration(file);
            String guild = playerInfoYml.getString("Guild");
            if(guild != null){
                guildSystem.putPlayerGuildMap(uuid, guild);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if(guildSystem.playerGuildMap.containsKey(uuid)){
            guildSystem.playerGuildMap.remove(uuid);
        }
    }
}
