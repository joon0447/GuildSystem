package org.joon1.guildsystem;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.joon1.guildsystem.Listener.GuildServerJoinQuitListener;
import org.joon1.guildsystem.Listener.GuildMenuListener;
import org.joon1.guildsystem.Listener.GuildCreateListener;
import org.joon1.guildsystem.commands.BalanceCommand;
import org.joon1.guildsystem.commands.GuildMenuCommand;
import org.joon1.guildsystem.commands.TestCommand;
import org.joon1.guildsystem.data.DataManager;
import org.joon1.guildsystem.hook.VaultHook;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;


public final class GuildSystem extends JavaPlugin {
    private VaultHook vaultHook = new VaultHook();
    private DataManager dataManager = new DataManager();
    public File dataFolder = this.getDataFolder();
    public static HashMap<UUID, String> playerGuildMap = new HashMap<>();

    @Override
    public void onEnable() {
        vaultHook.setVault();
        dataManager.loadDataManager(this);
        dataManager.createFile(this);
        dataManager.loadFile(this);

        LoadItem();

        getCommand("bal").setExecutor(new BalanceCommand());
        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("길드").setExecutor(new GuildMenuCommand(this));

        Bukkit.getPluginManager().registerEvents(new GuildMenuListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GuildCreateListener(this, this.dataManager), this);
        Bukkit.getPluginManager().registerEvents(new GuildServerJoinQuitListener(this), this);
    }

    @Override
    public void onDisable() {
        System.out.println("종료");
    }


    public static int TicketPrice = 1;
    public ItemStack GuildTicket;  // 플레이어 지급용 길드 생성 티켓
    public ItemStack GuildTicketPrice; // 길드 메뉴 출력 길드 생성 티켓 (비용 포함 출력)
    public ItemStack FindGuild; // 길드 메뉴 - 길드 찾기 아이콘
    public ItemStack GuildUserManager; // 길드 메뉴 - 길드 인원 관리
    public ItemStack GuildUserList; // 길드 메뉴 - 길드 인원 관리 - 길드 인원 정보
    public ItemStack GuildUserKick; // 길드 메뉴 - 길드 인원 관리 - 길드 인원 추방
    public ItemStack GuildUserInvite; // 길드 메뉴 - 길드 인원 관리 - 길드 인원 신청함
    public ItemStack GuildSkill; // 길드 메뉴 - 길드 스킬
    public ItemStack GuildOpenTrue; // 길드 메뉴 - 길드 공개
    public ItemStack GuildOpenFalse; // 길드 메뉴 - 길드 비공개

    public void LoadItem(){
        GuildTicket = new ItemStack(Material.PAPER);
        ItemMeta GuildTicketMeta = GuildTicket.getItemMeta();
        GuildTicketMeta.setDisplayName(ChatColor.GREEN + "길드 생성하기");
        GuildTicketMeta.setLore(Arrays.asList(ChatColor.GRAY + "길드를 생성할 수 있는 티켓입니다.",
                ChatColor.GRAY + "우클릭으로 사용이 가능합니다."));
        GuildTicket.setItemMeta(GuildTicketMeta);

        GuildTicketPrice = new ItemStack(Material.PAPER);
        ItemMeta GuildTicketPriceMeta = GuildTicketPrice.getItemMeta();
        GuildTicketPriceMeta.setDisplayName(ChatColor.GREEN + "길드 생성하기");
        GuildTicketPriceMeta.setLore(Arrays.asList(ChatColor.GRAY + "길드를 생성할 수 있는 티켓입니다.",
                ChatColor.GRAY + "우클릭으로 사용이 가능합니다.",
                ChatColor.BLUE + "구매 비용 : " +TicketPrice + "원"));
        GuildTicketPrice.setItemMeta(GuildTicketPriceMeta);

        FindGuild = new ItemStack(Material.ENDER_EYE);
        ItemMeta FindGuildMeta = FindGuild.getItemMeta();
        FindGuildMeta.setDisplayName(ChatColor.GREEN + "길드 찾기");
        FindGuild.setItemMeta(FindGuildMeta);

        GuildUserManager = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta GuildUserManagerMeta = GuildUserManager.getItemMeta();
        GuildUserManagerMeta.setDisplayName(ChatColor.GREEN + "길드 인원 관리");
        GuildUserManager.setItemMeta(GuildUserManagerMeta);

        GuildUserList = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta GuildUserListMeta = GuildUserList.getItemMeta();
        GuildUserListMeta.setDisplayName(ChatColor.GREEN + "길드 인원 보기");
        GuildUserList.setItemMeta(GuildUserListMeta);

        GuildUserKick = new ItemStack(Material.RED_CONCRETE);
        ItemMeta GuildUserKickMeta = GuildUserKick.getItemMeta();
        GuildUserKickMeta.setDisplayName(ChatColor.GREEN + "길드 인원 추방");
        GuildUserKick.setItemMeta(GuildUserKickMeta);

        GuildUserInvite = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta GuildUserInviteMeta = GuildUserInvite.getItemMeta();
        GuildUserInviteMeta.setDisplayName(ChatColor.GREEN + "길드 가입 신청함");
        GuildUserInvite.setItemMeta(GuildUserInviteMeta);

        GuildSkill = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta GuildSkillMeta = GuildSkill.getItemMeta();
        GuildSkillMeta.setDisplayName(ChatColor.GREEN + "길드 스킬");
        GuildSkillMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        GuildSkill.setItemMeta(GuildSkillMeta);

        GuildOpenTrue= new ItemStack(Material.ANVIL);
        ItemMeta GuildSettingMeta = GuildOpenTrue.getItemMeta();
        GuildSettingMeta.setDisplayName(ChatColor.GREEN + "길드 찾기 노출 허용");
        GuildSettingMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "좌클릭으로 길드 찾기 노출 여부를 설정 가능합니다."));
        GuildOpenTrue.setItemMeta(GuildSettingMeta);

        GuildOpenFalse = new ItemStack(Material.ANVIL);
        ItemMeta GuildOpenFalseMeta = GuildOpenFalse.getItemMeta();
        GuildOpenFalseMeta.setDisplayName(ChatColor.RED + "길드 찾기 노출 거부 ");
        GuildOpenFalseMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "좌클릭으로 길드 찾기 노출 여부를 설정 가능합니다."));
        GuildOpenFalse.setItemMeta(GuildOpenFalseMeta);
    }

    public void putPlayerGuildMap(UUID uuid, String guild){
        playerGuildMap.put(uuid, guild);
    }

}
