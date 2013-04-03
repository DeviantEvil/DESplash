package net.deviantevil.DESplash;

import java.util.Collection;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;

import com.google.common.collect.Iterables;

public class PotionEffectListener implements Listener {
	
	SplashPotionPlugin mPlugin;
	
	public PotionEffectListener(SplashPotionPlugin plugin) {
		mPlugin = plugin;
	}
	
	@EventHandler
	public void onPotionSplash(PotionSplashEvent e) {
		ThrownPotion potion = e.getEntity();
		Collection<PotionEffect> effects = potion.getEffects();
		PotionEffect effect;
		double intensity;
		/* More than one effect, custom potion, do not apply */
		if(effects.size() == 0 || effects.size() > 1) return;
		
		effect = Iterables.get(effects, 0);
		intensity = mPlugin.getConfigPotionIntensity(effect.getType());
		
		for(LivingEntity l : e.getAffectedEntities()) {
			e.setIntensity(l, e.getIntensity(l)*intensity);
		}
	}

}
