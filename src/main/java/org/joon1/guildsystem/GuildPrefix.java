package org.joon1.guildsystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.ArrayList;

public class GuildPrefix{

    ArrayList<Team> guildList = new ArrayList<>();
    Scoreboard scoreboard = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
    public void addPrefix(GuildSystem guildSystem){
        Bukkit.getServer().getScheduler().runTaskTimer(guildSystem, new Runnable(){
            @Override
            public void run(){
                Team none = scoreboard.getTeam("none");
                if(none == null){
                    none = scoreboard.registerNewTeam("none");
                }
                none.setPrefix("");
                for (Player player : Bukkit.getServer().getOnlinePlayers()){
                    String uuid = player.getUniqueId().toString();
                    File file = new File(guildSystem.getDataFolder(), "PlayerList/" + uuid + ".yml");
                    try{
                        if(file.exists()){
                            YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
                            String guild = yml.getString("Guild");
                            if(guild != null){
                                Team team = scoreboard.getTeam(guild);
                                if (team == null){
                                    scoreboard.registerNewTeam(guild);
                                }
                                team.setPrefix(ChatColor.GREEN +"[" + guild + "] ");
                                if(!guildList.contains(team)){
                                    guildList.add(team);
                                }else{
                                    int index = guildList.indexOf(team);
                                    team = guildList.get(index);
                                }
                                team.addEntry(player.getName());
                            }
                        }else{
                            none.addEntry(player.getName());
                        }
                    }catch (NullPointerException e){
                        throw e;
                    }
                }
            }
        }, 0, 20);
    }

}
