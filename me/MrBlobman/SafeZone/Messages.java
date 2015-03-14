package me.MrBlobman.SafeZone;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

public class Messages {
	public static String prefix;
	private static Configuration config;
	
	Messages(Configuration configuration){
		config = configuration;
		if (config.contains("Messages.ChatPrefix")){
			prefix = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.ChatPrefix"));
		}else{
			prefix = "";
		}
	}
	
	public static void safeIn(Player player, String safeZoneTime){
		if (config.contains("Messages.NotSafe")){
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.NotSafe").replace("<prefix>", prefix).replace("<safeZoneTime>", safeZoneTime)));
		}
	}
	
	public static void safe(Player player){
		if (config.contains("Messages.Safe")){
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.Safe").replace("<prefix>", prefix)));
		}
	}
	
	public static void countdownCancelled(Player player){
		if (config.contains("Messages.CountdownCancelled")){
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Messages.CountdownCancelled").replace("<prefix>", prefix)));
		}
	}
}
