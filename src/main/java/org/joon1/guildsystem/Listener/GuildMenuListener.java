package org.joon1.guildsystem.Listener;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.joon1.guildsystem.GuildSystem;
import org.joon1.guildsystem.Prefix;
import org.joon1.guildsystem.commands.GuildMenuCommand;
import org.joon1.guildsystem.hook.VaultHook;

import java.util.Arrays;

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
                    break;
            }
        }
        else if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.GREEN.toString() + ChatColor.BOLD + "길드 마스터 메뉴")){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            switch (e.getRawSlot()){
                case 10:
                    break;
                case 12:
                    break;
            }
        } else if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.GREEN.toString() + ChatColor.BOLD + "길드 메뉴")){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            switch (e.getRawSlot()){

            }
        }
    }
}
