package com.craftmoar.tprl;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Tprl extends JavaPlugin {
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
	}

	@Override
	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("tprl")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("This command cannot be used from the console.");
				return false;
			}
			Player player = (Player) sender;
			World default_world = player.getWorld();
			int ndt = Integer.parseInt(this.getConfig().getString("default_invulnerability_ticks")); 
			if (args.length == 0) {
				// use the config.yml values
				int default_min = Integer.parseInt(this.getConfig().getString(
						"min_default_location"));
				int default_max = Integer.parseInt(this.getConfig().getString(
						"max_default_location"));
				Location loc = this.getRandomLoc(default_min, default_max, default_world);
				player.setNoDamageTicks(ndt);
				player.teleport(loc);
			} else if (args.length == 2) {
				// send to a random location in the specified params
				int default_min = Integer.parseInt(args[0]);
				int default_max = Integer.parseInt(args[1]);
				Location loc = this.getRandomLoc(default_min, default_max, default_world);
				player.setNoDamageTicks(ndt);
				player.teleport(loc);
			} else {
				sender.sendMessage("Usage: /tprl [loc1] [loc2] or /tprl (for totally random)");
				return false;
			}
		}
		return true;
	}

	private Location getRandomLoc(int min, int max, World world) {
		Location loc = new Location(world, this.randomNum(min, max), 255,
				this.randomNum(min, max));
		return loc;
	}

	private int randomNum(Integer lownum, Integer highnum) {
		Random rand = new Random();
		int randomNum = rand.nextInt(highnum - lownum + 1) + lownum;
		return randomNum;
	}
}