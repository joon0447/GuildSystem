package org.joon1.guildsystem.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

public class CheckGuild implements Listener {

    public static HashMap<UUID, String> CheckGuild = new HashMap<>();
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

    }
}
