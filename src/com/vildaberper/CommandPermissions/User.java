package com.vildaberper.CommandPermissions;

import java.util.LinkedList;
import java.util.List;

public class User{
	private String name;
	private List<String> groups = new LinkedList<String>();
	private List<String> permissions = new LinkedList<String>();

	User(String name, List<String> groups, List<String> permissions){
		this.name = name;
		this.groups = groups;
		this.permissions = permissions;
	}

	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setGroups(List<String> groups){
		this.groups = groups;
	}
	public List<String> getGroups(){
		return groups;
	}
	public void setPermissions(List<String> permissions){
		this.permissions = permissions;
	}
	public List<String> getPermissions(){
		return permissions;
	}
}