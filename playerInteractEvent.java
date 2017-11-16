package fr.shiick.bringpetswithyou.events;

import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import fr.shiick.bringpetswithyou.BringPetsWithYou;

public class playerInteractEvent implements Listener {

	BringPetsWithYou bpwy;

	public playerInteractEvent(BringPetsWithYou bpwy) {
		this.bpwy = bpwy;
	}

	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();
		Entity entity = e.getRightClicked();
		ItemStack item = p.getInventory().getItemInMainHand();
		if (item.getItemMeta().getDisplayName().equalsIgnoreCase(bpwy.color(bpwy.getConfig().getString("Item.Name")))) {
			UUID uuid = e.getPlayer().getUniqueId();
			if (bpwy.cooldown.containsKey(uuid)) {
				float time = (System.currentTimeMillis() - bpwy.cooldown.get(uuid)) / 1000;
				if (!(time < 0.1f)) {
					if (!(entity instanceof Player)) {
						if (p.getPassengers().size() <= bpwy.getConfig().getInt("Config.EntityLimit")) {
							if (p.getPassengers().size() == 0) {
								p.addPassenger(entity);
								p.sendMessage(bpwy.color(bpwy.getConfig().getString("Message.EntityMount").replace("%entity%", entity.getName())));
							} else if (p.getPassengers().contains(entity)) {
								p.removePassenger(entity);
								p.sendMessage(bpwy.color(bpwy.getConfig().getString("Message.EntityDismount").replace("%entity%", entity.getName())));
							}
						} else {
							p.sendMessage(bpwy.color(bpwy.getConfig().getString("Message.NoPlace")));
						}
					} else {
						p.sendMessage(bpwy.color(bpwy.getConfig().getString("Message.Player")));
					}
					bpwy.cooldown.put(uuid, System.currentTimeMillis());
				}
			} else {
				bpwy.cooldown.put(uuid, System.currentTimeMillis());
			}
		}
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		bpwy.cooldown.remove(e.getPlayer().getUniqueId());
	}

}
