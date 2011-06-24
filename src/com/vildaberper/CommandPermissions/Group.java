package com.vildaberper.CommandPermissions;

import java.util.LinkedList;
import java.util.List;

public class Group{
	private String name;
	private boolean def = false;
	private List<String> permissions = new LinkedList<String>();

	Group(String name, boolean def, List<String> permissions){
		this.name = name;
		this.def = def;
		this.permissions = permissions;
	}

	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setDef(boolean def){
		this.def = def;
	}
	public boolean isDef(){
		return def;
	}
	public void setPermissions(List<String> permissions){
		this.permissions = permissions;
	}
	public List<String> getPermissions(){
		return permissions;
	}
}