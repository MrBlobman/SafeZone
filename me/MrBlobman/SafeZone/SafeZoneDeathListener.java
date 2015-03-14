package me.MrBlobman.SafeZone;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SafeZoneDeathListener implements Listener{
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		if (SafeZone.safeZoneWarningTasks.containsKey(event.getEntity())){
			SafeZone.safeZoneWarningTasks.get(event.getEntity()).cancelTask();
			if (SafeZone.alreadyWaitedCoutndown.contains(event.getEntity())){
				SafeZone.alreadyWaitedCoutndown.remove(event.getEntity());
			}
		}
	}
}
