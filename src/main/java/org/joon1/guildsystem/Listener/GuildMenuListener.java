package org.joon1.guildsystem.Listener;

import com.sun.org.apache.xpath.internal.operations.Bool;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.checkerframework.checker.units.qual.A;
import org.joon1.guildsystem.GuildSystem;
import org.joon1.guildsystem.Prefix;
import org.joon1.guildsystem.commands.GuildMenuCommand;
import org.joon1.guildsystem.hook.VaultHook;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

public class GuildMenuListener implements Listener {

    private GuildSystem guildSystem;
    public GuildMenuListener(GuildSystem guildSystem){
        this.guildSystem = guildSystem;
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.GREEN.toString() + ChatColor.BOLD +"길드에 참여하거나 만들어 보세요!")){ //길드 소속 X 메뉴
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            switch (e.getRawSlot()){
                case 12: //길드 생성 티켓 구매
                    Economy eco = VaultHook.getEconomy();
                    eco.withdrawPlayer(player, guildSystem.TicketPrice);
                    player.getInventory().addItem(guildSystem.GuildTicket);
                    player.sendMessage(Prefix.guildPrefix.getValue() + guildSystem.TicketPrice + "원을 지불하고 길드 생성권을 구매했습니다.");
                    player.closeInventory();
                    break;
                case 14: // 길드찾기
                    Inventory inv = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "길드 찾기");
                    File file = new File(guildSystem.getDataFolder(), "GuildList");
                    int seat = 0;
                    for(File f : file.listFiles()){
                        YamlConfiguration yc = YamlConfiguration.loadConfiguration(f);
                        boolean find = yc.getBoolean("FindGuild");
                        if(find){
                            String name = yc.getString("Guild Name");
                            UUID uuid = UUID.fromString(yc.getString("Guild Leader (UUID)"));
                            Player master = Bukkit.getPlayer(uuid);
                            ItemStack guild = new ItemStack(Material.END_CRYSTAL);
                            ItemMeta guildMeta = guild.getItemMeta();
                            guildMeta.setDisplayName(ChatColor.GREEN + name);
                            guildMeta.setLore(Arrays.asList(ChatColor.AQUA + "길드장 : " + master.getName(), ChatColor.DARK_GRAY + "좌클릭으로 가입신청하기"));
                            guild.setItemMeta(guildMeta);
                            inv.setItem(seat, guild);
                            seat++;
                        }
                    }
                    player.openInventory(inv);
                    break;
            }
        }
        else if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.GREEN.toString() + ChatColor.BOLD + "길드 마스터 메뉴")){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            switch (e.getRawSlot()){
                case 10: // 길드 정보
                    break;
                case 12: // 길드 인원 관리
                    Inventory inv = Bukkit.createInventory(player, 27, ChatColor.DARK_GRAY + "길드 인원 관리");
                    inv.setItem(11, guildSystem.GuildUserList);
                    inv.setItem(13, guildSystem.GuildUserKick);
                    inv.setItem(15, guildSystem.GuildUserInvite);
                    player.openInventory(inv);
                    break;
                case 14: // 길드 스킬
                    break;
                case 16: // 길드 공개 여부
                    File file = new File(guildSystem.getDataFolder(), "GuildList/" + guildSystem.playerGuildMap.get(player.getUniqueId()) + ".yml");
                    YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
                    Boolean find = yc.getBoolean("FindGuild");
                    if(find) player.sendMessage("길드 찾기 창에서의 노출을 비활성화합니다.");
                    else player.sendMessage("길드 찾기 창에서의 노출을 활성화합니다.");
                    yc.set("FindGuild", !find);
                    try{
                        yc.save(file);
                    }catch (IOException ex){
                        throw new RuntimeException(ex);
                    }
                    player.closeInventory();
                    break;
            }
        } else if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.GREEN.toString() + ChatColor.BOLD + "길드 메뉴")){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            switch (e.getRawSlot()){
            }
        } else if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.DARK_GRAY + "길드 인원 관리")){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            switch (e.getRawSlot()){
                case 11: // 길드 인원 관리
                    Inventory inv = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "길드 인원");
                    File file = new File(guildSystem.getDataFolder(), "GuildList/" + guildSystem.playerGuildMap.get(player.getUniqueId()) + ".yml");
                    YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
                    ConfigurationSection section = yc.getConfigurationSection("Players");
                    int countSeat = 0;
                    for(String user : section.getKeys(false)){
                        UUID uuid = UUID.fromString(user);
                        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                        headMeta.setOwningPlayer(Bukkit.getPlayer(uuid));
                        if(Bukkit.getOfflinePlayer(uuid).isOnline()){
                            headMeta.setDisplayName(ChatColor.GREEN + Bukkit.getPlayer(uuid).getName());
                            if(user.equals(yc.getString("Guild Leader (UUID)"))){
                                headMeta.setLore(Arrays.asList(ChatColor.GREEN + "온라인", ChatColor.LIGHT_PURPLE + "길드장"));
                            }else{
                                headMeta.setLore(Arrays.asList(ChatColor.GREEN + "온라인"));
                            }
                        }else{
                            headMeta.setDisplayName(ChatColor.GREEN + Bukkit.getPlayer(uuid).toString());
                            if(user.equals(yc.getString("Guild Leader (UUID)"))){
                                headMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "오프라인", ChatColor.LIGHT_PURPLE + "길드장"));
                            }else{
                                headMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "오프라인"));
                            }
                        }
                        head.setItemMeta(headMeta);
                        inv.setItem(countSeat, head);
                        countSeat++;
                    }
                    player.openInventory(inv);
                    break;
                case 13: // 길드 인원 추방
                    Inventory KickInv = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "길드 인원 추방");
                    File KickFile = new File(guildSystem.getDataFolder(), "GuildList/" + guildSystem.playerGuildMap.get(player.getUniqueId()) + ".yml");
                    YamlConfiguration KickYc = YamlConfiguration.loadConfiguration(KickFile);
                    ConfigurationSection KickSec = KickYc.getConfigurationSection("Players");
                    int kickSeat = 0;
                    for(String user : KickSec.getKeys(false)){
                        UUID uuid = UUID.fromString(user);
                        if(user.equals(KickYc.getString("Guild Leader (UUID)"))){
                            continue;
                        }
                        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
                        headMeta.setOwningPlayer(Bukkit.getPlayer(uuid));
                        if(Bukkit.getOfflinePlayer(uuid).isOnline()){
                            headMeta.setDisplayName(ChatColor.GREEN + Bukkit.getPlayer(uuid).getName());
                            headMeta.setLore(Arrays.asList(ChatColor.GREEN + "온라인"));

                        }else{
                            headMeta.setDisplayName(ChatColor.GREEN + Bukkit.getPlayer(uuid).toString());
                            headMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "오프라인"));
                        }
                        head.setItemMeta(headMeta);
                        KickInv.setItem(kickSeat, head);
                        kickSeat++;
                    }
                    player.openInventory(KickInv);
                    break;
                case 15: // 길드 인원 초대
                    Inventory inviteInv = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "길드 가입 신청함");
                    int invCount = 0;
                    File inviteFile = new File(guildSystem.getDataFolder(), "GuildList/" + guildSystem.playerGuildMap.get(player.getUniqueId()) + ".yml");
                    YamlConfiguration fyc = YamlConfiguration.loadConfiguration(inviteFile);
                    if(fyc.getList("Apply Players") != null) {
                        for (Object s : fyc.getList("Apply Players")) {
                            Player p = Bukkit.getPlayer(UUID.fromString(s.toString()));
                            ItemStack inviteHead = new ItemStack(Material.PLAYER_HEAD);
                            SkullMeta inviteHeadMeta = (SkullMeta) inviteHead.getItemMeta();
                            inviteHeadMeta.setOwningPlayer(p);
                            inviteHeadMeta.setDisplayName(p.getName());
                            inviteHeadMeta.setLore(Arrays.asList(ChatColor.GRAY + "좌클릭으로 수락하기"));
                            inviteHead.setItemMeta(inviteHeadMeta);
                            inviteInv.setItem(invCount, inviteHead);
                            invCount++;
                        }
                    }else{

                    }
