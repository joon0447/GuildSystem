package org.joon1.guildsystem.Listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.joon1.guildsystem.Prefix;
import org.joon1.guildsystem.commands.GuildMenuCommand;

import java.util.Arrays;

public class GuildMenuListener implements Listener {

    private ItemStack GuildTicket;
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(ChatColor.translateAlternateColorCodes('&',e.getView().getTitle()).equals(ChatColor.GREEN.toString() + ChatColor.BOLD + "길드 메뉴")){
            e.setCancelled(true);
            Player player = (Player) e.getWhoClicked();
            switch (e.getRawSlot()){
                case 12: //길드 생성 티켓 구매
                    GuildTicket = new ItemStack(Material.PAPER);
                    ItemMeta GuildTicketMeta = GuildTicket.getItemMeta();
                    GuildTicketMeta.setDisplayName(ChatColor.GREEN + "길드 생성하기");
                    GuildTicketMeta.setLore(Arrays.asList(ChatColor.GRAY + "길드를 생성할 수 있는 티켓입니다.",
                            ChatColor.GRAY + "우클릭으로 사용이 가능합니다."));
                    GuildTicket.setItemMeta(GuildTicketMeta);
                    player.getInventory().addItem(GuildTicket);
                    player.sendMessage(Prefix.guildPrefix + "길드 생성권을 구매하였습니다.");
                    break;
            }
            player.closeInventory();

        }
    }
}
