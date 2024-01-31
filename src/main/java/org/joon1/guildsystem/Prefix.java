package org.joon1.guildsystem;

import org.bukkit.ChatColor;


public enum Prefix {

    prefix("[Guild System]"),
    guildPrefix(ChatColor.YELLOW + "[ 길드 ] " + ChatColor.WHITE);
    private final String value;
    Prefix(String value) { this.value = value;}
    public String getValue() { return value; }

}


