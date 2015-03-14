package me.MrBlobman.SafeZone;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.jackproehl.plugins.CombatLog;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class SafeZone extends JavaPlugin{
	Logger logger = Logger.getLogger("Minecraft");
	//Hooks
	private CombatLog cl;
	private WorldGuardPlugin wg;
	//Maps and lists
	public static HashMap<Player, SafeZoneCountdown> safeZoneWarningTasks = new HashMap<Player, SafeZoneCountdown>();
	public static Set<Player> alreadyWaitedCoutndown = new HashSet<Player>();
	//Settings
	public static int safeZoneTime;
	
	public void onDisable(){
		logger.info("[SafeZone] has been disabled.");
	}
	
	public void onEnable(){
		if (!hookCombatLog()){
			getServer().getPluginManager().disablePlugin(this);
			logger.severe("[SafeZone] Cannot be enabled because the CombatLog plugin was not found.");
			return;
		}
		if (!hookWorldGuard()){
			getServer().getPluginManager().disablePlugin(this);
			logger.severe("[SafeZone] Cannot be enabled because the WorldGuard plugin was not found.");
			return;
		}
		registerListeners();
		this.saveDefaultConfig();
		initSettings();
		new Messages(this.getConfig());
		logger.info("[SafeZone] has been enabled.");
	}
	
	public HashMap<String, Long> getTaggedPlayers(){
		return this.cl.taggedPlayers;
	}
	
	private boolean hookCombatLog(){
		//Hook into combat tag.
		Plugin combatLog = getServer().getPluginManager().getPlugin("CombatLog");
		if (combatLog == null || !(combatLog instanceof CombatLog)){
			return false;
		}else{
			cl = (CombatLog) combatLog;
			return true;
		}
	}
	
	private boolean hookWorldGuard(){
		Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

		// WorldGuard may not be loaded
		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
		    return false;
		}
	    this.wg = (WorldGuardPlugin) plugin;
	    return true;
	}
	
	private void registerListeners(){
		getServer().getPluginManager().registerEvents(new SafeZoneDeathListener(), this);
		getServer().getPluginManager().registerEvents(new SafeZoneMoveListener(this, this.wg), this);
		getServer().getPluginManager().registerEvents(new SafeZonePvPListener(), this);
	}
	
	private void initSettings(){
		Configuration config = this.getConfig();
		if (config.contains("Settings.SafeZoneTime")){
			try{
				safeZoneTime = config.getInt("Settings.SafeZoneTime");
			}catch (Exception e){
				logger.info("[SafeZone] Error in config at Settings.SafeZoneTime");
			}
		}else{
			logger.info("[SafeZone] Error missing setting in config at Settings.SafeZoneTime");
			safeZoneTime = 0;
		}
	}
}
