package com.vildaberper.CommandPermissions;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.plugin.Plugin;
import org.bukkit.util.config.Configuration;

public class Perm{
	static Plugin plugin;
	private static List<Permissions> permissions = new LinkedList<Permissions>();

	public static Permissions getPermissions(String world){
		for(Permissions p : permissions){
			if(p.getWorld().equals(world)){
				return p;
			}
		}
		return null;
	}

	public static boolean hasPermission(String world, String user, String command){
		if(getPermissions(world) != null){
			if(getPermissions(world).hasPermission(user, command)){
				return true;
			}
		}
		return false;
	}

	public static void addPermissions(String world, String copy, List<User> users, List<Group> groups){
		permissions.add(new Permissions(world, copy, users, groups));
	}
	public static void delPermissions(String world){
		permissions.remove(getPermissions(world));
	}

	public static void savePermissions(String world){
		Configuration w = new Configuration(new File(plugin.getDataFolder(), world + ".yml"));

		w.setProperty("Copy", getPermissions(world).getCopy());
		for(Group g : getPermissions(world).getGroups()){
			w.setProperty("Groups." + g.getName() + ".Permissions", g.getPermissions());
			w.setProperty("Groups." + g.getName() + ".Default", g.isDef());
		}
		for(User u : getPermissions(world).getUsers()){
			w.setProperty("Users." + u.getName() + ".Permissions", u.getPermissions());
			w.setProperty("Users." + u.getName() + ".Groups", u.getGroups());
		}
		w.save();
	}
	public static void loadPermissions(String world){
		Configuration w = new Configuration(new File(plugin.getDataFolder(), world + ".yml"));
		List<Group> groups = new LinkedList<Group>();
		List<User> users = new LinkedList<User>();

		w.load();
		if(w.getKeys("Groups") != null){
			for(String g : w.getKeys("Groups")){
				groups.add(new Group(g, w.getBoolean("Groups." + g + ".Default", false), w.getStringList("Groups." + g + ".Permissions", new LinkedList<String>())));
			}
		}
		if(w.getKeys("Users") != null){
			for(String u : w.getKeys("Users")){
				users.add(new User(u, w.getStringList("Users." + u + ".Groups", new LinkedList<String>()), w.getStringList("Users." + u + ".Permissions", new LinkedList<String>())));
			}
		}
		Perm.addPermissions(world, w.getString("Copy", null), users, groups);
	}
	public static List<String> getWorlds(){
		List<String> list = new LinkedList<String>();

		for(Permissions p : permissions){
			list.add(p.getWorld());
		}
		return list;
	}
	public static File[] getFiles(){
		return plugin.getDataFolder().listFiles();
	}
}