package me.MrBlobman.SafeZone;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class SafeZoneCountdown implements Runnable{
	Player player;
	int countdown;
	BukkitTask task;
	
	@Override
	public void run() {
		if (countdown > 0){
			Messages.safeIn(player, String.valueOf(countdown));
			countdown--;
		}else{
			Messages.safe(player);
			SafeZone.alreadyWaitedCoutndown.add(player);
			this.task.cancel();
			if (SafeZone.safeZoneWarningTasks.containsKey(player)){
				SafeZone.safeZoneWarningTasks.remove(player);
			}
		}
	}
	
	SafeZoneCountdown(Player player, int countdownLength, Plugin plugin){
		this.player = player;
		this.countdown = countdownLength;
		this.task = Bukkit.getScheduler().runTaskTimer(plugin, this, 0L, 20L);
	}
	
	public void cancelTask(){
		this.task.cancel();
		if (SafeZone.safeZoneWarningTasks.containsKey(player)){
			SafeZone.safeZoneWarningTasks.remove(player);
		}
	}

}
