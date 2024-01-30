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

            Inventory guildMenu = Bukkit.createInventory(player, 27, ChatColor.GREEN.toString() + ChatColor.BOLD + "길드 메뉴");
            if(guildSystem.playerGuildMap.containsKey(player.getUniqueId())){ //플레이어 길드가 있을 때

            }else{ // 플레이어 길드가 없을 때
                ItemStack GuildTicket = new ItemStack(Material.PAPER);
                ItemMeta GuildTicketMeta = GuildTicket.getItemMeta();
                GuildTicketMeta.setDisplayName(ChatColor.GREEN + "길드 생성하기");
                GuildTicketMeta.setLore(Arrays.asList(ChatColor.GRAY + "길드를 생성할 수 있는 티켓입니다.",
                        ChatColor.GRAY + "우클릭으로 사용이 가능합니다.",
                        ChatColor.BLUE + "구매 비용 : 1원"));
                GuildTicket.setItemMeta(GuildTicketMeta);
                guildMenu.setItem(12, GuildTicket);

                ItemStack FindGuild = new ItemStack(Material.ENDER_EYE);
                ItemMeta FindGuildMeta = FindGuild.getItemMeta();
                FindGuildMeta.setDisplayName(ChatColor.GREEN + "길드 찾기");
                FindGuild.setItemMeta(FindGuildMeta);
                guildMenu.setItem(14, FindGuild);

                player.openInventory(guildMenu);
            }
        }

        return false;
    }

    public void checkPlayer(){

    }
}
