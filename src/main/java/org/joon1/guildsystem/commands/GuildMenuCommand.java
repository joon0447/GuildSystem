package org.joon1.guildsystem.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.joon1.guildsystem.GuildSystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuildMenuCommand implements CommandExecutor {
    private GuildSystem guildSystem;
    public GuildMenuCommand(GuildSystem guildSystem){
        this.guildSystem = guildSystem;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            File file = new File(guildSystem.getDataFolder(), "PlayerList/" + player.getUniqueId() + ".yml");
            if(!file.exists()){ // PlayerList에 정보가 없을 때 첫 파일 생성
                YamlConfiguration playerInfoYml = YamlConfiguration.loadConfiguration(file);
                playerInfoYml.set("UUID", player.getUniqueId().toString());
                playerInfoYml.set("Player name", player.getName());
                try{
                    playerInfoYml.save(file);
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }

            boolean check = false;
            boolean master = false;
            YamlConfiguration playerInfoYml = YamlConfiguration.loadConfiguration(file);
            if(playerInfoYml.getString("Guild") != null){
                guildSystem.putPlayerGuildMap(player.getUniqueId(), playerInfoYml.getString("Guild"));
                check = true;
                File gFile = new File(guildSystem.getDataFolder(), "GuildList/" + playerInfoYml.getString("Guild") + ".yml");
                YamlConfiguration gyc = YamlConfiguration.loadConfiguration(gFile);
                if(gyc.getString("Guild Leader (UUID)").equals(player.getUniqueId().toString())){
                    master = true;
                    check = false;
                }
            }

            if(master){ //플레이어가 길드 마스터일때
                Inventory guildMenu = Bukkit.createInventory(player, 27, ChatColor.GREEN.toString() + ChatColor.BOLD + "길드 마스터 메뉴");
                File GuildYml = new File(guildSystem.getDataFolder(), "GuildList/" + guildSystem.playerGuildMap.get(player.getUniqueId()) + ".yml");
                YamlConfiguration yc = YamlConfiguration.loadConfiguration(GuildYml);
                String GuildName = yc.getString("Guild Name");
                String Leader = yc.getString("Guild Leader (Name)");
                String GuildLevel = yc.getString("Guild Level");

                ItemStack GuildInfo = new ItemStack(Material.BOOK);
                ItemMeta GuildInfoMeta = GuildInfo.getItemMeta();
                GuildInfoMeta.setDisplayName(ChatColor.GREEN + "길드 정보");
                GuildInfoMeta.setLore(Arrays.asList(ChatColor.WHITE + "길드 이름 : " + GuildName, ChatColor.WHITE + "길드장 : " + Leader , ChatColor.WHITE + "길드 레벨 : " + GuildLevel));
                GuildInfo.setItemMeta(GuildInfoMeta);
                guildMenu.setItem(10, GuildInfo);
                guildMenu.setItem(12, guildSystem.GuildUserManager);
                guildMenu.setItem(14, guildSystem.GuildSkill);
                guildMenu.setItem(16, guildSystem.GuildSetting);
                player.openInventory(guildMenu);
            }else if(check){ //플레이어가 길드원일때
                Inventory guildMenu = Bukkit.createInventory(player, 27, ChatColor.GREEN.toString() + ChatColor.BOLD + "길드 메뉴");
                File GuildYml = new File(guildSystem.getDataFolder(), "GuildList/" + guildSystem.playerGuildMap.get(player.getUniqueId()) + ".yml");
                YamlConfiguration yc = YamlConfiguration.loadConfiguration(GuildYml);
                String GuildName = yc.getString("Guild Name");
                String Leader = yc.getString("Guild Leader (Name)");
                String GuildLevel = yc.getString("Guild Level");

                ItemStack GuildInfo = new ItemStack(Material.BOOK);
                ItemMeta GuildInfoMeta = GuildInfo.getItemMeta();
                GuildInfoMeta.setDisplayName(ChatColor.GREEN + "길드 정보");
                GuildInfoMeta.setLore(Arrays.asList(ChatColor.WHITE + "길드 이름 : " + GuildName, ChatColor.WHITE + "길드장 : " + Leader , ChatColor.WHITE + "길드 레벨 : " + GuildLevel));
                GuildInfo.setItemMeta(GuildInfoMeta);
                guildMenu.setItem(10, GuildInfo);
                guildMenu.setItem(12, guildSystem.GuildSkill);
            }
            else{ // 플레이어 길드가 없을 때
                Inventory guildMenu = Bukkit.createInventory(player, 27, ChatColor.GREEN.toString() + ChatColor.BOLD +"길드에 참여하거나 만들어 보세요!");
                guildMenu.setItem(12, guildSystem.GuildTicketPrice);
                guildMenu.setItem(14, guildSystem.FindGuild);
                player.openInventory(guildMenu);
            }

        }

        return false;
    }

}
