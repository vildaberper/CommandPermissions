package com.vildaberper.CommandPermissions;

import java.util.LinkedList;
import java.util.List;

public class Permissions{
	private String world;
	private String copy = null;
	private List<User> users = new LinkedList<User>();
	private List<Group> groups = new LinkedList<Group>();

	Permissions(String world, String copy, List<User> users, List<Group> groups){
		this.world = world;
		this.copy = copy;
		this.users = users;
		this.groups = groups;
	}

	public void setWorld(String world){
		this.world = world;
	}
	public String getWorld(){
		return world;
	}
	public void setCopy(String copy){
		this.copy = copy;
	}
	public String getCopy(){
		return copy;
	}
	public void setUsers(List<User> users){
		this.users = users;
	}
	public List<User> getUsers(){
		return users;
	}
	public void setGroups(List<Group> groups){
		this.groups = groups;
	}
	public List<Group> getGroups(){
		return groups;
	}

	private User getUser(String user){
		List<String> groups = new LinkedList<String>();
		if(this.copy != null){
			return Perm.getPermissions(this.copy).getUser(user);
		}
		for(User u : this.users){
			if(u.getName().equals(user)){
				return u;
			}
		}
		for(Group g : this.groups){
			if(g.isDef()){
				groups.add(g.getName());
			}
		}
		return new User(user, groups, new LinkedList<String>());
	}
	private Group getGroup(String group){
		if(this.copy != null){
			return Perm.getPermissions(this.copy).getGroup(group);
		}
		for(Group g : this.groups){
			if(g.getName().equals(group)){
				return g;
			}
		}
		return new Group(group, false, new LinkedList<String>());
	}

	public boolean hasPermission(String user, String command){
		if(hasPermissionUser(user, command)){
			return true;
		}
		for(String s : getUser(user).getGroups()){
			if(hasPermissionGroup(s, command)){
				return true;
			}
		}
		return false;
	}
	public boolean hasPermissionUser(String user, String command){
		if(this.copy != null){
			return Perm.getPermissions(this.copy).hasPermissionUser(user, command);
		}
		for(String p : getUser(user).getPermissions()){
			if(p.equals(command) || p.equals("*")){
				return true;
			}
		}
		return false;
	}
	public boolean hasPermissionGroup(String group, String command){
		if(this.copy != null){
			return Perm.getPermissions(this.copy).hasPermissionGroup(group, command);
		}
		for(String p : getGroup(group).getPermissions()){
			if(p.equals(command) || p.equals("*")){
				return true;
			}
		}
		return false;
	}
}