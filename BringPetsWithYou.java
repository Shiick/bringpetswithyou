package fr.shiick.bringpetswithyou;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.shiick.bringpetswithyou.commands.bpwyCmd;
import fr.shiick.bringpetswithyou.events.playerInteractEvent;
import net.md_5.bungee.api.ChatColor;

public class BringPetsWithYou extends JavaPlugin {

	public HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	
	@Override
	public void onEnable() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new playerInteractEvent(this), this);
		getCommand("bringpetswithyou").setExecutor(new bpwyCmd(this));
		saveDefaultConfig();
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getTool() {
		Material material = Material.getMaterial(getConfig().getInt("Item.ID"));
		int data = getConfig().getInt("Item.DATA");
		String name = getConfig().getString("Item.Name");
		ItemStack item = new ItemStack(material, 1, (byte) data);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(color(name));
		itemM.addEnchant(Enchantment.DURABILITY, 1, true);
		itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemM);
		return item;
	}
	
	public String color(String msg) {
		String message = ChatColor.translateAlternateColorCodes('&', msg);
		return message;
	}
	
}
