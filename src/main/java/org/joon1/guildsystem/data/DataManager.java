package org.joon1.guildsystem.data;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.joon1.guildsystem.GuildSystem;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DataManager {

    static public File isPlayerJoin;
    static public File PlayerListFolder;
    static public File GuildListFolder;

    private GuildSystem guildSystem;

    public void loadDataManager(GuildSystem guildSystem){
        this.guildSystem = guildSystem;
    }

    public void createFile(GuildSystem guildSystem){
        PlayerListFolder = new File(guildSystem.getDataFolder(), "PlayerList");
        if(!PlayerListFolder.exists()){
            PlayerListFolder.mkdirs();
        }

        GuildListFolder = new File(guildSystem.getDataFolder(), "GuildList");
        if(!GuildListFolder.exists()){
            GuildListFolder.mkdirs();
        }
    }

    public void loadFile(GuildSystem guildSystem){
//        File files[] = folder.listFiles();
//        for(File f : files) {
//            YamlConfiguration yc = YamlConfiguration.loadConfiguration(f);
//            UUID uuid = UUID.fromString(yc.getString("UUID"));
//            String guild = yc.getString("Guild");
//            if(guild != null){ // 길드가 있는 경우
//                guildSystem.putPlayerGuildMap(uuid, guild);
//            }
//        }
        System.out.println("길드 시스템 데이터 로드 완료");
    }

    public void createGuild(String guildName, UUID uuid){
        File file = new File(guildSystem.getDataFolder(), "PlayerList/" + uuid + ".yml");
        YamlConfiguration playerInfoYml = YamlConfiguration.loadConfiguration(file);
        playerInfoYml.set("Guild", guildName);
        guildSystem.putPlayerGuildMap(uuid, guildName);
        try{
            playerInfoYml.save(file);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        File GuildFile = new File(guildSystem.getDataFolder(), "GuildList/" + guildName + ".yml");
        YamlConfiguration GuildFileYml = YamlConfiguration.loadConfiguration(GuildFile);
        GuildFileYml.set("Guild Name", guildName);
        GuildFileYml.set("Guild Leader (Name)", Bukkit.getOfflinePlayer(uuid).getName());
        GuildFileYml.set("Guild Leader (UUID)", uuid.toString());
        GuildFileYml.set("Guild Level", 1);
        try{
            GuildFileYml.save(GuildFile);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