//                    for(File f : inviteFile.listFiles()){
//                        Player inviteP = Bukkit.getPlayer(UUID.fromString(fyc.getString("UUID")));
//                        if(fyc.getString("Guild") == null){
//
//                            inviteHeadMeta.setDisplayName(ChatColor.GREEN + inviteP.getName());
//                            inviteHeadMeta.setLore(Arrays.asList(ChatColor.GRAY + "좌클릭으로 초대하기"));
//
//
//                        }
//                    }
                    player.openInventory(inviteInv);
                    break;
            }
        }else if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.DARK_GRAY + "길드 인원")){
            e.setCancelled(true);
        }else if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.DARK_GRAY + "길드 인원 추방")){
            e.setCancelled(true);
        }else if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.DARK_GRAY + "길드 가입 신청함")){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
        }else if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.DARK_GRAY + "길드 찾기")){
            e.setCancelled(true);
            if(e.getCurrentItem() != null){
                String GuildName = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
                Player player = (Player) e.getWhoClicked();
                File my = new File(guildSystem.getDataFolder(), "PlayerList/" + player.getUniqueId() + ".yml");
                YamlConfiguration playeryc = YamlConfiguration.loadConfiguration(my);
                playeryc.set("FindGuild", true);
                try{
                    playeryc.save(my);
                }catch (IOException ex){
                    throw new RuntimeException(ex);
                }

                File guild = new File(guildSystem.getDataFolder(), "GuildList/" + GuildName + ".yml");
                YamlConfiguration guildyc = YamlConfiguration.loadConfiguration(guild);
                ArrayList<String> playerList = new ArrayList<>();
                if(guildyc.getList("Apply Players") != null){
                    for(Object s : guildyc.getList("Apply Players")){
                        playerList.add(s.toString());
                    }
                    playerList.add(player.getUniqueId().toString());
                    player.sendMessage("1234");
                }else{
                    playerList.add(player.getUniqueId().toString());
                    player.sendMessage("7777");
                }
                guildyc.set("Apply Players", playerList);
                try{
                    guildyc.save(guild);
                }catch (IOException ex){
                    throw new RuntimeException(ex);
                }
                player.closeInventory();
            }
        }
    }
}
