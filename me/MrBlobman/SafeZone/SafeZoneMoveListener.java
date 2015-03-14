package me.MrBlobman.SafeZone;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

public class SafeZoneMoveListener implements Listener{
	private SafeZone plugin;
	private WorldGuardPlugin wg;
	
	SafeZoneMoveListener(SafeZone instance, WorldGuardPlugin wgInstance){
		this.plugin = instance;
		this.wg = wgInstance;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event){
		final Player player = event.getPlayer();
		if (plugin.getTaggedPlayers().containsKey(player.getName()) || SafeZone.safeZoneWarningTasks.containsKey(player)){
			//Player moving is tagged for combat
			if (!wg.getRegionContainer().createQuery().testState(player.getLocation(), player, DefaultFlag.PVP)){
				//Move location is a safe zone
				if (!SafeZone.safeZoneWarningTasks.containsKey(player) && !SafeZone.alreadyWaitedCoutndown.contains(player)){
					//First time entering the safe zone, has no task associated with them
					SafeZone.safeZoneWarningTasks.put(player, new SafeZoneCountdown(player, SafeZone.safeZoneTime, plugin));
				}
			}else {
				//Move location is a pvp zone
				if (SafeZone.alreadyWaitedCoutndown.contains(player)){
					SafeZone.alreadyWaitedCoutndown.remove(player);
				}
				if (SafeZone.safeZoneWarningTasks.containsKey(player)){
					//player was not finished the countdown from previous safe zone cooldown and went back into pvp
					Messages.countdownCancelled(player);
					SafeZone.safeZoneWarningTasks.get(player).cancelTask();
				}
			}
		}
	}
	
	@EventHandler
	public void onTp(PlayerTeleportEvent event){
		final Player player = event.getPlayer();
		if (plugin.getTaggedPlayers().containsKey(player.getName()) || SafeZone.safeZoneWarningTasks.containsKey(player)){
			//Player moving is tagged for combat
			if (!wg.getRegionContainer().createQuery().testState(player.getLocation(), player, DefaultFlag.PVP)){
				//Move location is a safe zone
				if (!SafeZone.safeZoneWarningTasks.containsKey(player)){
					//First time entering the safe zone, has no task associated with them
					SafeZone.safeZoneWarningTasks.put(player, new SafeZoneCountdown(player, SafeZone.safeZoneTime, plugin));
				}
			}else {
				//Move location is a pvp zone
				if (SafeZone.alreadyWaitedCoutndown.contains(player)){
					SafeZone.alreadyWaitedCoutndown.remove(player);
				}
				if (SafeZone.safeZoneWarningTasks.containsKey(player)){
					//player was not finished the countdown from previous safe zone cooldown and went back into pvp
					Messages.countdownCancelled(player);
					SafeZone.safeZoneWarningTasks.get(player).cancelTask();
				}
			}
		}
	}
}
