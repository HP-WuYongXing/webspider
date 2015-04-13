package com.oliver.models;

public class StockInfo {

	private int id;
	private String issuingDate;
	private String listingDate;
	private String exchange;
	private String category;
	private double circulatingStock;
	private double totalStock;
	private String consignee;
	private int companyId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIssuingDate() {
		return issuingDate;
	}
	public void setIssuingDate(String issuingDate) {
		this.issuingDate = issuingDate;
	}
	public String getListingDate() {
		return listingDate;
	}
	public void setListingDate(String listingDate) {
		this.listingDate = listingDate;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public double getCirculatingStock() {
		return circulatingStock;
	}
	public void setCirculatingStock(double circulatingStock) {
		this.circulatingStock = circulatingStock;
	}
	public double getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(double totalStock) {
		this.totalStock = totalStock;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	@Override
	public String toString() {
		return "StockInfo [id=" + id + ", issuingDate=" + issuingDate
				+ ", listingDate=" + listingDate + ", exchange=" + exchange
				+ ", category=" + category + ", circulatingStock="
				+ circulatingStock + ", totalStock=" + totalStock
				+ ", consignee=" + consignee + ", companyId=" + companyId + "]";
	}
	
	
	
}
