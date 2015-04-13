package com.oliver.models;

public class Stock {
	
	private int id;
	private String code;
	private String name;
	private String prefix;
	private int type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "Stock [id=" + id + ", code=" + code + ", name=" + name
				+ ", prefix=" + prefix + ", type=" + type + "]";
	}
	
	
	
}
