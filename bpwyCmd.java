package fr.shiick.bringpetswithyou.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.shiick.bringpetswithyou.BringPetsWithYou;

public class bpwyCmd implements CommandExecutor {

	BringPetsWithYou bpwy;
	
	public bpwyCmd(BringPetsWithYou bpwy) {
		this.bpwy = bpwy;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.hasPermission("bpwy.admin")) {
				if(!(p.getInventory().firstEmpty() == -1)) {
					p.getInventory().addItem(bpwy.getTool());
					p.sendMessage(bpwy.color(bpwy.getConfig().getString("Message.GetTool")));
				} else {
					p.sendMessage(bpwy.color(bpwy.getConfig().getString("Message.Full")));
				}
			} else {
				p.sendMessage(bpwy.color(bpwy.getConfig().getString("Message.NoPerm")));
			}
		} else {
			sender.sendMessage(bpwy.getConfig().getString("Message.NotPlayer"));
		}
		return false;
	}

}
