package com.vildaberper.CommandPermissions;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerListener;

public class CommandPermissionsPlayerListener extends PlayerListener{
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event){
		if(!event.isCancelled()){
			if(!Perm.hasPermission(event.getPlayer().getWorld().getName(), event.getPlayer().getName(), event.getMessage().split(" ")[0].toLowerCase())){
				event.getPlayer().sendMessage(ChatColor.DARK_RED + "You do not have permission to do that.");
				event.setCancelled(true);
			}
		}
	}
}