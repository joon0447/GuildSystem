package org.joon1.guildsystem.data;


import org.bukkit.configuration.file.YamlConfiguration;
import org.joon1.guildsystem.GuildSystem;

import java.io.File;
import java.util.UUID;

public class DataManager {
    static public File isPlayerJoin;
    static public File folder;

    public void createFile(GuildSystem guildSystem){
        folder = new File(guildSystem.getDataFolder(), "PlayerList");
        if(!folder.exists()){
            folder.mkdirs();
        }
    }

    public void loadFile(GuildSystem guildSystem){
        File files[] = folder.listFiles();
        for(File f : files) {
            YamlConfiguration yc = YamlConfiguration.loadConfiguration(f);
            UUID uuid = UUID.fromString(yc.getString("UUID"));
            String guild = yc.getString("Guild");
            if(guild != null){ // 길드가 있는 경우
                guildSystem.putPlayerGuildMap(uuid, guild);
            }
        }
        System.out.println("길드 시스템 데이터 로드 완료");
    }
}
