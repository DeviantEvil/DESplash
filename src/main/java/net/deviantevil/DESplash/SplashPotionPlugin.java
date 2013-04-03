package net.deviantevil.DESplash;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class SplashPotionPlugin extends JavaPlugin {
	
	private PotionEffectListener mPotionEffectListener;
	private PotionTimeListener mPotionTimeListener;
	
	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		
		mPotionEffectListener = new PotionEffectListener(this);
		mPotionTimeListener = new PotionTimeListener(this);
		
		loadConfig();
		
		pm.registerEvents(mPotionEffectListener, this);
		pm.registerEvents(mPotionTimeListener, this);
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	private void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public double getConfigPotionIntensity(PotionEffectType type) {
		return getConfig().getDouble(
				"splash_intensity."+type.getName().toLowerCase(), 1.0);
	}
	
	public int getConfigPotionTime() {
		return getConfig().getInt("splash_time", 0);
	}

}
