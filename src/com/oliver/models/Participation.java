package com.oliver.models;

public class Participation {
	
	private int id;
	private String companyName;
	private float money;
	private float proportion;
	private String business;
	private int companyId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	public float getProportion() {
		return proportion;
	}
	public void setProportion(float proportion) {
		this.proportion = proportion;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		return "Participation [id=" + id + ", companyName=" + companyName
				+ ", money=" + money + ", proportion=" + proportion
				+ ", business=" + business + ", companyId=" + companyId + "]";
	}
	
	
	
	
	
	
}
