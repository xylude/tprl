package com.craftmoar.tprl;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
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
				Location loc = this.getRandomLoc(default_min, default_max, default_world, 0);
				player.setNoDamageTicks(ndt);
				player.teleport(loc);
			} else if (args.length == 2) {
				// send to a random location in the specified params
				int default_min = Integer.parseInt(args[0]);
				int default_max = Integer.parseInt(args[1]);
				Location loc = this.getRandomLoc(default_min, default_max, default_world, 0);
				player.setNoDamageTicks(ndt);
				
				player.teleport(loc);
			} else {
				sender.sendMessage("Usage: /tprl [loc1] [loc2] or /tprl (for totally random)");
				return false;
			}
		}
		return true;
	}

	private Location getRandomLoc(int min, int max, World world, int attempts) {
		if(attempts>5) {
			return null;
		}
		int x = randomNum(min, max);
		int height = 140;
		int z = randomNum(min, max);
		Location loc = new Location(world, x, height, z);
		while(loc.getBlock().getType()==Material.AIR) {
			if(height<1) {
				//Looks like this entire column was air?
				getRandomLoc(min, max, world, attempts++);
			}
			height--;
			loc = new Location(world, x, height, z);
		}
		return new Location(world, x, height+1, z);
	}
	private int randomNum(Integer lownum, Integer highnum) {
		Random rand = new Random();
		return lownum + (int)(rand.nextDouble() * ((highnum - lownum) + 1));
	}
}