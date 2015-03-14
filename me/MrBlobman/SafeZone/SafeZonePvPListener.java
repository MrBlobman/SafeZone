package me.MrBlobman.SafeZone;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.protection.events.DisallowedPVPEvent;

public class SafeZonePvPListener implements Listener{
	
	@EventHandler
	public void wgDamageEvent(DisallowedPVPEvent event){
		Player playerHit = event.getDefender();
		if (SafeZone.safeZoneWarningTasks.containsKey(playerHit)){
			event.setCancelled(true);
		}
	}
}
