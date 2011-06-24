package com.vildaberper.CommandPermissions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandPermissions extends JavaPlugin{
	public CommandPermissionsPlayerListener playerListener = new CommandPermissionsPlayerListener();
	@Override
	public void onDisable(){
		System.out.println(this.getDescription().getName() + " " + this.getDescription().getVersion() + " is disabled.");
	}

	@Override
	public void onEnable(){
		if(Perm.getFiles() != null && Perm.getFiles().length > 0){
			for(File file : Perm.getFiles()){
				Perm.loadPermissions(file.getName().substring(0, file.getName().length() - 4));
			}
		}else{
			FileWriter fw;
			try{
				this.getDataFolder().mkdir();
				fw = new FileWriter(new File(this.getDataFolder(), "RENAME-ME.yml"), true);

				fw.write("Copy: null # Should it ignore thease permissions and copy another world's?" + System.getProperty("line.separator"));
				fw.write("Groups:" + System.getProperty("line.separator"));
				fw.write("    Default: # The group's name, can be anything." + System.getProperty("line.separator"));
				fw.write("        Default: true # Should it be one of the default groups for undefined players?" + System.getProperty("line.separator"));
				fw.write("        Permissions: # Add your commands here." + System.getProperty("line.separator"));
				fw.write("            - '/list' # Allows the members of the group to use /list." + System.getProperty("line.separator"));
				fw.write("            - '/tp'" + System.getProperty("line.separator"));
				fw.write("    Admin:" + System.getProperty("line.separator"));
				fw.write("        Default: false" + System.getProperty("line.separator"));
				fw.write("        Permissions:" + System.getProperty("line.separator"));
				fw.write("            - '*' # This means all commands" + System.getProperty("line.separator"));
				fw.write("Users:" + System.getProperty("line.separator"));
				fw.write("    vildaberper: # The user's name." + System.getProperty("line.separator"));
				fw.write("        Groups: # What groups is the user a member of?" + System.getProperty("line.separator"));
				fw.write("            - 'Default'" + System.getProperty("line.separator"));
				fw.write("            - 'Admin'" + System.getProperty("line.separator"));
				fw.write("        Permissions: # User-specific commands." + System.getProperty("line.separator"));
				fw.write("            - '/reload'" + System.getProperty("line.separator"));
				fw.write("    Isakksson:" + System.getProperty("line.separator"));
				fw.write("        Groups:" + System.getProperty("line.separator"));
				fw.write("            - 'Default'" + System.getProperty("line.separator"));
				fw.write("        Permissions:" + System.getProperty("line.separator"));
				fw.write("            - '/help'" + System.getProperty("line.separator"));
				fw.flush();
				fw.close();
				System.out.println("Template file generated, remember to rename it to your world!");
			}catch(IOException e){
				System.out.println("Failed to generate template.");
			}
		}
		this.getServer().getPluginManager().registerEvent(Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Highest, this);
		System.out.println(this.getDescription().getName() + " " + this.getDescription().getVersion() + " is enabled. Loaded " + Perm.getWorlds().size() + " world(s).");
	}

	public void onLoad(){
		Perm.plugin = this;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(command.getName().equalsIgnoreCase("cperm")){
			if(args.length >= 2){
				if(args[0].equalsIgnoreCase("load")){
					if(!args[1].equalsIgnoreCase("all")){
						if(new File(this.getDataFolder(), args[1]+ ".yml").exists()){
							Perm.delPermissions(args[1]);
							Perm.loadPermissions(args[1]);
							sender.sendMessage("Successfully loaded '" + args[1] + ".yml'.");
							return true;
						}
						sender.sendMessage("Could not find '" + args[1] + ".yml'.");
					}else{
						if(this.getDataFolder().listFiles() != null){
							for(File file : this.getDataFolder().listFiles()){
								Perm.delPermissions(file.getName().substring(0, file.getName().length() - 4));
								Perm.loadPermissions(file.getName().substring(0, file.getName().length() - 4));
							}
							sender.sendMessage("Successfully loaded " + this.getDataFolder().listFiles().length + " world(s).");
							return true;
						}
						sender.sendMessage("There are no worlds to load.");
					}
				}else if(args[0].equalsIgnoreCase("save")){
					if(!args[1].equalsIgnoreCase("all")){
						if(Perm.getPermissions(args[1]) != null){
							Perm.savePermissions(args[1]);
							sender.sendMessage("Successfully saved '" + args[1] + ".yml'.");
							return true;
						}
						sender.sendMessage("Could not save '" + args[1] + ".yml'.");
					}else{
						if(Perm.getWorlds() != null){
							for(String w : Perm.getWorlds()){
								Perm.savePermissions(w);
							}
							sender.sendMessage("Successfully saved " + Perm.getWorlds().size() + " world(s).");
							return true;
						}
						sender.sendMessage("There are no worlds to save.");
					}
				}
			}
		}
		return false;
	}
}