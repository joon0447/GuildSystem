package org.joon1.guildsystem.Listener;

import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.joon1.guildsystem.GuildSystem;
import org.joon1.guildsystem.Prefix;
import org.joon1.guildsystem.data.DataManager;

import javax.xml.crypto.Data;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class GuildCreateListener implements Listener {

    private GuildSystem guildSystem;
    private DataManager dataManager;

    public GuildCreateListener(GuildSystem guildSystem, DataManager dataManager){
        this.guildSystem = guildSystem;
        this.dataManager = dataManager;
    }

    private void CreateGuild(String GuildName, UUID uuid){
        dataManager.createGuild(GuildName, uuid);
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        try{
            if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().equals(guildSystem.GuildTicket.getItemMeta()) &&
                    (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))){
                Player player = e.getPlayer();
                AnvilGUI builder = new AnvilGUI.Builder()
                        .onClick((slot, stateSnapshot) -> { // Either use sync or async variant, not both
                            if(slot != AnvilGUI.Slot.OUTPUT) {
                                return Collections.emptyList();
                            }
                            if(stateSnapshot.getText().length()>=2 && stateSnapshot.getText().length()<=6) {
                                String GuildName = stateSnapshot.getText();
                                CreateGuild(GuildName, player.getUniqueId());
                                stateSnapshot.getPlayer().sendMessage(GuildName + "길드를 만들었습니다.");
                                return Arrays.asList(AnvilGUI.ResponseAction.close());
                            } else {
                                player.sendMessage(Prefix.guildPrefix.getValue() + "길드 이름은 2~6 글자만 가능합니다.");
                                return Arrays.asList(AnvilGUI.ResponseAction.close());
                            }
                        })
                       // .text("What is the meaning of life?")                              //sets the text the GUI should start with
                        .title("길드 이름을 입력해주세요.")                                       //set the title of the GUI (only works in 1.14+)
                        .itemLeft(guildSystem.FindGuild)
                        .itemRight(guildSystem.GuildTicket)
                        .plugin(guildSystem)                                          //set the plugin instance
                        .open(player);

            }
        }catch (NullPointerException es){

        }

    }
}
