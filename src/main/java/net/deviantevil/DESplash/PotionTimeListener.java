package net.deviantevil.DESplash;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PotionTimeListener implements Listener {

	public static final String MSG_TIMESTAMP = ChatColor.RED
			+ "You cannot use a splash potion for %.1f second(s).";

	Map<String, Long> mPlayerTimeMap;
	SplashPotionPlugin mPlugin;

	public PotionTimeListener(SplashPotionPlugin plugin) {
		mPlayerTimeMap = new HashMap<String, Long>();
		mPlugin = plugin;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		int id = e.getPlayer().getItemInHand().getTypeId();
		int dmg = e.getPlayer().getItemInHand().getDurability();
		/* If item is a splash potion */
		if (id == 373 && (dmg & 0x4000) != 0) {
			long timestamp = getTimestamp(e.getPlayer());
			long time = System.currentTimeMillis();
			long delta = time - timestamp;
			if (time != 0 && delta < mPlugin.getConfigPotionTime()) {
				/* Can not throw a splash potion */
				double remaining = (mPlugin.getConfigPotionTime() - delta);
				e.setCancelled(true);
				e.getPlayer().sendMessage(
						String.format(MSG_TIMESTAMP, remaining/1000));
			} else {
				/* Can throw a splash potion */
				setTimestamp(e.getPlayer(), time);
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent e) {
		mPlayerTimeMap.remove(e.getPlayer().getName());
	}

	public long getTimestamp(Player player) {
		Long time = mPlayerTimeMap.get(player.getName());
		return time == null ? 0 : time;
	}

	public void setTimestamp(Player player, long time) {
		mPlayerTimeMap.put(player.getName(), time);
	}

}
